package xyz.pixelatedw.mineminenomi.mixins;

import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value={ClientboundAddEntityPacket.class})
public abstract class ClientboundAddEntityPacketMixin {
	@Redirect(method={"<init>(ILjava/util/UUID;DDDFFLnet/minecraft/world/entity/EntityType;ILnet/minecraft/world/phys/Vec3;D)V"}, at=@At(value="INVOKE", target="Lnet/minecraft/util/Mth;clamp(DDD)D"), expect=3, allow=3)
	private double clamp(double var1, double var2, double var3) {
		return var1;
	}
}