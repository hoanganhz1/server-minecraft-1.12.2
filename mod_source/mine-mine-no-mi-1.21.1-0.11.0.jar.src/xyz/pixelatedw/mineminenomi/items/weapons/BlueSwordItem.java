package xyz.pixelatedw.mineminenomi.items.weapons;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import xyz.pixelatedw.mineminenomi.abilities.gasu.BlueSwordAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.init.ModTiers;

public class BlueSwordItem extends ModSwordItem {
	public BlueSwordItem() {
		super(ModTiers.BLUE_SWORD,new Item.Properties().attributes(ModSwordItem$AttributesBuilder.of(6F).build()));
	}
	public InteractionResultHolder<ItemStack> use(Level var1, Player var2, InteractionHand var3) {
		Function<AbilityData, Boolean> var4 = (l0) -> {
			return Boolean.valueOf(l0.hasUnlockedAbility(((AbilityCore)(BlueSwordAbility.INSTANCE.get()))));
		};
		if (!(((Boolean)(((java.util.Optional)(AbilityData.get((LivingEntity)(var2)))).map((Function)(var4)).orElse(Boolean.valueOf(false)))).booleanValue())){
			return InteractionResultHolder.fail(var2.getItemInHand(var3));
		}else{
			Iterator var5 = WyHelper.getNearbyBlocks((Entity)(var2),10).iterator();
			do{
				if (var5.hasNext()){
					BlockPos var6 = ((BlockPos)(var5.next()));
					if ((var1.getBlockState(var6.above()).isAir()) && (((var6.hashCode()) % ((var1.random.nextInt(35)) + (1))) == (0))){
						var1.setBlock(var6.above(),Blocks.FIRE.defaultBlockState(),3);
						continue;
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
			Iterator var6_1 = TargetHelper.getEntitiesInArea((LevelAccessor)(var2.level()),(LivingEntity)(var2),10D,TargetPredicate.DEFAULT_AREA_CHECK,LivingEntity.class).iterator();
			do{
				if (var6_1.hasNext()){
					AbilityHelper.setSecondsOnFireBy((Entity)(((LivingEntity)(var6_1.next()))),20,(LivingEntity)(var2));
					continue;
				}else{
					break;
				}
			} while (true);
			var2.getCooldowns().addCooldown((Item)(this),600);
			return new InteractionResultHolder(InteractionResult.PASS,var2.getItemInHand(var3));
		}
	}
	public boolean hurtEnemy(ItemStack var1, LivingEntity var2, LivingEntity var3) {
		Function<AbilityData, Boolean> var4 = (l0) -> {
			return Boolean.valueOf(l0.hasUnlockedAbility(((AbilityCore)(BlueSwordAbility.INSTANCE.get()))));
		};
		if (!(((Boolean)(((java.util.Optional)(AbilityData.get(var3))).map((Function)(var4)).orElse(Boolean.valueOf(false)))).booleanValue())){
			return false;
		}else{
			AbilityHelper.setSecondsOnFireBy((Entity)(var2),20,var3);
			return true;
		}
	}
}