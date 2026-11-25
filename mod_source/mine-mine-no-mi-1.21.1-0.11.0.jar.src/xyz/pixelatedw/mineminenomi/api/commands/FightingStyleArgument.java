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
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;

public class FightingStyleArgument implements ArgumentType<FightingStyle> {
	public FightingStyle parse(StringReader var1) throws CommandSyntaxException {
		ResourceLocation var2 = ResourceLocation.read(var1);
		return ((FightingStyle)(WyRegistry.FIGHTING_STYLES.get(var2)));
	}
	public static FightingStyleArgument fightingStyle() {
		return new FightingStyleArgument();
	}
	public static <S> FightingStyle getFightingStyle(CommandContext<S> var0, String var1) {
		return ((FightingStyle)(var0.getArgument(var1,FightingStyle.class)));
	}
	public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var1, SuggestionsBuilder var2) {
		new StringReader(var2.getInput()).setCursor(var2.getStart());
		return (CompletableFuture<Suggestions>) (CompletableFuture) (this.suggestAbility(var2));
	}
	private CompletableFuture<Suggestions> suggestAbility(SuggestionsBuilder var1) {
		return SharedSuggestionProvider.suggestResource((Iterable)(WyRegistry.FIGHTING_STYLES.keySet()),var1);
	}
}