package xyz.pixelatedw.mineminenomi.abilities.doru;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.BlockTrackerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IDuringChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.blockgen.BlockGenerators;
import xyz.pixelatedw.mineminenomi.api.blockgen.BlockQueue;
import xyz.pixelatedw.mineminenomi.api.blockgen.IBlockGenerator;
import xyz.pixelatedw.mineminenomi.api.blockgen.IBlockPlacer;
import xyz.pixelatedw.mineminenomi.api.blockgen.SimpleBlockPlacer;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class TokudaiCandleAbility extends Ability {
	// Fields
	private static final float COOLDOWN = 6000F;
	private static final float HOLD_TIME = 1200F;
	private static final int FIRST_LAYER_SIZE = 15;
	private static final int SECOND_LAYER_SIZE = 10;
	private static final int THIRD_LAYER_XZ_SIZE = 5;
	private static final int THIRD_LAYER_Y_SIZE = 10;
	private static final int FOURTH_LAYER_XZ_SIZE = 13;
	private static final int FOURTH_LAYER_Y_SIZE = 10;
	private static final float RANGE = 100F;
	public static final IBlockGenerator CANDLE_GENERATOR;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<TokudaiCandleAbility>> INSTANCE;
	private final ContinuousComponent continuousComponent;
	private final ChargeComponent chargeComponent;
	private final RangeComponent rangeComponent;
	private final BlockTrackerComponent blockTrackerComponent;
	private BlockPos centerPos;
	private Vec3 centerPosVec;
	private int blocksPerTick;
	private SimpleBlockPlacer placer;

	public TokudaiCandleAbility(AbilityCore<TokudaiCandleAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this)).addTickEvent(100,(ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::onTickContinuityEvent)))).addEndEvent(100,(ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::onEndContinuityEvent))));
		this.chargeComponent = new ChargeComponent((IAbility)(this)).addTickEvent(100,(ChargeComponent$IDuringChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IDuringChargingEvent)(this::onTickChargeEvent)))).addEndEvent(100,(ChargeComponent$IEndChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent)(this::onEndChargeEvent))));
		this.rangeComponent = new RangeComponent((IAbility)(this));
		this.blockTrackerComponent = new BlockTrackerComponent((IAbility)(this));
		this.blocksPerTick = 200;
		AbilityComponent[] var2 = new AbilityComponent[]{this.chargeComponent,this.continuousComponent,this.rangeComponent,this.blockTrackerComponent};
		this.addComponents(var2);
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::onUseEvent))));
	}
	private void onUseEvent(LivingEntity var1, IAbility var2) {
		if (this.continuousComponent.isContinuous()){
			this.continuousComponent.stopContinuity(var1);
			return;
		}else{
			if (this.chargeComponent.isCharging()){
				return;
			}else{
				if ((this.centerPos) == (null)){
					Vec3 var3 = var1.position().add(0D,(double)(var1.getEyeHeight()),0D);
					Vec3 var4 = var3.add(var1.getLookAngle().scale(128D));
					this.setCenterPos(var1.level().clip(new ClipContext(var3,var4,ClipContext.Block.COLLIDER,ClipContext.Fluid.NONE,(Entity)(var1))).getBlockPos());
				}
				Iterator var3_1 = this.rangeComponent.getTargetsInArea(var1,100F,(TargetPredicate)(null)).iterator();
				do{
					if (var3_1.hasNext()){
						LivingEntity var4_1 = ((LivingEntity)(var3_1.next()));
						Vec3 var5 = var4_1.position().subtract(this.centerPosVec).normalize().multiply(10D,1D,10D);
						AbilityHelper.setDeltaMovement((Entity)(var4_1),var5.x,var4_1.getDeltaMovement().y,var5.z);
						continue;
					}else{
						break;
					}
				} while (true);
				this.blockTrackerComponent.initQueue(var1.level(),this.blocksPerTick);
				BlockState var4_2 = ((Block)(ModBlocks.WAX.get())).defaultBlockState();
				this.placer = new SimpleBlockPlacer().setBlock(var4_2).setSize(15).setRule(DefaultProtectionRules.AIR_FOLIAGE).setBlockQueue(this.blockTrackerComponent.getQueue());
				this.placer.generate(var1.level(),this.centerPos,BlockGenerators.CUBE);
				this.placer.setSize(10);
				this.placer.generate(var1.level(),this.centerPos.above(15),BlockGenerators.CUBE);
				this.placer.setSize(5,10);
				this.placer.generate(var1.level(),this.centerPos.above(25),BlockGenerators.CUBE);
				this.placer.setSize(13,10);
				this.placer.generate(var1.level(),this.centerPos.above(41),BlockGenerators.SPHERE);
				this.placer.setSize(4);
				BlockPos var6 = this.centerPos.offset(13,16,13);
				this.placer.generate(var1.level(),var6,CANDLE_GENERATOR);
				BlockPos var7 = var6.offset(-9,0,0);
				this.placer.generate(var1.level(),var7,CANDLE_GENERATOR);
				BlockPos var8 = var7.offset(-9,0,0);
				this.placer.generate(var1.level(),var8,CANDLE_GENERATOR);
				BlockPos var9 = var8.offset(-9,0,0);
				this.placer.generate(var1.level(),var9,CANDLE_GENERATOR);
				BlockPos var10 = var9.offset(0,0,-9);
				this.placer.generate(var1.level(),var10,CANDLE_GENERATOR);
				BlockPos var11 = var10.offset(0,0,-9);
				this.placer.generate(var1.level(),var11,CANDLE_GENERATOR);
				BlockPos var12 = var11.offset(0,0,-9);
				this.placer.generate(var1.level(),var12,CANDLE_GENERATOR);
				BlockPos var13 = var12.offset(9,0,0);
				this.placer.generate(var1.level(),var13,CANDLE_GENERATOR);
				BlockPos var14 = var13.offset(9,0,0);
				this.placer.generate(var1.level(),var14,CANDLE_GENERATOR);
				BlockPos var15 = var14.offset(9,0,0);
				this.placer.generate(var1.level(),var15,CANDLE_GENERATOR);
				BlockPos var16 = var15.offset(0,0,9);
				this.placer.generate(var1.level(),var16,CANDLE_GENERATOR);
				BlockPos var17 = var16.offset(0,0,9);
				this.placer.generate(var1.level(),var17,CANDLE_GENERATOR);
				float var18 = (float)((this.blockTrackerComponent.getQueue().getQueueSize()) / (this.blocksPerTick));
				this.chargeComponent.startCharging(var1,var18);
				return;
			}
		}
	}
	private void onTickChargeEvent(LivingEntity var1, IAbility var2) {
		this.blockTrackerComponent.tickBlockQueue(var1.level());
		if ((!(var1.level().isClientSide)) && (((var1.tickCount) % (5)) == (0))){
			Vec3 var3 = var1.position().add(var1.getLookAngle().multiply(5D,5D,5D)).subtract(this.centerPosVec.add(0D,10D,0D)).normalize().multiply(1.149999976158142D,1.149999976158142D,1.149999976158142D).reverse();
			int var4 = 0;
			do{
				if ((var4) < (15)){
					double var5 = (WyHelper.randomDouble()) / (2D);
					double var6 = (WyHelper.randomDouble()) / (2D);
					double var7 = (WyHelper.randomDouble()) / (2D);
					double var8 = (WyHelper.randomDouble()) / (5D);
					double var9 = (WyHelper.randomDouble()) / (5D);
					double var10 = (WyHelper.randomDouble()) / (5D);
					SimpleParticleData var11 = new SimpleParticleData(((ParticleType)(ModParticleTypes.MOKU.get())));
					var11.setLife(30);
					var11.setSize(3F);
					var11.setMotion((var3.x) + (var8),(var3.y) + (var9),(var3.z) + (var10));
					WyHelper.spawnParticles(var11,((ServerLevel)(var1.level())),(var1.getX()) + (var5),((var1.getY()) + ((double)(var1.getEyeHeight()))) + (var6),(var1.getZ()) + (var7));
					var4++;
					continue;
				}else{
					break;
				}
			} while (true);
		}
	}
	private void onEndChargeEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.startContinuity(var1,1200F);
		if (!(var1.level().isClientSide)){
			WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.TOKUDAI_CANDLE.get())),(Entity)(var1),(double)(this.centerPos.getX()),(double)(this.centerPos.getY()),(double)(this.centerPos.getZ()));
		}
	}
	private void onTickContinuityEvent(LivingEntity var1, IAbility var2) {
		if ((!(var1.level().isClientSide)) && (((var1.tickCount) % (100)) == (0))){
			Iterator var3 = this.rangeComponent.getTargetsInArea(var1,100F,(TargetPredicate)(null)).iterator();
			do{
				if (var3.hasNext()){
					LivingEntity var4 = ((LivingEntity)(var3.next()));
					var4.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,100,0));
					var4.addEffect(new MobEffectInstance(MobEffects.WEAKNESS,100,0));
					var4.addEffect(new MobEffectInstance((Holder)(ModEffects.TOKUDAI_CANDLE_POISON),100,0));
					continue;
				}else{
					break;
				}
			} while (true);
			WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.TOKUDAI_CANDLE.get())),(Entity)(var1),(double)(this.centerPos.getX()),(double)(this.centerPos.getY()),(double)(this.centerPos.getZ()));
		}
	}
	private void onEndContinuityEvent(LivingEntity var1, IAbility var2) {
		if (var1.level().isClientSide){
			return;
		}else{
			if ((this.placer) != (null)){
				Iterator var3 = this.placer.getPlacedPositions().iterator();
				do{
					if (var3.hasNext()){
						BlockPos var4 = ((BlockPos)(var3.next()));
						var1.level().setBlockAndUpdate(var4,Blocks.AIR.defaultBlockState());
						continue;
					}else{
						break;
					}
				} while (true);
			}
			this.centerPos = null;
			this.cooldownComponent.startCooldown(var1,6000F);
			return;
		}
	}
	public void setBlocksPerTick(int var1) {
		this.blocksPerTick = var1;
	}
	public void setCenterPos(BlockPos var1) {
		this.centerPos = var1;
		this.centerPosVec = Vec3.atCenterOf((Vec3i)(var1));
	}
	static  {
		IBlockGenerator var0 = (l0, l1, l2) -> {
			int lv1_3 = l2.getX();
			int lv1_4 = l2.getY();
			int lv1_5 = l2.getZ();
			int lv1_6 = l0.getSizeY();
			BlockPos.MutableBlockPos lv1_7 = new BlockPos.MutableBlockPos();
			int lv1_8 = 0;
			do{
				if ((lv1_8) < (lv1_6)){
					lv1_7.set(lv1_3,(lv1_4) + (lv1_8),lv1_5);
					l0.placeNext(l1,lv1_7.immutable(),l0.getState(),l0.getFlag(),l0.getRule());
					lv1_8++;
					continue;
				}else{
					break;
				}
			} while (true);
			l0.placeNext(l1,lv1_7.above(),Blocks.FIRE.defaultBlockState(),3,l0.getRule());
		};
		CANDLE_GENERATOR = var0;
		AbilityRegistryEntry var1 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			AbilityDescriptionLine$IDescriptionLine[] lv2_2 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(6000F)};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(TokudaiCandleAbility::new)))).addAdvancedDescriptionLine(lv2_2).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("tokudai_candle","Tokudai Candle",var1));
	}
}