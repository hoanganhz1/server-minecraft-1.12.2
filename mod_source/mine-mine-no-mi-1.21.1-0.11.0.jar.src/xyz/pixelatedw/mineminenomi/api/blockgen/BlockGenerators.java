package xyz.pixelatedw.mineminenomi.api.blockgen;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;

public class BlockGenerators {
	// Fields
	public static final IBlockGenerator CUBE;
	public static final IBlockGenerator SPHERE;
	public static final IBlockGenerator SPHERE_WITH_PLATFORM;

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
				if ((lv1_10) > (-(lv1_7))){
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
		CUBE = var0;
		IBlockGenerator var1 = (l0, l1, l2) -> {
			int lv2_3 = l2.getX();
			int lv2_4 = l2.getY();
			int lv2_5 = l2.getZ();
			int lv2_6 = l0.getSizeX();
			int lv2_7 = l0.getSizeY();
			int lv2_8 = ((lv2_6) - (l0.getThickness())) * ((lv2_6) - (l0.getThickness()));
			BlockPos.MutableBlockPos lv2_9 = new BlockPos.MutableBlockPos();
			double lv2_10 = (double)(lv2_7);
			LOOP_2:
			do{
				if ((lv2_10) >= ((double)(-(lv2_7)))){
					double lv2_11 = (double)(-(lv2_6));
					LOOP_1:
					do{
						if ((lv2_11) <= ((double)(lv2_6))){
							double lv2_12 = (double)(-(lv2_6));
							do{
								if ((lv2_12) <= ((double)(lv2_6))){
									double lv2_13 = (((lv2_11) * (lv2_11)) + ((lv2_12) * (lv2_12))) + ((lv2_10) * (lv2_10));
									if (((l0.isHollow()) && ((lv2_13) < ((double)(lv2_8)))) || ((lv2_13) >= ((double)((lv2_6) * (lv2_7))))){

									}else{
										lv2_9.set(((double)(lv2_3)) + (lv2_11),((double)(lv2_4)) + (lv2_10),((double)(lv2_5)) + (lv2_12));
										l0.placeNext(l1,lv2_9.immutable(),l0.getState(),l0.getFlag(),l0.getRule());
									}
									lv2_12 = (lv2_12) + (1D);
									continue;
								}else{
									break;
								}
							} while (true);
							lv2_11 = (lv2_11) + (1D);
							continue LOOP_1;
						}else{
							break;
						}
					} while (true);
					lv2_10 = (lv2_10) - (1D);
					continue LOOP_2;
				}else{
					break;
				}
			} while (true);
		};
		SPHERE = var1;
		IBlockGenerator var2 = (l0, l1, l2) -> {
			int lv3_3 = l2.getX();
			int lv3_4 = l2.getY();
			int lv3_5 = l2.getZ();
			int lv3_6 = l0.getSizeX();
			int lv3_7 = l0.getSizeY();
			int lv3_8 = ((lv3_6) - (l0.getThickness())) * ((lv3_6) - (l0.getThickness()));
			BlockPos.MutableBlockPos lv3_9 = new BlockPos.MutableBlockPos();
			double lv3_10 = (double)(lv3_7);
			LOOP_2:
			do{
				if ((lv3_10) >= ((double)(-(lv3_7)))){
					double lv3_11 = (double)(-(lv3_6));
					LOOP_1:
					do{
						if ((lv3_11) <= ((double)(lv3_6))){
							double lv3_12 = (double)(-(lv3_6));
							do{
								if ((lv3_12) <= ((double)(lv3_6))){
									double lv3_13 = (((lv3_11) * (lv3_11)) + ((lv3_12) * (lv3_12))) + ((lv3_10) * (lv3_10));
									if (((l0.isHollow()) && ((lv3_13) < ((double)(lv3_8)))) || ((lv3_13) >= ((double)((lv3_6) * (lv3_7))))){

									}else{
										lv3_9.set(((double)(lv3_3)) + (lv3_11),((double)(lv3_4)) + (lv3_10),((double)(lv3_5)) + (lv3_12));
										if ((((Math.sqrt(lv3_9.distSqr((Vec3i)(l2.below((l2.getY()) - (lv3_9.getY())))))) > (2.5D)) ? (1) : (0)) != (0)){
											l0.placeNext(l1,lv3_9.immutable(),l0.getState(),l0.getFlag(),l0.getRule());
										}
									}
									lv3_12 = (lv3_12) + (1D);
									continue;
								}else{
									break;
								}
							} while (true);
							lv3_11 = (lv3_11) + (1D);
							continue LOOP_1;
						}else{
							break;
						}
					} while (true);
					lv3_10 = (lv3_10) - (1D);
					continue LOOP_2;
				}else{
					break;
				}
			} while (true);
		};
		SPHERE_WITH_PLATFORM = var2;
	}
}