package xyz.pixelatedw.mineminenomi.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.server.permission.nodes.PermissionNode;
import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.enums.TrainingPointType;
import xyz.pixelatedw.mineminenomi.api.factions.IFactionRank;
import xyz.pixelatedw.mineminenomi.data.entity.CombatData;
import xyz.pixelatedw.mineminenomi.data.entity.DevilFruitData;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.data.entity.HakiData;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModPermissions;

public class CheckPlayerCommand {
	public static LiteralArgumentBuilder<CommandSourceStack> create() {
		LiteralArgumentBuilder var0 = ((LiteralArgumentBuilder)(((com.mojang.brigadier.builder.LiteralArgumentBuilder)(Commands.literal("check_player"))).requires((Predicate)(Requires.hasPermission(ModPermissions.CHECK_PLAYER_COMMAND)))));
		Command var1 = (l0) -> {
			return checkPlayer(l0,((CommandSourceStack)(l0.getSource())).getPlayerOrException(),true);
		};
		LiteralArgumentBuilder var2 = ((LiteralArgumentBuilder)(var0.executes(var1)));
		Command var3 = (l0) -> {
			return checkPlayer(l0,EntityArgument.getPlayer(l0,"target"),BoolArgumentType.getBool(l0,"show_attributes"));
		};
		var2.then(Commands.argument("target",(ArgumentType)(EntityArgument.player())).then(Commands.argument("show_attributes",(ArgumentType)(BoolArgumentType.bool())).executes(var3)));
		return var0;
	}
	private static int checkPlayer(CommandContext<CommandSourceStack> var0, ServerPlayer var1, boolean var2) {
		ServerPlayer var16 = null;
		ServerPlayer var19 = null;
		StringBuilder var52_3 = null;
		try{
			EntityStatsData var3 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get((LivingEntity)(var1)))).orElse((Object)(null))));
			CombatData var4 = ((CombatData)(((java.util.Optional)(CombatData.get((LivingEntity)(var1)))).orElse((Object)(null))));
			DevilFruitData var5 = ((DevilFruitData)(((java.util.Optional)(DevilFruitData.get((LivingEntity)(var1)))).orElse((Object)(null))));
			HakiData var6 = ((HakiData)(((java.util.Optional)(HakiData.get((LivingEntity)(var1)))).orElse((Object)(null))));
			if (((var3) != (null)) && (((var4) != (null)) && (((var5) != (null)) && ((var6) != (null))))){
				FactionsWorldData var7 = FactionsWorldData.get();
				Crew var8 = var7.getCrewWithMember(var1.getUUID());
				StringBuilder var9 = new StringBuilder();
				var9.append("===============================================\n");
				Function<Faction, String> var10 = (l0) -> {
			return l0.getLabel().getString();
		};
				String var11 = ((String)(((java.util.Optional)(var3.getFaction())).map((Function)(var10)).orElse("N/A")));
				Function<Race, String> var12 = (l0) -> {
			return l0.getLabel().getString();
		};
				String var13 = ((String)(((java.util.Optional)(var3.getRace())).map((Function)(var12)).orElse("N/A")));
				Function<FightingStyle, String> var14 = (l0) -> {
			return l0.getLabel().getString();
		};
				String var15 = ((String)(((java.util.Optional)(var3.getFightingStyle())).map((Function)(var14)).orElse("N/A")));
				var16 = var1;
				final ServerPlayer var16_f1 = var16;
				Function<Faction, Boolean> var17 = (l0) -> {
			return Boolean.valueOf(l0.canReceiveLoyalty((LivingEntity)(var16_f1)));
		};
				boolean var18 = ((Boolean)(((java.util.Optional)(var3.getFaction())).map((Function)(var17)).orElse(Boolean.valueOf(false)))).booleanValue();
				var19 = var1;
				final ServerPlayer var19_f2 = var19;
				Function<Faction, Boolean> var20 = (l0) -> {
			return Boolean.valueOf(l0.canReceiveBounty((LivingEntity)(var19_f2)));
		};
				boolean var21 = ((Boolean)(((java.util.Optional)(var3.getFaction())).map((Function)(var20)).orElse(Boolean.valueOf(false)))).booleanValue();
				String var22 = var1.getDisplayName().getString();
				String var23 = "Name: " + var22 + "\n";
				var9.append(var23);
				String var24 = var11;
				String var25 = "Faction: " + var24 + "\n";
				var9.append(var25);
				if (var18){
					double var26 = var3.getLoyalty();
					String var27 = "Loyalty: " + var26 + "\n";
					var9.append(var27);
					Function var28 = (Function) ((Function<Enum, String>) ((l0) -> {
			return ((IFactionRank)(l0)).getLocalizedName().getString();
		}));
					String var29 = ((String)(var3.getRank().map(var28).orElse("N/A")));
					String var30 = var29;
					String var31 = "Rank: " + var30 + "\n";
					var9.append(var31);
				}
				String var26_1 = var13;
				String var27_1 = "Race: " + var26_1 + "\n";
				var9.append(var27_1);
				if (var3.isCyborg()){
					int var28_1 = var3.getMaxCola();
					String var29_1 = "Max Cola: " + var28_1 + "\n";
					var9.append(var29_1);
					int var30_1 = var3.getUltraCola();
					String var31_1 = "Ultra Cola: " + var30_1 + "\n";
					var9.append(var31_1);
				}
				String var28_2 = var15;
				String var29_2 = "Style: " + var28_2 + "\n";
				var9.append(var29_2);
				double var30_2 = var3.getDoriki();
				String var31_2 = "Doriki: " + var30_2 + "\n";
				var9.append(var31_2);
				long var32 = var3.getBelly();
				String var33 = "Belly: " + var32 + "\n";
				var9.append(var33);
				long var34 = var3.getExtol();
				String var35 = "Extol: " + var34 + "\n";
				var9.append(var35);
				if (var21){
					long var36 = var3.getBounty();
					String var37 = "Bounty: " + var36 + "\n";
					var9.append(var37);
					long var38 = var7.getBounty(var1.getUUID());
					String var39 = "Issued Bounty: " + var38 + "\n";
					var9.append(var39);
				}
				if (var3.isPirate()){
					String var36_1 = ((var8) != (null)) ? (var8.getName()) : ("None");
					String var37_1 = "Crew: " + var36_1 + "\n";
					var9.append(var37_1);
				}
				ItemStack var36_2 = new ItemStack((ItemLike)(var5.getDevilFruitItem()));
				String var37_2 = (!(var36_2.isEmpty())) ? (var36_2.getHoverName().getString()) : ("None");
				String var38_1 = "Devil Fruit: " + var37_2 + "\n";
				var9.append(var38_1);
				double var39_1 = var3.getDamageMultiplier();
				String var40 = "Damage Multiplier: " + var39_1 + "\n";
				var9.append(var40);
				AttributeInstance var41 = var1.getAttribute(ModAttributes.TOUGHNESS);
				AttributeInstance var42 = var1.getAttribute(Attributes.ARMOR);
				double var43 = (var41.getValue()) / (((RangedAttribute)(var41.getAttribute().value())).getMaxValue());
				double var44 = (var42.getValue()) / (((RangedAttribute)(var42.getAttribute().value())).getMaxValue());
				var43 = (var43) * (0.6D);
				var44 = (var44) * (0.4D);
				double var45 = (var43) + (var44);
				int var46 = ((var45) > (0.949999988079071D)) ? (1) : (0);
				Object[] var47 = new Object[4];
				var47[0] = String.format("%.2f",new Object[]{Double.valueOf((var45) * (100D))});
				var47[1] = String.format("%.2f",new Object[]{Double.valueOf((var43) * (100D))});
				var47[2] = String.format("%.2f",new Object[]{Double.valueOf((var44) * (100D))});
				var47[3] = ((var46) != (0)) ? ("(overflowing past limit)") : ("");
				var9.append(String.format("Protection: %s%% (T:%s%% A:%s%%) %s \n",var47));
				TrainingPointType[] var48 = TrainingPointType.values();
				int var49 = var48.length;
				int var50 = 0;
				do{
					if ((var50) < (var49)){
						TrainingPointType var51 = var48[var50];
						String var52 = var51.name();
						int var53 = var3.getTrainingPoints(var51);
						String var54 = var52 + " Training Points: " + var53 + "\n";
						var9.append(var54);
						var50++;
						continue;
					}else{
						break;
					}
				} while (true);
				if (WyDebug.isDebug()){
					boolean var51_1 = WyHelper.isInCombat((LivingEntity)(var1));
					String var52_1 = "In Combat ?: " + var51_1 + "\n";
					var9.append(var52_1);
					long var53_1 = var4.getLastAttackTime();
					String var54_1 = "Combat Cache Timer: " + var53_1 + "\n";
					var9.append(var54_1);
				}
				if (var2){
					var9.append("-----\n");
					var9.append("§2Attribute | Current Value / Base Value§r\n");
					Iterator var51_2 = BuiltInRegistries.ATTRIBUTE.holders().toList().iterator();
					LOOP_1:
					do{
						if (var51_2.hasNext()){
							Holder var52_2 = ((Holder)(var51_2.next()));
							AttributeInstance var53_2 = var1.getAttributes().getInstance(var52_2);
							if (((var53_2) != (null)) && ((var53_2.getValue()) != (var53_2.getBaseValue()))){
								String var54_2 = ((Attribute)(var53_2.getAttribute().value())).getDescriptionId();
								double var55 = var53_2.getValue();
								double var56 = var53_2.getBaseValue();
								String var57 = "- §9" + var54_2 + " | " + var55 + "/" + var56 + "§r\n";
								var9.append(var57);
								Iterator var58 = var53_2.getModifiers().iterator();
								do{
									if (var58.hasNext()){
										AttributeModifier var59 = ((AttributeModifier)(var58.next()));
										String var60 = String.valueOf((Object)(var59.id()));
										double var61 = var59.amount();
										String var62 = "  " + var60 + " | " + var61 + "\n";
										var9.append(var62);
										continue;
									}else{
										continue LOOP_1;
									}
								} while (true);
							}else{
								continue;
							}
						}else{
							break;
						}
					} while (true);
				}
				var9.append("===============================================");
				CommandSourceStack var51_3 = ((CommandSourceStack)(var0.getSource()));
				var52_3 = var9;
				final StringBuilder var52_3_f3 = var52_3;
				Supplier<Component> var53_3 = () -> {
			return Component.literal(var52_3_f3.toString());
		};
				var51_3.sendSuccess(var53_3,true);
			}else{
				return -1;
			}
		}catch(Exception var3_1){
			var3_1.printStackTrace();
		}
		return 1;
	}
}