package xyz.pixelatedw.mineminenomi.api.protection;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BlockProtectionRule {
	// Fields
	 Set<TagKey<Block>> approvedTags;
	 Set<TagKey<Block>> bannedTags;
	 Set<Supplier<Block>> approvedBlocks;
	 Set<Supplier<Block>> bannedBlocks;
	 Set<BlockProtectionRule$IReplaceBlockRule> replaceRules;
	 boolean bypassGriefFlag;
	 boolean everythingFlag;

	public BlockProtectionRule() {
		this.approvedTags = new HashSet();
		this.bannedTags = new HashSet();
		this.approvedBlocks = new HashSet();
		this.bannedBlocks = new HashSet();
		this.replaceRules = new HashSet();
		this.bypassGriefFlag = false;
		this.everythingFlag = false;
	}
	public void setApprovedBlocks(Set<Supplier<Block>> var1) {
		this.approvedBlocks = var1;
	}
	public Set<Supplier<Block>> getApprovedBlocks() {
		return this.approvedBlocks;
	}
	public void setBannedBlocks(Set<Supplier<Block>> var1) {
		this.bannedBlocks = var1;
	}
	public Set<Supplier<Block>> getBannedBlocks() {
		return this.bannedBlocks;
	}
	private void setApprovedTags(Set<TagKey<Block>> var1) {
		this.approvedTags = var1;
	}
	public Set<TagKey<Block>> getApprovedTags() {
		return this.approvedTags;
	}
	public void setBannedTags(Set<TagKey<Block>> var1) {
		this.bannedTags = var1;
	}
	public Set<TagKey<Block>> getBannedTags() {
		return this.bannedTags;
	}
	private void setReplaceRules(Set<BlockProtectionRule$IReplaceBlockRule> var1) {
		this.replaceRules = var1;
	}
	public Set<BlockProtectionRule$IReplaceBlockRule> getReplaceRules() {
		return this.replaceRules;
	}
	public boolean check(Level var1, BlockPos var2, BlockState var3) {
		if (this.isBanned(var3)){
			return false;
		}else{
			Iterator var4 = this.replaceRules.iterator();
			do{
				if (var4.hasNext()){
					if (((BlockProtectionRule$IReplaceBlockRule)(var4.next())).replace(var1,var2,var3)){
						return true;
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
			return this.isApproved(var3);
		}
	}
	public boolean isApproved(BlockState var1) {
		if (this.everythingFlag){
			return true;
		}else{
			if (this.approvedTags.stream().anyMatch((Predicate)(((Predicate<TagKey>)((l0) -> {
			return var1.is(l0);
		}))))){
				return true;
			}else{
				if (this.approvedBlocks.stream().anyMatch((Predicate)(((java.util.function.Predicate)((Predicate) ((Predicate<Supplier>) ((l0) -> {
			return ((java.lang.Object)(var1.getBlock())).equals(l0.get());
		}))))))){
					return true;
				}else{
					return false;
				}
			}
		}
	}
	public boolean isBanned(BlockState var1) {
		if (this.bannedTags.stream().anyMatch((Predicate)(((Predicate<TagKey>)((l0) -> {
			return var1.is(l0);
		}))))){
			return true;
		}else{
			if (this.bannedBlocks.stream().anyMatch((Predicate)(((java.util.function.Predicate)((Predicate) ((Predicate<Supplier>) ((l0) -> {
			return ((java.lang.Object)(var1.getBlock())).equals(l0.get());
		}))))))){
				return true;
			}else{
				return false;
			}
		}
	}
	public boolean canBypassGriefProtection() {
		return this.bypassGriefFlag;
	}
	final void jdec$private$78797a2f706978656c61746564772f6d696e656d696e656e6f6d692f6170692f70726f74656374696f6e2f426c6f636b50726f74656374696f6e52756c65$0(Set arg0) { ((BlockProtectionRule)this).setApprovedTags(arg0); }

	final void jdec$private$78797a2f706978656c61746564772f6d696e656d696e656e6f6d692f6170692f70726f74656374696f6e2f426c6f636b50726f74656374696f6e52756c65$1(Set arg0) { ((BlockProtectionRule)this).setReplaceRules(arg0); }

}