package xyz.pixelatedw.mineminenomi.init;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.handling.IPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.commands.FGCommand;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.IPacket;
import xyz.pixelatedw.mineminenomi.packets.IServerPacket;
import xyz.pixelatedw.mineminenomi.packets.client.CFinishCCPacket;
import xyz.pixelatedw.mineminenomi.packets.client.CRequestSyncQuestDataPacket;
import xyz.pixelatedw.mineminenomi.packets.client.ability.CChangeAbilityAltModePacket;
import xyz.pixelatedw.mineminenomi.packets.client.ability.CEquipAbilityPacket;
import xyz.pixelatedw.mineminenomi.packets.client.ability.CRemoveAbilityPacket;
import xyz.pixelatedw.mineminenomi.packets.client.ability.CSetCombatBarPacket;
import xyz.pixelatedw.mineminenomi.packets.client.ability.CSetHakiColorPacket;
import xyz.pixelatedw.mineminenomi.packets.client.ability.CToggleCombatModePacket;
import xyz.pixelatedw.mineminenomi.packets.client.ability.CTogglePassiveAbilityPacket;
import xyz.pixelatedw.mineminenomi.packets.client.ability.CUseAbilityPacket;
import xyz.pixelatedw.mineminenomi.packets.client.ability.node.CUnlockAbilityNodePacket;
import xyz.pixelatedw.mineminenomi.packets.client.challenge.CAcceptChallengeInvitationPacket;
import xyz.pixelatedw.mineminenomi.packets.client.challenge.CDisbandChallengeGroupPacket;
import xyz.pixelatedw.mineminenomi.packets.client.challenge.CFetchPossibleInvitesPacket;
import xyz.pixelatedw.mineminenomi.packets.client.challenge.CSendChallengeInvitationPacket;
import xyz.pixelatedw.mineminenomi.packets.client.challenge.CStartChallengePacket;
import xyz.pixelatedw.mineminenomi.packets.client.crew.CCreateCrewPacket;
import xyz.pixelatedw.mineminenomi.packets.client.crew.CKickFromCrewPacket;
import xyz.pixelatedw.mineminenomi.packets.client.crew.CLeaveCrewPacket;
import xyz.pixelatedw.mineminenomi.packets.client.crew.CUpdateJollyRogerPacket;
import xyz.pixelatedw.mineminenomi.packets.client.entity.CInteractionTriggeredPacket;
import xyz.pixelatedw.mineminenomi.packets.client.entity.CSwitchVehicleModePacket;
import xyz.pixelatedw.mineminenomi.packets.client.entity.CSyncPlayerInputPacket;
import xyz.pixelatedw.mineminenomi.packets.client.entity.CUpdateInventoryPagePacket;
import xyz.pixelatedw.mineminenomi.packets.client.entity.interaction.CBarkeeperBuyRumPacket;
import xyz.pixelatedw.mineminenomi.packets.client.entity.interaction.CBarkeeperListNewsPacket;
import xyz.pixelatedw.mineminenomi.packets.client.entity.interaction.CBarkeeperListRumorsPacket;
import xyz.pixelatedw.mineminenomi.packets.client.entity.interaction.CDoctorHealPacket;
import xyz.pixelatedw.mineminenomi.packets.client.entity.interaction.CTrainerResetTreePacket;
import xyz.pixelatedw.mineminenomi.packets.client.quest.CAbandonQuestPacket;
import xyz.pixelatedw.mineminenomi.packets.client.quest.CUpdateQuestStatePacket;
import xyz.pixelatedw.mineminenomi.packets.client.trade.CBuyFromTraderPacket;
import xyz.pixelatedw.mineminenomi.packets.client.trade.CSellToTraderPacket;
import xyz.pixelatedw.mineminenomi.packets.client.ui.COpenAbilitiesListScreenPacket;
import xyz.pixelatedw.mineminenomi.packets.client.ui.COpenAbilityTreeScreenPacket;
import xyz.pixelatedw.mineminenomi.packets.client.ui.COpenChallengesScreenPacket;
import xyz.pixelatedw.mineminenomi.packets.client.ui.COpenCrewScreenPacket;
import xyz.pixelatedw.mineminenomi.packets.client.ui.COpenJollyRogerEditorScreenPacket;
import xyz.pixelatedw.mineminenomi.packets.client.ui.COpenPlayerScreenPacket;
import xyz.pixelatedw.mineminenomi.packets.client.ui.CSetDyeLayerPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SAddScreenShaderPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SRemoveScreenShaderPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSetFlagOnFirePacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSetRogueModePacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSpawnParticleEffectPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncAbilityDataPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncChallengeDataPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncDevilFruitPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncDynDimensionsPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncEntityStatsPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncHakiDataPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncProjectileExtrasPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncQuestDataPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncStrikerCrewPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SToggleAnimationPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SUpdateCombatStatePacket;
import xyz.pixelatedw.mineminenomi.packets.server.SViewProtectionPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.SDisableAbilityPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.SEquipAbilityPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.SRecalculateEyeHeightPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.SRemoveAbilityPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.SRemoveShadowEventsPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.SSetCombatBarPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.SSetHakiColorPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.SStartGCDPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.SToggleCombatModePacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.SToggleDrumsOfLiberationSoundPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.STriggerAbilityUseEventsPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.SUnlockAbilityNodePacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.SUpdateAbilityNBTPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.SUpdateEquippedAbilityPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.SUpdateHakiColorPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SChangeAbilityAltModePacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SChangeAnimationStatePacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SChangeMorphPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SForceStopChargingPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SSetContinuityThresholdPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SSetOverlayLayersPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SSetPauseTickPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SSetPoolInUsePacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SSetStacksPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SStartChargingPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SStartContinuityPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SStartCooldownPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SStartDisablePacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SStopChargingPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SStopContinuityPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SStopCooldownPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SStopDisablePacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SSwingTriggerPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.component.SUpdateAbilityComponentPacket;
import xyz.pixelatedw.mineminenomi.packets.server.challenge.SUpdateChallengeGroupPacket;
import xyz.pixelatedw.mineminenomi.packets.server.entity.SCarryEntityPacket;
import xyz.pixelatedw.mineminenomi.packets.server.entity.SForceDismountEntityPacket;
import xyz.pixelatedw.mineminenomi.packets.server.entity.SKairosekiCoatingPacket;
import xyz.pixelatedw.mineminenomi.packets.server.entity.SLeashPlayerPacket;
import xyz.pixelatedw.mineminenomi.packets.server.entity.SStopGCDPacket;
import xyz.pixelatedw.mineminenomi.packets.server.entity.SSyncCameraLockPacket;
import xyz.pixelatedw.mineminenomi.packets.server.entity.SUpdateColaAmountPacket;
import xyz.pixelatedw.mineminenomi.packets.server.entity.SUpdateLightningEntity;
import xyz.pixelatedw.mineminenomi.packets.server.entity.SUpdateProjBlockCollisionBox;
import xyz.pixelatedw.mineminenomi.packets.server.entity.SUpdateProjEntityCollisionBox;
import xyz.pixelatedw.mineminenomi.packets.server.quest.SFinishObjectivePacket;
import xyz.pixelatedw.mineminenomi.packets.server.randfruit.SSetFruitSeedsPacket;
import xyz.pixelatedw.mineminenomi.packets.server.randfruit.SSetRandomizedFruitsPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.SOpenAbilitySelectionScreenPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.SOpenAbilityTreeScreenPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.SOpenChallengesScreenPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.SOpenCharacterCreatorScreenPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.SOpenCreateCrewScreenPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.SOpenCrewScreenPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.SOpenEncyclopediaScreenPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.SOpenJollyRogerEditorScreenPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.SOpenPlayerScreenPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.SOpenQuestTrackerScreenPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.SOpenTraderScreenPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.SOpenWantedPosterScreenPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.SSetServerMaxBarsPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.SToggleDyeLayersPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.SUpdateOPBossEventPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.SUpdateRogueStateEventPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.acks.SScreenDataEventPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.acks.SSimpleMessageScreenEventPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.dialogues.SDialogueResponsePacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.dialogues.SOpenBarkeeperDialogueScreenPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.dialogues.SOpenTrainerDialogueScreenPacket;

@EventBusSubscriber(modid="mineminenomi")
public class ModNetwork {
	// Fields
	private static final String VERSION = "3";
	private static PayloadRegistrar registrar;

	@SubscribeEvent()
	public static void register(RegisterPayloadHandlersEvent var0) {
		registrar = var0.registrar("3");
		toServer(CUpdateQuestStatePacket.TYPE,CUpdateQuestStatePacket.STREAM_CODEC);
		toServer(CToggleCombatModePacket.TYPE,CToggleCombatModePacket.STREAM_CODEC);
		toServer(CUseAbilityPacket.TYPE,CUseAbilityPacket.STREAM_CODEC);
		toServer(CFinishCCPacket.TYPE,CFinishCCPacket.STREAM_CODEC);
		toServer(CRequestSyncQuestDataPacket.TYPE,CRequestSyncQuestDataPacket.STREAM_CODEC);
		toServer(CRemoveAbilityPacket.TYPE,CRemoveAbilityPacket.STREAM_CODEC);
		toServer(CCreateCrewPacket.TYPE,CCreateCrewPacket.STREAM_CODEC);
		toServer(CLeaveCrewPacket.TYPE,CLeaveCrewPacket.STREAM_CODEC);
		toServer(CUpdateJollyRogerPacket.TYPE,CUpdateJollyRogerPacket.STREAM_CODEC);
		toServer(CSetCombatBarPacket.TYPE,CSetCombatBarPacket.STREAM_CODEC);
		toServer(CStartChallengePacket.TYPE,CStartChallengePacket.STREAM_CODEC);
		toServer(CKickFromCrewPacket.TYPE,CKickFromCrewPacket.STREAM_CODEC);
		toServer(CAbandonQuestPacket.TYPE,CAbandonQuestPacket.STREAM_CODEC);
		toServer(CSellToTraderPacket.TYPE,CSellToTraderPacket.STREAM_CODEC);
		toServer(CBuyFromTraderPacket.TYPE,CBuyFromTraderPacket.STREAM_CODEC);
		toServer(CEquipAbilityPacket.TYPE,CEquipAbilityPacket.STREAM_CODEC);
		toServer(CTogglePassiveAbilityPacket.TYPE,CTogglePassiveAbilityPacket.STREAM_CODEC);
		toServer(COpenPlayerScreenPacket.TYPE,COpenPlayerScreenPacket.STREAM_CODEC);
		toServer(COpenChallengesScreenPacket.TYPE,COpenChallengesScreenPacket.STREAM_CODEC);
		toServer(COpenAbilitiesListScreenPacket.TYPE,COpenAbilitiesListScreenPacket.STREAM_CODEC);
		toServer(CSetHakiColorPacket.TYPE,CSetHakiColorPacket.STREAM_CODEC);
		toServer(CUpdateInventoryPagePacket.TYPE,CUpdateInventoryPagePacket.STREAM_CODEC);
		toServer(CDoctorHealPacket.TYPE,CDoctorHealPacket.STREAM_CODEC);
		toServer(CSwitchVehicleModePacket.TYPE,CSwitchVehicleModePacket.STREAM_CODEC);
		toServer(CSendChallengeInvitationPacket.TYPE,CSendChallengeInvitationPacket.STREAM_CODEC);
		toServer(CAcceptChallengeInvitationPacket.TYPE,CAcceptChallengeInvitationPacket.STREAM_CODEC);
		toServer(CDisbandChallengeGroupPacket.TYPE,CDisbandChallengeGroupPacket.STREAM_CODEC);
		toServer(CFetchPossibleInvitesPacket.TYPE,CFetchPossibleInvitesPacket.STREAM_CODEC);
		toServer(COpenCrewScreenPacket.TYPE,COpenCrewScreenPacket.STREAM_CODEC);
		toServer(CSyncPlayerInputPacket.TYPE,CSyncPlayerInputPacket.STREAM_CODEC);
		toServer(COpenJollyRogerEditorScreenPacket.TYPE,COpenJollyRogerEditorScreenPacket.STREAM_CODEC);
		toServer(CSetDyeLayerPacket.TYPE,CSetDyeLayerPacket.STREAM_CODEC);
		toServer(COpenAbilityTreeScreenPacket.TYPE,COpenAbilityTreeScreenPacket.STREAM_CODEC);
		toServer(CUnlockAbilityNodePacket.TYPE,CUnlockAbilityNodePacket.STREAM_CODEC);
		toServer(CBarkeeperBuyRumPacket.TYPE,CBarkeeperBuyRumPacket.STREAM_CODEC);
		toServer(CBarkeeperListNewsPacket.TYPE,CBarkeeperListNewsPacket.STREAM_CODEC);
		toServer(CBarkeeperListRumorsPacket.TYPE,CBarkeeperListRumorsPacket.STREAM_CODEC);
		toServer(CInteractionTriggeredPacket.TYPE,CInteractionTriggeredPacket.STREAM_CODEC);
		toServer(CTrainerResetTreePacket.TYPE,CTrainerResetTreePacket.STREAM_CODEC);
		toServer(CChangeAbilityAltModePacket.TYPE,CChangeAbilityAltModePacket.STREAM_CODEC);
		toClient(SSyncAbilityDataPacket.TYPE,SSyncAbilityDataPacket.STREAM_CODEC);
		toClient(SSyncQuestDataPacket.TYPE,SSyncQuestDataPacket.STREAM_CODEC);
		toClient(SSyncDevilFruitPacket.TYPE,SSyncDevilFruitPacket.STREAM_CODEC);
		toClient(SSyncEntityStatsPacket.TYPE,SSyncEntityStatsPacket.STREAM_CODEC);
		toClient(SRecalculateEyeHeightPacket.TYPE,SRecalculateEyeHeightPacket.STREAM_CODEC);
		toClient(SViewProtectionPacket.TYPE,SViewProtectionPacket.STREAM_CODEC);
		toClient(SOpenCharacterCreatorScreenPacket.TYPE,SOpenCharacterCreatorScreenPacket.STREAM_CODEC);
		toClient(SOpenWantedPosterScreenPacket.TYPE,SOpenWantedPosterScreenPacket.STREAM_CODEC);
		toClient(SOpenTraderScreenPacket.TYPE,SOpenTraderScreenPacket.STREAM_CODEC);
		toClient(SOpenJollyRogerEditorScreenPacket.TYPE,SOpenJollyRogerEditorScreenPacket.STREAM_CODEC);
		toClient(SOpenTrainerDialogueScreenPacket.TYPE,SOpenTrainerDialogueScreenPacket.STREAM_CODEC);
		toClient(SOpenCreateCrewScreenPacket.TYPE,SOpenCreateCrewScreenPacket.STREAM_CODEC);
		toClient(SSyncHakiDataPacket.TYPE,SSyncHakiDataPacket.STREAM_CODEC);
		toClient(SSyncChallengeDataPacket.TYPE,SSyncChallengeDataPacket.STREAM_CODEC);
		toClient(SUpdateEquippedAbilityPacket.TYPE,SUpdateEquippedAbilityPacket.STREAM_CODEC);
		toClient(SOpenPlayerScreenPacket.TYPE,SOpenPlayerScreenPacket.STREAM_CODEC);
		toClient(SSetCombatBarPacket.TYPE,SSetCombatBarPacket.STREAM_CODEC);
		toClient(SOpenChallengesScreenPacket.TYPE,SOpenChallengesScreenPacket.STREAM_CODEC);
		toClient(SSetRandomizedFruitsPacket.TYPE,SSetRandomizedFruitsPacket.STREAM_CODEC);
		toClient(SSetFruitSeedsPacket.TYPE,SSetFruitSeedsPacket.STREAM_CODEC);
		toClient(SOpenEncyclopediaScreenPacket.TYPE,SOpenEncyclopediaScreenPacket.STREAM_CODEC);
		toClient(SOpenQuestTrackerScreenPacket.TYPE,SOpenQuestTrackerScreenPacket.STREAM_CODEC);
		toClient(SOpenAbilitySelectionScreenPacket.TYPE,SOpenAbilitySelectionScreenPacket.STREAM_CODEC);
		toClient(SSyncDynDimensionsPacket.TYPE,SSyncDynDimensionsPacket.STREAM_CODEC);
		toClient(SSetHakiColorPacket.TYPE,SSetHakiColorPacket.STREAM_CODEC);
		toClient(SToggleCombatModePacket.TYPE,SToggleCombatModePacket.STREAM_CODEC);
		toClient(SDisableAbilityPacket.TYPE,SDisableAbilityPacket.STREAM_CODEC);
		toClient(SSpawnParticleEffectPacket.TYPE,SSpawnParticleEffectPacket.STREAM_CODEC);
		toClient(SUpdateOPBossEventPacket.TYPE,SUpdateOPBossEventPacket.STREAM_CODEC);
		toClient(SEquipAbilityPacket.TYPE,SEquipAbilityPacket.STREAM_CODEC);
		toClient(SStartGCDPacket.TYPE,SStartGCDPacket.STREAM_CODEC);
		toClient(SSetFlagOnFirePacket.TYPE,SSetFlagOnFirePacket.STREAM_CODEC);
		toClient(SUpdateColaAmountPacket.TYPE,SUpdateColaAmountPacket.STREAM_CODEC);
		toClient(SUpdateCombatStatePacket.TYPE,SUpdateCombatStatePacket.STREAM_CODEC);
		toClient(SCarryEntityPacket.TYPE,SCarryEntityPacket.STREAM_CODEC);
		toClient(SLeashPlayerPacket.TYPE,SLeashPlayerPacket.STREAM_CODEC);
		toClient(SUpdateChallengeGroupPacket.TYPE,SUpdateChallengeGroupPacket.STREAM_CODEC);
		toClient(SUpdateProjEntityCollisionBox.TYPE,SUpdateProjEntityCollisionBox.STREAM_CODEC);
		toClient(SUpdateProjBlockCollisionBox.TYPE,SUpdateProjBlockCollisionBox.STREAM_CODEC);
		toClient(SUpdateLightningEntity.TYPE,SUpdateLightningEntity.STREAM_CODEC);
		toClient(SOpenCrewScreenPacket.TYPE,SOpenCrewScreenPacket.STREAM_CODEC);
		toClient(SUpdateHakiColorPacket.TYPE,SUpdateHakiColorPacket.STREAM_CODEC);
		toClient(SToggleAnimationPacket.TYPE,SToggleAnimationPacket.STREAM_CODEC);
		toClient(SAddScreenShaderPacket.TYPE,SAddScreenShaderPacket.STREAM_CODEC);
		toClient(SRemoveScreenShaderPacket.TYPE,SRemoveScreenShaderPacket.STREAM_CODEC);
		toClient(SFinishObjectivePacket.TYPE,SFinishObjectivePacket.STREAM_CODEC);
		toClient(SSyncStrikerCrewPacket.TYPE,SSyncStrikerCrewPacket.STREAM_CODEC);
		toClient(SSimpleMessageScreenEventPacket.TYPE,SSimpleMessageScreenEventPacket.STREAM_CODEC);
		toClient(SScreenDataEventPacket.TYPE,SScreenDataEventPacket.STREAM_CODEC);
		toClient(SKairosekiCoatingPacket.TYPE,SKairosekiCoatingPacket.STREAM_CODEC);
		toClient(SSetServerMaxBarsPacket.TYPE,SSetServerMaxBarsPacket.STREAM_CODEC);
		toClient(SToggleDyeLayersPacket.TYPE,SToggleDyeLayersPacket.STREAM_CODEC);
		toClient(SSetRogueModePacket.TYPE,SSetRogueModePacket.STREAM_CODEC);
		toClient(STriggerAbilityUseEventsPacket.TYPE,STriggerAbilityUseEventsPacket.STREAM_CODEC);
		toClient(SToggleDrumsOfLiberationSoundPacket.TYPE,SToggleDrumsOfLiberationSoundPacket.STREAM_CODEC);
		toClient(SRemoveAbilityPacket.TYPE,SRemoveAbilityPacket.STREAM_CODEC);
		toClient(SOpenAbilityTreeScreenPacket.TYPE,SOpenAbilityTreeScreenPacket.STREAM_CODEC);
		toClient(SUnlockAbilityNodePacket.TYPE,SUnlockAbilityNodePacket.STREAM_CODEC);
		toClient(SUpdateAbilityNBTPacket.TYPE,SUpdateAbilityNBTPacket.STREAM_CODEC);
		toClient(SUpdateRogueStateEventPacket.TYPE,SUpdateRogueStateEventPacket.STREAM_CODEC);
		toClient(SRemoveShadowEventsPacket.TYPE,SRemoveShadowEventsPacket.STREAM_CODEC);
		toClient(SSyncProjectileExtrasPacket.TYPE,SSyncProjectileExtrasPacket.STREAM_CODEC);
		toClient(SOpenBarkeeperDialogueScreenPacket.TYPE,SOpenBarkeeperDialogueScreenPacket.STREAM_CODEC);
		toClient(SDialogueResponsePacket.TYPE,SDialogueResponsePacket.STREAM_CODEC);
		toClient(SForceDismountEntityPacket.TYPE,SForceDismountEntityPacket.STREAM_CODEC);
		toClient(SStopGCDPacket.TYPE,SStopGCDPacket.STREAM_CODEC);
		toClient(SSyncCameraLockPacket.TYPE,SSyncCameraLockPacket.STREAM_CODEC);
		toClient(SStartCooldownPacket.TYPE,SStartCooldownPacket.STREAM_CODEC);
		toClient(SStopCooldownPacket.TYPE,SStopCooldownPacket.STREAM_CODEC);
		toClient(SStartContinuityPacket.TYPE,SStartContinuityPacket.STREAM_CODEC);
		toClient(SStopContinuityPacket.TYPE,SStopContinuityPacket.STREAM_CODEC);
		toClient(SStartChargingPacket.TYPE,SStartChargingPacket.STREAM_CODEC);
		toClient(SStopChargingPacket.TYPE,SStopChargingPacket.STREAM_CODEC);
		toClient(SStartDisablePacket.TYPE,SStartDisablePacket.STREAM_CODEC);
		toClient(SStopDisablePacket.TYPE,SStopDisablePacket.STREAM_CODEC);
		toClient(SSetPoolInUsePacket.TYPE,SSetPoolInUsePacket.STREAM_CODEC);
		toClient(SSetStacksPacket.TYPE,SSetStacksPacket.STREAM_CODEC);
		toClient(SSetOverlayLayersPacket.TYPE,SSetOverlayLayersPacket.STREAM_CODEC);
		toClient(SSetPauseTickPacket.TYPE,SSetPauseTickPacket.STREAM_CODEC);
		toClient(SChangeAbilityAltModePacket.TYPE,SChangeAbilityAltModePacket.STREAM_CODEC);
		toClient(SSwingTriggerPacket.TYPE,SSwingTriggerPacket.STREAM_CODEC);
		toClient(SChangeMorphPacket.TYPE,SChangeMorphPacket.STREAM_CODEC);
		toClient(SChangeAnimationStatePacket.TYPE,SChangeAnimationStatePacket.STREAM_CODEC);
		toClient(SForceStopChargingPacket.TYPE,SForceStopChargingPacket.STREAM_CODEC);
		toClient(SSetContinuityThresholdPacket.TYPE,SSetContinuityThresholdPacket.STREAM_CODEC);
		toClient(SUpdateAbilityComponentPacket.TYPE,SUpdateAbilityComponentPacket.STREAM_CODEC);
	}
	private static <T extends IClientPacket> void toClient(CustomPacketPayload.Type<T> var0, StreamCodec<? super RegistryFriendlyByteBuf, T> var1) {
		registrar.playToClient(var0,var1,(IPayloadHandler)(((net.neoforged.neoforge.network.handling.IPayloadHandler)(ModNetwork::handlePacket))));
	}
	private static <T extends IServerPacket> void toServer(CustomPacketPayload.Type<T> var0, StreamCodec<? super RegistryFriendlyByteBuf, T> var1) {
		registrar.playToServer(var0,var1,(IPayloadHandler)(((net.neoforged.neoforge.network.handling.IPayloadHandler)(ModNetwork::handlePacket))));
	}
	private static <C extends IPayloadContext, T extends IPacket<C>> void handlePacket(T var0, IPayloadContext var1) {
		T var2 = var0;
		IPayloadContext var3 = var1;
		final T var2_f1 = var2;
		final IPayloadContext var3_f2 = var3;
		Runnable var4 = () -> {
			var2_f1.handle(var3_f2);
		};
		Function<Throwable, Void> var5 = (l0) -> {
			l0.printStackTrace();
			return null;
		};
		var1.enqueueWork(var4).exceptionally(var5);
		IPacket var6 = var0.reply((C)(var1));
		if ((var6) != (null)){
			var1.reply((CustomPacketPayload)(var6));
		}
	}
	public static void sendToServer(IServerPacket var0) {
		if (FGCommand.SHOW_PACKET_LOG){
			String var1 = String.valueOf((Object)(var0));
			String var2 = "Sent packet to server " + var1;
			WyDebug.debug(var2);
		}
		PacketDistributor.sendToServer((CustomPacketPayload)(var0),new CustomPacketPayload[0]);
	}
	public static void sendTo(IClientPacket var0, Player var1) {
		if (FGCommand.SHOW_PACKET_LOG){
			String var2 = String.valueOf((Object)(var0));
			String var3 = var1.getName().getString();
			String var4 = "Sent packet " + var2 + " to " + var3;
			WyDebug.debug(var4);
		}
		if (var1 instanceof ServerPlayer){
			ServerPlayer var2_1 = ((ServerPlayer)(var1));
			if ((var2_1.connection) != (null)){
				PacketDistributor.sendToPlayer(var2_1,(CustomPacketPayload)(var0),new CustomPacketPayload[0]);
			}else{
				return;
			}
		}
	}
	public static void sendToAll(IClientPacket var0) {
		if (FGCommand.SHOW_PACKET_LOG){
			String var1 = String.valueOf((Object)(var0));
			String var2 = "Sent packet to all " + var1;
			WyDebug.debug(var2);
		}
		PacketDistributor.sendToAllPlayers((CustomPacketPayload)(var0),new CustomPacketPayload[0]);
	}
	public static void sendToAllTracking(IClientPacket var0, Entity var1) {
		if (FGCommand.SHOW_PACKET_LOG){
			String var2 = String.valueOf((Object)(var0));
			String var3 = var1.getName().getString();
			String var4 = "Sent packet " + var2 + " to all tracking " + var3;
			WyDebug.debug(var4);
		}
		PacketDistributor.sendToPlayersTrackingEntity(var1,(CustomPacketPayload)(var0),new CustomPacketPayload[0]);
	}
	public static void sendToAllTrackingAndSelf(IClientPacket var0, LivingEntity var1) {
		sendToAllTracking(var0,(Entity)(var1));
		if (var1 instanceof Player){
			sendTo(var0,((Player)(var1)));
		}
	}
	public static void sendToAllAround(IClientPacket var0, Entity var1) {
		Level var2 = var1.level();
		if (var2 instanceof ServerLevel){
			sendToAllAroundDistance(var0,((ServerLevel)(var2)),var1.position(),256);
		}
	}
	public static void sendToAllAroundDistance(IClientPacket var0, ServerLevel var1, Vec3 var2, int var3) {
		if (FGCommand.SHOW_PACKET_LOG){
			String var4 = String.valueOf((Object)(var0));
			String var5 = String.valueOf((Object)(var2));
			String var6 = "Sent packet " + var4 + " to all around " + var5;
			WyDebug.debug(var6);
		}
		PacketDistributor.sendToPlayersNear(var1,(ServerPlayer)(null),var2.x(),var2.y(),var2.z(),(double)(var3),(CustomPacketPayload)(var0),new CustomPacketPayload[0]);
	}
}