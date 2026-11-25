package xyz.pixelatedw.mineminenomi.abilities.zushi;

import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.zushi.SagariNoRyuseiProjectile;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class SagariNoRyuseiAbility extends Ability {
	// Fields
	private static final int COOLDOWN = 900;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<SagariNoRyuseiAbility>> INSTANCE;
	private final ContinuousComponent continuousComponent;
	private final ProjectileComponent projectileComponent;
	private final AnimationComponent animationComponent;
	private final ExplosionComponent explosionComponent;
	private int count;

	public SagariNoRyuseiAbility(AbilityCore<SagariNoRyuseiAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this));
		this.projectileComponent = new ProjectileComponent((IAbility)(this),(ProjectileComponent$IProjectileFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory)((ProjectileComponent$IProjectileFactory) ((ProjectileComponent$IProjectileFactory<SagariNoRyuseiProjectile>) (this::createProjectile))))));
		this.animationComponent = new AnimationComponent((IAbility)(this));
		this.explosionComponent = new ExplosionComponent((IAbility)(this),13824);
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent,this.projectileComponent,this.animationComponent,this.explosionComponent};
		this.addComponents(var2);
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		this.count = 0;
		this.animationComponent.start(var1,ModAnimations.RAISE_RIGHT_ARM,7);
		int var3 = 2;
		do{
			if ((var3) < (16)){
				GraviZoneAbility.gravityRing(var1,4,var3,false);
				var3 = (var3) + (2);
				continue;
			}else{
				break;
			}
		} while (true);
		boolean var4 = (var1.getRandom().nextInt(3)) == (0);
		SagariNoRyuseiProjectile var5 = ((SagariNoRyuseiProjectile)(this.projectileComponent.getNewProjectile(var1)));
		var1.level().addFreshEntity((Entity)(var5));
		if (var4){
			this.count = (this.count) + (1);
			SagariNoRyuseiProjectile var6 = ((SagariNoRyuseiProjectile)(this.projectileComponent.getNewProjectile(var1)));
			var1.level().addFreshEntity((Entity)(var6));
		}
		this.cooldownComponent.startCooldown(var1,900F);
	}
	private SagariNoRyuseiProjectile createProjectile(LivingEntity var1) {
		HitResult var2 = WyHelper.rayTraceBlocksAndEntities((Entity)(var1));
		double var3 = var2.getLocation().x;
		double var4 = var2.getLocation().y;
		double var5 = var2.getLocation().z;
		float var6 = ((this.count) == (0)) ? ((float)(WyHelper.randomWithRange(24,30))) : ((float)(WyHelper.randomWithRange(8,10)));
		SagariNoRyuseiProjectile var7 = new SagariNoRyuseiProjectile(var1.level(),var1,(IAbility)(this));
		var7.setSize(var6);
		var7.setPos(var3,(var4) + (90D),var5);
		var7.setXRot(0F);
		var7.setYRot(0F);
		AbilityHelper.setDeltaMovement((Entity)(var7),0D,-1.85D,0D);
		return var7;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Using gravity, the user pulls one (or rarely two) meteorites down on their enemies."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(900F)};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(SagariNoRyuseiAbility::new)))).addDescriptionLine(lv1_3).addAdvancedDescriptionLine(lv1_4).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("sagari_no_ryusei","Sagari no Ryusei",var0));
	}
}