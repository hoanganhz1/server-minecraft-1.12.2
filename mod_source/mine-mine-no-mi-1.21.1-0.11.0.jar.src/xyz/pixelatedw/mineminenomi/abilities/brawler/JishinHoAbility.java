package xyz.pixelatedw.mineminenomi.abilities.brawler;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent$RangeType;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.CooldownComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.DealDamageComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.RangeComponentTooltip;
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
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.GroundParticlesEffect$Details;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$Details;

public class JishinHoAbility extends Ability {
	// Fields
	private static final float COOLDOWN = 300F;
	private static final float DAMAGE = 25F;
	private static final float RANGE = 7F;
	private static final GroundParticlesEffect$Details PARTICLE_DETAILS = new GroundParticlesEffect$Details(7,100);
	private static final int NODE_POINTS_MA = 10;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<JishinHoAbility>> INSTANCE;
	private final ContinuousComponent continuousComponent;
	private final DealDamageComponent dealDamageComponent;
	private final RangeComponent rangeComponent;

	public JishinHoAbility(AbilityCore<JishinHoAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this));
		this.dealDamageComponent = new DealDamageComponent((IAbility)(this));
		this.rangeComponent = new RangeComponent((IAbility)(this));
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent,this.dealDamageComponent,this.rangeComponent};
		this.addComponents(var2);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::requiresEmptyHand))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::onUseEvent))));
	}
	private static AbilityNode createNode(LivingEntity var0) {
		AbilityNode var1 = new AbilityNode(((AbilityCore)(INSTANCE.get())).getLocalizedName(),((AbilityCore)(INSTANCE.get())).getIcon(),new AbilityNode$NodePos(-2F,6F));
		TrainingPointsUnlockCondition var2 = TrainingPointsUnlockCondition.martialArts(10F);
		NodeUnlockAction var3 = SubtractPointsAction.martialArts(10F).andThen(UnlockAbilityAction.unlock((Supplier)(INSTANCE)));
		AbilityNode[] var4 = new AbilityNode[1];
		var4[0] = ((AbilityCore)(HakaiHoAbility.INSTANCE.get())).getNode(var0);
		var1.addPrerequisites(var4);
		var1.setUnlockRule(var2,var3);
		return var1;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv5_2 = new Pair[]{ImmutablePair.of((Object)("Punches the ground to cause a quake that damages everyone around"),(Object)(null))};
			Component[] lv5_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv5_2);
			SourceType[] lv5_4 = new SourceType[]{SourceType.FIST,SourceType.INDIRECT};
			AbilityCore$INodeFactory[] lv5_5 = new AbilityCore$INodeFactory[1];
			AbilityCore$INodeFactory lv5_6 = JishinHoAbility::createNode;
			lv5_5[0] = lv5_6;
			return new AbilityCore$Builder(l0,l1,AbilityCategory.STYLE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(JishinHoAbility::new)))).addDescriptionLine(lv5_3).setSourceType(lv5_4).setSourceElement(SourceElement.SHOCKWAVE).setSourceHakiNature(SourceHakiNature.HARDENING).setNodeFactories(lv5_5).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("jishin_ho","Jishin Ho",var0));
	}
	private void onUseEvent(LivingEntity var1, IAbility var2) {
		WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.GROUND_PARTICLES.get())),(Entity)(var1),var1.getX(),var1.getY(),var1.getZ(),(ParticleEffect$Details)(PARTICLE_DETAILS));
		List var3 = this.rangeComponent.getTargetsInArea(var1,7F);
		var3.remove(var1);
		var3.removeIf((Predicate)(((Predicate<LivingEntity>)((l0) -> {
			return (!(l0.onGround())) && ((AbilityHelper.getDifferenceToFloor((Entity)(var1))) > (1.5D));
		}))));
		Iterator var4 = var3.iterator();
		do{
			if (var4.hasNext()){
				LivingEntity var5 = ((LivingEntity)(var4.next()));
				if (this.dealDamageComponent.hurtTarget(var1,var5,25F)){
					AbilityHelper.setDeltaMovement((Entity)(var5),0D,0.75D,0D);
					continue;
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
		this.cooldownComponent.startCooldown(var1,300F);
	}
	public void appendDescription(Player var1, AbilityTooltip var2) {
		super.appendDescription(var1,var2);
		Consumer<CooldownComponentTooltip> var3 = (l0) -> {
			l0.cooldown(300F);
		};
		CooldownComponentTooltip.generate(var2,var3);
		Consumer<RangeComponentTooltip> var4 = (l0) -> {
			l0.range(7F,RangeComponent$RangeType.AOE);
		};
		RangeComponentTooltip.generate(var2,var4);
		Consumer<DealDamageComponentTooltip> var5 = (l0) -> {
			l0.damage(25F);
		};
		DealDamageComponentTooltip.generate(var2,var5);
	}
}