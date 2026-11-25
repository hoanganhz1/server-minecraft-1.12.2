package xyz.pixelatedw.mineminenomi.abilities.sniper;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
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
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$INodeFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.ContinuousComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.CooldownComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode$NodePos;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.SubtractPointsAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.RequiredStyleUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.TrainingPointsUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

public class HissatsuAbility extends Ability {
	// Fields
	private static final float COOLDOWN = 300F;
	private static final int NODE_POINTS_AC = 10;
	public static final Supplier<AbilityCore<HissatsuAbility>> INSTANCE;
	private final ContinuousComponent continuousComponent;

	public HissatsuAbility(AbilityCore<HissatsuAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this),true).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::onEndContinuity))));
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent};
		this.addComponents(var2);
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::onUseEvent))));
	}
	public void onUseEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.triggerContinuity(var1);
	}
	private void onEndContinuity(LivingEntity var1, IAbility var2) {
		this.cooldownComponent.startCooldown(var1,300F);
	}
	private static AbilityNode createNode(LivingEntity var0) {
		AbilityNode var1 = new AbilityNode(((AbilityCore)(INSTANCE.get())).getLocalizedName(),((AbilityCore)(INSTANCE.get())).getIcon(),new AbilityNode$NodePos(-9F,-8F));
		NodeUnlockCondition var2 = RequiredStyleUnlockCondition.requires(((FightingStyle)(ModFightingStyles.SNIPER.get()))).and(TrainingPointsUnlockCondition.accuracy(10F));
		NodeUnlockAction var3 = SubtractPointsAction.accuracy(10F).andThen(UnlockAbilityAction.unlock(INSTANCE));
		AbilityNode[] var4 = new AbilityNode[3];
		var4[0] = ((AbilityCore)(HiNoToriBoshiAbility.INSTANCE.get())).getNode(var0);
		var4[1] = ((AbilityCore)(RenpatsuNamariBoshiAbility.INSTANCE.get())).getNode(var0);
		var4[2] = ((AbilityCore)(KemuriBoshiAbility.INSTANCE.get())).getNode(var0);
		var1.addPrerequisites(var4);
		var1.setUnlockRule(var2,var3);
		return var1;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv5_2 = new Pair[]{ImmutablePair.of((Object)("Allows the next sniper ability to instantly hit the target"),(Object)(null))};
			Component[] lv5_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv5_2);
			AbilityCore$INodeFactory[] lv5_4 = new AbilityCore$INodeFactory[1];
			AbilityCore$INodeFactory lv5_5 = HissatsuAbility::createNode;
			lv5_4[0] = lv5_5;
			return new AbilityCore$Builder(l0,l1,AbilityCategory.STYLE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(HissatsuAbility::new)))).addDescriptionLine(lv5_3).setNodeFactories(lv5_4).build("mineminenomi");
		}));
		INSTANCE = ModRegistry.registerAbility("hissatsu","Hissatsu",var0);
	}
	public static boolean checkHitScan(LivingEntity var0) {
		Function<AbilityData, HissatsuAbility> var1 = (l0) -> {
			return ((HissatsuAbility)(l0.getEquippedAbility(((AbilityCore)(INSTANCE.get())))));
		};
		Optional var2 = AbilityData.get(var0).map(var1);
		if ((var2.isPresent()) && (((HissatsuAbility)(var2.get())).isContinuous())){
			HissatsuAbility var4 = ((HissatsuAbility)(var2.get()));
			LivingEntity var5 = var0;
			final LivingEntity var5_f1 = var5;
			Consumer<ContinuousComponent> var6 = (l0) -> {
			l0.stopContinuity(var5_f1);
		};
			((java.util.Optional)(var4.getComponent(((AbilityComponentKey)(ModAbilityComponents.CONTINUOUS.get()))))).ifPresent((Consumer)(var6));
			var0.level().playSound((Player)(null),var0.blockPosition(),((SoundEvent)(ModSounds.DASH_ABILITY_SWOOSH_SFX.get())),SoundSource.PLAYERS,2F,3F);
			return true;
		}else{
			return false;
		}
	}
	public void appendDescription(Player var1, AbilityTooltip var2) {
		super.appendDescription(var1,var2);
		Consumer<CooldownComponentTooltip> var3 = (l0) -> {
			l0.cooldown(300F);
		};
		CooldownComponentTooltip.generate(var2,var3);
		Consumer<ContinuousComponentTooltip> var4 = (l0) -> {
		};
		ContinuousComponentTooltip.generate(var2,var4);
	}
}