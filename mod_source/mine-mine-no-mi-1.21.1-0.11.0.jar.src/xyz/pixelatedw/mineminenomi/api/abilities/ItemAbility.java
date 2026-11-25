package xyz.pixelatedw.mineminenomi.api.abilities;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ItemSpawnComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ItemSpawnComponent$IMissingItemEvent;

public abstract class ItemAbility extends Ability {
	// Fields
	protected final ContinuousComponent continuousComponent;
	protected final ItemSpawnComponent itemSpawnComponent;

	public ItemAbility(AbilityCore<? extends ItemAbility> var1) {
		super((AbilityCore)(var1));
		this.continuousComponent = new ContinuousComponent((IAbility)(this),true).addStartEvent(200,(ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::startContinuityEvent)))).addEndEvent(200,(ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
		this.itemSpawnComponent = new ItemSpawnComponent((IAbility)(this)).addMissingItemEvent(200,(ItemSpawnComponent$IMissingItemEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ItemSpawnComponent$IMissingItemEvent)(this::missingItemEvent))));
		AbilityComponent[] var2 = new AbilityComponent[]{this.continuousComponent,this.itemSpawnComponent};
		this.addComponents(var2);
		this.addUseEvent(200,(Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::onUseEvent))));
	}
	private void onUseEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.triggerContinuity(var1);
	}
	private void startContinuityEvent(LivingEntity var1, IAbility var2) {
		this.itemSpawnComponent.spawnItem(var1,this.createItemStack(var1));
	}
	private void endContinuityEvent(LivingEntity var1, IAbility var2) {
		this.itemSpawnComponent.despawnItems(var1);
	}
	private void missingItemEvent(LivingEntity var1, IAbility var2) {
		this.continuousComponent.stopContinuity(var1);
	}
	public abstract ItemStack createItemStack(LivingEntity var0);
}