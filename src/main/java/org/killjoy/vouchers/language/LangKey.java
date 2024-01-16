package org.killjoy.vouchers.language;

import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.NodePath;

public enum LangKey {
    EDIT_MENU_TITLE("edit-menu-title"),

    HOLD_ITEM("create-command.hold-item"),
    ALREADY_EXISTS("create-command.exists"),

    GIVE_SENDER("give-command.sender"),
    GIVE_TARGET("give-command.target"),

    RENAME("rename.enter-name"),
    RENAME_SUCCESS("rename.success"),

    VOUCHER_DISABLED("voucher-disabled")
    ;

    private final NodePath path;

    LangKey(String path) {
        this.path = NodePath.of(path.split("\\."));
    }

    public String getValue(ConfigurationNode node) {
        return node.node(path).getString();
    }
}
