package by.kiselevich.periodicals.service.edition;

import by.kiselevich.periodicals.command.ResourceBundleMessages;
import by.kiselevich.periodicals.entity.Edition;
import by.kiselevich.periodicals.exception.ValidatorException;
import by.kiselevich.periodicals.exception.RepositoryException;
import by.kiselevich.periodicals.exception.ServiceException;
import by.kiselevich.periodicals.factory.RepositoryFactory;
import by.kiselevich.periodicals.repository.edition.EditionRepository;
import by.kiselevich.periodicals.specification.edition.*;
import by.kiselevich.periodicals.validator.EditionValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class EditionServiceImpl implements EditionService {

    private static final Logger LOG = LogManager.getLogger(EditionServiceImpl.class);

    private final EditionRepository editionRepository;
    private final EditionValidator editionValidator;

    public EditionServiceImpl() {
        editionRepository = RepositoryFactory.getInstance().getEditionRepository();
        editionValidator = EditionValidator.getInstance();
    }

    @Override
    public void add(Edition edition) throws ServiceException {
        try {
            editionValidator.checkEdition(edition);
            editionRepository.add(edition);
        } catch (RepositoryException e) {
            LOG.warn(e);
            throw new ServiceException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        } catch (ValidatorException e) {
            LOG.info(e);
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void update(Edition edition) throws ServiceException {
        try {
            editionValidator.checkEdition(edition);
            editionRepository.update(edition);
        } catch (RepositoryException e) {
            LOG.warn(e);
            throw new ServiceException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        } catch (ValidatorException e) {
            LOG.info(e);
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<Edition> getAllEditions() throws ServiceException {
        try {
            return editionRepository.query(new FindAllEditions());
        } catch (RepositoryException e) {
            LOG.warn(e);
            throw new ServiceException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }
    }

    @Override
    public List<Edition> getNotBlockedEditionsByNameAndTypeIdAndThemeId(String name, Integer typeId, Integer themeId) throws ServiceException {
        try {
            return editionRepository.query(new FindNotBlockedEditionsByNameAndTypeIdAndThemeId(name, typeId, themeId));
        } catch (RepositoryException e) {
            LOG.warn(e);
            throw new ServiceException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }
    }

    @Override
    public List<Edition> getEditionsById(int editionId, boolean findNotBlockedEditionsOnly) throws ServiceException {
        try {
            return editionRepository.query(new FindEditionById(editionId, findNotBlockedEditionsOnly));
        } catch (RepositoryException e) {
            LOG.warn(e);
            throw new ServiceException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }
    }

    @Override
    public void blockEdition(int id) throws ServiceException {
        try {
            if (editionRepository.query(new FindEditionById(id, false)).isEmpty()) {
                throw new ServiceException(ResourceBundleMessages.EDITION_NOT_FOUND.getKey());
            }
            editionRepository.block(id);
        } catch (RepositoryException e) {
            LOG.warn(e);
            throw new ServiceException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }
    }

    @Override
    public void unblockEdition(int id) throws ServiceException {
        try {
            if (editionRepository.query(new FindEditionById(id, false)).isEmpty()) {
                throw new ServiceException(ResourceBundleMessages.EDITION_NOT_FOUND.getKey());
            }
            editionRepository.unblock(id);
        } catch (RepositoryException e) {
            LOG.warn(e);
            throw new ServiceException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }
    }
}
