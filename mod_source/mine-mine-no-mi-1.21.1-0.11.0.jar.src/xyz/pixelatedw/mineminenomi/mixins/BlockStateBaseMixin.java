package xyz.pixelatedw.mineminenomi.mixins;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.pixelatedw.mineminenomi.abilities.LogiaBlockBypassingAbility;
import xyz.pixelatedw.mineminenomi.api.NuWorld;

@Mixin(value={BlockStateBase.class})
public abstract class BlockStateBaseMixin {
	@Shadow()
	protected abstract BlockState asState();
	@Inject(method={"randomTick"}, at={@At(value="HEAD")}, cancellable=true)
	public void mineminenomi$randomTick(ServerLevel var1, BlockPos var2, RandomSource var3, CallbackInfo var4) {
		if (NuWorld.isChallengeDimension((Level)(var1))){
			var4.cancel();
			return;
		}else{
			return;
		}
	}
	@Inject(method={"getCollisionShape(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;"}, at={@At(value="HEAD")}, cancellable=true)
	public void mineminenomi$getCollisionShape(BlockGetter var1, BlockPos var2, CollisionContext var3, CallbackInfoReturnable<VoxelShape> var4) {
		BlockState var5 = this.asState();
		if ((!(var5.isAir())) && (var3 instanceof EntityCollisionContext)){
			Entity var6 = ((EntityCollisionContext)(var3)).getEntity();
			if (var6 instanceof Player){
				boolean var7 = LogiaBlockBypassingAbility.isAllowedToGoThrough((LivingEntity)(((Player)(var6))),var5);
				boolean var8 = var6.isSteppingCarefully();
				int var9 = (((double)(var2.getY())) > ((var6.getY()) - (1D))) ? (1) : (0);
				if ((var7) && (!((!(var8)) && ((var9) == (0))))){
					var4.setReturnValue(Shapes.empty());
				}else{
					return;
				}
			}else{
				return;
			}
		}
	}
}