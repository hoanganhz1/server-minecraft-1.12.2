package xyz.pixelatedw.mineminenomi.api;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public abstract class WyBlockTagsProvider extends BlockTagsProvider {
	public WyBlockTagsProvider(PackOutput var1, CompletableFuture<HolderLookup.Provider> var2, String var3, @Nullable() ExistingFileHelper var4) {
		super(var1,var2,var3,var4);
	}
	public abstract void addTags(HolderLookup.Provider var0);
	public IntrinsicHolderTagsProvider.IntrinsicTagAppender<Block> tag(TagKey<Block> var1) {
		return (IntrinsicHolderTagsProvider.IntrinsicTagAppender<Block>) (IntrinsicHolderTagsProvider.IntrinsicTagAppender) (super.tag(var1));
	}
}