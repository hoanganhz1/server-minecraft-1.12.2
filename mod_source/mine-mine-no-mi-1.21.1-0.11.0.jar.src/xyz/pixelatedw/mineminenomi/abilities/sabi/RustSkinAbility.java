package xyz.pixelatedw.mineminenomi.abilities.sabi;

import java.util.Optional;
import java.util.function.Function;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiImbuingAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine$IDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility$IDuringPassiveEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent$IGetHitEvent;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModEnchantments;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModTags$Items;
import xyz.pixelatedw.mineminenomi.items.weapons.AbilitySwordItem;
import xyz.pixelatedw.mineminenomi.items.weapons.ModSwordItem;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class RustSkinAbility extends PassiveAbility {
	// Fields
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<RustSkinAbility>> INSTANCE;
	private final DamageTakenComponent damageTakenComponent;

	public RustSkinAbility(AbilityCore<RustSkinAbility> var1) {
		super((AbilityCore)(var1));
		this.damageTakenComponent = new DamageTakenComponent((IAbility)(this)).addOnAttackEvent((DamageTakenComponent$IGetHitEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent$IGetHitEvent)(this::damageTakenEvent))));
		AbilityComponent[] var2 = new AbilityComponent[]{this.damageTakenComponent};
		this.addComponents(var2);
		super.addDuringPassiveEvent((PassiveAbility$IDuringPassiveEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility$IDuringPassiveEvent)(this::onDuringPassive))));
	}
	private void onDuringPassive(LivingEntity var1) {
		if (var1.hasEffect((Holder)(ModEffects.PUNK_CROSS))){
			var1.removeEffect((Holder)(ModEffects.PUNK_CROSS));
		}
		if (var1.hasEffect((Holder)(ModEffects.GENOCIDE_RAID))){
			var1.removeEffect((Holder)(ModEffects.GENOCIDE_RAID));
		}
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv2_2 = new Pair[]{ImmutablePair.of((Object)("Makes the user immune to attacks received from iron based items, damaging them in the processes"),(Object)(null))};
			Component[] lv2_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv2_2);
			AbilityDescriptionLine$IDescriptionLine[] lv2_4 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,AbilityType.PASSIVE,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(RustSkinAbility::new)))).addDescriptionLine(lv2_3).addAdvancedDescriptionLine(lv2_4).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("rust_skin","Rust Skin",var0));
	}

	public float damageTakenEvent(LivingEntity var1, IAbility var2, DamageSource var3, float var4) {
		if (this.isPaused()){
			return var4;
		}else{
			Entity var5 = var3.getDirectEntity();
			Entity var6 = var3.getEntity();
			if (((var5) != (null)) && ((var5 instanceof Projectile) && (IDamageSourceHandler.getHandler(var3).hasElement(SourceElement.METAL)))){
				var5.kill();
				WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.RUST_SKIN.get())),(Entity)(var1),var1.getX(),var1.getY(),var1.getZ());
				return 0F;
			}else{
				if (((var6) != (null)) && (var6 instanceof LivingEntity)){
					LivingEntity var7 = ((LivingEntity)(var6));
					if (IDamageSourceHandler.getHandler(var3).hasElement(SourceElement.METAL)){
						WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.RUST_SKIN.get())),(Entity)(var1),var1.getX(),var1.getY(),var1.getZ());
						return 0F;
					}else{
						Function<AbilityData, BusoshokuHakiImbuingAbility> var8 = (l0) -> {
			return ((BusoshokuHakiImbuingAbility)(l0.getEquippedAbility(((AbilityCore)(BusoshokuHakiImbuingAbility.INSTANCE.get())))));
		};
						if (((Boolean)(((java.util.Optional)(AbilityData.get(var1))).map((Function)(var8)).map((Function)(((Function<BusoshokuHakiImbuingAbility, Boolean>)(Ability::isContinuous)))).orElse(Boolean.valueOf(false)))).booleanValue()){
							return var4;
						}else{
							boolean var17 = false;
							ItemStack var9 = var7.getItemBySlot(EquipmentSlot.MAINHAND);
							ItemStack var10 = var7.getItemBySlot(EquipmentSlot.OFFHAND);
							ItemStack var11 = null;
							Holder.Reference var12 = var1.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(ModEnchantments.KAIROSEKI);
							int var13 = ((var9.is(ModTags$Items.RUSTY)) && ((var9.getEnchantmentLevel((Holder)(var12))) <= (0))) ? (1) : (0);
							int var14 = ((var10.is(ModTags$Items.RUSTY)) && ((var10.getEnchantmentLevel((Holder)(var12))) <= (0))) ? (1) : (0);
							if (((var13) == (0)) && ((var14) == (0))){

							}else{
								var11 = var9;
							}
							Item var15 = var9.getItem();
							Item var16 = var10.getItem();
							if ((var15 instanceof ModSwordItem) && (!(var15 instanceof AbilitySwordItem))){
								var17 = ItemsHelper.isImmuneToRust(var7,var9);
								if (!(var17)){
									var11 = var9;
								}
							}
							if ((var16 instanceof ModSwordItem) && (!(var16 instanceof AbilitySwordItem))){
								var17 = ItemsHelper.isImmuneToRust(var7,var10);
								if (!(var17)){
									var11 = var10;
								}
							}
							if ((var11) != (null)){
								if (var11.isDamageableItem()){
									var11.hurtAndBreak(((var11.getMaxDamage()) / (4)) + (1),var1,EquipmentSlot.MAINHAND);
								}else{
									var11.shrink((1) + (this.random.nextInt(8)));
								}
								WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.RUST_SKIN.get())),(Entity)(var1),var1.getX(),var1.getY(),var1.getZ());
								return 0F;
							}
						}
					}
				}
				return var4;
			}
		}
	}}