package xyz.pixelatedw.mineminenomi.abilities.suna;

import java.util.UUID;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
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
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine$IDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusManager;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusOperation;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.entities.TornadoEntity;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class SablesAbility extends Ability {
	// Fields
	private static final float COOLDOWN_BONUS = 0.8F;
	private static final int COOLDOWN = 360;
	private static final int HOLD_TIME = 120;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<SablesAbility>> INSTANCE;
	private final ContinuousComponent continuityComponent;
	private TornadoEntity proj;

	public SablesAbility(AbilityCore<SablesAbility> var1) {
		super((AbilityCore)(var1));
		this.continuityComponent = new ContinuousComponent((IAbility)(this)).addStartEvent((ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::startContinuityEvent)))).addTickEvent((ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::duringContinuityEvent)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
		this.proj = null;
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuityComponent};
		this.addComponents(var2);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::requiresDryUser))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		this.continuityComponent.triggerContinuity(var1,120F);
	}
	private void startContinuityEvent(LivingEntity var1, IAbility var2) {
		this.proj = null;
	}
	private void duringContinuityEvent(LivingEntity var1, IAbility var2) {
		HitResult var3 = WyHelper.rayTraceBlocksAndEntities((Entity)(var1),100D);
		if ((this.proj) == (null)){
			this.proj = new TornadoEntity((Level)(var1.level()),(LivingEntity)(var1));
			this.proj.setMaxLife(300);
			this.proj.setSize(30F);
			this.proj.setSpeed(-2F);
			this.proj.setPos(var3.getLocation().x,var3.getLocation().y,var3.getLocation().z);
			var1.level().addFreshEntity((Entity)(this.proj));
		}
		if ((this.proj.isAlive()) && ((this.proj) != (null))){
			double var4 = Math.sqrt(this.proj.distanceToSqr(var3.getLocation()));
			if ((var3.getType().equals(HitResult.Type.BLOCK)) && ((var4) < (100D))){
				this.proj.setVector(var3.getLocation().add(0D,10D,0D));
			}
			return;
		}else{
			this.continuityComponent.stopContinuity(var1);
			return;
		}
	}
	private void endContinuityEvent(LivingEntity var1, IAbility var2) {
		if (((this.proj) != (null)) && (this.proj.isAlive())){
			this.proj.discard();
		}
		this.cooldownComponent.getBonusManager().removeBonus(SunaHelper.DESERT_COOLDOWN_BONUS);
		if (SunaHelper.isFruitBoosted(var1)){
			this.cooldownComponent.getBonusManager().addBonus(SunaHelper.DESERT_COOLDOWN_BONUS,"Desert Cooldown Bonus",BonusOperation.MUL,0.8F);
		}
		this.cooldownComponent.startCooldown(var1,360F);
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[2];
			((java.lang.Object[]) (lv1_2))[0] = ImmutablePair.of((Object)("The user launches a compressed sandstorm at the opponent, which sends them flying."),(Object)(null));
			Object[] lv1_3 = new Object[1];
			int lv1_4 = Math.round(19.999998F);
			String lv1_5 = "§a" + lv1_4 + "%§r";
			lv1_3[0] = lv1_5;
			((java.lang.Object[]) (lv1_2))[1] = ImmutablePair.of((Object)("While in a desert the cooldown of this ability is reduced by %s."),lv1_3);
			Component[] lv1_6 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_7 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(360F),ContinuousComponent.getTooltip(120F)};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(SablesAbility::new)))).addDescriptionLine(lv1_6).addAdvancedDescriptionLine(lv1_7).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("sables","Sables",var0));
	}
}