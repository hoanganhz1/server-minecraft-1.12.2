package xyz.pixelatedw.mineminenomi.init;

import java.util.Iterator;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.abilities.gasu.BlueSwordAbility;
import xyz.pixelatedw.mineminenomi.abilities.ittoryu.ShiShishiSonsonAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.enums.Currency;
import xyz.pixelatedw.mineminenomi.api.helpers.SoulboundItemHelper;
import xyz.pixelatedw.mineminenomi.api.items.components.RandomizedFruit;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.handlers.world.RandomizedFruitsHandler$Client;
import xyz.pixelatedw.mineminenomi.items.AbilityIconItem;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;

public class ModModelOverrides {
	// Fields
	public static final ResourceLocation SIZE_PROPERTY = ResourceLocation.parse("size");
	public static final ResourceLocation FILLED_PROPERTY = ResourceLocation.parse("filled");
	public static final ResourceLocation DEN_TYPE_PROPERTY = ResourceLocation.parse("den_type");
	public static final ResourceLocation OPEN_PROPERTY = ResourceLocation.parse("open");
	public static final ResourceLocation PULL_PROPERTY = ResourceLocation.parse("pull");
	public static final ResourceLocation PULLING_PROPERTY = ResourceLocation.parse("pulling");
	public static final ResourceLocation FRUIT_TYPE_PROPERTY = ResourceLocation.parse("type");
	public static final ResourceLocation HAKI_PROPERTY = ResourceLocation.parse("haki");
	public static final ResourceLocation SHEATHED_PROPERTY = ResourceLocation.parse("sheathed");
	public static final ResourceLocation APRIL_PROPERTY = ResourceLocation.parse("april");
	public static final ResourceLocation ABILITY_ICON_PROPERTY = ResourceLocation.fromNamespaceAndPath("mineminenomi","ability_icon");
	private static final ClampedItemPropertyFunction DEN_DEN_MUSHI;
	private static final ItemPropertyFunction BELLY_POUCH_SIZE;
	private static final ItemPropertyFunction EXTOL_POUCH_SIZE;
	private static final ItemPropertyFunction DEVIL_FRUITS_RANDOMIZER;
	private static final ClampedItemPropertyFunction SAKE_CUP_FILLED;
	private static final ClampedItemPropertyFunction SHEATHED_WEAPON;
	private static final ClampedItemPropertyFunction HAKI_WEAPON;
	private static final ClampedItemPropertyFunction UMBRELLA_OPEN;
	private static final ClampedItemPropertyFunction BLUE_SWORD_WEAPON;
	private static final ClampedItemPropertyFunction PULL;
	private static final ClampedItemPropertyFunction PULLING;
	private static final ClampedItemPropertyFunction LOLLIPOP;
	private static final ItemPropertyFunction ABILITY_ICON;

	public static void register() {
		Iterator var0 = ModValues.DEVIL_FRUITS.iterator();
		do{
			if (var0.hasNext()){
				ItemProperties.register((Item)(((AkumaNoMiItem)(var0.next()))),FRUIT_TYPE_PROPERTY,DEVIL_FRUITS_RANDOMIZER);
				continue;
			}else{
				break;
			}
		} while (true);
		ItemProperties.register(((Item)(ModItems.BELLY_POUCH.get())),SIZE_PROPERTY,BELLY_POUCH_SIZE);
		ItemProperties.register(((Item)(ModItems.EXTOL_POUCH.get())),SIZE_PROPERTY,EXTOL_POUCH_SIZE);
		ItemProperties.register(((Item)(ModItems.SAKE_CUP.get())),FILLED_PROPERTY,(ItemPropertyFunction)(SAKE_CUP_FILLED));
		ItemProperties.register(((Block)(ModBlocks.DEN_DEN_MUSHI.get())).asItem(),DEN_TYPE_PROPERTY,(ItemPropertyFunction)(DEN_DEN_MUSHI));
		ItemProperties.register(((Item)(ModItems.ABILITY_ICON.get())),ABILITY_ICON_PROPERTY,ABILITY_ICON);
		ItemProperties.register(((Item)(ModWeapons.UMBRELLA.get())),OPEN_PROPERTY,(ItemPropertyFunction)(UMBRELLA_OPEN));
		ItemProperties.register(((Item)(ModWeapons.SORCERY_CLIMA_TACT.get())),OPEN_PROPERTY,(ItemPropertyFunction)(SHEATHED_WEAPON));
		ItemProperties.register(((Item)(ModWeapons.KUJA_BOW.get())),PULL_PROPERTY,(ItemPropertyFunction)(PULL));
		ItemProperties.register(((Item)(ModWeapons.KUJA_BOW.get())),PULLING_PROPERTY,(ItemPropertyFunction)(PULLING));
		ItemProperties.register(((Item)(ModWeapons.KABUTO.get())),PULL_PROPERTY,(ItemPropertyFunction)(PULL));
		ItemProperties.register(((Item)(ModWeapons.KABUTO.get())),PULLING_PROPERTY,(ItemPropertyFunction)(PULLING));
		ItemProperties.register(((Item)(ModWeapons.BLACK_KABUTO.get())),PULL_PROPERTY,(ItemPropertyFunction)(PULL));
		ItemProperties.register(((Item)(ModWeapons.BLACK_KABUTO.get())),PULLING_PROPERTY,(ItemPropertyFunction)(PULLING));
		ItemProperties.register(((Item)(ModWeapons.GINGA_PACHINKO.get())),PULL_PROPERTY,(ItemPropertyFunction)(PULL));
		ItemProperties.register(((Item)(ModWeapons.GINGA_PACHINKO.get())),PULLING_PROPERTY,(ItemPropertyFunction)(PULLING));
		ItemProperties.register(((Item)(ModWeapons.PIPE.get())),HAKI_PROPERTY,(ItemPropertyFunction)(HAKI_WEAPON));
		ItemProperties.register(((Item)(ModWeapons.SCISSORS.get())),HAKI_PROPERTY,(ItemPropertyFunction)(HAKI_WEAPON));
		ItemProperties.register(((Item)(ModWeapons.KIKOKU.get())),HAKI_PROPERTY,(ItemPropertyFunction)(HAKI_WEAPON));
		ItemProperties.register(((Item)(ModWeapons.KIKOKU.get())),SHEATHED_PROPERTY,(ItemPropertyFunction)(SHEATHED_WEAPON));
		ItemProperties.register(((Item)(ModWeapons.KIRIBACHI.get())),HAKI_PROPERTY,(ItemPropertyFunction)(HAKI_WEAPON));
		ItemProperties.register(((Item)(ModWeapons.YORU.get())),HAKI_PROPERTY,(ItemPropertyFunction)(HAKI_WEAPON));
		ItemProperties.register(((Item)(ModWeapons.MURAKUMOGIRI.get())),HAKI_PROPERTY,(ItemPropertyFunction)(HAKI_WEAPON));
		ItemProperties.register(((Item)(ModWeapons.HOOK.get())),HAKI_PROPERTY,(ItemPropertyFunction)(HAKI_WEAPON));
		ItemProperties.register(((Item)(ModWeapons.HOOK.get())),SHEATHED_PROPERTY,(ItemPropertyFunction)(SHEATHED_WEAPON));
		ItemProperties.register(((Item)(ModWeapons.JITTE.get())),HAKI_PROPERTY,(ItemPropertyFunction)(HAKI_WEAPON));
		ItemProperties.register(((Item)(ModWeapons.NONOSAMA_STAFF.get())),HAKI_PROPERTY,(ItemPropertyFunction)(HAKI_WEAPON));
		ItemProperties.register(((Item)(ModWeapons.NONOSAMA_TRIDENT.get())),HAKI_PROPERTY,(ItemPropertyFunction)(HAKI_WEAPON));
		ItemProperties.register(((Item)(ModWeapons.HAMMER_5T.get())),HAKI_PROPERTY,(ItemPropertyFunction)(HAKI_WEAPON));
		ItemProperties.register(((Item)(ModWeapons.TONFA.get())),HAKI_PROPERTY,(ItemPropertyFunction)(HAKI_WEAPON));
		ItemProperties.register(((Item)(ModWeapons.ACES_KNIFE.get())),HAKI_PROPERTY,(ItemPropertyFunction)(HAKI_WEAPON));
		ItemProperties.register(((Item)(ModWeapons.MIHAWKS_KNIFE.get())),HAKI_PROPERTY,(ItemPropertyFunction)(HAKI_WEAPON));
		ItemProperties.register(((Item)(ModWeapons.MIHAWKS_KNIFE.get())),SHEATHED_PROPERTY,(ItemPropertyFunction)(SHEATHED_WEAPON));
		ItemProperties.register(((Item)(ModWeapons.SANDAI_KITETSU.get())),HAKI_PROPERTY,(ItemPropertyFunction)(HAKI_WEAPON));
		ItemProperties.register(((Item)(ModWeapons.SANDAI_KITETSU.get())),SHEATHED_PROPERTY,(ItemPropertyFunction)(SHEATHED_WEAPON));
		ItemProperties.register(((Item)(ModWeapons.WADO_ICHIMONJI.get())),HAKI_PROPERTY,(ItemPropertyFunction)(HAKI_WEAPON));
		ItemProperties.register(((Item)(ModWeapons.WADO_ICHIMONJI.get())),SHEATHED_PROPERTY,(ItemPropertyFunction)(SHEATHED_WEAPON));
		ItemProperties.register(((Item)(ModWeapons.NIDAI_KITETSU.get())),HAKI_PROPERTY,(ItemPropertyFunction)(HAKI_WEAPON));
		ItemProperties.register(((Item)(ModWeapons.NIDAI_KITETSU.get())),SHEATHED_PROPERTY,(ItemPropertyFunction)(SHEATHED_WEAPON));
		ItemProperties.register(((Item)(ModWeapons.SHUSUI.get())),HAKI_PROPERTY,(ItemPropertyFunction)(HAKI_WEAPON));
		ItemProperties.register(((Item)(ModWeapons.SHUSUI.get())),SHEATHED_PROPERTY,(ItemPropertyFunction)(SHEATHED_WEAPON));
		ItemProperties.register(((Item)(ModWeapons.ENMA.get())),HAKI_PROPERTY,(ItemPropertyFunction)(HAKI_WEAPON));
		ItemProperties.register(((Item)(ModWeapons.ENMA.get())),SHEATHED_PROPERTY,(ItemPropertyFunction)(SHEATHED_WEAPON));
		ItemProperties.register(((Item)(ModWeapons.AME_NO_HABAKIRI.get())),HAKI_PROPERTY,(ItemPropertyFunction)(HAKI_WEAPON));
		ItemProperties.register(((Item)(ModWeapons.AME_NO_HABAKIRI.get())),SHEATHED_PROPERTY,(ItemPropertyFunction)(SHEATHED_WEAPON));
		ItemProperties.register(((Item)(ModWeapons.SOUL_SOLID.get())),HAKI_PROPERTY,(ItemPropertyFunction)(HAKI_WEAPON));
		ItemProperties.register(((Item)(ModWeapons.SOUL_SOLID.get())),SHEATHED_PROPERTY,(ItemPropertyFunction)(SHEATHED_WEAPON));
		ItemProperties.register(((Item)(ModWeapons.DURANDAL.get())),HAKI_PROPERTY,(ItemPropertyFunction)(HAKI_WEAPON));
		ItemProperties.register(((Item)(ModWeapons.DURANDAL.get())),SHEATHED_PROPERTY,(ItemPropertyFunction)(SHEATHED_WEAPON));
		ItemProperties.register(((Item)(ModWeapons.MACE.get())),HAKI_PROPERTY,(ItemPropertyFunction)(HAKI_WEAPON));
		ItemProperties.register(((Item)(ModWeapons.DAISENSO.get())),HAKI_PROPERTY,(ItemPropertyFunction)(HAKI_WEAPON));
		ItemProperties.register(((Item)(ModWeapons.MOGURA.get())),HAKI_PROPERTY,(ItemPropertyFunction)(HAKI_WEAPON));
		ItemProperties.register(((Item)(ModWeapons.DALTONS_SPADE.get())),HAKI_PROPERTY,(ItemPropertyFunction)(HAKI_WEAPON));
		ItemProperties.register(((Item)(ModWeapons.ACE.get())),HAKI_PROPERTY,(ItemPropertyFunction)(HAKI_WEAPON));
		ItemProperties.register(((Item)(ModWeapons.BLUE_SWORD.get())),SHEATHED_PROPERTY,(ItemPropertyFunction)(BLUE_SWORD_WEAPON));
		ItemProperties.register(((Item)(ModWeapons.AXE.get())),HAKI_PROPERTY,(ItemPropertyFunction)(HAKI_WEAPON));
		ItemProperties.register(((Item)(ModWeapons.SPEAR.get())),HAKI_PROPERTY,(ItemPropertyFunction)(HAKI_WEAPON));
		ItemProperties.register(((Item)(ModWeapons.KATANA.get())),HAKI_PROPERTY,(ItemPropertyFunction)(HAKI_WEAPON));
		ItemProperties.register(((Item)(ModWeapons.DAGGER.get())),HAKI_PROPERTY,(ItemPropertyFunction)(HAKI_WEAPON));
		ItemProperties.register(((Item)(ModWeapons.CUTLASS.get())),HAKI_PROPERTY,(ItemPropertyFunction)(HAKI_WEAPON));
		ItemProperties.register(((Item)(ModWeapons.BROADSWORD.get())),HAKI_PROPERTY,(ItemPropertyFunction)(HAKI_WEAPON));
		ItemProperties.register(((Item)(ModWeapons.BISENTO.get())),HAKI_PROPERTY,(ItemPropertyFunction)(HAKI_WEAPON));
		ItemProperties.register(((Item)(ModWeapons.HASSAIKAI.get())),HAKI_PROPERTY,(ItemPropertyFunction)(HAKI_WEAPON));
		ItemProperties.register(((Item)(ModWeapons.GRYPHON.get())),HAKI_PROPERTY,(ItemPropertyFunction)(HAKI_WEAPON));
		ItemProperties.register(((Item)(ModWeapons.SAMEKIRI_BOCHO.get())),HAKI_PROPERTY,(ItemPropertyFunction)(HAKI_WEAPON));
		ItemProperties.register(((Item)(ModWeapons.CHAKRAM.get())),HAKI_PROPERTY,(ItemPropertyFunction)(HAKI_WEAPON));
		if (WyHelper.isAprilFirst()){
			ItemProperties.register(((Item)(ModItems.CIGAR.get())),APRIL_PROPERTY,(ItemPropertyFunction)(LOLLIPOP));
			ItemProperties.register(((Item)(ModItems.CIGARETTE.get())),APRIL_PROPERTY,(ItemPropertyFunction)(LOLLIPOP));
			ItemProperties.register(((Item)(ModItems.SMOKING_PIPE.get())),APRIL_PROPERTY,(ItemPropertyFunction)(LOLLIPOP));
			ItemProperties.register(((Item)(ModItems.THREE_CIGARS.get())),APRIL_PROPERTY,(ItemPropertyFunction)(LOLLIPOP));
		}
	}
	static  {
		ClampedItemPropertyFunction var0 = (l0, l1, l2, l3) -> {
			return ((float)(((Integer)(l0.getOrDefault(ModDataComponents.TYPE,Integer.valueOf(0)))).intValue())) / (2F);
		};
		DEN_DEN_MUSHI = var0;
		ItemPropertyFunction var1 = (l0, l1, l2, l3) -> {
			if ((l2) == (null)){
				return 0F;
			}else{
				long lv2_4 = ((Long)(l0.getOrDefault(ModDataComponents.BELLY,Long.valueOf(0L)))).longValue();
				int lv2_5 = 0;
				if (((lv2_4) > (1000L)) && ((lv2_4) < (5000L))){
					lv2_5 = 1;
				}else{
					if ((lv2_4) >= (5000L)){
						lv2_5 = 2;
					}
				}
				return (float)(lv2_5);
			}
		};
		BELLY_POUCH_SIZE = var1;
		ItemPropertyFunction var2 = (l0, l1, l2, l3) -> {
			if ((l2) == (null)){
				return 0F;
			}else{
				long lv3_4 = Currency.getBellyFromExtol(((Long)(l0.getOrDefault(ModDataComponents.EXTOL,Long.valueOf(0L)))).longValue());
				int lv3_5 = 0;
				if (((lv3_4) > (1000L)) && ((lv3_4) < (5000L))){
					lv3_5 = 1;
				}else{
					if ((lv3_4) >= (5000L)){
						lv3_5 = 2;
					}
				}
				return (float)(lv3_5);
			}
		};
		EXTOL_POUCH_SIZE = var2;
		ItemPropertyFunction var3 = (l0, l1, l2, l3) -> {
			if (((l0) != (null)) && (!(l0.isEmpty()))){
				Item lv4_4 = l0.getItem();
				if (lv4_4 instanceof AkumaNoMiItem){
					AkumaNoMiItem lv4_5 = ((AkumaNoMiItem)(lv4_4));
					if (!(RandomizedFruitsHandler$Client.HAS_RANDOMIZED_FRUIT)){
						l0.remove(ModDataComponents.RANDOMIZED_FRUIT);
						return 0F;
					}else{
						if (((l1) == (null)) && ((l2) != (null))){
							l1 = ((ClientLevel)(l2.level()));
						}
						lv4_5.applyRandomness((Level)(l1),l0);
						return (float)(((RandomizedFruit)(l0.getOrDefault(ModDataComponents.RANDOMIZED_FRUIT,RandomizedFruit.DEFAULT))).shape());
					}
				}else{
					return 1F;
				}
			}else{
				return 1F;
			}
		};
		DEVIL_FRUITS_RANDOMIZER = var3;
		ClampedItemPropertyFunction var4 = (l0, l1, l2, l3) -> {
			return (float)((SoulboundItemHelper.hasOwner(l0)) ? (1) : (0));
		};
		SAKE_CUP_FILLED = var4;
		ClampedItemPropertyFunction var5 = (l0, l1, l2, l3) -> {
			if ((l2) == (null)){
				return 1F;
			}else{
				boolean lv6_4 = ItemStack.isSameItem(l2.getMainHandItem(),l0);
				boolean lv6_5 = ItemStack.isSameItem(l2.getOffhandItem(),l0);
				int lv6_6 = ((lv6_4) || (lv6_5)) ? (1) : (0);
				AbilityData lv6_7 = ((AbilityData)(((java.util.Optional)(AbilityData.get(l2))).orElse((Object)(null))));
				if (((lv6_6) != (0)) && ((lv6_7) != (null))){
					ShiShishiSonsonAbility lv6_8 = ((ShiShishiSonsonAbility)(lv6_7.getEquippedAbility(((AbilityCore)(ShiShishiSonsonAbility.INSTANCE.get())))));
					if (((lv6_8) != (null)) && (lv6_8.isCharging())){
						return 1F;
					}
				}
				return ((lv6_6) != (0)) ? (0F) : (1F);
			}
		};
		SHEATHED_WEAPON = var5;
		ClampedItemPropertyFunction var6 = (l0, l1, l2, l3) -> {
			if ((l2) == (null)){
				return 0F;
			}else{
				return (l0.has(ModDataComponents.IMBUING)) ? (1F) : (0F);
			}
		};
		HAKI_WEAPON = var6;
		ClampedItemPropertyFunction var7 = (l0, l1, l2, l3) -> {
			if ((l2) == (null)){
				return 0F;
			}else{
				int lv8_4 = ((l2.getMainHandItem()) == (l0)) ? (1) : (0);
				int lv8_5 = ((l2.getOffhandItem()) == (l0)) ? (1) : (0);
				int lv8_6 = ((!(l2.onGround())) && ((l2.getDeltaMovement().y) < (0D))) ? (1) : (0);
				BlockPos lv8_7 = l2.blockPosition();
				BlockPos lv8_8 = new BlockPos(lv8_7.getX(),(int)(l2.getBoundingBox().maxY),lv8_7.getZ());
				int lv8_9 = ((l2.level().isRainingAt(lv8_7)) || (l2.level().isRainingAt(lv8_8))) ? (1) : (0);
				int lv8_10 = ((!(l2.getMainHandItem().isEmpty())) && ((l2.getMainHandItem().getItem()) == (ModWeapons.UMBRELLA.get()))) ? (1) : (0);
				int lv8_11 = ((!(l2.getOffhandItem().isEmpty())) && ((l2.getOffhandItem().getItem()) == (ModWeapons.UMBRELLA.get()))) ? (1) : (0);
				int lv8_12 = (((lv8_10) != (0)) || ((lv8_11) != (0))) ? (1) : (0);
				return ((lv8_4) == (0)) ? (((lv8_5) != (0)) ? (((lv8_6) == (0)) ? (((lv8_9) != (0)) ? (((lv8_12) != (0)) ? (1F) : (0F)) : (0F)) : (1F)) : (((lv8_9) != (0)) ? (((lv8_12) != (0)) ? (1F) : (0F)) : (0F))) : (((lv8_6) == (0)) ? (((lv8_9) != (0)) ? (((lv8_12) != (0)) ? (1F) : (0F)) : (0F)) : (1F));
			}
		};
		UMBRELLA_OPEN = var7;
		ClampedItemPropertyFunction var8 = (l0, l1, l2, l3) -> {
			if ((l2) == (null)){
				return 1F;
			}else{
				boolean lv10_4 = ItemStack.isSameItem(l2.getMainHandItem(),l0);
				boolean lv10_5 = ItemStack.isSameItem(l2.getOffhandItem(),l0);
				Function<AbilityData, Boolean> lv10_6 = (l2_0) -> {
				return Boolean.valueOf(l2_0.hasUnlockedAbility(((AbilityCore)(BlueSwordAbility.INSTANCE.get()))));
			};
				boolean lv10_7 = ((Boolean)(((java.util.Optional)(AbilityData.get(l2))).map((Function)(lv10_6)).orElse(Boolean.valueOf(false)))).booleanValue();
				return (!(lv10_4)) ? ((lv10_5) ? ((lv10_7) ? (0F) : (1F)) : (1F)) : ((lv10_7) ? (0F) : (1F));
			}
		};
		BLUE_SWORD_WEAPON = var8;
		ClampedItemPropertyFunction var9 = (l0, l1, l2, l3) -> {
			if ((l2) == (null)){
				return 0F;
			}else{
				return ((l2.getUseItem()) != (l0)) ? (0F) : (((float)((l0.getUseDuration(l2)) - (l2.getUseItemRemainingTicks()))) / (20F));
			}
		};
		PULL = var9;
		ClampedItemPropertyFunction var10 = (l0, l1, l2, l3) -> {
			return ((l2) != (null)) ? ((l2.isUsingItem()) ? (((l2.getUseItem()) == (l0)) ? (1F) : (0F)) : (0F)) : (0F);
		};
		PULLING = var10;
		ClampedItemPropertyFunction var11 = (l0, l1, l2, l3) -> {
			return (WyHelper.isAprilFirst()) ? (1F) : (0F);
		};
		LOLLIPOP = var11;
		ItemPropertyFunction var12 = (l0, l1, l2, l3) -> {
			if (((l0) != (null)) && (!(l0.isEmpty()))){
				ResourceLocation lv14_4 = ((ResourceLocation)(l0.getOrDefault(ModDataComponents.ABILITY_ICON,null)));
				if ((lv14_4) == (null)){
					return 0F;
				}else{
					return (float)(((Integer)(AbilityIconItem.ICONS.getOrDefault(lv14_4,Integer.valueOf(0)))).intValue());
				}
			}else{
				return 0F;
			}
		};
		ABILITY_ICON = var12;
	}
}