package xyz.pixelatedw.mineminenomi.data.triggers;

import com.mojang.serialization.Codec;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.data.entity.DevilFruitData;
import xyz.pixelatedw.mineminenomi.init.ModFruits;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;

public class YomiReviveTrigger extends SimpleCriterionTrigger<YomiReviveTrigger$TriggerInstance> {
	public Codec<YomiReviveTrigger$TriggerInstance> codec() {
		return YomiReviveTrigger$TriggerInstance.CODEC;
	}
	public void trigger(ServerPlayer var1) {
		ServerPlayer var2 = var1;
		final ServerPlayer var2_f1 = var2;
		Predicate<YomiReviveTrigger$TriggerInstance> var3 = (l0) -> {
			DevilFruitData lv1_2 = ((DevilFruitData)(((java.util.Optional)(DevilFruitData.get((LivingEntity)(var2_f1)))).orElse((Object)(null))));
			if ((lv1_2) == (null)){
				return false;
			}else{
				return (lv1_2.hasDevilFruit(ModFruits.YOMI_YOMI_NO_MI)) && (!(((MorphInfo)(ModMorphs.YOMI_SKELETON.get())).isActive((LivingEntity)(var2_f1))));
			}
		};
		this.trigger(var1,var3);
	}
}