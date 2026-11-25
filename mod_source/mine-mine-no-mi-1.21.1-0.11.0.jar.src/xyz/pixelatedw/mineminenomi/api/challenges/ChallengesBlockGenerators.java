package xyz.pixelatedw.mineminenomi.api.challenges;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import xyz.pixelatedw.mineminenomi.api.blockgen.IBlockGenerator;
import xyz.pixelatedw.mineminenomi.api.blockgen.IBlockPlacer;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;

public class ChallengesBlockGenerators {
	// Fields
	public static final IBlockGenerator CUBE_XZ;

	static  {
		IBlockGenerator var0 = (l0, l1, l2) -> {
			int lv1_3 = l2.getX();
			int lv1_4 = l2.getY();
			int lv1_5 = l2.getZ();
			int lv1_6 = l0.getSizeX();
			int lv1_7 = l0.getSizeY();
			int lv1_8 = l0.getSizeZ();
			BlockPos.MutableBlockPos lv1_9 = new BlockPos.MutableBlockPos();
			int lv1_10 = lv1_7;
			LOOP_2:
			do{
				if ((lv1_10) > (0)){
					int lv1_11 = -(lv1_6);
					LOOP_1:
					do{
						if ((lv1_11) < (lv1_6)){
							int lv1_12 = -(lv1_8);
							do{
								if ((lv1_12) < (lv1_8)){
									if (l0.isHollow()){
										if (((((lv1_11) < ((-(lv1_6)) + (l0.getThickness()))) || (((lv1_11) >= ((lv1_6) - (l0.getThickness()))) || (((lv1_10) < ((-(lv1_7)) + (l0.getThickness()))) || (((lv1_10) >= ((lv1_7) - (l0.getThickness()))) || (((lv1_12) < ((-(lv1_8)) + (l0.getThickness()))) || ((lv1_12) >= ((lv1_8) - (l0.getThickness())))))))) ? (1) : (0)) == (0)){

										}
									}else{
										lv1_9.set((lv1_3) + (lv1_11),(lv1_4) + (lv1_10),(lv1_5) + (lv1_12));
										l0.placeNext(l1,lv1_9.immutable(),l0.getState(),l0.getFlag(),l0.getRule());
									}
									lv1_12++;
									continue;
								}else{
									break;
								}
							} while (true);
							lv1_11++;
							continue LOOP_1;
						}else{
							break;
						}
					} while (true);
					lv1_10--;
					continue LOOP_2;
				}else{
					break;
				}
			} while (true);
		};
		CUBE_XZ = var0;
	}
}