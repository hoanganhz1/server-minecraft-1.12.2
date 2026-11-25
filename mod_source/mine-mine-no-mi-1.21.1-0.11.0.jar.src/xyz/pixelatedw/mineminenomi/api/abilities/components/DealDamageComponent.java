package xyz.pixelatedw.mineminenomi.api.abilities.components;

import java.util.LinkedList;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine$IDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityStat;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityStat$AbilityStatType;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityStat$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.damagesources.AbilityDamageSource;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModDamageSources;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public class DealDamageComponent extends AbilityComponent<IAbility> {
	// Fields
	private static final UUID DAMAGE_BONUS_MANAGER_UUID = UUID.fromString("f4ac25c4-ef9f-4537-8471-406a02737308");
	private static final UUID STYLE_BONUS = UUID.fromString("dd55f409-9924-4cca-afbe-522462884d71");
	private static final int TARGETS_SIZE = 10;
	private LinkedList<LivingEntity> lastTargets;
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
			AbilityStat$Builder lv2_4 = new AbilityStat$Builder((Component)(ModI18nAbilities.DESCRIPTION_STAT_NAME_DAMAGE),(var2_f1) * (0.4F),(var3_f2) * (0.4F));
			float lv2_5 = var2_f1;
			float lv2_6 = var3_f2;
			AbilityStat$Builder lv2_7 = lv2_4;
			final float lv2_5_f1 = lv2_5;
			final float lv2_6_f2 = lv2_6;
			final AbilityStat$Builder lv2_7_f3 = lv2_7;
			Consumer<DealDamageComponent> lv2_8 = (l2_0) -> {
				float lv1_4 = (l2_0.getBonusManager().applyBonus(lv2_5_f1)) - (lv2_5_f1);
				float lv1_5 = (l2_0.getBonusManager().applyBonus(lv2_6_f2)) - (lv2_6_f2);
				float lv1_6 = (lv1_4) + (lv1_5);
				lv2_7_f3.withBonus(lv1_4,lv1_5,((lv1_6) > (0F)) ? (AbilityStat$AbilityStatType.BUFF) : (((lv1_6) < (0F)) ? (AbilityStat$AbilityStatType.DEBUFF) : (AbilityStat$AbilityStatType.NEUTRAL)));
			};
			((java.util.Optional)(l1.getComponent(((AbilityComponentKey)(ModAbilityComponents.DAMAGE.get()))))).ifPresent((Consumer)(lv2_8));
			return lv2_4.build().getStatDescription();
		};
		return var4;
	}
	public DealDamageComponent(IAbility var1) {
		super(((AbilityComponentKey)(ModAbilityComponents.DAMAGE.get())),var1);
		this.lastTargets = (LinkedList) (new LinkedList());
		this.bonusManager = new BonusManager(DAMAGE_BONUS_MANAGER_UUID);
		this.addBonusManager(this.bonusManager);
	}
	public boolean hurtTarget(LivingEntity var1, LivingEntity var2, float var3) {
		return this.hurtTarget(var1,var2,var3,this.getDamageSource(var1));
	}
	public boolean hurtTarget(LivingEntity var1, LivingEntity var2, float var3, DamageSource var4) {
		this.ensureIsRegistered();
		if (var2.hurt(var4,var3)){
			if (var2.isAlive()){
				if (((this.lastTargets.size()) >= (0)) && ((this.lastTargets.size()) == (10))){
					this.lastTargets.remove();
				}
				this.lastTargets.add(var2);
			}
			return true;
		}else{
			return false;
		}
	}
	public AbilityDamageSource getDamageSource(LivingEntity var1) {
		return ModDamageSources.getInstance((LevelAccessor)(var1.level())).ability((Entity)(var1),this.getAbility().getCore());
	}
	@Nullable()
	public @Nullable() LivingEntity getLastTarget() {
		LivingEntity var1 = null;
		try{
			var1 = ((LivingEntity)(this.lastTargets.getLast()));
		}catch(Exception var1_1){
			return null;
		}
		return var1;
	}
	@Nullable()
	public @Nullable() LivingEntity getLastNTarget(int var1) {
		LivingEntity var2 = null;
		try{
			var2 = ((LivingEntity)(this.lastTargets.get(var1)));
		}catch(Exception var2_1){
			return null;
		}
		return var2;
	}
	public BonusManager getBonusManager() {
		return this.bonusManager;
	}
}