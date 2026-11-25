package xyz.pixelatedw.mineminenomi.items;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.entities.WantedPosterPackageEntity;
import xyz.pixelatedw.mineminenomi.init.ModEntities;

public class WantedPosterPackageItem extends Item {
	public WantedPosterPackageItem() {
		super(new Item.Properties());
	}
	public InteractionResultHolder<ItemStack> use(Level var1, Player var2, InteractionHand var3) {
		ItemStack var4 = var2.getItemInHand(var3);
		if (!(var1.isClientSide)){
			BlockHitResult var5 = Item.getPlayerPOVHitResult(var1,var2,ClipContext.Fluid.NONE);
			if ((var5.getType()) == (HitResult.Type.MISS)){
				return InteractionResultHolder.pass(var4);
			}else{
				if ((var5.getType()) != (HitResult.Type.BLOCK)){
					return InteractionResultHolder.pass(var4);
				}else{
					Vec3 var6 = var5.getLocation();
					WantedPosterPackageEntity var7 = ((WantedPosterPackageEntity)(((EntityType)(ModEntities.WANTED_POSTER_PACKAGE.get())).create(var1)));
					var7.setPos(var6);
					var1.addFreshEntity((Entity)(var7));
				}
			}
		}
		if (!(var2.getAbilities().instabuild)){
			var4.shrink(1);
		}
		return InteractionResultHolder.success(var4);
	}
}