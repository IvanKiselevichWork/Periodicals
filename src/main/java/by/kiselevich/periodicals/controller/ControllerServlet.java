package by.kiselevich.periodicals.controller;


import by.kiselevich.periodicals.command.Command;
import by.kiselevich.periodicals.command.CommandProvider;
import by.kiselevich.periodicals.command.Page;
import by.kiselevich.periodicals.command.JspParameter;
import by.kiselevich.periodicals.exception.NoJDBCDriverException;
import by.kiselevich.periodicals.exception.NoJDBCPropertiesException;
import by.kiselevich.periodicals.pool.ConnectionPool;
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
@MultipartConfig(maxFileSize = 1024 * 1024 * 5)
public class ControllerServlet extends HttpServlet {

    private static final Logger LOG = LogManager.getLogger(ControllerServlet.class);
    private static final CommandProvider commandProvider = CommandProvider.getInstance();
    private static final long serialVersionUID = 2992999480268151300L;

    @Override
    public void init() {
        LOG.trace("ControllerServlet init");
        try {
            ConnectionPool.INSTANCE.initPool();
        } catch (NoJDBCPropertiesException | NoJDBCDriverException e) {
            LOG.error(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        LOG.trace("ControllerServlet doGet");
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        LOG.trace("ControllerServlet doPost");
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        if (!ConnectionPool.INSTANCE.isPoolInitiated()) {
            req.getRequestDispatcher(Page.APP_FAILURE.getPath()).forward(req, resp);
        } else {
            try {
                String commandParameter = req.getParameter(JspParameter.COMMAND.getValue());
                Command command = commandProvider.getCommand(commandParameter);
                LOG.info("Executing command: {}", command);
                Page page = command.execute(req, resp);
                if (page != Page.EMPTY_PAGE) {
                    req.getRequestDispatcher(page.getPath()).forward(req, resp);
                }
            } catch (RuntimeException e) {
                LOG.error(e);
                req.getRequestDispatcher(Page.WRONG_REQUEST.getPath()).forward(req, resp);
            }
        }
    }

    @Override
    public void destroy() {
        LOG.trace("ControllerServlet destroy");
        ConnectionPool.INSTANCE.deInitPool();
    }
}