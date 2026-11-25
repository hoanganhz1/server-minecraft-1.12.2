package xyz.pixelatedw.mineminenomi.api.factions;

import java.util.Optional;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;

public enum MarineRank implements IFactionRank {
	// Fields
	CHORE_BOY(ModI18n.MARINE_TITLE_CHORE_BOY, 0),
	SEAMAN(ModI18n.MARINE_TITLE_SEAMAN, 5),
	PETTY_OFFICER(ModI18n.MARINE_TITLE_PETTY_OFFICER, 10),
	LIEUTENANT(ModI18n.MARINE_TITLE_LIEUTENANT, 15),
	COMMANDER(ModI18n.MARINE_TITLE_COMMANDER, 20),
	CAPTAIN(ModI18n.MARINE_TITLE_CAPTAIN, 25),
	COMMODORE(ModI18n.MARINE_TITLE_COMMODORE, 40),
	VICE_ADMIRAL(ModI18n.MARINE_TITLE_VICE_ADMIRAL, 50),
	ADMIRAL(ModI18n.MARINE_TITLE_ADMIRAL, 70),
	FLEET_ADMIRAL(ModI18n.MARINE_TITLE_FLEET_ADMIRAL, 100);
	private Component unlocalizedName;
	private int loyaltyRequired;

	private MarineRank(Component var3, int var4) {
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
		if (((var2) != (null)) && (var2.isMarine())){
			int var3 = (this.ordinal()) + (1);
			if ((var3) >= (values().length)){
				return true;
			}else{
				MarineRank var4 = values()[var3];
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