package by.kiselevich.periodicals.service.edition;

import by.kiselevich.periodicals.entity.Edition;
import by.kiselevich.periodicals.exception.ServiceException;

import java.util.List;

public interface EditionService {

    List<Edition> getAllEditions() throws ServiceException;
}
