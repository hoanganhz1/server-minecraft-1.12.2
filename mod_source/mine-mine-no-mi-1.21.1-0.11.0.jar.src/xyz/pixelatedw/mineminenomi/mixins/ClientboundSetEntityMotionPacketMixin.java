package xyz.pixelatedw.mineminenomi.mixins;

import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value={ClientboundSetEntityMotionPacket.class})
public abstract class ClientboundSetEntityMotionPacketMixin {
	@Redirect(method={"<init>(ILnet/minecraft/world/phys/Vec3;)V"}, at=@At(value="INVOKE", target="Lnet/minecraft/util/Mth;clamp(DDD)D"), expect=3, allow=3)
	private double clamp(double var1, double var2, double var3) {
		return var1;
	}
}