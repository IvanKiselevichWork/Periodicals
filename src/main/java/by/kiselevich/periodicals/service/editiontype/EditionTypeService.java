package by.kiselevich.periodicals.service.editiontype;

import by.kiselevich.periodicals.entity.EditionType;
import by.kiselevich.periodicals.exception.ServiceException;

import java.util.List;

public interface EditionTypeService {

    List<EditionType> getAllEditionsTypes() throws ServiceException;
}
