package xyz.pixelatedw.mineminenomi.abilities.rokushiki;

import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.StackComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.StackComponent$IStacksChangeEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.StackComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode$NodePos;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.SubtractPointsAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.DorikiUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.RequiredRaceUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.TrainingPointsUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityTooltipsHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

public class SoruAbility extends Ability {
	// Fields
	private static final float LONG_COOLDOWN = 200F;
	private static final float SHORT_COOLDOWN = 10F;
	private static final int STACKS = 5;
	private static final int DORIKI = 500;
	private static final int MARTIAL_ARTS_POINTS = 2;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<SoruAbility>> INSTANCE;
	private final StackComponent stackComponent;

	public SoruAbility(AbilityCore<SoruAbility> var1) {
		super((AbilityCore)(var1));
		this.stackComponent = new StackComponent((IAbility)(this),5).addStackChangeEvent((StackComponent$IStacksChangeEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.StackComponent$IStacksChangeEvent)(this::onStacksChange))));
		AbilityComponent[] var2 = new AbilityComponent[]{this.stackComponent};
		this.addComponents(var2);
		this.setOGCD();
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::canUseMomentumAbilities))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::onUseEvent))));
	}
	private void onUseEvent(LivingEntity var1, IAbility var2) {
		Vec3 var3 = var1.getLookAngle();
		double var4 = (double)(var1.zza);
		double var5 = (double)(var1.xxa);
		if (((var4) == (0D)) && ((var5) == (0D))){
			var4 = 1D;
		}
		double var6 = Math.sqrt(((var4) * (var4)) + ((var5) * (var5)));
		if ((var6) > (1D)){
			var4 = (var4) / (var6);
			var5 = (var5) / (var6);
		}
		Vec3 var7 = var3.multiply(1.75D,((var3.y) >= (0D)) ? (0D) : (0.6D),1.75D);
		Vec3 var8 = new Vec3((var3.z) * (1.75D),0D,(-(var3.x)) * (1.75D));
		Vec3 var9 = new Vec3(((var7.x) * (var4)) + ((var8.x) * (var5)),(var7.y) * (Math.abs(var4)),((var7.z) * (var4)) + ((var8.z) * (var5)));
		if (var1.isInWater()){
			var9 = var9.scale(0.2D);
		}
		AbilityHelper.setDeltaMovement((Entity)(var1),var9);
		var1.addEffect(new MobEffectInstance((Holder)(ModEffects.VANISH),5,0,false,false));
		var1.level().playSound((Player)(null),var1.blockPosition(),((SoundEvent)(ModSounds.TELEPORT_SFX.get())),SoundSource.PLAYERS,2F,1F);
		this.stackComponent.addStacks(var1,this,-1);
	}
	private void onStacksChange(LivingEntity var1, IAbility var2, int var3) {
		if ((var3) <= (0)){
			this.cooldownComponent.startCooldown(var1,200F);
			this.stackComponent.revertStacksToDefault(var1,this);
		}
		this.cooldownComponent.startCooldown(var1,10F);
	}
	private static AbilityNode createNode(LivingEntity var0) {
		AbilityNode var1 = new AbilityNode(((AbilityCore)(INSTANCE.get())).getLocalizedName(),((AbilityCore)(INSTANCE.get())).getIcon(),new AbilityNode$NodePos(9F,0F));
		DorikiUnlockCondition var2 = DorikiUnlockCondition.atLeast(500D);
		RequiredRaceUnlockCondition var3 = RequiredRaceUnlockCondition.requires(((Race)(ModRaces.HUMAN.get())));
		NodeUnlockCondition var4 = ((xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition)(var3)).and(((xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition)(var2)).and((NodeUnlockCondition)(TrainingPointsUnlockCondition.martialArts(2F)))).or(((xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition)(var2)).and((NodeUnlockCondition)(TrainingPointsUnlockCondition.martialArts(3F))));
		var1.setUnlockRule(var4,SubtractPointsAction.martialArts(2F).onlyIf(var3).andThen(SubtractPointsAction.martialArts(3F).onlyIf(((xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition)(var3)).not())).andThen(UnlockAbilityAction.unlock((Supplier)(INSTANCE))));
		return var1;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv2_2 = new Pair[]{ImmutablePair.of((Object)("Allows the user to move at an extremely high speed in bursts."),(Object)(null))};
			Component[] lv2_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv2_2);
			AbilityCore$INodeFactory[] lv2_4 = new AbilityCore$INodeFactory[1];
			AbilityCore$INodeFactory lv2_5 = SoruAbility::createNode;
			lv2_4[0] = lv2_5;
			return new AbilityCore$Builder(l0,l1,AbilityCategory.RACIAL,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(SoruAbility::new)))).addDescriptionLine(lv2_3).setNodeFactories(lv2_4).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("soru","Soru",var0));
	}
	public void appendDescription(Player var1, AbilityTooltip var2) {
		super.appendDescription(var1,var2);
		Consumer<StackComponentTooltip> var3 = (l0) -> {
			l0.stacks(5F);
		};
		StackComponentTooltip.generate(var2,var3);
		AbilityTooltipsHelper.generateShortLongCooldownStat(var2,this,10F,200F);
	}
}