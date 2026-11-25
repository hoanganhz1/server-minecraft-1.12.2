package xyz.pixelatedw.mineminenomi.items;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.SOpenCharacterCreatorScreenPacket;

public class CharacterCreatorItem extends Item {
	public CharacterCreatorItem() {
		super(new Item.Properties().stacksTo(1));
	}
	public InteractionResultHolder<ItemStack> use(Level var1, Player var2, InteractionHand var3) {
		if (!(var1.isClientSide)){
			boolean var4 = ServerConfig.getRaceRandomizer();
			boolean var5 = ServerConfig.getAllowSubRaceSelect();
			ModNetwork.sendTo(new SOpenCharacterCreatorScreenPacket(var4,var5),(Player)(((ServerPlayer)(var2))));
		}
		return new InteractionResultHolder(InteractionResult.SUCCESS,var2.getItemInHand(var3));
	}
}