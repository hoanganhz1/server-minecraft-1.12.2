package xyz.pixelatedw.mineminenomi.abilities.yuki;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine$IDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.blockgen.BlockGenerators;
import xyz.pixelatedw.mineminenomi.api.blockgen.IBlockGenerator;
import xyz.pixelatedw.mineminenomi.api.blockgen.SimpleBlockPlacer;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule$Builder;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModTags$Blocks;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$Details;
import xyz.pixelatedw.mineminenomi.particles.effects.yuki.KamakuraParticleEffect$Details;

public class KamakuraAbility extends Ability {
	// Fields
	private static final int COOLDOWN = 160;
	public static final BlockProtectionRule GRIEF_RULE;
	private static final KamakuraParticleEffect$Details DETAILS;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<KamakuraAbility>> INSTANCE;

	public KamakuraAbility(AbilityCore<KamakuraAbility> var1) {
		super((AbilityCore)(var1));
		super.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::onUseEvent))));
	}
	private void onUseEvent(LivingEntity var1, IAbility var2) {
		Vec3 var3 = (var1.isCrouching()) ? (var1.position()) : ((Vec3)(WyHelper.rayTraceBlocksAndEntities((Entity)(var1),64D).getLocation()));
		new SimpleBlockPlacer().setBlock(((Block)(ModBlocks.HARDENED_SNOW.get())).defaultBlockState()).setSize(4).setHollow().setRule(GRIEF_RULE).generate(var1.level(),BlockPos.containing((Position)(var3)),BlockGenerators.SPHERE);
		WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.KAMAKURA.get())),(Entity)(var1),var3.x,var3.y,var3.z,(ParticleEffect$Details)(DETAILS));
		this.cooldownComponent.startCooldown(var1,160F);
	}
	static  {
		BlockProtectionRule[] var0 = new BlockProtectionRule[]{DefaultProtectionRules.AIR_FOLIAGE_LIQUID};
		TagKey[] var1 = new TagKey[]{ModTags$Blocks.BLOCK_PROT_SNOW};
		GRIEF_RULE = new BlockProtectionRule$Builder(var0).addApprovedTags(var1).build();
		DETAILS = new KamakuraParticleEffect$Details(4);
		AbilityRegistryEntry var2 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Creates an igloo-like snow barrier where the user's pointing."),(Object)(null)),ImmutablePair.of((Object)("If used while crouching it'll create the igloo around the user."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(160F)};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(KamakuraAbility::new)))).addDescriptionLine(lv1_3).addAdvancedDescriptionLine(lv1_4).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("kamakura","Kamakura",var2));
	}
}