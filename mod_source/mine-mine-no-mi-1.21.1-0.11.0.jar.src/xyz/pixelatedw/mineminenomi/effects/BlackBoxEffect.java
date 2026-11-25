package xyz.pixelatedw.mineminenomi.effects;

import it.unimi.dsi.fastutil.ints.Int2DoubleFunction;
import java.awt.Color;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.block.Block;
import xyz.pixelatedw.mineminenomi.api.effects.IBlockOverlayEffect;
import xyz.pixelatedw.mineminenomi.api.helpers.AttributeHelper;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;

public class BlackBoxEffect extends BaseEffect implements IBlockOverlayEffect {
	public BlackBoxEffect() {
		super(MobEffectCategory.HARMFUL,Color.WHITE.getRGB());
		Int2DoubleFunction var1 = (l0) -> {
			return 1D;
		};
		Int2DoubleFunction var2 = (l0) -> {
			return 1D;
		};
		Int2DoubleFunction var3 = (l0) -> {
			return -256D;
		};
		this.addAttributeModifier(Attributes.MOVEMENT_SPEED,AttributeHelper.EFFECT_MOVEMENT_SPEED_ID,AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL,var1).addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE,AttributeHelper.EFFECT_KNOCKBACK_RESISTANCE_ID,AttributeModifier.Operation.ADD_VALUE,var2).addAttributeModifier(ModAttributes.JUMP_HEIGHT,AttributeHelper.EFFECT_JUMP_HEIGHT_ID,AttributeModifier.Operation.ADD_VALUE,var3);
	}
	public boolean shouldUpdateClient() {
		return true;
	}
	public boolean isBlockingRotation() {
		return true;
	}
	public Block getBlockOverlay(int var1, int var2) {
		return ((Block)(ModBlocks.DARKNESS.get()));
	}
	public boolean isRemoveable() {
		return false;
	}
}