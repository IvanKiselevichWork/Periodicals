package by.kiselevich.periodicals.service.edition;

import by.kiselevich.periodicals.command.ResourceBundleMessages;
import by.kiselevich.periodicals.entity.Edition;
import by.kiselevich.periodicals.exception.RepositoryException;
import by.kiselevich.periodicals.exception.ServiceException;
import by.kiselevich.periodicals.factory.EditionRepositoryFactory;
import by.kiselevich.periodicals.repository.edition.EditionRepository;
import by.kiselevich.periodicals.specification.edition.FindAllEditions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class EditionServiceImpl implements EditionService {

    private static final Logger LOG = LogManager.getLogger(EditionServiceImpl.class);

    private EditionRepository editionRepository;

    public EditionServiceImpl() {
        editionRepository = EditionRepositoryFactory.getInstance().getEditionRepository();
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
}
