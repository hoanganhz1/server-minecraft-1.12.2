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
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;

public class FactionArgument implements ArgumentType<Faction> {
	public Faction parse(StringReader var1) throws CommandSyntaxException {
		ResourceLocation var2 = ResourceLocation.read(var1);
		return ((Faction)(WyRegistry.FACTIONS.get(var2)));
	}
	public static FactionArgument faction() {
		return new FactionArgument();
	}
	public static <S> Faction getFaction(CommandContext<S> var0, String var1) {
		return ((Faction)(var0.getArgument(var1,Faction.class)));
	}
	public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var1, SuggestionsBuilder var2) {
		new StringReader(var2.getInput()).setCursor(var2.getStart());
		return (CompletableFuture<Suggestions>) (CompletableFuture) (this.suggestAbility(var2));
	}
	private CompletableFuture<Suggestions> suggestAbility(SuggestionsBuilder var1) {
		return SharedSuggestionProvider.suggestResource((Iterable)(WyRegistry.FACTIONS.keySet()),var1);
	}
}