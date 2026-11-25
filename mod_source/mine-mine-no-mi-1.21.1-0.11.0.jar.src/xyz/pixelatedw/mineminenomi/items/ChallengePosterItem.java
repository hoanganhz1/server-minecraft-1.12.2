package xyz.pixelatedw.mineminenomi.items;

import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.data.entity.ChallengeData;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nChallenges;

public class ChallengePosterItem extends Item {
	public ChallengePosterItem() {
		super(new Item.Properties().stacksTo(1));
	}
	public InteractionResultHolder<ItemStack> use(Level var1, Player var2, InteractionHand var3) {
		ItemStack var4 = var2.getItemInHand(var3);
		if (!(var1.isClientSide)){
			ChallengeData var5 = ((ChallengeData)(((java.util.Optional)(ChallengeData.get(var2))).orElse((Object)(null))));
			if ((var5) == (null)){
				return InteractionResultHolder.fail(var4);
			}else{
				ChallengeCore var6 = ((ChallengeCore)(WyRegistry.CHALLENGES.get(((ResourceLocation)(var4.get(ModDataComponents.CHALLENGE))))));
				if ((var6) != (null)){
					if (var5.hasChallenge(var6)){
						Object[] var7 = new Object[]{var6.getLocalizedTitle().getString()};
						var2.sendSystemMessage((Component)(Component.translatable(ModI18nChallenges.MESSAGE_ALREADY_UNLOCKED,var7)));
					}else{
						var5.addChallenge(var6);
						Object[] var7_1 = new Object[]{var6.getLocalizedTitle().getString()};
						var2.sendSystemMessage((Component)(Component.translatable(ModI18nChallenges.MESSAGE_UNLOCKED,var7_1)));
						var2.getInventory().removeItem(var4);
					}
				}
			}
		}
		return InteractionResultHolder.consume(var4);
	}
}