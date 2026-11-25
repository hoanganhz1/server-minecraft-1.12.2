package xyz.pixelatedw.mineminenomi.abilities.bomu;

import java.util.function.Consumer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IAbilityGoalFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IAfterShootEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.ContinuousComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.CooldownComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.ExplosionComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.ProjectileComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ProjectileAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.bomu.BreezeBreathBombProjectile;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class BreezeBreathBombAbility extends Ability {
	// Fields
	private static final float COOLDOWN = 300F;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<BreezeBreathBombAbility>> INSTANCE;
	private final ContinuousComponent continuousComponent;
	private final ProjectileComponent projectileComponent;
	private final ExplosionComponent explosionComponent;

	public BreezeBreathBombAbility(AbilityCore<BreezeBreathBombAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this)).addEndEvent(100,(ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
		this.projectileComponent = new ProjectileComponent((IAbility)(this),(ProjectileComponent$IProjectileFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory)((ProjectileComponent$IProjectileFactory) ((ProjectileComponent$IProjectileFactory<BreezeBreathBombProjectile>) (this::createProjectile)))))).addAfterShootEvent(100,(ProjectileComponent$IAfterShootEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IAfterShootEvent)(this::afterShootEvent))));
		this.explosionComponent = new ExplosionComponent((IAbility)(this));
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent,this.projectileComponent,this.explosionComponent};
		this.addComponents(var2);
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::onUseEvent))));
	}
	private void onUseEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.triggerContinuity(var1);
	}
	private void endContinuityEvent(LivingEntity var1, IAbility var2) {
		this.cooldownComponent.startCooldown(var1,300F);
	}
	private void afterShootEvent(LivingEntity var1) {
		this.continuousComponent.stopContinuity(var1);
	}
	public BreezeBreathBombProjectile createProjectile(LivingEntity var1) {
		return new BreezeBreathBombProjectile(var1.level(),var1,this);
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv5_2 = new Pair[]{ImmutablePair.of((Object)("Load a gun with explosive breath and shoot a chain explosion"),(Object)(null))};
			Component[] lv5_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv5_2);
			SourceType[] lv5_4 = new SourceType[]{SourceType.BULLET};
			AbilityCore$IAbilityGoalFactory[] lv5_5 = new AbilityCore$IAbilityGoalFactory[1];
			AbilityCore$IAbilityGoalFactory lv5_6 = ProjectileAbilityWrapperGoal::new;
			lv5_5[0] = lv5_6;
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(BreezeBreathBombAbility::new)))).addDescriptionLine(lv5_3).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceType(lv5_4).addGoalWrapper(lv5_5).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("breeze_breath_bomb","Breeze Breath Bomb",var0));
	}
	public void appendDescription(Player var1, AbilityTooltip var2) {
		super.appendDescription(var1,var2);
		Consumer<CooldownComponentTooltip> var3 = (l0) -> {
			l0.cooldown(300F);
		};
		CooldownComponentTooltip.generate(var2,var3);
		Consumer<ContinuousComponentTooltip> var4 = (l0) -> {
		};
		ContinuousComponentTooltip.generate(var2,var4);
		Consumer<ProjectileComponentTooltip> var5 = (l0) -> {
			l0.damage(15F);
		};
		ProjectileComponentTooltip.generate(var2,var5);
		Consumer<ExplosionComponentTooltip> var6 = (l0) -> {
			l0.damage(12F).range(2F);
		};
		ExplosionComponentTooltip.generate(var2,var6);
	}
}