package xyz.pixelatedw.mineminenomi.particles;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import xyz.pixelatedw.mineminenomi.api.math.EasingDirection;
import xyz.pixelatedw.mineminenomi.api.math.EasingFunction;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;

public class SimpleParticle extends SingleQuadParticle {
	// Fields
	private boolean hasMotionDecay;
	private boolean hasScaleDecay;
	private Vec3 rotation;
	private float rotationSpeed;
	protected ParticleRenderType type;
	protected final float initialSize;
	private boolean faceCamera;
	private float yaw;
	private float pitch;
	private int animIdx;
	private int animSpeed;
	 int maxFrames;
	private EasingFunction function;
	private EasingDirection easeDir;
	private float easeStrength;

	public SimpleParticle(SimpleParticleData var1, ParticleRenderType var2, ClientLevel var3, double var4, double var5, double var6, double var7, double var8, double var9) {
		super(var3,var4,var5,var6,0D,0D,0D);
		this.hasMotionDecay = true;
		this.hasScaleDecay = true;
		this.animIdx = 0;
		this.animSpeed = 2;
		this.maxFrames = 8;
		this.easeStrength = 1F;
		this.lifetime = (30) + (this.random.nextInt(10));
		this.age = 0;
		this.quadSize = 0.2F;
		this.gravity = 0F;
		this.setColor(1F,1F,1F);
		this.hasPhysics = false;
		this.type = var2;
		this.xd = var7;
		this.yd = var8;
		this.zd = var9;
		this.setColor(var1.getRed(),var1.getGreen(),var1.getBlue());
		this.setRotation(var1.getRotation());
		this.setAlpha(var1.getAlpha());
		this.setParticleSize((var1.getSize()) / (10F));
		this.setParticleRotation(var1.getRotationSpeed());
		this.setParticleAge(var1.getLife());
		this.setHasMotionDecay(var1.hasMotionDecay());
		this.setHasScaleDecay(var1.hasScaleDecay());
		this.faceCamera = var1.faceCamera();
		this.yaw = var1.getYaw();
		this.pitch = var1.getPitch();
		this.animSpeed = var1.getAnimationSpeed();
		this.function = var1.getFunction();
		this.easeDir = var1.getEaseDirecetion();
		this.easeStrength = var1.getEaseStrength();
		this.initialSize = this.quadSize;
	}
	public void tick() {
		this.xo = this.x;
		this.yo = this.y;
		this.zo = this.z;
		if ((this.gravity) != (0F)){
			this.yd = (-0.04D) * ((double)(this.gravity));
		}
		if (((this.age) % (this.animSpeed)) == (0)){
			this.animIdx = (this.animIdx) + (1);
		}
		this.move(this.xd,this.yd,this.zd);
		if (this.hasMotionDecay){
			this.xd = (this.xd) * (0.99D);
			this.yd = (this.yd) * (0.99D);
			this.zd = (this.zd) * (0.99D);
		}
		if (this.hasScaleDecay){
			if (((this.age) >= ((this.lifetime) / (2))) && ((this.quadSize) > (0F))){
				this.quadSize = (this.quadSize) / (1.1F);
			}
			if ((((this.age) + (5)) >= (this.lifetime)) && ((this.alpha) > (0F))){
				this.alpha = (float)(((double)(this.alpha)) / (1.15D));
			}
		}else{
			if ((this.function) != (null)){
				float var1 = 0F;
				float var2 = Math.min(((float)(this.age)) / (((float)(this.lifetime)) * ((1F) - (var1))),1F);
				float var3 = (this.function.apply(var2)) * (this.easeStrength);
				float var4 = this.initialSize;
				switch (SimpleParticle$1.$SwitchMap$xyz$pixelatedw$mineminenomi$api$math$EasingDirection[this.easeDir.ordinal()]){
				case 1:
					var4 = (this.initialSize) + ((this.initialSize) * (var3));
					break;
				case 2:
					var4 = (this.initialSize) - ((this.initialSize) * (var3));
					break;
				case 3:
					var4 = (((double)(var2)) < (0.5D)) ? ((this.initialSize) + ((this.initialSize) * (var3))) : ((this.initialSize) - ((this.initialSize) * (var3)));
					break;
				}
				this.quadSize = var4;
			}
			if ((((this.age) + (5)) >= (this.lifetime)) && ((this.alpha) > (0F))){
				this.alpha = (float)(((double)(this.alpha)) / (1.15D));
			}
		}
		if ((this.rotationSpeed) != (0F)){
			this.roll = (this.roll) - (this.rotationSpeed);
		}
		if (((this.age++) < (this.lifetime)) && (!(this.onGround))){

		}else{
			this.remove();
		}
	}
	public void render(VertexConsumer var1, Camera var2, float var3) {
		Vec3 var4 = var2.getPosition();
		float var5 = (float)((Mth.lerp((double)(var3),this.xo,this.x)) - (var4.x()));
		float var6 = (float)((Mth.lerp((double)(var3),this.yo,this.y)) - (var4.y()));
		float var7 = (float)((Mth.lerp((double)(var3),this.zo,this.z)) - (var4.z()));
		Quaternionf var8 = new Quaternionf(0F,0F,0F,1F);
		if (this.faceCamera){
			Quaternionf var9 = var8.rotateY((float)(Math.toRadians((double)(-(this.pitch)))));
			var8 = var9.rotateX((float)(Math.toRadians((double)(this.yaw))));
		}else{
			if ((this.roll) == (0F)){
				var8 = var2.rotation();
			}else{
				Quaternionf var9_1 = new Quaternionf((Quaternionfc)(var2.rotation()));
				var8 = var9_1.rotateAxis(Mth.lerp(var3,this.oRoll,this.roll),(Vector3fc)(this.rotation.toVector3f()));
			}
		}
		new Vector3f(-1F,-1F,0F).rotate((Quaternionfc)(var8));
		Vector3f[] var9_2 = new Vector3f[]{new Vector3f(-1F,-1F,0F),new Vector3f(-1F,1F,0F),new Vector3f(1F,1F,0F),new Vector3f(1F,-1F,0F)};
		Vector3f[] var10 = var9_2;
		float var11 = this.getQuadSize(var3);
		int var12 = 0;
		do{
			if ((var12) < (4)){
				Vector3f var13 = var10[var12];
				var13.rotate((Quaternionfc)(var8));
				var13.mul(var11);
				var13.add(var5,var6,var7);
				var12++;
				continue;
			}else{
				break;
			}
		} while (true);
		float var13_1 = this.getU0();
		float var14 = this.getU1();
		float var15 = this.getV0();
		float var16 = this.getV1();
		int var17 = this.getLightColor(var3);
		var1.addVertex(var10[0].x(),var10[0].y(),var10[0].z()).setUv(var14,var16).setColor(this.rCol,this.gCol,this.bCol,this.alpha).setLight(var17);
		var1.addVertex(var10[1].x(),var10[1].y(),var10[1].z()).setUv(var14,var15).setColor(this.rCol,this.gCol,this.bCol,this.alpha).setLight(var17);
		var1.addVertex(var10[2].x(),var10[2].y(),var10[2].z()).setUv(var13_1,var15).setColor(this.rCol,this.gCol,this.bCol,this.alpha).setLight(var17);
		var1.addVertex(var10[3].x(),var10[3].y(),var10[3].z()).setUv(var13_1,var16).setColor(this.rCol,this.gCol,this.bCol,this.alpha).setLight(var17);
	}
	public void setMaxFrames(int var1) {
		this.maxFrames = var1;
	}
	public SimpleParticle setAlphaF(float var1) {
		this.setAlpha(var1);
		return this;
	}
	public SimpleParticle setParticleSize(float var1) {
		this.quadSize = var1;
		return this;
	}
	public SimpleParticle setParticleRotation(float var1) {
		this.rotationSpeed = var1;
		return this;
	}
	public SimpleParticle setParticleGravity(float var1) {
		this.gravity = var1;
		return this;
	}
	public SimpleParticle setParticleAge(int var1) {
		this.lifetime = var1;
		return this;
	}
	public SimpleParticle setHasMotionDecay(boolean var1) {
		this.hasMotionDecay = var1;
		return this;
	}
	public Vec3 getRotationVec() {
		return this.rotation;
	}
	public SimpleParticle setRotation(Vec3 var1) {
		this.rotation = var1;
		return this;
	}
	public SimpleParticle setHasScaleDecay(boolean var1) {
		this.hasScaleDecay = var1;
		return this;
	}
	public Vec3 getPos() {
		return new Vec3(this.x,this.y,this.z);
	}
	public ParticleRenderType getRenderType() {
		return this.type;
	}
	protected float getU0() {
		return 0F;
	}
	protected float getV0() {
		return ((float)(this.animIdx)) / ((float)(this.maxFrames));
	}
	protected float getU1() {
		return 1F;
	}
	protected float getV1() {
		return ((float)((this.animIdx) + (1))) / ((float)(this.maxFrames));
	}
}