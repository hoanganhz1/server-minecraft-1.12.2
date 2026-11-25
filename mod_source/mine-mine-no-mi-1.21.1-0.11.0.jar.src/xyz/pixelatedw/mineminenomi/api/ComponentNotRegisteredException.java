package xyz.pixelatedw.mineminenomi.api;

import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;

public class ComponentNotRegisteredException extends RuntimeException {
	// Fields
	private static final long serialVersionUID = -2903959603666381083L;

	public ComponentNotRegisteredException(AbilityComponent<?> var1) {
		super((String)("Component " + var1.getKey().getId() + " is not registered for " + var1.getAbility().getCore()));
	}
	public ComponentNotRegisteredException(AbilityComponentKey<?> var1) {
		super((String)("Component " + var1.getId() + " is not registered"));
	}
}