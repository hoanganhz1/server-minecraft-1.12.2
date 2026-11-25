package xyz.pixelatedw.mineminenomi.api.quests;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.data.entity.QuestData;

public class QuestId<A extends Quest> {
	// Fields
	private ResourceLocation key;
	private String titleId;
	private Component title;
	 QuestId$IFactory<A> factory;
	 double repeatCooldown;
	 List<Holder<QuestId<? extends Quest>>> requirements;

	protected QuestId(QuestId$IFactory<A> var1) {
		this.repeatCooldown = -1D;
		this.requirements = new ArrayList();
		this.factory = var1;
	}
	public List<Holder<QuestId<? extends Quest>>> getRequirements() {
		return this.requirements;
	}
	private void setRequirements(List<Holder<QuestId<? extends Quest>>> var1) {
		this.requirements = var1;
	}
	protected String getOrCreateId() {
		if ((this.titleId) == (null)){
			ResourceLocation var1 = WyRegistry.QUESTS.getKey(this);
			if ((var1) != (null)){
				this.titleId = Util.makeDescriptionId("quest",var1);
			}
		}
		return this.titleId;
	}
	public String getTitleId() {
		return this.titleId;
	}
	public Component getTitle() {
		if ((this.title) == (null)){
			this.title = Component.translatable(this.getOrCreateId());
		}
		return this.title;
	}
	public boolean isLocked(Player var1) {
		QuestData var2 = ((QuestData)(((java.util.Optional)(QuestData.get(var1))).orElse((Object)(null))));
		if ((var2) == (null)){
			return true;
		}else{
			List var3 = this.getRequirements();
			if (((var3) != (null)) && ((var3.size()) > (0))){
				Iterator var4 = var3.iterator();
				do{
					if (var4.hasNext()){
						if (!(var2.hasFinishedQuest(((QuestId)(((Holder)(var4.next())).value()))))){
							return true;
						}else{
							continue;
						}
					}else{
						break;
					}
				} while (true);
				return false;
			}else{
				return false;
			}
		}
	}
	public boolean isRepeatable() {
		return (this.repeatCooldown) >= (0D);
	}
	public double getRepeatCooldown() {
		return this.repeatCooldown;
	}
	public void setRepeatCooldown(double var1) {
		this.repeatCooldown = var1;
	}
	public A createQuest() {
		return (A) (((xyz.pixelatedw.mineminenomi.api.quests.QuestId$IFactory)(this.factory)).create((QuestId)(this)));
	}
	@Nullable()
	public static <A extends Quest> @Nullable() QuestId<?> get(ResourceLocation var0) {
		return (QuestId<?>) (QuestId) (((QuestId)(WyRegistry.QUESTS.get(var0))));
	}
	@Nullable()
	public @Nullable() ResourceLocation getRegistryKey() {
		if ((this.key) == (null)){
			this.key = WyRegistry.QUESTS.getKey(this);
		}
		return this.key;
	}
	final void jdec$private$78797a2f706978656c61746564772f6d696e656d696e656e6f6d692f6170692f7175657374732f51756573744964$0(List arg0) { ((QuestId)this).setRequirements(arg0); }

}