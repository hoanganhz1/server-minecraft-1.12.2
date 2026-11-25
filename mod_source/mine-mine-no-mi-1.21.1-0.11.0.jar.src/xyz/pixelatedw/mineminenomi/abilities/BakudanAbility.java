package xyz.pixelatedw.mineminenomi.abilities;

import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent$RangeType;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.util.IntRange;
import xyz.pixelatedw.mineminenomi.entities.BombEntity;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class BakudanAbility extends Ability {
	// Fields
	private static final int COOLDOWN = 200;
	private static final int RANGE = 15;
	public static final TargetPredicate TARGET_TEST = new TargetPredicate().testEnemyFaction().testSimpleInView();
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<BakudanAbility>> INSTANCE;
	private final AnimationComponent animationComponent;
	private IntRange amount;

	public BakudanAbility(AbilityCore<BakudanAbility> var1) {
		super((AbilityCore)(var1));
		this.animationComponent = new AnimationComponent((IAbility)(this));
		this.amount = new IntRange(5,7);
		AbilityComponent[] var2 = new AbilityComponent[]{this.animationComponent};
		this.addComponents(var2);
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		this.animationComponent.start(var1,ModAnimations.RAISE_RIGHT_ARM,7);
		int var3 = WyHelper.randomWithRange(var1.getRandom(),this.amount.getMin().intValue(),this.amount.getMax().intValue());
		int var4 = 0;
		do{
			if ((var4) < (var3)){
				BombEntity var5 = new BombEntity((Level)(var1.level()),(LivingEntity)(var1));
				BlockPos var6 = WyHelper.findValidGroundLocation((Entity)(var1),var1.blockPosition(),15,7);
				if ((var6) == (null)){

				}else{
					var5.moveTo((double)(var6.getX()),(double)(((float)(var6.getY())) + (1.5F)),(double)(var6.getZ()),(float)(var1.getRandom().nextInt(360)),0F);
					var1.level().addFreshEntity((Entity)(var5));
				}
				var4++;
				continue;
			}else{
				break;
			}
		} while (true);
		this.cooldownComponent.startCooldown(var1,200F);
	}
	public void setAmount(int var1) {
		this.setAmount(var1,var1);
	}
	public void setAmount(int var1, int var2) {
		this.amount = new IntRange(var1,var2);
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Throws a bunch of bombs near the user, when enemies come in close proximity with these bombs they'll explode."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(200F),RangeComponent.getTooltip(15F,RangeComponent$RangeType.AOE)};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.STYLE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(BakudanAbility::new)))).addDescriptionLine(lv1_3).addAdvancedDescriptionLine(lv1_4).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("bakudan","Bakudan",var0));
	}
}