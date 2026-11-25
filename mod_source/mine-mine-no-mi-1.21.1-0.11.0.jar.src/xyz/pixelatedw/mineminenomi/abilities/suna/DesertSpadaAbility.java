package xyz.pixelatedw.mineminenomi.abilities.suna;

import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine$IDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityRegistryEntry;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusManager;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusOperation;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.suna.DesertSpadaProjectile;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

public class DesertSpadaAbility extends Ability {
	// Fields
	private static final float COOLDOWN_BONUS = 0.8F;
	private static final float DAMAGE_BONUS = 1.5F;
	private static final int COOLDOWN = 160;
	public static final DeferredHolder<AbilityCore<?>, AbilityCore<DesertSpadaAbility>> INSTANCE;
	private final ProjectileComponent projectileComponent;

	public DesertSpadaAbility(AbilityCore<DesertSpadaAbility> var1) {
		super((AbilityCore)(var1));
		this.projectileComponent = new ProjectileComponent((IAbility)(this),(ProjectileComponent$IProjectileFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent$IProjectileFactory)((ProjectileComponent$IProjectileFactory) ((ProjectileComponent$IProjectileFactory<DesertSpadaProjectile>) (this::createProjectile))))));
		AbilityComponent[] var2 = new AbilityComponent[]{this.projectileComponent};
		this.addComponents(var2);
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::requiresOnGround))));
		this.addCanUseCheck((Ability$ICanUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$ICanUseEvent)(AbilityUseConditions::requiresDryUser))));
		this.addUseEvent((Ability$IOnUseEvent)(((xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent)(this::useEvent))));
	}
	private void useEvent(LivingEntity var1, IAbility var2) {
		var1.level().playSound((Player)(null),var1.blockPosition(),((SoundEvent)(ModSounds.DESERT_SPADA_SFX.get())),SoundSource.PLAYERS,2F,1F);
		boolean var3 = SunaHelper.isFruitBoosted(var1);
		this.projectileComponent.shoot(var1,3F,1F);
		this.cooldownComponent.getBonusManager().removeBonus(SunaHelper.DESERT_COOLDOWN_BONUS);
		if (var3){
			this.cooldownComponent.getBonusManager().addBonus(SunaHelper.DESERT_COOLDOWN_BONUS,"Desert Cooldown Bonus",BonusOperation.MUL,0.8F);
		}
		this.cooldownComponent.startCooldown(var1,160F);
	}
	private DesertSpadaProjectile createProjectile(LivingEntity var1) {
		boolean var2 = SunaHelper.isFruitBoosted(var1);
		this.projectileComponent.getDamageBonusManager().removeBonus(SunaHelper.DESERT_DAMAGE_BONUS);
		if (var2){
			this.projectileComponent.getDamageBonusManager().addBonus(SunaHelper.DESERT_DAMAGE_BONUS,"Desert Damage Bonus",BonusOperation.MUL,1.5F);
		}
		DesertSpadaProjectile var3 = new DesertSpadaProjectile(var1.level(),var1,(IAbility)(this));
		var3.setMaxLife((var2) ? (30) : (20));
		return var3;
	}
	static  {
		AbilityRegistryEntry var0 = (AbilityRegistryEntry) ((AbilityRegistryEntry<AbilityCore>) ((l0, l1) -> {
			Pair[] lv1_2 = new Pair[2];
			((java.lang.Object[]) (lv1_2))[0] = ImmutablePair.of((Object)("The user forms their hand into a blade and stabs it into the ground, creating a sand blade that destroys anything on its path."),(Object)(null));
			Object[] lv1_3 = new Object[2];
			int lv1_4 = Math.round(19.999998F);
			String lv1_5 = "§a" + lv1_4 + "%§r";
			lv1_3[0] = lv1_5;
			int lv1_6 = Math.round((Math.abs(-0.5F)) * (100F));
			String lv1_7 = "§a" + lv1_6 + "%§r";
			lv1_3[1] = lv1_7;
			((java.lang.Object[]) (lv1_2))[1] = ImmutablePair.of((Object)("While in a desert the cooldown of this ability is reduced by %s and the damage is increased by %s."),lv1_3);
			Component[] lv1_8 = AbilityHelper.registerDescriptionText("mineminenomi",l0,lv1_2);
			AbilityDescriptionLine$IDescriptionLine[] lv1_9 = new AbilityDescriptionLine$IDescriptionLine[]{AbilityDescriptionLine.NEW_LINE,CooldownComponent.getTooltip(160F)};
			SourceType[] lv1_10 = new SourceType[]{SourceType.SLASH};
			return new AbilityCore$Builder(l0,l1,AbilityCategory.DEVIL_FRUITS,(AbilityCore$IFactory)(((xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore$IFactory)(DesertSpadaAbility::new)))).addDescriptionLine(lv1_8).addAdvancedDescriptionLine(lv1_9).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceType(lv1_10).build("mineminenomi");
		}));
		INSTANCE = (DeferredHolder) (ModRegistry.registerAbility("desert_spada","Desert Spada",var0));
	}
}