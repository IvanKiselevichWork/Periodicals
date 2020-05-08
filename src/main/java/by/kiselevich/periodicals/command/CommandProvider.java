package by.kiselevich.periodicals.command;

import by.kiselevich.periodicals.command.admin.*;
import by.kiselevich.periodicals.command.home.ShowHomePageCommand;
import by.kiselevich.periodicals.command.language.ChangeLanguageCommand;
import by.kiselevich.periodicals.command.signing.*;
import by.kiselevich.periodicals.command.user.*;
import by.kiselevich.periodicals.command.wrongrequest.ShowWrongRequestPageCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.EnumMap;
import java.util.Map;

public class CommandProvider {

    private static final Logger LOG = LogManager.getLogger(CommandProvider.class);

    private final Map<CommandName, Command> commandMap = new EnumMap<>(CommandName.class);

    private CommandProvider() {
        commandMap.put(CommandName.SIGN_IN, new SignInCommand());
        commandMap.put(CommandName.SIGN_UP, new SignUpCommand());
        commandMap.put(CommandName.SIGN_OUT, new SignOutCommand());
        commandMap.put(CommandName.HOME, new ShowHomePageCommand());
        commandMap.put(CommandName.WRONG_REQUEST, new ShowWrongRequestPageCommand());
        commandMap.put(CommandName.CHANGE_LANGUAGE, new ChangeLanguageCommand());
        commandMap.put(CommandName.ADMIN_PAGE, new ShowAdminPageCommand());
        commandMap.put(CommandName.SHOW_USERS, new ShowUsersCommand());
        commandMap.put(CommandName.SHOW_EDITIONS, new ShowEditionsCommand());
        commandMap.put(CommandName.SHOW_PAYMENTS, new ShowPaymentsCommand());
        commandMap.put(CommandName.SHOW_SUBSCRIPTIONS, new ShowSubscriptionsCommand());
        commandMap.put(CommandName.BLOCK_USER, new BlockUserCommand());
        commandMap.put(CommandName.UNBLOCK_USER, new UnblockUserCommand());
        commandMap.put(CommandName.ADD_EDITION, new AddEditionCommand());
        commandMap.put(CommandName.USER_PAGE, new ShowUserPageCommand());
        commandMap.put(CommandName.SHOW_USER_PAYMENTS, new ShowUserPaymentsCommand());
        commandMap.put(CommandName.SHOW_USER_SUBSCRIPTIONS, new ShowUserSubscriptionsCommand());
        commandMap.put(CommandName.SHOW_EDITION_SEARCH_FORM, new ShowEditionSearchFormCommand());
        commandMap.put(CommandName.FIND_EDITIONS, new FindEditionsCommand());
        commandMap.put(CommandName.ADD_SUBSCRIPTION, new AddSubscriptionCommand());
        commandMap.put(CommandName.STOP_SUBSCRIPTION, new StopSubscriptionCommand());
        commandMap.put(CommandName.BLOCK_EDITION, new BlockEditionCommand());
        commandMap.put(CommandName.UNBLOCK_EDITION, new UnblockEditionCommand());
        commandMap.put(CommandName.UPDATE_EDITION, new UpdateEditionCommand());
        commandMap.put(CommandName.REFILL_USER_BALANCE, new RefillBalanceCommand());
    }

    private static final class CommandProviderHolder {
        private static final CommandProvider INSTANCE = new CommandProvider();
    }

    public static CommandProvider getInstance() {
        return CommandProviderHolder.INSTANCE;
    }

    public Command getCommand(String commandNameString) {
        if (commandNameString == null) {
            LOG.warn("Command is null");
            return commandMap.get(CommandName.HOME);
        }

        Command command;
        CommandName commandName;
        try {
            commandName = CommandName.valueOf(commandNameString.toUpperCase());
            command = commandMap.get(commandName);
        } catch (IllegalArgumentException e) {
            LOG.warn(e);
            command = commandMap.get(CommandName.WRONG_REQUEST);
        }
        return command;
    }

    public Command getCommand(CommandName commandName) {
        return commandMap.get(commandName);
    }
}
