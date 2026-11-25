package xyz.pixelatedw.mineminenomi.mixins;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AreaEffectCloud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.pixelatedw.mineminenomi.api.effects.ILingeringEffect;

@Mixin(value={AreaEffectCloud.class})
public class AreaEffectCloudMixin {
	@Inject(method={"addEffect"}, at={@At(value="HEAD")}, cancellable=true)
	public void addEffect(MobEffectInstance var1, CallbackInfo var2) {
		if ((var1.getEffect() instanceof ILingeringEffect) && (!(((ILingeringEffect)(var1.getEffect())).isLingering()))){
			var2.cancel();
		}
	}
}