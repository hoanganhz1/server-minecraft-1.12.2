package xyz.pixelatedw.mineminenomi.datagen;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import xyz.pixelatedw.mineminenomi.datagen.advancements.AdventureAdvancements;
import xyz.pixelatedw.mineminenomi.datagen.advancements.ChallengeAdvancements;

public class ModAdvancementsProvider extends AdvancementProvider {
	public ModAdvancementsProvider(PackOutput var1, CompletableFuture<HolderLookup.Provider> var2, ExistingFileHelper var3) {
		super(var1,var2,var3,List.of(new ChallengeAdvancements(var3),new AdventureAdvancements(var3)));
	}
}