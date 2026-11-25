package xyz.pixelatedw.mineminenomi.blocks;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import xyz.pixelatedw.mineminenomi.abilities.suna.DesertGirasoleAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.DevilFruitData;
import xyz.pixelatedw.mineminenomi.init.ModFruits;

public class SunaSandBlock extends TrapAbilityBlock {
	public SunaSandBlock() {
		super(BlockBehaviour.Properties.of().mapColor(MapColor.SAND).noCollission().noLootTable().strength(5F,3F));
		this.setDamageDealt(4);
		this.setHorizontalFallSpeed(0.3D);
		this.setVerticalFallSpeed(0.15D);
		Supplier<MobEffectInstance> var1 = () -> {
			return new MobEffectInstance(MobEffects.DIG_SLOWDOWN,80,1,false,false,false);
		};
		this.setPotionEffect(var1);
	}
	public boolean canWalkOn(LivingEntity var1) {
		Function<DevilFruitData, Boolean> var2 = (l0) -> {
			return Boolean.valueOf(l0.hasDevilFruit(ModFruits.SUNA_SUNA_NO_MI));
		};
		boolean var3 = ((Boolean)(((java.util.Optional)(DevilFruitData.get(var1))).map((Function)(var2)).orElse(Boolean.valueOf(false)))).booleanValue();
		Function<AbilityData, Boolean> var4 = (l0) -> {
			return Boolean.valueOf(l0.hasUnlockedAbility(((AbilityCore)(DesertGirasoleAbility.INSTANCE.get()))));
		};
		boolean var5 = ((Boolean)(((java.util.Optional)(AbilityData.get(var1))).map((Function)(var4)).orElse(Boolean.valueOf(false)))).booleanValue();
		return (var3) || (var5);
	}
}