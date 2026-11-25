package xyz.pixelatedw.mineminenomi.api.helpers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import xyz.pixelatedw.mineminenomi.api.protection.ProtectedArea$RestorationEntry;

public class NBTHelper {
	public static void loadBlockSnapshotMap(Level var0, CompoundTag var1, Map<BlockPos, ProtectedArea$RestorationEntry> var2) {
		if ((var1.contains("blocks")) && (var1.contains("palette"))){
			ListTag var3 = var1.getList("blocks",10);
			ListTag var4 = var1.getList("palette",10);
			LinkedHashMap var5 = new LinkedHashMap();
			int var6 = 0;
			do{
				if ((var6) < (var4.size())){
					((java.util.Map)(var5)).put((Object)(Integer.valueOf(var6)),(Object)(NbtUtils.readBlockState((HolderGetter)(BuiltInRegistries.BLOCK.asLookup()),var4.getCompound(var6))));
					var6++;
					continue;
				}else{
					break;
				}
			} while (true);
			var6 = 0;
			do{
				if ((var6) < (var3.size())){
					CompoundTag var7 = var3.getCompound(var6);
					int var8 = var7.getInt("index");
					long var9 = (long)(var7.getInt("timestamp"));
					ListTag var10 = var7.getList("pos",3);
					BlockPos var11 = new BlockPos(var10.getInt(0),var10.getInt(1),var10.getInt(2));
					BlockState var12 = ((BlockState)(var5.get(Integer.valueOf(var8))));
					var2.put(var11,new ProtectedArea$RestorationEntry(var9,GlobalPos.of(var0.dimension(),var11),var12,(CompoundTag)(null)));
					var6++;
					continue;
				}else{
					break;
				}
			} while (true);
			return;
		}else{
			return;
		}
	}
	public static void saveBlockSnapshotMap(CompoundTag var0, Map<BlockPos, ProtectedArea$RestorationEntry> var1) {
		if (var1.isEmpty()){
			return;
		}else{
			LinkedHashMap var2 = new LinkedHashMap();
			ListTag var3 = new ListTag();
			Iterator<Map.Entry<BlockPos, ProtectedArea$RestorationEntry>> var4 = var1.entrySet().iterator();
			do{
				if (var4.hasNext()){
					Map.Entry var5 = ((Map.Entry)(var4.next()));
					BlockPos var6 = ((BlockPos)(var5.getKey()));
					BlockState var7 = ((ProtectedArea$RestorationEntry)(var5.getValue())).getBlock();
					((java.util.Map)(var2)).putIfAbsent((Object)(var7),(Object)(Integer.valueOf(((java.util.Map)(var2)).size())));
					CompoundTag var8 = new CompoundTag();
					var8.putInt("index",((Integer)(var2.get(var7))).intValue());
					var8.putLong("timestamp",((ProtectedArea$RestorationEntry)(var5.getValue())).getTimestamp());
					int[] var9 = new int[]{var6.getX(),var6.getY(),var6.getZ()};
					var8.put("pos",(Tag)(newIntegerList(var9)));
					var3.add(var8);
					continue;
				}else{
					break;
				}
			} while (true);
			var0.put("blocks",(Tag)(var3));
			ListTag var5_1 = new ListTag();
			Iterator var6_1 = var2.entrySet().iterator();
			do{
				if (var6_1.hasNext()){
					Map.Entry var7_1 = ((Map.Entry)(var6_1.next()));
					var5_1.add(((Integer)(var7_1.getValue())).intValue(),(Tag)(NbtUtils.writeBlockState(((BlockState)(var7_1.getKey())))));
					continue;
				}else{
					break;
				}
			} while (true);
			var0.put("palette",(Tag)(var5_1));
			return;
		}
	}
	private static ListTag newIntegerList(int... var0) {
		ListTag var1 = new ListTag();
		int[] var2 = var0;
		int var3 = var2.length;
		int var4 = 0;
		do{
			if ((var4) < (var3)){
				var1.add(IntTag.valueOf(var2[var4]));
				var4++;
				continue;
			}else{
				break;
			}
		} while (true);
		return var1;
	}
	public static void loadBlockCollection(CompoundTag var0, Collection<BlockState> var1) {
		LinkedHashMap var2 = new LinkedHashMap();
		Iterator var3 = var0.getCompound("palette").getAllKeys().iterator();
		do{
			if (var3.hasNext()){
				String var4 = ((String)(var3.next()));
				try{
					Block var5 = ((Block)(BuiltInRegistries.BLOCK.get(ResourceLocation.parse(var4))));
					if ((var5) != (null)){
						((java.util.Map)(var2)).putIfAbsent((Object)(Integer.valueOf(((java.util.Map)(var2)).size())),(Object)(var5));
						continue;
					}else{
						continue;
					}
				}catch(Exception var5_1){
					var5_1.printStackTrace();
					continue;
				}
			}else{
				break;
			}
		} while (true);
		int[] var4_1 = var0.getIntArray("indices");
		int var5_2 = var4_1.length;
		int var6 = 0;
		do{
			if ((var6) < (var5_2)){
				Block var7 = ((Block)(var2.get(Integer.valueOf(var4_1[var6]))));
				if ((var7) != (null)){
					var1.add(var7.defaultBlockState());
				}
				var6++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
	public static void saveBlockCollection(CompoundTag var0, Collection<BlockState> var1) {
		LinkedHashMap var2 = new LinkedHashMap();
		ArrayList var3 = new ArrayList();
		Iterator<BlockState> var4 = var1.iterator();
		do{
			if (var4.hasNext()){
				Block var5 = ((BlockState)(var4.next())).getBlock();
				((java.util.Map)(var2)).putIfAbsent((Object)(var5),(Object)(Integer.valueOf(((java.util.Map)(var2)).size())));
				var3.add(Integer.valueOf(((Integer)(var2.get(var5))).intValue()));
				continue;
			}else{
				break;
			}
		} while (true);
		var0.put("indices",(Tag)(new IntArrayTag((List)(var3))));
		CompoundTag var5_1 = new CompoundTag();
		Iterator var6 = var2.entrySet().iterator();
		do{
			if (var6.hasNext()){
				Map.Entry var7 = ((Map.Entry)(var6.next()));
				Optional var8 = BuiltInRegistries.BLOCK.getResourceKey(((Block)(var7.getKey())));
				if (var8.isPresent()){
					var5_1.putInt(((ResourceKey)(var8.get())).location().toString(),((Integer)(var7.getValue())).intValue());
					continue;
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
		var0.put("palette",(Tag)(var5_1));
	}
}