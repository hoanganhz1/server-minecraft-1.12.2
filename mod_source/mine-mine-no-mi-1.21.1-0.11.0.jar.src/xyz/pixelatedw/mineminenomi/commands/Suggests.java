package xyz.pixelatedw.mineminenomi.commands;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityGroups;

public class Suggests {
	public static SuggestionProvider<CommandSourceStack> allAbilityGroups() {
		SuggestionProvider<CommandSourceStack> var0 = (l0, l1) -> {
			String lv1_2 = l1.getRemaining().toLowerCase(Locale.ROOT);
			Iterator lv1_3 = AbilityGroups.getAllIds().iterator();
			do{
				if (lv1_3.hasNext()){
					ResourceLocation lv1_4 = ((ResourceLocation)(lv1_3.next()));
					String lv1_5 = lv1_4.toString();
					String lv1_6 = lv1_4.getPath();
					if ((!(SharedSuggestionProvider.matchesSubStr(lv1_2,lv1_5))) && (!(SharedSuggestionProvider.matchesSubStr(lv1_2,lv1_6)))){
						continue;
					}else{
						l1.suggest(lv1_5);
						continue;
					}
				}else{
					break;
				}
			} while (true);
			return l1.buildFuture();
		};
		return var0;
	}
}