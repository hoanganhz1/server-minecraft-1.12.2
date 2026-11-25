package xyz.pixelatedw.mineminenomi.api.events.entity;

import java.awt.Color;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.event.entity.EntityEvent;

public class OutlineColorEvent extends EntityEvent {
	// Fields
	private Color color;

	public OutlineColorEvent(Entity var1) {
		super(var1);
		this.color = Color.WHITE;
	}
	public void setColor(Color var1) {
		this.color = var1;
	}
	public Color getColor() {
		return this.color;
	}
}