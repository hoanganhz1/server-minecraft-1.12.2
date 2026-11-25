package xyz.pixelatedw.mineminenomi.mixins;

import net.minecraft.world.entity.player.Abilities;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.pixelatedw.mineminenomi.api.extensions.IPlayerAbilitiesExtension;

@Mixin(value={Abilities.class})
public class AbilitiesMixin implements IPlayerAbilitiesExtension {
	// Fields
	private boolean hasCustomFlight;

	public AbilitiesMixin() {
		this.hasCustomFlight = false;
	}
	public boolean hasCustomFlight() {
		return this.hasCustomFlight;
	}
	public void setCustomFlight(boolean var1) {
		this.hasCustomFlight = var1;
	}
	@Inject(method={"getFlyingSpeed()F"}, at={@At(value="RETURN")}, cancellable=true)
	public void getFlyingSpeed(CallbackInfoReturnable<Float> var1) {
		if (this.hasCustomFlight){
			var1.setReturnValue(Float.valueOf(0F));
		}
	}
}