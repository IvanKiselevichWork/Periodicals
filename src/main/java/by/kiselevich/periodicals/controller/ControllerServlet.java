package by.kiselevich.periodicals.controller;


import by.kiselevich.periodicals.command.Command;
import by.kiselevich.periodicals.command.CommandProvider;
import by.kiselevich.periodicals.command.Page;
import by.kiselevich.periodicals.command.Parameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {""})
@MultipartConfig(maxFileSize=1024*1024*5)
public class ControllerServlet extends HttpServlet {

    private static final Logger LOG = LogManager.getLogger(ControllerServlet.class);
    private static final CommandProvider commandProvider = new CommandProvider();

    @Override
    public void init() {
        LOG.trace("ControllerServlet init");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        LOG.trace("ControllerServlet doGet");
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        LOG.trace("ControllerServlet doPost");
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String commandParameter = req.getParameter(Parameter.COMMAND.getValue());
            Command command = commandProvider.getCommand(commandParameter);
            LOG.info("Executing command: {}", command);
            Page page = command.execute(req, resp);
            if (page != Page.EMPTY_PAGE) {
                req.getRequestDispatcher(page.getPath()).forward(req, resp);
            }
        } catch (IOException | ServletException e) {
            LOG.error(e);
        }
    }
}
