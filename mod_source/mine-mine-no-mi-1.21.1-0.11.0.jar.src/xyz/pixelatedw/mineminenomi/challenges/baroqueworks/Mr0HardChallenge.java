package xyz.pixelatedw.mineminenomi.challenges.baroqueworks;

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
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore$ITargetShowcase;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeDifficulty;
import xyz.pixelatedw.mineminenomi.challenges.arenas.AlabastaDesertSimpleArena;
import xyz.pixelatedw.mineminenomi.init.ModChallenges$Order;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModRegistry$I18nCategory;

public class Mr0HardChallenge extends Challenge {
	// Fields
	private static final String TITLE;
	public static final ResourceLocation REWARD;

	public static ChallengeCore<Mr0HardChallenge> create(String var0) {
		ChallengeCore$IChallengerPosition var1 = AlabastaDesertSimpleArena::getChallengerSpawnPos;
		ChallengeCore$ITargetShowcase[] var2 = new ChallengeCore$ITargetShowcase[1];
		ChallengeCore$ITargetShowcase var3 = Mr0Challenge::createMr0Showcase;
		var2[0] = var3;
		return (ChallengeCore<Mr0HardChallenge>) (ChallengeCore) (((xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore$Builder)(new ChallengeCore$Builder(var0,TITLE,Mr0Challenge.OBJECTIVE,ModNPCGroups.BAROQUE_WORKS.getName(),(ChallengeCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore$IFactory)(Mr0HardChallenge::new)))).setDifficulty(ChallengeDifficulty.HARD).setDifficultyStars(3).setRewardsFactor(3F).addArena(ArenaStyle.SIMPLE,(ChallengeArena)(AlabastaDesertSimpleArena.INSTANCE),var1,(ChallengeCore$IEnemyPosition)(((xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore$IEnemyPosition)(AlabastaDesertSimpleArena::getEnemySpawnPos)))).setEnemySpawns((ChallengeCore$IEnemySpawns)(((xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore$IEnemySpawns)(Mr0Challenge::setEnemeySpawns)))).setTargetShowcase(var2).setTimeLimit(10).setOrder(ModChallenges$Order.MR_0).setRewards(REWARD))).build());
	}
	public Mr0HardChallenge(ChallengeCore<Mr0HardChallenge> var1) {
		super((ChallengeCore)(var1));
	}
	static  {
		TITLE = ModRegistry.registerName(ModRegistry$I18nCategory.CHALLENGE,"baroque_works.mr_0_hard","Mr. 0 (Hard)");
		REWARD = ResourceLocation.fromNamespaceAndPath("mineminenomi","rewards/mr_0_hard");
	}
}