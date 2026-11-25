package xyz.pixelatedw.mineminenomi.api.abilities.components;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnEquipEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine$IDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityPool;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityStat;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityStat$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.api.ui.TexturedRectUI;
import xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SSetStacksPacket;

public class StackComponent extends AbilityComponent<IAbility> {
	// Fields
	private int defaultStacks;
	private int stacks;
	private final PriorityEventPool<StackComponent$IStacksChangeEvent> changeStackEvents;

	@Deprecated()
	public static AbilityDescriptionLine$IDescriptionLine getTooltip(int var0) {
		return getTooltip(var0,var0);
	}
	@Deprecated()
	public static AbilityDescriptionLine$IDescriptionLine getTooltip(int var0, int var1) {
		int var2 = var0;
		int var3 = var1;
		final int var2_f1 = var2;
		final int var3_f2 = var3;
		AbilityDescriptionLine$IDescriptionLine var4 = (l0, l1) -> {
			return new AbilityStat$Builder((Component)(ModI18nAbilities.DESCRIPTION_STAT_NAME_STACKS),var2_f1,var3_f2).build().getStatDescription();
		};
		return var4;
	}
	public StackComponent(IAbility var1) {
		this(var1,0);
	}
	public StackComponent(IAbility var1, int var2) {
		super(((AbilityComponentKey)(ModAbilityComponents.STACK.get())),var1);
		this.defaultStacks = 0;
		this.stacks = -1;
		this.changeStackEvents = (PriorityEventPool) (new PriorityEventPool());
		this.defaultStacks = var2;
	}
	public void postInit(IAbility var1) {
		StackComponent var2 = this;
		Consumer<SlotDecorationComponent> var3 = (l0) -> {
			StackComponent lv3_2 = this;
			SlotDecorationComponent$IPostRenderEvent lv3_3 = (l2_0, l2_1, l2_2, l2_3, l2_4, l2_5, l2_6, l2_7) -> {
				if ((this.getStacks()) > (0)){
					l2_2.pushPose();
					l2_2.translate((l2_5) + (16F),(l2_6) + (14F),5F);
					l2_2.scale(0.76F,0.76F,0.76F);
					int lv2_9 = this.getStacks();
					String lv2_10 = "" + lv2_9;
					RendererHelper.drawStringWithBorder(l2_1.font,l2_2,l2_3,Component.literal(lv2_10).getVisualOrderText(),0,0,-1,0);
					l2_2.popPose();
				}
			};
			l0.addPostRenderEvent(10,lv3_3);
		};
		((java.util.Optional)(var1.getComponent(((AbilityComponentKey)(ModAbilityComponents.SLOT_DECORATION.get()))))).ifPresent((Consumer)(var3));
		if (var1 instanceof Ability){
			((Ability)(var1)).addEquipEvent((Ability$IOnEquipEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnEquipEvent)(this::onAbilityEquipped))));
		}
	}
	public StackComponent addStackChangeEvent(StackComponent$IStacksChangeEvent var1) {
		this.changeStackEvents.addEvent(var1);
		return this;
	}
	public StackComponent addStackChangeEvent(int var1, StackComponent$IStacksChangeEvent var2) {
		this.changeStackEvents.addEvent(var1,var2);
		return this;
	}
	public void revertStacksToDefault(LivingEntity var1, IAbility var2) {
		this.setStacks(var1,var2,this.defaultStacks);
	}
	public void addStacks(LivingEntity var1, IAbility var2, int var3) {
		this.setStacks(var1,var2,(this.stacks) + (var3));
	}
	public void setStacks(LivingEntity var1, IAbility var2, int var3) {
		this.ensureIsRegistered();
		if ((this.stacks) == (var3)){
			return;
		}else{
			this.stacks = var3;
			StackComponent var4 = this;
			LivingEntity var5 = var1;
			int var6 = var3;
			final LivingEntity var5_f1 = var5;
			final int var6_f2 = var6;
			Consumer<StackComponent$IStacksChangeEvent> var7 = (l0) -> {
			l0.onStacksChange(var5_f1,this.getAbility(),var6_f2);
		};
			((xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool)(this.changeStackEvents)).dispatch((Consumer)(var7));
			if (!(var1.level().isClientSide)){
				Optional var8 = this.getAbility().getComponent(((AbilityComponentKey)(ModAbilityComponents.POOL.get())));
				if (var8.isPresent()){
					AbilityData var9 = ((AbilityData)(((java.util.Optional)(AbilityData.get(var1))).orElse((Object)(null))));
					if ((var9) != (null)){
						Iterator var10 = var9.getEquippedAndPassiveAbilities().iterator();
						do{
							if (var10.hasNext()){
								IAbility var11 = ((IAbility)(var10.next()));
								if ((var11) == (this.getAbility())){
									continue;
								}else{
									int var12 = var3;
									Optional var13 = var11.getComponent(((AbilityComponentKey)(ModAbilityComponents.POOL.get())));
									Optional var14 = var11.getComponent(((AbilityComponentKey)(ModAbilityComponents.STACK.get())));
									if ((var13.isPresent()) && ((((PoolComponent)(var8.get())).hasAtLeastOneSamePool(((PoolComponent)(var13.get())))) && ((var14.isPresent()) && (((PoolComponent)(var13.get())).getPools().stream().anyMatch((Predicate)(((Predicate<AbilityPool>)((l0) -> {
			Supplier<Boolean> lv6_1 = () -> {
				return Boolean.valueOf(false);
			};
			return ((Boolean)(l0.getFlagValue((String)("shareStacks"),(Supplier)(lv6_1)))).booleanValue();
		})))))))){
										if ((var12) > (((StackComponent)(var14.get())).getDefaultStacks())){
											var12 = ((StackComponent)(var14.get())).getDefaultStacks();
										}
										StackComponent var15 = ((StackComponent)(var14.get()));
										var15.stacks = var12;
										ModNetwork.sendToAllTrackingAndSelf(new SSetStacksPacket((LivingEntity)(var1),(IAbility)(var11),var12),var1);
										continue;
									}else{
										continue;
									}
								}
							}else{
								break;
							}
						} while (true);
					}
				}
				ModNetwork.sendToAllTrackingAndSelf(new SSetStacksPacket((LivingEntity)(var1),(IAbility)(var2),var3),var1);
			}
			return;
		}
	}
	public void setDefaultStacks(int var1) {
		this.defaultStacks = var1;
	}
	public int getDefaultStacks() {
		return this.defaultStacks;
	}
	public int getStacks() {
		return this.stacks;
	}
	private void onAbilityEquipped(LivingEntity var1, IAbility var2) {
		if ((this.stacks) < (0)){
			this.stacks = this.defaultStacks;
		}
	}
	@Nullable()
	public @Nullable() CompoundTag save(HolderLookup.Provider var1) {
		CompoundTag var2 = super.save(var1);
		var2.putInt("defaultStacks",this.defaultStacks);
		var2.putInt("stacks",this.stacks);
		return var2;
	}
	public void load(HolderLookup.Provider var1, CompoundTag var2) {
		super.load(var1,var2);
		this.defaultStacks = var2.getInt("defaultStacks");
		this.stacks = var2.getInt("stacks");
	}
}