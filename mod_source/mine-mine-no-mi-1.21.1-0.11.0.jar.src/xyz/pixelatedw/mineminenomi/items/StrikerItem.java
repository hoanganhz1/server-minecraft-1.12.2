package xyz.pixelatedw.mineminenomi.items;

import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.entities.vehicles.StrikerEntity;

public class StrikerItem extends Item {
	// Fields
	private static final Predicate<Entity> ENTITY_PREDICATE = EntitySelector.NO_SPECTATORS.and((Predicate)(((Predicate<Entity>)(Entity::isPickable))));

	public StrikerItem() {
		super(new Item.Properties().stacksTo(1));
	}
	public InteractionResultHolder<ItemStack> use(Level var1, Player var2, InteractionHand var3) {
		ItemStack var4 = var2.getItemInHand(InteractionHand.MAIN_HAND);
		BlockHitResult var5 = getPlayerPOVHitResult(var1,var2,ClipContext.Fluid.ANY);
		if ((var5.getType()) == (HitResult.Type.MISS)){
			return InteractionResultHolder.pass(var4);
		}else{
			Vec3 var6 = var2.getViewVector(1F);
			double var7 = 5D;
			List var8 = var1.getEntities((Entity)(var2),var2.getBoundingBox().expandTowards(var6.scale(var7)).inflate(1D),ENTITY_PREDICATE);
			if (!(var8.isEmpty())){
				Vec3 var9 = var2.getEyePosition(1F);
				Iterator var10 = var8.iterator();
				do{
					if (var10.hasNext()){
						Entity var11 = ((Entity)(var10.next()));
						if (var11.getBoundingBox().inflate((double)(var11.getPickRadius())).contains(var9)){
							return InteractionResultHolder.pass(var4);
						}else{
							continue;
						}
					}else{
						break;
					}
				} while (true);
			}
			if ((var5.getType()) == (HitResult.Type.BLOCK)){
				StrikerEntity var9_1 = new StrikerEntity(var1);
				var9_1.setPos(var5.getLocation().x,var5.getLocation().y,var5.getLocation().z);
				var9_1.setYRot(var2.getYRot());
				if (!(var1.noCollision((Entity)(var9_1),var9_1.getBoundingBox().inflate(-0.1D)))){
					return InteractionResultHolder.fail(var4);
				}else{
					if (!(var1.isClientSide)){
						var1.addFreshEntity((Entity)(var9_1));
						if (!(var2.getAbilities().instabuild)){
							var4.shrink(1);
						}
					}
					var2.awardStat(Stats.ITEM_USED.get(this));
					return InteractionResultHolder.sidedSuccess(var4,var1.isClientSide());
				}
			}else{
				return InteractionResultHolder.pass(var4);
			}
		}
	}
}