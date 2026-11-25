package xyz.pixelatedw.mineminenomi.abilities.netsu;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent$RangeType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class NekkaiJigokuAbility extends Ability {
	// Fields
	private static final int HOLD_TIME = 600;
	private static final int MIN_COOLDOWN = 20;
	private static final int MAX_COOLDOWN = 300;
	private static final int RANGE = 30;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<NekkaiJigokuAbility>> INSTANCE;
	private final ContinuousComponent continuousComponent;
	private final RangeComponent rangeComponent;

	public NekkaiJigokuAbility(AbilityCore<NekkaiJigokuAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this),true).addTickEvent((ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::duringContinuityEvent)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
		this.rangeComponent = new RangeComponent((IAbility)(this));
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent,this.rangeComponent};
		this.addComponents(var2);
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.triggerContinuity(var1,600F);
	}
	private void duringContinuityEvent(LivingEntity var1, IAbility var2) {
		List var3 = this.rangeComponent.getTargetsInArea(var1,30F);
		List var4 = WyHelper.getNearbyBlocks((Entity)(var1),30);
		Iterator var5 = var3.iterator();
		do{
			if (var5.hasNext()){
				LivingEntity var6 = ((LivingEntity)(var5.next()));
				if (var6.isInWater()){
					var6.hurt(var1.damageSources().onFire(),2F);
					continue;
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
		if (!(var1.level().isClientSide)){
			BlockPos.MutableBlockPos var6_1 = new BlockPos.MutableBlockPos();
			Iterator var7 = var4.iterator();
			do{
				if (var7.hasNext()){
					BlockPos var8 = ((BlockPos)(var7.next()));
					var6_1.set(var8.getX(),(var8.getY()) + (1),var8.getZ());
					if (((var1.level().getBlockState(var8).getBlock()) == (Blocks.WATER)) && (((var1.level().getBlockState((BlockPos)(var6_1)).getBlock()) == (Blocks.AIR)) && (((this.continuousComponent.getContinueTime()) % (5F)) == (0F)))){
						WyHelper.spawnParticles((ParticleOptions)(ParticleTypes.BUBBLE),((ServerLevel)(var1.level())),((double)(var8.getX())) + ((WyHelper.randomDouble()) / (2D)),((double)(var8.getY())) + (0.8D),((double)(var8.getZ())) + ((WyHelper.randomDouble()) / (2D)));
						continue;
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
		}
	}
	private void endContinuityEvent(LivingEntity var1, IAbility var2) {
		this.cooldownComponent.startCooldown(var1,Math.max(20F,(this.continuousComponent.getContinueTime()) / (2F)));
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Boils water around the user, damaging entities inside it."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(20F,300F),ContinuousComponent.getTooltip(600F),RangeComponent.getTooltip(30F,RangeComponent$RangeType.AOE)};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(NekkaiJigokuAbility::new)))).addDescriptionLine(lv1_3).addAdvancedDescriptionLine(lv1_4).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("nekkai_jigoku","Nekkai Jigoku",var0));
	}
}