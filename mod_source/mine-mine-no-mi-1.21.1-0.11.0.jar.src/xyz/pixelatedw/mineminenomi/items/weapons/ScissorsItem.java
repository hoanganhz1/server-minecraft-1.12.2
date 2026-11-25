package xyz.pixelatedw.mineminenomi.items.weapons;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.IShearable;
import xyz.pixelatedw.mineminenomi.abilities.kage.KageGiriAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModTiers;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.SRemoveShadowEventsPacket;

public class ScissorsItem extends ModSwordItem {
	public ScissorsItem() {
		super(ModTiers.SCISSORS,new Item.Properties().attributes(ModSwordItem$AttributesBuilder.of(5F,-2.8F).build()));
	}
	public boolean hurtEnemy(ItemStack var1, LivingEntity var2, LivingEntity var3) {
		if (var3 instanceof Player){
			Player var4 = ((Player)(var3));
			Function<AbilityData, KageGiriAbility> var5 = (l0) -> {
			return ((KageGiriAbility)(l0.getPassiveAbility(((AbilityCore)(KageGiriAbility.INSTANCE.get())))));
		};
			KageGiriAbility var6 = ((KageGiriAbility)(((java.util.Optional)(AbilityData.get(var3))).map((Function)(var5)).orElse(null)));
			int var7 = (((var6) != (null)) && (var6.canUse(var3).isSuccess())) ? (1) : (0);
			EntityStatsData var8 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get(var2))).orElse((Object)(null))));
			if (((var8) != (null)) && (((var7) != (0)) && (((var1) != (null)) && (((var1.getItem()) == (ModWeapons.SCISSORS.get())) && (var8.hasShadow()))))){
				if (((var6) != (null)) && (var6.addIfValid(var2.getUUID()))){
					var8.setShadow(false);
					var4.getInventory().add(new ItemStack(((ItemLike)(ModItems.SHADOW.get()))));
					ModNetwork.sendToAllTracking(new SRemoveShadowEventsPacket((LivingEntity)(var2)),(Entity)(var2));
				}
				return super.hurtEnemy(var1,var2,var3);
			}else{
				return super.hurtEnemy(var1,var2,var3);
			}
		}else{
			return super.hurtEnemy(var1,var2,var3);
		}
	}
	public boolean mineBlock(ItemStack var1, Level var2, BlockState var3, BlockPos var4, LivingEntity var5) {
		if (!(var2.isClientSide)){
			var1.hurtAndBreak(1,var5,EquipmentSlot.MAINHAND);
		}
		Block var6 = var3.getBlock();
		return (var3.is(BlockTags.LEAVES)) || (((var6) == (Blocks.COBWEB)) || (((var6) == (Blocks.TALL_GRASS)) || (((var6) == (Blocks.FERN)) || (((var6) == (Blocks.DEAD_BUSH)) || (((var6) == (Blocks.VINE)) || (((var6) == (Blocks.TRIPWIRE)) || ((var3.is(BlockTags.WOOL)) || (super.mineBlock(var1,var2,var3,var4,var5)))))))));
	}
	public boolean isCorrectToolForDrops(ItemStack var1, BlockState var2) {
		Block var3 = var2.getBlock();
		return ((var3) == (Blocks.COBWEB)) || (((var3) == (Blocks.REDSTONE_WIRE)) || ((var3) == (Blocks.TRIPWIRE)));
	}
	public float getDestroySpeed(ItemStack var1, BlockState var2) {
		if (((var2.getBlock()) != (Blocks.COBWEB)) && (!(var2.is(BlockTags.LEAVES)))){
			return (var2.is(BlockTags.WOOL)) ? (5F) : (super.getDestroySpeed(var1,var2));
		}else{
			return 15F;
		}
	}
	public InteractionResult interactLivingEntity(ItemStack var1, Player var2, LivingEntity var3, InteractionHand var4) {
		Random var10 = null;
		LivingEntity var9 = null;
		if (var3.level().isClientSide){
			return InteractionResult.PASS;
		}else{
			if (var3 instanceof IShearable){
				IShearable var5 = ((IShearable)(var3));
				BlockPos var6 = var3.blockPosition();
				if (var5.isShearable(var2,var1,var3.level(),var6)){
					List var7 = var5.onSheared(var2,var1,var3.level(),var6);
					Random var8 = new Random();
					var9 = var3;
					var10 = var8;
					final LivingEntity var9_f1 = var9;
					final Random var10_f2 = var10;
					Consumer<ItemStack> var11 = (l0) -> {
			ItemEntity lv2_3 = var9_f1.spawnAtLocation(l0,1F);
			lv2_3.setDeltaMovement(lv2_3.getDeltaMovement().add((double)(((var10_f2.nextFloat()) - (var10_f2.nextFloat())) * (0.1F)),(double)((var10_f2.nextFloat()) * (0.05F)),(double)(((var10_f2.nextFloat()) - (var10_f2.nextFloat())) * (0.1F))));
		};
					((java.lang.Iterable)(var7)).forEach((Consumer)(var11));
					var1.hurtAndBreak(1,var3,EquipmentSlot.MAINHAND);
				}
				return InteractionResult.SUCCESS;
			}else{
				return InteractionResult.PASS;
			}
		}
	}
}