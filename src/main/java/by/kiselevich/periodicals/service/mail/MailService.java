package by.kiselevich.periodicals.service.mail;

import by.kiselevich.periodicals.entity.User;

import javax.servlet.http.HttpSession;

/**
 * Service to work with email
 */
public interface MailService {

    /**
     * Sends registration letter to {@link User} with locate from {@link HttpSession}
     * @param user {@link User} to which letter send
     */
    void sendRegistrationLetter(User user);
}
