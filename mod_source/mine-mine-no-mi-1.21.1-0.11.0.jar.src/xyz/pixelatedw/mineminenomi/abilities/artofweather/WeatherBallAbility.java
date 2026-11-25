package xyz.pixelatedw.mineminenomi.abilities.artofweather;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.abilities.artofweather.tempos.ChargedTempoAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityPool;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.WeatherBallKind;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PoolComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips.CooldownComponentTooltip;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.artofweather.WeatherBallProjectile;
import xyz.pixelatedw.mineminenomi.init.ModAbilityPools;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public abstract class WeatherBallAbility extends Ability {
	// Fields
	private static final float COOLDOWN = 10F;
	private static final Predicate<IAbility> CHARGED_TEMPOS_CHECK;
	private final PoolComponent poolComponent;

	public WeatherBallAbility(AbilityCore<? extends WeatherBallAbility> var1) {
		super((AbilityCore)(var1));
		this.poolComponent = new PoolComponent((IAbility)(this),ModAbilityPools.WEATHER_BALLS,new AbilityPool[0]);
		AbilityComponent[] var2 = new AbilityComponent[]{this.poolComponent};
		this.addComponents(var2);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::requiresClimaTact))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::onUseEvent))));
	}
	private void onUseEvent(LivingEntity var1, IAbility var2) {
		Vec3 var3 = var1.position().add(var1.getLookAngle());
		ItemStack var4 = var1.getMainHandItem();
		if (var4.isEmpty()){
			this.cooldownComponent.startCooldown(var1,10F);
			return;
		}else{
			var1.getMainHandItem().hurtAndBreak(1,var1,EquipmentSlot.MAINHAND);
			if (var1.isCrouching()){
				this.checkForChargeTempo(var1,var4,var3);
			}else{
				WeatherBallProjectile var5 = this.getWeatherBallEntity(var1,var3);
				var1.level().addFreshEntity((Entity)(var5));
			}
			this.cooldownComponent.startCooldown(var1,10F);
			return;
		}
	}
	private void checkForChargeTempo(LivingEntity var1, ItemStack var2, Vec3 var3) {
		AbilityData var4 = ((AbilityData)(((java.util.Optional)(AbilityData.get(var1))).orElse((Object)(null))));
		if ((var4) == (null)){
			return;
		}else{
			if (!(ArtOfWeatherHelper.chargeWeatherBall(var2,this.getKind()))){
				return;
			}else{
				WyHelper.sendMessage(var1,(Component)(Component.literal(ArtOfWeatherHelper.getChargesString(var2))));
				WyHelper.spawnParticleEffect(this.getParticleEffect(),(Entity)(var1),var3.x(),(var1.getEyeY()) - (1D),var3.z());
				WeatherBallKind[] var5 = ArtOfWeatherHelper.getCharges(var2);
				if ((var5.length) > (0)){
					Set var6 = var4.getPassiveAbilities(CHARGED_TEMPOS_CHECK);
					if ((var6.size()) <= (0)){
						this.failedTempo(var1,var3,var2);
						return;
					}else{
						boolean var7 = true;
						Iterator var8 = var6.iterator();
						do{
							if (var8.hasNext()){
								IAbility var9 = ((IAbility)(var8.next()));
								if ((Arrays.equals((Object[])(var5),(Object[])(((ChargedTempoAbility)(var9)).getTempoOrder()))) && (var9.canUse(var1).isSuccess())){
									var9.use(var1);
									ArtOfWeatherHelper.emptyCharges(var2);
									var7 = false;
									continue;
								}else{
									continue;
								}
							}else{
								break;
							}
						} while (true);
						if (var7){
							this.failedTempo(var1,var3,var2);
						}else{
							return;
						}
					}
				}
				return;
			}
		}
	}
	private void failedTempo(LivingEntity var1, Vec3 var2, ItemStack var3) {
		WyHelper.spawnParticleEffect(((ParticleEffect)(ModParticleEffects.FAILED_TEMPO.get())),(Entity)(var1),var2.x(),var1.getY(),var2.z());
		ArtOfWeatherHelper.emptyCharges(var3);
	}
	public abstract WeatherBallKind getKind();
	public abstract ParticleEffect<?> getParticleEffect();
	public abstract WeatherBallProjectile getWeatherBallEntity(LivingEntity var0, Vec3 var1);
	static  {
		Predicate<IAbility> var0 = (l0) -> {
			if ((AbilityCategory.STYLE.isAbilityPartofCategory().test(l0)) && (l0 instanceof ChargedTempoAbility)){
				return true;
			}else{
				return false;
			}
		};
		CHARGED_TEMPOS_CHECK = var0;
	}
	public void appendDescription(Player var1, AbilityTooltip var2) {
		super.appendDescription(var1,var2);
		Consumer<CooldownComponentTooltip> var3 = (l0) -> {
			l0.cooldown(10F);
		};
		CooldownComponentTooltip.generate(var2,var3);
	}
}