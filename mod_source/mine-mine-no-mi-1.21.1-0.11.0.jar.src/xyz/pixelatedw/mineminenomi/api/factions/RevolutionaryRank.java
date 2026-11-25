package xyz.pixelatedw.mineminenomi.api.factions;

import java.util.Optional;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;

public enum RevolutionaryRank implements IFactionRank {
	// Fields
	MEMBER(ModI18n.REVOLUTIONARY_TITLE_MEMBER, 0),
	OFFICER(ModI18n.REVOLUTIONARY_TITLE_OFFICER, 30),
	COMMANDER(ModI18n.REVOLUTIONARY_TITLE_COMMANDER, 50),
	CHIEF_OF_STAFF(ModI18n.REVOLUTIONARY_TITLE_CHIEF_OF_STAFF, 80),
	SUPREME_COMMANDER(ModI18n.REVOLUTIONARY_TITLE_SUPREME_COMMANDER, 100);
	private Component unlocalizedName;
	private int loyaltyRequired;

	private RevolutionaryRank(Component var3, int var4) {
		this.unlocalizedName = var3;
		this.loyaltyRequired = var4;
	}
	public Component getLocalizedName() {
		return this.unlocalizedName;
	}
	public int getRequiredLoyalty() {
		return this.loyaltyRequired;
	}
	public boolean isUnlocked(LivingEntity var1) {
		EntityStatsData var2 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get(var1))).orElse((Object)(null))));
		if (((var2) != (null)) && (var2.isRevolutionary())){
			int var3 = (this.ordinal()) + (1);
			if ((var3) >= (values().length)){
				return true;
			}else{
				RevolutionaryRank var4 = values()[var3];
				if (((var2.getLoyalty()) >= ((double)(this.getRequiredLoyalty()))) && (!(((var4) != (null)) && (!((var2.getLoyalty()) < ((double)(var4.getRequiredLoyalty()))))))){
					return true;
				}else{
					return false;
				}
			}
		}else{
			return false;
		}
	}
}