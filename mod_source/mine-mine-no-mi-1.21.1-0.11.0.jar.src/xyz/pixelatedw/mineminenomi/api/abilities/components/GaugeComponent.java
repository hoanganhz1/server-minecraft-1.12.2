package xyz.pixelatedw.mineminenomi.api.abilities.components;

import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;

public class GaugeComponent extends AbilityComponent<IAbility> {
	// Fields
	private GaugeComponent$IGaugeRenderer<? extends IAbility> renderer;

	public <A extends IAbility> GaugeComponent(IAbility var1, GaugeComponent$IGaugeRenderer<A> var2) {
		super(((AbilityComponentKey)(ModAbilityComponents.GAUGE.get())),var1);
		this.renderer = var2;
		this.setClientSide();
	}
	public <A extends IAbility> GaugeComponent$IGaugeRenderer<A> getRenderer() {
		return (GaugeComponent$IGaugeRenderer<A>) (GaugeComponent$IGaugeRenderer) (this.renderer);
	}
}