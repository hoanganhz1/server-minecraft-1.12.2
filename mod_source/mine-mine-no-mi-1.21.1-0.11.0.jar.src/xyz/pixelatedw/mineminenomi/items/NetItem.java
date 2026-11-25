package xyz.pixelatedw.mineminenomi.items;

import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Position;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.enums.NetType;
import xyz.pixelatedw.mineminenomi.effects.CaughtInNetEffect;
import xyz.pixelatedw.mineminenomi.entities.NetEntity;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SToggleAnimationPacket;

public class NetItem extends Item implements ProjectileItem {
	// Fields
	private static final int COOLDOWN = 200;
	private static final int USE_TIME = 20;
	private DeferredHolder<MobEffect, CaughtInNetEffect> netEffect;

	public NetItem(DeferredHolder<MobEffect, CaughtInNetEffect> var1) {
		super(new Item.Properties().stacksTo(1));
		this.netEffect = var1;
	}
	public InteractionResultHolder<ItemStack> use(Level var1, Player var2, InteractionHand var3) {
		ItemStack var4 = var2.getItemInHand(var3);
		if (!(var1.isClientSide)){
			ModNetwork.sendToAllTrackingAndSelf(SToggleAnimationPacket.playAnimation((LivingEntity)(var2),ModAnimations.CHARGE_NET_THROW,this.getUseDuration(var4,(LivingEntity)(var2)),true),(LivingEntity)(var2));
		}
		var2.startUsingItem(var3);
		return InteractionResultHolder.consume(var4);
	}
	public void releaseUsing(ItemStack var1, Level var2, LivingEntity var3, int var4) {
		if (var3 instanceof Player){
			Player var5 = ((Player)(var3));
			if (!(var2.isClientSide)){
				ModNetwork.sendToAllTrackingAndSelf(SToggleAnimationPacket.stopAnimation((LivingEntity)(var5),ModAnimations.CHARGE_NET_THROW),(LivingEntity)(var5));
				if (((this.getUseDuration(var1,var3)) - (var4)) < (20)){
					return;
				}else{
					this.throwNet((LivingEntity)(var5),var2,var1);
				}
			}else{
				return;
			}
		}
	}
	public void onUseTick(Level var1, LivingEntity var2, ItemStack var3, int var4) {
		if ((var1.isClientSide) && (((this.getUseDuration(var3,var2)) - (var2.getUseItemRemainingTicks())) >= (20))){
			Minecraft.getInstance().gameRenderer.itemInHandRenderer.itemUsed(InteractionHand.MAIN_HAND);
		}
	}
	private void throwNet(LivingEntity var1, Level var2, ItemStack var3) {
		NetEntity var4 = NetEntity.createFromItem(var2,var1,this);
		var4.setPos(var1.getX(),(var1.getEyeY()) + (1D),var1.getZ());
		var4.shootFromRotation((Entity)(var1),var1.getXRot(),var1.getYRot(),0F,1.25F,0F);
		var2.addFreshEntity((Entity)(var4));
		int var5 = 1;
		if (var1 instanceof Player){
			Player var6 = ((Player)(var1));
			this.startCooldowns(var6.getCooldowns());
			var5 = (!(var6.getAbilities().instabuild)) ? (1) : (0);
		}
		if ((var5) != (0)){
			var3.shrink(1);
		}
	}
	private void startCooldowns(ItemCooldowns var1) {
		var1.addCooldown(((Item)(ModItems.ROPE_NET.get())),200);
		var1.addCooldown(((Item)(ModItems.KAIROSEKI_NET.get())),200);
	}
	public Holder<MobEffect> getEffect() {
		return (Holder<MobEffect>) (Holder) (this.netEffect.getDelegate());
	}
	public NetType getNetType() {
		return ((CaughtInNetEffect)(this.netEffect.value())).getType();
	}
	public int getUseDuration(ItemStack var1, LivingEntity var2) {
		return 72000;
	}
	public UseAnim getUseAnimation(ItemStack var1) {
		return UseAnim.NONE;
	}
	public Projectile asProjectile(Level var1, Position var2, ItemStack var3, Direction var4) {
		switch (NetItem$1.$SwitchMap$xyz$pixelatedw$mineminenomi$api$enums$NetType[this.getNetType().ordinal()]){
		default:
			throw new MatchException((String)(null),(Throwable)(null));
		case 1:

		case 2:
			NetEntity var5 = NetEntity.createKairosekiNet(var1,(LivingEntity)(null));
			var5.setPos(var2.x(),var2.y(),var2.z());
			return var5;
		}
	}
}