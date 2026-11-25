package xyz.pixelatedw.mineminenomi.api.challenges;

import com.google.common.collect.HashMultimap;
import com.mojang.datafixers.Products;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;

public class ChallengeCore<T extends Challenge> {
	// Fields
	public static final Codec<ChallengeCore<?>> CODEC;
	private ResourceLocation key;
	 String id;
	private @Nullable() Component title;
	final String unlocalizedTitle;
	private Component objective;
	final String unlocalizedObjective;
	final String category;
	 int timeLimit;
	 ResourceKey<LootTable> rewards;
	 ChallengeDifficulty difficulty;
	 int difficultyStars;
	 float rewardsFactor;
	 HashMultimap<ArenaStyle, ChallengeCore$ArenaEntry> arenas;
	 ChallengeCore$ITargetShowcase[] targetShowcase;
	private boolean isTargetCacheDirty;
	private LivingEntity[] targetsCache;
	 String[] bannedFactions;
	final ChallengeCore$IFactory<T> factory;
	 ChallengeCore$ICanStart startCheck;
	 ChallengeCore$IEnemySpawns enemySpawns;
	private @Nullable() String titleI18nId;
	private @Nullable() String objectiveI18nId;
	 int order;
	 Supplier<? extends ChallengeCore<?>> hardVersion;

	ChallengeCore(String var1, String var2, String var3, String var4, ChallengeCore$IFactory<T> var5) {
		this.arenas = (HashMultimap) (HashMultimap.create());
		this.isTargetCacheDirty = false;
		this.targetsCache = new LivingEntity[4];
		this.id = var1;
		this.unlocalizedTitle = var2;
		this.unlocalizedObjective = var3;
		this.category = var4;
		this.factory = var5;
	}
	@Nullable()
	public @Nullable() T createChallenge() {
		return (T) (((xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore$IFactory)(this.factory)).create((ChallengeCore)(this)));
	}
	public void setHardVersion(Supplier<? extends ChallengeCore<?>> var1) {
		this.hardVersion = var1;
	}
	@Nullable()
	public @Nullable() ChallengeCore<?> getHardVersion() {
		if ((this.hardVersion) == (null)){
			return (ChallengeCore<?>) (ChallengeCore) (null);
		}else{
			return (ChallengeCore<?>) (ChallengeCore) (((ChallengeCore)(this.hardVersion.get())));
		}
	}
	private void setTargetShowcase(ChallengeCore$ITargetShowcase... var1) {
		this.targetShowcase = var1;
		this.isTargetCacheDirty = true;
	}
	private void setRewardFactor(float var1) {
		this.rewardsFactor = var1;
	}
	private void setRewards(ResourceKey<LootTable> var1) {
		this.rewards = var1;
	}
	private void setArenas(HashMultimap<ArenaStyle, ChallengeCore$ArenaEntry> var1) {
		this.arenas = var1;
	}
	private void setDifficulty(ChallengeDifficulty var1) {
		this.difficulty = var1;
	}
	private void setDifficultyStars(int var1) {
		this.difficultyStars = var1;
	}
	private void setTimeLimit(int var1) {
		this.timeLimit = var1;
	}
	public void setBannedFactions(String... var1) {
		this.bannedFactions = var1;
	}
	public void setStartCheck(ChallengeCore$ICanStart var1) {
		this.startCheck = var1;
	}
	public void setEnemySpawns(ChallengeCore$IEnemySpawns var1) {
		this.enemySpawns = var1;
	}
	public String getId() {
		return this.id;
	}
	public float getRewardsFactor() {
		return this.rewardsFactor;
	}
	public ResourceKey<LootTable> getRewards() {
		return this.rewards;
	}
	public Component getLocalizedTitle() {
		if ((this.title) == (null)){
			this.title = Component.translatable(this.getUnlocalizedTitle());
		}
		return this.title;
	}
	public Component getLocalizedObjective() {
		if ((this.objective) == (null)){
			this.objective = Component.translatable(this.getUnlocalizedObjective());
		}
		return this.objective;
	}
	private String getTitleLocalizationId() {
		if ((this.titleI18nId) == (null)){
			ResourceLocation var1 = WyRegistry.CHALLENGES.getKey(this);
			if ((var1) != (null)){
				this.titleI18nId = Util.makeDescriptionId("challenge",var1);
			}
		}
		return this.titleI18nId;
	}
	private String getObjectiveLocalizationId() {
		if ((this.titleI18nId) == (null)){
			ResourceLocation var1 = WyRegistry.CHALLENGES.getKey(this);
			if ((var1) != (null)){
				String var2 = Util.makeDescriptionId("challenge",var1);
				String var3 = var2 + ".objective";
				this.objectiveI18nId = var3;
			}
		}
		return this.objectiveI18nId;
	}
	public LivingEntity[] getTargetShowcase(Level var1) {
		if ((this.isTargetCacheDirty) && ((this.targetShowcase) != (null))){
			int var2 = 0;
			ChallengeCore$ITargetShowcase[] var3 = this.targetShowcase;
			int var4 = var3.length;
			int var5 = 0;
			do{
				if ((var5) < (var4)){
					ChallengeCore$ITargetShowcase var6 = var3[var5];
					this.targetsCache[var2] = var6.createShowcase(var1);
					var2++;
					var5++;
					continue;
				}else{
					break;
				}
			} while (true);
			this.isTargetCacheDirty = false;
		}
		return this.targetsCache;
	}
	@Nullable()
	public @Nullable() ChallengeCore$ArenaEntry getArenaFromStyle(ArenaStyle var1, String var2) {
		Iterator var3 = this.arenas.get(var1).iterator();
		do{
			if (var3.hasNext()){
				ChallengeCore$ArenaEntry var4 = ((ChallengeCore$ArenaEntry)(var3.next()));
				if (var4.arena.getClass().getName().equals(var2)){
					return var4;
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
	public @Nullable() ChallengeCore$ArenaEntry pickRandomArena() {
		ArenaStyle var1 = ArenaStyle.SIMPLE;
		Iterator var2 = this.arenas.get(var1).iterator();
		if (var2.hasNext()){
			return ((ChallengeCore$ArenaEntry)(var2.next()));
		}else{
			return null;
		}
	}
	private void setOrder(int var1) {
		this.order = var1;
	}
	public int getOrder() {
		return this.order;
	}
	public ChallengeDifficulty getDifficulty() {
		return this.difficulty;
	}
	public int getDifficultyStars() {
		return this.difficultyStars;
	}
	public String getUnlocalizedTitle() {
		return this.unlocalizedTitle;
	}
	public String getUnlocalizedObjective() {
		return this.unlocalizedObjective;
	}
	public ChallengeCore$IFactory<? extends T> getFactory() {
		return (ChallengeCore$IFactory<? extends T>) (ChallengeCore$IFactory) (this.factory);
	}
	public Component getObjective() {
		return this.objective;
	}
	public String getCategory() {
		return this.category;
	}
	public int getTimeLimit() {
		return this.timeLimit;
	}
	public String[] getBannedFactions() {
		return this.bannedFactions;
	}
	public ChallengeCore$ICanStart getStartCheck() {
		return this.startCheck;
	}
	public ChallengeCore$IEnemySpawns getEnemySpawns() {
		return this.enemySpawns;
	}
	@Nullable()
	public @Nullable() ResourceLocation getRegistryKey() {
		if ((this.key) == (null)){
			this.key = WyRegistry.CHALLENGES.getKey(this);
		}
		return this.key;
	}
	static  {
		Function<RecordCodecBuilder.Instance, App> var0 = (l0) -> {
			Function lv3_1 = (Function) ((Function<ChallengeCore, ResourceLocation>) ((l2_0) -> {
				return l2_0.key;
			}));
			Function lv3_2 = (Function) ((Function<ResourceLocation, ChallengeCore>) ((l2_0) -> {
				return ((ChallengeCore)(WyRegistry.CHALLENGES.get(l2_0)));
			}));
			return l0.group((App)(ResourceLocation.CODEC.fieldOf("id").forGetter(lv3_1))).apply((Applicative)(l0),lv3_2);
		};
		CODEC = (Codec) (RecordCodecBuilder.create(var0));
	}
	final void jdec$private$78797a2f706978656c61746564772f6d696e656d696e656e6f6d692f6170692f6368616c6c656e6765732f4368616c6c656e6765436f7265$0(HashMultimap arg0) { ((ChallengeCore)this).setArenas(arg0); }

	final void jdec$private$78797a2f706978656c61746564772f6d696e656d696e656e6f6d692f6170692f6368616c6c656e6765732f4368616c6c656e6765436f7265$1(ChallengeDifficulty arg0) { ((ChallengeCore)this).setDifficulty(arg0); }

	final void jdec$private$78797a2f706978656c61746564772f6d696e656d696e656e6f6d692f6170692f6368616c6c656e6765732f4368616c6c656e6765436f7265$2(int arg0) { ((ChallengeCore)this).setDifficultyStars(arg0); }

	final void jdec$private$78797a2f706978656c61746564772f6d696e656d696e656e6f6d692f6170692f6368616c6c656e6765732f4368616c6c656e6765436f7265$3(int arg0) { ((ChallengeCore)this).setOrder(arg0); }

	final void jdec$private$78797a2f706978656c61746564772f6d696e656d696e656e6f6d692f6170692f6368616c6c656e6765732f4368616c6c656e6765436f7265$4(float arg0) { ((ChallengeCore)this).setRewardFactor(arg0); }

	final void jdec$private$78797a2f706978656c61746564772f6d696e656d696e656e6f6d692f6170692f6368616c6c656e6765732f4368616c6c656e6765436f7265$5(ResourceKey arg0) { ((ChallengeCore)this).setRewards(arg0); }

	final void jdec$private$78797a2f706978656c61746564772f6d696e656d696e656e6f6d692f6170692f6368616c6c656e6765732f4368616c6c656e6765436f7265$6(ChallengeCore$ITargetShowcase[] arg0) { ((ChallengeCore)this).setTargetShowcase(arg0); }

	final void jdec$private$78797a2f706978656c61746564772f6d696e656d696e656e6f6d692f6170692f6368616c6c656e6765732f4368616c6c656e6765436f7265$7(int arg0) { ((ChallengeCore)this).setTimeLimit(arg0); }

}