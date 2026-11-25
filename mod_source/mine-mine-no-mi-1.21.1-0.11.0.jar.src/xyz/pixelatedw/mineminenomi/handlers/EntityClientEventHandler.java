package xyz.pixelatedw.mineminenomi.handlers;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.Input;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.ComputeFovModifierEvent;
import net.neoforged.neoforge.client.event.RenderBlockScreenEffectEvent;
import net.neoforged.neoforge.client.event.RenderFrameEvent;
import net.neoforged.neoforge.client.event.RenderHandEvent;
import net.neoforged.neoforge.client.event.RenderLivingEvent;
import net.neoforged.neoforge.client.event.ViewportEvent;
import net.neoforged.neoforge.client.event.sound.PlaySoundEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import xyz.pixelatedw.mineminenomi.abilities.ZoomAbility;
import xyz.pixelatedw.mineminenomi.abilities.doa.DoaHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.events.TriEventResult;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.CameraLockData;
import xyz.pixelatedw.mineminenomi.handlers.ability.KukuPassivesHandler;
import xyz.pixelatedw.mineminenomi.handlers.ability.MaguPassivesHandler;
import xyz.pixelatedw.mineminenomi.handlers.ability.MinkPassivesHandler$Client;
import xyz.pixelatedw.mineminenomi.handlers.ability.MorphsHandler;
import xyz.pixelatedw.mineminenomi.handlers.ability.SwordsmanPassivesHandler$Client;
import xyz.pixelatedw.mineminenomi.handlers.entity.CarryHandler;
import xyz.pixelatedw.mineminenomi.handlers.entity.EffectsHandler$Client;
import xyz.pixelatedw.mineminenomi.handlers.entity.InputHandler;
import xyz.pixelatedw.mineminenomi.handlers.entity.ItemsHandler;
import xyz.pixelatedw.mineminenomi.handlers.world.RandomizedFruitsHandler$Client;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

@EventBusSubscriber(modid="mineminenomi", value={Dist.CLIENT})
public class EntityClientEventHandler {
	@SubscribeEvent()
	public static void onClientTick(ClientTickEvent.Pre var0) {
		Minecraft var1 = Minecraft.getInstance();
		LocalPlayer var2 = var1.player;
		if (((var2) != (null)) && ((var2.input) != (null))){
			InputHandler.handleNewPlayerInput(var2);
			if ((var2.hasEffect((Holder)(ModEffects.SILENT))) && (((var2.tickCount) % (20)) == (0))){
				var1.getSoundManager().stop();
			}
			return;
		}else{
			return;
		}
	}
	@SubscribeEvent()
	public static void onPlayerJoins(EntityJoinLevelEvent var0) {
		InputHandler.resetCachedKeybinds();
	}
	@SubscribeEvent()
	public static void onPlayerLeaves(PlayerEvent.PlayerLoggedOutEvent var0) {
		InputHandler.resetVanillaKeybinds();
	}
	@SubscribeEvent()
	public static void onPlayerCameraSetup(ViewportEvent.ComputeCameraAngles var0) {
		LocalPlayer var1 = Minecraft.getInstance().player;
		if (var1.hasEffect((Holder)(ModEffects.DOOR_HEAD))){
			var0.setYaw(((float)((var1.tickCount) * (10))) % (360F));
		}
		ViewportEvent.ComputeCameraAngles var2 = var0;
		final ViewportEvent.ComputeCameraAngles var2_f1 = var2;
		Consumer<CameraLockData> var3 = (l0) -> {
			if (l0.hasYawClamped()){
				var2_f1.setYaw(l0.getYaw());
			}
			if (l0.hasPitchClamped()){
				var2_f1.setPitch(l0.getPitch());
			}
		};
		((java.util.Optional)(CameraLockData.get((LivingEntity)(var1)))).ifPresent((Consumer)(var3));
	}
	@SubscribeEvent()
	public static void onEntityPreRendered(RenderLivingEvent.Pre<LivingEntity, EntityModel<LivingEntity>> var0) {
		LocalPlayer var1 = Minecraft.getInstance().player;
		LivingEntity var2 = var0.getEntity();
		if (EffectsHandler$Client.isInvisible(var2)){
			var0.setCanceled(true);
			return;
		}else{
			if (var2.hasEffect((Holder)(ModEffects.DOOR_HEAD))){
				var2.yBodyRot = 0F;
				var2.yBodyRotO = 0F;
			}
			if (var2.hasEffect((Holder)(ModEffects.NO_HANDS))){
				int var3 = var2.getEffect((Holder)(ModEffects.NO_HANDS)).getAmplifier();
				Iterator var4 = RendererHelper.getArmPartsFrom(var0.getRenderer().getModel(),HumanoidArm.RIGHT).iterator();
				do{
					if (var4.hasNext()){
						ModelPart var5 = ((ModelPart)(var4.next()));
						var5.visible = false;
						continue;
					}else{
						break;
					}
				} while (true);
				if ((var3) > (0)){
					Iterator var5_1 = RendererHelper.getArmPartsFrom(var0.getRenderer().getModel(),HumanoidArm.LEFT).iterator();
					do{
						if (var5_1.hasNext()){
							ModelPart var6 = ((ModelPart)(var5_1.next()));
							var6.visible = false;
							continue;
						}else{
							break;
						}
					} while (true);
				}
			}
			if (CarryHandler.isCarrying((LivingEntity)(var1),var2)){
				if ((Minecraft.getInstance().options.getCameraType()) == (CameraType.FIRST_PERSON)){
					var0.setCanceled(true);
					return;
				}else{
					return;
				}
			}else{
				return;
			}
		}
	}
	@SubscribeEvent()
	public static void onEntityPostRendered(RenderLivingEvent.Post<LivingEntity, EntityModel<LivingEntity>> var0) {
		LivingEntity var1 = var0.getEntity();
		if (var1.hasEffect((Holder)(ModEffects.DOOR_HEAD))){
			var1.yHeadRot = (var1.yHeadRot) + (10F);
			var1.yHeadRotO = (var1.yHeadRotO) + (10F);
			var1.yBodyRot = 0F;
			var1.yBodyRotO = 0F;
		}
		if (var1.hasEffect((Holder)(ModEffects.NO_HANDS))){
			int var2 = var1.getEffect((Holder)(ModEffects.NO_HANDS)).getAmplifier();
			Iterator var3 = RendererHelper.getArmPartsFrom(var0.getRenderer().getModel(),HumanoidArm.RIGHT).iterator();
			do{
				if (var3.hasNext()){
					ModelPart var4 = ((ModelPart)(var3.next()));
					var4.visible = true;
					continue;
				}else{
					break;
				}
			} while (true);
			if ((var2) > (0)){
				Iterator var4_1 = RendererHelper.getArmPartsFrom(var0.getRenderer().getModel(),HumanoidArm.LEFT).iterator();
				do{
					if (var4_1.hasNext()){
						ModelPart var5 = ((ModelPart)(var4_1.next()));
						var5.visible = true;
						continue;
					}else{
						break;
					}
				} while (true);
			}else{
				return;
			}
		}
	}
	@SubscribeEvent()
	public static void onFirstPersonViewRendered(RenderFrameEvent.Pre var0) {
		Minecraft var1 = Minecraft.getInstance();
		LocalPlayer var2 = var1.player;
		if (((var2) != (null)) && (var2.isAlive())){
			if (DoaHelper.isInsideDoor((LivingEntity)(var2))){
				RendererHelper.drawColourOnScreen(2817966,50,0F,0F,(float)(var1.getWindow().getGuiScaledWidth()),(float)(var1.getWindow().getGuiScaledHeight()),500F);
			}
			return;
		}else{
			return;
		}
	}
	@SubscribeEvent()
	public static void onEntityHandRendered(RenderHandEvent var0) {
		LocalPlayer var1 = Minecraft.getInstance().player;
		ItemStack var2 = var0.getItemStack();
		PoseStack var3 = var0.getPoseStack();
		int var4 = var0.getPackedLight();
		float var5 = var0.getSwingProgress();
		float var6 = var0.getEquipProgress();
		int var7 = ((var0.getHand()) == (InteractionHand.MAIN_HAND)) ? (1) : (0);
		boolean var8 = var2.isEmpty();
		if ((var7) != (0)){
			if (var1.hasEffect((Holder)(ModEffects.NO_HANDS))){
				var0.setCanceled(true);
				return;
			}else{
				if (var8){
					MinkPassivesHandler$Client.renderElectroFirstPerson((Player)(var1),var3,var0.getMultiBufferSource(),(float)(var4),var5,var6);
					if ((MorphsHandler.renderLimbWithOverlay(var3,var0.getMultiBufferSource(),var2,var4,var5,var6,var1)) == (TriEventResult.DENY)){
						var0.setCanceled(true);
					}else{
						return;
					}
				}else{
					return;
				}
			}
		}
	}
	@SubscribeEvent()
	public static void onTooltip(ItemTooltipEvent var0) {
		Player var1 = var0.getEntity();
		if ((var1) == (null)){
			return;
		}else{
			if (var0.getItemStack().isEmpty()){
				return;
			}else{
				ItemStack var2 = var0.getItemStack();
				List var3 = var0.getToolTip();
				ItemsHandler.kairosekiTooltip(var2,var3);
				ItemsHandler.dyeableTooltip(var2,var3);
				ItemsHandler.weatherBallChargesTooltip(var2,var3);
				if (RandomizedFruitsHandler$Client.HAS_RANDOMIZED_FRUIT){
					ItemsHandler.handleRandomizedFruitsTooltip(var2,var3);
				}
				KukuPassivesHandler.addKukuEdibleTooltips(var1,var2,var3);
				SwordsmanPassivesHandler$Client.swordsmanBonusTooltip(var1,var2,var3);
				RandomizedFruitsHandler$Client.handleHiddenFruitName(var1,var2,var3);
				return;
			}
		}
	}
	@SubscribeEvent()
	public static void onScreenEffectRender(RenderBlockScreenEffectEvent var0) {
		Player var1 = var0.getPlayer();
		if (MaguPassivesHandler.canSeeThroughFire(var1,var0.getOverlayType())){
			var0.setCanceled(true);
			return;
		}else{
			return;
		}
	}
	@SubscribeEvent()
	public static void onFogRender(ViewportEvent.RenderFog var0) {
		if (MaguPassivesHandler.canSeeInsideLava((Player)(Minecraft.getInstance().player))){
			var0.setCanceled(true);
			var0.setFarPlaneDistance(40F);
			return;
		}else{
			return;
		}
	}
	@SubscribeEvent()
	public static void onFOVChanged(ComputeFovModifierEvent var0) {
		LocalPlayer var1 = Minecraft.getInstance().player;
		AbilityData var2 = ((AbilityData)(((java.util.Optional)(AbilityData.get((LivingEntity)(var1)))).orElse((Object)(null))));
		if ((var2) == (null)){
			return;
		}else{
			ZoomAbility var3 = ((ZoomAbility)(var2.getEquippedAbility(((AbilityCore)(ZoomAbility.INSTANCE.get())))));
			boolean var4 = ItemsHelper.hasItemInSlot((LivingEntity)(var1),EquipmentSlot.HEAD,((Item)(ModArmors.SNIPER_GOGGLES.get())));
			int var5 = (((var3) != (null)) && (var3.isContinuous())) ? (1) : (0);
			if ((var4) && ((var5) != (0))){
				var0.setNewFovModifier(0.01F);
				return;
			}else{
				Predicate<IAbility> var6 = (l0) -> {
			return l0.hasComponent(((AbilityComponentKey)(ModAbilityComponents.CHANGE_STATS.get())));
		};
				Iterator var7 = var2.getEquippedAndPassiveAbilities(var6).iterator();
				do{
					if (var7.hasNext()){
						IAbility var8 = ((IAbility)(var7.next()));
						if (((var8) == (null)) || (!(((ChangeStatsComponent)(var8.getComponent(((AbilityComponentKey)(ModAbilityComponents.CHANGE_STATS.get()))).get())).hasModifier((LivingEntity)(var1),Attributes.MOVEMENT_SPEED)))){
							continue;
						}else{
							var0.setNewFovModifier(1F);
							break;
						}
					}else{
						break;
					}
				} while (true);
				return;
			}
		}
	}
	@SubscribeEvent()
	public static void onSoundPlayed(PlaySoundEvent var0) {
		Minecraft var1 = Minecraft.getInstance();
		if (((var1.level) != (null)) && ((var0.getSound()) != (null))){
			BlockPos var2 = BlockPos.containing(var0.getSound().getX(),var0.getSound().getY(),var0.getSound().getZ());
			if ((var1.player.hasEffect((Holder)(ModEffects.SILENT))) && (var2.closerThan((Vec3i)(var1.player.blockPosition()),30D))){
				var0.setSound((SoundInstance)(null));
			}
			return;
		}else{
			return;
		}
	}
}