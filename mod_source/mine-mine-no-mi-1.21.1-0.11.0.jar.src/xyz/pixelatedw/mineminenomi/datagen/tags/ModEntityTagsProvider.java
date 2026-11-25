package xyz.pixelatedw.mineminenomi.datagen.tags;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import xyz.pixelatedw.mineminenomi.init.ModEntities;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.init.ModTags$Entities;

public class ModEntityTagsProvider extends EntityTypeTagsProvider {
	public ModEntityTagsProvider(PackOutput var1, CompletableFuture<HolderLookup.Provider> var2, ExistingFileHelper var3) {
		super(var1,var2,"mineminenomi",var3);
	}
	protected void addTags(HolderLookup.Provider var1) {
		EntityType[] var2 = new EntityType[]{((EntityType)(ModMobs.BANANAWANI.get())),((EntityType)(ModMobs.YAGARA_BULL.get())),((EntityType)(ModMobs.PANDA_SHARK.get())),((EntityType)(ModMobs.FIGHTING_FISH.get())),((EntityType)(ModMobs.FLYING_FISH.get()))};
		this.tag(EntityTypeTags.CAN_BREATHE_UNDER_WATER).add(var2);
		EntityType[] var3 = new EntityType[]{((EntityType)(ModEntities.KAIROSEKI_NET.get())),((EntityType)(ModProjectiles.KAIROSEKI_BULLET.get()))};
		this.tag(ModTags$Entities.KAIROSEKI).add(var3);
		EntityType[] var4 = new EntityType[]{EntityType.IRON_GOLEM,((EntityType)(ModMobs.PACIFISTA.get())),((EntityType)(ModProjectiles.CANNON_BALL.get())),((EntityType)(ModProjectiles.NORMAL_BULLET.get()))};
		this.tag(ModTags$Entities.MAGNETIC).add(var4);
	}
}