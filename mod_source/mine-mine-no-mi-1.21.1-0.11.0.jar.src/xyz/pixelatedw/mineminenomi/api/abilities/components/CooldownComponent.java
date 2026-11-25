package xyz.pixelatedw.mineminenomi.api.abilities.components;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine$IDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityPool;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityStat$AbilityStatType;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityStat$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.math.EasingFunctionHelper;
import xyz.pixelatedw.mineminenomi.api.ui.TexturedRectUI;
import xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool;
import xyz.pixelatedw.mineminenomi.commands.FGCommand;
import xyz.pixelatedw.mineminenomi.config.ClientConfig;
import xyz.pixelatedw.mineminenomi.handlers.entity.PatreonHandler;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SStartCooldownPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SStopCooldownPacket;

public class CooldownComponent extends AbilityComponent<IAbility> {
	// Fields
	private static final UUID COOLDOWN_BONUS_MANAGER_UUID = UUID.fromString("99be6fa9-bcc6-4c9c-be00-ee024543015d");
	private boolean isOnCooldown;
	private float minCooldown;
	private float maxCooldown;
	private float startCooldown;
	private float cooldown;
	private boolean playReadyAnim;
	private float cooldownReadyAnim;
	private final PriorityEventPool<CooldownComponent$IStartCooldownEvent> startCooldownEvents;
	private final PriorityEventPool<CooldownComponent$IDuringCooldownEvent> tickCooldownEvents;
	private final PriorityEventPool<CooldownComponent$IEndCooldownEvent> stopCooldownEvents;
	private final BonusManager bonusManager;

	@Deprecated()
	public static AbilityDescriptionLine$IDescriptionLine getTooltip(float var0) {
		return getTooltip(var0,var0);
	}
	@Deprecated()
	public static AbilityDescriptionLine$IDescriptionLine getTooltip(float var0, float var1) {
		float var2 = var0;
		float var3 = var1;
		final float var2_f1 = var2;
		final float var3_f2 = var3;
		AbilityDescriptionLine$IDescriptionLine var4 = (l0, l1) -> {
			float lv2_4 = (float)(Math.round((var2_f1) / (20F)));
			float lv2_5 = (float)(Math.round((var3_f2) / (20F)));
			AbilityStat$Builder lv2_6 = new AbilityStat$Builder((Component)(ModI18nAbilities.DESCRIPTION_STAT_NAME_COOLDOWN),lv2_4,lv2_5).withUnit(ModI18nAbilities.DESCRIPTION_STAT_UNIT_SECONDS);
			float lv2_7 = var2_f1;
			float lv2_8 = var3_f2;
			AbilityStat$Builder lv2_9 = lv2_6;
			final float lv2_7_f1 = lv2_7;
			final float lv2_8_f2 = lv2_8;
			final AbilityStat$Builder lv2_9_f3 = lv2_9;
			Consumer<CooldownComponent> lv2_10 = (l2_0) -> {
				float lv1_4 = (float)(Math.round(((l2_0.getBonusManager().applyBonus(lv2_7_f1)) - (lv2_7_f1)) / (20F)));
				float lv1_5 = (float)(Math.round(((l2_0.getBonusManager().applyBonus(lv2_8_f2)) - (lv2_8_f2)) / (20F)));
				lv2_9_f3.withBonus(lv1_4,lv1_5,AbilityStat$AbilityStatType.from((lv1_4) + (lv1_5),true));
			};
			((java.util.Optional)(l1.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))))).ifPresent((Consumer)(lv2_10));
			return lv2_6.buildComponent();
		};
		return var4;
	}
	public CooldownComponent(IAbility var1) {
		super(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get())),var1);
		this.playReadyAnim = false;
		this.cooldownReadyAnim = 10F;
		this.startCooldownEvents = (PriorityEventPool) (new PriorityEventPool());
		this.tickCooldownEvents = (PriorityEventPool) (new PriorityEventPool());
		this.stopCooldownEvents = (PriorityEventPool) (new PriorityEventPool());
		this.bonusManager = new BonusManager(COOLDOWN_BONUS_MANAGER_UUID);
		this.addBonusManager(this.bonusManager);
	}
	public void postInit(IAbility var1) {
		CooldownComponent var2 = this;
		Consumer<SlotDecorationComponent> var3 = (l0) -> {
			CooldownComponent lv5_2 = this;
			SlotDecorationComponent lv5_3 = l0;
			final SlotDecorationComponent lv5_3_f1 = lv5_3;
			SlotDecorationComponent$IPreRenderEvent lv5_4 = (l2_0, l2_1, l2_2, l2_3, l2_4, l2_5, l2_6, l2_7) -> {
				if (this.isOnCooldown()){
					lv5_3_f1.setIconColor(0.6F,0.6F,0.6F);
					lv5_3_f1.setMaxValue(this.startCooldown);
					lv5_3_f1.setCurrentValue(this.cooldown);
					if (ClientConfig.isDisplayInPercentage()){
						float lv3_10 = ((this.cooldown) / (this.startCooldown)) * (100F);
						lv3_10 = Mth.clamp(lv3_10,0F,100F);
						String lv3_11 = String.format("%.0f",new Object[]{Float.valueOf(lv3_10)});
						String lv3_12 = lv3_11 + "%";
						lv5_3_f1.setDisplayText(lv3_12);
					}
					lv5_3_f1.setSlotColor(1F,0F,0F);
				}
			};
			l0.addPreRenderEvent(990,lv5_4);
			CooldownComponent lv5_5 = this;
			SlotDecorationComponent$IPostRenderEvent lv5_6 = (l2_0, l2_1, l2_2, l2_3, l2_4, l2_5, l2_6, l2_7) -> {
				if (((this.getCooldown()) == (1F)) && (!(this.playReadyAnim))){
					this.playReadyAnim = true;
					this.cooldownReadyAnim = 30F;
				}else{
					if ((this.playReadyAnim) && ((this.cooldownReadyAnim) > (0F))){
						this.cooldownReadyAnim = (float)(((double)(this.cooldownReadyAnim)) - ((1.75D) * ((double)(l2_1.getTimer().getGameTimeDeltaTicks()))));
						if ((this.cooldownReadyAnim) <= (0F)){
							this.playReadyAnim = false;
						}else{
							if ((this.cooldownReadyAnim) > (0F)){
								float lv4_9 = (this.cooldownReadyAnim) * (0.03F);
								lv4_9 = Math.max(0F,lv4_9);
								float lv4_10 = (1.8F) * (EasingFunctionHelper.easeOutSine(Float.valueOf(lv4_9)));
								float lv4_11 = (0.6F) * (EasingFunctionHelper.easeOutSine(Float.valueOf(lv4_9)));
								lv4_10 = Mth.clamp(lv4_10,1F,lv4_10);
								l2_2.pushPose();
								l2_2.translate(l2_5,l2_6,1F);
								l2_2.translate(12F,12F,0F);
								l2_2.scale(lv4_10,lv4_10,1F);
								l2_2.translate(-12F,-12F,0F);
								RenderSystem.enableBlend();
								l2_4.setSize(23F,23F);
								l2_4.setColor(0F,1F,0.5F,lv4_11);
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
			l0.addPostRenderEvent(990,lv5_6);
		};
		((java.util.Optional)(var1.getComponent(((AbilityComponentKey)(ModAbilityComponents.SLOT_DECORATION.get()))))).ifPresent((Consumer)(var3));
	}
	protected void doTick(LivingEntity var1) {
		LivingEntity var9 = null;
		if ((this.getAbility().hasComponent(((AbilityComponentKey)(ModAbilityComponents.DISABLE.get())))) && (((DisableComponent)(this.getAbility().getComponent(((AbilityComponentKey)(ModAbilityComponents.DISABLE.get()))).get())).isDisabled())){
			return;
		}else{
			if ((var1 instanceof Player) && ((PatreonHandler.isDevBuild()) && (FGCommand.NO_COOLDOWN))){
				this.stopCooldown(var1);
				return;
			}else{
				CooldownComponent var2 = this;
				Consumer var3 = (Consumer) ((Consumer<AltModeComponent>) ((l0) -> {
			l0.setDisabled(this.isOnCooldown());
		}));
				this.getAbility().getComponent(((AbilityComponentKey)(ModAbilityComponents.ALT_MODE.get()))).ifPresent(var3);
				if (this.isOnCooldown()){
					if ((this.cooldown) <= (0F)){
						this.stopCooldown(var1);
						return;
					}else{
						AttributeInstance var4 = var1.getAttribute(ModAttributes.TIME_PROGRESSION);
						double var5 = 1D;
						if ((var4) != (null)){
							var5 = var4.getValue();
						}
						this.cooldown = (float)(((double)(this.cooldown)) - (((double)(this.getTpsFactor())) * (var5)));
						int var6 = Math.max(1,(int)(this.getTpsFactor()));
						int var7 = 0;
						do{
							if ((var7) < (var6)){
								CooldownComponent var8 = this;
								var9 = var1;
								final LivingEntity var9_f1 = var9;
								Consumer<CooldownComponent$IDuringCooldownEvent> var10 = (l0) -> {
			l0.duringCooldown(var9_f1,this.getAbility());
		};
								((xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool)(this.tickCooldownEvents)).dispatch((Consumer)(var10));
								var7++;
								continue;
							}else{
								break;
							}
						} while (true);
					}
				}
				return;
			}
		}
	}
	public CooldownComponent addStartEvent(CooldownComponent$IStartCooldownEvent var1) {
		this.startCooldownEvents.addEvent(var1);
		return this;
	}
	public CooldownComponent addStartEvent(int var1, CooldownComponent$IStartCooldownEvent var2) {
		this.startCooldownEvents.addEvent(var1,var2);
		return this;
	}
	public CooldownComponent addTickEvent(CooldownComponent$IDuringCooldownEvent var1) {
		this.tickCooldownEvents.addEvent(var1);
		return this;
	}
	public CooldownComponent addTickEvent(int var1, CooldownComponent$IDuringCooldownEvent var2) {
		this.tickCooldownEvents.addEvent(var1,var2);
		return this;
	}
	public CooldownComponent addEndEvent(CooldownComponent$IEndCooldownEvent var1) {
		this.stopCooldownEvents.addEvent(var1);
		return this;
	}
	public CooldownComponent addEndEvent(int var1, CooldownComponent$IEndCooldownEvent var2) {
		this.stopCooldownEvents.addEvent(var1,var2);
		return this;
	}
	public void startCooldown(LivingEntity var1, float var2) {
		this.ensureIsRegistered();
		if (this.isOnCooldown()){
			return;
		}else{
			Optional var3 = this.getAbility().getComponent(((AbilityComponentKey)(ModAbilityComponents.POOL.get())));
			float var4 = Math.max(0F,var2);
			var4 = this.bonusManager.applyBonus(var4);
			this.startCooldown = var4;
			this.cooldown = var4;
			this.isOnCooldown = true;
			if (!(var1.level().isClientSide)){
				LivingEntity var5 = var1;
				final LivingEntity var5_f1 = var5;
				Consumer<PoolComponent> var6 = (l0) -> {
			if (!(l0.getPools().stream().anyMatch((Predicate)(((Predicate<AbilityPool>)((l2_0) -> {
				Supplier<Boolean> lv9_1 = () -> {
					return Boolean.valueOf(false);
				};
				return ((Boolean)(l2_0.getFlagValue((String)("ignoreCooldown"),(Supplier)(lv9_1)))).booleanValue();
			})))))){
				l0.startPoolInUse(var5_f1);
			}
		};
				((java.util.Optional)(var3)).ifPresent((Consumer)(var6));
			}
			CooldownComponent var5_1 = this;
			LivingEntity var6_1 = var1;
			final LivingEntity var6_1_f1 = var6_1;
			Consumer<CooldownComponent$IStartCooldownEvent> var7 = (l0) -> {
			l0.startCooldown(var6_1_f1,this.getAbility());
		};
			((xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool)(this.startCooldownEvents)).dispatch((Consumer)(var7));
			if (!(var1.level().isClientSide)){
				ModNetwork.sendToAllTrackingAndSelf(new SStartCooldownPacket((LivingEntity)(var1),(IAbility)(this.getAbility()),var2),var1);
			}
			return;
		}
	}
	public void stopCooldown(LivingEntity var1) {
		LivingEntity var5 = null;
		if (!(this.isOnCooldown())){
			return;
		}else{
			CooldownComponent var2 = this;
			LivingEntity var3 = var1;
			final LivingEntity var3_f1 = var3;
			Consumer<CooldownComponent$IEndCooldownEvent> var4 = (l0) -> {
			l0.endCooldown(var3_f1,this.getAbility());
		};
			((xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool)(this.stopCooldownEvents)).dispatch((Consumer)(var4));
			this.isOnCooldown = false;
			this.startCooldown = 0F;
			this.cooldown = 0F;
			this.getAbility().getComponent(((AbilityComponentKey)(ModAbilityComponents.SLOT_DECORATION.get()))).ifPresent((Consumer)(((Consumer<SlotDecorationComponent>)(SlotDecorationComponent::resetDecoration))));
			if (!(var1.level().isClientSide)){
				var5 = var1;
				final LivingEntity var5_f1 = var5;
				Consumer<PoolComponent> var6 = (l0) -> {
			l0.stopPoolInUse(var5_f1);
		};
				((java.util.Optional)(this.getAbility().getComponent(((AbilityComponentKey)(ModAbilityComponents.POOL.get()))))).ifPresent((Consumer)(var6));
			}
			if (!(var1.level().isClientSide)){
				ModNetwork.sendToAllTrackingAndSelf(new SStopCooldownPacket((LivingEntity)(var1),(IAbility)(this.getAbility())),var1);
			}
			return;
		}
	}
	public boolean isOnCooldown() {
		return this.isOnCooldown;
	}
	public float getStartCooldown() {
		return this.startCooldown;
	}
	public float getCooldown() {
		return this.cooldown;
	}
	public float getMinCooldown() {
		return this.minCooldown;
	}
	public float getMaxCooldown() {
		return this.maxCooldown;
	}
	public BonusManager getBonusManager() {
		return this.bonusManager;
	}
	@Nullable()
	public @Nullable() CompoundTag save(HolderLookup.Provider var1) {
		CompoundTag var2 = super.save(var1);
		var2.putFloat("cooldown",this.cooldown);
		var2.putFloat("startCooldown",this.startCooldown);
		return var2;
	}
	public void load(HolderLookup.Provider var1, CompoundTag var2) {
		super.load(var1,var2);
		this.cooldown = var2.getFloat("cooldown");
		this.startCooldown = var2.getFloat("cooldown");
		if ((this.cooldown) > (0F)){
			this.isOnCooldown = true;
		}
	}
}