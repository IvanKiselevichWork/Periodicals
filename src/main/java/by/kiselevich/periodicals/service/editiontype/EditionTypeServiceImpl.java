package by.kiselevich.periodicals.service.editiontype;

import by.kiselevich.periodicals.command.ResourceBundleMessages;
import by.kiselevich.periodicals.entity.EditionType;
import by.kiselevich.periodicals.exception.RepositoryException;
import by.kiselevich.periodicals.exception.ServiceException;
import by.kiselevich.periodicals.factory.RepositoryFactory;
import by.kiselevich.periodicals.repository.editiontype.EditionTypeRepository;
import by.kiselevich.periodicals.specification.editiontype.FindAllEditionsTypes;
import by.kiselevich.periodicals.specification.editiontype.FindEditionTypeById;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class EditionTypeServiceImpl implements EditionTypeService {

    private static final Logger LOG = LogManager.getLogger(EditionTypeServiceImpl.class);

    private final EditionTypeRepository editionTypeRepository;

    public EditionTypeServiceImpl() {
        editionTypeRepository = RepositoryFactory.getInstance().getEditionTypeRepository();
    }

    @Override
    public List<EditionType> getAllEditionsTypes() throws ServiceException {
        try {
            return editionTypeRepository.query(new FindAllEditionsTypes());
        } catch (RepositoryException e) {
            LOG.warn(e);
            throw new ServiceException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }
    }

    @Override
    public List<EditionType> getEditionTypeById(int id) throws ServiceException {
        try {
            return editionTypeRepository.query(new FindEditionTypeById(id));
        } catch (RepositoryException e) {
            LOG.warn(e);
            throw new ServiceException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }
    }
}
