package xyz.pixelatedw.mineminenomi.abilities.kachi;

import java.util.Iterator;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.blockgen.BlockGenerators;
import xyz.pixelatedw.mineminenomi.api.blockgen.IBlockGenerator;
import xyz.pixelatedw.mineminenomi.api.blockgen.SimpleBlockPlacer;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule$Builder;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule$IReplaceBlockRule;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModTags$Blocks;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class EvaporateAbility extends Ability {
	// Fields
	private static final int COOLDOWN = 240;
	private static final int HOLD_TIME = 120;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<EvaporateAbility>> INSTANCE;
	private static final BlockProtectionRule GRIEF_RULE;
	private final ContinuousComponent continuousComponent;

	public EvaporateAbility(AbilityCore<EvaporateAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this)).addTickEvent((ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::duringContinuityEvent)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent};
		this.addComponents(var2);
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.triggerContinuity(var1,120F);
	}
	private void duringContinuityEvent(LivingEntity var1, IAbility var2) {
		if (var1.level().isClientSide){
			return;
		}else{
			SimpleBlockPlacer var3 = new SimpleBlockPlacer().setBlock(Blocks.AIR.defaultBlockState()).setSize(6).setRule(GRIEF_RULE);
			Set var4 = var3.getPlacedPositions();
			var3.generate(var1.level(),var1.blockPosition(),BlockGenerators.SPHERE);
			Iterator var5 = var4.iterator();
			do{
				if (var5.hasNext()){
					BlockPos var6 = ((BlockPos)(var5.next()));
					if ((var1.level().getBlockState(var6).getBlock()) == (Blocks.AIR)){
						WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.EVAPORATE.get())),(Entity)(var1),(double)(var6.getX()),(double)(var6.getY()),(double)(var6.getZ()));
						continue;
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
	private void endContinuityEvent(LivingEntity var1, IAbility var2) {
		this.cooldownComponent.startCooldown(var1,240F);
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Evaporates the water and melts ice around the user."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(240F),ContinuousComponent.getTooltip(120F)};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(EvaporateAbility::new)))).addDescriptionLine(lv1_3).addAdvancedDescriptionLine(lv1_4).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("evaporate","Evaporate",var0));
		TagKey[] var1 = new TagKey[]{ModTags$Blocks.BLOCK_PROT_WATER,ModTags$Blocks.BLOCK_PROT_SNOW};
		BlockProtectionRule$IReplaceBlockRule var2 = (l0, l1, l2) -> {
			if (l2.hasProperty((Property)(BlockStateProperties.WATERLOGGED))){
				l0.setBlockAndUpdate(l1,((BlockState)(l2.setValue((Property)(BlockStateProperties.WATERLOGGED),(Comparable)(Boolean.valueOf(false))))));
				return true;
			}else{
				if ((!(l2.is(BlockTags.ICE))) && (((l2.getBlock()) != (Blocks.KELP)) && (((l2.getBlock()) != (Blocks.SEAGRASS)) && ((l2.getBlock()) != (Blocks.TALL_SEAGRASS))))){
					return false;
				}else{
					l0.setBlockAndUpdate(l1,Blocks.WATER.defaultBlockState());
					return true;
				}
			}
		};
		GRIEF_RULE = new BlockProtectionRule$Builder(new BlockProtectionRule[0]).addApprovedTags(var1).addReplaceRules(var2).build();
	}
}