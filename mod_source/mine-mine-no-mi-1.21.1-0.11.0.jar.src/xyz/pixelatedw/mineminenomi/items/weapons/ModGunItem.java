package xyz.pixelatedw.mineminenomi.items.weapons;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector3f;
import xyz.pixelatedw.mineminenomi.abilities.bomu.BreezeBreathBombAbility;
import xyz.pixelatedw.mineminenomi.abilities.sniper.HissatsuAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.init.ModTags$Items;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.items.bullets.BulletItem;

public class ModGunItem extends ProjectileWeaponItem {
	// Fields
	private static final int SHOTS_PER_GUNPOWDER = 5;
	private int maxGunpowder;
	private float bulletSpeed;
	private float inaccuracy;
	private int shotCooldown;
	private int reloadCooldown;
	private float damageMultiplier;
	private Predicate<ItemStack> bulletCheck;
	public static final Predicate<ItemStack> GUN_AMMO;
	public static final Predicate<ItemStack> BAZOOKA_AMMO;

	public ModGunItem(int var1) {
		this(var1,GUN_AMMO);
	}
	public ModGunItem(int var1, Predicate<ItemStack> var2) {
		super(new Item.Properties().stacksTo(1).durability(var1));
		this.maxGunpowder = 3;
		this.bulletSpeed = 2F;
		this.inaccuracy = 2F;
		this.shotCooldown = 10;
		this.reloadCooldown = 20;
		this.damageMultiplier = 1F;
		this.bulletCheck = var2;
	}
	public InteractionResultHolder<ItemStack> use(Level var1, Player var2, InteractionHand var3) {
		ItemStack var4 = var2.getItemInHand(var3);
		int var5 = (!(var2.getProjectile(var4).isEmpty())) ? (1) : (0);
		int var6 = ((this.getLoadedGunPowder(var4)) > (0)) ? (1) : (0);
		int var7 = 0;
		AbilityData var8 = ((AbilityData)(((java.util.Optional)(AbilityData.get((LivingEntity)(var2)))).orElse((Object)(null))));
		if ((var8) != (null)){
			BreezeBreathBombAbility var9 = ((BreezeBreathBombAbility)(var8.getEquippedAbility(((AbilityCore)(BreezeBreathBombAbility.INSTANCE.get())))));
			var7 = (((var9) != (null)) && (var9.isContinuous())) ? (1) : (0);
		}
		if ((var7) != (0)){
			playLoadSound(var4,(LivingEntity)(var2));
			var2.startUsingItem(var3);
			return InteractionResultHolder.success(var4);
		}else{
			if ((var6) == (0)){
				int var9_1 = 0;
				do{
					if ((var9_1) < (var2.getInventory().getContainerSize())){
						ItemStack var10 = var2.getInventory().getItem(var9_1);
						if ((var10.getItem()) == (Items.GUNPOWDER)){
							int var11 = this.maxGunpowder;
							if ((var10.getCount()) < (var11)){
								var11 = var10.getCount();
							}
							this.setLoadedGunPowder(var4,(var11) * (5));
							var2.getInventory().removeItem(var9_1,var11);
							var6 = 1;
							var2.getCooldowns().addCooldown((Item)(this),this.reloadCooldown);
							break;
						}else{
							var9_1++;
							continue;
						}
					}else{
						break;
					}
				} while (true);
			}
			if ((var6) == (0)){
				return InteractionResultHolder.fail(var4);
			}else{
				if ((!(var2.getAbilities().instabuild)) && ((var5) == (0))){
					return InteractionResultHolder.fail(var4);
				}else{
					playLoadSound(var4,(LivingEntity)(var2));
					var2.startUsingItem(var3);
					return InteractionResultHolder.consume(var4);
				}
			}
		}
	}
	protected void shootProjectile(LivingEntity var1, Projectile var2, int var3, float var4, float var5, float var6, LivingEntity var7) {
		var2.shootFromRotation((Entity)(var1),var1.getXRot(),(var1.getYRot()) + (var6),0F,var4,var5);
	}
	public Predicate<ItemStack> getAllSupportedProjectiles() {
		return this.bulletCheck;
	}
	public static void playLoadSound(ItemStack var0, LivingEntity var1) {
		var1.level().playSound((Player)(null),var1.blockPosition(),((SoundEvent)(ModSounds.GUN_LOAD.get())),SoundSource.PLAYERS,1.5F,(0.75F) + ((var1.getRandom().nextFloat()) / (4F)));
	}
	public static void playShootSound(ItemStack var0, LivingEntity var1) {
		if (((var0.getItem()) != (ModWeapons.FLINTLOCK.get())) && ((var0.getItem()) != (ModWeapons.WALKER.get()))){
			if ((var0.getItem()) == (ModWeapons.BAZOOKA.get())){
				var1.level().playSound((Player)(null),var1.blockPosition(),((SoundEvent)(ModSounds.GENERIC_EXPLOSION.get())),SoundSource.PLAYERS,2F,(0.75F) + ((var1.getRandom().nextFloat()) / (4F)));
			}else{
				if ((var0.getItem()) == (ModWeapons.SENRIKU.get())){
					var1.level().playSound((Player)(null),var1.blockPosition(),((SoundEvent)(ModSounds.SNIPER_SHOOT.get())),SoundSource.PLAYERS,2F,(0.75F) + ((var1.getRandom().nextFloat()) / (4F)));
				}else{
					return;
				}
			}
		}else{
			var1.level().playSound((Player)(null),var1.blockPosition(),((SoundEvent)(ModSounds.PISTOL_SHOOT.get())),SoundSource.PLAYERS,2F,(0.75F) + ((var1.getRandom().nextFloat()) / (4F)));
		}
	}
	public void spawnParticles(LivingEntity var1) {
		Vec3 var2 = var1.getLookAngle().yRot((float)(Math.toRadians(-30D)));
		Vec3 var3 = var1.position().add(0D,(double)(var1.getEyeHeight()),0D).add(var2);
		int var4 = 0;
		do{
			if ((var4) < (10)){
				if (((var4) % (2)) == (0)){
					WyHelper.spawnParticles((ParticleOptions)(ParticleTypes.POOF),((ServerLevel)(var1.level())),var3.x,var3.y,var3.z);
				}else{
					WyHelper.spawnParticles((ParticleOptions)(ParticleTypes.SMOKE),((ServerLevel)(var1.level())),var3.x,var3.y,var3.z);
				}
				var4++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
	public void setLoadedGunPowder(ItemStack var1, int var2) {
		var1.set(ModDataComponents.GUNPOWDER,Integer.valueOf(var2));
	}
	public int getLoadedGunPowder(ItemStack var1) {
		return ((Integer)(var1.getOrDefault(ModDataComponents.GUNPOWDER,Integer.valueOf(0)))).intValue();
	}
	public int getDefaultProjectileRange() {
		return 32;
	}
	public int getUseDuration(ItemStack var1, LivingEntity var2) {
		return 72000;
	}
	public UseAnim getUseAnimation(ItemStack var1) {
		return UseAnim.BOW;
	}
	public void appendHoverText(ItemStack var1, Item.TooltipContext var2, List<Component> var3, TooltipFlag var4) {
		if (var1.has(ModDataComponents.GUNPOWDER)){
			Object[] var5 = new Object[]{Integer.valueOf(this.getLoadedGunPowder(var1))};
			((java.util.List)(var3)).add((Object)(Component.translatable(ModI18n.GUN_POWDER_AMOUNT,var5)));
		}
	}
	public ModGunItem setGunpowderLimit(int var1) {
		this.maxGunpowder = var1;
		return this;
	}
	public ModGunItem setDamageMultiplier(float var1) {
		this.damageMultiplier = var1;
		return this;
	}
	public ModGunItem setShotCooldown(int var1) {
		this.shotCooldown = var1;
		return this;
	}
	public ModGunItem setReloadCooldown(int var1) {
		this.reloadCooldown = var1;
		return this;
	}
	public ModGunItem setInaccuracy(float var1) {
		this.inaccuracy = var1;
		return this;
	}
	public ModGunItem setBulletSpeed(float var1) {
		this.bulletSpeed = var1;
		return this;
	}
	static  {
		Predicate<ItemStack> var0 = (l0) -> {
			return l0.is(ModTags$Items.GUN_AMMO);
		};
		GUN_AMMO = var0;
		Predicate<ItemStack> var1 = (l0) -> {
			return l0.is(ModTags$Items.BAZOOKA_AMMO);
		};
		BAZOOKA_AMMO = var1;
	}

	public void releaseUsing(ItemStack var1, Level var2, LivingEntity var3, int var4) {
		LivingEntity var9 = null;
		if (var3 instanceof Player){
			Player var5 = ((Player)(var3));
			ItemStack var6 = var5.getProjectile(var1);
			if (!(var5.level().isClientSide)){
				AbilityData var7 = ((AbilityData)(((java.util.Optional)(AbilityData.get((LivingEntity)(var5)))).orElse((Object)(null))));
				if ((var7) != (null)){
					BreezeBreathBombAbility var8 = ((BreezeBreathBombAbility)(var7.getEquippedAbility(((AbilityCore)(BreezeBreathBombAbility.INSTANCE.get())))));
					if (((var8) != (null)) && (((Boolean)(var8.getComponent(((AbilityComponentKey)(ModAbilityComponents.CONTINUOUS.get()))).map((Function)(((Function<ContinuousComponent, Boolean>)(ContinuousComponent::isContinuous)))).orElse(Boolean.valueOf(false)))).booleanValue())){
						var9 = var3;
						final LivingEntity var9_f1 = var9;
						Consumer<ProjectileComponent> var10 = (l0) -> {
			l0.shoot(var9_f1);
		};
						((java.util.Optional)(var8.getComponent(((AbilityComponentKey)(ModAbilityComponents.PROJECTILE.get()))))).ifPresent((Consumer)(var10));
						return;
					}
				}
			}
			Item var7_1 = var6.getItem();
			if (var7_1 instanceof BulletItem){
				BulletItem var8_1 = ((BulletItem)(var7_1));
				int var9_1 = this.getLoadedGunPowder(var1);
				Registry var10_1 = ((Registry)(var2.registryAccess().registry(Registries.ENCHANTMENT).get()));
				int var11 = ((var5.isCreative()) || ((var1.getEnchantmentLevel(((Holder)(var10_1.getHolder(Enchantments.INFINITY).get())))) > (0))) ? (1) : (0);
				if (!(var2.isClientSide)){
					int var12 = (this.getUseDuration(var1,(LivingEntity)(var5))) - (var4);
					var12 = EventHooks.onArrowLoose(var1,var2,var5,var12,(!(var1.isEmpty())) || ((var11) != (0)));
					if ((var12) < (0)){
						return;
					}else{
						boolean var13 = HissatsuAbility.checkHitScan((LivingEntity)(var5));
						int var14 = EnchantmentHelper.processProjectileCount(((ServerLevel)(var2)),var1,(Entity)(var5),var12);
						int var15 = 0;
						do{
							if ((var15) < (var14)){
								NuProjectileEntity var16 = var8_1.createProjectile(var2,(LivingEntity)(var5));
								int var17 = var1.getEnchantmentLevel(((Holder)(var10_1.getHolder(Enchantments.POWER).get())));
								if ((var17) > (0)){
									var16.setDamage((float)((((double)(var16.getDamage())) + (((double)(var17)) * (0.5D))) + (0.5D)));
								}
								int var18 = var1.getEnchantmentLevel(((Holder)(var10_1.getHolder(Enchantments.PUNCH).get())));
								if ((var18) > (0)){
									var16.setKnockback(var18);
								}
								if ((var1.getEnchantmentLevel(((Holder)(var10_1.getHolder(Enchantments.FLAME).get())))) > (0)){
									var16.igniteForTicks(100);
								}
								var16.setDamage((float)(Math.round((var16.getDamage()) * (this.damageMultiplier))));
								if (var13){
									EntityHitResult var19 = WyHelper.rayTraceEntities((Entity)(var5),(double)((float)(var16.getMaxLife())),1.25D,ProjectileComponent.TARGET_CHECK);
									if (((var19.getEntity()) != (null)) && (var19.getEntity() instanceof LivingEntity)){
										var16.onHit((HitResult)(var19));
									}
								}else{
									if ((var15) > (0)){
										Vec3 var19_1 = var5.getUpVector(1F);
										Quaternionf var20 = new Quaternionf().setAngleAxis((double)(((float)(var15)) * (0.017453292F)),var19_1.x,var19_1.y,var19_1.z);
										Vector3f var21 = var5.getViewVector(1F).toVector3f().rotate((Quaternionfc)(var20));
										var16.shoot((double)(var21.x()),(double)(var21.y()),(double)(var21.z()),this.bulletSpeed,this.inaccuracy);
									}else{
										var16.shootFromRotation((Entity)(var5),var5.getXRot(),var5.getYRot(),0F,this.bulletSpeed,this.inaccuracy);
									}
									var5.level().addFreshEntity((Entity)(var16));
								}
								var15++;
								continue;
							}else{
								break;
							}
						} while (true);
						playShootSound(var1,(LivingEntity)(var5));
						this.spawnParticles((LivingEntity)(var5));
						var1.hurtAndBreak(1,(LivingEntity)(var5),var5.getEquipmentSlotForItem(var1));
						var5.awardStat(Stats.ITEM_USED.get(this));
					}
				}
				var5.getCooldowns().addCooldown((Item)(this),this.shotCooldown);
				if ((var11) != (0)){
					return;
				}else{
					this.setLoadedGunPowder(var1,Math.max(0,--(var9_1)));
					var6.shrink(1);
					if (var6.isEmpty()){
						var5.getInventory().removeItem(var6);
					}else{
						return;
					}
				}
			}else{
				return;
			}
		}
	}}