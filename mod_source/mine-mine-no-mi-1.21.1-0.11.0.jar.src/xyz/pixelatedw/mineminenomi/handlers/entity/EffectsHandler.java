package xyz.pixelatedw.mineminenomi.handlers.entity;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundRemoveMobEffectPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.MobEffectComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.effects.AbilityMobEffectInstance;
import xyz.pixelatedw.mineminenomi.api.effects.IBreakableEffect;
import xyz.pixelatedw.mineminenomi.api.effects.IChangeSwingSpeedEffect;
import xyz.pixelatedw.mineminenomi.api.effects.IDisableAbilitiesEffect;
import xyz.pixelatedw.mineminenomi.api.effects.IIgnoreMilkEffect;
import xyz.pixelatedw.mineminenomi.api.effects.ILinkedEffect;
import xyz.pixelatedw.mineminenomi.api.effects.IScreenShaderEffect;
import xyz.pixelatedw.mineminenomi.api.effects.ITransmisibleByProximityEffect;
import xyz.pixelatedw.mineminenomi.api.effects.ITransmisibleByTouchEffect;
import xyz.pixelatedw.mineminenomi.api.enums.HandcuffType;
import xyz.pixelatedw.mineminenomi.api.enums.NetType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.CarryData;
import xyz.pixelatedw.mineminenomi.data.entity.CombatData;
import xyz.pixelatedw.mineminenomi.effects.CaughtInNetEffect;
import xyz.pixelatedw.mineminenomi.effects.HandcuffedEffect;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.items.HandcuffsItem;
import xyz.pixelatedw.mineminenomi.mixins.ILivingEntityMixin;
import xyz.pixelatedw.mineminenomi.mixins.IMobEffectInstanceMixin;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SAddScreenShaderPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SRemoveScreenShaderPacket;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect$Details;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$Details;

public class EffectsHandler {
	public static void checkOilCovered(LivingEntity var0) {
		if ((var0.hasEffect((Holder)(ModEffects.OIL_COVERED))) && (var0.isOnFire())){
			AbilityExplosion var1 = new AbilityExplosion((Entity)(var0),(IAbility)(null),var0.getX(),var0.getY(),var0.getZ(),4F);
			var1.setStaticDamage(40F);
			var1.setFireAfterExplosion(true);
			var1.setSmokeParticles(((ParticleEffect)(ModParticleEffects.COMMON_EXPLOSION.get())),(ParticleEffect$Details)(CommonExplosionParticleEffect.EXPLOSION4));
			var1.explode();
			var0.removeEffect((Holder)(ModEffects.OIL_COVERED));
			var0.getServer().getPlayerList().broadcast((Player)(null),var0.position().x,var0.position().y,var0.position().z,100D,var0.getCommandSenderWorld().dimension(),(Packet)(new ClientboundRemoveMobEffectPacket(var0.getId(),(Holder)(ModEffects.OIL_COVERED))));
		}
	}
	public static float applyBreakableEffectDamage(LivingEntity var0, float var1) {
		Class var2 = IBreakableEffect.class;
		Objects.requireNonNull(var2);
		List var3 = ((List)(((java.util.stream.Stream)(((java.util.stream.Stream)(var0.getActiveEffectsMap().keySet().stream())).filter((Predicate)(((java.util.function.Predicate)(((Predicate<Holder>)(var2::isInstance)))))))).collect((Collector)(Collectors.toList()))));
		float var4 = 1F;
		Iterator var5 = var3.iterator();
		do{
			if (var5.hasNext()){
				Holder var6 = ((Holder)(var5.next()));
				if (((var6) != (null)) && (var6 instanceof IBreakableEffect)){
					IBreakableEffect var7 = ((IBreakableEffect)(var6));
					if (var0.hasEffect(var6)){
						var0.removeEffect(var6);
						float var8 = var7.getBreakingMultiplier();
						if ((var8) > (var4)){
							var4 = var8;
							continue;
						}else{
							continue;
						}
					}else{
						continue;
					}
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
		return (var1) * (var4);
	}
	public static void removeAbilityEffectsOnJoin(LivingEntity var0) {
		Iterator var1 = var0.getActiveEffects().iterator();
		do{
			if (var1.hasNext()){
				MobEffectInstance var2 = ((MobEffectInstance)(var1.next()));
				if (var2 instanceof AbilityMobEffectInstance){
					AbilityMobEffectInstance var3 = ((AbilityMobEffectInstance)(var2));
					var1.remove();
					((ILivingEntityMixin)(var0)).invokeOnEffectRemoved(var3);
					continue;
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
	}
	public static void removeUnneededEffects(LivingEntity var0) {
		Iterator var1 = var0.getActiveEffects().iterator();
		do{
			if (var1.hasNext()){
				if ((((MobEffectInstance)(var1.next())).getEffect().equals(ModEffects.IN_EVENT)) && (!(NuWorld.isChallengeDimension(var0.level())))){
					var0.removeEffect((Holder)(ModEffects.IN_EVENT));
					continue;
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
	}
	public static boolean checkApplicableEffect(LivingEntity var0, MobEffectInstance var1) {
		AbilityData var2 = ((AbilityData)(((java.util.Optional)(AbilityData.get(var0))).orElse((Object)(null))));
		if ((var2) == (null)){
			return true;
		}else{
			Iterator var3 = var2.getEquippedAndPassiveAbilities().iterator();
			do{
				if (var3.hasNext()){
					MobEffectComponent var4 = ((MobEffectComponent)(((IAbility)(var3.next())).getComponent(((AbilityComponentKey)(ModAbilityComponents.MOB_EFFECT.get()))).orElse(null)));
					if (((var4) != (null)) && (!(var4.checkEffect(var0,var1)))){
						return false;
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
			return true;
		}
	}
	public static float applySwingSpeedEffects(LivingEntity var0, float var1) {
		Iterator var2 = var0.getActiveEffects().iterator();
		do{
			if (var2.hasNext()){
				MobEffectInstance var3 = ((MobEffectInstance)(var2.next()));
				Holder var4 = var3.getEffect();
				if (var4 instanceof IChangeSwingSpeedEffect){
					var1 = (var1) / (((IChangeSwingSpeedEffect)(var4)).swingSpeedModifier(var3.getDuration(),var3.getAmplifier()));
					continue;
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
		return var1;
	}
	public static void removeScreenShader(LivingEntity var0, MobEffectInstance var1) {
		if ((!(var0.level().isClientSide)) && (var0 instanceof ServerPlayer)){
			ServerPlayer var2 = ((ServerPlayer)(var0));
			Holder var3 = var1.getEffect();
			if (var3 instanceof IScreenShaderEffect){
				IScreenShaderEffect var4 = ((IScreenShaderEffect)(var3));
				ModNetwork.sendTo(new SRemoveScreenShaderPacket(var4.getScreenShader()),(Player)(var2));
			}else{
				return;
			}
		}
	}
	public static void applyScreenShader(LivingEntity var0, MobEffectInstance var1) {
		if ((!(var0.level().isClientSide)) && (var0 instanceof ServerPlayer)){
			ServerPlayer var2 = ((ServerPlayer)(var0));
			Holder var3 = var1.getEffect();
			if (var3 instanceof IScreenShaderEffect){
				IScreenShaderEffect var4 = ((IScreenShaderEffect)(var3));
				ModNetwork.sendTo(new SAddScreenShaderPacket(var4.getScreenShader()),(Player)(var2));
			}else{
				return;
			}
		}
	}
	public static void launchedCollision(LivingEntity var0) {
		if ((var0.hasEffect((Holder)(ModEffects.LAUNCHED))) && (!((!(var0.horizontalCollision)) && (!(var0.verticalCollision))))){
			if (!(var0.level().getBlockState(var0.blockPosition().below()).isAir())){
				var0.hurt(var0.level().damageSources().fall(),10F);
			}
			var0.removeEffect((Holder)(ModEffects.LAUNCHED));
		}
	}
	public static void effectsReduction(LivingEntity var0, DamageSource var1) {
		DamageSources var2 = var0.level().damageSources();
		int var3 = ((var1) == (var2.lightningBolt())) ? (1) : (0);
		int var4 = ((var1) == (var2.lava())) ? (1) : (0);
		int var5 = (((var1) == (var2.onFire())) || ((var1) == (var2.inFire()))) ? (1) : (0);
		if (((var3) == (0)) && (((var4) == (0)) && ((var5) == (0)))){

		}else{
			if (var0.hasEffect((Holder)(ModEffects.FROZEN))){
				MobEffectInstance var6 = var0.getEffect((Holder)(ModEffects.FROZEN));
				((IMobEffectInstanceMixin)(var6)).setDuration((int)(((double)(var6.getDuration())) * (0.5D)));
				var0.clearFire();
				return;
			}else{
				if (var0.hasEffect((Holder)(ModEffects.FROSTBITE))){
					var0.removeEffect((Holder)(ModEffects.FROSTBITE));
					var0.clearFire();
				}
				if (var0.hasEffect((Holder)(ModEffects.CANDLE_LOCK))){
					var0.removeEffect((Holder)(ModEffects.CANDLE_LOCK));
					var0.clearFire();
				}
				if (var0.hasEffect((Holder)(ModEffects.CANDY_STUCK))){
					var0.removeEffect((Holder)(ModEffects.CANDY_STUCK));
					var0.clearFire();
				}else{
					return;
				}
			}
		}
	}
	public static void storeDrunkDamage(LivingEntity var0, float var1) {
		if (var0.hasEffect((Holder)(ModEffects.DRUNK))){
			float var2 = var1;
			LivingEntity var3 = var0;
			final float var2_f1 = var2;
			final LivingEntity var3_f2 = var3;
			Consumer<CombatData> var4 = (l0) -> {
			l0.setStoredDamage((l0.getStoredDamage()) + (var2_f1));
			var3_f2.heal(var2_f1);
		};
			((java.util.Optional)(CombatData.get(var0))).ifPresent((Consumer)(var4));
		}
	}
	public static boolean blockMilkRemoval(LivingEntity var0, MobEffectInstance var1) {
		if ((var0.getUsedItemHand()) == (null)){
			return false;
		}else{
			MobEffect var2 = ((MobEffect)(var1.getEffect().value()));
			if (((((var0.getItemInHand(var0.getUsedItemHand()).getItem()) == (Items.MILK_BUCKET)) ? (1) : (0)) != (0)) && ((var2 instanceof IIgnoreMilkEffect) && (!(((IIgnoreMilkEffect)(var2)).isRemoveable())))){
				return true;
			}else{
				return false;
			}
		}
	}
	public static void disableAbilitiesEffect(LivingEntity var0, MobEffectInstance var1) {
		Holder var2 = var1.getEffect();
		if (var2 instanceof IDisableAbilitiesEffect){
			Predicate var3 = ((IDisableAbilitiesEffect)(var2)).getDisabledAbilities();
			AbilityHelper.disableAbilities(var0,var1.getDuration(),var3);
		}
	}
	public static void enableAbilitiesEffect(LivingEntity var0, MobEffectInstance var1) {
		Holder var2 = var1.getEffect();
		if (var2 instanceof IDisableAbilitiesEffect){
			AbilityHelper.enableAbilities(var0,((IDisableAbilitiesEffect)(var2)).getDisabledAbilities());
		}
	}
	public static void damageDehydrationEffect(LivingEntity var0, ItemStack var1) {
		if (!(var0.hasEffect((Holder)(ModEffects.DEHYDRATION)))){
			return;
		}else{
			Item var2 = var1.getItem();
			boolean var3 = ((PotionContents)(var1.get(DataComponents.POTION_CONTENTS))).is(Potions.WATER);
			if (((var2) != (Items.MILK_BUCKET)) && (((var2) != (ModItems.COLA)) && (((var2) != (ModItems.ULTRA_COLA)) && (!(var3))))){

			}else{
				MobEffectInstance var4 = var0.getEffect((Holder)(ModEffects.DEHYDRATION));
				if ((var4.getAmplifier()) == (0)){
					var0.removeEffect((Holder)(ModEffects.DEHYDRATION));
				}else{
					int var5 = (var4.getAmplifier()) - (1);
					((IMobEffectInstanceMixin)(var4)).setAmplifier(var5);
				}
			}
			return;
		}
	}
	public static void applyWetEffectFromProjectile(LivingEntity var0, Projectile var1) {
		if ((var1 instanceof ThrownPotion) && (((PotionContents)(((ThrownPotion)(var1)).getItem().get(DataComponents.POTION_CONTENTS))).is(Potions.WATER))){
			var0.addEffect(new MobEffectInstance((Holder)(ModEffects.WET),60,0,false,false));
		}
	}
	public static void tryLinkEffectsWithPassangers(LivingEntity var0) {
		MobEffectInstance var7_1 = null;
		Iterator var1 = var0.getActiveEffects().iterator();
		LOOP_1:
		do{
			if (var1.hasNext()){
				MobEffectInstance var2 = ((MobEffectInstance)(var1.next()));
				int var3 = ((var2.getEffect() instanceof ITransmisibleByTouchEffect) && (((ITransmisibleByTouchEffect)(var2.getEffect())).isTransmisibleByTouch())) ? (1) : (0);
				boolean var4 = var2.getEffect() instanceof ILinkedEffect;
				if (((var3) == (0)) && (!(var4))){
					continue;
				}else{
					MobEffectInstance var5 = new MobEffectInstance(var2.getEffect(),var2.getDuration(),var2.getAmplifier(),var2.isAmbient(),var2.isVisible(),var2.showIcon());
					Iterator var6 = var0.getPassengers().iterator();
					do{
						if (var6.hasNext()){
							Entity var7 = ((Entity)(var6.next()));
							if (((var7) != (null)) && ((var7.isAlive()) && (var7 instanceof LivingEntity))){
								((LivingEntity)(var7)).addEffect(var5);
								continue;
							}else{
								continue;
							}
						}else{
							break;
						}
					} while (true);
					var7_1 = var5;
					final MobEffectInstance var7_1_f1 = var7_1;
					Consumer<CarryData> var8 = (l0) -> {
			if (l0.isCarrying()){
				l0.getCarry().addEffect(var7_1_f1);
			}
		};
					((java.util.Optional)(CarryData.get(var0))).ifPresent((Consumer)(var8));
					continue LOOP_1;
				}
			}else{
				break;
			}
		} while (true);
	}
	public static void tryUnlinkEffectsWithPassangers(LivingEntity var0) {
		MobEffectInstance var4_1 = null;
		Iterator var1 = var0.getActiveEffects().iterator();
		LOOP_1:
		do{
			if (var1.hasNext()){
				MobEffectInstance var2 = ((MobEffectInstance)(var1.next()));
				if (var2.getEffect() instanceof ILinkedEffect){
					Iterator var3 = var0.getPassengers().iterator();
					do{
						if (var3.hasNext()){
							Entity var4 = ((Entity)(var3.next()));
							if (((var4) != (null)) && ((var4.isAlive()) && (var4 instanceof LivingEntity))){
								((LivingEntity)(var4)).removeEffect(var2.getEffect());
								continue;
							}else{
								continue;
							}
						}else{
							break;
						}
					} while (true);
					var4_1 = var2;
					final MobEffectInstance var4_1_f1 = var4_1;
					Consumer<CarryData> var5 = (l0) -> {
			if (l0.isCarrying()){
				l0.getCarry().removeEffect(var4_1_f1.getEffect());
			}
		};
					((java.util.Optional)(CarryData.get(var0))).ifPresent((Consumer)(var5));
					continue LOOP_1;
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
	}
	public static void trySpreadProximityEffect(LivingEntity var0, MobEffectInstance var1) {
		Holder var2 = var1.getEffect();
		if (var2 instanceof ITransmisibleByProximityEffect){
			ITransmisibleByProximityEffect var3 = ((ITransmisibleByProximityEffect)(var2));
			if (var3.isTransmisibleByProximity()){
				float var4 = var3.poximityTransmisionDistance();
				Iterator var5 = TargetHelper.getEntitiesInArea((LevelAccessor)(var0.level()),var0,(double)(var4),TargetPredicate.EVERYTHING,LivingEntity.class).iterator();
				do{
					if (var5.hasNext()){
						LivingEntity var6 = ((LivingEntity)(var5.next()));
						if (!(var6.hasEffect(var1.getEffect()))){
							var6.addEffect(new MobEffectInstance(var1.getEffect(),var1.getDuration(),var1.getAmplifier(),var1.isAmbient(),var1.isVisible(),var1.showIcon()));
							continue;
						}else{
							continue;
						}
					}else{
						break;
					}
				} while (true);
			}else{
				return;
			}
		}
	}
	public static void damageHandcuffs(LivingEntity var0, float var1) {
		Iterator var2 = var0.getActiveEffects().iterator();
		do{
			if (var2.hasNext()){
				int var4 = 0;
				int var5 = 0;
				MobEffectInstance var3 = ((MobEffectInstance)(var2.next()));
				if (var3.getEffect() instanceof HandcuffedEffect){
					var4 = var3.getDuration();
					var5 = (int)(((float)(var4)) - ((var1) * (30F)));
					((IMobEffectInstanceMixin)(var3)).setDuration(var5);
					WyHelper.sendApplyEffectToAllNearby(var0,var0.position(),100,var3);
					if (((var3.getDuration()) <= (1)) && (((((HandcuffedEffect)(var3.getEffect())).getType()) == (HandcuffType.KAIROSEKI)) && (var0 instanceof Player))){
						Player var6 = ((Player)(var0));
						Predicate<IAbility> var7 = (l0) -> {
			return (l0.getCore().getCategory()) == (AbilityCategory.DEVIL_FRUITS);
		};
						AbilityHelper.enableAbilities((LivingEntity)(var6),var7);
					}
				}
				if (var3.getEffect() instanceof CaughtInNetEffect){
					var4 = var3.getDuration();
					var5 = (int)(((float)(var4)) - ((var1) * (50F)));
					((IMobEffectInstanceMixin)(var3)).setDuration(var5);
					WyHelper.sendApplyEffectToAllNearby(var0,var0.position(),100,var3);
					if (((var3.getDuration()) <= (1)) && (((((CaughtInNetEffect)(var3.getEffect())).getType()) == (NetType.KAIROSEKI)) && (var0 instanceof Player))){
						Player var6_1 = ((Player)(var0));
						Predicate<IAbility> var7_1 = (l0) -> {
			return (l0.getCore().getCategory()) == (AbilityCategory.DEVIL_FRUITS);
		};
						AbilityHelper.enableAbilities((LivingEntity)(var6_1),var7_1);
						continue;
					}else{
						continue;
					}
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
	}
	public static boolean applyHandcuffs(LivingEntity var0, LivingEntity var1, float var2) {
		boolean var3 = false;
		Iterator var4 = var0.getActiveEffects().iterator();
		do{
			if (var4.hasNext()){
				if (((MobEffectInstance)(var4.next())).getEffect() instanceof HandcuffedEffect){
					var3 = true;
					break;
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
		if (var3){
			return false;
		}else{
			if ((!((var2) <= (0F))) && (!(((var0.getHealth()) - (var2)) > (0F)))){
				return HandcuffsItem.handleHandcuffActivation(var1.getOffhandItem(),var0,var2);
			}else{
				return false;
			}
		}
	}
	public static void unlockHandcuffs(LivingEntity var0, LivingEntity var1) {
		if ((var0.getMainHandItem().getItem()) != (ModItems.KEY)){
			return;
		}else{
			if (var1.getActiveEffects().stream().anyMatch((Predicate)(((Predicate<MobEffectInstance>)((l0) -> {
			return l0.getEffect() instanceof HandcuffedEffect;
		}))))){
				var0.getMainHandItem().shrink(1);
				if ((var1.hasEffect((Holder)(ModEffects.HANDCUFFED_EXPLOSIVE))) && (!(var1.level().isClientSide))){
					AbilityExplosion var2 = new AbilityExplosion((Entity)(var1),(IAbility)(null),var1.getX(),var1.getY(),var1.getZ(),4F);
					var2.setStaticDamage(15F);
					var2.ignoreFactionChecks();
					var2.setSmokeParticles(((ParticleEffect)(ModParticleEffects.COMMON_EXPLOSION.get())),(ParticleEffect$Details)(CommonExplosionParticleEffect.EXPLOSION5));
					var2.explode();
				}
				var1.removeEffect((Holder)(ModEffects.HANDCUFFED));
				var1.removeEffect((Holder)(ModEffects.HANDCUFFED_KAIROSEKI));
				var1.removeEffect((Holder)(ModEffects.HANDCUFFED_EXPLOSIVE));
			}
			return;
		}
	}
	public static void handcuffedDeath(LivingEntity var0) {
		if (var0.hasEffect((Holder)(ModEffects.HANDCUFFED))){
			var0.spawnAtLocation(((Item)(ModItems.NORMAL_HANDCUFFS.get())).getDefaultInstance());
		}else{
			if (var0.hasEffect((Holder)(ModEffects.HANDCUFFED_KAIROSEKI))){
				var0.spawnAtLocation(((Item)(ModItems.KAIROSEKI_HANDCUFFS.get())).getDefaultInstance());
			}else{
				if ((var0.hasEffect((Holder)(ModEffects.HANDCUFFED_EXPLOSIVE))) && (!(var0.level().isClientSide))){
					AbilityExplosion var1 = new AbilityExplosion((Entity)(var0),(IAbility)(null),var0.getX(),var0.getY(),var0.getZ(),4F);
					var1.setStaticDamage(15F);
					var1.ignoreFactionChecks();
					var1.setSmokeParticles(((ParticleEffect)(ModParticleEffects.COMMON_EXPLOSION.get())),(ParticleEffect$Details)(CommonExplosionParticleEffect.EXPLOSION5));
					var1.explode();
				}else{
					return;
				}
			}
		}
	}
	public static void bubblyCoralTick(LivingEntity var0) {
		if (var0.hasEffect(ModEffects.BUBBLY_CORAL)){
			var0.setAirSupply(var0.getMaxAirSupply());
		}
	}
	public static void damageBubblyCoral(LivingEntity var0) {
		if (var0.hasEffect(ModEffects.BUBBLY_CORAL)){
			float var1 = 4500F;
			float var2 = ((float)(((MobEffectInstance)(Objects.requireNonNull(var0.getEffect(ModEffects.BUBBLY_CORAL)))).getDuration())) / (var1);
			if ((Math.random()) > ((double)(var2))){
				var0.removeEffect(ModEffects.BUBBLY_CORAL);
			}else{
				return;
			}
		}
	}
}