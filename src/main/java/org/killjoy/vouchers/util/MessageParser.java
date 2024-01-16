package org.killjoy.vouchers.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public final class MessageParser {

    private MessageParser() {
    }

    public static Component parse(final String value) {
        return MiniMessage.miniMessage().deserialize(value);
    }
}
