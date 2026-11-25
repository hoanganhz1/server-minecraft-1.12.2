package xyz.pixelatedw.mineminenomi.init;

import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import xyz.pixelatedw.mineminenomi.abilities.bari.BarrierbilityBatAbility;
import xyz.pixelatedw.mineminenomi.abilities.doru.DoruDoruArtsKenAbility;
import xyz.pixelatedw.mineminenomi.abilities.hie.IceSaberAbility;
import xyz.pixelatedw.mineminenomi.abilities.noro.NoroNoroBeamSwordAbility;
import xyz.pixelatedw.mineminenomi.abilities.ope.GammaKnifeAbility;
import xyz.pixelatedw.mineminenomi.abilities.pika.AmaNoMurakumoAbility;
import xyz.pixelatedw.mineminenomi.abilities.yuki.TabiraYukiAbility;
import xyz.pixelatedw.mineminenomi.api.items.components.MultiChannelItemColor;
import xyz.pixelatedw.mineminenomi.items.weapons.AbilitySwordItem;
import xyz.pixelatedw.mineminenomi.items.weapons.BlueSwordItem;
import xyz.pixelatedw.mineminenomi.items.weapons.ChakramItem;
import xyz.pixelatedw.mineminenomi.items.weapons.ClimaTactItem;
import xyz.pixelatedw.mineminenomi.items.weapons.DoruPickaxeItem;
import xyz.pixelatedw.mineminenomi.items.weapons.KujaBowItem;
import xyz.pixelatedw.mineminenomi.items.weapons.ModGunItem;
import xyz.pixelatedw.mineminenomi.items.weapons.ModSpearItem;
import xyz.pixelatedw.mineminenomi.items.weapons.ModSwordItem;
import xyz.pixelatedw.mineminenomi.items.weapons.ModSwordItem$AttributesBuilder;
import xyz.pixelatedw.mineminenomi.items.weapons.PopGreenBowItem;
import xyz.pixelatedw.mineminenomi.items.weapons.ScissorsItem;
import xyz.pixelatedw.mineminenomi.items.weapons.WarabideSwordItem;

public class ModWeapons {
	// Fields
	public static final DeferredItem<ModSwordItem> SCISSORS = ModRegistry.registerItem("Scissors",(Supplier)(((Supplier<ModSwordItem>)(ScissorsItem::new))));
	public static final DeferredItem<ModSwordItem> KIKOKU;
	public static final DeferredItem<ModSwordItem> KIRIBACHI;
	public static final DeferredItem<ModSwordItem> YORU;
	public static final DeferredItem<ModSwordItem> MURAKUMOGIRI;
	public static final DeferredItem<ModSwordItem> HOOK;
	public static final DeferredItem<TieredItem> UMBRELLA;
	public static final DeferredItem<ModSwordItem> NONOSAMA_STAFF;
	public static final DeferredItem<ModSpearItem> NONOSAMA_TRIDENT;
	public static final DeferredItem<ModSwordItem> HAMMER_5T;
	public static final DeferredItem<ModSwordItem> ACES_KNIFE;
	public static final DeferredItem<ModSwordItem> MIHAWKS_KNIFE;
	public static final DeferredItem<ModSwordItem> SANDAI_KITETSU;
	public static final DeferredItem<ModSwordItem> WADO_ICHIMONJI;
	public static final DeferredItem<ModSwordItem> NIDAI_KITETSU;
	public static final DeferredItem<ModSwordItem> SHUSUI;
	public static final DeferredItem<ModSwordItem> ENMA;
	public static final DeferredItem<ModSwordItem> AME_NO_HABAKIRI;
	public static final DeferredItem<ModSwordItem> SOUL_SOLID;
	public static final DeferredItem<ModSwordItem> DURANDAL;
	public static final DeferredItem<ModSwordItem> DAISENSO;
	public static final DeferredItem<ModSwordItem> ACE;
	public static final DeferredItem<ModSpearItem> MOGURA;
	public static final DeferredItem<ModSwordItem> DALTONS_SPADE;
	public static final DeferredItem<ModSwordItem> SAMEKIRI_BOCHO;
	public static final DeferredItem<ModSwordItem> CAT_CLAWS;
	public static final DeferredItem<ModSwordItem> HASSAIKAI;
	public static final DeferredItem<ModSwordItem> GRYPHON;
	public static final DeferredItem<ModSwordItem> AXE_HAND;
	public static final DeferredItem<ModSwordItem> PIPE;
	public static final DeferredItem<ModSwordItem> TONFA;
	public static final DeferredItem<ModSwordItem> JITTE;
	public static final DeferredItem<ModSwordItem> KATANA;
	public static final DeferredItem<ModSwordItem> CUTLASS;
	public static final DeferredItem<ModSwordItem> BROADSWORD;
	public static final DeferredItem<ModSwordItem> BISENTO;
	public static final DeferredItem<ModSwordItem> DAGGER;
	public static final DeferredItem<ModSwordItem> AXE;
	public static final DeferredItem<ModSpearItem> SPEAR;
	public static final DeferredItem<ModSwordItem> CLEAVER;
	public static final DeferredItem<ModSwordItem> MACE;
	public static final DeferredItem<ChakramItem> CHAKRAM;
	public static final DeferredItem<ClimaTactItem> CLIMA_TACT;
	public static final DeferredItem<ClimaTactItem> PERFECT_CLIMA_TACT;
	public static final DeferredItem<ClimaTactItem> SORCERY_CLIMA_TACT;
	public static final DeferredItem<ModGunItem> FLINTLOCK;
	public static final DeferredItem<ModGunItem> SENRIKU;
	public static final DeferredItem<ModGunItem> BAZOOKA;
	public static final DeferredItem<ModGunItem> WALKER;
	public static final DeferredItem<KujaBowItem> KUJA_BOW;
	public static final DeferredItem<PopGreenBowItem> KABUTO;
	public static final DeferredItem<PopGreenBowItem> BLACK_KABUTO;
	public static final DeferredItem<PopGreenBowItem> GINGA_PACHINKO;
	public static final DeferredItem<AbilitySwordItem<IceSaberAbility>> ICE_SABER;
	public static final DeferredItem<AbilitySwordItem<AmaNoMurakumoAbility>> AMA_NO_MURAKUMO;
	public static final DeferredItem<AbilitySwordItem<NoroNoroBeamSwordAbility>> NORO_NORO_BEAM_SWORD;
	public static final DeferredItem<AbilitySwordItem<DoruDoruArtsKenAbility>> DORU_DORU_ARTS_KEN;
	public static final DeferredItem<DoruPickaxeItem> DORU_PICKAXE;
	public static final DeferredItem<BlueSwordItem> BLUE_SWORD;
	public static final DeferredItem<AbilitySwordItem<TabiraYukiAbility>> TABIRA_YUKI;
	public static final DeferredItem<WarabideSwordItem> WARABIDE_SWORD;
	public static final DeferredItem<AbilitySwordItem<BarrierbilityBatAbility>> BARRIERBILITY_BAT;
	public static final DeferredItem<AbilitySwordItem<GammaKnifeAbility>> GAMMA_KNIFE;

	public static void init() {
	}
	static  {
		Supplier<ModSwordItem> var0 = () -> {
			return new ModSwordItem(ModTiers.KIKOKU,new Item.Properties().attributes(ModSwordItem$AttributesBuilder.of(7F).build()));
		};
		KIKOKU = (DeferredItem) (ModRegistry.registerItem("Kikoku",var0));
		Supplier<ModSwordItem> var1 = () -> {
			return new ModSwordItem(ModTiers.KIRIBACHI,new Item.Properties().attributes(ModSwordItem$AttributesBuilder.of(6F).reach(0.5F).build()));
		};
		KIRIBACHI = (DeferredItem) (ModRegistry.registerItem("Kiribachi",var1));
		Supplier<ModSwordItem> var2 = () -> {
			return new ModSwordItem(ModTiers.YORU,new Item.Properties().attributes(ModSwordItem$AttributesBuilder.of(11F,-2F).build()));
		};
		YORU = (DeferredItem) (ModRegistry.registerItem("Yoru",var2));
		Supplier<ModSwordItem> var3 = () -> {
			return new ModSwordItem(ModTiers.MURAKUMOGIRI,new Item.Properties().attributes(ModSwordItem$AttributesBuilder.of(12F,-2.9F).reach(2F).build()));
		};
		MURAKUMOGIRI = (DeferredItem) (ModRegistry.registerItem("Murakumogiri",var3));
		Supplier<ModSwordItem> var4 = () -> {
			return new ModSwordItem(ModTiers.HOOK,new Item.Properties().attributes(ModSwordItem$AttributesBuilder.of(5F).build())).addEffect(MobEffects.POISON,300);
		};
		HOOK = (DeferredItem) (ModRegistry.registerItem("Hook",var4));
		Supplier<TieredItem> var5 = () -> {
			return new TieredItem(ModTiers.UMBRELLA,new Item.Properties());
		};
		UMBRELLA = (DeferredItem) (ModRegistry.registerItem("Umbrella",var5));
		Supplier<ModSwordItem> var6 = () -> {
			return new ModSwordItem(ModTiers.NONOSAMA_STAFF,new Item.Properties().attributes(ModSwordItem$AttributesBuilder.of(6F,-2.6F).reach(1F).build()));
		};
		NONOSAMA_STAFF = (DeferredItem) (ModRegistry.registerItem("Nonosama Staff",var6));
		Supplier<ModSpearItem> var7 = () -> {
			return new ModSpearItem(ModTiers.NONOSAMA_TRIDENT,new Item.Properties().attributes(ModSwordItem$AttributesBuilder.of(9F,-2.6F).reach(1F).build()));
		};
		NONOSAMA_TRIDENT = (DeferredItem) (ModRegistry.registerItem("Nonosama Trident",var7));
		Supplier<ModSwordItem> var8 = () -> {
			return new ModSwordItem(ModTiers.HAMMER_5T,new Item.Properties().attributes(ModSwordItem$AttributesBuilder.of(1F).build()));
		};
		HAMMER_5T = (DeferredItem) (ModRegistry.registerItem("5t Hammer",var8));
		Supplier<ModSwordItem> var9 = () -> {
			return new ModSwordItem(ModTiers.ACES_KNIFE,new Item.Properties().attributes(ModSwordItem$AttributesBuilder.of(4F,-1.1F).build()));
		};
		ACES_KNIFE = (DeferredItem) (ModRegistry.registerItem("Ace's Knife",var9));
		Supplier<ModSwordItem> var10 = () -> {
			return new ModSwordItem(ModTiers.MIHAWKS_KNIFE,new Item.Properties().attributes(ModSwordItem$AttributesBuilder.of(4F,-1.1F).build()));
		};
		MIHAWKS_KNIFE = (DeferredItem) (ModRegistry.registerItem("Mihawk's Knife",var10));
		Supplier<ModSwordItem> var11 = () -> {
			return new ModSwordItem(ModTiers.SANDAI_KITETSU,new Item.Properties().attributes(ModSwordItem$AttributesBuilder.of(8F,-2F).build()));
		};
		SANDAI_KITETSU = (DeferredItem) (ModRegistry.registerItem("Sandai Kitetsu",var11));
		Supplier<ModSwordItem> var12 = () -> {
			return new ModSwordItem(ModTiers.WADO_ICHIMONJI,new Item.Properties().attributes(ModSwordItem$AttributesBuilder.of(8F,-2F).build()));
		};
		WADO_ICHIMONJI = (DeferredItem) (ModRegistry.registerItem("Wado Ichimonji",var12));
		Supplier<ModSwordItem> var13 = () -> {
			return new ModSwordItem(ModTiers.NIDAI_KITETSU,new Item.Properties().attributes(ModSwordItem$AttributesBuilder.of(8F,-2F).build()));
		};
		NIDAI_KITETSU = (DeferredItem) (ModRegistry.registerItem("Nidai Kitetsu",var13));
		Supplier<ModSwordItem> var14 = () -> {
			return new ModSwordItem(ModTiers.SHUSUI,new Item.Properties().attributes(ModSwordItem$AttributesBuilder.of(9F,-2F).build()));
		};
		SHUSUI = (DeferredItem) (ModRegistry.registerItem("Shusui",var14));
		Supplier<ModSwordItem> var15 = () -> {
			return new ModSwordItem(ModTiers.ENMA,new Item.Properties().attributes(ModSwordItem$AttributesBuilder.of(9F,-2F).build()));
		};
		ENMA = (DeferredItem) (ModRegistry.registerItem("Enma",var15));
		Supplier<ModSwordItem> var16 = () -> {
			return new ModSwordItem(ModTiers.AME_NO_HABAKIRI,new Item.Properties().attributes(ModSwordItem$AttributesBuilder.of(9F,-2F).build()));
		};
		AME_NO_HABAKIRI = (DeferredItem) (ModRegistry.registerItem("Ame no Habakiri",var16));
		Supplier<ModSwordItem> var17 = () -> {
			return new ModSwordItem(ModTiers.SOUL_SOLID,new Item.Properties().attributes(ModSwordItem$AttributesBuilder.of(6F,-1.4F).build()));
		};
		SOUL_SOLID = (DeferredItem) (ModRegistry.registerItem("Soul Solid",var17));
		Supplier<ModSwordItem> var18 = () -> {
			return new ModSwordItem(ModTiers.DURANDAL,new Item.Properties().attributes(ModSwordItem$AttributesBuilder.of(6F,-1.5F).build()));
		};
		DURANDAL = (DeferredItem) (ModRegistry.registerItem("Durandal",var18));
		Supplier<ModSwordItem> var19 = () -> {
			return new ModSwordItem(ModTiers.DAISENSO,new Item.Properties().attributes(ModSwordItem$AttributesBuilder.of(5F,-2.6F).reach(1.5F).build()));
		};
		DAISENSO = (DeferredItem) (ModRegistry.registerItem("Daisenso",var19));
		Supplier<ModSwordItem> var20 = () -> {
			return new ModSwordItem(ModTiers.ACE,new Item.Properties().attributes(ModSwordItem$AttributesBuilder.of(11F,-1.7F).build()));
		};
		ACE = (DeferredItem) (ModRegistry.registerItem("Ace",var20));
		Supplier<ModSpearItem> var21 = () -> {
			return new ModSpearItem(ModTiers.MOGURA,new Item.Properties().attributes(ModSwordItem$AttributesBuilder.of(10F,-2.6F).reach(1.5F).build()));
		};
		MOGURA = (DeferredItem) (ModRegistry.registerItem("Mogura",var21));
		Supplier<ModSwordItem> var22 = () -> {
			return new ModSwordItem(ModTiers.DALTONS_SPADE,new Item.Properties().attributes(ModSwordItem$AttributesBuilder.of(6F,-2F).build()));
		};
		DALTONS_SPADE = (DeferredItem) (ModRegistry.registerItem("Dalton's Spade",var22));
		Supplier<ModSwordItem> var23 = () -> {
			return new ModSwordItem(ModTiers.SAMEKIRI_BOCHO,new Item.Properties().attributes(ModSwordItem$AttributesBuilder.of(7F,-2F).reach(0.25F).build()));
		};
		SAMEKIRI_BOCHO = (DeferredItem) (ModRegistry.registerItem("Samekiri Bocho",var23));
		Supplier<ModSwordItem> var24 = () -> {
			return new ModSwordItem(ModTiers.CAT_CLAWS,new Item.Properties().attributes(ModSwordItem$AttributesBuilder.of(7F,-1.3F).build()));
		};
		CAT_CLAWS = (DeferredItem) (ModRegistry.registerItem("Cat Claws",var24));
		Supplier<ModSwordItem> var25 = () -> {
			return new ModSwordItem(ModTiers.HASSAIKAI,new Item.Properties().attributes(ModSwordItem$AttributesBuilder.of(12F,-3.2F).reach(0.25F).build()));
		};
		HASSAIKAI = (DeferredItem) (ModRegistry.registerItem("Hassaikai",var25));
		Supplier<ModSwordItem> var26 = () -> {
			return new ModSwordItem(ModTiers.GRYPHON,new Item.Properties().attributes(ModSwordItem$AttributesBuilder.of(10F,-2F).build()));
		};
		GRYPHON = (DeferredItem) (ModRegistry.registerItem("Gryphon",var26));
		Supplier<ModSwordItem> var27 = () -> {
			return new ModSwordItem(ModTiers.AXE_HAND,new Item.Properties().attributes(ModSwordItem$AttributesBuilder.of(5F,-2.2F).build()));
		};
		AXE_HAND = (DeferredItem) (ModRegistry.registerItem("Axe Hand",var27));
		Supplier<ModSwordItem> var28 = () -> {
			return new ModSwordItem(ModTiers.PIPE,new Item.Properties().attributes(ModSwordItem$AttributesBuilder.of(5F).build()));
		};
		PIPE = (DeferredItem) (ModRegistry.registerItem("Pipe",var28));
		Supplier<ModSwordItem> var29 = () -> {
			return new ModSwordItem(ModTiers.TONFA,new Item.Properties().attributes(ModSwordItem$AttributesBuilder.of(5F).build()));
		};
		TONFA = (DeferredItem) (ModRegistry.registerItem("Tonfa",var29));
		Supplier<ModSwordItem> var30 = () -> {
			return new ModSwordItem(ModTiers.JITTE,new Item.Properties().attributes(ModSwordItem$AttributesBuilder.of(6F).build()).component(ModDataComponents.MULTI_CHANNEL_DYES,MultiChannelItemColor.WEAPON_COLOR));
		};
		JITTE = (DeferredItem) (ModRegistry.registerItem("Jitte",var30));
		Supplier<ModSwordItem> var31 = () -> {
			return new ModSwordItem(ModTiers.KATANA,new Item.Properties().attributes(ModSwordItem$AttributesBuilder.of(5F,-2.2F).build()).component(ModDataComponents.MULTI_CHANNEL_DYES,MultiChannelItemColor.WEAPON_COLOR));
		};
		KATANA = (DeferredItem) (ModRegistry.registerItem("Katana",var31));
		Supplier<ModSwordItem> var32 = () -> {
			return new ModSwordItem(ModTiers.CUTLASS,new Item.Properties().attributes(ModSwordItem$AttributesBuilder.of(5F,-2.4F).build()).component(ModDataComponents.MULTI_CHANNEL_DYES,MultiChannelItemColor.WEAPON_COLOR));
		};
		CUTLASS = (DeferredItem) (ModRegistry.registerItem("Cutlass",var32));
		Supplier<ModSwordItem> var33 = () -> {
			return new ModSwordItem(ModTiers.BROADSWORD,new Item.Properties().attributes(ModSwordItem$AttributesBuilder.of(6F,-2.5F).build()).component(ModDataComponents.MULTI_CHANNEL_DYES,MultiChannelItemColor.WEAPON_COLOR));
		};
		BROADSWORD = (DeferredItem) (ModRegistry.registerItem("Broadsword",var33));
		Supplier<ModSwordItem> var34 = () -> {
			return new ModSwordItem(ModTiers.BISENTO,new Item.Properties().attributes(ModSwordItem$AttributesBuilder.of(6F,-2.7F).reach(1.5F).build()).component(ModDataComponents.MULTI_CHANNEL_DYES,MultiChannelItemColor.WEAPON_COLOR));
		};
		BISENTO = (DeferredItem) (ModRegistry.registerItem("Bisento",var34));
		Supplier<ModSwordItem> var35 = () -> {
			return new ModSwordItem(ModTiers.DAGGER,new Item.Properties().attributes(ModSwordItem$AttributesBuilder.of(4F,-1.3F).build()).component(ModDataComponents.MULTI_CHANNEL_DYES,MultiChannelItemColor.WEAPON_COLOR));
		};
		DAGGER = (DeferredItem) (ModRegistry.registerItem("Dagger",var35));
		Supplier<ModSwordItem> var36 = () -> {
			return new ModSwordItem(ModTiers.AXE,new Item.Properties().attributes(ModSwordItem$AttributesBuilder.of(6F,-2.7F).build()).component(ModDataComponents.MULTI_CHANNEL_DYES,MultiChannelItemColor.WEAPON_COLOR));
		};
		AXE = (DeferredItem) (ModRegistry.registerItem("Axe",var36));
		Supplier<ModSpearItem> var37 = () -> {
			return new ModSpearItem(ModTiers.SPEAR,new Item.Properties().attributes(ModSwordItem$AttributesBuilder.of(5F,-2.5F).reach(1F).build()).component(ModDataComponents.MULTI_CHANNEL_DYES,MultiChannelItemColor.WEAPON_COLOR));
		};
		SPEAR = (DeferredItem) (ModRegistry.registerItem("Spear",var37));
		Supplier<ModSwordItem> var38 = () -> {
			return new ModSwordItem(ModTiers.CLEAVER,new Item.Properties().attributes(ModSwordItem$AttributesBuilder.of(6F,-2.5F).reach(0.25F).build()).component(ModDataComponents.MULTI_CHANNEL_DYES,MultiChannelItemColor.WEAPON_COLOR));
		};
		CLEAVER = (DeferredItem) (ModRegistry.registerItem("Cleaver",var38));
		Supplier<ModSwordItem> var39 = () -> {
			return new ModSwordItem(ModTiers.MACE,new Item.Properties().attributes(ModSwordItem$AttributesBuilder.of(8F,-3.2F).reach(0.25F).build()).component(ModDataComponents.MULTI_CHANNEL_DYES,MultiChannelItemColor.WEAPON_COLOR));
		};
		MACE = (DeferredItem) (ModRegistry.registerItem("Mace",var39));
		CHAKRAM = (DeferredItem) (ModRegistry.registerItem("Chakram",(Supplier)(((Supplier<ChakramItem>)(ChakramItem::new)))));
		Supplier<ClimaTactItem> var40 = () -> {
			return new ClimaTactItem(ModTiers.CLIMA_TACT,1);
		};
		CLIMA_TACT = (DeferredItem) (ModRegistry.registerItem("Clima Tact",var40));
		Supplier<ClimaTactItem> var41 = () -> {
			return new ClimaTactItem(ModTiers.PERFECT_CLIMA_TACT,2);
		};
		PERFECT_CLIMA_TACT = (DeferredItem) (ModRegistry.registerItem("Perfect Clima Tact",var41));
		Supplier<ClimaTactItem> var42 = () -> {
			return new ClimaTactItem(ModTiers.SORCERY_CLIMA_TACT,4);
		};
		SORCERY_CLIMA_TACT = (DeferredItem) (ModRegistry.registerItem("Sorcery Clima Tact",var42));
		Supplier<ModGunItem> var43 = () -> {
			return new ModGunItem(200).setShotCooldown(15).setReloadCooldown(40).setInaccuracy(1.5F).setBulletSpeed(3F).setDamageMultiplier(1F).setGunpowderLimit(5);
		};
		FLINTLOCK = (DeferredItem) (ModRegistry.registerItem("Flintlock",var43));
		Supplier<ModGunItem> var44 = () -> {
			return new ModGunItem(800).setShotCooldown(20).setReloadCooldown(60).setInaccuracy(0F).setBulletSpeed(4.5F).setDamageMultiplier(2.5F).setGunpowderLimit(4);
		};
		SENRIKU = (DeferredItem) (ModRegistry.registerItem("Senriku",var44));
		Supplier<ModGunItem> var45 = () -> {
			return new ModGunItem(800,ModGunItem.BAZOOKA_AMMO).setShotCooldown(60).setInaccuracy(0.5F).setBulletSpeed(3F).setDamageMultiplier(2F).setGunpowderLimit(1);
		};
		BAZOOKA = (DeferredItem) (ModRegistry.registerItem("Bazooka",var45));
		Supplier<ModGunItem> var46 = () -> {
			return new ModGunItem(2500).setShotCooldown(10).setReloadCooldown(30).setInaccuracy(0.5F).setBulletSpeed(4F).setDamageMultiplier(2F).setGunpowderLimit(7);
		};
		WALKER = (DeferredItem) (ModRegistry.registerItem("Walker",var46));
		KUJA_BOW = (DeferredItem) (ModRegistry.registerItem("Kuja Bow",(Supplier)(((Supplier<KujaBowItem>)(KujaBowItem::new)))));
		Supplier<PopGreenBowItem> var47 = () -> {
			return new PopGreenBowItem(400);
		};
		KABUTO = (DeferredItem) (ModRegistry.registerItem("Kabuto",var47));
		Supplier<PopGreenBowItem> var48 = () -> {
			return new PopGreenBowItem(800);
		};
		BLACK_KABUTO = (DeferredItem) (ModRegistry.registerItem("Kuro Kabuto",var48));
		Supplier<PopGreenBowItem> var49 = () -> {
			return new PopGreenBowItem(200);
		};
		GINGA_PACHINKO = (DeferredItem) (ModRegistry.registerItem("Ginga Pachinko",var49));
		Supplier var50 = (Supplier) ((Supplier<AbilitySwordItem>) (() -> {
			return ((AbilitySwordItem)(new AbilitySwordItem(ModTiers.ICE_SABER,(Supplier)(IceSaberAbility.INSTANCE),new Item.Properties().attributes(ModSwordItem$AttributesBuilder.of(12F,-1.8F).build())).addEffect((Holder)(ModEffects.FROSTBITE),40,1)));
		}));
		ICE_SABER = (DeferredItem) (ModRegistry.registerItem("Ice Saber",var50));
		Supplier var51 = (Supplier) ((Supplier<AbilitySwordItem>) (() -> {
			return new AbilitySwordItem(ModTiers.AMA_NO_MURAKUMO,(Supplier)(AmaNoMurakumoAbility.INSTANCE),new Item.Properties().attributes(ModSwordItem$AttributesBuilder.of(14F,0F).build()));
		}));
		AMA_NO_MURAKUMO = (DeferredItem) (ModRegistry.registerItem("Ama no Murakumo",var51));
		Supplier var52 = (Supplier) ((Supplier<AbilitySwordItem>) (() -> {
			return ((AbilitySwordItem)(new AbilitySwordItem(ModTiers.NORO_NORO_BEAM_SWORD,(Supplier)(NoroNoroBeamSwordAbility.INSTANCE),new Item.Properties().attributes(ModSwordItem$AttributesBuilder.of(7F,0F).build())).addEffect((Holder)(ModEffects.NORO_SLOWNESS),40,1)));
		}));
		NORO_NORO_BEAM_SWORD = (DeferredItem) (ModRegistry.registerItem("Noro Noro Beam Sword",var52));
		Supplier var53 = (Supplier) ((Supplier<AbilitySwordItem>) (() -> {
			return new AbilitySwordItem(ModTiers.DORU_DORU_ARTS_KEN,(Supplier)(DoruDoruArtsKenAbility.INSTANCE),new Item.Properties().attributes(ModSwordItem$AttributesBuilder.of(7F).build()).component(ModDataComponents.MULTI_CHANNEL_DYES,MultiChannelItemColor.WEAPON_COLOR));
		}));
		DORU_DORU_ARTS_KEN = (DeferredItem) (ModRegistry.registerItem("Doru Doru Arts: Ken",var53));
		Supplier<DoruPickaxeItem> var54 = () -> {
			return new DoruPickaxeItem(ModTiers.DORU_DORU_ARTS_KEN,1,-2.8F);
		};
		DORU_PICKAXE = (DeferredItem) (ModRegistry.registerItem("Doru Doru Arts: Pickaxe",var54));
		BLUE_SWORD = (DeferredItem) (ModRegistry.registerItem("Blue Sword",(Supplier)(((Supplier<BlueSwordItem>)(BlueSwordItem::new)))));
		Supplier var55 = (Supplier) ((Supplier<AbilitySwordItem>) (() -> {
			return ((AbilitySwordItem)(new AbilitySwordItem(ModTiers.TABIRA_YUKI,(Supplier)(TabiraYukiAbility.INSTANCE),new Item.Properties().attributes(ModSwordItem$AttributesBuilder.of(9F,-1.9F).build())).addEffect((Holder)(ModEffects.FROSTBITE),20,1)));
		}));
		TABIRA_YUKI = (DeferredItem) (ModRegistry.registerItem("Tabira Yuki",var55));
		WARABIDE_SWORD = (DeferredItem) (ModRegistry.registerItem("Warabide Sword",(Supplier)(((Supplier<WarabideSwordItem>)(WarabideSwordItem::new)))));
		Supplier var56 = (Supplier) ((Supplier<AbilitySwordItem>) (() -> {
			return new AbilitySwordItem(ModTiers.BARRIERBILITY_BAT,(Supplier)(BarrierbilityBatAbility.INSTANCE),new Item.Properties().attributes(ModSwordItem$AttributesBuilder.of(7F).build()));
		}));
		BARRIERBILITY_BAT = (DeferredItem) (ModRegistry.registerItem("Barrierbility Bat",var56));
		Supplier var57 = (Supplier) ((Supplier<AbilitySwordItem>) (() -> {
			return new AbilitySwordItem(ModTiers.GAMMA_KNIFE,(Supplier)(GammaKnifeAbility.INSTANCE),new Item.Properties().attributes(ModSwordItem$AttributesBuilder.of(1F,0F).build()));
		}));
		GAMMA_KNIFE = (DeferredItem) (ModRegistry.registerItem("Gamma Knife",var57));
	}
}