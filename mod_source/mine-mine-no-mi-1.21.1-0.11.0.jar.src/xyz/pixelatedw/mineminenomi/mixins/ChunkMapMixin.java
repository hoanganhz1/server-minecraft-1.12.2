package xyz.pixelatedw.mineminenomi.mixins;

import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkAccess;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.pixelatedw.mineminenomi.api.NuWorld;

@Mixin(value={ChunkMap.class})
public class ChunkMapMixin {
	// Fields
	@Shadow() @Final() private ServerLevel level;

	@Inject(method={"save"}, at={@At(value="HEAD")}, cancellable=true)
	private void mineminenomi$save(ChunkAccess var1, CallbackInfoReturnable<Boolean> var2) {
		if (NuWorld.isChallengeDimension((Level)(this.level))){
			var2.setReturnValue(Boolean.valueOf(false));
		}
	}
}