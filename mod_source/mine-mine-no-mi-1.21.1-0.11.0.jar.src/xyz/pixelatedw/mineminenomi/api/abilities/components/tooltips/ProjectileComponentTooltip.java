package xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityStat$AbilityStatType;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityStat$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusManager;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public class ProjectileComponentTooltip {
	// Fields
	private boolean auto;
	private NuProjectileEntity projectile;
	private float minDamage;
	private float maxDamage;
	private float piercing;

	public ProjectileComponentTooltip() {
		this.auto = true;
	}
	public static void generate(AbilityTooltip var0, Consumer<ProjectileComponentTooltip> var1) {
		ProjectileComponentTooltip var2 = new ProjectileComponentTooltip();
		var1.accept(var2);
		ProjectileComponent var3 = ((ProjectileComponent)(var0.getAbility().getComponent(((AbilityComponentKey)(ModAbilityComponents.PROJECTILE.get()))).orElse(null)));
		float var6 = 0.0F;
		float var7 = 0.0F;
		if ((var2.auto) && ((var3) != (null))){
			var6 = var3.getCachedProjectile((LivingEntity)(var0.getPlayer())).getDamage();
			var7 = var6;
		}else{
			if ((var2.projectile) != (null)){
				var6 = var2.projectile.getDamage();
				var7 = var6;
			}else{
				var6 = var2.minDamage;
				var7 = var2.maxDamage;
			}
		}
		int var8 = ((var7) > (0F)) ? (1) : (0);
		int var9 = ((var2.piercing) > (0F)) ? (1) : (0);
		if (((((var8) != (0)) || ((var9) != (0))) ? (1) : (0)) == (0)){
			return;
		}else{
			LinkedList var10 = new LinkedList();
			String var11 = ModI18nAbilities.DESCRIPTION_STAT_NAME_PROJECTILE.getString();
			String var12 = "§a" + var11 + "§r";
			var10.add(Component.literal(var12));
			if ((var8) != (0)){
				AbilityStat$Builder var13 = new AbilityStat$Builder((Component)(ModI18nAbilities.DESCRIPTION_STAT_NAME_DAMAGE),(var6) * (0.4F),(var7) * (0.4F));
				if ((var3) != (null)){
					float var14 = (var3.getDamageBonusManager().applyBonus(var6)) - (var6);
					float var15 = (var3.getDamageBonusManager().applyBonus(var7)) - (var7);
					float var16 = (var14) + (var15);
					var13 = var13.withBonus(var14,var15,((var16) > (0F)) ? (AbilityStat$AbilityStatType.BUFF) : (((var16) < (0F)) ? (AbilityStat$AbilityStatType.DEBUFF) : (AbilityStat$AbilityStatType.NEUTRAL)));
				}
				var10.add(var13.buildComponent(2));
			}
			var0.set(((AbilityComponentKey)(ModAbilityComponents.PROJECTILE.get())),(Collection)(var10));
			return;
		}
	}
	public ProjectileComponentTooltip from(NuProjectileEntity var1) {
		this.projectile = var1;
		this.disableAuto();
		return this;
	}
	public ProjectileComponentTooltip disableAuto() {
		this.auto = false;
		return this;
	}
	public ProjectileComponentTooltip damage(float var1) {
		return this.damage(var1,var1);
	}
	public ProjectileComponentTooltip damage(float var1, float var2) {
		this.minDamage = var1;
		this.maxDamage = var2;
		this.disableAuto();
		return this;
	}
	public ProjectileComponentTooltip piercing(float var1) {
		this.piercing = var1;
		this.disableAuto();
		return this;
	}
}