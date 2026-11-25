package xyz.pixelatedw.mineminenomi.items;

import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.entities.vehicles.CannonEntity;

public class CannonItem extends Item {
	public CannonItem() {
		super(new Item.Properties().stacksTo(1));
	}
	public InteractionResultHolder<ItemStack> use(Level var1, Player var2, InteractionHand var3) {
		ItemStack var4 = var2.getItemInHand(InteractionHand.MAIN_HAND);
		BlockHitResult var5 = getPlayerPOVHitResult(var1,var2,ClipContext.Fluid.ANY);
		if ((var5.getType()) == (HitResult.Type.MISS)){
			return InteractionResultHolder.pass(var4);
		}else{
			if ((var5.getType()) == (HitResult.Type.BLOCK)){
				CannonEntity var6 = new CannonEntity(var1);
				var6.setPos(var5.getLocation().x,var5.getLocation().y,var5.getLocation().z);
				var6.setYRot(var2.getYRot());
				if (!(var1.isClientSide)){
					var1.addFreshEntity((Entity)(var6));
					if (!(var2.getAbilities().instabuild)){
						var4.shrink(1);
					}
				}
				var2.awardStat(Stats.ITEM_USED.get(this));
				return InteractionResultHolder.sidedSuccess(var4,var1.isClientSide());
			}else{
				return InteractionResultHolder.pass(var4);
			}
		}
	}
}