package xyz.pixelatedw.mineminenomi.quests;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.enums.TrainingPointType;
import xyz.pixelatedw.mineminenomi.api.quests.Quest$ICompleteQuestEvent;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncEntityStatsPacket;

public abstract class QuestCompletionEvents {
	public static Quest$ICompleteQuestEvent giveTrainingPoints(TrainingPointType var0, int var1) {
		int var2 = var1;
		TrainingPointType var3 = var0;
		final int var2_f1 = var2;
		final TrainingPointType var3_f2 = var3;
		Quest$ICompleteQuestEvent var4 = (l0) -> {
			int lv2_3 = var2_f1;
			TrainingPointType lv2_4 = var3_f2;
			Player lv2_5 = l0;
			final int lv2_3_f1 = lv2_3;
			final TrainingPointType lv2_4_f2 = lv2_4;
			final Player lv2_5_f3 = lv2_5;
			Consumer<EntityStatsData> lv2_6 = (l2_0) -> {
				int lv1_4 = lv2_3_f1;
				TrainingPointType lv1_5 = ((TrainingPointType)(l2_0.getFightingStyle().map((Function)(((Function<FightingStyle, TrainingPointType>)(FightingStyle::getTrainingAffinity)))).orElse(null)));
				if (((lv1_5) != (null)) && (lv1_5.equals(lv2_4_f2))){
					lv1_4 = Math.round(((float)(lv2_3_f1)) * (1.25F));
				}
				l2_0.alterTrainingPoints(lv2_4_f2,lv1_4);
				ModNetwork.sendTo(new SSyncEntityStatsPacket((LivingEntity)(lv2_5_f3)),lv2_5_f3);
			};
			((java.util.Optional)(EntityStatsData.get((LivingEntity)(l0)))).ifPresent((Consumer)(lv2_6));
		};
		return var4;
	}
}