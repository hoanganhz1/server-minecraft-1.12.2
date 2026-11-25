package xyz.pixelatedw.mineminenomi.mixins;

import javax.annotation.Nullable;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ProgressListener;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.pixelatedw.mineminenomi.api.NuWorld;

@Mixin(value={ServerLevel.class})
public class ServerLevelMixin {
	@Inject(method={"save"}, at={@At(value="HEAD")}, cancellable=true)
	public void mineminenomi$save(@Nullable() ProgressListener var1, boolean var2, boolean var3, CallbackInfo var4) {
		if (NuWorld.isChallengeDimension((Level)(((ServerLevel)(this))))){
			var4.cancel();
		}
	}
}