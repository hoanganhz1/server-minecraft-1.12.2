package xyz.pixelatedw.mineminenomi.challenges.baroqueworks;

import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.challenges.ArenaStyle;
import xyz.pixelatedw.mineminenomi.api.challenges.Challenge;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeArena;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore$Builder;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore$IChallengerPosition;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore$IEnemyPosition;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore$IEnemySpawns;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeDifficulty;
import xyz.pixelatedw.mineminenomi.challenges.arenas.JungleClearingSimpleArena;
import xyz.pixelatedw.mineminenomi.init.ModChallenges$Order;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModRegistry$I18nCategory;

public class Mr5MissValentineHardChallenge extends Challenge {
	// Fields
	private static final String TITLE;
	public static final ResourceLocation REWARD;

	public static ChallengeCore<Mr5MissValentineHardChallenge> create(String var0) {
		ChallengeCore$IChallengerPosition var1 = JungleClearingSimpleArena::getChallengerSpawnPos;
		Supplier[] var2 = new Supplier[]{ModMobs.MR5,ModMobs.MISS_VALENTINE};
		return (ChallengeCore<Mr5MissValentineHardChallenge>) (ChallengeCore) (((xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore$Builder)(new ChallengeCore$Builder(var0,TITLE,Mr5MissValentineChallenge.OBJECTIVE,ModNPCGroups.BAROQUE_WORKS.getName(),(ChallengeCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore$IFactory)(Mr5MissValentineHardChallenge::new)))).setDifficulty(ChallengeDifficulty.HARD).setDifficultyStars(2).addArena(ArenaStyle.SIMPLE,(ChallengeArena)(JungleClearingSimpleArena.INSTANCE),var1,(ChallengeCore$IEnemyPosition)(((xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore$IEnemyPosition)(JungleClearingSimpleArena::getEnemySpawnPos)))).setEnemySpawns((ChallengeCore$IEnemySpawns)(((xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore$IEnemySpawns)(Mr5MissValentineChallenge::setEnemeySpawns)))).setTargetShowcase(var2).setTimeLimit(10).setOrder(ModChallenges$Order.MR_5_MISS_VALENTINE).setRewards(REWARD))).build());
	}
	public Mr5MissValentineHardChallenge(ChallengeCore<Mr5MissValentineHardChallenge> var1) {
		super((ChallengeCore)(var1));
	}
	static  {
		TITLE = ModRegistry.registerName(ModRegistry$I18nCategory.CHALLENGE,"baroque_works.mr_5_and_miss_valentine_hard","Mr. 5 & Miss Valentine (Hard)");
		REWARD = ResourceLocation.fromNamespaceAndPath("mineminenomi","rewards/mr_5_and_miss_valentine_hard");
	}
}