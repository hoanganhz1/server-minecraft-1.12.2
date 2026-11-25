package xyz.pixelatedw.mineminenomi.abilities.baku;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
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
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModTags$Blocks;
import xyz.pixelatedw.mineminenomi.particles.effects.BreakingBlocksParticleEffect$Details;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$Details;

public class BakuMunchAbility extends Ability {
	// Fields
	private static final float COOLDOWN = 60F;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<BakuMunchAbility>> INSTANCE;
	private BreakingBlocksParticleEffect$Details details;

	public BakuMunchAbility(AbilityCore<BakuMunchAbility> var1) {
		super((AbilityCore)(var1));
		this.details = new BreakingBlocksParticleEffect$Details(100);
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		BlockHitResult var3 = WyHelper.rayTraceBlocks((Entity)(var1),16D);
		BlockPos.MutableBlockPos var4 = new BlockPos.MutableBlockPos();
		if ((Mth.sqrt((float)(var1.distanceToSqr(var3.getLocation().x,var3.getLocation().y,var3.getLocation().z)))) < (5F)){
			ArrayList var5 = new ArrayList();
			int var6 = -2;
			LOOP_2:
			do{
				if ((var6) < (2)){
					int var7 = 0;
					LOOP_1:
					do{
						if ((var7) < (3)){
							int var8 = -2;
							do{
								if ((var8) < (2)){
									int var9 = ((int)(var3.getLocation().x)) + (var6);
									int var10 = ((int)(var3.getLocation().y)) - (var7);
									var4.set(var9,var10,((int)(var3.getLocation().z)) + (var8));
									BlockState var11 = var1.level().getBlockState((BlockPos)(var4));
									if ((!(var11.is(ModTags$Blocks.KAIROSEKI))) && (NuWorld.setBlockState(var1.level(),(BlockPos)(var4),Blocks.AIR.defaultBlockState(),2,DefaultProtectionRules.CORE_FOLIAGE_ORE))){
										if (var1 instanceof Player){
											((Player)(var1)).getInventory().add(new ItemStack((ItemLike)(var11.getBlock())));
										}
										var5.add(var4.immutable());
									}
									var8++;
									continue;
								}else{
									break;
								}
							} while (true);
							var7++;
							continue LOOP_1;
						}else{
							break;
						}
					} while (true);
					var6++;
					continue LOOP_2;
				}else{
					break;
				}
			} while (true);
			if ((((java.util.List)(var5)).size()) > (0)){
				this.details.setPositions((List)(var5));
				WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.BREAKING_BLOCKS.get())),(Entity)(var1),0D,0D,0D,(ParticleEffect$Details)(this.details));
			}
		}
		this.cooldownComponent.startCooldown(var1,60F);
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Allows the user to eat a big chunk of blocks in front of him, obtaining all of them as blocks in their inventory"),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(60F)};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(BakuMunchAbility::new)))).addDescriptionLine(lv1_3).addAdvancedDescriptionLine(lv1_4).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("baku_munch","Baku Munch",var0));
	}
}