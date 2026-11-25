package xyz.pixelatedw.mineminenomi.api.quests.components;

import com.google.common.base.Predicate;
import java.util.function.Consumer;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool;

public class CountdownQuestComponent extends QuestComponent {
	// Fields
	private final PriorityEventPool<CountdownQuestComponent$IFinishCountdownEvent> onFinishCountdownEvents;
	private final Interval countdown;
	private final Predicate<Objective> canCountdown;

	public CountdownQuestComponent(Objective var1, int var2) {
		this(var1,var2,(l0$sam) -> {
			Objective l0 = ((Objective)(l0$sam));

			return true;
		});
	}
	public CountdownQuestComponent(Objective var1, int var2, Predicate<Objective> var3) {
		super(var1);
		this.onFinishCountdownEvents = (PriorityEventPool) (new PriorityEventPool());
		this.countdown = Interval.startAtMax(var2);
		this.canCountdown = var3;
	}
	public CountdownQuestComponent addFinishCountdownEvent(CountdownQuestComponent$IFinishCountdownEvent var1) {
		return this.addFinishCountdownEvent(100,var1);
	}
	public CountdownQuestComponent addFinishCountdownEvent(int var1, CountdownQuestComponent$IFinishCountdownEvent var2) {
		this.onFinishCountdownEvents.addEvent(var1,var2);
		return this;
	}
	public void tick(Player var1) {
		Player var3 = null;
		if (!(super.isValid())){
			return;
		}else{
			if (!(this.canCountdown.test(super.getObjective()))){
				this.restart();
				return;
			}else{
				if (!(this.countdown.canTick())){
					return;
				}else{
					CountdownQuestComponent var2 = this;
					var3 = var1;
					final Player var3_f1 = var3;
					Consumer<CountdownQuestComponent$IFinishCountdownEvent> var4 = (l0) -> {
			l0.tick(var3_f1,super.getObjective());
		};
					((xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool)(this.onFinishCountdownEvents)).dispatch((Consumer)(var4));
					return;
				}
			}
		}
	}
	public void restart() {
		this.countdown.restartIntervalToMax();
	}
}