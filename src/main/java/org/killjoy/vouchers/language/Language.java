package org.killjoy.vouchers.language;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import net.kyori.adventure.text.Component;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.framework.qual.DefaultQualifier;
import org.killjoy.vouchers.Vouchers;
import org.killjoy.vouchers.util.MessageParser;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.gson.GsonConfigurationLoader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

@DefaultQualifier(NonNull.class)
@Singleton
public final class Language {

    private final Vouchers plugin;
    private final GsonConfigurationLoader loader;

    @Nullable
    private ConfigurationNode root;

    @Inject
    public Language(@Named("dataFolder") Path dataFolder, Vouchers plugin) {
        this.plugin = plugin;

        this.loader = build(dataFolder.resolve("language.json"));

        try {
            this.load();
        } catch (final ConfigurateException ex) {
            plugin.getSLF4JLogger().error("An error occurred when trying to load the language file.", ex);
        }
    }

    public void load() throws ConfigurateException {
        this.root = loader.load();
    }

    public Component get(LangKey key) {
        return get(key, null);
    }

    public Component get(LangKey key, @Nullable Map<String, Object> placeholders) {
        @Nullable String raw;

        if (root == null) {
            raw = "<red>Failed to load the language file.";
        } else {
            raw = root.node(key.get()).getString();
        }

        if (raw == null) {
            raw = String.format("<red>Missing value for key: %s", key);
        }

        if (placeholders != null) {
            for (var entry : placeholders.entrySet()) {
                raw = raw.replaceAll("%" + entry.getKey(), String.valueOf(entry.getValue()));
            }
        }

        return MessageParser.parse(raw);
    }

    private GsonConfigurationLoader build(final Path source) {
        if (Files.notExists(source)) {
            plugin.saveResource("language.json", false);
        }
        return GsonConfigurationLoader.builder().path(source).build();
    }
}
