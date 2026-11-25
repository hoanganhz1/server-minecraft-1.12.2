package xyz.pixelatedw.mineminenomi.mixins.client;

import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.pixelatedw.mineminenomi.data.entity.DevilFruitData;
import xyz.pixelatedw.mineminenomi.init.ModFruits;

@Mixin(value={DeathScreen.class})
public class DeathScreenMixin {
	// Fields
	@Shadow() @Final() @Mutable() public boolean hardcore;

	@Inject(method={"init"}, at={@At(value="HEAD")})
	public void mineminenomi$init(CallbackInfo var1) {
		DevilFruitData var2 = ((DevilFruitData)(((java.util.Optional)(DevilFruitData.get((LivingEntity)(Minecraft.getInstance().player)))).orElse((Object)(null))));
		if ((var2) == (null)){
			return;
		}else{
			if ((var2.hasDevilFruit(ModFruits.YOMI_YOMI_NO_MI)) && (!(var2.hasYomiPower()))){
				this.hardcore = false;
			}
			return;
		}
	}
}