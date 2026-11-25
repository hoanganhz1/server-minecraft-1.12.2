package xyz.pixelatedw.mineminenomi.api.abilities;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PauseTickComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PauseTickComponent$IPauseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PauseTickComponent$IResumeEvent;

public abstract class PassiveStatBonusAbility extends PassiveAbility {
	// Fields
	private final ChangeStatsComponent statsComponent;
	private HashMap<Holder<Attribute>, Function<LivingEntity, AttributeModifier>> dynamicAttributes;
	private HashMap<Holder<Attribute>, Double> cache;

	public PassiveStatBonusAbility(AbilityCore<? extends PassiveStatBonusAbility> var1) {
		super((AbilityCore)(var1));
		this.statsComponent = new ChangeStatsComponent((IAbility)(this));
		this.dynamicAttributes = (HashMap) (new HashMap());
		this.cache = (HashMap) (new HashMap());
		AbilityComponent[] var2 = new AbilityComponent[]{this.statsComponent};
		this.addComponents(var2);
		this.pauseTickComponent.addResumeEvent(100,(PauseTickComponent$IResumeEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.PauseTickComponent$IResumeEvent)(this::resumeEvent))));
		this.pauseTickComponent.addPauseEvent(100,(PauseTickComponent$IPauseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.PauseTickComponent$IPauseEvent)(this::pauseEvent))));
		this.addDuringPassiveEvent((PassiveAbility$IDuringPassiveEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility$IDuringPassiveEvent)(this::duringPassiveEvent))));
		this.setTickRate(40F);
	}
	private void duringPassiveEvent(LivingEntity var1) {
		if (!(var1.level().isClientSide)){
			this.updateStats(var1);
		}
		if (!(this.getCheck().test(var1))){
			this.statsComponent.removeModifiers(var1);
			return;
		}else{
			this.statsComponent.applyModifiers(var1);
			return;
		}
	}
	private void resumeEvent(LivingEntity var1, IAbility var2) {
		if (this.getCheck().test(var1)){
			this.statsComponent.applyModifiers(var1);
		}
	}
	private void pauseEvent(LivingEntity var1, IAbility var2) {
		this.statsComponent.removeModifiers(var1);
	}
	private boolean updateStats(LivingEntity var1) {
		boolean var2 = false;
		Iterator var3 = this.dynamicAttributes.entrySet().iterator();
		do{
			if (var3.hasNext()){
				Map.Entry var4 = ((Map.Entry)(var3.next()));
				AttributeModifier var5 = ((AttributeModifier)(((Function)(var4.getValue())).apply(var1)));
				double var6 = var5.amount();
				Double var7 = ((Double)(this.cache.get(var4.getKey())));
				if (((var7) != (null)) && ((var7.doubleValue()) == (var6))){
					continue;
				}else{
					this.statsComponent.removeModifier(var1,((Holder)(var4.getKey())),var5);
					this.statsComponent.removeAttributeModifier(((Holder)(var4.getKey())));
					this.statsComponent.addAttributeModifier(((Holder)(var4.getKey())),var5);
					this.cache.put(((Holder)(var4.getKey())),Double.valueOf(var6));
					var2 = true;
					continue;
				}
			}else{
				break;
			}
		} while (true);
		return var2;
	}
	public abstract Predicate<LivingEntity> getCheck();
	public void pushStaticAttribute(Holder<Attribute> var1, AttributeModifier var2) {
		PassiveStatBonusAbility var3 = this;
		Predicate<? super LivingEntity> var4 = (l0) -> {
			return !(this.isPaused());
		};
		this.statsComponent.addAttributeModifier(var1,var2,this.getCheck().and(var4));
	}
	public void pushStaticAttribute(Holder<Attribute> var1, AttributeModifier var2, @Nullable() Predicate<LivingEntity> var3) {
		this.statsComponent.addAttributeModifier(var1,var2,var3);
	}
	public void pushDynamicAttribute(Holder<Attribute> var1, Function<LivingEntity, AttributeModifier> var2) {
		this.dynamicAttributes.put(var1,var2);
	}
	public HashMap<Holder<Attribute>, Function<LivingEntity, AttributeModifier>> getDynamicAttributes() {
		return new HashMap((Map)(this.dynamicAttributes));
	}
}