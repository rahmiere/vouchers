package org.killjoy.vouchers.language;

import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.NodePath;

public enum LangKey {
    EDIT_MENU_TITLE("edit-menu.title"),
    EDIT_MENU_RENAME("edit-menu.rename"),
    MUST_HOLD_ITEM("create-command.must-hold-item"),
    VOUCHER_ALREADY_EXISTS("create-command.voucher-exists"),

    RENAME("rename.enter-name"),
    RENAME_SUCCESS("rename.success"),

    GIVE("give.sender"),
    RECEIVED("give.target"),

    VOUCHER_DISABLED("voucher.disabled")
    ;

    private final NodePath path;

    LangKey(String path) {
        this.path = NodePath.of(path.split("\\."));
    }

    public String getValue(ConfigurationNode node) {
        return node.node(path).getString();
    }
}
