package xyz.pixelatedw.mineminenomi.api.abilities.components;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine$IDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityStat;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityStat$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.protection.ProtectedArea;
import xyz.pixelatedw.mineminenomi.api.ui.TexturedRectUI;
import xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool;
import xyz.pixelatedw.mineminenomi.config.ClientConfig;
import xyz.pixelatedw.mineminenomi.data.world.ProtectedAreasData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SStartChargingPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SStopChargingPacket;

public class ChargeComponent extends AbilityComponent<IAbility> {
	// Fields
	private static final UUID MAX_CHARGE_BONUS_MANAGER_UUID = UUID.fromString("75b893d9-9d02-457e-9c35-02e468586fcc");
	private boolean isCharging;
	private float maxChargeTime;
	private float chargeTime;
	private boolean isCancelable;
	private Predicate<ChargeComponent> isCancelableTest;
	private final PriorityEventPool<ChargeComponent$IStartChargingEvent> startChargeEvents;
	private final PriorityEventPool<ChargeComponent$IDuringChargingEvent> tickChargeEvents;
	private final PriorityEventPool<ChargeComponent$IEndChargingEvent> stopChargeEvents;
	private final BonusManager maxChargeBonusManager;

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
			return new AbilityStat$Builder((Component)(ModI18nAbilities.DESCRIPTION_STAT_NAME_CHARGE),Math.round((var2_f1) / (20F)),Math.round((var3_f2) / (20F))).withUnit(ModI18nAbilities.DESCRIPTION_STAT_UNIT_SECONDS).build().getStatDescription();
		};
		return var4;
	}
	public ChargeComponent(IAbility var1) {
		this((IAbility)(var1),false);
		this.addBonusManager(this.maxChargeBonusManager);
	}
	public ChargeComponent(IAbility var1, boolean var2) {
		super(((AbilityComponentKey)(ModAbilityComponents.CHARGE.get())),var1);
		this.startChargeEvents = (PriorityEventPool) (new PriorityEventPool());
		this.tickChargeEvents = (PriorityEventPool) (new PriorityEventPool());
		this.stopChargeEvents = (PriorityEventPool) (new PriorityEventPool());
		this.maxChargeBonusManager = new BonusManager(MAX_CHARGE_BONUS_MANAGER_UUID);
		this.isCancelable = var2;
		Predicate<ChargeComponent> var3 = (l0) -> {
			return true;
		};
		this.isCancelableTest = var3;
	}
	public ChargeComponent(IAbility var1, Predicate<ChargeComponent> var2) {
		super(((AbilityComponentKey)(ModAbilityComponents.CHARGE.get())),var1);
		this.startChargeEvents = (PriorityEventPool) (new PriorityEventPool());
		this.tickChargeEvents = (PriorityEventPool) (new PriorityEventPool());
		this.stopChargeEvents = (PriorityEventPool) (new PriorityEventPool());
		this.maxChargeBonusManager = new BonusManager(MAX_CHARGE_BONUS_MANAGER_UUID);
		this.isCancelable = true;
		this.isCancelableTest = var2;
	}
	public void postInit(IAbility var1) {
		ChargeComponent var2 = this;
		Consumer<SlotDecorationComponent> var3 = (l0) -> {
			ChargeComponent lv4_2 = this;
			SlotDecorationComponent lv4_3 = l0;
			final SlotDecorationComponent lv4_3_f1 = lv4_3;
			SlotDecorationComponent$IPreRenderEvent lv4_4 = (l2_0, l2_1, l2_2, l2_3, l2_4, l2_5, l2_6, l2_7) -> {
				if (this.isCharging()){
					lv4_3_f1.setMaxValue(this.maxChargeTime);
					lv4_3_f1.setCurrentValue(this.chargeTime);
					if (ClientConfig.isDisplayInPercentage()){
						float lv3_10 = ((this.chargeTime) / (this.maxChargeTime)) * (100F);
						lv3_10 = Mth.clamp(lv3_10,0F,100F);
						String lv3_11 = String.format("%.0f",new Object[]{Float.valueOf(lv3_10)});
						String lv3_12 = lv3_11 + "%";
						lv4_3_f1.setDisplayText(lv3_12);
					}
					lv4_3_f1.setSlotColor(1F,1F,0F);
				}
			};
			l0.addPreRenderEvent(981,lv4_4);
		};
		((java.util.Optional)(var1.getComponent(((AbilityComponentKey)(ModAbilityComponents.SLOT_DECORATION.get()))))).ifPresent((Consumer)(var3));
	}
	protected void doTick(LivingEntity var1) {
		LivingEntity var9 = null;
		if ((this.getAbility().hasComponent(((AbilityComponentKey)(ModAbilityComponents.DISABLE.get())))) && (((DisableComponent)(this.getAbility().getComponent(((AbilityComponentKey)(ModAbilityComponents.DISABLE.get()))).get())).isDisabled())){
			return;
		}else{
			ChargeComponent var2 = this;
			Consumer var3 = (Consumer) ((Consumer<AltModeComponent>) ((l0) -> {
			l0.setDisabled(this.isCharging());
		}));
			this.getAbility().getComponent(((AbilityComponentKey)(ModAbilityComponents.ALT_MODE.get()))).ifPresent(var3);
			if (this.isCharging()){
				if (var1.hasEffect((Holder)(ModEffects.IN_EVENT))){
					this.forceStopCharging(var1);
					return;
				}else{
					if (((var1.level()) != (null)) && (!(var1.level().isClientSide))){
						Optional var4 = ProtectedAreasData.get(((ServerLevel)(var1.level()))).getProtectedArea(var1.blockPosition().getX(),var1.blockPosition().getY(),var1.blockPosition().getZ());
						if ((var4.isPresent()) && (!(((ProtectedArea)(var4.get())).canUseAbility(this.getAbility().getCore())))){
							this.forceStopCharging(var1);
							return;
						}else{
							if ((this.chargeTime) >= (this.maxChargeTime)){
								this.stopCharging(var1);
								return;
							}
						}
					}
					AttributeInstance var4_1 = var1.getAttribute(ModAttributes.TIME_PROGRESSION);
					double var5 = 1D;
					if ((var4_1) != (null)){
						var5 = var4_1.getValue();
					}
					this.chargeTime = (float)(((double)(this.chargeTime)) + (((double)(this.getTpsFactor())) * (var5)));
					int var6 = Math.max(1,(int)(this.getTpsFactor()));
					int var7 = 0;
					do{
						if ((var7) < (var6)){
							ChargeComponent var8 = this;
							var9 = var1;
							final LivingEntity var9_f1 = var9;
							Consumer<ChargeComponent$IDuringChargingEvent> var10 = (l0) -> {
			l0.duringCharging(var9_f1,this.getAbility());
		};
							((xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool)(this.tickChargeEvents)).dispatch((Consumer)(var10));
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
	public ChargeComponent addStartEvent(ChargeComponent$IStartChargingEvent var1) {
		this.startChargeEvents.addEvent(var1);
		return this;
	}
	public ChargeComponent addStartEvent(int var1, ChargeComponent$IStartChargingEvent var2) {
		this.startChargeEvents.addEvent(var1,var2);
		return this;
	}
	public ChargeComponent addTickEvent(ChargeComponent$IDuringChargingEvent var1) {
		this.tickChargeEvents.addEvent(var1);
		return this;
	}
	public ChargeComponent addTickEvent(int var1, ChargeComponent$IDuringChargingEvent var2) {
		this.tickChargeEvents.addEvent(var1,var2);
		return this;
	}
	public ChargeComponent addEndEvent(ChargeComponent$IEndChargingEvent var1) {
		this.stopChargeEvents.addEvent(var1);
		return this;
	}
	public ChargeComponent addEndEvent(int var1, ChargeComponent$IEndChargingEvent var2) {
		this.stopChargeEvents.addEvent(var1,var2);
		return this;
	}
	public void startCharging(LivingEntity var1, float var2) {
		LivingEntity var7 = null;
		LivingEntity var8_1 = null;
		this.ensureIsRegistered();
		if (this.isCharging()){
			if ((this.isCancelable()) && (this.isCancelableTest.test(this))){
				this.stopCharging(var1);
			}
			return;
		}else{
			Optional var3 = this.getAbility().getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get())));
			if ((var3.isPresent()) && (((CooldownComponent)(var3.get())).isOnCooldown())){
				return;
			}else{
				Optional var4 = this.getAbility().getComponent(((AbilityComponentKey)(ModAbilityComponents.DISABLE.get())));
				if ((var4.isPresent()) && (((DisableComponent)(var4.get())).isDisabled())){
					return;
				}else{
					Optional var5 = this.getAbility().getComponent(((AbilityComponentKey)(ModAbilityComponents.POOL.get())));
					if ((var5.isPresent()) && (!(((PoolComponent)(var5.get())).canUsePool(var1)))){
						return;
					}else{
						float var6 = Math.max(1F,var2);
						var6 = this.maxChargeBonusManager.applyBonus(var6);
						this.maxChargeTime = var6;
						this.chargeTime = 0F;
						this.isCharging = true;
						if (((var1.level()) != (null)) && (!(var1.level().isClientSide))){
							var7 = var1;
							final LivingEntity var7_f1 = var7;
							Consumer<PoolComponent> var8 = (l0) -> {
			l0.startPoolInUse(var7_f1);
		};
							((java.util.Optional)(var5)).ifPresent((Consumer)(var8));
						}
						ChargeComponent var7_1 = this;
						var8_1 = var1;
						final LivingEntity var8_1_f2 = var8_1;
						Consumer<ChargeComponent$IStartChargingEvent> var9 = (l0) -> {
			l0.startCharging(var8_1_f2,this.getAbility());
		};
						((xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool)(this.startChargeEvents)).dispatch((Consumer)(var9));
						if (((var1.level()) != (null)) && (!(var1.level().isClientSide))){
							ModNetwork.sendToAllTrackingAndSelf(new SStartChargingPacket((LivingEntity)(var1),(IAbility)(this.getAbility()),var2),var1);
						}
						return;
					}
				}
			}
		}
	}
	public void stopCharging(LivingEntity var1) {
		ChargeComponent var2 = this;
		LivingEntity var3 = var1;
		final LivingEntity var3_f1 = var3;
		Consumer<ChargeComponent$IEndChargingEvent> var4 = (l0) -> {
			l0.endCharging(var3_f1,this.getAbility());
		};
		((xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool)(this.stopChargeEvents)).dispatch((Consumer)(var4));
		this.forceStopCharging(var1);
	}
	public void forceStopCharging(LivingEntity var1) {
		this.isCharging = false;
		this.chargeTime = 0F;
		this.maxChargeTime = 0F;
		this.getAbility().getComponent(((AbilityComponentKey)(ModAbilityComponents.SLOT_DECORATION.get()))).ifPresent((Consumer)(((Consumer<SlotDecorationComponent>)(SlotDecorationComponent::resetDecoration))));
		if (((var1.level()) != (null)) && (!(var1.level().isClientSide))){
			LivingEntity var2 = var1;
			final LivingEntity var2_f1 = var2;
			Consumer<PoolComponent> var3 = (l0) -> {
			l0.stopPoolInUse(var2_f1);
		};
			((java.util.Optional)(this.getAbility().getComponent(((AbilityComponentKey)(ModAbilityComponents.POOL.get()))))).ifPresent((Consumer)(var3));
			ModNetwork.sendToAllTrackingAndSelf(new SStopChargingPacket((LivingEntity)(var1),(IAbility)(this.getAbility())),var1);
		}
	}
	public boolean isCharging() {
		return this.isCharging;
	}
	public float getMaxChargeTime() {
		return this.maxChargeTime;
	}
	public float getChargeTime() {
		return this.chargeTime;
	}
	public float getChargePercentage() {
		float var1 = (this.chargeTime) / (this.maxChargeTime);
		if (Float.isNaN(var1)){
			return 0F;
		}else{
			return var1;
		}
	}
	public boolean isCancelable() {
		return this.isCancelable;
	}
	public BonusManager getMaxChargeBonusManager() {
		return this.maxChargeBonusManager;
	}
	@Nullable()
	public @Nullable() CompoundTag save(HolderLookup.Provider var1) {
		CompoundTag var2 = super.save(var1);
		var2.putFloat("chargeTime",this.chargeTime);
		var2.putFloat("maxChargeTime",this.maxChargeTime);
		return var2;
	}
	public void load(HolderLookup.Provider var1, CompoundTag var2) {
		super.load(var1,var2);
		this.chargeTime = var2.getFloat("chargeTime");
		this.maxChargeTime = var2.getFloat("maxChargeTime");
		if ((this.chargeTime) > (0F)){
			this.isCharging = true;
		}
	}
}