package xyz.pixelatedw.mineminenomi.abilities.gasu;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusManager;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusOperation;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent$RangeType;
import xyz.pixelatedw.mineminenomi.api.damagesources.AbilityDamageSource;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class KarakuniAbility extends Ability {
	// Fields
	private static final UUID SHINOKUNI_RANGE_BONUS = UUID.fromString("05f3fc04-68e5-4f7b-a513-7237ad6fd643");
	private static final int COOLDOWN = 600;
	private static final int HOLD_TIME = 100;
	private static final int RANGE = 9;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<KarakuniAbility>> INSTANCE;
	private final ContinuousComponent continuousComponent;
	private final RangeComponent rangeComponent;
	private final DealDamageComponent dealDamageComponent;
	private Interval suffocateInterval;

	public KarakuniAbility(AbilityCore<KarakuniAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this)).addStartEvent(100,(ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::startContinuityEvent)))).addTickEvent((ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::duringContinuityEvent)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::stopContinuityEvent))));
		this.rangeComponent = new RangeComponent((IAbility)(this));
		this.dealDamageComponent = new DealDamageComponent((IAbility)(this));
		this.suffocateInterval = new Interval(2);
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent,this.rangeComponent,this.dealDamageComponent};
		this.addComponents(var2);
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.triggerContinuity(var1,100F);
	}
	private void startContinuityEvent(LivingEntity var1, IAbility var2) {
		this.suffocateInterval.restartIntervalToZero();
	}
	private void duringContinuityEvent(LivingEntity var1, IAbility var2) {
		if (this.suffocateInterval.canTick()){
			this.rangeComponent.getBonusManager().removeBonus(SHINOKUNI_RANGE_BONUS);
			if (((MorphInfo)(ModMorphs.SHINOKUNI.get())).isActive(var1)){
				this.rangeComponent.getBonusManager().addBonus(SHINOKUNI_RANGE_BONUS,"Shinokuni Range Bonus",BonusOperation.ADD,3F);
			}
			List var3 = this.rangeComponent.getTargetsInArea(var1,9F);
			List var4 = WyHelper.getNearbyBlocks((Entity)(var1),9);
			Iterator var5 = var3.iterator();
			do{
				if (var5.hasNext()){
					LivingEntity var6 = ((LivingEntity)(var5.next()));
					if (var1.getType().is(EntityTypeTags.UNDEAD)){
						continue;
					}else{
						var6.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,500,2,false,false));
						var6.addEffect(new MobEffectInstance(MobEffects.WEAKNESS,500,1,false,false));
						var6.setAirSupply((var6.getAirSupply()) - (50));
						if ((var6.getAirSupply()) <= (0)){
							if ((var6.getHealth()) > (8F)){
								AbilityDamageSource var7 = this.dealDamageComponent.getDamageSource(var1);
								IDamageSourceHandler var8 = IDamageSourceHandler.getHandler(var7);
								var8.bypassLogia();
								var8.addAbilityPiercing(1F,this.getCore());
								var8.setUnavoidable();
								this.dealDamageComponent.hurtTarget(var1,var6,8F,var7);
							}else{
								var6.addEffect(new MobEffectInstance((Holder)(ModEffects.UNCONSCIOUS),200,1));
							}
						}
						if (var6.isOnFire()){
							var6.clearFire();
							continue;
						}else{
							continue;
						}
					}
				}else{
					break;
				}
			} while (true);
			if (var1.isOnFire()){
				var1.clearFire();
			}
			BlockPos.MutableBlockPos var6_1 = new BlockPos.MutableBlockPos();
			Iterator var7_1 = var4.iterator();
			do{
				if (var7_1.hasNext()){
					BlockPos var8_1 = ((BlockPos)(var7_1.next()));
					var6_1.set(var8_1.getX(),(var8_1.getY()) + (1),var8_1.getZ());
					if (((var1.level().getBlockState(var8_1).getBlock()) == (Blocks.FIRE)) && ((var1.level().getBlockState((BlockPos)(var6_1)).getBlock()) == (Blocks.AIR))){
						var1.level().removeBlock(var8_1,false);
						continue;
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
		}
	}
	private void stopContinuityEvent(LivingEntity var1, IAbility var2) {
		this.cooldownComponent.startCooldown(var1,600F);
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Removes the oxygen around the user, suffocating and weakening everyone in the vicinity"),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(600F),ContinuousComponent.getTooltip(100F),RangeComponent.getTooltip(9F,RangeComponent$RangeType.AOE)};
			SourceType[] lv1_5 = new SourceType[]{SourceType.INTERNAL};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(KarakuniAbility::new)))).addDescriptionLine(lv1_3).addAdvancedDescriptionLine(lv1_4).setSourceType(lv1_5).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("karakuni","Karakuni",var0));
	}
}