package xyz.pixelatedw.mineminenomi.challenges.buggypirates;

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
import xyz.pixelatedw.mineminenomi.challenges.arenas.CircusArena;
import xyz.pixelatedw.mineminenomi.init.ModChallenges$Order;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModRegistry$I18nCategory;

public class CabajiHardChallenge extends Challenge {
	// Fields
	private static final String TITLE;
	public static final ResourceLocation REWARD;

	public static ChallengeCore<CabajiHardChallenge> create(String var0) {
		ChallengeCore$IChallengerPosition var1 = CircusArena::getChallengerSpawnPos;
		ChallengeCore$ITargetShowcase[] var2 = new ChallengeCore$ITargetShowcase[1];
		ChallengeCore$ITargetShowcase var3 = CabajiChallenge::createShowcase;
		var2[0] = var3;
		return (ChallengeCore<CabajiHardChallenge>) (ChallengeCore) (((xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore$Builder)(new ChallengeCore$Builder(var0,TITLE,CabajiChallenge.OBJECTIVE,ModNPCGroups.BUGGY_PIRATES.getName(),(ChallengeCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore$IFactory)(CabajiHardChallenge::new)))).setDifficulty(ChallengeDifficulty.HARD).setDifficultyStars(1).addArena(ArenaStyle.SIMPLE,(ChallengeArena)(CircusArena.INSTANCE),var1,(ChallengeCore$IEnemyPosition)(((xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore$IEnemyPosition)(CircusArena::getEnemySpawnPos)))).setEnemySpawns((ChallengeCore$IEnemySpawns)(((xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore$IEnemySpawns)(CabajiChallenge::setEnemeySpawns)))).setTargetShowcase(var2).setTimeLimit(10).setOrder(ModChallenges$Order.CABAJI).setRewards(REWARD))).build());
	}
	public CabajiHardChallenge(ChallengeCore<CabajiHardChallenge> var1) {
		super((ChallengeCore)(var1));
	}
	static  {
		TITLE = ModRegistry.registerName(ModRegistry$I18nCategory.CHALLENGE,"buggy_pirates.cabaji_hard","Cabaji (Hard)");
		REWARD = ResourceLocation.fromNamespaceAndPath("mineminenomi","rewards/cabaji_hard");
	}
}