package xyz.pixelatedw.mineminenomi.challenges.blackcatpirates;

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
import xyz.pixelatedw.mineminenomi.challenges.arenas.SyrupHillSimpleArena;
import xyz.pixelatedw.mineminenomi.init.ModChallenges$Order;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModRegistry$I18nCategory;

public class NyanbanBrothersHardChallenge extends Challenge {
	// Fields
	private static final String TITLE;
	public static final ResourceLocation REWARD;

	public static ChallengeCore<NyanbanBrothersHardChallenge> create(String var0) {
		ChallengeCore$IChallengerPosition var1 = SyrupHillSimpleArena::getChallengerSpawnPos;
		Supplier[] var2 = new Supplier[]{ModMobs.SHAM,ModMobs.BUCHI};
		return (ChallengeCore<NyanbanBrothersHardChallenge>) (ChallengeCore) (((xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore$Builder)(new ChallengeCore$Builder(var0,TITLE,NyanbanBrothersChallenge.OBJECTIVE,ModNPCGroups.BLACK_CAT_PIRATES.getName(),(ChallengeCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore$IFactory)(NyanbanBrothersHardChallenge::new)))).setDifficulty(ChallengeDifficulty.HARD).setDifficultyStars(1).addArena(ArenaStyle.SIMPLE,(ChallengeArena)(SyrupHillSimpleArena.INSTANCE),var1,(ChallengeCore$IEnemyPosition)(((xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore$IEnemyPosition)(SyrupHillSimpleArena::getEnemySpawnPos)))).setEnemySpawns((ChallengeCore$IEnemySpawns)(((xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore$IEnemySpawns)(NyanbanBrothersChallenge::setEnemeySpawns)))).setTargetShowcase(var2).setTimeLimit(10).setOrder(ModChallenges$Order.SHAM_BUCHI).setRewards(REWARD))).build());
	}
	public NyanbanBrothersHardChallenge(ChallengeCore<NyanbanBrothersHardChallenge> var1) {
		super((ChallengeCore)(var1));
	}
	static  {
		TITLE = ModRegistry.registerName(ModRegistry$I18nCategory.CHALLENGE,"black_cat_pirates.nyanban_brothers_hard","Nyanban Brothers (Hard)");
		REWARD = ResourceLocation.fromNamespaceAndPath("mineminenomi","rewards/nyanban_brothers_hard");
	}
}