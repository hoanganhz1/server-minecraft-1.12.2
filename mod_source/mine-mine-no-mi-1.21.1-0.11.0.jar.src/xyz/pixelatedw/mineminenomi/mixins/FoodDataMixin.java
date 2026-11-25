package xyz.pixelatedw.mineminenomi.mixins;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={FoodData.class})
public class FoodDataMixin {
	// Fields
	@Shadow() private float exhaustionLevel;
	@Shadow() private float saturationLevel;

	@ModifyArg(method={"tick"}, at=@At(value="INVOKE", target="Lnet/minecraft/world/food/FoodData;addExhaustion(F)V"), index=0)
	public float changeExhaustion(float var1) {
		return Math.max((var1) / (2F),0.1F);
	}
	@Inject(method={"tick"}, at={@At(value="HEAD")})
	public void changeSaturation(Player var1, CallbackInfo var2) {
		if (((this.exhaustionLevel) > (4F)) && ((this.saturationLevel) > (0F))){
			this.saturationLevel = (1F) + (Math.max((this.saturationLevel) - (0.75F),0F));
		}
	}
}