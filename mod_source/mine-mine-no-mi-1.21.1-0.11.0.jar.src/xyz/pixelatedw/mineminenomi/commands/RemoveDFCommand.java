package xyz.pixelatedw.mineminenomi.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundPlayerAbilitiesPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.server.permission.nodes.PermissionNode;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.abilities.FlyAbility;
import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.config.GeneralConfig;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.DevilFruitData;
import xyz.pixelatedw.mineminenomi.data.entity.HakiData;
import xyz.pixelatedw.mineminenomi.data.world.OneFruitWorldData;
import xyz.pixelatedw.mineminenomi.handlers.ability.ProgressionHandler;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModFruits;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModPermissions;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncAbilityDataPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncDevilFruitPacket;

public class RemoveDFCommand {
	// Fields
	private static final Predicate<CommandSourceStack> REQUIRE;

	
	static  {
		Predicate<CommandSourceStack> var0 = (l0) -> {
			int lv8_1 = (((Boolean)(GeneralConfig.PUBLIC_REMOVEDF.get())).booleanValue()) ? (0) : (2);
			PermissionNode[] lv8_2 = new PermissionNode[]{ModPermissions.REMOVE_DF_COMMAND,ModPermissions.REMOVE_DF_COMMAND_SELF};
			boolean lv8_3 = Requires.hasEitherPermission(lv8_2).test(l0);
			return (l0.hasPermission(lv8_1)) || (lv8_3);
		};
		REQUIRE = var0;
	}
public static LiteralArgumentBuilder<CommandSourceStack> create() {
		LiteralArgumentBuilder var0 = ((LiteralArgumentBuilder)(Commands.literal("removedf").requires(REQUIRE)));
		Command var1 = (l0) -> {
			return removesDF(l0,((CommandSourceStack)(l0.getSource())).getPlayerOrException());
		};
		LiteralArgumentBuilder var2 = ((LiteralArgumentBuilder)(var0.executes(var1)));
		RequiredArgumentBuilder var3 = ((RequiredArgumentBuilder)(((com.mojang.brigadier.builder.RequiredArgumentBuilder)(Commands.argument("target",(ArgumentType)(EntityArgument.player())))).requires((Predicate)(Requires.hasPermission(ModPermissions.REMOVE_DF_COMMAND)))));
		Command var4 = (l0) -> {
			return removesDF(l0,EntityArgument.getPlayer(l0,"target"));
		};
		var2.then(var3.executes(var4));
		return var0;
	}
	private static int removesDF(CommandContext<CommandSourceStack> var0, ServerPlayer var1) throws CommandSyntaxException {
		ServerPlayer var7 = null;
		ServerPlayer var9 = null;
		try{
			DevilFruitData var2 = ((DevilFruitData)(((java.util.Optional)(DevilFruitData.get((LivingEntity)(var1)))).orElse((Object)(null))));
			AbilityData var3 = ((AbilityData)(((java.util.Optional)(AbilityData.get((LivingEntity)(var1)))).orElse((Object)(null))));
			if (((var2) != (null)) && ((var3) != (null))){
				OneFruitWorldData var4 = OneFruitWorldData.get();
				if (var2.hasAnyDevilFruit()){
					var4.lostOneFruit(((ResourceLocation)(var2.getDevilFruit().get())),(LivingEntity)(var1),"Removed via Command");
					if (var2.hasYamiPower()){
						var4.lostOneFruit(((AkumaNoMiItem)(ModFruits.YAMI_YAMI_NO_MI.get())).getRegistryKey(),(LivingEntity)(var1),"Removed via Command");
					}
					var2.removeDevilFruit();
					var2.clearMorphs();
					var2.setAwakenedFruit(false);
				}
				Iterator var5 = var3.getEquippedAndPassiveAbilities().iterator();
				do{
					if (var5.hasNext()){
						IAbility var6 = ((IAbility)(var5.next()));
						var7 = var1;
						final ServerPlayer var7_f1 = var7;
						Consumer<ChangeStatsComponent> var8 = (l0) -> {
			Iterator lv3_2 = l0.getModifiers().entrySet().iterator();
			do{
				if (lv3_2.hasNext()){
					Map.Entry lv3_3 = ((Map.Entry)(lv3_2.next()));
					var7_f1.getAttribute(((Holder)(lv3_3.getKey()))).removeModifier(((AttributeModifier)(((ImmutablePair)(lv3_3.getValue())).getValue())).id());
					continue;
				}else{
					break;
				}
			} while (true);
		};
						((java.util.Optional)(var6.getComponent(((AbilityComponentKey)(ModAbilityComponents.CHANGE_STATS.get()))))).ifPresent((Consumer)(var8));
						continue;
					}else{
						break;
					}
				} while (true);
				Predicate<IAbility> var6_1 = (l0) -> {
			return (l0 instanceof FlyAbility) && (!(((FlyAbility)(l0)).isPaused()));
		};
				if (((((var3.getPassiveAbilities(var6_1).size()) > (0)) ? (1) : (0)) != (0)) && ((!(var1.isCreative())) && (!(var1.isSpectator())))){
					var1.getAbilities().mayfly = false;
					var1.getAbilities().flying = false;
					var1.connection.send((Packet)(new ClientboundPlayerAbilitiesPacket(var1.getAbilities())));
				}
				var3.clearUnlockedAbilities(AbilityCategory.DEVIL_FRUITS.isCorePartofCategory());
				var3.clearPassiveAbilities(AbilityCategory.DEVIL_FRUITS.isAbilityPartofCategory());
				var3.clearEquippedAbilities(AbilityCategory.DEVIL_FRUITS.isAbilityPartofCategory());
				ProgressionHandler.checkForRacialUnlocks((Player)(var1));
				if (WyDebug.isDebug()){
					var1.removeAllEffects();
					Consumer<HakiData> var7_1 = (l0) -> {
			l0.setHakiOveruse(0);
		};
					((java.util.Optional)(HakiData.get((LivingEntity)(var1)))).ifPresent((Consumer)(var7_1));
				}
				ModNetwork.sendToAllTrackingAndSelf(new SSyncDevilFruitPacket((LivingEntity)(var1)),(LivingEntity)(var1));
				ModNetwork.sendToAllTrackingAndSelf(new SSyncAbilityDataPacket((LivingEntity)(var1)),(LivingEntity)(var1));
				Predicate<IAbility> var7_2 = (l0) -> {
			return true;
		};
				AbilityHelper.enableAbilities((LivingEntity)(var1),var7_2);
				CommandSourceStack var8_1 = ((CommandSourceStack)(var0.getSource()));
				var9 = var1;
				final ServerPlayer var9_f2 = var9;
				Supplier<Component> var10 = () -> {
			String lv7_1 = String.valueOf((Object)(ChatFormatting.GREEN));
			String lv7_2 = var9_f2.getDisplayName().getString();
			String lv7_3 = lv7_1 + "Removed Devil Fruit for " + lv7_2;
			return Component.literal(lv7_3);
		};
				var8_1.sendSuccess(var10,true);
			}else{
				return -1;
			}
		}catch(Exception var2_1){
			var2_1.printStackTrace();
		}
		return 1;
	}}