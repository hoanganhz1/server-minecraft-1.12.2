package xyz.pixelatedw.mineminenomi.api.helpers;

import java.util.UUID;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.items.components.Soulbound;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;

public class SoulboundItemHelper {
	@Nullable()
	public static Pair<UUID, LivingEntity> getOwner(Level var0, ItemStack var1) {
		UUID var2 = ((Soulbound)(var1.get(ModDataComponents.SOULBOUND))).id();
		Entity var3 = WyHelper.getLoadedEntity(var0,var2);
		if (var3 instanceof LivingEntity){
			return ImmutablePair.of(var2,((LivingEntity)(var3)));
		}else{
			return ImmutablePair.of(var2,(Object)(null));
		}
	}
	@Nullable()
	public static UUID getOwnerUUID(ItemStack var0) {
		if (!(var0.has(ModDataComponents.SOULBOUND))){
			return null;
		}else{
			return ((Soulbound)(var0.get(ModDataComponents.SOULBOUND))).id();
		}
	}
	public static boolean hasOwner(ItemStack var0) {
		return var0.has(ModDataComponents.SOULBOUND);
	}
	public static void setOwner(ItemStack var0, @Nullable() LivingEntity var1) {
		if ((var1) == (null)){
			var0.remove(ModDataComponents.SOULBOUND);
		}else{
			var0.set(ModDataComponents.SOULBOUND,new Soulbound(var1));
		}
	}
}