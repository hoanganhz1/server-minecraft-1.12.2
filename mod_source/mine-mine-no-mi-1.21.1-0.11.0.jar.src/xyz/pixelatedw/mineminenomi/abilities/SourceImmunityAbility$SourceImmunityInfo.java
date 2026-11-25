package xyz.pixelatedw.mineminenomi.abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import org.apache.commons.lang3.tuple.Pair;

public class SourceImmunityAbility$SourceImmunityInfo {
	// Fields
	private static final List<ResourceKey<DamageType>> DEFAULT_LOGIA_IMMUNITIES;
	private ArrayList<ResourceKey<DamageType>> sourceImmunities;
	private ArrayList<Pair<ResourceKey<DamageType>, Integer>> sourceResistances;
	private ArrayList<Predicate<DamageSource>> sourceRules;

	public SourceImmunityAbility$SourceImmunityInfo() {
		this.sourceImmunities = (ArrayList) (new ArrayList());
		this.sourceResistances = (ArrayList) (new ArrayList());
		this.sourceRules = (ArrayList) (new ArrayList());
		Iterator var1 = DEFAULT_LOGIA_IMMUNITIES.iterator();
		do{
			if (var1.hasNext()){
				ResourceKey var2 = ((ResourceKey)(var1.next()));
				ResourceKey[] var3 = new ResourceKey[]{var2};
				this.addSourceImmunities(var3);
				continue;
			}else{
				break;
			}
		} while (true);
	}
	public boolean isImmune(ResourceKey<DamageType> var1) {
		Iterator var2 = this.sourceImmunities.iterator();
		do{
			if (var2.hasNext()){
				if (((java.lang.Object)(var1)).equals((Object)(((ResourceKey)(var2.next()))))){
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
	public boolean isImmune(DamageSource var0) { throw new RuntimeException("yak-decompiler: undecompilable method body"); /* yak-decompiler: ParseBytesCode failed: unsupported_source: lambda source body requires original synthetic declaration metadata */ }
	public <T extends SourceImmunityAbility$SourceImmunityInfo> T addSourceRule(Predicate<DamageSource> var1) {
		this.sourceRules.add(var1);
		return (T) (this);
	}
	public <T extends SourceImmunityAbility$SourceImmunityInfo> T addSourceImmunities(ResourceKey<DamageType>... var1) {
		((java.util.ArrayList)(this.sourceImmunities)).addAll((Collection)(Arrays.asList(var1)));
		return (T) (this);
	}
	public <T extends SourceImmunityAbility$SourceImmunityInfo> T addSourceResistance(ResourceKey<DamageType> var1, int var2) {
		this.sourceResistances.add(Pair.of(var1,Integer.valueOf(var2)));
		return (T) (this);
	}
	static  {
		ResourceKey[] var0 = new ResourceKey[]{DamageTypes.CACTUS,DamageTypes.SWEET_BERRY_BUSH,DamageTypes.FALLING_ANVIL,DamageTypes.FLY_INTO_WALL,DamageTypes.FALL,DamageTypes.FALLING_BLOCK};
		DEFAULT_LOGIA_IMMUNITIES = (List) (Arrays.asList(var0));
	}
}