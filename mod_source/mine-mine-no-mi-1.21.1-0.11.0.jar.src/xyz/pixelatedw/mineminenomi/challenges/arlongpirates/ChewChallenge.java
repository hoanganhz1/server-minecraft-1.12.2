package xyz.pixelatedw.mineminenomi.challenges.arlongpirates;

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
import xyz.pixelatedw.mineminenomi.challenges.arenas.ArlongParkSimpleArena;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.arlongpirates.ChewEntity;
import xyz.pixelatedw.mineminenomi.init.ModChallenges;
import xyz.pixelatedw.mineminenomi.init.ModChallenges$Order;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModRegistry$I18nCategory;

public class ChewChallenge extends Challenge {
	// Fields
	private static final String TITLE;
	public static final String OBJECTIVE;
	public static final ResourceLocation REWARD;

	public static ChallengeCore<ChewChallenge> create(String var0) {
		ChallengeCore$IChallengerPosition var1 = ArlongParkSimpleArena::getChallengerSpawnPos;
		Supplier[] var2 = new Supplier[]{ModMobs.CHEW};
		return (ChallengeCore<ChewChallenge>) (ChallengeCore) (((xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore$Builder)(new ChallengeCore$Builder(var0,TITLE,OBJECTIVE,ModNPCGroups.ARLONG_PIRATES.getName(),(ChallengeCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore$IFactory)(ChewChallenge::new)))).setDifficulty(ChallengeDifficulty.STANDARD).setDifficultyStars(1).addArena(ArenaStyle.SIMPLE,(ChallengeArena)(ArlongParkSimpleArena.INSTANCE),var1,(ChallengeCore$IEnemyPosition)(((xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore$IEnemyPosition)(ArlongParkSimpleArena::getEnemySpawnPos)))).setEnemySpawns((ChallengeCore$IEnemySpawns)(((xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore$IEnemySpawns)(ChewChallenge::setEnemeySpawns)))).setTargetShowcase(var2).setTimeLimit(10).setOrder(ModChallenges$Order.CHEW).setRewards(REWARD).setHardVersion((Supplier)(ModChallenges.CHEW_HARD)))).build());
	}
	public ChewChallenge(ChallengeCore<ChewChallenge> var1) {
		super((ChallengeCore)(var1));
	}
	public static Set<ChallengeArena$EnemySpawn> setEnemeySpawns(InProgressChallenge var0, ChallengeArena$SpawnPosition[] var1) {
		HashSet var2 = new HashSet();
		ChewEntity var3 = new ChewEntity(var0);
		var2.add(new ChallengeArena$EnemySpawn((LivingEntity)(var3),var1[0]));
		return var2;
	}
	static  {
		TITLE = ModRegistry.registerName(ModRegistry$I18nCategory.CHALLENGE,"chew","Chew");
		OBJECTIVE = ModRegistry.registerName(ModRegistry$I18nCategory.CHALLENGE,"chew.objective","Defeat Chew");
		REWARD = ResourceLocation.fromNamespaceAndPath("mineminenomi","rewards/chew");
	}
}