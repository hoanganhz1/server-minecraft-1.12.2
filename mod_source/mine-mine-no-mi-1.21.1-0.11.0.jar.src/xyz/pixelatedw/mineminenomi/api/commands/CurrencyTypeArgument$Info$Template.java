package xyz.pixelatedw.mineminenomi.api.commands;

import com.mojang.brigadier.arguments.ArgumentType;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;

public class CurrencyTypeArgument$Info$Template<T extends Enum<T>> implements ArgumentTypeInfo.Template<CurrencyTypeArgument<T>> {
	// Fields
	final Class<T> enumClass;
	final CurrencyTypeArgument$Info this$0;

	CurrencyTypeArgument$Info$Template(final CurrencyTypeArgument$Info var1, Class var2) {
		this.this$0 = var1;
		this.enumClass = (Class) (var2);
	}
	public CurrencyTypeArgument<T> instantiate(CommandBuildContext var1) {
		return new CurrencyTypeArgument(this.enumClass);
	}
	public ArgumentTypeInfo<CurrencyTypeArgument<T>, ?> type() {
		return (ArgumentTypeInfo<CurrencyTypeArgument<T>, ?>) (ArgumentTypeInfo) (this.this$0);
	}
}