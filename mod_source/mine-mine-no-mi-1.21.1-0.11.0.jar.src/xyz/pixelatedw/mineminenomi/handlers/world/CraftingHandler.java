package xyz.pixelatedw.mineminenomi.handlers.world;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.inventory.CraftingScreen;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.api.DFEncyclopediaEntry;
import xyz.pixelatedw.mineminenomi.api.crafting.MultiChannelDyeRecipe;
import xyz.pixelatedw.mineminenomi.api.items.components.MultiChannelItemColor;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;
import xyz.pixelatedw.mineminenomi.items.DFEncyclopediaItem;
import xyz.pixelatedw.mineminenomi.items.FlagItem;
import xyz.pixelatedw.mineminenomi.items.WantedPosterItem;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.SToggleDyeLayersPacket;
import xyz.pixelatedw.mineminenomi.ui.components.DyeLayerComponent;

public class CraftingHandler {
	// Fields
	public static final ResourceLocation FLAG_UPGRADE_RECIPE = ResourceLocation.fromNamespaceAndPath("mineminenomi","flag_upgrade");
	public static final ResourceLocation WANTED_POSTER_UPGRADE_RECIPE = ResourceLocation.fromNamespaceAndPath("mineminenomi","wanted_poster_upgrade");
	public static final ResourceLocation ENCYCLOPEDIA_MERGE_RECIPE = ResourceLocation.fromNamespaceAndPath("mineminenomi","devil_fruit_encyclopedia_merge");

	@OnlyIn(value=Dist.CLIENT)
	public static void initNewDyeLayerWidget(Minecraft var0, CraftingScreen var1, Consumer<GuiEventListener> var2) {
		int var3 = var1.getGuiLeft();
		int var4 = ((var1.height) / (2)) - (59);
		var2.accept((GuiEventListener)(new DyeLayerComponent(var0,var1,var3,var4)));
	}
	public static boolean onGridChanged(AbstractContainerMenu var0, Level var1, Player var2, CraftingContainer var3, ResultContainer var4, @Nullable() RecipeHolder<CraftingRecipe> var5) {
		if ((!(var1.isClientSide)) && (var2 instanceof ServerPlayer)){
			ServerPlayer var6 = ((ServerPlayer)(var2));
			Optional var7 = EntityStatsData.get((LivingEntity)(var6));
			Optional var8 = var1.getServer().getRecipeManager().getRecipeFor(RecipeType.CRAFTING,(RecipeInput)(var3.asCraftInput()),var1,var5);
			ItemStack var9 = ItemStack.EMPTY;
			if (var8.isPresent()){
				ItemStack var12 = null;
				RecipeHolder var10 = ((RecipeHolder)(var8.get()));
				CraftingRecipe var11 = ((CraftingRecipe)(var10.value()));
				if ((!(var10.id().equals(FLAG_UPGRADE_RECIPE))) && (!(var10.id().equals(WANTED_POSTER_UPGRADE_RECIPE)))){
					int var13 = 0;
					if (var10.id().equals(ENCYCLOPEDIA_MERGE_RECIPE)){
						if (var4.setRecipeUsed(var1,var6,var10)){
							var12 = ItemStack.EMPTY;
							var13 = 0;
							do{
								if ((var13) < (var3.getContainerSize())){
									ItemStack var14 = var3.getItem(var13);
									if ((!(var14.isEmpty())) && (((java.lang.Object)(var14.getItem())).equals(ModItems.DEVIL_FRUIT_ENCYCLOPEDIA.get()))){
										if (var9.isEmpty()){
											var9 = var14.copy();
										}else{
											var12 = var14.copy();
										}
									}
									var13++;
									continue;
								}else{
									break;
								}
							} while (true);
							if ((!(var9.isEmpty())) && (!(var12.isEmpty()))){
								var9 = handleEncyclopediaMerge(var9,var12);
							}
						}
					}else{
						if (var11 instanceof MultiChannelDyeRecipe){
							MultiChannelDyeRecipe var12_1 = ((MultiChannelDyeRecipe)(var11));
							if (var4.setRecipeUsed(var1,var6,var10)){
								var12 = ItemStack.EMPTY;
								var13 = 0;
								do{
									if ((var13) < (var3.getContainerSize())){
										ItemStack var15 = var3.getItem(var13);
										if ((!(var15.isEmpty())) && (var15.has(ModDataComponents.MULTI_CHANNEL_DYES))){
											var12 = var15;
										}
										var13++;
										continue;
									}else{
										break;
									}
								} while (true);
								if (!(var12.isEmpty())){
									MultiChannelItemColor var15_1 = ((MultiChannelItemColor)(var12.get(ModDataComponents.MULTI_CHANNEL_DYES)));
									int var16 = Math.min(var12_1.getLayer(),(var15_1.colors().length) - (1));
									Consumer<EntityStatsData> var17 = (l0) -> {
			l0.setDyeLayerCheck(true);
		};
									((java.util.Optional)(var7)).ifPresent((Consumer)(var17));
									ModNetwork.sendTo(new SToggleDyeLayersPacket(var16,var15_1.colors().length,true),(Player)(var6));
								}
							}
						}
					}
				}else{
					if (var4.setRecipeUsed(var1,var6,var10)){
						ItemStack var12_1_1 = var3.getItem(4).copy();
						var9 = handleFlagUpgrades(var12_1_1);
					}
				}
				if (!(var9.isEmpty())){
					var4.setItem(0,var9);
					var6.connection.send((Packet)(new ClientboundContainerSetSlotPacket(var0.containerId,var0.incrementStateId(),0,var9)));
					return true;
				}else{
					return false;
				}
			}else{
				if (((Boolean)(var7.map((Function)(((Function<EntityStatsData, Boolean>)(EntityStatsData::isDyeLayerCheck)))).orElse(Boolean.valueOf(false)))).booleanValue()){
					Consumer<EntityStatsData> var10_1 = (l0) -> {
			l0.setDyeLayerCheck(false);
		};
					((java.util.Optional)(var7)).ifPresent((Consumer)(var10_1));
					ModNetwork.sendTo(new SToggleDyeLayersPacket(0,0,false),(Player)(var6));
				}else{
					return false;
				}
			}
		}
		return false;
	}
	private static ItemStack handleEncyclopediaMerge(ItemStack var0, ItemStack var1) {
		Iterator var2 = DFEncyclopediaItem.getEntries(var1).iterator();
		do{
			if (var2.hasNext()){
				DFEncyclopediaEntry var3 = ((DFEncyclopediaEntry)(var2.next()));
				DFEncyclopediaItem.addFruitClues(var0,var3.getDevilFruit().getRegistryKey(),var3);
				continue;
			}else{
				break;
			}
		} while (true);
		return var0;
	}
	private static ItemStack handleWantedPosterUpgrades(ItemStack var0) {
		if (WantedPosterItem.upgradeCanvasSize(var0)){
			return var0;
		}else{
			return ItemStack.EMPTY;
		}
	}
	private static ItemStack handleFlagUpgrades(ItemStack var0) {
		if (FlagItem.upgradeCanvasSize(var0)){
			return var0;
		}else{
			return ItemStack.EMPTY;
		}
	}
}