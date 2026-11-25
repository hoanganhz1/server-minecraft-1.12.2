package xyz.pixelatedw.mineminenomi.items.weapons;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.mixins.IMobEffectInstanceMixin;

public class ModSwordItem extends SwordItem {
	// Fields
	public static final ResourceLocation BASE_ATTACK_REACH_ID = ResourceLocation.withDefaultNamespace("base_attack_reach");
	private Set<ModSwordItem$HitEffect> hitEffects;

	public ModSwordItem(Tier var1, Item.Properties var2) {
		super(var1,var2);
		this.hitEffects = new HashSet();
	}
	public boolean hurtEnemy(ItemStack var1, LivingEntity var2, LivingEntity var3) {
		super.hurtEnemy(var1,var2,var3);
		Iterator var4 = this.hitEffects.iterator();
		do{
			if (var4.hasNext()){
				ModSwordItem$HitEffect var5 = ((ModSwordItem$HitEffect)(var4.next()));
				if (((var5.addedStacks) > (0)) && (var2.hasEffect(var5.effect))){
					MobEffectInstance var6 = var2.getEffect(var5.effect);
					int var7 = var2.getEffect(var5.effect).getDuration();
					((IMobEffectInstanceMixin)(var6)).setDuration((var7) + ((20) * (var5.addedStacks)));
					((IMobEffectInstanceMixin)(var6)).setAmplifier((var6.getAmplifier()) + (var5.addedStacks));
					continue;
				}else{
					var2.addEffect(new MobEffectInstance(var5.effect,var5.duration,0));
					continue;
				}
			}else{
				break;
			}
		} while (true);
		return true;
	}
	public <T extends ModSwordItem> T addEffect(Holder<MobEffect> var1, int var2) {
		this.hitEffects.add(new ModSwordItem$HitEffect(var1,var2,0));
		return (T) (this);
	}
	public <T extends ModSwordItem> T addEffect(Holder<MobEffect> var1, int var2, int var3) {
		this.hitEffects.add(new ModSwordItem$HitEffect(var1,var2,var3));
		return (T) (this);
	}
	@Deprecated()
	public ModSwordItem setRustImmunity() {
		return this;
	}
	@Deprecated()
	public ModSwordItem setIsPoisonous(int var1) {
		this.addEffect(MobEffects.POISON,var1);
		return this;
	}
	@Deprecated()
	public ModSwordItem setFrosbiteTimer(int var1) {
		this.addEffect((Holder)(ModEffects.FROSTBITE),var1);
		return this;
	}
}