package xyz.pixelatedw.mineminenomi.api.util;

import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

public class Result {
	// Fields
	private Result$ResultType result;
	private @Nullable() Component message;

	public Result(Result$ResultType var1, @Nullable() Component var2) {
		this.result = var1;
		this.message = var2;
	}
	public Result$ResultType getResult() {
		return this.result;
	}
	@Nullable()
	public @Nullable() Component getMessage() {
		return this.message;
	}
	public boolean isSuccess() {
		return (this.getResult()) == (Result$ResultType.SUCCESS);
	}
	public boolean isSuccessOr(Result$ISuccessCallback var1) {
		if (this.isSuccess()){
			return true;
		}else{
			var1.callback(this.getMessage());
			return false;
		}
	}
	public boolean isFail() {
		return (this.getResult()) == (Result$ResultType.FAIL);
	}
	public Result and(Result var1) {
		return (this.isSuccess()) ? ((var1.isSuccess()) ? (success()) : ((this.isFail()) ? (fail(this.getMessage())) : (fail(var1.getMessage())))) : ((this.isFail()) ? (fail(this.getMessage())) : (fail(var1.getMessage())));
	}
	public Result or(Result var1) {
		return (!(this.isSuccess())) ? ((var1.isSuccess()) ? (success()) : ((this.isFail()) ? (fail(this.getMessage())) : (fail(var1.getMessage())))) : (success());
	}
	public static Result success() {
		return new Result(Result$ResultType.SUCCESS,(Component)(null));
	}
	public static Result fail(@Nullable() Component var0) {
		return new Result(Result$ResultType.FAIL,var0);
	}
	public Result inverse() {
		if ((this.getResult()) == (Result$ResultType.SUCCESS)){
			this.result = Result$ResultType.FAIL;
		}else{
			this.result = Result$ResultType.SUCCESS;
		}
		return this;
	}
}