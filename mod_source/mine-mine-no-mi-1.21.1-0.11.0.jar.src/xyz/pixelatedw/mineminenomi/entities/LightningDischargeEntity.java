package xyz.pixelatedw.mineminenomi.entities;

import java.awt.Color;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.entity.IEntityWithComplexSpawn;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class LightningDischargeEntity extends Entity implements IEntityWithComplexSpawn, TraceableEntity {
	// Fields
	private static final Color DEFAULT_COLOR = WyHelper.hexToRGB("#F0EC7155");
	private UUID ownerUUID;
	private LivingEntity owner;
	private float size;
	private int aliveTicks;
	private float lightningLength;
	private int lightningDensity;
	private int lightningDetail;
	private int color;
	private int alpha;
	private int outlineColor;
	private int outlineAlpha;
	private boolean isSplit;
	private int updateRate;
	private int skipSegments;
	private int renderMode;

	public LightningDischargeEntity(Level var1) {
		super(((EntityType)(ModProjectiles.LIGHTNING_DISCHARGE.get())),var1);
		this.size = 1F;
		this.aliveTicks = 60;
		this.lightningLength = 1F;
		this.lightningDensity = 8;
		this.lightningDetail = 6;
		this.color = DEFAULT_COLOR.getRGB();
		this.alpha = DEFAULT_COLOR.getAlpha();
		this.outlineColor = 0;
		this.outlineAlpha = 0;
		this.isSplit = false;
		this.updateRate = 5;
		this.skipSegments = 0;
		this.renderMode = 0;
	}
	public LightningDischargeEntity(EntityType<? extends LightningDischargeEntity> var1, Level var2) {
		super(var1,var2);
		this.size = 1F;
		this.aliveTicks = 60;
		this.lightningLength = 1F;
		this.lightningDensity = 8;
		this.lightningDetail = 6;
		this.color = DEFAULT_COLOR.getRGB();
		this.alpha = DEFAULT_COLOR.getAlpha();
		this.outlineColor = 0;
		this.outlineAlpha = 0;
		this.isSplit = false;
		this.updateRate = 5;
		this.skipSegments = 0;
		this.renderMode = 0;
		this.noCulling = true;
	}
	public LightningDischargeEntity(Entity var1, double var2, double var3, double var4, float var5, float var6) {
		this(((EntityType)(ModProjectiles.LIGHTNING_DISCHARGE.get())),var1.level());
		this.setOwner(var1.getUUID());
		this.moveTo(var2,var3,var4,var5,var6);
	}
	public long getSeed() {
		return this.random.nextLong();
	}
	public void tick() {
		super.tick();
		if (!(this.level().isClientSide)){
			if ((this.getOwnerId()) == (null)){
				this.discard();
				return;
			}else{
				if (((this.getAliveTicks()) > (0)) && ((this.tickCount) >= (this.getAliveTicks()))){
					this.discard();
				}else{
					return;
				}
			}
		}
	}
	@OnlyIn(value=Dist.CLIENT)
	public boolean shouldRenderAtSqrDistance(double var1) {
		double var2 = (64D) * (Entity.getViewScale());
		return (var1) < ((var2) * (var2));
	}
	public void addAdditionalSaveData(CompoundTag var1) {
	}
	public void readAdditionalSaveData(CompoundTag var1) {
	}
	public void writeSpawnData(RegistryFriendlyByteBuf var1) {
		var1.writeBoolean((this.ownerUUID) != (null));
		if ((this.ownerUUID) != (null)){
			var1.writeUUID(this.ownerUUID);
		}
		var1.writeFloat(this.lightningLength);
		var1.writeInt(this.lightningDensity);
		var1.writeInt(this.lightningDetail);
		var1.writeFloat(this.size);
		var1.writeInt(this.updateRate);
		var1.writeInt(this.color);
		var1.writeInt(this.alpha);
		var1.writeInt(this.outlineColor);
		var1.writeInt(this.outlineAlpha);
		var1.writeBoolean(this.isSplit);
		var1.writeInt(this.skipSegments);
		var1.writeInt(this.renderMode);
	}
	public void readSpawnData(RegistryFriendlyByteBuf var1) {
		if (var1.readBoolean()){
			this.ownerUUID = var1.readUUID();
		}
		this.lightningLength = var1.readFloat();
		this.lightningDensity = var1.readInt();
		this.lightningDetail = var1.readInt();
		this.size = var1.readFloat();
		this.updateRate = var1.readInt();
		this.color = var1.readInt();
		this.alpha = var1.readInt();
		this.outlineColor = var1.readInt();
		this.outlineAlpha = var1.readInt();
		this.isSplit = var1.readBoolean();
		this.skipSegments = var1.readInt();
		this.renderMode = var1.readInt();
	}
	public void setSkipSegments(int var1) {
		this.skipSegments = var1;
	}
	public void setOwner(@Nullable() UUID var1) {
		this.ownerUUID = var1;
	}
	public void setUpdateRate(int var1) {
		this.updateRate = var1;
	}
	public void setSplit() {
		this.isSplit = true;
	}
	public void setDetails(int var1) {
		this.lightningDetail = var1;
	}
	public void setDensity(int var1) {
		this.lightningDensity = var1;
	}
	public void setSize(float var1) {
		this.size = var1;
	}
	public void setColor(Color var1) {
		this.color = var1.getRGB();
		this.alpha = var1.getAlpha();
	}
	public void setOutlineColor(Color var1) {
		this.outlineColor = var1.getRGB();
		this.outlineAlpha = var1.getAlpha();
	}
	public void setLightningLength(float var1) {
		this.lightningLength = var1;
	}
	public int getSkipSegmnets() {
		return this.skipSegments;
	}
	@Nullable()
	public UUID getOwnerId() {
		return this.ownerUUID;
	}
	@Nullable()
	public Entity getOwner() {
		if (((this.owner) == (null)) && ((this.ownerUUID) != (null))){
			Level var1 = this.level();
			if (var1 instanceof ServerLevel){
				Entity var2 = ((ServerLevel)(var1)).getEntity(this.ownerUUID);
				if (var2 instanceof LivingEntity){
					this.owner = ((LivingEntity)(var2));
				}
			}
		}
		return this.owner;
	}
	public int getUpdateRate() {
		return this.updateRate;
	}
	public boolean isSplit() {
		return this.isSplit;
	}
	public boolean isRenderingTransparent() {
		return (this.renderMode) == (1);
	}
	public int getRenderMode() {
		return 1;
	}
	public int getDetails() {
		return this.lightningDetail;
	}
	public int getDensity() {
		return this.lightningDensity;
	}
	public float getSize() {
		return this.size;
	}
	public int getColor() {
		return this.color;
	}
	public int getOutlineColor() {
		return this.outlineColor;
	}
	public int getAlpha() {
		return this.alpha;
	}
	public int getOutlineAlpha() {
		return this.outlineAlpha;
	}
	public float getLightningLength() {
		return this.lightningLength;
	}
	public int getAliveTicks() {
		return this.aliveTicks;
	}
	public void setAliveTicks(int var1) {
		this.aliveTicks = var1;
	}
	protected void defineSynchedData(SynchedEntityData.Builder var1) {
	}
}