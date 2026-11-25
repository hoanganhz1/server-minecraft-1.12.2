package xyz.pixelatedw.mineminenomi.morphs.ryuallosaurus;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.abilities.ryuallosaurus.AllosaurusWalkPointAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;

public class AllosaurusWalkMorphInfo extends MorphInfo {
	// Fields
	private static final EntityDimensions STANDING_SIZE = EntityDimensions.scalable(3F,3.7F);
	private static final EntityDimensions CROUCHING_SIZE = EntityDimensions.scalable(3F,3.6F);
	private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi","textures/models/morphs/allosaurus_walk.png");

	@Nullable()
	public @Nullable() ResourceLocation getTexture(LivingEntity var1) {
		return TEXTURE;
	}
	public Component getDisplayName() {
		return ((AbilityCore)(AllosaurusWalkPointAbility.INSTANCE.get())).getLocalizedName();
	}
	public float getEyeHeight(LivingEntity var1) {
		return 3.5F;
	}
	public float getShadowSize() {
		return 1.5F;
	}
	public boolean canMount() {
		return false;
	}
	public void positionRider(LivingEntity var1, Entity var2) {
		var2.setPos(var1.getPassengerRidingPosition(var2).subtract(0D,1.2D,0D));
	}
	public Map<Pose, EntityDimensions> getSizes() {
		return ImmutableMap.builder().put(Pose.STANDING,STANDING_SIZE).put(Pose.CROUCHING,CROUCHING_SIZE).build();
	}
}