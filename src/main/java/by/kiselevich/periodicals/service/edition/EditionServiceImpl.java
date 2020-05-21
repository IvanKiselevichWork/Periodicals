package by.kiselevich.periodicals.service.edition;

import by.kiselevich.periodicals.command.ResourceBundleMessages;
import by.kiselevich.periodicals.dao.edition.EditionDao;
import by.kiselevich.periodicals.entity.Edition;
import by.kiselevich.periodicals.exception.DaoException;
import by.kiselevich.periodicals.exception.ValidatorException;
import by.kiselevich.periodicals.exception.ServiceException;
import by.kiselevich.periodicals.validator.EditionValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of {@link EditionService}
 */
@Service
public class EditionServiceImpl implements EditionService {

    private static final Logger LOG = LogManager.getLogger(EditionServiceImpl.class);

    private EditionDao editionDao;
    private final EditionValidator editionValidator;

    public EditionServiceImpl() {
        editionValidator = EditionValidator.getInstance();
    }

    public void setEditionDao(EditionDao editionDao) {
        this.editionDao = editionDao;
    }

    @Override
    @Transactional
    public void add(Edition edition) throws ServiceException {
        try {
            editionValidator.checkEdition(edition);
            editionDao.add(edition);
        } catch (DaoException e) {
            LOG.warn(e);
            throw new ServiceException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        } catch (ValidatorException e) {
            LOG.info(e);
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public void update(Edition edition) throws ServiceException {
        try {
            editionValidator.checkEdition(edition);
            editionDao.update(edition);
        } catch (DaoException e) {
            LOG.warn(e);
            throw new ServiceException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        } catch (ValidatorException e) {
            LOG.info(e);
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public List<Edition> getAllEditions() throws ServiceException {
        try {
            return editionDao.getAllEditions();
        } catch (DaoException e) {
            LOG.warn(e);
            throw new ServiceException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }
    }

    @Override
    @Transactional
    public List<Edition> getNotBlockedEditionsByNameAndTypeIdAndThemeId(String name, Integer typeId, Integer themeId) throws ServiceException {
        try {
            return editionDao.getEditionsByNameAndTypeAndThemeAndBlockage(name, typeId, themeId, false);
        } catch (DaoException e) {
            LOG.warn(e);
            throw new ServiceException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }
    }

    @Override
    @Transactional
    public Edition getEditionById(int editionId, boolean findNotBlockedEditionsOnly) throws ServiceException {
        try {
            return editionDao.getEditionById(editionId, findNotBlockedEditionsOnly);
        } catch (DaoException e) {
            LOG.warn(e);
            throw new ServiceException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }
    }

    @Override
    @Transactional
    public void blockEdition(int id) throws ServiceException {
        try {
            if (editionDao.getEditionById(id, false) == null) {
                throw new ServiceException(ResourceBundleMessages.EDITION_NOT_FOUND.getKey());
            }
            editionDao.block(id);
        } catch (DaoException e) {
            LOG.warn(e);
            throw new ServiceException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }
    }

    @Override
    @Transactional
    public void unblockEdition(int id) throws ServiceException {
        try {
            if (editionDao.getEditionById(id, false) == null) {
                throw new ServiceException(ResourceBundleMessages.EDITION_NOT_FOUND.getKey());
            }
            editionDao.unblock(id);
        } catch (DaoException e) {
            LOG.warn(e);
            throw new ServiceException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }
    }
}
