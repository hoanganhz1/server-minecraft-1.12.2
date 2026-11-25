package xyz.pixelatedw.mineminenomi.packets.client.trade;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.ServerPayloadContext;
import xyz.pixelatedw.mineminenomi.api.enums.Currency;
import xyz.pixelatedw.mineminenomi.api.enums.StatChangeSource;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.entities.mobs.civilians.SkypieanTraderEntity;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.IServerPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncEntityStatsPacket;

public final record CSellToTraderPacket(int traderId, int amount) {
	// Fields
	public static final CustomPacketPayload.Type<CSellToTraderPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","c_sell_to_trader"));
	public static final StreamCodec<RegistryFriendlyByteBuf, CSellToTraderPacket> STREAM_CODEC;

	public void handle(ServerPayloadContext var1) {
		ServerPlayer var2 = var1.player();
		Entity var3 = var2.level().getEntity(this.traderId);
		if ((var2.distanceToSqr(var3)) > (25D)){
			return;
		}else{
			if (((var3) != (null)) && (var3 instanceof SkypieanTraderEntity)){
				SkypieanTraderEntity var4 = ((SkypieanTraderEntity)(var3));
				EntityStatsData var5 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get((LivingEntity)(var2)))).orElse((Object)(null))));
				if ((var5) == (null)){
					return;
				}else{
					int var6 = 0;
					ArrayList var7 = new ArrayList();
					int var8 = 0;
					do{
						if ((var8) < (var2.getInventory().getContainerSize())){
							ItemStack var9 = var2.getInventory().getItem(var8);
							if (((var9) != (null)) && ((!(var9.isEmpty())) && (var9.is(ItemTags.DIRT)))){
								var6 = (var6) + (var9.getCount());
								if (!(((java.util.List)(var7)).contains((Object)(var9)))){
									var7.add(var9);
									var8++;
									continue;
								}else{
									var8++;
									continue;
								}
							}else{
								var8++;
								continue;
							}
						}else{
							break;
						}
					} while (true);
					var6 = Mth.clamp(var6,0,this.amount);
					if ((var6) >= (this.amount)){
						var8 = var6;
						Iterator var9_1 = var7.iterator();
						do{
							if (var9_1.hasNext()){
								ItemStack var10 = ((ItemStack)(var9_1.next()));
								if ((var8) <= (0)){
									break;
								}else{
									int var11 = var10.getCount();
									if (((var8) - (var11)) >= (0)){
										var8 = (var8) - (var11);
										var10.shrink(var11);
										continue;
									}else{
										int var12 = var8;
										var8 = (var8) - (var12);
										var10.shrink(var12);
										continue;
									}
								}
							}else{
								break;
							}
						} while (true);
						var4.removeTradesLeft(this.amount);
						var5.alterExtol((long)(Math.round(((float)(Currency.getExtolFromBelly((long)(var6)))) * (10F))),StatChangeSource.NATURAL);
						ModNetwork.sendTo(new SSyncEntityStatsPacket((LivingEntity)(var2),(EntityStatsData)(var5)),(Player)(var2));
					}else{
						return;
					}
				}
			}
			return;
		}
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		Function<CSellToTraderPacket, Integer> var0 = CSellToTraderPacket::traderId;
		Function<CSellToTraderPacket, Integer> var1 = CSellToTraderPacket::amount;
		STREAM_CODEC = (StreamCodec) (StreamCodec.composite(ByteBufCodecs.INT,var0,ByteBufCodecs.INT,var1,(BiFunction)(((BiFunction<Integer, Integer, CSellToTraderPacket>)(CSellToTraderPacket::new)))));
	}
}