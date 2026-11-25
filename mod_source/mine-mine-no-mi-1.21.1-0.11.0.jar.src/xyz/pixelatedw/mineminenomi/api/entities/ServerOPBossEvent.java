package xyz.pixelatedw.mineminenomi.api.entities;

import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeInfo;
import xyz.pixelatedw.mineminenomi.api.challenges.IChallengeBoss;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.IClientPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ui.SUpdateOPBossEventPacket;

public class ServerOPBossEvent extends ServerBossEvent {
	// Fields
	private static final BossEvent.BossBarColor[] COLOR_ORDER;
	private int totalBars;
	private int activeBars;
	private final int type;

	public ServerOPBossEvent(Component var1) {
		super(var1,BossEvent.BossBarColor.RED,BossEvent.BossBarOverlay.NOTCHED_10);
		this.totalBars = -1;
		this.activeBars = -1;
		this.type = 2;
	}
	public void tick(LivingEntity var1) {
		if ((var1 instanceof IChallengeBoss) && (((IChallengeBoss)(var1)).getChallengeInfo().isDifficultyHard())){
			Style var2 = this.getName().getStyle().applyFormat(ChatFormatting.DARK_RED).withBold(Boolean.valueOf(true));
			this.setName((Component)(this.getName().copy().setStyle(var2)));
		}
		if ((this.type) == (0)){
			this.setOverlay(BossEvent.BossBarOverlay.NOTCHED_10);
			this.setColor(BossEvent.BossBarColor.RED);
			this.setProgress((var1.getHealth()) / (var1.getMaxHealth()));
		}else{
			if ((this.type) == (1)){
				int var2_1 = (int)(((var1.getMaxHealth()) / (500F)) * (5F));
				var2_1 = Math.max(1,var2_1);
				var2_1 = Math.min(COLOR_ORDER.length,var2_1);
				float var3 = (var1.getMaxHealth()) / ((float)(var2_1));
				if ((var1.getHealth()) == (var1.getMaxHealth())){
					this.setColor(COLOR_ORDER[(var2_1) - (1)]);
					this.setProgress(1F);
					return;
				}else{
					float var4 = ((var1.getHealth()) % (var3)) / (var3);
					int var5 = ((int)(Math.ceil((double)(((var1.getHealth()) / (var1.getMaxHealth())) * ((float)(var2_1)))))) - (1);
					var5 = Math.max(0,var5);
					this.setColor(COLOR_ORDER[var5]);
					this.setProgress(var4);
				}
			}else{
				if ((this.type) == (2)){
					float var2_2 = Math.min(var1.getMaxHealth(),100F);
					int var3_1 = (int)(Math.ceil((double)(((var1.getMaxHealth()) - (var2_2)) / (var2_2))));
					int var4_1 = (int)(Math.ceil((double)(((var1.getHealth()) - (var2_2)) / (var2_2))));
					this.setBars(var3_1,var4_1);
					if ((var1.getHealth()) == (var1.getMaxHealth())){
						this.setProgress(1F);
						return;
					}else{
						float var5_1 = Math.min((var1.getMaxHealth()) - ((float)((var4_1) * (100))),100F);
						this.setProgress(((var1.getHealth()) % (var2_2)) / (var5_1));
					}
				}else{
					return;
				}
			}
		}
	}
	public void setBars(int var1, int var2) {
		boolean var3 = false;
		if ((this.totalBars) != (var1)){
			this.totalBars = var1;
			var3 = true;
		}
		if ((this.activeBars) != (var2)){
			this.activeBars = var2;
			var3 = true;
		}
		if (var3){
			this.update();
		}
	}
	public void update() {
		if (this.isVisible()){
			SUpdateOPBossEventPacket var1 = new SUpdateOPBossEventPacket(this.getId(),this.totalBars,this.activeBars);
			Iterator var2 = this.getPlayers().iterator();
			do{
				if (var2.hasNext()){
					ModNetwork.sendTo(var1,(Player)(((ServerPlayer)(var2.next()))));
					continue;
				}else{
					break;
				}
			} while (true);
		}
	}
	static  {
		BossEvent.BossBarColor[] var0 = new BossEvent.BossBarColor[]{BossEvent.BossBarColor.GREEN,BossEvent.BossBarColor.YELLOW,BossEvent.BossBarColor.PINK,BossEvent.BossBarColor.RED,BossEvent.BossBarColor.PURPLE};
		COLOR_ORDER = var0;
	}
}