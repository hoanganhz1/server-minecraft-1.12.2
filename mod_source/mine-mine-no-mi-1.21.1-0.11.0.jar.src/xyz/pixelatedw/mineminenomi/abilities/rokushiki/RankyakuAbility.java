package xyz.pixelatedw.mineminenomi.abilities.rokushiki;

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
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.CooldownComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.ExplosionComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.ProjectileComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode$NodePos;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.SubtractPointsAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.DorikiUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.RequiredRaceUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.TrainingPointsUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.rokushiki.RankyakuProjectile;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class RankyakuAbility extends Ability {
	// Fields
	public static final float COOLDOWN = 240F;
	public static final float EXPLOSION_DAMAGE = 15F;
	public static final float EXPLOSION_RANGE = 3F;
	private static final int DORIKI = 5000;
	private static final int ACCURACY_POINTS = 4;
	private static final int MARTIAL_ARTS_POINTS = 1;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<RankyakuAbility>> INSTANCE;
	private final ProjectileComponent projectileComponent;
	private final ExplosionComponent explosionComponent;

	public RankyakuAbility(AbilityCore<RankyakuAbility> var1) {
		super((AbilityCore)(var1));
		this.projectileComponent = new ProjectileComponent((IAbility)(this),(ProjectileComponent$IProjectileFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory)((ProjectileComponent$IProjectileFactory) ((ProjectileComponent$IProjectileFactory<RankyakuProjectile>) (this::createProjectile))))));
		this.explosionComponent = new ExplosionComponent((IAbility)(this));
		AbilityComponent[] var2 = new AbilityComponent[]{this.projectileComponent,this.explosionComponent};
		this.addComponents(var2);
		this.explosionComponent.setBlocksAffectedLimit(256);
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::onUseEvent))));
	}
	private void onUseEvent(LivingEntity var1, IAbility var2) {
		this.projectileComponent.shoot(var1,3.25F,1F);
		this.cooldownComponent.startCooldown(var1,240F);
	}
	private RankyakuProjectile createProjectile(LivingEntity var1) {
		return new RankyakuProjectile(var1.level(),var1,(IAbility)(this));
	}
	private static AbilityNode createNode(LivingEntity var0) {
		AbilityNode var1 = new AbilityNode(((AbilityCore)(INSTANCE.get())).getLocalizedName(),((AbilityCore)(INSTANCE.get())).getIcon(),new AbilityNode$NodePos(5F,0F));
		DorikiUnlockCondition var2 = DorikiUnlockCondition.atLeast(5000D);
		RequiredRaceUnlockCondition var3 = RequiredRaceUnlockCondition.requires(((Race)(ModRaces.HUMAN.get())));
		NodeUnlockCondition var4 = ((xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition)(var3)).and(((xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition)(var2)).and((NodeUnlockCondition)(TrainingPointsUnlockCondition.martialArts(1F))).and(TrainingPointsUnlockCondition.accuracy(4F))).or(((xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition)(var2)).and((NodeUnlockCondition)(TrainingPointsUnlockCondition.martialArts(1.5F))).and(TrainingPointsUnlockCondition.accuracy(6F)));
		var1.setUnlockRule(var4,SubtractPointsAction.martialArts(1F).onlyIf(var3).andThen(SubtractPointsAction.accuracy(4F).onlyIf(var3)).andThen(SubtractPointsAction.martialArts(1.5F).onlyIf(((xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition)(var3)).not())).andThen(SubtractPointsAction.accuracy(6F).onlyIf(((xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition)(var3)).not())).andThen(UnlockAbilityAction.unlock((Supplier)(INSTANCE))));
		return var1;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv4_2 = new Pair[]{ImmutablePair.of((Object)("By kicking at a very high speed, the user launches an air blade at the opponent"),(Object)(null))};
			Component[] lv4_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv4_2);
			SourceType[] lv4_4 = new SourceType[]{SourceType.SLASH,SourceType.FIST,SourceType.INDIRECT};
			AbilityCore$INodeFactory[] lv4_5 = new AbilityCore$INodeFactory[1];
			AbilityCore$INodeFactory lv4_6 = RankyakuAbility::createNode;
			lv4_5[0] = lv4_6;
			return new AbilityCore$Builder(l0,l1,AbilityCategory.RACIAL,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(RankyakuAbility::new)))).addDescriptionLine(lv4_3).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceElement(SourceElement.AIR).setSourceType(lv4_4).setNodeFactories(lv4_5).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("rankyaku","Rankyaku",var0));
	}
	public void appendDescription(Player var1, AbilityTooltip var2) {
		super.appendDescription(var1,var2);
		Consumer<CooldownComponentTooltip> var3 = (l0) -> {
			l0.cooldown(240F);
		};
		CooldownComponentTooltip.generate(var2,var3);
		Consumer<ProjectileComponentTooltip> var4 = (l0) -> {
		};
		ProjectileComponentTooltip.generate(var2,var4);
		Consumer<ExplosionComponentTooltip> var5 = (l0) -> {
			l0.damage(15F).range(3F);
		};
		ExplosionComponentTooltip.generate(var2,var5);
	}
}