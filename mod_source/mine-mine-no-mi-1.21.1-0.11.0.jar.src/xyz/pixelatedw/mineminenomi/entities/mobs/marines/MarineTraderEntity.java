package xyz.pixelatedw.mineminenomi.entities.mobs.marines;

import java.util.Optional;
import java.util.function.Function;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootTable;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.enums.Currency;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.entities.mobs.TraderEntity;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModLootTables;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;

public class MarineTraderEntity extends TraderEntity {
	public MarineTraderEntity(EntityType<? extends MarineTraderEntity> var1, Level var2) {
		super((EntityType)(var1),var2,MobsHelper.MARINE_TRADERS_TEXTURES);
		if (((var2) != (null)) && (!(var2.isClientSide))){
			this.getEntityStats().setFaction(((Faction)(ModFactions.MARINE.get())));
		}
	}
	public boolean canTrade(Player var1) {
		Function<EntityStatsData, Boolean> var2 = (l0) -> {
			return Boolean.valueOf((l0.isMarine()) || (l0.isBountyHunter()));
		};
		return ((Boolean)(((java.util.Optional)(EntityStatsData.get((LivingEntity)(var1)))).map((Function)(var2)).orElse(Boolean.valueOf(false)))).booleanValue();
	}
	public ResourceKey<LootTable> getTradeTable() {
		return ModLootTables.MARINE_TRADER;
	}
	public Component getTradeFailMessage() {
		return ModI18n.TRADER_NO_PIRATE;
	}
	public Currency getCurrency() {
		return Currency.BELLY;
	}
}