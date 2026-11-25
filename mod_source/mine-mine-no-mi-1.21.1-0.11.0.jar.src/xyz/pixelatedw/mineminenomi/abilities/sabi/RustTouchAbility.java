package xyz.pixelatedw.mineminenomi.abilities.sabi;

import java.util.Iterator;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay$OverlayPart;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PunchAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.SkinOverlayComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.init.ModTags$Items;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class RustTouchAbility extends PunchAbility {
	// Fields
	private static final float COOLDOWN = 300F;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<RustTouchAbility>> INSTANCE;
	private static final AbilityOverlay OVERLAY;
	private final SkinOverlayComponent skinOverlayComponent;

	public RustTouchAbility(AbilityCore<RustTouchAbility> var1) {
		super((AbilityCore)(var1));
		this.skinOverlayComponent = new SkinOverlayComponent((IAbility)(this),OVERLAY,new AbilityOverlay[0]);
		AbilityComponent[] var2 = new AbilityComponent[]{this.skinOverlayComponent};
		this.addComponents(var2);
		this.continuousComponent.addStartEvent(100,(ContinuousComponent$IStartContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IStartContinuousEvent)(this::startContinuityEvent))));
		this.continuousComponent.addEndEvent(100,(ContinuousComponent$IEndContinuousEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent$IEndContinuousEvent)(this::endContinuityEvent))));
	}
	private void startContinuityEvent(LivingEntity var1, IAbility var2) {
		this.skinOverlayComponent.showAll(var1);
	}
	private void endContinuityEvent(LivingEntity var1, IAbility var2) {
		this.skinOverlayComponent.hideAll(var1);
	}
	public boolean onHitEffect(LivingEntity var1, LivingEntity var2, DamageSource var3) {
		var2.addEffect(new MobEffectInstance(MobEffects.WITHER,160,2));
		var2.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN,160,1));
		var2.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,160,1));
		Iterator var4 = ItemsHelper.getAllInventoryItems(var2).iterator();
		do{
			if (var4.hasNext()){
				ItemStack var5 = ((ItemStack)(var4.next()));
				if ((!(var5.isEmpty())) && (var5.is(ModTags$Items.RUSTY))){
					if (var5.isDamageableItem()){
						var5.hurtAndBreak((((var5.getMaxDamage()) / (3)) + (1)) + (1),var2,EquipmentSlot.MAINHAND);
						continue;
					}else{
						var5.shrink((1) + (this.random.nextInt(4)));
						continue;
					}
				}else{
					continue;
				}
			}else{
				break;
			}
		} while (true);
		WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.RUST_TOUCH.get())),(Entity)(var1),var2.getX(),var2.getY(),var2.getZ());
		return true;
	}
	public int getUseLimit() {
		return 1;
	}
	public float getPunchDamage() {
		return 10F;
	}
	public float getPunchCooldown() {
		return 300F;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[]{ImmutablePair.of((Object)("Rusts the enemy and the items equipped on them"),(Object)(null))};
			Component[] lv1_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			SourceType[] lv1_4 = new SourceType[]{SourceType.FIST};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(RustTouchAbility::new)))).addDescriptionLine(lv1_3).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(lv1_4).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("rust_touch","Rust Touch",var0));
		OVERLAY = new AbilityOverlay$Builder().setOverlayPart(AbilityOverlay$OverlayPart.LIMB).setTexture(ModResources.RUST_TOUCH_ARM).build();
	}
}