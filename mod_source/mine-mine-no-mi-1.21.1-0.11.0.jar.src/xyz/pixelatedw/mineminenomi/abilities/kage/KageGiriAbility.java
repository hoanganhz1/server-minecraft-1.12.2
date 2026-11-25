package xyz.pixelatedw.mineminenomi.abilities.kage;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class KageGiriAbility extends PassiveAbility {
	// Fields
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<KageGiriAbility>> INSTANCE;
	private Map<UUID, Long> shadowsList;

	public KageGiriAbility(AbilityCore<KageGiriAbility> var1) {
		super((AbilityCore)(var1));
		this.shadowsList = new HashMap();
	}
	public boolean addIfValid(UUID var1) {
		if (this.shadowsList.containsKey(var1)){
			if (this.canTakeShadow(((Long)(this.shadowsList.get(var1))).longValue())){
				this.shadowsList.remove(var1);
				return true;
			}else{
				return false;
			}
		}else{
			this.shadowsList.put(var1,Long.valueOf(System.currentTimeMillis()));
			return true;
		}
	}
	public boolean canTakeShadow(long var1) {
		return ((System.currentTimeMillis()) - (var1)) >= (600000L);
	}
	public void saveAdditional(CompoundTag var1) {
		ListTag var2 = new ListTag();
		Iterator var3 = this.shadowsList.entrySet().iterator();
		do{
			if (var3.hasNext()){
				Map.Entry var4 = ((Map.Entry)(var3.next()));
				if (this.canTakeShadow(((Long)(var4.getValue())).longValue())){
					continue;
				}else{
					CompoundTag var5 = new CompoundTag();
					var5.putUUID("id",((UUID)(var4.getKey())));
					var5.putLong("time",((Long)(var4.getValue())).longValue());
					var2.add(var5);
					continue;
				}
			}else{
				break;
			}
		} while (true);
		var1.put("shadows",(Tag)(var2));
	}
	public void loadAdditional(CompoundTag var1) {
		ListTag var2 = var1.getList("shadows",10);
		this.shadowsList.clear();
		int var3 = 0;
		do{
			if ((var3) < (var2.size())){
				CompoundTag var4 = var2.getCompound(var3);
				UUID var5 = var4.getUUID("id");
				long var6 = var4.getLong("time");
				if (this.canTakeShadow(var6)){

				}else{
					this.shadowsList.put(var5,Long.valueOf(var6));
				}
				var3++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Allows the user to cut an enemy's shadow using a pair of Scissors"),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,AbilityType.PASSIVE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(KageGiriAbility::new)))).addDescriptionLine(lv1_3).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("kage_giri","Kage Giri",var0));
	}
}