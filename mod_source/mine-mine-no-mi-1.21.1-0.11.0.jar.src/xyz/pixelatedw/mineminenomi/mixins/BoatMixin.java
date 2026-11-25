package xyz.pixelatedw.mineminenomi.mixins;

import java.util.Optional;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.Boat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import xyz.pixelatedw.mineminenomi.data.entity.KairosekiCoatingData;

@Mixin(value={Boat.class})
public class BoatMixin {
	@ModifyVariable(method={"setDamage"}, at=@At(value="HEAD"), ordinal=0)
	public float mineminenomi$setDamage(float var1) {
		KairosekiCoatingData var2 = ((KairosekiCoatingData)(((java.util.Optional)(KairosekiCoatingData.get((Entity)(((Boat)(this)))))).orElse((Object)(null))));
		if ((var2) != (null)){
			float var3 = (1F) - (((float)(var2.getCoatingLevel())) / (5F));
			var3 = Mth.clamp(var3,0.5F,1F);
			var1 = (var1) * (var3);
		}
		return var1;
	}
}