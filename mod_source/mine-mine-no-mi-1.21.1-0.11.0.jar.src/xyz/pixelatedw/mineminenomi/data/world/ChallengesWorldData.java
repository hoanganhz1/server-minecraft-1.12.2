package xyz.pixelatedw.mineminenomi.data.world;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.UnmodifiableIterator;
import com.mojang.authlib.GameProfile;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.challenges.Challenge;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.ChallengeData;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nChallenges;
import xyz.pixelatedw.mineminenomi.world.DynamicDimensionManager;

public class ChallengesWorldData extends SavedData {
	// Fields
	private static final String IDENTIFIER = "mineminenomi-challenges";
	private static final ChallengesWorldData INSTANCE = initWorldData();
	private Map<UUID, InProgressChallenge> inProgressChallenges;
	private Map<Integer, InProgressChallenge> inProgressChallengesHashCache;
	private Map<UUID, CompoundTag> challengerCache;

	public ChallengesWorldData() {
		this.inProgressChallenges = new HashMap();
		this.inProgressChallengesHashCache = new HashMap();
		this.challengerCache = new HashMap();
	}
	private static ChallengesWorldData initWorldData() {
		Supplier<ChallengesWorldData> var0 = ChallengesWorldData::new;
		return ((ChallengesWorldData)(ServerLifecycleHooks.getCurrentServer().overworld().getDataStorage().computeIfAbsent(new SavedData.Factory(var0,(BiFunction)(((BiFunction<CompoundTag, HolderLookup.Provider, ChallengesWorldData>)(ChallengesWorldData::load)))),"mineminenomi-challenges")));
	}
	public static ChallengesWorldData get() {
		return INSTANCE;
	}
	public static ChallengesWorldData load(CompoundTag var0, HolderLookup.Provider var1) {
		ChallengesWorldData var2 = new ChallengesWorldData();
		var2.challengerCache.clear();
		ListTag var3 = var0.getList("cache",10);
		int var4 = 0;
		do{
			if ((var4) < (var3.size())){
				CompoundTag var5 = var3.getCompound(var4);
				UUID var6 = var5.getUUID("id");
				var2.challengerCache.put(var6,var5.getCompound("data"));
				var4++;
				continue;
			}else{
				break;
			}
		} while (true);
		return var2;
	}
	public CompoundTag save(CompoundTag var1, HolderLookup.Provider var2) {
		ListTag var3 = new ListTag();
		Iterator var4 = this.challengerCache.entrySet().iterator();
		do{
			if (var4.hasNext()){
				Map.Entry var5 = ((Map.Entry)(var4.next()));
				CompoundTag var6 = new CompoundTag();
				var6.putUUID("id",((UUID)(var5.getKey())));
				var6.put("data",((Tag)(var5.getValue())));
				var3.add(var6);
				continue;
			}else{
				break;
			}
		} while (true);
		var1.put("cache",(Tag)(var3));
		return var1;
	}
	public boolean startChallenge(ServerPlayer var1, @Nullable() List<LivingEntity> var2, ChallengeCore<?> var3, boolean var4) {
		ServerPlayer var16 = null;
		List<LivingEntity> var17 = null;
		Challenge var18 = null;
		if (!(ServerConfig.isChallengesEnabled())){
			return false;
		}else{
			ChallengeData var5 = ((ChallengeData)(((java.util.Optional)(ChallengeData.get((Player)(var1)))).orElse((Object)(null))));
			if ((var5) != (null)){
				Challenge var6 = var5.getChallenge(var3);
				if ((var6) == (null)){
					Object[] var7 = new Object[]{var3.getLocalizedTitle()};
					WyHelper.sendMessage((LivingEntity)(var1),(Component)(Component.translatable(ModI18nChallenges.MESSAGE_NOT_UNLOCKED,var7)));
					return false;
				}else{
					String var7_1 = var1.getGameProfile().getName();
					String var8 = var6.getCore().getId().toUpperCase();
					String var9 = var7_1 + " started " + var8 + " challenge.";
					WyDebug.info(var9);
					long var10 = System.nanoTime();
					String var11 = var1.getStringUUID();
					String var12 = "challenges_" + var11;
					ResourceLocation var13 = ResourceLocation.fromNamespaceAndPath("mineminenomi",var12);
					ResourceKey var14 = ResourceKey.create(Registries.DIMENSION,var13);
					ChallengesWorldData var15 = this;
					var16 = var1;
					var17 = var2;
					var18 = var6;
					boolean var19 = var4;
					final ServerPlayer var16_f1 = var16;
					final List<LivingEntity> var17_f2 = var17;
					final Challenge var18_f3 = var18;
					final boolean var19_f1 = var19;
					Consumer<ServerLevel> var20 = (l0) -> {
			UUID lv1_6 = var16_f1.getUUID();
			InProgressChallenge lv1_7 = new InProgressChallenge(lv1_6,var16_f1,l0,var17_f2,var18_f3,var19_f1);
			if (this.inProgressChallenges.containsKey(lv1_6)){
				this.stopChallenge(lv1_7);
			}
			this.inProgressChallenges.put(lv1_6,lv1_7);
			Iterator lv1_8 = lv1_7.getGroupCache().entrySet().iterator();
			do{
				if (lv1_8.hasNext()){
					Map.Entry lv1_9 = ((Map.Entry)(lv1_8.next()));
					this.challengerCache.put(((UUID)(lv1_9.getKey())),((CompoundTag)(lv1_9.getValue())));
					continue;
				}else{
					break;
				}
			} while (true);
			this.setDirty();
		};
					DynamicDimensionManager.INSTANCE.queueLevelForRegistration(var14,var20);
					long var21 = ((System.nanoTime()) - (var10)) / (1000000L);
					long var22 = var21;
					String var23 = "Dimension setup: " + var22 + "ms";
					WyDebug.debug(var23);
					return true;
				}
			}else{
				return false;
			}
		}
	}
	public void stopChallenge(InProgressChallenge var1) {
		ChallengeData var2 = ((ChallengeData)(((java.util.Optional)(ChallengeData.get((Player)(var1.getOwner())))).orElse((Object)(null))));
		if ((var2) != (null)){
			UnmodifiableIterator var3 = var2.getGroupMembersIds().iterator();
			do{
				if (var3.hasNext()){
					UUID var4 = ((UUID)(var3.next()));
					ServerPlayer var5 = var1.getShard().getServer().getPlayerList().getPlayer(var4);
					if ((var5) == (null)){
						continue;
					}else{
						Consumer<ChallengeData> var6 = (l0) -> {
			l0.setInGroup((UUID)(null));
		};
						((java.util.Optional)(ChallengeData.get((Player)(var5)))).ifPresent((Consumer)(var6));
						var2.removeGroupMember(var5.getUUID());
						continue;
					}
				}else{
					break;
				}
			} while (true);
			var2.setInGroup((UUID)(null));
			var2.removeGroupMember(var1.getOwner().getUUID());
		}
		String var3_1 = var1.getOwner().getGameProfile().getName();
		String var4_1 = var3_1 + "'s challenge stopped.";
		WyDebug.info(var4_1);
		Iterator var5_1 = var1.getGroupCache().keySet().iterator();
		do{
			if (var5_1.hasNext()){
				UUID var6_1 = ((UUID)(var5_1.next()));
				this.challengerCache.remove(var6_1);
				continue;
			}else{
				break;
			}
		} while (true);
		this.inProgressChallenges.remove(var1.getId());
		this.inProgressChallengesHashCache.remove(Integer.valueOf(((java.lang.Object)(var1.getShard())).hashCode()));
		this.setDirty();
	}
	@Nullable()
	public @Nullable() InProgressChallenge getInProgressChallengeFor(LivingEntity var1) {
		UUID var2 = var1.getUUID();
		return ((InProgressChallenge)(this.inProgressChallenges.get(var2)));
	}
	@Nullable()
	public @Nullable() InProgressChallenge getInProgressChallengeFor(ServerLevel var1) {
		if (this.inProgressChallengesHashCache.containsKey(Integer.valueOf(((java.lang.Object)(var1)).hashCode()))){
			return ((InProgressChallenge)(this.inProgressChallengesHashCache.get(Integer.valueOf(((java.lang.Object)(var1)).hashCode()))));
		}else{
			Iterator var2 = this.inProgressChallenges.values().iterator();
			do{
				if (var2.hasNext()){
					InProgressChallenge var3 = ((InProgressChallenge)(var2.next()));
					if (((java.lang.Object)(var3.getShard())).equals((Object)(var1))){
						this.inProgressChallengesHashCache.put(Integer.valueOf(((java.lang.Object)(var1)).hashCode()),var3);
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
	}
	public Optional<CompoundTag> getChallengerCache(UUID var1) {
		return Optional.ofNullable(((CompoundTag)(this.challengerCache.get(var1))));
	}
	public void removeChallengerCache(UUID var1) {
		this.challengerCache.remove(var1);
		this.setDirty();
	}
	public void tick(ServerLevel var1) {
		InProgressChallenge var2 = this.getInProgressChallengeFor(var1);
		if ((var2) == (null)){
			return;
		}else{
			if (var2.canDelete()){
				this.stopChallenge(var2);
				return;
			}else{
				var2.tick();
				return;
			}
		}
	}
}