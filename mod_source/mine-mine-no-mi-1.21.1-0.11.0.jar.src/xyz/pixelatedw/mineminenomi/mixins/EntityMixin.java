package xyz.pixelatedw.mineminenomi.mixins;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PortalProcessor;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.entity.PartEntity;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.pixelatedw.mineminenomi.api.effects.IVanishEffect;
import xyz.pixelatedw.mineminenomi.api.extensions.IEntityExtension;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.data.entity.DevilFruitData;
import xyz.pixelatedw.mineminenomi.handlers.entity.KairosekiCoatingHandler;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

@Mixin(value={Entity.class})
public class EntityMixin implements IEntityExtension {
	// Fields
	@Shadow() private PortalProcessor portalProcess;

	@Inject(method={"positionRider(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/entity/Entity$MoveFunction;)V"}, at={@At(value="TAIL")}, cancellable=true)
	public void mineminenomi$positionRider(Entity var1, Entity.MoveFunction var2, CallbackInfo var3) {
		Entity var4 = ((Entity)(this));
		if (var4 instanceof LivingEntity){
			LivingEntity var5 = ((LivingEntity)(var4));
			Iterator var6 = ((DevilFruitData)(((java.util.Optional)(DevilFruitData.get(var5))).orElse((Object)(null)))).getActiveMorphs().iterator();
			if (var6.hasNext()){
				((MorphInfo)(var6.next())).positionRider(var5,var1);
				return;
			}else{
				return;
			}
		}else{
			return;
		}
	}
	@Inject(method={"spawnAtLocation(Lnet/minecraft/world/level/ItemLike;I)Lnet/minecraft/world/entity/item/ItemEntity;"}, at={@At(value="HEAD")}, cancellable=true)
	public void mineminenomi$spawnAtLocation(ItemLike var1, int var2, CallbackInfoReturnable<ItemEntity> var3) {
		ItemEntity var4 = KairosekiCoatingHandler.dropCoatedItem(((Entity)(this)),new ItemStack(var1));
		if ((var4) != (null)){
			var3.setReturnValue(var4);
			return;
		}else{
			return;
		}
	}
	@Inject(method={"distanceToSqr(Lnet/minecraft/world/entity/Entity;)D"}, at={@At(value="RETURN")}, cancellable=true)
	public void mineminenomi$distanceToSqr(Entity var1, CallbackInfoReturnable<Double> var2) {
		Entity var3 = ((Entity)(this));
		double var4 = ((Double)(var2.getReturnValue())).doubleValue();
		if (var1.isMultipartEntity()){
			PartEntity[] var5 = var1.getParts();
			int var6 = var5.length;
			int var7 = 0;
			do{
				if ((var7) < (var6)){
					double var8 = var3.distanceToSqr((Entity)(var5[var7]));
					if ((var8) < (var4)){
						var2.setReturnValue(Double.valueOf(var8));
					}
					var7++;
					continue;
				}else{
					break;
				}
			} while (true);
		}
	}
	@Inject(method={"canSpawnSprintParticle"}, at={@At(value="HEAD")}, cancellable=true)
	public void mineminenomi$canSpawnSprintParticle(CallbackInfoReturnable<Boolean> var1) {
		Entity var2 = ((Entity)(this));
		if (var2 instanceof LivingEntity){
			Iterator var3 = ((LivingEntity)(var2)).getActiveEffects().iterator();
			do{
				if (var3.hasNext()){
					MobEffectInstance var4 = ((MobEffectInstance)(var3.next()));
					if ((var4.getEffect() instanceof IVanishEffect) && ((((IVanishEffect)(var4.getEffect())).isVanished(((LivingEntity)(var2)),var4.getDuration(),var4.getAmplifier())) && (((IVanishEffect)(var4.getEffect())).disableParticles()))){
						var1.setReturnValue(Boolean.valueOf(false));
						return;
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
		}
	}
	@Inject(method={"isInvisible"}, at={@At(value="HEAD")}, cancellable=true)
	public void mineminenomi$isInvisible(CallbackInfoReturnable<Boolean> var1) {
		Entity var2 = ((Entity)(this));
		if (var2 instanceof LivingEntity){
			Iterator var3 = ((LivingEntity)(var2)).getActiveEffects().iterator();
			do{
				if (var3.hasNext()){
					MobEffectInstance var4 = ((MobEffectInstance)(var3.next()));
					if ((var4.getEffect() instanceof IVanishEffect) && (((IVanishEffect)(var4.getEffect())).isVanished(((LivingEntity)(var2)),var4.getDuration(),var4.getAmplifier()))){
						var1.setReturnValue(Boolean.valueOf(true));
						return;
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
		}
	}
	@Inject(method={"isSteppingCarefully"}, at={@At(value="HEAD")}, cancellable=true)
	public void mineminenomi$isSteppingCarefully(CallbackInfoReturnable<Boolean> var1) {
		Entity var2 = ((Entity)(this));
		if ((var2 instanceof LivingEntity) && (((LivingEntity)(var2)).hasEffect((Holder)(ModEffects.SILENT)))){
			var1.setReturnValue(Boolean.valueOf(true));
		}
	}
	public boolean isInPortal() {
		return ((this.portalProcess) != (null)) && (this.portalProcess.isInsidePortalThisTick());
	}
}