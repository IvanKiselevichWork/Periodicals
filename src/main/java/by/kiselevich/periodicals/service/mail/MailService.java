package by.kiselevich.periodicals.service.mail;

import by.kiselevich.periodicals.entity.User;

import javax.servlet.http.HttpSession;


public interface MailService {

    /**
     * Sends registration letter to {@link User} with locate from {@link HttpSession}
     * @param user {@link User} to which letter send
     * @param session {@link HttpSession} of {@link User}
     */
    void sendRegistrationLetter(User user, HttpSession session);
}
