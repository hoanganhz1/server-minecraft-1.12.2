package xyz.pixelatedw.mineminenomi.abilities.jiki;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IDuringChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IStartChargingEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTrackerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.MorphComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.MentionHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class PunkCornaDioAbility extends Ability {
	// Fields
	private static final int REQUIRED_IRON = 160;
	private static final int CHARGE_TIME = 60;
	private static final int HOLD_TIME = 100;
	private static final int COOLDOWN = 200;
	private static final int RANGE = 6;
	private static final float DAMAGE = 100F;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<PunkCornaDioAbility>> INSTANCE;
	private static final AttributeModifier TOUGHNESS_MODIFIER;
	private static final AttributeModifier STEP_HEIGHT_MODIFIER;
	private final ChargeComponent chargeComponent;
	private final ContinuousComponent continuousComponent;
	private final HitTrackerComponent hitTrackerComponent;
	private final RangeComponent rangeComponent;
	private final DealDamageComponent dealDamageComponent;
	private final MorphComponent morphComponent;
	private final ChangeStatsComponent changeStatsComponent;
	private List<ItemStack> magneticItems;

	public PunkCornaDioAbility(AbilityCore<PunkCornaDioAbility> var1) {
		super((AbilityCore)(var1));
		this.chargeComponent = new ChargeComponent((IAbility)(this)).addStartEvent((ChargeComponent$IStartChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IStartChargingEvent)(this::startChargeEvent)))).addTickEvent((ChargeComponent$IDuringChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IDuringChargingEvent)(this::duringChargeEvent)))).addEndEvent((ChargeComponent$IEndChargingEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent$IEndChargingEvent)(this::endChargeEvent))));
		this.continuousComponent = new ContinuousComponent((IAbility)(this)).addTickEvent((ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::duringContinuityEvent)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
		this.hitTrackerComponent = new HitTrackerComponent((IAbility)(this));
		this.rangeComponent = new RangeComponent((IAbility)(this));
		this.dealDamageComponent = new DealDamageComponent((IAbility)(this));
		this.morphComponent = new MorphComponent((IAbility)(this));
		this.changeStatsComponent = new ChangeStatsComponent((IAbility)(this));
		this.magneticItems = new ArrayList();
		AbilityComponent[] var2 = new AbilityComponent[]{this.chargeComponent,this.continuousComponent,this.hitTrackerComponent,this.rangeComponent,this.dealDamageComponent,this.morphComponent,this.changeStatsComponent};
		this.addComponents(var2);
		this.changeStatsComponent.addAttributeModifier(ModAttributes.TOUGHNESS,TOUGHNESS_MODIFIER);
		this.changeStatsComponent.addAttributeModifier(Attributes.STEP_HEIGHT,STEP_HEIGHT_MODIFIER);
		this.addCanUseCheck(JikiHelper.getMetalicItemsCheck(160));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		if (this.continuousComponent.isContinuous()){
			return;
		}else{
			this.chargeComponent.startCharging(var1,60F);
			return;
		}
	}
	private void startChargeEvent(LivingEntity var1, IAbility var2) {
		List var3 = ItemsHelper.getAllInventoryItems(var1);
		this.magneticItems = (List) (JikiHelper.getMagneticItemsNeeded(var1,var3,160F));
		this.morphComponent.startMorph(var1,((MorphInfo)(ModMorphs.PUNK_CORNA_DIO.get())));
		this.changeStatsComponent.applyModifiers(var1);
		this.hitTrackerComponent.clearHits();
	}
	private void duringChargeEvent(LivingEntity var1, IAbility var2) {
		var1.addEffect(new MobEffectInstance((Holder)(ModEffects.MOVEMENT_BLOCKED),2,2,false,false));
	}
	private void endChargeEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.startContinuity(var1,100F);
	}
	private void duringContinuityEvent(LivingEntity var1, IAbility var2) {
		var1.addEffect(new MobEffectInstance((Holder)(ModEffects.MOVEMENT_BLOCKED),2,2,false,false));
		if ((this.magneticItems.size()) <= (0)){
			this.continuousComponent.stopContinuity(var1);
		}
		Vec3 var3 = var1.getLookAngle();
		Vec3 var4 = var3.multiply(2.3D,0D,2.3D);
		var1.move(MoverType.SELF,var4);
		if (((this.continuousComponent.getContinueTime()) % (25F)) == (0F)){
			this.hitTrackerComponent.clearHits();
		}
		Vec3 var5 = var3.multiply(4D,1D,4D);
		Iterator var6 = this.rangeComponent.getTargetsInArea(var1,6F).iterator();
		do{
			if (var6.hasNext()){
				LivingEntity var7 = ((LivingEntity)(var6.next()));
				if ((this.hitTrackerComponent.canHit((Entity)(var7))) && (this.dealDamageComponent.hurtTarget(var1,var7,100F))){
					if ((WyHelper.randomDouble()) > (0.75D)){
						var1.addEffect(new MobEffectInstance((Holder)(ModEffects.DIZZY),100,1,false,false));
					}
					AbilityHelper.setDeltaMovement((Entity)(var7),var5.x,0.2D,var5.z);
					continue;
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
	}
	private void endContinuityEvent(LivingEntity var1, IAbility var2) {
		if (((this.magneticItems) != (null)) && ((this.magneticItems.size()) > (0))){
			ItemsHelper.itemBreakParticles(var1,160,((ItemStack)(this.magneticItems.get(0))));
			var1.level().playSound((Player)(null),var1.blockPosition(),SoundEvents.ITEM_BREAK,var1.getSoundSource(),0.5F,1F);
			JikiHelper.dropComponentItems(var1.level(),var1.position(),this.magneticItems);
		}
		this.morphComponent.stopMorph(var1);
		this.changeStatsComponent.removeModifiers(var1);
		this.cooldownComponent.startCooldown(var1,200F);
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[1];
			Object[] lv1_3 = new Object[]{MentionHelper.mentionText(Integer.valueOf(160))};
			((java.lang.Object[]) (lv1_2))[0] = ImmutablePair.of((Object)("Uses %s magnetic items from the inventory to create a bull which can then be used to smash into enemies dealing huge damage, knocking them away and potentially stunning them."),lv1_3);
			Component[] lv1_4 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_5 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(200F),ChargeComponent.getTooltip(60F),DealDamageComponent.getTooltip(100F)};
			SourceType[] lv1_6 = new SourceType[]{SourceType.BLUNT};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(PunkCornaDioAbility::new)))).addDescriptionLine(lv1_4).addAdvancedDescriptionLine(lv1_5).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceElement(SourceElement.METAL).setSourceType(lv1_6).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("punk_corna_dio","Punk Corna Dio",var0));
		TOUGHNESS_MODIFIER = new AttributeModifier(ResourceLocation.fromNamespaceAndPath("mineminenomi","ability.punk_corna.toughness"),3D,AttributeModifier.Operation.ADD_VALUE);
		STEP_HEIGHT_MODIFIER = new AttributeModifier(ResourceLocation.fromNamespaceAndPath("mineminenomi","ability.punk_corna.step_height"),3D,AttributeModifier.Operation.ADD_VALUE);
	}
}