package xyz.pixelatedw.mineminenomi.abilities.sabi;

import com.google.common.collect.ImmutableList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
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
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModTags$Blocks;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class RustBreakAbility extends Ability {
	// Fields
	private static final float COOLDOWN = 400F;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<RustBreakAbility>> INSTANCE;

	public RustBreakAbility(AbilityCore<RustBreakAbility> var1) {
		super((AbilityCore)(var1));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::onUseEvent))));
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv2_2 = new Pair[]{ImmutablePair.of((Object)("Rusts iron blocks in front of the user"),(Object)(null))};
			Component[] lv2_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv2_2);
			AbilityDescriptionLine$IDescriptionLine[] lv2_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(RustBreakAbility::new)))).addDescriptionLine(lv2_3).addAdvancedDescriptionLine(lv2_4).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("rust_break","Rust Break",var0));
	}

	private void onUseEvent(LivingEntity var1, IAbility var2) {
		HitResult var3 = WyHelper.rayTraceBlocksAndEntities((Entity)(var1));
		if ((var1.distanceToSqr(var3.getLocation())) > (50D)){
			return;
		}else{
			BlockPos var4 = BlockPos.containing((Position)(var3.getLocation()));
			LivingEntity var5 = var1;
			final LivingEntity var5_f1 = var5;
			Predicate<BlockPos> var6 = (l0) -> {
			return var5_f1.level().getBlockState(l0).is(ModTags$Blocks.RUSTY);
		};
			Iterator var7 = WyHelper.getNearbyBlocks(var4,(LevelAccessor)(var1.level()),1,var6,(List)(ImmutableList.of(Blocks.AIR))).iterator();
			LOOP_1:
			do{
				if (var7.hasNext()){
					BlockPos var8 = ((BlockPos)(var7.next()));
					WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.RUST_BREAK.get())),(Entity)(var1),(double)(var8.getX()),(double)(var8.getY()),(double)(var8.getZ()));
					boolean var9 = this.random.nextBoolean();
					int var10 = 0;
					do{
						if ((var10) < (this.random.nextInt(3))){
							ItemStack var11 = (var9) ? (new ItemStack((ItemLike)(Items.IRON_INGOT))) : (new ItemStack((ItemLike)(Items.IRON_NUGGET)));
							ItemEntity var12 = new ItemEntity(var1.level(),(double)(var8.getX()),(double)(var8.getY()),(double)(var8.getZ()),var11);
							var1.level().addFreshEntity((Entity)(var12));
							var10++;
							continue;
						}else{
							break;
						}
					} while (true);
					var1.level().removeBlock(var8,false);
					continue LOOP_1;
				}else{
					break;
				}
			} while (true);
			this.cooldownComponent.startCooldown(var1,400F);
			return;
		}
	}}