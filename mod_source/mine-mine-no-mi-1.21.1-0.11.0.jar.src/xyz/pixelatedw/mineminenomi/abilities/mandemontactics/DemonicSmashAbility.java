package xyz.pixelatedw.mineminenomi.abilities.mandemontactics;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent$RangeType;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.api.math.VectorHelper;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class DemonicSmashAbility extends Ability {
	// Fields
	private static final int COOLDOWN = 160;
	private static final float RANGE = 1.25F;
	private static final float DAMAGE = 20F;
	public static final TargetPredicate TARGET_TEST = new TargetPredicate().testEnemyFaction().testSimpleInView();
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<DemonicSmashAbility>> INSTANCE;
	private final RangeComponent rangeComponent;
	private final DealDamageComponent dealDamageComponent;
	private final AnimationComponent animationComponent;
	private boolean isLookingRight;

	public DemonicSmashAbility(AbilityCore<DemonicSmashAbility> var1) {
		super((AbilityCore)(var1));
		this.rangeComponent = new RangeComponent((IAbility)(this));
		this.dealDamageComponent = new DealDamageComponent((IAbility)(this));
		this.animationComponent = new AnimationComponent((IAbility)(this));
		this.isLookingRight = true;
		AbilityComponent[] var2 = new AbilityComponent[]{this.rangeComponent,this.dealDamageComponent,this.animationComponent};
		this.addComponents(var2);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::requiresBluntWeapon))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		List var3 = this.rangeComponent.getTargetsInLine(var1,1.25F,1.5F);
		this.isLookingRight = var1.getRandom().nextBoolean();
		this.animationComponent.start(var1,ModAnimations.DEMONIC_SMASH,7);
		Vec3 var4 = VectorHelper.getRelativeOffset(var1.position(),var1.getYRot(),new Vec3((this.isLookingRight) ? (-5D) : (5D),0D,0D));
		Vec3 var5 = var1.position().subtract(var4);
		Iterator var6 = var3.iterator();
		do{
			if (var6.hasNext()){
				LivingEntity var7 = ((LivingEntity)(var6.next()));
				ItemsHelper.stopShieldAndStartCooldown(var7,100);
				if (this.dealDamageComponent.hurtTarget(var1,var7,20F)){
					AbilityHelper.setDeltaMovement((Entity)(var7),var5);
					continue;
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
		this.cooldownComponent.startCooldown(var1,160F);
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			AbilityDescriptionLine$IDescriptionLine[] lv1_2 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(160F),RangeComponent.getTooltip(1.25F,RangeComponent$RangeType.LINE),DealDamageComponent.getTooltip(20F)};
			SourceType[] lv1_3 = new SourceType[]{SourceType.BLUNT};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.STYLE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(DemonicSmashAbility::new)))).addAdvancedDescriptionLine(lv1_2).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(lv1_3).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("demonic_smash","Demonic Smash",var0));
	}
}