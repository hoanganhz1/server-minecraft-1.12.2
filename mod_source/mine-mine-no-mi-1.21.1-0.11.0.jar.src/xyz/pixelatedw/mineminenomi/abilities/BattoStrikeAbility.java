package xyz.pixelatedw.mineminenomi.abilities;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IStartChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent$RangeType;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class BattoStrikeAbility extends Ability {
	// Fields
	private static final int CHARGE_TIME = 5;
	private static final int COOLDOWN = 300;
	private static final float DAMAGE = 20F;
	private static final int RANGE = 4;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<BattoStrikeAbility>> INSTANCE;
	private final ChargeComponent chargeComponent;
	private final AnimationComponent animationComponent;
	private final DealDamageComponent damageComponent;
	private final RangeComponent rangeComponent;

	public BattoStrikeAbility(AbilityCore<BattoStrikeAbility> var1) {
		super((AbilityCore)(var1));
		this.chargeComponent = new ChargeComponent((IAbility)(this)).addStartEvent((ChargeComponent$IStartChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IStartChargingEvent)(this::startChargeEvent)))).addEndEvent((ChargeComponent$IEndChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent)(this::stopChargeEvent))));
		this.animationComponent = new AnimationComponent((IAbility)(this));
		this.damageComponent = new DealDamageComponent((IAbility)(this));
		this.rangeComponent = new RangeComponent((IAbility)(this));
		AbilityComponent[] var2 = new AbilityComponent[]{this.chargeComponent,this.animationComponent,this.damageComponent,this.rangeComponent};
		this.addComponents(var2);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::requiresBluntWeapon))));
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::requiresOnGround))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::onUseEvent))));
	}
	private void onUseEvent(LivingEntity var1, IAbility var2) {
		this.chargeComponent.startCharging(var1,5F);
	}
	private void startChargeEvent(LivingEntity var1, IAbility var2) {
		var1.addEffect(new MobEffectInstance((Holder)(ModEffects.MOVEMENT_BLOCKED),5,0));
		this.animationComponent.start(var1,ModAnimations.BATTO_STRIKE);
	}
	private void stopChargeEvent(LivingEntity var1, IAbility var2) {
		Iterator var3 = this.rangeComponent.getTargetsInLine(var1,4F,2F).iterator();
		do{
			if (var3.hasNext()){
				LivingEntity var4 = ((LivingEntity)(var3.next()));
				if (this.damageComponent.hurtTarget(var1,var4,20F)){
					Vec3 var5 = var4.position().subtract(var1.position()).normalize().multiply(2.5D,1D,2.5D);
					AbilityHelper.setDeltaMovement((Entity)(var4),var5.x,1.75D,var5.z);
					continue;
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
		this.animationComponent.stop(var1);
		this.cooldownComponent.startCooldown(var1,300F);
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			AbilityDescriptionLine$IDescriptionLine[] lv1_2 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(300F),ChargeComponent.getTooltip(5F),RangeComponent.getTooltip(4F,RangeComponent$RangeType.LINE),DealDamageComponent.getTooltip(20F)};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.STYLE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(BattoStrikeAbility::new)))).addAdvancedDescriptionLine(lv1_2).setSourceHakiNature(SourceHakiNature.IMBUING).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("batto_strike","Batto Strike",var0));
	}
}