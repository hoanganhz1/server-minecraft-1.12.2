package xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;

public class UnlockAbilityAction implements NodeUnlockAction {
	// Fields
	private final Supplier<? extends AbilityCore<?>> core;
	static final boolean $assertionsDisabled;

	public static UnlockAbilityAction unlock(Supplier<? extends AbilityCore<?>> var0) {
		return new UnlockAbilityAction(var0);
	}
	public UnlockAbilityAction(Supplier<? extends AbilityCore<?>> var1) {
		this.core = var1;
		if ((!($assertionsDisabled)) && ((this.core) == (null))){
			throw new AssertionError((Object)("Ability Core is null"));
		}else{
			return;
		}
	}
	public void onUnlock(LivingEntity var1) {
		AbilityHelper.checkAndUnlockAbility(var1,((AbilityCore)(this.core.get())));
	}
	public void onLock(LivingEntity var1) {
		UnlockAbilityAction var2 = this;
		Consumer<AbilityData> var3 = (l0) -> {
			l0.removeUnlockedAbility(((AbilityCore)(this.core.get())));
		};
		((java.util.Optional)(AbilityData.get(var1))).ifPresent((Consumer)(var3));
	}
	static  {
		$assertionsDisabled = !(UnlockAbilityAction.class.desiredAssertionStatus());
	}
}