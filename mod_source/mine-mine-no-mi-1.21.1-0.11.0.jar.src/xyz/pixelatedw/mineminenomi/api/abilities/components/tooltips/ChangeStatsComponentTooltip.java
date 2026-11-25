package xyz.pixelatedw.mineminenomi.api.abilities.components.tooltips;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityStat$Builder;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityTooltip;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveStatBonusAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;

public class ChangeStatsComponentTooltip {
	// Fields
	private boolean disableAuto;
	private HashMap<Attribute, AttributeModifier> manualAttributes;

	public ChangeStatsComponentTooltip() {
		this.manualAttributes = (HashMap) (new HashMap());
	}
	public static void generate(AbilityTooltip var0, Consumer<ChangeStatsComponentTooltip> var1) {
		ChangeStatsComponentTooltip var2 = new ChangeStatsComponentTooltip();
		var1.accept(var2);
		ChangeStatsComponent var3 = ((ChangeStatsComponent)(var0.getAbility().getComponentOrThrow(((AbilityComponentKey)(ModAbilityComponents.CHANGE_STATS.get())))));
		Comparator var4 = (Comparator) ((Comparator<Map.Entry>) ((l0, l1) -> {
			return ((Attribute)(((Holder)(l0.getKey())).value())).getDescriptionId().compareTo(((Attribute)(((Holder)(l1.getKey())).value())).getDescriptionId());
		}));
		Set var5 = ((Set)(((java.util.stream.Stream)(((java.util.stream.Stream)(var3.getModifiers().entrySet().stream())).sorted((Comparator)(var4)))).collect((Collector)(Collectors.toSet()))));
		if (var5.isEmpty()){
			return;
		}else{
			LinkedList var6 = new LinkedList();
			String var7 = ModI18nAbilities.DESCRIPTION_STAT_NAME_STATS.getString();
			String var8 = "§a" + var7 + "§r";
			var6.add(Component.literal(var8));
			HashMap var9 = new HashMap();
			if (!(var2.disableAuto)){
				Iterator var10 = var5.iterator();
				do{
					if (var10.hasNext()){
						Map.Entry var11 = ((Map.Entry)(var10.next()));
						AttributeModifier var12 = ((AttributeModifier)(((ImmutablePair)(var11.getValue())).getRight()));
						var9.put(((Attribute)(((Holder)(var11.getKey())).value())),var12);
						continue;
					}else{
						break;
					}
				} while (true);
			}
			var9.putAll((Map)(var2.manualAttributes));
			IAbility var10_1 = var0.getAbility();
			if (var10_1 instanceof PassiveStatBonusAbility){
				Iterator var11_1 = ((PassiveStatBonusAbility)(var10_1)).getDynamicAttributes().entrySet().iterator();
				do{
					if (var11_1.hasNext()){
						Map.Entry var12_1 = ((Map.Entry)(var11_1.next()));
						AttributeModifier var13 = ((AttributeModifier)(((Function)(var12_1.getValue())).apply(var0.getPlayer())));
						var9.put(((Attribute)(((Holder)(var12_1.getKey())).value())),var13);
						continue;
					}else{
						break;
					}
				} while (true);
			}
			Iterator var11_2 = var9.entrySet().iterator();
			do{
				if (var11_2.hasNext()){
					Map.Entry var12_2 = ((Map.Entry)(var11_2.next()));
					float var13_1 = (float)(((AttributeModifier)(var12_2.getValue())).amount());
					int var14 = ((var13_1) > (0F)) ? (1) : (0);
					MutableComponent var15 = Component.translatable(((Attribute)(var12_2.getKey())).getDescriptionId());
					String var16 = "";
					switch (ChangeStatsComponentTooltip$1.$SwitchMap$net$minecraft$world$entity$ai$attributes$AttributeModifier$Operation[((AttributeModifier)(var12_2.getValue())).operation().ordinal()]){
					case 1:
						var16 = ((var14) != (0)) ? ("+") : ("");
						break;
					case 2:
						var13_1 = (float)((((Attribute)(var12_2.getKey())).getDefaultValue()) * (((AttributeModifier)(var12_2.getValue())).amount()));
						var16 = ((var14) != (0)) ? ("+") : ("");
						break;
					case 3:
						var16 = "x";
						var13_1 = (float)((1D) + (((AttributeModifier)(var12_2.getValue())).amount()));
						break;
					}
					var6.add(new AbilityStat$Builder((Component)(var15),var13_1).withSign(var16).withBuff((var14) != (0)).buildComponent(2));
					continue;
				}else{
					break;
				}
			} while (true);
			var0.set(((AbilityComponentKey)(ModAbilityComponents.CHANGE_STATS.get())),(Collection)(var6));
			return;
		}
	}
	public ChangeStatsComponentTooltip disableAuto() {
		this.disableAuto = true;
		return this;
	}
	public ChangeStatsComponentTooltip addAttribute(Attribute var1, AttributeModifier var2) {
		this.manualAttributes.put(var1,var2);
		return this;
	}
}