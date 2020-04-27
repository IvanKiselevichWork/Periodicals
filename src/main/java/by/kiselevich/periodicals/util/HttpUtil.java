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
    private static final Locale DEFAULT_LOCALE = new Locale("en");
    private static final String XSS_SYMBOLS_REGEX = "[%<>]";
    private static final String XSS_SYMBOL_REPLACEMENT = "_";


    private HttpUtil() {

    }

    /**
     * Writes a string to response
     * @param resp {@link HttpServletResponse} to write message in
     * @param message {@link String} to be written
     */
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

    /**
     * Returns string from {@code pagecontent} {@link ResourceBundle} by key with locale from session
     * @param session {@link HttpSession} for getting locale
     * @param key {@link String} to find message by
     * @return localized message
     */
    public static String getLocalizedMessageFromResources(HttpSession session, String key) {
        String language = (String) session.getAttribute(Attribute.LANGUAGE.getValue());
        Locale locale;
        if (language != null) {
            locale = new Locale(language);
        } else {
            locale = DEFAULT_LOCALE;
        }

        ResourceBundle resourceBundle = ResourceBundle.getBundle(RESOURCE_NAME, locale);
        return resourceBundle.getString(key);
    }

    /**
     * Replace symbols {@code <>&} with '_' to prevent XSS
     * @param data {@code String} to replace
     * @return {@code String} without XSS symbols
     */
    public static String parseToPreventXss(String data) {
        return data.replaceAll(XSS_SYMBOLS_REGEX, XSS_SYMBOL_REPLACEMENT);
    }
}
