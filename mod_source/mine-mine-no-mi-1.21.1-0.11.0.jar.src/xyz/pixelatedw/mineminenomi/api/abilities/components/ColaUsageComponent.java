package xyz.pixelatedw.mineminenomi.api.abilities.components;

import java.util.Optional;
import java.util.UUID;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine$IDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityStat;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityStat$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.entity.SUpdateColaAmountPacket;

public class ColaUsageComponent extends AbilityComponent<IAbility> {
	// Fields
	private static final UUID COLA_BONUS_MANAGER_UUID = UUID.fromString("6c6757f6-23e3-4af1-bc32-7c18acb37327");
	private final BonusManager colaBonusManager;

	public static Ability$ICanUseEvent hasEnoughCola(int var0) {
		int var1 = var0;
		final int var1_f1 = var1;
		Ability$ICanUseEvent var2 = (l0, l1) -> {
			EntityStatsData lv1_3 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get(l0))).orElse((Object)(null))));
			if ((lv1_3) == (null)){
				return Result.fail(null);
			}else{
				ColaUsageComponent lv1_4 = ((ColaUsageComponent)(l1.getComponent(((AbilityComponentKey)(ModAbilityComponents.COLA_USAGE.get()))).orElse(null)));
				if ((lv1_4) == (null)){
					return Result.fail(null);
				}else{
					int lv1_5 = Math.round(lv1_4.colaBonusManager.applyBonus((float)(var1_f1)));
					if (((lv1_3.getCola()) - (lv1_5)) < (0)){
						return Result.fail(ModI18nAbilities.MESSAGE_NOT_ENOUGH_COLA);
					}else{
						return Result.success();
					}
				}
			}
		};
		return var2;
	}
	@Deprecated()
	public static AbilityDescriptionLine$IDescriptionLine getColaTooltip(int var0) {
		int var1 = var0;
		final int var1_f1 = var1;
		AbilityDescriptionLine$IDescriptionLine var2 = (l0, l1) -> {
			ColaUsageComponent lv2_3 = ((ColaUsageComponent)(l1.getComponent(((AbilityComponentKey)(ModAbilityComponents.COLA_USAGE.get()))).orElse(null)));
			int lv2_4 = var1_f1;
			if ((lv2_3) != (null)){
				lv2_4 = Math.round(lv2_3.colaBonusManager.applyBonus((float)(var1_f1)));
			}
			return new AbilityStat$Builder((Component)(ModI18nAbilities.DESCRIPTION_STAT_NAME_COLA),lv2_4).build().getStatDescription();
		};
		return var2;
	}
	public ColaUsageComponent(IAbility var1) {
		super(((AbilityComponentKey)(ModAbilityComponents.COLA_USAGE.get())),var1);
		this.colaBonusManager = new BonusManager(COLA_BONUS_MANAGER_UUID);
		this.addBonusManager(this.colaBonusManager);
	}
	public boolean consumeCola(LivingEntity var1, int var2) {
		if ((var2) <= (0)){
			return true;
		}else{
			EntityStatsData var3 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get(var1))).orElse((Object)(null))));
			if ((var3) == (null)){
				return false;
			}else{
				int var4 = Math.round(this.colaBonusManager.applyBonus((float)(var2)));
				if ((var3.getCola()) < (var4)){
					return false;
				}else{
					var3.alterCola(-(var4));
					if (var1 instanceof ServerPlayer){
						ServerPlayer var5 = ((ServerPlayer)(var1));
						ModNetwork.sendTo(new SUpdateColaAmountPacket(var1),(Player)(var5));
					}
					return true;
				}
			}
		}
	}
	public BonusManager getColaBonusManager() {
		return this.colaBonusManager;
	}
}