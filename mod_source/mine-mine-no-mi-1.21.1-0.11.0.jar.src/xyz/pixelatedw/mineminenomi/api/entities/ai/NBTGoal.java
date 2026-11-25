package xyz.pixelatedw.mineminenomi.api.entities.ai;

import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.enums.NBTGoalType;

public final record NBTGoal(NBTGoalType type, int priority, AbilityCore<?> abilityId) {}