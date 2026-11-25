package xyz.pixelatedw.mineminenomi.api.abilities.components;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine$IDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityStat;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityStat$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public class ChangeStatsComponent extends AbilityComponent<IAbility> {
	// Fields
	private Map<Holder<Attribute>, ImmutablePair<Predicate<LivingEntity>, AttributeModifier>> modifiers;
	private boolean hasModsApplied;

	@Deprecated()
	public static AbilityDescriptionLine$IDescriptionLine getTooltip() {
		AbilityDescriptionLine$IDescriptionLine var0 = (l0, l1) -> {
			Optional lv1_2 = l1.getComponent(((AbilityComponentKey)(ModAbilityComponents.CHANGE_STATS.get())));
			if (lv1_2.isPresent()){
				Component[] lv1_3 = new Component[(((ChangeStatsComponent)(lv1_2.get())).modifiers.size()) + (1)];
				int lv1_4 = 0;
				String lv1_5 = ModI18nAbilities.DESCRIPTION_STAT_NAME_STATS.getString();
				String lv1_6 = "§a" + lv1_5 + "§r";
				((java.lang.Object[]) (lv1_3))[lv1_4++] = Component.literal(lv1_6);
				Iterator lv1_7 = ((ChangeStatsComponent)(lv1_2.get())).modifiers.entrySet().iterator();
				do{
					if (lv1_7.hasNext()){
						Map.Entry lv1_8 = ((Map.Entry)(lv1_7.next()));
						Attribute lv1_9 = ((Attribute)(((Holder)(lv1_8.getKey())).value()));
						AttributeModifier lv1_10 = ((AttributeModifier)(((ImmutablePair)(lv1_8.getValue())).getRight()));
						float lv1_11 = (float)(lv1_10.amount());
						int lv1_12 = ((lv1_11) > (0F)) ? (1) : (0);
						MutableComponent lv1_13 = Component.translatable(lv1_9.getDescriptionId());
						String lv1_14 = "";
						switch (ChangeStatsComponent$1.$SwitchMap$net$minecraft$world$entity$ai$attributes$AttributeModifier$Operation[lv1_10.operation().ordinal()]){
						case 1:
							lv1_14 = ((lv1_12) != (0)) ? ("+") : ("");
							break;
						case 2:
							lv1_11 = (float)((lv1_9.getDefaultValue()) * (lv1_10.amount()));
							lv1_14 = ((lv1_12) != (0)) ? ("+") : ("");
							break;
						case 3:
							lv1_14 = "x";
							lv1_11 = (float)((1D) + (lv1_10.amount()));
							break;
						}
						Component lv1_15 = new AbilityStat$Builder((Component)(lv1_13),lv1_11).withSign(lv1_14).withBuff((lv1_12) != (0)).build().getStatDescription(2);
						lv1_3[lv1_4++] = lv1_15;
						continue;
					}else{
						break;
					}
				} while (true);
				StringBuilder lv1_8_1 = new StringBuilder();
				lv1_4 = 0;
				Component[] lv1_9_1 = lv1_3;
				int lv1_10_1 = lv1_9_1.length;
				int lv1_11_1 = 0;
				do{
					if ((lv1_11_1) < (lv1_10_1)){
						Component lv1_12_1 = lv1_9_1[lv1_11_1];
						String lv1_13_1 = lv1_12_1.getString();
						String lv1_14_1 = ((lv1_4++) < ((lv1_3.length) - (1))) ? ("\n") : ("");
						String lv1_15_1 = lv1_13_1 + lv1_14_1;
						lv1_8_1.append(lv1_15_1);
						lv1_11_1++;
						continue;
					}else{
						break;
					}
				} while (true);
				return Component.literal(lv1_8_1.toString());
			}else{
				return null;
			}
		};
		return var0;
	}
	public ChangeStatsComponent(IAbility var1) {
		super(((AbilityComponentKey)(ModAbilityComponents.CHANGE_STATS.get())),var1);
		this.modifiers = new HashMap();
	}
	public ChangeStatsComponent addAttributeModifier(Holder<Attribute> var1, AttributeModifier var2) {
		return this.addAttributeModifier(var1,var2,(Predicate)(null));
	}
	public ChangeStatsComponent addAttributeModifier(Holder<Attribute> var1, AttributeModifier var2, @Nullable() Predicate<LivingEntity> var3) {
		this.removeAttributeModifier(var1);
		this.modifiers.put(var1,ImmutablePair.of(var3,var2));
		return this;
	}
	public ChangeStatsComponent removeAttributeModifier(Holder<Attribute> var1) {
		this.modifiers.remove(var1);
		return this;
	}
	public void clearAttributeModifiers() {
		this.modifiers.clear();
	}
	public void applyModifiers(LivingEntity var1) {
		Iterator var2 = this.modifiers.entrySet().iterator();
		do{
			if (var2.hasNext()){
				Map.Entry var3 = ((Map.Entry)(var2.next()));
				this.applyModifier(var1,((Holder)(var3.getKey())),((AttributeModifier)(((ImmutablePair)(var3.getValue())).getRight())));
				continue;
			}else{
				break;
			}
		} while (true);
		this.hasModsApplied = true;
	}
	private void applyMissingModifier(LivingEntity var1, Holder<Attribute> var2, AttributeModifier var3) {
		if (!(this.hasModifier(var1,var2,var3.id()))){
			this.applyModifier(var1,var2,var3);
		}
		this.hasModsApplied = true;
	}
	private void applyModifier(LivingEntity var1, Holder<Attribute> var2, AttributeModifier var3) {
		this.ensureIsRegistered();
		AttributeInstance var4 = var1.getAttribute(var2);
		if ((var4) != (null)){
			var4.removeModifier(var3);
			var4.addTransientModifier(var3);
		}
	}
	public void removeModifiers(LivingEntity var1) {
		if (!(this.hasModsApplied)){
			return;
		}else{
			Iterator var2 = this.modifiers.entrySet().iterator();
			do{
				if (var2.hasNext()){
					Map.Entry var3 = ((Map.Entry)(var2.next()));
					this.removeModifier(var1,((Holder)(var3.getKey())),((AttributeModifier)(((ImmutablePair)(var3.getValue())).getRight())));
					continue;
				}else{
					break;
				}
			} while (true);
			this.hasModsApplied = false;
			return;
		}
	}
	public void removeModifier(LivingEntity var1, Holder<Attribute> var2, AttributeModifier var3) {
		this.removeModifier(var1,var2,var3.id());
	}
	public void removeModifier(LivingEntity var1, Holder<Attribute> var2, ResourceLocation var3) {
		AttributeInstance var4 = var1.getAttribute(var2);
		if ((this.hasModsApplied) && (((var4) != (null)) && (this.modifiers.keySet().contains(var4.getAttribute())))){
			var4.removeModifier(var3);
			if (var2.equals(Attributes.MAX_HEALTH)){
				float var5 = (var1.getHealth()) - (var1.getMaxHealth());
				if ((var5) > (0F)){
					var1.setHealth((var1.getHealth()) - (var5));
				}else{
					return;
				}
			}else{
				return;
			}
		}
	}
	public boolean hasModifier(LivingEntity var1, Holder<Attribute> var2) {
		return (var1.getAttribute(var2)) != (null);
	}
	private boolean hasModifier(LivingEntity var1, Holder<Attribute> var2, ResourceLocation var3) {
		AttributeInstance var4 = var1.getAttribute(var2);
		if ((var4) == (null)){
			return false;
		}else{
			return (var4.getModifier(var3)) != (null);
		}
	}
	protected void doTick(LivingEntity var1) {
		ChangeStatsComponent var2 = this;
		LivingEntity var3 = var1;
		final LivingEntity var3_f1 = var3;
		BiConsumer<Holder, ImmutablePair> var4 = (l0, l1) -> {
			if ((l1.getLeft()) != (null)){
				if (((Predicate)(l1.getLeft())).test(var3_f1)){
					this.applyMissingModifier(var3_f1,l0,((AttributeModifier)(l1.getRight())));
				}else{
					this.removeModifier(var3_f1,l0,((AttributeModifier)(l1.getRight())));
				}
			}
		};
		this.modifiers.forEach((BiConsumer)(var4));
	}
	public Map<Holder<Attribute>, ImmutablePair<Predicate<LivingEntity>, AttributeModifier>> getModifiers() {
		return new HashMap(this.modifiers);
	}
	@Nullable()
	public @Nullable() CompoundTag save(HolderLookup.Provider var1) {
		CompoundTag var2 = super.save(var1);
		var2.putBoolean("hasModsApplied",this.hasModsApplied);
		return var2;
	}
	public void load(HolderLookup.Provider var1, CompoundTag var2) {
		super.load(var1,var2);
		this.hasModsApplied = var2.getBoolean("hasModsApplied");
	}
}