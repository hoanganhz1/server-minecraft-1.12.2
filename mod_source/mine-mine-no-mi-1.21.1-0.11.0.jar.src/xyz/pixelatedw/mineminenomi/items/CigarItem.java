package xyz.pixelatedw.mineminenomi.items;

import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.data.entity.DevilFruitData;
import xyz.pixelatedw.mineminenomi.init.ModFruits;

public class CigarItem extends Item {
	// Fields
	private int smokeFreqency;

	public CigarItem(int var1) {
		super(new Item.Properties().durability(1000));
		this.smokeFreqency = 1;
		this.smokeFreqency = var1;
	}
	public InteractionResultHolder<ItemStack> use(Level var1, Player var2, InteractionHand var3) {
		ItemStack var4 = var2.getItemInHand(var3);
		if (var2.getItemBySlot(EquipmentSlot.HEAD).isEmpty()){
			ItemStack var5 = new ItemStack((ItemLike)(var4.getItem()),1);
			var2.setItemSlot(EquipmentSlot.HEAD,var5);
			var2.playSound(SoundEvents.FLINTANDSTEEL_USE,1F,1F);
			var2.getItemInHand(var3).shrink(1);
		}
		return InteractionResultHolder.success(var4);
	}
	public boolean canEquip(ItemStack var1, EquipmentSlot var2, LivingEntity var3) {
		return var2.equals(EquipmentSlot.HEAD);
	}
	public void onArmorTick(ItemStack var1, Level var2, Player var3) {
	}
	public void inventoryTick(ItemStack var1, Level var2, Entity var3, int var4, boolean var5) {
		if (var3 instanceof LivingEntity){
			LivingEntity var6 = ((LivingEntity)(var3));
			if ((ItemsHelper.hasItemInSlot(var6,EquipmentSlot.HEAD,this)) && (((var6.tickCount) % (this.smokeFreqency)) == (0))){
				if (!(var6.isUnderWater())){
					Vec3 var7 = var6.getLookAngle().scale((0.5D) + ((double)((var6.getBbWidth()) / (2F)))).yRot((float)(Math.toRadians(-20D)));
					var2.addParticle((ParticleOptions)(ParticleTypes.CAMPFIRE_COSY_SMOKE),(var6.getX()) + (var7.x),(var7.y) + (var6.getEyeY()),(var6.getZ()) + (var7.z),0D,0.04D,0D);
				}
				if (!(var2.isClientSide)){
					DevilFruitData var7_1 = ((DevilFruitData)(((java.util.Optional)(DevilFruitData.get(var6))).orElse((Object)(null))));
					if ((var7_1) == (null)){
						return;
					}else{
						if ((!(var7_1.hasDevilFruit(ModFruits.MOKU_MOKU_NO_MI))) && (!(var7_1.hasDevilFruit(ModFruits.GASU_GASU_NO_MI)))){
							var1.hurtAndBreak(1,var6,EquipmentSlot.HEAD);
						}else{
							return;
						}
					}
				}
			}
			super.inventoryTick(var1,var2,(Entity)(var6),var4,var5);
			return;
		}else{
			return;
		}
	}
	public Component getName(ItemStack var1) {
		if (WyHelper.isAprilFirst()){
			return Component.literal("Lollipop");
		}else{
			return super.getName(var1);
		}
	}
}