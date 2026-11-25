package xyz.pixelatedw.mineminenomi.abilities.sui;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Position;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
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
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModTags$Blocks;
import xyz.pixelatedw.mineminenomi.mixins.ILivingEntityMixin;

public class FreeSwimmingAbility extends Ability {
	// Fields
	private static final int COOLDOWN = 20;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<FreeSwimmingAbility>> INSTANCE;
	private final ContinuousComponent continuousComponent;
	private boolean isSwimming;

	public FreeSwimmingAbility(AbilityCore<FreeSwimmingAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this),true).addTickEvent((ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::tickContinuityEvent)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
		this.isSwimming = false;
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent};
		this.addComponents(var2);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::canUseMomentumAbilities))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.triggerContinuity(var1);
	}
	private void tickContinuityEvent(LivingEntity var1, IAbility var2) {
		var1.noPhysics = true;
		AbilityData var3 = ((AbilityData)(((java.util.Optional)(AbilityData.get(var1))).orElse((Object)(null))));
		if ((var3) == (null)){
			return;
		}else{
			boolean var4 = false;
			if (var1 instanceof Player){
				var4 = ((Player)(var1)).getAbilities().flying;
			}
			BlockPos var5 = var1.blockPosition();
			int var6 = ((var1.level().getBlockState(var5.above()).isAir()) && (var1.level().getBlockState(var5.below()).isAir())) ? (1) : (0);
			int var7 = ((((var1.position().y) - (AbilityHelper.getFloorLevel((Entity)(var1)).y())) > (0D)) && (var1.level().getBlockState(var5.below()).isAir())) ? (1) : (0);
			NekomimiPunchAbility var8 = ((NekomimiPunchAbility)(var3.getEquippedAbility(((AbilityCore)(NekomimiPunchAbility.INSTANCE.get())))));
			int var9 = (((var8) != (null)) && (var8.isContinuous())) ? (1) : (0);
			int var10 = (((var8) != (null)) && (((var1.level().getGameTime()) - (var8.getLastUseGametime())) < (100L))) ? (1) : (0);
			if ((!(var1.level().isClientSide)) && (var1.isInWater())){
				this.continuousComponent.stopContinuity(var1);
				return;
			}else{
				boolean var11 = false;
				if (((var7) != (0)) && (((var9) == (0)) && (((var10) == (0)) && (!(isEntityInsideOpaqueBlock(var1)))))){
					var11 = true;
				}
				if (var11){
					var1.noPhysics = false;
					return;
				}else{
					boolean var12 = isEntityInsideOpaqueBlock(var1);
					if (((!(var12)) && (!(var1.isSprinting()))) || (var4)){

					}else{
						var1.setPose(Pose.SWIMMING);
						var1.fallDistance = 0F;
					}
					if (((var6) != (0)) && ((var9) == (0))){
						var1.noPhysics = true;
						return;
					}else{
						NyanNyanSuplexAbility var13 = ((NyanNyanSuplexAbility)(var3.getEquippedAbility(((AbilityCore)(NyanNyanSuplexAbility.INSTANCE.get())))));
						if (((var13) != (null)) && (var13.isCharging())){
							return;
						}else{
							var1.setSwimming(true);
							boolean var14 = ((var1.getPose()) == (Pose.SWIMMING)) || ((var9) != (0));
							if (!(var1.level().isClientSide)){
								this.isSwimming = var14;
							}
							if ((var14) && (!((!(var12)) && (!(((var6) != (0)) && ((var9) != (0))))))){
								double var20 = 0.0;
								double var15 = 0D;
								double var16 = 0D;
								double var17 = 0D;
								double var18 = (var1.getAttribute(NeoForgeMod.SWIM_SPEED).getValue()) / (2D);
								Vec3 var19 = var1.getLookAngle();
								if ((var1.zza) != (0F)){
									var20 = Mth.clamp(var18,1.3D,6D);
									var15 = ((var19.x) * (var20)) * ((double)(var1.zza));
									var16 = ((var19.y) * (var20)) * ((double)(var1.zza));
									var17 = ((var19.z) * (var20)) * ((double)(var1.zza));
								}
								if ((var9) != (0)){
									var20 = 1.6D;
									var15 = ((var19.x) * (var20)) * (1D);
									var16 = ((var19.y) * (var20)) * (1D);
									var17 = ((var19.z) * (var20)) * (1D);
								}
								boolean var20_1 = ((ILivingEntityMixin)(var1)).isJumping();
								if (var1.isShiftKeyDown()){
									var16 = -0.20000000298023224D;
								}else{
									if ((var20_1) && (!(var1.level().getBlockState(var5.below()).isAir()))){
										var16 = 0.20000000298023224D;
										if (var1.level().getBlockState(var5.above()).isAir()){
											var16 = 0.6000000238418579D;
										}
									}
								}
								BlockPos var21 = BlockPos.containing((Position)(var1.position().add(var15,(var16) + ((double)(var1.getEyeHeight())),var17)));
								if (var1.level().getBlockState(var21).is(ModTags$Blocks.BLOCK_PROT_RESTRICTED)){
									Vec3 var22 = ((var1.zza) < (0F)) ? (var19) : ((Vec3)(var19.reverse()));
									var15 = var22.x;
									var16 = var22.y;
									var17 = var22.z;
								}
								if ((var1.getY()) < (-60D)){
									var16 = 0D;
								}
								AbilityHelper.setDeltaMovement((Entity)(var1),var15,var16,var17);
							}
							return;
						}
					}
				}
			}
		}
	}
	private void endContinuityEvent(LivingEntity var1, IAbility var2) {
		this.isSwimming = false;
		this.cooldownComponent.startCooldown(var1,20F);
		var1.noPhysics = false;
	}
	public void setSwimming(boolean var1) {
		this.isSwimming = var1;
	}
	public boolean isSwimming() {
		return this.isSwimming;
	}
	public static boolean isEntityInsideOpaqueBlock(LivingEntity var0) {
		BlockPos.MutableBlockPos var1 = new BlockPos.MutableBlockPos();
		int var2 = 0;
		do{
			if ((var2) < (8)){
				int var3 = Mth.floor(((var0.getY()) + ((double)((((float)(((var2) >> (0)) % (2))) - (0.5F)) * (0.1F)))) + ((double)(var0.getEyeHeight())));
				int var4 = Mth.floor((var0.getX()) + ((double)(((((float)(((var2) >> (1)) % (2))) - (0.5F)) * (var0.getBbHeight())) * (0.8F))));
				int var5 = Mth.floor((var0.getZ()) + ((double)(((((float)(((var2) >> (2)) % (2))) - (0.5F)) * (var0.getBbWidth())) * (0.8F))));
				if (((var1.getX()) == (var4)) && (((var1.getY()) == (var3)) && ((var1.getZ()) == (var5)))){

				}else{
					var1.set(var4,var3,var5);
					boolean var6 = var0.level().getBlockState((BlockPos)(var1)).isFaceSturdy((BlockGetter)(var0.level()),(BlockPos)(var1),Direction.UP);
					boolean var7 = var0.level().getFluidState((BlockPos)(var1)).isSource();
					if ((!(var6)) && (!(var7))){

					}else{
						return true;
					}
				}
				var2++;
				continue;
			}else{
				break;
			}
		} while (true);
		return false;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Lets the user swim trough blocks. Swimming is activated by running into a ground block."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(20F),ContinuousComponent.getTooltip()};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(FreeSwimmingAbility::new)))).addDescriptionLine(lv1_3).addAdvancedDescriptionLine(lv1_4).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("free_swimming","Free Swimming",var0));
	}
}