package xyz.pixelatedw.mineminenomi.api.abilities.components;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.math.EasingFunctionHelper;
import xyz.pixelatedw.mineminenomi.api.ui.TexturedRectUI;
import xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SChangeAbilityAltModePacket;

public class AltModeComponent<E extends Enum<E>> extends AbilityComponent<IAbility> {
	// Fields
	private final PriorityEventPool<AltModeComponent$IChangeModeEvent<E>> changeModeEvents;
	private final Class<E> modeClass;
	private final E defaultMode;
	private E currentMode;
	private boolean isModeChangeAutomatic;
	private boolean playReadyAnim;
	private float readyAnim;

	public AltModeComponent(IAbility var1, Class<E> var2, E var3) {
		this(var1,var2,var3,false);
	}
	public AltModeComponent(IAbility var1, Class<E> var2, E var3, boolean var4) {
		super(((AbilityComponentKey)(ModAbilityComponents.ALT_MODE.get())),var1);
		this.changeModeEvents = new PriorityEventPool();
		this.playReadyAnim = false;
		this.readyAnim = 0F;
		this.modeClass = var2;
		this.defaultMode = var3;
		this.currentMode = var3;
		this.isModeChangeAutomatic = var4;
	}
	public AltModeComponent<E> addChangeModeEvent(AltModeComponent$IChangeModeEvent<E> var1) {
		this.changeModeEvents.addEvent(var1);
		return this;
	}
	public AltModeComponent<E> addChangeModeEvent(int var1, AltModeComponent$IChangeModeEvent<E> var2) {
		this.changeModeEvents.addEvent(var1,var2);
		return this;
	}
	public void setMode(LivingEntity var1, Enum<?> var2) {
		this.setMode(var1,var2,true);
	}
	public void revertToDefault(LivingEntity var1) {
		this.setMode(var1,(E)(this.defaultMode),true);
	}
	private void setMode(LivingEntity var1, E var2, boolean var3) {
		super.ensureIsRegistered();
		if (!(this.isMode(var2))){
			try{
				if (((!(this.changeModeEvents.dispatchCancelable((Predicate)(((java.util.function.Predicate)((Predicate) ((Predicate<AltModeComponent$IChangeModeEvent>) ((l0) -> {
			return !(l0.changeMode(var1,this.getAbility(),var2));
		})))))))) ? (1) : (0)) == (0)){
					return;
				}else{
					this.currentMode = var2;
					this.playReadyAnim = true;
				}
			}catch(Exception var4){
				var4.printStackTrace();
				return;
			}
		}
		if ((var3) && (var1 instanceof ServerPlayer)){
			ServerPlayer var4_1 = ((ServerPlayer)(var1));
			ModNetwork.sendToAllTrackingAndSelf(new SChangeAbilityAltModePacket((LivingEntity)(var4_1),this.getAbility(),this.getCurrentMode()),(LivingEntity)(var4_1));
			if (!(this.isAutomatic())){
				Object[] var5 = new Object[]{this.getAbility().getDisplayName().getString(),this.getCurrentMode().name()};
				var4_1.sendSystemMessage((Component)(Component.translatable(ModI18nAbilities.MESSAGE_ALT_MODE_CHANGE,var5)));
			}else{
				return;
			}
		}
	}
	public void setNextInCycle(LivingEntity var1) {
		Enum[] var2 = ((Enum[])(this.modeClass.getEnumConstants()));
		this.setMode(var1,var2[((this.currentMode.ordinal()) + (1)) % (var2.length)]);
	}
	public boolean isMode(E var1) {
		return this.currentMode.equals(var1);
	}
	public E getCurrentMode() {
		return (E) (this.currentMode);
	}
	public Class<E> getAltMode() {
		return (Class<E>) (Class) (this.modeClass);
	}
	public boolean isAutomatic() {
		return this.isModeChangeAutomatic;
	}
	public void postInit(IAbility var1) {
		AltModeComponent var2 = this;
		Consumer<SlotDecorationComponent> var3 = (l0) -> {
			AltModeComponent lv3_2 = this;
			SlotDecorationComponent$IPostRenderEvent lv3_3 = (l2_0, l2_1, l2_2, l2_3, l2_4, l2_5, l2_6, l2_7) -> {
				if ((this.playReadyAnim) && ((this.readyAnim) <= (0F))){
					this.readyAnim = 30F;
				}else{
					if ((this.playReadyAnim) && ((this.readyAnim) > (0F))){
						this.readyAnim = (float)(((double)(this.readyAnim)) - ((1.75D) * ((double)(l2_1.getTimer().getGameTimeDeltaTicks()))));
						if ((this.readyAnim) <= (0F)){
							this.playReadyAnim = false;
						}else{
							if ((this.readyAnim) > (0F)){
								float lv2_9 = (this.readyAnim) * (0.03F);
								lv2_9 = Math.max(0F,lv2_9);
								float lv2_10 = (1.8F) * (EasingFunctionHelper.easeOutSine(Float.valueOf(lv2_9)));
								float lv2_11 = (1F) * (EasingFunctionHelper.easeOutSine(Float.valueOf(lv2_9)));
								lv2_10 = Mth.clamp(lv2_10,1F,lv2_10);
								l2_2.pushPose();
								l2_2.translate((double)(l2_5),(double)(l2_6),1D);
								l2_2.translate(12D,12D,0D);
								l2_2.scale(lv2_10,lv2_10,1F);
								l2_2.translate(-12D,-12D,0D);
								RenderSystem.enableBlend();
								l2_4.setSize(23F,23F);
								l2_4.setColor(1F,0.95F,0F,lv2_11);
								l2_4.draw(l2_2,0F,0F);
								RenderSystem.disableBlend();
								l2_2.popPose();
							}else{
								return;
							}
						}
					}else{
						return;
					}
				}
			};
			l0.addPostRenderEvent(10,lv3_3);
		};
		((java.util.Optional)(var1.getComponent(((AbilityComponentKey)(ModAbilityComponents.SLOT_DECORATION.get()))))).ifPresent((Consumer)(var3));
	}
	@Nullable()
	public @Nullable() CompoundTag save(HolderLookup.Provider var1) {
		CompoundTag var2 = super.save(var1);
		var2.putString("currentMode",this.currentMode.toString());
		return var2;
	}
	public void load(HolderLookup.Provider var1, CompoundTag var2) {
		super.load(var1,var2);
		try{
			this.currentMode = (E) (Enum.valueOf((Class)(this.modeClass),var2.getString("currentMode")));
		}catch(Exception var3){

		}
	}
}