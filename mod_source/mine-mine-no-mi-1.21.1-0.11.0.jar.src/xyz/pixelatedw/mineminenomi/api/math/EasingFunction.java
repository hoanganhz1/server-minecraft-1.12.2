package xyz.pixelatedw.mineminenomi.api.math;

import com.mojang.serialization.Codec;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.util.StringRepresentable;

public enum EasingFunction implements StringRepresentable {
	// Fields
	SINE_IN((Function)(((Function<Float, Float>)(EasingFunctionHelper::easeInSine)))),
	QUAD_IN((Function)(((Function<Float, Float>)(EasingFunctionHelper::easeInQuad)))),
	CUBIC_IN((Function)(((Function<Float, Float>)(EasingFunctionHelper::easeInCubic)))),
	ELASTIC_IN((Function)(((Function<Float, Float>)(EasingFunctionHelper::easeInElastic)))),
	BACK_IN((Function)(((Function<Float, Float>)(EasingFunctionHelper::easeInBack)))),
	BOUNCE_IN((Function)(((Function<Float, Float>)(EasingFunctionHelper::easeInBounce)))),
	SINE_OUT((Function)(((Function<Float, Float>)(EasingFunctionHelper::easeOutSine)))),
	QUAD_OUT((Function)(((Function<Float, Float>)(EasingFunctionHelper::easeOutQuad)))),
	CUBIC_OUT((Function)(((Function<Float, Float>)(EasingFunctionHelper::easeOutCubic)))),
	ELASTIC_OUT((Function)(((Function<Float, Float>)(EasingFunctionHelper::easeOutElastic)))),
	BACK_OUT((Function)(((Function<Float, Float>)(EasingFunctionHelper::easeOutBack)))),
	BOUNCE_OUT((Function)(((Function<Float, Float>)(EasingFunctionHelper::easeOutBounce)))),
	SINE_IN_OUT((Function)(((Function<Float, Float>)(EasingFunctionHelper::easeInOutSine)))),
	QUAD_IN_OUT((Function)(((Function<Float, Float>)(EasingFunctionHelper::easeInOutQuad)))),
	CUBIC_IN_OUT((Function)(((Function<Float, Float>)(EasingFunctionHelper::easeInOutCubic)))),
	ELASTIC_IN_OUT((Function)(((Function<Float, Float>)(EasingFunctionHelper::easeInOutElastic)))),
	BACK_IN_OUT((Function)(((Function<Float, Float>)(EasingFunctionHelper::easeInOutBack)))),
	BOUNCE_IN_OUT((Function)(((Function<Float, Float>)(EasingFunctionHelper::easeInOutBounce))));
	public static final Codec<EasingFunction> CODEC = StringRepresentable.fromEnum((Supplier)(((Supplier<EasingFunction[]>)(EasingFunction::values))));
	private Function<Float, Float> func;

	private EasingFunction(Function<Float, Float> var3) {
		this.func = var3;
	}
	public float apply(float var1) {
		return ((Float)(((java.util.function.Function)(this.func)).apply((Object)(Float.valueOf(var1))))).floatValue();
	}
	public String getSerializedName() {
		return this.name();
	}
}