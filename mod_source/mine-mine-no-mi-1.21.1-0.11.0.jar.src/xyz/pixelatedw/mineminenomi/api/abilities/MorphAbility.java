package xyz.pixelatedw.mineminenomi.api.abilities;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.entity.PartEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.MorphComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PoolComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.ChangeStatsComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.ContinuousComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.CooldownComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.DevilFruitData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModAbilityPools;

public abstract class MorphAbility extends Ability {
	// Fields
	protected final ContinuousComponent continuousComponent;
	protected final ChangeStatsComponent statsComponent;
	protected final MorphComponent morphComponent;
	protected final PoolComponent poolComponent;

	public MorphAbility(AbilityCore<? extends MorphAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this),true).addStartEvent(110,(ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::startContinuityEvent)))).addTickEvent(110,(ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::tickContinuityEvent)))).addEndEvent(110,(ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::stopContinuityEvent))));
		this.statsComponent = new ChangeStatsComponent((IAbility)(this));
		this.morphComponent = new MorphComponent((IAbility)(this));
		this.poolComponent = new PoolComponent((IAbility)(this),ModAbilityPools.MORPHS,new AbilityPool[0]);
		this.setOGCD();
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent,this.statsComponent,this.morphComponent,this.poolComponent};
		this.addComponents(var2);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::requiresTransformationSpace))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::onUseEvent))));
	}
	private void onUseEvent(LivingEntity var1, IAbility var2) {
		Optional var3 = ((Optional)(DevilFruitData.get(var1).map((Function)(((Function<DevilFruitData, Optional>)(DevilFruitData::getCurrentMorph)))).get()));
		if ((var3.isPresent()) && (!(((java.lang.Object)(((MorphInfo)(var3.get())))).equals((Object)(this.getTransformation()))))){
			AbilityData var4 = ((AbilityData)(((java.util.Optional)(AbilityData.get(var1))).orElse((Object)(null))));
			if ((var4) != (null)){
				AbilityComponentKey[] var5 = new AbilityComponentKey[]{((AbilityComponentKey)(ModAbilityComponents.MORPH.get())),((AbilityComponentKey)(ModAbilityComponents.CONTINUOUS.get()))};
				Iterator var6 = var4.getEquippedAbilitiesWith(var5).iterator();
				do{
					if (var6.hasNext()){
						IAbility var7 = ((IAbility)(var6.next()));
						if ((var7) == (this)){
							continue;
						}else{
							((ContinuousComponent)(var7.getComponent(((AbilityComponentKey)(ModAbilityComponents.CONTINUOUS.get()))).get())).stopContinuity(var1);
							continue;
						}
					}else{
						break;
					}
				} while (true);
			}
		}
		this.continuousComponent.triggerContinuity(var1,this.getContinuityHoldTime());
	}
	private void startContinuityEvent(LivingEntity var1, IAbility var2) {
		float var3 = (var1.getHealth()) / (var1.getMaxHealth());
		var1.setPose(Pose.STANDING);
		this.morphComponent.startMorph(var1,this.getTransformation());
		this.statsComponent.applyModifiers(var1);
		var1.setHealth((var1.getMaxHealth()) * (var3));
	}
	private void tickContinuityEvent(LivingEntity var1, IAbility var2) {
		if (!(this.morphComponent.isMorphed())){
			this.continuousComponent.stopContinuity(var1);
		}
		Iterator var3 = this.morphComponent.getParts().entrySet().iterator();
		do{
			if (var3.hasNext()){
				Map.Entry var4 = ((Map.Entry)(var3.next()));
				String var5 = ((String)(var4.getKey()));
				PartEntity var6 = ((PartEntity)(var4.getValue()));
				Vec3 var7 = var6.position();
				this.handlePart(var5,var6,(IAbility)(this));
				var6.xo = var7.x;
				var6.yo = var7.y;
				var6.zo = var7.z;
				var6.xOld = var7.x;
				var6.yOld = var7.y;
				var6.zOld = var7.z;
				continue;
			}else{
				break;
			}
		} while (true);
	}
	private void stopContinuityEvent(LivingEntity var1, IAbility var2) {
		float var3 = (var1.getHealth()) / (var1.getMaxHealth());
		this.morphComponent.stopMorph(var1);
		this.statsComponent.removeModifiers(var1);
		this.cooldownComponent.startCooldown(var1,this.getCooldownTicks());
		var1.setHealth((var1.getMaxHealth()) * (var3));
	}
	public abstract MorphInfo getTransformation();
	public float getContinuityHoldTime() {
		return -1F;
	}
	public float getCooldownTicks() {
		return 10F;
	}
	public void handlePart(String var1, PartEntity<?> var2, IAbility var3) {
	}
	public void appendDescription(Player var1, AbilityTooltip var2) {
		super.appendDescription(var1,var2);
		MorphAbility var3 = this;
		Consumer<CooldownComponentTooltip> var4 = (l0) -> {
			l0.cooldown(this.getCooldownTicks());
		};
		CooldownComponentTooltip.generate(var2,var4);
		MorphAbility var5 = this;
		Consumer<ContinuousComponentTooltip> var6 = (l0) -> {
			l0.holdTime(this.getContinuityHoldTime());
		};
		ContinuousComponentTooltip.generate(var2,var6);
		Consumer<ChangeStatsComponentTooltip> var7 = (l0) -> {
		};
		ChangeStatsComponentTooltip.generate(var2,var7);
	}
}