package xyz.pixelatedw.mineminenomi.mixins;

import net.minecraft.world.entity.LightningBolt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import xyz.pixelatedw.mineminenomi.api.extensions.ILightningBoltEntityExtension;

@Mixin(value={LightningBolt.class})
public abstract class LightningBoltMixin implements ILightningBoltEntityExtension {
	// Fields
	private boolean isLocal;

	public void setLocal() {
		this.isLocal = true;
	}
	public boolean isLocal() {
		return this.isLocal;
	}
	@ModifyArg(method={"tick"}, at=@At(value="INVOKE", target="Lnet/minecraft/world/level/Level;playLocalSound(DDDLnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FFZ)V"), index=6)
	private float mineminenomi$setVolume(float var1) {
		if ((this.isLocal) && ((var1) == (10000F))){
			return 5F;
		}else{
			return var1;
		}
	}
}