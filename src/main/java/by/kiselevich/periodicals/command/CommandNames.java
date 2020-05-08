package by.kiselevich.periodicals.command;

import java.util.EnumSet;
import java.util.Set;

import static by.kiselevich.periodicals.command.CommandName.*;

public enum CommandNames {
    GUEST(EnumSet.of(
            WRONG_REQUEST,
            HOME,
            SIGN_IN,
            SIGN_UP,
            CHANGE_LANGUAGE
        )
    ),
    USER(EnumSet.of(
            WRONG_REQUEST,
            HOME,
            SIGN_OUT,
            CHANGE_LANGUAGE,
            USER_PAGE,
            SHOW_USER_PAYMENTS,
            SHOW_USER_SUBSCRIPTIONS,
            SHOW_EDITION_SEARCH_FORM,
            FIND_EDITIONS,
            ADD_SUBSCRIPTION,
            STOP_SUBSCRIPTION,
            REFILL_USER_BALANCE
        )
    ),
    ADMIN(EnumSet.of(
            WRONG_REQUEST,
            HOME,
            SIGN_OUT,
            CHANGE_LANGUAGE,
            ADMIN_PAGE,
            SHOW_USERS,
            SHOW_EDITIONS,
            SHOW_PAYMENTS,
            SHOW_SUBSCRIPTIONS,
            BLOCK_USER,
            UNBLOCK_USER,
            ADD_EDITION,
            BLOCK_EDITION,
            UNBLOCK_EDITION,
            UPDATE_EDITION
        )
    );

    private final Set<CommandName> names;

    CommandNames(Set<CommandName> names) {
        this.names = names;
    }

    public Set<CommandName> getNames() {
        return names;
    }
}
