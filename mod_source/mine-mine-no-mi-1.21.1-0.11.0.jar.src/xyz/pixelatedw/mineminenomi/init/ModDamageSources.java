package xyz.pixelatedw.mineminenomi.init;

import java.util.ArrayList;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LevelAccessor;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.damagesources.AbilityDamageSource;
import xyz.pixelatedw.mineminenomi.api.damagesources.BaseDamageSource;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;

public class ModDamageSources {
	// Fields
	private static final Map<RegistryAccess, ModDamageSources> CACHE = new IdentityHashMap();
	private final Registry<DamageType> registry;
	private final DamageSource devilsCurse;
	private final DamageSource outOfBody;
	private final BaseDamageSource frostbites;
	private final BaseDamageSource frozen;
	private final BaseDamageSource poison;
	private final BaseDamageSource gravity;
	private final BaseDamageSource sunIncineration;
	private final DamageSource storedDamage;
	private final DamageSource bleed;

	public static final void init(LevelAccessor var0) {
		getInstance(var0);
	}
	public static final ModDamageSources getInstance(LevelAccessor var0) {
		return ((ModDamageSources)(((java.util.Map)(CACHE)).computeIfAbsent((Object)(var0.registryAccess()),(Function)(((java.util.function.Function)(((Function<RegistryAccess, ModDamageSources>)(ModDamageSources::new))))))));
	}
	public ModDamageSources(RegistryAccess var1) {
		this.registry = (Registry) (var1.registryOrThrow(Registries.DAMAGE_TYPE));
		this.devilsCurse = this.genericSource(ModDamageTypes.DEVILS_CURSE);
		this.outOfBody = this.genericSource(ModDamageTypes.OUT_OF_BODY);
		this.frostbites = this.genericBaseSource(ModDamageTypes.FROSTBITES,SourceElement.ICE,(ArrayList)(null),(SourceHakiNature)(null));
		this.frozen = this.genericBaseSource(ModDamageTypes.FROZEN,SourceElement.ICE,(ArrayList)(null),(SourceHakiNature)(null));
		this.poison = this.genericBaseSource(ModDamageTypes.POISON,SourceElement.POISON,(ArrayList)(null),(SourceHakiNature)(null));
		this.gravity = this.genericBaseSource(ModDamageTypes.GRAVITY,SourceElement.GRAVITY,(ArrayList)(null),(SourceHakiNature)(null));
		this.sunIncineration = this.genericBaseSource(ModDamageTypes.SUN_INCINERATION,SourceElement.FIRE,(ArrayList)(null),(SourceHakiNature)(null));
		this.storedDamage = this.genericSource(ModDamageTypes.STORED_DAMAGE);
		this.bleed = this.genericSource(ModDamageTypes.BLEED);
	}
	private BaseDamageSource genericBaseSource(ResourceKey<DamageType> var1, @Nullable() SourceElement var2, @Nullable() ArrayList<SourceType> var3, @Nullable() SourceHakiNature var4) {
		return new BaseDamageSource((Holder)(((net.minecraft.core.Registry)(this.registry)).getHolderOrThrow((ResourceKey)(var1))),var2,var3,var4);
	}
	private DamageSource genericSource(ResourceKey<DamageType> var1) {
		return new DamageSource((Holder)(((net.minecraft.core.Registry)(this.registry)).getHolderOrThrow((ResourceKey)(var1))));
	}
	private DamageSource genericSource(ResourceKey<DamageType> var1, @Nullable() Entity var2) {
		return new DamageSource((Holder)(((net.minecraft.core.Registry)(this.registry)).getHolderOrThrow((ResourceKey)(var1))),var2);
	}
	public DamageSource heartAttack(@Nullable() LivingEntity var1) {
		return this.genericSource(ModDamageTypes.HEART_ATTACK,(Entity)(var1));
	}
	public DamageSource devilsCurse() {
		return this.devilsCurse;
	}
	public DamageSource souldbound(@Nullable() LivingEntity var1) {
		return this.genericSource(ModDamageTypes.SOULBOUND,(Entity)(var1));
	}
	public DamageSource outOfBody() {
		return this.outOfBody;
	}
	public BaseDamageSource frostbites() {
		return this.frostbites;
	}
	public BaseDamageSource frozen() {
		return this.frozen;
	}
	public BaseDamageSource poison() {
		return this.poison;
	}
	public BaseDamageSource gravity() {
		return this.gravity;
	}
	public BaseDamageSource sunIncineration() {
		return this.sunIncineration;
	}
	public DamageSource storedDamage() {
		return this.storedDamage;
	}
	public DamageSource bleed() {
		return this.bleed;
	}
	public AbilityDamageSource ability(@Nullable() Entity var1, AbilityCore<?> var2) {
		return new AbilityDamageSource((Holder)(this.registry.getHolderOrThrow(ModDamageTypes.ABILITY)),var1,var2);
	}
	public AbilityDamageSource ability(@Nullable() Entity var1, @Nullable() Entity var2, AbilityCore<?> var3) {
		return new AbilityDamageSource((Holder)(this.registry.getHolderOrThrow(ModDamageTypes.ABILITY)),var1,var2,var3);
	}
	public BaseDamageSource projectile(NuProjectileEntity var1, @Nullable() Entity var2) {
		return new BaseDamageSource((Holder)(this.registry.getHolderOrThrow(ModDamageTypes.PROJECTILE)),(Entity)(var1),var2,var1.getSourceElement(),(Collection)(var1.getSourceTypes()),var1.getSourceHakiNature());
	}
	public DamageSource rejectDial(@Nullable() Entity var1) {
		return new DamageSource((Holder)(this.registry.getHolderOrThrow(ModDamageTypes.REJECT_DIAL)),var1);
	}
}