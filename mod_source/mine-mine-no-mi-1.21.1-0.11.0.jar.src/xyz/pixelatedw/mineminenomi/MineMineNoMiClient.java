package xyz.pixelatedw.mineminenomi;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.RepositorySource;
import net.minecraft.server.packs.resources.ResourceProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.IExtensionPoint;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.IConfigSpec;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforgespi.language.IModFileInfo;
import net.neoforged.neoforgespi.locating.IModFile;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.config.ClientConfig;
import xyz.pixelatedw.mineminenomi.containers.WhiteWalkieStorageContainer;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModContainers;
import xyz.pixelatedw.mineminenomi.init.ModItems$Client;
import xyz.pixelatedw.mineminenomi.init.ModModelOverrides;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes$Client;
import xyz.pixelatedw.mineminenomi.init.ModRenderTypes;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.integrations.clothconfig.ClothConfigIntegration;
import xyz.pixelatedw.mineminenomi.integrations.curios.CuriosIntegration;
import xyz.pixelatedw.mineminenomi.ui.screens.WhiteWalkieStorageScreen;

@Mod(value="mineminenomi", dist={Dist.CLIENT})
public class MineMineNoMiClient {
	public MineMineNoMiClient(IEventBus var1, ModContainer var2) {
		var1.addListener((Consumer)(((Consumer<RegisterParticleProvidersEvent>)(ModParticleTypes$Client::registerParticleFactories))));
		var1.addListener((Consumer)(((Consumer<FMLClientSetupEvent>)(this::modClientSetup))));
		var1.addListener((Consumer)(((Consumer<RegisterMenuScreensEvent>)(this::registerMenuScreens))));
		var1.addListener((Consumer)(((Consumer<RegisterShadersEvent>)(this::registerShaders))));
		var1.addListener((Consumer)(((Consumer<RegisterColorHandlersEvent.Item>)(this::registerColorHandlers))));
		var1.addListener((Consumer)(((Consumer<AddPackFindersEvent>)(this::registerClientPackFinder))));
		var2.registerConfig(ModConfig.Type.CLIENT,(IConfigSpec)(ClientConfig.SPEC));
		if (MineMineNoMi.hasClothConfigInstalled()){
			ClothConfigIntegration.registerModConfig(var2);
		}else{
			IConfigScreenFactory var3 = ConfigurationScreen::new;
			var2.registerExtensionPoint(IConfigScreenFactory.class,(IExtensionPoint)(var3));
		}
	}
	private void modClientSetup(FMLClientSetupEvent var1) {
		ModI18n.init();
		ModModelOverrides.register();
		if (WyDebug.isDebug()){
			WyHelper.generateJSONLangs();
		}
		Runnable var2 = () -> {
			ModAnimations.init();
			if (MineMineNoMi.hasCuriosInstalled()){
				CuriosIntegration.registerCurioRenderers();
			}
		};
		var1.enqueueWork(var2);
	}
	private void registerMenuScreens(RegisterMenuScreensEvent var1) {
		MenuType var2 = ((MenuType)(ModContainers.WHITE_WALKIE_STORAGE.get()));
		MenuScreens.ScreenConstructor var3 = (l0, l1, l2) -> {
			return new WhiteWalkieStorageScreen(l0,l1);
		};
		var1.register(var2,var3);
	}
	private void registerShaders(RegisterShadersEvent var1) {
		try{
			Consumer<ShaderInstance> var2 = (l0) -> {
			ModRenderTypes.transparentColorShaderInstance = l0;
		};
			var1.registerShader(new ShaderInstance(var1.getResourceProvider(),ResourceLocation.fromNamespaceAndPath("mineminenomi","rendertype_entity_translucent_color_no_texture"),DefaultVertexFormat.NEW_ENTITY),var2);
			Consumer<ShaderInstance> var3 = (l0) -> {
			ModRenderTypes.auraHakiShaderInstance = l0;
		};
			var1.registerShader(new ShaderInstance(var1.getResourceProvider(),ResourceLocation.fromNamespaceAndPath("mineminenomi","rendertype_entity_aura_haki"),DefaultVertexFormat.POSITION_TEX_COLOR),var3);
		}catch(IOException var2_1){
			var2_1.printStackTrace();
		}
	}
	private void registerColorHandlers(RegisterColorHandlersEvent.Item var1) {
		ModItems$Client.registerColorHandlers(var1);
	}
	private void registerClientPackFinder(AddPackFindersEvent var1) {
		this.registerResourcePack(var1,"dub-pack",(Component)(Component.literal("Dub Pack")));
		this.registerResourcePack(var1,"blue-goro-pack",(Component)(Component.literal("Blue Goro Pack")));
	}
	@Nullable()
	private void registerResourcePack(AddPackFindersEvent var1, String var2, Component var3) {
		String[] var4 = new String[1];
		String var5 = var2;
		String var6 = "packs/" + var5;
		var4[0] = var6;
		Path var7 = ModList.get().getModFileById("mineminenomi").getFile().findResource(var4);
		PackLocationInfo var8 = new PackLocationInfo(var2,var3,PackSource.BUILT_IN,Optional.empty());
		Pack var9 = Pack.readMetaAndCreate(var8,(Pack.ResourcesSupplier)(new PathPackResources.PathResourcesSupplier(var7)),PackType.CLIENT_RESOURCES,new PackSelectionConfig(false,Pack.Position.TOP,false));
		if ((var9) == (null)){
			String var10 = var3.getString();
			String var11 = "Resource Pack " + var10 + " not found!";
			WyDebug.error(var11);
			return;
		}else{
			Pack var10_1 = var9;
			final Pack var10_1_f1 = var10_1;
			RepositorySource var11_1 = (l0) -> {
			l0.accept(var10_1_f1);
		};
			var1.addRepositorySource(var11_1);
			return;
		}
	}
}