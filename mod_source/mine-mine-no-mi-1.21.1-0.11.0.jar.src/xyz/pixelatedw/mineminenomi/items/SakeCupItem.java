package xyz.pixelatedw.mineminenomi.items;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.common.NeoForge;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.events.entity.CrewEvent$Join;
import xyz.pixelatedw.mineminenomi.api.helpers.FactionHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.SoulboundItemHelper;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModFoods;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncEntityStatsPacket;

public class SakeCupItem extends Item {
	public SakeCupItem() {
		super(new Item.Properties().stacksTo(1).food(ModFoods.ALCOHOL));
	}
	public InteractionResultHolder<ItemStack> use(Level var1, Player var2, InteractionHand var3) {
		ItemStack var4 = var2.getItemInHand(var3);
		if ((getLeader(var2.getMainHandItem(),var1)) != (null)){
			var2.startUsingItem(var3);
		}else{
			int var5 = WyHelper.getIndexOfItemStack(((Item)(ModItems.SAKE_BOTTLE.get())),var2.getInventory());
			if ((var5) >= (0)){
				var2.getInventory().getItem(var5).hurtAndBreak(1,(LivingEntity)(var2),EquipmentSlot.MAINHAND);
				setLeader(var2.getMainHandItem(),var2);
				return InteractionResultHolder.success(var4);
			}
		}
		return InteractionResultHolder.fail(var4);
	}
	public ItemStack finishUsingItem(ItemStack var1, Level var2, LivingEntity var3) {
		if ((!(var2.isClientSide)) && (var3 instanceof Player)){
			Player var4 = ((Player)(var3));
			Player var5 = getLeader(var1,var4.level());
			if ((var5) != (null)){
				FactionsWorldData var6 = FactionsWorldData.get();
				Crew var7 = var6.getCrewWithCaptain(var5.getUUID());
				if ((var7) == (null)){
					String var8 = var5.getName().getString();
					String var9 = "Cannot find a crew for captain " + var8;
					WyDebug.debug(var9);
				}else{
					if ((!(((CrewEvent$Join)(NeoForge.EVENT_BUS.post((Event)(new CrewEvent$Join(var4,var7))))).isCanceled())) && (!(var7.hasMember(var4.getUUID())))){
						var6.addCrewMember(var7,(LivingEntity)(var4));
						Object[] var8_1 = new Object[]{var4.getName().getString()};
						FactionHelper.sendMessageToCrew(var2,var7,(Component)(Component.translatable(ModI18n.CREW_MESSAGE_NEW_JOIN,var8_1)));
						Consumer<EntityStatsData> var9_1 = (l0) -> {
			l0.setFaction(((Faction)(ModFactions.PIRATE.get())));
		};
						((java.util.Optional)(EntityStatsData.get(var3))).ifPresent((Consumer)(var9_1));
						ModNetwork.sendToAllTrackingAndSelf(new SSyncEntityStatsPacket((LivingEntity)(var4)),(LivingEntity)(var4));
						setLeader(var1,(Player)(null));
					}
				}
			}
			var4.getInventory().add(new ItemStack(((ItemLike)(ModItems.SAKE_CUP.get()))));
			var1.shrink(1);
		}
		return var1;
	}
	public static void setLeader(ItemStack var0, @Nullable() Player var1) {
		SoulboundItemHelper.setOwner(var0,(LivingEntity)(var1));
	}
	public static boolean hasLeaderSet(ItemStack var0) {
		return SoulboundItemHelper.hasOwner(var0);
	}
	@Nullable()
	public static @Nullable() Player getLeader(ItemStack var0, Level var1) {
		Pair var2 = SoulboundItemHelper.getOwner(var1,var0);
		if ((var2) != (null)){
			return var1.getPlayerByUUID(((UUID)(var2.getKey())));
		}else{
			return null;
		}
	}
	public UseAnim getUseAnimation(ItemStack var1) {
		return UseAnim.DRINK;
	}
}