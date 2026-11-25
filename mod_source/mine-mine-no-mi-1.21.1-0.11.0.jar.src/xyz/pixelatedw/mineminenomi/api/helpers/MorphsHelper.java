package xyz.pixelatedw.mineminenomi.api.helpers;

import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.MorphComponent;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.DevilFruitData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModFruits;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;

public class MorphsHelper {
	public static float getMorphEyeHeight(LivingEntity var0) {
		DevilFruitData var1 = ((DevilFruitData)(((java.util.Optional)(DevilFruitData.get(var0))).orElse((Object)(null))));
		if ((var1) == (null)){
			return -1F;
		}else{
			Optional var2 = var1.getCurrentMorph();
			if (var2.isPresent()){
				float var3 = ((MorphInfo)(var2.get())).getEyeHeight(var0);
				if ((var3) > (0F)){
					return var3;
				}else{
					return -1F;
				}
			}else{
				Iterator var3_1 = var1.getActiveMorphs().iterator();
				do{
					if (var3_1.hasNext()){
						MorphInfo var4 = ((MorphInfo)(var3_1.next()));
						if (!(var4.isPartial())){
							continue;
						}else{
							float var5 = var4.getEyeHeight(var0);
							if ((var5) > (0F)){
								return var5;
							}else{
								continue;
							}
						}
					}else{
						break;
					}
				} while (true);
				return -1F;
			}
		}
	}
	@Nullable()
	public static @Nullable() EntityDimensions getMorphDimensions(LivingEntity var0, Pose var1) {
		DevilFruitData var2 = ((DevilFruitData)(((java.util.Optional)(DevilFruitData.get(var0))).orElse((Object)(null))));
		if ((var2) == (null)){
			return null;
		}else{
			Optional var3 = var2.getCurrentMorph();
			if (var3.isPresent()){
				Map var4 = ((MorphInfo)(var3.get())).getSizes();
				if (((var4) != (null)) && ((var4.containsKey(var0.getPose())) && ((var4.get(var0.getPose())) != (null)))){
					return ((EntityDimensions)(var4.get(var0.getPose())));
				}else{
					return null;
				}
			}else{
				Iterator var4_1 = var2.getActiveMorphs().iterator();
				do{
					if (var4_1.hasNext()){
						MorphInfo var5 = ((MorphInfo)(var4_1.next()));
						if (!(var5.isPartial())){
							continue;
						}else{
							Map var6 = var5.getSizes();
							if (((var6) != (null)) && ((var6.containsKey(var0.getPose())) && ((var6.get(var0.getPose())) != (null)))){
								return ((EntityDimensions)(var6.get(var0.getPose())));
							}else{
								continue;
							}
						}
					}else{
						break;
					}
				} while (true);
				return null;
			}
		}
	}
	@Deprecated(forRemoval=true)		
@Nullable()
	public static @Nullable() MorphInfo getZoanInfo(LivingEntity var0) {
		DevilFruitData var1 = ((DevilFruitData)(((java.util.Optional)(DevilFruitData.get(var0))).orElse((Object)(null))));
		AbilityData var2 = ((AbilityData)(((java.util.Optional)(AbilityData.get(var0))).orElse((Object)(null))));
		if (((var1) != (null)) && ((var2) != (null))){
			if ((var1.hasDevilFruit(ModFruits.YOMI_YOMI_NO_MI)) && (((MorphInfo)(ModMorphs.YOMI_SKELETON.get())).isActive(var0))){
				return ((MorphInfo)(ModMorphs.YOMI_SKELETON.get()));
			}else{
				Optional var3 = var1.getCurrentMorph();
				if (var3.isPresent()){
					return ((MorphInfo)(var3.get()));
				}else{
					Iterator var4 = var2.getEquippedAbilities().iterator();
					do{
						if (var4.hasNext()){
							IAbility var5 = ((IAbility)(var4.next()));
							if (!(var5.hasComponent(((AbilityComponentKey)(ModAbilityComponents.MORPH.get()))))){
								continue;
							}else{
								MorphComponent var6 = ((MorphComponent)(var5.getComponent(((AbilityComponentKey)(ModAbilityComponents.MORPH.get()))).get()));
								if (var6.isMorphed()){
									return var6.getMorphInfo();
								}else{
									continue;
								}
							}
						}else{
							break;
						}
					} while (true);
					return null;
				}
			}
		}else{
			return null;
		}
	}
}