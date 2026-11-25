package xyz.pixelatedw.mineminenomi.abilities.haki;

import java.awt.Color;
import java.util.Iterator;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.enums.HakiRank;
import xyz.pixelatedw.mineminenomi.api.enums.HakiType;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.config.ClientConfig;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.config.ServerConfig$HaoshokuUnlockLogic;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.data.entity.HakiData;
import xyz.pixelatedw.mineminenomi.data.entity.ProjectileData;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPBossEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;

public class HakiHelper {
	// Fields
	public static final float BASE_ADVANCED_DAMAGE = 2F;
	private static final ResourceLocation ADVANCED_BUSOSHOKU_HAKI_REACH_BONUS_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi","advanced_busoshoku_haki.reach");
	public static final ResourceLocation ADVANCED_BUSOSHOKU_HAKI_STRENGTH_BONUS_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi","advanced_busoshoku_haki.strength");
	public static final ResourceLocation BASIC_BUSOSHOKU_HAKI_STRENGTH_BONUS_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi","basic_busoshoku_haki.strength");

	@OnlyIn(value=Dist.CLIENT)
	public static int getConfigHaoshokuColour(LivingEntity var0) {
		if (ServerConfig.isHaoColoringCustom()){
			return ClientConfig.getHakiColor().getRGB();
		}else{
			if (!(ServerConfig.isHaoColoringRandom())){
				return 16711680;
			}else{
				if ((var0) == (null)){
					return 16711680;
				}else{
					Random var1 = new Random(var0.getUUID().getMostSignificantBits());
					int var2 = WyHelper.randomWithRange(var1,0,255);
					int var3 = WyHelper.randomWithRange(var1,0,255);
					int var4 = WyHelper.randomWithRange(var1,0,255);
					return new Color(var2,var3,var4).getRGB();
				}
			}
		}
	}
	public static int getHaoshokuColour(LivingEntity var0) {
		if ((var0) == (null)){
			return 16711680;
		}else{
			return ((Integer)(HakiData.get(var0).map((Function)(((Function<HakiData, Integer>)(HakiData::getHaoshokuHakiColour)))).orElse(Integer.valueOf(16711680)))).intValue();
		}
	}
	public static double getBasicBusoshokuHakiDamageBoost(LivingEntity var0, float var1) {
		double var2 = ((Double)(EntityStatsData.get(var0).map((Function)(((Function<EntityStatsData, Double>)(EntityStatsData::getDoriki)))).orElse(Double.valueOf(0D)))).doubleValue();
		float var3 = ((Float)(HakiData.get(var0).map((Function)(((Function<HakiData, Float>)(HakiData::getBusoshokuHakiExp)))).orElse(Float.valueOf(0F)))).floatValue();
		double var4 = (var2) / ((double)(ServerConfig.getDorikiLimit()));
		return (((double)(var1)) * (0.35D)) * (((0.2D) * (var4)) + ((0.8D) * ((double)((var3) / ((float)(ServerConfig.getHakiExpLimit()))))));
	}
	public static double getAdvancedBusoshokuHakiDamageBoost(LivingEntity var0, float var1) {
		double var2 = ((Double)(EntityStatsData.get(var0).map((Function)(((Function<EntityStatsData, Double>)(EntityStatsData::getDoriki)))).orElse(Double.valueOf(0D)))).doubleValue();
		float var3 = ((Float)(HakiData.get(var0).map((Function)(((Function<HakiData, Float>)(HakiData::getBusoshokuHakiExp)))).orElse(Float.valueOf(0F)))).floatValue();
		double var4 = (var2) / ((double)(ServerConfig.getDorikiLimit()));
		return (2D) + ((((double)(var1)) * (0.45D)) * (((0.2D) * (var4)) + ((0.8D) * ((double)((var3) / ((float)(ServerConfig.getHakiExpLimit())))))));
	}
	@Deprecated()
	public static AttributeModifier getAdvancedBusoshokuHakiAttackReachBonus(AbilityCore<?> var0, double var1) {
		return new AttributeModifier(ADVANCED_BUSOSHOKU_HAKI_REACH_BONUS_ID,var1,AttributeModifier.Operation.ADD_VALUE);
	}
	@Deprecated()
	public static AttributeModifier getAdvancedBusoshokuHakiAttackDamageBonus(AbilityCore<?> var0, double var1) {
		return new AttributeModifier(ADVANCED_BUSOSHOKU_HAKI_STRENGTH_BONUS_ID,var1,AttributeModifier.Operation.ADD_VALUE);
	}
	@Deprecated()
	public static AttributeModifier getBasicBusoshokuHakiAttackDamageBonus(AbilityCore<?> var0, double var1) {
		return new AttributeModifier(BASIC_BUSOSHOKU_HAKI_STRENGTH_BONUS_ID,var1,AttributeModifier.Operation.ADD_VALUE);
	}
	public static boolean isHaoshokuBorn(Player var0) {
		if (!(ServerConfig.isHaoshokuUnlockLogicChanceBased())){
			return false;
		}else{
			int var1 = 0;
			long var2 = var0.getUUID().getMostSignificantBits();
			String var3 = "" + var2;
			String[] var4 = var3.split("");
			int var5 = 0;
			String[] var6 = var4;
			int var7 = var6.length;
			int var8 = 0;
			do{
				if ((var8) < (var7)){
					String var9 = var6[var8];
					if (var9.equalsIgnoreCase("-")){

					}else{
						var5 = (var5) + (Integer.parseInt(var9));
					}
					var8++;
					continue;
				}else{
					break;
				}
			} while (true);
			var5 = Mth.clamp((var5) & (10),0,10);
			if ((ServerConfig.getHaoshokuUnlockLogic()) == (ServerConfig$HaoshokuUnlockLogic.TRUE_RANDOM)){
				String[] var9_1 = String.valueOf(((ServerLevel)(var0.level())).getSeed()).split("");
				var7 = 0;
				String[] var10 = var9_1;
				int var11 = var10.length;
				int var12 = 0;
				do{
					if ((var12) < (var11)){
						String var13 = var10[var12];
						if (var13.equalsIgnoreCase("-")){

						}else{
							var7 = (var7) + (Integer.parseInt(var13));
						}
						var12++;
						continue;
					}else{
						break;
					}
				} while (true);
				var7 = Mth.clamp((var7) & (10),0,10);
				var1 = ((var5) == (var7)) ? (1) : (0);
			}else{
				if (((ServerConfig.getHaoshokuUnlockLogic()) != (ServerConfig$HaoshokuUnlockLogic.RANDOM)) && ((ServerConfig.getHaoshokuUnlockLogic()) != (ServerConfig$HaoshokuUnlockLogic.COMBINED))){

				}else{
					var1 = ((var5) <= (1)) ? (1) : (0);
				}
			}
			return (((var1) & 1) != 0);
		}
	}
	public static double getBusoshokuHardeningExpNeeded(LivingEntity var0) {
		RandomSource var1 = var0.getRandom();
		var1.setSeed(var0.getUUID().getMostSignificantBits());
		return (double)((30) + (WyHelper.randomWithRange(var1,-2,25)));
	}
	public static double getBusoshokuFullBodyExpNeeded(LivingEntity var0) {
		RandomSource var1 = var0.getRandom();
		var1.setSeed(var0.getUUID().getMostSignificantBits());
		return (double)((50) + (WyHelper.randomWithRange(var1,0,20)));
	}
	public static double getBusoshokuEmissionExpNeeded(LivingEntity var0) {
		RandomSource var1 = var0.getRandom();
		var1.setSeed(var0.getUUID().getMostSignificantBits());
		return (double)((70) + (WyHelper.randomWithRange(var1,0,10)));
	}
	public static double getBusoshokuInternalDestructionExpNeeded(LivingEntity var0) {
		RandomSource var1 = var0.getRandom();
		var1.setSeed(var0.getUUID().getMostSignificantBits());
		return (double)((80) + (WyHelper.randomWithRange(var1,0,15)));
	}
	public static double getKenbunshokuAuraExpNeeded(LivingEntity var0) {
		RandomSource var1 = var0.getRandom();
		var1.setSeed(var0.getUUID().getMostSignificantBits());
		return (double)((30) + (WyHelper.randomWithRange(var1,-5,20)));
	}
	public static double getKenbunshokuFutureSightExpNeeded(LivingEntity var0) {
		RandomSource var1 = var0.getRandom();
		var1.setSeed(var0.getUUID().getMostSignificantBits());
		return (double)((50) + (WyHelper.randomWithRange(var1,0,30)));
	}
	public static HakiRank getHakiRank(HakiType var0, LivingEntity var1) {
		HakiRank var2 = HakiRank.values()[0];
		AbilityData var3 = ((AbilityData)(((java.util.Optional)(AbilityData.get(var1))).orElse((Object)(null))));
		if ((var3) == (null)){
			return var2;
		}else{
			HakiData var4 = ((HakiData)(((java.util.Optional)(HakiData.get(var1))).orElse((Object)(null))));
			if ((var4) == (null)){
				return var2;
			}else{
				float var5 = 0F;
				boolean var6 = false;
				if ((var0) == (HakiType.BUSOSHOKU)){
					var5 = var4.getBusoshokuHakiExp();
					var6 = (var3.hasUnlockedAbility(((AbilityCore)(BusoshokuHakiHardeningAbility.INSTANCE.get())))) || (var3.hasUnlockedAbility(((AbilityCore)(BusoshokuHakiImbuingAbility.INSTANCE.get()))));
				}else{
					if ((var0) == (HakiType.KENBUNSHOKU)){
						var5 = var4.getKenbunshokuHakiExp();
						var6 = var3.hasUnlockedAbility(((AbilityCore)(KenbunshokuHakiAuraAbility.INSTANCE.get())));
					}
				}
				float var7 = ((var5) / ((float)(ServerConfig.getHakiExpLimit()))) * (100F);
				if (((var7) >= (0F)) && ((var7) < (15F))){
					var2 = HakiRank.BEGINNER;
				}else{
					if (((var7) >= (15F)) && (var6)){
						if (((var7) >= (15F)) && ((var7) < (30F))){
							var2 = HakiRank.INITIATE;
						}else{
							if (((var7) >= (30F)) && ((var7) < (50F))){
								var2 = HakiRank.ADEPT;
							}else{
								if (((var7) >= (50F)) && ((var7) < (90F))){
									var2 = HakiRank.PROFICIENT;
								}else{
									if ((var7) >= (90F)){
										var2 = HakiRank.MASTER;
									}
								}
							}
						}
					}
				}
				return var2;
			}
		}
	}
	public static boolean hasAnyHakiEnabled(LivingEntity var0) {
		if ((var0) == (null)){
			return false;
		}else{
			AbilityData var1 = ((AbilityData)(((java.util.Optional)(AbilityData.get(var0))).orElse((Object)(null))));
			if ((var1) == (null)){
				return false;
			}else{
				boolean var2 = false;
				Iterator var3 = var1.getEquippedAbilities(AbilityCategory.HAKI.isAbilityPartofCategory()).iterator();
				do{
					if (var3.hasNext()){
						IAbility var4 = ((IAbility)(var3.next()));
						if ((var4) == (null)){
							continue;
						}else{
							Optional var5 = var4.getComponent(((AbilityComponentKey)(ModAbilityComponents.CONTINUOUS.get())));
							if ((var5.isPresent()) && (((ContinuousComponent)(var5.get())).isContinuous())){
								var2 = true;
								continue;
							}else{
								continue;
							}
						}
					}else{
						break;
					}
				} while (true);
				return var2;
			}
		}
	}
	public static boolean hasAdvancedBusoActive(LivingEntity var0) {
		return (hasBusoEmissionActive(var0)) || (hasBusoInternalDestructionActive(var0));
	}
	public static boolean hasBusoFullbodyActive(LivingEntity var0) {
		if ((var0) == (null)){
			return false;
		}else{
			Function<AbilityData, BusoshokuHakiFullBodyHardeningAbility> var1 = (l0) -> {
			return ((BusoshokuHakiFullBodyHardeningAbility)(l0.getEquippedAbility(((AbilityCore)(BusoshokuHakiFullBodyHardeningAbility.INSTANCE.get())))));
		};
			Function<BusoshokuHakiFullBodyHardeningAbility, Boolean> var2 = (l0) -> {
			return Boolean.valueOf(((l0) != (null)) && (l0.isContinuous()));
		};
			return ((Boolean)(((java.util.Optional)(((java.util.Optional)(AbilityData.get(var0))).map((Function)(var1)))).map((Function)(var2)).orElse(Boolean.valueOf(false)))).booleanValue();
		}
	}
	public static boolean hasBusoEmissionActive(LivingEntity var0) {
		if ((var0) == (null)){
			return false;
		}else{
			Function<AbilityData, BusoshokuHakiEmissionAbility> var1 = (l0) -> {
			return ((BusoshokuHakiEmissionAbility)(l0.getEquippedAbility(((AbilityCore)(BusoshokuHakiEmissionAbility.INSTANCE.get())))));
		};
			Function<BusoshokuHakiEmissionAbility, Boolean> var2 = (l0) -> {
			return Boolean.valueOf(((l0) != (null)) && (l0.isContinuous()));
		};
			return ((Boolean)(((java.util.Optional)(((java.util.Optional)(AbilityData.get(var0))).map((Function)(var1)))).map((Function)(var2)).orElse(Boolean.valueOf(false)))).booleanValue();
		}
	}
	public static boolean hasBusoInternalDestructionActive(LivingEntity var0) {
		if ((var0) == (null)){
			return false;
		}else{
			Function<AbilityData, BusoshokuHakiInternalDestructionAbility> var1 = (l0) -> {
			return ((BusoshokuHakiInternalDestructionAbility)(l0.getEquippedAbility(((AbilityCore)(BusoshokuHakiInternalDestructionAbility.INSTANCE.get())))));
		};
			Function<BusoshokuHakiInternalDestructionAbility, Boolean> var2 = (l0) -> {
			return Boolean.valueOf(((l0) != (null)) && (l0.isContinuous()));
		};
			return ((Boolean)(((java.util.Optional)(((java.util.Optional)(AbilityData.get(var0))).map((Function)(var1)))).map((Function)(var2)).orElse(Boolean.valueOf(false)))).booleanValue();
		}
	}
	public static boolean hasInfusionActive(LivingEntity var0) {
		if ((var0) == (null)){
			return false;
		}else{
			Function<AbilityData, HaoshokuHakiInfusionAbility> var1 = (l0) -> {
			return ((HaoshokuHakiInfusionAbility)(l0.getEquippedAbility(((AbilityCore)(HaoshokuHakiInfusionAbility.INSTANCE.get())))));
		};
			Function<HaoshokuHakiInfusionAbility, Boolean> var2 = (l0) -> {
			return Boolean.valueOf(((l0) != (null)) && (l0.isContinuous()));
		};
			return ((Boolean)(((java.util.Optional)(((java.util.Optional)(AbilityData.get(var0))).map((Function)(var1)))).map((Function)(var2)).orElse(Boolean.valueOf(false)))).booleanValue();
		}
	}
	public static boolean hasHardeningActive(LivingEntity var0) {
		return hasHardeningActive(var0,true,true);
	}
	public static boolean hasHardeningActive(LivingEntity var0, boolean var1, boolean var2) {
		if ((var0) == (null)){
			return false;
		}else{
			AbilityData var3 = ((AbilityData)(((java.util.Optional)(AbilityData.get(var0))).orElse((Object)(null))));
			if ((var3) == (null)){
				return false;
			}else{
				int var4 = 0;
				BusoshokuHakiHardeningAbility var5 = ((BusoshokuHakiHardeningAbility)(var3.getEquippedAbility(((AbilityCore)(BusoshokuHakiHardeningAbility.INSTANCE.get())))));
				int var6 = (((var5) != (null)) && (var5.isContinuous())) ? (1) : (0);
				var4 = (var4) | (((var5) != (null)) ? (1) : (0));
				if ((var6) != (0)){
					return true;
				}else{
					BusoshokuHakiFullBodyHardeningAbility var7 = ((BusoshokuHakiFullBodyHardeningAbility)(var3.getEquippedAbility(((AbilityCore)(BusoshokuHakiFullBodyHardeningAbility.INSTANCE.get())))));
					int var8 = (((var7) != (null)) && (var7.isContinuous())) ? (1) : (0);
					var4 = (var4) | (((var7) != (null)) ? (1) : (0));
					if ((var8) != (0)){
						return true;
					}else{
						if (var1){
							HaoshokuHakiInfusionAbility var9 = ((HaoshokuHakiInfusionAbility)(var3.getEquippedAbility(((AbilityCore)(HaoshokuHakiInfusionAbility.INSTANCE.get())))));
							int var10 = (((var9) != (null)) && (var9.isContinuous())) ? (1) : (0);
							var4 = (var4) | (((var9) != (null)) ? (1) : (0));
							if ((var10) != (0)){
								return true;
							}
						}
						if (var2){
							BusoshokuHakiEmissionAbility var9_1 = ((BusoshokuHakiEmissionAbility)(var3.getEquippedAbility(((AbilityCore)(BusoshokuHakiEmissionAbility.INSTANCE.get())))));
							boolean var10_1 = ((var9_1) != (null)) && (var9_1.isContinuous());
							var4 = (var4) | (((var9_1) != (null)) ? (1) : (0));
							if (var10_1){
								return true;
							}else{
								BusoshokuHakiInternalDestructionAbility var11 = ((BusoshokuHakiInternalDestructionAbility)(var3.getEquippedAbility(((AbilityCore)(BusoshokuHakiInternalDestructionAbility.INSTANCE.get())))));
								int var12 = (((var11) != (null)) && (var11.isContinuous())) ? (1) : (0);
								var4 = (var4) | (((var11) != (null)) ? (1) : (0));
								if ((var12) != (0)){
									return true;
								}
							}
						}
						if ((var0 instanceof Mob) && ((!(var0 instanceof OPEntity)) && ((!(var0 instanceof OPBossEntity)) && (((var4) == (0)) && (hasNativeHaki(var0)))))){
							return true;
						}else{
							return false;
						}
					}
				}
			}
		}
	}
	public static boolean hasImbuingActive(LivingEntity var0) {
		return hasImbuingActive(var0,true,true);
	}
	public static boolean hasImbuingActive(LivingEntity var0, boolean var1, boolean var2) {
		if ((var0) == (null)){
			return false;
		}else{
			AbilityData var3 = ((AbilityData)(((java.util.Optional)(AbilityData.get(var0))).orElse((Object)(null))));
			if ((var3) == (null)){
				return false;
			}else{
				int var4 = 0;
				BusoshokuHakiImbuingAbility var5 = ((BusoshokuHakiImbuingAbility)(var3.getEquippedAbility(((AbilityCore)(BusoshokuHakiImbuingAbility.INSTANCE.get())))));
				int var6 = (((var5) != (null)) && (var5.isContinuous())) ? (1) : (0);
				var4 = (var4) | (((var5) != (null)) ? (1) : (0));
				if ((var6) != (0)){
					return true;
				}else{
					if (var1){
						HaoshokuHakiInfusionAbility var7 = ((HaoshokuHakiInfusionAbility)(var3.getEquippedAbility(((AbilityCore)(HaoshokuHakiInfusionAbility.INSTANCE.get())))));
						int var8 = (((var7) != (null)) && (var7.isContinuous())) ? (1) : (0);
						var4 = (var4) | (((var7) != (null)) ? (1) : (0));
						if ((var8) != (0)){
							return true;
						}
					}
					if (var2){
						BusoshokuHakiEmissionAbility var7_1 = ((BusoshokuHakiEmissionAbility)(var3.getEquippedAbility(((AbilityCore)(BusoshokuHakiEmissionAbility.INSTANCE.get())))));
						boolean var8_1 = ((var7_1) != (null)) && (var7_1.isContinuous());
						var4 = (var4) | (((var7_1) != (null)) ? (1) : (0));
						if (var8_1){
							return true;
						}else{
							BusoshokuHakiInternalDestructionAbility var9 = ((BusoshokuHakiInternalDestructionAbility)(var3.getEquippedAbility(((AbilityCore)(BusoshokuHakiInternalDestructionAbility.INSTANCE.get())))));
							int var10 = (((var9) != (null)) && (var9.isContinuous())) ? (1) : (0);
							var4 = (var4) | (((var9) != (null)) ? (1) : (0));
							if ((var10) != (0)){
								return true;
							}
						}
					}
					if ((var0 instanceof Mob) && ((!(var0 instanceof OPEntity)) && (!(var0 instanceof OPBossEntity)))){
						if (((var0.getMainHandItem()) != (null)) && ((!(var0.getMainHandItem().isEmpty())) && (ItemsHelper.isImbuable(var0.getMainHandItem())))){
							if (((var4) == (0)) && (hasNativeHaki(var0))){
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
			}
		}
	}
	public static boolean hasNativeHaki(LivingEntity var0) {
		AttributeInstance var1 = var0.getAttributes().getInstance(Attributes.ATTACK_DAMAGE);
		double var2 = ((var1) != (null)) ? (var1.getBaseValue()) : (0D);
		Function<EntityStatsData, Boolean> var3 = (l0) -> {
			return Boolean.valueOf((l0.getDoriki()) >= (5000D));
		};
		boolean var4 = ((Boolean)(((java.util.Optional)(EntityStatsData.get(var0))).map((Function)(var3)).orElse(Boolean.valueOf(false)))).booleanValue();
		return (((((var2) >= ((double)(((var0.level().getDifficulty()) == (Difficulty.HARD)) ? (5) : (7)))) && (ServerConfig.isNativeHakiEnabled())) ? (1) : (0)) != (0)) || (var4);
	}
	public static boolean checkForHakiOveruse(LivingEntity var0, int var1) {
		if (var0.level().isClientSide){
			return false;
		}else{
			int var2 = var1;
			final int var2_f1 = var2;
			Consumer<HakiData> var3 = (l0) -> {
			if ((var2_f1) != (-1)){
				l0.alterHakiOveruse((1) + (var2_f1));
			}
		};
			((java.util.Optional)(HakiData.get(var0))).ifPresent((Consumer)(var3));
			return !(canEnableHaki(var0));
		}
	}
	public static boolean canEnableHaki(LivingEntity var0) {
		HakiData var1 = ((HakiData)(((java.util.Optional)(HakiData.get(var0))).orElse((Object)(null))));
		if ((var1) == (null)){
			return false;
		}else{
			return (var1.getHakiOveruse()) < (var1.getMaxOveruse());
		}
	}
	public static boolean isProjectileBusoshokuImbued(Projectile var0) {
		return ((Boolean)(ProjectileData.get(var0).map((Function)(((Function<ProjectileData, Boolean>)(ProjectileData::isProjectileBusoshokuImbued)))).orElse(Boolean.valueOf(false)))).booleanValue();
	}
	public static boolean isProjectileBusoshokuShrouded(Projectile var0) {
		return ((Boolean)(ProjectileData.get(var0).map((Function)(((Function<ProjectileData, Boolean>)(ProjectileData::isProjectileBusoshokuShrouded)))).orElse(Boolean.valueOf(false)))).booleanValue();
	}
	public static boolean isProjectileHaoshokuInfused(Projectile var0) {
		return ((Boolean)(ProjectileData.get(var0).map((Function)(((Function<ProjectileData, Boolean>)(ProjectileData::isProjectileHaoshokuInfused)))).orElse(Boolean.valueOf(false)))).booleanValue();
	}
}