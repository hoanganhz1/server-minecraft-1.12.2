package xyz.pixelatedw.mineminenomi.api.entities;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;

public interface ITrainer {
	// Fields
	public static final List<QuestId<?>> EMPTY = Lists.newArrayList();

	public abstract List<QuestId<?>> getAvailableQuests(Player var0);
}