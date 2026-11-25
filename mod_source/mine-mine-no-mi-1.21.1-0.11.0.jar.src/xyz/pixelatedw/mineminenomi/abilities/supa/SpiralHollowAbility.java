package xyz.pixelatedw.mineminenomi.abilities.supa;

import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.supa.SpiralHollowProjectile;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class SpiralHollowAbility extends Ability {
	// Fields
	private static final float COOLDOWN = 180F;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<SpiralHollowAbility>> INSTANCE;
	private final DealDamageComponent dealDamageComponent;
	private final ProjectileComponent projectileComponent;

	public SpiralHollowAbility(AbilityCore<SpiralHollowAbility> var1) {
		super((AbilityCore)(var1));
		this.dealDamageComponent = new DealDamageComponent((IAbility)(this));
		this.projectileComponent = new ProjectileComponent((IAbility)(this),(ProjectileComponent$IProjectileFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory)((ProjectileComponent$IProjectileFactory) ((ProjectileComponent$IProjectileFactory<SpiralHollowProjectile>) (this::createProjectile))))));
		AbilityComponent[] var2 = new AbilityComponent[]{this.dealDamageComponent,this.projectileComponent};
		this.addComponents(var2);
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::onUseEvent))));
	}
	private void onUseEvent(LivingEntity var1, IAbility var2) {
		Vec3 var3 = Vec3.ZERO;
		Direction var4 = Direction.fromYRot((double)(var1.getYRot()));
		Vec3 var5 = var3.add((double)(Math.abs(var4.getNormal().getX())),(double)(Math.abs(var4.getNormal().getY())),(double)(Math.abs(var4.getNormal().getZ()))).multiply(1.2D,1D,1.2D);
		double var6 = ((var1.getY()) + ((double)(var1.getEyeHeight()))) - (0.5D);
		SpiralHollowProjectile var7 = ((SpiralHollowProjectile)(this.projectileComponent.getNewProjectile(var1)));
		var7.moveTo((var1.getX()) + (var5.z),var6,(var1.getZ()) + (var5.x),0F,0F);
		var1.level().addFreshEntity((Entity)(var7));
		var7.shootFromRotation((Entity)(var1),var1.getXRot(),var1.getYRot(),0F,2F,1F);
		SpiralHollowProjectile var8 = ((SpiralHollowProjectile)(this.projectileComponent.getNewProjectile(var1)));
		var8.moveTo((var1.getX()) - (var5.z),var6,(var1.getZ()) - (var5.x),0F,0F);
		var1.level().addFreshEntity((Entity)(var8));
		var8.shootFromRotation((Entity)(var1),var1.getXRot(),var1.getYRot(),0F,2F,1F);
		var1.swing(var1.getUsedItemHand(),true);
		this.cooldownComponent.startCooldown(var1,180F);
	}
	private SpiralHollowProjectile createProjectile(LivingEntity var1) {
		return new SpiralHollowProjectile(var1.level(),var1,(IAbility)(this));
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Creates circular blades along the user's forearms slicing enemies in a close line"),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(180F)};
			SourceType[] lv1_5 = new SourceType[]{SourceType.FIST};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(SpiralHollowAbility::new)))).addDescriptionLine(lv1_3).addAdvancedDescriptionLine(lv1_4).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(lv1_5).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("spiral_hollow","Spiral Hollow",var0));
	}
}