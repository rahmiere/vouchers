package org.killjoy.vouchers.menu.impl;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.framework.qual.DefaultQualifier;
import org.incendo.interfaces.paper.type.ChestInterface;
import org.killjoy.vouchers.language.LangKey;
import org.killjoy.vouchers.language.Language;
import org.killjoy.vouchers.menu.Menu;
import org.killjoy.vouchers.voucher.Voucher;

import java.util.Collections;

@DefaultQualifier(NonNull.class)
public final class EditMenu extends Menu {

    private final Language language;
    private final Voucher voucher;

    @Inject
    public EditMenu(Language language, @Assisted Voucher voucher) {
        this.language = language;
        this.voucher = voucher;
    }

    @Override
    protected ChestInterface build() {
        return ChestInterface.builder()
                .title(language.get(LangKey.EDIT_MENU_TITLE, Collections.singletonMap("key", voucher.getKey())))
                .rows(1)
                .build();
    }
}
