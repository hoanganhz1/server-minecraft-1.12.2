package xyz.pixelatedw.mineminenomi.api.ui;

import java.awt.Color;
import java.util.List;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.FormattedCharSequence;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;

public class SequencedString {
	// Fields
	private String message;
	private int maxLength;
	private int color;
	private char[] chars;
	private int maxTicks;
	private int tickCount;
	private int delayTicks;
	private boolean isComplete;

	public SequencedString(String var1, int var2, int var3) {
		this(var1,var2,var3,(var3) + (100));
	}
	public SequencedString(String var1, int var2, int var3, int var4) {
		this.color = Color.WHITE.getRGB();
		this.delayTicks = this.maxTicks;
		this.setMessage(var1);
		this.maxLength = var2;
		this.maxTicks = var3;
		this.tickCount = 0;
		this.delayTicks = var4;
	}
	public void render(GuiGraphics var1, Font var2, int var3, int var4, float var5) {
		String var6 = "";
		if (!(this.isComplete)){
			int var7 = 0;
			do{
				if ((var7) < (this.chars.length)){
					if (((this.tickCount) >= (this.calculateTicksNeeded(var7))) && ((((float)(this.tickCount)) * (var5)) < ((float)(this.delayTicks)))){
						String var8 = var6;
						char var9 = this.chars[var7];
						String var10 = var8 + var9;
						var6 = var10;
					}
					var7++;
					continue;
				}else{
					break;
				}
			} while (true);
			if (var6.equals(this.message)){
				this.isComplete = true;
			}
		}else{
			var6 = this.message;
		}
		List var7_1 = var2.split((FormattedText)(Component.literal(var6)),this.maxLength);
		int var8_1 = 0;
		do{
			if ((var8_1) < (var7_1.size())){
				RendererHelper.drawStringWithBorder(var2,var1,((FormattedCharSequence)(var7_1.get(var8_1))),var3,(var4) + ((10) * (var8_1)),this.color);
				var8_1++;
				continue;
			}else{
				break;
			}
		} while (true);
		this.tickCount = (this.tickCount) + (1);
	}
	public int calculateTicksNeeded(int var1) {
		int var2 = this.message.length();
		return ((var1) * (this.maxTicks)) / (var2);
	}
	public void setMessage(Component var1) {
		this.setMessage(var1.getString());
	}
	public void setMessage(String var1) {
		this.message = var1;
		this.chars = new char[this.message.length()];
		int var2 = 0;
		do{
			if ((var2) < (this.message.length())){
				this.chars[var2] = this.message.charAt(var2);
				var2++;
				continue;
			}else{
				break;
			}
		} while (true);
	}
	public void setMaxLength(int var1) {
		this.maxLength = var1;
	}
	public void setMaxTicks(int var1) {
		this.maxTicks = var1;
	}
	public void setDelayTicks(int var1) {
		this.delayTicks = var1;
	}
	public void resetCounter() {
		this.tickCount = 0;
		this.isComplete = false;
	}
	public int getTickCount() {
		return this.tickCount;
	}
	public int getDelayTicks() {
		return this.delayTicks;
	}
}