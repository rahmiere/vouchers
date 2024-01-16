package org.killjoy.vouchers.menu;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.killjoy.vouchers.menu.impl.EditMenu;

public interface MenuFactory {

    @NonNull EditMenu editMenu();
}
