package xyz.pixelatedw.mineminenomi.api.abilities.nodes;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.data.entity.AbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.EntityStatsData;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nNodes;

public class AbilityNode {
	// Fields
	private final Set<AbilityNode> prerequisites;
	private boolean unlocked;
	private final Component localizedName;
	private final ResourceLocation icon;
	private final AbilityNode$NodePos position;
	private final MutableComponent tooltip;
	private NodeUnlockCondition unlockCondition;
	private NodeUnlockAction unlockAction;

	public AbilityNode(Component var1, ResourceLocation var2, AbilityNode$NodePos var3) {
		this.prerequisites = new LinkedHashSet();
		this.unlocked = false;
		this.tooltip = Component.empty();
		this.localizedName = var1;
		this.icon = var2;
		this.position = var3;
	}
	public Component getLocalizedName() {
		return this.localizedName;
	}
	public ResourceLocation getIcon() {
		return this.icon;
	}
	public AbilityNode$NodePos getPosition() {
		return this.position;
	}
	public Component getTooltip() {
		return this.tooltip;
	}
	public void appendTooltip(Component var1) {
		this.tooltip.append(var1);
	}
	public Set<AbilityNode> getPrerequisites() {
		return (Set<AbilityNode>) (Set) (Set.copyOf((Collection)(this.prerequisites)));
	}
	@SafeVarargs()
	public final void addPrerequisites(AbilityNode... var1) {
		int var2 = 1;
		AbilityNode[] var3 = var1;
		int var4 = var3.length;
		int var5 = 0;
		do{
			if ((var5) < (var4)){
				AbilityNode var6 = var3[var5];
				this.prerequisites.add(var6);
				Object[] var7 = new Object[]{var6.getLocalizedName().getString()};
				this.appendTooltip((Component)(Component.translatable(ModI18nNodes.NODE_CHECK,var7)));
				if ((var2) != (var1.length)){
					this.appendTooltip((Component)(Component.literal("\n")));
				}
				var2++;
				var5++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
	public void setUnlockRule(@Nullable() NodeUnlockCondition var1, NodeUnlockAction var2) {
		this.unlockCondition = var1;
		this.unlockAction = var2;
		if ((var1) != (null)){
			if ((this.tooltip.getString().length()) > (0)){
				this.appendTooltip((Component)(Component.literal("\n\n")));
			}
			this.appendTooltip((Component)(var1.getTooltip()));
		}
	}
	public boolean isUnlocked(LivingEntity var1) {
		return this.unlocked;
	}
	public boolean canUnlock(LivingEntity var1) {
		if (this.isUnlocked(var1)){
			return false;
		}else{
			if (((this.unlockCondition) != (null)) && (!(this.unlockCondition.test(var1)))){
				return false;
			}else{
				Iterator var2 = this.prerequisites.iterator();
				do{
					if (var2.hasNext()){
						if (!(((AbilityNode)(var2.next())).isUnlocked(var1))){
							return false;
						}else{
							continue;
						}
					}else{
						break;
					}
				} while (true);
				EntityStatsData var3 = ((EntityStatsData)(((java.util.Optional)(EntityStatsData.get(var1))).orElse((Object)(null))));
				if (((var3) != (null)) && ((var3.getSpentTrainingPoints()) >= (AbilityHelper.getTrainingPointsLimit(var1)))){
					return false;
				}else{
					return true;
				}
			}
		}
	}
	public void unlockNode(LivingEntity var1) {
		if (this.unlocked){
			return;
		}else{
			if ((((AbilityData)(((java.util.Optional)(AbilityData.get(var1))).orElse((Object)(null))))) == (null)){
				return;
			}else{
				this.unlocked = true;
				if ((this.unlockAction) != (null)){
					this.unlockAction.onUnlock(var1);
				}
				return;
			}
		}
	}
	public void lockNode(LivingEntity var1) {
		if (!(this.unlocked)){
			return;
		}else{
			if ((this.unlockAction) != (null)){
				this.unlockAction.onLock(var1);
			}
			this.unlocked = false;
			return;
		}
	}
	public CompoundTag save() {
		CompoundTag var1 = new CompoundTag();
		var1.putBoolean("unlocked",this.unlocked);
		return var1;
	}
	public void load(CompoundTag var1) {
		this.unlocked = var1.getBoolean("unlocked");
	}
	public int hashCode() {
		int var2 = 1;
		var2 = ((31) * (var2)) + (this.localizedName.hashCode());
		var2 = ((31) * (var2)) + (this.icon.hashCode());
		var2 = ((31) * (var2)) + (this.position.hashCode());
		return var2;
	}
}