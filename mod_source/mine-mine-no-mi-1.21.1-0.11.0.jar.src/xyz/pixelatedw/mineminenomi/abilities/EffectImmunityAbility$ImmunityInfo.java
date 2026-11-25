package xyz.pixelatedw.mineminenomi.abilities;

import java.util.ArrayList;
import java.util.Collection;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public class EffectImmunityAbility$ImmunityInfo {
	// Fields
	private ArrayList<Holder<MobEffect>> immunityEffects;
	private ArrayList<Holder<MobEffect>> resistanceEffects;
	private ArrayList<Integer> resistanceEffectsReduction;

	public EffectImmunityAbility$ImmunityInfo() {
		this.immunityEffects = (ArrayList) (new ArrayList());
		this.resistanceEffects = (ArrayList) (new ArrayList());
		this.resistanceEffectsReduction = (ArrayList) (new ArrayList());
	}
	public EffectImmunityAbility$ImmunityInfo clone() {
		EffectImmunityAbility$ImmunityInfo var1 = new EffectImmunityAbility$ImmunityInfo();
		var1.immunityEffects = (ArrayList) (this.immunityEffects);
		var1.resistanceEffects = (ArrayList) (this.resistanceEffects);
		var1.resistanceEffectsReduction = (ArrayList) (this.resistanceEffectsReduction);
		return var1;
	}
	public EffectImmunityAbility$ImmunityInfo combine(EffectImmunityAbility$ImmunityInfo var1) {
		this.immunityEffects.addAll((Collection)(var1.immunityEffects));
		this.resistanceEffects.addAll((Collection)(var1.resistanceEffects));
		this.resistanceEffectsReduction.addAll((Collection)(var1.resistanceEffectsReduction));
		return this;
	}
	public ArrayList<Holder<MobEffect>> getImmunityEffects() {
		return this.immunityEffects;
	}
	public ArrayList<Holder<MobEffect>> getResistanceEffects() {
		return this.resistanceEffects;
	}
	public ArrayList<Integer> getResistanceEffectsReduction() {
		return this.resistanceEffectsReduction;
	}
	public <T extends EffectImmunityAbility$ImmunityInfo> T addImmunityEffects(Holder<MobEffect>... var1) {
		Holder<MobEffect>[] var2 = var1;
		int var3 = var2.length;
		int var4 = 0;
		do{
			if ((var4) < (var3)){
				Holder<MobEffect> var5 = var2[var4];
				this.immunityEffects.add(var5);
				var4++;
				continue;
			}else{
				break;
			}
		} while (true);
		return (T) (this);
	}
	public <T extends EffectImmunityAbility$ImmunityInfo> T addResistanceEffect(Holder<MobEffect> var1, int var2) {
		this.resistanceEffects.add(var1);
		this.resistanceEffectsReduction.add(Integer.valueOf(var2));
		return (T) (this);
	}
	public <T extends EffectImmunityAbility$ImmunityInfo> T addLogiaImmunities() {
		this.addResistanceEffect((Holder)(ModEffects.MOVEMENT_BLOCKED),2);
		return (T) (this);
	}
}