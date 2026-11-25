package xyz.pixelatedw.mineminenomi.api.items.components;

import com.mojang.authlib.GameProfile;
import com.mojang.datafixers.Products;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.datafixers.util.Function10;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.Util;
import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.crew.JollyRoger;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.data.world.EventsWorldData;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;

public final record WantedPosterData(UUID owner, String ownerName, long bounty, boolean isExpired, String background, String date, Optional<ResourceLocation> picture, Optional<Faction> faction, Optional<JollyRoger> jollyRoger, Optional<Vec3> trackedPosition) {
	// Fields
	public static final Codec<WantedPosterData> CODEC;
	private static final String[] WANTED_POSTER_BACKGROUNDS;
	private static final DecimalFormat DECIMAL_FORMAT;
	private static final SimpleDateFormat DATE_FORMAT;

	public static WantedPosterData create(LivingEntity var0, long var1) {
		long var2 = ((long)(var0.hashCode())) + (var1);
		Random var3 = new Random(var2);
		UUID var4 = var0.getUUID();
		String var5 = var0.getName().getString();
		Optional var6 = Optional.empty();
		if (var0 instanceof Player){
			var5 = ((Player)(var0)).getGameProfile().getName();
		}else{
			if (var0 instanceof OPEntity){
				OPEntity var7 = ((OPEntity)(var0));
				ResourceLocation var8 = var7.getCurrentTexture();
				if ((var8) == (null)){
					var6 = Optional.ofNullable(var7.getCurrentTexture());
				}else{
					var6 = Optional.ofNullable(var8);
				}
			}
		}
		Optional var7_1 = Optional.empty();
		Crew var8_1 = FactionsWorldData.get().getCrewWithMember(var4);
		if ((var8_1) != (null)){
			var7_1 = Optional.ofNullable(var8_1.getJollyRoger());
		}
		Optional var9 = Optional.ofNullable(((Faction)(EntityStatsData.get(var0).flatMap((Function)(((Function<EntityStatsData, Optional>)(EntityStatsData::getFaction)))).orElse(null))));
		int var10 = WyHelper.randomWithRange(var3,0,(WANTED_POSTER_BACKGROUNDS.length) - (1));
		String var11 = WANTED_POSTER_BACKGROUNDS[var10];
		String var12 = DATE_FORMAT.format(new Date());
		return new WantedPosterData(var4,var5,var1,false,var11,var12,var6,var9,var7_1,Optional.empty());
	}
	public static WantedPosterData empty() {
		return new WantedPosterData(Util.NIL_UUID,"None",0L,true,"","",Optional.empty(),Optional.empty(),Optional.empty(),Optional.empty());
	}
	public static WantedPosterData from(CompoundTag var0) {
		if (var0.isEmpty()){
			WyDebug.warn("Wanted Poster Data created without any actual data!");
			return empty();
		}else{
			UUID var1 = var0.getUUID("UUID");
			String var2 = var0.getString("Name");
			Optional var3 = Optional.empty();
			if (var0.contains("Texture")){
				var3 = Optional.ofNullable(ResourceLocation.parse(var0.getString("Texture")));
			}
			Optional var4 = Optional.empty();
			if (var0.contains("TrackedPosition",10)){
				CompoundTag var5 = var0.getCompound("TrackedPosition");
				double var6 = var5.getDouble("X");
				double var7 = var5.getDouble("Y");
				double var8 = var5.getDouble("Z");
				var4 = Optional.ofNullable(new Vec3(var6,var7,var8));
			}
			Optional var5_1 = Optional.empty();
			if (var0.contains("JollyRoger",10)){
				var5_1 = Optional.ofNullable(JollyRoger.from(var0.getCompound("JollyRoger")));
			}
			long var6_1 = var0.getLong("Bounty");
			boolean var7_1 = var0.getBoolean("IsExpired");
			Optional var8_1 = Optional.empty();
			if (var0.contains("Faction")){
				var8_1 = Optional.ofNullable(((Faction)(WyRegistry.FACTIONS.get(ResourceLocation.parse(var0.getString("Faction"))))));
			}
			String var9 = var0.getString("Background");
			String var10 = var0.getString("Date");
			return new WantedPosterData(var1,var2,var6_1,var7_1,var9,var10,var3,var8_1,var5_1,var4);
		}
	}
	public boolean isExpired(Level var1) {
		FactionsWorldData var2 = FactionsWorldData.get();
		if ((var2) == (null)){
			WyDebug.warn("Checking for expiration on client side! Something has gone wrong.");
			return false;
		}else{
			Entity var3 = WyHelper.getLoadedEntity(var1,this.owner);
			if (((((var3) != (null)) && (var3 instanceof OPEntity)) ? (1) : (0)) != (0)){
				EventsWorldData var4 = EventsWorldData.get();
				if (((var4) != (null)) && (!(var4.hasNTEventFor(this.owner)))){
					return true;
				}
			}
			return (var2.getBounty(this.owner)) != (this.bounty);
		}
	}
	public WantedPosterData setExpired() {
		return new WantedPosterData(this.owner,this.ownerName,this.bounty,true,this.background,this.date,this.picture,this.faction,this.jollyRoger,this.trackedPosition);
	}
	public WantedPosterData setTrackedPosition(@Nullable() Vec3 var1) {
		return new WantedPosterData(this.owner,this.ownerName,this.bounty,true,this.background,this.date,this.picture,this.faction,this.jollyRoger,Optional.ofNullable(var1));
	}
	public String getBountyString() {
		return DECIMAL_FORMAT.format(this.bounty);
	}
	static  {
		Function<RecordCodecBuilder.Instance, App> var0 = (l0) -> {
			return l0.group((App)(UUIDUtil.CODEC.fieldOf("owner").forGetter((Function)(((Function<WantedPosterData, UUID>)(WantedPosterData::owner))))),(App)(Codec.STRING.fieldOf("ownerName").forGetter((Function)(((Function<WantedPosterData, String>)(WantedPosterData::ownerName))))),(App)(Codec.LONG.fieldOf("bounty").forGetter((Function)(((Function<WantedPosterData, Long>)(WantedPosterData::bounty))))),(App)(Codec.BOOL.fieldOf("isExpired").forGetter((Function)(((Function<WantedPosterData, Boolean>)(WantedPosterData::isExpired))))),(App)(Codec.STRING.fieldOf("background").forGetter((Function)(((Function<WantedPosterData, String>)(WantedPosterData::background))))),(App)(Codec.STRING.fieldOf("date").forGetter((Function)(((Function<WantedPosterData, String>)(WantedPosterData::date))))),(App)(ResourceLocation.CODEC.optionalFieldOf("picture").forGetter((Function)(((Function<WantedPosterData, Optional>)(WantedPosterData::picture))))),(App)(Faction.CODEC.optionalFieldOf("faction").forGetter((Function)(((Function<WantedPosterData, Optional>)(WantedPosterData::faction))))),(App)(JollyRoger.CODEC.optionalFieldOf("jollyRoger").forGetter((Function)(((Function<WantedPosterData, Optional>)(WantedPosterData::jollyRoger))))),(App)(Vec3.CODEC.optionalFieldOf("trackedPosition").forGetter((Function)(((Function<WantedPosterData, Optional>)(WantedPosterData::trackedPosition)))))).apply((Applicative)(l0),(Function10)(((com.mojang.datafixers.util.Function10)(WantedPosterData::new))));
		};
		CODEC = (Codec) (RecordCodecBuilder.create(var0));
		String[] var1 = new String[]{"forest1","forest2","jungle1","jungle2","hills1","hills2","hills3","plains1","plains2","plains3","taiga1","taiga2"};
		WANTED_POSTER_BACKGROUNDS = var1;
		DECIMAL_FORMAT = new DecimalFormat("#,##0");
		DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	}

	public CompoundTag writeTag() {
		CompoundTag var1 = new CompoundTag();
		if ((this.owner) == (null)){
			return var1;
		}else{
			var1.putUUID("UUID",this.owner);
			var1.putString("Name",this.ownerName);
			var1.putLong("Bounty",this.bounty);
			var1.putBoolean("IsExpired",this.isExpired);
			CompoundTag var2 = var1;
			final CompoundTag var2_f1 = var2;
			Consumer<? super Faction> var3 = (l0) -> {
			var2_f1.putString("Faction",l0.getRegistryName().toString());
		};
			((java.util.Optional)(this.faction)).ifPresent((Consumer)(var3));
			var1.putString("Background",this.background);
			var1.putString("Date",this.date);
			if (this.picture.isPresent()){
				var1.putString("Texture",((ResourceLocation)(this.picture.get())).toString());
			}
			if (this.trackedPosition.isPresent()){
				CompoundTag var4 = new CompoundTag();
				var4.putDouble("X",((Vec3)(this.trackedPosition.get())).x);
				var4.putDouble("Y",((Vec3)(this.trackedPosition.get())).y);
				var4.putDouble("Z",((Vec3)(this.trackedPosition.get())).z);
				var1.put("TrackedPosition",(Tag)(var4));
			}
			if (this.jollyRoger.isPresent()){
				var1.put("JollyRoger",(Tag)(((JollyRoger)(this.jollyRoger.get())).write()));
			}
			return var1;
		}
	}}