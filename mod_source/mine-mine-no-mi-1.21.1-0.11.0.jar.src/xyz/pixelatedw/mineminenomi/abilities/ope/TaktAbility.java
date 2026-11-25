package xyz.pixelatedw.mineminenomi.abilities.ope;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.api.protection.ProtectedArea;
import xyz.pixelatedw.mineminenomi.api.protection.ProtectedArea$RestorationEntry;
import xyz.pixelatedw.mineminenomi.api.util.DBlockPos;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.data.world.ProtectedAreasData;
import xyz.pixelatedw.mineminenomi.init.ModEntityPredicates;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModTags$Blocks;

public class TaktAbility extends Ability {
	// Fields
	private static final float COOLDOWN = 240F;
	private static final float HOLD_TIME = 60F;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<TaktAbility>> INSTANCE;
	private final ContinuousComponent continuousComponent;
	private List<Entity> grabbedEntities;

	public TaktAbility(AbilityCore<TaktAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this)).addTickEvent((ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::onContinuityTick)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::onContinuityEnd))));
		this.grabbedEntities = new ArrayList();
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent};
		this.addComponents(var2);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(RoomAbility::hasRoomActive))));
		this.addContinueUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(RoomAbility::hasRoomActive))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::onUseEvent))));
	}
	
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv9_2 = new Pair[]{ImmutablePair.of((Object)("The user lifts entities its looking at, preventing them from moving freely."),(Object)(null))};
			Component[] lv9_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv9_2);
			AbilityDescriptionLine$IDescriptionLine[] lv9_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(240F),ContinuousComponent.getTooltip(60F)};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(TaktAbility::new)))).addDescriptionLine(lv9_3).addAdvancedDescriptionLine(lv9_4).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("takt","Takt",var0));
	}

	private void onUseEvent(LivingEntity var1, IAbility var2) {
		Function<AbilityData, RoomAbility> var3 = (l0) -> {
			return ((RoomAbility)(l0.getEquippedAbility(((AbilityCore)(RoomAbility.INSTANCE.get())))));
		};
		RoomAbility var4 = ((RoomAbility)(((java.util.Optional)(AbilityData.get(var1))).map((Function)(var3)).orElse(null)));
		if ((var4) == (null)){
			return;
		}else{
			HitResult var5 = WyHelper.rayTraceBlocksAndEntities((Entity)(var1),(double)(var4.getROOMSize()));
			BlockPos var6 = BlockPos.containing((Position)(var5.getLocation()));
			if ((var5.getType()) == (HitResult.Type.BLOCK)){
				var6 = new BlockPos((Vec3i)(((BlockHitResult)(var5)).getBlockPos()));
			}else{
				if ((var5.getType()) == (HitResult.Type.ENTITY)){
					var6 = new BlockPos((Vec3i)(((EntityHitResult)(var5)).getEntity().blockPosition()));
				}
			}
			ProtectedAreasData var7 = ProtectedAreasData.get(((ServerLevel)(var1.level())));
			LivingEntity var8 = var1;
			ProtectedAreasData var9 = var7;
			final LivingEntity var8_f1 = var8;
			final ProtectedAreasData var9_f2 = var9;
			Function<BlockPos, FallingBlockEntity> var10 = (l0) -> {
			BlockState lv2_3 = var8_f1.level().getBlockState(l0);
			if ((var9_f2) != (null)){
				Optional lv2_4 = var9_f2.getProtectedArea(l0.getX(),l0.getY(),l0.getZ());
				if ((lv2_4.isPresent()) && ((((ProtectedArea)(lv2_4.get())).canDestroyBlocks()) && (((ProtectedArea)(lv2_4.get())).canRestoreBlocks()))){
					DBlockPos lv2_5 = new DBlockPos(l0.getX(),l0.getY(),l0.getZ(),var8_f1.level().dimension());
					BlockSnapshot lv2_6 = BlockSnapshot.create(var8_f1.level().dimension(),(LevelAccessor)(var8_f1.level()),l0,2);
					((ProtectedArea)(lv2_4.get())).queueForRestoration((BlockPos)(lv2_5),new ProtectedArea$RestorationEntry(var8_f1.level().getGameTime(),(BlockSnapshot)(lv2_6)));
				}
			}
			FallingBlockEntity lv2_4_1 = FallingBlockEntity.fall(var8_f1.level(),l0,lv2_3);
			AbilityHelper.setDeltaMovement((Entity)(lv2_4_1),0D,0D,0D);
			lv2_4_1.time = 5;
			lv2_4_1.setNoGravity(true);
			lv2_4_1.dropItem = false;
			var8_f1.level().removeBlock(l0,true);
			return lv2_4_1;
		};
			Function<BlockPos, FallingBlockEntity> var11 = var10;
			List var12 = this.grabbedEntities;
			Objects.requireNonNull(var12);
			WyHelper.getNearbyBlocks(var6,(LevelAccessor)(var1.level()),2,isPositionGriefable(var1),(List)(ImmutableList.of(Blocks.AIR))).stream().map(var11).forEach((Consumer)(((Consumer<FallingBlockEntity>)(var12::add))));
			RoomAbility var13 = var4;
			final RoomAbility var13_f3 = var13;
			Predicate<LivingEntity> var14 = (l0) -> {
			return var13_f3.isPositionInRoom(l0.blockPosition());
		};
			List var15 = this.grabbedEntities;
			Objects.requireNonNull(var15);
			TargetHelper.getEntitiesInArea((LevelAccessor)(var1.level()),var1,var5.getLocation(),2D,TargetPredicate.DEFAULT_AREA_CHECK,LivingEntity.class).stream().filter(ModEntityPredicates.IS_ALIVE_AND_SURVIVAL).filter(var14).forEach((Consumer)(((Consumer<LivingEntity>)(var15::add))));
			if (!(this.grabbedEntities.isEmpty())){
				this.continuousComponent.triggerContinuity(var1,60F);
			}
			return;
		}
	}
	private void onContinuityTick(LivingEntity var1, IAbility var2) {
		LivingEntity var5 = null;
		RoomAbility var6 = null;
		if (var1.level().isClientSide){
			return;
		}else{
			if (this.grabbedEntities.isEmpty()){
				this.continuousComponent.stopContinuity(var1);
				return;
			}else{
				Function<AbilityData, RoomAbility> var3 = (l0) -> {
			return ((RoomAbility)(l0.getEquippedAbility(((AbilityCore)(RoomAbility.INSTANCE.get())))));
		};
				RoomAbility var4 = ((RoomAbility)(((java.util.Optional)(AbilityData.get(var1))).map((Function)(var3)).orElse(null)));
				if ((var4) == (null)){
					return;
				}else{
					var5 = var1;
					var6 = var4;
					final LivingEntity var5_f1 = var5;
					final RoomAbility var6_f2 = var6;
					Consumer<Entity> var7 = (l0) -> {
			l0.setXRot(l0.xRotO);
			l0.setYRot(l0.yRotO);
			Random lv5_3 = new Random((long)(l0.hashCode()));
			double lv5_4 = (double)(WyHelper.randomWithRange(lv5_3,-2,2));
			double lv5_5 = (double)(WyHelper.randomWithRange(lv5_3,-2,2));
			double lv5_6 = (double)(WyHelper.randomWithRange(lv5_3,-2,2));
			double lv5_7 = 8D;
			Vec3 lv5_8 = var5_f1.getLookAngle();
			Vec3 lv5_9 = new Vec3(((lv5_8.x) * (lv5_7)) + (lv5_4),((((double)(var5_f1.getEyeHeight())) / (2D)) + ((lv5_8.y) * (lv5_7))) + (lv5_5),((lv5_8.z) * (lv5_7)) + (lv5_6));
			if (((l0 instanceof LivingEntity) && (var6_f2.isPositionInRoom(l0.blockPosition()))) || (isPositionGriefable(var5_f1).test(l0.blockPosition()))){
				AbilityHelper.setDeltaMovement(l0,var5_f1.position().add(lv5_9).subtract(l0.position()));
				if (l0 instanceof ServerPlayer){
					ServerPlayer lv5_10 = ((ServerPlayer)(l0));
				}
			}
			l0.fallDistance = 0F;
		};
					((java.util.stream.Stream)(this.grabbedEntities.stream())).forEach((Consumer)(var7));
					return;
				}
			}
		}
	}private void onContinuityEnd(LivingEntity var1, IAbility var2) {
		Consumer<Entity> var3 = (l0) -> {
			l0.setNoGravity(false);
		};
		((java.util.stream.Stream)(((java.util.stream.Stream)(this.grabbedEntities.stream())).filter((Predicate)(((java.util.function.Predicate)(((Predicate<Entity>)(Entity::isNoGravity)))))))).forEach((Consumer)(var3));
		this.grabbedEntities.clear();
		this.cooldownComponent.startCooldown(var1,240F);
	}
	private static Predicate<BlockPos> isPositionGriefable(LivingEntity var0) {
		Function<AbilityData, RoomAbility> var1 = (l0) -> {
			return ((RoomAbility)(l0.getEquippedAbility(((AbilityCore)(RoomAbility.INSTANCE.get())))));
		};
		RoomAbility var2 = ((RoomAbility)(((java.util.Optional)(AbilityData.get(var0))).map((Function)(var1)).orElse(null)));
		if ((var2) == (null)){
			return Predicates.alwaysFalse();
		}else{
			ProtectedAreasData var3 = ProtectedAreasData.get(((ServerLevel)(var0.level())));
			ProtectedAreasData var4 = var3;
			RoomAbility var5 = var2;
			LivingEntity var6 = var0;
			final ProtectedAreasData var4_f1 = var4;
			final RoomAbility var5_f2 = var5;
			final LivingEntity var6_f3 = var6;
			Predicate<BlockPos> var7 = (l0) -> {
			if (((!(ServerConfig.isAbilityGriefingEnabled())) ? (1) : (0)) != (0)){
				return false;
			}else{
				Optional lv8_4 = var4_f1.getProtectedArea(l0.getX(),l0.getY(),l0.getZ());
				if ((lv8_4.isPresent()) && (!(((ProtectedArea)(lv8_4.get())).canDestroyBlocks()))){
					return false;
				}else{
					if (!(var5_f2.isPositionInRoom(l0))){
						return false;
					}else{
						return !(var6_f3.level().getBlockState(l0).is(ModTags$Blocks.BLOCK_PROT_RESTRICTED));
					}
				}
			}
		};
			return var7;
		}
	}}