package xyz.pixelatedw.mineminenomi.abilities;

import net.minecraft.network.chat.Component;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public abstract class RideableAbility extends PassiveAbility {
	public RideableAbility(AbilityCore<? extends RideableAbility> var1) {
		super((AbilityCore)(var1));
		this.setDisplayName(ModI18nAbilities.RIDEABLE_NAME);
	}
}