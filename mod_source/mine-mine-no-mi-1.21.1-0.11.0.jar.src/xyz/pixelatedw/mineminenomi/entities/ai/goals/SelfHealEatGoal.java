package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import java.util.EnumSet;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;

public class SelfHealEatGoal extends TickedGoal<Mob> {
	// Fields
	private static final int COOLDOWN = 1200;
	private static final Item[] FOODS;
	private ItemStack prevItem;
	private int eatTicks;

	public SelfHealEatGoal(Mob var1) {
		super(var1);
		this.prevItem = ItemStack.EMPTY;
		this.eatTicks = 0;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE));
	}
	public boolean canUse() {
		if ((this.entity.getHealth()) >= (this.entity.getMaxHealth())){
			return false;
		}else{
			if (GoalHelper.hasAliveTarget(this.entity)){
				return false;
			}else{
				if (((this.entity.getLastHurtByMob()) != (null)) && (this.entity.getLastHurtByMob().isAlive())){
					return false;
				}else{
					if (!(this.hasTimePassedSinceLastEnd(1200F))){
						return false;
					}else{
						return true;
					}
				}
			}
		}
	}
	public boolean canContinueToUse() {
		if ((this.eatTicks) > (60)){
			return false;
		}else{
			return true;
		}
	}
	public void start() {
		super.start();
		this.prevItem = this.entity.getMainHandItem();
		ItemStack var1 = FOODS[this.entity.getRandom().nextInt(FOODS.length)].getDefaultInstance();
		this.entity.setItemSlot(EquipmentSlot.MAINHAND,var1);
		this.entity.startUsingItem(InteractionHand.MAIN_HAND);
		this.eatTicks = 0;
	}
	public void tick() {
		this.eatTicks = (this.eatTicks) + (1);
	}
	public void stop() {
		super.stop();
		this.entity.stopUsingItem();
		this.entity.setItemSlot(EquipmentSlot.MAINHAND,this.prevItem);
		this.entity.heal(10F);
	}
	static  {
		Item[] var0 = new Item[]{Items.COOKED_BEEF,Items.COOKED_CHICKEN,Items.COOKED_COD,Items.COOKED_MUTTON,Items.COOKED_PORKCHOP,Items.COOKED_RABBIT,Items.COOKED_SALMON,Items.BREAD};
		FOODS = var0;
	}
}