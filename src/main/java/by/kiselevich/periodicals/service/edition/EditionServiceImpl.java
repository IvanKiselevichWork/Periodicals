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

    private EditionRepository editionRepository;
    private EditionValidator editionValidator;

    public EditionServiceImpl() {
        editionRepository = RepositoryFactory.getInstance().getEditionRepository();
        editionValidator = new EditionValidator();
    }

    @Override
    public Edition add(Edition edition) throws ServiceException {
        try {
            editionValidator.checkEdition(edition);
            editionRepository.add(edition);

            return edition;
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
    public List<Edition> getNotBlockedEditionsByNameAndTypeIdAndThemeId(String name, int typeId, int themeId) throws ServiceException {
        try {
            return editionRepository.query(new FindNotBlockedEditionsByNameAndTypeIdAndThemeId(name, typeId, themeId));
        } catch (RepositoryException e) {
            LOG.warn(e);
            throw new ServiceException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }
    }

    @Override
    public List<Edition> getNotBlockedEditionsByNameAndTypeId(String name, int typeId) throws ServiceException {
        try {
            return editionRepository.query(new FindNotBlockedEditionsByNameAndTypeId(name, typeId));
        } catch (RepositoryException e) {
            LOG.warn(e);
            throw new ServiceException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }
    }

    @Override
    public List<Edition> getNotBlockedEditionsByNameAndThemeId(String name, int themeId) throws ServiceException {
        try {
            return editionRepository.query(new FindNotBlockedEditionsByNameAndThemeId(name, themeId));
        } catch (RepositoryException e) {
            LOG.warn(e);
            throw new ServiceException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }
    }

    @Override
    public List<Edition> getNotBlockedEditionsByName(String name) throws ServiceException {
        try {
            return editionRepository.query(new FindNotBlockedEditionsByName(name));
        } catch (RepositoryException e) {
            LOG.warn(e);
            throw new ServiceException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }
    }

    @Override
    public List<Edition> getEditionsById(int editionId) throws ServiceException {
        try {
            return editionRepository.query(new FindEditionById(editionId));
        } catch (RepositoryException e) {
            LOG.warn(e);
            throw new ServiceException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }
    }

    @Override
    public void blockEdition(int id) throws ServiceException {
        try {
            editionRepository.block(id);
        } catch (RepositoryException e) {
            LOG.warn(e);
            throw new ServiceException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }
    }

    @Override
    public void unblockEdition(int id) throws ServiceException {
        try {
            editionRepository.unblock(id);
        } catch (RepositoryException e) {
            LOG.warn(e);
            throw new ServiceException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }
    }
}
