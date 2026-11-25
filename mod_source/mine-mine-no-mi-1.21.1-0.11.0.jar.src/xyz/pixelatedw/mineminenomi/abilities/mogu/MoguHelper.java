package xyz.pixelatedw.mineminenomi.abilities.mogu;

import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;

public class MoguHelper {
	public static Result requiresHeavyPoint(LivingEntity var0, IAbility var1) {
		return AbilityUseConditions.requiresMorph(var0,var1,new MorphInfo[]{((MorphInfo)(ModMorphs.MOGU_HEAVY.get()))});
	}
}