package xyz.pixelatedw.mineminenomi.quests.objectives;

import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.enums.ItemWeight;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.init.ModTags$Items;

public final class SharedHitChecks {
	// Fields
	public static final HitEntityObjective$ICheckHit USED_TECHNOLOGY;
	public static final HitEntityObjective$ICheckHit HAS_SWORD;
	public static final HitEntityObjective$ICheckHit HAS_BOW;
	public static final HitEntityObjective$ICheckHit HAS_WEAPON;
	public static final HitEntityObjective$ICheckHit HAS_LIGHT_WEAPON;
	public static final HitEntityObjective$ICheckHit HAS_MEDIUM_WEAPON;
	public static final HitEntityObjective$ICheckHit HAS_HEAVY_WEAPON;

	public static final boolean isMelee(DamageSource var0) {
		Entity var1 = var0.getEntity();
		if (var1 instanceof LivingEntity){
			LivingEntity var2 = ((LivingEntity)(var1));
			if ((var0.getEntity()) != (var0.getDirectEntity())){
				return false;
			}else{
				return var2.getMainHandItem().isEmpty();
			}
		}else{
			return false;
		}
	}
	public static final boolean isProjectile(DamageSource var0) {
		if (isMelee(var0)){
			return false;
		}else{
			Entity var1 = var0.getDirectEntity();
			if (var1 instanceof Projectile){
				Projectile var2 = ((Projectile)(var1));
				return true;
			}else{
				return false;
			}
		}
	}
	public static final boolean isBeyondEntityReach(LivingEntity var0, Entity var1) {
		return ((double)(getDistance((Entity)(var0),var1.position()))) > (var0.getAttributeValue(Attributes.ENTITY_INTERACTION_RANGE));
	}
	public static final boolean isMoving(Entity var0) {
		Vec3 var1 = var0.getDeltaMovement();
		if ((var1.lengthSqr()) < (0.0001D)){
			return false;
		}else{
			return (Entity.collideBoundingBox(var0,var1,var0.getBoundingBox(),var0.level(),List.of()).lengthSqr()) > (0.0001D);
		}
	}
	public static final boolean hasMinDistance(Entity var0, Vec3 var1, float var2) {
		return (getDistance(var0,var1)) >= (var2);
	}
	public static final boolean hasMaxDistance(Entity var0, Vec3 var1, float var2) {
		return (getDistance(var0,var1)) <= (var2);
	}
	public static final boolean through(Entity var0, Vec3 var1, float var2, float var3) {
		float var4 = getDistance(var0,var1);
		return ((var4) >= (var2)) && ((var4) <= (var3));
	}
	private static float getDistance(Entity var0, Vec3 var1) {
		return (Mth.sqrt((float)(var0.distanceToSqr(var1.x(),var1.y(),var1.z())))) - ((var0.getBbWidth()) / (2F));
	}
	static  {
		HitEntityObjective$ICheckHit var0 = (l0, l1, l2, l3) -> {
			ItemStack lv1_4 = l0.getMainHandItem();
			return (IDamageSourceHandler.getHandler(l2).hasType(SourceType.TECHNOLOGY)) || (lv1_4.is(ModTags$Items.CLIMA_TACTS));
		};
		USED_TECHNOLOGY = var0;
		HitEntityObjective$ICheckHit var1 = (l0, l1, l2, l3) -> {
			return ItemsHelper.isSword(l0.getMainHandItem());
		};
		HAS_SWORD = var1;
		HitEntityObjective$ICheckHit var2 = (l0, l1, l2, l3) -> {
			return ItemsHelper.isBowOrGun(l0.getMainHandItem());
		};
		HAS_BOW = var2;
		HitEntityObjective$ICheckHit var3 = (l0, l1, l2, l3) -> {
			return ItemsHelper.isWeapon(l0.getMainHandItem());
		};
		HAS_WEAPON = var3;
		HitEntityObjective$ICheckHit var4 = (l0, l1, l2, l3) -> {
			ItemStack lv5_4 = l0.getMainHandItem();
			return (HAS_WEAPON.test(l0,l1,l2,l3)) && ((ItemsHelper.getItemWeight(lv5_4)) == (ItemWeight.LIGHT));
		};
		HAS_LIGHT_WEAPON = var4;
		HitEntityObjective$ICheckHit var5 = (l0, l1, l2, l3) -> {
			ItemStack lv6_4 = l0.getMainHandItem();
			return (HAS_WEAPON.test(l0,l1,l2,l3)) && ((ItemsHelper.getItemWeight(lv6_4)) == (ItemWeight.MEDIUM));
		};
		HAS_MEDIUM_WEAPON = var5;
		HitEntityObjective$ICheckHit var6 = (l0, l1, l2, l3) -> {
			ItemStack lv7_4 = l0.getMainHandItem();
			return (HAS_WEAPON.test(l0,l1,l2,l3)) && ((ItemsHelper.getItemWeight(lv7_4)) == (ItemWeight.HEAVY));
		};
		HAS_HEAVY_WEAPON = var6;
	}
}