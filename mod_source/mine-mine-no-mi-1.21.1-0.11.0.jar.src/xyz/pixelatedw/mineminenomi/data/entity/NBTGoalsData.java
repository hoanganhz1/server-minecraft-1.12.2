package xyz.pixelatedw.mineminenomi.data.entity;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.common.util.INBTSerializable;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.entities.ai.NBTGoal;
import xyz.pixelatedw.mineminenomi.api.enums.NBTGoalType;
import xyz.pixelatedw.mineminenomi.init.ModAttachments;

public class NBTGoalsData implements INBTSerializable<CompoundTag> {
	// Fields
	private Set<NBTGoal> goals;

	public NBTGoalsData() {
		this.goals = new HashSet();
	}
	public static NBTGoalsData createCapability(IAttachmentHolder var0) {
		return new NBTGoalsData();
	}
	public static Optional<NBTGoalsData> get(LivingEntity var0) {
		if (!(var0.hasData(ModAttachments.NBT_GOALS))){
			var0.setData(ModAttachments.NBT_GOALS,new NBTGoalsData());
		}
		return (Optional<NBTGoalsData>) (Optional) (var0.getExistingData(ModAttachments.NBT_GOALS));
	}
	public static NBTGoalsData getOrCreate(LivingEntity var0) {
		if (!(var0.hasData(ModAttachments.NBT_GOALS))){
			var0.setData(ModAttachments.NBT_GOALS,new NBTGoalsData());
		}
		return ((NBTGoalsData)(var0.getData(ModAttachments.NBT_GOALS)));
	}
	public Set<NBTGoal> getGoals() {
		return this.goals;
	}
	public CompoundTag serializeNBT(HolderLookup.Provider var1) {
		CompoundTag var2 = new CompoundTag();
		ListTag var3 = new ListTag();
		Iterator var4 = this.goals.iterator();
		do{
			if (var4.hasNext()){
				NBTGoal var5 = ((NBTGoal)(var4.next()));
				CompoundTag var6 = new CompoundTag();
				var6.putString("type",var5.type().toString());
				var6.putInt("priority",var5.priority());
				var6.putString("abilityId",var5.abilityId().getRegistryKey().toString());
				var3.add(var6);
				continue;
			}else{
				break;
			}
		} while (true);
		var2.put("goals",(Tag)(var3));
		return var2;
	}
	public void deserializeNBT(HolderLookup.Provider var1, CompoundTag var2) {
		ListTag var3 = var2.getList("goals",10);
		int var4 = 0;
		do{
			if ((var4) < (var3.size())){
				try{
					CompoundTag var5 = var3.getCompound(var4);
					NBTGoalType var6 = NBTGoalType.valueOf(var5.getString("type"));
					int var7 = var5.getInt("priority");
					AbilityCore var8 = ((AbilityCore)(WyRegistry.ABILITIES.get(ResourceLocation.parse(var5.getString("abilityId")))));
					if ((var8) == (null)){

					}else{
						NBTGoal var9 = new NBTGoal(var6,var7,var8);
						this.goals.add(var9);
					}
				}catch(Exception var5_1){
					var5_1.printStackTrace();
				}
				var4++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
}