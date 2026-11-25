package xyz.pixelatedw.mineminenomi.abilities.zoumammoth;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine$IDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent$RangeType;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityTooltipsHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class AncientSweepAbility extends Ability {
	// Fields
	private static final int COOLDOWN = 160;
	private static final int CHARGE_TIME = 40;
	private static final int HEAVY_RANGE = 3;
	private static final int GUARD_RANGE = 6;
	private static final int HEAVY_DAMAGE = 15;
	private static final int GUARD_DAMAGE = 20;
	private static final AbilityDescriptionLine$IDescriptionLine RANGE_TOOLTIP;
	private static final AbilityDescriptionLine$IDescriptionLine DAMAGE_TOOLTIP;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<AncientSweepAbility>> INSTANCE;
	private final ChargeComponent chargeComponent;
	private final RangeComponent rangeComponent;
	private final DealDamageComponent dealDamageComponent;

	public AncientSweepAbility(AbilityCore<AncientSweepAbility> var1) {
		super((AbilityCore)(var1));
		this.chargeComponent = new ChargeComponent((IAbility)(this)).addEndEvent(100,(ChargeComponent$IEndChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent)(this::endChargeEvent))));
		this.rangeComponent = new RangeComponent((IAbility)(this));
		this.dealDamageComponent = new DealDamageComponent((IAbility)(this));
		AbilityComponent[] var2 = new AbilityComponent[]{this.chargeComponent,this.rangeComponent,this.dealDamageComponent};
		this.addComponents(var2);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(ZouMammothHelper::requiresEitherPoint))));
		this.addContinueUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(ZouMammothHelper::requiresEitherPoint))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		this.chargeComponent.startCharging(var1,40F);
	}
	public void endChargeEvent(LivingEntity var1, IAbility var2) {
		float var3 = 15F;
		float var4 = 3F;
		if (((MorphInfo)(ModMorphs.MAMMOTH_GUARD.get())).isActive(var1)){
			var4 = (var4) * (2F);
			var3 = (var3) + (5F);
		}
		Iterator var5 = this.rangeComponent.getTargetsInLine(var1,var4,3F).iterator();
		do{
			if (var5.hasNext()){
				LivingEntity var6 = ((LivingEntity)(var5.next()));
				if (this.dealDamageComponent.hurtTarget(var1,var6,var3)){
					Vec3 var7 = var1.getLookAngle().normalize().multiply(3D,1D,3D);
					AbilityHelper.setDeltaMovement((Entity)(var6),var7.x,(var1.getDeltaMovement().y()) + (0.5D),var7.z);
					continue;
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
		if (!(var1.level().isClientSide)){
			var1.swing(InteractionHand.MAIN_HAND,true);
		}
		this.cooldownComponent.startCooldown(var1,160F);
	}
	static  {
		AbilityDescriptionLine$IDescriptionLine var0 = (l0, l1) -> {
			int lv1_2 = 3;
			if (((MorphInfo)(ModMorphs.MAMMOTH_GUARD.get())).isActive(l0)){
				lv1_2 = 6;
			}
			return RangeComponent.getTooltip((float)(lv1_2),RangeComponent$RangeType.LINE).expand(l0,l1);
		};
		RANGE_TOOLTIP = var0;
		AbilityDescriptionLine$IDescriptionLine var1 = (l0, l1) -> {
			int lv2_2 = 15;
			if (((MorphInfo)(ModMorphs.MAMMOTH_GUARD.get())).isActive(l0)){
				lv2_2 = 20;
			}
			return DealDamageComponent.getTooltip((float)(lv2_2)).expand(l0,l1);
		};
		DAMAGE_TOOLTIP = var1;
		AbilityRegistryEntry var2 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv3_2 = new Pair[]{ImmutablePair.of((Object)("Hits all enemies in a frontal cone with your trunk."),(Object)(null))};
			Component[] lv3_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv3_2);
			AbilityDescriptionLine$IDescriptionLine[] lv3_4 = new AbilityDescriptionLine$IDescriptionLine[2];
			lv3_4[0] = AbilityDescriptionLine.NEW_LINE;
			Supplier[] lv3_5 = new Supplier[]{ModMorphs.MAMMOTH_GUARD,ModMorphs.MAMMOTH_HEAVY};
			lv3_4[1] = AbilityTooltipsHelper.getRequiredMorphTooltip(lv3_5);
			AbilityDescriptionLine$IDescriptionLine[] lv3_6 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(160F),ChargeComponent.getTooltip(40F),RANGE_TOOLTIP,DAMAGE_TOOLTIP};
			SourceType[] lv3_7 = new SourceType[]{SourceType.FIST};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(AncientSweepAbility::new)))).addDescriptionLine(lv3_3).addDescriptionLine(lv3_4).addAdvancedDescriptionLine(lv3_6).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(lv3_7).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("ancient_sweep","Ancient Sweep",var2));
	}
}