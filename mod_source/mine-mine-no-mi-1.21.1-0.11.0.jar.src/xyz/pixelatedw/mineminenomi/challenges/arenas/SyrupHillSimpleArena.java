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
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengesBlockGenerators;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
import xyz.pixelatedw.mineminenomi.api.helpers.ChallengesHelper;
import xyz.pixelatedw.mineminenomi.blocks.PoneglyphBlock;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;

public class SyrupHillSimpleArena extends ChallengeArena {
	// Fields
	public static final SyrupHillSimpleArena INSTANCE = new SyrupHillSimpleArena();
	private static final int ARENA_SIZE_RADIUS = 50;
	private static final int ARENA_SIZE = 100;

	private SyrupHillSimpleArena() {
		super(ArenaStyle.SIMPLE);
	}
	public void buildArena(InProgressChallenge var1, BlockQueue var2) {
		BlockPos var4 = var1.getArenaPos().immutable().below(49).above(18);
		new RandomBlockPlacer().addBlock(((BlockState)(((Block)(ModBlocks.PONEGLYPH.get())).defaultBlockState().setValue((Property)(PoneglyphBlock.TEXTURE),(Comparable)(Integer.valueOf(0))))),100).addBlock(((BlockState)(((Block)(ModBlocks.PONEGLYPH.get())).defaultBlockState().setValue((Property)(PoneglyphBlock.TEXTURE),(Comparable)(Integer.valueOf(1))))),100).addBlock(((BlockState)(((Block)(ModBlocks.PONEGLYPH.get())).defaultBlockState().setValue((Property)(PoneglyphBlock.TEXTURE),(Comparable)(Integer.valueOf(2))))),100).setSize(50,10).setBlockQueue(var2).generate((Level)(var1.getShard()),var1.getArenaPos().below(11),ChallengesBlockGenerators.CUBE_XZ);
		new SimpleBlockPlacer().setBlock(Blocks.BARRIER.defaultBlockState()).setSize(50).setHollow().setThickness(3).generate((Level)(var1.getShard()),var1.getArenaPos(),BlockGenerators.CUBE);
	}
	public BoundingBox getArenaLimits() {
		return new BoundingBox(-50,-50,-50,50,50,50);
	}
	public static ChallengeArena$SpawnPosition getChallengerSpawnPos(int var0, InProgressChallenge var1) {
		BlockPos var2 = new BlockPos(((var1.getArenaPos().getX()) + (15)) - ((var0) + (1)),var1.getArenaPos().getY(),(var1.getArenaPos().getZ()) + (15));
		return new ChallengeArena$SpawnPosition(var2,135F,0F);
	}
	public static ChallengeArena$SpawnPosition[] getEnemySpawnPos(InProgressChallenge var0) {
		return ChallengesHelper.get4DefaultSpawnPoints(new BlockPos((var0.getArenaPos().getX()) - (15),var0.getArenaPos().getY(),(var0.getArenaPos().getZ()) - (15)),140F);
	}
}