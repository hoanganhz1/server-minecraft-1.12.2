package xyz.pixelatedw.mineminenomi.abilities.yuki;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent$RangeType;
import xyz.pixelatedw.mineminenomi.api.blockgen.IBlockGenerator;
import xyz.pixelatedw.mineminenomi.api.blockgen.IBlockPlacer;
import xyz.pixelatedw.mineminenomi.api.blockgen.SimpleBlockPlacer;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule$Builder;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.effects.FrostbiteEffect;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class FubukiAbility extends Ability {
	// Fields
	private static final int COOLDOWN = 400;
	private static final int DAMAGE = 20;
	private static final int RANGE = 25;
	private static final int HOLD_TIME = 100;
	private static final BlockProtectionRule GRIEF_RULE = new BlockProtectionRule$Builder(new BlockProtectionRule[]{DefaultProtectionRules.AIR_FOLIAGE}).build();
	public static final IBlockGenerator FUBUKI;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<FubukiAbility>> INSTANCE;
	private final DealDamageComponent dealDamageComponent;
	private final RangeComponent rangeComponent;
	private final ContinuousComponent continuousComponent;
	private final Interval damageInterval;
	private int multiplier;

	public FubukiAbility(AbilityCore<FubukiAbility> var1) {
		super((AbilityCore)(var1));
		this.dealDamageComponent = new DealDamageComponent((IAbility)(this));
		this.rangeComponent = new RangeComponent((IAbility)(this));
		this.continuousComponent = new ContinuousComponent((IAbility)(this),true).addStartEvent((ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::onContinuousStart)))).addTickEvent((ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::onContinuousTick)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::onContinuousEnd))));
		this.damageInterval = new Interval(20);
		this.multiplier = 1;
		AbilityComponent[] var2 = new AbilityComponent[]{this.dealDamageComponent,this.rangeComponent,this.continuousComponent};
		this.addComponents(var2);
		super.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::onUseEvent))));
	}
	private void onUseEvent(LivingEntity var1, IAbility var2) {
		if (this.continuousComponent.isContinuous()){
			this.continuousComponent.stopContinuity(var1);
		}else{
			this.continuousComponent.startContinuity(var1,100F);
		}
	}
	public void onContinuousStart(LivingEntity var1, IAbility var2) {
		this.damageInterval.restartIntervalToZero();
	}
	public void onContinuousTick(LivingEntity var1, IAbility var2) {
		if (var1.level().isClientSide){
			return;
		}else{
			if (this.damageInterval.canTick()){
				int var3 = (5) * (this.multiplier);
				float var4 = (4F) * ((float)(this.multiplier));
				new SimpleBlockPlacer().setBlock(Blocks.SNOW.defaultBlockState()).setSize(var3).setRule(GRIEF_RULE).generate(var1.level(),var1.blockPosition(),FUBUKI);
				Iterator var5 = this.rangeComponent.getTargetsInArea(var1,(float)(var3)).iterator();
				do{
					if (var5.hasNext()){
						LivingEntity var6 = ((LivingEntity)(var5.next()));
						FrostbiteEffect.addFrostbiteStacks(var6,2);
						if ((!(var6.hasEffect((Holder)(ModEffects.FROSTBITE)))) && (!(var6.hasEffect((Holder)(ModEffects.FROZEN))))){
							continue;
						}else{
							this.dealDamageComponent.hurtTarget(var1,var6,var4);
							continue;
						}
					}else{
						break;
					}
				} while (true);
				WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.FUBUKI.get())),(Entity)(var1),var1.getX(),var1.getY(),var1.getZ());
				this.multiplier = (this.multiplier) + (1);
			}
			return;
		}
	}
	public void onContinuousEnd(LivingEntity var1, IAbility var2) {
		this.multiplier = 1;
		this.cooldownComponent.startCooldown(var1,400F);
	}
	static  {
		IBlockGenerator var0 = (l0, l1, l2) -> {
			int lv1_3 = l2.getX();
			int lv1_4 = l2.getY();
			int lv1_5 = l2.getZ();
			int lv1_6 = l0.getSizeX();
			int lv1_7 = l0.getSizeY();
			int lv1_8 = ((lv1_6) - (l0.getThickness())) * ((lv1_6) - (l0.getThickness()));
			BlockPos.MutableBlockPos lv1_9 = new BlockPos.MutableBlockPos();
			double lv1_10 = (double)(lv1_7);
			LOOP_2:
			do{
				if ((lv1_10) >= ((double)(-(lv1_7)))){
					double lv1_11 = (double)(-(lv1_6));
					LOOP_1:
					do{
						if ((lv1_11) <= ((double)(lv1_6))){
							double lv1_12 = (double)(-(lv1_6));
							do{
								if ((lv1_12) <= ((double)(lv1_6))){
									double lv1_13 = (((lv1_11) * (lv1_11)) + ((lv1_12) * (lv1_12))) + ((lv1_10) * (lv1_10));
									if (((l0.isHollow()) && ((lv1_13) < ((double)(lv1_8)))) || ((lv1_13) >= ((double)((lv1_6) * (lv1_7))))){

									}else{
										lv1_9.set(((double)(lv1_3)) + (lv1_11),((double)(lv1_4)) + (lv1_10),((double)(lv1_5)) + (lv1_12));
										if ((Block.canSupportCenter((LevelReader)(l1),lv1_9.below(),Direction.UP)) && ((l1.getBlockState(lv1_9.below()).getBlock()) != (Blocks.SNOW))){
											l0.placeNext(l1,lv1_9.immutable(),l0.getState(),l0.getFlag(),l0.getRule());
										}
									}
									lv1_12 = (lv1_12) + (1D);
									continue;
								}else{
									break;
								}
							} while (true);
							lv1_11 = (lv1_11) + (1D);
							continue LOOP_1;
						}else{
							break;
						}
					} while (true);
					lv1_10 = (lv1_10) - (1D);
					continue LOOP_2;
				}else{
					break;
				}
			} while (true);
		};
		FUBUKI = var0;
		AbilityRegistryEntry var1 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv2_2 = new Pair[1];
			Object[] lv2_3 = new Object[]{ModEffects.FROSTBITE};
			((java.lang.Object[]) (lv2_2))[0] = ImmutablePair.of((Object)("The user releases an extremely cold stream around themselves inflicting %s and causing serious damage to those affected by it."),lv2_3);
			Component[] lv2_4 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv2_2);
			AbilityDescriptionLine$IDescriptionLine[] lv2_5 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(400F),DealDamageComponent.getTooltip(20F),RangeComponent.getTooltip(25F,RangeComponent$RangeType.AOE)};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(FubukiAbility::new)))).addDescriptionLine(lv2_4).addAdvancedDescriptionLine(lv2_5).setSourceHakiNature(SourceHakiNature.SPECIAL).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("fubuki","Fubuki",var1));
	}
}