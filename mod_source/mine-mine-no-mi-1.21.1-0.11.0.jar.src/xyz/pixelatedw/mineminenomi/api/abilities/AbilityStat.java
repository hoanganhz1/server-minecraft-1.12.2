package xyz.pixelatedw.mineminenomi.api.abilities;

import java.text.NumberFormat;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.util.INumberRange;
import xyz.pixelatedw.mineminenomi.config.ClientConfig;

public class AbilityStat {
	// Fields
	 Component name;
	 @Nullable() Component unit;
	 INumberRange<?> value;
	 INumberRange<?> bonus;
	 AbilityStat$AbilityStatType bonusType;
	private boolean isBuff;
	 String sign;

	AbilityStat(Component var1, INumberRange<?> var2) {
		this.name = var1;
		this.value = var2;
	}
	private void setUnit(Component var1) {
		this.unit = var1;
	}
	private void setBonus(INumberRange<?> var1, AbilityStat$AbilityStatType var2) {
		this.bonus = var1;
		this.bonusType = var2;
	}
	private void setBuff(boolean var1) {
		this.isBuff = var1;
	}
	private void setSign(String var1) {
		this.sign = var1;
	}
	public Component getStatDescription() {
		return this.getStatDescription(0);
	}
	public Component getStatDescription(int var1) {
		Object[] var2 = new Object[10];
		int var3 = 0;
		StringBuilder var4 = new StringBuilder();
		var4.append("%s%s§r");
		var2[var3++] = (this.isBuff) ? ("§a") : ("§c");
		var2[var3++] = this.name.getString();
		NumberFormat var5 = NumberFormat.getInstance();
		var5.setMaximumFractionDigits(2);
		float var6 = 0F;
		float var7 = 0F;
		String var8 = "";
		if (((this.bonus) != (null)) && (ClientConfig.isAbilityBonusesMergeEnable())){
			var8 = this.bonusType.getColorCode();
			var6 = this.bonus.getMin().floatValue();
			if (this.bonus.isFixed()){
				var7 = var6;
			}else{
				if (this.bonus.isRange()){
					var7 = this.bonus.getMax().floatValue();
				}
			}
		}
		if (this.value.isInfinite()){
			var4.append(" ∞");
		}else{
			if (this.value.isRange()){
				var4.append(" %s%s%s-%s§r");
				var2[var3++] = var8;
				var2[var3++] = this.sign;
				var2[var3++] = var5.format((double)((this.value.getMin().floatValue()) + (var6)));
				var2[var3++] = var5.format((double)((this.value.getMax().floatValue()) + (var7)));
			}else{
				var4.append(" %s%s%s§r");
				var2[var3++] = this.sign;
				var2[var3++] = var8;
				var2[var3++] = var5.format((double)((this.value.getMin().floatValue()) + (var6)));
			}
		}
		if ((this.unit) != (null)){
			var4.append(" %s");
			var2[var3++] = this.unit.getString();
		}
		if ((var1) > (0)){
			do{
				if ((var1) > (0)){
					var4.insert(0," ");
					var1--;
					continue;
				}else{
					break;
				}
			} while (true);
		}
		if (((this.bonus) != (null)) && (!(ClientConfig.isAbilityBonusesMergeEnable()))){
			float var9 = this.bonus.getMin().floatValue();
			float var10 = this.bonus.getMax().floatValue();
			if ((this.bonus.isRange()) && (!(((var9) == (0F)) && ((var10) == (0F))))){
				var4.append(" (%s%s§r-%s%s§r)");
				String var11 = this.bonusType.getColorCode();
				String var12 = ((var9) == (0F)) ? ("") : ((String)(((var9) > (0F)) ? ("+") : ("")));
				String var13 = var11 + var12;
				var2[var3++] = var13;
				var2[var3++] = var5.format((Object)(this.bonus.getMin()));
				String var14 = this.bonusType.getColorCode();
				String var15 = ((var10) == (0F)) ? ("") : ((String)(((var10) > (0F)) ? ("+") : ("")));
				String var16 = var14 + var15;
				var2[var3++] = var16;
				var2[var3++] = var5.format((Object)(this.bonus.getMax()));
			}else{
				if ((this.bonus.isFixed()) && ((var9) != (0F))){
					var4.append(" (%s%s§r)");
					String var11_1 = this.bonusType.getColorCode();
					String var12_1 = ((var9) > (0F)) ? ("+") : ("");
					String var13_1 = var11_1 + var12_1;
					var2[var3++] = var13_1;
					var2[var3++] = var5.format((Object)(this.bonus.getMin()));
				}
			}
		}
		return Component.literal(String.format(var4.toString(),var2));
	}
	final void jdec$private$78797a2f706978656c61746564772f6d696e656d696e656e6f6d692f6170692f6162696c69746965732f4162696c69747953746174$0(INumberRange arg0, AbilityStat$AbilityStatType arg1) { ((AbilityStat)this).setBonus(arg0, arg1); }

	final void jdec$private$78797a2f706978656c61746564772f6d696e656d696e656e6f6d692f6170692f6162696c69746965732f4162696c69747953746174$1(boolean arg0) { ((AbilityStat)this).setBuff(arg0); }

	final void jdec$private$78797a2f706978656c61746564772f6d696e656d696e656e6f6d692f6170692f6162696c69746965732f4162696c69747953746174$2(String arg0) { ((AbilityStat)this).setSign(arg0); }

	final void jdec$private$78797a2f706978656c61746564772f6d696e656d696e656e6f6d692f6170692f6162696c69746965732f4162696c69747953746174$3(Component arg0) { ((AbilityStat)this).setUnit(arg0); }

}