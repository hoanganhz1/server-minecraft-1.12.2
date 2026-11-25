package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.entities.ChakramEntity;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;
import xyz.pixelatedw.mineminenomi.items.weapons.ChakramItem;

public class ThrowChakramsGoal extends TickedGoal<Mob> {
	// Fields
	private LivingEntity target;
	private final int cooldown;

	public ThrowChakramsGoal(Mob var1) {
		super(var1);
		this.cooldown = 200;
	}
	public ThrowChakramsGoal(Mob var1, int var2) {
		super(var1);
		this.cooldown = var2;
	}
	public boolean canUse() {
		if (!(GoalHelper.hasAliveTarget(this.entity))){
			return false;
		}else{
			if (!(this.hasTimePassedSinceLastEnd((float)(this.cooldown)))){
				return false;
			}else{
				this.target = this.entity.getTarget();
				if (GoalHelper.isWithinDistance((LivingEntity)(this.entity),this.target,6D)){
					return false;
				}else{
					return true;
				}
			}
		}
	}
	public boolean canContinueToUse() {
		return false;
	}
	public void start() {
		super.start();
		float var1 = (float)((this.target.getX()) - (this.entity.getX()));
		float var2 = (float)(Math.toDegrees(Math.atan2((double)(var1),(double)((float)((this.target.getZ()) - (this.entity.getZ()))))));
		ChakramEntity var3 = new ChakramEntity((LivingEntity)(this.entity),(ItemStack)(((ChakramItem)(ModWeapons.CHAKRAM.get())).getDefaultInstance()));
		var3.setPos(var3.getX(),this.entity.getEyeY(),var3.getZ());
		var3.shootFromRotation((Entity)(this.entity),this.entity.getXRot(),-(var2),0F,3F,1F);
		var3.setAttackDamage((float)((3) + (((ChakramItem)(ModWeapons.CHAKRAM.get())).getDamage(this.entity.getMainHandItem()))));
		this.entity.level().addFreshEntity((Entity)(var3));
	}
}