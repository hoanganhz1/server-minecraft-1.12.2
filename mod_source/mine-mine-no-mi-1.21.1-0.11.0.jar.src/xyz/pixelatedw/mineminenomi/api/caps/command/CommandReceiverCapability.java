package xyz.pixelatedw.mineminenomi.api.caps.command;

import java.util.Optional;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.capabilities.EntityCapability;

public class CommandReceiverCapability {
	// Fields
	public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("mineminenomi","command_receiver");
	public static final EntityCapability<ICommandReceiver, Void> COMMAND_RECEIVER = EntityCapability.createVoid(ID,ICommandReceiver.class);

	public static Optional<ICommandReceiver> get(LivingEntity var0) {
		return Optional.ofNullable(((ICommandReceiver)(var0.getCapability(COMMAND_RECEIVER,null))));
	}
}