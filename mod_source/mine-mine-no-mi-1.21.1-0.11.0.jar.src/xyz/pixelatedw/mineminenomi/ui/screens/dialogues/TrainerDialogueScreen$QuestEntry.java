package xyz.pixelatedw.mineminenomi.ui.screens.dialogues;

import xyz.pixelatedw.mineminenomi.api.quests.QuestId;

public class TrainerDialogueScreen$QuestEntry {
	// Fields
	private final QuestId<?> id;
	private boolean complete;
	private boolean locked;

	public TrainerDialogueScreen$QuestEntry(QuestId<?> var1) {
		this.id = var1;
	}
	public void setComplete() {
		this.complete = true;
	}
	public void setLocked() {
		this.locked = true;
	}
	public QuestId<?> getQuestId() {
		return (QuestId<?>) (QuestId) (this.id);
	}
	public boolean isCompleted() {
		return this.complete;
	}
	public boolean isLocked() {
		return this.locked;
	}
}