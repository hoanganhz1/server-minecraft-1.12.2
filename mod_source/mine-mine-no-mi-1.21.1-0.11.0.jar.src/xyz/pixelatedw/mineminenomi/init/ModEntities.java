package xyz.pixelatedw.mineminenomi.init;

import java.util.function.Supplier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.entities.AkumaNoMiEntity;
import xyz.pixelatedw.mineminenomi.entities.BombEntity;
import xyz.pixelatedw.mineminenomi.entities.ChakramEntity;
import xyz.pixelatedw.mineminenomi.entities.NetEntity;
import xyz.pixelatedw.mineminenomi.entities.SphereEntity;
import xyz.pixelatedw.mineminenomi.entities.SpikeEntity;
import xyz.pixelatedw.mineminenomi.entities.ThrownSpearEntity;
import xyz.pixelatedw.mineminenomi.entities.TornadoEntity;
import xyz.pixelatedw.mineminenomi.entities.VivreCardEntity;
import xyz.pixelatedw.mineminenomi.entities.WantedPosterPackageEntity;
import xyz.pixelatedw.mineminenomi.entities.clouds.ChloroBallCloudEntity;
import xyz.pixelatedw.mineminenomi.entities.clouds.KemuriBoshiCloudEntity;
import xyz.pixelatedw.mineminenomi.entities.clouds.MH5CloudEntity;
import xyz.pixelatedw.mineminenomi.entities.clouds.MirageTempoCloudEntity;
import xyz.pixelatedw.mineminenomi.entities.clouds.WeatherCloudEntity;
import xyz.pixelatedw.mineminenomi.entities.projectiles.ThrowingWeaponEntity;
import xyz.pixelatedw.mineminenomi.entities.vehicles.CannonEntity;
import xyz.pixelatedw.mineminenomi.entities.vehicles.StrikerEntity;
import xyz.pixelatedw.mineminenomi.entities.vehicles.UnicycleEntity;

public class ModEntities {
	// Fields
	public static final Supplier<EntityType<CannonEntity>> CANNON;
	public static final Supplier<EntityType<StrikerEntity>> STRIKER;
	public static final Supplier<EntityType<UnicycleEntity>> UNICYCLE;
	public static final Supplier<EntityType<KemuriBoshiCloudEntity>> KEMURI_BOSHI_CLOUD;
	public static final Supplier<EntityType<MirageTempoCloudEntity>> MIRAGE_TEMPO_CLOUD;
	public static final Supplier<EntityType<WeatherCloudEntity>> WEATHER_CLOUD;
	public static final Supplier<EntityType<MH5CloudEntity>> MH5_CLOUD;
	public static final Supplier<EntityType<ChloroBallCloudEntity>> CHLORO_BALL_CLOUD;
	public static final Supplier<EntityType<WantedPosterPackageEntity>> WANTED_POSTER_PACKAGE;
	public static final Supplier<EntityType<VivreCardEntity>> VIVRE_CARD;
	public static final Supplier<EntityType<BombEntity>> BOMB;
	public static final Supplier<EntityType<AkumaNoMiEntity>> DEVIL_FRUIT_ITEM;
	public static final Supplier<EntityType<SpikeEntity>> SPIKE;
	public static final Supplier<EntityType<TornadoEntity>> TORNADO;
	public static final Supplier<EntityType<SphereEntity>> SPHERE;
	public static final Supplier<EntityType<NetEntity>> ROPE_NET;
	public static final Supplier<EntityType<NetEntity>> KAIROSEKI_NET;
	public static final Supplier<EntityType<ThrowingWeaponEntity>> THROWING_WEAPON;
	public static final Supplier<EntityType<ThrownSpearEntity>> THROWN_SPEAR;
	public static final Supplier<EntityType<ChakramEntity>> CHAKRAM;

	public static void init() {
	}
	static  {
		Supplier<EntityType> var0 = () -> {
			EntityType.EntityFactory lv1_0 = CannonEntity::new;
			return EntityType.Builder.of(lv1_0,MobCategory.MISC).sized(1.25F,1.25F).clientTrackingRange(10).build("mineminenomi:cannon");
		};
		CANNON = ModRegistry.registerEntityType("Cannon",var0);
		Supplier<EntityType> var1 = () -> {
			EntityType.EntityFactory lv2_0 = StrikerEntity::new;
			return EntityType.Builder.of(lv2_0,MobCategory.MISC).sized(1.75F,0.5625F).clientTrackingRange(10).build("mineminenomi:striker");
		};
		STRIKER = ModRegistry.registerEntityType("Striker",var1);
		Supplier<EntityType> var2 = () -> {
			EntityType.EntityFactory lv3_0 = UnicycleEntity::new;
			return EntityType.Builder.of(lv3_0,MobCategory.MISC).sized(0.75F,0.75F).clientTrackingRange(10).build("mineminenomi:unicycle");
		};
		UNICYCLE = ModRegistry.registerEntityType("Unicycle",var2);
		Supplier<EntityType> var3 = () -> {
			EntityType.EntityFactory lv4_0 = KemuriBoshiCloudEntity::new;
			return ModRegistry.createEntityType(lv4_0,MobCategory.MISC).sized(0.5F,0.5F).build("mineminenomi:kemuri_boshi_cloud");
		};
		KEMURI_BOSHI_CLOUD = ModRegistry.registerEntityType("Kemuri Boshi Cloud",var3);
		Supplier<EntityType> var4 = () -> {
			EntityType.EntityFactory lv5_0 = MirageTempoCloudEntity::new;
			return ModRegistry.createEntityType(lv5_0,MobCategory.MISC).sized(0.5F,0.5F).build("mineminenomi:mirage_tempo_cloud");
		};
		MIRAGE_TEMPO_CLOUD = ModRegistry.registerEntityType("Mirage Cloud",var4);
		Supplier<EntityType> var5 = () -> {
			EntityType.EntityFactory lv6_0 = WeatherCloudEntity::new;
			return ModRegistry.createEntityType(lv6_0,MobCategory.MISC).sized(0.5F,0.5F).build("mineminenomi:weather_cloud");
		};
		WEATHER_CLOUD = ModRegistry.registerEntityType("Weather Cloud",var5);
		Supplier<EntityType> var6 = () -> {
			EntityType.EntityFactory lv7_0 = MH5CloudEntity::new;
			return ModRegistry.createEntityType(lv7_0,MobCategory.MISC).sized(0.5F,0.5F).build("mineminenomi:mh5_cloud");
		};
		MH5_CLOUD = ModRegistry.registerEntityType("MH5 Cloud",var6);
		Supplier<EntityType> var7 = () -> {
			EntityType.EntityFactory lv8_0 = ChloroBallCloudEntity::new;
			return ModRegistry.createEntityType(lv8_0,MobCategory.MISC).sized(0.5F,0.5F).build("mineminenomi:chloro_ball_cloud");
		};
		CHLORO_BALL_CLOUD = ModRegistry.registerEntityType("Chloro Ball Cloud",var7);
		Supplier<EntityType> var8 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(WantedPosterPackageEntity::new)))).sized(1.4F,0.25F).build("mineminenomi:wanted_poster_package");
		};
		WANTED_POSTER_PACKAGE = ModRegistry.registerEntityType("Wanted Poster Package",var8);
		Supplier<EntityType> var9 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(VivreCardEntity::new)))).sized(0.4F,0.4F).build("mineminenomi:vivre_card");
		};
		VIVRE_CARD = ModRegistry.registerEntityType("Vivre Card",var9);
		Supplier<EntityType> var10 = () -> {
			EntityType.EntityFactory lv11_0 = BombEntity::new;
			return ModRegistry.createEntityType(lv11_0,MobCategory.MISC).setUpdateInterval(1).sized(0.8F,0.8F).build("mineminenomi:bomb");
		};
		BOMB = ModRegistry.registerEntityType("Bomb",var10);
		Supplier<EntityType> var11 = () -> {
			EntityType.EntityFactory lv12_0 = AkumaNoMiEntity::new;
			return ModRegistry.createEntityType(lv12_0,MobCategory.MISC).sized(0.25F,0.25F).build("mineminenomi:df_item");
		};
		DEVIL_FRUIT_ITEM = ModRegistry.registerEntityType("Devil Fruit",var11);
		Supplier<EntityType> var12 = () -> {
			EntityType.EntityFactory lv13_0 = SpikeEntity::new;
			return ModRegistry.createEntityType(lv13_0,MobCategory.MISC).sized(0.2F,0.2F).build("mineminenomi:spike");
		};
		SPIKE = ModRegistry.registerEntityType("Spike",var12);
		Supplier<EntityType> var13 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(TornadoEntity::new)))).sized(1.5F,3F).build("mineminenomi:tornado");
		};
		TORNADO = ModRegistry.registerEntityType("Tornado",var13);
		Supplier<EntityType> var14 = () -> {
			EntityType.EntityFactory lv15_0 = SphereEntity::new;
			return ModRegistry.createEntityType(lv15_0,MobCategory.MISC).sized(0.5F,0.5F).build("mineminenomi:sphere");
		};
		SPHERE = ModRegistry.registerEntityType("Sphere",var14);
		Supplier<EntityType> var15 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(NetEntity::new)))).sized(1.2F,0.5F).build("mineminenomi:rope_net");
		};
		ROPE_NET = ModRegistry.registerEntityType("Rope Net",var15);
		Supplier<EntityType> var16 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(NetEntity::new)))).sized(1.2F,0.5F).build("mineminenomi:kairoseki_net");
		};
		KAIROSEKI_NET = ModRegistry.registerEntityType("Kairoseki Net",var16);
		Supplier<EntityType> var17 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(ThrowingWeaponEntity::new)))).sized(1F,1F).build("mineminenomi:throwing_knife");
		};
		THROWING_WEAPON = ModRegistry.registerEntityType("Throwing Weapon",var17);
		Supplier<EntityType> var18 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(ThrownSpearEntity::new)))).sized(0.5F,0.5F).build("mineminenomi:thrown_spear");
		};
		THROWN_SPEAR = ModRegistry.registerEntityType("Thrown Spear",var18);
		Supplier<EntityType> var19 = () -> {
			return ModRegistry.createProjectileType((EntityType.EntityFactory)(((net.minecraft.world.entity.EntityType.EntityFactory)(ChakramEntity::new)))).sized(0.75F,0.5F).build("mineminenomi:chakram");
		};
		CHAKRAM = ModRegistry.registerEntityType("Chakram",var19);
	}
}