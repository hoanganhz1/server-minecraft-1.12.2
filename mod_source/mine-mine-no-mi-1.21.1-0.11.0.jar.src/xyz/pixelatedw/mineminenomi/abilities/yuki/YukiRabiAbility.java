package xyz.pixelatedw.mineminenomi.abilities.yuki;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent$IRepeaterStopEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent$IRepeaterTriggerEvent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.yuki.YukiRabiProjectile;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class YukiRabiAbility extends Ability {
	// Fields
	private static final float COOLDOWN = 120F;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<YukiRabiAbility>> INSTANCE;
	private final ContinuousComponent continuousComponent;
	private final RepeaterComponent repeaterComponent;
	private final ProjectileComponent projectileComponent;

	public YukiRabiAbility(AbilityCore<YukiRabiAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this),true).addStartEvent((ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::onContinuityStart))));
		this.repeaterComponent = new RepeaterComponent((IAbility)(this)).addTriggerEvent((RepeaterComponent$IRepeaterTriggerEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent$IRepeaterTriggerEvent)(this::onRepeaterTrigger)))).addStopEvent((RepeaterComponent$IRepeaterStopEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent$IRepeaterStopEvent)(this::onRepeaterStop))));
		this.projectileComponent = new ProjectileComponent((IAbility)(this),(ProjectileComponent$IProjectileFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory)((ProjectileComponent$IProjectileFactory) ((ProjectileComponent$IProjectileFactory<YukiRabiProjectile>) (this::createProjectile))))));
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent,this.repeaterComponent,this.projectileComponent};
		super.addComponents(var2);
		super.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::onUseEvent))));
	}
	private void onUseEvent(LivingEntity var1, IAbility var2) {
		if (this.continuousComponent.isContinuous()){
			this.repeaterComponent.stop(var1);
			return;
		}else{
			this.continuousComponent.triggerContinuity(var1);
			return;
		}
	}
	private void onContinuityStart(LivingEntity var1, IAbility var2) {
		if (var1.level().isClientSide){
			return;
		}else{
			this.repeaterComponent.start(var1,6,3);
			return;
		}
	}
	private void onRepeaterTrigger(LivingEntity var1, IAbility var2) {
		this.projectileComponent.shoot(var1,2F,2F);
	}
	private void onRepeaterStop(LivingEntity var1, IAbility var2) {
		this.continuousComponent.stopContinuity(var1);
		this.cooldownComponent.startCooldown(var1,120F);
	}
	private YukiRabiProjectile createProjectile(LivingEntity var1) {
		return new YukiRabiProjectile(var1.level(),var1,(IAbility)(this));
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[1];
			Object[] lv1_3 = new Object[]{ModEffects.FROSTBITE};
			((java.lang.Object[]) (lv1_2))[0] = ImmutablePair.of((Object)("Launches numerous hardened snowballs, shaped like a rabbit's head, that can inflict %s on their enemies."),lv1_3);
			Component[] lv1_4 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(YukiRabiAbility::new)))).addDescriptionLine(lv1_4).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceElement(SourceElement.ICE).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("yuki_rabi","Yuki Rabi",var0));
	}
}