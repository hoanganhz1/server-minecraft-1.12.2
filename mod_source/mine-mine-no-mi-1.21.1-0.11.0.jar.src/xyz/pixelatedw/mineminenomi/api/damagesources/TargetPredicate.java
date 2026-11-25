package xyz.pixelatedw.mineminenomi.api.damagesources;

import com.google.common.base.Predicates;
import java.util.function.Predicate;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.init.ModEntityPredicates;

public class TargetPredicate {
	// Fields
	public static final TargetPredicate EVERYTHING = new TargetPredicate().selector((Predicate)(Predicates.alwaysTrue())).testVanilla(TargetingConditions.forNonCombat());
	public static final TargetPredicate DEFAULT_AREA_CHECK = new TargetPredicate().testEnemyFaction();
	public static final TargetPredicate DEFAULT_LINE_CHECK = new TargetPredicate().testEnemyFaction().testAdvancedInView();
	private TargetingConditions vanillaPredicate;
	private boolean testSimpleInView;
	private boolean testAdvancedInView;
	private float fov;
	private boolean testEnemy;
	private boolean testFriendly;
	private Predicate<Entity> selector;

	public TargetPredicate() {
		this.vanillaPredicate = TargetingConditions.DEFAULT;
	}
	public TargetPredicate testVanilla(TargetingConditions var1) {
		this.vanillaPredicate = var1;
		return this;
	}
	public TargetPredicate testSimpleInView() {
		this.testSimpleInView = true;
		return this;
	}
	public TargetPredicate testAdvancedInView() {
		this.testAdvancedInView = true;
		this.fov = 60F;
		return this;
	}
	public TargetPredicate testAdvancedInView(float var1) {
		this.testAdvancedInView = true;
		this.fov = var1;
		return this;
	}
	public TargetPredicate testEnemyFaction() {
		this.testEnemy = true;
		return this;
	}
	public TargetPredicate testFriendlyFaction() {
		this.testFriendly = true;
		return this;
	}
	public TargetPredicate selector(@Nullable() Predicate<Entity> var1) {
		this.selector = var1;
		return this;
	}
	public boolean test(@Nullable() LivingEntity var1, Entity var2) {
		if (var2 instanceof LivingEntity){
			LivingEntity var3 = ((LivingEntity)(var2));
			if (!(this.vanillaPredicate.test(var1,var3))){
				return false;
			}
		}
		if (((this.selector) != (null)) && (!(this.selector.test(var2)))){
			return false;
		}else{
			if ((this.testEnemy) && (!(ModEntityPredicates.getEnemyFactions(var1).test(var2)))){
				return false;
			}else{
				if ((this.testFriendly) && (!(ModEntityPredicates.getFriendlyFactions(var1).test(var2)))){
					return false;
				}else{
					if ((this.testSimpleInView) && (!(var1.hasLineOfSight(var2)))){
						return false;
					}else{
						if ((this.testAdvancedInView) && (!((var1.hasLineOfSight(var2)) && (TargetHelper.isEntityInView((Entity)(var1),var2,this.fov))))){
							return false;
						}else{
							return true;
						}
					}
				}
			}
		}
	}
}