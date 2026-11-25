package xyz.pixelatedw.mineminenomi.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public class DemonPoisonBlock extends PoisonBlock {
	public void entityInside(BlockState var1, Level var2, BlockPos var3, Entity var4) {
		if (var4 instanceof LivingEntity){
			LivingEntity var5 = ((LivingEntity)(var4));
			if (!(var5.hasEffect((Holder)(ModEffects.DOKU_POISON)))){
				var5.addEffect(new MobEffectInstance((Holder)(ModEffects.DOKU_POISON),100,2));
			}else{
				return;
			}
		}
	}
}