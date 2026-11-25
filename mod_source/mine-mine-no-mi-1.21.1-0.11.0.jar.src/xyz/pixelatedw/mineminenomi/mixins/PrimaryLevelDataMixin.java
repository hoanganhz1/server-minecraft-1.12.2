package xyz.pixelatedw.mineminenomi.mixins;

import com.mojang.serialization.Lifecycle;
import net.minecraft.world.level.storage.PrimaryLevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={PrimaryLevelData.class})
public class PrimaryLevelDataMixin {
	@Inject(method={"worldGenSettingsLifecycle"}, at={@At(value="HEAD")}, cancellable=true)
	public void mineminenomi$forceStable(CallbackInfoReturnable<Lifecycle> var1) {
		var1.setReturnValue(Lifecycle.stable());
	}
}