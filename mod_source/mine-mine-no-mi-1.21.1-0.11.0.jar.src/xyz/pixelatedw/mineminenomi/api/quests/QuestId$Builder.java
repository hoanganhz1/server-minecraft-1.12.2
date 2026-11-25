package xyz.pixelatedw.mineminenomi.api.quests;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.Holder;

public class QuestId$Builder<A extends Quest> {
	// Fields
	 QuestId$IFactory<A> factory;
	 double repeatCooldown;
	 List<Holder<QuestId<? extends Quest>>> requirements;

	public QuestId$Builder(QuestId$IFactory<A> var1) {
		this.repeatCooldown = -1D;
		this.requirements = new ArrayList();
		this.factory = var1;
	}
	public QuestId$Builder<A> setRepeatCooldown(double var1) {
		this.repeatCooldown = var1;
		return this;
	}
	@SafeVarargs()
	public final QuestId$Builder<A> addRequirements(Holder<QuestId<? extends Quest>>... var1) {
		Holder<QuestId<? extends Quest>>[] var2 = var1;
		int var3 = var2.length;
		int var4 = 0;
		do{
			if ((var4) < (var3)){
				Holder<QuestId<? extends Quest>> var5 = var2[var4];
				this.requirements.add(var5);
				var4++;
				continue;
			}else{
				break;
			}
		} while (true);
		return this;
	}
	public QuestId<A> build() {
		QuestId var1 = new QuestId(this.factory);
		var1.setRepeatCooldown(this.repeatCooldown);
		((QuestId)(var1)).jdec$private$78797a2f706978656c61746564772f6d696e656d696e656e6f6d692f6170692f7175657374732f51756573744964$0(((List)(this.requirements)));
		return var1;
	}
}