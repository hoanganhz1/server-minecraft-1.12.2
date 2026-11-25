package xyz.pixelatedw.mineminenomi.abilities.gura;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.util.BlockSnapshot;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BlockTrackerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IDuringChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IStartChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent$RangeType;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.blockgen.BlockGenerators;
import xyz.pixelatedw.mineminenomi.api.blockgen.IBlockGenerator;
import xyz.pixelatedw.mineminenomi.api.blockgen.SimpleBlockPlacer;
import xyz.pixelatedw.mineminenomi.api.damagesources.AbilityDamageSource;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.api.protection.ProtectedArea;
import xyz.pixelatedw.mineminenomi.api.protection.ProtectedArea$RestorationEntry;
import xyz.pixelatedw.mineminenomi.api.util.DBlockPos;
import xyz.pixelatedw.mineminenomi.data.world.ProtectedAreasData;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.init.ModTags$Blocks;
import xyz.pixelatedw.mineminenomi.particles.effects.BreakingBlocksParticleEffect$Details;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class ShimaYurashiAbility extends Ability {
	// Fields
	private static final int EXPLOSION_RADIUS = 35;
	private static final int EXPLOSION_DEPTH = 10;
	private static final int COOLDOWN = 1200;
	private static final int CHARGE_TIME = 100;
	private static final int MIN_DAMAGE = 66;
	private static final int MAX_DAMAGE = 80;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<ShimaYurashiAbility>> INSTANCE;
	private final ChargeComponent chargeComponent;
	private final AnimationComponent animationComponent;
	private final RangeComponent rangeComponent;
	private final DealDamageComponent dealDamageComponent;
	private final BlockTrackerComponent blockTrackerComponent;
	private BreakingBlocksParticleEffect$Details details;

	public ShimaYurashiAbility(AbilityCore<ShimaYurashiAbility> var1) {
		super((AbilityCore)(var1));
		this.chargeComponent = new ChargeComponent((IAbility)(this)).addStartEvent((ChargeComponent$IStartChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IStartChargingEvent)(this::startChargeEvent)))).addTickEvent((ChargeComponent$IDuringChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IDuringChargingEvent)(this::duringChargeEvent)))).addEndEvent((ChargeComponent$IEndChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent)(this::endChargeEvent))));
		this.animationComponent = new AnimationComponent((IAbility)(this));
		this.rangeComponent = new RangeComponent((IAbility)(this));
		this.dealDamageComponent = new DealDamageComponent((IAbility)(this));
		this.blockTrackerComponent = new BlockTrackerComponent((IAbility)(this));
		this.details = new BreakingBlocksParticleEffect$Details(40);
		AbilityComponent[] var2 = new AbilityComponent[]{this.chargeComponent,this.animationComponent,this.rangeComponent,this.dealDamageComponent,this.blockTrackerComponent};
		this.addComponents(var2);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::requiresOnGround))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		this.chargeComponent.startCharging(var1,100F);
	}
	
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv6_2 = new Pair[]{ImmutablePair.of((Object)("The user grabs the air around them and pulls it downwards after which the nearby land and entities are sent flying."),(Object)(null))};
			Component[] lv6_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv6_2);
			AbilityDescriptionLine$IDescriptionLine[] lv6_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(1200F),ChargeComponent.getTooltip(100F),RangeComponent.getTooltip(35F,RangeComponent$RangeType.AOE),DealDamageComponent.getTooltip(66F,66F)};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(ShimaYurashiAbility::new)))).addDescriptionLine(lv6_3).addAdvancedDescriptionLine(lv6_4).setSourceElement(SourceElement.SHOCKWAVE).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("shima_yurashi","Shima Yurashi",var0));
	}

	private void startChargeEvent(LivingEntity var1, IAbility var2) {
		List var3 = this.blockTrackerComponent.getNearbyBlockPositions(var1.blockPosition(),(LevelAccessor)(var1.level()),17,5,ModTags$Blocks.BLOCK_PROT_CORE);
		Collections.shuffle(var3);
		this.details.clearPositions();
		this.blockTrackerComponent.clearPositions();
		Optional var4 = ProtectedAreasData.get(((ServerLevel)(var1.level()))).getProtectedArea((int)(var1.getX()),(int)(var1.getY()),(int)(var1.getZ()));
		if ((!(var4.isEmpty())) && (!(((ProtectedArea)(var4.get())).canDestroyBlocks()))){

		}else{
			ShimaYurashiAbility var5 = this;
			LivingEntity var6 = var1;
			final LivingEntity var6_f1 = var6;
			Consumer<BlockPos> var7 = (l0) -> {
			if (var6_f1.level().canSeeSky(l0)){
				this.details.addPosition(l0);
			}
			if ((l0.distSqr((Vec3i)(var6_f1.blockPosition()))) > (9D)){
				this.blockTrackerComponent.addBlockPos(l0);
			}
		};
			((java.util.stream.Stream)(var3.stream().limit(600L))).forEach((Consumer)(var7));
		}
		this.animationComponent.start(var1,ModAnimations.KAISHIN,100);
	}private void duringChargeEvent(LivingEntity var1, IAbility var2) {
		if (((this.chargeComponent.getChargeTime()) % (5F)) == (0F)){
			WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.GREAT_STOMP.get())),(Entity)(var1),var1.getX(),var1.getY(),var1.getZ());
		}
		var1.addEffect(new MobEffectInstance((Holder)(ModEffects.MOVEMENT_BLOCKED),10,0,false,false));
		List var3 = this.rangeComponent.getTargetsInArea(var1,35F);
		Predicate<LivingEntity> var4 = (l0) -> {
			return ((l0) != (null)) && ((l0.isAlive()) && (l0.level().clip(new ClipContext(l0.position(),l0.position().add(0D,-10D,0D),ClipContext.Block.COLLIDER,ClipContext.Fluid.ANY,(Entity)(l0))).getType().equals(HitResult.Type.BLOCK)));
		};
		Consumer<LivingEntity> var5 = (l0) -> {
			l0.addEffect(new MobEffectInstance((Holder)(ModEffects.DIZZY),10,0,false,false));
		};
		((java.util.stream.Stream)(((java.util.stream.Stream)(var3.stream())).filter((Predicate)(var4)))).forEach((Consumer)(var5));
	}
	private void endChargeEvent(LivingEntity var1, IAbility var2) {
		if (var1.level().isClientSide){
			return;
		}else{
			List var3 = this.rangeComponent.getTargetsInArea(var1,35F);
			AbilityDamageSource var4 = this.dealDamageComponent.getDamageSource(var1);
			IDamageSourceHandler var5 = IDamageSourceHandler.getHandler(var4);
			var5.bypassLogia();
			var5.addAbilityPiercing(0.5F,this.getCore());
			var5.setUnavoidable();
			LivingEntity var6 = var1;
			final LivingEntity var6_f1 = var6;
			Predicate<LivingEntity> var7 = (l0) -> {
			return ((l0) != (null)) && ((l0.isAlive()) && (var6_f1.hasLineOfSight((Entity)(l0))));
		};
			ShimaYurashiAbility var8 = this;
			LivingEntity var9 = var1;
			DamageSource var10 = var4;
			final LivingEntity var9_f2 = var9;
			final DamageSource var10_f3 = var10;
			Consumer<LivingEntity> var11 = (l0) -> {
			double lv5_4 = (80D) - ((Math.min(66D,Math.sqrt(l0.distanceToSqr((Entity)(var9_f2))))) / (2D));
			if ((this.dealDamageComponent.hurtTarget(var9_f2,l0,(float)(lv5_4),var10_f3)) && (l0.onGround())){
				Vec3 lv5_5 = var9_f2.position().subtract(l0.position()).normalize().multiply(25D,1D,25D);
				AbilityHelper.setDeltaMovement((Entity)(l0),-(lv5_5.x),3D,-(lv5_5.z));
			}
		};
			((java.util.stream.Stream)(((java.util.stream.Stream)(var3.stream())).filter((Predicate)(var7)))).forEach((Consumer)(var11));
			EntityHitResult var12 = WyHelper.rayTraceEntities((Entity)(var1),1.5D);
			WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.AIR_CRACKS.get())),(Entity)(var1),var12.getLocation().x(),var1.getEyeY(),var12.getLocation().z());
			ProtectedAreasData var13 = ProtectedAreasData.get(((ServerLevel)(var1.level())));
			Iterator var14 = this.blockTrackerComponent.getPositions().iterator();
			do{
				if (var14.hasNext()){
					BlockPos var15 = ((BlockPos)(var14.next()));
					if ((var13) != (null)){
						Optional var16 = var13.getProtectedArea(var15.getX(),var15.getY(),var15.getZ());
						if ((var16.isPresent()) && ((((ProtectedArea)(var16.get())).canDestroyBlocks()) && (((ProtectedArea)(var16.get())).canRestoreBlocks()))){
							DBlockPos var17 = new DBlockPos(var15.getX(),var15.getY(),var15.getZ(),var1.level().dimension());
							BlockSnapshot var18 = BlockSnapshot.create(var1.level().dimension(),(LevelAccessor)(var1.level()),var15,2);
							((ProtectedArea)(var16.get())).queueForRestoration((BlockPos)(var17),new ProtectedArea$RestorationEntry(var1.level().getGameTime(),(BlockSnapshot)(var18)));
						}
					}
					FallingBlockEntity var16_1 = FallingBlockEntity.fall(var1.level(),var15,var1.level().getBlockState(var15));
					var16_1.dropItem = false;
					var16_1.time = 1;
					Vec3 var17_1 = var1.position().subtract(var16_1.position()).normalize().multiply(2D,2D,2D);
					AbilityHelper.setDeltaMovement((Entity)(var16_1),-(var17_1.x),(1.5D) + (var17_1.y),-(var17_1.z));
					continue;
				}else{
					break;
				}
			} while (true);
			new SimpleBlockPlacer().setBlock(Blocks.AIR.defaultBlockState()).setSize(35,10).setRule(DefaultProtectionRules.CORE_FOLIAGE_ORE_LIQUID).generate(var1.level(),var1.blockPosition(),BlockGenerators.SPHERE_WITH_PLATFORM);
			var1.level().playSound((Player)(null),var1.blockPosition(),((SoundEvent)(ModSounds.GURA_SFX.get())),SoundSource.PLAYERS,5F,1F);
			this.animationComponent.stop(var1);
			this.cooldownComponent.startCooldown(var1,1200F);
			return;
		}
	}}