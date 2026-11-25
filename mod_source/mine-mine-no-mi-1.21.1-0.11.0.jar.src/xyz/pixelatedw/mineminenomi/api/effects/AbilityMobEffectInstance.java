package xyz.pixelatedw.mineminenomi.api.effects;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;

public class AbilityMobEffectInstance extends MobEffectInstance {
	// Fields
	private final IAbility parent;

	public AbilityMobEffectInstance(IAbility var1, Holder<MobEffect> var2) {
		this(var1,var2,0,0);
	}
	public AbilityMobEffectInstance(IAbility var1, Holder<MobEffect> var2, int var3, int var4) {
		this(var1,var2,var3,var4,false,true);
	}
	public AbilityMobEffectInstance(IAbility var1, Holder<MobEffect> var2, int var3, int var4, boolean var5, boolean var6) {
		this(var1,var2,var3,var4,var5,var6,var6);
	}
	public AbilityMobEffectInstance(IAbility var1, Holder<MobEffect> var2, int var3, int var4, boolean var5, boolean var6, boolean var7) {
		super(var2,var3,var4,var5,var6,var7);
		this.parent = var1;
	}
	public IAbility getParent() {
		return this.parent;
	}
}