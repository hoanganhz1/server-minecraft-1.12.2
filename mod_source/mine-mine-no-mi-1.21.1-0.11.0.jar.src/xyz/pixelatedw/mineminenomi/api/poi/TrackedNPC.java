package xyz.pixelatedw.mineminenomi.api.poi;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.entities.IMobDetails;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.AttributeHelper;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.data.entity.HakiData;
import xyz.pixelatedw.mineminenomi.entities.mobs.NotoriousEntity;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModRaces;

public class TrackedNPC {
	// Fields
	private static final double MIN_DORIKI = 1000D;
	private static final float MIN_OBS_HAKI = 10F;
	private static final float MIN_BUSO_HAKI = 10F;
	private static final long MIN_BOUNTY = 100000L;
	private static final float MAX_TIME = 1.5552e+08F;
	private static final List<Supplier<FightingStyle>> STYLES;
	private static final List<Supplier<Race>> RACES;
	private UUID uuid;
	private Random random;
	private long seed;
	private long id;
	private Vec3 pos;
	private long lastGameUpdate;
	private long lastNewsUpdate;
	private Faction faction;
	private Race race;
	private Race subRace;
	private FightingStyle style;
	private double doriki;
	private long bounty;
	private float kenbunshokuExp;
	private float busoshokuExp;
	private NewsEntry newsEntry;

	private TrackedNPC() {
		this.pos = Vec3.ZERO;
	}
	public TrackedNPC(ServerLevel var1, long var2, Faction var3, long var4) {
		this.pos = Vec3.ZERO;
		this.setSeed(var4);
		this.id = var2;
		this.faction = var3;
		this.style = ((FightingStyle)(((Supplier)(STYLES.get(this.random.nextInt(STYLES.size())))).get()));
		this.race = ((Race)(((Supplier)(RACES.get(this.random.nextInt(RACES.size())))).get()));
		if (((java.lang.Object)(this.race)).equals(ModRaces.MINK.get())){
			int var5 = ((Race)(ModRaces.MINK.get())).getSubRaces().size();
			this.subRace = ((Race)(((Supplier)(((Race)(ModRaces.MINK.get())).getSubRaces().get(this.random.nextInt(var5)))).get()));
		}
		this.lastGameUpdate = var1.getGameTime();
		this.doriki = 1000D;
		this.kenbunshokuExp = 10F;
		this.busoshokuExp = 10F;
		if (this.faction.equals(ModFactions.PIRATE.get())){
			this.bounty = 100000L;
		}
	}
	public static TrackedNPC from(CompoundTag var0) {
		TrackedNPC var1 = new TrackedNPC();
		var1.setSeed(var0.getLong("seed"));
		var1.id = var0.getLong("id");
		var1.uuid = var0.getUUID("uuid");
		var1.lastGameUpdate = var0.getLong("lastGameUpdate");
		var1.lastNewsUpdate = var0.getLong("lastNewsUpdate");
		ResourceLocation var2 = ResourceLocation.parse(var0.getString("faction"));
		Faction var3 = ((Faction)(WyRegistry.FACTIONS.get(var2)));
		Objects.requireNonNull(var3,"Faction of a TrackedNPC cannot be null!");
		var1.faction = var3;
		if (var1.faction.equals(ModFactions.PIRATE.get())){
			var1.bounty = var0.getLong("bounty");
		}
		ResourceLocation var4 = ResourceLocation.parse(var0.getString("race"));
		Race var5 = ((Race)(WyRegistry.RACES.get(var4)));
		Objects.requireNonNull(var5,"Race of a TrackedNPC cannot be null!");
		var1.race = var5;
		ResourceLocation var6 = ResourceLocation.parse(var0.getString("subRace"));
		Race var7 = ((Race)(WyRegistry.RACES.get(var6)));
		var1.subRace = var7;
		ResourceLocation var8 = ResourceLocation.parse(var0.getString("style"));
		FightingStyle var9 = ((FightingStyle)(WyRegistry.FIGHTING_STYLES.get(var8)));
		Objects.requireNonNull(var9,"Fighting Style of a TrackedNPC cannot be null!");
		var1.style = var9;
		var1.doriki = var0.getDouble("doriki");
		var1.kenbunshokuExp = var0.getFloat("kenbunshokuExp");
		var1.busoshokuExp = var0.getFloat("busoshokuExp");
		return var1;
	}
	public long getId() {
		return this.id;
	}
	public UUID getUUID() {
		return this.uuid;
	}
	public long getSeed() {
		return this.seed;
	}
	public void setSeed(long var1) {
		this.seed = var1;
		if ((this.random) == (null)){
			this.random = new Random(var1);
		}else{
			this.random.setSeed(var1);
		}
		this.uuid = new UUID(this.random.nextLong(),this.random.nextLong());
	}
	public double getDoriki() {
		return this.doriki;
	}
	public long getBounty() {
		return this.bounty;
	}
	public float getObservationHaki() {
		return this.kenbunshokuExp;
	}
	public float getBusoshokuHaki() {
		return this.busoshokuExp;
	}
	public Random getRandom() {
		return this.random;
	}
	public Faction getFaction() {
		return this.faction;
	}
	public Race getRace() {
		return this.race;
	}
	@Nullable()
	public Race getSubRace() {
		return this.subRace;
	}
	public FightingStyle getStyle() {
		return this.style;
	}
	public NewsEntry getNewsEntry(Level var1) {
		if ((this.newsEntry) == (null)){
			this.updateNewsEntry(var1);
		}
		return this.newsEntry;
	}
	public void updateNewsEntry(Level var1) {
		this.newsEntry = NewsEntry.createNewsFor(this,var1);
		this.lastNewsUpdate = var1.getGameTime();
	}
	public void recalculateDifficulty(ServerLevel var1) {
		float var2 = ((float)(var1.getGameTime())) / (1.5552e+08F);
		int var3 = 0;
		double var4 = 0D;
		float var5 = 0F;
		Iterator var6 = var1.players().iterator();
		do{
			if (var6.hasNext()){
				ServerPlayer var7 = ((ServerPlayer)(var6.next()));
				var3++;
				var4 = (var4) + (((Double)(EntityStatsData.get((LivingEntity)(var7)).map((Function)(((Function<EntityStatsData, Double>)(EntityStatsData::getDoriki)))).orElse(Double.valueOf(0D)))).doubleValue());
				Optional var8 = HakiData.get((LivingEntity)(var7));
				float var9 = (var5) + (((Float)(var8.map((Function)(((Function<HakiData, Float>)(HakiData::getBusoshokuHakiExp)))).orElse(Float.valueOf(0F)))).floatValue());
				var5 = (var9) + (((Float)(var8.map((Function)(((Function<HakiData, Float>)(HakiData::getKenbunshokuHakiExp)))).orElse(Float.valueOf(0F)))).floatValue());
				continue;
			}else{
				break;
			}
		} while (true);
		double var7_1 = (double)((ServerConfig.getDorikiLimit()) * (var3));
		float var8_1 = (float)(((ServerConfig.getHakiExpLimit()) * (2)) * (var3));
		double var9_1 = Mth.clamp((var4) / (var7_1),0D,0.5D);
		double var10 = Mth.clamp((double)((var5) / (var8_1)),0D,0.5D);
		double var11 = (((var9_1) * (0.3D)) + ((var10) * (0.3D))) + (((double)(var2)) * (0.4D));
		if (Double.isNaN(this.doriki)){
			this.doriki = 1000D;
		}
		double var12 = (double)((float)((this.doriki) * ((1D) + (var9_1))));
		if ((var12) < (this.doriki)){
			var12 = this.doriki;
		}
		this.doriki = Mth.clamp(var12,1000D,(double)(ServerConfig.getDorikiLimit()));
		if (Float.isNaN(this.kenbunshokuExp)){
			this.kenbunshokuExp = 10F;
		}
		float var13 = (float)(((double)(this.kenbunshokuExp)) * ((1D) + (var10)));
		if ((var13) < (this.kenbunshokuExp)){
			var13 = this.kenbunshokuExp;
		}
		this.kenbunshokuExp = Mth.clamp(var13,10F,(float)(ServerConfig.getHakiExpLimit()));
		if (Float.isNaN(this.busoshokuExp)){
			this.busoshokuExp = 10F;
		}
		float var14 = (float)(((double)(this.busoshokuExp)) * ((1D) + (var10)));
		if ((var14) < (this.busoshokuExp)){
			var14 = this.busoshokuExp;
		}
		this.busoshokuExp = Mth.clamp(var14,10F,(float)(ServerConfig.getHakiExpLimit()));
		long var15 = 100000L;
		if (this.faction.equals(ModFactions.PIRATE.get())){
			long var16 = (long)(((double)(var15)) * ((1D) + (var11)));
			var15 = (var16) + ((long)(this.random.nextInt(10000)));
		}
		this.bounty = (this.bounty) + (var15);
		this.bounty = WyHelper.clamp(this.bounty,10000L,100000000000L);
		this.lastGameUpdate = var1.getGameTime();
	}
	@Nullable()
	public NotoriousEntity createTrackedMob(Level var1) {
		NotoriousEntity var2 = null;
		if (this.getFaction().equals(ModFactions.PIRATE.get())){
			EntityType var3 = ((EntityType)(ModMobs.PIRATE_NOTORIOUS_CAPTAIN.get()));
			IMobDetails var4 = NotoriousEntity::initPirate;
			var2 = new NotoriousEntity(var3,var1,var4,this);
		}else{
			if (this.getFaction().equals(ModFactions.MARINE.get())){
				EntityType var3_1 = ((EntityType)(ModMobs.MARINE_VICE_ADMIRAL.get()));
				IMobDetails var4_1 = NotoriousEntity::initMarine;
				var2 = new NotoriousEntity(var3_1,var1,var4_1,this);
			}
		}
		if ((var2) == (null)){
			WyDebug.warn("Null entity found when creating a Notorious entity, this will most likely break things!");
		}
		return var2;
	}
	@Nullable()
	public NotoriousEntity spawnTrackedMob(ServerLevel var1, Vec3 var2) {
		NotoriousEntity var3 = this.createTrackedMob((Level)(var1));
		if ((var3) == (null)){
			return null;
		}else{
			int var4 = var1.getHeight(Heightmap.Types.MOTION_BLOCKING,(int)(var2.x()),(int)(var2.z()));
			var3.setPos(var2.x(),(double)((var4) + (1)),var2.z());
			EntityStatsData var5 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get((LivingEntity)(var3)))).orElse((Object)(null))));
			HakiData var6 = ((HakiData)(((java.util.Optional)(HakiData.get((LivingEntity)(var3)))).orElse((Object)(null))));
			if (((var5) != (null)) && ((var6) != (null))){
				this.doriki = Mth.clamp(this.doriki,1000D,(double)(ServerConfig.getDorikiLimit()));
				this.bounty = WyHelper.clamp(this.bounty,10000L,100000000000L);
				var5.setDoriki(this.doriki);
				var5.setFaction(this.faction);
				if (this.faction.equals(ModFactions.PIRATE.get())){
					var5.setBounty(this.bounty);
				}
				var5.setRace(this.race);
				var5.setSubRace(this.subRace);
				var5.setFightingStyle(this.style);
				var6.setKenbunshokuHakiExp(this.kenbunshokuExp);
				var6.setBusoshokuHakiExp(this.busoshokuExp);
				double var7 = (200D) + ((this.doriki) / (20D));
				var7 = Mth.clamp(var7,200D,((double)(ServerConfig.getDorikiLimit())) / (20D));
				var3.getAttribute(Attributes.MAX_HEALTH).setBaseValue(var7);
				AttributeHelper.updateToughnessAttribute((LivingEntity)(var3));
				var3.heal(var3.getMaxHealth());
				var1.addFreshEntity((Entity)(var3));
				return var3;
			}else{
				return null;
			}
		}
	}
	public CompoundTag save() {
		CompoundTag var1 = new CompoundTag();
		var1.putLong("seed",this.seed);
		var1.putUUID("uuid",this.uuid);
		var1.putLong("id",this.id);
		var1.putLong("lastGameUpdate",this.lastGameUpdate);
		var1.putLong("lastNewsUpdate",this.lastNewsUpdate);
		var1.putString("faction",this.faction.getRegistryName().toString());
		var1.putString("race",this.race.getRegistryName().toString());
		var1.putString("style",this.style.getRegistryName().toString());
		var1.putDouble("doriki",this.doriki);
		var1.putLong("bounty",this.bounty);
		var1.putFloat("kenbunshokuExp",this.kenbunshokuExp);
		var1.putFloat("busoshokuExp",this.busoshokuExp);
		return var1;
	}
	public boolean equals(Object var1) {
		if (var1 instanceof TrackedNPC){
			TrackedNPC var2 = ((TrackedNPC)(var1));
			if ((this.id) == (var2.id)){
				return true;
			}else{
				return super.equals(var1);
			}
		}else{
			return false;
		}
	}
	static  {
		Supplier[] var0 = new Supplier[]{ModFightingStyles.SWORDSMAN,ModFightingStyles.BRAWLER,ModFightingStyles.BLACK_LEG,ModFightingStyles.SNIPER};
		STYLES = Lists.newArrayList(var0);
		Supplier[] var1 = new Supplier[]{ModRaces.HUMAN,ModRaces.FISHMAN,ModRaces.MINK};
		RACES = Lists.newArrayList(var1);
	}
}