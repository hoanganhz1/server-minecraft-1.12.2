package xyz.pixelatedw.mineminenomi.api.abilities.components;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.OutgoingChatMessage;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.common.CommonHooks;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability$IOnUseEvent;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;

public class AnimeScreamComponent extends AbilityComponent<IAbility> {
	// Fields
	private static final Ability$IOnUseEvent SIMPLE_SCREAM;
	private static final TargetingConditions SCREAM_TARGETTING;

	public AnimeScreamComponent(IAbility var1) {
		super(((AbilityComponentKey)(ModAbilityComponents.ANIME_SCREAM.get())),var1);
	}
	public void scream(LivingEntity var1) {
		this.scream(var1,this.getAbility().getDisplayName().getString());
	}
	public void scream(LivingEntity var1, String var2) {
		this.ensureIsRegistered();
		Level var3 = var1.level();
		if ((var3) == (null)){
			return;
		}else{
			if ((!(var3.isClientSide)) && (ServerConfig.isAnimeScreamingEnabled())){
				Level var4 = var1.getCommandSenderWorld();
				ChatType.Bound var5 = ChatType.bind(ChatType.MSG_COMMAND_INCOMING,(Entity)(var1)).withTargetName(var1.getDisplayName());
				Object[] var6 = new Object[]{var1.getDisplayName(),CommonHooks.newChatWithLinks(var2.toUpperCase())};
				MutableComponent var7 = Component.translatable("chat.type.text",var6).setStyle(Style.EMPTY.withItalic(Boolean.valueOf(true)));
				AABB var8 = var1.getBoundingBox().inflate(100D,50D,100D);
				OutgoingChatMessage.Disguised var9 = new OutgoingChatMessage.Disguised((Component)(var7));
				Iterator var10 = var4.getNearbyPlayers(SCREAM_TARGETTING,var1,var8).iterator();
				do{
					if (var10.hasNext()){
						Player var11 = ((Player)(var10.next()));
						if (var11 instanceof ServerPlayer){
							((ServerPlayer)(var11)).sendChatMessage((OutgoingChatMessage)(var9),false,var5);
							continue;
						}else{
							continue;
						}
					}else{
						break;
					}
				} while (true);
			}
			return;
		}
	}
	static  {
		Ability$IOnUseEvent var0 = (l0, l1) -> {
			LivingEntity lv7_2 = l0;
			final LivingEntity lv7_2_f1 = lv7_2;
			Consumer<AnimeScreamComponent> lv7_3 = (l2_0) -> {
				l2_0.scream(lv7_2_f1);
			};
			((java.util.Optional)(l1.getComponent(((AbilityComponentKey)(ModAbilityComponents.ANIME_SCREAM.get()))))).ifPresent((Consumer)(lv7_3));
		};
		SIMPLE_SCREAM = var0;
		SCREAM_TARGETTING = TargetingConditions.forCombat().range(100D);
	}

	public void postInit(IAbility var1) {
		IAbility var3_1 = null;
		if (var1.hasComponent(((AbilityComponentKey)(ModAbilityComponents.CHARGE.get())))){
			AnimeScreamComponent var2 = this;
			IAbility var3 = var1;
			final IAbility var3_f1 = var3;
			Consumer<ChargeComponent> var4 = (l0) -> {
			String[] lv3_3 = this.getAbility().getDisplayName().getString().split(" ",0);
			AnimeScreamComponent lv3_4 = this;
			String[] lv3_5 = lv3_3;
			final String[] lv3_5_f1 = lv3_5;
			ChargeComponent$IStartChargingEvent lv3_6 = (l2_0, l2_1) -> {
				String lv1_4 = lv3_5_f1[0];
				String lv1_5 = ((lv3_5_f1.length) > (1)) ? ("...") : ("");
				String lv1_6 = lv1_4 + lv1_5;
				this.scream(l2_0,lv1_6);
			};
			l0.addStartEvent(lv3_6);
			if ((lv3_3.length) > (1)){
				AnimeScreamComponent lv3_7 = this;
				String[] lv3_8 = lv3_3;
				final String[] lv3_8_f1 = lv3_8;
				ChargeComponent$IEndChargingEvent lv3_9 = (l2_0, l2_1) -> {
				String lv2_4 = lv3_8_f1[1];
				String lv2_5 = "..." + lv2_4;
				this.scream(l2_0,lv2_5);
			};
				l0.addEndEvent(lv3_9);
			}
			if (var3_f1 instanceof Ability){
				((Ability)(var3_f1)).removeUseEvent(SIMPLE_SCREAM);
			}
		};
			((java.util.Optional)(var1.getComponent(((AbilityComponentKey)(ModAbilityComponents.CHARGE.get()))))).ifPresent((Consumer)(var4));
		}else{
			if (var1.hasComponent(((AbilityComponentKey)(ModAbilityComponents.CONTINUOUS.get())))){
				AnimeScreamComponent var2_1 = this;
				var3_1 = var1;
				final IAbility var3_1_f1 = var3_1;
				Consumer<ContinuousComponent> var4_1 = (l0) -> {
			AnimeScreamComponent lv5_3 = this;
			ContinuousComponent$IStartContinuousEvent lv5_4 = (l2_0, l2_1) -> {
				this.scream(l2_0);
			};
			l0.addStartEvent(lv5_4);
			if (var3_1_f1 instanceof Ability){
				((Ability)(var3_1_f1)).removeUseEvent(SIMPLE_SCREAM);
			}
		};
				((java.util.Optional)(var1.getComponent(((AbilityComponentKey)(ModAbilityComponents.CONTINUOUS.get()))))).ifPresent((Consumer)(var4_1));
			}else{
				if ((var1 instanceof Ability) && (!(((Ability)(var1)).hasUseEvent(SIMPLE_SCREAM)))){
					((Ability)(var1)).addUseEvent(SIMPLE_SCREAM);
				}else{
					return;
				}
			}
		}
	}}