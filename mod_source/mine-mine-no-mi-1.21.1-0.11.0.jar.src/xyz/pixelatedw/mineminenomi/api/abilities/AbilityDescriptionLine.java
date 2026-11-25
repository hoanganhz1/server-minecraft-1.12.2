package xyz.pixelatedw.mineminenomi.api.abilities;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public class AbilityDescriptionLine {
	// Fields
	public static final AbilityDescriptionLine$IDescriptionLine NEW_LINE;
	public static final AbilityDescriptionLine$IDescriptionLine DOUBLE_NEW_LINE;
	private @Nullable() Component staticLine;
	private @Nullable() AbilityDescriptionLine$IDescriptionLine dynamicLine;
	private boolean isAdvanced;

	private AbilityDescriptionLine(AbilityDescriptionLine$IDescriptionLine var1, boolean var2) {
		this.dynamicLine = var1;
		this.isAdvanced = var2;
	}
	private AbilityDescriptionLine(Component var1, boolean var2) {
		this.staticLine = var1;
		this.isAdvanced = var2;
	}
	public static AbilityDescriptionLine of(AbilityDescriptionLine$IDescriptionLine var0) {
		return new AbilityDescriptionLine((AbilityDescriptionLine$IDescriptionLine)(var0),false);
	}
	public static AbilityDescriptionLine of(AbilityDescriptionLine$IDescriptionLine var0, boolean var1) {
		return new AbilityDescriptionLine((AbilityDescriptionLine$IDescriptionLine)(var0),var1);
	}
	public static AbilityDescriptionLine of(Component var0) {
		return new AbilityDescriptionLine((Component)(var0),false);
	}
	public static AbilityDescriptionLine of(Component var0, boolean var1) {
		return new AbilityDescriptionLine((Component)(var0),var1);
	}
	@Nullable()
	public @Nullable() Component getTextComponent(LivingEntity var1, IAbility var2) {
		if ((this.staticLine) != (null)){
			return this.staticLine;
		}else{
			return this.dynamicLine.expand(var1,var2);
		}
	}
	@Nullable()
	public @Nullable() Component getStaticTextComponent() {
		return this.staticLine;
	}
	public boolean isStatic() {
		return (this.staticLine) != (null);
	}
	public boolean isDynamic() {
		return (this.dynamicLine) != (null);
	}
	public boolean isAdvanced() {
		return this.isAdvanced;
	}
	static  {
		AbilityDescriptionLine$IDescriptionLine var0 = (l0, l1) -> {
			return Component.literal(" ");
		};
		NEW_LINE = var0;
		AbilityDescriptionLine$IDescriptionLine var1 = (l0, l1) -> {
			return Component.literal(System.getProperty("line.separator"));
		};
		DOUBLE_NEW_LINE = var1;
	}
}