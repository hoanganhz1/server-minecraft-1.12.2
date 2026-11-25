package xyz.pixelatedw.mineminenomi.api.helpers;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.UnmodifiableIterator;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.damagesources.AbilityDamageSource;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler$NuDamageValue;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.data.entity.DevilFruitData;
import xyz.pixelatedw.mineminenomi.init.ModFruits;

public class DamageSourceHelper {
	// Fields
	private static final HashMap<EntityType<?>, IDamageSourceHandler$NuDamageValue> ENTITY_DAMAGE_VALUES = new HashMap();

	public static void registerEntityDamageValue(EntityType<?> var0, IDamageSourceHandler$NuDamageValue var1) {
		ENTITY_DAMAGE_VALUES.put(var0,var1);
	}
	public static IDamageSourceHandler$NuDamageValue getDamageValueFor(EntityType<?> var0) {
		return ((IDamageSourceHandler$NuDamageValue)(((java.util.HashMap)(ENTITY_DAMAGE_VALUES)).get((Object)(var0))));
	}
	public static ImmutableMap<EntityType<?>, IDamageSourceHandler$NuDamageValue> getEntityDamageMap() {
		return (ImmutableMap<EntityType<?>, IDamageSourceHandler$NuDamageValue>) (ImmutableMap) (ImmutableMap.copyOf((Map)(ENTITY_DAMAGE_VALUES)));
	}
	public static IDamageSourceHandler$NuDamageValue initializeDamageValueFromSource(DamageSource var0) {
		if (var0 instanceof AbilityDamageSource){
			AbilityDamageSource var1 = ((AbilityDamageSource)(var0));
			SourceType[] var2 = ((SourceType[])(var1.getTypes().toArray(new SourceType[0])));
			return new IDamageSourceHandler$NuDamageValue(var1.getAbilityCore(),var1.getElement(),var1.getHakiNature(),var2);
		}else{
			Entity var1_1 = var0.getEntity();
			Entity var2_1 = var0.getDirectEntity();
			if ((var2_1) != (null)){
				IDamageSourceHandler$NuDamageValue var3 = getDamageValueFor(var2_1.getType());
				if ((var3) != (null)){
					return var3;
				}
			}
			SourceElement var3_1 = SourceElement.NONE;
			SourceHakiNature var4 = SourceHakiNature.UNKNOWN;
			HashSet var5 = new HashSet();
			if (var0.is(DamageTypeTags.IS_FIRE)){
				var3_1 = SourceElement.FIRE;
			}else{
				if (var0.is(DamageTypeTags.IS_FREEZING)){
					var3_1 = SourceElement.ICE;
				}else{
					if (var0.is(DamageTypeTags.IS_EXPLOSION)){
						var3_1 = SourceElement.EXPLOSION;
					}else{
						if (var0.is(DamageTypeTags.IS_LIGHTNING)){
							var3_1 = SourceElement.LIGHTNING;
						}else{
							if (var0.is(DamageTypeTags.IS_PROJECTILE)){
								var5.add(SourceType.PROJECTILE);
								var4 = SourceHakiNature.IMBUING;
							}else{
								if ((!(var0.is(DamageTypes.MOB_ATTACK))) && (!(var0.is(DamageTypes.PLAYER_ATTACK)))){
									if (var0.is(DamageTypes.SONIC_BOOM)){
										var3_1 = SourceElement.SHOCKWAVE;
										var5.add(SourceType.INTERNAL);
									}
								}else{
									var4 = SourceHakiNature.HARDENING;
									var5.add(SourceType.VANILLA);
									var5.add(SourceType.PHYSICAL);
									if (((var2_1) != (null)) && (var2_1 instanceof LivingEntity)){
										LivingEntity var6 = ((LivingEntity)(var2_1));
										ItemStack var7 = var6.getMainHandItem();
										if (var7.isEmpty()){
											var5.add(SourceType.FIST);
											Function<DevilFruitData, Boolean> var8 = (l0) -> {
			return Boolean.valueOf(l0.hasDevilFruit(ModFruits.GOMU_GOMU_NO_MI));
		};
											if (((Boolean)(((java.util.Optional)(DevilFruitData.get(var6))).map((Function)(var8)).orElse(Boolean.valueOf(false)))).booleanValue()){
												var3_1 = SourceElement.RUBBER;
											}
										}else{
											if (ItemsHelper.isSword(var7)){
												var5.add(SourceType.SLASH);
												var4 = SourceHakiNature.IMBUING;
											}else{
												if (ItemsHelper.isBlunt(var7)){
													var5.add(SourceType.BLUNT);
													var4 = SourceHakiNature.IMBUING;
												}else{
													if (!(var7.isEmpty())){
														var4 = SourceHakiNature.IMBUING;
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
			SourceType[] var6_1 = ((SourceType[])(var5.toArray(new SourceType[0])));
			if ((var2_1) != (null)){
				UnmodifiableIterator var7_1 = getEntityDamageMap().entrySet().iterator();
				do{
					if (var7_1.hasNext()){
						Map.Entry var8_1 = ((Map.Entry)(var7_1.next()));
						if (((java.lang.Object)(var2_1.getType())).equals(var8_1.getKey())){
							var3_1 = ((IDamageSourceHandler$NuDamageValue)(var8_1.getValue())).element();
							var4 = ((IDamageSourceHandler$NuDamageValue)(var8_1.getValue())).hakiNature();
							var6_1 = ((IDamageSourceHandler$NuDamageValue)(var8_1.getValue())).sourceTypes();
							break;
						}else{
							continue;
						}
					}else{
						break;
					}
				} while (true);
			}
			return new IDamageSourceHandler$NuDamageValue((AbilityCore)(null),var3_1,var4,var6_1);
		}
	}
}