package xyz.pixelatedw.mineminenomi;

import com.google.common.collect.ImmutableMultimap;
import com.mojang.logging.LogUtils;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.IConfigSpec;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.world.chunk.TicketController;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.server.permission.events.PermissionGatherEvent;
import org.slf4j.Logger;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler$NuDamageValue;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.DamageSourceHelper;
import xyz.pixelatedw.mineminenomi.api.util.PausableTimer;
import xyz.pixelatedw.mineminenomi.api.util.TPSDelta;
import xyz.pixelatedw.mineminenomi.config.CommonConfig;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.datagen.ModAdvancementsProvider;
import xyz.pixelatedw.mineminenomi.datagen.ModItemModelProvider;
import xyz.pixelatedw.mineminenomi.datagen.ModLootTableProvider;
import xyz.pixelatedw.mineminenomi.datagen.ModRecipesProvider;
import xyz.pixelatedw.mineminenomi.datagen.tags.ModBiomeTagsProvider;
import xyz.pixelatedw.mineminenomi.datagen.tags.ModBlockTagsProvider;
import xyz.pixelatedw.mineminenomi.datagen.tags.ModCurioItemTagsProvider;
import xyz.pixelatedw.mineminenomi.datagen.tags.ModDamageTypeTagsProvider;
import xyz.pixelatedw.mineminenomi.datagen.tags.ModEntityTagsProvider;
import xyz.pixelatedw.mineminenomi.datagen.tags.ModItemTagsProvider;
import xyz.pixelatedw.mineminenomi.datagen.tags.ModStructureTagsProvider;
import xyz.pixelatedw.mineminenomi.handlers.ModEventHandler;
import xyz.pixelatedw.mineminenomi.handlers.entity.CommandReceiverHandler;
import xyz.pixelatedw.mineminenomi.init.ModAbilities;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModAdvancements;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModAttachments;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModBiomeModifiers;
import xyz.pixelatedw.mineminenomi.init.ModBlockEntities;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModChallenges;
import xyz.pixelatedw.mineminenomi.init.ModCommandArgumentTypes;
import xyz.pixelatedw.mineminenomi.init.ModConfiguredFeatures;
import xyz.pixelatedw.mineminenomi.init.ModContainers;
import xyz.pixelatedw.mineminenomi.init.ModCreativeTabs;
import xyz.pixelatedw.mineminenomi.init.ModDamageTypes;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;
import xyz.pixelatedw.mineminenomi.init.ModDimensions;
import xyz.pixelatedw.mineminenomi.init.ModDispenseBehaviors;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModEnchantments;
import xyz.pixelatedw.mineminenomi.init.ModEntities;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModFeatures;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModFruits;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModJollyRogers;
import xyz.pixelatedw.mineminenomi.init.ModLootTypes;
import xyz.pixelatedw.mineminenomi.init.ModMemoryModuleTypes;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.init.ModPermissions;
import xyz.pixelatedw.mineminenomi.init.ModPlacedFeatures;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.init.ModQuests;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.init.ModRecipes;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.init.ModStructures;
import xyz.pixelatedw.mineminenomi.init.ModTags;
import xyz.pixelatedw.mineminenomi.init.ModValues;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;
import xyz.pixelatedw.mineminenomi.integrations.curios.CuriosIntegration;
import xyz.pixelatedw.mineminenomi.mixins.IRangedAttributeMixin;

@Mod(value="mineminenomi")
public class MineMineNoMi {
	// Fields
	public static final PausableTimer PAUSABLE_TIMER = new PausableTimer();
	public static final String PROJECT_ID = "mineminenomi";
	public static final String PROJECT_VERSION = "0.11.0";
	public static final String PROJECT_NAME = "Mine Mine no Mi";
	public static final Logger LOGGER = LogUtils.getLogger();
	public static final TicketController TICKET_CONTROLLER = new TicketController(ResourceLocation.fromNamespaceAndPath("mineminenomi","main"));

	public MineMineNoMi(IEventBus var1, ModContainer var2) {
		Locale.setDefault(Locale.ENGLISH);
		PAUSABLE_TIMER.start();
		ModEventHandler.init();
		var1.addListener((Consumer)(((Consumer<FMLCommonSetupEvent>)(this::modCommonSetup))));
		var1.addListener((Consumer)(((Consumer<FMLLoadCompleteEvent>)(this::modLoadComplete))));
		var1.addListener((Consumer)(((Consumer<RegisterCapabilitiesEvent>)(this::registerCapabilities))));
		var1.addListener((Consumer)(((Consumer<NewRegistryEvent>)(this::registerNewRegistries))));
		var1.addListener((Consumer)(((Consumer<GatherDataEvent>)(this::registerGatherData))));
		NeoForge.EVENT_BUS.addListener((Consumer)(((Consumer<PermissionGatherEvent.Nodes>)(ModPermissions::registerPermissions))));
		ModRegistry.init(var1);
		ModItems.init();
		ModFruits.init();
		ModArmors.init();
		ModWeapons.init();
		ModTags.init();
		ModCreativeTabs.init();
		ModBlocks.init();
		ModContainers.init();
		ModBlockEntities.init();
		ModAbilityComponents.init();
		ModAbilities.init();
		ModMorphs.init();
		ModMobs.init();
		ModProjectiles.init();
		ModEntities.init();
		ModFactions.init();
		ModRaces.init();
		ModFightingStyles.init();
		ModChallenges.init();
		ModQuests.init();
		ModFeatures.init();
		ModDimensions.init();
		ModStructures.init();
		ModEnchantments.init();
		ModAttributes.init();
		ModEffects.init();
		ModJollyRogers.init();
		ModLootTypes.init();
		ModMemoryModuleTypes.init();
		ModParticleEffects.init();
		ModParticleTypes.init();
		ModSounds.init();
		ModCommandArgumentTypes.init();
		ModAdvancements.init();
		ModAttachments.init();
		ModRecipes.init();
		ModDamageTypes.init();
		ModDataComponents.init();
		var2.registerConfig(ModConfig.Type.COMMON,(IConfigSpec)(CommonConfig.SPEC));
		var2.registerConfig(ModConfig.Type.SERVER,(IConfigSpec)(ServerConfig.SPEC));
		TPSDelta.INSTANCE.init();
	}
	private void registerCapabilities(RegisterCapabilitiesEvent var1) {
		if (hasCuriosInstalled()){
			CuriosIntegration.setupCurioCapabilities(var1);
		}
		CommandReceiverHandler.registerCommandReceiver(var1);
	}
	private void registerNewRegistries(NewRegistryEvent var1) {
		var1.register(WyRegistry.ABILITIES);
		var1.register(WyRegistry.ABILITY_COMPONENTS);
		var1.register(WyRegistry.JOLLY_ROGER_ELEMENTS);
		var1.register(WyRegistry.CHALLENGES);
		var1.register(WyRegistry.MORPHS);
		var1.register(WyRegistry.PARTICLE_EFFECTS);
		var1.register(WyRegistry.QUESTS);
		var1.register(WyRegistry.FACTIONS);
		var1.register(WyRegistry.FIGHTING_STYLES);
		var1.register(WyRegistry.RACES);
		var1.register(WyRegistry.PART_ENTITY_TYPES);
	}
	private void registerGatherData(GatherDataEvent var1) {
		DataGenerator var2 = var1.getGenerator();
		PackOutput var3 = var2.getPackOutput();
		CompletableFuture var4 = var1.getLookupProvider();
		ExistingFileHelper var5 = var1.getExistingFileHelper();
		RegistrySetBuilder var6 = new RegistrySetBuilder().add(Registries.DAMAGE_TYPE,(RegistrySetBuilder.RegistryBootstrap)(((net.minecraft.core.RegistrySetBuilder.RegistryBootstrap)(ModDamageTypes::bootstrap)))).add(Registries.ENCHANTMENT,(RegistrySetBuilder.RegistryBootstrap)(((net.minecraft.core.RegistrySetBuilder.RegistryBootstrap)(ModEnchantments::bootstrap)))).add(Registries.CONFIGURED_FEATURE,(RegistrySetBuilder.RegistryBootstrap)(((net.minecraft.core.RegistrySetBuilder.RegistryBootstrap)(ModConfiguredFeatures::bootstrap)))).add(Registries.PLACED_FEATURE,(RegistrySetBuilder.RegistryBootstrap)(((net.minecraft.core.RegistrySetBuilder.RegistryBootstrap)(ModPlacedFeatures::bootstrap)))).add(NeoForgeRegistries.Keys.BIOME_MODIFIERS,(RegistrySetBuilder.RegistryBootstrap)(((net.minecraft.core.RegistrySetBuilder.RegistryBootstrap)(ModBiomeModifiers::bootstrap))));
		CompletableFuture var7 = ((DatapackBuiltinEntriesProvider)(var2.addProvider(var1.includeServer(),(DataProvider)(new DatapackBuiltinEntriesProvider(var3,var4,var6,Set.of((Object)("mineminenomi"))))))).getRegistryProvider();
		ModBlockTagsProvider var8 = new ModBlockTagsProvider(var3,var7,var5);
		var2.addProvider(var1.includeServer(),(DataProvider)(new ModDamageTypeTagsProvider(var3,var7,var5)));
		var2.addProvider(var1.includeServer(),(DataProvider)(var8));
		var2.addProvider(var1.includeServer(),(DataProvider)(new ModItemTagsProvider(var3,var7,var8.contentsGetter(),var5)));
		var2.addProvider(var1.includeServer(),(DataProvider)(new ModEntityTagsProvider(var3,var7,var5)));
		var2.addProvider(var1.includeServer(),(DataProvider)(new ModBiomeTagsProvider(var3,var7,var5)));
		var2.addProvider(var1.includeServer(),(DataProvider)(new ModItemModelProvider(var3,var5)));
		var2.addProvider(var1.includeServer(),(DataProvider)(new ModStructureTagsProvider(var3,var7,var5)));
		if (hasCuriosInstalled()){
			var2.addProvider(var1.includeServer(),(DataProvider)(new ModCurioItemTagsProvider(var3,var7,var8.contentsGetter(),var5)));
		}
		var2.addProvider(var1.includeServer(),(DataProvider)(new ModAdvancementsProvider(var3,var7,var5)));
		var2.addProvider(var1.includeServer(),(DataProvider)(new ModRecipesProvider(var3,var7)));
		var2.addProvider(var1.includeServer(),(DataProvider)(new ModLootTableProvider(var3,var7)));
	}
	private void modCommonSetup(FMLCommonSetupEvent var1) {
		ModDispenseBehaviors.init();
		ImmutableMultimap.Builder var2 = ImmutableMultimap.builder();
		Iterator var3 = WyRegistry.ABILITIES.iterator();
		do{
			if (var3.hasNext()){
				AbilityCore var4 = ((AbilityCore)(var3.next()));
				var2.put(var4.getCategory(),var4);
				continue;
			}else{
				break;
			}
		} while (true);
		ModValues.abilityCategoryMap = var2.build();
		if (hasCuriosInstalled()){
			CuriosIntegration.registerCurioItems();
		}
	}
	private void modLoadComplete(FMLLoadCompleteEvent var1) {
		((IRangedAttributeMixin)(Attributes.MAX_HEALTH.value())).setMaxValue(3000D);
		DamageSourceHelper.registerEntityDamageValue(EntityType.LLAMA_SPIT,new IDamageSourceHandler$NuDamageValue((AbilityCore)(null),SourceElement.WATER,SourceHakiNature.IMBUING,new SourceType[]{SourceType.PROJECTILE}));
	}
	public static boolean hasBlueGoroPackSelected() {
		if (FMLEnvironment.dist.isClient()){
			return WyHelper.isResourcePackEnabled("blue-goro-pack");
		}else{
			return WyHelper.isDatapackEnabled("blue-goro-pack");
		}
	}
	public static boolean hasCuriosInstalled() {
		return ModList.get().isLoaded("curios");
	}
	public static boolean hasClothConfigInstalled() {
		return ModList.get().isLoaded("cloth_config");
	}
}