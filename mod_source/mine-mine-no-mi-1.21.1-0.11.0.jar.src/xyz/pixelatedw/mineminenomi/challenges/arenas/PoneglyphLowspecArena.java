package xyz.pixelatedw.mineminenomi.challenges.arenas;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import xyz.pixelatedw.mineminenomi.api.blockgen.BlockGenerators;
import xyz.pixelatedw.mineminenomi.api.blockgen.BlockQueue;
import xyz.pixelatedw.mineminenomi.api.blockgen.IBlockGenerator;
import xyz.pixelatedw.mineminenomi.api.blockgen.RandomBlockPlacer;
import xyz.pixelatedw.mineminenomi.api.blockgen.SimpleBlockPlacer;
import xyz.pixelatedw.mineminenomi.api.challenges.ArenaStyle;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeArena;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeArena$SpawnPosition;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore$ArenaEntry;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore$IChallengerPosition;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore$IEnemyPosition;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengesBlockGenerators;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
import xyz.pixelatedw.mineminenomi.api.helpers.ChallengesHelper;
import xyz.pixelatedw.mineminenomi.blocks.PoneglyphBlock;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;

public class PoneglyphLowspecArena extends ChallengeArena {
	// Fields
	public static final PoneglyphLowspecArena INSTANCE = new PoneglyphLowspecArena();
	public static final ChallengeCore$ArenaEntry ARENA_INFO;
	private static final int ARENA_SIZE_RADIUS = 50;
	private static final int ARENA_THICCNESS = 2;

	private PoneglyphLowspecArena() {
		super(ArenaStyle.LOWSPEC);
	}
	public void buildArena(InProgressChallenge var1, BlockQueue var2) {
		boolean var3 = var1.isUltimateDifficulty();
		new RandomBlockPlacer().addBlock(((BlockState)(((Block)(ModBlocks.PONEGLYPH.get())).defaultBlockState().setValue((Property)(PoneglyphBlock.TEXTURE),(Comparable)(Integer.valueOf(0))))),100).addBlock(((BlockState)(((Block)(ModBlocks.PONEGLYPH.get())).defaultBlockState().setValue((Property)(PoneglyphBlock.TEXTURE),(Comparable)(Integer.valueOf(1))))),100).addBlock(((BlockState)(((Block)(ModBlocks.PONEGLYPH.get())).defaultBlockState().setValue((Property)(PoneglyphBlock.TEXTURE),(Comparable)(Integer.valueOf(2))))),100).setSize(50,2).setBlockQueue(var2).generate((Level)(var1.getShard()),var1.getArenaPos().below(6),ChallengesBlockGenerators.CUBE_XZ);
		new SimpleBlockPlacer().setBlock(Blocks.BARRIER.defaultBlockState()).setSize(50,25).setHollow().setThickness(3).generate((Level)(var1.getShard()),var1.getArenaPos().above(17),BlockGenerators.CUBE);
	}
	public BoundingBox getArenaLimits() {
		return new BoundingBox(-50,-50,-50,50,50,50);
	}
	public static ChallengeArena$SpawnPosition getChallengerSpawnPos(int var0, InProgressChallenge var1) {
		BlockPos var2 = new BlockPos(((var1.getArenaPos().getX()) + (15)) - ((var0) + (1)),(var1.getArenaPos().getY()) - (3),(var1.getArenaPos().getZ()) + (15));
		return new ChallengeArena$SpawnPosition(var2,135F,0F);
	}
	public static ChallengeArena$SpawnPosition[] getEnemySpawnPos(InProgressChallenge var0) {
		return ChallengesHelper.get4DefaultSpawnPoints(new BlockPos((var0.getArenaPos().getX()) - (15),(var0.getArenaPos().getY()) - (3),(var0.getArenaPos().getZ()) - (15)),140F);
	}
	static  {
		ChallengeCore$IChallengerPosition var0 = PoneglyphLowspecArena::getChallengerSpawnPos;
		ARENA_INFO = new ChallengeCore$ArenaEntry((ChallengeArena)(INSTANCE),var0,(ChallengeCore$IEnemyPosition)(((xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore$IEnemyPosition)(PoneglyphLowspecArena::getEnemySpawnPos))));
	}
}