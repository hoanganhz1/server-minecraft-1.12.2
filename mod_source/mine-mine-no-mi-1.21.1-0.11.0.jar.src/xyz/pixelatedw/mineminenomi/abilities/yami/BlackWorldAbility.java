package xyz.pixelatedw.mineminenomi.abilities.yami;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.blockgen.BlockGenerators;
import xyz.pixelatedw.mineminenomi.api.blockgen.IAfterBlockGenerationEvent;
import xyz.pixelatedw.mineminenomi.api.blockgen.IAfterPlaceBlockEvent;
import xyz.pixelatedw.mineminenomi.api.blockgen.IBlockGenerator;
import xyz.pixelatedw.mineminenomi.api.blockgen.SimpleBlockPlacer;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.SUpdateAbilityNBTPacket;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class BlackWorldAbility extends Ability {
	// Fields
	private static final int COOLDOWN = 200;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<BlackWorldAbility>> INSTANCE;

	public BlackWorldAbility(AbilityCore<BlackWorldAbility> var1) {
		super((AbilityCore)(var1));
		this.addComponents(new AbilityComponent[0]);
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv4_2 = new Pair[]{ImmutablePair.of((Object)("The user spreads their power to the surroundings, blinding enemies and creating pillars of Darkness."),(Object)(null))};
			Component[] lv4_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv4_2);
			AbilityDescriptionLine$IDescriptionLine[] lv4_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(200F)};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(BlackWorldAbility::new)))).addDescriptionLine(lv4_3).addAdvancedDescriptionLine(lv4_4).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("black_world","Black World",var0));
	}

	private void useEvent(LivingEntity var1, IAbility var2) {
		Level var3 = var1.level();
		if (((var3) != (null)) && (!(var3.isClientSide))){
			WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.BLACK_WORLD.get())),(Entity)(var1),var1.getX(),var1.getY(),var1.getZ());
			Function<AbilityData, AbsorbedBlocksAbility> var4 = (l0) -> {
			return ((AbsorbedBlocksAbility)(l0.getPassiveAbility(((AbilityCore)(AbsorbedBlocksAbility.INSTANCE.get())))));
		};
			AbsorbedBlocksAbility var5 = ((AbsorbedBlocksAbility)(((java.util.Optional)(AbilityData.get(var1))).map((Function)(var4)).orElse(null)));
			AbsorbedBlocksAbility var6 = var5;
			final AbsorbedBlocksAbility var6_f1 = var6;
			IAfterPlaceBlockEvent var7 = (l0, l1, l2) -> {
			if ((var6_f1) != (null)){
				var6_f1.addMarkedBlock(l1,l0);
			}
		};
			AbsorbedBlocksAbility var8 = var5;
			LivingEntity var9 = var1;
			final AbsorbedBlocksAbility var8_f2 = var8;
			final LivingEntity var9_f3 = var9;
			IAfterBlockGenerationEvent var10 = (l0, l1) -> {
			if (((var8_f2) != (null)) && (var9_f3 instanceof Player)){
				Player lv3_4 = ((Player)(var9_f3));
				ModNetwork.sendTo(new SUpdateAbilityNBTPacket((LivingEntity)(lv3_4),(IAbility)(var8_f2)),lv3_4);
			}
		};
			SimpleBlockPlacer var11 = new SimpleBlockPlacer().setBlock(((Block)(ModBlocks.DARKNESS.get())).defaultBlockState()).setRule(DefaultProtectionRules.AIR).setSize(1,5).setAfterPlaceEvent(var7).setAfterGenerationEvent(var10);
			int var12 = 0;
			do{
				if ((var12) < (16)){
					Vec3 var13 = Vec3.directionFromRotation(0F,(float)(var1.getRandom().nextInt(360))).normalize().scale((double)((10) + (var1.getRandom().nextInt(5))));
					var11.generate(var3,var1.blockPosition().offset((int)(var13.x),0,(int)(var13.z)),BlockGenerators.CUBE);
					var12++;
					continue;
				}else{
					break;
				}
			} while (true);
			Iterator var13_1 = TargetHelper.getEntitiesInArea((LevelAccessor)(var1.level()),var1,var1.position(),20D,TargetPredicate.DEFAULT_AREA_CHECK,LivingEntity.class).iterator();
			do{
				if (var13_1.hasNext()){
					LivingEntity var14 = ((LivingEntity)(var13_1.next()));
					var14.addEffect(new MobEffectInstance(MobEffects.BLINDNESS,200,1,false,false));
					var14.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,200,1,false,false));
					continue;
				}else{
					break;
				}
			} while (true);
			this.cooldownComponent.startCooldown(var1,200F);
			return;
		}else{
			return;
		}
	}}