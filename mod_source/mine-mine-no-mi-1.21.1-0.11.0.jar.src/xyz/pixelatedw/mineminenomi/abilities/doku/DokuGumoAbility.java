package xyz.pixelatedw.mineminenomi.abilities.doku;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent$IChangeModeEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusManager;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusOperation;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent$RangeType;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModRegistry$I18nCategory;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$Details;
import xyz.pixelatedw.mineminenomi.particles.effects.doku.DokuParticleEffectDetails;

public class DokuGumoAbility extends Ability {
	// Fields
	private static final UUID VENOM_RANGE_BONUS_UUID = UUID.fromString("77d2625c-c112-4ebf-9f1e-01311c66968b");
	private static final Component VENOM_NAME = Component.translatable(ModRegistry.registerName(ModRegistry$I18nCategory.ABILITY,"doku_gumo_venom","Venom Doku Gumo"));
	private static final ResourceLocation NORMAL_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/abilities/doku_gumo.png");
	private static final ResourceLocation VENOM_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/abilities/doku_gumo_venom.png");
	private static final int COOLDOWN = 600;
	private static final int HOLD_TIME = 400;
	private static final int RANGE = 10;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<DokuGumoAbility>> INSTANCE;
	private final ContinuousComponent continuousComponent;
	private final RangeComponent rangeComponent;
	private final AltModeComponent<DokuGumoAbility$Mode> altModeComponent;

	public DokuGumoAbility(AbilityCore<DokuGumoAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this)).addTickEvent((ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::duringContinuityEvent)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
		this.rangeComponent = new RangeComponent((IAbility)(this));
		this.altModeComponent = (AltModeComponent) (new AltModeComponent((IAbility)(this),DokuGumoAbility$Mode.class,DokuGumoAbility$Mode.NORMAL,true).addChangeModeEvent((AltModeComponent$IChangeModeEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent$IChangeModeEvent)(this::onAltModeChange)))));
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent,this.rangeComponent,this.altModeComponent};
		this.addComponents(var2);
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.triggerContinuity(var1,400F);
	}
	private void duringContinuityEvent(LivingEntity var1, IAbility var2) {
		int var3 = 0;
		int var4 = 100;
		boolean var5 = false;
		int var6 = 0;
		if (((MorphInfo)(ModMorphs.VENOM_DEMON.get())).isActive(var1)){
			var5 = true;
			var3 = (var3) + (2);
			var4 = (var4) * (2);
			this.rangeComponent.getBonusManager().addBonus(VENOM_RANGE_BONUS_UUID,"Venom Range Bonus",BonusOperation.MUL,2F);
			var6 = 1;
		}
		Iterator var7 = this.rangeComponent.getTargetsInArea(var1,10F).iterator();
		do{
			if (var7.hasNext()){
				LivingEntity var8 = ((LivingEntity)(var7.next()));
				if (!(var8.hasEffect(MobEffects.BLINDNESS))){
					var8.addEffect(new MobEffectInstance(MobEffects.BLINDNESS,var4,var3));
				}
				if (!(var8.hasEffect((Holder)(ModEffects.DOKU_POISON)))){
					var8.addEffect(new MobEffectInstance((Holder)(ModEffects.DOKU_POISON),var4,var6));
				}
				if (!(var8.hasEffect(MobEffects.WEAKNESS))){
					var8.addEffect(new MobEffectInstance(MobEffects.WEAKNESS,var4,var3));
					continue;
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
		if (((this.continuousComponent.getContinueTime()) % (2F)) == (0F)){
			ParticleEffect var8_1 = ((ParticleEffect)(ModParticleEffects.DOKU_GUMO.get()));
			WyHelper.spawnParticleEffect(var8_1,(Entity)(var1),var1.getX(),var1.getY(),var1.getZ(),(ParticleEffect$Details)((var5) ? (DokuHelper.VENOM_DETAILS) : (null)));
		}
	}
	private void endContinuityEvent(LivingEntity var1, IAbility var2) {
		this.cooldownComponent.startCooldown(var1,600F);
	}
	public void setNormalMode(LivingEntity var1) {
		this.altModeComponent.setMode(var1,(Enum)(DokuGumoAbility$Mode.NORMAL));
	}
	public void setVenomMode(LivingEntity var1) {
		this.altModeComponent.setMode(var1,(Enum)(DokuGumoAbility$Mode.VENOM));
	}
	private boolean onAltModeChange(LivingEntity var1, IAbility var2, DokuGumoAbility$Mode var3) {
		if ((var3) == (DokuGumoAbility$Mode.VENOM)){
			this.setDisplayName(VENOM_NAME);
			this.setDisplayIcon(VENOM_ICON);
		}else{
			if ((var3) == (DokuGumoAbility$Mode.NORMAL)){
				this.setDisplayName(((AbilityCore)(INSTANCE.get())).getLocalizedName());
				this.setDisplayIcon(NORMAL_ICON);
			}else{
				return true;
			}
		}
		return true;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Creates a dense cloud of poisonous smoke, which moves along with the user and poisons and blinds everyone inside."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(600F),ContinuousComponent.getTooltip(400F),RangeComponent.getTooltip(10F,RangeComponent$RangeType.AOE)};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(DokuGumoAbility::new)))).addDescriptionLine(lv1_3).addAdvancedDescriptionLine(lv1_4).setSourceElement(SourceElement.POISON).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("doku_gumo","Doku Gumo",var0));
	}
}