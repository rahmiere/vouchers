package org.killjoy.vouchers.language;

import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.NodePath;

public enum LangKey {
    EDIT_MENU_TITLE("edit-menu.title")
    ;

    private final NodePath path;

    LangKey(String path) {
        this.path = NodePath.of(path.split("\\."));
    }

    public String get() {
        return path.toString();
    }

    public String getValue(ConfigurationNode node) {
        return node.node(path).getString();
    }
}
