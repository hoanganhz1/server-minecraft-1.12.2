package xyz.pixelatedw.mineminenomi.api.protection;

import com.google.common.base.Strings;
import com.mojang.datafixers.util.Function4;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.commons.lang3.RandomStringUtils;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.helpers.NBTHelper;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.world.ProtectedAreasData;

public class ProtectedArea {
	// Fields
	public static final int MAX_SIZE = 59999999;
	public static final StreamCodec<FriendlyByteBuf, ProtectedArea> STREAM_CODEC;
	private BlockPos centerPos;
	private int size;
	private String label;
	private boolean allowBlockDestruction;
	private boolean allowEntityDamage;
	private boolean allowPlayerDamage;
	private boolean allowAbilities;
	private boolean allowBlockRestoration;
	private boolean allowStatLoss;
	private boolean allowDeath;
	private boolean allowMobSpawns;
	private boolean isDisabled;
	private int restoreInterval;
	private int restoreAmount;
	private int restoreDistance;
	private int unconsciousTime;
	private int restoreTick;
	private Map<BlockPos, ProtectedArea$RestorationEntry> restoreBlocks;

	ProtectedArea() {
		this.label = "";
		this.allowBlockDestruction = false;
		this.allowEntityDamage = false;
		this.allowPlayerDamage = false;
		this.allowAbilities = false;
		this.allowBlockRestoration = false;
		this.allowStatLoss = true;
		this.allowDeath = true;
		this.allowMobSpawns = true;
		this.isDisabled = false;
		this.restoreInterval = 20;
		this.restoreAmount = 15;
		this.restoreDistance = 10;
		this.unconsciousTime = 40;
		this.restoreBlocks = new HashMap();
	}
	public ProtectedArea(BlockPos var1, int var2, String var3) {
		this(var1,var2,var3,false);
	}
	public ProtectedArea(BlockPos var1, int var2, String var3, boolean var4) {
		this.label = "";
		this.allowBlockDestruction = false;
		this.allowEntityDamage = false;
		this.allowPlayerDamage = false;
		this.allowAbilities = false;
		this.allowBlockRestoration = false;
		this.allowStatLoss = true;
		this.allowDeath = true;
		this.allowMobSpawns = true;
		this.isDisabled = false;
		this.restoreInterval = 20;
		this.restoreAmount = 15;
		this.restoreDistance = 10;
		this.unconsciousTime = 40;
		this.restoreBlocks = new HashMap();
		this.centerPos = var1;
		this.setLabel(var3);
		this.size = var2;
		this.isDisabled = var4;
	}
	public static ProtectedArea from(ServerLevel var0, CompoundTag var1) {
		ProtectedArea var2 = new ProtectedArea();
		var2.load(var0,var1);
		var2.setLabel(var2.getLabel());
		return var2;
	}
	public ProtectedArea setBlockDestruction(boolean var1) {
		this.allowBlockDestruction = var1;
		return this;
	}
	public boolean canDestroyBlocks() {
		return this.allowBlockDestruction;
	}
	public ProtectedArea setEntityDamage(boolean var1) {
		this.allowEntityDamage = var1;
		return this;
	}
	public boolean canHurtEntities() {
		return this.allowEntityDamage;
	}
	public ProtectedArea setPlayerDamage(boolean var1) {
		this.allowPlayerDamage = var1;
		return this;
	}
	public boolean canHurtPlayers() {
		return this.allowPlayerDamage;
	}
	public ProtectedArea setBlockRestoration(boolean var1) {
		this.allowBlockRestoration = var1;
		return this;
	}
	public boolean canRestoreBlocks() {
		return this.allowBlockRestoration;
	}
	public ProtectedArea setStatLoss(boolean var1) {
		this.allowStatLoss = var1;
		return this;
	}
	public boolean canLoseStats() {
		return this.allowStatLoss;
	}
	public ProtectedArea setDeath(boolean var1) {
		this.allowDeath = var1;
		return this;
	}
	public boolean canDie() {
		return this.allowDeath;
	}
	public ProtectedArea setMobSpawns(boolean var1) {
		this.allowMobSpawns = var1;
		return this;
	}
	public boolean canMobsSpawn() {
		return this.allowMobSpawns;
	}
	public ProtectedArea setDisabled(boolean var1) {
		this.isDisabled = var1;
		return this;
	}
	public boolean isDisabled() {
		return this.isDisabled;
	}
	public ProtectedArea setAbilitiesUsage(boolean var1) {
		this.allowAbilities = var1;
		return this;
	}
	public boolean canAbilitiesBeUsed() {
		return this.allowAbilities;
	}
	public boolean canUseAbility(AbilityCore<?> var1) {
		if (!(this.allowAbilities)){
			if (ServerConfig.isAbilityProtectionWhitelisted(var1)){
				return true;
			}else{
				return false;
			}
		}else{
			return true;
		}
	}
	public ProtectedArea setRestoreInterval(int var1) {
		this.restoreInterval = var1;
		return this;
	}
	public int getRestoreInterval() {
		return this.restoreInterval;
	}
	public ProtectedArea setRestoreAmount(int var1) {
		this.restoreAmount = var1;
		return this;
	}
	public int getRestoreAmount() {
		return this.restoreAmount;
	}
	public ProtectedArea setRestoreDistance(int var1) {
		this.restoreDistance = var1;
		return this;
	}
	public int getRestoreDistance() {
		return this.restoreDistance;
	}
	public ProtectedArea setUnconsciousTime(int var1) {
		this.unconsciousTime = var1;
		return this;
	}
	public int getUnconsciousTime() {
		return this.unconsciousTime;
	}
	public void queueForRestoration(Level var1, BlockPos var2) {
		this.queueForRestoration(var2,new ProtectedArea$RestorationEntry((Level)(var1),(BlockPos)(var2)));
	}
	public void queueForRestoration(BlockPos var1, ProtectedArea$RestorationEntry var2) {
		this.restoreBlocks.putIfAbsent(var1,var2);
	}
	
	public Map<BlockPos, ProtectedArea$RestorationEntry> getRestorationMap() {
		return this.restoreBlocks;
	}
	public void restoreBlocks(Level var1) {
		if (((this.getRestorationMap().size()) > (0)) && (this.canRestoreBlocks())){
			if (((this.getRestoreInterval()) > (0)) && ((this.restoreTick--) > (0))){
				return;
			}else{
				Iterator var2 = this.getRestorationMap().entrySet().iterator();
				int var3 = this.restoreAmount;
				LOOP_1:
				do{
					if (((var3) > (0)) && (var2.hasNext())){
						Map.Entry var4 = ((Map.Entry)(var2.next()));
						if ((var1.getGameTime()) < ((((ProtectedArea$RestorationEntry)(var4.getValue())).getTimestamp()) + (ServerConfig.getGlobalProtectionGraceTime()))){
							continue;
						}else{
							BlockPos var5 = ((BlockPos)(var4.getKey()));
							if ((this.restoreDistance) > (0)){
								boolean var6 = false;
								Iterator var7 = var1.players().iterator();
								do{
									if (var7.hasNext()){
										if (((Player)(var7.next())).blockPosition().closerThan((Vec3i)(var5),(double)(this.restoreDistance))){
											var6 = true;
											break;
										}else{
											continue;
										}
									}else{
										break;
									}
								} while (true);
								if (var6){
									continue LOOP_1;
								}
							}
							var1.setBlock(var5,((ProtectedArea$RestorationEntry)(var4.getValue())).getBlock(),3);
							CompoundTag var6_1 = ((ProtectedArea$RestorationEntry)(var4.getValue())).getTag();
							if ((var6_1) != (null)){
								BlockEntity var8 = var1.getBlockEntity(var5);
								if ((var8) != (null)){
									var8.loadWithComponents(var6_1,(HolderLookup.Provider)(var1.registryAccess()));
									var8.setChanged();
								}
							}
							var2.remove();
							var3--;
							continue;
						}
					}else{
						break;
					}
				} while (true);
				if (var1 instanceof ServerLevel){
					ProtectedAreasData.get(((ServerLevel)(var1))).setDirty();
				}
				return;
			}
		}else{
			return;
		}
	}
	public void setLabel(String var1) {
		this.label = this.checkValidLabel(var1);
	}
	public String getLabel() {
		return WyHelper.getResourceName(this.label);
	}
	public BlockPos getCenter() {
		return this.centerPos;
	}
	public int getSize() {
		return this.size;
	}
	public void setSize(int var1) {
		this.size = var1;
	}
	public boolean isInside(Level var1, int var2, int var3, int var4) {
		if (this.isDisabled){
			return false;
		}else{
			int var5 = (this.getSize()) + (1);
			if (((var2) < ((this.getCenter().getX()) + (var5))) && (((var2) > ((this.getCenter().getX()) - (var5))) && (((var3) < ((this.getCenter().getY()) + (var5))) && (((var3) > ((this.getCenter().getY()) - (var5))) && (((var4) < ((this.getCenter().getZ()) + (var5))) && ((var4) > ((this.getCenter().getZ()) - (var5)))))))){
				return true;
			}else{
				return false;
			}
		}
	}
	private String checkValidLabel(String var1) {
		if (Strings.isNullOrEmpty(var1)){
			return RandomStringUtils.randomAlphabetic(5);
		}else{
			return WyHelper.getResourceName(var1);
		}
	}
	public CompoundTag save() {
		CompoundTag var1 = new CompoundTag();
		var1.putString("label",this.label);
		var1.putInt("x",this.centerPos.getX());
		var1.putInt("y",this.centerPos.getY());
		var1.putInt("z",this.centerPos.getZ());
		var1.putInt("size",this.size);
		var1.putBoolean("allowBlockDestruction",this.allowBlockDestruction);
		var1.putBoolean("allowEntityDamage",this.allowEntityDamage);
		var1.putBoolean("allowPlayerDamage",this.allowPlayerDamage);
		var1.putBoolean("allowBlockRestoration",this.allowBlockRestoration);
		var1.putBoolean("allowAbilities",this.allowAbilities);
		var1.putBoolean("allowStatLoss",this.allowStatLoss);
		var1.putBoolean("allowDeath",this.allowDeath);
		var1.putBoolean("allowMobSpawns",this.allowMobSpawns);
		var1.putBoolean("isDisabled",this.isDisabled);
		var1.putInt("restoreInterval",this.restoreInterval);
		var1.putInt("restoreAmount",this.restoreAmount);
		var1.putInt("restoreDistance",this.restoreDistance);
		var1.putInt("unconsciousTime",this.unconsciousTime);
		NBTHelper.saveBlockSnapshotMap(var1,this.restoreBlocks);
		return var1;
	}
	public void load(ServerLevel var1, CompoundTag var2) {
		this.label = var2.getString("label");
		int var3 = var2.getInt("x");
		int var4 = var2.getInt("y");
		int var5 = var2.getInt("z");
		this.centerPos = new BlockPos(var3,var4,var5);
		this.size = var2.getInt("size");
		this.allowBlockDestruction = var2.getBoolean("allowBlockDestruction");
		this.allowEntityDamage = var2.getBoolean("allowEntityDamage");
		this.allowPlayerDamage = var2.getBoolean("allowPlayerDamage");
		this.allowBlockRestoration = var2.getBoolean("allowBlockRestoration");
		this.allowAbilities = var2.getBoolean("allowAbilities");
		this.allowStatLoss = var2.getBoolean("allowStatLoss");
		this.allowDeath = var2.getBoolean("allowDeath");
		this.allowMobSpawns = var2.getBoolean("allowMobSpawns");
		this.isDisabled = var2.getBoolean("isDisabled");
		this.restoreInterval = var2.getInt("restoreInterval");
		this.restoreAmount = var2.getInt("restoreAmount");
		this.restoreDistance = var2.getInt("restoreDistance");
		this.unconsciousTime = var2.getInt("unconsciousTime");
		this.restoreBlocks.clear();
		NBTHelper.loadBlockSnapshotMap((Level)(var1),var2,this.restoreBlocks);
	}
	static  {
		Function<ProtectedArea, BlockPos> var0 = (l0) -> {
			return l0.centerPos;
		};
		Function<ProtectedArea, Integer> var1 = (l0) -> {
			return Integer.valueOf(l0.size);
		};
		Function<ProtectedArea, String> var2 = (l0) -> {
			return l0.label;
		};
		Function<ProtectedArea, Boolean> var3 = (l0) -> {
			return Boolean.valueOf(l0.isDisabled);
		};
		STREAM_CODEC = (StreamCodec) (StreamCodec.composite(BlockPos.STREAM_CODEC,var0,ByteBufCodecs.INT,var1,ByteBufCodecs.STRING_UTF8,var2,ByteBufCodecs.BOOL,var3,(Function4)(((com.mojang.datafixers.util.Function4)(ProtectedArea::new)))));
	}
public void queueForRestoration(Map<BlockPos, ProtectedArea$RestorationEntry> var1) {
		var1.entrySet().removeIf((Predicate)(((java.util.function.Predicate)((Predicate) ((Predicate<Map.Entry>) ((l0) -> {
			return this.restoreBlocks.containsKey(l0.getKey());
		}))))));
		((java.util.Map)(this.restoreBlocks)).putAll((Map)(var1));
	}}