package by.kiselevich.periodicals.controller;


import by.kiselevich.periodicals.command.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class ControllerServlet {

    private static final Logger LOG = LogManager.getLogger(ControllerServlet.class);
    private static final CommandProvider commandProvider = CommandProvider.getInstance();
    private static final long serialVersionUID = 2992999480268151300L;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public void processGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        processRequest(req, resp);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
        public void processPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String commandParameter = req.getParameter(JspParameter.COMMAND.getValue());
        Command command = commandProvider.getCommand(commandParameter);
        LOG.info("Executing command: {}", command);
        Page page = command.execute(req, resp);
        if (page != Page.EMPTY_PAGE) {
            req.getRequestDispatcher(page.getPath()).forward(req, resp);
        }
    }
}
