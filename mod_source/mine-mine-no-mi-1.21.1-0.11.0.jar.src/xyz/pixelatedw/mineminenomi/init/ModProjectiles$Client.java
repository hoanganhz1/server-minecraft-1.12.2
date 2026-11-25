package xyz.pixelatedw.mineminenomi.init;

import java.awt.Color;
import java.util.function.Supplier;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import xyz.pixelatedw.mineminenomi.models.CubeModel;
import xyz.pixelatedw.mineminenomi.models.SphereModel;
import xyz.pixelatedw.mineminenomi.models.entities.projectiles.ArrowModel;
import xyz.pixelatedw.mineminenomi.models.entities.projectiles.BaraFestivalModel;
import xyz.pixelatedw.mineminenomi.models.entities.projectiles.BrickBatModel;
import xyz.pixelatedw.mineminenomi.models.entities.projectiles.CrescentModel;
import xyz.pixelatedw.mineminenomi.models.entities.projectiles.HeartModel;
import xyz.pixelatedw.mineminenomi.models.entities.projectiles.HydraModel;
import xyz.pixelatedw.mineminenomi.models.entities.projectiles.MiniHollowModel;
import xyz.pixelatedw.mineminenomi.models.entities.projectiles.NegativeHollowModel;
import xyz.pixelatedw.mineminenomi.models.entities.projectiles.OnibiModel;
import xyz.pixelatedw.mineminenomi.models.entities.projectiles.PawModel;
import xyz.pixelatedw.mineminenomi.models.entities.projectiles.PheasantModel;
import xyz.pixelatedw.mineminenomi.models.entities.projectiles.PillarModel;
import xyz.pixelatedw.mineminenomi.models.entities.projectiles.PlaneModel;
import xyz.pixelatedw.mineminenomi.models.entities.projectiles.RingModel;
import xyz.pixelatedw.mineminenomi.models.entities.projectiles.SharkModel;
import xyz.pixelatedw.mineminenomi.models.entities.projectiles.SpearModel;
import xyz.pixelatedw.mineminenomi.models.entities.projectiles.TokuHollowModel;
import xyz.pixelatedw.mineminenomi.models.entities.projectiles.TopModel;
import xyz.pixelatedw.mineminenomi.models.entities.projectiles.TridentModel;
import xyz.pixelatedw.mineminenomi.models.entities.projectiles.YukiRabiModel;
import xyz.pixelatedw.mineminenomi.models.entities.projectiles.effects.SpiralEffectModel;
import xyz.pixelatedw.mineminenomi.models.morphs.partials.PunkGibsonModel;

public class ModProjectiles$Client {
	// Fields
	private static final ResourceLocation MAGMA_TEXTURE;
	private static final ResourceLocation MERO_MELLOW_TEXTURE;
	private static final ResourceLocation BRICK_BAT_TEXTURE;
	private static final ResourceLocation NORO_BEAM_TEXTURE;
	private static final ResourceLocation TSUNO_TOKAGE_TEXTURE;
	private static final ResourceLocation HIKEN_TEXTURE;
	private static final ResourceLocation ICE_BLOCK_PARTISAN_TEXTURE;
	private static final ResourceLocation ICE_BLOCK_PHEASANT_TEXTURE;
	private static final ResourceLocation YUKI_RABI_TEXTURE;
	private static final ResourceLocation NEGATIVE_HOLLOW_TEXTURE;
	private static final ResourceLocation TOKU_HOLLOW_TEXTURE;
	private static final ResourceLocation DRAGON_ROAR_TEXTURE;
	private static final ResourceLocation DORU_MORI_TEXTURE;
	private static final ResourceLocation SHARKS_TEXTURE;
	private static final ResourceLocation TOP_TEXTURE;
	private static final ResourceLocation ZOU_SKIN_TEXTURE;
	private static final Supplier<Color> GORO_COLOR;

	public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions var0) {
		var0.registerLayerDefinition(CubeModel.LAYER_LOCATION,(Supplier)(((Supplier<LayerDefinition>)(CubeModel::createLayer))));
		var0.registerLayerDefinition(SphereModel.LAYER_LOCATION,(Supplier)(((Supplier<LayerDefinition>)(SphereModel::createLayer))));
		var0.registerLayerDefinition(SharkModel.LAYER_LOCATION,(Supplier)(((Supplier<LayerDefinition>)(SharkModel::createLayer))));
		var0.registerLayerDefinition(SpearModel.LAYER_LOCATION,(Supplier)(((Supplier<LayerDefinition>)(SpearModel::createLayer))));
		var0.registerLayerDefinition(PlaneModel.LAYER_LOCATION,(Supplier)(((Supplier<LayerDefinition>)(PlaneModel::createLayer))));
		var0.registerLayerDefinition(MiniHollowModel.LAYER_LOCATION,(Supplier)(((Supplier<LayerDefinition>)(MiniHollowModel::createLayer))));
		var0.registerLayerDefinition(NegativeHollowModel.LAYER_LOCATION,(Supplier)(((Supplier<LayerDefinition>)(NegativeHollowModel::createLayer))));
		var0.registerLayerDefinition(TokuHollowModel.LAYER_LOCATION,(Supplier)(((Supplier<LayerDefinition>)(TokuHollowModel::createLayer))));
		var0.registerLayerDefinition(RingModel.LAYER_LOCATION,(Supplier)(((Supplier<LayerDefinition>)(RingModel::createLayer))));
		var0.registerLayerDefinition(HeartModel.LAYER_LOCATION,(Supplier)(((Supplier<LayerDefinition>)(HeartModel::createLayer))));
		var0.registerLayerDefinition(TridentModel.LAYER_LOCATION,(Supplier)(((Supplier<LayerDefinition>)(TridentModel::createLayer))));
		var0.registerLayerDefinition(PawModel.LAYER_LOCATION,(Supplier)(((Supplier<LayerDefinition>)(PawModel::createLayer))));
		var0.registerLayerDefinition(PheasantModel.LAYER_LOCATION,(Supplier)(((Supplier<LayerDefinition>)(PheasantModel::createLayer))));
		var0.registerLayerDefinition(CrescentModel.LAYER_LOCATION,(Supplier)(((Supplier<LayerDefinition>)(CrescentModel::createLayer))));
		var0.registerLayerDefinition(YukiRabiModel.LAYER_LOCATION,(Supplier)(((Supplier<LayerDefinition>)(YukiRabiModel::createLayer))));
		var0.registerLayerDefinition(ArrowModel.LAYER_LOCATION,(Supplier)(((Supplier<LayerDefinition>)(ArrowModel::createLayer))));
		var0.registerLayerDefinition(TopModel.LAYER_LOCATION,(Supplier)(((Supplier<LayerDefinition>)(TopModel::createLayer))));
		var0.registerLayerDefinition(HydraModel.LAYER_LOCATION,(Supplier)(((Supplier<LayerDefinition>)(HydraModel::createLayer))));
		var0.registerLayerDefinition(BrickBatModel.LAYER_LOCATION,(Supplier)(((Supplier<LayerDefinition>)(BrickBatModel::createLayer))));
		var0.registerLayerDefinition(PillarModel.LAYER_LOCATION,(Supplier)(((Supplier<LayerDefinition>)(PillarModel::createLayer))));
		var0.registerLayerDefinition(BaraFestivalModel.LAYER_LOCATION,(Supplier)(((Supplier<LayerDefinition>)(BaraFestivalModel::createBodyLayer))));
		var0.registerLayerDefinition(OnibiModel.LAYER_LOCATION,(Supplier)(((Supplier<LayerDefinition>)(OnibiModel::createBodyLayer))));
		var0.registerLayerDefinition(SpiralEffectModel.LAYER_LOCATION,(Supplier)(((Supplier<LayerDefinition>)(SpiralEffectModel::createLayer))));
	}
	public static void registerRenderers(EntityRenderersEvent.RegisterRenderers var0, EntityRendererProvider.Context var1) { throw new RuntimeException("yak-decompiler: undecompilable method body"); /* yak-decompiler: ParseBytesCode failed: unsupported_source: lambda source body requires original synthetic declaration metadata */ }
	static { /* yak-decompiler: undecompilable <clinit>: ParseBytesCode failed: unsupported_source: lambda source body requires original synthetic declaration metadata */ }
}