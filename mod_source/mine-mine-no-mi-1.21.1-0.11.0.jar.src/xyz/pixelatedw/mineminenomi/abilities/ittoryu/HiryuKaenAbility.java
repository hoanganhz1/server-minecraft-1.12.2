package xyz.pixelatedw.mineminenomi.abilities.ittoryu;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.abilities.santoryu.OTatsumakiAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$INodeFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.DropHitAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
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
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class HiryuKaenAbility extends DropHitAbility {
	// Fields
	private static final int COOLDOWN = 240;
	private static final float DAMAGE = 20F;
	private static final float HOLD_TIME = 15F;
	private static final int NODE_POINTS_WM = 8;
	private static final int NODE_POINTS_AC = 2;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<HiryuKaenAbility>> INSTANCE;
	private final DealDamageComponent dealDamageComponent;
	private final RangeComponent rangeComponent;

	public HiryuKaenAbility(AbilityCore<HiryuKaenAbility> var1) {
		super((AbilityCore)(var1));
		this.dealDamageComponent = new DealDamageComponent((IAbility)(this));
		this.rangeComponent = new RangeComponent((IAbility)(this));
		this.setCancelable(false);
		AbilityComponent[] var2 = new AbilityComponent[]{this.dealDamageComponent,this.rangeComponent};
		this.addComponents(var2);
		this.continuousComponent.addStartEvent((ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::onContinuityStart))));
		this.continuousComponent.addTickEvent((ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::onContinuityTick))));
		this.continuousComponent.addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::onContinuityEnd))));
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::requiresSword))));
	}
	public void onLanding(LivingEntity var1) {
	}
	private void onContinuityStart(LivingEntity var1, IAbility var2) {
		this.hitTrackerComponent.clearHits();
		Vec3 var3 = var1.getLookAngle().multiply(4D,1D,4D);
		AbilityHelper.setDeltaMovement((Entity)(var1),var3.x,2.25D,var3.z);
	}
	private void onContinuityTick(LivingEntity var1, IAbility var2) {
		if ((this.continuousComponent.getContinueTime()) > (15F)){
			return;
		}else{
			List var3 = this.rangeComponent.getTargetsInArea(var1,(float)(var1.getAttributeValue(Attributes.ENTITY_INTERACTION_RANGE)));
			var3.remove(var1);
			Iterator var4 = var3.iterator();
			do{
				if (var4.hasNext()){
					LivingEntity var5 = ((LivingEntity)(var4.next()));
					if ((this.hitTrackerComponent.canHit((Entity)(var5))) && ((var1.hasLineOfSight((Entity)(var5))) && (this.dealDamageComponent.hurtTarget(var1,var5,20F)))){
						var5.igniteForSeconds(4F);
						if (!(var1.level().isClientSide)){
							((ServerLevel)(var1.level())).getChunkSource().broadcastAndSend((Entity)(var1),(Packet)(new ClientboundAnimatePacket((Entity)(var1),0)));
							WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.HIRYU_KAEN.get())),(Entity)(var5),var5.getX(),var5.getY(),var5.getZ());
							continue;
						}else{
							continue;
						}
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
			return;
		}
	}
	private void onContinuityEnd(LivingEntity var1, IAbility var2) {
		this.cooldownComponent.startCooldown(var1,240F);
	}
	private static AbilityNode createNode(LivingEntity var0) {
		AbilityNode var1 = new AbilityNode(((AbilityCore)(INSTANCE.get())).getLocalizedName(),((AbilityCore)(INSTANCE.get())).getIcon(),new AbilityNode$NodePos(-5F,-5F));
		NodeUnlockCondition var2 = TrainingPointsUnlockCondition.accuracy(2F).and(TrainingPointsUnlockCondition.weaponMastery(8F));
		NodeUnlockAction var3 = SubtractPointsAction.accuracy(2F).andThen(SubtractPointsAction.weaponMastery(8F).andThen(UnlockAbilityAction.unlock((Supplier)(INSTANCE))));
		AbilityNode[] var4 = new AbilityNode[1];
		var4[0] = ((AbilityCore)(OTatsumakiAbility.INSTANCE.get())).getNode(var0);
		var1.addPrerequisites(var4);
		var1.setUnlockRule(var2,var3);
		return var1;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv4_2 = new Pair[]{ImmutablePair.of((Object)("The user jumps high up into the air and slashes their opponent. After slashing them, the user's opponent then bursts into flames from where they were slashed."),(Object)(null))};
			Component[] lv4_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv4_2);
			SourceType[] lv4_4 = new SourceType[]{SourceType.SLASH};
			AbilityCore$INodeFactory[] lv4_5 = new AbilityCore$INodeFactory[1];
			AbilityCore$INodeFactory lv4_6 = HiryuKaenAbility::createNode;
			lv4_5[0] = lv4_6;
			return new AbilityCore$Builder(l0,l1,AbilityCategory.STYLE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(HiryuKaenAbility::new)))).addDescriptionLine(lv4_3).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceType(lv4_4).setSourceElement(SourceElement.FIRE).setNodeFactories(lv4_5).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("hiryu_kaen","Hiryu: Kaen",var0));
	}
	public void appendDescription(Player var1, AbilityTooltip var2) {
		super.appendDescription(var1,var2);
		Consumer<CooldownComponentTooltip> var3 = (l0) -> {
			l0.cooldown(240F);
		};
		CooldownComponentTooltip.generate(var2,var3);
		Consumer<DealDamageComponentTooltip> var4 = (l0) -> {
			l0.damage(20F);
		};
		DealDamageComponentTooltip.generate(var2,var4);
		Player var5 = var1;
		final Player var5_f1 = var5;
		Consumer<RangeComponentTooltip> var6 = (l0) -> {
			l0.range((float)(var5_f1.getAttributeValue(Attributes.ENTITY_INTERACTION_RANGE)),RangeComponent$RangeType.AOE);
		};
		RangeComponentTooltip.generate(var2,var6);
	}
}