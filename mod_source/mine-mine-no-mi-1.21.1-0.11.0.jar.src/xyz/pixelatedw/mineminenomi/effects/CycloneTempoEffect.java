package xyz.pixelatedw.mineminenomi.effects;

import java.awt.Color;
import java.util.Iterator;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AttributeHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;

public class CycloneTempoEffect extends BaseEffect {
	public CycloneTempoEffect() {
		super(MobEffectCategory.BENEFICIAL,WyHelper.hexToRGB("#000000").getRGB());
		this.addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE,AttributeHelper.EFFECT_KNOCKBACK_RESISTANCE_ID,1D,AttributeModifier.Operation.ADD_VALUE);
	}
	public boolean shouldApplyEffectTickThisTick(int var1, int var2) {
		return ((var1) % (10)) == (0);
	}
	public boolean applyEffectTick(LivingEntity var1, int var2) {
		List var3 = TargetHelper.getEntitiesInArea((LevelAccessor)(var1.level()),var1,6D,TargetPredicate.DEFAULT_AREA_CHECK,LivingEntity.class);
		Vec3 var4 = var1.position();
		Iterator var5 = var3.iterator();
		do{
			if (var5.hasNext()){
				LivingEntity var6 = ((LivingEntity)(var5.next()));
				Vec3 var7 = var6.position().subtract(var4).normalize().multiply(2D,1D,2D);
				AbilityHelper.setDeltaMovement((Entity)(var6),var7.x,0.4D,var7.z);
				continue;
			}else{
				break;
			}
		} while (true);
		return true;
	}
	public boolean isRemoveable() {
		return false;
	}
}