package xyz.pixelatedw.mineminenomi.mixins;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.damagesources.BaseDamageSource;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler$NuDamageValue;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.init.ModTags$DamageTypes;

@Mixin(value={DamageSource.class})
public class DamageSourceMixin implements IDamageSourceHandler, Cloneable {
	// Fields
	private static final String PIERCING_TAG = "piercing";
	private static final String LOGIA_BYPASS_TAG = "bypassLogia";
	private static final String FRIEENDLY_BYPASS_TAG = "bypassFriendly";
	private static final String UNAVOIDABLE_TAG = "unavoidable";
	private static final IDamageSourceHandler$NuDamageValue NON_ABILITY_VALUE = new IDamageSourceHandler$NuDamageValue((AbilityCore)(null),SourceElement.NONE,SourceHakiNature.HARDENING,new SourceType[]{SourceType.FIST});
	private Map<@Nullable() AbilityCore<?>, CompoundTag> tags;
	private Map<IDamageSourceHandler$NuDamageValue, Float> damageValues;

	public DamageSourceMixin() {
		this.tags = new HashMap();
		this.damageValues = new HashMap();
	}
	public IDamageSourceHandler clone() {
		DamageSource var1 = ((DamageSource)(this));
		IDamageSourceHandler var2 = ((IDamageSourceHandler)(new DamageSource(var1.typeHolder(),var1.getDirectEntity(),var1.getEntity(),var1.getSourcePosition())));
		DamageSourceMixin var3 = ((DamageSourceMixin)(var2));
		var3.tags = (Map) (this.tags);
		DamageSourceMixin var4 = ((DamageSourceMixin)(var2));
		var4.damageValues = (Map) (this.damageValues);
		return var2;
	}
	public boolean isInitialized() {
		return (this.damageValues.size()) > (0);
	}
	public Map<AbilityCore<?>, CompoundTag> getTags() {
		return this.tags;
	}
	@Nullable()
	public @Nullable() CompoundTag getAbilityTags(AbilityCore<?> var1) {
		Objects.requireNonNull(var1,"Null ability found");
		Iterator var2 = this.tags.entrySet().iterator();
		do{
			if (var2.hasNext()){
				Map.Entry var3 = ((Map.Entry)(var2.next()));
				if (((var3.getKey()) != (null)) && (((AbilityCore)(var3.getKey())).equals(var1))){
					return ((CompoundTag)(var3.getValue()));
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
		return null;
	}
	public void addAbilityPiercing(float var1, AbilityCore<?> var2) {
		Objects.requireNonNull(var2,"Null ability found, use `addGlobalPiercing` for global piercing otherwise this is a bug");
		if (!(this.tags.containsKey(var2))){
			this.tags.put(var2,new CompoundTag());
		}
		((CompoundTag)(((java.util.Map)(this.tags)).get((Object)(var2)))).putFloat("piercing",var1);
	}
	public float getAbilityPiercing(@Nullable() AbilityCore<?> var1) {
		if ((var1) == (null)){
			return 0F;
		}else{
			Iterator var2 = this.tags.entrySet().iterator();
			do{
				if (var2.hasNext()){
					Map.Entry var3 = ((Map.Entry)(var2.next()));
					if (((var3.getKey()) != (null)) && (((AbilityCore)(var3.getKey())).equals(var1))){
						return Mth.clamp(((CompoundTag)(var3.getValue())).getFloat("piercing"),0F,1F);
					}else{
						continue;
					}
				}else{
					break;
				}
			} while (true);
			return 0F;
		}
	}
	public void addGlobalPiercing(float var1) {
		Function<? super AbilityCore<?>, ? extends CompoundTag> var2 = (Function) ((Function<AbilityCore, CompoundTag>) ((l0) -> {
			return new CompoundTag();
		}));
		((java.util.Map)(this.tags)).computeIfAbsent((Object)(null),(Function)(var2));
		((CompoundTag)(this.tags.get((Object)(null)))).putFloat("piercing",var1);
	}
	public float getGlobalPiercing() {
		Function<? super AbilityCore<?>, ? extends CompoundTag> var1 = (Function) ((Function<AbilityCore, CompoundTag>) ((l0) -> {
			return new CompoundTag();
		}));
		((java.util.Map)(this.tags)).computeIfAbsent((Object)(null),(Function)(var1));
		return Mth.clamp(((CompoundTag)(this.tags.get((Object)(null)))).getFloat("piercing"),0F,1F);
	}
	public void bypassLogia() {
		Function<? super AbilityCore<?>, ? extends CompoundTag> var1 = (Function) ((Function<AbilityCore, CompoundTag>) ((l0) -> {
			return new CompoundTag();
		}));
		((java.util.Map)(this.tags)).computeIfAbsent((Object)(null),(Function)(var1));
		((CompoundTag)(this.tags.get((Object)(null)))).putBoolean("bypassLogia",true);
	}
	public boolean isBypassingLogia() {
		Function<? super AbilityCore<?>, ? extends CompoundTag> var1 = (Function) ((Function<AbilityCore, CompoundTag>) ((l0) -> {
			return new CompoundTag();
		}));
		((java.util.Map)(this.tags)).computeIfAbsent((Object)(null),(Function)(var1));
		return (((DamageSource)(this)).is(ModTags$DamageTypes.BYPASSES_LOGIA)) || (((CompoundTag)(this.tags.get((Object)(null)))).getBoolean("bypassLogia"));
	}
	public void bypassFriendly() {
		Function<? super AbilityCore<?>, ? extends CompoundTag> var1 = (Function) ((Function<AbilityCore, CompoundTag>) ((l0) -> {
			return new CompoundTag();
		}));
		((java.util.Map)(this.tags)).computeIfAbsent((Object)(null),(Function)(var1));
		((CompoundTag)(this.tags.get((Object)(null)))).putBoolean("bypassFriendly",true);
	}
	public boolean isBypassingFriendly() {
		Function<? super AbilityCore<?>, ? extends CompoundTag> var1 = (Function) ((Function<AbilityCore, CompoundTag>) ((l0) -> {
			return new CompoundTag();
		}));
		((java.util.Map)(this.tags)).computeIfAbsent((Object)(null),(Function)(var1));
		return (((DamageSource)(this)).is(ModTags$DamageTypes.BYPASSES_FRIENDLY_PROTECTION)) || (((CompoundTag)(this.tags.get((Object)(null)))).getBoolean("bypassFriendly"));
	}
	public void setUnavoidable() {
		Function<? super AbilityCore<?>, ? extends CompoundTag> var1 = (Function) ((Function<AbilityCore, CompoundTag>) ((l0) -> {
			return new CompoundTag();
		}));
		((java.util.Map)(this.tags)).computeIfAbsent((Object)(null),(Function)(var1));
		((CompoundTag)(this.tags.get((Object)(null)))).putBoolean("unavoidable",true);
	}
	public boolean isUnavoidable() {
		Function<? super AbilityCore<?>, ? extends CompoundTag> var1 = (Function) ((Function<AbilityCore, CompoundTag>) ((l0) -> {
			return new CompoundTag();
		}));
		((java.util.Map)(this.tags)).computeIfAbsent((Object)(null),(Function)(var1));
		return (((DamageSource)(this)).is(ModTags$DamageTypes.UNAVOIDABLE)) || (((CompoundTag)(this.tags.get((Object)(null)))).getBoolean("unavoidable"));
	}
	public void reset() {
		this.tags = new HashMap();
		this.damageValues.clear();
	}
	public void clearDamage() {
		this.damageValues.clear();
	}
	public void addVanillaPunch(float var1) {
		this.addDamage(var1,NON_ABILITY_VALUE);
	}
	public void addAbilityDamage(float var1, @Nullable() AbilityCore<?> var2) {
		SourceElement var3 = var2.getSourceElement();
		SourceHakiNature var4 = var2.getSourceHakiNature();
		SourceType[] var5 = ((SourceType[])(var2.getSourceTypes().toArray(new SourceType[0])));
		this.addDamage(var1,new IDamageSourceHandler$NuDamageValue(var2,var3,var4,var5));
	}
	public void addDamage(float var1, SourceElement var2, SourceHakiNature var3, SourceType... var4) {
		this.addDamage(var1,new IDamageSourceHandler$NuDamageValue((AbilityCore)(null),var2,var3,var4));
	}
	public void addDamage(float var1, IDamageSourceHandler$NuDamageValue var2) {
		float var3 = (float)(Math.max((double)(var1),0D));
		this.damageValues.put(var2,Float.valueOf(var3));
	}
	public Set<Map.Entry<IDamageSourceHandler$NuDamageValue, Float>> getDamageValues() {
		return (Set<Map.Entry<IDamageSourceHandler$NuDamageValue, Float>>) (Set) (Collections.unmodifiableSet((Set)(this.damageValues.entrySet())));
	}
	public float getTotalDamage() {
		BinaryOperator<Float> var1 = (Float l0, Float l1) -> {
			return Float.valueOf((l0.floatValue()) + (l1.floatValue()));
		};
		return ((Float)(((java.util.stream.Stream)(this.damageValues.values().stream())).reduce((Object)(Float.valueOf(0F)),(BinaryOperator)(var1)))).floatValue();
	}
	public float getTotalBaseDamage() {
		Predicate var1 = (Predicate) ((Predicate<Map.Entry>) ((l0) -> {
			return (((IDamageSourceHandler$NuDamageValue)(l0.getKey())).ability()) == (null);
		}));
		BinaryOperator<Float> var2 = (Float l0, Float l1) -> {
			return Float.valueOf((l0.floatValue()) + (l1.floatValue()));
		};
		return ((Float)(((java.util.stream.Stream)(((java.util.stream.Stream)(this.damageValues.entrySet().stream())).filter((Predicate)(var1)).map((Function)(((Function<Map.Entry<?, Float>, Float>)(Map.Entry::getValue)))))).reduce((Object)(Float.valueOf(0F)),(BinaryOperator)(var2)))).floatValue();
	}
	public float getTotalAbilityDamage() {
		Predicate var1 = (Predicate) ((Predicate<Map.Entry>) ((l0) -> {
			return (((IDamageSourceHandler$NuDamageValue)(l0.getKey())).ability()) != (null);
		}));
		BinaryOperator<Float> var2 = (Float l0, Float l1) -> {
			return Float.valueOf((l0.floatValue()) + (l1.floatValue()));
		};
		return ((Float)(((java.util.stream.Stream)(((java.util.stream.Stream)(this.damageValues.entrySet().stream())).filter((Predicate)(var1)).map((Function)(((Function<Map.Entry<?, Float>, Float>)(Map.Entry::getValue)))))).reduce((Object)(Float.valueOf(0F)),(BinaryOperator)(var2)))).floatValue();
	}
	public float getTotalAbilityDamageForType(SourceType var1) {
		float var2 = 0F;
		Iterator var3 = this.damageValues.entrySet().iterator();
		do{
			if (var3.hasNext()){
				Map.Entry var4 = ((Map.Entry)(var3.next()));
				if (((((IDamageSourceHandler$NuDamageValue)(var4.getKey())).ability()) == (null)) || (!(((IDamageSourceHandler$NuDamageValue)(var4.getKey())).hasType(var1)))){
					continue;
				}else{
					var2 = (var2) + (((Float)(var4.getValue())).floatValue());
					continue;
				}
			}else{
				break;
			}
		} while (true);
		return var2;
	}
	public float getTotalAbilityDamageForHaki(SourceHakiNature var1) {
		float var2 = 0F;
		Iterator var3 = this.damageValues.entrySet().iterator();
		do{
			if (var3.hasNext()){
				Map.Entry var4 = ((Map.Entry)(var3.next()));
				if (((((IDamageSourceHandler$NuDamageValue)(var4.getKey())).ability()) == (null)) || (!(((IDamageSourceHandler$NuDamageValue)(var4.getKey())).hakiNature().equals(var1)))){
					continue;
				}else{
					var2 = (var2) + (((Float)(var4.getValue())).floatValue());
					continue;
				}
			}else{
				break;
			}
		} while (true);
		return var2;
	}
	public boolean hasElement(SourceElement var1) {
		DamageSource var2 = ((DamageSource)(this));
		if ((var2 instanceof BaseDamageSource) && (((BaseDamageSource)(var2)).getElement().equals(var1))){
			return true;
		}else{
			Iterator var3 = this.damageValues.keySet().iterator();
			do{
				if (var3.hasNext()){
					if (((IDamageSourceHandler$NuDamageValue)(var3.next())).element().equals(var1)){
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
	}
	public boolean hasHakiNature(SourceHakiNature var1) {
		DamageSource var2 = ((DamageSource)(this));
		if ((var2 instanceof BaseDamageSource) && (((BaseDamageSource)(var2)).getHakiNature().equals(var1))){
			return true;
		}else{
			Iterator var3 = this.damageValues.keySet().iterator();
			do{
				if (var3.hasNext()){
					if (((IDamageSourceHandler$NuDamageValue)(var3.next())).hakiNature().equals(var1)){
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
	}
	public boolean hasType(SourceType var1) {
		DamageSource var2 = ((DamageSource)(this));
		if ((var2 instanceof BaseDamageSource) && (((BaseDamageSource)(var2)).getTypes().contains(var1))){
			return true;
		}else{
			Iterator var3 = this.damageValues.keySet().iterator();
			do{
				if (var3.hasNext()){
					if (((IDamageSourceHandler$NuDamageValue)(var3.next())).hasType(var1)){
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
	}
	public boolean hasAbility(AbilityCore<?> var1) {
		Iterator var2 = this.damageValues.keySet().iterator();
		do{
			if (var2.hasNext()){
				IDamageSourceHandler$NuDamageValue var3 = ((IDamageSourceHandler$NuDamageValue)(var2.next()));
				if (((var3.ability()) != (null)) && (var3.ability().equals(var1))){
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
	public Set<AbilityCore<?>> getAbilitiesForElement(SourceElement var1) {
		HashSet var2 = new HashSet();
		Iterator var3 = this.damageValues.keySet().iterator();
		do{
			if (var3.hasNext()){
				IDamageSourceHandler$NuDamageValue var4 = ((IDamageSourceHandler$NuDamageValue)(var3.next()));
				if (var4.element().equals(var1)){
					var2.add(var4.ability());
					continue;
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
		return var2;
	}
	public Set<AbilityCore<?>> getAbilitiesForHakiNature(SourceHakiNature var1) {
		HashSet var2 = new HashSet();
		Iterator var3 = this.damageValues.keySet().iterator();
		do{
			if (var3.hasNext()){
				IDamageSourceHandler$NuDamageValue var4 = ((IDamageSourceHandler$NuDamageValue)(var3.next()));
				if (var4.hakiNature().equals(var1)){
					var2.add(var4.ability());
					continue;
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
		return var2;
	}
	public Set<AbilityCore<?>> getAbilitiesForType(SourceType var1) {
		HashSet var2 = new HashSet();
		Iterator var3 = this.damageValues.keySet().iterator();
		LOOP_1:
		do{
			if (var3.hasNext()){
				IDamageSourceHandler$NuDamageValue var4 = ((IDamageSourceHandler$NuDamageValue)(var3.next()));
				SourceType[] var5 = var4.sourceTypes();
				int var6 = var5.length;
				int var7 = 0;
				do{
					if ((var7) < (var6)){
						if (var5[var7].equals(var1)){
							var2.add(var4.ability());
							continue LOOP_1;
						}else{
							var7++;
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
		return var2;
	}
}