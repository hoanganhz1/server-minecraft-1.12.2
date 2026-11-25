package xyz.pixelatedw.mineminenomi.mixins.client;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.common.NeoForge;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.pixelatedw.mineminenomi.api.events.entity.SetCameraOffsetEvent;
import xyz.pixelatedw.mineminenomi.api.events.entity.SetCameraOffsetEvent$Result;
import xyz.pixelatedw.mineminenomi.api.events.entity.SetCameraZoomEvent;
import xyz.pixelatedw.mineminenomi.api.events.entity.SetCameraZoomEvent$Result;

@Mixin(value={Camera.class})
public class CameraMixin {
	// Fields
	@Shadow() public BlockGetter level;
	@Shadow() public Entity entity;
	@Shadow() public Vec3 position;
	@Shadow() @Final() public final Vector3f forwards;

	public CameraMixin() {
		this.forwards = new Vector3f(0F,0F,1F);
	}
	@Inject(method={"getMaxZoom"}, at={@At(value="HEAD")}, cancellable=true)
	public void mineminenomi$getMaxZoom(float var1, CallbackInfoReturnable<Float> var2) {
		Minecraft var3 = Minecraft.getInstance();
		SetCameraZoomEvent var4 = ((SetCameraZoomEvent)(NeoForge.EVENT_BUS.post((Event)(new SetCameraZoomEvent((Player)(var3.player),var1)))));
		if ((var4.getResult()) == (SetCameraZoomEvent$Result.ALLOW)){
			var1 = var4.getZoom();
			int var5 = 0;
			do{
				if ((var5) < (8)){
					float var6 = (float)((((var5) & (1)) * (2)) - (1));
					float var7 = (float)(((((var5) >> (1)) & (1)) * (2)) - (1));
					float var8 = (float)(((((var5) >> (2)) & (1)) * (2)) - (1));
					var6 = (var6) * (0.1F);
					var7 = (var7) * (0.1F);
					var8 = (var8) * (0.1F);
					Vec3 var9 = this.position.add((double)(var6),(double)(var7),(double)(var8));
					Vec3 var10 = new Vec3((((this.position.x) - ((double)((this.forwards.x()) * (var1)))) + ((double)(var6))) + ((double)(var8)),((this.position.y) - ((double)((this.forwards.y()) * (var1)))) + ((double)(var7)),((this.position.z) - ((double)((this.forwards.z()) * (var1)))) + ((double)(var8)));
					BlockHitResult var11 = this.level.clip(new ClipContext(var9,var10,ClipContext.Block.VISUAL,ClipContext.Fluid.NONE,this.entity));
					if ((var11.getType()) != (HitResult.Type.MISS)){
						float var12 = (float)(var11.getLocation().distanceTo(this.position));
						if ((var12) < (var1)){
							var1 = var12;
							var5++;
							continue;
						}else{
							var5++;
							continue;
						}
					}else{
						var5++;
						continue;
					}
				}else{
					break;
				}
			} while (true);
			var2.setReturnValue(Float.valueOf(var1));
		}
	}
	@Inject(method={"setPosition(Lnet/minecraft/world/phys/Vec3;)V"}, at={@At(value="TAIL")})
	public void mineminenomi$setPosition(Vec3 var1, CallbackInfo var2) {
		Minecraft var3 = Minecraft.getInstance();
		SetCameraOffsetEvent var4 = ((SetCameraOffsetEvent)(NeoForge.EVENT_BUS.post((Event)(new SetCameraOffsetEvent((Player)(var3.player),var1)))));
		if ((var4.getResult()) == (SetCameraOffsetEvent$Result.ALLOW)){
			this.position = var4.getCameraPosition();
		}
	}
}