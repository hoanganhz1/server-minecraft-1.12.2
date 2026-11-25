package xyz.pixelatedw.mineminenomi.api.commands;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Stream;
import net.minecraft.commands.SharedSuggestionProvider;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;

public class CrewArgument implements ArgumentType<Crew> {
	public Crew parse(StringReader var1) throws CommandSyntaxException {
		return FactionsWorldData.get().getCrewByName(var1.readQuotedString());
	}
	public static CrewArgument crew() {
		return new CrewArgument();
	}
	public static <S> Crew getCrew(CommandContext<S> var0, String var1) {
		return ((Crew)(var0.getArgument(var1,Crew.class)));
	}
	public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var1, SuggestionsBuilder var2) {
		new StringReader(var2.getInput()).setCursor(var2.getStart());
		return (CompletableFuture<Suggestions>) (CompletableFuture) (this.suggestCrew(var2));
	}
	private CompletableFuture<Suggestions> suggestCrew(SuggestionsBuilder var1) {
		String var2 = var1.getInput();
		int var3 = var1.getStart();
		StringReader var4 = new StringReader(var2);
		var4.setCursor(var3);
		if ((((var4.canRead()) && ((var4.peek()) == (34))) ? (1) : (0)) != (0)){
			var4.skip();
		}
		List var5 = FactionsWorldData.get().getCrews();
		if ((var5) == (null)){
			return SharedSuggestionProvider.suggest(new String[0],var1);
		}else{
			Function<Crew, String> var6 = (l0) -> {
			String lv1_1 = l0.getName();
			String lv1_2 = "\"" + lv1_1 + "\"";
			return lv1_2;
		};
			IntFunction<String[]> var7 = (int l0) -> {
			return new String[l0];
		};
			return SharedSuggestionProvider.suggest(((String[])(var5.stream().map(var6).toArray((IntFunction)(var7)))),var1);
		}
	}
}