package xyz.pixelatedw.mineminenomi.abilities.cyborg;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.ColaUsageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.ColaUsageComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.CooldownComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.ProjectileComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode$NodePos;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.SubtractPointsAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.TrainingPointsUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.cyborg.CoupDeVentProjectile;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class CoupDeVentAbility extends Ability {
	// Fields
	private static final float COOLDOWN = 240F;
	private static final int COLA_REQUIRED = 30;
	private static final int ACCURACY_POINTS = 2;
	private static final int TECHNOLOGY_POINTS = 3;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<CoupDeVentAbility>> INSTANCE;
	private final ProjectileComponent projectileComponent;
	private final ColaUsageComponent colaUsageComponent;

	public CoupDeVentAbility(AbilityCore<CoupDeVentAbility> var1) {
		super((AbilityCore)(var1));
		this.projectileComponent = new ProjectileComponent((IAbility)(this),(ProjectileComponent$IProjectileFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory)((ProjectileComponent$IProjectileFactory) ((ProjectileComponent$IProjectileFactory<CoupDeVentProjectile>) (this::createProjectile))))));
		this.colaUsageComponent = new ColaUsageComponent((IAbility)(this));
		AbilityComponent[] var2 = new AbilityComponent[]{this.projectileComponent,this.colaUsageComponent};
		this.addComponents(var2);
		this.addCanUseCheck(ColaUsageComponent.hasEnoughCola(30));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		if ((((EntityStatsData)(((java.util.Optional)(EntityStatsData.get(var1))).orElse((Object)(null))))) == (null)){
			return;
		}else{
			this.projectileComponent.shoot(var1,3F,1F);
			this.colaUsageComponent.consumeCola(var1,30);
			this.cooldownComponent.startCooldown(var1,240F);
			return;
		}
	}
	private CoupDeVentProjectile createProjectile(LivingEntity var1) {
		return new CoupDeVentProjectile(var1.level(),var1,(IAbility)(this));
	}
	private static AbilityNode createNode(LivingEntity var0) {
		AbilityNode var1 = new AbilityNode(((AbilityCore)(INSTANCE.get())).getLocalizedName(),((AbilityCore)(INSTANCE.get())).getIcon(),new AbilityNode$NodePos(14F,-2F));
		NodeUnlockCondition var2 = TrainingPointsUnlockCondition.technology(3F).and(TrainingPointsUnlockCondition.accuracy(2F));
		NodeUnlockAction var3 = SubtractPointsAction.technology(3F).andThen(SubtractPointsAction.accuracy(2F)).andThen(UnlockAbilityAction.unlock((Supplier)(INSTANCE)));
		AbilityNode[] var4 = new AbilityNode[1];
		var4[0] = ((AbilityCore)(ColaFuelAbility.INSTANCE.get())).getNode(var0);
		var1.addPrerequisites(var4);
		var1.setUnlockRule(var2,var3);
		return var1;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv4_2 = new Pair[]{ImmutablePair.of((Object)("Launches a powerful blast of compressed air that blows the opponent away."),(Object)(null))};
			Component[] lv4_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv4_2);
			SourceType[] lv4_4 = new SourceType[]{SourceType.TECHNOLOGY};
			AbilityCore$INodeFactory[] lv4_5 = new AbilityCore$INodeFactory[1];
			AbilityCore$INodeFactory lv4_6 = CoupDeVentAbility::createNode;
			lv4_5[0] = lv4_6;
			return new AbilityCore$Builder(l0,l1,AbilityCategory.RACIAL,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(CoupDeVentAbility::new)))).addDescriptionLine(lv4_3).setSourceHakiNature(SourceHakiNature.SPECIAL).setSourceElement(SourceElement.SHOCKWAVE).setSourceType(lv4_4).setNodeFactories(lv4_5).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("coup_de_vent","Coup de Vent",var0));
	}
	public void appendDescription(Player var1, AbilityTooltip var2) {
		super.appendDescription(var1,var2);
		Consumer<CooldownComponentTooltip> var3 = (l0) -> {
			l0.cooldown(240F);
		};
		CooldownComponentTooltip.generate(var2,var3);
		Consumer<ColaUsageComponentTooltip> var4 = (l0) -> {
			l0.cola(30F);
		};
		ColaUsageComponentTooltip.generate(var2,var4);
		Consumer<ProjectileComponentTooltip> var5 = (l0) -> {
		};
		ProjectileComponentTooltip.generate(var2,var5);
	}
}