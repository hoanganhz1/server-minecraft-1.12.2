package xyz.pixelatedw.mineminenomi.entities.mobs;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.OpenDoorGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.RandomWalkingAroundHomeGoal;
import xyz.pixelatedw.mineminenomi.entities.ai.goals.SelfHealEatGoal;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.dialogues.SOpenBarkeeperDialogueScreenPacket;

public class BarkeeperEntity extends OPEntity {
	// Fields
	public static final int BASE_RUM_PRICE = 100;
	public static final int BASE_RUMOR_PRICE = 1000;
	public int previousHash;

	public BarkeeperEntity(EntityType<BarkeeperEntity> var1, Level var2) {
		super((EntityType)(var1),var2,MobsHelper.BARKEEPER_TEXTURES);
		this.previousHash = 0;
	}
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1,(Goal)(new FloatGoal((Mob)(this))));
		this.goalSelector.addGoal(2,(Goal)(new OpenDoorGoal((Mob)(this),false)));
		this.goalSelector.addGoal(3,(Goal)(new RandomWalkingAroundHomeGoal(this,0.5D).setWalkDistance(2).setWalkOffset(2)));
		this.goalSelector.addGoal(4,(Goal)(new SelfHealEatGoal((Mob)(this))));
		this.goalSelector.addGoal(5,(Goal)(new LookAtPlayerGoal((Mob)(this),Player.class,8F)));
		this.goalSelector.addGoal(5,(Goal)(new RandomLookAroundGoal((Mob)(this))));
		this.targetSelector.addGoal(0,(Goal)(new HurtByTargetGoal((PathfinderMob)(this),new Class[0])));
	}
	public static AttributeSupplier.Builder createAttributes() {
		return OPEntity.createAttributes().add(Attributes.FOLLOW_RANGE,10D).add(Attributes.MOVEMENT_SPEED,0.3D).add(Attributes.ATTACK_DAMAGE,1D).add(Attributes.MAX_HEALTH,20D).add(Attributes.ARMOR,0D);
	}
	protected InteractionResult mobInteract(Player var1, InteractionHand var2) {
		if ((var2) != (InteractionHand.MAIN_HAND)){
			return InteractionResult.FAIL;
		}else{
			if (!(var1.level().isClientSide)){
				ModNetwork.sendTo(new SOpenBarkeeperDialogueScreenPacket(this.getId()),var1);
			}
			return InteractionResult.PASS;
		}
	}
}