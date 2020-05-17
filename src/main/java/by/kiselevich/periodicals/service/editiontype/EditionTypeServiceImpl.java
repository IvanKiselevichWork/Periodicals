package by.kiselevich.periodicals.service.editiontype;

import by.kiselevich.periodicals.command.ResourceBundleMessages;
import by.kiselevich.periodicals.dao.editiontype.EditionTypeDao;
import by.kiselevich.periodicals.entity.EditionType;
import by.kiselevich.periodicals.exception.DaoException;
import by.kiselevich.periodicals.exception.ServiceException;
import by.kiselevich.periodicals.factory.DaoFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Implementation of {@link EditionTypeService}
 */
public class EditionTypeServiceImpl implements EditionTypeService {

    private static final Logger LOG = LogManager.getLogger(EditionTypeServiceImpl.class);

    private final EditionTypeDao editionTypeDao;

    public EditionTypeServiceImpl() {
        editionTypeDao = DaoFactory.getInstance().getEditionTypeDao();
    }

    @Override
    public List<EditionType> getAllEditionsTypes() throws ServiceException {
        try {
            return editionTypeDao.getAllEditionTypes();
        } catch (DaoException e) {
            LOG.warn(e);
            throw new ServiceException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }
    }

    @Override
    public EditionType getEditionTypeById(int id) throws ServiceException {
        try {
            return editionTypeDao.getEditionTypeById(id);
        } catch (DaoException e) {
            LOG.warn(e);
            throw new ServiceException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }
    }
}
