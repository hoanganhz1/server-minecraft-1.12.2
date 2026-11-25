package xyz.pixelatedw.mineminenomi.entities;

import java.awt.Color;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.entity.IEntityWithComplexSpawn;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool;
import xyz.pixelatedw.mineminenomi.init.ModEntities;
import xyz.pixelatedw.mineminenomi.renderers.ArenaSkybox;

public class SphereEntity extends Entity implements IEntityWithComplexSpawn {
	// Fields
	private static final ResourceLocation DEFAULT_TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/skyboxes/default.png");
	private static final EntityDataAccessor<Float> RADIUS = SynchedEntityData.defineId(SphereEntity.class,EntityDataSerializers.FLOAT);
	@Nullable() private LivingEntity spawner;
	private Vec3 oldPos;
	private ResourceLocation[] textures;
	private Color color;
	private int animationSpeed;
	private int detailLevel;
	private boolean hasSpawner;
	private boolean isFullWrapping;
	private boolean move;
	@OnlyIn(value=Dist.CLIENT) private ArenaSkybox cachedSkybox;
	private final PriorityEventPool<SphereEntity$ITickEvent> tickEvents;

	public SphereEntity(EntityType<? extends SphereEntity> var1, Level var2) {
		super(var1,var2);
		ResourceLocation[] var3 = new ResourceLocation[]{DEFAULT_TEXTURE};
		this.textures = var3;
		this.color = Color.WHITE;
		this.animationSpeed = 1;
		this.detailLevel = 8;
		this.move = false;
		this.tickEvents = (PriorityEventPool) (new PriorityEventPool());
	}
	public SphereEntity(Level var1) {
		this(((EntityType)(ModEntities.SPHERE.get())),var1);
	}
	public SphereEntity(Level var1, LivingEntity var2) {
		this((EntityType<? extends SphereEntity>)(EntityType)(((EntityType)(ModEntities.SPHERE.get()))),var1);
		this.spawner = var2;
		this.hasSpawner = true;
		this.setPos(var2.getX(),var2.getY(),var2.getZ());
	}
	public void setTexture(boolean var1, ResourceLocation... var2) {
		this.textures = var2;
		this.isFullWrapping = var1;
	}
	public ResourceLocation[] getTexture() {
		return this.textures;
	}
	public boolean isFullWrapping() {
		return this.isFullWrapping;
	}
	public void setAnimationSpeed(int var1) {
		this.animationSpeed = var1;
	}
	public int getAnimationSpeed() {
		return this.animationSpeed;
	}
	public void setColor(Color var1) {
		this.color = var1;
	}
	public Color getColor() {
		return this.color;
	}
	public void setRadius(float var1) {
		this.getEntityData().set(RADIUS,Float.valueOf(var1));
	}
	public float getRadius() {
		return ((Float)(this.getEntityData().get(RADIUS))).floatValue();
	}
	public void setDetailLevel(int var1) {
		this.detailLevel = var1;
	}
	public int getDetailLevel() {
		return this.detailLevel;
	}
	public void addTickEvent(SphereEntity$ITickEvent var1) {
		this.tickEvents.addEvent(var1);
	}
	@OnlyIn(value=Dist.CLIENT)
	public ArenaSkybox getSkybox() {
		if ((this.cachedSkybox) == (null)){
			this.cachedSkybox = new ArenaSkybox();
			this.cachedSkybox.setTexture(this.isFullWrapping,this.textures);
			this.cachedSkybox.setAnimationSpeed(this.animationSpeed);
			this.cachedSkybox.setColor(this.color);
			this.cachedSkybox.setDetailLevel(this.detailLevel);
		}
		this.cachedSkybox.setRadius(this.getRadius());
		return this.cachedSkybox;
	}
	protected void defineSynchedData(SynchedEntityData.Builder var1) {
		var1.define(RADIUS,Float.valueOf(1F));
	}
	public void tick() {
		super.tick();
		if ((this.oldPos) == (null)){
			this.oldPos = this.position();
		}
		if (!(this.move)){
			if (!(this.position().equals(this.oldPos))){
				this.setPos(this.oldPos.x,this.oldPos.y,this.oldPos.z);
			}
		}else{
			this.oldPos = this.position();
			this.move = false;
		}
		if (((this.tickCount) % (20)) == (0)){
			if ((this.hasSpawner) && (!(((this.spawner) != (null)) && (this.spawner.isAlive())))){
				this.discard();
				return;
			}else{
				SphereEntity var1 = this;
				Consumer<SphereEntity$ITickEvent> var2 = (l0) -> {
			l0.tick(this);
		};
				((xyz.pixelatedw.mineminenomi.api.util.PriorityEventPool)(this.tickEvents)).dispatch((Consumer)(var2));
			}
		}
	}
	@OnlyIn(value=Dist.CLIENT)
	public boolean shouldRender(double var1, double var2, double var3) {
		return true;
	}
	protected void addAdditionalSaveData(CompoundTag var1) {
		var1.putBoolean("hasSpawner",this.hasSpawner);
		var1.putInt("textures",this.textures.length);
		int var2 = 0;
		do{
			if ((var2) < (this.textures.length)){
				int var3 = var2;
				String var4 = "texture" + var3;
				var1.putString(var4,this.textures[var2].toString());
				var2++;
				continue;
			}else{
				break;
			}
		} while (true);
		var1.putBoolean("isFullWrapping",this.isFullWrapping);
		var1.putInt("animationSpeed",this.animationSpeed);
		var1.putInt("rgb",this.color.getRGB());
		var1.putInt("alpha",this.color.getAlpha());
		var1.putFloat("radius",((Float)(this.getEntityData().get(RADIUS))).floatValue());
		var1.putInt("detailLevel",this.detailLevel);
		var1.putBoolean("move",this.move);
	}
	protected void readAdditionalSaveData(CompoundTag var1) {
		this.hasSpawner = var1.getBoolean("hasSpawner");
		int var2 = var1.getInt("textures");
		this.textures = new ResourceLocation[var2];
		int var3 = 0;
		do{
			if ((var3) < (var2)){
				int var4 = var3;
				String var5 = "texture" + var4;
				ResourceLocation var6 = ResourceLocation.tryParse(var1.getString(var5));
				this.textures[var3] = ((var6) != (null)) ? (var6) : (DEFAULT_TEXTURE);
				var3++;
				continue;
			}else{
				break;
			}
		} while (true);
		this.isFullWrapping = var1.getBoolean("isFullWrapping");
		this.animationSpeed = var1.getInt("animationSpeed");
		this.color = WyHelper.intToRGB(var1.getInt("rgb"),var1.getInt("alpha"));
		this.getEntityData().set(RADIUS,Float.valueOf(var1.getFloat("radius")));
		this.detailLevel = var1.getInt("detailLevel");
		this.move = var1.getBoolean("move");
	}
	public void writeSpawnData(RegistryFriendlyByteBuf var1) {
		var1.writeInt(this.textures.length);
		ResourceLocation[] var2 = this.textures;
		int var3 = var2.length;
		int var4 = 0;
		do{
			if ((var4) < (var3)){
				var1.writeResourceLocation(var2[var4]);
				var4++;
				continue;
			}else{
				break;
			}
		} while (true);
		var1.writeBoolean(this.isFullWrapping);
		var1.writeInt(this.animationSpeed);
		var1.writeInt(this.color.getRGB());
		var1.writeInt(this.color.getAlpha());
		var1.writeInt(this.detailLevel);
		var1.writeBoolean(this.move);
	}
	public void readSpawnData(RegistryFriendlyByteBuf var1) {
		int var2 = var1.readInt();
		this.textures = new ResourceLocation[var2];
		int var3 = 0;
		do{
			if ((var3) < (var2)){
				this.textures[var3] = var1.readResourceLocation();
				var3++;
				continue;
			}else{
				break;
			}
		} while (true);
		this.isFullWrapping = var1.readBoolean();
		this.animationSpeed = var1.readInt();
		var3 = var1.readInt();
		int var4 = var1.readInt();
		this.color = WyHelper.intToRGB(var3,var4);
		this.detailLevel = var1.readInt();
		this.move = var1.readBoolean();
	}
}