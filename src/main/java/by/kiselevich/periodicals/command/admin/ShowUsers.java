package by.kiselevich.periodicals.command.admin;

import by.kiselevich.periodicals.command.Attribute;
import by.kiselevich.periodicals.command.Command;
import by.kiselevich.periodicals.command.Page;
import by.kiselevich.periodicals.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class ShowUsers implements Command {

    @Override
    public Page execute(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute(Attribute.ADMIN_PAGE_OPTION.getValue(), AdminPageOption.USERS);
        List<User> userList = new ArrayList<>();
        User user = new User(1, 1, "login1", new char[] {'1'}, "full name1", "", null, true);
        userList.add(user);
        req.setAttribute(Attribute.USERS.getValue(), userList);
        return Page.ADMIN_PAGE;
    }
}
