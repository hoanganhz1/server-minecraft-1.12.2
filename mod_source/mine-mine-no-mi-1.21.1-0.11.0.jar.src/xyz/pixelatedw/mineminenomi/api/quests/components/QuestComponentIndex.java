package xyz.pixelatedw.mineminenomi.api.quests.components;

import com.google.common.collect.ImmutableSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.objectives.Objective;

public class QuestComponentIndex {
	// Fields
	private final Map<Class<? extends QuestComponent>, Set<QuestComponent>> componentsByType;

	public QuestComponentIndex() {
		this.componentsByType = new HashMap();
	}
	public <T extends QuestComponent> Set<T> getComponentsByType(Class<T> var1) {
		Set var2 = ((Set)(((java.util.Map)(this.componentsByType)).get((Object)(var1))));
		return ((var2) == (null)) ? (Set.of()) : (ImmutableSet.copyOf((Collection)(var2)));
	}
	public void indexQuestComponents(Quest var1) {
		if ((var1) == (null)){
			return;
		}else{
			Iterator var2 = var1.getObjectives().iterator();
			LOOP_1:
			do{
				if (var2.hasNext()){
					Iterator var3 = ((Objective)(var2.next())).getComponents().iterator();
					do{
						if (var3.hasNext()){
							QuestComponent var4 = ((QuestComponent)(var3.next()));
							Function<? super Class<? extends QuestComponent>, ? extends Set<QuestComponent>> var5 = (Function) ((Function<Class, Set>) ((l0) -> {
			return new HashSet();
		}));
							((Set)(((java.util.Map)(this.componentsByType)).computeIfAbsent((Object)(var4.getClass()),(Function)(var5)))).add(var4);
							continue;
						}else{
							continue LOOP_1;
						}
					} while (true);
				}else{
					break;
				}
			} while (true);
			return;
		}
	}
	public void unindexQuestComponents(Quest var1) {
		if ((var1) == (null)){
			return;
		}else{
			Iterator var2 = var1.getObjectives().iterator();
			LOOP_1:
			do{
				if (var2.hasNext()){
					Iterator var3 = ((Objective)(var2.next())).getComponents().iterator();
					do{
						if (var3.hasNext()){
							QuestComponent var4 = ((QuestComponent)(var3.next()));
							Set var5 = ((Set)(((java.util.Map)(this.componentsByType)).get((Object)(var4.getClass()))));
							if ((var5) == (null)){
								continue;
							}else{
								var5.remove(var4);
								if (var5.isEmpty()){
									this.componentsByType.remove(var4.getClass());
									continue;
								}else{
									continue;
								}
							}
						}else{
							continue LOOP_1;
						}
					} while (true);
				}else{
					break;
				}
			} while (true);
			return;
		}
	}
	public void clear() {
		this.componentsByType.clear();
	}
}