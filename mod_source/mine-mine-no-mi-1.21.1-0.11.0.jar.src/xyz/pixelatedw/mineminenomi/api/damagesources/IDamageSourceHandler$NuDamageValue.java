package xyz.pixelatedw.mineminenomi.api.damagesources;

import java.util.Arrays;
import java.util.stream.Stream;
import net.minecraft.network.chat.Component;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;

public final record IDamageSourceHandler$NuDamageValue(AbilityCore<?> ability, SourceElement element, SourceHakiNature hakiNature, SourceType[] sourceTypes) {
	public boolean hasType(SourceType var0) { throw new RuntimeException("yak-decompiler: undecompilable method body"); /* yak-decompiler: ParseBytesCode failed: unsupported_source: lambda source body requires original synthetic declaration metadata */ }
	public String toString() {
		String var1 = ((this.ability) != (null)) ? (this.ability.getLocalizedName().getString()) : ("null");
		return String.format("[ability=\"%s\", element=%s, hakiNature=%s, sourceTypes=%s]",new Object[]{var1,this.element,this.hakiNature,Arrays.toString((Object[])(this.sourceTypes))});
	}
}