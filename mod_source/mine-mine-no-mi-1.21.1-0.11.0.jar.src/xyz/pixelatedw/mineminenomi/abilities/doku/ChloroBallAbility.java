package xyz.pixelatedw.mineminenomi.abilities.doku;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
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
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine$IDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent$IChangeModeEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent$IRepeaterStopEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent$IRepeaterTriggerEvent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.doku.ChloroBallProjectile;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModRegistry$I18nCategory;

public class ChloroBallAbility extends Ability {
	// Fields
	private static final Component VENOM_NAME = Component.translatable(ModRegistry.registerName(ModRegistry$I18nCategory.ABILITY,"chloro_ball_venom","Venom Chloro Ball"));
	private static final ResourceLocation NORMAL_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/abilities/chloro_ball.png");
	private static final ResourceLocation VENOM_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/abilities/chloro_ball_venom.png");
	private static final int COOLDOWN = 180;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<ChloroBallAbility>> INSTANCE;
	private final ContinuousComponent continuousComponent;
	private final RepeaterComponent repeaterComponent;
	private final ProjectileComponent projectileComponent;
	private final AltModeComponent<ChloroBallAbility$Mode> altModeComponent;
	private final ExplosionComponent explosionComponent;

	public ChloroBallAbility(AbilityCore<ChloroBallAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this)).addStartEvent((ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::startContinuityEvent)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
		this.repeaterComponent = new RepeaterComponent((IAbility)(this)).addTriggerEvent((RepeaterComponent$IRepeaterTriggerEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent$IRepeaterTriggerEvent)(this::triggerRepeaterEvent)))).addStopEvent((RepeaterComponent$IRepeaterStopEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent$IRepeaterStopEvent)(this::endRepeaterEvent))));
		this.projectileComponent = new ProjectileComponent((IAbility)(this),(ProjectileComponent$IProjectileFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory)((ProjectileComponent$IProjectileFactory) ((ProjectileComponent$IProjectileFactory<ChloroBallProjectile>) (this::createProjectile))))));
		this.altModeComponent = (AltModeComponent) (new AltModeComponent((IAbility)(this),ChloroBallAbility$Mode.class,ChloroBallAbility$Mode.NORMAL,true).addChangeModeEvent((AltModeComponent$IChangeModeEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent$IChangeModeEvent)(this::onAltModeChange)))));
		this.explosionComponent = new ExplosionComponent((IAbility)(this));
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent,this.repeaterComponent,this.projectileComponent,this.altModeComponent,this.explosionComponent};
		this.addComponents(var2);
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.triggerContinuity(var1);
	}
	private void startContinuityEvent(LivingEntity var1, IAbility var2) {
		if (((MorphInfo)(ModMorphs.VENOM_DEMON.get())).isActive(var1)){
			this.repeaterComponent.start(var1,3,5);
		}else{
			this.repeaterComponent.start(var1,1,5);
		}
	}
	private void triggerRepeaterEvent(LivingEntity var1, IAbility var2) {
		this.projectileComponent.shoot(var1,2F,1F);
	}
	private void endRepeaterEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.stopContinuity(var1);
	}
	private void endContinuityEvent(LivingEntity var1, IAbility var2) {
		this.cooldownComponent.startCooldown(var1,180F);
	}
	private ChloroBallProjectile createProjectile(LivingEntity var1) {
		boolean var2 = ((MorphInfo)(ModMorphs.VENOM_DEMON.get())).isActive(var1);
		return new ChloroBallProjectile(var1.level(),var1,(IAbility)(this),var2);
	}
	public void setNormalMode(LivingEntity var1) {
		this.altModeComponent.setMode(var1,(Enum)(ChloroBallAbility$Mode.NORMAL));
	}
	public void setVenomMode(LivingEntity var1) {
		this.altModeComponent.setMode(var1,(Enum)(ChloroBallAbility$Mode.VENOM));
	}
	private boolean onAltModeChange(LivingEntity var1, IAbility var2, ChloroBallAbility$Mode var3) {
		if ((var3) == (ChloroBallAbility$Mode.VENOM)){
			this.setDisplayName(VENOM_NAME);
			this.setDisplayIcon(VENOM_ICON);
		}else{
			if ((var3) == (ChloroBallAbility$Mode.NORMAL)){
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
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("The user spits a bubble made of poison towards the enemy, which also leaves poison on the ground."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(180F)};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(ChloroBallAbility::new)))).addDescriptionLine(lv1_3).addAdvancedDescriptionLine(lv1_4).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceElement(SourceElement.POISON).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("chloro_ball","Chloro Ball",var0));
	}
}