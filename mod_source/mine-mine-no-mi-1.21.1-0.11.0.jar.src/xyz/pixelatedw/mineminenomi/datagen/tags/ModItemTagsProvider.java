package xyz.pixelatedw.mineminenomi.datagen.tags;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import xyz.pixelatedw.mineminenomi.api.enums.FruitType;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModTags$Blocks;
import xyz.pixelatedw.mineminenomi.init.ModTags$Items;
import xyz.pixelatedw.mineminenomi.init.ModValues;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;

public class ModItemTagsProvider extends ItemTagsProvider {
	public ModItemTagsProvider(PackOutput var1, CompletableFuture<HolderLookup.Provider> var2, CompletableFuture<TagsProvider.TagLookup<Block>> var3, ExistingFileHelper var4) {
		super(var1,var2,var3,"mineminenomi",var4);
	}
	protected void addTags(HolderLookup.Provider var1) {
		Item[] var2 = new Item[]{((Item)(ModItems.SEA_KING_MEAT.get())),((Item)(ModItems.COOKED_SEA_KING_MEAT.get()))};
		this.tag(ItemTags.MEAT).add(var2);
		this.copy(ModTags$Blocks.MANGROVE_LOGS,ModTags$Items.MANGROVE_LOGS);
		Item[] var3 = new Item[]{((Item)(ModItems.TIER_1_BOX.get())),((Item)(ModItems.TIER_2_BOX.get())),((Item)(ModItems.TIER_3_BOX.get())),((Item)(ModItems.CHALLENGE_POSTER.get()))};
		this.tag(ModTags$Items.BANNED_ITEMS_CHALLANGES).add(var3);
		Item[] var4 = new Item[]{((Item)(ModItems.KAIROSEKI.get())),((Item)(ModItems.KAIROSEKI_BULLET.get())),((Item)(ModItems.KAIROSEKI_HANDCUFFS.get())),((Item)(ModItems.KAIROSEKI_NET.get())),((Item)(ModItems.DENSE_KAIROSEKI.get()))};
		this.tag(ModTags$Items.KAIROSEKI).add(var4);
		this.copy(ModTags$Blocks.KAIROSEKI,ModTags$Items.KAIROSEKI);
		Item[] var5 = new Item[]{((Item)(ModWeapons.SCISSORS.get())),((Item)(ModWeapons.KATANA.get())),((Item)(ModWeapons.BROADSWORD.get())),((Item)(ModWeapons.CLEAVER.get())),((Item)(ModWeapons.DAGGER.get())),((Item)(ModWeapons.AXE.get())),((Item)(ModWeapons.CUTLASS.get())),((Item)(ModWeapons.SAMEKIRI_BOCHO.get())),((Item)(ModWeapons.CAT_CLAWS.get())),((Item)(ModWeapons.GRYPHON.get())),((Item)(ModWeapons.ACE.get())),((Item)(ModWeapons.ACES_KNIFE.get())),((Item)(ModWeapons.MIHAWKS_KNIFE.get())),((Item)(ModWeapons.SANDAI_KITETSU.get())),((Item)(ModWeapons.WADO_ICHIMONJI.get())),((Item)(ModWeapons.NIDAI_KITETSU.get())),((Item)(ModWeapons.SHUSUI.get())),((Item)(ModWeapons.ENMA.get())),((Item)(ModWeapons.AME_NO_HABAKIRI.get())),((Item)(ModWeapons.SOUL_SOLID.get())),((Item)(ModWeapons.DURANDAL.get())),((Item)(ModWeapons.KIKOKU.get())),((Item)(ModWeapons.KIRIBACHI.get())),((Item)(ModWeapons.YORU.get())),((Item)(ModWeapons.HOOK.get())),((Item)(ModWeapons.CHAKRAM.get())),((Item)(ModWeapons.AXE_HAND.get())),((Item)(ModWeapons.DALTONS_SPADE.get()))};
		Item[] var6 = var5;
		Item[] var7 = new Item[]{((Item)(ModWeapons.ICE_SABER.get())),((Item)(ModWeapons.AMA_NO_MURAKUMO.get())),((Item)(ModWeapons.NORO_NORO_BEAM_SWORD.get())),((Item)(ModWeapons.DORU_DORU_ARTS_KEN.get())),((Item)(ModWeapons.BLUE_SWORD.get())),((Item)(ModWeapons.TABIRA_YUKI.get())),((Item)(ModWeapons.WARABIDE_SWORD.get())),((Item)(ModWeapons.GAMMA_KNIFE.get()))};
		Item[] var8 = var7;
		Item[] var9 = new Item[]{((Item)(ModWeapons.PIPE.get())),((Item)(ModWeapons.TONFA.get())),((Item)(ModWeapons.MACE.get())),((Item)(ModWeapons.HASSAIKAI.get())),((Item)(ModWeapons.HAMMER_5T.get())),((Item)(ModWeapons.CLIMA_TACT.get())),((Item)(ModWeapons.PERFECT_CLIMA_TACT.get())),((Item)(ModWeapons.SORCERY_CLIMA_TACT.get())),((Item)(ModWeapons.NONOSAMA_STAFF.get()))};
		Item[] var10 = var9;
		Item[] var11 = new Item[]{((Item)(ModWeapons.BARRIERBILITY_BAT.get()))};
		Item[] var12 = var11;
		Item[] var13 = new Item[]{((Item)(ModWeapons.BLACK_KABUTO.get())),((Item)(ModWeapons.GINGA_PACHINKO.get())),((Item)(ModWeapons.KABUTO.get())),((Item)(ModWeapons.KUJA_BOW.get()))};
		Item[] var14 = var13;
		Item[] var15 = new Item[]{((Item)(ModWeapons.FLINTLOCK.get())),((Item)(ModWeapons.SENRIKU.get())),((Item)(ModWeapons.BAZOOKA.get())),((Item)(ModWeapons.WALKER.get()))};
		Item[] var16 = var15;
		Item[] var17 = new Item[]{((Item)(ModWeapons.DALTONS_SPADE.get())),((Item)(ModWeapons.DAISENSO.get())),((Item)(ModWeapons.MOGURA.get())),((Item)(ModWeapons.MURAKUMOGIRI.get())),((Item)(ModWeapons.NONOSAMA_STAFF.get())),((Item)(ModWeapons.NONOSAMA_TRIDENT.get())),((Item)(ModWeapons.CLIMA_TACT.get())),((Item)(ModWeapons.PERFECT_CLIMA_TACT.get())),((Item)(ModWeapons.SORCERY_CLIMA_TACT.get())),((Item)(ModWeapons.SPEAR.get())),((Item)(ModWeapons.PIPE.get())),((Item)(ModWeapons.BISENTO.get()))};
		Item[] var18 = var17;
		Item[] var19 = new Item[]{Items.IRON_INGOT,Items.IRON_ORE,Items.IRON_BLOCK,Items.RAW_IRON,Items.RAW_IRON_BLOCK,Items.IRON_NUGGET,Items.IRON_BARS,Items.IRON_TRAPDOOR,Items.ANVIL,Items.IRON_DOOR,Items.CAULDRON,Items.HOPPER,Items.MINECART,Items.CHEST_MINECART,Items.COMMAND_BLOCK_MINECART,Items.FURNACE_MINECART,Items.HOPPER_MINECART,Items.TNT_MINECART,Items.RAIL,Items.IRON_AXE,Items.IRON_HOE,Items.IRON_PICKAXE,Items.IRON_SHOVEL,Items.IRON_SWORD,Items.BUCKET,Items.COMPASS,Items.SHIELD,Items.FLINT_AND_STEEL,Items.IRON_HELMET,Items.IRON_CHESTPLATE,Items.IRON_LEGGINGS,Items.IRON_BOOTS,Items.IRON_HORSE_ARMOR,Items.SHEARS,Items.CHAIN,((Item)(ModItems.NORMAL_HANDCUFFS.get())),((Item)(ModItems.EXPLOSIVE_HANDCUFFS.get())),((Item)(ModItems.TIER_2_BOX.get())),((Item)(ModItems.BULLET.get())),((Item)(ModItems.CANNON_BALL.get()))};
		Item var20 = ((Item)(ModWeapons.CLIMA_TACT.get()));
		Item[] var21 = new Item[]{((Item)(ModWeapons.PERFECT_CLIMA_TACT.get())),((Item)(ModWeapons.SORCERY_CLIMA_TACT.get())),((Item)(ModWeapons.NONOSAMA_STAFF.get()))};
		this.tag(ModTags$Items.MAGNETIC).add(var6).add(var10).add(var16).add(var18).remove(ModTags$Items.CLIMA_TACTS).add(var19).remove(var20,var21);
		this.tag(ItemTags.SWORDS).add(var6).add(var8);
		this.tag(ModTags$Items.BLUNTS).add(var10).add(var12);
		this.tag(ModTags$Items.GUNS).add(var16);
		this.tag(ModTags$Items.POLEARMS).add(var18);
		this.tag(Tags.Items.TOOLS_BOW).add(var14);
		this.tag(Tags.Items.TOOLS_SPEAR).add((Object)(((Item)(ModWeapons.NONOSAMA_TRIDENT.get()))));
		Item[] var22 = new Item[]{((Item)(ModWeapons.CLIMA_TACT.get())),((Item)(ModWeapons.PERFECT_CLIMA_TACT.get())),((Item)(ModWeapons.SORCERY_CLIMA_TACT.get()))};
		this.tag(ModTags$Items.CLIMA_TACTS).add(var22);
		this.tag(ModTags$Items.RUSTY).addTag(ModTags$Items.MAGNETIC);
		this.copy(ModTags$Blocks.RUSTY,ModTags$Items.RUSTY);
		Predicate<AkumaNoMiItem> var23 = (l0) -> {
			return (l0.getType()) == (FruitType.PARAMECIA);
		};
		ModItemTagsProvider var24 = this;
		Consumer<AkumaNoMiItem> var25 = (l0) -> {
			this.tag(ModTags$Items.PARAMECIA).add((Object)(l0));
		};
		((java.util.stream.Stream)(((java.util.stream.Stream)(ModValues.DEVIL_FRUITS.stream())).filter((Predicate)(var23)))).forEach((Consumer)(var25));
		Predicate<AkumaNoMiItem> var26 = (l0) -> {
			return (l0.getType()) == (FruitType.LOGIA);
		};
		ModItemTagsProvider var27 = this;
		Consumer<AkumaNoMiItem> var28 = (l0) -> {
			this.tag(ModTags$Items.LOGIA).add((Object)(l0));
		};
		((java.util.stream.Stream)(((java.util.stream.Stream)(ModValues.DEVIL_FRUITS.stream())).filter((Predicate)(var26)))).forEach((Consumer)(var28));
		Predicate<AkumaNoMiItem> var29 = (l0) -> {
			return ((l0.getType()) == (FruitType.ZOAN)) || (((l0.getType()) == (FruitType.ANCIENT_ZOAN)) || ((l0.getType()) == (FruitType.MYTHICAL_ZOAN)));
		};
		ModItemTagsProvider var30 = this;
		Consumer<AkumaNoMiItem> var31 = (l0) -> {
			this.tag(ModTags$Items.ZOAN).add((Object)(l0));
		};
		((java.util.stream.Stream)(((java.util.stream.Stream)(ModValues.DEVIL_FRUITS.stream())).filter((Predicate)(var29)))).forEach((Consumer)(var31));
		this.tag(ModTags$Items.DEVIL_FRUIT).addTag(ModTags$Items.PARAMECIA).addTag(ModTags$Items.LOGIA).addTag(ModTags$Items.ZOAN);
		Item[] var32 = new Item[]{((Item)(ModWeapons.YORU.get())),((Item)(ModWeapons.MURAKUMOGIRI.get())),((Item)(ModWeapons.ACE.get()))};
		this.tag(ModTags$Items.SUPREME_GRADE).add(var32);
		Item[] var33 = new Item[]{((Item)(ModWeapons.NIDAI_KITETSU.get())),((Item)(ModWeapons.WADO_ICHIMONJI.get())),((Item)(ModWeapons.ENMA.get())),((Item)(ModWeapons.SHUSUI.get())),((Item)(ModWeapons.AME_NO_HABAKIRI.get()))};
		this.tag(ModTags$Items.GREAT_GRADE).add(var33);
		Item[] var34 = new Item[]{Items.APPLE,((Item)(ModItems.TANGERINE.get()))};
		this.tag(ModTags$Items.FRUIT_REINCARNATION).add(var34);
		Item[] var35 = new Item[]{((Item)(ModItems.BULLET.get())),((Item)(ModItems.KAIROSEKI_BULLET.get()))};
		this.tag(ModTags$Items.GUN_AMMO).add(var35);
		this.tag(ModTags$Items.BAZOOKA_AMMO).add((Object)(((Item)(ModItems.CANNON_BALL.get()))));
		Item[] var36 = new Item[]{((Item)(ModWeapons.JITTE.get())),((Item)(ModWeapons.NONOSAMA_STAFF.get())),((Item)(ModWeapons.NONOSAMA_TRIDENT.get()))};
		this.tag(ModTags$Items.RUST_IMMUNITY).add(var36);
		this.tag(ModTags$Items.HARDENING_DAMAGE_BONUS);
		this.tag(ModTags$Items.IMBUING_DAMAGE_BONUS).addTag(ModTags$Items.BLUNTS).addTag(ItemTags.SWORDS).addTag(ModTags$Items.CLIMA_TACTS).addTag(ModTags$Items.GUNS).addTag(Tags.Items.TOOLS_BOW);
		this.tag(ModTags$Items.MELEE_ENCHANTABLE_BY_SMITHING).addTag(ItemTags.SWORDS).addTag(ModTags$Items.BLUNTS).addTag(ModTags$Items.CLIMA_TACTS);
		this.tag(ModTags$Items.RANGED_ENCHANTABLE_BY_SMITHING).addTag(Tags.Items.TOOLS_BOW).addTag(ModTags$Items.GUNS);
		this.tag(ItemTags.LOGS).addTag(ModTags$Items.MANGROVE_LOGS);
		this.tag(ItemTags.PLANKS).add((Object)(((Block)(ModBlocks.MANGROVE_PLANKS.get())).asItem()));
		this.tag(ModTags$Items.WEAPONS).addTag(ItemTags.SWORDS).addTag(ItemTags.AXES).addTag(Tags.Items.TOOLS_SPEAR).addTag(Tags.Items.TOOLS_BOW).addTag(Tags.Items.TOOLS_CROSSBOW).addTag(ModTags$Items.BLUNTS).addTag(ModTags$Items.GUNS).addTag(ModTags$Items.POLEARMS);
		this.tag(ModTags$Items.TECHNOLOGY).add((Object)(((Block)(ModBlocks.AXE_DIAL.get())).asItem())).add((Object)(((Block)(ModBlocks.BREATH_DIAL.get())).asItem())).add((Object)(((Block)(ModBlocks.EISEN_DIAL.get())).asItem())).add((Object)(((Block)(ModBlocks.FLAME_DIAL.get())).asItem())).add((Object)(((Block)(ModBlocks.FLASH_DIAL.get())).asItem())).add((Object)(((Block)(ModBlocks.IMPACT_DIAL.get())).asItem())).add((Object)(((Block)(ModBlocks.MILKY_DIAL.get())).asItem())).add((Object)(((Block)(ModBlocks.REJECT_DIAL.get())).asItem())).addTag(ModTags$Items.CLIMA_TACTS);
		Item[] var37 = new Item[]{Items.GLOW_BERRIES,Items.SWEET_BERRIES};
		Item[] var38 = var37;
		Item[] var39 = new Item[]{Items.BEETROOT_SEEDS,Items.MELON_SEEDS,Items.PUMPKIN_SEEDS,Items.TORCHFLOWER_SEEDS,Items.WHEAT_SEEDS};
		Item[] var40 = var39;
		this.tag(ModTags$Items.DUGONG_FOOD).addTag(ItemTags.MEAT).addTag(ItemTags.FISHES).add((Object)(Items.KELP));
		this.tag(ModTags$Items.YAGARA_FOOD).add((Object)(Items.TROPICAL_FISH));
		this.tag(ModTags$Items.WHITE_WALKIE_FOOD).addTag(ItemTags.MEAT);
		this.tag(ModTags$Items.BANANAWANI_FOOD).addTag(ItemTags.FISHES);
		this.tag(ModTags$Items.BIG_DUCK_FOOD).add(var38).add(var40);
		this.tag(ModTags$Items.FLYING_FISH_FOOD).addTag(ItemTags.FISHES);
	}
}