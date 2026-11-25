package xyz.pixelatedw.mineminenomi.packets.client.trade;

import com.mojang.datafixers.util.Function3;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.ServerPayloadContext;
import xyz.pixelatedw.mineminenomi.api.TradeEntry;
import xyz.pixelatedw.mineminenomi.api.enums.Currency;
import xyz.pixelatedw.mineminenomi.api.enums.StatChangeSource;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.entities.mobs.TraderEntity;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.IServerPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncEntityStatsPacket;

public final record CBuyFromTraderPacket(int traderId, ItemStack stack, int amount) {
	// Fields
	public static final CustomPacketPayload.Type<CBuyFromTraderPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","c_buy_from_trader"));
	public static final StreamCodec<RegistryFriendlyByteBuf, CBuyFromTraderPacket> STREAM_CODEC;

	public void handle(ServerPayloadContext var1) {
		ItemStack var6 = null;
		ServerPlayer var2 = var1.player();
		Entity var3 = var2.level().getEntity(this.traderId);
		if ((var3) == (null)){
			return;
		}else{
			if (var3 instanceof TraderEntity){
				TraderEntity var4 = ((TraderEntity)(var3));
				if ((!((var2.distanceToSqr((Entity)(var4))) > (25D))) && ((this.amount) > (0))){
					ItemStack var5 = this.stack;
					if (((var5) != (null)) && (!(var5.isEmpty()))){
						var6 = var5;
						final ItemStack var6_f1 = var6;
						Predicate<TradeEntry> var7 = (l0) -> {
			return ((java.lang.Object)(l0.itemStack().getItem())).equals((Object)(var6_f1.getItem()));
		};
						Optional var8 = var4.getTradingItems().stream().filter(var7).findFirst();
						if (!(var8.isPresent())){
							return;
						}else{
							TradeEntry var9 = ((TradeEntry)(var8.get()));
							if ((var2.getInventory().items.stream().filter((Predicate)(((Predicate<ItemStack>)(ItemStack::isEmpty)))).count()) < ((long)(Mth.ceil((float)((this.amount) / (64)))))){
								return;
							}else{
								if (((this.amount) > (var9.getCount())) && (!(var9.hasInfiniteStock()))){
									return;
								}else{
									EntityStatsData var10 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get((LivingEntity)(var2)))).orElse((Object)(null))));
									if ((var10) == (null)){
										return;
									}else{
										int var11 = (var9.getPrice()) * (this.amount);
										if ((((var4.getCurrency()) == (Currency.BELLY)) ? (var10.getBelly()) : (var10.getExtol())) < ((long)(var11))){
											return;
										}else{
											ItemStack var12 = new ItemStack((ItemLike)(var9.itemStack().getItem()));
											var12.setCount(this.amount);
											if (((Boolean)(var9.itemStack().getOrDefault(ModDataComponents.IS_CLONE,Boolean.valueOf(false)))).booleanValue()){
												var12.set(ModDataComponents.IS_CLONE,Boolean.valueOf(true));
											}
											if (!(var9.hasInfiniteStock())){
												int var13 = (var9.getCount()) - (this.amount);
												if ((var13) <= (0)){
													var4.getTradingItems().remove(var9);
												}else{
													var9.setCount(var13);
												}
											}
											var2.getInventory().add(var12);
											if ((var4.getCurrency()) == (Currency.BELLY)){
												var10.alterBelly((long)(-(var11)),StatChangeSource.NATURAL);
											}else{
												if ((var4.getCurrency()) == (Currency.EXTOL)){
													var10.alterExtol((long)(-(var11)),StatChangeSource.NATURAL);
												}
											}
											ModNetwork.sendTo(new SSyncEntityStatsPacket((LivingEntity)(var2)),(Player)(var2));
											var4.setTradingItems(var4.getTradingItems());
											return;
										}
									}
								}
							}
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
		Function<CBuyFromTraderPacket, Integer> var0 = CBuyFromTraderPacket::traderId;
		Function<CBuyFromTraderPacket, ItemStack> var1 = CBuyFromTraderPacket::stack;
		Function<CBuyFromTraderPacket, Integer> var2 = CBuyFromTraderPacket::amount;
		STREAM_CODEC = (StreamCodec) (StreamCodec.composite(ByteBufCodecs.INT,var0,ItemStack.STREAM_CODEC,var1,ByteBufCodecs.INT,var2,(Function3)(((com.mojang.datafixers.util.Function3)(CBuyFromTraderPacket::new)))));
	}
}