package xyz.pixelatedw.mineminenomi.abilities.suna;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.abilities.LogiaBlockBypassingAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModTags$Blocks;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class SunaTravelAbility extends LogiaBlockBypassingAbility {
	// Fields
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<SunaTravelAbility>> INSTANCE;

	public SunaTravelAbility(AbilityCore<SunaTravelAbility> var1) {
		super((AbilityCore)(var1));
	}
	public void spawnParticles(LivingEntity var1) {
		WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.SAND_BLADE_IDLE.get())),(Entity)(var1),var1.getX(),(double)(var1.level().getHeight(Heightmap.Types.OCEAN_FLOOR,(int)(var1.getX()),(int)(var1.getZ()))),var1.getZ());
	}
	public boolean canGoThrough(BlockState var1) {
		return var1.is(ModTags$Blocks.LOGIA_BLOCK_PASS_SUNA);
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,AbilityType.PASSIVE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(SunaTravelAbility::new)))).addDescriptionLine(DESCRIPTION).setIcon(ResourceLocation.fromNamespaceAndPath("minecraft","textures/block/sand.png")).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("suna_block_bypassing","Suna Block Bypassing",var0));
	}
}