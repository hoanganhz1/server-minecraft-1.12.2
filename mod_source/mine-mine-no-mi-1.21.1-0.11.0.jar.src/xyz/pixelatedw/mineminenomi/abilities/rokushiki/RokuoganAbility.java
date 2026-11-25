package xyz.pixelatedw.mineminenomi.abilities.rokushiki;

import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$INodeFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PunchAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory;
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
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.rokushiki.RokuoganProjectile;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

public class RokuoganAbility extends PunchAbility {
	// Fields
	private static final float COOLDOWN = 700F;
	public static final float EXPLOSION_RANGE = 7F;
	private static final int DORIKI = 9000;
	private static final int MARTIAL_ARTS_POINTS = 10;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<RokuoganAbility>> INSTANCE;
	private final ProjectileComponent projectileComponent;
	private final ExplosionComponent explosionComponent;

	public RokuoganAbility(AbilityCore<RokuoganAbility> var1) {
		super((AbilityCore)(var1));
		this.projectileComponent = new ProjectileComponent((IAbility)(this),(ProjectileComponent$IProjectileFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory)((ProjectileComponent$IProjectileFactory) ((ProjectileComponent$IProjectileFactory<RokuoganProjectile>) (this::createProjectile))))));
		this.explosionComponent = new ExplosionComponent((IAbility)(this));
		AbilityComponent[] var2 = new AbilityComponent[]{this.projectileComponent,this.explosionComponent};
		this.addComponents(var2);
	}
	public boolean onHitEffect(LivingEntity var1, LivingEntity var2, DamageSource var3) {
		RokuoganProjectile var4 = ((RokuoganProjectile)(this.projectileComponent.getNewProjectile(var1)));
		var1.level().addFreshEntity((Entity)(var4));
		var4.shootFromRotation((Entity)(var1),var1.getXRot(),var1.getYRot(),0F,2F,1F);
		Vec3 var5 = var1.getLookAngle().multiply(2D,2D,2D);
		var4.setPos((var2.getX()) + (var5.x),(var2.getY()) + (var5.y),(var2.getZ()) + (var5.z));
		var1.level().playSound((Player)(null),var1.blockPosition(),((SoundEvent)(ModSounds.ROKUOGAN.get())),SoundSource.PLAYERS,2.5F,(0.2F) + (var1.getRandom().nextFloat()));
		return true;
	}
	public int getUseLimit() {
		return 1;
	}
	public float getPunchDamage() {
		return 60F;
	}
	public float getPunchCooldown() {
		return 700F;
	}
	private RokuoganProjectile createProjectile(LivingEntity var1) {
		return new RokuoganProjectile(var1.level(),var1,(IAbility)(this));
	}
	private static AbilityNode createNode(LivingEntity var0) {
		AbilityNode var1 = new AbilityNode(((AbilityCore)(INSTANCE.get())).getLocalizedName(),((AbilityCore)(INSTANCE.get())).getIcon(),new AbilityNode$NodePos(7F,0F));
		DorikiUnlockCondition var2 = DorikiUnlockCondition.atLeast(9000D);
		RequiredRaceUnlockCondition var3 = RequiredRaceUnlockCondition.requires(((Race)(ModRaces.HUMAN.get())));
		NodeUnlockCondition var4 = ((xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition)(var3)).and(((xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition)(var2)).and((NodeUnlockCondition)(TrainingPointsUnlockCondition.martialArts(10F)))).or(((xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition)(var2)).and((NodeUnlockCondition)(TrainingPointsUnlockCondition.martialArts(15F))));
		NodeUnlockAction var5 = SubtractPointsAction.martialArts(10F).onlyIf(var3).andThen(SubtractPointsAction.martialArts(15F).onlyIf(((xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition)(var3)).not())).andThen(UnlockAbilityAction.unlock((Supplier)(INSTANCE)));
		AbilityNode[] var6 = new AbilityNode[6];
		var6[0] = ((AbilityCore)(GeppoAbility.INSTANCE.get())).getNode(var0);
		var6[1] = ((AbilityCore)(TekkaiAbility.INSTANCE.get())).getNode(var0);
		var6[2] = ((AbilityCore)(RankyakuAbility.INSTANCE.get())).getNode(var0);
		var6[3] = ((AbilityCore)(SoruAbility.INSTANCE.get())).getNode(var0);
		var6[4] = ((AbilityCore)(ShiganAbility.INSTANCE.get())).getNode(var0);
		var6[5] = ((AbilityCore)(KamieAbility.INSTANCE.get())).getNode(var0);
		var1.addPrerequisites(var6);
		var1.setUnlockRule(var4,var5);
		return var1;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv3_2 = new Pair[]{ImmutablePair.of((Object)("The user places both their fists right in front of the target to focus their physical strength to launch a devastating shockwave forward"),(Object)(null))};
			Component[] lv3_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv3_2);
			SourceType[] lv3_4 = new SourceType[]{SourceType.FIST};
			AbilityCore$INodeFactory[] lv3_5 = new AbilityCore$INodeFactory[1];
			AbilityCore$INodeFactory lv3_6 = RokuoganAbility::createNode;
			lv3_5[0] = lv3_6;
			return new AbilityCore$Builder(l0,l1,AbilityCategory.RACIAL,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(RokuoganAbility::new)))).addDescriptionLine(lv3_3).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceElement(SourceElement.SHOCKWAVE).setSourceType(lv3_4).setNodeFactories(lv3_5).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("rokuogan","Rokuogan",var0));
	}
	public void appendDescription(Player var1, AbilityTooltip var2) {
		super.appendDescription(var1,var2);
		Consumer<ProjectileComponentTooltip> var3 = (l0) -> {
		};
		ProjectileComponentTooltip.generate(var2,var3);
		Consumer<ExplosionComponentTooltip> var4 = (l0) -> {
			l0.range(7F);
		};
		ExplosionComponentTooltip.generate(var2,var4);
	}
}