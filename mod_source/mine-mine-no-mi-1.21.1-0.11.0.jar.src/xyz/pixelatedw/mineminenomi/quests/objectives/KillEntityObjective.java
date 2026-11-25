package xyz.pixelatedw.mineminenomi.quests.objectives;

import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.components.KillTriggerQuestComponent;
import xyz.pixelatedw.mineminenomi.api.quests.components.KillTriggerQuestComponent$IKillEntityEvent;
import xyz.pixelatedw.mineminenomi.api.quests.components.QuestComponent;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncQuestDataPacket;

public class KillEntityObjective extends Objective {
	// Fields
	public static final KillEntityObjective$ICheckKill DEFAULT_RULE;
	public static final KillEntityObjective$ICheckKill MELEE_CHECK;
	public static final KillEntityObjective$ICheckKill PROJECTILE_CHECK;
	private KillEntityObjective$ICheckKill killCheck;
	public final KillTriggerQuestComponent killTrigger;

	public KillEntityObjective(Quest var1, Component var2, int var3) {
		this(var1,var2,var3,DEFAULT_RULE);
	}
	public KillEntityObjective(Quest var1, Component var2, int var3, KillEntityObjective$ICheckKill var4) {
		super(var1,var2);
		this.killTrigger = new KillTriggerQuestComponent((Objective)(this)).addOnKillEvent(200,(KillTriggerQuestComponent$IKillEntityEvent)(((xyz.pixelatedw.mineminenomi.api.quests.components.KillTriggerQuestComponent$IKillEntityEvent)(this::checkKill))));
		QuestComponent[] var5 = new QuestComponent[]{this.killTrigger};
		super.addComponents(var5);
		super.setMaxProgress((float)(var3));
		this.killCheck = var4;
	}
	public void checkKill(Player var1, LivingEntity var2, DamageSource var3, Objective var4) {
		if (this.killCheck.test(var1,var2,var3)){
			var4.alterProgress(var1,1F);
			ModNetwork.sendTo(new SSyncQuestDataPacket(var1),var1);
		}
	}
	public KillEntityObjective$ICheckKill getCheck() {
		return this.killCheck;
	}
	public void addCheck(KillEntityObjective$ICheckKill var1) {
		this.killCheck = this.killCheck.and(var1);
	}
	static  {
		KillEntityObjective$ICheckKill var0 = (l0, l1, l2) -> {
			boolean lv1_3 = l1 instanceof Mob;
			AttributeInstance lv1_4 = l1.getAttributes().getInstance(Attributes.ATTACK_DAMAGE);
			int lv1_5 = (((lv1_4) != (null)) && ((lv1_4.getValue()) > (0D))) ? (1) : (0);
			return (lv1_3) && ((lv1_5) != (0));
		};
		DEFAULT_RULE = var0;
		KillEntityObjective$ICheckKill var1 = (l0, l1, l2) -> {
			return SharedHitChecks.isMelee(l2);
		};
		MELEE_CHECK = var1;
		KillEntityObjective$ICheckKill var2 = (l0, l1, l2) -> {
			return SharedHitChecks.isProjectile(l2);
		};
		PROJECTILE_CHECK = var2;
	}
}