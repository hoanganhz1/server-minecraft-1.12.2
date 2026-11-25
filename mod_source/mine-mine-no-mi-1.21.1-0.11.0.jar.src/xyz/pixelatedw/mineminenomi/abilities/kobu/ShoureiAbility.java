package xyz.pixelatedw.mineminenomi.abilities.kobu;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent$RangeType;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class ShoureiAbility extends Ability {
	// Fields
	private static final TargetPredicate TARGETS_CHECK = new TargetPredicate().testFriendlyFaction();
	private static final int COOLDOWN = 1200;
	private static final int RANGE = 20;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<ShoureiAbility>> INSTANCE;
	private final RangeComponent rangeComponent;

	public ShoureiAbility(AbilityCore<ShoureiAbility> var1) {
		super((AbilityCore)(var1));
		this.rangeComponent = new RangeComponent((IAbility)(this));
		AbilityComponent[] var2 = new AbilityComponent[]{this.rangeComponent};
		this.addComponents(var2);
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		Iterator var3 = this.rangeComponent.getTargetsInArea(var1,20F,TARGETS_CHECK).iterator();
		do{
			if (var3.hasNext()){
				LivingEntity var4 = ((LivingEntity)(var3.next()));
				var4.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST,600,1,true,false,true));
				var4.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,600,1,true,false,true));
				var4.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE,600,1,true,false,true));
				var4.addEffect(new MobEffectInstance(MobEffects.REGENERATION,600,0,true,false,true));
				WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.SHOUREI.get())),(Entity)(var1),var4.getX(),var4.getY(),var4.getZ());
				continue;
			}else{
				break;
			}
		} while (true);
		this.cooldownComponent.startCooldown(var1,1200F);
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Increases other people's fighting spirit and physical strength by simply encouraging them with words"),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(1200F),RangeComponent.getTooltip(20F,RangeComponent$RangeType.AOE)};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(ShoureiAbility::new)))).addDescriptionLine(lv1_3).addAdvancedDescriptionLine(lv1_4).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("shourei","Shourei",var0));
	}
}