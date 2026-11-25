package xyz.pixelatedw.mineminenomi.api.helpers;

import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;

public class AbilityGroups {
	// Fields
	private static final List<ResourceLocation> FRUIT_GROUPS = new ArrayList();
	private static final HashMap<ResourceLocation, Set<Supplier<? extends AbilityCore<?>>>> STANDLONE_GROUPS = new HashMap();
	private static final List<ResourceLocation> ALL_IDS = new ArrayList();
	private static final List<String> ALL_STRING_IDS = new ArrayList();

	public static void addFruitGroup(ResourceLocation var0) {
		FRUIT_GROUPS.add(var0);
		ALL_IDS.add(var0);
		ALL_STRING_IDS.add(var0.toString());
	}
	public static void addStandaloneGroup(ResourceLocation var0, Set<Supplier<? extends AbilityCore<?>>> var1) {
		STANDLONE_GROUPS.put(var0,var1);
		ALL_IDS.add(var0);
		ALL_STRING_IDS.add(var0.toString());
	}
	public static List<ResourceLocation> getAllIds() {
		return ALL_IDS;
	}
	public static List<String> getAllStringIds() {
		return ALL_STRING_IDS;
	}
	public static Set<Supplier<? extends AbilityCore<?>>> getAbilities(ResourceLocation var0) {
		if (!(FRUIT_GROUPS.isEmpty())){
			Iterator var1 = FRUIT_GROUPS.iterator();
			do{
				if (var1.hasNext()){
					if (((ResourceLocation)(var1.next())).equals(var0)){
						Item var2 = ((Item)(BuiltInRegistries.ITEM.get(var0)));
						if (var2 instanceof AkumaNoMiItem){
							return ((AkumaNoMiItem)(var2)).getRegistryAbilities();
						}else{
							continue;
						}
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
		}
		if (!(STANDLONE_GROUPS.isEmpty())){
			Iterator var1_1 = STANDLONE_GROUPS.entrySet().iterator();
			do{
				if (var1_1.hasNext()){
					Map.Entry var2_1 = ((Map.Entry)(var1_1.next()));
					if (((ResourceLocation)(var2_1.getKey())).equals(var0)){
						return ((Set)(var2_1.getValue()));
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
		}
		return Sets.newHashSet();
	}
}