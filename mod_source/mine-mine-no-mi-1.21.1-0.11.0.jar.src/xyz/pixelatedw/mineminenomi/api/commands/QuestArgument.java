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
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;

public class QuestArgument implements ArgumentType<QuestId<?>> {
	public QuestId<?> parse(StringReader var1) throws CommandSyntaxException {
		ResourceLocation var2 = ResourceLocation.read(var1);
		return (QuestId<?>) (QuestId) (((QuestId)(WyRegistry.QUESTS.get(var2))));
	}
	public static QuestArgument quest() {
		return new QuestArgument();
	}
	public static <S> QuestId<?> getQuest(CommandContext<S> var0, String var1) {
		return (QuestId<?>) (QuestId) (((QuestId)(var0.getArgument(var1,QuestId.class))));
	}
	public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var1, SuggestionsBuilder var2) {
		new StringReader(var2.getInput()).setCursor(var2.getStart());
		return (CompletableFuture<Suggestions>) (CompletableFuture) (this.suggestQuest(var2));
	}
	private CompletableFuture<Suggestions> suggestQuest(SuggestionsBuilder var1) {
		return SharedSuggestionProvider.suggestResource((Iterable)(WyRegistry.QUESTS.keySet()),var1);
	}
}