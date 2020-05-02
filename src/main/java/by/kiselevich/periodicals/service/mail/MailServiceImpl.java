package by.kiselevich.periodicals.service.mail;

import by.kiselevich.periodicals.entity.User;
import by.kiselevich.periodicals.util.EmailSender;
import by.kiselevich.periodicals.util.HttpUtil;

/**
 * Implementation of {@link MailService}
 */
public class MailServiceImpl implements MailService {

    private static final String SUBJECT_KEY = "registration_letter_subject";
    private static final String BODY_FORMAT_KEY = "registration_letter_body";
    private static final String RU_LANGUAGE = "ru";
    private static final String EN_LANGUAGE = "en";
    private static final String SUBJECT_DELIMITER = " ";
    private static final String BODY_DELIMITER = "<br><br>";

    @Override
    public void sendRegistrationLetter(User user) {
        String letterSubjectRu = HttpUtil.getLocalizedMessageFromResources(RU_LANGUAGE, SUBJECT_KEY);
        String bodyFormatRu = HttpUtil.getLocalizedMessageFromResources(RU_LANGUAGE, BODY_FORMAT_KEY);
        String letterSubjectEn = HttpUtil.getLocalizedMessageFromResources(EN_LANGUAGE, SUBJECT_KEY);
        String bodyFormatEn = HttpUtil.getLocalizedMessageFromResources(EN_LANGUAGE, BODY_FORMAT_KEY);
        String bodyRu = String.format(bodyFormatRu, user.getFullName());
        String bodyEn = String.format(bodyFormatEn, user.getFullName());
        String letterSubject = letterSubjectEn + SUBJECT_DELIMITER + letterSubjectRu;
        String body = bodyEn + BODY_DELIMITER + bodyRu;
        EmailSender.getInstance().sendLetter(user.getEmail(), letterSubject, body);
    }
}
