package xyz.pixelatedw.mineminenomi.api.events.entity;

import java.util.Optional;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;

public class SetPlayerDetailsEvent extends PlayerEvent {
	// Fields
	private Optional<EntityStatsData> props;

	public SetPlayerDetailsEvent(Player var1) {
		super(var1);
		this.props = (Optional) (EntityStatsData.get((LivingEntity)(var1)));
	}
	public Optional<EntityStatsData> getEntityStats() {
		return this.props;
	}
}