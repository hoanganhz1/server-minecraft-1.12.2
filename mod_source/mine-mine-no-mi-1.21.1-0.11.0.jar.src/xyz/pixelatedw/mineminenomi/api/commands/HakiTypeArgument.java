package xyz.pixelatedw.mineminenomi.api.commands;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.commands.SharedSuggestionProvider;
import xyz.pixelatedw.mineminenomi.api.enums.HakiType;

public class HakiTypeArgument<T extends Enum<T>> implements ArgumentType<T> {
	// Fields
	final Class<T> enumClass;

	public static HakiTypeArgument<HakiType> hakiType() {
		return new HakiTypeArgument(HakiType.class);
	}
	public HakiTypeArgument(Class<T> var1) {
		this.enumClass = var1;
	}
	public T parse(StringReader var1) throws CommandSyntaxException {
		return (T) (Enum.valueOf((Class)(this.enumClass),var1.readUnquotedString()));
	}
	public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var1, SuggestionsBuilder var2) {
		return (CompletableFuture<Suggestions>) (CompletableFuture) (SharedSuggestionProvider.suggest((Stream)(Stream.of(((Enum[])(this.enumClass.getEnumConstants()))).map((Function)(((Function<Enum, String>)(Object::toString))))),(SuggestionsBuilder)(var2)));
	}
	public Collection<String> getExamples() {
		return ((Collection)(((java.util.stream.Stream)(Stream.of(((Enum[])(this.enumClass.getEnumConstants()))).map((Function)(((Function<Enum, String>)(Object::toString)))))).collect((Collector)(Collectors.toList()))));
	}
}