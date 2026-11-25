package xyz.pixelatedw.mineminenomi.datagen.loottables;

import java.util.function.BiConsumer;
import java.util.function.Function;
import net.minecraft.advancements.critereon.EntityFlagsPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import xyz.pixelatedw.mineminenomi.datagen.loottables.entities.BigDuckDrops;
import xyz.pixelatedw.mineminenomi.datagen.loottables.entities.BruteDrops;
import xyz.pixelatedw.mineminenomi.datagen.loottables.entities.CaptainDrops;
import xyz.pixelatedw.mineminenomi.datagen.loottables.entities.DenDenMushiDrops;
import xyz.pixelatedw.mineminenomi.datagen.loottables.entities.GorillaDrops;
import xyz.pixelatedw.mineminenomi.datagen.loottables.entities.GruntDrops;
import xyz.pixelatedw.mineminenomi.datagen.loottables.entities.LapahnDrops;
import xyz.pixelatedw.mineminenomi.datagen.loottables.entities.NormalDugongDrops;
import xyz.pixelatedw.mineminenomi.datagen.loottables.entities.RareDugongDrops;
import xyz.pixelatedw.mineminenomi.datagen.loottables.entities.SeaCowDrops;
import xyz.pixelatedw.mineminenomi.datagen.loottables.entities.SkypieanDrops;
import xyz.pixelatedw.mineminenomi.datagen.loottables.entities.SkypieanTraderDrops;
import xyz.pixelatedw.mineminenomi.datagen.loottables.entities.SniperDrops;
import xyz.pixelatedw.mineminenomi.datagen.loottables.entities.TraderDrops;
import xyz.pixelatedw.mineminenomi.datagen.loottables.entities.WhiteWalkieDrops;
import xyz.pixelatedw.mineminenomi.datagen.loottables.entities.YagaraBullDrops;
import xyz.pixelatedw.mineminenomi.init.ModMobs;

public final record EntitiesLootTablesDataGen(HolderLookup.Provider registries) {
	// Fields
	public static final EntityPredicate ON_FIRE_CHECK = EntityPredicate.Builder.entity().flags(EntityFlagsPredicate.Builder.flags().setOnFire(Boolean.valueOf(true))).build();

	public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> var1) {
		this.addLootTable(var1,((EntityType)(ModMobs.KUNG_FU_DUGONG.get())),NormalDugongDrops.getTable(this.registries));
		this.addLootTable(var1,((EntityType)(ModMobs.BOXING_DUGONG.get())),NormalDugongDrops.getTable(this.registries));
		this.addLootTable(var1,((EntityType)(ModMobs.WRESTLING_DUGONG.get())),NormalDugongDrops.getTable(this.registries));
		this.addLootTable(var1,((EntityType)(ModMobs.LEGENDARY_MASTER_DUGONG.get())),RareDugongDrops.getTable(this.registries));
		this.addLootTable(var1,((EntityType)(ModMobs.WANDERING_DUGONG.get())),RareDugongDrops.getTable(this.registries));
		this.addLootTable(var1,((EntityType)(ModMobs.LAPAHN.get())),LapahnDrops.getTable(this.registries));
		this.addLootTable(var1,((EntityType)(ModMobs.WHITE_WALKIE.get())),WhiteWalkieDrops.getTable(this.registries));
		this.addLootTable(var1,((EntityType)(ModMobs.SEA_COW.get())),SeaCowDrops.getTable(this.registries));
		this.addLootTable(var1,((EntityType)(ModMobs.DEN_DEN_MUSHI.get())),DenDenMushiDrops.getTable(this.registries));
		this.addLootTable(var1,((EntityType)(ModMobs.YAGARA_BULL.get())),YagaraBullDrops.getTable(this.registries));
		this.addLootTable(var1,((EntityType)(ModMobs.BIG_DUCK.get())),BigDuckDrops.getTable(this.registries));
		this.addLootTable(var1,((EntityType)(ModMobs.BLUGORI.get())),GorillaDrops.getTable(this.registries));
		this.addLootTable(var1,((EntityType)(ModMobs.BLAGORI.get())),GorillaDrops.getTable(this.registries));
		this.addLootTable(var1,((EntityType)(ModMobs.MARINE_GRUNT.get())),GruntDrops.getTable(this.registries));
		this.addLootTable(var1,((EntityType)(ModMobs.PIRATE_GRUNT.get())),GruntDrops.getTable(this.registries));
		this.addLootTable(var1,((EntityType)(ModMobs.BANDIT_GRUNT.get())),GruntDrops.getTable(this.registries));
		this.addLootTable(var1,((EntityType)(ModMobs.MARINE_SNIPER.get())),SniperDrops.getTable(this.registries));
		this.addLootTable(var1,((EntityType)(ModMobs.BANDIT_SNIPER.get())),SniperDrops.getTable(this.registries));
		this.addLootTable(var1,((EntityType)(ModMobs.MARINE_BRUTE.get())),BruteDrops.getTable(this.registries));
		this.addLootTable(var1,((EntityType)(ModMobs.PIRATE_BRUTE.get())),BruteDrops.getTable(this.registries));
		this.addLootTable(var1,((EntityType)(ModMobs.BANDIT_BRUTE.get())),BruteDrops.getTable(this.registries));
		this.addLootTable(var1,((EntityType)(ModMobs.MARINE_CAPTAIN.get())),CaptainDrops.getTable(this.registries));
		this.addLootTable(var1,((EntityType)(ModMobs.PIRATE_CAPTAIN.get())),CaptainDrops.getTable(this.registries));
		this.addLootTable(var1,((EntityType)(ModMobs.BANDIT_CAPTAIN.get())),CaptainDrops.getTable(this.registries));
		this.addLootTable(var1,((EntityType)(ModMobs.MARINE_TRADER.get())),TraderDrops.getTable());
		this.addLootTable(var1,((EntityType)(ModMobs.PIRATE_TRADER.get())),TraderDrops.getTable());
		this.addLootTable(var1,((EntityType)(ModMobs.SKYPIEAN_TRADER.get())),SkypieanTraderDrops.getTable());
		this.addLootTable(var1,((EntityType)(ModMobs.SKYPIEAN_CIVILIAN.get())),SkypieanDrops.getTable());
	}
	public void addLootTable(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> var1, EntityType<?> var2, LootTable.Builder var3) {
		String var4 = EntityType.getKey(var2).getPath();
		String var5 = var4;
		String var6 = "entities/" + var5;
		var1.accept(ResourceKey.create(Registries.LOOT_TABLE,ResourceLocation.fromNamespaceAndPath("mineminenomi",var6)),var3);
	}
	public static LootTableProvider.SubProviderEntry create() {
		Function<HolderLookup.Provider, LootTableSubProvider> var0 = EntitiesLootTablesDataGen::new;
		return new LootTableProvider.SubProviderEntry(var0,LootContextParamSets.ENTITY);
	}
}