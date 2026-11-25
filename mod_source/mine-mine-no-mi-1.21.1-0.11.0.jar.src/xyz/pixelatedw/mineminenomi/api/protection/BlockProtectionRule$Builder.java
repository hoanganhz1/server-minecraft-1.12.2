package xyz.pixelatedw.mineminenomi.api.protection;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class BlockProtectionRule$Builder {
	// Fields
	 Set<TagKey<Block>> approvedTags;
	 Set<TagKey<Block>> bannedTags;
	 Set<Supplier<Block>> approvedBlocks;
	 Set<Supplier<Block>> bannedBlocks;
	 Set<BlockProtectionRule$IReplaceBlockRule> replaceRules;
	 boolean bypassGriefFlag;
	 boolean everythingFlag;

	public BlockProtectionRule$Builder(BlockProtectionRule... var1) {
		this.approvedTags = new HashSet();
		this.bannedTags = new HashSet();
		this.approvedBlocks = new HashSet();
		this.bannedBlocks = new HashSet();
		this.replaceRules = new HashSet();
		this.bypassGriefFlag = false;
		this.everythingFlag = false;
		BlockProtectionRule[] var2 = var1;
		int var3 = var2.length;
		int var4 = 0;
		do{
			if ((var4) < (var3)){
				BlockProtectionRule var5 = var2[var4];
				((java.util.Set)(this.replaceRules)).addAll((Collection)(var5.getReplaceRules()));
				((java.util.Set)(this.approvedTags)).addAll((Collection)(var5.getApprovedTags()));
				((java.util.Set)(this.bannedTags)).addAll((Collection)(var5.getBannedTags()));
				((java.util.Set)(this.approvedBlocks)).addAll((Collection)(var5.getApprovedBlocks()));
				((java.util.Set)(this.bannedBlocks)).addAll((Collection)(var5.getBannedBlocks()));
				var4++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
	public BlockProtectionRule$Builder addReplaceRules(BlockProtectionRule$IReplaceBlockRule var1) {
		this.replaceRules.add(var1);
		return this;
	}
	public BlockProtectionRule$Builder addApprovedTags(TagKey<Block>... var1) {
		this.approvedTags.addAll((Collection)(Lists.newArrayList(var1)));
		return this;
	}
	public BlockProtectionRule$Builder addBannedTags(TagKey<Block>... var1) {
		this.bannedTags.addAll((Collection)(Lists.newArrayList(var1)));
		return this;
	}
	public BlockProtectionRule$Builder addApprovedBlocks(Supplier<Block>... var1) {
		this.approvedBlocks.addAll((Collection)(Lists.newArrayList(var1)));
		return this;
	}
	public BlockProtectionRule$Builder addBannedBlocks(Supplier<Block>... var1) {
		this.bannedBlocks.addAll((Collection)(Lists.newArrayList(var1)));
		return this;
	}
	public BlockProtectionRule$Builder setBypassGriefFlag() {
		this.bypassGriefFlag = true;
		return this;
	}
	public BlockProtectionRule$Builder setEverythingFlag() {
		this.everythingFlag = true;
		return this;
	}
	public BlockProtectionRule build() {
		BlockProtectionRule var1 = new BlockProtectionRule();
		((BlockProtectionRule)(var1)).jdec$private$78797a2f706978656c61746564772f6d696e656d696e656e6f6d692f6170692f70726f74656374696f6e2f426c6f636b50726f74656374696f6e52756c65$1(((Set)(this.replaceRules)));
		((BlockProtectionRule)(var1)).jdec$private$78797a2f706978656c61746564772f6d696e656d696e656e6f6d692f6170692f70726f74656374696f6e2f426c6f636b50726f74656374696f6e52756c65$0(((Set)(this.approvedTags)));
		var1.setBannedTags(this.bannedTags);
		var1.setApprovedBlocks(this.approvedBlocks);
		var1.setBannedBlocks(this.bannedBlocks);
		var1.bypassGriefFlag = this.bypassGriefFlag;
		var1.everythingFlag = this.everythingFlag;
		return var1;
	}
}