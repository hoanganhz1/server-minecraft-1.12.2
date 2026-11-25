package xyz.pixelatedw.mineminenomi.handlers.entity;

import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityEvent;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.DevilFruitData;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.handlers.ability.ProgressionHandler;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncAbilityDataPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncChallengeDataPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncDevilFruitPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncEntityStatsPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncHakiDataPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncQuestDataPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.SRecalculateEyeHeightPacket;

public class SyncHandler {
	public static void joinWorldSync(Player var0) {
		Consumer<DevilFruitData> var1 = (l0) -> {
			if ((new ItemStack((ItemLike)(l0.getDevilFruitItem())).getItem()) == (Blocks.AIR.asItem())){
				l0.setDevilFruit(((ResourceLocation)(null)));
			}
		};
		((java.util.Optional)(DevilFruitData.get((LivingEntity)(var0)))).ifPresent((Consumer)(var1));
		ProgressionHandler.checkAllForNewUnlocks(var0,false);
		Consumer<AbilityData> var2 = (l0) -> {
			l0.setCombatBarSet(0);
		};
		((java.util.Optional)(AbilityData.get((LivingEntity)(var0)))).ifPresent((Consumer)(var2));
		Consumer<EntityStatsData> var3 = (l0) -> {
			l0.setCombatMode(false);
		};
		((java.util.Optional)(EntityStatsData.get((LivingEntity)(var0)))).ifPresent((Consumer)(var3));
		dimensionSync(var0);
	}
	public static void dimensionSync(Player var0) {
		ModNetwork.sendToAllTrackingAndSelf(new SSyncEntityStatsPacket((LivingEntity)(var0)),(LivingEntity)(var0));
		ModNetwork.sendToAllTrackingAndSelf(new SSyncDevilFruitPacket((LivingEntity)(var0)),(LivingEntity)(var0));
		ModNetwork.sendToAllTrackingAndSelf(new SSyncAbilityDataPacket((LivingEntity)(var0)),(LivingEntity)(var0));
		ModNetwork.sendToAllTrackingAndSelf(new SSyncHakiDataPacket((LivingEntity)(var0)),(LivingEntity)(var0));
		ModNetwork.sendToAllTrackingAndSelf(new SSyncQuestDataPacket(var0),(LivingEntity)(var0));
		ModNetwork.sendTo(new SSyncChallengeDataPacket(var0),var0);
		NeoForge.EVENT_BUS.post((Event)(new EntityEvent.Size((Entity)(var0),var0.getPose(),var0.getDimensions(var0.getPose()))));
		ModNetwork.sendToAllTrackingAndSelf(new SRecalculateEyeHeightPacket(var0.getId()),(LivingEntity)(var0));
	}
	public static void trackingSync(LivingEntity var0, Player var1) {
		ModNetwork.sendTo(new SSyncEntityStatsPacket(var0),var1);
		ModNetwork.sendTo(new SSyncDevilFruitPacket(var0),var1);
		ModNetwork.sendTo(new SSyncAbilityDataPacket(var0),var1);
		ModNetwork.sendTo(new SSyncHakiDataPacket(var0),var1);
	}
}