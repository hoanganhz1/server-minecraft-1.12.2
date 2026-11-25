package xyz.pixelatedw.mineminenomi.api.abilities;

import java.util.function.Consumer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent$IRepeaterStopEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent$IRepeaterTriggerEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.CooldownComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.ProjectileComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.RepeaterComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;

public abstract class RepeaterAbility extends Ability {
	// Fields
	protected final ContinuousComponent continuousComponent;
	protected final RepeaterComponent repeaterComponent;
	protected final ProjectileComponent projectileComponent;
	private RepeaterAbility$IOnTriggerShootEvent customShootLogic;

	public RepeaterAbility(AbilityCore<? extends RepeaterAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this),true).addStartEvent(200,(ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::onContinuityStart))));
		this.repeaterComponent = new RepeaterComponent((IAbility)(this)).addTriggerEvent(200,(RepeaterComponent$IRepeaterTriggerEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent$IRepeaterTriggerEvent)(this::onRepeaterTrigger)))).addStopEvent(200,(RepeaterComponent$IRepeaterStopEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent$IRepeaterStopEvent)(this::onRepeaterStop))));
		this.projectileComponent = new ProjectileComponent((IAbility)(this),(ProjectileComponent$IProjectileFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory)((ProjectileComponent$IProjectileFactory) ((ProjectileComponent$IProjectileFactory<NuProjectileEntity>) (this::getProjectileFactory))))));
		this.customShootLogic = null;
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent,this.repeaterComponent,this.projectileComponent};
		this.addComponents(var2);
		this.addUseEvent(200,(Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::onUseEvent))));
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
			this.repeaterComponent.start(var1,this.getMaxTriggers(),this.getTriggerInterval());
			return;
		}
	}
	private void onRepeaterTrigger(LivingEntity var1, IAbility var2) {
		if (!(this.canContinueToUse(var1))){
			this.repeaterComponent.stop(var1);
		}
		if ((this.customShootLogic) != (null)){
			this.customShootLogic.customShootLogic(var1);
		}else{
			this.projectileComponent.shoot(var1,this.getProjectileSpeed(),this.getProjectileSpread());
		}
	}
	private void onRepeaterStop(LivingEntity var1, IAbility var2) {
		this.continuousComponent.stopContinuity(var1);
		this.cooldownComponent.startCooldown(var1,this.getRepeaterCooldown());
	}
	public void setCustomShootLogic(RepeaterAbility$IOnTriggerShootEvent var1) {
		this.customShootLogic = var1;
	}
	public float getProjectileSpeed() {
		return 2F;
	}
	public float getProjectileSpread() {
		return 1F;
	}
	public abstract int getMaxTriggers();
	public abstract int getTriggerInterval();
	public abstract float getRepeaterCooldown();
	public abstract <P extends NuProjectileEntity> P getProjectileFactory(LivingEntity var0);
	public void appendDescription(Player var1, AbilityTooltip var2) {
		super.appendDescription(var1,var2);
		RepeaterAbility var3 = this;
		Consumer<CooldownComponentTooltip> var4 = (l0) -> {
			l0.cooldown(this.getRepeaterCooldown());
		};
		CooldownComponentTooltip.generate(var2,var4);
		RepeaterAbility var5 = this;
		Consumer<RepeaterComponentTooltip> var6 = (l0) -> {
			l0.triggers(this.getMaxTriggers(),(float)(this.getTriggerInterval()));
		};
		RepeaterComponentTooltip.generate(var2,var6);
		Consumer<ProjectileComponentTooltip> var7 = (l0) -> {
		};
		ProjectileComponentTooltip.generate(var2,var7);
	}
}