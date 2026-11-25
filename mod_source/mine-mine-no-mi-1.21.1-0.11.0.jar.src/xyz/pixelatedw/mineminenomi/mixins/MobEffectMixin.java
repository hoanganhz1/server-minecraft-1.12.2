package xyz.pixelatedw.mineminenomi.mixins;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.common.NeoForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.pixelatedw.mineminenomi.api.events.AttributeModifierApplyEvent;
import xyz.pixelatedw.mineminenomi.api.events.AttributeModifierRemoveEvent;

@Mixin(value={MobEffect.class})
public class MobEffectMixin {
	@Inject(method={"addAttributeModifiers"}, at={@At(value="INVOKE", target="Lnet/minecraft/world/entity/ai/attributes/AttributeInstance;addPermanentModifier(Lnet/minecraft/world/entity/ai/attributes/AttributeModifier;)V", shift=At.Shift.AFTER)})
	public void mineminenomi$applyAttributesModifiersToEntity(AttributeMap var1, int var2, CallbackInfo var3) {
		NeoForge.EVENT_BUS.post((Event)(new AttributeModifierApplyEvent(var1,var2)));
	}
	@Inject(method={"removeAttributeModifiers"}, at={@At(value="INVOKE", target="Lnet/minecraft/world/entity/ai/attributes/AttributeInstance;removeModifier(Lnet/minecraft/world/entity/ai/attributes/AttributeModifier;)V", shift=At.Shift.AFTER)})
	public void mineminenomi$removeAttributesModifiersFromEntity(AttributeMap var1, int var2, CallbackInfo var3) {
		NeoForge.EVENT_BUS.post((Event)(new AttributeModifierRemoveEvent(var1,var2)));
	}
}