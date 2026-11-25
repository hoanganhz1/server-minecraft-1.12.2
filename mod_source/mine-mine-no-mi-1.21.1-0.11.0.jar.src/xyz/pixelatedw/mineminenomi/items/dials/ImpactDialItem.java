package xyz.pixelatedw.mineminenomi.items.dials;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler$NuDamageValue;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect$Details;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$Details;

public class ImpactDialItem extends BlockItem {
	public ImpactDialItem(Block var1) {
		super(var1,new Item.Properties().stacksTo(16));
	}
	public InteractionResultHolder<ItemStack> use(Level var1, Player var2, InteractionHand var3) {
		ItemStack var4 = var2.getItemInHand(var3);
		if (!(var1.isClientSide)){
			var2.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE,2,4));
			AbilityExplosion var5 = new AbilityExplosion((Entity)(var2),(IAbility)(null),(double)(var2.blockPosition().getX()),(double)(var2.blockPosition().getY()),(double)(var2.blockPosition().getZ()),3F);
			IDamageSourceHandler var6 = IDamageSourceHandler.getHandler(var5.getDamageSource());
			var6.addDamage(0F,new IDamageSourceHandler$NuDamageValue((AbilityCore)(null),SourceElement.SHOCKWAVE,SourceHakiNature.UNKNOWN,new SourceType[]{SourceType.TECHNOLOGY}));
			var5.setStaticDamage(5F);
			var5.setSmokeParticles(((ParticleEffect)(ModParticleEffects.COMMON_EXPLOSION.get())),(ParticleEffect$Details)(CommonExplosionParticleEffect.EXPLOSION2));
			var5.explode();
			var2.getCooldowns().addCooldown((Item)(this),10);
			var4.shrink(1);
		}
		return InteractionResultHolder.success(var4);
	}
}