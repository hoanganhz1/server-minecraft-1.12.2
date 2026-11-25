package xyz.pixelatedw.mineminenomi.audio;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;

public class DrumsOfLiberationTickableSound extends AbstractTickableSoundInstance {
	// Fields
	private static final int INTRO_DURATION = 72;
	private static final int FADE_IN_DURATION = 60;
	private final int stage;
	private final Entity source;
	private float playTime;

	public DrumsOfLiberationTickableSound(SoundEvent var1, int var2, Entity var3) {
		super(var1,SoundSource.PLAYERS,SoundInstance.createUnseededRandom());
		this.playTime = 0F;
		this.stage = var2;
		this.source = var3;
		this.looping = (this.stage) == (1);
		this.delay = 0;
		this.volume = 0F;
		this.relative = true;
	}
	public boolean canPlaySound() {
		return ((this.source) != (null)) && (this.source.isAlive());
	}
	public boolean canStartSilent() {
		return true;
	}
	public void tick() {
		if (this.source.isAlive()){
			if ((this.stage) == (0)){
				if ((this.playTime) > (72F)){
					this.stop();
				}else{
					this.volume = Mth.clamp((this.playTime) / (120F),0F,0.5F);
				}
			}else{
				if ((this.stage) == (1)){
					if ((this.playTime) > (1200F)){
						this.stop();
					}else{
						if ((this.playTime) > (60F)){
							this.volume = 0.5F;
						}else{
							return;
						}
					}
				}else{
					return;
				}
			}
		}else{
			this.stop();
		}
	}
}