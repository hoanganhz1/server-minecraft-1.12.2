package xyz.pixelatedw.mineminenomi.challenges.marines;

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
import xyz.pixelatedw.mineminenomi.challenges.arenas.MarineSmallBaseSimpleArena;
import xyz.pixelatedw.mineminenomi.init.ModChallenges$Order;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModRegistry$I18nCategory;

public class MorganHardChallenge extends Challenge {
	// Fields
	private static final String TITLE;
	public static final ResourceLocation REWARD;

	public static ChallengeCore<MorganHardChallenge> create(String var0) {
		ChallengeCore$IChallengerPosition var1 = MarineSmallBaseSimpleArena::getChallengerSpawnPos;
		ChallengeCore$ITargetShowcase[] var2 = new ChallengeCore$ITargetShowcase[1];
		ChallengeCore$ITargetShowcase var3 = MorganChallenge::createShowcase;
		var2[0] = var3;
		return (ChallengeCore<MorganHardChallenge>) (ChallengeCore) (((xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore$Builder)(new ChallengeCore$Builder("morgan_hard",TITLE,MorganChallenge.OBJECTIVE,ModNPCGroups.MARINES,(ChallengeCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore$IFactory)(MorganHardChallenge::new)))).setDifficulty(ChallengeDifficulty.HARD).setDifficultyStars(1).addArena(ArenaStyle.SIMPLE,(ChallengeArena)(MarineSmallBaseSimpleArena.INSTANCE),var1,(ChallengeCore$IEnemyPosition)(((xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore$IEnemyPosition)(MarineSmallBaseSimpleArena::getEnemySpawnPos)))).setEnemySpawns((ChallengeCore$IEnemySpawns)(((xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore$IEnemySpawns)(MorganChallenge::setEnemeySpawns)))).setTargetShowcase(var2).setTimeLimit(10).setOrder(ModChallenges$Order.MORGAN).setRewards(REWARD))).build());
	}
	public MorganHardChallenge(ChallengeCore<MorganHardChallenge> var1) {
		super((ChallengeCore)(var1));
	}
	static  {
		TITLE = ModRegistry.registerName(ModRegistry$I18nCategory.CHALLENGE,"marines.morgan_hard","Morgan (Hard)");
		REWARD = ResourceLocation.fromNamespaceAndPath("mineminenomi","rewards/morgan_hard");
	}
}