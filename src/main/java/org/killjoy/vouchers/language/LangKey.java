package org.killjoy.vouchers.language;

import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.NodePath;

public enum LangKey {
    EDIT_MENU_TITLE("edit-menu.title"),
    MUST_HOLD_ITEM("create-command.must-hold-item"),
    VOUCHER_ALREADY_EXISTS("create-command.voucher-exists")
    ;

    private final NodePath path;

    LangKey(String path) {
        this.path = NodePath.of(path.split("\\."));
    }

    public String getValue(ConfigurationNode node) {
        return node.node(path).getString();
    }
}
