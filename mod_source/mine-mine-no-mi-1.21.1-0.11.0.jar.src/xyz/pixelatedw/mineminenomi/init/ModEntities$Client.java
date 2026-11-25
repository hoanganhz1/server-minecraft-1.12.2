package xyz.pixelatedw.mineminenomi.init;

import java.util.function.Supplier;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemEntityRenderer;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import xyz.pixelatedw.mineminenomi.models.entities.CandleLockModel;
import xyz.pixelatedw.mineminenomi.models.entities.HandcuffsModel;
import xyz.pixelatedw.mineminenomi.models.entities.SniperTargetModel;
import xyz.pixelatedw.mineminenomi.models.entities.TornadoModel;
import xyz.pixelatedw.mineminenomi.models.entities.WantedPosterPackageModel;
import xyz.pixelatedw.mineminenomi.models.entities.vehicles.CannonModel;
import xyz.pixelatedw.mineminenomi.models.entities.vehicles.StrikerModel;
import xyz.pixelatedw.mineminenomi.models.entities.vehicles.UnicycleModel;
import xyz.pixelatedw.mineminenomi.renderers.entities.BombRenderer$Factory;
import xyz.pixelatedw.mineminenomi.renderers.entities.ChakramRenderer$Factory;
import xyz.pixelatedw.mineminenomi.renderers.entities.EmptyRenderer$Factory;
import xyz.pixelatedw.mineminenomi.renderers.entities.NetRenderer$Factory;
import xyz.pixelatedw.mineminenomi.renderers.entities.SphereRenderer$Factory;
import xyz.pixelatedw.mineminenomi.renderers.entities.SpikeRenderer$Factory;
import xyz.pixelatedw.mineminenomi.renderers.entities.ThrowingWeaponRenderer$Factory;
import xyz.pixelatedw.mineminenomi.renderers.entities.ThrownSpearRenderer$Factory;
import xyz.pixelatedw.mineminenomi.renderers.entities.TornadoRenderer$Factory;
import xyz.pixelatedw.mineminenomi.renderers.entities.VivreCardRenderer$Factory;
import xyz.pixelatedw.mineminenomi.renderers.entities.WantedPosterPackageRenderer$Factory;
import xyz.pixelatedw.mineminenomi.renderers.entities.vehicles.CannonRenderer$Factory;
import xyz.pixelatedw.mineminenomi.renderers.entities.vehicles.StrikerRenderer$Factory;
import xyz.pixelatedw.mineminenomi.renderers.entities.vehicles.UnicycleRenderer$Factory;

public class ModEntities$Client {
	public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions var0) {
		var0.registerLayerDefinition(StrikerModel.LAYER_LOCATION,(Supplier)(((Supplier<LayerDefinition>)(StrikerModel::createLayer))));
		var0.registerLayerDefinition(UnicycleModel.LAYER_LOCATION,(Supplier)(((Supplier<LayerDefinition>)(UnicycleModel::createLayer))));
		var0.registerLayerDefinition(CannonModel.LAYER_LOCATION,(Supplier)(((Supplier<LayerDefinition>)(CannonModel::createLayer))));
		var0.registerLayerDefinition(WantedPosterPackageModel.LAYER_LOCATION,(Supplier)(((Supplier<LayerDefinition>)(WantedPosterPackageModel::createLayer))));
		var0.registerLayerDefinition(SniperTargetModel.LAYER_LOCATION,(Supplier)(((Supplier<LayerDefinition>)(SniperTargetModel::createLayer))));
		var0.registerLayerDefinition(TornadoModel.LAYER_LOCATION,(Supplier)(((Supplier<LayerDefinition>)(TornadoModel::createLayer))));
		var0.registerLayerDefinition(CandleLockModel.LAYER_LOCATION,(Supplier)(((Supplier<LayerDefinition>)(CandleLockModel::createLayer))));
		var0.registerLayerDefinition(HandcuffsModel.LAYER_LOCATION,(Supplier)(((Supplier<LayerDefinition>)(HandcuffsModel::createLayer))));
	}
	public static void registerRenderers(EntityRenderersEvent.RegisterRenderers var0, EntityRendererProvider.Context var1) {
		var0.registerEntityRenderer(((EntityType)(ModEntities.STRIKER.get())),(EntityRendererProvider)(new StrikerRenderer$Factory()));
		var0.registerEntityRenderer(((EntityType)(ModEntities.UNICYCLE.get())),(EntityRendererProvider)(new UnicycleRenderer$Factory()));
		var0.registerEntityRenderer(((EntityType)(ModEntities.CANNON.get())),(EntityRendererProvider)(new CannonRenderer$Factory()));
		var0.registerEntityRenderer(((EntityType)(ModEntities.KEMURI_BOSHI_CLOUD.get())),(EntityRendererProvider)(new EmptyRenderer$Factory()));
		var0.registerEntityRenderer(((EntityType)(ModEntities.MIRAGE_TEMPO_CLOUD.get())),(EntityRendererProvider)(new EmptyRenderer$Factory()));
		var0.registerEntityRenderer(((EntityType)(ModEntities.WEATHER_CLOUD.get())),(EntityRendererProvider)(new EmptyRenderer$Factory()));
		var0.registerEntityRenderer(((EntityType)(ModEntities.MH5_CLOUD.get())),(EntityRendererProvider)(new EmptyRenderer$Factory()));
		var0.registerEntityRenderer(((EntityType)(ModEntities.CHLORO_BALL_CLOUD.get())),(EntityRendererProvider)(new EmptyRenderer$Factory()));
		var0.registerEntityRenderer(((EntityType)(ModEntities.WANTED_POSTER_PACKAGE.get())),(EntityRendererProvider)(new WantedPosterPackageRenderer$Factory()));
		var0.registerEntityRenderer(((EntityType)(ModEntities.VIVRE_CARD.get())),(EntityRendererProvider)(new VivreCardRenderer$Factory()));
		var0.registerEntityRenderer(((EntityType)(ModEntities.ROPE_NET.get())),(EntityRendererProvider)(new NetRenderer$Factory()));
		var0.registerEntityRenderer(((EntityType)(ModEntities.KAIROSEKI_NET.get())),(EntityRendererProvider)(new NetRenderer$Factory()));
		var0.registerEntityRenderer(((EntityType)(ModEntities.THROWN_SPEAR.get())),(EntityRendererProvider)(new ThrownSpearRenderer$Factory()));
		var0.registerEntityRenderer(((EntityType)(ModEntities.CHAKRAM.get())),(EntityRendererProvider)(new ChakramRenderer$Factory()));
		var0.registerEntityRenderer(((EntityType)(ModEntities.SPIKE.get())),(EntityRendererProvider)(new SpikeRenderer$Factory()));
		var0.registerEntityRenderer(((EntityType)(ModEntities.TORNADO.get())),(EntityRendererProvider)(new TornadoRenderer$Factory()));
		var0.registerEntityRenderer(((EntityType)(ModEntities.SPHERE.get())),(EntityRendererProvider)(new SphereRenderer$Factory()));
		var0.registerEntityRenderer(((EntityType)(ModEntities.THROWING_WEAPON.get())),(EntityRendererProvider)(new ThrowingWeaponRenderer$Factory()));
		var0.registerEntityRenderer(((EntityType)(ModEntities.BOMB.get())),(EntityRendererProvider)(new BombRenderer$Factory()));
		var0.registerEntityRenderer(((EntityType)(ModEntities.DEVIL_FRUIT_ITEM.get())),(EntityRendererProvider)(((net.minecraft.client.renderer.entity.EntityRendererProvider)(ItemEntityRenderer::new))));
	}
}