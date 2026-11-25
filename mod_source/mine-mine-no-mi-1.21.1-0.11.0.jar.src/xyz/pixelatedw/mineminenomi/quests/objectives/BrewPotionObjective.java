package xyz.pixelatedw.mineminenomi.quests.objectives;

import com.google.common.collect.Sets;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.IBrewPotionObjective;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;
import xyz.pixelatedw.mineminenomi.init.ModDataComponents;

public class BrewPotionObjective extends Objective implements IBrewPotionObjective {
	// Fields
	private Set<Holder<MobEffect>> effects;
	private Item[] types;

	public BrewPotionObjective(Quest var1, Component var2, int var3) {
		this(var1,var2,var3,new Item[]{Items.POTION,Items.LINGERING_POTION,Items.SPLASH_POTION},null);
	}
	public BrewPotionObjective(Quest var1, Component var2, int var3, Holder<MobEffect>[] var4) {
		this(var1,var2,var3,new Item[]{Items.POTION,Items.LINGERING_POTION,Items.SPLASH_POTION},var4);
	}
	public BrewPotionObjective(Quest var1, Component var2, int var3, Item[] var4, Holder<MobEffect>[] var5) {
		super(var1,var2);
		this.types = null;
		this.setMaxProgress((float)(var3));
		this.effects = Sets.newHashSet(var5);
		this.types = var4;
	}
	public boolean checkPotion(Player var1, ItemStack var2) {
		if ((this.types) == (null)){
			return false;
		}else{
			if (var2.has(ModDataComponents.QUEST_MARKER)){
				return false;
			}else{
				boolean var3 = false;
				boolean var4 = true;
				Item[] var5 = this.types;
				int var6 = var5.length;
				int var7 = 0;
				do{
					if ((var7) < (var6)){
						Item var8 = var5[var7];
						if ((var2.getItem()) == (var8)){
							var3 = true;
							break;
						}else{
							var7++;
							continue;
						}
					}else{
						break;
					}
				} while (true);
				PotionContents var8_1 = ((PotionContents)(var2.getOrDefault(DataComponents.POTION_CONTENTS,PotionContents.EMPTY)));
				if (((this.effects) != (null)) && (var3)){
					Iterator var9 = var8_1.getAllEffects().iterator();
					do{
						if (var9.hasNext()){
							MobEffectInstance var10 = ((MobEffectInstance)(var9.next()));
							var4 = this.effects.contains(var10.getEffect());
							if (var4){
								break;
							}else{
								continue;
							}
						}else{
							break;
						}
					} while (true);
				}else{
					if (((this.effects) == (null)) && (var3)){
						var4 = var8_1.hasEffects();
					}
				}
				if ((var2.isEmpty()) && (var4)){
					var3 = true;
				}
				if ((var3) && (var4)){
					var2.set(ModDataComponents.QUEST_MARKER,Boolean.valueOf(true));
				}
				return (var3) && (var4);
			}
		}
	}
}