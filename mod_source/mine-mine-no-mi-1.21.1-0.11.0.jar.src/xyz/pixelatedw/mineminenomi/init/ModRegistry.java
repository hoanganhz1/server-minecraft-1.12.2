package xyz.pixelatedw.mineminenomi.init;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacementType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import xyz.pixelatedw.mineminenomi.api.AttributeRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.WyRegistry$Keys;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.challenges.Challenge;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeRegsitryEntry;
import xyz.pixelatedw.mineminenomi.api.crew.JollyRogerElement;
import xyz.pixelatedw.mineminenomi.api.crew.JollyRogerElement$LayerType;
import xyz.pixelatedw.mineminenomi.api.crew.JollyRogerElementRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityGroups;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class ModRegistry {
	// Fields
	private static HashMap<String, String> langMap;
	private static List<DeferredItem<DeferredSpawnEggItem>> modSpawnEggs;
	public static final DeferredRegister.Items ITEMS;
	private static final DeferredRegister.Blocks BLOCKS;
	private static final DeferredRegister.DataComponents DATA_COMPONENTS;
	private static final DeferredRegister<MenuType<?>> CONTAINER_TYPES;
	private static final DeferredRegister<AbilityCore<? extends IAbility>> ABILITIES;
	private static final DeferredRegister<AbilityComponentKey<? extends AbilityComponent<?>>> ABILITY_COMPONENTS;
	private static final DeferredRegister<MobEffect> EFFECTS;
	private static final DeferredRegister<EntityType<?>> ENTITY_TYPES;
	private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES;
	private static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES;
	private static final DeferredRegister<QuestId<?>> QUESTS;
	private static final DeferredRegister<Feature<?>> FEATURES;
	private static final DeferredRegister<SoundEvent> SOUNDS;
	private static final DeferredRegister<Attribute> ATTRIBUTES;
	private static final DeferredRegister<ChallengeCore<?>> CHALLENGES;
	private static final DeferredRegister<ParticleEffect<?>> PARTICLE_EFFECTS;
	private static final DeferredRegister<ArgumentTypeInfo<?, ?>> COMMAND_ARGUMENT_TYPES;
	private static final DeferredRegister<JollyRogerElement> JOLLY_ROGER_ELEMENTS;
	private static final DeferredRegister<MorphInfo> MORPHS;
	private static final DeferredRegister<CreativeModeTab> CREATIVE_TABS;
	private static final DeferredRegister<LootItemFunctionType<?>> LOOT_FUNCTIONS;
	private static final DeferredRegister<LootItemConditionType> LOOT_CONDITIONS;
	private static final DeferredRegister<MemoryModuleType<?>> MEMORY_MODULE_TYPES;
	private static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS;
	private static final DeferredRegister<Faction> FACTIONS;
	private static final DeferredRegister<FightingStyle> FIGHTING_STYLES;
	private static final DeferredRegister<Race> RACES;
	private static final DeferredRegister<MapCodec<? extends ChunkGenerator>> CHUNK_GENERATORS;
	private static final DeferredRegister<StructurePlacementType<?>> STRUCTURE_PLACEMENTS;
	private static final DeferredRegister<StructureProcessorType<?>> STRUCTURE_PROCESSOR;
	private static final DeferredRegister<AttachmentType<?>> ATTACHMENTS;
	private static final DeferredRegister<CriterionTrigger<?>> TRIGGERS;

	public static HashMap<String, String> getLangMap() {
		return langMap;
	}
	public static List<DeferredItem<DeferredSpawnEggItem>> getModSpawnEggs() {
		return modSpawnEggs;
	}
	public static void init(IEventBus var0) {
		ITEMS.register(var0);
		BLOCKS.register(var0);
		DATA_COMPONENTS.register(var0);
		CONTAINER_TYPES.register(var0);
		ABILITIES.register(var0);
		ABILITY_COMPONENTS.register(var0);
		EFFECTS.register(var0);
		ENTITY_TYPES.register(var0);
		BLOCK_ENTITIES.register(var0);
		PARTICLE_TYPES.register(var0);
		QUESTS.register(var0);
		FEATURES.register(var0);
		SOUNDS.register(var0);
		ATTRIBUTES.register(var0);
		CHALLENGES.register(var0);
		PARTICLE_EFFECTS.register(var0);
		COMMAND_ARGUMENT_TYPES.register(var0);
		JOLLY_ROGER_ELEMENTS.register(var0);
		MORPHS.register(var0);
		CREATIVE_TABS.register(var0);
		LOOT_FUNCTIONS.register(var0);
		LOOT_CONDITIONS.register(var0);
		MEMORY_MODULE_TYPES.register(var0);
		RECIPE_SERIALIZERS.register(var0);
		FACTIONS.register(var0);
		FIGHTING_STYLES.register(var0);
		RACES.register(var0);
		CHUNK_GENERATORS.register(var0);
		STRUCTURE_PLACEMENTS.register(var0);
		STRUCTURE_PROCESSOR.register(var0);
		ATTACHMENTS.register(var0);
		TRIGGERS.register(var0);
	}
	@Deprecated()
	public static String registerName(String var0, String var1) {
		getLangMap().put(var0,var1);
		return var0;
	}
	public static String registerName(ModRegistry$I18nCategory var0, String var1, String var2) {
		String var3 = var0.id;
		String var4 = var1;
		String var5 = var3 + ".mineminenomi." + var4;
		var1 = var5;
		if ((WyDebug.isDebug()) && (getLangMap().containsKey(var1))){
			String var6 = var1;
			String var7 = var6 + " key has already been registered!";
			WyDebug.error(var7);
		}
		getLangMap().put(var1,var2);
		return var1;
	}
	public static String registerAbilityName(String var0, String var1) {
		return registerName(ModRegistry$I18nCategory.ABILITY,var0,var1);
	}
	public static <A extends ArgumentType<?>, T extends ArgumentTypeInfo.Template<A>, I extends ArgumentTypeInfo<A, T>> DeferredHolder<ArgumentTypeInfo<?, ?>, I> registerCommandArgumentType(String var0, Supplier<I> var1) {
		return (DeferredHolder<ArgumentTypeInfo<?, ?>, I>) (DeferredHolder) (((net.neoforged.neoforge.registries.DeferredRegister)(COMMAND_ARGUMENT_TYPES)).register((String)(var0),(Supplier)(var1)));
	}
	public static <T extends LootItemFunction> DeferredHolder<LootItemFunctionType<?>, LootItemFunctionType<T>> registerLootFunction(String var0, MapCodec<T> var1) {
		MapCodec<T> var2 = var1;
		final MapCodec<T> var2_f1 = var2;
		Supplier<LootItemFunctionType> var3 = () -> {
			return new LootItemFunctionType(var2_f1);
		};
		return (DeferredHolder<LootItemFunctionType<?>, LootItemFunctionType<T>>) (DeferredHolder) (((net.neoforged.neoforge.registries.DeferredRegister)(LOOT_FUNCTIONS)).register((String)(var0),(Supplier)(var3)));
	}
	public static <T extends LootItemCondition> DeferredHolder<LootItemConditionType, LootItemConditionType> registerLootCondition(String var0, MapCodec<T> var1) {
		MapCodec<T> var2 = var1;
		final MapCodec<T> var2_f1 = var2;
		Supplier<LootItemConditionType> var3 = () -> {
			return new LootItemConditionType(var2_f1);
		};
		return (DeferredHolder<LootItemConditionType, LootItemConditionType>) (DeferredHolder) (((net.neoforged.neoforge.registries.DeferredRegister)(LOOT_CONDITIONS)).register((String)(var0),(Supplier)(var3)));
	}
	public static <T> DeferredHolder<MemoryModuleType<?>, MemoryModuleType<T>> registerMemoryModule(String var0, Codec<T> var1) {
		Codec<T> var2 = var1;
		final Codec<T> var2_f1 = var2;
		Supplier<MemoryModuleType> var3 = () -> {
			return new MemoryModuleType(Optional.of(var2_f1));
		};
		return (DeferredHolder<MemoryModuleType<?>, MemoryModuleType<T>>) (DeferredHolder) (((net.neoforged.neoforge.registries.DeferredRegister)(MEMORY_MODULE_TYPES)).register((String)(var0),(Supplier)(var3)));
	}
	public static <T> DeferredHolder<MemoryModuleType<?>, MemoryModuleType<T>> registerMemoryModule(String var0) {
		Supplier<MemoryModuleType> var1 = () -> {
			return new MemoryModuleType(Optional.empty());
		};
		return (DeferredHolder<MemoryModuleType<?>, MemoryModuleType<T>>) (DeferredHolder) (((net.neoforged.neoforge.registries.DeferredRegister)(MEMORY_MODULE_TYPES)).register((String)(var0),(Supplier)(var1)));
	}
	public static <S extends RecipeSerializer<T>, T extends Recipe<?>> DeferredHolder<RecipeSerializer<?>, S> registerRecipeSerializer(String var0, Supplier<S> var1) {
		return (DeferredHolder<RecipeSerializer<?>, S>) (DeferredHolder) (((net.neoforged.neoforge.registries.DeferredRegister)(RECIPE_SERIALIZERS)).register((String)(var0),(Supplier)(var1)));
	}
	public static <T extends CreativeModeTab> DeferredHolder<CreativeModeTab, T> registerCreativeTab(String var0, Supplier<T> var1) {
		String var2 = WyHelper.getResourceName(var0);
		registerName(ModRegistry$I18nCategory.CREATIVE_TAB,var2,var0);
		return (DeferredHolder<CreativeModeTab, T>) (DeferredHolder) (((net.neoforged.neoforge.registries.DeferredRegister)(CREATIVE_TABS)).register((String)(var2),(Supplier)(var1)));
	}
	public static <T extends ParticleEffect<?>> DeferredHolder<ParticleEffect<?>, T> registerParticleEffect(String var0, Supplier<T> var1) {
		String var2 = WyHelper.getResourceName(var0);
		String var3 = var2;
		String var4 = "particle_effect.mineminenomi." + var3;
		return (DeferredHolder<ParticleEffect<?>, T>) (DeferredHolder) (((net.neoforged.neoforge.registries.DeferredRegister)(PARTICLE_EFFECTS)).register((String)(var4),(Supplier)(var1)));
	}
	public static DeferredHolder<Attribute, Attribute> registerAttribute(String var0, AttributeRegistryEntry var1) {
		String var2 = WyHelper.getResourceName(var0);
		String var3 = var2;
		String var4 = "name." + var3;
		String var5 = registerName(ModRegistry$I18nCategory.ATTRIBUTE,var4,var0);
		AttributeRegistryEntry var6 = var1;
		String var7 = var5;
		final AttributeRegistryEntry var6_f1 = var6;
		final String var7_f2 = var7;
		Supplier<Attribute> var8 = () -> {
			return var6_f1.get(var7_f2);
		};
		return (DeferredHolder<Attribute, Attribute>) (DeferredHolder) (((net.neoforged.neoforge.registries.DeferredRegister)(ATTRIBUTES)).register((String)(var2),(Supplier)(var8)));
	}
	public static <T extends MorphInfo> DeferredHolder<MorphInfo, T> registerMorph(String var0, Supplier<T> var1) {
		return (DeferredHolder<MorphInfo, T>) (DeferredHolder) (((net.neoforged.neoforge.registries.DeferredRegister)(MORPHS)).register((String)(var0),(Supplier)(var1)));
	}
	public static DeferredHolder<JollyRogerElement, JollyRogerElement> registerJollyRogerElement(String var0, String var1, JollyRogerElement$LayerType var2, JollyRogerElementRegistryEntry var3) {
		switch (ModRegistry$1.$SwitchMap$xyz$pixelatedw$mineminenomi$api$crew$JollyRogerElement$LayerType[var2.ordinal()]){
		default:
			throw new MatchException((String)(null),(Throwable)(null));
		case 1:

		case 2:

		case 3:
			String var4 = "details";
			String var5 = var4;
			String var6 = var0;
			String var7 = "jolly_roger.mineminenomi." + var5 + "." + var6;
			MutableComponent var8 = Component.translatable(registerName(var7,var1));
			String var9 = var4;
			String var10 = var0;
			String var11 = var9 + "_" + var10;
			String var12 = var11;
			String var13 = var4;
			String var14 = var0;
			JollyRogerElementRegistryEntry var15 = var3;
			JollyRogerElement$LayerType var16 = var2;
			MutableComponent var17 = var8;
			final String var13_f1 = var13;
			final String var14_f2 = var14;
			final JollyRogerElementRegistryEntry var15_f3 = var15;
			final JollyRogerElement$LayerType var16_f4 = var16;
			final MutableComponent var17_f5 = var17;
			Supplier<JollyRogerElement> var18 = () -> {
			String lv6_5 = var13_f1;
			String lv6_6 = var14_f2;
			String lv6_7 = "textures/jolly_rogers/" + lv6_5 + "/" + lv6_6 + ".png";
			ResourceLocation lv6_8 = ResourceLocation.fromNamespaceAndPath("mineminenomi",lv6_7);
			return var15_f3.get(var16_f4).setTexture(lv6_8).setLocalizedName(var17_f5);
		};
			return (DeferredHolder<JollyRogerElement, JollyRogerElement>) (DeferredHolder) (((net.neoforged.neoforge.registries.DeferredRegister)(JOLLY_ROGER_ELEMENTS)).register((String)(var12),(Supplier)(var18)));
		}
	}
	public static DeferredHolder<JollyRogerElement, JollyRogerElement> registerJollyRogerElement(JollyRogerElement var0) {
		String var1 = WyHelper.getResourceName(var0.getTexture().toString().replace((CharSequence)("mineminenomi"),(CharSequence)("")));
		JollyRogerElement var2 = var0;
		final JollyRogerElement var2_f1 = var2;
		Supplier<JollyRogerElement> var3 = () -> {
			return var2_f1;
		};
		return (DeferredHolder<JollyRogerElement, JollyRogerElement>) (DeferredHolder) (((net.neoforged.neoforge.registries.DeferredRegister)(JOLLY_ROGER_ELEMENTS)).register((String)(var1),(Supplier)(var3)));
	}
	public static <T extends ParticleType<?>> DeferredHolder<ParticleType<?>, T> registerParticleType(String var0, Supplier<T> var1) {
		String var2 = WyHelper.getResourceName(var0);
		return (DeferredHolder<ParticleType<?>, T>) (DeferredHolder) (((net.neoforged.neoforge.registries.DeferredRegister)(PARTICLE_TYPES)).register((String)(var2),(Supplier)(var1)));
	}
	public static <T extends Feature<?>> DeferredHolder<Feature<?>, T> registerFeature(String var0, Supplier<T> var1) {
		String var2 = WyHelper.getResourceName(var0);
		return (DeferredHolder<Feature<?>, T>) (DeferredHolder) (((net.neoforged.neoforge.registries.DeferredRegister)(FEATURES)).register((String)(var2),(Supplier)(var1)));
	}
	public static <T extends MobEffect> DeferredHolder<MobEffect, T> registerEffect(String var0, Supplier<T> var1) {
		return (DeferredHolder<MobEffect, T>) (DeferredHolder) (registerEffect((String)(var0),(String)(WyHelper.getResourceName(var0)),(Supplier)(var1)));
	}
	public static <T extends MobEffect> DeferredHolder<MobEffect, T> registerEffect(String var0, String var1, Supplier<T> var2) {
		String var3 = WyHelper.getResourceName(var1);
		String var4 = var3;
		String var5 = "effect.mineminenomi." + var4;
		getLangMap().put(var5,var0);
		return (DeferredHolder<MobEffect, T>) (DeferredHolder) (((net.neoforged.neoforge.registries.DeferredRegister)(EFFECTS)).register((String)(var3),(Supplier)(var2)));
	}
	public static <T extends Quest> DeferredHolder<QuestId<?>, QuestId<T>> registerQuest(String var0, String var1, Supplier<QuestId<T>> var2) {
		registerName(ModRegistry$I18nCategory.QUEST,var0,var1);
		return (DeferredHolder<QuestId<?>, QuestId<T>>) (DeferredHolder) (((net.neoforged.neoforge.registries.DeferredRegister)(QUESTS)).register((String)(var0),(Supplier)(var2)));
	}
	public static Component registerObjectiveTitle(ResourceLocation var0, int var1, String var2, Object... var3) {
		String var4 = String.valueOf((Object)(var0));
		int var5 = var1;
		String var6 = var4 + ".objective." + var5;
		return Component.translatable(registerName(var6,var2),var3);
	}
	public static Component registerObjectiveTitle(String var0, int var1, String var2, Object... var3) {
		String var4 = var0;
		int var5 = var1;
		String var6 = var4 + ".objective." + var5;
		return Component.translatable(registerName(ModRegistry$I18nCategory.QUEST,var6,var2),var3);
	}
	public static <T extends IAbility> DeferredHolder<AbilityCore<?>, AbilityCore<T>> registerAbility(String var0, String var1, AbilityRegistryEntry<AbilityCore<T>> var2) {
		registerName(ModRegistry$I18nCategory.ABILITY,var0,var1);
		AbilityRegistryEntry<AbilityCore<T>> var3 = var2;
		String var4 = var0;
		String var5 = var1;
		final AbilityRegistryEntry<AbilityCore<T>> var3_f1 = var3;
		final String var4_f2 = var4;
		final String var5_f3 = var5;
		Supplier var6 = (Supplier) ((Supplier<AbilityCore>) (() -> {
			return ((AbilityCore)(var3_f1.get(var4_f2,var5_f3)));
		}));
		return (DeferredHolder<AbilityCore<?>, AbilityCore<T>>) (DeferredHolder) (ABILITIES.register(var0,var6));
	}
	public static <C extends AbilityComponent<?>> DeferredHolder<AbilityComponentKey<? extends AbilityComponent<?>>, AbilityComponentKey<C>> registerAbilityComponent(String var0) {
		String var1 = var0;
		final String var1_f1 = var1;
		Supplier var2 = (Supplier) ((Supplier<AbilityComponentKey>) (() -> {
			return AbilityComponentKey.key(ResourceLocation.fromNamespaceAndPath("mineminenomi",var1_f1));
		}));
		return (DeferredHolder<AbilityComponentKey<? extends AbilityComponent<?>>, AbilityComponentKey<C>>) (DeferredHolder) (ABILITY_COMPONENTS.register(var0,var2));
	}
	public static DeferredHolder<SoundEvent, SoundEvent> registerSound(String var0) {
		String var1 = WyHelper.getResourceName(var0);
		String var2 = var1;
		String var3 = "mineminenomi.subtitle." + var2;
		getLangMap().put(var3,var0);
		String var4 = var1;
		final String var4_f1 = var4;
		Supplier<SoundEvent> var5 = () -> {
			return SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("mineminenomi",var4_f1));
		};
		return (DeferredHolder<SoundEvent, SoundEvent>) (DeferredHolder) (((net.neoforged.neoforge.registries.DeferredRegister)(SOUNDS)).register((String)(var1),(Supplier)(var5)));
	}
	public static <T extends AkumaNoMiItem> DeferredItem<T> registerFruitItem(String var0, Supplier<T> var1) {
		String var2 = WyHelper.getResourceName(var0);
		registerName(ModRegistry$I18nCategory.ITEM,var2,var0);
		AbilityGroups.addFruitGroup(ResourceLocation.fromNamespaceAndPath("mineminenomi",var2));
		return (DeferredItem<T>) (DeferredItem) (((net.neoforged.neoforge.registries.DeferredRegister.Items)(ITEMS)).register((String)(var2),(Supplier)(var1)));
	}
	public static <T extends Item> DeferredItem<T> registerItem(String var0, Supplier<T> var1) {
		return (DeferredItem<T>) (DeferredItem) (registerItem((String)(WyHelper.getResourceName(var0)),(String)(var0),(Supplier)(var1)));
	}
	public static <T extends Item> DeferredItem<T> registerItem(String var0, String var1, Supplier<T> var2) {
		registerName(ModRegistry$I18nCategory.ITEM,var0,var1);
		return (DeferredItem<T>) (DeferredItem) (((net.neoforged.neoforge.registries.DeferredRegister.Items)(ITEMS)).register((String)(var0),(Supplier)(var2)));
	}
	public static DeferredItem<DeferredSpawnEggItem> registerSpawnEggItem(String var0, Supplier<DeferredSpawnEggItem> var1) {
		String var2 = WyHelper.getResourceName(var0);
		String var3 = var2;
		String var4 = var3 + "_spawn_egg";
		String var5 = var4;
		String var6 = var0;
		String var7 = "Spawn " + var6;
		String var8 = var7;
		String var9 = var5;
		String var10 = "item.mineminenomi." + var9;
		getLangMap().put(var10,var8);
		DeferredItem var11 = ITEMS.register(var5,var1);
		getModSpawnEggs().add(var11);
		return var11;
	}
	public static <T extends Block> DeferredHolder<Block, T> registerBlock(String var0, Supplier<T> var1) {
		return (DeferredHolder<Block, T>) (DeferredHolder) (registerBlock((String)(var0),(Supplier)(var1),(Item.Properties)(new Item.Properties())));
	}
	public static <T extends Block> DeferredHolder<Block, T> registerBlock(String var0, Supplier<T> var1, Item.Properties var2) {
		String var3 = WyHelper.getResourceName(var0);
		String var4 = var3;
		String var5 = "block.mineminenomi." + var4;
		getLangMap().put(var5,var0);
		DeferredBlock var6 = BLOCKS.register(var3,var1);
		DeferredBlock var7 = var6;
		Item.Properties var8 = var2;
		final DeferredBlock var7_f1 = var7;
		final Item.Properties var8_f2 = var8;
		Supplier<BlockItem> var9 = () -> {
			return new BlockItem(((Block)(var7_f1.get())),var8_f2);
		};
		registerItem((String)(var0),(Supplier)(var9));
		return var6;
	}
	public static <T extends Block> DeferredHolder<Block, T> registerBlock(String var0, Supplier<T> var1, Function<T, BlockItem> var2) {
		String var3 = WyHelper.getResourceName(var0);
		String var4 = var3;
		String var5 = "block.mineminenomi." + var4;
		getLangMap().put(var5,var0);
		DeferredBlock var6 = BLOCKS.register(var3,var1);
		Function<T, BlockItem> var7 = var2;
		DeferredBlock var8 = var6;
		final Function<T, BlockItem> var7_f1 = var7;
		final DeferredBlock var8_f2 = var8;
		Supplier<BlockItem> var9 = () -> {
			return ((BlockItem)(((java.util.function.Function)(var7_f1)).apply((Object)(((Block)(var8_f2.get()))))));
		};
		registerItem((String)(var0),(Supplier)(var9));
		return var6;
	}
	public static <C extends AbstractContainerMenu> DeferredHolder<MenuType<?>, MenuType<C>> registerContainer(String var0, IContainerFactory<C> var1) {
		String var2 = WyHelper.getResourceName(var0);
		IContainerFactory<C> var3 = var1;
		final IContainerFactory<C> var3_f1 = var3;
		Supplier<MenuType> var4 = () -> {
			return IMenuTypeExtension.create(var3_f1);
		};
		return (DeferredHolder<MenuType<?>, MenuType<C>>) (DeferredHolder) (((net.neoforged.neoforge.registries.DeferredRegister)(CONTAINER_TYPES)).register((String)(var2),(Supplier)(var4)));
	}
	public static <T extends BlockEntity> DeferredHolder<BlockEntityType<?>, BlockEntityType<T>> registerTileEntity(String var0, Supplier<BlockEntityType<T>> var1) {
		String var2 = WyHelper.getResourceName(var0);
		return (DeferredHolder<BlockEntityType<?>, BlockEntityType<T>>) (DeferredHolder) (((net.neoforged.neoforge.registries.DeferredRegister)(BLOCK_ENTITIES)).register((String)(var2),(Supplier)(var1)));
	}
	public static <T extends Entity> EntityType.Builder<T> createEntityType(EntityType.EntityFactory<T> var0, MobCategory var1) {
		EntityType.Builder var2 = EntityType.Builder.of(var0,var1);
		var2.clientTrackingRange(8).setUpdateInterval(5).sized(0.5F,0.5F);
		return var2;
	}
	public static <T extends Mob> EntityType.Builder<T> createMobType(EntityType.EntityFactory<T> var0, EnumProxy<MobCategory> var1) {
		return (EntityType.Builder<T>) (EntityType.Builder) (createMobType((EntityType.EntityFactory)(var0),(MobCategory)(((MobCategory)(var1.getValue())))));
	}
	public static <T extends Mob> EntityType.Builder<T> createMobType(EntityType.EntityFactory<T> var0, MobCategory var1) {
		EntityType.Builder var2 = EntityType.Builder.of(var0,var1);
		var2.clientTrackingRange(10).setShouldReceiveVelocityUpdates(true).setUpdateInterval(3).sized(0.6F,1.8F);
		return var2;
	}
	public static <T extends Entity> EntityType.Builder<T> createProjectileType(EntityType.EntityFactory<T> var0) {
		EntityType.Builder var1 = EntityType.Builder.of(var0,MobCategory.MISC);
		var1.clientTrackingRange(64).setShouldReceiveVelocityUpdates(true).setUpdateInterval(1).sized(0.75F,0.75F);
		return var1;
	}
	public static <T extends EntityType<? extends Entity>> DeferredHolder<EntityType<?>, T> registerEntityType(String var0, Supplier<T> var1) {
		return (DeferredHolder<EntityType<?>, T>) (DeferredHolder) (registerEntityType((String)(var0),(String)(WyHelper.getResourceName(var0)),(Supplier)(var1)));
	}
	public static <T extends EntityType<? extends Entity>> DeferredHolder<EntityType<?>, T> registerEntityType(String var0, String var1, Supplier<T> var2) {
		String var3 = var1;
		String var4 = "entity.mineminenomi." + var3;
		getLangMap().put(var4,var0);
		return (DeferredHolder<EntityType<?>, T>) (DeferredHolder) (((net.neoforged.neoforge.registries.DeferredRegister)(ENTITY_TYPES)).register((String)(var1),(Supplier)(var2)));
	}
	public static <T extends Challenge> DeferredHolder<ChallengeCore<?>, ChallengeCore<T>> registerChallenge(String var0, ChallengeRegsitryEntry<ChallengeCore<T>> var1) {
		ChallengeRegsitryEntry<ChallengeCore<T>> var2 = var1;
		String var3 = var0;
		final ChallengeRegsitryEntry<ChallengeCore<T>> var2_f1 = var2;
		final String var3_f2 = var3;
		Supplier var4 = (Supplier) ((Supplier<ChallengeCore>) (() -> {
			return ((ChallengeCore)(var2_f1.get(var3_f2)));
		}));
		return (DeferredHolder<ChallengeCore<?>, ChallengeCore<T>>) (DeferredHolder) (CHALLENGES.register(var0,var4));
	}
	public static <I extends Faction> DeferredHolder<Faction, I> registerFaction(String var0, String var1, Supplier<I> var2) {
		registerName(ModRegistry$I18nCategory.FACTION,var0,var1);
		return (DeferredHolder<Faction, I>) (DeferredHolder) (((net.neoforged.neoforge.registries.DeferredRegister)(FACTIONS)).register((String)(var0),(Supplier)(var2)));
	}
	public static <I extends FightingStyle> DeferredHolder<FightingStyle, I> registerStyle(String var0, String var1, Supplier<I> var2) {
		registerName(ModRegistry$I18nCategory.STYLE,var0,var1);
		return (DeferredHolder<FightingStyle, I>) (DeferredHolder) (((net.neoforged.neoforge.registries.DeferredRegister)(FIGHTING_STYLES)).register((String)(var0),(Supplier)(var2)));
	}
	public static <I extends Race> DeferredHolder<Race, I> registerRace(String var0, String var1, Supplier<I> var2) {
		registerName(ModRegistry$I18nCategory.RACE,var0,var1);
		return (DeferredHolder<Race, I>) (DeferredHolder) (((net.neoforged.neoforge.registries.DeferredRegister)(RACES)).register((String)(var0),(Supplier)(var2)));
	}
	public static <C extends MapCodec<? extends ChunkGenerator>> DeferredHolder<MapCodec<? extends ChunkGenerator>, C> registerChunkGenerator(String var0, Supplier<C> var1) {
		return (DeferredHolder<MapCodec<? extends ChunkGenerator>, C>) (DeferredHolder) (((net.neoforged.neoforge.registries.DeferredRegister)(CHUNK_GENERATORS)).register((String)(var0),(Supplier)(var1)));
	}
	public static <C extends StructurePlacementType<? extends StructurePlacement>> DeferredHolder<StructurePlacementType<? extends StructurePlacement>, C> registerStructurePlacement(String var0, Supplier<C> var1) {
		return (DeferredHolder<StructurePlacementType<? extends StructurePlacement>, C>) (DeferredHolder) (((net.neoforged.neoforge.registries.DeferredRegister)(STRUCTURE_PLACEMENTS)).register((String)(var0),(Supplier)(var1)));
	}
	public static <C extends StructureProcessorType<? extends StructureProcessor>> DeferredHolder<StructureProcessorType<? extends StructureProcessor>, C> registerStructureProcessor(String var0, Supplier<C> var1) {
		return (DeferredHolder<StructureProcessorType<? extends StructureProcessor>, C>) (DeferredHolder) (((net.neoforged.neoforge.registries.DeferredRegister)(STRUCTURE_PROCESSOR)).register((String)(var0),(Supplier)(var1)));
	}
	public static <T> DeferredHolder<AttachmentType<?>, AttachmentType<T>> registerAttachment(String var0, Supplier<AttachmentType<T>> var1) {
		return (DeferredHolder<AttachmentType<?>, AttachmentType<T>>) (DeferredHolder) (((net.neoforged.neoforge.registries.DeferredRegister)(ATTACHMENTS)).register((String)(var0),(Supplier)(var1)));
	}
	public static <T extends CriterionTrigger<?>> DeferredHolder<CriterionTrigger<?>, T> registerTrigger(String var0, Supplier<T> var1) {
		return (DeferredHolder<CriterionTrigger<?>, T>) (DeferredHolder) (((net.neoforged.neoforge.registries.DeferredRegister)(TRIGGERS)).register((String)(var0),(Supplier)(var1)));
	}
	public static <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>> registerDataComponent(String var0, Supplier<DataComponentType<T>> var1) {
		return (DeferredHolder<DataComponentType<?>, DataComponentType<T>>) (DeferredHolder) (((net.neoforged.neoforge.registries.DeferredRegister.DataComponents)(DATA_COMPONENTS)).register((String)(var0),(Supplier)(var1)));
	}
	static  {
		langMap = (HashMap) (new HashMap());
		modSpawnEggs = new ArrayList();
		ITEMS = DeferredRegister.createItems("mineminenomi");
		BLOCKS = DeferredRegister.createBlocks("mineminenomi");
		DATA_COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE,"mineminenomi");
		CONTAINER_TYPES = (DeferredRegister) (DeferredRegister.create(Registries.MENU,"mineminenomi"));
		ABILITIES = (DeferredRegister) (DeferredRegister.create(WyRegistry$Keys.ABILITIES,"mineminenomi"));
		ABILITY_COMPONENTS = (DeferredRegister) (DeferredRegister.create(WyRegistry$Keys.ABILITY_COMPONENTS,"mineminenomi"));
		EFFECTS = (DeferredRegister) (DeferredRegister.create(Registries.MOB_EFFECT,"mineminenomi"));
		ENTITY_TYPES = (DeferredRegister) (DeferredRegister.create(Registries.ENTITY_TYPE,"mineminenomi"));
		BLOCK_ENTITIES = (DeferredRegister) (DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE,"mineminenomi"));
		PARTICLE_TYPES = (DeferredRegister) (DeferredRegister.create(Registries.PARTICLE_TYPE,"mineminenomi"));
		QUESTS = (DeferredRegister) (DeferredRegister.create(WyRegistry$Keys.QUESTS,"mineminenomi"));
		FEATURES = (DeferredRegister) (DeferredRegister.create(Registries.FEATURE,"mineminenomi"));
		SOUNDS = (DeferredRegister) (DeferredRegister.create(Registries.SOUND_EVENT,"mineminenomi"));
		ATTRIBUTES = (DeferredRegister) (DeferredRegister.create(Registries.ATTRIBUTE,"mineminenomi"));
		CHALLENGES = (DeferredRegister) (DeferredRegister.create(WyRegistry$Keys.CHALLENGES,"mineminenomi"));
		PARTICLE_EFFECTS = (DeferredRegister) (DeferredRegister.create(WyRegistry$Keys.PARTICLE_EFFECTS,"mineminenomi"));
		COMMAND_ARGUMENT_TYPES = (DeferredRegister) (DeferredRegister.create(Registries.COMMAND_ARGUMENT_TYPE,"mineminenomi"));
		JOLLY_ROGER_ELEMENTS = (DeferredRegister) (DeferredRegister.create(WyRegistry$Keys.JOLLY_ROGER_ELEMENTS,"mineminenomi"));
		MORPHS = (DeferredRegister) (DeferredRegister.create(WyRegistry$Keys.MORPHS,"mineminenomi"));
		CREATIVE_TABS = (DeferredRegister) (DeferredRegister.create(Registries.CREATIVE_MODE_TAB,"mineminenomi"));
		LOOT_FUNCTIONS = (DeferredRegister) (DeferredRegister.create(Registries.LOOT_FUNCTION_TYPE,"mineminenomi"));
		LOOT_CONDITIONS = (DeferredRegister) (DeferredRegister.create(Registries.LOOT_CONDITION_TYPE,"mineminenomi"));
		MEMORY_MODULE_TYPES = (DeferredRegister) (DeferredRegister.create(Registries.MEMORY_MODULE_TYPE,"mineminenomi"));
		RECIPE_SERIALIZERS = (DeferredRegister) (DeferredRegister.create(Registries.RECIPE_SERIALIZER,"mineminenomi"));
		FACTIONS = (DeferredRegister) (DeferredRegister.create(WyRegistry$Keys.FACTIONS,"mineminenomi"));
		FIGHTING_STYLES = (DeferredRegister) (DeferredRegister.create(WyRegistry$Keys.FIGHTING_STYLES,"mineminenomi"));
		RACES = (DeferredRegister) (DeferredRegister.create(WyRegistry$Keys.RACES,"mineminenomi"));
		CHUNK_GENERATORS = (DeferredRegister) (DeferredRegister.create(Registries.CHUNK_GENERATOR,"mineminenomi"));
		STRUCTURE_PLACEMENTS = (DeferredRegister) (DeferredRegister.create(Registries.STRUCTURE_PLACEMENT,"mineminenomi"));
		STRUCTURE_PROCESSOR = (DeferredRegister) (DeferredRegister.create(Registries.STRUCTURE_PROCESSOR,"mineminenomi"));
		ATTACHMENTS = (DeferredRegister) (DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES,"mineminenomi"));
		TRIGGERS = (DeferredRegister) (DeferredRegister.create(Registries.TRIGGER_TYPE,"mineminenomi"));
	}
}