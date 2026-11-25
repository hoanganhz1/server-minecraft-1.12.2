package xyz.pixelatedw.mineminenomi.data.entity;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.client.model.EntityModel;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.animations.Animation;
import xyz.pixelatedw.mineminenomi.api.animations.AnimationId;
import xyz.pixelatedw.mineminenomi.init.ModAttachments;

public class AnimationData implements INBTSerializable<CompoundTag> {
	// Fields
	private LivingEntity owner;
	private Animation<?, ?> currentAnimation;
	private LinkedList<Animation<? extends LivingEntity, ? extends EntityModel<? extends LivingEntity>>> animations;

	public static AnimationData createCapability(IAttachmentHolder var0) {
		return new AnimationData(((LivingEntity)(var0)));
	}
	public static Optional<AnimationData> get(LivingEntity var0) {
		if (!(var0.hasData(ModAttachments.ANIMATION))){
			var0.setData(ModAttachments.ANIMATION,new AnimationData(var0));
		}
		return (Optional<AnimationData>) (Optional) (var0.getExistingData(ModAttachments.ANIMATION));
	}
	public static AnimationData getOrCreate(LivingEntity var0) {
		if (!(var0.hasData(ModAttachments.ANIMATION))){
			var0.setData(ModAttachments.ANIMATION,new AnimationData(var0));
		}
		return ((AnimationData)(var0.getData(ModAttachments.ANIMATION)));
	}
	private AnimationData(LivingEntity var1) {
		this.animations = (LinkedList) (new LinkedList());
		this.owner = var1;
	}
	public void startAnimation(AnimationId<?> var1, int var2, boolean var3) {
		if (((this.currentAnimation) != (null)) && (!(var3))){
			return;
		}else{
			if (((this.currentAnimation) != (null)) && (!(this.animations.stream().anyMatch((Predicate)(((java.util.function.Predicate)((Predicate) ((Predicate<Animation>) ((l0) -> {
			return ((java.lang.Object)(l0.getId())).equals((Object)(var1));
		}))))))))){
				this.animations.push(this.currentAnimation);
			}
			this.currentAnimation = (Animation) (AnimationId.getRegisteredAnimation((AnimationId)(var1)));
			if ((this.currentAnimation) != (null)){
				this.currentAnimation.start(this.owner,var2);
			}
			return;
		}
	}
	public void stopAnimation(AnimationId<?> var1) {
		if (((this.currentAnimation) != (null)) && (((java.lang.Object)(this.currentAnimation.getId())).equals((Object)(var1)))){
			this.currentAnimation.stop(this.owner);
			this.currentAnimation = null;
		}else{
			AnimationId<?> var2 = var1;
			final AnimationId<?> var2_f1 = var2;
			Predicate var3 = (Predicate) ((Predicate<Animation>) ((l0) -> {
			return ((java.lang.Object)(l0.getId())).equals((Object)(var2_f1));
		}));
			Optional var4 = this.animations.stream().filter(var3).findFirst();
			if (var4.isPresent()){
				((Animation)(var4.get())).stop(this.owner);
			}else{
				return;
			}
		}
	}
	public boolean isAnimationPlaying(AnimationId<?> var1) {
		if (((this.currentAnimation) != (null)) && ((((java.lang.Object)(this.currentAnimation.getId())).equals((Object)(var1))) && (this.currentAnimation.isPlaying()))){
			return true;
		}else{
			return false;
		}
	}
	@Nullable()
	public <E extends LivingEntity, M extends EntityModel<E>> @Nullable() Animation<E, M> getAnimation() {
		if (((this.currentAnimation) != (null)) && (this.currentAnimation.isStopped())){
			return (Animation<E, M>) (Animation) (null);
		}else{
			return (Animation<E, M>) (Animation) (this.currentAnimation);
		}
	}
	public void tick() {
		if ((this.owner) == (null)){
			return;
		}else{
			if ((this.currentAnimation) != (null)){
				this.currentAnimation.tick(this.owner);
				if (this.currentAnimation.isStopped()){
					this.currentAnimation = null;
				}
			}
			Iterator var1 = this.animations.iterator();
			do{
				if (var1.hasNext()){
					Animation var2 = ((Animation)(var1.next()));
					var2.tick(this.owner);
					if (var2.isStopped()){
						var1.remove();
						continue;
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
			if (((this.animations.size()) > (0)) && (!(((this.currentAnimation) != (null)) && (!(this.currentAnimation.isStopped()))))){
				Animation var2_1 = ((Animation)(this.animations.pollFirst()));
				if ((var2_1) != (null)){
					this.currentAnimation = (Animation) (var2_1);
				}else{
					return;
				}
			}
			return;
		}
	}
	public LinkedList<Animation<?, ?>> getAnimations() {
		return new LinkedList((Collection)(this.animations));
	}
	public CompoundTag serializeNBT(HolderLookup.Provider var1) {
		return new CompoundTag();
	}
	public void deserializeNBT(HolderLookup.Provider var1, CompoundTag var2) {
	}
}