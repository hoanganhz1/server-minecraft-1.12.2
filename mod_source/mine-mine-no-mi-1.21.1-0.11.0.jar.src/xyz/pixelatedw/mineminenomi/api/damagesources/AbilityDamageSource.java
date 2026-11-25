package xyz.pixelatedw.mineminenomi.api.damagesources;

import java.util.ArrayList;
import java.util.Collection;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;

public class AbilityDamageSource extends BaseDamageSource {
	// Fields
	private AbilityCore<?> core;

	public AbilityDamageSource(Holder<DamageType> var1, @Nullable() Entity var2, @Nullable() Entity var3, @Nullable() Vec3 var4, AbilityCore<?> var5) {
		super(var1,var2,var3,var4,var5.getSourceElement(),(Collection)(var5.getSourceTypes()),var5.getSourceHakiNature());
		this.core = var5;
	}
	public AbilityDamageSource(Holder<DamageType> var1, @Nullable() Entity var2, @Nullable() Entity var3, AbilityCore<?> var4) {
		this(var1,var2,var3,((Vec3)(null)),var4);
	}
	public AbilityDamageSource(Holder<DamageType> var1, Vec3 var2, AbilityCore<?> var3) {
		this(var1,((Entity)(null)),((Entity)(null)),var2,var3);
	}
	public AbilityDamageSource(Holder<DamageType> var1, @Nullable() Entity var2, AbilityCore<?> var3) {
		this(var1,var2,var2,var3);
	}
	public AbilityDamageSource(Holder<DamageType> var1, AbilityCore<?> var2) {
		this(var1,((Entity)(null)),((Entity)(null)),((Vec3)(null)),var2);
	}
	public AbilityCore<?> getAbilityCore() {
		return (AbilityCore<?>) (AbilityCore) (this.core);
	}
	public Component getLocalizedDeathMessage(LivingEntity var1) {
		String var2 = this.type().msgId();
		String var3 = "death.attack." + var2;
		String var4 = var3;
		if (((this.getEntity()) != (null)) && (this.getEntity() instanceof LivingEntity)){
			String var5 = var4;
			String var6 = var5 + ".player";
			String var7 = var6;
			Object[] var8 = new Object[]{var1.getDisplayName(),this.getEntity().getDisplayName(),this.getAbilityCore().getLocalizedName()};
			return Component.translatable(var7,var8);
		}else{
			Object[] var5_1 = new Object[]{var1.getDisplayName(),this.getAbilityCore().getLocalizedName()};
			return Component.translatable(var4,var5_1);
		}
	}
}