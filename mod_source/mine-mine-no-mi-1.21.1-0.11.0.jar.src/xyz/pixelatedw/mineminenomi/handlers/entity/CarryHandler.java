package xyz.pixelatedw.mineminenomi.handlers.entity;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.api.effects.IBindHandsEffect;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.data.entity.CarryData;
import xyz.pixelatedw.mineminenomi.data.entity.DevilFruitData;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.effects.CaughtInNetEffect;
import xyz.pixelatedw.mineminenomi.effects.HandcuffedEffect;
import xyz.pixelatedw.mineminenomi.handlers.ability.OutOfBodyHandler;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModEntityPredicates;

public class CarryHandler {
	public static boolean tryStopLeashing(Player var0) {
		CarryData var1 = ((CarryData)(((java.util.Optional)(CarryData.get((LivingEntity)(var0)))).orElse((Object)(null))));
		if (((var1) != (null)) && (var1.isLeashed())){
			var1.dropLeash();
			return true;
		}else{
			return false;
		}
	}
	public static boolean tryDropCarried(Player var0) {
		CarryData var1 = ((CarryData)(((java.util.Optional)(CarryData.get((LivingEntity)(var0)))).orElse((Object)(null))));
		if ((var1) == (null)){
			return false;
		}else{
			if (var1.isCarrying()){
				LivingEntity var2 = var1.getCarry();
				var1.stopCarrying();
				if ((var2) != (null)){
					Vec3 var3 = var0.getEyePosition().add(var0.getLookAngle());
					var2.setPos(var3.x(),var3.y(),var3.z());
					AbilityHelper.setDeltaMovement((Entity)(var2),var0.getLookAngle().multiply(0.5D,1D,0.5D));
					return true;
				}else{
					return false;
				}
			}else{
				return false;
			}
		}
	}
	public static void moveCarriedTarget(Player var0) {
		CarryData var1 = ((CarryData)(((java.util.Optional)(CarryData.get((LivingEntity)(var0)))).orElse((Object)(null))));
		if ((var1) == (null)){
			return;
		}else{
			if (var1.isCarrying()){
				LivingEntity var2 = var1.getCarry();
				if (((var2) != (null)) && ((var2.isAlive()) && (canCarry((LivingEntity)(var0),var2)))){
					var0.addEffect(new MobEffectInstance((Holder)(ModEffects.CARRYING),40,0));
					AbilityHelper.setDeltaMovement((Entity)(var2),Vec3.ZERO);
					var2.teleportTo(var0.getX(),(var0.getEyeY()) + (0.75D),var0.getZ());
					var2.fallDistance = 0F;
				}else{
					var1.stopCarrying();
					return;
				}
			}
			return;
		}
	}
	public static void dragLeashedTarget(Player var0) {
		CarryData var1 = ((CarryData)(((java.util.Optional)(CarryData.get((LivingEntity)(var0)))).orElse((Object)(null))));
		if ((var1) == (null)){
			return;
		}else{
			if (var1.isLeashed()){
				boolean var2 = false;
				Iterator var3 = var0.getActiveEffects().iterator();
				do{
					if (var3.hasNext()){
						if (((MobEffectInstance)(var3.next())).getEffect() instanceof HandcuffedEffect){
							var2 = true;
							break;
						}else{
							continue;
						}
					}else{
						break;
					}
				} while (true);
				if (!(var2)){
					var1.dropLeash();
					return;
				}else{
					LivingEntity var4 = var1.getLeashHolder();
					if ((var4) != (null)){
						float var5 = var0.distanceTo((Entity)(var4));
						if ((var5) > (6F)){
							double var6 = ((var4.getX()) - (var0.getX())) / ((double)(var5));
							double var7 = ((var4.getY()) - (var0.getY())) / ((double)(var5));
							double var8 = ((var4.getZ()) - (var0.getZ())) / ((double)(var5));
							if (!(var0.onGround())){
								var6 = (var6) / (1.75D);
								var7 = (var7) / (1.75D);
								var8 = (var8) / (1.75D);
							}
							AbilityHelper.setDeltaMovement((Entity)(var0),var6,var7,var8);
						}else{
							return;
						}
					}else{
						return;
					}
				}
			}
			return;
		}
	}
	public static boolean tryLeashTarget(Player var0, Player var1) {
		CarryData var2 = ((CarryData)(((java.util.Optional)(CarryData.get((LivingEntity)(var0)))).orElse((Object)(null))));
		CarryData var3 = ((CarryData)(((java.util.Optional)(CarryData.get((LivingEntity)(var1)))).orElse((Object)(null))));
		if (((var2) != (null)) && ((var3) != (null))){
			ItemStack var4 = var0.getMainHandItem();
			if ((!(var4.isEmpty())) && (((var4.getItem()) == (Items.LEAD)) && (var3.canBeLeashed((LivingEntity)(var0))))){
				boolean var5 = false;
				Iterator var6 = var1.getActiveEffects().iterator();
				do{
					if (var6.hasNext()){
						if (((MobEffectInstance)(var6.next())).getEffect() instanceof HandcuffedEffect){
							var5 = true;
							break;
						}else{
							continue;
						}
					}else{
						break;
					}
				} while (true);
				if (var5){
					var3.setLeashedTo((LivingEntity)(var0));
					var4.shrink(1);
					return true;
				}else{
					return false;
				}
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	public static boolean tryPickupTarget(Player var0, LivingEntity var1) {
		CarryData var2 = ((CarryData)(((java.util.Optional)(CarryData.get((LivingEntity)(var0)))).orElse((Object)(null))));
		CarryData var3 = ((CarryData)(((java.util.Optional)(CarryData.get(var1))).orElse((Object)(null))));
		if (((var2) != (null)) && ((var3) != (null))){
			if ((var1 instanceof Player) && ((var3.isLeashed()) && (var3.getLeashHolder().equals(var0)))){
				var3.dropLeash();
				return true;
			}else{
				if ((!(var2.isCarrying())) && (canCarry((LivingEntity)(var0),var1))){
					var2.startCarrying(var1);
					return true;
				}else{
					return false;
				}
			}
		}else{
			return false;
		}
	}
	private static boolean canCarry(LivingEntity var0, LivingEntity var1) {
		if (((var0) != (null)) && ((var1) != (null))){
			Iterator var2 = var0.getActiveEffects().iterator();
			do{
				if (var2.hasNext()){
					MobEffect var3 = ((MobEffect)(((MobEffectInstance)(var2.next())).getEffect().value()));
					if ((var3 instanceof IBindHandsEffect) && (((IBindHandsEffect)(var3)).isBlockingSwings())){
						return false;
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
			if (OutOfBodyHandler.getOOBAbility(var0).isPresent()){
				return false;
			}else{
				MorphInfo var3_1 = ((MorphInfo)(DevilFruitData.get(var1).flatMap((Function)(((Function<DevilFruitData, Optional>)(DevilFruitData::getCurrentMorph)))).orElse(null)));
				if (((var3_1) != (null)) && (!(var3_1.canMount()))){
					return false;
				}else{
					CarryData var4 = ((CarryData)(((java.util.Optional)(CarryData.get(var0))).orElse((Object)(null))));
					if ((var4) == (null)){
						return false;
					}else{
						if ((var4.isCarrying()) && (var4.getCarry().equals(var1))){
							if (var0.hasEffect((Holder)(ModEffects.DOA_INVISIBILITY))){
								return true;
							}
						}else{
							if ((!(var1.hasEffect((Holder)(ModEffects.DOA_INVISIBILITY)))) && (var0.hasEffect((Holder)(ModEffects.DOA_INVISIBILITY)))){
								return false;
							}
						}
						boolean var5 = ((Boolean)(DevilFruitData.get(var1).map((Function)(((Function<DevilFruitData, Boolean>)(DevilFruitData::hasAnyDevilFruit)))).orElse(Boolean.valueOf(false)))).booleanValue();
						boolean var6 = ((Boolean)(EntityStatsData.get(var1).map((Function)(((Function<EntityStatsData, Boolean>)(EntityStatsData::isRogue)))).orElse(Boolean.valueOf(false)))).booleanValue();
						Iterator var7 = var1.getActiveEffects().iterator();
						do{
							if (var7.hasNext()){
								MobEffect var8 = ((MobEffect)(((MobEffectInstance)(var7.next())).getEffect().value()));
								if (((!(((java.util.function.Predicate)(ModEntityPredicates.getFriendlyFactions(var0))).test((Object)(var1)))) && (!(var6))) || (!((var5) && (((java.lang.Object)(var8)).equals(ModEffects.WATER_WEAKNESS.get()))))){
									if (var8 instanceof CaughtInNetEffect){
										return true;
									}else{
										continue;
									}
								}else{
									return true;
								}
							}else{
								break;
							}
						} while (true);
						MobEffectInstance var8_1 = var1.getEffect((Holder)(ModEffects.UNCONSCIOUS));
						if (((var8_1) != (null)) && ((var8_1.getAmplifier()) > (0))){
							return true;
						}else{
							return false;
						}
					}
				}
			}
		}else{
			return false;
		}
	}
	public static boolean isCarrying(LivingEntity var0, LivingEntity var1) {
		CarryData var2 = ((CarryData)(((java.util.Optional)(CarryData.get(var0))).orElse((Object)(null))));
		if ((var2) == (null)){
			return false;
		}else{
			if (!(var2.isCarrying())){
				return false;
			}else{
				if (((var2.getCarry()) != (null)) && ((var2.getCarry()) == (var1))){
					if (!(canCarry(var0,var1))){
						return false;
					}else{
						return true;
					}
				}else{
					return false;
				}
			}
		}
	}
}