package by.kiselevich.periodicals.util;

import by.kiselevich.periodicals.command.Attribute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.ResourceBundle;

public class HttpUtil {

    private static final Logger LOG = LogManager.getLogger(HttpUtil.class);

    private static final String TEXT_CONTENT_TYPE = "text/plain";
    private static final String UTF_8_ENCODING = "UTF-8";
    private static final String RESOURCE_NAME = "pagecontent";
    private static final Locale DEFAULT_LOCALE = new Locale("en", "US");

    private static final int LANGUAGE_BEGIN_INDEX = 0;
    private static final int LANGUAGE_END_INDEX = 2;
    private static final int TERRITORY_BEGIN_INDEX = 3;
    private static final int TERRITORY_END_INDEX = 5;

    private HttpUtil() {

    }

    public static void writeMessageToResponse(HttpServletResponse resp, String message) {
        try {
            resp.setContentType(TEXT_CONTENT_TYPE);
            resp.setCharacterEncoding(UTF_8_ENCODING);
            PrintWriter printWriter = resp.getWriter();
            printWriter.write(message);
        } catch (IOException e) {
            LOG.warn(e);
        }
    }

    public static String getLocalizedMessageFromResources(HttpSession session, String key) {
        String language = (String) session.getAttribute(Attribute.LANGUAGE.getValue());
        Locale locale;
        if (language != null) {
            // language has next syntax: "en_US", or "ru_RU", but we need, for example: new Locale("ru", "RU"), so we use substring
            locale = new Locale(language.substring(LANGUAGE_BEGIN_INDEX, LANGUAGE_END_INDEX), language.substring(TERRITORY_BEGIN_INDEX, TERRITORY_END_INDEX));
        } else {
            locale = DEFAULT_LOCALE;
        }

        ResourceBundle resourceBundle = ResourceBundle.getBundle(RESOURCE_NAME, locale);
        return resourceBundle.getString(key);
    }
}
