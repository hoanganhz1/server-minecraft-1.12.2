package xyz.pixelatedw.mineminenomi.abilities.hana;

import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine$IDescriptionLine;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class HanaHelper {
	// Fields
	public static final AbilityDescriptionLine$IDescriptionLine REQUIRES_MIL_FLEUR;
	public static final UUID MIL_DAMAGE_BONUS;

	public static void spawnBlossomEffect(LivingEntity var0) {
		WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.BLOOM.get())),(Entity)(var0),var0.getX(),var0.getY(),var0.getZ());
		var0.level().playSound((Player)(null),var0.blockPosition(),((SoundEvent)(ModSounds.HANA_SFX.get())),SoundSource.PLAYERS,2F,1F);
	}
	static  {
		AbilityDescriptionLine$IDescriptionLine var0 = (l0, l1) -> {
			Object[] lv1_2 = new Object[1];
			String lv1_3 = ((AbilityCore)(MilFleurAbility.INSTANCE.get())).getLocalizedName().getString();
			String lv1_4 = "§a" + lv1_3 + "§r";
			lv1_2[0] = lv1_4;
			return Component.translatable(ModI18nAbilities.DEPENDENCY_SINGLE_ACTIVE,lv1_2);
		};
		REQUIRES_MIL_FLEUR = var0;
		MIL_DAMAGE_BONUS = UUID.fromString("9fab4eda-6b81-4602-90f9-d2373304604f");
	}
}