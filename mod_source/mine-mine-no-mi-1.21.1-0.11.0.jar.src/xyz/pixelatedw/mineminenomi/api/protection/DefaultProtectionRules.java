package xyz.pixelatedw.mineminenomi.api.protection;

import net.minecraft.tags.TagKey;
import net.neoforged.neoforge.common.Tags;
import xyz.pixelatedw.mineminenomi.init.ModTags$Blocks;

public class DefaultProtectionRules {
	// Fields
	public static final BlockProtectionRule FOLIAGE;
	public static final BlockProtectionRule AIR;
	public static final BlockProtectionRule AIR_FOLIAGE;
	public static final BlockProtectionRule AIR_FOLIAGE_LIQUID;
	public static final BlockProtectionRule CORE;
	public static final BlockProtectionRule CORE_FOLIAGE;
	public static final BlockProtectionRule CORE_FOLIAGE_ORE;
	public static final BlockProtectionRule CORE_FOLIAGE_ORE_LIQUID;
	public static final BlockProtectionRule CORE_FOLIAGE_ORE_LIQUID_AIR;

	static  {
		TagKey[] var0 = new TagKey[]{ModTags$Blocks.BLOCK_PROT_FOLIAGE};
		FOLIAGE = new BlockProtectionRule$Builder(new BlockProtectionRule[0]).addApprovedTags(var0).build();
		TagKey[] var1 = new TagKey[]{ModTags$Blocks.BLOCK_PROT_AIR};
		AIR = new BlockProtectionRule$Builder(new BlockProtectionRule[0]).addApprovedTags(var1).build();
		BlockProtectionRule[] var2 = new BlockProtectionRule[]{AIR};
		TagKey[] var3 = new TagKey[]{ModTags$Blocks.BLOCK_PROT_FOLIAGE};
		AIR_FOLIAGE = new BlockProtectionRule$Builder(var2).addApprovedTags(var3).build();
		BlockProtectionRule[] var4 = new BlockProtectionRule[]{AIR_FOLIAGE};
		TagKey[] var5 = new TagKey[]{ModTags$Blocks.BLOCK_PROT_LIQUIDS};
		AIR_FOLIAGE_LIQUID = new BlockProtectionRule$Builder(var4).addApprovedTags(var5).build();
		TagKey[] var6 = new TagKey[]{ModTags$Blocks.BLOCK_PROT_CORE};
		CORE = new BlockProtectionRule$Builder(new BlockProtectionRule[0]).addApprovedTags(var6).build();
		BlockProtectionRule[] var7 = new BlockProtectionRule[]{CORE};
		TagKey[] var8 = new TagKey[]{ModTags$Blocks.BLOCK_PROT_FOLIAGE};
		CORE_FOLIAGE = new BlockProtectionRule$Builder(var7).addApprovedTags(var8).build();
		BlockProtectionRule[] var9 = new BlockProtectionRule[]{CORE_FOLIAGE};
		TagKey[] var10 = new TagKey[]{Tags.Blocks.ORES};
		CORE_FOLIAGE_ORE = new BlockProtectionRule$Builder(var9).addApprovedTags(var10).build();
		BlockProtectionRule[] var11 = new BlockProtectionRule[]{CORE_FOLIAGE_ORE};
		TagKey[] var12 = new TagKey[]{ModTags$Blocks.BLOCK_PROT_LIQUIDS};
		CORE_FOLIAGE_ORE_LIQUID = new BlockProtectionRule$Builder(var11).addApprovedTags(var12).build();
		BlockProtectionRule[] var13 = new BlockProtectionRule[]{CORE_FOLIAGE_ORE_LIQUID};
		TagKey[] var14 = new TagKey[]{ModTags$Blocks.BLOCK_PROT_AIR};
		CORE_FOLIAGE_ORE_LIQUID_AIR = new BlockProtectionRule$Builder(var13).addApprovedTags(var14).build();
	}
}