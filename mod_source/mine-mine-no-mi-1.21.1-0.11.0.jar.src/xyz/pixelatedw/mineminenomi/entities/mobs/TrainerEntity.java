package xyz.pixelatedw.mineminenomi.entities.mobs;

import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.entities.ITrainer;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.JumpOutOfHoleGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.SelfHealEatGoal;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.dialogues.SOpenTrainerDialogueScreenPacket;

public abstract class TrainerEntity extends OPEntity implements ITrainer {
	// Fields
	private static final float SPAWNER_DESPAWN_DISTANCE = 40000F;
	public static final int PER_NODE_RESET_PRICE = 50;

	public TrainerEntity(EntityType<? extends TrainerEntity> var1, Level var2) {
		this(var1,var2,null);
	}
	public TrainerEntity(EntityType<? extends TrainerEntity> var1, Level var2, ResourceLocation[] var3) {
		super((EntityType)(var1),var2,var3);
	}
	public void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0,(Goal)(new JumpOutOfHoleGoal((Mob)(this))));
		this.goalSelector.addGoal(1,(Goal)(new FloatGoal((Mob)(this))));
		this.goalSelector.addGoal(3,(Goal)(new WaterAvoidingRandomStrollGoal((PathfinderMob)(this),0.8D)));
		this.goalSelector.addGoal(4,(Goal)(new SelfHealEatGoal((Mob)(this))));
		this.goalSelector.addGoal(5,(Goal)(new LookAtPlayerGoal((Mob)(this),Player.class,8F)));
		this.goalSelector.addGoal(5,(Goal)(new RandomLookAroundGoal((Mob)(this))));
	}
	public boolean removeWhenFarAway(double var1) {
		if ((this.isSpawnedViaSpawner()) && ((var1) < (40000D))){
			return false;
		}else{
			if ((this.hasCustomName()) && (!(ServerConfig.getDespawnWithNametag()))){
				return false;
			}else{
				return true;
			}
		}
	}
	public void tick() {
		if ((!(this.level().isClientSide)) && (((this.level().getDifficulty()) == (Difficulty.PEACEFUL)) && (!(NuWorld.isChallengeDimension(this.level()))))){
			this.setTarget((LivingEntity)(null));
			this.setLastHurtByMob((LivingEntity)(null));
			this.setLastHurtMob((Entity)(null));
		}
		super.tick();
	}
	protected InteractionResult mobInteract(Player var1, InteractionHand var2) {
		if ((var2) != (InteractionHand.MAIN_HAND)){
			return InteractionResult.FAIL;
		}else{
			ItemStack var3 = var1.getItemInHand(var2);
			if ((!(var3.isEmpty())) && ((var3.getItem()) == (Items.NAME_TAG))){
				return InteractionResult.FAIL;
			}else{
				if (!(var1.level().isClientSide)){
					ModNetwork.sendTo(new SOpenTrainerDialogueScreenPacket(var1,this),var1);
				}
				return InteractionResult.PASS;
			}
		}
	}
}