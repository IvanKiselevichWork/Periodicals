package by.kiselevich.periodicals.service.mail;

import by.kiselevich.periodicals.entity.User;

import javax.servlet.http.HttpSession;


public interface MailService {

    void sendRegistrationLetter(User user, HttpSession session);
}
