package xyz.pixelatedw.mineminenomi.entities.mobs.animals;

import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.entity.IEntityWithComplexSpawn;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;

public class DenDenMushiEntity extends Animal implements IEntityWithComplexSpawn {
	// Fields
	private int type;

	public DenDenMushiEntity(EntityType<DenDenMushiEntity> var1, Level var2) {
		super(var1,var2);
		this.type = 0;
		this.xpReward = 0;
		if (((var2) != (null)) && (!(var2.isClientSide))){
			this.type = this.random.nextInt(MobsHelper.DEN_DEN_MUSHI_TEXTURES.length);
		}
	}
	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED,0.11999999731779099D).add(Attributes.MAX_HEALTH,5D);
	}
	protected void registerGoals() {
		this.goalSelector.addGoal(1,(Goal)(new PanicGoal((PathfinderMob)(this),0.75D)));
		this.goalSelector.addGoal(2,(Goal)(new WaterAvoidingRandomStrollGoal((PathfinderMob)(this),1D)));
		this.goalSelector.addGoal(3,(Goal)(new LookAtPlayerGoal((Mob)(this),Player.class,6F)));
		this.goalSelector.addGoal(4,(Goal)(new RandomLookAroundGoal((Mob)(this))));
	}
	public InteractionResult mobInteract(Player var1, InteractionHand var2) {
		ItemStack var3 = var1.getItemInHand(var2);
		if ((!(var1.level().isClientSide)) && (((var2) == (InteractionHand.MAIN_HAND)) && ((var3.getItem()) == (Items.IRON_INGOT)))){
			ItemStack var4 = new ItemStack(((ItemLike)(ModBlocks.DEN_DEN_MUSHI.get())));
			var4.set(ModDataComponents.TYPE,Integer.valueOf(this.type));
			var1.addItem(var4);
			var3.shrink(1);
			this.remove(Entity.RemovalReason.DISCARDED);
			return InteractionResult.SUCCESS;
		}else{
			return InteractionResult.FAIL;
		}
	}
	public void addAdditionalSaveData(CompoundTag var1) {
		super.addAdditionalSaveData(var1);
		var1.putInt("type",this.type);
	}
	public void readAdditionalSaveData(CompoundTag var1) {
		super.readAdditionalSaveData(var1);
		this.type = var1.getInt("type");
	}
	public void writeSpawnData(RegistryFriendlyByteBuf var1) {
		var1.writeInt(this.type);
	}
	public void readSpawnData(RegistryFriendlyByteBuf var1) {
		this.type = var1.readInt();
	}
	public AgeableMob getBreedOffspring(ServerLevel var1, AgeableMob var2) {
		return null;
	}
	public ResourceLocation getCurrentTexture() {
		return MobsHelper.DEN_DEN_MUSHI_TEXTURES[this.type];
	}
	public boolean isFood(ItemStack var1) {
		return false;
	}
}