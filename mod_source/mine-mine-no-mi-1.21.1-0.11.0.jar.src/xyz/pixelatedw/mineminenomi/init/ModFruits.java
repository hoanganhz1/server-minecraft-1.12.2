package xyz.pixelatedw.mineminenomi.init;

import java.util.function.Supplier;
import net.neoforged.neoforge.registries.DeferredItem;
import xyz.pixelatedw.mineminenomi.abilities.BulletReflectAbility;
import xyz.pixelatedw.mineminenomi.abilities.ExplosionImmunityAbility;
import xyz.pixelatedw.mineminenomi.abilities.HandcuffsImmunityAbility;
import xyz.pixelatedw.mineminenomi.abilities.HeatImmunityAbility;
import xyz.pixelatedw.mineminenomi.abilities.LogiaHeatImmunityAbility;
import xyz.pixelatedw.mineminenomi.abilities.LogiaImmunityAbility;
import xyz.pixelatedw.mineminenomi.abilities.NegativeImmunityAbility;
import xyz.pixelatedw.mineminenomi.abilities.NoFallDamageAbility;
import xyz.pixelatedw.mineminenomi.abilities.PoisonImmunityAbility;
import xyz.pixelatedw.mineminenomi.abilities.SlashDamageImmunityAbility;
import xyz.pixelatedw.mineminenomi.abilities.awa.GoldenHourAbility;
import xyz.pixelatedw.mineminenomi.abilities.awa.RelaxHourAbility;
import xyz.pixelatedw.mineminenomi.abilities.awa.SoapDefenseAbility;
import xyz.pixelatedw.mineminenomi.abilities.baku.BakuFactoryAbility;
import xyz.pixelatedw.mineminenomi.abilities.baku.BakuMunchAbility;
import xyz.pixelatedw.mineminenomi.abilities.baku.BakuTsuihoAbility;
import xyz.pixelatedw.mineminenomi.abilities.baku.BeroCannonAbility;
import xyz.pixelatedw.mineminenomi.abilities.bane.SpringDeathKnockAbility;
import xyz.pixelatedw.mineminenomi.abilities.bane.SpringHopperAbility;
import xyz.pixelatedw.mineminenomi.abilities.bane.SpringSnipeAbility;
import xyz.pixelatedw.mineminenomi.abilities.bara.BaraBaraCarAbility;
import xyz.pixelatedw.mineminenomi.abilities.bara.BaraBaraFestivalAbility;
import xyz.pixelatedw.mineminenomi.abilities.bara.BaraBaraHoAbility;
import xyz.pixelatedw.mineminenomi.abilities.bara.BaraSplitAbility;
import xyz.pixelatedw.mineminenomi.abilities.bara.KuchuKirimomiDaiCircusAbility;
import xyz.pixelatedw.mineminenomi.abilities.bari.BariBariNoPistolAbility;
import xyz.pixelatedw.mineminenomi.abilities.bari.BarrierAbility;
import xyz.pixelatedw.mineminenomi.abilities.bari.BarrierBallAbility;
import xyz.pixelatedw.mineminenomi.abilities.bari.BarrierCrashAbility;
import xyz.pixelatedw.mineminenomi.abilities.bari.BarrierbilityBatAbility;
import xyz.pixelatedw.mineminenomi.abilities.bari.BarrierbilityStairsAbility;
import xyz.pixelatedw.mineminenomi.abilities.beta.BetaBetaChainAbility;
import xyz.pixelatedw.mineminenomi.abilities.beta.BetaCoatingAbility;
import xyz.pixelatedw.mineminenomi.abilities.beta.BetaImmunityAbility;
import xyz.pixelatedw.mineminenomi.abilities.beta.BetaLauncherAbility;
import xyz.pixelatedw.mineminenomi.abilities.beta.HanamizuShinkenAbility;
import xyz.pixelatedw.mineminenomi.abilities.bomu.BreezeBreathBombAbility;
import xyz.pixelatedw.mineminenomi.abilities.bomu.ExplosivePunchAbility;
import xyz.pixelatedw.mineminenomi.abilities.bomu.KickBombAbility;
import xyz.pixelatedw.mineminenomi.abilities.bomu.NoseFancyCannonAbility;
import xyz.pixelatedw.mineminenomi.abilities.bomu.ZenshinKibakuAbility;
import xyz.pixelatedw.mineminenomi.abilities.chiyu.ChiyupopoAbility;
import xyz.pixelatedw.mineminenomi.abilities.chiyu.HealingTouchAbility;
import xyz.pixelatedw.mineminenomi.abilities.chiyu.KenpopoAbility;
import xyz.pixelatedw.mineminenomi.abilities.chiyu.TearsAbility;
import xyz.pixelatedw.mineminenomi.abilities.deka.DekaDekaAbility;
import xyz.pixelatedw.mineminenomi.abilities.deka.DekaTrampleAbility;
import xyz.pixelatedw.mineminenomi.abilities.doa.AirDoorAbility;
import xyz.pixelatedw.mineminenomi.abilities.doa.DoorDoorAbility;
import xyz.pixelatedw.mineminenomi.abilities.doa.KaitenDoorAbility;
import xyz.pixelatedw.mineminenomi.abilities.doku.ChloroBallAbility;
import xyz.pixelatedw.mineminenomi.abilities.doku.DokuFuguAbility;
import xyz.pixelatedw.mineminenomi.abilities.doku.DokuGumoAbility;
import xyz.pixelatedw.mineminenomi.abilities.doku.HydraAbility;
import xyz.pixelatedw.mineminenomi.abilities.doku.VenomDemonAbility;
import xyz.pixelatedw.mineminenomi.abilities.doku.VenomRoadAbility;
import xyz.pixelatedw.mineminenomi.abilities.doru.CandleChampionAbility;
import xyz.pixelatedw.mineminenomi.abilities.doru.CandleHouseAbility;
import xyz.pixelatedw.mineminenomi.abilities.doru.CandleLockAbility;
import xyz.pixelatedw.mineminenomi.abilities.doru.CandleWallAbility;
import xyz.pixelatedw.mineminenomi.abilities.doru.DoruDoruArtsKenAbility;
import xyz.pixelatedw.mineminenomi.abilities.doru.DoruDoruArtsMoriAbility;
import xyz.pixelatedw.mineminenomi.abilities.doru.DoruDoruArtsPickaxeAbility;
import xyz.pixelatedw.mineminenomi.abilities.doru.DoruDoruBallAbility;
import xyz.pixelatedw.mineminenomi.abilities.doru.DoruDoruNoYakataAbility;
import xyz.pixelatedw.mineminenomi.abilities.gasu.BlueSwordAbility;
import xyz.pixelatedw.mineminenomi.abilities.gasu.GasRobeAbility;
import xyz.pixelatedw.mineminenomi.abilities.gasu.GastanetAbility;
import xyz.pixelatedw.mineminenomi.abilities.gasu.GastilleAbility;
import xyz.pixelatedw.mineminenomi.abilities.gasu.GasuFlyAbility;
import xyz.pixelatedw.mineminenomi.abilities.gasu.GasuImmunityAbility;
import xyz.pixelatedw.mineminenomi.abilities.gasu.GasuLogiaAbility;
import xyz.pixelatedw.mineminenomi.abilities.gasu.KarakuniAbility;
import xyz.pixelatedw.mineminenomi.abilities.gasu.KoroAbility;
import xyz.pixelatedw.mineminenomi.abilities.gasu.ShinokuniAbility;
import xyz.pixelatedw.mineminenomi.abilities.goe.DragonsRoarAbility;
import xyz.pixelatedw.mineminenomi.abilities.goe.TodorokiAbility;
import xyz.pixelatedw.mineminenomi.abilities.gomu.BouncyAbility;
import xyz.pixelatedw.mineminenomi.abilities.gomu.GearFifthAbility;
import xyz.pixelatedw.mineminenomi.abilities.gomu.GearFourthAbility;
import xyz.pixelatedw.mineminenomi.abilities.gomu.GearFourthFlightAbility;
import xyz.pixelatedw.mineminenomi.abilities.gomu.GearSecondAbility;
import xyz.pixelatedw.mineminenomi.abilities.gomu.GearThirdAbility;
import xyz.pixelatedw.mineminenomi.abilities.gomu.GomuGomuNoBazookaAbility;
import xyz.pixelatedw.mineminenomi.abilities.gomu.GomuGomuNoDawnWhipAbility;
import xyz.pixelatedw.mineminenomi.abilities.gomu.GomuGomuNoGatlingAbility;
import xyz.pixelatedw.mineminenomi.abilities.gomu.GomuGomuNoGigantAbility;
import xyz.pixelatedw.mineminenomi.abilities.gomu.GomuGomuNoPistolAbility;
import xyz.pixelatedw.mineminenomi.abilities.gomu.GomuGomuNoRocketAbility;
import xyz.pixelatedw.mineminenomi.abilities.gomu.GomuImmunityAbility;
import xyz.pixelatedw.mineminenomi.abilities.goro.ElThorAbility;
import xyz.pixelatedw.mineminenomi.abilities.goro.GoroLogiaAbility;
import xyz.pixelatedw.mineminenomi.abilities.goro.GoroTravelAbility;
import xyz.pixelatedw.mineminenomi.abilities.goro.KariAbility;
import xyz.pixelatedw.mineminenomi.abilities.goro.RaigoAbility;
import xyz.pixelatedw.mineminenomi.abilities.goro.SangoAbility;
import xyz.pixelatedw.mineminenomi.abilities.goro.SparkStepAbility;
import xyz.pixelatedw.mineminenomi.abilities.goro.VariAbility;
import xyz.pixelatedw.mineminenomi.abilities.goro.VoltAmaruAbility;
import xyz.pixelatedw.mineminenomi.abilities.goro.VoltAmaruFlightAbility;
import xyz.pixelatedw.mineminenomi.abilities.gura.GekishinAbility;
import xyz.pixelatedw.mineminenomi.abilities.gura.GuraImmunityAbility;
import xyz.pixelatedw.mineminenomi.abilities.gura.KabutowariAbility;
import xyz.pixelatedw.mineminenomi.abilities.gura.ShimaYurashiAbility;
import xyz.pixelatedw.mineminenomi.abilities.gura.ShingenNoIchigekiAbility;
import xyz.pixelatedw.mineminenomi.abilities.gura.TenchiMeidoAbility;
import xyz.pixelatedw.mineminenomi.abilities.hana.CampoDeFloresAbility;
import xyz.pixelatedw.mineminenomi.abilities.hana.CienFleurStompAbility;
import xyz.pixelatedw.mineminenomi.abilities.hana.CienFleurWingAbility;
import xyz.pixelatedw.mineminenomi.abilities.hana.DosFleurClutchAbility;
import xyz.pixelatedw.mineminenomi.abilities.hana.MilFleurAbility;
import xyz.pixelatedw.mineminenomi.abilities.hana.SeisFleurSlapAbility;
import xyz.pixelatedw.mineminenomi.abilities.hana.SeisFleurTwistAbility;
import xyz.pixelatedw.mineminenomi.abilities.hana.VeinteFleurCalendulaAbility;
import xyz.pixelatedw.mineminenomi.abilities.hie.FrostWalkerAbility;
import xyz.pixelatedw.mineminenomi.abilities.hie.HieImmunityAbility;
import xyz.pixelatedw.mineminenomi.abilities.hie.HieLogiaAbility;
import xyz.pixelatedw.mineminenomi.abilities.hie.HieTravelAbility;
import xyz.pixelatedw.mineminenomi.abilities.hie.IceAgeAbility;
import xyz.pixelatedw.mineminenomi.abilities.hie.IceBallAbility;
import xyz.pixelatedw.mineminenomi.abilities.hie.IceBlockAvalancheAbility;
import xyz.pixelatedw.mineminenomi.abilities.hie.IceBlockPartisanAbility;
import xyz.pixelatedw.mineminenomi.abilities.hie.IceBlockPheasantAbility;
import xyz.pixelatedw.mineminenomi.abilities.hie.IceSaberAbility;
import xyz.pixelatedw.mineminenomi.abilities.hie.IceTimeAbility;
import xyz.pixelatedw.mineminenomi.abilities.hiso.ForewarnAbility;
import xyz.pixelatedw.mineminenomi.abilities.hiso.LookoutAbility;
import xyz.pixelatedw.mineminenomi.abilities.hitodaibutsu.HitoDaibutsuPointAbility;
import xyz.pixelatedw.mineminenomi.abilities.hitodaibutsu.ImpactBlastAbility;
import xyz.pixelatedw.mineminenomi.abilities.horo.MiniHollowAbility;
import xyz.pixelatedw.mineminenomi.abilities.horo.NegativeHollowAbility;
import xyz.pixelatedw.mineminenomi.abilities.horo.TokuHollowAbility;
import xyz.pixelatedw.mineminenomi.abilities.horo.YutaiRidatsuAbility;
import xyz.pixelatedw.mineminenomi.abilities.horu.ChiyuHormoneAbility;
import xyz.pixelatedw.mineminenomi.abilities.horu.DeathWinkAbility;
import xyz.pixelatedw.mineminenomi.abilities.horu.GanmenSeichoHormoneAbility;
import xyz.pixelatedw.mineminenomi.abilities.horu.OnnaHormoneAbility;
import xyz.pixelatedw.mineminenomi.abilities.horu.TensionHormoneAbility;
import xyz.pixelatedw.mineminenomi.abilities.ito.BlackKnightAbility;
import xyz.pixelatedw.mineminenomi.abilities.ito.FullbrightAbility;
import xyz.pixelatedw.mineminenomi.abilities.ito.KumoNoSugakiAbility;
import xyz.pixelatedw.mineminenomi.abilities.ito.OverheatAbility;
import xyz.pixelatedw.mineminenomi.abilities.ito.ParasiteAbility;
import xyz.pixelatedw.mineminenomi.abilities.ito.SoraNoMichiAbility;
import xyz.pixelatedw.mineminenomi.abilities.ito.TamaitoAbility;
import xyz.pixelatedw.mineminenomi.abilities.ito.TorikagoAbility;
import xyz.pixelatedw.mineminenomi.abilities.jiki.AttractAbility;
import xyz.pixelatedw.mineminenomi.abilities.jiki.DamnedPunkAbility;
import xyz.pixelatedw.mineminenomi.abilities.jiki.GenocideRaidAbility;
import xyz.pixelatedw.mineminenomi.abilities.jiki.MagneticItemsAbility;
import xyz.pixelatedw.mineminenomi.abilities.jiki.PunkCornaDioAbility;
import xyz.pixelatedw.mineminenomi.abilities.jiki.PunkCrossAbility;
import xyz.pixelatedw.mineminenomi.abilities.jiki.PunkGibsonAbility;
import xyz.pixelatedw.mineminenomi.abilities.jiki.PunkPistolsAbility;
import xyz.pixelatedw.mineminenomi.abilities.jiki.RepelAbility;
import xyz.pixelatedw.mineminenomi.abilities.kachi.EvaporateAbility;
import xyz.pixelatedw.mineminenomi.abilities.kachi.HotBoilingSpecialAbility;
import xyz.pixelatedw.mineminenomi.abilities.kachi.VulcanizationAbility;
import xyz.pixelatedw.mineminenomi.abilities.kage.BlackBoxAbility;
import xyz.pixelatedw.mineminenomi.abilities.kage.BrickBatAbility;
import xyz.pixelatedw.mineminenomi.abilities.kage.DoppelmanAbility;
import xyz.pixelatedw.mineminenomi.abilities.kage.KageGiriAbility;
import xyz.pixelatedw.mineminenomi.abilities.kage.KageKakumeiAbility;
import xyz.pixelatedw.mineminenomi.abilities.kage.KagemushaAbility;
import xyz.pixelatedw.mineminenomi.abilities.kage.NightmareSoldiersAbility;
import xyz.pixelatedw.mineminenomi.abilities.kage.ShadowsAsgardAbility;
import xyz.pixelatedw.mineminenomi.abilities.kage.TsunoTokageAbility;
import xyz.pixelatedw.mineminenomi.abilities.kame.KameGuardPointAbility;
import xyz.pixelatedw.mineminenomi.abilities.kame.KameRideableAbility;
import xyz.pixelatedw.mineminenomi.abilities.kame.KameWalkPointAbility;
import xyz.pixelatedw.mineminenomi.abilities.karu.IngaZarashiAbility;
import xyz.pixelatedw.mineminenomi.abilities.karu.KarmaAbility;
import xyz.pixelatedw.mineminenomi.abilities.kilo.KiloPress10000Ability;
import xyz.pixelatedw.mineminenomi.abilities.kilo.KiloPress1Ability;
import xyz.pixelatedw.mineminenomi.abilities.kilo.KiloPunch5000Ability;
import xyz.pixelatedw.mineminenomi.abilities.kira.BrilliantPunkAbility;
import xyz.pixelatedw.mineminenomi.abilities.kira.CabochonKnuckleAbility;
import xyz.pixelatedw.mineminenomi.abilities.kira.DiamondBodyAbility;
import xyz.pixelatedw.mineminenomi.abilities.kobu.ShoureiAbility;
import xyz.pixelatedw.mineminenomi.abilities.kuku.GastronomorphAbility;
import xyz.pixelatedw.mineminenomi.abilities.kuku.GourmetSnipeAbility;
import xyz.pixelatedw.mineminenomi.abilities.kuku.GourmetamorphosisAbility;
import xyz.pixelatedw.mineminenomi.abilities.magu.BakuretsuKazanAbility;
import xyz.pixelatedw.mineminenomi.abilities.magu.DaiFunkaAbility;
import xyz.pixelatedw.mineminenomi.abilities.magu.LavaFlowAbility;
import xyz.pixelatedw.mineminenomi.abilities.magu.MagmaCoatingAbility;
import xyz.pixelatedw.mineminenomi.abilities.magu.MaguLogiaAbility;
import xyz.pixelatedw.mineminenomi.abilities.magu.MeigoAbility;
import xyz.pixelatedw.mineminenomi.abilities.magu.RyuseiKazanAbility;
import xyz.pixelatedw.mineminenomi.abilities.mera.DaiEnkaiAbility;
import xyz.pixelatedw.mineminenomi.abilities.mera.HeatDashAbility;
import xyz.pixelatedw.mineminenomi.abilities.mera.HibashiraAbility;
import xyz.pixelatedw.mineminenomi.abilities.mera.HidarumaAbility;
import xyz.pixelatedw.mineminenomi.abilities.mera.HiganAbility;
import xyz.pixelatedw.mineminenomi.abilities.mera.HikenAbility;
import xyz.pixelatedw.mineminenomi.abilities.mera.JujikaAbility;
import xyz.pixelatedw.mineminenomi.abilities.mera.KyokaenAbility;
import xyz.pixelatedw.mineminenomi.abilities.mera.MeraLogiaAbility;
import xyz.pixelatedw.mineminenomi.abilities.mero.MeroMeroMellowAbility;
import xyz.pixelatedw.mineminenomi.abilities.mero.PerfumeFemurAbility;
import xyz.pixelatedw.mineminenomi.abilities.mero.PistolKissAbility;
import xyz.pixelatedw.mineminenomi.abilities.mero.SlaveArrowAbility;
import xyz.pixelatedw.mineminenomi.abilities.mini.MiniMiniAbility;
import xyz.pixelatedw.mineminenomi.abilities.mini.PaperFloatAbility;
import xyz.pixelatedw.mineminenomi.abilities.mogu.MoguHeavyPointAbility;
import xyz.pixelatedw.mineminenomi.abilities.mogu.MoguraBananaAbility;
import xyz.pixelatedw.mineminenomi.abilities.mogu.MoguraTonpoAbility;
import xyz.pixelatedw.mineminenomi.abilities.moku.MokuFlyAbility;
import xyz.pixelatedw.mineminenomi.abilities.moku.MokuImmunityAbility;
import xyz.pixelatedw.mineminenomi.abilities.moku.MokuLogiaAbility;
import xyz.pixelatedw.mineminenomi.abilities.moku.SmokeLaunchAbility;
import xyz.pixelatedw.mineminenomi.abilities.moku.WhiteBlowAbility;
import xyz.pixelatedw.mineminenomi.abilities.moku.WhiteBlowRushAbility;
import xyz.pixelatedw.mineminenomi.abilities.moku.WhiteLauncherAbility;
import xyz.pixelatedw.mineminenomi.abilities.moku.WhiteOutAbility;
import xyz.pixelatedw.mineminenomi.abilities.moku.WhitePullAbility;
import xyz.pixelatedw.mineminenomi.abilities.moku.WhiteSnakeAbility;
import xyz.pixelatedw.mineminenomi.abilities.moku.WhiteStrikeAbility;
import xyz.pixelatedw.mineminenomi.abilities.nagi.SilentAbility;
import xyz.pixelatedw.mineminenomi.abilities.nekoleopard.ClawStrikeAbility;
import xyz.pixelatedw.mineminenomi.abilities.nekoleopard.FerociousLeapAbility;
import xyz.pixelatedw.mineminenomi.abilities.nekoleopard.LeopardHeavyPointAbility;
import xyz.pixelatedw.mineminenomi.abilities.nekoleopard.LeopardWalkPointAbility;
import xyz.pixelatedw.mineminenomi.abilities.nekoleopard.NekoNightVisionAbility;
import xyz.pixelatedw.mineminenomi.abilities.netsu.HeatDenasshiAbility;
import xyz.pixelatedw.mineminenomi.abilities.netsu.NekkaiGyoraiAbility;
import xyz.pixelatedw.mineminenomi.abilities.netsu.NekkaiJigokuAbility;
import xyz.pixelatedw.mineminenomi.abilities.netsu.NetsuEnhancementAbility;
import xyz.pixelatedw.mineminenomi.abilities.nikyu.HanpatsuAbility;
import xyz.pixelatedw.mineminenomi.abilities.nikyu.NikyuPushAbility;
import xyz.pixelatedw.mineminenomi.abilities.nikyu.PadHoAbility;
import xyz.pixelatedw.mineminenomi.abilities.nikyu.PainRepelAbility;
import xyz.pixelatedw.mineminenomi.abilities.nikyu.PuniAbility;
import xyz.pixelatedw.mineminenomi.abilities.nikyu.TsuppariPadHoAbility;
import xyz.pixelatedw.mineminenomi.abilities.nikyu.UrsusShockAbility;
import xyz.pixelatedw.mineminenomi.abilities.noro.KyubiRushAbility;
import xyz.pixelatedw.mineminenomi.abilities.noro.NoroNoroBeamAbility;
import xyz.pixelatedw.mineminenomi.abilities.noro.NoroNoroBeamSwordAbility;
import xyz.pixelatedw.mineminenomi.abilities.ope.CounterShockAbility;
import xyz.pixelatedw.mineminenomi.abilities.ope.GammaKnifeAbility;
import xyz.pixelatedw.mineminenomi.abilities.ope.InjectionShotAbility;
import xyz.pixelatedw.mineminenomi.abilities.ope.MesAbility;
import xyz.pixelatedw.mineminenomi.abilities.ope.RoomAbility;
import xyz.pixelatedw.mineminenomi.abilities.ope.ShamblesAbility;
import xyz.pixelatedw.mineminenomi.abilities.ope.TaktAbility;
import xyz.pixelatedw.mineminenomi.abilities.ori.AwaseBaoriAbility;
import xyz.pixelatedw.mineminenomi.abilities.ori.BindAbility;
import xyz.pixelatedw.mineminenomi.abilities.ori.GreatCageAbility;
import xyz.pixelatedw.mineminenomi.abilities.oto.BonAbility;
import xyz.pixelatedw.mineminenomi.abilities.oto.DonAbility;
import xyz.pixelatedw.mineminenomi.abilities.oto.ShanAbility;
import xyz.pixelatedw.mineminenomi.abilities.pero.CandyArmorAbility;
import xyz.pixelatedw.mineminenomi.abilities.pero.CandyEscalatorAbility;
import xyz.pixelatedw.mineminenomi.abilities.pero.CandyManAbility;
import xyz.pixelatedw.mineminenomi.abilities.pero.CandyWallAbility;
import xyz.pixelatedw.mineminenomi.abilities.pero.CandyWaveAbility;
import xyz.pixelatedw.mineminenomi.abilities.pero.PeroImmunityAbility;
import xyz.pixelatedw.mineminenomi.abilities.pika.AmaNoMurakumoAbility;
import xyz.pixelatedw.mineminenomi.abilities.pika.AmaterasuAbility;
import xyz.pixelatedw.mineminenomi.abilities.pika.FlashAbility;
import xyz.pixelatedw.mineminenomi.abilities.pika.LightAccelerationAbility;
import xyz.pixelatedw.mineminenomi.abilities.pika.PikaLogiaAbility;
import xyz.pixelatedw.mineminenomi.abilities.pika.PikaTravelAbility;
import xyz.pixelatedw.mineminenomi.abilities.pika.YasakaniNoMagatamaAbility;
import xyz.pixelatedw.mineminenomi.abilities.pika.YataNoKagamiAbility;
import xyz.pixelatedw.mineminenomi.abilities.ryuallosaurus.AllosaurusHeavyPointAbility;
import xyz.pixelatedw.mineminenomi.abilities.ryuallosaurus.AllosaurusRideableAbility;
import xyz.pixelatedw.mineminenomi.abilities.ryuallosaurus.AllosaurusWalkPointAbility;
import xyz.pixelatedw.mineminenomi.abilities.ryuallosaurus.AncientBiteAbility;
import xyz.pixelatedw.mineminenomi.abilities.ryuallosaurus.AncientTailSpinAbility;
import xyz.pixelatedw.mineminenomi.abilities.ryubrachiosaurus.BrachioBomberAbility;
import xyz.pixelatedw.mineminenomi.abilities.ryubrachiosaurus.BrachioGrabAbility;
import xyz.pixelatedw.mineminenomi.abilities.ryubrachiosaurus.BrachiosaurusGuardPointAbility;
import xyz.pixelatedw.mineminenomi.abilities.ryubrachiosaurus.BrachiosaurusHeavyPointAbility;
import xyz.pixelatedw.mineminenomi.abilities.ryubrachiosaurus.BrachiosaurusRideableAbility;
import xyz.pixelatedw.mineminenomi.abilities.ryupteranodon.BarizodonAbility;
import xyz.pixelatedw.mineminenomi.abilities.ryupteranodon.BeakGrabAbility;
import xyz.pixelatedw.mineminenomi.abilities.ryupteranodon.PteranodonAssaultPointAbility;
import xyz.pixelatedw.mineminenomi.abilities.ryupteranodon.PteranodonFlightAbility;
import xyz.pixelatedw.mineminenomi.abilities.ryupteranodon.PteranodonFlyPointAbility;
import xyz.pixelatedw.mineminenomi.abilities.ryupteranodon.TankyudonAbility;
import xyz.pixelatedw.mineminenomi.abilities.ryupteranodon.TempuraudonAbility;
import xyz.pixelatedw.mineminenomi.abilities.sabi.RustBreakAbility;
import xyz.pixelatedw.mineminenomi.abilities.sabi.RustSkinAbility;
import xyz.pixelatedw.mineminenomi.abilities.sabi.RustTouchAbility;
import xyz.pixelatedw.mineminenomi.abilities.sai.HornDashAbility;
import xyz.pixelatedw.mineminenomi.abilities.sai.RhinoSmashAbility;
import xyz.pixelatedw.mineminenomi.abilities.sai.SaiHeavyPointAbility;
import xyz.pixelatedw.mineminenomi.abilities.sai.SaiRideableAbility;
import xyz.pixelatedw.mineminenomi.abilities.sai.SaiWalkPointAbility;
import xyz.pixelatedw.mineminenomi.abilities.saraaxolotl.AxolotlHealAbility;
import xyz.pixelatedw.mineminenomi.abilities.saraaxolotl.AxolotlHeavyPointAbility;
import xyz.pixelatedw.mineminenomi.abilities.saraaxolotl.AxolotlWalkPointAbility;
import xyz.pixelatedw.mineminenomi.abilities.saraaxolotl.HeartRegenAbility;
import xyz.pixelatedw.mineminenomi.abilities.saraaxolotl.PlayDeadAbility;
import xyz.pixelatedw.mineminenomi.abilities.saraaxolotl.PoisonSpitAbility;
import xyz.pixelatedw.mineminenomi.abilities.sube.SubeSubeDeflectAbility;
import xyz.pixelatedw.mineminenomi.abilities.sube.SubeSubeSpurAbility;
import xyz.pixelatedw.mineminenomi.abilities.sui.FreeSwimmingAbility;
import xyz.pixelatedw.mineminenomi.abilities.sui.NekomimiPunchAbility;
import xyz.pixelatedw.mineminenomi.abilities.sui.NyanNyanSuplexAbility;
import xyz.pixelatedw.mineminenomi.abilities.sui.NyanNyanSuplexBabyBusterAbility;
import xyz.pixelatedw.mineminenomi.abilities.suke.ShishaNoTeAbility;
import xyz.pixelatedw.mineminenomi.abilities.suke.SkattingAbility;
import xyz.pixelatedw.mineminenomi.abilities.suke.SukePunchAbility;
import xyz.pixelatedw.mineminenomi.abilities.suna.BarjanAbility;
import xyz.pixelatedw.mineminenomi.abilities.suna.DesertEncierroAbility;
import xyz.pixelatedw.mineminenomi.abilities.suna.DesertGirasoleAbility;
import xyz.pixelatedw.mineminenomi.abilities.suna.DesertGrandeEspadaAbility;
import xyz.pixelatedw.mineminenomi.abilities.suna.DesertSpadaAbility;
import xyz.pixelatedw.mineminenomi.abilities.suna.GrandeSablesAbility;
import xyz.pixelatedw.mineminenomi.abilities.suna.GroundDeathAbility;
import xyz.pixelatedw.mineminenomi.abilities.suna.SablesAbility;
import xyz.pixelatedw.mineminenomi.abilities.suna.SablesPesadoAbility;
import xyz.pixelatedw.mineminenomi.abilities.suna.SunaFlyAbility;
import xyz.pixelatedw.mineminenomi.abilities.suna.SunaLogiaAbility;
import xyz.pixelatedw.mineminenomi.abilities.suna.SunaTravelAbility;
import xyz.pixelatedw.mineminenomi.abilities.supa.AtomicSpurtAbility;
import xyz.pixelatedw.mineminenomi.abilities.supa.SparClawAbility;
import xyz.pixelatedw.mineminenomi.abilities.supa.SparklingDaisyAbility;
import xyz.pixelatedw.mineminenomi.abilities.supa.SpiderAbility;
import xyz.pixelatedw.mineminenomi.abilities.supa.SpiralHollowAbility;
import xyz.pixelatedw.mineminenomi.abilities.toriphoenix.BlueBirdAbility;
import xyz.pixelatedw.mineminenomi.abilities.toriphoenix.FlamesOfRegenerationAbility;
import xyz.pixelatedw.mineminenomi.abilities.toriphoenix.FujiazamiAbility;
import xyz.pixelatedw.mineminenomi.abilities.toriphoenix.PhoenixAssaultPointAbility;
import xyz.pixelatedw.mineminenomi.abilities.toriphoenix.PhoenixFlightAbility;
import xyz.pixelatedw.mineminenomi.abilities.toriphoenix.PhoenixFlyPointAbility;
import xyz.pixelatedw.mineminenomi.abilities.toriphoenix.PhoenixGoenAbility;
import xyz.pixelatedw.mineminenomi.abilities.toriphoenix.SaiseiNoHonoAbility;
import xyz.pixelatedw.mineminenomi.abilities.toriphoenix.TenseiNoSoenAbility;
import xyz.pixelatedw.mineminenomi.abilities.ushibison.BisonHeavyPointAbility;
import xyz.pixelatedw.mineminenomi.abilities.ushibison.BisonRideableAbility;
import xyz.pixelatedw.mineminenomi.abilities.ushibison.BisonSmashAbility;
import xyz.pixelatedw.mineminenomi.abilities.ushibison.BisonWalkPointAbility;
import xyz.pixelatedw.mineminenomi.abilities.ushibison.FiddleBanffAbility;
import xyz.pixelatedw.mineminenomi.abilities.ushibison.KokuteiCrossAbility;
import xyz.pixelatedw.mineminenomi.abilities.ushigiraffe.BiganAbility;
import xyz.pixelatedw.mineminenomi.abilities.ushigiraffe.GiraffeHeavyPointAbility;
import xyz.pixelatedw.mineminenomi.abilities.ushigiraffe.GiraffeRideableAbility;
import xyz.pixelatedw.mineminenomi.abilities.ushigiraffe.GiraffeWalkPointAbility;
import xyz.pixelatedw.mineminenomi.abilities.wara.LifeMinusAbility;
import xyz.pixelatedw.mineminenomi.abilities.wara.StrawManAbility;
import xyz.pixelatedw.mineminenomi.abilities.wara.WarabideSwordAbility;
import xyz.pixelatedw.mineminenomi.abilities.yami.AbsorbedBlocksAbility;
import xyz.pixelatedw.mineminenomi.abilities.yami.BlackHoleAbility;
import xyz.pixelatedw.mineminenomi.abilities.yami.BlackRoadAbility;
import xyz.pixelatedw.mineminenomi.abilities.yami.BlackWorldAbility;
import xyz.pixelatedw.mineminenomi.abilities.yami.DarkMatterAbility;
import xyz.pixelatedw.mineminenomi.abilities.yami.KurouzuAbility;
import xyz.pixelatedw.mineminenomi.abilities.yami.LiberationAbility;
import xyz.pixelatedw.mineminenomi.abilities.yomi.KasuriutaFubukiGiriAbility;
import xyz.pixelatedw.mineminenomi.abilities.yomi.SoulParadeAbility;
import xyz.pixelatedw.mineminenomi.abilities.yomi.YomiImmunityAbility;
import xyz.pixelatedw.mineminenomi.abilities.yomi.YomiNoReikiAbility;
import xyz.pixelatedw.mineminenomi.abilities.yuki.FubukiAbility;
import xyz.pixelatedw.mineminenomi.abilities.yuki.KamakuraAbility;
import xyz.pixelatedw.mineminenomi.abilities.yuki.KamakuraJussoshiAbility;
import xyz.pixelatedw.mineminenomi.abilities.yuki.TabiraYukiAbility;
import xyz.pixelatedw.mineminenomi.abilities.yuki.YukiGakiAbility;
import xyz.pixelatedw.mineminenomi.abilities.yuki.YukiImmunityAbility;
import xyz.pixelatedw.mineminenomi.abilities.yuki.YukiLogiaAbility;
import xyz.pixelatedw.mineminenomi.abilities.yuki.YukiRabiAbility;
import xyz.pixelatedw.mineminenomi.abilities.yuki.YukiTravelAbility;
import xyz.pixelatedw.mineminenomi.abilities.zou.GreatStompAbility;
import xyz.pixelatedw.mineminenomi.abilities.zou.IvoryDartAbility;
import xyz.pixelatedw.mineminenomi.abilities.zou.IvoryStompAbility;
import xyz.pixelatedw.mineminenomi.abilities.zou.TrunkShotAbility;
import xyz.pixelatedw.mineminenomi.abilities.zou.ZouGuardPointAbility;
import xyz.pixelatedw.mineminenomi.abilities.zou.ZouHeavyPointAbility;
import xyz.pixelatedw.mineminenomi.abilities.zou.ZouRideableAbility;
import xyz.pixelatedw.mineminenomi.abilities.zoumammoth.AncientStompAbility;
import xyz.pixelatedw.mineminenomi.abilities.zoumammoth.AncientSweepAbility;
import xyz.pixelatedw.mineminenomi.abilities.zoumammoth.AncientTrunkShotAbility;
import xyz.pixelatedw.mineminenomi.abilities.zoumammoth.MammothGuardPointAbility;
import xyz.pixelatedw.mineminenomi.abilities.zoumammoth.MammothHeavyPointAbility;
import xyz.pixelatedw.mineminenomi.abilities.zoumammoth.MammothRideableAbility;
import xyz.pixelatedw.mineminenomi.abilities.zoumammoth.MammothTrampleAbility;
import xyz.pixelatedw.mineminenomi.abilities.zushi.AbareHimatsuriAbility;
import xyz.pixelatedw.mineminenomi.abilities.zushi.GraviPullAbility;
import xyz.pixelatedw.mineminenomi.abilities.zushi.GraviZoneAbility;
import xyz.pixelatedw.mineminenomi.abilities.zushi.JigokuTabiAbility;
import xyz.pixelatedw.mineminenomi.abilities.zushi.MokoAbility;
import xyz.pixelatedw.mineminenomi.abilities.zushi.SagariNoRyuseiAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.enums.FruitType;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;

public class ModFruits {
	// Fields
	public static final Supplier<AkumaNoMiItem> RYU_RYU_NO_MI_ALLOSAURUS;
	public static final Supplier<AkumaNoMiItem> SAI_SAI_NO_MI;
	public static final Supplier<AkumaNoMiItem> SARA_SARA_NO_MI_AXOLOTL;
	public static final Supplier<AkumaNoMiItem> RYU_RYU_NO_MI_BRACHIOSAURUS;
	public static final Supplier<AkumaNoMiItem> RYU_RYU_NO_MI_PTERANODON;
	public static final Supplier<AkumaNoMiItem> KAME_KAME_NO_MI;
	public static final Supplier<AkumaNoMiItem> NEKO_NEKO_NO_MI_LEOPARD;
	public static final Supplier<AkumaNoMiItem> ZOU_ZOU_NO_MI_MAMMOTH;
	public static final Supplier<AkumaNoMiItem> HITO_HITO_NO_MI;
	public static final Supplier<AkumaNoMiItem> USHI_USHI_NO_MI_GIRAFFE;
	public static final Supplier<AkumaNoMiItem> MOGU_MOGU_NO_MI;
	public static final Supplier<AkumaNoMiItem> USHI_USHI_NO_MI_BISON;
	public static final Supplier<AkumaNoMiItem> ZOU_ZOU_NO_MI;
	public static final Supplier<AkumaNoMiItem> HITO_HITO_NO_MI_DAIBUTSU;
	public static final Supplier<AkumaNoMiItem> TORI_TORI_NO_MI_PHOENIX;
	public static final Supplier<AkumaNoMiItem> MOCHI_MOCHI_NO_MI;
	public static final Supplier<AkumaNoMiItem> DEKA_DEKA_NO_MI;
	public static final Supplier<AkumaNoMiItem> KARU_KARU_NO_MI;
	public static final Supplier<AkumaNoMiItem> JIKI_JIKI_NO_MI;
	public static final Supplier<AkumaNoMiItem> OTO_OTO_NO_MI;
	public static final Supplier<AkumaNoMiItem> BARA_BARA_NO_MI;
	public static final Supplier<AkumaNoMiItem> SUBE_SUBE_NO_MI;
	public static final Supplier<AkumaNoMiItem> KOBU_KOBU_NO_MI;
	public static final Supplier<AkumaNoMiItem> HANA_HANA_NO_MI;
	public static final Supplier<AkumaNoMiItem> MINI_MINI_NO_MI;
	public static final Supplier<AkumaNoMiItem> HISO_HISO_NO_MI;
	public static final Supplier<AkumaNoMiItem> KUKU_KUKU_NO_MI;
	public static final Supplier<AkumaNoMiItem> KIRA_KIRA_NO_MI;
	public static final Supplier<AkumaNoMiItem> BETA_BETA_NO_MI;
	public static final Supplier<AkumaNoMiItem> NAGI_NAGI_NO_MI;
	public static final Supplier<AkumaNoMiItem> PERO_PERO_NO_MI;
	public static final Supplier<AkumaNoMiItem> NETSU_NETSU_NO_MI;
	public static final Supplier<AkumaNoMiItem> SUI_SUI_NO_MI;
	public static final Supplier<AkumaNoMiItem> AWA_AWA_NO_MI;
	public static final Supplier<AkumaNoMiItem> WARA_WARA_NO_MI;
	public static final Supplier<AkumaNoMiItem> KACHI_KACHI_NO_MI;
	public static final Supplier<AkumaNoMiItem> DOA_DOA_NO_MI;
	public static final Supplier<AkumaNoMiItem> CHIYU_CHIYU_NO_MI;
	public static final Supplier<AkumaNoMiItem> SABI_SABI_NO_MI;
	public static final Supplier<AkumaNoMiItem> YOMI_YOMI_NO_MI;
	public static final Supplier<AkumaNoMiItem> BAKU_BAKU_NO_MI;
	public static final Supplier<AkumaNoMiItem> HORU_HORU_NO_MI;
	public static final Supplier<AkumaNoMiItem> KILO_KILO_NO_MI;
	public static final Supplier<AkumaNoMiItem> GOE_GOE_NO_MI;
	public static final Supplier<AkumaNoMiItem> MERO_MERO_NO_MI;
	public static final Supplier<AkumaNoMiItem> ORI_ORI_NO_MI;
	public static final Supplier<AkumaNoMiItem> SUPA_SUPA_NO_MI;
	public static final Supplier<AkumaNoMiItem> HORO_HORO_NO_MI;
	public static final Supplier<AkumaNoMiItem> ITO_ITO_NO_MI;
	public static final Supplier<AkumaNoMiItem> ZUSHI_ZUSHI_NO_MI;
	public static final Supplier<AkumaNoMiItem> BARI_BARI_NO_MI;
	public static final Supplier<AkumaNoMiItem> DOKU_DOKU_NO_MI;
	public static final Supplier<AkumaNoMiItem> DORU_DORU_NO_MI;
	public static final Supplier<AkumaNoMiItem> KAGE_KAGE_NO_MI;
	public static final Supplier<AkumaNoMiItem> GURA_GURA_NO_MI;
	public static final Supplier<AkumaNoMiItem> BOMU_BOMU_NO_MI;
	public static final Supplier<AkumaNoMiItem> NIKYU_NIKYU_NO_MI;
	public static final Supplier<AkumaNoMiItem> NORO_NORO_NO_MI;
	public static final Supplier<AkumaNoMiItem> OPE_OPE_NO_MI;
	public static final Supplier<AkumaNoMiItem> SUKE_SUKE_NO_MI;
	public static final Supplier<AkumaNoMiItem> GOMU_GOMU_NO_MI;
	public static final Supplier<AkumaNoMiItem> BANE_BANE_NO_MI;
	public static final Supplier<AkumaNoMiItem> YUKI_YUKI_NO_MI;
	public static final Supplier<AkumaNoMiItem> YAMI_YAMI_NO_MI;
	public static final Supplier<AkumaNoMiItem> GASU_GASU_NO_MI;
	public static final Supplier<AkumaNoMiItem> MAGU_MAGU_NO_MI;
	public static final Supplier<AkumaNoMiItem> SUNA_SUNA_NO_MI;
	public static final Supplier<AkumaNoMiItem> GORO_GORO_NO_MI;
	public static final Supplier<AkumaNoMiItem> MOKU_MOKU_NO_MI;
	public static final Supplier<AkumaNoMiItem> PIKA_PIKA_NO_MI;
	public static final Supplier<AkumaNoMiItem> HIE_HIE_NO_MI;
	public static final Supplier<AkumaNoMiItem> MERA_MERA_NO_MI;

	private static <T extends AkumaNoMiItem> Supplier<T> registerMochi(String var0, Supplier<T> var1) {
		if (WyHelper.isAprilFirst()){
			return ModRegistry.registerFruitItem(var0,var1);
		}else{
			return (Supplier<T>) (Supplier) (null);
		}
	}
	public static void init() {
	}
	static  {
		Supplier<AkumaNoMiItem> var0 = () -> {
			return new AkumaNoMiItem(2,FruitType.ANCIENT_ZOAN,new Supplier[]{AllosaurusWalkPointAbility.INSTANCE,AllosaurusHeavyPointAbility.INSTANCE,AncientBiteAbility.INSTANCE,AncientTailSpinAbility.INSTANCE,AllosaurusRideableAbility.INSTANCE});
		};
		RYU_RYU_NO_MI_ALLOSAURUS = ModRegistry.registerFruitItem("Ryu Ryu no Mi, Model: Allosaurus",var0);
		Supplier<AkumaNoMiItem> var1 = () -> {
			return new AkumaNoMiItem(2,FruitType.ZOAN,new Supplier[]{SaiWalkPointAbility.INSTANCE,SaiHeavyPointAbility.INSTANCE,RhinoSmashAbility.INSTANCE,HornDashAbility.INSTANCE,SaiRideableAbility.INSTANCE});
		};
		SAI_SAI_NO_MI = ModRegistry.registerFruitItem("Sai Sai no Mi",var1);
		Supplier<AkumaNoMiItem> var2 = () -> {
			return new AkumaNoMiItem(2,FruitType.ZOAN,new Supplier[]{AxolotlHeavyPointAbility.INSTANCE,AxolotlWalkPointAbility.INSTANCE,PoisonSpitAbility.INSTANCE,AxolotlHealAbility.INSTANCE,PlayDeadAbility.INSTANCE,HeartRegenAbility.INSTANCE});
		};
		SARA_SARA_NO_MI_AXOLOTL = ModRegistry.registerFruitItem("Sara Sara no Mi, Model: Axolotl",var2);
		Supplier<AkumaNoMiItem> var3 = () -> {
			return new AkumaNoMiItem(2,FruitType.ANCIENT_ZOAN,new Supplier[]{BrachiosaurusGuardPointAbility.INSTANCE,BrachiosaurusHeavyPointAbility.INSTANCE,BrachioBomberAbility.INSTANCE,BrachioGrabAbility.INSTANCE,BrachiosaurusRideableAbility.INSTANCE});
		};
		RYU_RYU_NO_MI_BRACHIOSAURUS = ModRegistry.registerFruitItem("Ryu Ryu no Mi, Model: Brachiosaurus",var3);
		Supplier<AkumaNoMiItem> var4 = () -> {
			return new AkumaNoMiItem(2,FruitType.ANCIENT_ZOAN,new Supplier[]{PteranodonFlyPointAbility.INSTANCE,PteranodonAssaultPointAbility.INSTANCE,TempuraudonAbility.INSTANCE,TankyudonAbility.INSTANCE,BeakGrabAbility.INSTANCE,BarizodonAbility.INSTANCE,PteranodonFlightAbility.INSTANCE});
		};
		RYU_RYU_NO_MI_PTERANODON = ModRegistry.registerFruitItem("Ryu Ryu no Mi, Model: Pteranodon",var4);
		Supplier<AkumaNoMiItem> var5 = () -> {
			return new AkumaNoMiItem(1,FruitType.ZOAN,new Supplier[]{KameGuardPointAbility.INSTANCE,KameWalkPointAbility.INSTANCE,KameRideableAbility.INSTANCE});
		};
		KAME_KAME_NO_MI = ModRegistry.registerFruitItem("Kame Kame no Mi",var5);
		Supplier<AkumaNoMiItem> var6 = () -> {
			return new AkumaNoMiItem(2,FruitType.ZOAN,new Supplier[]{LeopardWalkPointAbility.INSTANCE,LeopardHeavyPointAbility.INSTANCE,FerociousLeapAbility.INSTANCE,ClawStrikeAbility.INSTANCE,NekoNightVisionAbility.INSTANCE});
		};
		NEKO_NEKO_NO_MI_LEOPARD = ModRegistry.registerFruitItem("Neko Neko no Mi, Model: Leopard",var6);
		Supplier<AkumaNoMiItem> var7 = () -> {
			return new AkumaNoMiItem(2,FruitType.ANCIENT_ZOAN,new Supplier[]{MammothGuardPointAbility.INSTANCE,MammothHeavyPointAbility.INSTANCE,MammothTrampleAbility.INSTANCE,AncientStompAbility.INSTANCE,AncientSweepAbility.INSTANCE,AncientTrunkShotAbility.INSTANCE,MammothRideableAbility.INSTANCE});
		};
		ZOU_ZOU_NO_MI_MAMMOTH = ModRegistry.registerFruitItem("Zou Zou no Mi, Model: Mammoth",var7);
		Supplier<AkumaNoMiItem> var8 = () -> {
			return new AkumaNoMiItem(1,FruitType.ZOAN,new Supplier[0]);
		};
		HITO_HITO_NO_MI = ModRegistry.registerFruitItem("Hito Hito no Mi",var8);
		Supplier<AkumaNoMiItem> var9 = () -> {
			return new AkumaNoMiItem(1,FruitType.ZOAN,new Supplier[]{GiraffeHeavyPointAbility.INSTANCE,GiraffeWalkPointAbility.INSTANCE,BiganAbility.INSTANCE,GiraffeRideableAbility.INSTANCE});
		};
		USHI_USHI_NO_MI_GIRAFFE = ModRegistry.registerFruitItem("Ushi Ushi no Mi, Model: Giraffe",var9);
		Supplier<AkumaNoMiItem> var10 = () -> {
			return new AkumaNoMiItem(1,FruitType.ZOAN,new Supplier[]{MoguHeavyPointAbility.INSTANCE,MoguraBananaAbility.INSTANCE,MoguraTonpoAbility.INSTANCE});
		};
		MOGU_MOGU_NO_MI = ModRegistry.registerFruitItem("Mogu Mogu no Mi",var10);
		Supplier<AkumaNoMiItem> var11 = () -> {
			return new AkumaNoMiItem(2,FruitType.ZOAN,new Supplier[]{BisonHeavyPointAbility.INSTANCE,BisonWalkPointAbility.INSTANCE,FiddleBanffAbility.INSTANCE,KokuteiCrossAbility.INSTANCE,BisonSmashAbility.INSTANCE,BisonRideableAbility.INSTANCE});
		};
		USHI_USHI_NO_MI_BISON = ModRegistry.registerFruitItem("Ushi Ushi no Mi, Model: Bison",var11);
		Supplier<AkumaNoMiItem> var12 = () -> {
			return new AkumaNoMiItem(2,FruitType.ZOAN,new Supplier[]{ZouGuardPointAbility.INSTANCE,ZouHeavyPointAbility.INSTANCE,TrunkShotAbility.INSTANCE,GreatStompAbility.INSTANCE,IvoryDartAbility.INSTANCE,IvoryStompAbility.INSTANCE,ZouRideableAbility.INSTANCE});
		};
		ZOU_ZOU_NO_MI = ModRegistry.registerFruitItem("Zou Zou no Mi",var12);
		Supplier<AkumaNoMiItem> var13 = () -> {
			return new AkumaNoMiItem(3,FruitType.MYTHICAL_ZOAN,new Supplier[]{HitoDaibutsuPointAbility.INSTANCE,ImpactBlastAbility.INSTANCE});
		};
		HITO_HITO_NO_MI_DAIBUTSU = ModRegistry.registerFruitItem("Hito Hito no Mi, Model: Daibutsu",var13);
		Supplier<AkumaNoMiItem> var14 = () -> {
			return new AkumaNoMiItem(3,FruitType.MYTHICAL_ZOAN,new Supplier[]{PhoenixAssaultPointAbility.INSTANCE,PhoenixFlyPointAbility.INSTANCE,PhoenixGoenAbility.INSTANCE,SaiseiNoHonoAbility.INSTANCE,FujiazamiAbility.INSTANCE,TenseiNoSoenAbility.INSTANCE,BlueBirdAbility.INSTANCE,FlamesOfRegenerationAbility.INSTANCE,PhoenixFlightAbility.INSTANCE});
		};
		TORI_TORI_NO_MI_PHOENIX = ModRegistry.registerFruitItem("Tori Tori no Mi, Model: Phoenix",var14);
		Supplier<AkumaNoMiItem> var15 = () -> {
			return new AkumaNoMiItem(3,FruitType.PARAMECIA,new Supplier[0]);
		};
		MOCHI_MOCHI_NO_MI = (Supplier) (registerMochi("Mochi Mochi no Mi",var15));
		Supplier<AkumaNoMiItem> var16 = () -> {
			return new AkumaNoMiItem(1,FruitType.PARAMECIA,new Supplier[]{DekaDekaAbility.INSTANCE,DekaTrampleAbility.INSTANCE});
		};
		DEKA_DEKA_NO_MI = ModRegistry.registerFruitItem("Deka Deka no Mi",var16);
		Supplier<AkumaNoMiItem> var17 = () -> {
			return new AkumaNoMiItem(2,FruitType.PARAMECIA,new Supplier[]{IngaZarashiAbility.INSTANCE,KarmaAbility.INSTANCE});
		};
		KARU_KARU_NO_MI = ModRegistry.registerFruitItem("Karu Karu no Mi",var17);
		Supplier<AkumaNoMiItem> var18 = () -> {
			return new AkumaNoMiItem(3,FruitType.PARAMECIA,new Supplier[]{AttractAbility.INSTANCE,RepelAbility.INSTANCE,PunkGibsonAbility.INSTANCE,PunkPistolsAbility.INSTANCE,PunkCornaDioAbility.INSTANCE,DamnedPunkAbility.INSTANCE,MagneticItemsAbility.INSTANCE,GenocideRaidAbility.INSTANCE,PunkCrossAbility.INSTANCE});
		};
		JIKI_JIKI_NO_MI = ModRegistry.registerFruitItem("Jiki Jiki no Mi",var18);
		Supplier<AkumaNoMiItem> var19 = () -> {
			return new AkumaNoMiItem(2,FruitType.PARAMECIA,new Supplier[]{BonAbility.INSTANCE,DonAbility.INSTANCE,ShanAbility.INSTANCE});
		};
		OTO_OTO_NO_MI = ModRegistry.registerFruitItem("Oto Oto no Mi",var19);
		Supplier<AkumaNoMiItem> var20 = () -> {
			return new AkumaNoMiItem(2,FruitType.PARAMECIA,new Supplier[]{BaraBaraHoAbility.INSTANCE,BaraBaraFestivalAbility.INSTANCE,KuchuKirimomiDaiCircusAbility.INSTANCE,BaraBaraCarAbility.INSTANCE,BaraSplitAbility.INSTANCE,SlashDamageImmunityAbility.INSTANCE});
		};
		BARA_BARA_NO_MI = ModRegistry.registerFruitItem("Bara Bara No Mi",var20);
		Supplier<AkumaNoMiItem> var21 = () -> {
			return new AkumaNoMiItem(1,FruitType.PARAMECIA,new Supplier[]{SubeSubeSpurAbility.INSTANCE,SubeSubeDeflectAbility.INSTANCE,HandcuffsImmunityAbility.INSTANCE});
		};
		SUBE_SUBE_NO_MI = ModRegistry.registerFruitItem("Sube Sube No Mi",var21);
		Supplier<AkumaNoMiItem> var22 = () -> {
			return new AkumaNoMiItem(1,FruitType.PARAMECIA,new Supplier[]{ShoureiAbility.INSTANCE,NegativeImmunityAbility.INSTANCE});
		};
		KOBU_KOBU_NO_MI = ModRegistry.registerFruitItem("Kobu Kobu No Mi",var22);
		Supplier<AkumaNoMiItem> var23 = () -> {
			return new AkumaNoMiItem(2,FruitType.PARAMECIA,new Supplier[]{DosFleurClutchAbility.INSTANCE,SeisFleurSlapAbility.INSTANCE,SeisFleurTwistAbility.INSTANCE,VeinteFleurCalendulaAbility.INSTANCE,CienFleurWingAbility.INSTANCE,CienFleurStompAbility.INSTANCE,CampoDeFloresAbility.INSTANCE,MilFleurAbility.INSTANCE});
		};
		HANA_HANA_NO_MI = ModRegistry.registerFruitItem("Hana Hana No Mi",var23);
		Supplier<AkumaNoMiItem> var24 = () -> {
			return new AkumaNoMiItem(1,FruitType.PARAMECIA,new Supplier[]{MiniMiniAbility.INSTANCE,PaperFloatAbility.INSTANCE});
		};
		MINI_MINI_NO_MI = ModRegistry.registerFruitItem("Mini Mini No Mi",var24);
		Supplier<AkumaNoMiItem> var25 = () -> {
			return new AkumaNoMiItem(1,FruitType.PARAMECIA,new Supplier[]{ForewarnAbility.INSTANCE,LookoutAbility.INSTANCE});
		};
		HISO_HISO_NO_MI = ModRegistry.registerFruitItem("Hiso Hiso No Mi",var25);
		Supplier<AkumaNoMiItem> var26 = () -> {
			return new AkumaNoMiItem(1,FruitType.PARAMECIA,new Supplier[]{GourmetamorphosisAbility.INSTANCE,GastronomorphAbility.INSTANCE,GourmetSnipeAbility.INSTANCE});
		};
		KUKU_KUKU_NO_MI = ModRegistry.registerFruitItem("Kuku Kuku no Mi",var26);
		Supplier<AkumaNoMiItem> var27 = () -> {
			return new AkumaNoMiItem(2,FruitType.PARAMECIA,new Supplier[]{DiamondBodyAbility.INSTANCE,BrilliantPunkAbility.INSTANCE,CabochonKnuckleAbility.INSTANCE});
		};
		KIRA_KIRA_NO_MI = ModRegistry.registerFruitItem("Kira Kira no Mi",var27);
		Supplier<AkumaNoMiItem> var28 = () -> {
			return new AkumaNoMiItem(2,FruitType.PARAMECIA,new Supplier[]{BetaCoatingAbility.INSTANCE,BetaLauncherAbility.INSTANCE,HanamizuShinkenAbility.INSTANCE,BetaBetaChainAbility.INSTANCE,BetaImmunityAbility.INSTANCE});
		};
		BETA_BETA_NO_MI = ModRegistry.registerFruitItem("Beta Beta no Mi",var28);
		Supplier<AkumaNoMiItem> var29 = () -> {
			return new AkumaNoMiItem(1,FruitType.PARAMECIA,new Supplier[]{SilentAbility.INSTANCE});
		};
		NAGI_NAGI_NO_MI = ModRegistry.registerFruitItem("Nagi Nagi no Mi",var29);
		Supplier<AkumaNoMiItem> var30 = () -> {
			return new AkumaNoMiItem(2,FruitType.PARAMECIA,new Supplier[]{CandyManAbility.INSTANCE,CandyWallAbility.INSTANCE,CandyEscalatorAbility.INSTANCE,CandyWaveAbility.INSTANCE,CandyArmorAbility.INSTANCE,PeroImmunityAbility.INSTANCE});
		};
		PERO_PERO_NO_MI = ModRegistry.registerFruitItem("Pero Pero no Mi",var30);
		Supplier<AkumaNoMiItem> var31 = () -> {
			return new AkumaNoMiItem(2,FruitType.PARAMECIA,new Supplier[]{NetsuEnhancementAbility.INSTANCE,NekkaiJigokuAbility.INSTANCE,NekkaiGyoraiAbility.INSTANCE,HeatDenasshiAbility.INSTANCE,HeatImmunityAbility.INSTANCE});
		};
		NETSU_NETSU_NO_MI = ModRegistry.registerFruitItem("Netsu Netsu no Mi",var31);
		Supplier<AkumaNoMiItem> var32 = () -> {
			return new AkumaNoMiItem(2,FruitType.PARAMECIA,new Supplier[]{FreeSwimmingAbility.INSTANCE,NekomimiPunchAbility.INSTANCE,NyanNyanSuplexAbility.INSTANCE,NyanNyanSuplexBabyBusterAbility.INSTANCE});
		};
		SUI_SUI_NO_MI = ModRegistry.registerFruitItem("Sui Sui no Mi",var32);
		Supplier<AkumaNoMiItem> var33 = () -> {
			return new AkumaNoMiItem(1,FruitType.PARAMECIA,new Supplier[]{GoldenHourAbility.INSTANCE,RelaxHourAbility.INSTANCE,SoapDefenseAbility.INSTANCE});
		};
		AWA_AWA_NO_MI = ModRegistry.registerFruitItem("Awa Awa no Mi",var33);
		Supplier<AkumaNoMiItem> var34 = () -> {
			return new AkumaNoMiItem(3,FruitType.PARAMECIA,new Supplier[]{StrawManAbility.INSTANCE,WarabideSwordAbility.INSTANCE,LifeMinusAbility.INSTANCE});
		};
		WARA_WARA_NO_MI = ModRegistry.registerFruitItem("Wara Wara no Mi",var34);
		Supplier<AkumaNoMiItem> var35 = () -> {
			return new AkumaNoMiItem(1,FruitType.PARAMECIA,new Supplier[]{EvaporateAbility.INSTANCE,HotBoilingSpecialAbility.INSTANCE,VulcanizationAbility.INSTANCE});
		};
		KACHI_KACHI_NO_MI = ModRegistry.registerFruitItem("Kachi Kachi no Mi",var35);
		Supplier<AkumaNoMiItem> var36 = () -> {
			return new AkumaNoMiItem(2,FruitType.PARAMECIA,new Supplier[]{DoorDoorAbility.INSTANCE,KaitenDoorAbility.INSTANCE,AirDoorAbility.INSTANCE});
		};
		DOA_DOA_NO_MI = ModRegistry.registerFruitItem("Doa Doa no Mi",var36);
		Supplier<AkumaNoMiItem> var37 = () -> {
			return new AkumaNoMiItem(1,FruitType.PARAMECIA,new Supplier[]{HealingTouchAbility.INSTANCE,ChiyupopoAbility.INSTANCE,KenpopoAbility.INSTANCE,TearsAbility.INSTANCE});
		};
		CHIYU_CHIYU_NO_MI = ModRegistry.registerFruitItem("Chiyu Chiyu no Mi",var37);
		Supplier<AkumaNoMiItem> var38 = () -> {
			return new AkumaNoMiItem(1,FruitType.PARAMECIA,new Supplier[]{RustTouchAbility.INSTANCE,RustBreakAbility.INSTANCE,RustSkinAbility.INSTANCE});
		};
		SABI_SABI_NO_MI = ModRegistry.registerFruitItem("Sabi Sabi no Mi",var38);
		Supplier<AkumaNoMiItem> var39 = () -> {
			return new AkumaNoMiItem(2,FruitType.PARAMECIA,new Supplier[]{SoulParadeAbility.INSTANCE,KasuriutaFubukiGiriAbility.INSTANCE,YomiNoReikiAbility.INSTANCE,YomiImmunityAbility.INSTANCE});
		};
		YOMI_YOMI_NO_MI = ModRegistry.registerFruitItem("Yomi Yomi no Mi",var39);
		Supplier<AkumaNoMiItem> var40 = () -> {
			return new AkumaNoMiItem(1,FruitType.PARAMECIA,new Supplier[]{BakuMunchAbility.INSTANCE,BeroCannonAbility.INSTANCE,BakuTsuihoAbility.INSTANCE,BakuFactoryAbility.INSTANCE});
		};
		BAKU_BAKU_NO_MI = ModRegistry.registerFruitItem("Baku Baku no Mi",var40);
		Supplier<AkumaNoMiItem> var41 = () -> {
			return new AkumaNoMiItem(1,FruitType.PARAMECIA,new Supplier[]{OnnaHormoneAbility.INSTANCE,ChiyuHormoneAbility.INSTANCE,TensionHormoneAbility.INSTANCE,GanmenSeichoHormoneAbility.INSTANCE,DeathWinkAbility.INSTANCE});
		};
		HORU_HORU_NO_MI = ModRegistry.registerFruitItem("Horu Horu no Mi",var41);
		Supplier<AkumaNoMiItem> var42 = () -> {
			return new AkumaNoMiItem(1,FruitType.PARAMECIA,new Supplier[]{KiloPress1Ability.INSTANCE,KiloPress10000Ability.INSTANCE,KiloPunch5000Ability.INSTANCE});
		};
		KILO_KILO_NO_MI = ModRegistry.registerFruitItem("Kilo Kilo no Mi",var42);
		Supplier<AkumaNoMiItem> var43 = () -> {
			return new AkumaNoMiItem(1,FruitType.PARAMECIA,new Supplier[]{TodorokiAbility.INSTANCE,DragonsRoarAbility.INSTANCE});
		};
		GOE_GOE_NO_MI = ModRegistry.registerFruitItem("Goe Goe no Mi",var43);
		Supplier<AkumaNoMiItem> var44 = () -> {
			return new AkumaNoMiItem(2,FruitType.PARAMECIA,new Supplier[]{MeroMeroMellowAbility.INSTANCE,PistolKissAbility.INSTANCE,SlaveArrowAbility.INSTANCE,PerfumeFemurAbility.INSTANCE});
		};
		MERO_MERO_NO_MI = ModRegistry.registerFruitItem("Mero Mero no Mi",var44);
		Supplier<AkumaNoMiItem> var45 = () -> {
			return new AkumaNoMiItem(1,FruitType.PARAMECIA,new Supplier[]{BindAbility.INSTANCE,GreatCageAbility.INSTANCE,AwaseBaoriAbility.INSTANCE});
		};
		ORI_ORI_NO_MI = ModRegistry.registerFruitItem("Ori Ori no Mi",var45);
		Supplier<AkumaNoMiItem> var46 = () -> {
			return new AkumaNoMiItem(2,FruitType.PARAMECIA,new Supplier[]{SpiderAbility.INSTANCE,SparClawAbility.INSTANCE,SpiralHollowAbility.INSTANCE,SparklingDaisyAbility.INSTANCE,AtomicSpurtAbility.INSTANCE});
		};
		SUPA_SUPA_NO_MI = ModRegistry.registerFruitItem("Supa Supa no Mi",var46);
		Supplier<AkumaNoMiItem> var47 = () -> {
			return new AkumaNoMiItem(2,FruitType.PARAMECIA,new Supplier[]{NegativeHollowAbility.INSTANCE,MiniHollowAbility.INSTANCE,TokuHollowAbility.INSTANCE,YutaiRidatsuAbility.INSTANCE});
		};
		HORO_HORO_NO_MI = ModRegistry.registerFruitItem("Horo Horo no Mi",var47);
		Supplier<AkumaNoMiItem> var48 = () -> {
			return new AkumaNoMiItem(3,FruitType.PARAMECIA,new Supplier[]{ParasiteAbility.INSTANCE,SoraNoMichiAbility.INSTANCE,TamaitoAbility.INSTANCE,FullbrightAbility.INSTANCE,OverheatAbility.INSTANCE,KumoNoSugakiAbility.INSTANCE,TorikagoAbility.INSTANCE,BlackKnightAbility.INSTANCE});
		};
		ITO_ITO_NO_MI = ModRegistry.registerFruitItem("Ito Ito no Mi",var48);
		Supplier<AkumaNoMiItem> var49 = () -> {
			return new AkumaNoMiItem(3,FruitType.PARAMECIA,new Supplier[]{JigokuTabiAbility.INSTANCE,SagariNoRyuseiAbility.INSTANCE,MokoAbility.INSTANCE,AbareHimatsuriAbility.INSTANCE,GraviZoneAbility.INSTANCE,GraviPullAbility.INSTANCE});
		};
		ZUSHI_ZUSHI_NO_MI = ModRegistry.registerFruitItem("Zushi Zushi no Mi",var49);
		Supplier<AkumaNoMiItem> var50 = () -> {
			return new AkumaNoMiItem(1,FruitType.PARAMECIA,new Supplier[]{BarrierAbility.INSTANCE,BarrierBallAbility.INSTANCE,BarrierCrashAbility.INSTANCE,BariBariNoPistolAbility.INSTANCE,BarrierbilityStairsAbility.INSTANCE,BarrierbilityBatAbility.INSTANCE});
		};
		BARI_BARI_NO_MI = ModRegistry.registerFruitItem("Bari Bari no Mi",var50);
		Supplier<AkumaNoMiItem> var51 = () -> {
			return new AkumaNoMiItem(3,FruitType.PARAMECIA,new Supplier[]{HydraAbility.INSTANCE,ChloroBallAbility.INSTANCE,DokuFuguAbility.INSTANCE,VenomRoadAbility.INSTANCE,DokuGumoAbility.INSTANCE,VenomDemonAbility.INSTANCE,PoisonImmunityAbility.INSTANCE});
		};
		DOKU_DOKU_NO_MI = ModRegistry.registerFruitItem("Doku Doku no Mi",var51);
		Supplier<AkumaNoMiItem> var52 = () -> {
			return new AkumaNoMiItem(2,FruitType.PARAMECIA,new Supplier[]{CandleWallAbility.INSTANCE,CandleHouseAbility.INSTANCE,DoruDoruArtsMoriAbility.INSTANCE,DoruDoruArtsKenAbility.INSTANCE,CandleLockAbility.INSTANCE,DoruDoruArtsPickaxeAbility.INSTANCE,DoruDoruBallAbility.INSTANCE,DoruDoruNoYakataAbility.INSTANCE,CandleChampionAbility.INSTANCE});
		};
		DORU_DORU_NO_MI = ModRegistry.registerFruitItem("Doru Doru no Mi",var52);
		Supplier<AkumaNoMiItem> var53 = () -> {
			return new AkumaNoMiItem(2,FruitType.PARAMECIA,new Supplier[]{DoppelmanAbility.INSTANCE,KagemushaAbility.INSTANCE,BrickBatAbility.INSTANCE,BlackBoxAbility.INSTANCE,TsunoTokageAbility.INSTANCE,KageKakumeiAbility.INSTANCE,KageGiriAbility.INSTANCE,NightmareSoldiersAbility.INSTANCE,ShadowsAsgardAbility.INSTANCE});
		};
		KAGE_KAGE_NO_MI = ModRegistry.registerFruitItem("Kage Kage no Mi",var53);
		Supplier<AkumaNoMiItem> var54 = () -> {
			return new AkumaNoMiItem(3,FruitType.PARAMECIA,new Supplier[]{GekishinAbility.INSTANCE,ShingenNoIchigekiAbility.INSTANCE,TenchiMeidoAbility.INSTANCE,ShimaYurashiAbility.INSTANCE,GuraImmunityAbility.INSTANCE,KabutowariAbility.INSTANCE});
		};
		GURA_GURA_NO_MI = ModRegistry.registerFruitItem("Gura Gura no Mi",var54);
		Supplier<AkumaNoMiItem> var55 = () -> {
			return new AkumaNoMiItem(2,FruitType.PARAMECIA,new Supplier[]{NoseFancyCannonAbility.INSTANCE,KickBombAbility.INSTANCE,ZenshinKibakuAbility.INSTANCE,ExplosivePunchAbility.INSTANCE,BreezeBreathBombAbility.INSTANCE,ExplosionImmunityAbility.INSTANCE});
		};
		BOMU_BOMU_NO_MI = ModRegistry.registerFruitItem("Bomu Bomu no Mi",var55);
		Supplier<AkumaNoMiItem> var56 = () -> {
			return new AkumaNoMiItem(3,FruitType.PARAMECIA,new Supplier[]{PadHoAbility.INSTANCE,HanpatsuAbility.INSTANCE,TsuppariPadHoAbility.INSTANCE,UrsusShockAbility.INSTANCE,PuniAbility.INSTANCE,NikyuPushAbility.INSTANCE,PainRepelAbility.INSTANCE});
		};
		NIKYU_NIKYU_NO_MI = ModRegistry.registerFruitItem("Nikyu Nikyu no Mi",var56);
		Supplier<AkumaNoMiItem> var57 = () -> {
			return new AkumaNoMiItem(1,FruitType.PARAMECIA,new Supplier[]{NoroNoroBeamAbility.INSTANCE,NoroNoroBeamSwordAbility.INSTANCE,KyubiRushAbility.INSTANCE});
		};
		NORO_NORO_NO_MI = ModRegistry.registerFruitItem("Noro Noro no Mi",var57);
		Supplier<AkumaNoMiItem> var58 = () -> {
			return new AkumaNoMiItem(2,FruitType.PARAMECIA,new Supplier[]{RoomAbility.INSTANCE,ShamblesAbility.INSTANCE,TaktAbility.INSTANCE,InjectionShotAbility.INSTANCE,CounterShockAbility.INSTANCE,MesAbility.INSTANCE,GammaKnifeAbility.INSTANCE});
		};
		OPE_OPE_NO_MI = ModRegistry.registerFruitItem("Ope Ope no Mi",var58);
		Supplier<AkumaNoMiItem> var59 = () -> {
			return new AkumaNoMiItem(1,FruitType.PARAMECIA,new Supplier[]{SkattingAbility.INSTANCE,ShishaNoTeAbility.INSTANCE,SukePunchAbility.INSTANCE});
		};
		SUKE_SUKE_NO_MI = ModRegistry.registerFruitItem("Suke Suke no Mi",var59);
		Supplier<AkumaNoMiItem> var60 = () -> {
			return new AkumaNoMiItem(2,FruitType.PARAMECIA,new Supplier[]{GomuGomuNoPistolAbility.INSTANCE,GomuGomuNoGatlingAbility.INSTANCE,GomuGomuNoBazookaAbility.INSTANCE,GomuGomuNoRocketAbility.INSTANCE,GearSecondAbility.INSTANCE,GearThirdAbility.INSTANCE,GearFourthAbility.INSTANCE,GearFourthFlightAbility.INSTANCE,BouncyAbility.INSTANCE,BulletReflectAbility.INSTANCE,GomuImmunityAbility.INSTANCE,GearFifthAbility.INSTANCE,GomuGomuNoGigantAbility.INSTANCE,GomuGomuNoDawnWhipAbility.INSTANCE});
		};
		GOMU_GOMU_NO_MI = ModRegistry.registerFruitItem("Gomu Gomu no Mi",var60);
		Supplier<AkumaNoMiItem> var61 = () -> {
			return new AkumaNoMiItem(1,FruitType.PARAMECIA,new Supplier[]{SpringHopperAbility.INSTANCE,SpringSnipeAbility.INSTANCE,SpringDeathKnockAbility.INSTANCE,NoFallDamageAbility.INSTANCE});
		};
		BANE_BANE_NO_MI = ModRegistry.registerFruitItem("Bane Bane no Mi",var61);
		Supplier<AkumaNoMiItem> var62 = () -> {
			return new AkumaNoMiItem(3,FruitType.LOGIA,new Supplier[]{KamakuraAbility.INSTANCE,YukiRabiAbility.INSTANCE,KamakuraJussoshiAbility.INSTANCE,TabiraYukiAbility.INSTANCE,YukiGakiAbility.INSTANCE,FubukiAbility.INSTANCE,YukiLogiaAbility.INSTANCE,YukiImmunityAbility.INSTANCE,YukiTravelAbility.INSTANCE});
		};
		YUKI_YUKI_NO_MI = ModRegistry.registerFruitItem("Yuki Yuki no Mi",var62);
		Supplier<AkumaNoMiItem> var63 = () -> {
			return new AkumaNoMiItem(3,FruitType.LOGIA,new Supplier[]{BlackHoleAbility.INSTANCE,BlackRoadAbility.INSTANCE,LiberationAbility.INSTANCE,KurouzuAbility.INSTANCE,DarkMatterAbility.INSTANCE,BlackWorldAbility.INSTANCE,AbsorbedBlocksAbility.INSTANCE});
		};
		YAMI_YAMI_NO_MI = ModRegistry.registerFruitItem("Yami Yami no Mi",var63);
		Supplier<AkumaNoMiItem> var64 = () -> {
			return new AkumaNoMiItem(3,FruitType.LOGIA,new Supplier[]{GasRobeAbility.INSTANCE,GastanetAbility.INSTANCE,GastilleAbility.INSTANCE,BlueSwordAbility.INSTANCE,KarakuniAbility.INSTANCE,ShinokuniAbility.INSTANCE,KoroAbility.INSTANCE,GasuFlyAbility.INSTANCE,GasuImmunityAbility.INSTANCE,GasuLogiaAbility.INSTANCE});
		};
		GASU_GASU_NO_MI = ModRegistry.registerFruitItem("Gasu Gasu no Mi",var64);
		Supplier<AkumaNoMiItem> var65 = () -> {
			return new AkumaNoMiItem(3,FruitType.LOGIA,new Supplier[]{DaiFunkaAbility.INSTANCE,RyuseiKazanAbility.INSTANCE,MeigoAbility.INSTANCE,BakuretsuKazanAbility.INSTANCE,LavaFlowAbility.INSTANCE,MagmaCoatingAbility.INSTANCE,MaguLogiaAbility.INSTANCE,LogiaHeatImmunityAbility.INSTANCE});
		};
		MAGU_MAGU_NO_MI = ModRegistry.registerFruitItem("Magu Magu no Mi",var65);
		Supplier<AkumaNoMiItem> var66 = () -> {
			return new AkumaNoMiItem(3,FruitType.LOGIA,new Supplier[]{DesertSpadaAbility.INSTANCE,DesertGrandeEspadaAbility.INSTANCE,SablesAbility.INSTANCE,GrandeSablesAbility.INSTANCE,BarjanAbility.INSTANCE,DesertEncierroAbility.INSTANCE,GroundDeathAbility.INSTANCE,DesertGirasoleAbility.INSTANCE,SunaFlyAbility.INSTANCE,SablesPesadoAbility.INSTANCE,SunaLogiaAbility.INSTANCE,LogiaImmunityAbility.INSTANCE,SunaTravelAbility.INSTANCE});
		};
		SUNA_SUNA_NO_MI = ModRegistry.registerFruitItem("Suna Suna no Mi",var66);
		Supplier<AkumaNoMiItem> var67 = () -> {
			return new AkumaNoMiItem(3,FruitType.LOGIA,new Supplier[]{SparkStepAbility.INSTANCE,ElThorAbility.INSTANCE,VariAbility.INSTANCE,KariAbility.INSTANCE,SangoAbility.INSTANCE,VoltAmaruAbility.INSTANCE,VoltAmaruFlightAbility.INSTANCE,RaigoAbility.INSTANCE,GoroLogiaAbility.INSTANCE,LogiaHeatImmunityAbility.INSTANCE,GoroTravelAbility.INSTANCE});
		};
		GORO_GORO_NO_MI = ModRegistry.registerFruitItem("Goro Goro no Mi",var67);
		Supplier<AkumaNoMiItem> var68 = () -> {
			return new AkumaNoMiItem(3,FruitType.LOGIA,new Supplier[]{WhiteBlowAbility.INSTANCE,WhiteSnakeAbility.INSTANCE,WhiteLauncherAbility.INSTANCE,WhiteStrikeAbility.INSTANCE,WhiteBlowRushAbility.INSTANCE,WhiteOutAbility.INSTANCE,MokuFlyAbility.INSTANCE,WhitePullAbility.INSTANCE,SmokeLaunchAbility.INSTANCE,MokuLogiaAbility.INSTANCE,MokuImmunityAbility.INSTANCE});
		};
		MOKU_MOKU_NO_MI = ModRegistry.registerFruitItem("Moku Moku no Mi",var68);
		Supplier<AkumaNoMiItem> var69 = () -> {
			return new AkumaNoMiItem(3,FruitType.LOGIA,new Supplier[]{YataNoKagamiAbility.INSTANCE,YasakaniNoMagatamaAbility.INSTANCE,AmaNoMurakumoAbility.INSTANCE,AmaterasuAbility.INSTANCE,FlashAbility.INSTANCE,PikaLogiaAbility.INSTANCE,LightAccelerationAbility.INSTANCE,LogiaImmunityAbility.INSTANCE,PikaTravelAbility.INSTANCE});
		};
		PIKA_PIKA_NO_MI = ModRegistry.registerFruitItem("Pika Pika no Mi",var69);
		Supplier<AkumaNoMiItem> var70 = () -> {
			return new AkumaNoMiItem(3,FruitType.LOGIA,new Supplier[]{IceBlockPartisanAbility.INSTANCE,IceAgeAbility.INSTANCE,IceBallAbility.INSTANCE,IceSaberAbility.INSTANCE,IceTimeAbility.INSTANCE,IceBlockPheasantAbility.INSTANCE,IceBlockAvalancheAbility.INSTANCE,HieLogiaAbility.INSTANCE,FrostWalkerAbility.INSTANCE,HieImmunityAbility.INSTANCE,HieTravelAbility.INSTANCE});
		};
		HIE_HIE_NO_MI = ModRegistry.registerFruitItem("Hie Hie no Mi",var70);
		Supplier<AkumaNoMiItem> var71 = () -> {
			return new AkumaNoMiItem(3,FruitType.LOGIA,new Supplier[]{HikenAbility.INSTANCE,HiganAbility.INSTANCE,DaiEnkaiAbility.INSTANCE,HidarumaAbility.INSTANCE,JujikaAbility.INSTANCE,HeatDashAbility.INSTANCE,KyokaenAbility.INSTANCE,HibashiraAbility.INSTANCE,MeraLogiaAbility.INSTANCE,LogiaHeatImmunityAbility.INSTANCE});
		};
		MERA_MERA_NO_MI = ModRegistry.registerFruitItem("Mera Mera no Mi",var71);
	}
}