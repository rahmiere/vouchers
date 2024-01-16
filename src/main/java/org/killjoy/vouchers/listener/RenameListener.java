package org.killjoy.vouchers.listener;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.killjoy.vouchers.Vouchers;
import org.killjoy.vouchers.language.LangKey;
import org.killjoy.vouchers.language.Language;
import org.killjoy.vouchers.util.MessageParser;
import org.killjoy.vouchers.voucher.Voucher;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public final class RenameListener implements Listener {

    private final Map<Player, Voucher> renameMap = new ConcurrentHashMap<>();

    private final Vouchers plugin;
    private final Language language;

    @Inject
    public RenameListener(Vouchers plugin, Language language) {
        this.plugin = plugin;
        this.language = language;
    }

    @EventHandler
    public void onChat(AsyncChatEvent event) {
        Player player = event.getPlayer();
        Voucher voucher = this.renameMap.remove(player);

        if (voucher == null) {
            return;
        }

        event.setCancelled(true);

        Bukkit.getScheduler().runTask(this.plugin, () -> {
            String name = MessageParser.raw(event.message());
            voucher.setName(name);
            player.sendMessage(language.get(LangKey.RENAME_SUCCESS, Collections.singletonMap("name", name)));
        });
    }

    public Map<Player, Voucher> getRenameMap() {
        return renameMap;
    }
}
