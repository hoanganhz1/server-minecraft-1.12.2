package xyz.pixelatedw.mineminenomi.abilities.hie;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility$IDuringPassiveEvent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule$Builder;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModTags$Blocks;

public class FrostWalkerAbility extends PassiveAbility {
	// Fields
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<FrostWalkerAbility>> INSTANCE;
	private static final BlockProtectionRule GRIEF_RULE;

	public FrostWalkerAbility(AbilityCore<FrostWalkerAbility> var1) {
		super((AbilityCore)(var1));
		this.addDuringPassiveEvent((PassiveAbility$IDuringPassiveEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility$IDuringPassiveEvent)(this::duringPassiveEvent))));
	}
	private void duringPassiveEvent(LivingEntity var1) {
		if (var1.level().isClientSide){
			return;
		}else{
			if ((!(var1.isCrouching())) && ((!(var1.isInWater())) && (((var1.getVehicle()) == (null)) && (!((var1.getHealth()) < ((var1.getMaxHealth()) / (5F))))))){
				if ((var1 instanceof Player) && (((Player)(var1)).getAbilities().flying)){
					return;
				}else{
					BlockPos.MutableBlockPos var2 = new BlockPos.MutableBlockPos();
					int var3 = -1;
					LOOP_2:
					do{
						if ((var3) <= (1)){
							int var4 = -2;
							LOOP_1:
							do{
								if ((var4) <= (2)){
									int var5 = -1;
									do{
										if ((var5) <= (1)){
											var2.set((var1.getX()) + ((double)(var3)),(var1.getY()) + ((double)(var4)),(var1.getZ()) + ((double)(var5)));
											if (NuWorld.setBlockState((Entity)(var1),(BlockPos)(var2),Blocks.FROSTED_ICE.defaultBlockState(),3,GRIEF_RULE)){
												var1.level().scheduleTick((BlockPos)(var2),Blocks.FROSTED_ICE,Mth.nextInt(var1.getRandom(),20,60));
											}
											var5++;
											continue;
										}else{
											break;
										}
									} while (true);
									var4++;
									continue LOOP_1;
								}else{
									break;
								}
							} while (true);
							var3++;
							continue LOOP_2;
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
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Turns all water the user walks on into ice"),(Object)(null)),ImmutablePair.of((Object)("§aCrouching§r temporarily disables the ability"),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,AbilityType.PASSIVE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(FrostWalkerAbility::new)))).addDescriptionLine(lv1_3).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("frost_walker","Frost Walker",var0));
		TagKey[] var1 = new TagKey[]{ModTags$Blocks.BLOCK_PROT_OCEAN_PLANTS,ModTags$Blocks.BLOCK_PROT_WATER};
		GRIEF_RULE = new BlockProtectionRule$Builder(new BlockProtectionRule[0]).addApprovedTags(var1).build();
	}
}