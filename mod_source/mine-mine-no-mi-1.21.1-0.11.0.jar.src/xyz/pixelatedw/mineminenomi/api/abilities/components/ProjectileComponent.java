package xyz.pixelatedw.mineminenomi.api.abilities.components;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine$IDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityStat;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityStat$AbilityStatType;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityStat$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuLightningEntity;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public class ProjectileComponent extends AbilityComponent<IAbility> {
	// Fields
	private static final UUID PROJ_DAMAGE_BONUS_MANAGER_UUID = UUID.fromString("2ab0d674-f9ca-4c11-976a-c8a105332318");
	private static final UUID PROJ_INACCURACY_BONUS_MANAGER_UUID = UUID.fromString("2ad65e4e-5edf-4c95-a826-d1f7bba33c23");
	public static final Predicate<Entity> TARGET_CHECK;
	private final ProjectileComponent$IProjectileFactory<?> factory;
	private NuProjectileEntity cachedProjectile;
	private NuProjectileEntity projectileShot;
	private boolean isHitScan;
	private final PriorityEventPool<ProjectileComponent$IAfterShootEvent> afterShootEvents;
	private final BonusManager damageBonusManager;
	private final BonusManager inaccuracyBonusManager;

	
	
	public <P extends NuProjectileEntity> ProjectileComponent(IAbility var1, ProjectileComponent$IProjectileFactory<P> var2) {
		super(((AbilityComponentKey)(ModAbilityComponents.PROJECTILE.get())),var1);
		this.afterShootEvents = (PriorityEventPool) (new PriorityEventPool());
		this.damageBonusManager = new BonusManager(PROJ_DAMAGE_BONUS_MANAGER_UUID);
		this.inaccuracyBonusManager = new BonusManager(PROJ_INACCURACY_BONUS_MANAGER_UUID);
		this.factory = var2;
		this.addBonusManager(this.damageBonusManager);
		this.addBonusManager(this.inaccuracyBonusManager);
	}
	public ProjectileComponent addAfterShootEvent(int var1, ProjectileComponent$IAfterShootEvent var2) {
		this.afterShootEvents.addEvent(var1,var2);
		return this;
	}
	public void shoot(LivingEntity var1) {
		this.shoot(var1,2F,1F);
	}
	public void shoot(LivingEntity var1, float var2, float var3) {
		this.shoot(this.getNewProjectile(var1),var1,var1.getXRot(),var1.getYRot(),var2,var3);
	}
	public void shootWithSpread(LivingEntity var1, float var2, float var3, double var4) {
		NuProjectileEntity var5 = this.getNewProjectile(var1);
		double var6 = ((var1.getX()) + (WyHelper.randomWithRange(-(var4),var4))) + (WyHelper.randomDouble());
		double var7 = ((var1.getEyeY()) + (WyHelper.randomWithRange(0D,var4))) + (WyHelper.randomDouble());
		var5.moveTo(var6,var7,((var1.getZ()) + (WyHelper.randomWithRange(-(var4),var4))) + (WyHelper.randomDouble()),var1.getYRot(),var1.getXRot());
		this.shoot(var5,var1,var1.getXRot(),var1.getYRot(),var2,var3);
	}
	@Nullable()
	public @Nullable() NuProjectileEntity getShotProjectile() {
		return this.projectileShot;
	}
	public boolean hasProjectileAlive() {
		return ((this.projectileShot) != (null)) && (this.projectileShot.isAlive());
	}
	public <P extends NuProjectileEntity> P getNewProjectile(LivingEntity var1) {
		NuProjectileEntity var2 = this.factory.createProjectile(var1);
		var2.setDamage(this.damageBonusManager.applyBonus(var2.getDamage()));
		return (P) (var2);
	}
	public BonusManager getDamageBonusManager() {
		return this.damageBonusManager;
	}
	public BonusManager getInaccuracyBonusManager() {
		return this.inaccuracyBonusManager;
	}
	public NuProjectileEntity getCachedProjectile(LivingEntity var1) {
		if ((this.cachedProjectile) == (null)){
			this.cachedProjectile = this.factory.createProjectile(var1);
		}
		return this.cachedProjectile;
	}
	public void setHitScan(boolean var1) {
		this.isHitScan = var1;
	}
	static  {
		Predicate<Entity> var0 = (l0) -> {
			if (((l0) != (null)) && ((l0.isAlive()) && (l0 instanceof LivingEntity))){
				return true;
			}else{
				return false;
			}
		};
		TARGET_CHECK = var0;
	}
@Deprecated()
	public static AbilityDescriptionLine$IDescriptionLine[] getProjectileTooltips() {
		AbilityDescriptionLine$IDescriptionLine[] var0 = new AbilityDescriptionLine$IDescriptionLine[3];
		AbilityDescriptionLine$IDescriptionLine var1 = (l0, l1) -> {
			String lv1_2 = ModI18nAbilities.DESCRIPTION_STAT_NAME_PROJECTILE.getString();
			String lv1_3 = "§a" + lv1_2 + "§r";
			return Component.literal(lv1_3);
		};
		var0[0] = var1;
		var0[1] = getDamageFromProjectileTooltip();
		var0[2] = getPiercingFromProjectileTooltip();
		return var0;
	}
	@Deprecated()
	private static AbilityDescriptionLine$IDescriptionLine getDamageFromProjectileTooltip() {
		AbilityDescriptionLine$IDescriptionLine var0 = (l0, l1) -> {
			LivingEntity lv4_2 = l0;
			final LivingEntity lv4_2_f1 = lv4_2;
			Function<ProjectileComponent, NuProjectileEntity> lv4_3 = (l2_0) -> {
				return l2_0.getCachedProjectile(lv4_2_f1);
			};
			NuProjectileEntity lv4_4 = ((NuProjectileEntity)(((java.util.Optional)(l1.getComponent(((AbilityComponentKey)(ModAbilityComponents.PROJECTILE.get()))))).map((Function)(lv4_3)).orElse(null)));
			if (((lv4_4) != (null)) && ((lv4_4.getDamage()) > (0F))){
				NuProjectileEntity lv4_5 = lv4_4;
				final NuProjectileEntity lv4_5_f1 = lv4_5;
				Function<BonusManager, Float> lv4_6 = (l2_0) -> {
				return Float.valueOf((l2_0.applyBonus(lv4_5_f1.getDamage())) - (lv4_5_f1.getDamage()));
			};
				float lv4_7 = ((Float)(((java.util.Optional)(l1.getComponent(((AbilityComponentKey)(ModAbilityComponents.PROJECTILE.get()))).map((Function)(((Function<ProjectileComponent, BonusManager>)(ProjectileComponent::getDamageBonusManager)))))).map((Function)(lv4_6)).orElse(Float.valueOf(0F)))).floatValue();
				AbilityStat$AbilityStatType lv4_8 = ((lv4_7) > (0F)) ? (AbilityStat$AbilityStatType.BUFF) : (((lv4_7) < (0F)) ? (AbilityStat$AbilityStatType.DEBUFF) : (AbilityStat$AbilityStatType.NEUTRAL));
				return new AbilityStat$Builder((Component)(ModI18nAbilities.DESCRIPTION_STAT_NAME_DAMAGE),lv4_4.getDamage()).withBonus(lv4_7,lv4_8).build().getStatDescription(2);
			}else{
				return null;
			}
		};
		return var0;
	}@Deprecated()
	private static AbilityDescriptionLine$IDescriptionLine getPiercingFromProjectileTooltip() {
		AbilityDescriptionLine$IDescriptionLine var0 = (l0, l1) -> {
			return null;
		};
		return var0;
	}
	public void shoot(NuProjectileEntity var1, LivingEntity var2, float var3, float var4, float var5, float var6) {
		this.ensureIsRegistered();
		var1.setOwner((Entity)(var2));
		if (this.isHitScan){
			float var7 = (float)(var1.getMaxLife());
			EntityHitResult var8 = WyHelper.rayTraceEntities((Entity)(var2),(double)(var7),(double)(var1.getBbWidth()),TARGET_CHECK);
			if (((var8.getEntity()) != (null)) && (var8.getEntity() instanceof LivingEntity)){
				var1.onHit((HitResult)(var8));
			}else{
				if ((var8.getEntity()) == (null)){
					BlockHitResult var9 = WyHelper.rayTraceBlocks((Entity)(var2),(double)(var7));
					var1.setPos(var9.getLocation().x,var8.getLocation().y,var8.getLocation().z);
					var1.onHit((HitResult)(var9));
				}
			}
		}else{
			var6 = ItemsHelper.getSniperInaccuracy(var6,var2);
			var6 = this.inaccuracyBonusManager.applyBonus(var6);
			this.projectileShot = var1;
			if (var1 instanceof NuLightningEntity){
				NuLightningEntity var7_1 = ((NuLightningEntity)(var1));
			}else{
				if (var2 instanceof Mob){
					Mob var7_2 = ((Mob)(var2));
					if ((var7_2.getTarget()) != (null)){
						double var8_1 = (var7_2.getTarget().getX()) - (var2.getX());
						double var9_1 = (var7_2.getTarget().getY(0D)) - (var1.getY());
						double var10 = (var7_2.getTarget().getZ()) - (var2.getZ());
						var1.shoot(var8_1,(var9_1) + ((Math.sqrt(((var8_1) * (var8_1)) + ((var10) * (var10)))) * (0.05D)),var10,2.5F,0F);
					}else{
						var1.shootFromRotation((Entity)(var2),var2.getXRot(),var2.getYRot(),0F,var5,var6);
					}
				}else{
					var1.shootFromRotation((Entity)(var2),var2.getXRot(),var2.getYRot(),0F,var5,var6);
				}
			}
			var2.level().addFreshEntity((Entity)(var1));
		}
		LivingEntity var7_3 = var2;
		final LivingEntity var7_3_f1 = var7_3;
		Consumer<ProjectileComponent$IAfterShootEvent> var8_2 = (l0) -> {
			l0.triggerAfterEvent(var7_3_f1);
		};
		((xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool)(this.afterShootEvents)).dispatch((Consumer)(var8_2));
	}}