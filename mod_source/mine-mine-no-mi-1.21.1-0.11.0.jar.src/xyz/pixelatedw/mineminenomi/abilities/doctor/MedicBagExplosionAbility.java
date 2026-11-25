package xyz.pixelatedw.mineminenomi.abilities.doctor;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.HealComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent$RangeType;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.CooldownComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.HealComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.RangeComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode$NodePos;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.SubtractPointsAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.TrainingPointsUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class MedicBagExplosionAbility extends Ability {
	// Fields
	public static final TargetPredicate ENEMY_AREA_CHECK = new TargetPredicate().testEnemyFaction();
	public static final TargetPredicate FRIENDLY_AREA_CHECK = new TargetPredicate().testFriendlyFaction();
	private static final int COOLDOWN = 800;
	private static final int RANGE = 10;
	private static final int MIN_HEAL = 5;
	private static final int MAX_HEAL = 50;
	private static final int NODE_POINTS_TE = 10;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<MedicBagExplosionAbility>> INSTANCE;
	private final RangeComponent rangeComponent;
	private final HealComponent healComponent;

	public MedicBagExplosionAbility(AbilityCore<MedicBagExplosionAbility> var1) {
		super((AbilityCore)(var1));
		this.rangeComponent = new RangeComponent((IAbility)(this));
		this.healComponent = new HealComponent((IAbility)(this));
		AbilityComponent[] var2 = new AbilityComponent[]{this.rangeComponent,this.healComponent};
		this.addComponents(var2);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::requiresMedicBag))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		Optional var3 = (!(var1.getMainHandItem().isEmpty())) ? (Optional.ofNullable(var1.getMainHandItem())) : ((Optional)(ItemsHelper.findItemInSlot(var1,EquipmentSlot.CHEST,((Item)(ModArmors.MEDIC_BAG.get())))));
		if (!(var3.isPresent())){
			return;
		}else{
			float var4 = (float)(Mth.clamp(WyHelper.percentage(20D,(double)(var1.getMaxHealth())),5D,50D));
			this.healComponent.healTarget(var1,var1,var4);
			Iterator var5 = this.rangeComponent.getTargetsInArea(var1,10F,ENEMY_AREA_CHECK).iterator();
			do{
				if (var5.hasNext()){
					LivingEntity var6 = ((LivingEntity)(var5.next()));
					FailedExperimentAbility$Mode var7 = FailedExperimentAbility$Mode.values()[WyHelper.randomWithRange(0,5)];
					var6.addEffect(new MobEffectInstance(var7.getEffect(),200,1));
					continue;
				}else{
					break;
				}
			} while (true);
			Iterator var6_1 = this.rangeComponent.getTargetsInArea(var1,10F,FRIENDLY_AREA_CHECK).iterator();
			do{
				if (var6_1.hasNext()){
					((LivingEntity)(var6_1.next())).addEffect(new MobEffectInstance(MobEffects.REGENERATION,100,2));
					continue;
				}else{
					break;
				}
			} while (true);
			WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.MEDIC_BAG_EXPLOSION.get())),(Entity)(var1),var1.position().x,var1.position().y,var1.position().z);
			this.cooldownComponent.startCooldown(var1,800F);
			((ItemStack)(var3.get())).hurtAndBreak(250,var1,EquipmentSlot.MAINHAND);
			return;
		}
	}
	private static AbilityNode createNode(LivingEntity var0) {
		AbilityNode var1 = new AbilityNode(((AbilityCore)(INSTANCE.get())).getLocalizedName(),((AbilityCore)(INSTANCE.get())).getIcon(),new AbilityNode$NodePos(-11F,-3F));
		TrainingPointsUnlockCondition var2 = TrainingPointsUnlockCondition.technology(10F);
		NodeUnlockAction var3 = SubtractPointsAction.technology(10F).andThen(UnlockAbilityAction.unlock((Supplier)(INSTANCE)));
		AbilityNode[] var4 = new AbilityNode[2];
		var4[0] = ((AbilityCore)(FirstAidAbility.INSTANCE.get())).getNode(var0);
		var4[1] = ((AbilityCore)(AntidoteShotAbility.INSTANCE.get())).getNode(var0);
		var1.addPrerequisites(var4);
		var1.setUnlockRule(var2,var3);
		return var1;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv4_2 = new Pair[]{ImmutablePair.of((Object)("By sacrificing the medic bag's durability the user can heal themselves with regeneration while applying debuffs to nearby enemies."),(Object)(null))};
			Component[] lv4_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv4_2);
			AbilityCore$INodeFactory[] lv4_4 = new AbilityCore$INodeFactory[1];
			AbilityCore$INodeFactory lv4_5 = MedicBagExplosionAbility::createNode;
			lv4_4[0] = lv4_5;
			return new AbilityCore$Builder(l0,l1,AbilityCategory.STYLE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(MedicBagExplosionAbility::new)))).addDescriptionLine(lv4_3).setNodeFactories(lv4_4).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("medic_bag_explosion","Medic Bag Explosion",var0));
	}
	public void appendDescription(Player var1, AbilityTooltip var2) {
		super.appendDescription(var1,var2);
		Consumer<CooldownComponentTooltip> var3 = (l0) -> {
			l0.cooldown(800F);
		};
		CooldownComponentTooltip.generate(var2,var3);
		Consumer<RangeComponentTooltip> var4 = (l0) -> {
			l0.range(10F,RangeComponent$RangeType.AOE);
		};
		RangeComponentTooltip.generate(var2,var4);
		Consumer<HealComponentTooltip> var5 = (l0) -> {
			l0.heal(5F,50F);
		};
		HealComponentTooltip.generate(var2,var5);
	}
}