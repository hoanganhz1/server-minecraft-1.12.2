package xyz.pixelatedw.mineminenomi.items.weapons;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;

public final record ModSwordItem$HitEffect(Holder<MobEffect> effect, int duration, int addedStacks) {}