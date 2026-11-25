package xyz.pixelatedw.mineminenomi.api.abilities;

import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.fml.common.asm.enumextension.IExtensibleEnum;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModValues;

public enum AbilityCategory implements IExtensibleEnum {
	// Fields
	DEVIL_FRUITS(AbilityHelper.DF_CATEGORY_ICON),
	RACIAL(AbilityHelper.RACE_CATEGORY_ICON),
	STYLE((Function)(null)),
	HAKI(AbilityHelper.HAKI_CATEGORY_ICON),
	FACTION((Function)(null)),
	EQUIPMENT((Function)(null)),
	ALL((Function)(null));
	private Function<Player, ResourceLocation> iconFunction;

	private AbilityCategory(Function<Player, ResourceLocation> var3) {
		Function<Player, ResourceLocation> var4 = (l0) -> {
			return ModValues.NIL_LOCATION;
		};
		this.iconFunction = var4;
		this.iconFunction = var3;
	}
	@Nullable()
	public @Nullable() ResourceLocation getIcon(Player var1) {
		if (((this.iconFunction) != (null)) && ((var1) != (null))){
			return ((ResourceLocation)(((java.util.function.Function)(this.iconFunction)).apply((Object)(var1))));
		}else{
			return null;
		}
	}
	public Predicate<IAbility> isAbilityPartofCategory() {
		if ((this) == (ALL)){
			Predicate<IAbility> var1 = (l0) -> {
			return true;
		};
			return var1;
		}else{
			AbilityCategory var1_1 = this;
			Predicate<IAbility> var2 = (l0) -> {
			return l0.getCore().getCategory().equals(this);
		};
			return var2;
		}
	}
	public Predicate<AbilityCore<?>> isCorePartofCategory() {
		if ((this) == (ALL)){
			Predicate<AbilityCore<?>> var1 = (Predicate) ((Predicate<AbilityCore>) ((l0) -> {
			return true;
		}));
			return var1;
		}else{
			AbilityCategory var1_1 = this;
			Predicate<AbilityCore<?>> var2 = (Predicate) ((Predicate<AbilityCore>) ((l0) -> {
			return l0.getCategory().equals(this);
		}));
			return var2;
		}
	}
	public static AbilityCategory create(String var0, Function<Player, ResourceLocation> var1) {
		throw new IllegalStateException((String)("Enum not extended"));
	}
}