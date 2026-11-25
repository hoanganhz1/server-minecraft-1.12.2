package xyz.pixelatedw.mineminenomi.api.abilities.components;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine$IDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityStat$AbilityStatType;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityStat$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public class RangeComponent extends AbilityComponent<IAbility> {
	// Fields
	private static final UUID RANGE_BONUS_MANAGER_UUID = UUID.fromString("c3bc77bd-2f17-4d99-bcb6-e2305b5c075f");
	private float previousRange;
	private float previousDistance;
	private TargetPredicate areaCheck;
	private TargetPredicate lineCheck;
	private final BonusManager bonusManager;

	@Deprecated()
	public static AbilityDescriptionLine$IDescriptionLine getTooltip(Function<LivingEntity, Float> var0, RangeComponent$RangeType var1) {
		Function<LivingEntity, Float> var2 = var0;
		RangeComponent$RangeType var3 = var1;
		final Function<LivingEntity, Float> var2_f1 = var2;
		final RangeComponent$RangeType var3_f2 = var3;
		AbilityDescriptionLine$IDescriptionLine var4 = (l0, l1) -> {
			float lv1_4 = ((Float)(((java.util.function.Function)(var2_f1)).apply((Object)(l0)))).floatValue();
			return getToolTip(l0,l1,lv1_4,lv1_4,var3_f2);
		};
		return var4;
	}
	@Deprecated()
	public static AbilityDescriptionLine$IDescriptionLine getTooltip(float var0, RangeComponent$RangeType var1) {
		return getTooltip(var0,var0,var1);
	}
	@Deprecated()
	public static AbilityDescriptionLine$IDescriptionLine getTooltip(float var0, float var1, RangeComponent$RangeType var2) {
		float var3 = var0;
		float var4 = var1;
		RangeComponent$RangeType var5 = var2;
		final float var3_f1 = var3;
		final float var4_f2 = var4;
		final RangeComponent$RangeType var5_f3 = var5;
		AbilityDescriptionLine$IDescriptionLine var6 = (l0, l1) -> {
			return getToolTip(l0,l1,var3_f1,var4_f2,var5_f3);
		};
		return var6;
	}
	@Deprecated()
	public static Component getToolTip(LivingEntity var0, IAbility var1, float var2, float var3, RangeComponent$RangeType var4) {
		AbilityStat$Builder var5 = new AbilityStat$Builder((Component)(ModI18nAbilities.DESCRIPTION_STAT_NAME_RANGE),var2,var3);
		AbilityStat$Builder var6 = var5;
		final AbilityStat$Builder var6_f1 = var6;
		Consumer<RangeComponent> var7 = (l0) -> {
			float lv3_2 = (l0.getBonusManager().applyBonus(l0.getPreviousRange())) - (l0.getPreviousRange());
			var6_f1.withBonus(lv3_2,((lv3_2) > (0F)) ? (AbilityStat$AbilityStatType.BUFF) : (((lv3_2) < (0F)) ? (AbilityStat$AbilityStatType.DEBUFF) : (AbilityStat$AbilityStatType.NEUTRAL)));
		};
		((java.util.Optional)(var1.getComponent(((AbilityComponentKey)(ModAbilityComponents.RANGE.get()))))).ifPresent((Consumer)(var7));
		switch (var4.ordinal()){
		case 0:

		case 1:

		case 2:

		default:
			return var5.withUnit(ModI18nAbilities.DESCRIPTION_STAT_UNIT_BLOCKS).buildComponent();
		}
	}
	public RangeComponent(IAbility var1) {
		super(((AbilityComponentKey)(ModAbilityComponents.RANGE.get())),var1);
		this.areaCheck = TargetPredicate.DEFAULT_AREA_CHECK;
		this.lineCheck = TargetPredicate.DEFAULT_LINE_CHECK;
		this.bonusManager = new BonusManager(RANGE_BONUS_MANAGER_UUID);
		this.addBonusManager(this.bonusManager);
	}
	public List<LivingEntity> getTargetsInArea(LivingEntity var1, float var2) {
		return (List<LivingEntity>) (List) (this.getTargetsInArea(var1,var2,this.areaCheck));
	}
	public List<LivingEntity> getTargetsInArea(LivingEntity var1, BlockPos var2, float var3) {
		return (List<LivingEntity>) (List) (this.getTargetsInArea(var1,var2,var3,this.areaCheck));
	}
	public List<LivingEntity> getTargetsInArea(LivingEntity var1, float var2, @Nullable() TargetPredicate var3) {
		return (List<LivingEntity>) (List) (this.getTargetsInArea(var1,var1.blockPosition(),var2,var3));
	}
	public List<LivingEntity> getTargetsInArea(LivingEntity var1, BlockPos var2, float var3, @Nullable() TargetPredicate var4) {
		return (List<LivingEntity>) (List) (this.getTargetsInArea(var1,var2,var3,var4,LivingEntity.class));
	}
	public <E extends LivingEntity> List<E> getTargetsInArea(LivingEntity var1, BlockPos var2, float var3, @Nullable() TargetPredicate var4, Class<E> var5) {
		this.ensureIsRegistered();
		this.previousRange = var3;
		var3 = this.bonusManager.applyBonus(var3);
		return TargetHelper.getEntitiesInArea((LevelAccessor)(var1.level()),var1,var2,(double)(var3),var4,var5);
	}
	public List<LivingEntity> getTargetsInLine(LivingEntity var1, float var2, float var3) {
		return (List<LivingEntity>) (List) (this.getTargetsInLine(var1,var2,var3,this.lineCheck));
	}
	public List<LivingEntity> getTargetsInLine(LivingEntity var1, BlockPos var2, float var3, float var4) {
		return (List<LivingEntity>) (List) (this.getTargetsInLine(var1,var2,var3,var4,this.lineCheck));
	}
	public List<LivingEntity> getTargetsInLine(LivingEntity var1, float var2, float var3, @Nullable() TargetPredicate var4) {
		return (List<LivingEntity>) (List) (this.getTargetsInLine(var1,var1.blockPosition(),var2,var3,var4));
	}
	public List<LivingEntity> getTargetsInLine(LivingEntity var1, BlockPos var2, float var3, float var4, @Nullable() TargetPredicate var5) {
		return (List<LivingEntity>) (List) (this.getTargetsInLine(var1,var1.blockPosition(),var3,var4,var5,LivingEntity.class));
	}
	public <E extends LivingEntity> List<E> getTargetsInLine(LivingEntity var1, BlockPos var2, float var3, float var4, @Nullable() TargetPredicate var5, Class<E> var6) {
		this.ensureIsRegistered();
		this.previousRange = var4;
		this.previousDistance = var3;
		var3 = this.bonusManager.applyBonus(var3);
		var4 = this.bonusManager.applyBonus(var4);
		return (List<E>) (List) (TargetHelper.getEntitiesInLine((LivingEntity)(var1),(BlockPos)(var2),var3,var4,(TargetPredicate)(var5),(Class)(var6)));
	}
	public float getPreviousRange() {
		return this.previousRange;
	}
	public float getRange() {
		return this.bonusManager.applyBonus(this.previousRange);
	}
	public float getPreviousDistance() {
		return this.previousDistance;
	}
	public float getDistance() {
		return this.bonusManager.applyBonus(this.previousDistance);
	}
	public void setAreaCheck(TargetPredicate var1) {
		this.areaCheck = var1;
	}
	public void setLineCheck(TargetPredicate var1) {
		this.lineCheck = var1;
	}
	public BonusManager getBonusManager() {
		return this.bonusManager;
	}
}