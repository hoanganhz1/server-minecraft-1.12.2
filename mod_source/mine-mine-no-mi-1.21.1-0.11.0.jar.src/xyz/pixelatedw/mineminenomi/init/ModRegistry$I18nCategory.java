package xyz.pixelatedw.mineminenomi.init;

public enum ModRegistry$I18nCategory {
	// Fields
	GUI("gui"),
	ABILITY("ability"),
	ABILITY_NODE("ability_node"),
	ITEM("item"),
	BLOCK("block"),
	CONTAINER("container"),
	ENTITY("entity"),
	FACTION("faction"),
	RACE("race"),
	STYLE("style"),
	QUEST("quest"),
	CREATIVE_TAB("itemGroup"),
	PARTICLE_EFFECT("particle_effect"),
	ATTRIBUTE("attribute"),
	EFFECT("effect"),
	ENCHANTMENT("enchantment"),
	CHALLENGE("challenge"),
	ADVANCEMENT("advancement"),
	KEYBIND("key"),
	KEYBIND_CATEGORY("key.categories"),
	MORPH("morph"),
	CREW("crew"),
	FACTION_RANK("faction_rank"),
	COMMAND("command"),
	TUTORIAL("tutorial"),
	CONFIG("configuration");
	 String id;

	private ModRegistry$I18nCategory(String var3) {
		this.id = var3;
	}
	public String getId() {
		return this.id;
	}
}