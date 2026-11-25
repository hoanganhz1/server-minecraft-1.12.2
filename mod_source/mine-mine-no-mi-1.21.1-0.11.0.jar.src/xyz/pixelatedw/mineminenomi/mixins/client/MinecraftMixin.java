package xyz.pixelatedw.mineminenomi.mixins.client;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.pixelatedw.mineminenomi.MineMineNoMi;

@Mixin(value={Minecraft.class})
public abstract class MinecraftMixin {
	// Fields
	@Shadow() private boolean pause;

	@Inject(method={"runTick"}, at={@At(value="FIELD", target="Lnet/minecraft/client/Minecraft;pause:Z", opcode=181, shift=At.Shift.AFTER)})
	private void mineminenomi$runTickReturn(boolean var1, CallbackInfo var2) {
		if (this.pause){
			MineMineNoMi.PAUSABLE_TIMER.pause();
		}else{
			MineMineNoMi.PAUSABLE_TIMER.start();
		}
	}
}