package xyz.pixelatedw.mineminenomi.abilities.mini;

import java.util.Optional;
import java.util.function.Function;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility$IDuringPassiveEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class PaperFloatAbility extends PassiveAbility {
	// Fields
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<PaperFloatAbility>> INSTANCE;
	private final AnimationComponent animationComponent;

	public PaperFloatAbility(AbilityCore<PaperFloatAbility> var1) {
		super((AbilityCore)(var1));
		this.animationComponent = new AnimationComponent((IAbility)(this));
		AbilityComponent[] var2 = new AbilityComponent[]{this.animationComponent};
		this.addComponents(var2);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::canUseMomentumAbilities))));
		this.addDuringPassiveEvent((PassiveAbility$IDuringPassiveEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility$IDuringPassiveEvent)(this::duringPassiveEvent))));
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv3_2 = new Pair[]{ImmutablePair.of((Object)("While in the mini form and holding a piece of paper the user is able to float."),(Object)(null))};
			Component[] lv3_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv3_2);
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,AbilityType.PASSIVE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(PaperFloatAbility::new)))).addDescriptionLine(lv3_3).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("paper_float","Paper Float",var0));
	}
	private void duringPassiveEvent(LivingEntity var1) {
		Function<AbilityData, MiniMiniAbility> var2 = (l0) -> {
			return ((MiniMiniAbility)(l0.getEquippedAbility(((AbilityCore)(MiniMiniAbility.INSTANCE.get())))));
		};
		Function<MiniMiniAbility, Boolean> var3 = (l0) -> {
			return Boolean.valueOf(l0.isContinuous());
		};
		boolean var4 = ((Boolean)(((java.util.Optional)(((java.util.Optional)(AbilityData.get(var1))).map((Function)(var2)))).map((Function)(var3)).orElse(Boolean.valueOf(false)))).booleanValue();
		int var5 = (((var1.getMainHandItem().getItem()) == (Items.PAPER)) || ((var1.getOffhandItem().getItem()) == (Items.PAPER))) ? (1) : (0);
		int var6 = ((!(var1.onGround())) && ((var1.getDeltaMovement().y) < (0D))) ? (1) : (0);
		if ((var4) && (((var5) != (0)) && ((var6) != (0)))){
			var1.fallDistance = 0F;
			this.animationComponent.start(var1,ModAnimations.RAISE_ARMS,20);
			AbilityHelper.setDeltaMovement((Entity)(var1),var1.getDeltaMovement().x,(var1.getDeltaMovement().y) / (2D),var1.getDeltaMovement().z);
		}
	}
}