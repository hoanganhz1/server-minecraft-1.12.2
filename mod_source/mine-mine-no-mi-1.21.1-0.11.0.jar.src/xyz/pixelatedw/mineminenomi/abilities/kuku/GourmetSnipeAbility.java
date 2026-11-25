package xyz.pixelatedw.mineminenomi.abilities.kuku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTrackerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent$RangeType;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule$Builder;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.BreakingBlocksParticleEffect$Details;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$Details;

public class GourmetSnipeAbility extends Ability {
	// Fields
	private static final BlockProtectionRule GRIEF_RULE = new BlockProtectionRule$Builder(new BlockProtectionRule[]{DefaultProtectionRules.CORE_FOLIAGE}).build();
	private static final ArrayList<Item> FOODS;
	private static final int HOLD_TIME = 10;
	private static final int COOLDOWN = 400;
	private static final float RANGE = 2F;
	private static final float DAMAGE = 20F;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<GourmetSnipeAbility>> INSTANCE;
	private final ContinuousComponent continuousComponent;
	private final RangeComponent rangeComponent;
	private final DealDamageComponent dealDamageComponent;
	private final AnimationComponent animationComponent;
	private final HitTrackerComponent hitTrackerComponent;
	private BreakingBlocksParticleEffect$Details details;
	private int initialY;

	public GourmetSnipeAbility(AbilityCore<GourmetSnipeAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this),true).addStartEvent((ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::startContinuityEvent)))).addTickEvent((ContinuousComponent$IDuringContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IDuringContinuousEvent)(this::duringContinuityEvent)))).addEndEvent((ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
		this.rangeComponent = new RangeComponent((IAbility)(this));
		this.dealDamageComponent = new DealDamageComponent((IAbility)(this));
		this.animationComponent = new AnimationComponent((IAbility)(this));
		this.hitTrackerComponent = new HitTrackerComponent((IAbility)(this));
		this.details = new BreakingBlocksParticleEffect$Details(20);
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent,this.rangeComponent,this.dealDamageComponent,this.animationComponent,this.hitTrackerComponent};
		this.addComponents(var2);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::canUseMomentumAbilities))));
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::requiresMeleeWeapon))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.startContinuity(var1,10F);
	}
	private void startContinuityEvent(LivingEntity var1, IAbility var2) {
		this.initialY = (int)(var1.getY());
		Vec3 var3 = var1.getLookAngle().multiply(6D,6D,6D);
		AbilityHelper.setDeltaMovement((Entity)(var1),var3.x,var3.y,var3.z);
		this.continuousComponent.startContinuity(var1,10F);
		this.animationComponent.start(var1,ModAnimations.SHOOT_SELF_FORWARD);
	}
	private void duringContinuityEvent(LivingEntity var1, IAbility var2) {
		if (var1.level().isClientSide){
			return;
		}else{
			Vec3 var3 = var1.getLookAngle().multiply(1.6D,1D,1.6D);
			AbilityHelper.setDeltaMovement((Entity)(var1),var3.x,var1.getDeltaMovement().y(),var3.z);
			if (var1.isAlive()){
				Iterator var4 = this.rangeComponent.getTargetsInArea(var1,2F).iterator();
				do{
					if (var4.hasNext()){
						LivingEntity var5 = ((LivingEntity)(var4.next()));
						if (this.hitTrackerComponent.canHit((Entity)(var5))){
							this.dealDamageComponent.hurtTarget(var1,var5,20F);
							continue;
						}else{
							continue;
						}
					}else{
						break;
					}
				} while (true);
				ArrayList var5_1 = new ArrayList();
				Iterator var6 = WyHelper.getNearbyBlocks((Entity)(var1),2).iterator();
				do{
					if (var6.hasNext()){
						BlockPos var7 = ((BlockPos)(var6.next()));
						if (((var7.getY()) >= (this.initialY)) && (NuWorld.setBlockState((Entity)(var1),var7,Blocks.AIR.defaultBlockState(),3,GRIEF_RULE))){
							if ((var1.level().random.nextDouble()) > (0.4D)){
								ItemStack var8 = new ItemStack(((ItemLike)(FOODS.get(WyHelper.randomWithRange(0,(FOODS.size()) - (1))))));
								var1.level().addFreshEntity((Entity)(new ItemEntity(var1.level(),(double)(var7.getX()),(double)(var7.getY()),(double)(var7.getZ()),var8)));
							}
							var5_1.add(var7);
							continue;
						}else{
							continue;
						}
					}else{
						break;
					}
				} while (true);
				if ((((java.util.List)(var5_1)).size()) > (0)){
					this.details.setPositions((List)(var5_1));
					WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.BREAKING_BLOCKS.get())),(Entity)(var1),0D,0D,0D,(ParticleEffect$Details)(this.details));
				}else{
					return;
				}
			}
			return;
		}
	}
	private void endContinuityEvent(LivingEntity var1, IAbility var2) {
		this.animationComponent.stop(var1);
		this.cooldownComponent.startCooldown(var1,400F);
	}
	static  {
		Item[] var0 = new Item[]{Items.COOKED_CHICKEN,Items.COOKED_RABBIT,Items.COOKED_COD,Items.APPLE,Items.COOKED_MUTTON,Items.COOKED_SALMON,Items.HONEY_BOTTLE,Items.COOKED_PORKCHOP};
		FOODS = (ArrayList) (new ArrayList((Collection)(Arrays.asList(var0))));
		AbilityRegistryEntry var1 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Launches the user forward and converts everything cut into food."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(400F),RangeComponent.getTooltip(2F,RangeComponent$RangeType.LINE),DealDamageComponent.getTooltip(20F)};
			SourceType[] lv1_5 = new SourceType[]{SourceType.PHYSICAL};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(GourmetSnipeAbility::new)))).addDescriptionLine(lv1_3).addAdvancedDescriptionLine(lv1_4).setSourceType(lv1_5).setSourceHakiNature(SourceHakiNature.HARDENING).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("gourmet_snipe","Gourmet Snipe",var1));
	}
}