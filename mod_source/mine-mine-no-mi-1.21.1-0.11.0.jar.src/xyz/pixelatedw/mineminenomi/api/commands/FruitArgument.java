package xyz.pixelatedw.mineminenomi.api.commands;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Stream;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.init.ModValues;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;

public class FruitArgument implements ArgumentType<AkumaNoMiItem> {
	public AkumaNoMiItem parse(StringReader var1) throws CommandSyntaxException {
		ResourceLocation var2 = ResourceLocation.read(var1);
		return ((AkumaNoMiItem)(BuiltInRegistries.ITEM.get(var2)));
	}
	public static FruitArgument fruit() {
		return new FruitArgument();
	}
	public static <S> AkumaNoMiItem getFruit(CommandContext<S> var0, String var1) {
		return ((AkumaNoMiItem)(var0.getArgument(var1,AkumaNoMiItem.class)));
	}
	public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var1, SuggestionsBuilder var2) {
		new StringReader(var2.getInput()).setCursor(var2.getStart());
		return (CompletableFuture<Suggestions>) (CompletableFuture) (this.suggestFruit(var2));
	}
	private CompletableFuture<Suggestions> suggestFruit(SuggestionsBuilder var1) {
		return (CompletableFuture<Suggestions>) (CompletableFuture) (SharedSuggestionProvider.suggestResource((Stream)(ModValues.DEVIL_FRUITS.stream().map((Function)(((Function<AkumaNoMiItem, ResourceLocation>)(AkumaNoMiItem::getRegistryKey))))),(SuggestionsBuilder)(var1)));
	}
}