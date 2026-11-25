package xyz.pixelatedw.mineminenomi.quests.objectives;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.ICureEffectObjective;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;

public class CureEffectObjective extends Objective implements ICureEffectObjective {
	// Fields
	private MobEffect[] effects;

	public CureEffectObjective(Quest var1, Component var2, int var3, MobEffect var4) {
		this((Quest)(var1),(Component)(var2),var3,(MobEffect[])(new MobEffect[]{var4}));
	}
	public CureEffectObjective(Quest var1, Component var2, int var3, MobEffect[] var4) {
		super(var1,var2);
		this.setMaxProgress((float)(var3));
		this.effects = var4;
	}
	public boolean checkEffect(Player var1, MobEffectInstance var2) {
		return Arrays.stream(this.effects).anyMatch((Predicate)(((Predicate<MobEffect>)((l0) -> {
			return (var2.getEffect()) == (l0);
		}))));
	}
}