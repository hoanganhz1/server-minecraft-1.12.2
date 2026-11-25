package xyz.pixelatedw.mineminenomi.abilities.ori;

import java.util.Iterator;
import java.util.Set;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.BlockTrackerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.blockgen.BlockGenerators;
import xyz.pixelatedw.mineminenomi.api.blockgen.BlockQueue;
import xyz.pixelatedw.mineminenomi.api.blockgen.IBlockGenerator;
import xyz.pixelatedw.mineminenomi.api.blockgen.SimpleBlockPlacer;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule$Builder;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class GreatCageAbility extends Ability {
	// Fields
	private static final int HOLD_TIME = 1200;
	private static final int MIN_COOLDOWN = 40;
	private static final int MAX_COOLDOWN = 340;
	private static final BlockProtectionRule GRIEF_RULE = new BlockProtectionRule$Builder(new BlockProtectionRule[]{DefaultProtectionRules.AIR_FOLIAGE_LIQUID}).setBypassGriefFlag().build();
	public static final Supplier<AbilityCore<GreatCageAbility>> INSTANCE;
	private final ContinuousComponent continuousComponent;
	private final BlockTrackerComponent blockTrackerComponent;
	private SimpleBlockPlacer placer;

	public GreatCageAbility(AbilityCore<GreatCageAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this),true).addStartEvent((ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::startContinuityEvent)))).addTickEvent((ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::duringContinuityEvent)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
		this.blockTrackerComponent = new BlockTrackerComponent((IAbility)(this));
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent,this.blockTrackerComponent};
		this.addComponents(var2);
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.triggerContinuity(var1,1200F);
	}
	private void startContinuityEvent(LivingEntity var1, IAbility var2) {
		this.blockTrackerComponent.initQueue(var1.level(),200);
		this.placer = new SimpleBlockPlacer().setBlock(((Block)(ModBlocks.ORI_BARS.get())).defaultBlockState()).setSize(20).setRule(GRIEF_RULE).setHollow().setFlag(259).setBlockQueue(this.blockTrackerComponent.getQueue());
		this.placer.generate(var1.level(),var1.blockPosition(),BlockGenerators.CUBE);
		this.blockTrackerComponent.getQueue().start();
	}
	private void duringContinuityEvent(LivingEntity var1, IAbility var2) {
		this.blockTrackerComponent.tickBlockQueue(var1.level());
	}
	private void endContinuityEvent(LivingEntity var1, IAbility var2) {
		if ((this.placer) != (null)){
			Iterator var3 = this.placer.getPlacedPositions().iterator();
			do{
				if (var3.hasNext()){
					BlockPos var4 = ((BlockPos)(var3.next()));
					if (((java.lang.Object)(var1.level().getBlockState(var4).getBlock())).equals(ModBlocks.ORI_BARS.get())){
						var1.level().setBlockAndUpdate(var4,Blocks.AIR.defaultBlockState());
						continue;
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
		}
		float var3_1 = (40F) + ((this.continuousComponent.getContinueTime()) / (4F));
		this.cooldownComponent.startCooldown(var1,var3_1);
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Creates a big cage trapping the user and all nearby entities in it."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(40F,340F),ContinuousComponent.getTooltip(1200F)};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(GreatCageAbility::new)))).addDescriptionLine(lv1_3).addAdvancedDescriptionLine(lv1_4).build("mineminenomi");
		}));
		INSTANCE = ModRegistry.registerAbility("great_cage","Great Cage",var0);
	}
}