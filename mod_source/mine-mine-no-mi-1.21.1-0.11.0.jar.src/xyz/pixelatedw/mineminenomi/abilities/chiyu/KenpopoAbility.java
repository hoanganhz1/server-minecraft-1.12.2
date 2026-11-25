package xyz.pixelatedw.mineminenomi.abilities.chiyu;

import java.util.function.Predicate;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PunchAbility;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModEntityPredicates;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.items.DandelionItem;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class KenpopoAbility extends PunchAbility {
	// Fields
	private static final float COOLDOWN = 1000F;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<KenpopoAbility>> INSTANCE;

	public KenpopoAbility(AbilityCore<KenpopoAbility> var1) {
		super((AbilityCore)(var1));
		this.clearUseChecks();
	}
	public int getUseLimit() {
		return 1;
	}
	public float getPunchCooldown() {
		return 1000F;
	}
	public boolean onHitEffect(LivingEntity var1, LivingEntity var2, DamageSource var3) {
		Predicate var4 = ModEntityPredicates.getFriendlyFactions(var1);
		if (((var2.getEffect(MobEffects.WEAKNESS)) == (null)) && (var4.test(var2))){
			float var5 = (var2.getHealth()) * (0.25F);
			ItemStack var6 = new ItemStack(((ItemLike)(ModItems.DANDELION.get())));
			DandelionItem.setHealAmount(var6,var5);
			if (var1 instanceof Player){
				((Player)(var1)).addItem(var6);
			}
			var2.setHealth((var2.getHealth()) - (var5));
			var2.addEffect(new MobEffectInstance(MobEffects.WEAKNESS,1800,0));
			WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.CHIYUPOPO.get())),(Entity)(var1),var1.getX(),var1.getY(),var1.getZ());
			return false;
		}else{
			return false;
		}
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Takes an ally's life force, transforming it into a Dandelion. Dandelions can be eaten for healing"),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			SourceType[] lv1_4 = new SourceType[]{SourceType.FIST,SourceType.FRIENDLY};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(KenpopoAbility::new)))).addDescriptionLine(lv1_3).setSourceType(lv1_4).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("kenpopo","Kenpopo",var0));
	}
}