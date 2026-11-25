package xyz.pixelatedw.mineminenomi.particles.effects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.phys.Vec3;

public class BreakingBlocksParticleEffect$Details extends ParticleEffect$Details {
	// Fields
	 int amount;
	 boolean isVecList;
	 List<BlockPos> positions;
	 List<Vec3> vecPositions;

	public BreakingBlocksParticleEffect$Details() {
		this.amount = 60;
		this.positions = new ArrayList();
		this.vecPositions = new ArrayList();
	}
	public BreakingBlocksParticleEffect$Details(int var1) {
		this.amount = 60;
		this.positions = new ArrayList();
		this.vecPositions = new ArrayList();
		this.amount = var1;
	}
	public void save(CompoundTag var1) {
		var1.putInt("amount",this.amount);
		var1.putBoolean("isVec",this.isVecList);
		ListTag var2 = new ListTag();
		if ((this.positions.size()) > (0)){
			Iterator var3 = this.positions.iterator();
			do{
				if (var3.hasNext()){
					BlockPos var4 = ((BlockPos)(var3.next()));
					CompoundTag var5 = new CompoundTag();
					var5.putInt("x",var4.getX());
					var5.putInt("y",var4.getY());
					var5.putInt("z",var4.getZ());
					var2.add(var5);
					continue;
				}else{
					break;
				}
			} while (true);
		}
		var1.put("positions",(Tag)(var2));
		ListTag var3_1 = new ListTag();
		if ((this.vecPositions.size()) > (0)){
			Iterator var4_1 = this.vecPositions.iterator();
			do{
				if (var4_1.hasNext()){
					Vec3 var5_1 = ((Vec3)(var4_1.next()));
					CompoundTag var6 = new CompoundTag();
					var6.putDouble("x",var5_1.x());
					var6.putDouble("y",var5_1.y());
					var6.putDouble("z",var5_1.z());
					var3_1.add(var6);
					continue;
				}else{
					break;
				}
			} while (true);
		}
		var1.put("vecPositions",(Tag)(var3_1));
	}
	public void load(CompoundTag var1) {
		this.amount = var1.getInt("amount");
		this.isVecList = var1.getBoolean("isVec");
		ListTag var2 = var1.getList("positions",10);
		this.positions.clear();
		int var3 = 0;
		do{
			if ((var3) < (var2.size())){
				CompoundTag var4 = var2.getCompound(var3);
				BlockPos var5 = new BlockPos(var4.getInt("x"),var4.getInt("y"),var4.getInt("z"));
				this.positions.add(var5);
				var3++;
				continue;
			}else{
				break;
			}
		} while (true);
		ListTag var4_1 = var1.getList("vecPositions",10);
		this.vecPositions.clear();
		int var5_1 = 0;
		do{
			if ((var5_1) < (var4_1.size())){
				CompoundTag var6 = var4_1.getCompound(var5_1);
				Vec3 var7 = new Vec3(var6.getDouble("x"),var6.getDouble("y"),var6.getDouble("z"));
				this.vecPositions.add(var7);
				var5_1++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
	public void setParticleAmount(int var1) {
		this.amount = var1;
	}
	public void setPositions(List<BlockPos> var1) {
		this.positions = var1;
	}
	public void setPositions(BlockPos[] var1) {
		((java.util.List)(this.positions)).addAll((Collection)(Arrays.asList(var1)));
	}
	public void setVecPositions(List<Vec3> var1) {
		this.vecPositions = var1;
		this.isVecList = true;
	}
	public void addPosition(BlockPos var1) {
		this.positions.add(var1);
	}
	public void clearPositions() {
		this.positions.clear();
	}
}