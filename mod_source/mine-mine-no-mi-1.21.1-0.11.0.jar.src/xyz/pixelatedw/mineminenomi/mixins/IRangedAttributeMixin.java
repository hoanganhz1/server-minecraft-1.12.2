package xyz.pixelatedw.mineminenomi.mixins;

import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={RangedAttribute.class}, priority=2000)
public interface IRangedAttributeMixin {
	@Accessor(value="minValue")		
@Mutable()
	public abstract void setMinValue(double var0);
	@Accessor(value="maxValue")		
@Mutable()
	public abstract void setMaxValue(double var0);
}