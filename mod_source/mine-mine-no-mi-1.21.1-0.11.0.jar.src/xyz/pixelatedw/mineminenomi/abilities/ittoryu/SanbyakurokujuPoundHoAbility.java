package xyz.pixelatedw.mineminenomi.abilities.ittoryu;

import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
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
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.TrainingPointsUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.ittoryu.SanbyakurokujuPoundHoProjectile;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class SanbyakurokujuPoundHoAbility extends Ability {
	// Fields
	private static final float COOLDOWN = 320F;
	private static final int ANIMATION_TICKS = 7;
	private static final int NODE_POINTS_WM = 3;
	private static final int NODE_POINTS_AC = 5;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<SanbyakurokujuPoundHoAbility>> INSTANCE;
	private final AnimationComponent animationComponent;
	private final ProjectileComponent projectileComponent;
	private final ExplosionComponent explosionComponent;

	public SanbyakurokujuPoundHoAbility(AbilityCore<SanbyakurokujuPoundHoAbility> var1) {
		super((AbilityCore)(var1));
		this.animationComponent = new AnimationComponent((IAbility)(this));
		this.projectileComponent = new ProjectileComponent((IAbility)(this),(ProjectileComponent$IProjectileFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory)((ProjectileComponent$IProjectileFactory) ((ProjectileComponent$IProjectileFactory<SanbyakurokujuPoundHoProjectile>) (this::createProjectile))))));
		this.explosionComponent = new ExplosionComponent((IAbility)(this));
		AbilityComponent[] var2 = new AbilityComponent[]{this.animationComponent,this.projectileComponent,this.explosionComponent};
		this.addComponents(var2);
		this.explosionComponent.setBlocksAffectedLimit(218);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::requiresSword))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::onUseEvent))));
	}
	private void onUseEvent(LivingEntity var1, IAbility var2) {
		var1.getMainHandItem().hurtAndBreak(1,var1,EquipmentSlot.MAINHAND);
		this.animationComponent.start(var1,ModAnimations.OVER_SHOULDER_SLASH,7);
		this.projectileComponent.shoot(var1);
		if (!(var1.level().isClientSide)){
			((ServerLevel)(var1.level())).getChunkSource().broadcastAndSend((Entity)(var1),(Packet)(new ClientboundAnimatePacket((Entity)(var1),0)));
		}
		this.cooldownComponent.startCooldown(var1,320F);
	}
	private SanbyakurokujuPoundHoProjectile createProjectile(LivingEntity var1) {
		return new SanbyakurokujuPoundHoProjectile(var1.level(),var1,(Ability)(this));
	}
	private static AbilityNode createNode(LivingEntity var0) {
		AbilityNode var1 = new AbilityNode(((AbilityCore)(INSTANCE.get())).getLocalizedName(),((AbilityCore)(INSTANCE.get())).getIcon(),new AbilityNode$NodePos(-4F,-1F));
		NodeUnlockCondition var2 = TrainingPointsUnlockCondition.accuracy(5F).and(TrainingPointsUnlockCondition.weaponMastery(3F));
		NodeUnlockAction var3 = SubtractPointsAction.accuracy(5F).andThen(SubtractPointsAction.weaponMastery(3F).andThen(UnlockAbilityAction.unlock((Supplier)(INSTANCE))));
		AbilityNode[] var4 = new AbilityNode[1];
		var4[0] = ((AbilityCore)(ShiShishiSonsonAbility.INSTANCE.get())).getNode(var0);
		var1.addPrerequisites(var4);
		var1.setUnlockRule(var2,var3);
		return var1;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv4_2 = new Pair[]{ImmutablePair.of((Object)("The user launches a powerful ranged slash, causing great destruction"),(Object)(null))};
			Component[] lv4_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv4_2);
			SourceType[] lv4_4 = new SourceType[]{SourceType.SLASH};
			AbilityCore$INodeFactory[] lv4_5 = new AbilityCore$INodeFactory[1];
			AbilityCore$INodeFactory lv4_6 = SanbyakurokujuPoundHoAbility::createNode;
			lv4_5[0] = lv4_6;
			return new AbilityCore$Builder(l0,l1,AbilityCategory.STYLE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(SanbyakurokujuPoundHoAbility::new)))).addDescriptionLine(lv4_3).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceType(lv4_4).setSourceElement(SourceElement.AIR).setNodeFactories(lv4_5).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("sanbyakurokuju_pound_ho","Sanbyakurokuju Pound Ho",var0));
	}
	public void appendDescription(Player var1, AbilityTooltip var2) {
		super.appendDescription(var1,var2);
		Consumer<CooldownComponentTooltip> var3 = (l0) -> {
			l0.cooldown(320F);
		};
		CooldownComponentTooltip.generate(var2,var3);
		Consumer<ProjectileComponentTooltip> var4 = (l0) -> {
			l0.damage(25F);
		};
		ProjectileComponentTooltip.generate(var2,var4);
		Consumer<ExplosionComponentTooltip> var5 = (l0) -> {
			l0.damage(10F).range(2F);
		};
		ExplosionComponentTooltip.generate(var2,var5);
	}
}