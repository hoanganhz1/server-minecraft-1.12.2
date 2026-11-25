package xyz.pixelatedw.mineminenomi.challenges.arlongpirates;

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
import xyz.pixelatedw.mineminenomi.challenges.arenas.ArlongParkSimpleArena;
import xyz.pixelatedw.mineminenomi.init.ModChallenges$Order;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModRegistry$I18nCategory;

public class ArlongHardChallenge extends Challenge {
	// Fields
	private static final String TITLE;
	public static final ResourceLocation REWARD;

	public static ChallengeCore<ArlongHardChallenge> create(String var0) {
		ChallengeCore$IChallengerPosition var1 = ArlongParkSimpleArena::getChallengerSpawnPos;
		Supplier[] var2 = new Supplier[]{ModMobs.ARLONG};
		return (ChallengeCore<ArlongHardChallenge>) (ChallengeCore) (((xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore$Builder)(new ChallengeCore$Builder(var0,TITLE,ArlongChallenge.OBJECTIVE,ModNPCGroups.ARLONG_PIRATES.getName(),(ChallengeCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore$IFactory)(ArlongHardChallenge::new)))).setDifficulty(ChallengeDifficulty.HARD).setDifficultyStars(1).setRewardsFactor(2F).addArena(ArenaStyle.SIMPLE,(ChallengeArena)(ArlongParkSimpleArena.INSTANCE),var1,(ChallengeCore$IEnemyPosition)(((xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore$IEnemyPosition)(ArlongParkSimpleArena::getEnemySpawnPos)))).setEnemySpawns((ChallengeCore$IEnemySpawns)(((xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore$IEnemySpawns)(ArlongChallenge::setEnemeySpawns)))).setTargetShowcase(var2).setTimeLimit(10).setOrder(ModChallenges$Order.ARLONG).setRewards(REWARD))).build());
	}
	public ArlongHardChallenge(ChallengeCore<ArlongHardChallenge> var1) {
		super((ChallengeCore)(var1));
	}
	static  {
		TITLE = ModRegistry.registerName(ModRegistry$I18nCategory.CHALLENGE,"arlong_hard","Arlong (Hard)");
		REWARD = ResourceLocation.fromNamespaceAndPath("mineminenomi","rewards/arlong_hard");
	}
}