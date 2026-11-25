package xyz.pixelatedw.mineminenomi.packets.client.ui;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.ServerPayloadContext;
import xyz.pixelatedw.mineminenomi.api.crafting.MultiChannelDyeRecipe;
import xyz.pixelatedw.mineminenomi.api.items.components.MultiChannelItemColor;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;
import xyz.pixelatedw.mineminenomi.packets.IServerPacket;

public final record CSetDyeLayerPacket(int layer) {
	// Fields
	public static final CustomPacketPayload.Type<CSetDyeLayerPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","c_set_dye_layer"));
	public static final StreamCodec<FriendlyByteBuf, CSetDyeLayerPacket> STREAM_CODEC;

	public void handle(ServerPayloadContext var1) {
		ServerPlayer var2 = var1.player();
		AbstractContainerMenu var3 = var2.containerMenu;
		if (var3 instanceof CraftingMenu){
			CraftingMenu var4 = ((CraftingMenu)(var3));
			Container var5 = ((Slot)(var4.slots.get(1))).container;
			if (var5 instanceof CraftingContainer){
				CraftingContainer var6 = ((CraftingContainer)(var5));
				Optional var7 = var2.getServer().getRecipeManager().getRecipeFor(RecipeType.CRAFTING,(RecipeInput)(var6.asCraftInput()),var2.level());
				if (var7.isPresent()){
					ItemStack var8 = ((Slot)(var4.slots.get(0))).getItem();
					Recipe var9 = ((RecipeHolder)(var7.get())).value();
					if (var9 instanceof MultiChannelDyeRecipe){
						MultiChannelDyeRecipe var10 = ((MultiChannelDyeRecipe)(var9));
						MultiChannelItemColor var11 = ((MultiChannelItemColor)(var8.get(ModDataComponents.MULTI_CHANNEL_DYES)));
						if ((var11) != (null)){
							var10.setLayer(Math.min(this.layer,var11.colors().length));
							var10.assemble(var6.asCraftInput(),(HolderLookup.Provider)(var2.level().registryAccess()));
							var4.slotsChanged((Container)(var6));
						}else{
							return;
						}
					}else{
						return;
					}
				}else{
					return;
				}
			}else{
				return;
			}
		}
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		Function<CSetDyeLayerPacket, Integer> var0 = CSetDyeLayerPacket::layer;
		STREAM_CODEC = (StreamCodec) (StreamCodec.composite(ByteBufCodecs.INT,var0,(Function)(((Function<Integer, CSetDyeLayerPacket>)(CSetDyeLayerPacket::new)))));
	}
}