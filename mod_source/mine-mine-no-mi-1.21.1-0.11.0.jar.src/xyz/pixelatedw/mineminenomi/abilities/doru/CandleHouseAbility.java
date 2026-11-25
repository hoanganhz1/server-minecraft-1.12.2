package xyz.pixelatedw.mineminenomi.abilities.doru;

import java.util.Optional;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class CandleHouseAbility extends Ability {
	// Fields
	private static final float COOLDOWN = 1000F;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<CandleHouseAbility>> INSTANCE;
	private static final BlockProtectionRule GRIEF_RULE;

	public CandleHouseAbility(AbilityCore<CandleHouseAbility> var1) {
		super((AbilityCore)(var1));
		this.addUseEvent(100,(Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv2_2 = new Pair[]{ImmutablePair.of((Object)("Creates a big house-like structure made of wax, to protect the user"),(Object)(null))};
			Component[] lv2_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv2_2);
			AbilityDescriptionLine$IDescriptionLine[] lv2_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(1000F)};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(CandleHouseAbility::new)))).addDescriptionLine(lv2_3).addAdvancedDescriptionLine(lv2_4).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("candle_house","Candle House",var0));
		GRIEF_RULE = DefaultProtectionRules.AIR_FOLIAGE;
	}

	private void useEvent(LivingEntity var1, IAbility var2) {
		Level var3 = var1.level();
		if (((var3) != (null)) && (!(var3.isClientSide))){
			int var8 = 0;
			int var4 = 1;
			Function<AbilityData, CandleChampionAbility> var5 = (l0) -> {
			return ((CandleChampionAbility)(l0.getEquippedAbility(((AbilityCore)(CandleChampionAbility.INSTANCE.get())))));
		};
			if (((Boolean)(((java.util.Optional)(AbilityData.get(var1))).map((Function)(var5)).map((Function)(((Function<CandleChampionAbility, Boolean>)(Ability::isContinuous)))).orElse(Boolean.valueOf(false)))).booleanValue()){
				var4 = 3;
			}
			BlockState var6 = ((Block)(ModBlocks.WAX.get())).defaultBlockState();
			int var7 = -3;
			LOOP_5:
			do{
				if ((var7) <= (3)){
					int var9 = 0;
					var8 = 0;
					LOOP_1:
					do{
						if ((var8) < (var4)){
							var9 = -5;
							do{
								if ((var9) < (5)){
									NuWorld.setBlockState((Entity)(var1),var1.blockPosition().offset((6) - (var8),var7,-(var9)),var6,3,GRIEF_RULE);
									var9++;
									continue;
								}else{
									break;
								}
							} while (true);
							var8++;
							continue LOOP_1;
						}else{
							break;
						}
					} while (true);
					var8 = 0;
					LOOP_2:
					do{
						if ((var8) < (var4)){
							var9 = -5;
							do{
								if ((var9) < (5)){
									NuWorld.setBlockState((Entity)(var1),var1.blockPosition().offset((-5) - (var8),var7,-(var9)),var6,3,GRIEF_RULE);
									var9++;
									continue;
								}else{
									break;
								}
							} while (true);
							var8++;
							continue LOOP_2;
						}else{
							break;
						}
					} while (true);
					var8 = -5;
					LOOP_3:
					do{
						if ((var8) < (5)){
							var9 = 0;
							do{
								if ((var9) < (var4)){
									NuWorld.setBlockState((Entity)(var1),var1.blockPosition().offset(-(var8),var7,(6) - (var9)),var6,3,GRIEF_RULE);
									var9++;
									continue;
								}else{
									break;
								}
							} while (true);
							var8++;
							continue LOOP_3;
						}else{
							break;
						}
					} while (true);
					var8 = -5;
					LOOP_4:
					do{
						if ((var8) < (5)){
							var9 = 0;
							do{
								if ((var9) < (var4)){
									NuWorld.setBlockState((Entity)(var1),var1.blockPosition().offset(-(var8),var7,(-5) - (var9)),var6,3,GRIEF_RULE);
									var9++;
									continue;
								}else{
									break;
								}
							} while (true);
							var8++;
							continue LOOP_4;
						}else{
							break;
						}
					} while (true);
					var7++;
					continue LOOP_5;
				}else{
					break;
				}
			} while (true);
			var7 = -5;
			LOOP_6:
			do{
				if ((var7) < (5)){
					var8 = -5;
					do{
						if ((var8) < (5)){
							NuWorld.setBlockState((Entity)(var1),var1.blockPosition().offset(-(var7),4,-(var8)),var6,3,GRIEF_RULE);
							var8++;
							continue;
						}else{
							break;
						}
					} while (true);
					var7++;
					continue LOOP_6;
				}else{
					break;
				}
			} while (true);
			this.cooldownComponent.startCooldown(var1,1000F);
			return;
		}else{
			return;
		}
	}}