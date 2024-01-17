package org.killjoy.vouchers.menu;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.killjoy.vouchers.menu.impl.EditMenu;
import org.killjoy.vouchers.menu.impl.SelectItemStackMenu;
import org.killjoy.vouchers.voucher.Voucher;

public interface MenuFactory {

    @NonNull EditMenu editMenu(final Voucher voucher);

    @NonNull SelectItemStackMenu selectItemMenu(final Voucher voucher);
}
