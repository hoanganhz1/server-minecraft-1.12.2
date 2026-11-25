package xyz.pixelatedw.mineminenomi.abilities.doku;

import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.particles.effects.doku.DokuParticleEffectDetails;

public class DokuHelper {
	// Fields
	public static final DokuParticleEffectDetails VENOM_DETAILS = new DokuParticleEffectDetails().setHasVenomDemon();

	public static Result requiresVenomDemon(LivingEntity var0, IAbility var1) {
		return AbilityUseConditions.requiresMorph(var0,var1,new MorphInfo[]{((MorphInfo)(ModMorphs.VENOM_DEMON.get()))});
	}
}