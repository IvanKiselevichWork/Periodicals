package by.kiselevich.periodicals.command.admin;

import by.kiselevich.periodicals.command.Attribute;
import by.kiselevich.periodicals.command.JspParameter;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Class for {@link by.kiselevich.periodicals.command.Command} implementations, to work with long list by pages
 * @param <T> entity to work with parametrized {@link List}
 */
public class LongListUtil<T> {
    public static final int DEFAULT_RECORD_COUNT_PER_PAGE = 50;
    public static final int DEFAULT_PAGE_NUMBER = 1;

    /**
     * Returns sublist from received {@code list} by page number, got from {@link HttpServletRequest}. <br>
     * If page invalid, return first page.
     * @param req {@link HttpServletRequest} for page number getting
     * @param list {@link List} to get sublist from
     * @return {@link List}
     */
    public List<T> getSubListByPageFromRequest(HttpServletRequest req, List<T> list) {
        int subscriptionCount = list.size();
        int pages = subscriptionCount / DEFAULT_RECORD_COUNT_PER_PAGE + (subscriptionCount % DEFAULT_RECORD_COUNT_PER_PAGE == 0 ? 0 : 1);
        req.setAttribute(Attribute.PAGES.getValue(), pages);

        int pageNumber = getPageNumberFromRequest(req);
        if (pageNumber < 1 || pageNumber > pages) {
            pageNumber = 1;
        }
        int fromIndex = (pageNumber - 1) * DEFAULT_RECORD_COUNT_PER_PAGE;
        int toIndex =  pageNumber * DEFAULT_RECORD_COUNT_PER_PAGE;
        if (toIndex > subscriptionCount) {
            toIndex = subscriptionCount;
        }
        list = list.subList(fromIndex, toIndex);
        return list;
    }

    private int getPageNumberFromRequest(HttpServletRequest req) {
        String pageNumberString = req.getParameter(JspParameter.PAGE.getValue());
        try {
            return Integer.parseInt(pageNumberString);
        } catch (NumberFormatException e) {
            return DEFAULT_PAGE_NUMBER;
        }
    }
}
