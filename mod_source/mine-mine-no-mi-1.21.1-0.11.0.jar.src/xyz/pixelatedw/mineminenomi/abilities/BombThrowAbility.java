package xyz.pixelatedw.mineminenomi.abilities;

import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.BombEntity;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class BombThrowAbility extends Ability {
	// Fields
	private static final float COOLDOWN = 240F;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<BombThrowAbility>> INSTANCE;

	public BombThrowAbility(AbilityCore<BombThrowAbility> var1) {
		super((AbilityCore)(var1));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		BombEntity var3 = new BombEntity((Level)(var1.level()),(LivingEntity)(var1));
		var3.setExplodeOnImpact();
		Vec3 var4 = var1.getLookAngle().multiply(2D,2D,2D);
		var3.moveTo((var1.position().x()) + (var4.x()),var1.getEyeY(),(var1.position().z()) + (var4.z()),(float)(var1.getRandom().nextInt(360)),(float)(var1.getRandom().nextInt(360)));
		AbilityHelper.setDeltaMovement(var3,var4);
		var1.level().addFreshEntity((Entity)(var3));
		this.cooldownComponent.startCooldown(var1,240F);
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Throws a bomb."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(240F)};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.STYLE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(BombThrowAbility::new)))).addDescriptionLine(lv1_3).addAdvancedDescriptionLine(lv1_4).setSourceElement(SourceElement.EXPLOSION).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("bomb_throw","Bomb Throw",var0));
	}
}