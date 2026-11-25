package xyz.pixelatedw.mineminenomi.api.math;

public class EasingFunctionHelper {
	public static float easeInOutQuad(Float var0) {
		return ((easeInQuad(var0)) * ((1F) - (var0.floatValue()))) + ((easeOutQuad(var0)) * (var0.floatValue()));
	}
	public static float easeOutQuad(Float var0) {
		return (float)((1D) - (Math.pow((double)((1F) - (var0.floatValue())),2D)));
	}
	public static float easeInQuad(Float var0) {
		return (var0.floatValue()) * (var0.floatValue());
	}
	public static float easeOutCubic(Float var0) {
		return (float)((1D) - (Math.pow((double)((1F) - (var0.floatValue())),3D)));
	}
	public static float easeInCubic(Float var0) {
		return ((var0.floatValue()) * (var0.floatValue())) * (var0.floatValue());
	}
	public static float easeInOutCubic(Float var0) {
		return (float)((((double)(var0.floatValue())) < (0.5D)) ? ((double)((((4F) * (var0.floatValue())) * (var0.floatValue())) * (var0.floatValue()))) : ((1D) - ((Math.pow((double)(((-2F) * (var0.floatValue())) + (2F)),3D)) / (2D))));
	}
	public static float easeOutQuart(Float var0) {
		return (float)((1D) - (Math.pow((double)((1F) - (var0.floatValue())),4D)));
	}
	public static float easeInQuart(Float var0) {
		return (((var0.floatValue()) * (var0.floatValue())) * (var0.floatValue())) * (var0.floatValue());
	}
	public static float easeInOutQuart(Float var0) {
		return (float)((((double)(var0.floatValue())) < (0.5D)) ? ((double)((((8F) * (var0.floatValue())) * (var0.floatValue())) * (var0.floatValue()))) : ((1D) - ((Math.pow((double)(((-2F) * (var0.floatValue())) + (2F)),4D)) / (2D))));
	}
	public static float easeOutQuint(Float var0) {
		return (float)((1D) - (Math.pow((double)((1F) - (var0.floatValue())),5D)));
	}
	public static float easeInQuint(Float var0) {
		return ((((var0.floatValue()) * (var0.floatValue())) * (var0.floatValue())) * (var0.floatValue())) * (var0.floatValue());
	}
	public static float easeInOutQuint(Float var0) {
		return (float)((((double)(var0.floatValue())) < (0.5D)) ? ((double)((((16F) * (var0.floatValue())) * (var0.floatValue())) * (var0.floatValue()))) : ((1D) - ((Math.pow((double)(((-2F) * (var0.floatValue())) + (2F)),5D)) / (2D))));
	}
	public static float easeOutSine(Float var0) {
		return (float)(Math.sin((((double)(var0.floatValue())) * (3.141592653589793D)) / (2D)));
	}
	public static float easeInSine(Float var0) {
		return (float)((1D) - (Math.cos((((double)(var0.floatValue())) * (3.141592653589793D)) / (2D))));
	}
	public static float easeInOutSine(Float var0) {
		return (float)((-((Math.cos((3.141592653589793D) * ((double)(var0.floatValue())))) - (1D))) / (2D));
	}
	public static float easeInBack(Float var0) {
		float var1 = 1.70158F;
		return (((((var1) + (1F)) * (var0.floatValue())) * (var0.floatValue())) * (var0.floatValue())) - (((var1) * (var0.floatValue())) * (var0.floatValue()));
	}
	public static float easeOutBack(Float var0) {
		float var1 = 1.70158F;
		return (float)(((1D) + (((double)((var1) + (1F))) * (Math.pow((double)((var0.floatValue()) - (1F)),3D)))) + (((double)(var1)) * (Math.pow((double)((var0.floatValue()) - (1F)),2D))));
	}
	public static float easeInOutBack(Float var0) {
		float var1 = (1.70158F) + (1.525F);
		return (float)((((double)(var0.floatValue())) < (0.5D)) ? (((Math.pow((double)((2F) * (var0.floatValue())),2D)) * ((double)(((((var1) + (1F)) * (2F)) * (var0.floatValue())) - (var1)))) / (2D)) : ((((Math.pow((double)(((2F) * (var0.floatValue())) - (2F)),2D)) * ((double)((((var1) + (1F)) * (((var0.floatValue()) * (2F)) - (2F))) + (var1)))) + (2D)) / (2D)));
	}
	public static float easeInElastic(Float var0) {
		float var1 = 2.0943952F;
		return (float)(((var0.floatValue()) == (0F)) ? (0D) : (((var0.floatValue()) == (1F)) ? (1D) : ((-(Math.pow(2D,(double)(((10F) * (var0.floatValue())) - (10F))))) * (Math.sin((((double)((var0.floatValue()) * (10F))) - (10.75D)) * ((double)(var1)))))));
	}
	public static float easeOutElastic(Float var0) {
		float var1 = 2.0943952F;
		return (float)(((var0.floatValue()) == (0F)) ? (0D) : (((var0.floatValue()) == (1F)) ? (1D) : (((Math.pow(2D,(double)((-10F) * (var0.floatValue())))) * (Math.sin((((double)((var0.floatValue()) * (10F))) - (0.75D)) * ((double)(var1))))) + (1D))));
	}
	public static float easeInOutElastic(Float var0) {
		float var1 = 1.3962634F;
		return (float)(((var0.floatValue()) == (0F)) ? (0D) : (((var0.floatValue()) == (1F)) ? (1D) : ((((double)(var0.floatValue())) < (0.5D)) ? ((-((Math.pow(2D,(double)(((20F) * (var0.floatValue())) - (10F)))) * (Math.sin((((double)((20F) * (var0.floatValue()))) - (11.125D)) * ((double)(var1)))))) / (2D)) : ((((Math.pow(2D,(double)(((-20F) * (var0.floatValue())) + (10F)))) * (Math.sin((((double)((20F) * (var0.floatValue()))) - (11.125D)) * ((double)(var1))))) / (2D)) + (1D)))));
	}
	public static float easeInBounce(Float var0) {
		return easeOutBounce(Float.valueOf((1F) - (var0.floatValue())));
	}
	public static float easeOutBounce(Float var0) {
		float var1 = 7.5625F;
		float var2 = 2.75F;
		if ((var0.floatValue()) < ((1F) / (var2))){
			return ((var1) * (var0.floatValue())) * (var0.floatValue());
		}else{
			if ((var0.floatValue()) < ((2F) / (var2))){
				return (float)(((double)(((var1) * ((var0 = Float.valueOf((var0.floatValue()) - ((1.5F) / (var2)))).floatValue())) * (var0.floatValue()))) + (0.75D));
			}else{
				if (((double)(var0.floatValue())) < ((2.5D) / ((double)(var2)))){
					return (float)(((double)(((var1) * ((var0 = Float.valueOf((var0.floatValue()) - ((2.625F) / (var2)))).floatValue())) * (var0.floatValue()))) + (0.984375D));
				}else{
					return (float)(((double)(((var1) * ((var0 = Float.valueOf((var0.floatValue()) - ((2.25F) / (var2)))).floatValue())) * (var0.floatValue()))) + (0.9375D));
				}
			}
		}
	}
	public static float easeInOutBounce(Float var0) {
		return (((double)(var0.floatValue())) < (0.5D)) ? (((1F) - (easeOutBounce(Float.valueOf((1F) - ((2F) * (var0.floatValue())))))) / (2F)) : (((1F) + (easeOutBounce(Float.valueOf(((2F) * (var0.floatValue())) - (1F))))) / (2F));
	}
	public static float easeAbsoluteSine(Float var0) {
		return (float)(Math.abs(Math.sin((double)(var0.floatValue()))));
	}
}