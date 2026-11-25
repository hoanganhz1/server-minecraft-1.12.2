package xyz.pixelatedw.mineminenomi.abilities.ito;

import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
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
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine$IDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.mobs.abilities.BlackKnightEntity;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class BlackKnightAbility extends Ability {
	// Fields
	private static final int HOLD_TIME = 20000;
	private static final int MIN_COOLDOWN = 40;
	private static final int MAX_COOLDOWN = 6000;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<BlackKnightAbility>> INSTANCE;
	private final ContinuousComponent continuousComponent;
	private BlackKnightEntity blackKnight;

	public BlackKnightAbility(AbilityCore<BlackKnightAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this),true).addStartEvent(100,(ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::startContinuityEvent)))).addTickEvent(100,(ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::onTickEvent)))).addEndEvent(100,(ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::stopContinuityEvent))));
		this.blackKnight = null;
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent};
		this.addComponents(var2);
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::onUseEvent))));
	}
	private void onUseEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.triggerContinuity(var1);
	}
	private void startContinuityEvent(LivingEntity var1, IAbility var2) {
		this.blackKnight = new BlackKnightEntity((Level)(var1.level()),(LivingEntity)(var1));
		this.blackKnight.moveTo(var1.getX(),var1.getY(),var1.getZ(),var1.getYRot(),var1.getXRot());
		EquipmentSlot[] var3 = EquipmentSlot.values();
		int var4 = var3.length;
		int var5 = 0;
		do{
			if ((var5) < (var4)){
				EquipmentSlot var6 = var3[var5];
				ItemStack var7 = var1.getItemBySlot(var6);
				this.blackKnight.setItemSlot(var6,var7);
				var5++;
				continue;
			}else{
				break;
			}
		} while (true);
		var1.level().addFreshEntity((Entity)(this.blackKnight));
	}
	private void onTickEvent(LivingEntity var1, IAbility var2) {
		if (((this.blackKnight) != (null)) && (this.blackKnight.isAlive())){
			return;
		}else{
			this.continuousComponent.stopContinuity(var1);
			return;
		}
	}
	private void stopContinuityEvent(LivingEntity var1, IAbility var2) {
		if ((this.blackKnight) != (null)){
			this.blackKnight.discard();
		}
		float var3 = Mth.clamp(this.continuousComponent.getContinueTime(),40F,6000F);
		this.cooldownComponent.startCooldown(var1,var3);
	}
	@Nullable()
	public BlackKnightEntity getBlackKnight() {
		return this.blackKnight;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Creates a clone of the user made entirely out of compressed strings"),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(40F,6000F),ContinuousComponent.getTooltip()};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(BlackKnightAbility::new)))).addDescriptionLine(lv1_3).addAdvancedDescriptionLine(lv1_4).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("black_knight","Black Knight",var0));
	}
}