package xyz.pixelatedw.mineminenomi.items.weapons;

import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
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
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.entities.ThrownSpearEntity;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SToggleAnimationPacket;

public class ModSpearItem extends ModSwordItem {
	public ModSpearItem(Tier var1, Item.Properties var2) {
		super(var1,var2);
	}
	public InteractionResultHolder<ItemStack> use(Level var1, Player var2, InteractionHand var3) {
		ItemStack var4 = var2.getItemInHand(var3);
		if ((var4.getDamageValue()) >= ((var4.getMaxDamage()) - (1))){
			return InteractionResultHolder.fail(var4);
		}else{
			if (((EnchantmentHelper.getTridentSpinAttackStrength(var4,(LivingEntity)(var2))) > (0F)) && (!(var2.isInWaterOrRain()))){
				return InteractionResultHolder.fail(var4);
			}else{
				if (!(var1.isClientSide)){
					ModNetwork.sendToAllTrackingAndSelf(SToggleAnimationPacket.playAnimation((LivingEntity)(var2),ModAnimations.THROW_SPEAR,this.getUseDuration(var4,(LivingEntity)(var2)),true),(LivingEntity)(var2));
				}
				var2.startUsingItem(var3);
				return InteractionResultHolder.consume(var4);
			}
		}
	}
	public void releaseUsing(ItemStack var1, Level var2, LivingEntity var3, int var4) {
		if (var3 instanceof Player){
			Player var5 = ((Player)(var3));
			if (!(var2.isClientSide)){
				ModNetwork.sendToAllTrackingAndSelf(SToggleAnimationPacket.stopAnimation((LivingEntity)(var5),ModAnimations.THROW_SPEAR),(LivingEntity)(var5));
			}
			if (((this.getUseDuration(var1,var3)) - (var4)) >= (10)){
				float var6 = EnchantmentHelper.getTridentSpinAttackStrength(var1,(LivingEntity)(var5));
				if ((!((var6) <= (0F))) && (!(var5.isInWaterOrRain()))){
					return;
				}else{
					Holder var7 = ((Holder)(EnchantmentHelper.pickHighestLevel(var1,EnchantmentEffectComponents.TRIDENT_SOUND).orElse(SoundEvents.TRIDENT_THROW)));
					var1.hurtAndBreak(1,(LivingEntity)(var5),var3.getEquipmentSlotForItem(var1));
					var5.awardStat(Stats.ITEM_USED.get(this));
					float var8 = (float)(((ItemAttributeModifiers)(var1.get(DataComponents.ATTRIBUTE_MODIFIERS))).compute(1D,EquipmentSlot.MAINHAND));
					if ((var6) == (0F)){
						ThrownSpearEntity var9 = new ThrownSpearEntity((Player)(var5),(ItemStack)(var1));
						var9.setAttackDamage((5F) + (var8));
						var9.shootFromRotation((Entity)(var5),var5.getXRot(),var5.getYRot(),0F,(2.5F) + ((var6) * (0.5F)),1F);
						if (var5.getAbilities().instabuild){
							var9.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
						}
						var2.addFreshEntity((Entity)(var9));
						var2.playSound(var5,(Entity)(var9),((SoundEvent)(var7.value())),SoundSource.PLAYERS,1F,1F);
						if (!(var5.getAbilities().instabuild)){
							var5.getInventory().removeItem(var1);
						}else{
							return;
						}
					}else{
						if ((var6) > (0F)){
							float var9_1 = var5.getYRot();
							float var10 = var5.getXRot();
							float var11 = (-(Mth.sin((var9_1) * (0.017453292F)))) * (Mth.cos((var10) * (0.017453292F)));
							float var12 = -(Mth.sin((var10) * (0.017453292F)));
							float var13 = (Mth.cos((var9_1) * (0.017453292F))) * (Mth.cos((var10) * (0.017453292F)));
							float var14 = Mth.sqrt((((var11) * (var11)) + ((var12) * (var12))) + ((var13) * (var13)));
							float var15 = (3F) * (((1F) + (var6)) / (4F));
							var11 = (var11) * ((var15) / (var14));
							var12 = (var12) * ((var15) / (var14));
							var13 = (var13) * ((var15) / (var14));
							var5.push((double)(var11),(double)(var12),(double)(var13));
							var5.startAutoSpinAttack(20,var8,var1);
							if (var5.onGround()){
								var5.move(MoverType.SELF,new Vec3(0D,1.1999999284744263D,0D));
							}
							var2.playSound((Player)(null),(Entity)(var5),((SoundEvent)(var7.value())),SoundSource.PLAYERS,1F,1F);
						}else{
							return;
						}
					}
				}
			}else{
				return;
			}
		}
	}
	public UseAnim getUseAnimation(ItemStack var1) {
		return UseAnim.NONE;
	}
	public int getUseDuration(ItemStack var1, LivingEntity var2) {
		return 72000;
	}
	public int getEnchantmentValue() {
		return 1;
	}
}