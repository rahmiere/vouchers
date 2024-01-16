package org.killjoy.vouchers.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

public final class MessageParser {

    private MessageParser() {
    }

    public static Component parse(final String value) {
        return MiniMessage.miniMessage().deserialize(value);
    }

    public static String raw(final Component component) {
        return PlainTextComponentSerializer.plainText().serialize(component);
    }
}
