package xyz.pixelatedw.mineminenomi.abilities.gura;

import java.util.Iterator;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine$IDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PunchAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent$IChangeModeEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect$Details;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect$Details;

public class ShingenNoIchigekiAbility extends PunchAbility {
	// Fields
	private static final Component CROWD_NAME = Component.translatable(ModRegistry.registerAbilityName("shingen_no_ichigeki_crowd","Shingen no Ichigeki: Crowd"));
	private static final ResourceLocation SHINGEN_NO_ICHIGEKI_SINGLE_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/abilities/shingen_no_ichigeki.png");
	private static final ResourceLocation SHINGEN_NO_ICHIGEKI_CROWD_ICON = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/abilities/shingen_no_ichigeki_crowd.png");
	private static final float COOLDOWN = 200F;
	private static final float PUNCH_DAMAGE = 40F;
	private static final float AOE_DAMAGE = 10F;
	private static final int RANGE = 10;
	private static final int EXPLOSION_SIZE = 3;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<ShingenNoIchigekiAbility>> INSTANCE;
	private final AltModeComponent<ShingenNoIchigekiAbility$Mode> altModeComponent;
	private final DealDamageComponent dealDamageComponent;
	private final ExplosionComponent explosionComponent;
	private final RangeComponent rangeComponent;

	public ShingenNoIchigekiAbility(AbilityCore<ShingenNoIchigekiAbility> var1) {
		super((AbilityCore)(var1));
		this.altModeComponent = (AltModeComponent) (new AltModeComponent((IAbility)(this),ShingenNoIchigekiAbility$Mode.class,ShingenNoIchigekiAbility$Mode.SINGLE).addChangeModeEvent((AltModeComponent$IChangeModeEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent$IChangeModeEvent)(this::onAltModeChange)))));
		this.dealDamageComponent = new DealDamageComponent((IAbility)(this));
		this.explosionComponent = new ExplosionComponent((IAbility)(this));
		this.rangeComponent = new RangeComponent((IAbility)(this));
		AbilityComponent[] var2 = new AbilityComponent[]{this.altModeComponent,this.dealDamageComponent,this.explosionComponent,this.rangeComponent};
		this.addComponents(var2);
		this.addUseEvent(100,(Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::onUseEvent))));
	}
	private void onUseEvent(LivingEntity var1, IAbility var2) {
		if ((this.altModeComponent.getCurrentMode()) == (ShingenNoIchigekiAbility$Mode.SINGLE)){
			return;
		}else{
			if ((this.altModeComponent.getCurrentMode()) == (ShingenNoIchigekiAbility$Mode.CROWD)){
				AbilityExplosion var3 = this.explosionComponent.createExplosion((Entity)(var1),var1.getX(),var1.getY(),var1.getZ(),3F);
				var3.setDestroyBlocks(false);
				var3.setDamageEntities(false);
				var3.setSmokeParticles(((ParticleEffect)(ModParticleEffects.COMMON_EXPLOSION.get())),(ParticleEffect$Details)(CommonExplosionParticleEffect.EXPLOSION4));
				var3.explode();
				List var4 = this.rangeComponent.getTargetsInArea(var1,10F);
				var4.remove(var1);
				Iterator var5 = var4.iterator();
				do{
					if (var5.hasNext()){
						LivingEntity var6 = ((LivingEntity)(var5.next()));
						if (this.dealDamageComponent.hurtTarget(var1,var6,10F)){
							Vec3 var7 = var1.position().subtract(var6.position()).normalize().multiply(4D,1D,4D);
							AbilityHelper.setDeltaMovement((Entity)(var6),-(var7.x),(0.5D) + (var7.y),-(var7.z));
							continue;
						}else{
							continue;
						}
					}else{
						break;
					}
				} while (true);
				this.cooldownComponent.startCooldown(var1,200F);
			}
			return;
		}
	}
	public boolean onHitEffect(LivingEntity var1, LivingEntity var2, DamageSource var3) {
		IDamageSourceHandler var4 = IDamageSourceHandler.getHandler(var3);
		var4.bypassLogia();
		var4.addAbilityPiercing(0.75F,this.getCore());
		AbilityExplosion var5 = this.explosionComponent.createExplosion((Entity)(var1),var2.getX(),var2.getY(),var2.getZ(),3F);
		var5.setStaticDamage(10F);
		var5.setDestroyBlocks(false);
		var5.setDamageEntities(false);
		var5.setSmokeParticles(((ParticleEffect)(ModParticleEffects.COMMON_EXPLOSION.get())),(ParticleEffect$Details)(CommonExplosionParticleEffect.EXPLOSION4));
		var5.explode();
		Vec3 var6 = var1.position().subtract(var2.position()).normalize().multiply(2D,1D,2D);
		AbilityHelper.setDeltaMovement((Entity)(var2),-(var6.x),(0.5D) + (var6.y),-(var6.z));
		var2.addEffect(new MobEffectInstance((Holder)(ModEffects.DIZZY),100,0,false,false));
		return false;
	}
	private boolean onAltModeChange(LivingEntity var1, IAbility var2, ShingenNoIchigekiAbility$Mode var3) {
		if ((var3) == (ShingenNoIchigekiAbility$Mode.SINGLE)){
			this.setDisplayIcon(SHINGEN_NO_ICHIGEKI_SINGLE_ICON);
		}else{
			if ((var3) == (ShingenNoIchigekiAbility$Mode.CROWD)){
				this.setDisplayIcon(SHINGEN_NO_ICHIGEKI_CROWD_ICON);
			}else{
				return true;
			}
		}
		return true;
	}
	public float getPunchCooldown() {
		return 200F;
	}
	public int getUseLimit() {
		return 1;
	}
	public float getPunchDamage() {
		return 40F;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv5_2 = new Pair[]{ImmutablePair.of((Object)("The user focuses vibrations around their fist in an spherical bubble, releasing when hitting an enemy."),(Object)(null)),ImmutablePair.of((Object)("The user slams their fist on the ground pushing all nearby enemies."),(Object)(null))};
			Component[] lv5_3 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv5_2);
			AbilityDescriptionLine$IDescriptionLine[] lv5_4 = new AbilityDescriptionLine$IDescriptionLine[4];
			String lv5_5 = l1;
			final String lv5_5_f1 = lv5_5;
			AbilityDescriptionLine$IDescriptionLine lv5_6 = (l2_0, l2_1) -> {
				return Component.literal(lv5_5_f1).setStyle(Style.EMPTY.withColor(ChatFormatting.GREEN));
			};
			lv5_4[0] = lv5_6;
			Component[] lv5_7 = lv5_3;
			final Component[] lv5_7_f2 = lv5_7;
			AbilityDescriptionLine$IDescriptionLine lv5_8 = (l2_0, l2_1) -> {
				return lv5_7_f2[0];
			};
			lv5_4[1] = lv5_8;
			lv5_4[2] = CooldownComponent.getTooltip(200F);
			lv5_4[3] = DealDamageComponent.getTooltip(40F);
			AbilityDescriptionLine$IDescriptionLine[] lv5_9 = new AbilityDescriptionLine$IDescriptionLine[5];
			lv5_9[0] = AbilityDescriptionLine.NEW_LINE;
			AbilityDescriptionLine$IDescriptionLine lv5_10 = (l2_0, l2_1) -> {
				return CROWD_NAME.copy().setStyle(Style.EMPTY.withColor(ChatFormatting.GREEN));
			};
			lv5_9[1] = lv5_10;
			Component[] lv5_11 = lv5_3;
			final Component[] lv5_11_f3 = lv5_11;
			AbilityDescriptionLine$IDescriptionLine lv5_12 = (l2_0, l2_1) -> {
				return lv5_11_f3[1];
			};
			lv5_9[2] = lv5_12;
			lv5_9[3] = CooldownComponent.getTooltip(200F);
			lv5_9[4] = DealDamageComponent.getTooltip(5F);
			SourceType[] lv5_13 = new SourceType[]{SourceType.FIST};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(ShingenNoIchigekiAbility::new)))).addDescriptionLine(lv5_4).addDescriptionLine(lv5_9).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(lv5_13).setSourceElement(SourceElement.SHOCKWAVE).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("shingen_no_ichigeki","Shingen no Ichigeki",var0));
	}
}