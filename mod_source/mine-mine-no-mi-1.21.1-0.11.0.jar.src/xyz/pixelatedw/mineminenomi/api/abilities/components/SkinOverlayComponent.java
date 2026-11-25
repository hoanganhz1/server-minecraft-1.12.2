package xyz.pixelatedw.mineminenomi.api.abilities.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay$OverlayPart;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SSetOverlayLayersPacket;

public class SkinOverlayComponent extends AbilityComponent<IAbility> {
	// Fields
	private Map<Integer, AbilityOverlay> overlays;
	private Set<Integer> shownOverlays;

	public SkinOverlayComponent(IAbility var1, AbilityOverlay var2, AbilityOverlay... var3) {
		super(((AbilityComponentKey)(ModAbilityComponents.SKIN_OVERLAY.get())),var1);
		this.overlays = new HashMap();
		this.shownOverlays = new HashSet();
		this.addOverlay(var2);
		AbilityOverlay[] var4 = var3;
		int var5 = var4.length;
		int var6 = 0;
		do{
			if ((var6) < (var5)){
				this.addOverlay(var4[var6]);
				var6++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
	public void addOverlay(AbilityOverlay var1) {
		this.overlays.put(Integer.valueOf(var1.hashCode()),var1);
	}
	public void removeOverlay(AbilityOverlay var1) {
		this.overlays.remove(Integer.valueOf(var1.hashCode()));
	}
	public Optional<AbilityOverlay> getShownOverlay(AbilityOverlay$OverlayPart... var1) {
		if ((var1.length) <= (0)){
			return (Optional<AbilityOverlay>) (Optional) (Optional.empty());
		}else{
			ArrayList var2 = new ArrayList();
			Collections.addAll((Collection)(var2),var1);
			SkinOverlayComponent var3 = this;
			Function<Integer, AbilityOverlay> var4 = (Integer l0) -> {
			return ((AbilityOverlay)(this.overlays.get(l0)));
		};
			List var5 = var2;
			final List var5_f1 = var5;
			Predicate<AbilityOverlay> var6 = (l0) -> {
			return var5_f1.contains(l0.getOverlayPart());
		};
			return (Optional<AbilityOverlay>) (Optional) (((java.util.stream.Stream)(((java.util.stream.Stream)(this.shownOverlays.stream())).map((Function)(var4)).filter((Predicate)(((Predicate<AbilityOverlay>)(Objects::nonNull)))))).filter((Predicate)(var6)).findFirst());
		}
	}
	public boolean hasAnyShownOverlay(AbilityOverlay$OverlayPart... var1) {
		if ((var1.length) <= (0)){
			return false;
		}else{
			ArrayList var2 = new ArrayList();
			Collections.addAll((Collection)(var2),var1);
			SkinOverlayComponent var3 = this;
			Function<Integer, AbilityOverlay> var4 = (Integer l0) -> {
			return ((AbilityOverlay)(this.overlays.get(l0)));
		};
			List var5 = var2;
			final List var5_f1 = var5;
			Predicate<AbilityOverlay> var6 = (l0) -> {
			return var5_f1.contains(l0.getOverlayPart());
		};
			return (this.shownOverlays.stream().map(var4).filter((Predicate)(((Predicate<AbilityOverlay>)(Objects::nonNull)))).filter(var6).count()) > (0L);
		}
	}
	public void showAll(LivingEntity var1) {
		this.ensureIsRegistered();
		((java.util.Set)(this.shownOverlays)).addAll((Collection)(this.overlays.keySet()));
		this.updateShownOverlays(var1);
	}
	public void show(LivingEntity var1, AbilityOverlay var2) {
		this.ensureIsRegistered();
		this.shownOverlays.add(Integer.valueOf(var2.hashCode()));
		this.updateShownOverlays(var1);
	}
	public void hideAll(LivingEntity var1) {
		this.shownOverlays.clear();
		this.updateShownOverlays(var1);
	}
	public void hide(LivingEntity var1, AbilityOverlay var2) {
		this.shownOverlays.remove(Integer.valueOf(var2.hashCode()));
		this.updateShownOverlays(var1);
	}
	public void setShownOverlays(Set<Integer> var1) {
		this.shownOverlays = var1;
	}
	private void updateShownOverlays(LivingEntity var1) {
		if (!(var1.level().isClientSide)){
			ModNetwork.sendToAllTrackingAndSelf(new SSetOverlayLayersPacket(var1,this.getAbility(),this.shownOverlays),var1);
		}
	}
}