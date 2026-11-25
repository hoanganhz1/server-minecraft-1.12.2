package xyz.pixelatedw.mineminenomi.api.commands;

import com.mojang.brigadier.arguments.ArgumentType;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;

public class HakiTypeArgument$Info$Template<T extends Enum<T>> implements ArgumentTypeInfo.Template<HakiTypeArgument<T>> {
	// Fields
	final Class<T> enumClass;
	final HakiTypeArgument$Info this$0;

	HakiTypeArgument$Info$Template(final HakiTypeArgument$Info var1, Class var2) {
		this.this$0 = var1;
		this.enumClass = (Class) (var2);
	}
	public HakiTypeArgument<T> instantiate(CommandBuildContext var1) {
		return new HakiTypeArgument(this.enumClass);
	}
	public ArgumentTypeInfo<HakiTypeArgument<T>, ?> type() {
		return (ArgumentTypeInfo<HakiTypeArgument<T>, ?>) (ArgumentTypeInfo) (this.this$0);
	}
}