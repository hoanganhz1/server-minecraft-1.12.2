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
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.CooldownComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.ExplosionComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.ProjectileComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities.ProjectileAbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.bomu.NoseFancyCannonProjectile;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class NoseFancyCannonAbility extends Ability {
	// Fields
	private static final float COOLDOWN = 80F;
	private static final int ANIMATION_TICKS = 7;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<NoseFancyCannonAbility>> INSTANCE;
	private final ProjectileComponent projectileComponent;
	private final AnimationComponent animationComponent;
	private final ExplosionComponent explosionComponent;

	public NoseFancyCannonAbility(AbilityCore<NoseFancyCannonAbility> var1) {
		super((AbilityCore)(var1));
		this.projectileComponent = new ProjectileComponent((IAbility)(this),(ProjectileComponent$IProjectileFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory)((ProjectileComponent$IProjectileFactory) ((ProjectileComponent$IProjectileFactory<NoseFancyCannonProjectile>) (this::createProjectile))))));
		this.animationComponent = new AnimationComponent((IAbility)(this));
		this.explosionComponent = new ExplosionComponent((IAbility)(this));
		AbilityComponent[] var2 = new AbilityComponent[]{this.projectileComponent,this.animationComponent,this.explosionComponent};
		this.addComponents(var2);
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::onUseEvent))));
	}
	private void onUseEvent(LivingEntity var1, IAbility var2) {
		this.projectileComponent.shoot(var1,2F,1F);
		this.cooldownComponent.startCooldown(var1,80F);
		this.animationComponent.start(var1,ModAnimations.AIM_SNIPER,7);
	}
	private NoseFancyCannonProjectile createProjectile(LivingEntity var1) {
		return new NoseFancyCannonProjectile(var1.level(),var1,this);
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv4_2 = new Pair[]{ImmutablePair.of((Object)("Shoots dried mucus at the opponent, which explodes on impact"),(Object)(null))};
			Component[] lv4_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv4_2);
			AbilityCore$IAbilityGoalFactory[] lv4_4 = new AbilityCore$IAbilityGoalFactory[1];
			AbilityCore$IAbilityGoalFactory lv4_5 = ProjectileAbilityWrapperGoal::new;
			lv4_4[0] = lv4_5;
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(NoseFancyCannonAbility::new)))).addDescriptionLine(lv4_3).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceElement(SourceElement.EXPLOSION).addGoalWrapper(lv4_4).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("nose_fancy_cannon","Nose Fancy Cannon",var0));
	}
	public void appendDescription(Player var1, AbilityTooltip var2) {
		super.appendDescription(var1,var2);
		Consumer<CooldownComponentTooltip> var3 = (l0) -> {
			l0.cooldown(80F);
		};
		CooldownComponentTooltip.generate(var2,var3);
		Consumer<ProjectileComponentTooltip> var4 = (l0) -> {
			l0.damage(10F);
		};
		ProjectileComponentTooltip.generate(var2,var4);
		Consumer<ExplosionComponentTooltip> var5 = (l0) -> {
			l0.damage(15F).range(3F);
		};
		ExplosionComponentTooltip.generate(var2,var5);
	}
}