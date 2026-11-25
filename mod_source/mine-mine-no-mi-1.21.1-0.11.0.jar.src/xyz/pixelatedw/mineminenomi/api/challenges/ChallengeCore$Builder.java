package xyz.pixelatedw.mineminenomi.api.challenges;

import com.google.common.collect.HashMultimap;
import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.storage.loot.LootTable;

public class ChallengeCore$Builder<T extends Challenge> {
	// Fields
	 String id;
	 String unlocalizedTitle;
	 String unlocalizedObjective;
	 String category;
	 int timeLimit;
	 ResourceKey<LootTable> rewards;
	 ChallengeDifficulty difficulty;
	 int difficultyStars;
	 float rewardsFactor;
	 HashMultimap<ArenaStyle, ChallengeCore$ArenaEntry> arenas;
	 ChallengeCore$ITargetShowcase[] targetShowcase;
	 String[] bannedFactions;
	 ChallengeCore$IFactory<T> factory;
	 ChallengeCore$ICanStart startCheck;
	 ChallengeCore$IEnemySpawns enemySpawns;
	 int order;
	 Supplier<? extends ChallengeCore<?>> hardVersion;

	public ChallengeCore$Builder(String var0, String var1, String var2, String var3, ChallengeCore$IFactory<T> var4) { throw new RuntimeException("yak-decompiler: undecompilable method body"); /* yak-decompiler: ParseBytesCode failed: unsupported_source: lambda source body requires original synthetic declaration metadata */ }
	public ChallengeCore$Builder<T> setOrder(int var1) {
		this.order = var1;
		return this;
	}
	public ChallengeCore$Builder<T> setHardVersion(Supplier<? extends ChallengeCore<?>> var1) {
		this.hardVersion = var1;
		return this;
	}
	public ChallengeCore$Builder<T> setTimeLimit(int var1) {
		this.timeLimit = var1;
		return this;
	}
	public ChallengeCore$Builder<T> setRewardsFactor(float var1) {
		this.rewardsFactor = var1;
		return this;
	}
	public ChallengeCore$Builder<T> setRewards(ResourceLocation var1) {
		this.rewards = (ResourceKey) (ResourceKey.create(Registries.LOOT_TABLE,var1));
		return this;
	}
	public ChallengeCore$Builder<T> setBannedFactions(String... var1) {
		this.bannedFactions = var1;
		return this;
	}
	public ChallengeCore$Builder<T> setStartCheck(ChallengeCore$ICanStart var1) {
		this.startCheck = var1;
		return this;
	}
	public ChallengeCore$Builder<T> setDifficulty(ChallengeDifficulty var1) {
		this.difficulty = var1;
		return this;
	}
	public ChallengeCore$Builder<T> setDifficultyStars(int var1) {
		this.difficultyStars = Math.min(var1,10);
		return this;
	}
	public ChallengeCore$Builder<T> addArena(ArenaStyle var1, ChallengeArena var2, ChallengeCore$IChallengerPosition var3, ChallengeCore$IEnemyPosition var4) {
		ChallengeCore$ArenaEntry var5 = new ChallengeCore$ArenaEntry(var2,var3,var4);
		this.arenas.put(var1,var5);
		return this;
	}
	public ChallengeCore$Builder<T> setEnemySpawns(ChallengeCore$IEnemySpawns var1) {
		this.enemySpawns = var1;
		return this;
	}
	public ChallengeCore$Builder<T> setTargetShowcase(Supplier<? extends EntityType<?>>... var0) { throw new RuntimeException("yak-decompiler: undecompilable method body"); /* yak-decompiler: ParseBytesCode failed: unsupported_source: lambda source body requires original synthetic declaration metadata */ }
	public ChallengeCore$Builder<T> setTargetShowcase(ChallengeCore$ITargetShowcase... var1) {
		this.targetShowcase = var1;
		return this;
	}
	public ChallengeCore<T> build() {
		ChallengeCore var1 = new ChallengeCore(this.id,this.unlocalizedTitle,this.unlocalizedObjective,this.category,this.factory);
		((ChallengeCore)(var1)).jdec$private$78797a2f706978656c61746564772f6d696e656d696e656e6f6d692f6170692f6368616c6c656e6765732f4368616c6c656e6765436f7265$7(this.timeLimit);
		((ChallengeCore)(var1)).jdec$private$78797a2f706978656c61746564772f6d696e656d696e656e6f6d692f6170692f6368616c6c656e6765732f4368616c6c656e6765436f7265$4(this.rewardsFactor);
		((ChallengeCore)(var1)).jdec$private$78797a2f706978656c61746564772f6d696e656d696e656e6f6d692f6170692f6368616c6c656e6765732f4368616c6c656e6765436f7265$5(((ResourceKey)(this.rewards)));
		var1.setBannedFactions(this.bannedFactions);
		var1.setStartCheck(this.startCheck);
		((ChallengeCore)(var1)).jdec$private$78797a2f706978656c61746564772f6d696e656d696e656e6f6d692f6170692f6368616c6c656e6765732f4368616c6c656e6765436f7265$1(((ChallengeDifficulty)(this.difficulty)));
		var1.setHardVersion(this.hardVersion);
		int var2 = this.difficultyStars;
		if ((this.difficulty) == (ChallengeDifficulty.HARD)){
			var2 = (var2) + (10);
		}else{
			if ((this.difficulty) == (ChallengeDifficulty.ULTIMATE)){
				var2 = (var2) + (20);
			}
		}
		((ChallengeCore)(var1)).jdec$private$78797a2f706978656c61746564772f6d696e656d696e656e6f6d692f6170692f6368616c6c656e6765732f4368616c6c656e6765436f7265$2(var2);
		((ChallengeCore)(var1)).jdec$private$78797a2f706978656c61746564772f6d696e656d696e656e6f6d692f6170692f6368616c6c656e6765732f4368616c6c656e6765436f7265$0(((HashMultimap)(this.arenas)));
		((ChallengeCore)(var1)).jdec$private$78797a2f706978656c61746564772f6d696e656d696e656e6f6d692f6170692f6368616c6c656e6765732f4368616c6c656e6765436f7265$6(((ChallengeCore$ITargetShowcase[])(this.targetShowcase)));
		var1.setEnemySpawns(this.enemySpawns);
		if ((this.difficulty) == (ChallengeDifficulty.HARD)){
			this.order = (this.order) + (1);
		}else{
			if ((this.difficulty) == (ChallengeDifficulty.ULTIMATE)){
				this.order = (this.order) + (2);
			}
		}
		((ChallengeCore)(var1)).jdec$private$78797a2f706978656c61746564772f6d696e656d696e656e6f6d692f6170692f6368616c6c656e6765732f4368616c6c656e6765436f7265$3(this.order);
		return var1;
	}
}