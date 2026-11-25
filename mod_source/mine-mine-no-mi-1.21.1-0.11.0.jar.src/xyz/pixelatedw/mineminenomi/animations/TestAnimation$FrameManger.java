package xyz.pixelatedw.mineminenomi.animations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.model.geom.ModelPart;

public class TestAnimation$FrameManger {
	// Fields
	private Map<String, List<TestAnimation$AnimationFrame>> frames;
	private int currentIndex;
	private int time;
	 int maxDuration;
	final TestAnimation this$0;

	TestAnimation$FrameManger(final TestAnimation var1) {
		this.this$0 = var1;
		this.frames = new HashMap();
	}
	public TestAnimation$AnimationFrame createFrame(String var1, int var2) {
		TestAnimation$AnimationFrame var3 = new TestAnimation$AnimationFrame(this.this$0,var1,var2);
		if (this.frames.containsKey(var1)){
			((List)(this.frames.get(var1))).add(var3);
		}else{
			ArrayList var4 = new ArrayList();
			var4.add(var3);
			this.frames.putIfAbsent(var1,(List<TestAnimation$AnimationFrame>)(var4));
		}
		this.maxDuration = (this.maxDuration) + (var3.duration);
		return var3;
	}
	public void apply(String var1, ModelPart var2) {
		List var3 = ((List)(this.frames.get(var1)));
		TestAnimation$AnimationFrame var4 = null;
		if ((this.currentIndex) > (0)){
			var4 = ((TestAnimation$AnimationFrame)(var3.get((this.currentIndex) - (1))));
		}
		TestAnimation$AnimationFrame var5 = ((TestAnimation$AnimationFrame)(var3.get(this.currentIndex)));
		if ((var5) != (null)){
			((TestAnimation$AnimationFrame)(var5)).jdec$private$78797a2f706978656c61746564772f6d696e656d696e656e6f6d692f616e696d6174696f6e732f54657374416e696d6174696f6e24416e696d6174696f6e4672616d65$0(((ModelPart)(var2)), ((TestAnimation$AnimationFrame)(var4)));
			float var6 = ((float)((this.currentIndex) + (1))) / ((float)(var3.size()));
			float var7 = (Math.min(((float)(this.time)) / ((float)(this.maxDuration)),1F)) * (var6);
			this.time = (this.time) + (1);
			boolean var8 = var5.play(var2,var7,var4);
			int var9 = ((this.currentIndex) < (var3.size())) ? (1) : (0);
			if ((var8) && ((var9) != (0))){
				this.currentIndex = (this.currentIndex) + (1);
			}else{
				return;
			}
		}
	}
}