package xyz.pixelatedw.mineminenomi.entities;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.world.OneFruitWorldData;
import xyz.pixelatedw.mineminenomi.handlers.world.OneFruitPerWorldHandler;
import xyz.pixelatedw.mineminenomi.init.ModEntities;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;

public class AkumaNoMiEntity extends ItemEntity {
	// Fields
	private int playerCounter;

	public AkumaNoMiEntity(EntityType<? extends ItemEntity> var1, Level var2) {
		super(var1,var2);
		this.playerCounter = 0;
	}
	public AkumaNoMiEntity(Level var1) {
		super(((EntityType)(ModEntities.DEVIL_FRUIT_ITEM.get())),var1);
		this.playerCounter = 0;
	}
	public AkumaNoMiEntity(Level var1, double var2, double var3, double var4, ItemStack var5) {
		this(var1);
		this.setItem(var5);
		this.lifespan = ((var5.getItem()) == (null)) ? (6000) : (var5.getEntityLifespan(var1));
		this.setPos(var2,var3,var4);
		this.setXRot((this.random.nextFloat()) * (360F));
	}
	public void remove(Entity.RemovalReason var1) {
		if ((this.getItem().getItem() instanceof AkumaNoMiItem) && ((!(this.getItem().isEmpty())) && ((!(this.level().isClientSide)) && (var1.shouldDestroy())))){
			OneFruitWorldData.get().lostOneFruit(((AkumaNoMiItem)(this.getItem().getItem())).getRegistryKey(),(LivingEntity)(null),"Dropped item got destroyed");
		}
		super.remove(var1);
	}
	public void playerTouch(Player var1) {
		super.playerTouch(var1);
		if ((!(var1.level().isClientSide)) && (!(this.hasPickUpDelay()))){
			this.playerCounter = -1;
		}
	}
	protected void handlePortal() {
	}
	public void startSeenByPlayer(ServerPlayer var1) {
		this.playerCounter = (this.playerCounter) + (1);
	}
	public void stopSeenByPlayer(ServerPlayer var1) {
		if (((this.playerCounter) > (0)) && (((this.playerCounter) - (1)) <= (0))){
			if (!(this.getItem().getItem() instanceof AkumaNoMiItem)){
				return;
			}else{
				if (ServerConfig.hasChunkUnloadingRealisticLogic()){
					OneFruitPerWorldHandler.unloadRealistic(((AkumaNoMiItem)(this.getItem().getItem())));
				}
				if (ServerConfig.hasChunkUnloadingInstantLogic()){
					OneFruitPerWorldHandler.unloadInstant(((AkumaNoMiItem)(this.getItem().getItem())));
				}else{
					return;
				}
			}
		}
	}
}