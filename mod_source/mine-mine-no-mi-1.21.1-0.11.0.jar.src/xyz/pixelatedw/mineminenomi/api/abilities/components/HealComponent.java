package xyz.pixelatedw.mineminenomi.api.abilities.components;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.common.NeoForge;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine$IDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityStat;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityStat$AbilityStatType;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityStat$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.events.entity.LivingHealByEvent;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public class HealComponent extends AbilityComponent<IAbility> {
	// Fields
	private static final UUID HEAL_BONUS_MANAGER_UUID = UUID.fromString("c778872e-258a-4672-8cd6-0f930ad9adba");
	private final BonusManager bonusManager;

	@Deprecated()
	public static AbilityDescriptionLine$IDescriptionLine getTooltip(float var0) {
		return getTooltip(var0,var0);
	}
	@Deprecated()
	public static AbilityDescriptionLine$IDescriptionLine getTooltip(float var0, float var1) {
		float var2 = var0;
		float var3 = var1;
		final float var2_f1 = var2;
		final float var3_f2 = var3;
		AbilityDescriptionLine$IDescriptionLine var4 = (l0, l1) -> {
			AbilityStat$Builder lv2_4 = new AbilityStat$Builder((Component)(ModI18nAbilities.DESCRIPTION_STAT_NAME_HEAL),var2_f1,var3_f2);
			float lv2_5 = var2_f1;
			float lv2_6 = var3_f2;
			AbilityStat$Builder lv2_7 = lv2_4;
			final float lv2_5_f1 = lv2_5;
			final float lv2_6_f2 = lv2_6;
			final AbilityStat$Builder lv2_7_f3 = lv2_7;
			Consumer<HealComponent> lv2_8 = (l2_0) -> {
				float lv1_4 = (l2_0.getBonusManager().applyBonus(lv2_5_f1)) - (lv2_5_f1);
				float lv1_5 = (l2_0.getBonusManager().applyBonus(lv2_6_f2)) - (lv2_6_f2);
				float lv1_6 = (lv1_4) + (lv1_5);
				lv2_7_f3.withBonus(lv1_4,lv1_5,((lv1_6) > (0F)) ? (AbilityStat$AbilityStatType.BUFF) : (((lv1_6) < (0F)) ? (AbilityStat$AbilityStatType.DEBUFF) : (AbilityStat$AbilityStatType.NEUTRAL)));
			};
			((java.util.Optional)(l1.getComponent(((AbilityComponentKey)(ModAbilityComponents.HEAL.get()))))).ifPresent((Consumer)(lv2_8));
			return lv2_4.build().getStatDescription();
		};
		return var4;
	}
	public HealComponent(IAbility var1) {
		super(((AbilityComponentKey)(ModAbilityComponents.HEAL.get())),var1);
		this.bonusManager = new BonusManager(HEAL_BONUS_MANAGER_UUID);
		this.addBonusManager(this.bonusManager);
	}
	public boolean healTarget(LivingEntity var1, LivingEntity var2, float var3) {
		this.ensureIsRegistered();
		float var4 = this.bonusManager.applyBonus(var3);
		if (!(((LivingHealByEvent)(NeoForge.EVENT_BUS.post((Event)(new LivingHealByEvent(var1,var2,var4))))).isCanceled())){
			var2.heal(var4);
			return true;
		}else{
			return false;
		}
	}
	public BonusManager getBonusManager() {
		return this.bonusManager;
	}
}