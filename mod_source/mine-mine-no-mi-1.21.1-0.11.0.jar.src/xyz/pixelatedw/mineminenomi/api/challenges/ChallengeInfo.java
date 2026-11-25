package xyz.pixelatedw.mineminenomi.api.challenges;

import com.google.common.collect.ImmutableList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.world.entity.LivingEntity;

public class ChallengeInfo {
	// Fields
	private static final ImmutableList<LivingEntity> EMPTY_GROUP = ImmutableList.of();
	@Nullable() private InProgressChallenge challengeData;

	public ChallengeDifficulty getDifficulty() {
		if ((this.challengeData) == (null)){
			return ChallengeDifficulty.STANDARD;
		}else{
			return this.challengeData.getCore().getDifficulty();
		}
	}
	public List<LivingEntity> getChallengerGroup() {
		if ((this.challengeData) == (null)){
			return EMPTY_GROUP;
		}else{
			return (List<LivingEntity>) (List) (this.challengeData.getGroup());
		}
	}
	public boolean isDifficultyStandard() {
		return (this.getDifficulty()) == (ChallengeDifficulty.STANDARD);
	}
	public boolean isDifficultyHard() {
		return (this.getDifficulty()) == (ChallengeDifficulty.HARD);
	}
	public boolean isDifficultyUltimate() {
		return (this.getDifficulty()) == (ChallengeDifficulty.ULTIMATE);
	}
	public float getScaling() {
		float var1 = (float)((((this.getChallengerGroup().size()) - (1)) * (50)) + ((this.getDifficulty().ordinal()) * (75)));
		var1 = Math.min(var1,300F);
		return (var1) / (300F);
	}
	public void setInProgressChallenge(@Nullable() InProgressChallenge var1) {
		this.challengeData = var1;
	}
	@Nullable()
	public InProgressChallenge getInProgressChallengeData() {
		return this.challengeData;
	}
}