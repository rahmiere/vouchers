package org.killjoy.vouchers.language;

public enum LangKey {

    ;

    private final String key;

    LangKey(String key) {
        this.key = key;
    }

    public String get() {
        return key;
    }
}
