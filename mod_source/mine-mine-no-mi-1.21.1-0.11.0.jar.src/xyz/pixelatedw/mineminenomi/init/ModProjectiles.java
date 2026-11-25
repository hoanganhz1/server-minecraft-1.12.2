package xyz.pixelatedw.mineminenomi.init;

import java.util.function.Supplier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.entities.LightningDischargeEntity;
import xyz.pixelatedw.mineminenomi.entities.projectiles.AxeDialProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.CannonBallProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.HookEntity;
import xyz.pixelatedw.mineminenomi.entities.projectiles.KairosekiBulletProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.MilkyDialProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.NormalBulletProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.PopGreenProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.artofweather.CoolBallProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.artofweather.GustSwordProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.artofweather.HeatBallProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.artofweather.ThunderBallProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.artofweather.ThunderLanceProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.artofweather.ThunderboltProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.artofweather.ThunderstormProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.artofweather.WeatherEggProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.awa.RelaxHourProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.baku.BeroCannonProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.bane.SpringDeathKnockProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.bara.BaraBaraHoProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.bara.BaraFestivalEntity;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.bara.DaiCircusProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.bari.BarrierCrashProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.bari.BarrierbilityStairsProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.beta.BetaBetaChainProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.beta.StickyProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.blackleg.ExtraHachisProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.blackleg.PoeleAFrireProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.bomu.BreezeBreathBombProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.bomu.NoseFancyCannonProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.brawler.KingPunchProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.carnivaltricks.TopEntity;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.cyborg.CoupDeVentProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.cyborg.FreshFireProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.cyborg.RadicalBeamProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.cyborg.StrongRightProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.doku.ChloroBallProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.doku.HydraProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.doku.VenomRoadProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.doru.CandleLockProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.doru.ChampFightProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.doru.DoruDoruArtsMoriProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.electro.ElectricalLunaProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.electro.ElectricalShowerProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.electro.ElectroVisualProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.fishmankarate.MizuShuryudanProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.fishmankarate.MizuTaihoProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.fishmankarate.PackOfSharksProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.fishmankarate.UchimizuProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.fishmankarate.YarinamiProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.gasu.GasRobeProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.gasu.GastilleProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.goe.DragonsRoarProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.goe.TodorokiProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.gomu.GomuGomuNoBajrangGunProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.gomu.GomuGomuNoBazookaProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.gomu.GomuGomuNoElephantGunProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.gomu.GomuGomuNoGrizzlyMagnumProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.gomu.GomuGomuNoJetBazookaProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.gomu.GomuGomuNoJetPistolProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.gomu.GomuGomuNoKingKongGunProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.gomu.GomuGomuNoLeoBazookaProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.gomu.GomuGomuNoPistolProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.gomu.GomuGomuNoRocketProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.goro.ElThorProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.goro.RaigoLightningProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.goro.RaigoProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.goro.SangoProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.gura.GekishinProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.hana.CampoDeFloresEntity;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.hana.HanaFeetEntity;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.hana.HanaHandsEntity;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.hie.IceBallProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.hie.IceBlockAvalancheProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.hie.IceBlockPartisanProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.hie.IceBlockPheasantProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.hitodaibutsu.ImpactBlastProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.horo.MiniHollowProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.horo.NegativeHollowProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.horo.TokuHollowProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.ito.OverheatProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.ito.StringPillarProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.ito.TamaitoProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.ittoryu.SanbyakurokujuPoundHoProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.ittoryu.YakkodoriProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.jiki.DamnedPunkProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.jiki.GenocideRaidEffectEntity;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.jiki.PunkGibsonProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.jiki.PunkPistolProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.kage.BlackBoxProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.kage.BrickBatProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.kage.TsunoTokagePillarEntity;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.magu.DaiFunkaProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.magu.MeigoProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.magu.RyuseiKazanProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.mera.DaiEnkaiEnteiProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.mera.DaiEnkaiOnibiProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.mera.HidarumaProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.mera.HiganProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.mera.HikenProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.mera.JujikaProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.mero.MeroMeroMellowProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.mero.PistolKissProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.mero.SlaveArrowProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.moku.WhiteBlowProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.moku.WhiteBlowRushProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.moku.WhiteOutProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.moku.WhiteSnakeProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.netsu.NekkaiGyoraiProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.nikyu.ChargingUrsusShockEntity;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.nikyu.PadHoProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.nikyu.PainEntity;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.nikyu.UrsusShockProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.noro.NoroNoroBeamProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.ori.AwaseBaoriProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.pero.CandyEscalatorProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.pero.CandyWaveProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.pika.AmaterasuProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.pika.YasakaniNoMagatamaProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.rokushiki.RankyakuProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.rokushiki.RokuoganProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.ryupteranodon.BarizodonProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.ryupteranodon.TempuraudonProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.saraaxolotl.PoisonSpitProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.sniper.HiNoToriBoshiProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.sniper.KemuriBoshiProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.sniper.NemuriBoshiProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.sniper.RenpatsuNamariBoshiProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.sniper.SakuretsuSabotenBoshiProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.sniper.TetsuBoshiProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.sniper.TokuyoAburaBoshiProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.suke.ShishaNoTeProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.suna.DesertGrandeEspadaProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.suna.DesertSpadaProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.suna.SablesPesadoProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.supa.SparklingDaisyAftereffectProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.supa.SpiralHollowProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.toriphoenix.PhoenixGoenProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.yami.BlackRoadProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.yami.DarkMatterProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.yami.LiberationProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.yuki.YukiRabiProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.zou.TrunkShotProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.zushi.MokoProjectile;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.zushi.SagariNoRyuseiProjectile;

public class ModProjectiles {
	// Fields
	public static final Supplier<EntityType<RelaxHourProjectile>> RELAX_HOUR;
	public static final Supplier<EntityType<BeroCannonProjectile>> BERO_CANNON;
	public static final Supplier<EntityType<BarrierCrashProjectile>> BARRIER_CRASH;
	public static final Supplier<EntityType<BarrierbilityStairsProjectile>> BARRIERBILITY_STAIRS;
	public static final Supplier<EntityType<StickyProjectile>> STICKY_PROJECTILE;
	public static final Supplier<EntityType<BetaBetaChainProjectile>> BETA_BETA_CHAIN;
	public static final Supplier<EntityType<NoseFancyCannonProjectile>> NOSE_FANCY_CANNON;
	public static final Supplier<EntityType<BreezeBreathBombProjectile>> BREEZE_BREATH_BOMB;
	public static final Supplier<EntityType<HikenProjectile>> HIKEN;
	public static final Supplier<EntityType<HiganProjectile>> HIGAN;
	public static final Supplier<EntityType<DaiEnkaiEnteiProjectile>> DAI_ENKAI_ENTEI;
	public static final Supplier<EntityType<DaiEnkaiOnibiProjectile>> DAI_ENKAI_ONIBI;
	public static final Supplier<EntityType<HidarumaProjectile>> HIDARUMA;
	public static final Supplier<EntityType<JujikaProjectile>> JUJIKA;
	public static final Supplier<EntityType<NekkaiGyoraiProjectile>> NEKKAI_GYORAI;
	public static final Supplier<EntityType<ShishaNoTeProjectile>> SHISHA_NO_TE;
	public static final Supplier<EntityType<IceBlockPartisanProjectile>> ICE_BLOCK_PARTISAN;
	public static final Supplier<EntityType<IceBallProjectile>> ICE_BALL;
	public static final Supplier<EntityType<IceBlockPheasantProjectile>> ICE_BLOCK_PHEASANT;
	public static final Supplier<EntityType<IceBlockAvalancheProjectile>> ICE_BLOCK_AVALANCHE;
	public static final Supplier<EntityType<YukiRabiProjectile>> YUKI_RABI;
	public static final Supplier<EntityType<WhiteBlowProjectile>> WHITE_BLOW;
	public static final Supplier<EntityType<WhiteBlowRushProjectile>> WHITE_BLOW_RUSH;
	public static final Supplier<EntityType<WhiteSnakeProjectile>> WHITE_SNAKE;
	public static final Supplier<EntityType<WhiteOutProjectile>> WHITE_OUT;
	public static final Supplier<EntityType<NoroNoroBeamProjectile>> NORO_NORO_BEAM;
	public static final Supplier<EntityType<NegativeHollowProjectile>> NEGATIVE_HOLLOW;
	public static final Supplier<EntityType<MiniHollowProjectile>> MINI_HOLLOW;
	public static final Supplier<EntityType<TokuHollowProjectile>> TOKU_HOLLOW;
	public static final Supplier<EntityType<DragonsRoarProjectile>> DRAGONS_ROAR;
	public static final Supplier<EntityType<TodorokiProjectile>> TODOROKI;
	public static final Supplier<EntityType<AwaseBaoriProjectile>> AWASE_BAORI;
	public static final Supplier<EntityType<MeroMeroMellowProjectile>> MERO_MERO_MELLOW;
	public static final Supplier<EntityType<PistolKissProjectile>> PISTOL_KISS;
	public static final Supplier<EntityType<SlaveArrowProjectile>> SLAVE_ARROW;
	public static final Supplier<EntityType<SpringDeathKnockProjectile>> SPRING_DEATH_KNOCK;
	public static final Supplier<EntityType<PadHoProjectile>> PAD_HO;
	public static final Supplier<EntityType<UrsusShockProjectile>> URSUS_SHOCK;
	public static final Supplier<EntityType<ChargingUrsusShockEntity>> CHARGING_URSUS_SHOCK;
	public static final Supplier<EntityType<PainEntity>> PAIN;
	public static final Supplier<EntityType<AmaterasuProjectile>> AMATERASU;
	public static final Supplier<EntityType<YasakaniNoMagatamaProjectile>> YASAKANI_NO_MAGATAMA;
	public static final Supplier<EntityType<DaiFunkaProjectile>> DAI_FUNKA;
	public static final Supplier<EntityType<RyuseiKazanProjectile>> RYUSEI_KAZAN;
	public static final Supplier<EntityType<MeigoProjectile>> MEIGO;
	public static final Supplier<EntityType<HanaHandsEntity>> HANA_HANDS;
	public static final Supplier<EntityType<HanaFeetEntity>> HANA_FEET;
	public static final Supplier<EntityType<CampoDeFloresEntity>> CAMPO_DE_FLORES;
	public static final Supplier<EntityType<DoruDoruArtsMoriProjectile>> DORU_DORU_ARTS_MORI;
	public static final Supplier<EntityType<CandleLockProjectile>> CANDLE_LOCK;
	public static final Supplier<EntityType<ChampFightProjectile>> CHAMP_FIGHT;
	public static final Supplier<EntityType<GasRobeProjectile>> GAS_ROBE;
	public static final Supplier<EntityType<GastilleProjectile>> GASTILLE;
	public static final Supplier<EntityType<DesertSpadaProjectile>> DESERT_SPADA;
	public static final Supplier<EntityType<DesertGrandeEspadaProjectile>> DESERT_GRANDE_ESPADA;
	public static final Supplier<EntityType<SablesPesadoProjectile>> SABLES_PESADO;
	public static final Supplier<EntityType<GekishinProjectile>> GEKISHIN;
	public static final Supplier<EntityType<SpiralHollowProjectile>> SPIRAL_HOLLOW;
	public static final Supplier<EntityType<SparklingDaisyAftereffectProjectile>> SPARKLING_DAISY_AFTER;
	public static final Supplier<EntityType<PoisonSpitProjectile>> POISON_SPIT;
	public static final Supplier<EntityType<ImpactBlastProjectile>> IMPACT_BLAST;
	public static final Supplier<EntityType<SagariNoRyuseiProjectile>> SAGARI_NO_RYUSEI;
	public static final Supplier<EntityType<MokoProjectile>> MOKO;
	public static final Supplier<EntityType<CandyEscalatorProjectile>> CANDY_ESCALATOR;
	public static final Supplier<EntityType<CandyWaveProjectile>> CANDY_WAVE;
	public static final Supplier<EntityType<TamaitoProjectile>> TAMAITO;
	public static final Supplier<EntityType<StringPillarProjectile>> STRING_PILLAR;
	public static final Supplier<EntityType<OverheatProjectile>> OVERHEAT;
	public static final Supplier<EntityType<BrickBatProjectile>> BRICK_BAT;
	public static final Supplier<EntityType<BlackBoxProjectile>> BLACK_BOX;
	public static final Supplier<EntityType<TsunoTokagePillarEntity>> TSUNO_TOKAGE;
	public static final Supplier<EntityType<HydraProjectile>> HYDRA;
	public static final Supplier<EntityType<ChloroBallProjectile>> CHLORO_BALL;
	public static final Supplier<EntityType<VenomRoadProjectile>> VENOM_ROAD;
	public static final Supplier<EntityType<GomuGomuNoPistolProjectile>> GOMU_GOMU_NO_PISTOL;
	public static final Supplier<EntityType<GomuGomuNoJetPistolProjectile>> GOMU_GOMU_NO_JET_PISTOL;
	public static final Supplier<EntityType<GomuGomuNoElephantGunProjectile>> GOMU_GOMU_NO_ELEPHANT_GUN;
	public static final Supplier<EntityType<GomuGomuNoKingKongGunProjectile>> GOMU_GOMU_NO_KING_KONG_GUN;
	public static final Supplier<EntityType<GomuGomuNoBajrangGunProjectile>> GOMU_GOMU_NO_BAJRANG_GUN;
	public static final Supplier<EntityType<GomuGomuNoBazookaProjectile>> GOMU_GOMU_NO_BAZOOKA;
	public static final Supplier<EntityType<GomuGomuNoJetBazookaProjectile>> GOMU_GOMU_NO_JET_BAZOOKA;
	public static final Supplier<EntityType<GomuGomuNoGrizzlyMagnumProjectile>> GOMU_GOMU_NO_GRIZZLY_MAGNUM;
	public static final Supplier<EntityType<GomuGomuNoLeoBazookaProjectile>> GOMU_GOMU_NO_LEO_BAZOOKA;
	public static final Supplier<EntityType<GomuGomuNoRocketProjectile>> GOMU_GOMU_NO_ROCKET;
	public static final Supplier<EntityType<SangoProjectile>> SANGO;
	public static final Supplier<EntityType<ElThorProjectile>> EL_THOR;
	public static final Supplier<EntityType<RaigoProjectile>> RAIGO;
	public static final Supplier<EntityType<RaigoLightningProjectile>> RAIGO_LIGHTNING;
	public static final Supplier<EntityType<LiberationProjectile>> LIBERATION;
	public static final Supplier<EntityType<DarkMatterProjectile>> DARK_MATTER;
	public static final Supplier<EntityType<BlackRoadProjectile>> BLACK_ROAD;
	public static final Supplier<EntityType<PunkGibsonProjectile>> PUNK_GIBSON;
	public static final Supplier<EntityType<PunkPistolProjectile>> PUNK_PISTOL;
	public static final Supplier<EntityType<DamnedPunkProjectile>> DAMNED_PUNK;
	public static final Supplier<EntityType<GenocideRaidEffectEntity>> GENOCIDE_RAID_EFFECT;
	public static final Supplier<EntityType<BarizodonProjectile>> BARIZODON;
	public static final Supplier<EntityType<TempuraudonProjectile>> TEMPURAUDON;
	public static final Supplier<EntityType<BaraBaraHoProjectile>> BARA_BARA_HO;
	public static final Supplier<EntityType<DaiCircusProjectile>> DAI_CIRCUS;
	public static final Supplier<EntityType<BaraFestivalEntity>> BARA_FESTIVAL;
	public static final Supplier<EntityType<PhoenixGoenProjectile>> PHOENIX_GOEN;
	public static final Supplier<EntityType<TrunkShotProjectile>> TRUNK_SHOT;
	public static final Supplier<EntityType<RankyakuProjectile>> RANKYAKU;
	public static final Supplier<EntityType<RokuoganProjectile>> ROKUOGAN;
	public static final Supplier<EntityType<UchimizuProjectile>> UCHIMIZU;
	public static final Supplier<EntityType<PackOfSharksProjectile>> PACK_OF_SHARKS;
	public static final Supplier<EntityType<YarinamiProjectile>> YARINAMI;
	public static final Supplier<EntityType<MizuTaihoProjectile>> MIZU_TAIHO;
	public static final Supplier<EntityType<MizuShuryudanProjectile>> MIZU_SHURYUDAN;
	public static final Supplier<EntityType<FreshFireProjectile>> FRESH_FIRE;
	public static final Supplier<EntityType<StrongRightProjectile>> STRONG_RIGHT;
	public static final Supplier<EntityType<CoupDeVentProjectile>> COUP_DE_VENT;
	public static final Supplier<EntityType<RadicalBeamProjectile>> RADICAL_BEAM;
	public static final Supplier<EntityType<ElectricalLunaProjectile>> ELECTRICAL_LUNA;
	public static final Supplier<EntityType<ElectricalShowerProjectile>> ELECTRICAL_SHOWER;
	public static final Supplier<EntityType<ElectroVisualProjectile>> ELECTRO_VISUAL;
	public static final Supplier<EntityType<SanbyakurokujuPoundHoProjectile>> SANBYAKUROKUJU_POUND_HO;
	public static final Supplier<EntityType<YakkodoriProjectile>> YAKKODORI;
	public static final Supplier<EntityType<KingPunchProjectile>> KING_PUNCH;
	public static final Supplier<EntityType<ExtraHachisProjectile>> EXTRA_HACHIS;
	public static final Supplier<EntityType<PoeleAFrireProjectile>> POELE_A_FRIRE;
	public static final Supplier<EntityType<HiNoToriBoshiProjectile>> HI_NO_TORI_BOSHI;
	public static final Supplier<EntityType<KemuriBoshiProjectile>> KEMURI_BOSHI;
	public static final Supplier<EntityType<RenpatsuNamariBoshiProjectile>> RENPATSU_NAMARI_BOSHI;
	public static final Supplier<EntityType<SakuretsuSabotenBoshiProjectile>> SAKURETSU_SABOTEN_BOSHI;
	public static final Supplier<EntityType<TetsuBoshiProjectile>> TETSU_BOSHI;
	public static final Supplier<EntityType<TokuyoAburaBoshiProjectile>> TOKUYO_ABURA_BOSHI;
	public static final Supplier<EntityType<NemuriBoshiProjectile>> NEMURI_BOSHI;
	public static final Supplier<EntityType<HiNoToriBoshiProjectile>> KAEN_BOSHI;
	public static final Supplier<EntityType<HeatBallProjectile>> HEAT_BALL;
	public static final Supplier<EntityType<CoolBallProjectile>> COOL_BALL;
	public static final Supplier<EntityType<ThunderBallProjectile>> THUNDER_BALL;
	public static final Supplier<EntityType<GustSwordProjectile>> GUST_SWORD;
	public static final Supplier<EntityType<WeatherEggProjectile>> WEATHER_EGG;
	public static final Supplier<EntityType<ThunderLanceProjectile>> THUNDER_LANCE;
	public static final Supplier<EntityType<ThunderboltProjectile>> THUNDERBOLT;
	public static final Supplier<EntityType<ThunderstormProjectile>> THUNDERSTORM;
	public static final Supplier<EntityType<TopEntity>> TOP;
	public static final Supplier<EntityType<NormalBulletProjectile>> NORMAL_BULLET;
	public static final Supplier<EntityType<KairosekiBulletProjectile>> KAIROSEKI_BULLET;
	public static final Supplier<EntityType<CannonBallProjectile>> CANNON_BALL;
	public static final Supplier<EntityType<PopGreenProjectile>> POP_GREEN;
	public static final Supplier<EntityType<HookEntity>> HOOK;
	public static final Supplier<EntityType<AxeDialProjectile>> AXE_DIAL_PROJECTILE;
	public static final Supplier<EntityType<MilkyDialProjectile>> MILKY_DIAL_PROJECTILE;
	public static final Supplier<EntityType<LightningDischargeEntity>> LIGHTNING_DISCHARGE;

	public static void init() {
	}
	static  {
		Supplier<EntityType> var0 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(RelaxHourProjectile::new)))).sized(0.5F,0.5F).build("mineminenomi:relax_hour");
		};
		RELAX_HOUR = ModRegistry.registerEntityType("Relax Hour",var0);
		Supplier<EntityType> var1 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(BeroCannonProjectile::new)))).sized(0.5F,0.5F).build("mineminenomi:bero_cannon");
		};
		BERO_CANNON = ModRegistry.registerEntityType("Bero Cannon",var1);
		Supplier<EntityType> var2 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(BarrierCrashProjectile::new)))).sized(4F,5F).build("mineminenomi:barrier_crash");
		};
		BARRIER_CRASH = ModRegistry.registerEntityType("Barrier Crash",var2);
		Supplier<EntityType> var3 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(BarrierbilityStairsProjectile::new)))).sized(0.5F,0.5F).build("mineminenomi:barrierbility_stairs");
		};
		BARRIERBILITY_STAIRS = ModRegistry.registerEntityType("Barrierbility Stairs",var3);
		Supplier<EntityType> var4 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(StickyProjectile::new)))).sized(0.5F,0.5F).build("mineminenomi:sticky");
		};
		STICKY_PROJECTILE = ModRegistry.registerEntityType("Mucus Bomb",var4);
		Supplier<EntityType> var5 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(BetaBetaChainProjectile::new)))).sized(0.5F,0.5F).build("mineminenomi:beta_beta_chain");
		};
		BETA_BETA_CHAIN = ModRegistry.registerEntityType("Beta Chain",var5);
		Supplier<EntityType> var6 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(NoseFancyCannonProjectile::new)))).sized(1F,1F).build("mineminenomi:nose_fancy_cannon");
		};
		NOSE_FANCY_CANNON = ModRegistry.registerEntityType("Nose Fancy Cannon",var6);
		Supplier<EntityType> var7 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(BreezeBreathBombProjectile::new)))).sized(1F,1F).build("mineminenomi:breeze_breath_bomb");
		};
		BREEZE_BREATH_BOMB = ModRegistry.registerEntityType("Breeze Breath Bomb",var7);
		Supplier<EntityType> var8 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(HikenProjectile::new)))).fireImmune().sized(1.2F,1.2F).build("mineminenomi:hiken");
		};
		HIKEN = ModRegistry.registerEntityType("Hiken",var8);
		Supplier<EntityType> var9 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(HiganProjectile::new)))).fireImmune().sized(0.5F,0.5F).build("mineminenomi:higan");
		};
		HIGAN = ModRegistry.registerEntityType("Higan",var9);
		Supplier<EntityType> var10 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(DaiEnkaiEnteiProjectile::new)))).fireImmune().sized(3F,3F).build("mineminenomi:dai_enkai_entei");
		};
		DAI_ENKAI_ENTEI = ModRegistry.registerEntityType("Dai Enkai: Entei",var10);
		Supplier<EntityType> var11 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(DaiEnkaiOnibiProjectile::new)))).fireImmune().sized(7.5F,7.5F).build("mineminenomi:dai_enkai_onibi");
		};
		DAI_ENKAI_ONIBI = ModRegistry.registerEntityType("Dai Enkai: Onibi",var11);
		Supplier<EntityType> var12 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(HidarumaProjectile::new)))).fireImmune().sized(0.5F,0.5F).build("mineminenomi:hidaruma");
		};
		HIDARUMA = ModRegistry.registerEntityType("Hidaruma",var12);
		Supplier<EntityType> var13 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(JujikaProjectile::new)))).fireImmune().sized(1F,1F).build("mineminenomi:jujika");
		};
		JUJIKA = ModRegistry.registerEntityType("Jujika",var13);
		Supplier<EntityType> var14 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(NekkaiGyoraiProjectile::new)))).sized(0.5F,0.5F).build("mineminenomi:nekkai_gyorai");
		};
		NEKKAI_GYORAI = ModRegistry.registerEntityType("Nekkai Gyorai",var14);
		Supplier<EntityType> var15 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(ShishaNoTeProjectile::new)))).sized(0.5F,0.5F).build("mineminenomi:shisha_no_te");
		};
		SHISHA_NO_TE = ModRegistry.registerEntityType("Shisha no Te",var15);
		Supplier<EntityType> var16 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(IceBlockPartisanProjectile::new)))).sized(0.5F,0.5F).build("mineminenomi:ice_block_partisan");
		};
		ICE_BLOCK_PARTISAN = ModRegistry.registerEntityType("Ice Block: Partisan",var16);
		Supplier<EntityType> var17 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(IceBallProjectile::new)))).sized(0.7F,0.7F).build("mineminenomi:ice_ball");
		};
		ICE_BALL = ModRegistry.registerEntityType("Ice Ball",var17);
		Supplier<EntityType> var18 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(IceBlockPheasantProjectile::new)))).sized(2F,2F).build("mineminenomi:ice_block_pheasant");
		};
		ICE_BLOCK_PHEASANT = ModRegistry.registerEntityType("Ice Block: Pheasant",var18);
		Supplier<EntityType> var19 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(IceBlockAvalancheProjectile::new)))).sized(9F,9F).build("mineminenomi:ice_block_avalanche");
		};
		ICE_BLOCK_AVALANCHE = ModRegistry.registerEntityType("Ice Block: Avalanche",var19);
		Supplier<EntityType> var20 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(YukiRabiProjectile::new)))).sized(0.5F,0.5F).build("mineminenomi:yuki_rabi");
		};
		YUKI_RABI = ModRegistry.registerEntityType("Yuki Rabi",var20);
		Supplier<EntityType> var21 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(WhiteBlowProjectile::new)))).sized(0.5F,0.5F).build("mineminenomi:white_blow");
		};
		WHITE_BLOW = ModRegistry.registerEntityType("White Blow",var21);
		Supplier<EntityType> var22 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(WhiteBlowRushProjectile::new)))).sized(0.5F,0.5F).build("mineminenomi:white_blow_rush");
		};
		WHITE_BLOW_RUSH = ModRegistry.registerEntityType("White Blow Rush",var22);
		Supplier<EntityType> var23 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(WhiteSnakeProjectile::new)))).sized(0.5F,0.5F).build("mineminenomi:white_snake");
		};
		WHITE_SNAKE = ModRegistry.registerEntityType("White Snake",var23);
		Supplier<EntityType> var24 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(WhiteOutProjectile::new)))).sized(0.5F,0.5F).build("mineminenomi:white_out");
		};
		WHITE_OUT = ModRegistry.registerEntityType("White Out",var24);
		Supplier<EntityType> var25 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(NoroNoroBeamProjectile::new)))).sized(1.5F,1.5F).build("mineminenomi:noro_noro_beam");
		};
		NORO_NORO_BEAM = ModRegistry.registerEntityType("Noro Noro Beam",var25);
		Supplier<EntityType> var26 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(NegativeHollowProjectile::new)))).sized(0.5F,0.5F).build("mineminenomi:negative_hollow");
		};
		NEGATIVE_HOLLOW = ModRegistry.registerEntityType("Negative Hollow",var26);
		Supplier<EntityType> var27 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(MiniHollowProjectile::new)))).sized(0.5F,0.5F).build("mineminenomi:mini_hollow");
		};
		MINI_HOLLOW = ModRegistry.registerEntityType("Mini Hollow",var27);
		Supplier<EntityType> var28 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(TokuHollowProjectile::new)))).sized(1F,1F).build("mineminenomi:toku_hollow");
		};
		TOKU_HOLLOW = ModRegistry.registerEntityType("Toku Hollow",var28);
		Supplier<EntityType> var29 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(DragonsRoarProjectile::new)))).sized(0.5F,0.5F).build("mineminenomi:dragons_roar");
		};
		DRAGONS_ROAR = ModRegistry.registerEntityType("Dragon's Roar",var29);
		Supplier<EntityType> var30 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(TodorokiProjectile::new)))).sized(0.5F,0.5F).build("mineminenomi:todoroki");
		};
		TODOROKI = ModRegistry.registerEntityType("Todoroki",var30);
		Supplier<EntityType> var31 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(AwaseBaoriProjectile::new)))).sized(0.5F,0.5F).build("mineminenomi:awase_baori");
		};
		AWASE_BAORI = ModRegistry.registerEntityType("Awase Baori",var31);
		Supplier<EntityType> var32 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(MeroMeroMellowProjectile::new)))).sized(3.5F,3.5F).build("mineminenomi:mero_mero_mellow");
		};
		MERO_MERO_MELLOW = ModRegistry.registerEntityType("Mero Mero Mellow",var32);
		Supplier<EntityType> var33 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(PistolKissProjectile::new)))).sized(0.5F,0.5F).build("mineminenomi:pistol_kiss");
		};
		PISTOL_KISS = ModRegistry.registerEntityType("Pistol Kiss",var33);
		Supplier<EntityType> var34 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(SlaveArrowProjectile::new)))).sized(0.5F,0.5F).build("mineminenomi:slave_arrow");
		};
		SLAVE_ARROW = ModRegistry.registerEntityType("Slave Arrow",var34);
		Supplier<EntityType> var35 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(SpringDeathKnockProjectile::new)))).sized(1.5F,1.5F).build("mineminenomi:spring_death_knock");
		};
		SPRING_DEATH_KNOCK = ModRegistry.registerEntityType("Spring Death Knock",var35);
		Supplier<EntityType> var36 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(PadHoProjectile::new)))).sized(0.5F,0.5F).build("mineminenomi:pad_ho");
		};
		PAD_HO = ModRegistry.registerEntityType("Pad Ho",var36);
		Supplier<EntityType> var37 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(UrsusShockProjectile::new)))).sized(1F,1F).build("mineminenomi:ursus_shock");
		};
		URSUS_SHOCK = ModRegistry.registerEntityType("Ursus Shock",var37);
		Supplier<EntityType> var38 = () -> {
			EntityType.EntityFactory lv39_0 = ChargingUrsusShockEntity::new;
			return ModRegistry.createEntityType(lv39_0,MobCategory.MISC).sized(1F,1F).build("mineminenomi:charging_ursus_shock");
		};
		CHARGING_URSUS_SHOCK = ModRegistry.registerEntityType("Charging Ursus Shock",var38);
		Supplier<EntityType> var39 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(PainEntity::new)))).sized(1.5F,1.5F).build("mineminenomi:pain");
		};
		PAIN = ModRegistry.registerEntityType("Pain",var39);
		Supplier<EntityType> var40 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(AmaterasuProjectile::new)))).sized(0.5F,0.5F).build("mineminenomi:amaterasu");
		};
		AMATERASU = ModRegistry.registerEntityType("Amaterasu",var40);
		Supplier<EntityType> var41 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(YasakaniNoMagatamaProjectile::new)))).sized(0.2F,0.2F).build("mineminenomi:yasakani_no_magatama");
		};
		YASAKANI_NO_MAGATAMA = ModRegistry.registerEntityType("Yasakani no Magatama",var41);
		Supplier<EntityType> var42 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(DaiFunkaProjectile::new)))).sized(4.5F,4.5F).build("mineminenomi:dai_funka");
		};
		DAI_FUNKA = ModRegistry.registerEntityType("Dai Funka",var42);
		Supplier<EntityType> var43 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(RyuseiKazanProjectile::new)))).sized(4.5F,4.5F).build("mineminenomi:ryusei_kazan");
		};
		RYUSEI_KAZAN = ModRegistry.registerEntityType("Ryusei Kazan",var43);
		Supplier<EntityType> var44 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(MeigoProjectile::new)))).sized(0.5F,0.5F).build("mineminenomi:meigo");
		};
		MEIGO = ModRegistry.registerEntityType("Meigo",var44);
		Supplier<EntityType> var45 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(HanaHandsEntity::new)))).sized(0.1F,0.1F).build("mineminenomi:hana_hands");
		};
		HANA_HANDS = ModRegistry.registerEntityType("Hana Hands",var45);
		Supplier<EntityType> var46 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(HanaFeetEntity::new)))).sized(2F,2F).build("mineminenomi:hana_feet");
		};
		HANA_FEET = ModRegistry.registerEntityType("Feet",var46);
		Supplier<EntityType> var47 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(CampoDeFloresEntity::new)))).sized(1F,0.1F).build("mineminenomi:campo_de_flores");
		};
		CAMPO_DE_FLORES = ModRegistry.registerEntityType("Campo de Flores",var47);
		Supplier<EntityType> var48 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(DoruDoruArtsMoriProjectile::new)))).sized(0.5F,0.5F).build("mineminenomi:doru_doru_arts_mori");
		};
		DORU_DORU_ARTS_MORI = ModRegistry.registerEntityType("Doru Doru Arts: Mori",var48);
		Supplier<EntityType> var49 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(CandleLockProjectile::new)))).sized(0.5F,0.5F).build("mineminenomi:candle_lock");
		};
		CANDLE_LOCK = ModRegistry.registerEntityType("Candle Lock",var49);
		Supplier<EntityType> var50 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(ChampFightProjectile::new)))).sized(0.5F,0.5F).build("mineminenomi:champ_fight");
		};
		CHAMP_FIGHT = ModRegistry.registerEntityType("Champ Fight",var50);
		Supplier<EntityType> var51 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(GasRobeProjectile::new)))).sized(1F,1F).build("mineminenomi:gas_robe");
		};
		GAS_ROBE = ModRegistry.registerEntityType("Gas Robe",var51);
		Supplier<EntityType> var52 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(GastilleProjectile::new)))).sized(0.5F,0.5F).build("mineminenomi:gastille");
		};
		GASTILLE = ModRegistry.registerEntityType("Gastille",var52);
		Supplier<EntityType> var53 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(DesertSpadaProjectile::new)))).sized(2F,2F).build("mineminenomi:desert_spada");
		};
		DESERT_SPADA = ModRegistry.registerEntityType("Desert Spada",var53);
		Supplier<EntityType> var54 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(DesertGrandeEspadaProjectile::new)))).sized(1.25F,1.25F).build("mineminenomi:desert_grande_spada");
		};
		DESERT_GRANDE_ESPADA = ModRegistry.registerEntityType("Desert Grande Spada",var54);
		Supplier<EntityType> var55 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(SablesPesadoProjectile::new)))).sized(1.5F,1.5F).build("mineminenomi:sables_pesado");
		};
		SABLES_PESADO = ModRegistry.registerEntityType("Sables Pesado",var55);
		Supplier<EntityType> var56 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(GekishinProjectile::new)))).sized(1.25F,1.25F).build("mineminenomi:gekishin");
		};
		GEKISHIN = ModRegistry.registerEntityType("Gekishin",var56);
		Supplier<EntityType> var57 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(SpiralHollowProjectile::new)))).sized(1F,1F).build("mineminenomi:spiral_hollow");
		};
		SPIRAL_HOLLOW = ModRegistry.registerEntityType("Spiral Hollow",var57);
		Supplier<EntityType> var58 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(SparklingDaisyAftereffectProjectile::new)))).sized(1F,1F).build("mineminenomi:sparkling_daisy_after");
		};
		SPARKLING_DAISY_AFTER = ModRegistry.registerEntityType("Sparkling Daisy Aftereffect",var58);
		Supplier<EntityType> var59 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(PoisonSpitProjectile::new)))).sized(0.5F,0.5F).build("mineminenomi:poison_spit");
		};
		POISON_SPIT = ModRegistry.registerEntityType("Poison Spit",var59);
		Supplier<EntityType> var60 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(ImpactBlastProjectile::new)))).sized(0.5F,0.5F).build("mineminenomi:impact_blast");
		};
		IMPACT_BLAST = ModRegistry.registerEntityType("Impact Blast",var60);
		Supplier<EntityType> var61 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(SagariNoRyuseiProjectile::new)))).sized(8F,8F).build("mineminenomi:sagari_no_ryusei");
		};
		SAGARI_NO_RYUSEI = ModRegistry.registerEntityType("Sagari no Ryusei",var61);
		Supplier<EntityType> var62 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(MokoProjectile::new)))).sized(1F,1F).build("mineminenomi:moko");
		};
		MOKO = ModRegistry.registerEntityType("Moko",var62);
		Supplier<EntityType> var63 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(CandyEscalatorProjectile::new)))).sized(0.5F,0.5F).build("mineminenomi:candy_escalator");
		};
		CANDY_ESCALATOR = ModRegistry.registerEntityType("Candy Escalator",var63);
		Supplier<EntityType> var64 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(CandyWaveProjectile::new)))).sized(3F,3F).build("mineminenomi:candy_wave");
		};
		CANDY_WAVE = ModRegistry.registerEntityType("Candy Wave",var64);
		Supplier<EntityType> var65 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(TamaitoProjectile::new)))).sized(0.5F,0.5F).build("mineminenomi:tamaito");
		};
		TAMAITO = ModRegistry.registerEntityType("Tamaito",var65);
		Supplier<EntityType> var66 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(StringPillarProjectile::new)))).sized(1F,10F).build("mineminenomi:string_pillar");
		};
		STRING_PILLAR = ModRegistry.registerEntityType("String Pillar",var66);
		Supplier<EntityType> var67 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(OverheatProjectile::new)))).sized(0.5F,0.5F).build("mineminenomi:overheat");
		};
		OVERHEAT = ModRegistry.registerEntityType("Overheat",var67);
		Supplier<EntityType> var68 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(BrickBatProjectile::new)))).sized(0.5F,0.5F).build("mineminenomi:brick_bat");
		};
		BRICK_BAT = ModRegistry.registerEntityType("Brick Bat",var68);
		Supplier<EntityType> var69 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(BlackBoxProjectile::new)))).sized(0.5F,0.5F).build("mineminenomi:black_box");
		};
		BLACK_BOX = ModRegistry.registerEntityType("Brick Box",var69);
		Supplier<EntityType> var70 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(TsunoTokagePillarEntity::new)))).sized(1.5F,1.5F).build("mineminenomi:tsuno_tokage");
		};
		TSUNO_TOKAGE = ModRegistry.registerEntityType("Tsuno Tokage",var70);
		Supplier<EntityType> var71 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(HydraProjectile::new)))).sized(2F,2F).build("mineminenomi:hydra");
		};
		HYDRA = ModRegistry.registerEntityType("Hydra",var71);
		Supplier<EntityType> var72 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(ChloroBallProjectile::new)))).sized(0.75F,0.75F).build("mineminenomi:chloro_ball");
		};
		CHLORO_BALL = ModRegistry.registerEntityType("Chloro Ball",var72);
		Supplier<EntityType> var73 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(VenomRoadProjectile::new)))).sized(2F,2F).build("mineminenomi:venom_road");
		};
		VENOM_ROAD = ModRegistry.registerEntityType("Venom Road",var73);
		Supplier<EntityType> var74 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(GomuGomuNoPistolProjectile::new)))).sized(0.5F,0.5F).build("mineminenomi:gomu_gomu_no_pistol");
		};
		GOMU_GOMU_NO_PISTOL = ModRegistry.registerEntityType("Gomu Gomu no Pistol",var74);
		Supplier<EntityType> var75 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(GomuGomuNoJetPistolProjectile::new)))).sized(0.5F,0.5F).build("mineminenomi:gomu_gomu_no_jet_pistol");
		};
		GOMU_GOMU_NO_JET_PISTOL = ModRegistry.registerEntityType("Gomu Gomu no Jet Pistol",var75);
		Supplier<EntityType> var76 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(GomuGomuNoElephantGunProjectile::new)))).sized(5F,5F).build("mineminenomi:gomu_gomu_no_elephant_gun");
		};
		GOMU_GOMU_NO_ELEPHANT_GUN = ModRegistry.registerEntityType("Gomu Gomu no Elephant Gun",var76);
		Supplier<EntityType> var77 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(GomuGomuNoKingKongGunProjectile::new)))).sized(5F,5F).build("mineminenomi:gomu_gomu_no_king_kong_gun");
		};
		GOMU_GOMU_NO_KING_KONG_GUN = ModRegistry.registerEntityType("Gomu Gomu no King Kong Gun",var77);
		Supplier<EntityType> var78 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(GomuGomuNoBajrangGunProjectile::new)))).sized(20F,20F).build("mineminenomi:gomu_gomu_no_bajrang_gun");
		};
		GOMU_GOMU_NO_BAJRANG_GUN = ModRegistry.registerEntityType("Gomu Gomu no Bajrang Gun",var78);
		Supplier<EntityType> var79 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(GomuGomuNoBazookaProjectile::new)))).sized(0.5F,0.5F).build("mineminenomi:gomu_gomu_no_bazooka");
		};
		GOMU_GOMU_NO_BAZOOKA = ModRegistry.registerEntityType("Gomu Gomu no Bazooka",var79);
		Supplier<EntityType> var80 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(GomuGomuNoJetBazookaProjectile::new)))).sized(0.5F,0.5F).build("mineminenomi:gomu_gomu_no_jet_bazooka");
		};
		GOMU_GOMU_NO_JET_BAZOOKA = ModRegistry.registerEntityType("Gomu Gomu no Jet Bazooka",var80);
		Supplier<EntityType> var81 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(GomuGomuNoGrizzlyMagnumProjectile::new)))).sized(5F,5F).build("mineminenomi:gomu_gomu_no_grizzly_magnum");
		};
		GOMU_GOMU_NO_GRIZZLY_MAGNUM = ModRegistry.registerEntityType("Gomu Gomu no Grizzly Magnum",var81);
		Supplier<EntityType> var82 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(GomuGomuNoLeoBazookaProjectile::new)))).sized(5F,5F).build("mineminenomi:gomu_gomu_no_leo_bazooka");
		};
		GOMU_GOMU_NO_LEO_BAZOOKA = ModRegistry.registerEntityType("Gomu Gomu no Leo Bazooka",var82);
		Supplier<EntityType> var83 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(GomuGomuNoRocketProjectile::new)))).sized(1F,1F).build("mineminenomi:gomu_gomu_no_rocket");
		};
		GOMU_GOMU_NO_ROCKET = ModRegistry.registerEntityType("Gomu Gomu no Rocket",var83);
		Supplier<EntityType> var84 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(SangoProjectile::new)))).sized(0.5F,0.5F).build("mineminenomi:sango");
		};
		SANGO = ModRegistry.registerEntityType("Sango",var84);
		Supplier<EntityType> var85 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(ElThorProjectile::new)))).sized(0.5F,0.5F).build("mineminenomi:el_thor");
		};
		EL_THOR = ModRegistry.registerEntityType("El Thor",var85);
		Supplier<EntityType> var86 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(RaigoProjectile::new)))).sized(15F,15F).build("mineminenomi:raigo");
		};
		RAIGO = ModRegistry.registerEntityType("Raigo",var86);
		Supplier<EntityType> var87 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(RaigoLightningProjectile::new)))).sized(0.5F,0.5F).build("mineminenomi:raigo_lightning");
		};
		RAIGO_LIGHTNING = ModRegistry.registerEntityType("Raigo Lightning",var87);
		Supplier<EntityType> var88 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(LiberationProjectile::new)))).sized(1F,1F).build("mineminenomi:liberation");
		};
		LIBERATION = ModRegistry.registerEntityType("Liberation",var88);
		Supplier<EntityType> var89 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(DarkMatterProjectile::new)))).sized(1F,1F).build("mineminenomi:dark_matter");
		};
		DARK_MATTER = ModRegistry.registerEntityType("Dark Matter",var89);
		Supplier<EntityType> var90 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(BlackRoadProjectile::new)))).sized(1F,1F).build("mineminenomi:black_road");
		};
		BLACK_ROAD = ModRegistry.registerEntityType("Black Road",var90);
		Supplier<EntityType> var91 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(PunkGibsonProjectile::new)))).sized(1.5F,1.5F).build("mineminenomi:punk_gibson");
		};
		PUNK_GIBSON = ModRegistry.registerEntityType("Punk Gibson",var91);
		Supplier<EntityType> var92 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(PunkPistolProjectile::new)))).sized(0.5F,0.5F).build("mineminenomi:punk_pistol");
		};
		PUNK_PISTOL = ModRegistry.registerEntityType("Punk Pistol",var92);
		Supplier<EntityType> var93 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(DamnedPunkProjectile::new)))).sized(1F,1F).build("mineminenomi:damned_punk");
		};
		DAMNED_PUNK = ModRegistry.registerEntityType("Damned Punk",var93);
		Supplier<EntityType> var94 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(GenocideRaidEffectEntity::new)))).sized(0.5F,0.5F).build("mineminenomi:genocide_raid_effect");
		};
		GENOCIDE_RAID_EFFECT = ModRegistry.registerEntityType("Genocide Raid Effect",var94);
		Supplier<EntityType> var95 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(BarizodonProjectile::new)))).sized(1.5F,1.5F).build("mineminenomi:barizodon");
		};
		BARIZODON = ModRegistry.registerEntityType("Barizodon",var95);
		Supplier<EntityType> var96 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(TempuraudonProjectile::new)))).sized(0.5F,0.5F).build("mineminenomi:tempuraudon");
		};
		TEMPURAUDON = ModRegistry.registerEntityType("Tempuraudon",var96);
		Supplier<EntityType> var97 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(BaraBaraHoProjectile::new)))).sized(1.2F,1.2F).build("mineminenomi:bara_bara_ho");
		};
		BARA_BARA_HO = ModRegistry.registerEntityType("Bara Bara Ho",var97);
		Supplier<EntityType> var98 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(DaiCircusProjectile::new)))).sized(0.5F,0.5F).build("mineminenomi:dai_circus");
		};
		DAI_CIRCUS = ModRegistry.registerEntityType("Dai Circus",var98);
		Supplier<EntityType> var99 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(BaraFestivalEntity::new)))).sized(0.1F,0.1F).build("mineminenomi:bara_festival");
		};
		BARA_FESTIVAL = ModRegistry.registerEntityType("Bara Festival",var99);
		Supplier<EntityType> var100 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(PhoenixGoenProjectile::new)))).sized(1.5F,1.5F).build("mineminenomi:phoenix_goen");
		};
		PHOENIX_GOEN = ModRegistry.registerEntityType("Phoenix Goen",var100);
		Supplier<EntityType> var101 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(TrunkShotProjectile::new)))).sized(1.5F,1.5F).build("mineminenomi:trunk_shot");
		};
		TRUNK_SHOT = ModRegistry.registerEntityType("Trunk Shot",var101);
		Supplier<EntityType> var102 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(RankyakuProjectile::new)))).sized(5.25F,0.5F).build("mineminenomi:rankyaku");
		};
		RANKYAKU = ModRegistry.registerEntityType("Rankyaku",var102);
		Supplier<EntityType> var103 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(RokuoganProjectile::new)))).sized(3F,3F).build("mineminenomi:rokuogan");
		};
		ROKUOGAN = ModRegistry.registerEntityType("Rokuogan",var103);
		Supplier<EntityType> var104 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(UchimizuProjectile::new)))).sized(0.5F,0.5F).build("mineminenomi:uchimizu");
		};
		UCHIMIZU = ModRegistry.registerEntityType("Uchimizu",var104);
		Supplier<EntityType> var105 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(PackOfSharksProjectile::new)))).sized(0.5F,0.5F).build("mineminenomi:pack_of_sharks");
		};
		PACK_OF_SHARKS = ModRegistry.registerEntityType("Pack of Sharks",var105);
		Supplier<EntityType> var106 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(YarinamiProjectile::new)))).sized(2F,2F).build("mineminenomi:yarinami");
		};
		YARINAMI = ModRegistry.registerEntityType("Yarinami",var106);
		Supplier<EntityType> var107 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(MizuTaihoProjectile::new)))).sized(2.5F,2.5F).build("mineminenomi:mizu_taiho");
		};
		MIZU_TAIHO = ModRegistry.registerEntityType("Mizu Taiho",var107);
		Supplier<EntityType> var108 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(MizuShuryudanProjectile::new)))).sized(0.5F,0.5F).build("mineminenomi:mizu_shuryudan");
		};
		MIZU_SHURYUDAN = ModRegistry.registerEntityType("Mizu Shuryudan",var108);
		Supplier<EntityType> var109 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(FreshFireProjectile::new)))).sized(0.5F,0.5F).fireImmune().build("mineminenomi:fresh_fire");
		};
		FRESH_FIRE = ModRegistry.registerEntityType("Fresh Fire",var109);
		Supplier<EntityType> var110 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(StrongRightProjectile::new)))).sized(0.5F,0.5F).build("mineminenomi:strong_right");
		};
		STRONG_RIGHT = ModRegistry.registerEntityType("Strong Right",var110);
		Supplier<EntityType> var111 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(CoupDeVentProjectile::new)))).sized(0.5F,0.5F).build("mineminenomi:coup_de_vent");
		};
		COUP_DE_VENT = ModRegistry.registerEntityType("Coup de Vent",var111);
		Supplier<EntityType> var112 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(RadicalBeamProjectile::new)))).sized(0.5F,0.5F).build("mineminenomi:radical_beam");
		};
		RADICAL_BEAM = ModRegistry.registerEntityType("Radical Beam",var112);
		Supplier<EntityType> var113 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(ElectricalLunaProjectile::new)))).sized(0.5F,0.5F).build("mineminenomi:electrical_luna");
		};
		ELECTRICAL_LUNA = ModRegistry.registerEntityType("Electrical Luna",var113);
		Supplier<EntityType> var114 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(ElectricalShowerProjectile::new)))).sized(0.5F,0.5F).build("mineminenomi:electrical_shower");
		};
		ELECTRICAL_SHOWER = ModRegistry.registerEntityType("Electrical Shower",var114);
		Supplier<EntityType> var115 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(ElectroVisualProjectile::new)))).sized(0.5F,0.5F).build("mineminenomi:electro_visual");
		};
		ELECTRO_VISUAL = ModRegistry.registerEntityType("Electro Visual",var115);
		Supplier<EntityType> var116 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(SanbyakurokujuPoundHoProjectile::new)))).sized(3F,0.5F).build("mineminenomi:sanbyakurokuju_pound_ho");
		};
		SANBYAKUROKUJU_POUND_HO = ModRegistry.registerEntityType("Sanbyakurokuju Pound Ho",var116);
		Supplier<EntityType> var117 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(YakkodoriProjectile::new)))).sized(0.5F,1.5F).build("mineminenomi:yakkodori");
		};
		YAKKODORI = ModRegistry.registerEntityType("Yakkodori",var117);
		Supplier<EntityType> var118 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(KingPunchProjectile::new)))).sized(0.5F,0.5F).build("mineminenomi:king_punch");
		};
		KING_PUNCH = ModRegistry.registerEntityType("King Punch",var118);
		Supplier<EntityType> var119 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(ExtraHachisProjectile::new)))).sized(0.5F,0.5F).build("mineminenomi:extra_hachis");
		};
		EXTRA_HACHIS = ModRegistry.registerEntityType("Extra Hachis",var119);
		Supplier<EntityType> var120 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(PoeleAFrireProjectile::new)))).sized(0.5F,0.5F).build("mineminenomi:poele_a_frire");
		};
		POELE_A_FRIRE = ModRegistry.registerEntityType("Poele a Frire",var120);
		Supplier<EntityType> var121 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(HiNoToriBoshiProjectile::new)))).sized(2.25F,2.25F).build("mineminenomi:hi_no_tori_boshi");
		};
		HI_NO_TORI_BOSHI = ModRegistry.registerEntityType("Hi no Tori Boshi",var121);
		Supplier<EntityType> var122 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(KemuriBoshiProjectile::new)))).sized(0.5F,0.5F).build("mineminenomi:kemuri_boshi");
		};
		KEMURI_BOSHI = ModRegistry.registerEntityType("Kemuri Boshi",var122);
		Supplier<EntityType> var123 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(RenpatsuNamariBoshiProjectile::new)))).sized(0.5F,0.5F).build("mineminenomi:renpatsu_namari_boshi");
		};
		RENPATSU_NAMARI_BOSHI = ModRegistry.registerEntityType("Renpatsu Namari Boshi",var123);
		Supplier<EntityType> var124 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(SakuretsuSabotenBoshiProjectile::new)))).sized(0.5F,0.5F).build("mineminenomi:sakuretsu_saboten_boshi");
		};
		SAKURETSU_SABOTEN_BOSHI = ModRegistry.registerEntityType("Sakuretsu Saboten Boshi",var124);
		Supplier<EntityType> var125 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(TetsuBoshiProjectile::new)))).sized(0.5F,0.5F).build("mineminenomi:tetsu_boshi");
		};
		TETSU_BOSHI = ModRegistry.registerEntityType("Tetsu Boshi",var125);
		Supplier<EntityType> var126 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(TokuyoAburaBoshiProjectile::new)))).sized(0.5F,0.5F).build("mineminenomi:tokuya_abura_boshi");
		};
		TOKUYO_ABURA_BOSHI = ModRegistry.registerEntityType("Tokuyo Abura Boshi",var126);
		Supplier<EntityType> var127 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(NemuriBoshiProjectile::new)))).sized(0.5F,0.5F).build("mineminenomi:nemuri_boshi");
		};
		NEMURI_BOSHI = ModRegistry.registerEntityType("Nemuri Boshi",var127);
		Supplier<EntityType> var128 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(HiNoToriBoshiProjectile::new)))).sized(0.4F,0.4F).build("mineminenomi:kaen_boshi");
		};
		KAEN_BOSHI = ModRegistry.registerEntityType("Kaen Boshi",var128);
		Supplier<EntityType> var129 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(HeatBallProjectile::new)))).sized(0.5F,0.5F).build("heat_ball");
		};
		HEAT_BALL = ModRegistry.registerEntityType("Heat Ball",var129);
		Supplier<EntityType> var130 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(CoolBallProjectile::new)))).sized(0.5F,0.5F).build("cool_ball");
		};
		COOL_BALL = ModRegistry.registerEntityType("Cool Ball",var130);
		Supplier<EntityType> var131 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(ThunderBallProjectile::new)))).sized(0.5F,0.5F).build("thunder_ball");
		};
		THUNDER_BALL = ModRegistry.registerEntityType("Thunder Ball",var131);
		Supplier<EntityType> var132 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(GustSwordProjectile::new)))).sized(0.5F,0.5F).build("gust_sword");
		};
		GUST_SWORD = ModRegistry.registerEntityType("Gust Sword",var132);
		Supplier<EntityType> var133 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(WeatherEggProjectile::new)))).sized(0.5F,0.5F).build("weather_egg");
		};
		WEATHER_EGG = ModRegistry.registerEntityType("Weather Egg",var133);
		Supplier<EntityType> var134 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(ThunderLanceProjectile::new)))).sized(0.5F,0.5F).build("mineminenomi:thunder_lance");
		};
		THUNDER_LANCE = ModRegistry.registerEntityType("Thunder Lance",var134);
		Supplier<EntityType> var135 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(ThunderboltProjectile::new)))).sized(0.5F,0.5F).build("mineminenomi:thunderbolt");
		};
		THUNDERBOLT = ModRegistry.registerEntityType("Thunderbolt",var135);
		Supplier<EntityType> var136 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(ThunderstormProjectile::new)))).sized(0.5F,0.5F).build("mineminenomi:thunderstorm");
		};
		THUNDERSTORM = ModRegistry.registerEntityType("Thunderstorm",var136);
		Supplier<EntityType> var137 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(TopEntity::new)))).sized(0.25F,0.25F).build("mineminenomi:top");
		};
		TOP = ModRegistry.registerEntityType("Top",var137);
		Supplier<EntityType> var138 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(NormalBulletProjectile::new)))).sized(0.3F,0.3F).build("mineminenomi:normal_bullet");
		};
		NORMAL_BULLET = ModRegistry.registerEntityType("Normal Bullet",var138);
		Supplier<EntityType> var139 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(KairosekiBulletProjectile::new)))).sized(0.3F,0.3F).build("mineminenomi:kairoseki_bullet");
		};
		KAIROSEKI_BULLET = ModRegistry.registerEntityType("Kairoseki Bullet",var139);
		Supplier<EntityType> var140 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(CannonBallProjectile::new)))).sized(0.75F,0.75F).build("mineminenomi:cannon_ball");
		};
		CANNON_BALL = ModRegistry.registerEntityType("Cannon Ball",var140);
		Supplier<EntityType> var141 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(PopGreenProjectile::new)))).sized(0.3F,0.3F).build("mineminenomi:pop_green");
		};
		POP_GREEN = ModRegistry.registerEntityType("Pop Green",var141);
		Supplier<EntityType> var142 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(HookEntity::new)))).build("mineminenomi:hook");
		};
		HOOK = ModRegistry.registerEntityType("Hook",var142);
		Supplier<EntityType> var143 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(AxeDialProjectile::new)))).sized(3.5F,0.5F).build("mineminenomi:axe_dial_projectile");
		};
		AXE_DIAL_PROJECTILE = ModRegistry.registerEntityType("Axe Dial",var143);
		Supplier<EntityType> var144 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(MilkyDialProjectile::new)))).sized(0.3F,0.3F).build("mineminenomi:milky_dial_projectile");
		};
		MILKY_DIAL_PROJECTILE = ModRegistry.registerEntityType("Milky Dial",var144);
		Supplier<EntityType> var145 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(LightningDischargeEntity::new)))).sized(0.1F,0.1F).build("mineminenomi:lightning_discharge");
		};
		LIGHTNING_DISCHARGE = ModRegistry.registerEntityType("Lightning Discharge",var145);
	}
}