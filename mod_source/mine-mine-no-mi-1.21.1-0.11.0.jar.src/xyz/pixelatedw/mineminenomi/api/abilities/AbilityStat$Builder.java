package xyz.pixelatedw.mineminenomi.api.abilities;

import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.util.FloatRange;
import xyz.pixelatedw.mineminenomi.api.util.INumberRange;
import xyz.pixelatedw.mineminenomi.api.util.IntRange;

public class AbilityStat$Builder {
	// Fields
	 Component name;
	 @Nullable() Component unit;
	 INumberRange<?> value;
	 INumberRange<?> bonus;
	 AbilityStat$AbilityStatType bonusType;
	private boolean isStatBuff;
	 String sign;

	public AbilityStat$Builder(Component var1, float var2) {
		this.bonusType = AbilityStat$AbilityStatType.NEUTRAL;
		this.isStatBuff = true;
		this.sign = "";
		this.name = var1;
		this.value = (INumberRange<?>) (new FloatRange(var2));
	}
	public AbilityStat$Builder(Component var1, float var2, float var3) {
		this.bonusType = AbilityStat$AbilityStatType.NEUTRAL;
		this.isStatBuff = true;
		this.sign = "";
		this.name = var1;
		this.value = (INumberRange<?>) (new FloatRange(var2,var3));
	}
	public AbilityStat$Builder(Component var1, int var2) {
		this.bonusType = AbilityStat$AbilityStatType.NEUTRAL;
		this.isStatBuff = true;
		this.sign = "";
		this.name = var1;
		this.value = (INumberRange<?>) (new IntRange(var2));
	}
	public AbilityStat$Builder(Component var1, int var2, int var3) {
		this.bonusType = AbilityStat$AbilityStatType.NEUTRAL;
		this.isStatBuff = true;
		this.sign = "";
		this.name = var1;
		this.value = (INumberRange<?>) (new IntRange(var2,var3));
	}
	public AbilityStat$Builder withUnit(Component var1) {
		this.unit = var1;
		return this;
	}
	public AbilityStat$Builder withBonus(float var1, AbilityStat$AbilityStatType var2) {
		this.bonus = (INumberRange<?>) (new FloatRange(var1));
		this.bonusType = var2;
		return this;
	}
	public AbilityStat$Builder withBonus(float var1, float var2, AbilityStat$AbilityStatType var3) {
		this.bonus = (INumberRange<?>) (new FloatRange(var1,var2));
		this.bonusType = var3;
		return this;
	}
	public AbilityStat$Builder withBuff(boolean var1) {
		this.isStatBuff = var1;
		return this;
	}
	public AbilityStat$Builder withSign(String var1) {
		this.sign = var1;
		return this;
	}
	@Deprecated()
	public AbilityStat build() {
		AbilityStat var1 = new AbilityStat(this.name,this.value);
		((AbilityStat)(var1)).jdec$private$78797a2f706978656c61746564772f6d696e656d696e656e6f6d692f6170692f6162696c69746965732f4162696c69747953746174$3(((Component)(this.unit)));
		((AbilityStat)(var1)).jdec$private$78797a2f706978656c61746564772f6d696e656d696e656e6f6d692f6170692f6162696c69746965732f4162696c69747953746174$0(((INumberRange)(this.bonus)), ((AbilityStat$AbilityStatType)(this.bonusType)));
		((AbilityStat)(var1)).jdec$private$78797a2f706978656c61746564772f6d696e656d696e656e6f6d692f6170692f6162696c69746965732f4162696c69747953746174$1(this.isStatBuff);
		((AbilityStat)(var1)).jdec$private$78797a2f706978656c61746564772f6d696e656d696e656e6f6d692f6170692f6162696c69746965732f4162696c69747953746174$2(((String)(this.sign)));
		return var1;
	}
	public Component buildComponent() {
		return this.buildComponent(0);
	}
	public Component buildComponent(int var1) {
		AbilityStat var2 = new AbilityStat(this.name,this.value);
		((AbilityStat)(var2)).jdec$private$78797a2f706978656c61746564772f6d696e656d696e656e6f6d692f6170692f6162696c69746965732f4162696c69747953746174$3(((Component)(this.unit)));
		((AbilityStat)(var2)).jdec$private$78797a2f706978656c61746564772f6d696e656d696e656e6f6d692f6170692f6162696c69746965732f4162696c69747953746174$0(((INumberRange)(this.bonus)), ((AbilityStat$AbilityStatType)(this.bonusType)));
		((AbilityStat)(var2)).jdec$private$78797a2f706978656c61746564772f6d696e656d696e656e6f6d692f6170692f6162696c69746965732f4162696c69747953746174$1(this.isStatBuff);
		((AbilityStat)(var2)).jdec$private$78797a2f706978656c61746564772f6d696e656d696e656e6f6d692f6170692f6162696c69746965732f4162696c69747953746174$2(((String)(this.sign)));
		return var2.getStatDescription(var1);
	}
}