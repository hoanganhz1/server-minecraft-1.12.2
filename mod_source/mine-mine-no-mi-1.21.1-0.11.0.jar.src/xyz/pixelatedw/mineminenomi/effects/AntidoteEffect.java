package xyz.pixelatedw.mineminenomi.effects;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public class AntidoteEffect extends BaseEffect {
	public AntidoteEffect() {
		super(MobEffectCategory.BENEFICIAL,WyHelper.hexToRGB("#36be4e").getRGB());
	}
	public boolean shouldApplyEffectTickThisTick(int var1, int var2) {
		return true;
	}
	public boolean applyEffectTick(LivingEntity var1, int var2) {
		if (var1.level().isClientSide){
			return false;
		}else{
			MobEffectInstance var3 = var1.getEffect((Holder)(ModEffects.ANTIDOTE));
			if ((var3) == (null)){
				return false;
			}else{
				if (((var3.getDuration()) % (10)) == (0)){
					Collection var4 = var1.getActiveEffects();
					ArrayList var5 = new ArrayList();
					Iterator var6 = var4.iterator();
					do{
						if (var6.hasNext()){
							MobEffectInstance var7 = ((MobEffectInstance)(var6.next()));
							if (this.isImmuneTo(var7.getEffect())){
								var5.add(var7.getEffect());
								continue;
							}else{
								continue;
							}
						}else{
							break;
						}
					} while (true);
					Iterator var7_1 = var5.iterator();
					do{
						if (var7_1.hasNext()){
							var1.removeEffect(((Holder)(var7_1.next())));
							continue;
						}else{
							break;
						}
					} while (true);
				}
				return true;
			}
		}
	}
	private boolean isImmuneTo(Holder<MobEffect> var1) {
		if (((var1) != (MobEffects.BLINDNESS)) && (((var1) != (MobEffects.CONFUSION)) && (((var1) != (MobEffects.POISON)) && (((var1) != (MobEffects.HUNGER)) && (((var1) != (MobEffects.WEAKNESS)) && ((var1) != (ModEffects.HUNGER.get()))))))){
			return false;
		}else{
			return true;
		}
	}
	public boolean isLingering() {
		return true;
	}
}