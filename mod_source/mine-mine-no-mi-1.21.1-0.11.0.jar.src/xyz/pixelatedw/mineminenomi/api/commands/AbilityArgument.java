package xyz.pixelatedw.mineminenomi.api.commands;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;

public class AbilityArgument implements ArgumentType<AbilityCore<?>> {
	public AbilityCore<?> parse(StringReader var1) throws CommandSyntaxException {
		ResourceLocation var2 = ResourceLocation.read(var1);
		return (AbilityCore<?>) (AbilityCore) (((AbilityCore)(WyRegistry.ABILITIES.get(var2))));
	}
	public static AbilityArgument ability() {
		return new AbilityArgument();
	}
	public static <S> AbilityCore<?> getAbility(CommandContext<S> var0, String var1) {
		return (AbilityCore<?>) (AbilityCore) (((AbilityCore)(var0.getArgument(var1,AbilityCore.class))));
	}
	public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var1, SuggestionsBuilder var2) {
		new StringReader(var2.getInput()).setCursor(var2.getStart());
		return (CompletableFuture<Suggestions>) (CompletableFuture) (this.suggestAbility(var2));
	}
	private CompletableFuture<Suggestions> suggestAbility(SuggestionsBuilder var1) {
		return SharedSuggestionProvider.suggestResource((Iterable)(WyRegistry.ABILITIES.keySet()),var1);
	}
}