package xyz.pixelatedw.mineminenomi.entities.mobs.civilians;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootTable;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.enums.Currency;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.entities.mobs.TraderEntity;
import xyz.pixelatedw.mineminenomi.init.ModFactions;
import xyz.pixelatedw.mineminenomi.init.ModLootTables;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.SOpenTraderScreenPacket;

public class SkypieanTraderEntity extends TraderEntity {
	// Fields
	public static final float EXTOL_MULTIPLIER = 10F;
	private int dirtTradesLeft;

	public SkypieanTraderEntity(EntityType<? extends SkypieanTraderEntity> var1, Level var2) {
		super((EntityType)(var1),var2,MobsHelper.SKYPEAN_TRADERS_TEXTURES);
		this.dirtTradesLeft = 120;
		this.setCanBuyFromPlayers();
	}
	public void registerGoals() {
		Consumer<EntityStatsData> var1 = (l0) -> {
			l0.setFaction(((Faction)(ModFactions.CIVILIAN.get())));
			l0.setRace(((Race)(ModRaces.SKYPIEAN.get())));
		};
		((java.util.Optional)(EntityStatsData.get((LivingEntity)(this)))).ifPresent((Consumer)(var1));
	}
	public boolean canTrade(Player var1) {
		return true;
	}
	public ResourceKey<LootTable> getTradeTable() {
		return ModLootTables.SKYPIEAN_TRADER;
	}
	public void addAdditionalSaveData(CompoundTag var1) {
		super.addAdditionalSaveData(var1);
		var1.putInt("tradesLeft",this.dirtTradesLeft);
	}
	public void readAdditionalSaveData(CompoundTag var1) {
		super.readAdditionalSaveData(var1);
		this.dirtTradesLeft = var1.getInt("tradesLeft");
	}
	protected InteractionResult mobInteract(Player var1, InteractionHand var2) {
		if (!(var1.level().isClientSide)){
			ModNetwork.sendTo(new SOpenTraderScreenPacket(this.getId(),this.tradeEntries,this.dirtTradesLeft),var1);
			return InteractionResult.SUCCESS;
		}else{
			return InteractionResult.PASS;
		}
	}
	public void removeTradesLeft(int var1) {
		if (((this.dirtTradesLeft) - (var1)) > (0)){
			this.dirtTradesLeft = (this.dirtTradesLeft) - (var1);
		}else{
			this.dirtTradesLeft = 0;
		}
	}
	public void setTradesLeft(int var1) {
		this.dirtTradesLeft = var1;
	}
	public int getTradesLeft() {
		return this.dirtTradesLeft;
	}
	public long getExtolLeftInStock() {
		return (long)(Math.round(((float)(Currency.getExtolFromBelly((long)(this.dirtTradesLeft)))) * (10F)));
	}
	public Component getTradeFailMessage() {
		return Component.empty();
	}
	public Currency getCurrency() {
		return Currency.EXTOL;
	}
}