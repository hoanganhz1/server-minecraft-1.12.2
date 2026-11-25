package xyz.pixelatedw.mineminenomi.items.weapons;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.entities.ChakramEntity;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModTiers;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SToggleAnimationPacket;

public class ChakramItem extends ModSwordItem {
	public ChakramItem() {
		super(ModTiers.CHAKRAM,new Item.Properties().attributes(ModSwordItem$AttributesBuilder.of(8F,-3.2F).build()));
	}
	public InteractionResultHolder<ItemStack> use(Level var1, Player var2, InteractionHand var3) {
		ItemStack var4 = var2.getItemInHand(var3);
		if ((var4.getDamageValue()) >= ((var4.getMaxDamage()) - (1))){
			return InteractionResultHolder.fail(var4);
		}else{
			if (!(var1.isClientSide)){
				ModNetwork.sendToAllTrackingAndSelf(SToggleAnimationPacket.playAnimation((LivingEntity)(var2),ModAnimations.THROW_CHAKRAM,this.getUseDuration(var4,(LivingEntity)(var2)),true),(LivingEntity)(var2));
			}
			var2.startUsingItem(var3);
			return InteractionResultHolder.consume(var4);
		}
	}
	public void releaseUsing(ItemStack var1, Level var2, LivingEntity var3, int var4) {
		if (var3 instanceof Player){
			Player var5 = ((Player)(var3));
			if (!(var2.isClientSide)){
				ModNetwork.sendToAllTrackingAndSelf(SToggleAnimationPacket.stopAnimation((LivingEntity)(var5),ModAnimations.THROW_CHAKRAM),(LivingEntity)(var5));
			}
			int var6 = (this.getUseDuration(var1,var3)) - (var4);
			if ((var6) >= (4)){
				float var7 = ((float)(Mth.clamp(var6,4,20))) / (10F);
				var1.hurtAndBreak(1,(LivingEntity)(var5),var3.getEquipmentSlotForItem(var1));
				var5.awardStat(Stats.ITEM_USED.get(this));
				ChakramEntity var8 = new ChakramEntity((LivingEntity)(var5),(ItemStack)(var1));
				var8.setAttackDamage(((1F) + (var7)) + ((float)(this.getDamage(var1))));
				var8.shootFromRotation((Entity)(var5),var5.getXRot(),var5.getYRot(),0F,(1F) + (var7),1F);
				if (var5.getAbilities().instabuild){
					var8.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
				}
				var2.addFreshEntity((Entity)(var8));
				var2.playSound(var5,(Entity)(var8),SoundEvents.FISHING_BOBBER_THROW,SoundSource.PLAYERS,1F,1.5F);
				if (!(var5.getAbilities().instabuild)){
					var5.getInventory().removeItem(var1);
				}else{
					return;
				}
			}else{
				return;
			}
		}
	}
	public UseAnim getUseAnimation(ItemStack var1) {
		return UseAnim.BOW;
	}
	public int getUseDuration(ItemStack var1, LivingEntity var2) {
		return 72000;
	}
	public int getEnchantmentValue() {
		return 1;
	}
}