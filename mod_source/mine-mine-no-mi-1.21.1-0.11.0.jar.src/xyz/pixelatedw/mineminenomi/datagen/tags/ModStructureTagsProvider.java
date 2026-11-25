package xyz.pixelatedw.mineminenomi.datagen.tags;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.init.ModStructures;
import xyz.pixelatedw.mineminenomi.init.ModTags$Structures;

public class ModStructureTagsProvider extends TagsProvider<Structure> {
	public ModStructureTagsProvider(PackOutput var1, CompletableFuture<HolderLookup.Provider> var2, @Nullable() ExistingFileHelper var3) {
		super(var1,Registries.STRUCTURE,var2,"mineminenomi",var3);
	}
	protected void addTags(HolderLookup.Provider var1) {
		this.tag(ModTags$Structures.IS_SMALL_SHIP).add(ModStructures.PIRATE_SMALL_SHIP);
		ResourceKey[] var2 = new ResourceKey[]{ModStructures.GHOST_SHIP,ModStructures.PIRATE_MEDIUM_SHIP};
		this.tag(ModTags$Structures.IS_MEDIUM_SHIP).add(var2);
		ResourceKey[] var3 = new ResourceKey[]{ModStructures.PIRATE_LARGE_SHIP,ModStructures.MARINE_BATTLESHIP};
		this.tag(ModTags$Structures.IS_LARGE_SHIP).add(var3);
		TagKey[] var4 = new TagKey[]{ModTags$Structures.IS_SMALL_SHIP,ModTags$Structures.IS_MEDIUM_SHIP,ModTags$Structures.IS_LARGE_SHIP};
		this.tag(ModTags$Structures.IS_SHIP).addTags(var4);
		this.tag(ModTags$Structures.IS_CAMP).add(ModStructures.BANDIT_CAMP);
		this.tag(ModTags$Structures.IS_WATCH_TOWER).add(ModStructures.MARINE_WATCH_TOWER);
		ResourceKey[] var5 = new ResourceKey[]{ModStructures.MARINE_SMALL_BASE,ModStructures.BANDIT_ARENA,ModStructures.BANDIT_FORT,ModStructures.BANDIT_HOUSE};
		this.tag(ModTags$Structures.IS_SMALL_BASE).add(var5);
		this.tag(ModTags$Structures.IS_LARGE_BASE).add(ModStructures.MARINE_LARGE_BASE);
		ResourceKey[] var6 = new ResourceKey[]{ModStructures.SWORDSMAN_DOJO,ModStructures.SNIPER_RANGE,ModStructures.BRAWLER_RING,ModStructures.BLACK_LEG_KITCHEN,ModStructures.MEDIC_TENT,ModStructures.TAVERN};
		this.tag(ModTags$Structures.IS_TRAINING_STRUCTURE).add(var6);
		ResourceKey[] var7 = new ResourceKey[]{ModStructures.SKY_ISLANDS_SINGLE,ModStructures.SKY_ISLANDS_TOWN};
		this.tag(ModTags$Structures.IS_SKY_ISLAND).add(var7);
		ResourceKey[] var8 = new ResourceKey[]{ModStructures.MARINE_BATTLESHIP,ModStructures.MARINE_LARGE_BASE,ModStructures.MARINE_SMALL_BASE,ModStructures.MARINE_WATCH_TOWER};
		this.tag(ModTags$Structures.MARINE).add(var8);
		ResourceKey[] var9 = new ResourceKey[]{ModStructures.PIRATE_SMALL_SHIP,ModStructures.PIRATE_MEDIUM_SHIP,ModStructures.PIRATE_LARGE_SHIP};
		this.tag(ModTags$Structures.PIRATE).add(var9);
		ResourceKey[] var10 = new ResourceKey[]{ModStructures.BANDIT_ARENA,ModStructures.BANDIT_CAMP,ModStructures.BANDIT_FORT,ModStructures.BANDIT_HOUSE};
		this.tag(ModTags$Structures.BANDIT).add(var10);
	}
}