package xyz.pixelatedw.mineminenomi.entities.ai.goals.abilities;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiFullBodyHardeningAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiHardeningAbility;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiImbuingAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.ai.AbilityWrapperGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;

public class HakiAbilityWrapperGoal<A extends IAbility> extends AbilityWrapperGoal<Mob, A> {
	public HakiAbilityWrapperGoal(Mob var1, AbilityCore<A> var2) {
		super(var1,var2);
	}
	public boolean canUseWrapper() {
		if (!(GoalHelper.hasAliveTarget(this.entity))){
			return false;
		}else{
			if (!(this.hasTimePassedSinceLastEnd(20F))){
				return false;
			}else{
				boolean var1 = this.entity.getMainHandItem().isEmpty();
				if (((this.getAbility().getCore()) != (BusoshokuHakiHardeningAbility.INSTANCE.get())) && ((this.getAbility().getCore()) != (BusoshokuHakiFullBodyHardeningAbility.INSTANCE.get()))){
					if ((this.getAbility().getCore()) == (BusoshokuHakiImbuingAbility.INSTANCE.get())){
						return !(var1);
					}else{
						return true;
					}
				}else{
					return var1;
				}
			}
		}
	}
	public boolean canContinueToUseWrapper() {
		if (!(GoalHelper.hasAliveTarget(this.entity))){
			return false;
		}else{
			return true;
		}
	}
	public void startWrapper() {
	}
	public void tickWrapper() {
	}
	public void stopWrapper() {
	}
}