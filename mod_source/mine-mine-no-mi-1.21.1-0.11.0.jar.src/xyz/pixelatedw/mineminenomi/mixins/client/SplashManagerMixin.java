package xyz.pixelatedw.mineminenomi.mixins.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.SplashRenderer;
import net.minecraft.client.resources.SplashManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.RandomSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.pixelatedw.mineminenomi.config.ClientConfig;

@Mixin(value={SplashManager.class})
public class SplashManagerMixin {
	// Fields
	private static final ResourceLocation MMNM_SPLASHES = ResourceLocation.fromNamespaceAndPath("mineminenomi","texts/splashes.txt");
	private static final RandomSource RANDOM = RandomSource.create();

	@Inject(method={"getSplash"}, at={@At(value="HEAD")}, cancellable=true)
	private void mineminenomi$getSplash(CallbackInfoReturnable<SplashRenderer> var1) {
		if (!(ClientConfig.isModSplashTextEnabled())){
			return;
		}else{
			ArrayList var2 = new ArrayList();
			try{
				BufferedReader var3 = Minecraft.getInstance().getResourceManager().openAsReader(MMNM_SPLASHES);
				try{
					Predicate<String> var4 = (l0) -> {
			return (l0.hashCode()) != (125780783);
		};
					Objects.requireNonNull(var2);
					var3.lines().map((Function)(((Function<String, String>)(String::trim)))).filter(var4).forEach((Consumer)(((Consumer<String>)(var2::add))));
				}catch(Throwable var4_1){
					if ((var3) != (null)){
						try{
							var3.close();
						}catch(Throwable var5){
							var4_1.addSuppressed(var5);
						}
					}
					throw var4_1;
				}
				if ((var3) != (null)){
					var3.close();
				}
			}catch(IOException var3_1){

			}
			if ((RANDOM.nextInt(3)) == (0)){
				var1.setReturnValue(new SplashRenderer(((String)(var2.get(RANDOM.nextInt(((java.util.List)(var2)).size()))))));
			}
			return;
		}
	}
}