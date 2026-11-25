package xyz.pixelatedw.mineminenomi.abilities.cyborg;

import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnTickEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$INodeFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.CooldownComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.ProjectileComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode$NodePos;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.SubtractPointsAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.TrainingPointsUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.cyborg.StrongRightProjectile;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class StrongRightAbility extends Ability {
	// Fields
	private static final float COOLDOWN = 100F;
	private static final int TECHNOLOGY_POINTS = 3;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<StrongRightAbility>> INSTANCE;
	private final ProjectileComponent projectileComponent;
	private boolean isShot;
	private NuProjectileEntity projectile;

	public StrongRightAbility(AbilityCore<StrongRightAbility> var1) {
		super((AbilityCore)(var1));
		this.projectileComponent = new ProjectileComponent((IAbility)(this),(ProjectileComponent$IProjectileFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory)((ProjectileComponent$IProjectileFactory) ((ProjectileComponent$IProjectileFactory<StrongRightProjectile>) (this::createProjectile))))));
		AbilityComponent[] var2 = new AbilityComponent[]{this.projectileComponent};
		this.addComponents(var2);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::requiresHands))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
		this.addTickEvent((Ability$IOnTickEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnTickEvent)(this::tickEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		this.projectileComponent.shoot(var1);
		this.projectile = this.projectileComponent.getShotProjectile();
		this.isShot = true;
		var1.addEffect(new MobEffectInstance((Holder)(ModEffects.NO_HANDS),200,0));
		this.cooldownComponent.startCooldown(var1,100F);
	}
	private void tickEvent(LivingEntity var1, IAbility var2) {
		if ((this.isShot) && (((this.projectile) != (null)) && (!(this.projectile.isAlive())))){
			this.projectile.remove(Entity.RemovalReason.DISCARDED);
			this.projectile = null;
			var1.removeEffect((Holder)(ModEffects.NO_HANDS));
			this.isShot = false;
		}
	}
	private StrongRightProjectile createProjectile(LivingEntity var1) {
		return new StrongRightProjectile(var1.level(),var1,(IAbility)(this));
	}
	private static AbilityNode createNode(LivingEntity var0) {
		AbilityNode var1 = new AbilityNode(((AbilityCore)(INSTANCE.get())).getLocalizedName(),((AbilityCore)(INSTANCE.get())).getIcon(),new AbilityNode$NodePos(13F,-4F));
		TrainingPointsUnlockCondition var2 = TrainingPointsUnlockCondition.technology(3F);
		var1.setUnlockRule(var2,SubtractPointsAction.technology(3F).andThen(UnlockAbilityAction.unlock((Supplier)(INSTANCE))));
		return var1;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv3_2 = new Pair[]{ImmutablePair.of((Object)("The user punches the opponent with an extensible short range metal fist."),(Object)(null))};
			Component[] lv3_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv3_2);
			SourceType[] lv3_4 = new SourceType[]{SourceType.FIST,SourceType.TECHNOLOGY};
			AbilityCore$INodeFactory[] lv3_5 = new AbilityCore$INodeFactory[1];
			AbilityCore$INodeFactory lv3_6 = StrongRightAbility::createNode;
			lv3_5[0] = lv3_6;
			return new AbilityCore$Builder(l0,l1,AbilityCategory.RACIAL,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(StrongRightAbility::new)))).addDescriptionLine(lv3_3).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(lv3_4).setNodeFactories(lv3_5).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("strong_right","Strong Right",var0));
	}
	public void appendDescription(Player var1, AbilityTooltip var2) {
		super.appendDescription(var1,var2);
		Consumer<CooldownComponentTooltip> var3 = (l0) -> {
			l0.cooldown(100F);
		};
		CooldownComponentTooltip.generate(var2,var3);
		Consumer<ProjectileComponentTooltip> var4 = (l0) -> {
		};
		ProjectileComponentTooltip.generate(var2,var4);
	}
}