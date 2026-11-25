package xyz.pixelatedw.mineminenomi.api.helpers;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine$IDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityStat$AbilityStatType;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityStat$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusManager;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public class AbilityTooltipsHelper {
	// Fields
	public static final Component NEW_LINE = Component.literal(" ");
	public static final Component DOUBLE_NEW_LINE = Component.literal("\n");
	public static final int RED_COLOR = 16399420;

	@SafeVarargs()
	public static String[] registerDescriptionText(String var0, String var1, String... var2) {
		int var3 = 0;
		do{
			if ((var3) < (var2.length)){
				String var4 = String.format("ability.%s.%s.description.%s",new Object[]{var0,var1,Integer.valueOf(var3)});
				String var5 = ModRegistry.registerName(var4,var2[var3]);
				var2[var3] = var5;
				var3++;
				continue;
			}else{
				break;
			}
		} while (true);
		return var2;
	}
	public static void populateDescription(AbilityTooltip var0, IAbility var1, Object... var2) {
		Object[][] var3 = new Object[][]{var2};
		populateDescription(var0,var1,var3);
	}
	public static void populateDescription(AbilityTooltip var0, IAbility var1, Object[][] var2) {
		String[] var3 = var1.getCore().getDescriptionKeys();
		int var4 = 0;
		LOOP_1:
		do{
			if ((var4) < (var3.length)){
				String var5 = var3[var4];
				Object[] var6 = new Object[var2[var4].length];
				int var7 = 0;
				do{
					if ((var7) < (var2[var4].length)){
						MutableComponent var8 = MentionHelper.parseAndMention(var2[var4][var7]);
						if ((var8) == (null)){

						}else{
							var6[var7] = var8;
						}
						var7++;
						continue;
					}else{
						break;
					}
				} while (true);
				var0.add((Component)(Component.translatable(var5,var6)));
				var4++;
				continue LOOP_1;
			}else{
				break;
			}
		} while (true);
	}
	public static void generateWaterBuffedProjectileDamageStat(AbilityTooltip var0, IAbility var1, float var2, float var3) {
		float var4 = (var2) * (var3);
		AbilityStat$Builder var5 = new AbilityStat$Builder((Component)(ModI18nAbilities.DESCRIPTION_STAT_NAME_BASE_DAMAGE),(var2) * (0.4F));
		AbilityStat$Builder var6 = new AbilityStat$Builder((Component)(ModI18nAbilities.DESCRIPTION_STAT_NAME_WATER_DAMAGE),(var4) * (0.4F));
		float var7 = var2;
		AbilityStat$Builder var8 = var5;
		float var9 = var4;
		AbilityStat$Builder var10 = var6;
		final float var7_f1 = var7;
		final AbilityStat$Builder var8_f2 = var8;
		final float var9_f3 = var9;
		final AbilityStat$Builder var10_f4 = var10;
		Consumer<ProjectileComponent> var11 = (l0) -> {
			float lv1_5 = (l0.getDamageBonusManager().applyBonus(var7_f1)) - (var7_f1);
			var8_f2.withBonus(lv1_5,((lv1_5) > (0F)) ? (AbilityStat$AbilityStatType.BUFF) : (((lv1_5) < (0F)) ? (AbilityStat$AbilityStatType.DEBUFF) : (AbilityStat$AbilityStatType.NEUTRAL)));
			float lv1_6 = (l0.getDamageBonusManager().applyBonus(var9_f3)) - (var9_f3);
			var10_f4.withBonus(lv1_6,((lv1_6) > (0F)) ? (AbilityStat$AbilityStatType.BUFF) : (((lv1_6) < (0F)) ? (AbilityStat$AbilityStatType.DEBUFF) : (AbilityStat$AbilityStatType.NEUTRAL)));
		};
		((java.util.Optional)(var1.getComponent(((AbilityComponentKey)(ModAbilityComponents.PROJECTILE.get()))))).ifPresent((Consumer)(var11));
		Component var12 = var5.buildComponent(2);
		Component var13 = var6.buildComponent(2);
		StringBuilder var14 = new StringBuilder();
		String var15 = ModI18nAbilities.DESCRIPTION_STAT_NAME_PROJECTILE.getString();
		String var16 = "§a" + var15 + "§r\n";
		var14.append(var16);
		String var17 = var12.getString();
		String var18 = var17 + "\n";
		var14.append(var18);
		var14.append(var13.getString());
		var0.add((Component)(Component.literal(var14.toString())));
	}
	public static void generateWaterBuffedDamageStat(AbilityTooltip var0, IAbility var1, float var2, float var3) {
		float var4 = (var2) * (var3);
		AbilityStat$Builder var5 = new AbilityStat$Builder((Component)(ModI18nAbilities.DESCRIPTION_STAT_NAME_BASE_DAMAGE),(var2) * (0.4F),(var2) * (0.4F));
		AbilityStat$Builder var6 = new AbilityStat$Builder((Component)(ModI18nAbilities.DESCRIPTION_STAT_NAME_WATER_DAMAGE),(var4) * (0.4F),(var4) * (0.4F));
		float var7 = var2;
		AbilityStat$Builder var8 = var5;
		float var9 = var4;
		AbilityStat$Builder var10 = var6;
		final float var7_f1 = var7;
		final AbilityStat$Builder var8_f2 = var8;
		final float var9_f3 = var9;
		final AbilityStat$Builder var10_f4 = var10;
		Consumer<DealDamageComponent> var11 = (l0) -> {
			float lv2_5 = (l0.getBonusManager().applyBonus(var7_f1)) - (var7_f1);
			var8_f2.withBonus(lv2_5,((lv2_5) > (0F)) ? (AbilityStat$AbilityStatType.BUFF) : (((lv2_5) < (0F)) ? (AbilityStat$AbilityStatType.DEBUFF) : (AbilityStat$AbilityStatType.NEUTRAL)));
			float lv2_6 = (l0.getBonusManager().applyBonus(var9_f3)) - (var9_f3);
			var10_f4.withBonus(lv2_6,((lv2_6) > (0F)) ? (AbilityStat$AbilityStatType.BUFF) : (((lv2_6) < (0F)) ? (AbilityStat$AbilityStatType.DEBUFF) : (AbilityStat$AbilityStatType.NEUTRAL)));
		};
		((java.util.Optional)(var1.getComponent(((AbilityComponentKey)(ModAbilityComponents.DAMAGE.get()))))).ifPresent((Consumer)(var11));
		Component var12 = var5.buildComponent();
		Component var13 = var6.buildComponent();
		StringBuilder var14 = new StringBuilder();
		String var15 = var12.getString();
		String var16 = var15 + "\n";
		var14.append(var16);
		var14.append(var13.getString());
		var0.add((Component)(Component.literal(var14.toString())));
	}
	public static void generateShortLongCooldownStat(AbilityTooltip var0, IAbility var1, float var2, float var3) {
		Object[] var4 = new Object[]{ModI18nAbilities.DESCRIPTION_STAT_UNIT_SECONDS.getString(),ModI18nAbilities.DESCRIPTION_STAT_UNIT_PER_STACK.getString()};
		MutableComponent var5 = Component.translatable("%s %s",var4);
		float var6 = (var2) / (20F);
		float var7 = (var3) / (20F);
		AbilityStat$Builder var8 = new AbilityStat$Builder((Component)(ModI18nAbilities.DESCRIPTION_STAT_NAME_SHORT_COOLDOWN),var6).withUnit((Component)(var5));
		AbilityStat$Builder var9 = new AbilityStat$Builder((Component)(ModI18nAbilities.DESCRIPTION_STAT_NAME_LONG_COOLDOWN),var7).withUnit(ModI18nAbilities.DESCRIPTION_STAT_UNIT_SECONDS);
		float var10 = var6;
		AbilityStat$Builder var11 = var8;
		float var12 = var7;
		AbilityStat$Builder var13 = var9;
		final float var10_f1 = var10;
		final AbilityStat$Builder var11_f2 = var11;
		final float var12_f3 = var12;
		final AbilityStat$Builder var13_f4 = var13;
		Consumer<CooldownComponent> var14 = (l0) -> {
			float lv3_5 = (l0.getBonusManager().applyBonus(var10_f1)) - (var10_f1);
			var11_f2.withBonus(lv3_5,((lv3_5) < (0F)) ? (AbilityStat$AbilityStatType.BUFF) : (((lv3_5) > (0F)) ? (AbilityStat$AbilityStatType.DEBUFF) : (AbilityStat$AbilityStatType.NEUTRAL)));
			float lv3_6 = (l0.getBonusManager().applyBonus(var12_f3)) - (var12_f3);
			var13_f4.withBonus(lv3_6,((lv3_6) < (0F)) ? (AbilityStat$AbilityStatType.BUFF) : (((lv3_6) > (0F)) ? (AbilityStat$AbilityStatType.DEBUFF) : (AbilityStat$AbilityStatType.NEUTRAL)));
		};
		((java.util.Optional)(var1.getComponent(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get()))))).ifPresent((Consumer)(var14));
		var0.add(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get())),var8.buildComponent());
		var0.add(((AbilityComponentKey)(ModAbilityComponents.COOLDOWN.get())),var9.buildComponent());
	}
	public static void generateRequiredActiveAbilityTooltip(AbilityTooltip var0, Supplier<? extends AbilityCore<?>>... var1) {
		MutableComponent var2 = null;
		if ((var1.length) == (1)){
			Object[] var3 = new Object[1];
			String var4 = ((AbilityCore)(var1[0].get())).getLocalizedName().getString();
			String var5 = "§a" + var4 + "§r";
			var3[0] = var5;
			var2 = Component.translatable(ModI18nAbilities.DEPENDENCY_SINGLE_ACTIVE,var3);
		}else{
			if ((var1.length) > (1)){
				Object[] var3_1 = new Object[2];
				String var4_1 = ((AbilityCore)(var1[0].get())).getLocalizedName().getString();
				String var5_1 = "§a" + var4_1 + "§r";
				var3_1[0] = var5_1;
				String var6 = ((AbilityCore)(var1[1].get())).getLocalizedName().getString();
				String var7 = "§a" + var6 + "§r";
				var3_1[1] = var7;
				var2 = Component.translatable(ModI18nAbilities.DEPENDENCY_DOUBLE_ACTIVE,var3_1);
			}
		}
		var0.add(1100,(Component)(var2));
		var0.add(1100,NEW_LINE);
	}
	@Deprecated()
	public static AbilityDescriptionLine$IDescriptionLine getRequiredActiveAbilityTooltip(Supplier<? extends AbilityCore<?>>... var0) {
		Supplier<? extends AbilityCore<?>>[] var1 = var0;
		final Supplier<? extends AbilityCore<?>>[] var1_f1 = var1;
		AbilityDescriptionLine$IDescriptionLine var2 = (l0, l1) -> {
			MutableComponent lv4_3 = null;
			if ((var1_f1.length) == (1)){
				Object[] lv4_4 = new Object[1];
				String lv4_5 = ((AbilityCore)(var1_f1[0].get())).getLocalizedName().getString();
				String lv4_6 = "§a" + lv4_5 + "§r";
				lv4_4[0] = lv4_6;
				lv4_3 = Component.translatable(ModI18nAbilities.DEPENDENCY_SINGLE_ACTIVE,lv4_4);
			}else{
				if ((var1_f1.length) > (1)){
					Object[] lv4_4_1 = new Object[2];
					String lv4_5_1 = ((AbilityCore)(var1_f1[0].get())).getLocalizedName().getString();
					String lv4_6_1 = "§a" + lv4_5_1 + "§r";
					lv4_4_1[0] = lv4_6_1;
					String lv4_7 = ((AbilityCore)(var1_f1[1].get())).getLocalizedName().getString();
					String lv4_8 = "§a" + lv4_7 + "§r";
					lv4_4_1[1] = lv4_8;
					lv4_3 = Component.translatable(ModI18nAbilities.DEPENDENCY_DOUBLE_ACTIVE,lv4_4_1);
				}
			}
			return lv4_3;
		};
		return var2;
	}
	public static void generateRequiredMorphTooltip(AbilityTooltip var0, Supplier<MorphInfo>... var1) {
		MutableComponent var2 = null;
		if ((var1.length) == (1)){
			Object[] var3 = new Object[1];
			String var4 = ((MorphInfo)(var1[0].get())).getDisplayName().getString();
			String var5 = "§a" + var4 + "§r";
			var3[0] = var5;
			var2 = Component.translatable(ModI18nAbilities.DEPENDENCY_SINGLE_ACTIVE,var3);
		}else{
			if ((var1.length) > (1)){
				Object[] var3_1 = new Object[2];
				String var4_1 = ((MorphInfo)(var1[0].get())).getDisplayName().getString();
				String var5_1 = "§a" + var4_1 + "§r";
				var3_1[0] = var5_1;
				String var6 = ((MorphInfo)(var1[1].get())).getDisplayName().getString();
				String var7 = "§a" + var6 + "§r";
				var3_1[1] = var7;
				var2 = Component.translatable(ModI18nAbilities.DEPENDENCY_DOUBLE_ACTIVE,var3_1);
			}
		}
		var0.add(1100,(Component)(var2));
		var0.add(1100,NEW_LINE);
	}
	@Deprecated()
	public static AbilityDescriptionLine$IDescriptionLine getRequiredMorphTooltip(Supplier<MorphInfo>... var0) {
		Supplier<MorphInfo>[] var1 = var0;
		final Supplier<MorphInfo>[] var1_f1 = var1;
		AbilityDescriptionLine$IDescriptionLine var2 = (l0, l1) -> {
			MutableComponent lv5_3 = null;
			if ((var1_f1.length) == (1)){
				Object[] lv5_4 = new Object[1];
				String lv5_5 = ((MorphInfo)(var1_f1[0].get())).getDisplayName().getString();
				String lv5_6 = "§a" + lv5_5 + "§r";
				lv5_4[0] = lv5_6;
				lv5_3 = Component.translatable(ModI18nAbilities.DEPENDENCY_SINGLE_ACTIVE,lv5_4);
			}else{
				if ((var1_f1.length) > (1)){
					Object[] lv5_4_1 = new Object[2];
					String lv5_5_1 = ((MorphInfo)(var1_f1[0].get())).getDisplayName().getString();
					String lv5_6_1 = "§a" + lv5_5_1 + "§r";
					lv5_4_1[0] = lv5_6_1;
					String lv5_7 = ((MorphInfo)(var1_f1[1].get())).getDisplayName().getString();
					String lv5_8 = "§a" + lv5_7 + "§r";
					lv5_4_1[1] = lv5_8;
					lv5_3 = Component.translatable(ModI18nAbilities.DEPENDENCY_DOUBLE_ACTIVE,lv5_4_1);
				}
			}
			return lv5_3;
		};
		return var2;
	}
}