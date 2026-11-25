package xyz.pixelatedw.mineminenomi.datagen.tags;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModTags$CurioItems;

public class ModCurioItemTagsProvider extends ItemTagsProvider {
	public ModCurioItemTagsProvider(PackOutput var1, CompletableFuture<HolderLookup.Provider> var2, CompletableFuture<TagsProvider.TagLookup<Block>> var3, ExistingFileHelper var4) {
		super(var1,var2,var3,"curios",var4);
	}
	protected void addTags(HolderLookup.Provider var1) {
		Item[] var2 = new Item[]{((Item)(ModArmors.MARINE_CAPTAIN_CAPE.get())),((Item)(ModArmors.PIRATE_CAPTAIN_CAPE.get())),((Item)(ModArmors.COLA_BACKPACK.get())),((Item)(ModArmors.MEDIC_BAG.get())),((Item)(ModArmors.TOMOE_DRUMS.get())),((Item)(ModArmors.FLUFFY_CAPE.get())),((Item)(ModArmors.WHITE_WALKIE_COAT.get()))};
		this.tag(ModTags$CurioItems.BACK).add(var2);
		this.tag(ModTags$CurioItems.CHARM).add((Object)(((Item)(ModItems.COLOR_PALETTE.get()))));
		Item[] var3 = new Item[]{((Item)(ModArmors.FRANKY_GLASSES.get())),((Item)(ModArmors.CABAJI_SCARF.get())),((Item)(ModArmors.BIG_RED_NOSE.get())),((Item)(ModArmors.KURO_GLASSES.get())),((Item)(ModArmors.MR3_GLASSES.get())),((Item)(ModArmors.MR5_GLASSES.get())),((Item)(ModArmors.SNIPER_GOGGLES.get())),((Item)(ModArmors.MH5_GAS_MASK.get())),((Item)(ModArmors.KIZARU_GLASSES.get())),((Item)(ModArmors.DOFFY_GLASSES.get())),((Item)(ModArmors.HEART_GLASSES.get())),((Item)(ModItems.CIGAR.get())),((Item)(ModItems.CIGARETTE.get())),((Item)(ModItems.SMOKING_PIPE.get())),((Item)(ModItems.THREE_CIGARS.get())),((Item)(ModArmors.IRON_JAW.get())),((Item)(ModArmors.SOGEKING_MASK.get())),((Item)(ModArmors.WHOS_WHO_MASK.get())),((Item)(ModArmors.BASTILLE_MASK.get())),((Item)(ModArmors.KILLER_MASK.get()))};
		this.tag(ModTags$CurioItems.FACE).add(var3);
		Item[] var4 = new Item[]{((Item)(ModArmors.CELESTIAL_DRAGON_BUBBLE.get())),((Item)(ModArmors.BICORNE.get())),((Item)(ModArmors.CHOPPER_HAT.get())),((Item)(ModArmors.CHOPPER_HAT_2.get())),((Item)(ModArmors.LAW_HAT.get())),((Item)(ModArmors.TRICORNE.get())),((Item)(ModArmors.SABO_HAT.get())),((Item)(ModArmors.MIHAWK_HAT.get())),((Item)(ModArmors.FLEET_ADMIRAL_HAT.get())),((Item)(ModArmors.PLUME_HAT.get())),((Item)(ModArmors.WIDE_BRIM_HAT.get())),((Item)(ModArmors.STRAW_HAT.get())),((Item)(ModArmors.ACE_HAT.get())),((Item)(ModArmors.SOUL_KING_CROWN.get())),((Item)(ModArmors.WIZARD_HAT.get())),((Item)(ModArmors.BANDANA.get())),((Item)(ModArmors.BLUGORI_HOOD.get())),((Item)(ModArmors.MARINE_HEAD.get())),((Item)(ModArmors.ARLONG_HAT.get())),((Item)(ModArmors.SENOR_PINK_HEAD.get())),((Item)(ModArmors.PEARL_HAT.get()))};
		this.tag(ModTags$CurioItems.HEAD).add(var4);
	}
}