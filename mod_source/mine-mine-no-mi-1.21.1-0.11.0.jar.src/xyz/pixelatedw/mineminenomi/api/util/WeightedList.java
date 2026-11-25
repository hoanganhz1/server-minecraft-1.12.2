package xyz.pixelatedw.mineminenomi.api.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.Nullable;

public class WeightedList<T extends Object> {
	// Fields
	private final List<WeightedList$Entry<T>> entries;
	private int maxWeight;
	private int totalWeight;

	public WeightedList(Object... var1) {
		this.entries = new ArrayList();
		this.maxWeight = 1;
		if ((var1.length) == (0)){
			return;
		}else{
			if ((var1.length) == (1)){
				((xyz.pixelatedw.mineminenomi.api.util.WeightedList)(this)).addEntry((Object)(var1[0]),100);
				return;
			}else{
				if (((var1.length) % (2)) != (0)){
					try{
						int var2 = var1.length;
						String var3 = "Number of parameters must either be 0, 1 or divisible by 2, number of parameters found is " + var2;
						throw new Exception((String)(var3));
					}catch(Exception var2_1){
						var2_1.printStackTrace();
						return;
					}
				}else{
					int var2_2 = 0;
					do{
						if ((var2_2) < (var1.length)){
							if (var1[(var2_2) + (1)] instanceof Number){
								int var3_1 = ((Number)(var1[(var2_2) + (1)])).intValue();
								((xyz.pixelatedw.mineminenomi.api.util.WeightedList)(this)).addEntry((Object)(var1[var2_2]),var3_1);
							}
							var2_2 = (var2_2) + (2);
							continue;
						}else{
							break;
						}
					} while (true);
					return;
				}
			}
		}
	}
	public void add(T var1, int var2) {
		this.addEntry(var1,var2);
	}
	public void addEntry(T var1, int var2) {
		if ((var2) < (1)){
			return;
		}else{
			WeightedList$Entry var3 = new WeightedList$Entry(this);
			var3.object = var1;
			var3.weight = (float)(var2);
			this.totalWeight = (this.totalWeight) + (var2);
			this.entries.add(var3);
			if ((var2) > (this.maxWeight)){
				this.maxWeight = var2;
			}
			return;
		}
	}
	@Nullable()
	public @Nullable() T pick(RandomSource var1) {
		if ((this.entries.size()) == (1)){
			return (T) (((WeightedList$Entry)(this.entries.get(0))).object);
		}else{
			int var2 = var1.nextInt(this.maxWeight);
			if (this.entries.isEmpty()){
				return null;
			}else{
				WeightedList$Entry var5 = null;
				int var3 = this.entries.size();
				int var4 = var1.nextInt(var3);
				do{
					var5 = ((WeightedList$Entry)(this.entries.get(var4)));
					if ((var5.weight) > ((float)(var2))){
						break;
					}else{
						var4 = ((var4) >= (var3)) ? ((var4) + (1)) : (0);
						continue;
					}
				} while (true);
				return (T) (var5.object);
			}
		}
	}
	public Set<T> pickN(RandomSource var1, int var2) {
		Object var6 = null;
		int var3 = 5;
		HashSet var4 = new HashSet();
		if ((this.size()) <= (0)){
			return var4;
		}else{
			do{
				if (((((java.util.Set)(var4)).size()) < (var2)) && ((var3) > (0))){
					T var5 = this.pick(var1);
					if ((var5) == (null)){
						var3--;
						continue;
					}else{
						var6 = var5;
						final Object var6_f1 = var6;
						Predicate<String> var7 = (l0) -> {
			return var6_f1.toString().equalsIgnoreCase(l0);
		};
						if (!(var4.stream().map((Function)(((Function<Object, String>)(Object::toString)))).anyMatch(var7))){
							var4.add(var5);
							continue;
						}else{
							var3--;
							continue;
						}
					}
				}else{
					break;
				}
			} while (true);
			return var4;
		}
	}
	public void clear() {
		this.entries.clear();
	}
	public float getWeight(T var1) {
		if ((var1) == (null)){
			return -1F;
		}else{
			T var2 = var1;
			final T var2_f1 = var2;
			Predicate<WeightedList$Entry> var3 = (l0) -> {
			return (l0.object) == (var2_f1);
		};
			Optional var4 = this.entries.stream().filter(var3).findFirst();
			if (var4.isPresent()){
				return ((WeightedList$Entry)(var4.get())).weight;
			}else{
				return -1F;
			}
		}
	}
	public int size() {
		return this.entries.size();
	}
	public double getTotalWeight() {
		return (double)(this.totalWeight);
	}
}