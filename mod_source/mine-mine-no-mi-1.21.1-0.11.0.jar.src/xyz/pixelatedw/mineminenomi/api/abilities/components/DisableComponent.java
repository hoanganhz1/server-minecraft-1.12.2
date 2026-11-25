package xyz.pixelatedw.mineminenomi.api.abilities.components;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.api.ui.TexturedRectUI;
import xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool;
import xyz.pixelatedw.mineminenomi.config.ClientConfig;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SStartDisablePacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SStopDisablePacket;

public class DisableComponent extends AbilityComponent<IAbility> {
	// Fields
	private boolean isDisabled;
	private float startDisableTime;
	private float disableTime;
	private boolean isInfinite;
	private final PriorityEventPool<DisableComponent$IStartDisableEvent> startDisableEvents;
	private final PriorityEventPool<DisableComponent$IEndDisableEvent> stopDisableEvents;

	public DisableComponent(IAbility var1) {
		super(((AbilityComponentKey)(ModAbilityComponents.DISABLE.get())),var1);
		this.startDisableEvents = (PriorityEventPool) (new PriorityEventPool());
		this.stopDisableEvents = (PriorityEventPool) (new PriorityEventPool());
		this.setTickRate(1);
	}
	public void postInit(IAbility var1) {
		DisableComponent var2 = this;
		Consumer<SlotDecorationComponent> var3 = (l0) -> {
			DisableComponent lv3_2 = this;
			SlotDecorationComponent lv3_3 = l0;
			final SlotDecorationComponent lv3_3_f1 = lv3_3;
			SlotDecorationComponent$IPreRenderEvent lv3_4 = (l2_0, l2_1, l2_2, l2_3, l2_4, l2_5, l2_6, l2_7) -> {
				if (this.isDisabled()){
					lv3_3_f1.setMaxValue(this.startDisableTime);
					lv3_3_f1.setCurrentValue(this.disableTime);
					if (ClientConfig.isDisplayInPercentage()){
						float lv1_10 = ((this.disableTime) / (this.startDisableTime)) * (100F);
						lv1_10 = Mth.clamp(lv1_10,0F,100F);
						String lv1_11 = String.format("%.0f",new Object[]{Float.valueOf(lv1_10)});
						String lv1_12 = lv1_11 + "%";
						lv3_3_f1.setDisplayText(lv1_12);
					}
					lv3_3_f1.setSlotColor(0F,0F,0F);
					lv3_3_f1.setIconColor(0.4F,0.4F,0.4F);
				}
			};
			l0.addPreRenderEvent(999,lv3_4);
			DisableComponent lv3_5 = this;
			SlotDecorationComponent$IPostRenderEvent lv3_6 = (l2_0, l2_1, l2_2, l2_3, l2_4, l2_5, l2_6, l2_7) -> {
				if (this.isDisabled()){
					RendererHelper.drawIcon(ModResources.DISABLED_ABILITY,l2_2,(l2_5) + (4F),(l2_6) + (4F),0F,16F,16F);
					RenderSystem.setShaderTexture(0,ModResources.WIDGETS);
				}
			};
			l0.addPostRenderEvent(999,lv3_6);
		};
		((java.util.Optional)(var1.getComponent(((AbilityComponentKey)(ModAbilityComponents.SLOT_DECORATION.get()))))).ifPresent((Consumer)(var3));
	}
	public DisableComponent addStartEvent(DisableComponent$IStartDisableEvent var1) {
		this.startDisableEvents.addEvent(var1);
		return this;
	}
	public DisableComponent addStartEvent(int var1, DisableComponent$IStartDisableEvent var2) {
		this.startDisableEvents.addEvent(var1,var2);
		return this;
	}
	public DisableComponent addEndEvent(DisableComponent$IEndDisableEvent var1) {
		this.stopDisableEvents.addEvent(var1);
		return this;
	}
	public DisableComponent addEndEvent(int var1, DisableComponent$IEndDisableEvent var2) {
		this.stopDisableEvents.addEvent(var1,var2);
		return this;
	}
	protected void doTick(LivingEntity var1) {
		DisableComponent var2 = this;
		Consumer var3 = (Consumer) ((Consumer<AltModeComponent>) ((l0) -> {
			l0.setDisabled(this.isDisabled());
		}));
		this.getAbility().getComponent(((AbilityComponentKey)(ModAbilityComponents.ALT_MODE.get()))).ifPresent(var3);
		if (this.isDisabled()){
			if ((this.disableTime) > (0F)){
				AttributeInstance var4 = var1.getAttribute(ModAttributes.TIME_PROGRESSION);
				double var5 = 1D;
				if ((var4) != (null)){
					var5 = var4.getValue();
				}
				this.disableTime = (float)(((double)(this.disableTime)) - ((1D) * (var5)));
			}else{
				if (((this.disableTime) <= (0F)) && (!(this.isInfinite))){
					this.stopDisable(var1);
					return;
				}else{
					return;
				}
			}
		}
	}
	public boolean isDisabled() {
		return this.isDisabled;
	}
	public float getStartDisableTime() {
		return this.startDisableTime;
	}
	public float getDisableTime() {
		return this.disableTime;
	}
	public boolean isInfinite() {
		return this.isInfinite;
	}
	public void startDisable(LivingEntity var1, float var2) {
		this.ensureIsRegistered();
		if (this.isDisabled()){
			return;
		}else{
			if ((var2) < (0F)){
				this.isInfinite = true;
			}else{
				this.isInfinite = false;
			}
			this.startDisableTime = var2;
			this.disableTime = var2;
			this.isDisabled = true;
			DisableComponent var3 = this;
			LivingEntity var4 = var1;
			final LivingEntity var4_f1 = var4;
			Consumer<DisableComponent$IStartDisableEvent> var5 = (l0) -> {
			l0.startDisable(var4_f1,this.getAbility());
		};
			((xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool)(this.startDisableEvents)).dispatch((Consumer)(var5));
			if (!(var1.level().isClientSide)){
				ModNetwork.sendToAllTrackingAndSelf(new SStartDisablePacket((LivingEntity)(var1),(IAbility)(this.getAbility()),var2),var1);
			}
			return;
		}
	}
	public void stopDisable(LivingEntity var1) {
		if (!(this.isDisabled())){
			return;
		}else{
			DisableComponent var2 = this;
			LivingEntity var3 = var1;
			final LivingEntity var3_f1 = var3;
			Consumer<DisableComponent$IEndDisableEvent> var4 = (l0) -> {
			l0.endDisable(var3_f1,this.getAbility());
		};
			((xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool)(this.stopDisableEvents)).dispatch((Consumer)(var4));
			this.disableTime = 0F;
			this.isDisabled = false;
			Consumer<SlotDecorationComponent> var5 = (l0) -> {
			l0.resetDecoration();
		};
			((java.util.Optional)(this.getAbility().getComponent(((AbilityComponentKey)(ModAbilityComponents.SLOT_DECORATION.get()))))).ifPresent((Consumer)(var5));
			if (!(var1.level().isClientSide)){
				ModNetwork.sendToAllTrackingAndSelf(new SStopDisablePacket((LivingEntity)(var1),(IAbility)(this.getAbility())),var1);
			}
			return;
		}
	}
	@Nullable()
	public @Nullable() CompoundTag save(HolderLookup.Provider var1) {
		CompoundTag var2 = super.save(var1);
		var2.putFloat("disableTime",this.disableTime);
		var2.putFloat("startDisableTime",this.startDisableTime);
		var2.putBoolean("isInfinite",this.isInfinite);
		return var2;
	}
	public void load(HolderLookup.Provider var1, CompoundTag var2) {
		super.load(var1,var2);
		this.disableTime = var2.getFloat("disableTime");
		this.startDisableTime = var2.getFloat("startDisableTime");
		this.isInfinite = var2.getBoolean("isInfinite");
		if ((this.disableTime) > (0F)){
			this.isDisabled = true;
		}
	}
}