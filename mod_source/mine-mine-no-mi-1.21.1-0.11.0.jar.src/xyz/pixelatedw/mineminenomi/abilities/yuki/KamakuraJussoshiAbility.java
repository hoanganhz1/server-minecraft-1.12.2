package xyz.pixelatedw.mineminenomi.abilities.yuki;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.network.chat.Component;
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
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$Details;
import xyz.pixelatedw.mineminenomi.particles.effects.yuki.KamakuraParticleEffect$Details;

public class KamakuraJussoshiAbility extends Ability {
	// Fields
	private static final int COOLDOWN = 240;
	private static final KamakuraParticleEffect$Details DETAILS = new KamakuraParticleEffect$Details(8);
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<KamakuraJussoshiAbility>> INSTANCE;

	public KamakuraJussoshiAbility(AbilityCore<KamakuraJussoshiAbility> var1) {
		super((AbilityCore)(var1));
		super.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::onUseEvent))));
	}
	private void onUseEvent(LivingEntity var1, IAbility var2) {
		Vec3 var3 = (var1.isCrouching()) ? (var1.position()) : ((Vec3)(WyHelper.rayTraceBlocksAndEntities((Entity)(var1),64D).getLocation()));
		BlockPos var4 = BlockPos.containing((Position)(var3));
		SimpleBlockPlacer var5 = new SimpleBlockPlacer().setBlock(((Block)(ModBlocks.HARDENED_SNOW.get())).defaultBlockState()).setSize(4).setHollow().setRule(KamakuraAbility.GRIEF_RULE);
		var5.generate(var1.level(),var4,BlockGenerators.SPHERE);
		SimpleBlockPlacer var6 = new SimpleBlockPlacer().setBlock(((Block)(ModBlocks.HARDENED_SNOW.get())).defaultBlockState()).setSize(6).setHollow().setRule(KamakuraAbility.GRIEF_RULE);
		var6.generate(var1.level(),var4,BlockGenerators.SPHERE);
		SimpleBlockPlacer var7 = new SimpleBlockPlacer().setBlock(((Block)(ModBlocks.HARDENED_SNOW.get())).defaultBlockState()).setSize(8).setHollow().setRule(KamakuraAbility.GRIEF_RULE);
		var7.generate(var1.level(),var4,BlockGenerators.SPHERE);
		WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.KAMAKURA.get())),(Entity)(var1),var3.x,var3.y,var3.z,(ParticleEffect$Details)(DETAILS));
		this.cooldownComponent.startCooldown(var1,240F);
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Like 'Kamakura', but creates a multi-layered snow barrier."),(Object)(null)),ImmutablePair.of((Object)("If used while crouching it'll create the igloo around the user."),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(240F)};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(KamakuraJussoshiAbility::new)))).addDescriptionLine(lv1_3).addAdvancedDescriptionLine(lv1_4).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("kamakura_jussoshi","Kamakura Jussoshi",var0));
	}
}