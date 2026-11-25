package xyz.pixelatedw.mineminenomi.api.damagesources;

import java.util.ArrayList;
import java.util.Collection;
import net.minecraft.core.Holder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class BaseDamageSource extends DamageSource {
	// Fields
	private SourceElement element;
	private Collection<SourceType> sourceTypes;
	private SourceHakiNature hakiNature;

	public BaseDamageSource(Holder<DamageType> var1, @Nullable() Entity var2, @Nullable() Entity var3, @Nullable() Vec3 var4, @Nullable() SourceElement var5, @Nullable() Collection<SourceType> var6, @Nullable() SourceHakiNature var7) {
		super(var1,var2,var3,var4);
		this.element = ((var5) == (null)) ? (SourceElement.NONE) : (var5);
		this.sourceTypes = ((var6) == (null)) ? (new ArrayList()) : (var6);
		this.hakiNature = ((var7) == (null)) ? (SourceHakiNature.UNKNOWN) : (var7);
	}
	public BaseDamageSource(Holder<DamageType> var1, @Nullable() Entity var2, @Nullable() Entity var3, @Nullable() SourceElement var4, @Nullable() Collection<SourceType> var5, @Nullable() SourceHakiNature var6) {
		this(var1,var2,var3,((Vec3)(null)),var4,var5,var6);
	}
	public BaseDamageSource(Holder<DamageType> var1, Vec3 var2, @Nullable() SourceElement var3, @Nullable() Collection<SourceType> var4, @Nullable() SourceHakiNature var5) {
		this(var1,((Entity)(null)),((Entity)(null)),var2,var3,var4,var5);
	}
	public BaseDamageSource(Holder<DamageType> var1, @Nullable() Entity var2, @Nullable() SourceElement var3, @Nullable() Collection<SourceType> var4, @Nullable() SourceHakiNature var5) {
		this(var1,var2,var2,var3,var4,var5);
	}
	public BaseDamageSource(Holder<DamageType> var1, @Nullable() Entity var2) {
		this(var1,var2,var2,null,null,null);
	}
	public BaseDamageSource(Holder<DamageType> var1, @Nullable() SourceElement var2, @Nullable() Collection<SourceType> var3, @Nullable() SourceHakiNature var4) {
		this(var1,((Entity)(null)),((Entity)(null)),((Vec3)(null)),var2,var3,var4);
	}
	public BaseDamageSource(Holder<DamageType> var1) {
		this(var1,((Entity)(null)),((Entity)(null)),((Vec3)(null)),null,null,null);
	}
	public SourceElement getElement() {
		return this.element;
	}
	public Collection<SourceType> getTypes() {
		return new ArrayList(this.sourceTypes);
	}
	public void addType(SourceType var1) {
		if (!(this.sourceTypes.contains(var1))){
			this.sourceTypes.add(var1);
		}
	}
	public SourceHakiNature getHakiNature() {
		return this.hakiNature;
	}
}