package xyz.pixelatedw.mineminenomi.datagen.tags;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageTypes;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import xyz.pixelatedw.mineminenomi.init.ModDamageTypes;
import xyz.pixelatedw.mineminenomi.init.ModTags$DamageTypes;

public class ModDamageTypeTagsProvider extends DamageTypeTagsProvider {
	public ModDamageTypeTagsProvider(PackOutput var1, CompletableFuture<HolderLookup.Provider> var2, ExistingFileHelper var3) {
		super(var1,var2,"mineminenomi",var3);
	}
	protected void addTags(HolderLookup.Provider var1) {
		ResourceKey[] var2 = new ResourceKey[]{ModDamageTypes.BLEED,ModDamageTypes.HEART_ATTACK,ModDamageTypes.DEVILS_CURSE,ModDamageTypes.SOULBOUND,ModDamageTypes.OUT_OF_BODY,ModDamageTypes.SUN_INCINERATION,ModDamageTypes.STORED_DAMAGE,DamageTypes.DROWN};
		this.tag(ModTags$DamageTypes.UNAVOIDABLE).add(var2);
		ResourceKey[] var3 = new ResourceKey[]{ModDamageTypes.FROSTBITES,ModDamageTypes.FROZEN,ModDamageTypes.POISON,ModDamageTypes.GRAVITY};
		this.tag(DamageTypeTags.BYPASSES_ARMOR).addTag(ModTags$DamageTypes.UNAVOIDABLE).add(var3);
		this.tag(DamageTypeTags.BYPASSES_SHIELD).addTag(DamageTypeTags.BYPASSES_ARMOR);
		this.tag(DamageTypeTags.BYPASSES_EFFECTS).addTag(ModTags$DamageTypes.UNAVOIDABLE).add(ModDamageTypes.GRAVITY);
		ResourceKey[] var4 = new ResourceKey[]{ModDamageTypes.FROSTBITES,ModDamageTypes.FROZEN,ModDamageTypes.POISON,ModDamageTypes.GRAVITY};
		this.tag(DamageTypeTags.BYPASSES_RESISTANCE).addTag(ModTags$DamageTypes.UNAVOIDABLE).add(var4);
		ResourceKey[] var5 = new ResourceKey[]{ModDamageTypes.FROSTBITES,ModDamageTypes.FROZEN,ModDamageTypes.POISON,ModDamageTypes.GRAVITY};
		this.tag(DamageTypeTags.BYPASSES_ENCHANTMENTS).addTag(ModTags$DamageTypes.UNAVOIDABLE).add(var5);
		ResourceKey[] var6 = new ResourceKey[]{ModDamageTypes.FROSTBITES,ModDamageTypes.FROZEN,ModDamageTypes.POISON,ModDamageTypes.GRAVITY};
		this.tag(ModTags$DamageTypes.BYPASSES_LOGIA).addTag(ModTags$DamageTypes.UNAVOIDABLE).add(var6);
		ResourceKey[] var7 = new ResourceKey[]{ModDamageTypes.FROSTBITES,ModDamageTypes.FROZEN};
		this.tag(DamageTypeTags.IS_FREEZING).add(var7);
		this.tag(DamageTypeTags.IS_FIRE).add(ModDamageTypes.SUN_INCINERATION);
		this.tag(DamageTypeTags.IS_PROJECTILE).add(ModDamageTypes.PROJECTILE);
	}
}