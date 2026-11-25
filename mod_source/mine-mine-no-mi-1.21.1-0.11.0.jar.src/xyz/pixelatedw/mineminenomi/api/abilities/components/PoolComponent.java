package xyz.pixelatedw.mineminenomi.api.abilities.components;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityPool;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SSetPoolInUsePacket;

public class PoolComponent extends AbilityComponent<IAbility> {
	// Fields
	private final Set<AbilityPool> pools;
	private final int useLimit;
	private boolean poolInUse;
	private float ticksLocked;
	private Predicate<IAbility> predicate;

	public PoolComponent(IAbility var1, AbilityPool var2, AbilityPool... var3) {
		this(var1,1,var2,var3);
	}
	public PoolComponent(IAbility var1, int var2, AbilityPool var3, AbilityPool... var4) {
		super(((AbilityComponentKey)(ModAbilityComponents.POOL.get())),var1);
		AbilityPool[] var5 = new AbilityPool[]{var3};
		this.pools = Sets.newHashSet(var5);
		this.useLimit = var2;
		Collections.addAll((Collection)(this.pools),var4);
	}
	public void postInit(IAbility var0) { throw new RuntimeException("yak-decompiler: undecompilable method body"); /* yak-decompiler: ParseBytesCode failed: invalid_input: dump lambda method `xyz.pixelatedw.mineminenomi.api.abilities.components.PoolComponent.lambda$postInit$2` error: ParseByte */ }
	protected void doTick(LivingEntity var1) {
		if ((this.getAbility().hasComponent(((AbilityComponentKey)(ModAbilityComponents.DISABLE.get())))) && (((DisableComponent)(this.getAbility().getComponent(((AbilityComponentKey)(ModAbilityComponents.DISABLE.get()))).get())).isDisabled())){
			return;
		}else{
			return;
		}
	}
	public Set<AbilityPool> getPools() {
		return this.pools;
	}
	public void removeFromPool(AbilityPool var1) {
		this.pools.remove(var1);
	}
	public boolean containsPool(AbilityPool var1) {
		Iterator var2 = this.pools.iterator();
		do{
			if (var2.hasNext()){
				if (((java.lang.Object)(((AbilityPool)(var2.next())))).equals((Object)(var1))){
					return true;
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
		return false;
	}
	public boolean hasAtLeastOneSamePool(PoolComponent var1) {
		Iterator var2 = this.pools.iterator();
		LOOP_1:
		do{
			if (var2.hasNext()){
				AbilityPool var3 = ((AbilityPool)(var2.next()));
				Iterator var4 = var1.getPools().iterator();
				do{
					if (var4.hasNext()){
						if (((java.lang.Object)(var3)).equals((Object)(((AbilityPool)(var4.next()))))){
							return true;
						}else{
							continue;
						}
					}else{
						continue LOOP_1;
					}
				} while (true);
			}else{
				break;
			}
		} while (true);
		return false;
	}
	public Set<IAbility> getAbilitiesInPool(LivingEntity var1) {
		PoolComponent var2 = this;
		Function var3 = (Function) ((Function<AbilityData, Set>) ((l0) -> {
			return l0.getEquippedAbilities(this.predicate);
		}));
		return ((Set)(((java.util.Optional)(AbilityData.get(var1).map(var3))).orElse((Object)(Set.of()))));
	}
	public int getActiveCount(LivingEntity var1) {
		Function<IAbility, PoolComponent> var2 = (l0) -> {
			return ((PoolComponent)(l0.getComponent(((AbilityComponentKey)(ModAbilityComponents.POOL.get()))).get()));
		};
		return (int)(this.getAbilitiesInPool(var1).stream().map(var2).filter((Predicate)(((Predicate<PoolComponent>)(PoolComponent::isPoolInUse)))).count());
	}
	public boolean canUsePool(LivingEntity var1) {
		return (this.isPoolInUse()) || ((this.getActiveCount(var1)) < (this.useLimit));
	}
	public boolean isPoolInUse() {
		return this.poolInUse;
	}
	public void setAbilityFromPoolInUse(LivingEntity var1, boolean var2) {
		this.poolInUse = var2;
		if (var1.level().isClientSide){
			Iterator var3 = this.getAbilitiesInPool(var1).iterator();
			do{
				if (var3.hasNext()){
					IAbility var4 = ((IAbility)(var3.next()));
					PoolComponent var5 = ((PoolComponent)(var4.getComponent(((AbilityComponentKey)(ModAbilityComponents.POOL.get()))).orElse(null)));
					if (((var5) != (null)) && (!(var5.isPoolInUse()))){
						var4.getComponent(((AbilityComponentKey)(ModAbilityComponents.SLOT_DECORATION.get()))).ifPresent((Consumer)(((Consumer<SlotDecorationComponent>)(SlotDecorationComponent::resetDecoration))));
						continue;
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
		}else{
			ModNetwork.sendToAllTrackingAndSelf(new SSetPoolInUsePacket((LivingEntity)(var1),(IAbility)(this.getAbility()),var2,0),var1);
		}
	}
	public void startPoolInUse(LivingEntity var1) {
		this.ensureIsRegistered();
		this.setAbilityFromPoolInUse(var1,true);
	}
	public void stopPoolInUse(LivingEntity var1) {
		this.setAbilityFromPoolInUse(var1,false);
	}
}