package xyz.pixelatedw.mineminenomi.abilities;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent$IGetHitEvent;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class BulletReflectAbility extends PassiveAbility {
	// Fields
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<BulletReflectAbility>> INSTANCE;
	private final DamageTakenComponent damageTakenComponent;

	public BulletReflectAbility(AbilityCore<BulletReflectAbility> var1) {
		super((AbilityCore)(var1));
		this.damageTakenComponent = new DamageTakenComponent((IAbility)(this)).addOnAttackEvent((DamageTakenComponent$IGetHitEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent$IGetHitEvent)(this::onDamageTaken))));
		AbilityComponent[] var2 = new AbilityComponent[]{this.damageTakenComponent};
		this.addComponents(var2);
	}
	private float onDamageTaken(LivingEntity var1, IAbility var2, DamageSource var3, float var4) {
		IDamageSourceHandler var5 = IDamageSourceHandler.getHandler(var3);
		if ((((var5.hasType(SourceType.PHYSICAL)) && ((var5.hasType(SourceType.PROJECTILE)) && (var5.hasType(SourceType.BLUNT)))) ? (1) : (0)) != (0)){
			Entity var6 = var3.getDirectEntity();
			if (var6 instanceof Projectile){
				Projectile var7 = ((Projectile)(var6));
				var7.tickCount = 0;
				var7.setOwner((Entity)(var1));
				AbilityHelper.setDeltaMovement((Entity)(var7),-(var7.getDeltaMovement().x),-(var7.getDeltaMovement().y),-(var7.getDeltaMovement().z));
			}
			return 0F;
		}else{
			return var4;
		}
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Reflects bullets"),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,AbilityType.PASSIVE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(BulletReflectAbility::new)))).addDescriptionLine(lv1_3).setHidden().build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("bullet_reflect","Bullet Reflect",var0));
	}
}