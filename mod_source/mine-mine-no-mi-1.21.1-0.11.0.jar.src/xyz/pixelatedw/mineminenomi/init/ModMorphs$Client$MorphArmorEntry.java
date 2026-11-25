package xyz.pixelatedw.mineminenomi.init;

import net.minecraft.client.model.EntityModel;
import net.minecraft.world.entity.LivingEntity;

public final record ModMorphs$Client$MorphArmorEntry<T extends LivingEntity, M extends EntityModel<T>>(M inner, M outer) {}