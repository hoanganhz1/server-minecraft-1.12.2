package xyz.pixelatedw.mineminenomi.packets.client;

import com.mojang.datafixers.util.Function4;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.handling.ServerPayloadContext;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.ICharacterCreatorEntry;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.enums.TrainingPointType;
import xyz.pixelatedw.mineminenomi.api.events.entity.SetPlayerDetailsEvent;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.data.triggers.SelectFactionTrigger;
import xyz.pixelatedw.mineminenomi.data.triggers.SelectRaceTrigger;
import xyz.pixelatedw.mineminenomi.data.triggers.SelectStyleTrigger;
import xyz.pixelatedw.mineminenomi.handlers.ability.ProgressionHandler;
import xyz.pixelatedw.mineminenomi.init.ModAdvancements;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.items.CharacterCreatorItem;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.IServerPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncAbilityDataPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncEntityStatsPacket;

public final record CFinishCCPacket(int factionId, int raceId, int styleId, int subRaceId) {
	// Fields
	public static final CustomPacketPayload.Type<CFinishCCPacket> TYPE = new CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath("mineminenomi","c_finish_cc"));
	public static final StreamCodec<FriendlyByteBuf, CFinishCCPacket> STREAM_CODEC;

	public void handle(ServerPayloadContext var1) {
		ServerPlayer var2 = var1.player();
		EntityStatsData var3 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get((LivingEntity)(var2)))).orElse((Object)(null))));
		if ((var3) == (null)){
			return;
		}else{
			int var4 = ((!(var2.getMainHandItem().isEmpty())) && (((java.lang.Object)(var2.getMainHandItem().getItem())).equals(ModItems.CHARACTER_CREATOR.get()))) ? (1) : (0);
			int var5 = ((var3.getFaction().isEmpty()) || ((var3.getRace().isEmpty()) || (var3.getFightingStyle().isEmpty()))) ? (1) : (0);
			if (((var4) == (0)) && ((var5) == (0))){
				return;
			}else{
				List var6 = WyRegistry.FACTIONS.stream().filter((Predicate)(((Predicate<Faction>)(ICharacterCreatorEntry::isInBook)))).sorted((Comparator)(((Comparator<Faction>)(ICharacterCreatorEntry::compareTo)))).toList();
				List var7 = WyRegistry.RACES.stream().filter((Predicate)(((Predicate<Race>)(Race::isMainRace)))).filter((Predicate)(((Predicate<Race>)(ICharacterCreatorEntry::isInBook)))).sorted((Comparator)(((Comparator<Race>)(ICharacterCreatorEntry::compareTo)))).toList();
				List var8 = WyRegistry.FIGHTING_STYLES.stream().filter((Predicate)(((Predicate<FightingStyle>)(ICharacterCreatorEntry::isInBook)))).sorted((Comparator)(((Comparator<FightingStyle>)(ICharacterCreatorEntry::compareTo)))).toList();
				int var9 = (this.factionId) % ((var6.size()) + (1));
				int var10 = (this.raceId) % ((var7.size()) + (1));
				int var11 = (this.styleId) % ((var8.size()) + (1));
				Faction var12 = null;
				if ((var9) == (0)){
					var12 = ((Faction)(var6.get(var2.getRandom().nextInt(var6.size()))));
				}else{
					var12 = ((Faction)(var6.get((var9) - (1))));
				}
				var3.setFaction(var12);
				((SelectFactionTrigger)(ModAdvancements.SELECT_FACTION.get())).trigger(var2,var12.getRegistryName());
				Race var13 = null;
				if (((var10) != (0)) && (!(ServerConfig.getRaceRandomizer()))){
					var13 = ((Race)(var7.get((var10) - (1))));
				}else{
					var13 = ((Race)(var7.get(var2.getRandom().nextInt(var7.size()))));
				}
				var3.setRace(var13);
				((SelectRaceTrigger)(ModAdvancements.SELECT_RACE.get())).trigger(var2,var13.getRegistryName());
				List var14 = var13.getSubRaces().stream().map((Function)(((Function<Supplier<Race>, Race>)(Supplier::get)))).filter((Predicate)(((Predicate<Race>)(ICharacterCreatorEntry::isInBook)))).sorted((Comparator)(((Comparator<Race>)(ICharacterCreatorEntry::compareTo)))).toList();
				if ((var13.hasSubRaces()) && ((var14.size()) > (0))){
					int var15 = (this.subRaceId) % (var13.getSubRaces().size());
					Race var16 = null;
					if (ServerConfig.getAllowSubRaceSelect()){
						var16 = ((Race)(var14.get(var15)));
					}else{
						var16 = ((Race)(var14.get(var2.getRandom().nextInt(var14.size()))));
					}
					var3.setSubRace(var16);
				}
				FightingStyle var15_1 = null;
				if ((var11) == (0)){
					var15_1 = ((FightingStyle)(var8.get(var2.getRandom().nextInt(var8.size()))));
				}else{
					var15_1 = ((FightingStyle)(var8.get((var11) - (1))));
				}
				var3.setFightingStyle(var15_1);
				((SelectStyleTrigger)(ModAdvancements.SELECT_STYLE.get())).trigger(var2,var15_1.getRegistryName());
				if (var3.isCyborg()){
					var3.setCola(var3.getMaxCola());
					var3.alterTrainingPoints(TrainingPointType.TECHNOLOGY,10);
				}
				Iterator var16_1 = var2.getInventory().items.iterator();
				do{
					if (var16_1.hasNext()){
						ItemStack var17 = ((ItemStack)(var16_1.next()));
						if (((var17) != (null)) && (var17.getItem() instanceof CharacterCreatorItem)){
							var2.getInventory().removeItem(var17);
							continue;
						}else{
							continue;
						}
					}else{
						break;
					}
				} while (true);
				AbilityData var17_1 = ((AbilityData)(((java.util.Optional)(AbilityData.get((LivingEntity)(var2)))).orElse((Object)(null))));
				if ((var17_1) != (null)){
					Iterator var18 = var17_1.getNodes().iterator();
					do{
						if (var18.hasNext()){
							((AbilityNode)(var18.next())).lockNode((LivingEntity)(var2));
							continue;
						}else{
							break;
						}
					} while (true);
				}
				SetPlayerDetailsEvent var18_1 = new SetPlayerDetailsEvent((Player)(var2));
				NeoForge.EVENT_BUS.post((Event)(var18_1));
				ModNetwork.sendToAllTrackingAndSelf(new SSyncEntityStatsPacket((LivingEntity)(var2)),(LivingEntity)(var2));
				ProgressionHandler.checkAllForNewUnlocks((Player)(var2),false);
				ModNetwork.sendTo(new SSyncAbilityDataPacket((LivingEntity)(var2)),(Player)(var2));
				return;
			}
		}
	}
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return (CustomPacketPayload.Type<? extends CustomPacketPayload>) (CustomPacketPayload.Type) (TYPE);
	}
	static  {
		Function<CFinishCCPacket, Integer> var0 = CFinishCCPacket::factionId;
		Function<CFinishCCPacket, Integer> var1 = CFinishCCPacket::raceId;
		Function<CFinishCCPacket, Integer> var2 = CFinishCCPacket::styleId;
		Function<CFinishCCPacket, Integer> var3 = CFinishCCPacket::subRaceId;
		STREAM_CODEC = (StreamCodec) (StreamCodec.composite(ByteBufCodecs.INT,var0,ByteBufCodecs.INT,var1,ByteBufCodecs.INT,var2,ByteBufCodecs.INT,var3,(Function4)(((com.mojang.datafixers.util.Function4)(CFinishCCPacket::new)))));
	}
}