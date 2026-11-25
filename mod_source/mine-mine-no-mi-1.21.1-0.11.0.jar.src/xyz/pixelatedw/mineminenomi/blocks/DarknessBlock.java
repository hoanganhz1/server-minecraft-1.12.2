package xyz.pixelatedw.mineminenomi.blocks;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.abilities.yami.BlackHoleAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.damagesources.BaseDamageSource;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.DevilFruitData;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModDamageSources;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModFruits;

public class DarknessBlock extends TrapAbilityBlock {
	public DarknessBlock() {
		super(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).noLootTable().strength(-1F,3.6e+06F));
		this.setDamageDealt(6);
		this.setHorizontalFallSpeed(0.03D);
		this.setVerticalFallSpeed(0.3D);
		Function<Level, DamageSource> var1 = (l0) -> {
			return ModDamageSources.getInstance((LevelAccessor)(l0)).gravity();
		};
		this.setDamageSource(var1);
		Supplier<MobEffectInstance> var2 = () -> {
			return new MobEffectInstance((Holder)(ModEffects.MOVEMENT_BLOCKED),2,0,false,false,false);
		};
		this.setPotionEffect(var2);
	}
	public boolean canWalkOn(LivingEntity var1) {
		Function<DevilFruitData, Boolean> var2 = (l0) -> {
			return Boolean.valueOf(l0.hasDevilFruit(ModFruits.YAMI_YAMI_NO_MI));
		};
		boolean var3 = ((Boolean)(((java.util.Optional)(DevilFruitData.get(var1))).map((Function)(var2)).orElse(Boolean.valueOf(false)))).booleanValue();
		Function<AbilityData, Boolean> var4 = (l0) -> {
			return Boolean.valueOf(l0.hasUnlockedAbility(((AbilityCore)(BlackHoleAbility.INSTANCE.get()))));
		};
		boolean var5 = ((Boolean)(((java.util.Optional)(AbilityData.get(var1))).map((Function)(var4)).orElse(Boolean.valueOf(false)))).booleanValue();
		return (var3) || (var5);
	}
	public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
		return super.updateShape(var1,var2,var3,var4,var5,var6);
	}
	public boolean canSurvive(BlockState var1, LevelReader var2, BlockPos var3) {
		if ((var2.getBlockState(var3.below()).getBlock()) == (ModBlocks.DARKNESS.get())){
			return true;
		}else{
			return Block.canSupportCenter(var2,var3.below(),Direction.DOWN);
		}
	}
}