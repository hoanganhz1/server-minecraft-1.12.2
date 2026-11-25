package xyz.pixelatedw.mineminenomi.mixins;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.neoforged.neoforge.common.util.BlockSnapshot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.pixelatedw.mineminenomi.api.protection.ProtectedArea;
import xyz.pixelatedw.mineminenomi.api.protection.ProtectedArea$RestorationEntry;
import xyz.pixelatedw.mineminenomi.data.world.ProtectedAreasData;

@Mixin(value={FallingBlockEntity.class})
public class FallingBlockEntityMixin {
	@Inject(method={"tick"}, at={@At(value="INVOKE", target="Lnet/minecraft/world/level/Level;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z", shift=At.Shift.BEFORE)})
	public void mineminenomi$beforeLanding(CallbackInfo var1) {
		FallingBlockEntity var2 = ((FallingBlockEntity)(this));
		if (!(var2.level().isClientSide)){
			BlockPos var3 = var2.blockPosition();
			Optional var4 = ProtectedAreasData.get(((ServerLevel)(var2.level()))).getProtectedArea(var3.getX(),var3.getY(),var3.getZ());
			if (var4.isPresent()){
				BlockSnapshot var5 = BlockSnapshot.create(var2.level().dimension(),(LevelAccessor)(var2.level()),var3,2);
				((ProtectedArea)(var4.get())).queueForRestoration(var3,new ProtectedArea$RestorationEntry(var2.level().getGameTime(),(BlockSnapshot)(var5)));
			}else{
				return;
			}
		}
	}
}