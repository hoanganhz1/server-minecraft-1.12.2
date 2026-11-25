package xyz.pixelatedw.mineminenomi.abilities.kuku;

import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
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
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule$Builder;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class GastronomorphAbility extends Ability {
	// Fields
	private static final int CHARGE_TIME = 60;
	private static final int COOLDOWN = 300;
	private static final int RANGE_Y = 3;
	private static final int RANGE_XZ = 128;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<GastronomorphAbility>> INSTANCE;
	public static final BlockProtectionRule GRIEF_RULE;
	private final ChargeComponent chargeComponent;
	private final BlockTrackerComponent blockTrackerComponent;

	public GastronomorphAbility(AbilityCore<GastronomorphAbility> var1) {
		super((AbilityCore)(var1));
		this.chargeComponent = new ChargeComponent((IAbility)(this)).addStartEvent((ChargeComponent$IStartChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IStartChargingEvent)(this::startChargeEvent)))).addTickEvent((ChargeComponent$IDuringChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IDuringChargingEvent)(this::duringChargeEvent)))).addEndEvent((ChargeComponent$IEndChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent)(this::endChargeEvent))));
		this.blockTrackerComponent = new BlockTrackerComponent((IAbility)(this));
		AbilityComponent[] var2 = new AbilityComponent[]{this.chargeComponent,this.blockTrackerComponent};
		this.addComponents(var2);
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		this.chargeComponent.startCharging(var1,60F);
	}
	private void startChargeEvent(LivingEntity var1, IAbility var2) {
		if (var1.level().isClientSide){
			return;
		}else{
			this.blockTrackerComponent.initQueue(var1.level(),15);
			BlockPos.MutableBlockPos var4 = new BlockPos.MutableBlockPos();
			int var5 = -3;
			LOOP_2:
			do{
				if ((var5) < (3)){
					int var6 = (var5) * (var5);
					int var7 = -128;
					LOOP_1:
					do{
						if ((var7) < (128)){
							int var8 = (var7) * (var7);
							int var9 = -128;
							do{
								if ((var9) < (128)){
									int var10 = ((var8) + (var6)) + ((var9) * (var9));
									if ((((double)(var10)) >= (384D)) || (((0.05D) + (this.random.nextDouble())) < (((double)(var10)) / (384D)))){
										var9++;
										continue;
									}else{
										int var11 = (var1.getBlockX()) + (var7);
										int var12 = (var1.getBlockY()) + (var5);
										var4.set(var11,var12,(var1.getBlockZ()) + (var9));
										if (!(var1.level().getBlockState(var4.above()).isAir())){
											var9++;
											continue;
										}else{
											this.blockTrackerComponent.addBlockToQueue(var4.immutable(),((Block)(ModBlocks.SPONGE_CAKE.get())).defaultBlockState(),3,DefaultProtectionRules.CORE_FOLIAGE_ORE_LIQUID,(IAfterPlaceBlockEvent)(null)).setDistance((double)(var10));
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
			return;
		}
	}
	private void duringChargeEvent(LivingEntity var1, IAbility var2) {
		if (var1.level().isClientSide){
			return;
		}else{
			var1.addEffect(new MobEffectInstance((Holder)(ModEffects.MOVEMENT_BLOCKED),2,1,false,false));
			this.blockTrackerComponent.tickBlockQueue(var1.level());
			return;
		}
	}
	private void endChargeEvent(LivingEntity var1, IAbility var2) {
		this.cooldownComponent.startCooldown(var1,300F);
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Turns the surroundings into cake sponge blocks."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(300F),ChargeComponent.getTooltip(60F),RangeComponent.getTooltip(128F,RangeComponent$RangeType.LINE)};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(GastronomorphAbility::new)))).addDescriptionLine(lv1_3).addAdvancedDescriptionLine(lv1_4).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("gastronomorph","Gastronomorph",var0));
		GRIEF_RULE = new BlockProtectionRule$Builder(new BlockProtectionRule[]{DefaultProtectionRules.CORE_FOLIAGE}).build();
	}
}