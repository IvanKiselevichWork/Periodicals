package by.kiselevich.periodicals.factory;

import by.kiselevich.periodicals.service.edition.EditionService;
import by.kiselevich.periodicals.service.edition.EditionServiceImpl;

public class EditionServiceFactory {

    private EditionService editionService;

    private EditionServiceFactory(){
        editionService = new EditionServiceImpl();
    }

    private static class EditionServiceFactoryHolder {
        private static final EditionServiceFactory INSTANCE = new EditionServiceFactory();
    }

    public static EditionServiceFactory getInstance() {
        return EditionServiceFactoryHolder.INSTANCE;
    }

    public EditionService getEditionService() {
        return editionService;
    }
}
