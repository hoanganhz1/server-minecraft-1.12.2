package xyz.pixelatedw.mineminenomi.init;

import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.items.AbilityIconItem;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiBoxItem;
import xyz.pixelatedw.mineminenomi.items.BellyPouchItem;
import xyz.pixelatedw.mineminenomi.items.BottleOfRumItem;
import xyz.pixelatedw.mineminenomi.items.BubblyCoralItem;
import xyz.pixelatedw.mineminenomi.items.CannonItem;
import xyz.pixelatedw.mineminenomi.items.ChallengePosterItem;
import xyz.pixelatedw.mineminenomi.items.CharacterCreatorItem;
import xyz.pixelatedw.mineminenomi.items.CigarItem;
import xyz.pixelatedw.mineminenomi.items.ColaItem;
import xyz.pixelatedw.mineminenomi.items.CookedSeaKingMeatItem;
import xyz.pixelatedw.mineminenomi.items.DFEncyclopediaItem;
import xyz.pixelatedw.mineminenomi.items.DandelionItem;
import xyz.pixelatedw.mineminenomi.items.ExtolPouchItem;
import xyz.pixelatedw.mineminenomi.items.GoldDenDenMushiItem;
import xyz.pixelatedw.mineminenomi.items.HandcuffsItem;
import xyz.pixelatedw.mineminenomi.items.HeartItem;
import xyz.pixelatedw.mineminenomi.items.NetItem;
import xyz.pixelatedw.mineminenomi.items.SakeBottleItem;
import xyz.pixelatedw.mineminenomi.items.SakeCupItem;
import xyz.pixelatedw.mineminenomi.items.SeaKingMeatItem;
import xyz.pixelatedw.mineminenomi.items.ShadowItem;
import xyz.pixelatedw.mineminenomi.items.StrawDollItem;
import xyz.pixelatedw.mineminenomi.items.StrikerItem;
import xyz.pixelatedw.mineminenomi.items.TangerineItem;
import xyz.pixelatedw.mineminenomi.items.UltraColaItem;
import xyz.pixelatedw.mineminenomi.items.UnicycleItem;
import xyz.pixelatedw.mineminenomi.items.VivreCardItem;
import xyz.pixelatedw.mineminenomi.items.WantedPosterPackageItem;
import xyz.pixelatedw.mineminenomi.items.WateringCanItem;
import xyz.pixelatedw.mineminenomi.items.bullets.BulletItem;
import xyz.pixelatedw.mineminenomi.items.bullets.CannonBallItem;
import xyz.pixelatedw.mineminenomi.items.bullets.KairosekiBulletItem;
import xyz.pixelatedw.mineminenomi.items.bullets.NormalBulletItem;

public class ModItems {
	// Fields
	public static final Supplier<Item> CHARACTER_CREATOR = ModRegistry.registerItem("Character Creator",(Supplier)(((Supplier<Item>)(CharacterCreatorItem::new))));
	public static final Supplier<Item> KAIROSEKI;
	public static final Supplier<Item> DENSE_KAIROSEKI;
	public static final Supplier<Item> BELLY_POUCH;
	public static final Supplier<Item> EXTOL_POUCH;
	public static final Supplier<Item> KEY;
	public static final Supplier<Item> VIVRE_CARD;
	public static final Supplier<Item> BUBBLY_CORAL;
	public static final Supplier<Item> GOLD_DEN_DEN_MUSHI;
	public static final Supplier<Item> NORMAL_HANDCUFFS;
	public static final Supplier<Item> KAIROSEKI_HANDCUFFS;
	public static final Supplier<Item> EXPLOSIVE_HANDCUFFS;
	public static final Supplier<Item> DEVIL_FRUIT_ENCYCLOPEDIA;
	public static final Supplier<Item> CHALLENGE_POSTER;
	public static final Supplier<NetItem> ROPE_NET;
	public static final Supplier<NetItem> KAIROSEKI_NET;
	public static final Supplier<Item> WANTED_POSTER_PACKAGE;
	public static final Supplier<AbilityIconItem> ABILITY_ICON;
	public static final Supplier<Item> STRIKER;
	public static final Supplier<Item> UNICYCLE;
	public static final Supplier<Item> CANNON;
	public static final Supplier<Item> SHADOW;
	public static final Supplier<Item> DANDELION;
	public static final Supplier<Item> HEART;
	public static final Supplier<Item> COLOR_PALETTE;
	public static final Supplier<Item> WATERING_CAN;
	public static final Supplier<Item> STRAW_DOLL;
	public static final Supplier<AkumaNoMiBoxItem> TIER_1_BOX;
	public static final Supplier<AkumaNoMiBoxItem> TIER_2_BOX;
	public static final Supplier<AkumaNoMiBoxItem> TIER_3_BOX;
	public static final Supplier<BulletItem> BULLET;
	public static final Supplier<BulletItem> KAIROSEKI_BULLET;
	public static final Supplier<Item> POP_GREEN;
	public static final Supplier<BulletItem> CANNON_BALL;
	public static final Supplier<Item> SEA_KING_MEAT;
	public static final Supplier<Item> COOKED_SEA_KING_MEAT;
	public static final Supplier<Item> COLA;
	public static final Supplier<Item> ULTRA_COLA;
	public static final Supplier<Item> EMPTY_COLA;
	public static final Supplier<Item> EMPTY_ULTRA_COLA;
	public static final Supplier<Item> SAKE_BOTTLE;
	public static final Supplier<Item> SAKE_CUP;
	public static final Supplier<Item> BOTTLE_OF_RUM;
	public static final Supplier<Item> TANGERINE;
	public static final Supplier<Item> THREE_CIGARS;
	public static final Supplier<Item> CIGARETTE;
	public static final Supplier<Item> SMOKING_PIPE;
	public static final Supplier<Item> CIGAR;

	public static void init() {
	}
	static  {
		Supplier<Item> var0 = () -> {
			return new Item(new Item.Properties());
		};
		KAIROSEKI = ModRegistry.registerItem("Kairoseki",var0);
		Supplier<Item> var1 = () -> {
			return new Item(new Item.Properties());
		};
		DENSE_KAIROSEKI = ModRegistry.registerItem("Dense Kairoseki",var1);
		BELLY_POUCH = ModRegistry.registerItem("Belly Pouch",(Supplier)(((Supplier<Item>)(BellyPouchItem::new))));
		EXTOL_POUCH = ModRegistry.registerItem("Extol Pouch",(Supplier)(((Supplier<Item>)(ExtolPouchItem::new))));
		Supplier<Item> var2 = () -> {
			return new Item(new Item.Properties());
		};
		KEY = ModRegistry.registerItem("Key",var2);
		VIVRE_CARD = ModRegistry.registerItem("Vivre Card",(Supplier)(((Supplier<Item>)(VivreCardItem::new))));
		BUBBLY_CORAL = ModRegistry.registerItem("Bubbly Coral",(Supplier)(((Supplier<Item>)(BubblyCoralItem::new))));
		GOLD_DEN_DEN_MUSHI = ModRegistry.registerItem("Golden Den Den Mushi",(Supplier)(((Supplier<Item>)(GoldDenDenMushiItem::new))));
		Supplier<Item> var3 = () -> {
			return new HandcuffsItem((Holder)(ModEffects.HANDCUFFED));
		};
		NORMAL_HANDCUFFS = ModRegistry.registerItem("Handcuffs",var3);
		Supplier<Item> var4 = () -> {
			return new HandcuffsItem((Holder)(ModEffects.HANDCUFFED_KAIROSEKI));
		};
		KAIROSEKI_HANDCUFFS = ModRegistry.registerItem("Kairoseki Handcuffs",var4);
		Supplier<Item> var5 = () -> {
			return new HandcuffsItem((Holder)(ModEffects.HANDCUFFED_EXPLOSIVE));
		};
		EXPLOSIVE_HANDCUFFS = ModRegistry.registerItem("Explosive Handcuffs",var5);
		DEVIL_FRUIT_ENCYCLOPEDIA = ModRegistry.registerItem("Devil Fruit Encyclopedia",(Supplier)(((Supplier<Item>)(DFEncyclopediaItem::new))));
		CHALLENGE_POSTER = ModRegistry.registerItem("Challenge Poster",(Supplier)(((Supplier<Item>)(ChallengePosterItem::new))));
		Supplier<NetItem> var6 = () -> {
			return new NetItem(ModEffects.CAUGHT_IN_NET);
		};
		ROPE_NET = ModRegistry.registerItem("Rope Net",var6);
		Supplier<NetItem> var7 = () -> {
			return new NetItem(ModEffects.CAUGHT_IN_KAIROSEKI_NET);
		};
		KAIROSEKI_NET = ModRegistry.registerItem("Kairoseki Net",var7);
		WANTED_POSTER_PACKAGE = ModRegistry.registerItem("Wanted Poster Package",(Supplier)(((Supplier<Item>)(WantedPosterPackageItem::new))));
		ABILITY_ICON = ModRegistry.registerItem("Ability Icon",(Supplier)(((Supplier<AbilityIconItem>)(AbilityIconItem::new))));
		STRIKER = ModRegistry.registerItem("Striker",(Supplier)(((Supplier<Item>)(StrikerItem::new))));
		UNICYCLE = ModRegistry.registerItem("Unicycle",(Supplier)(((Supplier<Item>)(UnicycleItem::new))));
		CANNON = ModRegistry.registerItem("Cannon",(Supplier)(((Supplier<Item>)(CannonItem::new))));
		SHADOW = ModRegistry.registerItem("Shadow",(Supplier)(((Supplier<Item>)(ShadowItem::new))));
		DANDELION = ModRegistry.registerItem("Dandelion",(Supplier)(((Supplier<Item>)(DandelionItem::new))));
		HEART = ModRegistry.registerItem("Heart",(Supplier)(((Supplier<Item>)(HeartItem::new))));
		Supplier<Item> var8 = () -> {
			return new Item(new Item.Properties().stacksTo(1));
		};
		COLOR_PALETTE = ModRegistry.registerItem("Color Palette",var8);
		WATERING_CAN = ModRegistry.registerItem("Watering Can",(Supplier)(((Supplier<Item>)(WateringCanItem::new))));
		STRAW_DOLL = ModRegistry.registerItem("Straw Doll",(Supplier)(((Supplier<Item>)(StrawDollItem::new))));
		Supplier<AkumaNoMiBoxItem> var9 = () -> {
			return new AkumaNoMiBoxItem(ModLootTables.TIER_1_FRUITS);
		};
		TIER_1_BOX = ModRegistry.registerItem("Wooden Box",var9);
		Supplier<AkumaNoMiBoxItem> var10 = () -> {
			return new AkumaNoMiBoxItem(ModLootTables.TIER_2_FRUITS);
		};
		TIER_2_BOX = ModRegistry.registerItem("Iron Box",var10);
		Supplier<AkumaNoMiBoxItem> var11 = () -> {
			return new AkumaNoMiBoxItem(ModLootTables.TIER_3_FRUITS);
		};
		TIER_3_BOX = ModRegistry.registerItem("Golden Box",var11);
		BULLET = ModRegistry.registerItem("Bullet",(Supplier)(((Supplier<BulletItem>)(NormalBulletItem::new))));
		KAIROSEKI_BULLET = ModRegistry.registerItem("Kairoseki Bullet",(Supplier)(((Supplier<BulletItem>)(KairosekiBulletItem::new))));
		Supplier<Item> var12 = () -> {
			return new Item(new Item.Properties());
		};
		POP_GREEN = ModRegistry.registerItem("Pop Green",var12);
		CANNON_BALL = ModRegistry.registerItem("Cannon Ball",(Supplier)(((Supplier<BulletItem>)(CannonBallItem::new))));
		SEA_KING_MEAT = ModRegistry.registerItem("Sea King Meat",(Supplier)(((Supplier<Item>)(SeaKingMeatItem::new))));
		COOKED_SEA_KING_MEAT = ModRegistry.registerItem("Cooked Sea King Meat",(Supplier)(((Supplier<Item>)(CookedSeaKingMeatItem::new))));
		COLA = ModRegistry.registerItem("Cola",(Supplier)(((Supplier<Item>)(ColaItem::new))));
		ULTRA_COLA = ModRegistry.registerItem("Ultra Cola",(Supplier)(((Supplier<Item>)(UltraColaItem::new))));
		Supplier<Item> var13 = () -> {
			return new Item(new Item.Properties().stacksTo(16));
		};
		EMPTY_COLA = ModRegistry.registerItem("Empty Cola",var13);
		Supplier<Item> var14 = () -> {
			return new Item(new Item.Properties().stacksTo(16));
		};
		EMPTY_ULTRA_COLA = ModRegistry.registerItem("Empty Ultra Cola",var14);
		SAKE_BOTTLE = ModRegistry.registerItem("Sake Bottle",(Supplier)(((Supplier<Item>)(SakeBottleItem::new))));
		SAKE_CUP = ModRegistry.registerItem("Sake Cup",(Supplier)(((Supplier<Item>)(SakeCupItem::new))));
		BOTTLE_OF_RUM = ModRegistry.registerItem("Bottle of Rum",(Supplier)(((Supplier<Item>)(BottleOfRumItem::new))));
		TANGERINE = ModRegistry.registerItem("Tangerine",(Supplier)(((Supplier<Item>)(TangerineItem::new))));
		Supplier<Item> var15 = () -> {
			return new CigarItem(10);
		};
		THREE_CIGARS = ModRegistry.registerItem("Three Cigars",var15);
		Supplier<Item> var16 = () -> {
			return new CigarItem(15);
		};
		CIGARETTE = ModRegistry.registerItem("Cigarette",var16);
		Supplier<Item> var17 = () -> {
			return new CigarItem(15);
		};
		SMOKING_PIPE = ModRegistry.registerItem("Smoking Pipe",var17);
		Supplier<Item> var18 = () -> {
			return new CigarItem(10);
		};
		CIGAR = ModRegistry.registerItem("Cigar",var18);
	}
}