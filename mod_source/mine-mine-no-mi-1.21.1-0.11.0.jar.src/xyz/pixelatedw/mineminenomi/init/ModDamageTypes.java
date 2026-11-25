package xyz.pixelatedw.mineminenomi.init;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;

public class ModDamageTypes {
	// Fields
	public static final ResourceKey<DamageType> HEART_ATTACK = register("heart_attack","%1$s died due to a heart attack");
	public static final ResourceKey<DamageType> DEVILS_CURSE = register("devils_curse","%1$s died while trying to eat multiple Devil Fruits");
	public static final ResourceKey<DamageType> SOULBOUND = register("souldbound","%1$s died because their soulbound item got destoyed","%1$s died because their soulbound item got destoyed by %2$s");
	public static final ResourceKey<DamageType> OUT_OF_BODY = register("out_of_body","%1$s's body was killed");
	public static final ResourceKey<DamageType> FROSTBITES = register("frostbites","%1$s freezed to death");
	public static final ResourceKey<DamageType> FROZEN = register("frozen","%1$s freezed to death");
	public static final ResourceKey<DamageType> POISON = register("poison","%1$s died due to poison");
	public static final ResourceKey<DamageType> GRAVITY = register("gravity","%1$s died due to gravity");
	public static final ResourceKey<DamageType> SUN_INCINERATION = register("sun_incineration","%1$s was killed by the sun");
	public static final ResourceKey<DamageType> STORED_DAMAGE = register("stored_damage","%1$s got killed after waking up from their drunkenness");
	public static final ResourceKey<DamageType> BLEED = register("bleed","%1$s bled to death");
	public static final ResourceKey<DamageType> ABILITY = register("ability","%1$s was killed by %2$s","%1$s was killed by %2$s using %3$s");
	public static final ResourceKey<DamageType> REJECT_DIAL = register("reject_dial","%1$s was killed by a Reject Dial","%1$s was killed by %2$s using a Reject Dial");
	public static final ResourceKey<DamageType> PROJECTILE = register("projectile","%1$s was shot dead","%1$s was killed by %2$s");

	public static void bootstrap(BootstrapContext<DamageType> var0) {
		var0.register(HEART_ATTACK,new DamageType("heart_attack",DamageScaling.NEVER,0F));
		var0.register(DEVILS_CURSE,new DamageType("devils_curse",DamageScaling.NEVER,0F));
		var0.register(SOULBOUND,new DamageType("souldbound",DamageScaling.NEVER,0F));
		var0.register(OUT_OF_BODY,new DamageType("out_of_body",DamageScaling.NEVER,0F));
		var0.register(FROSTBITES,new DamageType("frostbites",DamageScaling.NEVER,0F));
		var0.register(FROZEN,new DamageType("frozen",DamageScaling.NEVER,0F));
		var0.register(POISON,new DamageType("poison",DamageScaling.NEVER,0F));
		var0.register(GRAVITY,new DamageType("gravity",DamageScaling.NEVER,0F));
		var0.register(SUN_INCINERATION,new DamageType("sun_incineration",DamageScaling.NEVER,0F));
		var0.register(STORED_DAMAGE,new DamageType("stored_damage",DamageScaling.NEVER,0F));
		var0.register(BLEED,new DamageType("bleed",DamageScaling.NEVER,0F));
		var0.register(ABILITY,new DamageType("ability",DamageScaling.NEVER,0F));
		var0.register(REJECT_DIAL,new DamageType("reject_dial",DamageScaling.NEVER,0F));
		var0.register(PROJECTILE,new DamageType("projectile",DamageScaling.NEVER,0F));
	}
	private static ResourceKey<DamageType> register(String var0, String var1) {
		return register(var0,var1,var1);
	}
	private static ResourceKey<DamageType> register(String var0, String var1, String var2) {
		String var3 = var0;
		String var4 = "death.attack." + var3;
		ModRegistry.registerName(var4,var1);
		String var5 = var0;
		String var6 = "death.attack." + var5 + ".item";
		ModRegistry.registerName(var6,var1);
		String var7 = var0;
		String var8 = "death.attack." + var7 + ".player";
		ModRegistry.registerName(var8,var2);
		return register(var0);
	}
	private static ResourceKey<DamageType> register(String var0) {
		return ResourceKey.create(Registries.DAMAGE_TYPE,ResourceLocation.fromNamespaceAndPath("mineminenomi",var0));
	}
	public static void init() {
	}
}