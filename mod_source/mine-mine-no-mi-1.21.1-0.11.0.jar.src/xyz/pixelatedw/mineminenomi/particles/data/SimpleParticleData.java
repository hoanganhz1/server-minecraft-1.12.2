package xyz.pixelatedw.mineminenomi.particles.data;

import com.google.common.base.Strings;
import com.mojang.datafixers.Products;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.datafixers.util.Function15;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.awt.Color;
import java.util.function.Function;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.codec.StreamDecoder;
import net.minecraft.network.codec.StreamEncoder;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.math.EasingDirection;
import xyz.pixelatedw.mineminenomi.api.math.EasingFunction;

public class SimpleParticleData extends ParticleType<SimpleParticleData> implements ParticleOptions {
	// Fields
	private int color;
	private Vec3 motion;
	private Vec3 rotation;
	private float size;
	private float rotSpeed;
	private int life;
	private boolean hasMotionDecay;
	private boolean hasScaleDecay;
	private boolean faceCamera;
	private float yaw;
	private float pitch;
	private int animSpeed;
	private String function;
	private int easeDirection;
	private float easeStrength;
	protected ParticleType<SimpleParticleData> type;

	public static MapCodec<SimpleParticleData> createMapCodec(ParticleType<SimpleParticleData> var0) {
		ParticleType<SimpleParticleData> var1 = var0;
		final ParticleType<SimpleParticleData> var1_f1 = var1;
		Function<RecordCodecBuilder.Instance, App> var2 = (l0) -> {
			Function<SimpleParticleData, Integer> lv17_2 = (l2_0) -> {
				return Integer.valueOf(l2_0.color);
			};
			Function<SimpleParticleData, Float> lv17_3 = (l2_0) -> {
				return Float.valueOf(l2_0.size);
			};
			Function<SimpleParticleData, Float> lv17_4 = (l2_0) -> {
				return Float.valueOf(l2_0.rotSpeed);
			};
			Function<SimpleParticleData, Integer> lv17_5 = (l2_0) -> {
				return Integer.valueOf(l2_0.life);
			};
			Function<SimpleParticleData, Float> lv17_6 = (l2_0) -> {
				return Float.valueOf(l2_0.yaw);
			};
			Function<SimpleParticleData, Float> lv17_7 = (l2_0) -> {
				return Float.valueOf(l2_0.pitch);
			};
			Function<SimpleParticleData, Boolean> lv17_8 = (l2_0) -> {
				return Boolean.valueOf(l2_0.hasMotionDecay);
			};
			Function<SimpleParticleData, Boolean> lv17_9 = (l2_0) -> {
				return Boolean.valueOf(l2_0.hasScaleDecay);
			};
			Function<SimpleParticleData, Boolean> lv17_10 = (l2_0) -> {
				return Boolean.valueOf(l2_0.faceCamera);
			};
			Function<SimpleParticleData, Vec3> lv17_11 = (l2_0) -> {
				return l2_0.motion;
			};
			Function<SimpleParticleData, Vec3> lv17_12 = (l2_0) -> {
				return l2_0.rotation;
			};
			Function<SimpleParticleData, Integer> lv17_13 = (l2_0) -> {
				return Integer.valueOf(l2_0.animSpeed);
			};
			Function<SimpleParticleData, EasingFunction> lv17_14 = (l2_0) -> {
				return EasingFunction.valueOf(l2_0.function);
			};
			Function<SimpleParticleData, Integer> lv17_15 = (l2_0) -> {
				return Integer.valueOf(l2_0.easeDirection);
			};
			Function<SimpleParticleData, Float> lv17_16 = (l2_0) -> {
				return Float.valueOf(l2_0.easeStrength);
			};
			ParticleType<SimpleParticleData> lv17_17 = var1_f1;
			final ParticleType<SimpleParticleData> lv17_17_f1 = lv17_17;
			Function15 lv17_18 = (l2_0$sam, l2_1$sam, l2_2$sam, l2_3$sam, l2_4$sam, l2_5$sam, l2_6$sam, l2_7$sam, l2_8$sam, l2_9$sam, l2_10$sam, l2_11$sam, l2_12$sam, l2_13$sam, l2_14$sam) -> {
				Integer l2_0 = ((Integer)(l2_0$sam));
				Float l2_1 = ((Float)(l2_1$sam));
				Float l2_2 = ((Float)(l2_2$sam));
				Integer l2_3 = ((Integer)(l2_3$sam));
				Float l2_4 = ((Float)(l2_4$sam));
				Float l2_5 = ((Float)(l2_5$sam));
				Boolean l2_6 = ((Boolean)(l2_6$sam));
				Boolean l2_7 = ((Boolean)(l2_7$sam));
				Boolean l2_8 = ((Boolean)(l2_8$sam));
				Vec3 l2_9 = ((Vec3)(l2_9$sam));
				Vec3 l2_10 = ((Vec3)(l2_10$sam));
				Integer l2_11 = ((Integer)(l2_11$sam));
				EasingFunction l2_12 = ((EasingFunction)(l2_12$sam));
				Integer l2_13 = ((Integer)(l2_13$sam));
				Float l2_14 = ((Float)(l2_14$sam));

				SimpleParticleData lv16_16 = new SimpleParticleData(lv17_17_f1);
				lv16_16.setColor(l2_0.intValue());
				lv16_16.setMotion(l2_9);
				lv16_16.setRotation(l2_10);
				lv16_16.setSize(l2_1.floatValue());
				lv16_16.setRotationSpeed(l2_2.floatValue());
				lv16_16.setLife(l2_3.intValue());
				lv16_16.setLookVec(l2_4.floatValue(),l2_5.floatValue());
				lv16_16.setHasMotionDecay(l2_6.booleanValue());
				lv16_16.setHasScaleDecay(l2_7.booleanValue());
				if (l2_8.booleanValue()){
					lv16_16.faceCamera();
				}
				lv16_16.setAnimationSpeed(l2_11.intValue());
				lv16_16.setFunction(l2_12);
				lv16_16.setEaseDirection(l2_13.intValue());
				lv16_16.setEaseStrength(l2_14.floatValue());
				return lv16_16;
			};
			return l0.group((App)(((com.mojang.serialization.MapCodec)(Codec.INT.fieldOf("color"))).forGetter((Function)(lv17_2))),(App)(((com.mojang.serialization.MapCodec)(Codec.FLOAT.fieldOf("size"))).forGetter((Function)(lv17_3))),(App)(((com.mojang.serialization.MapCodec)(Codec.FLOAT.fieldOf("rotSpeed"))).forGetter((Function)(lv17_4))),(App)(((com.mojang.serialization.MapCodec)(Codec.INT.fieldOf("life"))).forGetter((Function)(lv17_5))),(App)(((com.mojang.serialization.MapCodec)(Codec.FLOAT.fieldOf("yaw"))).forGetter((Function)(lv17_6))),(App)(((com.mojang.serialization.MapCodec)(Codec.FLOAT.fieldOf("pitch"))).forGetter((Function)(lv17_7))),(App)(((com.mojang.serialization.MapCodec)(Codec.BOOL.fieldOf("hasMotionDecay"))).forGetter((Function)(lv17_8))),(App)(((com.mojang.serialization.MapCodec)(Codec.BOOL.fieldOf("hasScaleDecay"))).forGetter((Function)(lv17_9))),(App)(((com.mojang.serialization.MapCodec)(Codec.BOOL.fieldOf("faceCamera"))).forGetter((Function)(lv17_10))),(App)(((com.mojang.serialization.MapCodec)(Vec3.CODEC.fieldOf("motion"))).forGetter((Function)(lv17_11))),(App)(((com.mojang.serialization.MapCodec)(Vec3.CODEC.fieldOf("rot"))).forGetter((Function)(lv17_12))),(App)(((com.mojang.serialization.MapCodec)(Codec.INT.fieldOf("animSpeed"))).forGetter((Function)(lv17_13))),(App)(((com.mojang.serialization.MapCodec)(EasingFunction.CODEC.fieldOf("easingFunction"))).forGetter((Function)(lv17_14))),(App)(((com.mojang.serialization.MapCodec)(Codec.INT.fieldOf("easeDir"))).forGetter((Function)(lv17_15))),(App)(((com.mojang.serialization.MapCodec)(Codec.FLOAT.fieldOf("easeStrength"))).forGetter((Function)(lv17_16)))).apply((Applicative)(l0),lv17_18);
		};
		return (MapCodec<SimpleParticleData>) (MapCodec) (RecordCodecBuilder.mapCodec((Function)(var2)));
	}
	public static StreamCodec<? super RegistryFriendlyByteBuf, SimpleParticleData> createStreamCodec(ParticleType<SimpleParticleData> var0) {
		StreamEncoder var1 = (l0$sam, l1$sam) -> {
			RegistryFriendlyByteBuf l0 = ((RegistryFriendlyByteBuf)(l0$sam));
			SimpleParticleData l1 = ((SimpleParticleData)(l1$sam));

			l0.writeInt(l1.color);
			l0.writeFloat(l1.size);
			l0.writeFloat(l1.rotSpeed);
			l0.writeInt(l1.life);
			l0.writeFloat(l1.yaw);
			l0.writeFloat(l1.pitch);
			l0.writeBoolean(l1.hasMotionDecay);
			l0.writeBoolean(l1.hasScaleDecay);
			l0.writeBoolean(l1.faceCamera);
			l0.writeVec3(l1.motion);
			l0.writeVec3(l1.rotation);
			l0.writeInt(l1.animSpeed);
			int lv18_2 = ((l1.function) != (null)) ? (l1.function.length()) : (0);
			l0.writeInt(lv18_2);
			if ((l1.function) != (null)){
				l0.writeUtf(l1.function,lv18_2);
			}
			l0.writeInt(l1.easeDirection);
			l0.writeFloat(l1.easeStrength);
		};
		ParticleType<SimpleParticleData> var2 = var0;
		final ParticleType<SimpleParticleData> var2_f1 = var2;
		StreamDecoder var3 = (l0$sam) -> {
			RegistryFriendlyByteBuf l0 = ((RegistryFriendlyByteBuf)(l0$sam));

			int lv19_2 = l0.readInt();
			float lv19_3 = l0.readFloat();
			float lv19_4 = l0.readFloat();
			int lv19_5 = l0.readInt();
			float lv19_6 = l0.readFloat();
			float lv19_7 = l0.readFloat();
			boolean lv19_8 = l0.readBoolean();
			boolean lv19_9 = l0.readBoolean();
			boolean lv19_10 = l0.readBoolean();
			Vec3 lv19_11 = l0.readVec3();
			Vec3 lv19_12 = l0.readVec3();
			int lv19_13 = l0.readInt();
			int lv19_14 = l0.readInt();
			String lv19_15 = ((lv19_14) > (0)) ? (l0.readUtf(lv19_14)) : (null);
			int lv19_16 = l0.readInt();
			float lv19_17 = l0.readFloat();
			SimpleParticleData lv19_18 = new SimpleParticleData(var2_f1);
			lv19_18.setColor(lv19_2);
			lv19_18.setMotion(lv19_11);
			lv19_18.setRotation(lv19_12);
			lv19_18.setSize(lv19_3);
			lv19_18.setRotationSpeed(lv19_4);
			lv19_18.setLife(lv19_5);
			lv19_18.setLookVec(lv19_6,lv19_7);
			lv19_18.setHasMotionDecay(lv19_8);
			lv19_18.setHasScaleDecay(lv19_9);
			if (lv19_10){
				lv19_18.faceCamera();
			}
			lv19_18.setAnimationSpeed(lv19_13);
			if ((lv19_15) != (null)){
				lv19_18.setFunction(lv19_15);
			}
			lv19_18.setEaseDirection(lv19_16);
			lv19_18.setEaseStrength(lv19_17);
			return lv19_18;
		};
		return StreamCodec.of(var1,var3);
	}
	public SimpleParticleData() {
		super(true);
		this.color = Color.WHITE.getRGB();
		this.motion = Vec3.ZERO;
		this.rotation = new Vec3(0D,0D,0D);
		this.size = 1F;
		this.rotSpeed = 0F;
		this.life = 10;
		this.hasMotionDecay = true;
		this.hasScaleDecay = true;
		this.faceCamera = false;
		this.animSpeed = 2;
		this.function = null;
		this.easeStrength = 1F;
		this.type = this;
	}
	public SimpleParticleData(ParticleType<SimpleParticleData> var1) {
		super(true);
		this.color = Color.WHITE.getRGB();
		this.motion = Vec3.ZERO;
		this.rotation = new Vec3(0D,0D,0D);
		this.size = 1F;
		this.rotSpeed = 0F;
		this.life = 10;
		this.hasMotionDecay = true;
		this.hasScaleDecay = true;
		this.faceCamera = false;
		this.animSpeed = 2;
		this.function = null;
		this.easeStrength = 1F;
		this.type = var1;
	}
	public SimpleParticleData(boolean var1) {
		super(var1);
		this.color = Color.WHITE.getRGB();
		this.motion = Vec3.ZERO;
		this.rotation = new Vec3(0D,0D,0D);
		this.size = 1F;
		this.rotSpeed = 0F;
		this.life = 10;
		this.hasMotionDecay = true;
		this.hasScaleDecay = true;
		this.faceCamera = false;
		this.animSpeed = 2;
		this.function = null;
		this.easeStrength = 1F;
		this.type = this;
	}
	public void setLookVec(float var1, float var2) {
		this.yaw = var1;
		this.pitch = var2;
		this.faceCamera = true;
	}
	public boolean faceCamera() {
		return this.faceCamera;
	}
	public float getYaw() {
		return this.yaw;
	}
	public float getPitch() {
		return this.pitch;
	}
	public SimpleParticleData setMotion(Vec3 var1) {
		this.motion = var1;
		return this;
	}
	public SimpleParticleData setMotion(double var1, double var2, double var3) {
		this.motion = new Vec3(var1,var2,var3);
		return this;
	}
	public SimpleParticleData setRotation(float var1, float var2, float var3) {
		return this.setRotation(new Vec3((double)(var1),(double)(var2),(double)(var3)));
	}
	public SimpleParticleData setRotation(Vec3 var1) {
		this.rotation = var1;
		this.rotSpeed = 0.3F;
		return this;
	}
	public void setAnimationSpeed(int var1) {
		this.animSpeed = var1;
	}
	public int getAnimationSpeed() {
		return this.animSpeed;
	}
	public void setEaseStrength(float var1) {
		this.easeStrength = var1;
	}
	public float getEaseStrength() {
		return this.easeStrength;
	}
	public void setEaseDirection(int var1) {
		this.easeDirection = var1;
	}
	public void setEaseDirection(EasingDirection var1) {
		this.easeDirection = var1.ordinal();
	}
	public EasingDirection getEaseDirecetion() {
		return EasingDirection.values()[this.easeDirection];
	}
	public void setFunction(String var1) {
		this.function = var1;
		this.setHasScaleDecay(false);
	}
	public void setFunction(EasingFunction var1) {
		this.setFunction(var1.name());
	}
	public EasingFunction getFunction() {
		if (Strings.isNullOrEmpty(this.function)){
			return null;
		}else{
			return EasingFunction.valueOf(this.function);
		}
	}
	public SimpleParticleData setColor(int var1) {
		this.color = var1;
		return this;
	}
	public SimpleParticleData setColor(float var1, float var2, float var3) {
		return this.setColor(var1,var2,var3,1F);
	}
	public SimpleParticleData setColor(float var1, float var2, float var3, float var4) {
		this.color = WyHelper.packColor(var1,var2,var3,var4);
		return this;
	}
	public SimpleParticleData setRotationSpeed(float var1) {
		this.rotSpeed = var1;
		return this;
	}
	public SimpleParticleData setSize(float var1) {
		this.size = var1;
		return this;
	}
	public SimpleParticleData setLife(int var1) {
		this.life = var1;
		return this;
	}
	public SimpleParticleData setHasMotionDecay(boolean var1) {
		this.hasMotionDecay = var1;
		return this;
	}
	public SimpleParticleData setHasScaleDecay(boolean var1) {
		this.hasScaleDecay = var1;
		return this;
	}
	public float getRed() {
		return WyHelper.unpackColorRed(this.color);
	}
	public float getGreen() {
		return WyHelper.unpackColorGreen(this.color);
	}
	public float getBlue() {
		return WyHelper.unpackColorBlue(this.color);
	}
	public float getAlpha() {
		return WyHelper.unpackColorAlpha(this.color);
	}
	public float getSize() {
		return this.size;
	}
	public float getRotationSpeed() {
		return this.rotSpeed;
	}
	public int getLife() {
		return this.life;
	}
	public double getDeltaMovementX() {
		return this.motion.x;
	}
	public double getDeltaMovementY() {
		return this.motion.y;
	}
	public double getDeltaMovementZ() {
		return this.motion.z;
	}
	public Vec3 getRotation() {
		return this.rotation;
	}
	public boolean hasMotionDecay() {
		return this.hasMotionDecay;
	}
	public boolean hasScaleDecay() {
		return this.hasScaleDecay;
	}
	public ParticleType<?> getType() {
		return (ParticleType<?>) (ParticleType) (this.type);
	}
	public MapCodec<SimpleParticleData> codec() {
		return createMapCodec(this.type);
	}
	public StreamCodec<? super RegistryFriendlyByteBuf, SimpleParticleData> streamCodec() {
		return createStreamCodec(this.type);
	}
}