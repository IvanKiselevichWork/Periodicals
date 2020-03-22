package by.kiselevich.periodicals.tag;

import by.kiselevich.periodicals.command.ResourceBundleMessages;
import by.kiselevich.periodicals.util.HttpUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class CopyrightTag extends TagSupport {

    private static final Logger LOG = LogManager.getLogger(CopyrightTag.class);
    private static final long serialVersionUID = -5066815186006999344L;

    @Override
    public int doStartTag() {
        try {
            String copyright = HttpUtil.getLocalizedMessageFromResources(pageContext.getSession(), ResourceBundleMessages.COPYRIGHT.getKey());
            JspWriter jspWriter = pageContext.getOut();
            jspWriter.write(copyright);
        } catch (IOException e) {
            LOG.warn(e);
        }
        return SKIP_BODY;
    }
}
