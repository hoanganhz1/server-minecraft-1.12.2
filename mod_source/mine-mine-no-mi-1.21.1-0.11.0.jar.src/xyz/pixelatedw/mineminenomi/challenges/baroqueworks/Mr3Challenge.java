package xyz.pixelatedw.mineminenomi.challenges.baroqueworks;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.api.challenges.ArenaStyle;
import xyz.pixelatedw.mineminenomi.api.challenges.Challenge;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeArena;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeArena$EnemySpawn;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeArena$SpawnPosition;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore$Builder;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore$IChallengerPosition;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore$IEnemyPosition;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore$IEnemySpawns;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeDifficulty;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
import xyz.pixelatedw.mineminenomi.challenges.arenas.JungleClearingSimpleArena;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.baroqueworks.Mr3Entity;
import xyz.pixelatedw.mineminenomi.init.ModChallenges;
import xyz.pixelatedw.mineminenomi.init.ModChallenges$Order;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModRegistry$I18nCategory;

public class Mr3Challenge extends Challenge {
	// Fields
	private static final String TITLE;
	public static final String OBJECTIVE;
	public static final ResourceLocation REWARD;

	public static ChallengeCore<Mr3Challenge> create(String var0) {
		ChallengeCore$IChallengerPosition var1 = JungleClearingSimpleArena::getChallengerSpawnPos;
		Supplier[] var2 = new Supplier[]{ModMobs.MR3};
		return (ChallengeCore<Mr3Challenge>) (ChallengeCore) (((xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore$Builder)(new ChallengeCore$Builder(var0,TITLE,OBJECTIVE,ModNPCGroups.BAROQUE_WORKS.getName(),(ChallengeCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore$IFactory)(Mr3Challenge::new)))).setDifficulty(ChallengeDifficulty.STANDARD).setDifficultyStars(2).setRewardsFactor(2F).addArena(ArenaStyle.SIMPLE,(ChallengeArena)(JungleClearingSimpleArena.INSTANCE),var1,(ChallengeCore$IEnemyPosition)(((xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore$IEnemyPosition)(JungleClearingSimpleArena::getEnemySpawnPos)))).setEnemySpawns((ChallengeCore$IEnemySpawns)(((xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore$IEnemySpawns)(Mr3Challenge::setEnemeySpawns)))).setTargetShowcase(var2).setTimeLimit(10).setOrder(ModChallenges$Order.MR_3).setRewards(REWARD).setHardVersion((Supplier)(ModChallenges.MR_3_HARD)))).build());
	}
	public Mr3Challenge(ChallengeCore<Mr3Challenge> var1) {
		super((ChallengeCore)(var1));
	}
	public static Set<ChallengeArena$EnemySpawn> setEnemeySpawns(InProgressChallenge var0, ChallengeArena$SpawnPosition[] var1) {
		HashSet var2 = new HashSet();
		Mr3Entity var3 = new Mr3Entity(var0);
		var2.add(new ChallengeArena$EnemySpawn((LivingEntity)(var3),var1[0]));
		return var2;
	}
	static  {
		TITLE = ModRegistry.registerName(ModRegistry$I18nCategory.CHALLENGE,"baroque_works.mr_3","Mr. 3");
		OBJECTIVE = ModRegistry.registerName(ModRegistry$I18nCategory.CHALLENGE,"baroque_works.mr_3.objective","Defeat Mr. 3");
		REWARD = ResourceLocation.fromNamespaceAndPath("mineminenomi","rewards/mr_3");
	}
}