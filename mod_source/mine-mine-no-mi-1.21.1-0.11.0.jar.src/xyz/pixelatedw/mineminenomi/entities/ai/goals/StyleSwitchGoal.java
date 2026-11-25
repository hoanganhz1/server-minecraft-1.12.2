package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import java.util.Optional;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;

public class StyleSwitchGoal extends TickedGoal<Mob> {
	// Fields
	private static final int COOLDOWN = 40;
	private LivingEntity target;
	private FightingStyle currentStyle;
	private FightingStyle nextStyle;
	private final StyleSwitchGoal$IStyleSwitchEvent event;
	private final EntityStatsData props;

	public StyleSwitchGoal(Mob var1, StyleSwitchGoal$IStyleSwitchEvent var2) {
		super(var1);
		this.event = var2;
		this.props = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get((LivingEntity)(var1)))).orElse((Object)(null))));
	}
	public boolean canUse() {
		if ((this.props) == (null)){
			return false;
		}else{
			if (!(GoalHelper.hasAliveTarget(this.entity))){
				return false;
			}else{
				if (!(this.hasTimePassedSinceLastEnd(40F))){
					return false;
				}else{
					this.target = this.entity.getTarget();
					this.currentStyle = ((FightingStyle)(((java.util.Optional)(this.props.getFightingStyle())).orElse((Object)(null))));
					if ((this.currentStyle) == (null)){
						return false;
					}else{
						this.nextStyle = this.event.nextChange(this.entity,this.target,this.currentStyle);
						if (((java.lang.Object)(this.nextStyle)).equals((Object)(this.currentStyle))){
							return false;
						}else{
							return true;
						}
					}
				}
			}
		}
	}
	public boolean canContinueToUse() {
		return false;
	}
	public void start() {
		super.start();
		this.props.setFightingStyle(this.nextStyle);
		String var1 = this.entity.getDisplayName().getString();
		String var2 = this.currentStyle.getLabel().getString();
		String var3 = this.nextStyle.getLabel().getString();
		String var4 = var1 + " has changed their style from " + var2 + " to " + var3;
		WyDebug.debug(var4);
	}
}