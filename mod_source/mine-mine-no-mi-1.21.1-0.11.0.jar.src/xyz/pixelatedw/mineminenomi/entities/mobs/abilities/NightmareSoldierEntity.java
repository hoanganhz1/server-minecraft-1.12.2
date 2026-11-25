package xyz.pixelatedw.mineminenomi.entities.mobs.abilities;

import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.api.damagesources.AbilityDamageSource;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.entities.IRandomizedTexture;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModDamageSources;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModTags$Entities;

public class NightmareSoldierEntity extends CloneEntity implements IRandomizedTexture {
	// Fields
	private static final int RESPAWN_TIMER = 600;
	private boolean respawning;
	private int respawnTimer;
	private boolean canDieTick;
	private ResourceLocation currentTexture;

	public NightmareSoldierEntity(EntityType<NightmareSoldierEntity> var1, Level var2) {
		super((EntityType)(var1),var2);
	}
	public NightmareSoldierEntity(Level var1, LivingEntity var2) {
		super(((EntityType)(ModMobs.NIGHTMARE_SOLDIER.get())),var1,var2);
		if (((var1) != (null)) && (!(var1.isClientSide))){
			this.setSprinting(false);
			this.setMaxAliveTicks(-1);
			int var3 = this.random.nextInt(MobsHelper.NIGHTMARE_SOLDIER_TEXTURES.length);
			this.currentTexture = MobsHelper.NIGHTMARE_SOLDIER_TEXTURES[var3];
		}
	}
	protected void registerGoals() {
		this.goalSelector.addGoal(0,(Goal)(new FloatGoal((Mob)(this))));
		this.goalSelector.addGoal(1,(Goal)(new MeleeAttackGoal((PathfinderMob)(this),1D,true)));
		this.goalSelector.addGoal(3,(Goal)(new WaterAvoidingRandomStrollGoal((PathfinderMob)(this),0.8D)));
		this.goalSelector.addGoal(5,(Goal)(new LookAtPlayerGoal((Mob)(this),Player.class,8F)));
		this.goalSelector.addGoal(5,(Goal)(new RandomLookAroundGoal((Mob)(this))));
	}
	public static AttributeSupplier.Builder createAttributes() {
		return OPEntity.createAttributes().add(Attributes.ATTACK_DAMAGE,6D).add(Attributes.FOLLOW_RANGE,60D).add(Attributes.MOVEMENT_SPEED,0.30000001192092896D).add(Attributes.MAX_HEALTH,100D).add(Attributes.KNOCKBACK_RESISTANCE,0.5D).add(Attributes.ARMOR,0D);
	}
	public boolean hurt(DamageSource var1, float var2) {
		if (var1.is(DamageTypeTags.BYPASSES_INVULNERABILITY)){
			this.canDieTick = true;
			return super.hurt(var1,var2);
		}else{
			if ((var1.getDirectEntity()) != (null)){
				if (var1.getDirectEntity().getType().is(ModTags$Entities.KAIROSEKI)){
					this.canDieTick = true;
					this.hurt(ModDamageSources.getInstance((LevelAccessor)(this.level())).devilsCurse(),this.getMaxHealth());
					return false;
				}else{
					if ((this.respawning) && (var1.getDirectEntity() instanceof Player)){
						return false;
					}
				}
			}
			if ((var1.getEntity()) != (null)){
				if ((var1 instanceof AbilityDamageSource) && ((((AbilityDamageSource)(var1)).getElement()) == (SourceElement.WATER))){
					this.canDieTick = true;
					this.hurt(ModDamageSources.getInstance((LevelAccessor)(this.level())).devilsCurse(),this.getMaxHealth());
					return false;
				}else{
					if ((var1.getEntity() instanceof Player) && ((var1.getEntity()) == (this.getOwner()))){
						return false;
					}
				}
			}
			return super.hurt(var1,var2);
		}
	}
	public void tick() {
		if (!(this.level().isClientSide)){
			if (((this.getOwner()) != (null)) && (this.getOwner().isAlive())){
				if (this.canDieTick){
					this.hurt(ModDamageSources.getInstance((LevelAccessor)(this.level())).devilsCurse(),this.getMaxHealth());
				}else{
					if ((!(this.canDieTick)) && ((this.getHealth()) <= (0F))){
						this.setHealth(1F);
						this.addEffect(new MobEffectInstance((Holder)(ModEffects.UNCONSCIOUS),600,0));
						this.respawning = true;
						this.respawnTimer = 600;
					}
				}
				if (this.respawning){
					if ((this.respawnTimer) > (0)){
						this.respawnTimer = (this.respawnTimer) - (1);
					}else{
						this.removeEffect((Holder)(ModEffects.UNCONSCIOUS));
						this.setHealth(this.getMaxHealth());
						this.respawning = false;
					}
				}
				if ((this.isAlive()) && (!((!((this.getFluidTypeHeight(((FluidType)(NeoForgeMod.WATER_TYPE.value())))) > (0.9D))) && (!(this.hasEffect((Holder)(ModEffects.WET))))))){
					this.hurt(ModDamageSources.getInstance((LevelAccessor)(this.level())).devilsCurse(),this.getMaxHealth());
				}
			}else{
				this.discard();
				return;
			}
		}
		super.tick();
	}
	public void addAdditionalSaveData(CompoundTag var1) {
		super.addAdditionalSaveData(var1);
		if ((this.currentTexture) != (null)){
			var1.putString("texture",this.currentTexture.toString());
		}
	}
	public void readAdditionalSaveData(CompoundTag var1) {
		super.readAdditionalSaveData(var1);
		if (var1.contains("texture")){
			this.currentTexture = ResourceLocation.parse(var1.getString("texture"));
		}
	}
	public void writeSpawnData(RegistryFriendlyByteBuf var1) {
		super.writeSpawnData(var1);
		if ((this.currentTexture) == (null)){
			return;
		}else{
			int var2 = this.currentTexture.toString().length();
			var1.writeInt(var2);
			var1.writeUtf(this.currentTexture.toString(),var2);
			return;
		}
	}
	public void readSpawnData(RegistryFriendlyByteBuf var1) {
		super.readSpawnData(var1);
		int var2 = var1.readInt();
		this.currentTexture = ResourceLocation.parse(var1.readUtf(var2));
	}
	public void setOwner(LivingEntity var1) {
		super.setOwner(var1);
	}
	public ResourceLocation getTexture() {
		if ((this.currentTexture) == (null)){
			return MobsHelper.NIGHTMARE_SOLDIER_TEXTURES[0];
		}else{
			return this.currentTexture;
		}
	}
}