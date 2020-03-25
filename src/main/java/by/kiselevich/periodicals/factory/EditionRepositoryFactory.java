package by.kiselevich.periodicals.factory;

import by.kiselevich.periodicals.repository.edition.EditionRepository;
import by.kiselevich.periodicals.repository.edition.EditionRepositoryImpl;

public class EditionRepositoryFactory {

    private EditionRepository editionRepository;

    private EditionRepositoryFactory(){
        editionRepository = new EditionRepositoryImpl();
    }

    private static class EditionRepositoryFactoryHolder {
        private static final EditionRepositoryFactory INSTANCE = new EditionRepositoryFactory();
    }

    public static EditionRepositoryFactory getInstance() {
        return EditionRepositoryFactoryHolder.INSTANCE;
    }

    public EditionRepository getEditionRepository() {
        return editionRepository;
    }
}
