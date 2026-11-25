package xyz.pixelatedw.mineminenomi.mixins;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemCooldowns;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.pixelatedw.mineminenomi.api.extensions.IPlayerExtension;
import xyz.pixelatedw.mineminenomi.api.helpers.MorphsHelper;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

@Mixin(value={Player.class}, priority=990)
public class PlayerMixin implements IPlayerExtension {
	// Fields
	private boolean isScoped;

	@Inject(method={"getDimensions"}, at={@At(value="HEAD")}, cancellable=true)
	public void mineminenomi$updateDimensions(Pose var1, CallbackInfoReturnable<EntityDimensions> var2) {
		EntityDimensions var3 = MorphsHelper.getMorphDimensions((LivingEntity)(((Player)(this))),var1);
		if ((var3) != (null)){
			var2.setReturnValue(var3);
		}
	}
	@Redirect(method={"tick"}, at=@At(value="INVOKE", target="Lnet/minecraft/world/item/ItemCooldowns;tick()V"))
	public void mineminenomi$noroTick(ItemCooldowns var1) {
		Player var2 = ((Player)(this));
		if (var2.hasEffect((Holder)(ModEffects.NORO_SLOWNESS))){
			int var3 = Math.max(5,(5) * (var2.getEffect((Holder)(ModEffects.NORO_SLOWNESS)).getAmplifier()));
			if (((var2.tickCount) % (var3)) == (0)){
				var1.tick();
			}else{
				return;
			}
		}else{
			var1.tick();
		}
	}
	@Inject(method={"isScoping"}, at={@At(value="HEAD")}, cancellable=true)
	public void mineminenomi$isScoping(CallbackInfoReturnable<Boolean> var1) {
		if (this.isScoped){
			var1.setReturnValue(Boolean.valueOf(true));
		}
	}
	public void setScoping(boolean var1) {
		this.isScoped = var1;
	}
}