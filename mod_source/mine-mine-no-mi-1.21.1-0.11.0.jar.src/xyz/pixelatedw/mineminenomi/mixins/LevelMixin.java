package xyz.pixelatedw.mineminenomi.mixins;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.entity.LevelEntityGetter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.pixelatedw.mineminenomi.api.IEntityGetter;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
import xyz.pixelatedw.mineminenomi.data.world.ChallengesWorldData;

@Mixin(value={Level.class})
public abstract class LevelMixin implements IEntityGetter {
	@Shadow()
	protected abstract LevelEntityGetter<Entity> getEntities();
	public LevelEntityGetter<Entity> mineminenomi$getEntities() {
		return (LevelEntityGetter<Entity>) (LevelEntityGetter) (this.getEntities());
	}
	@Inject(method={"setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;II)Z"}, at={@At(value="INVOKE", target="Lnet/minecraft/world/level/chunk/LevelChunk;setBlockState(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Z)Lnet/minecraft/world/level/block/state/BlockState;", shift=At.Shift.AFTER)})
	public void mineminenomi$setBlock(BlockPos var1, BlockState var2, int var3, int var4, CallbackInfoReturnable<Boolean> var5) {
		Level var6 = ((Level)(this));
		if ((!(var6.isClientSide())) && (var6 instanceof ServerLevel)){
			ServerLevel var7 = ((ServerLevel)(var6));
			if ((!(var2.isAir())) && (NuWorld.isChallengeDimension(var6))){
				InProgressChallenge var8 = ChallengesWorldData.get().getInProgressChallengeFor(var7);
				if ((var8) != (null)){
					var8.trackBlockPos(var1);
				}else{
					return;
				}
			}else{
				return;
			}
		}
	}
}