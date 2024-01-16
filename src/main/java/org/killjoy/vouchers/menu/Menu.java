package org.killjoy.vouchers.menu;

import com.google.common.base.Suppliers;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.framework.qual.DefaultQualifier;
import org.incendo.interfaces.paper.PlayerViewer;
import org.incendo.interfaces.paper.type.ChestInterface;

import java.util.function.Supplier;

@DefaultQualifier(NonNull.class)
public abstract class Menu {

    private final Supplier<ChestInterface> chestInterface = Suppliers.memoize(this::build);

    protected abstract ChestInterface build();

    public final void open(final Player player) {
        this.chestInterface.get().open(PlayerViewer.of(player));
    }
}
