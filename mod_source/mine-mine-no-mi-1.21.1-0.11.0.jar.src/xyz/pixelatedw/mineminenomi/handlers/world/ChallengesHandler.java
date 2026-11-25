package xyz.pixelatedw.mineminenomi.handlers.world;

import java.util.Optional;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCache;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
import xyz.pixelatedw.mineminenomi.data.world.ChallengesWorldData;
import xyz.pixelatedw.mineminenomi.init.ModTags$Items;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nChallenges;

public class ChallengesHandler {
	public static void kickPlayerFromChallengeOnLogin(ServerPlayer var0, Level var1) {
		ServerLevel var2 = var0.getServer().getLevel(Level.OVERWORLD);
		ChallengesWorldData var3 = ChallengesWorldData.get();
		InProgressChallenge var4 = var3.getInProgressChallengeFor((LivingEntity)(var0));
		if ((var4) != (null)){
			var4.despawnChallenger((LivingEntity)(var0));
		}else{
			Optional var5 = var3.getChallengerCache(var0.getUUID());
			if (var5.isPresent()){
				ChallengeCache.load((LivingEntity)(var0),((CompoundTag)(var5.get())));
				var3.removeChallengerCache(var0.getUUID());
			}
			var0.teleportTo(var2,(double)(var2.getSharedSpawnPos().getX()),(double)(var2.getSharedSpawnPos().getY()),(double)(var2.getSharedSpawnPos().getZ()),270F,0F);
		}
	}
	public static boolean isItemBlockedInChallenge(LivingEntity var0, ItemStack var1) {
		if ((!(var0.level().isClientSide)) && (NuWorld.isChallengeDimension(var0.level()))){
			InProgressChallenge var2 = ChallengesWorldData.get().getInProgressChallengeFor(var0);
			if ((var2) != (null)){
				boolean var3 = var1.has(DataComponents.FOOD);
				boolean var4 = var1.getItem() instanceof PotionItem;
				int var5 = ((var2.hasRestrictions()) && ((var2.hasActiveRestrictions()) && ((var3) || (var4)))) ? (1) : (0);
				boolean var6 = var1.is(ModTags$Items.BANNED_ITEMS_CHALLANGES);
				if (((var5) == (0)) && (!(var6))){
					return false;
				}else{
					if (!(var0.level().isClientSide)){
						var0.sendSystemMessage(ModI18nChallenges.MESSAGE_CANNOT_USE_ITEM);
					}
					return true;
				}
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
}