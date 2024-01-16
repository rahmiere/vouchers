package org.killjoy.vouchers.menu.impl;

import com.google.inject.Inject;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.framework.qual.DefaultQualifier;
import org.incendo.interfaces.paper.type.ChestInterface;
import org.killjoy.vouchers.language.LangKey;
import org.killjoy.vouchers.language.Language;
import org.killjoy.vouchers.menu.Menu;

@DefaultQualifier(NonNull.class)
public final class EditMenu extends Menu {

    private final Language language;

    @Inject
    public EditMenu(Language language) {
        this.language = language;
    }

    @Override
    protected ChestInterface build() {
        return ChestInterface.builder()
                .title(language.get(LangKey.EDIT_MENU_TITLE))
                .rows(1)
                .build();
    }
}
