package xyz.pixelatedw.mineminenomi.data.world;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.crew.Crew$Member;
import xyz.pixelatedw.mineminenomi.api.crew.JollyRoger;
import xyz.pixelatedw.mineminenomi.api.crew.JollyRogerElement;
import xyz.pixelatedw.mineminenomi.init.ModJollyRogers;

public class FactionsWorldData extends SavedData {
	// Fields
	private static final String IDENTIFIER = "mineminenomi-factions";
	private HashMap<String, Crew> pirateCrews;
	private HashMap<UUID, Long> issuedBounties;

	public FactionsWorldData() {
		this.pirateCrews = (HashMap) (new HashMap());
		this.issuedBounties = (HashMap) (new HashMap());
	}
	public static FactionsWorldData get() {
		ServerLevel var0 = ServerLifecycleHooks.getCurrentServer().overworld();
		Supplier<FactionsWorldData> var1 = FactionsWorldData::new;
		return ((FactionsWorldData)(var0.getDataStorage().computeIfAbsent(new SavedData.Factory(var1,(BiFunction)(((BiFunction<CompoundTag, HolderLookup.Provider, FactionsWorldData>)(FactionsWorldData::load)))),"mineminenomi-factions")));
	}
	public static FactionsWorldData load(CompoundTag var0, HolderLookup.Provider var1) {
		FactionsWorldData var2 = new FactionsWorldData();
		CompoundTag var3 = var0.getCompound("issuedBounties");
		var2.issuedBounties.clear();
		FactionsWorldData var4 = var2;
		CompoundTag var5 = var3;
		final FactionsWorldData var4_f1 = var4;
		final CompoundTag var5_f2 = var5;
		Consumer<String> var6 = (l0) -> {
			var4_f1.issuedBounties.put(UUID.fromString(l0),Long.valueOf(var5_f2.getLong(l0)));
		};
		((java.util.stream.Stream)(var3.getAllKeys().stream())).forEach((Consumer)(var6));
		ListTag var7 = var0.getList("crews",10);
		var2.pirateCrews.clear();
		int var8 = 0;
		do{
			if ((var8) < (var7.size())){
				Crew var9 = Crew.from(var7.getCompound(var8));
				var2.pirateCrews.put(WyHelper.getResourceName(var9.getName()),var9);
				var8++;
				continue;
			}else{
				break;
			}
		} while (true);
		return var2;
	}
	public CompoundTag save(CompoundTag var1, HolderLookup.Provider var2) {
		CompoundTag var3 = new CompoundTag();
		if ((this.issuedBounties.size()) > (0)){
			CompoundTag var4 = var3;
			final CompoundTag var4_f1 = var4;
			Consumer var5 = (Consumer) ((Consumer<Map.Entry>) ((l0) -> {
			var4_f1.putLong(((UUID)(l0.getKey())).toString(),((Long)(l0.getValue())).longValue());
		}));
			this.issuedBounties.entrySet().stream().forEach(var5);
		}
		var1.put("issuedBounties",(Tag)(var3));
		ListTag var4_1 = new ListTag();
		Iterator var5_1 = this.pirateCrews.values().iterator();
		do{
			if (var5_1.hasNext()){
				var4_1.add(((Crew)(var5_1.next())).write());
				continue;
			}else{
				break;
			}
		} while (true);
		var1.put("crews",(Tag)(var4_1));
		return var1;
	}
	public void tick(Level var1) {
		var1.getProfiler().push("crews");
		FactionsWorldData var2 = this;
		Consumer<? super Crew> var3 = (l0) -> {
			((Crew)(l0)).tick();
			if (((Crew)(l0)).isEmpty()){
				this.removeCrew((Crew)(l0));
			}
		};
		((java.lang.Iterable)(this.getCrews())).forEach((Consumer)(var3));
		var1.getProfiler().pop();
	}
	public List<Crew> getCrews() {
		return new ArrayList(this.pirateCrews.values());
	}
	@Nullable()
	public @Nullable() Crew getCrewWithMember(UUID var1) {
		Iterator var2 = this.pirateCrews.values().iterator();
		LOOP_1:
		do{
			if (var2.hasNext()){
				Crew var3 = ((Crew)(var2.next()));
				Iterator var4 = var3.getMembers().iterator();
				do{
					if (var4.hasNext()){
						if (((Crew$Member)(var4.next())).getUUID().equals(var1)){
							return var3;
						}else{
							continue;
						}
					}else{
						continue LOOP_1;
					}
				} while (true);
			}else{
				break;
			}
		} while (true);
		return null;
	}
	@Nullable()
	public @Nullable() Crew getCrewByName(String var1) {
		Iterator var2 = this.pirateCrews.values().iterator();
		do{
			if (var2.hasNext()){
				Crew var3 = ((Crew)(var2.next()));
				if (var3.getName().equals(var1)){
					return var3;
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
		return null;
	}
	@Nullable()
	public @Nullable() Crew getCrewWithCaptain(UUID var1) {
		UUID var2 = var1;
		final UUID var2_f1 = var2;
		Predicate<Crew> var3 = (l0) -> {
			return ((l0.getCaptain()) != (null)) && (l0.getCaptain().getUUID().equals(var2_f1));
		};
		return ((Crew)(((java.util.Optional)(((java.util.stream.Stream)(this.pirateCrews.values().stream())).filter((Predicate)(var3)).findFirst())).orElse((Object)(null))));
	}
	public void removeCrew(Crew var1) {
		String var2 = WyHelper.getResourceName(var1.getName());
		if (this.pirateCrews.containsKey(var2)){
			this.pirateCrews.remove(var2);
		}
		this.setDirty();
	}
	public void addCrew(Crew var1) {
		String var2 = WyHelper.getResourceName(var1.getName());
		if (!(this.pirateCrews.containsKey(var2))){
			this.pirateCrews.put(var2,var1);
		}
		this.setDirty();
	}
	public void removeCrewMember(Level var1, Crew var2, UUID var3) {
		var2.removeMember(var1,var3);
		this.setDirty();
	}
	public void addCrewMember(Crew var1, LivingEntity var2) {
		this.addCrewMember(var1,var2,true);
	}
	public void addTemporaryCrewMember(Crew var1, LivingEntity var2) {
		this.addCrewMember(var1,var2,false);
	}
	public void addCrewMember(Crew var1, LivingEntity var2, boolean var3) {
		String var4 = WyHelper.getResourceName(var1.getName());
		if (!(this.pirateCrews.containsKey(var4))){
			this.pirateCrews.put(var4,var1);
		}else{
			var1 = ((Crew)(this.pirateCrews.get(var4)));
		}
		var1.addMember(var2,var3);
		this.setDirty();
	}
	public void updateCrewJollyRoger(ServerPlayer var1, Crew var2, JollyRoger var3) {
		if (((var3.getBase()) != (null)) && (!(var3.getBase().canUse((Player)(var1),var2)))){
			var3.setBase(((JollyRogerElement)(ModJollyRogers.BASE_SKULL.get())));
		}
		int var4 = 0;
		do{
			if ((var4) < (var3.getBackgrounds().length)){
				JollyRogerElement var5 = var3.getBackgrounds()[var4];
				if (((var5) != (null)) && (!(var5.canUse((Player)(var1),var2)))){
					var3.setBackground(var4,(JollyRogerElement)(null));
				}
				var4++;
				continue;
			}else{
				break;
			}
		} while (true);
		var4 = 0;
		do{
			if ((var4) < (var3.getDetails().length)){
				JollyRogerElement var5_1 = var3.getDetails()[var4];
				if (((var5_1) != (null)) && (!(var5_1.canUse((Player)(var1),var2)))){
					var3.setDetail(var4,var5_1);
				}
				var4++;
				continue;
			}else{
				break;
			}
		} while (true);
		var2.setJollyRoger(var3);
		this.setDirty();
	}
	public HashMap<UUID, Long> getAllBounties() {
		return this.issuedBounties;
	}
	public Object[] getRandomBounty() {
		int var1 = this.getAllBounties().size();
		if ((var1) <= (0)){
			return null;
		}else{
			Object var2 = this.getAllBounties().keySet().toArray()[new Random().nextInt(var1)];
			long var3 = ((Long)(this.getAllBounties().get(var2))).longValue();
			Object[] var4 = new Object[]{var2,Long.valueOf(var3)};
			return var4;
		}
	}
	public long getBounty(UUID var1) {
		if (this.issuedBounties.containsKey(var1)){
			return ((Long)(this.issuedBounties.get(var1))).longValue();
		}else{
			return 0L;
		}
	}
	public boolean hasIssuedBounty(LivingEntity var1) {
		return this.issuedBounties.containsKey(var1.getUUID());
	}
	public void removeBounty(UUID var1) {
		if (this.issuedBounties.containsKey(var1)){
			this.issuedBounties.remove(var1);
		}
	}
	public void issueBounty(UUID var1, long var2) {
		if (this.issuedBounties.containsKey(var1)){
			this.issuedBounties.remove(var1);
			this.issuedBounties.put(var1,Long.valueOf(var2));
		}else{
			this.issuedBounties.put(var1,Long.valueOf(var2));
		}
		this.setDirty();
	}
}