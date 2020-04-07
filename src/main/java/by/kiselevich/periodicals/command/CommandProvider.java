package by.kiselevich.periodicals.command;

import by.kiselevich.periodicals.command.admin.*;
import by.kiselevich.periodicals.command.home.ShowHomePage;
import by.kiselevich.periodicals.command.language.ChangeLanguage;
import by.kiselevich.periodicals.command.signing.*;
import by.kiselevich.periodicals.command.user.ShowUserPage;
import by.kiselevich.periodicals.command.user.ShowUserPayments;
import by.kiselevich.periodicals.command.user.ShowUserSubscriptions;
import by.kiselevich.periodicals.command.wrongrequest.ShowWrongRequestPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.EnumMap;
import java.util.Map;

public class CommandProvider {

    private static final Logger LOG = LogManager.getLogger(CommandProvider.class);

    private final Map<CommandName, Command> commandMap = new EnumMap<>(CommandName.class);

    private CommandProvider() {
        commandMap.put(CommandName.SIGN_IN, new SignIn());
        commandMap.put(CommandName.SIGN_UP, new SignUp());
        commandMap.put(CommandName.SIGN_OUT, new SignOut());
        commandMap.put(CommandName.HOME, new ShowHomePage());
        commandMap.put(CommandName.WRONG_REQUEST, new ShowWrongRequestPage());
        commandMap.put(CommandName.CHANGE_LANGUAGE, new ChangeLanguage());
        commandMap.put(CommandName.ADMIN, new ShowAdminPage());
        commandMap.put(CommandName.SHOW_USERS, new ShowUsers());
        commandMap.put(CommandName.SHOW_EDITIONS, new ShowEditions());
        commandMap.put(CommandName.SHOW_PAYMENTS, new ShowPayments());
        commandMap.put(CommandName.SHOW_SUBSCRIPTIONS, new ShowSubscriptions());
        commandMap.put(CommandName.BLOCK_USER, new BlockUser());
        commandMap.put(CommandName.UNBLOCK_USER, new UnblockUser());
        commandMap.put(CommandName.ADD_EDITION, new AddEdition());
        commandMap.put(CommandName.USER, new ShowUserPage());
        commandMap.put(CommandName.SHOW_USER_PAYMENTS, new ShowUserPayments());
        commandMap.put(CommandName.SHOW_USER_SUBSCRIPTIONS, new ShowUserSubscriptions());
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
