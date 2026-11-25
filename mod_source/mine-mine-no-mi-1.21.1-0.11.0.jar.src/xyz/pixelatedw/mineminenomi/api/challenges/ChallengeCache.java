package xyz.pixelatedw.mineminenomi.api.challenges;

import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.inventory.PlayerEnderChestContainer;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.DevilFruitData;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.data.entity.HakiData;

public class ChallengeCache {
	public static CompoundTag save(LivingEntity var0) {
		CompoundTag var1 = new CompoundTag();
		var1.putFloat("Health",var0.getHealth());
		CompoundTag var2 = var1;
		LivingEntity var3 = var0;
		final CompoundTag var2_f1 = var2;
		final LivingEntity var3_f2 = var3;
		Consumer<EntityStatsData> var4 = (l0) -> {
			var2_f1.put("EntityStatsCapability",(Tag)(l0.serializeNBT((HolderLookup.Provider)(var3_f2.registryAccess()))));
		};
		((java.util.Optional)(EntityStatsData.get(var0))).ifPresent((Consumer)(var4));
		CompoundTag var5 = var1;
		LivingEntity var6 = var0;
		final CompoundTag var5_f3 = var5;
		final LivingEntity var6_f4 = var6;
		Consumer<HakiData> var7 = (l0) -> {
			var5_f3.put("HakiCapability",(Tag)(l0.serializeNBT((HolderLookup.Provider)(var6_f4.registryAccess()))));
		};
		((java.util.Optional)(HakiData.get(var0))).ifPresent((Consumer)(var7));
		CompoundTag var8 = var1;
		LivingEntity var9 = var0;
		final CompoundTag var8_f5 = var8;
		final LivingEntity var9_f6 = var9;
		Consumer<DevilFruitData> var10 = (l0) -> {
			var8_f5.put("DevilFruitCapability",(Tag)(l0.serializeNBT((HolderLookup.Provider)(var9_f6.registryAccess()))));
		};
		((java.util.Optional)(DevilFruitData.get(var0))).ifPresent((Consumer)(var10));
		CompoundTag var11 = var1;
		LivingEntity var12 = var0;
		final CompoundTag var11_f7 = var11;
		final LivingEntity var12_f8 = var12;
		Consumer<AbilityData> var13 = (l0) -> {
			var11_f7.put("AbilityCapability",(Tag)(l0.serializeNBT((HolderLookup.Provider)(var12_f8.registryAccess()))));
		};
		((java.util.Optional)(AbilityData.get(var0))).ifPresent((Consumer)(var13));
		if (var0 instanceof Player){
			Player var14 = ((Player)(var0));
			var14.getFoodData().addAdditionalSaveData(var1);
			var1.put("Inventory",(Tag)(var14.getInventory().save(new ListTag())));
			var1.put("EnderItems",(Tag)(var14.getEnderChestInventory().createTag((HolderLookup.Provider)(var0.registryAccess()))));
		}
		return var1;
	}
	public static void load(LivingEntity var0, CompoundTag var1) {
		var0.setHealth(var1.getFloat("Health"));
		LivingEntity var2 = var0;
		CompoundTag var3 = var1;
		final LivingEntity var2_f1 = var2;
		final CompoundTag var3_f2 = var3;
		Consumer<EntityStatsData> var4 = (l0) -> {
			l0.deserializeNBT((HolderLookup.Provider)(var2_f1.registryAccess()),var3_f2.getCompound("EntityStatsCapability"));
		};
		((java.util.Optional)(EntityStatsData.get(var0))).ifPresent((Consumer)(var4));
		LivingEntity var5 = var0;
		CompoundTag var6 = var1;
		final LivingEntity var5_f3 = var5;
		final CompoundTag var6_f4 = var6;
		Consumer<HakiData> var7 = (l0) -> {
			l0.deserializeNBT((HolderLookup.Provider)(var5_f3.registryAccess()),var6_f4.getCompound("HakiCapability"));
		};
		((java.util.Optional)(HakiData.get(var0))).ifPresent((Consumer)(var7));
		LivingEntity var8 = var0;
		CompoundTag var9 = var1;
		final LivingEntity var8_f5 = var8;
		final CompoundTag var9_f6 = var9;
		Consumer<DevilFruitData> var10 = (l0) -> {
			l0.deserializeNBT((HolderLookup.Provider)(var8_f5.registryAccess()),var9_f6.getCompound("DevilFruitCapability"));
		};
		((java.util.Optional)(DevilFruitData.get(var0))).ifPresent((Consumer)(var10));
		LivingEntity var11 = var0;
		CompoundTag var12 = var1;
		final LivingEntity var11_f7 = var11;
		final CompoundTag var12_f8 = var12;
		Consumer<AbilityData> var13 = (l0) -> {
			l0.deserializeNBT((HolderLookup.Provider)(var11_f7.registryAccess()),var12_f8.getCompound("AbilityCapability"));
		};
		((java.util.Optional)(AbilityData.get(var0))).ifPresent((Consumer)(var13));
		if (var0 instanceof Player){
			Player var14 = ((Player)(var0));
			var14.getFoodData().readAdditionalSaveData(var1);
			var14.getInventory().load(var1.getList("Inventory",10));
			var14.getEnderChestInventory().fromTag(var1.getList("EnderItems",10),(HolderLookup.Provider)(var0.registryAccess()));
		}
	}
}