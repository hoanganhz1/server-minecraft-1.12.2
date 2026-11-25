package xyz.pixelatedw.mineminenomi.effects;

import java.awt.Color;
import java.util.function.Function;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import xyz.pixelatedw.mineminenomi.api.WyHelper;

public class HungerOverTimeEffect extends BaseEffect {
	// Fields
	private final float baseHunger;
	private final int freq;
	private Function<Integer, Float> hungerScaling;

	public HungerOverTimeEffect() {
		this(2F,20);
	}
	public HungerOverTimeEffect(float var1, int var2) {
		super(MobEffectCategory.HARMFUL,WyHelper.hexToRGB("#813d2d").getRGB());
		this.baseHunger = var1;
		this.freq = var2;
		HungerOverTimeEffect var3 = this;
		Function<Integer, Float> var4 = (Integer l0) -> {
			return Float.valueOf((this.baseHunger) * ((float)(l0.intValue())));
		};
		this.hungerScaling = var4;
	}
	public boolean shouldApplyEffectTickThisTick(int var1, int var2) {
		return ((var1) % (this.freq)) == (0);
	}
	public boolean applyEffectTick(LivingEntity var1, int var2) {
		if (var1 instanceof Player){
			Player var3 = ((Player)(var1));
			float var4 = Math.max(this.baseHunger,((Float)(((java.util.function.Function)(this.hungerScaling)).apply((Object)(Integer.valueOf(var2))))).floatValue());
			int var5 = (int)(((float)(var3.getFoodData().getFoodLevel())) - (var4));
			var5 = Math.max(var5,0);
			var3.getFoodData().setFoodLevel(var5);
		}
		return true;
	}
	public float getBaseHunger() {
		return this.baseHunger;
	}
	public void setHungerScaling(Function<Integer, Float> var1) {
		this.hungerScaling = var1;
	}
	public boolean isLingering() {
		return true;
	}
}