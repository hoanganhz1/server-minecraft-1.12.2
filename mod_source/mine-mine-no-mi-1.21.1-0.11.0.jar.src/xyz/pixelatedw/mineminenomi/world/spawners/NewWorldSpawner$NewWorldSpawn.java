package xyz.pixelatedw.mineminenomi.world.spawners;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.neoforged.neoforge.registries.DeferredHolder;

public final record NewWorldSpawner$NewWorldSpawn(DeferredHolder<EntityType<?>, ? extends EntityType<? extends Mob>> entityType, int min, int max) {}