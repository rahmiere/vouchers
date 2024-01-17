package org.killjoy.vouchers.language;

import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.NodePath;

public enum LangKey {
    EDIT_MENU_TITLE("edit-menu.title"),
    RENAME("edit-menu.rename.enter-name"),
    RENAME_SUCCESS("edit-menu.rename.success"),

    ITEM_UPDATED("edit-menu.item.updated"),

    VOUCHER_TOGGLE_ON("edit-menu.toggle.on"),
    VOUCHER_TOGGLE_OFF("edit-menu.toggle.off"),


    HOLD_ITEM("create-command.hold-item"),
    ALREADY_EXISTS("create-command.exists"),

    GIVE_SENDER("give-command.sender"),
    GIVE_TARGET("give-command.target"),


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
