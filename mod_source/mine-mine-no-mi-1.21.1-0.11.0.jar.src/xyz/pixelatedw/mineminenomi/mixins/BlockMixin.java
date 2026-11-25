package xyz.pixelatedw.mineminenomi.mixins;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.protection.ProtectedArea;
import xyz.pixelatedw.mineminenomi.data.world.ProtectedAreasData;

@Mixin(value={Block.class})
public class BlockMixin {
	@Inject(method={"popResource"}, at={@At(value="HEAD")}, cancellable=true)
	private static void mineminenomi$popResource(Level var0, BlockPos var1, ItemStack var2, CallbackInfo var3) {
		if (!(var0.isClientSide)){
			if (NuWorld.isChallengeDimension(var0)){
				var3.cancel();
				return;
			}else{
				Optional var4 = ProtectedAreasData.get(((ServerLevel)(var0))).getProtectedArea(var1.getX(),var1.getY(),var1.getZ());
				if ((var4.isPresent()) && ((((ProtectedArea)(var4.get())).canDestroyBlocks()) && (((ProtectedArea)(var4.get())).canRestoreBlocks()))){
					var3.cancel();
					return;
				}else{
					return;
				}
			}
		}else{
			return;
		}
	}
}