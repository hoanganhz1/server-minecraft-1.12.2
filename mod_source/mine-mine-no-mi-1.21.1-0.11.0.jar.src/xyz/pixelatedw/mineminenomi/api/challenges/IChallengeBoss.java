package xyz.pixelatedw.mineminenomi.api.challenges;

import javax.annotation.Nullable;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import xyz.pixelatedw.mineminenomi.api.entities.IPhasesEntity;
import xyz.pixelatedw.mineminenomi.api.entities.ai.NPCPhaseManager;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.IRevengeEntity;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;

public interface IChallengeBoss extends IRevengeEntity, IPhasesEntity {
	// Fields
	public static final ResourceLocation GROUP_SCALE_HP_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi","challenge_scale.hp");
	public static final ResourceLocation GROUP_SCALE_TOUGHNESS_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi","challenge_scale.toughness");
	public static final ResourceLocation GROUP_SCALE_ATTACK_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi","challenge_scale.attack");
	public static final ResourceLocation GROUP_SCALE_GCD_ID = ResourceLocation.fromNamespaceAndPath("mineminenomi","challenge_scale.gcd");

	public abstract ChallengeInfo getChallengeInfo();
	public default void applyDifficultyModifiers(LivingEntity var1) {
		float var2 = this.getChallengeInfo().getScaling();
		if ((var2) > (0F)){
			AttributeInstance var3 = var1.getAttribute(Attributes.MAX_HEALTH);
			if ((var3) != (null)){
				var3.addPermanentModifier(new AttributeModifier(GROUP_SCALE_HP_ID,this.getHealthScalingModifier(var2),AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
			}
			AttributeInstance var4 = var1.getAttribute(ModAttributes.TOUGHNESS);
			if ((var4) != (null)){
				var4.addPermanentModifier(new AttributeModifier(GROUP_SCALE_TOUGHNESS_ID,this.getToughnessScalingModifier(var2),AttributeModifier.Operation.ADD_VALUE));
			}
			AttributeInstance var5 = var1.getAttribute(Attributes.ATTACK_DAMAGE);
			if ((var5) != (null)){
				var5.addPermanentModifier(new AttributeModifier(GROUP_SCALE_ATTACK_ID,this.getAttackScalingModifier(var2),AttributeModifier.Operation.ADD_VALUE));
			}
			AttributeInstance var6 = var1.getAttribute(ModAttributes.GCD);
			if ((var6) != (null)){
				var6.addPermanentModifier(new AttributeModifier(GROUP_SCALE_GCD_ID,this.getGCDScalingModifier(var2),AttributeModifier.Operation.ADD_VALUE));
			}else{
				return;
			}
		}
	}
	@Nullable()
	public default NPCPhaseManager getPhaseManager() {
		return null;
	}
	public default boolean isDifficultyStandard() {
		return this.getChallengeInfo().isDifficultyStandard();
	}
	public default boolean isDifficultyHard() {
		return this.getChallengeInfo().isDifficultyHard();
	}
	public default boolean isDifficultyHardOrAbove() {
		return (this.getChallengeInfo().getDifficulty().ordinal()) >= (ChallengeDifficulty.HARD.ordinal());
	}
	public default boolean isDifficultyUltimate() {
		return this.getChallengeInfo().isDifficultyUltimate();
	}
	public default double getHealthScalingModifier(float var1) {
		return (double)(var1);
	}
	public default double getToughnessScalingModifier(float var1) {
		return (double)((4F) * (var1));
	}
	public default double getAttackScalingModifier(float var1) {
		return (double)((3F) * (var1));
	}
	public default double getGCDScalingModifier(float var1) {
		return (double)(-((15F) * (var1)));
	}
}