package xyz.pixelatedw.mineminenomi.api.abilities.components;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class BonusManager {
	// Fields
	 UUID id;
	private Map<UUID, BonusManager$BonusValue> bonuses;

	public BonusManager(UUID var1) {
		this.bonuses = new HashMap();
		this.id = var1;
	}
	public float applyBonus(float var1) {
		Iterator var2 = this.bonuses.values().iterator();
		do{
			if (var2.hasNext()){
				BonusManager$BonusValue var3 = ((BonusManager$BonusValue)(var2.next()));
				switch (BonusManager$1.$SwitchMap$xyz$pixelatedw$mineminenomi$api$abilities$components$BonusOperation[var3.getType().ordinal()]){
				case 1:
					var1 = (var1) + (var3.getValue());
					break;
				case 2:
					var1 = (var1) * (var3.getValue());
					break;
				}
				continue;
			}else{
				break;
			}
		} while (true);
		var1 = Math.max(0F,var1);
		return var1;
	}
	public boolean hasBonus(UUID var1) {
		return this.bonuses.containsKey(var1);
	}
	public void addBonus(BonusManager$BonusValue var1) {
		this.bonuses.put(var1.getUUID(),var1);
	}
	public void addBonus(UUID var1, String var2, BonusOperation var3, float var4) {
		this.bonuses.put(var1,new BonusManager$BonusValue(var1,var2,var3,var4));
	}
	public void removeBonus(UUID var1) {
		this.bonuses.remove(var1);
	}
	public void clearBonuses() {
		this.bonuses.clear();
	}
	public UUID getId() {
		return this.id;
	}
	public void setBonusMap(Map<UUID, BonusManager$BonusValue> var1) {
		this.bonuses = var1;
	}
	public Set<Map.Entry<UUID, BonusManager$BonusValue>> getBonuses() {
		return (Set<Map.Entry<UUID, BonusManager$BonusValue>>) (Set) (this.bonuses.entrySet());
	}
}