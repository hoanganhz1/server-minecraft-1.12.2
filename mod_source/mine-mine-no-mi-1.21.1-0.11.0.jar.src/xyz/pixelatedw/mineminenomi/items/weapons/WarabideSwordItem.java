package xyz.pixelatedw.mineminenomi.items.weapons;

import java.util.function.Supplier;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.abilities.wara.WarabideSwordAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.init.ModTiers;

public class WarabideSwordItem extends AbilitySwordItem<WarabideSwordAbility> {
	public WarabideSwordItem() {
		super(ModTiers.WARABIDE_SWORD,(Supplier)(WarabideSwordAbility.INSTANCE),new Item.Properties().attributes(ModSwordItem$AttributesBuilder.of(7F).build()));
	}
	public InteractionResultHolder<ItemStack> use(Level var1, Player var2, InteractionHand var3) {
		return (InteractionResultHolder<ItemStack>) (InteractionResultHolder) (super.use(var1,var2,var3));
	}
}