package org.killjoy.vouchers;

import org.bukkit.plugin.java.JavaPlugin;

public final class Vouchers extends JavaPlugin {

    @Override
    public void onEnable() {
        getSLF4JLogger().info("Enabled!");
    }
}