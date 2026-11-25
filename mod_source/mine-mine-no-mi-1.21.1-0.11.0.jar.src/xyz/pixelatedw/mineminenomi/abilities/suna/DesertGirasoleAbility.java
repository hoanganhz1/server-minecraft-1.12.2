package xyz.pixelatedw.mineminenomi.abilities.suna;

import java.util.UUID;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine$IDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BlockTrackerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusManager;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusOperation;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IDuringChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IStartChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent$RangeType;
import xyz.pixelatedw.mineminenomi.api.blockgen.BlockQueue;
import xyz.pixelatedw.mineminenomi.api.blockgen.BlockQueue$BlockQueueData;
import xyz.pixelatedw.mineminenomi.api.blockgen.IAfterPlaceBlockEvent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class DesertGirasoleAbility extends Ability {
	// Fields
	private static final float COOLDOWN_BONUS = 0.8F;
	private static final float RANGE_BONUS = 2F;
	private static final int COOLDOWN = 600;
	private static final int CHARGE_TIME = 100;
	private static final int SAND_RANGE_XZ = 200;
	private static final int SAND_RANGE_Y = 4;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<DesertGirasoleAbility>> INSTANCE;
	private final ChargeComponent chargeComponent;
	private final RangeComponent rangeComponent;
	private final BlockTrackerComponent blockTrackerComponent;

	public DesertGirasoleAbility(AbilityCore<DesertGirasoleAbility> var1) {
		super((AbilityCore)(var1));
		this.chargeComponent = new ChargeComponent((IAbility)(this)).addStartEvent((ChargeComponent$IStartChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IStartChargingEvent)(this::startChargeEvent)))).addTickEvent((ChargeComponent$IDuringChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IDuringChargingEvent)(this::duringChargeEvent)))).addEndEvent((ChargeComponent$IEndChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent)(this::endChargingEvent))));
		this.rangeComponent = new RangeComponent((IAbility)(this));
		this.blockTrackerComponent = new BlockTrackerComponent((IAbility)(this));
		AbilityComponent[] var2 = new AbilityComponent[]{this.rangeComponent,this.chargeComponent,this.blockTrackerComponent};
		this.addComponents(var2);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::requiresDryUser))));
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::requiresOnGround))));
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(SunaHelper::requiresInactiveGroundDeath))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		this.chargeComponent.startCharging(var1,100F);
	}
	private void startChargeEvent(LivingEntity var1, IAbility var2) {
		this.rangeComponent.getBonusManager().removeBonus(SunaHelper.DESERT_RANGE_BONUS);
		if (SunaHelper.isFruitBoosted(var1)){
			this.rangeComponent.getBonusManager().addBonus(SunaHelper.DESERT_RANGE_BONUS,"Desert Range Bonus",BonusOperation.MUL,2F);
		}
		this.blockTrackerComponent.initQueue(var1.level(),1000);
		BlockPos.MutableBlockPos var4 = new BlockPos.MutableBlockPos();
		int var5 = -4;
		LOOP_2:
		do{
			if ((var5) < (4)){
				int var6 = (var5) * (var5);
				int var7 = -200;
				LOOP_1:
				do{
					if ((var7) < (200)){
						int var8 = (var7) * (var7);
						int var9 = -200;
						do{
							if ((var9) < (200)){
								int var10 = ((var8) + (var6)) + ((var9) * (var9));
								if ((((double)(var10)) >= (800D)) || ((this.random.nextDouble()) < (((double)(var10)) / (800D)))){
									var9++;
									continue;
								}else{
									int var11 = (var1.getBlockX()) + (var7);
									int var12 = (var1.getBlockY()) + (var5);
									var4.set(var11,var12,(var1.getBlockZ()) + (var9));
									if (((java.lang.Object)(var1.level().getBlockState((BlockPos)(var4)).getBlock())).equals((Object)(Blocks.SAND))){
										this.blockTrackerComponent.addBlockToQueue(var4.immutable(),((Block)(ModBlocks.SUNA_SAND.get())).defaultBlockState(),3,DefaultProtectionRules.CORE_FOLIAGE_ORE_LIQUID,(IAfterPlaceBlockEvent)(null)).setDistance((double)(var10));
										var9++;
										continue;
									}else{
										var9++;
										continue;
									}
								}
							}else{
								break;
							}
						} while (true);
						var7++;
						continue LOOP_1;
					}else{
						break;
					}
				} while (true);
				var5++;
				continue LOOP_2;
			}else{
				break;
			}
		} while (true);
		this.blockTrackerComponent.getQueue().start();
		if ((this.blockTrackerComponent.getQueue().getQueueSize()) > (0)){
			WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.DESERT_GIRASOLE_1.get())),(Entity)(var1),var1.getX(),var1.getY(),var1.getZ());
		}
	}
	private void duringChargeEvent(LivingEntity var1, IAbility var2) {
		if (var1.level().isClientSide){
			return;
		}else{
			var1.addEffect(new MobEffectInstance((Holder)(ModEffects.MOVEMENT_BLOCKED),5,1,false,false));
			this.blockTrackerComponent.tickBlockQueue(var1.level());
			return;
		}
	}
	private void endChargingEvent(LivingEntity var1, IAbility var2) {
		WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.DESERT_GIRASOLE_2.get())),(Entity)(var1),var1.getX(),var1.getY(),var1.getZ());
		this.cooldownComponent.getBonusManager().removeBonus(SunaHelper.DESERT_COOLDOWN_BONUS);
		if (SunaHelper.isFruitBoosted(var1)){
			this.cooldownComponent.getBonusManager().addBonus(SunaHelper.DESERT_COOLDOWN_BONUS,"Desert Cooldown Bonus",BonusOperation.MUL,0.8F);
		}
		this.cooldownComponent.startCooldown(var1,600F);
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[3];
			((java.lang.Object[]) (lv1_2))[0] = ImmutablePair.of((Object)("A giant pit of quicksand will be formed."),(Object)(null));
			((java.lang.Object[]) (lv1_2))[1] = ImmutablePair.of((Object)("Can only be used on sand."),(Object)(null));
			Object[] lv1_3 = new Object[2];
			int lv1_4 = Math.round(19.999998F);
			String lv1_5 = "§a" + lv1_4 + "%§r";
			lv1_3[0] = lv1_5;
			int lv1_6 = Math.round((Math.abs(-1F)) * (100F));
			String lv1_7 = "§a" + lv1_6 + "%§r";
			lv1_3[1] = lv1_7;
			((java.lang.Object[]) (lv1_2))[2] = ImmutablePair.of((Object)("While in a desert the cooldown of this ability is reduced by %s and range is increased by %s."),lv1_3);
			Component[] lv1_8 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_9 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(600F),ChargeComponent.getTooltip(100F),RangeComponent.getTooltip(200F,RangeComponent$RangeType.AOE)};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(DesertGirasoleAbility::new)))).addDescriptionLine(lv1_8).addAdvancedDescriptionLine(lv1_9).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("desert_girasole","Desert Girasole",var0));
	}
}