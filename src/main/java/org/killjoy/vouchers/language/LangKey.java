package org.killjoy.vouchers.language;

public enum LangKey {
    EDIT_MENU_TITLE("edit-menu.title")
    ;

    private final String key;

    LangKey(String key) {
        this.key = key;
    }

    public String get() {
        return key;
    }
}
