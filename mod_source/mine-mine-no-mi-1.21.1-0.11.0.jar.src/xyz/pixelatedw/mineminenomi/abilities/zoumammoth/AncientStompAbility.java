package xyz.pixelatedw.mineminenomi.abilities.zoumammoth;

import com.google.common.collect.ImmutableList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent$RangeType;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent$IRepeaterStopEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent$IRepeaterTriggerEvent;
import xyz.pixelatedw.mineminenomi.api.damagesources.AbilityDamageSource;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityTooltipsHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModTags$Blocks;

public class AncientStompAbility extends Ability {
	// Fields
	private static final int COOLDOWN = 200;
	private static final int RADIUS = 7;
	private static final int DAMAGE = 10;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<AncientStompAbility>> INSTANCE;
	private final ContinuousComponent continuousComponent;
	private final RepeaterComponent repeaterComponent;
	private final RangeComponent rangeComponent;
	private final DealDamageComponent dealDamageComponent;
	private Iterator<BlockPos> targetedBlocks;

	public AncientStompAbility(AbilityCore<AncientStompAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this)).addStartEvent(100,(ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::startContinuityEvent)))).addEndEvent(100,(ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
		this.repeaterComponent = new RepeaterComponent((IAbility)(this)).addTriggerEvent(100,(RepeaterComponent$IRepeaterTriggerEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent$IRepeaterTriggerEvent)(this::triggerRepeaterEvent)))).addStopEvent(100,(RepeaterComponent$IRepeaterStopEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent$IRepeaterStopEvent)(this::stopRepeaterEvent))));
		this.rangeComponent = new RangeComponent((IAbility)(this));
		this.dealDamageComponent = new DealDamageComponent((IAbility)(this));
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent,this.repeaterComponent,this.rangeComponent,this.dealDamageComponent};
		this.addComponents(var2);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(ZouMammothHelper::requiresGuardPoint))));
		this.addContinueUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(ZouMammothHelper::requiresGuardPoint))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.startContinuity(var1);
	}
	private void stopRepeaterEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.stopContinuity(var1);
	}
	private void endContinuityEvent(LivingEntity var1, IAbility var2) {
		this.cooldownComponent.startCooldown(var1,200F);
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv3_2 = new Pair[]{ImmutablePair.of((Object)("Stomps the ground multiple times creating shockwaves that deal damage to all nearby enemies."),(Object)(null))};
			Component[] lv3_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv3_2);
			AbilityDescriptionLine$IDescriptionLine[] lv3_4 = new AbilityDescriptionLine$IDescriptionLine[2];
			lv3_4[0] = AbilityDescriptionLine.NEW_LINE;
			Supplier[] lv3_5 = new Supplier[]{ModMorphs.MAMMOTH_GUARD};
			lv3_4[1] = AbilityTooltipsHelper.getRequiredMorphTooltip(lv3_5);
			AbilityDescriptionLine$IDescriptionLine[] lv3_6 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(200F),DealDamageComponent.getTooltip(10F),RangeComponent.getTooltip(7F,RangeComponent$RangeType.AOE)};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(AncientStompAbility::new)))).addDescriptionLine(lv3_3).addDescriptionLine(lv3_4).addAdvancedDescriptionLine(lv3_6).setSourceHakiNature(SourceHakiNature.SPECIAL).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("ancient_stomp","Ancient Stomp",var0));
	}

	private void startContinuityEvent(LivingEntity var1, IAbility var2) {
		LivingEntity var3 = var1;
		final LivingEntity var3_f1 = var3;
		Predicate<BlockPos> var4 = (l0) -> {
			return (var3_f1.level().getBlockState(l0.above()).isAir()) && (((double)(l0.getY())) > ((var3_f1.getY()) - (3D)));
		};
		Predicate<BlockPos> var5 = var4;
		Vec3 var6 = var1.position().add(var1.getLookAngle().multiply(7D,1D,7D));
		List var7 = WyHelper.getNearbyBlocks(BlockPos.containing(var6.x(),var1.getY(),var6.z()),(LevelAccessor)(var1.level()),7,var5,(List)(ImmutableList.of(Blocks.AIR)));
		this.targetedBlocks = (Iterator) (WyHelper.shuffle((Collection)(var7)).stream().limit(10L).iterator());
		this.repeaterComponent.start(var1,12,8);
	}
	private void triggerRepeaterEvent(LivingEntity var1, IAbility var2) {
		if (((this.targetedBlocks) != (null)) && (this.targetedBlocks.hasNext())){
			BlockPos var3 = ((BlockPos)(this.targetedBlocks.next()));
			List var4 = this.rangeComponent.getTargetsInArea(var1,7F);
			AbilityDamageSource var5 = this.dealDamageComponent.getDamageSource(var1);
			Iterator var6 = var4.iterator();
			do{
				if (var6.hasNext()){
					LivingEntity var7 = ((LivingEntity)(var6.next()));
					if (this.dealDamageComponent.hurtTarget(var1,var7,10F,var5)){
						var7.addEffect(new MobEffectInstance((Holder)(ModEffects.DIZZY),20,0));
						AbilityHelper.setDeltaMovement((Entity)(var7),var7.getDeltaMovement().add(0D,0.25D,0D));
						continue;
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
			LivingEntity var7_1 = var1;
			final LivingEntity var7_1_f1 = var7_1;
			Predicate<BlockPos> var8 = (l0) -> {
			return var7_1_f1.level().getBlockState(l0).is(ModTags$Blocks.BLOCK_PROT_FOLIAGE);
		};
			List var9 = WyHelper.getNearbyBlocks(var1.blockPosition(),(LevelAccessor)(var1.level()),7,var8,(List)(ImmutableList.of(Blocks.AIR)));
			if (!(var1.level().isClientSide)){
				Iterator var10 = var9.iterator();
				LOOP_1:
				do{
					if (var10.hasNext()){
						BlockPos var11 = ((BlockPos)(var10.next()));
						BlockState var12 = var1.level().getBlockState(var11);
						int var13 = 0;
						do{
							if ((var13) < (150)){
								double var14 = WyHelper.randomDouble();
								double var15 = WyHelper.randomDouble();
								double var16 = WyHelper.randomDouble();
								((ServerLevel)(var1.level())).sendParticles((ParticleOptions)(new BlockParticleOption(ParticleTypes.BLOCK,var12)),((double)(var11.getX())) + (var14),((double)(var11.getY())) + (var15),((double)(var11.getZ())) + (var16),1,0D,0D,0D,0D);
								var13++;
								continue;
							}else{
								continue LOOP_1;
							}
						} while (true);
					}else{
						break;
					}
				} while (true);
				BlockState var11_1 = var1.level().getBlockState(var3);
				if (!(var11_1.isAir())){
					int var12_1 = 0;
					do{
						if ((var12_1) < (250)){
							double var13_1 = WyHelper.randomDouble();
							double var14 = WyHelper.randomDouble();
							((ServerLevel)(var1.level())).sendParticles((ParticleOptions)(new BlockParticleOption(ParticleTypes.BLOCK,var11_1)),((double)((var3.getX()) + (WyHelper.randomWithRange(-3,3)))) + (var13_1),(double)((var3.getY()) + (1)),((double)((var3.getZ()) + (WyHelper.randomWithRange(-3,3)))) + (var14),1,0D,0D,0D,0D);
							var12_1++;
							continue;
						}else{
							break;
						}
					} while (true);
				}else{
					return;
				}
			}
			return;
		}else{
			this.continuousComponent.stopContinuity(var1);
			return;
		}
	}}