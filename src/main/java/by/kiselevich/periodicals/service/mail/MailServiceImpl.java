package by.kiselevich.periodicals.service.mail;

import by.kiselevich.periodicals.entity.User;
import by.kiselevich.periodicals.util.EmailSender;
import by.kiselevich.periodicals.util.HttpUtil;

import javax.servlet.http.HttpSession;

public class MailServiceImpl implements MailService {

    private static final String SUBJECT_KEY = "registration_letter_subject";
    private static final String BODY_FORMAT_KEY = "registration_letter_body";

    @Override
    public void sendRegistrationLetter(User user, HttpSession session) {
        String letterSubject = HttpUtil.getLocalizedMessageFromResources(session, SUBJECT_KEY);
        String bodyFormat = HttpUtil.getLocalizedMessageFromResources(session, BODY_FORMAT_KEY);
        String body = String.format(bodyFormat, user.getFullName());
        EmailSender.getInstance().sendLetter(user.getEmail(), letterSubject, body);
    }
}
