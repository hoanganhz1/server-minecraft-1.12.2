package xyz.pixelatedw.mineminenomi.handlers.entity;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent$DamageState;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.api.quests.components.CountdownQuestComponent;
import xyz.pixelatedw.mineminenomi.api.quests.components.DamageTakenQuestComponent;
import xyz.pixelatedw.mineminenomi.api.quests.components.HitTriggerQuestComponent;
import xyz.pixelatedw.mineminenomi.api.quests.components.KillTriggerQuestComponent;
import xyz.pixelatedw.mineminenomi.api.quests.components.ProjectileQuestComponent;
import xyz.pixelatedw.mineminenomi.api.quests.components.QuestComponent;
import xyz.pixelatedw.mineminenomi.api.quests.components.QuestComponentIndex;
import xyz.pixelatedw.mineminenomi.api.quests.components.SwingTriggerQuestComponent;
import xyz.pixelatedw.mineminenomi.api.quests.components.UseAbilityQuestComponent;
import xyz.pixelatedw.mineminenomi.api.quests.components.UseItemQuestComponent;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.ICollectItemObjective;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.ICureEffectObjective;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.IEntityInteractObjective;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.IEquipItemObjective;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.IHealEntityObjective;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.IReachDorikiObjective;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.ISurviveObjective;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;
import xyz.pixelatedw.mineminenomi.data.entity.QuestData;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncQuestDataPacket;
import xyz.pixelatedw.mineminenomi.quests.objectives.TameEntityObjective;

public class QuestsHandler {
	public static void handleTickableObjective(Player var0) {
		QuestData var1 = ((QuestData)(((java.util.Optional)(QuestData.get(var0))).orElse((Object)(null))));
		if ((var1) == (null)){
			return;
		}else{
			Iterator var2 = var1.getComponentIndex().getComponentsByType(CountdownQuestComponent.class).iterator();
			do{
				if (var2.hasNext()){
					((CountdownQuestComponent)(var2.next())).tick(var0);
					continue;
				}else{
					break;
				}
			} while (true);
			return;
		}
	}
	public static void handleTameObjective(Player var0) {
		QuestData var1 = ((QuestData)(((java.util.Optional)(QuestData.get(var0))).orElse((Object)(null))));
		if ((var1) == (null)){
			return;
		}else{
			Iterator var2 = var1.getInProgressObjectives().iterator();
			do{
				if (var2.hasNext()){
					Objective var3 = ((Objective)(var2.next()));
					if (var3 instanceof TameEntityObjective){
						var3.alterProgress(var0,1F);
						ModNetwork.sendTo(new SSyncQuestDataPacket(var0),var0);
						continue;
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
			return;
		}
	}
	public static void handleCureEffectObjective(Player var0, MobEffectInstance var1) {
		QuestData var2 = ((QuestData)(((java.util.Optional)(QuestData.get(var0))).orElse((Object)(null))));
		if ((var2) == (null)){
			return;
		}else{
			Iterator var3 = var2.getInProgressObjectives().iterator();
			do{
				if (var3.hasNext()){
					Objective var4 = ((Objective)(var3.next()));
					if ((var4 instanceof ICureEffectObjective) && (((ICureEffectObjective)(var4)).checkEffect(var0,var1))){
						var4.alterProgress(var0,1F);
						ModNetwork.sendTo(new SSyncQuestDataPacket(var0),var0);
						continue;
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
			return;
		}
	}
	public static void handleReachDorikiObjective(Player var0, double var1) {
		QuestData var2 = ((QuestData)(((java.util.Optional)(QuestData.get(var0))).orElse((Object)(null))));
		if ((var2) == (null)){
			return;
		}else{
			Iterator var3 = var2.getInProgressObjectives().iterator();
			do{
				if (var3.hasNext()){
					Objective var4 = ((Objective)(var3.next()));
					if ((var4 instanceof IReachDorikiObjective) && (((IReachDorikiObjective)(var4)).checkDoriki(var0))){
						var4.setProgress(var0,(float)(var1),true);
						ModNetwork.sendTo(new SSyncQuestDataPacket(var0),var0);
						continue;
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
			return;
		}
	}
	public static void handleEntityInteractionObjective(Player var0, LivingEntity var1) {
		QuestData var2 = ((QuestData)(((java.util.Optional)(QuestData.get(var0))).orElse((Object)(null))));
		if ((var2) == (null)){
			return;
		}else{
			Iterator var3 = var2.getInProgressObjectives().iterator();
			do{
				if (var3.hasNext()){
					Objective var4 = ((Objective)(var3.next()));
					if ((var4 instanceof IEntityInteractObjective) && (((IEntityInteractObjective)(var4)).checkInteraction(var0,(Entity)(var1)))){
						var4.alterProgress(var0,1F);
						ModNetwork.sendTo(new SSyncQuestDataPacket(var0),var0);
						return;
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
			return;
		}
	}
	public static void handleRemainingCollectedItems(Player var0) {
		QuestData var1 = ((QuestData)(((java.util.Optional)(QuestData.get(var0))).orElse((Object)(null))));
		if ((var1) == (null)){
			return;
		}else{
			Iterator var2 = var1.getInProgressObjectives().iterator();
			LOOP_1:
			do{
				if (var2.hasNext()){
					Objective var3 = ((Objective)(var2.next()));
					if (var3 instanceof ICollectItemObjective){
						ICollectItemObjective var4 = ((ICollectItemObjective)(var3));
						if ((var3.getProgress()) > (0F)){
							boolean var5 = true;
							Iterator var6 = ItemsHelper.getAllInventoryItems((LivingEntity)(var0)).iterator();
							do{
								if (var6.hasNext()){
									ItemStack var7 = ((ItemStack)(var6.next()));
									if ((var7.isEmpty()) || (!(var4.checkItem(var7)))){
										continue;
									}else{
										var3.setProgress(var0,(float)(var7.getCount()),true);
										ModNetwork.sendTo(new SSyncQuestDataPacket(var0),var0);
										var5 = false;
										continue;
									}
								}else{
									break;
								}
							} while (true);
							if (var5){
								var3.setProgress(var0,0F,true);
								ModNetwork.sendTo(new SSyncQuestDataPacket(var0),var0);
								continue LOOP_1;
							}else{
								continue LOOP_1;
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
			return;
		}
	}
	public static void handleCollectItemObjective(Player var0, ItemStack var1, int var2) {
		QuestData var3 = ((QuestData)(((java.util.Optional)(QuestData.get(var0))).orElse((Object)(null))));
		if ((var3) == (null)){
			return;
		}else{
			Iterator var4 = var3.getInProgressObjectives().iterator();
			do{
				if (var4.hasNext()){
					Objective var5 = ((Objective)(var4.next()));
					if ((var5 instanceof ICollectItemObjective) && (((ICollectItemObjective)(var5)).checkItem(var1))){
						var5.alterProgress(var0,(float)(var2),true);
						ModNetwork.sendTo(new SSyncQuestDataPacket(var0),var0);
						continue;
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
			return;
		}
	}
	public static void handleHealObjective(Player var0, LivingEntity var1, float var2) {
		QuestData var3 = ((QuestData)(((java.util.Optional)(QuestData.get(var0))).orElse((Object)(null))));
		if ((var3) == (null)){
			return;
		}else{
			Iterator var4 = var3.getInProgressObjectives().iterator();
			do{
				if (var4.hasNext()){
					Objective var5 = ((Objective)(var4.next()));
					if ((var5 instanceof IHealEntityObjective) && (((IHealEntityObjective)(var5)).checkHeal(var0,var1,var2))){
						var5.alterProgress(var0,1F);
						ModNetwork.sendTo(new SSyncQuestDataPacket(var0),var0);
						continue;
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
			return;
		}
	}
	public static void handleHitObjective(LivingEntity var0, LivingEntity var1, DamageSource var2, float var3) {
		if (var0 instanceof Player){
			Player var4 = ((Player)(var0));
			QuestData var5 = ((QuestData)(((java.util.Optional)(QuestData.get(var4))).orElse((Object)(null))));
			if ((var5) == (null)){
				return;
			}else{
				Iterator var6 = var5.getComponentIndex().getComponentsByType(HitTriggerQuestComponent.class).iterator();
				do{
					if (var6.hasNext()){
						((HitTriggerQuestComponent)(var6.next())).onHit(var4,var1,var2,var3);
						continue;
					}else{
						break;
					}
				} while (true);
				return;
			}
		}else{
			return;
		}
	}
	public static void handleDamageTakenObjective(LivingEntity var0, DamageSource var1, float var2, DamageTakenComponent$DamageState var3) {
		if (var0 instanceof Player){
			Player var4 = ((Player)(var0));
			QuestData var5 = ((QuestData)(((java.util.Optional)(QuestData.get(var4))).orElse((Object)(null))));
			if ((var5) == (null)){
				return;
			}else{
				Iterator var6 = var5.getComponentIndex().getComponentsByType(DamageTakenQuestComponent.class).iterator();
				do{
					if (var6.hasNext()){
						((DamageTakenQuestComponent)(var6.next())).checkDamageTaken(var4,var1,var2,var3);
						continue;
					}else{
						break;
					}
				} while (true);
				return;
			}
		}else{
			return;
		}
	}
	public static void handleProjectileShotObjective(Projectile var0) {
		if ((var0.getOwner()) != (null)){
			Entity var1 = var0.getOwner();
			if (var1 instanceof Player){
				Player var2 = ((Player)(var1));
				QuestData var3 = ((QuestData)(((java.util.Optional)(QuestData.get(var2))).orElse((Object)(null))));
				if ((var3) == (null)){
					return;
				}else{
					Iterator var4 = var3.getComponentIndex().getComponentsByType(ProjectileQuestComponent.class).iterator();
					do{
						if (var4.hasNext()){
							((ProjectileQuestComponent)(var4.next())).projectileShot(var2,var0);
							continue;
						}else{
							break;
						}
					} while (true);
					return;
				}
			}else{
				return;
			}
		}else{
			return;
		}
	}
	public static void handleProjectileDespawnObjective(Projectile var0) {
		if ((var0.getOwner()) != (null)){
			Entity var1 = var0.getOwner();
			if (var1 instanceof Player){
				Player var2 = ((Player)(var1));
				QuestData var3 = ((QuestData)(((java.util.Optional)(QuestData.get(var2))).orElse((Object)(null))));
				if ((var3) == (null)){
					return;
				}else{
					Iterator var4 = var3.getComponentIndex().getComponentsByType(ProjectileQuestComponent.class).iterator();
					do{
						if (var4.hasNext()){
							((ProjectileQuestComponent)(var4.next())).projectileDespawn(var2,var0);
							continue;
						}else{
							break;
						}
					} while (true);
					return;
				}
			}else{
				return;
			}
		}else{
			return;
		}
	}
	public static void handleKillObjective(LivingEntity var0, DamageSource var1) {
		Entity var2 = var1.getEntity();
		if (var2 instanceof Player){
			Player var3 = ((Player)(var2));
			QuestData var4 = ((QuestData)(((java.util.Optional)(QuestData.get(var3))).orElse((Object)(null))));
			if ((var4) == (null)){
				return;
			}else{
				Iterator var5 = var4.getComponentIndex().getComponentsByType(KillTriggerQuestComponent.class).iterator();
				do{
					if (var5.hasNext()){
						((KillTriggerQuestComponent)(var5.next())).checkKill(var3,var0,var1);
						continue;
					}else{
						break;
					}
				} while (true);
				return;
			}
		}else{
			return;
		}
	}
	public static void handleEquipmentObjective(Player var0, EquipmentSlot var1, ItemStack var2) {
		QuestData var3 = ((QuestData)(((java.util.Optional)(QuestData.get(var0))).orElse((Object)(null))));
		if ((var3) == (null)){
			return;
		}else{
			Iterator var4 = var3.getInProgressObjectives().iterator();
			do{
				if (var4.hasNext()){
					Objective var5 = ((Objective)(var4.next()));
					if (var5 instanceof IEquipItemObjective){
						IEquipItemObjective var6 = ((IEquipItemObjective)(var5));
						if (var6.checkSlot(var1)){
							if (var6.checkEquippedItem(var0,var2)){
								var5.alterProgress(var0,1F);
								ModNetwork.sendTo(new SSyncQuestDataPacket(var0),var0);
								continue;
							}else{
								var5.alterProgress(var0,-1F,true);
								ModNetwork.sendTo(new SSyncQuestDataPacket(var0),var0);
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
			return;
		}
	}
	public static void handleSurvivalObjective(Player var0) {
		QuestData var1 = ((QuestData)(((java.util.Optional)(QuestData.get(var0))).orElse((Object)(null))));
		if ((var1) == (null)){
			return;
		}else{
			Iterator var2 = var1.getInProgressObjectives().iterator();
			do{
				if (var2.hasNext()){
					Objective var3 = ((Objective)(var2.next()));
					if ((var3 instanceof ISurviveObjective) && (((ISurviveObjective)(var3)).checkTime(var0))){
						var3.alterProgress(var0,1F);
						ModNetwork.sendTo(new SSyncQuestDataPacket(var0),var0);
						continue;
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
			return;
		}
	}
	public static void handleUseItemObjective(Player var0, ItemStack var1, int var2) {
		QuestData var3 = ((QuestData)(((java.util.Optional)(QuestData.get(var0))).orElse((Object)(null))));
		if ((var3) == (null)){
			return;
		}else{
			Iterator var4 = var3.getComponentIndex().getComponentsByType(UseItemQuestComponent.class).iterator();
			do{
				if (var4.hasNext()){
					((UseItemQuestComponent)(var4.next())).useItem(var0,var1,var2);
					continue;
				}else{
					break;
				}
			} while (true);
			return;
		}
	}
	public static void handleUseAbilityObjective(Player var0, IAbility var1) {
		QuestData var2 = ((QuestData)(((java.util.Optional)(QuestData.get(var0))).orElse((Object)(null))));
		if ((var2) == (null)){
			return;
		}else{
			Iterator var3 = var2.getComponentIndex().getComponentsByType(UseAbilityQuestComponent.class).iterator();
			do{
				if (var3.hasNext()){
					((UseAbilityQuestComponent)(var3.next())).useAbility(var0,var1);
					continue;
				}else{
					break;
				}
			} while (true);
			return;
		}
	}
	public static void handleSwingObjective(Player var0) {
		QuestData var1 = ((QuestData)(((java.util.Optional)(QuestData.get(var0))).orElse((Object)(null))));
		if ((var1) == (null)){
			return;
		}else{
			Iterator var2 = var1.getComponentIndex().getComponentsByType(SwingTriggerQuestComponent.class).iterator();
			do{
				if (var2.hasNext()){
					((SwingTriggerQuestComponent)(var2.next())).swing((LivingEntity)(var0));
					continue;
				}else{
					break;
				}
			} while (true);
			return;
		}
	}
}