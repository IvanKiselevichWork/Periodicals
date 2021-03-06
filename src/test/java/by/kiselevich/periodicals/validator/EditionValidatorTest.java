package by.kiselevich.periodicals.validator;

import by.kiselevich.periodicals.entity.Edition;
import by.kiselevich.periodicals.entity.EditionTheme;
import by.kiselevich.periodicals.entity.EditionType;
import by.kiselevich.periodicals.exception.NoJDBCDriverException;
import by.kiselevich.periodicals.exception.NoJDBCPropertiesException;
import by.kiselevich.periodicals.exception.ServiceException;
import by.kiselevich.periodicals.exception.ValidatorException;
import by.kiselevich.periodicals.pool.ConnectionPoolImpl;
import org.junit.*;

import java.math.BigDecimal;

public class EditionValidatorTest extends Assert {

    private static final EditionValidator editionValidator = EditionValidator.getInstance();

    @BeforeClass
    public static void init() throws NoJDBCDriverException, NoJDBCPropertiesException {
        ConnectionPoolImpl.INSTANCE.initPool();
    }

    @AfterClass
    public static void deInit() {
        ConnectionPoolImpl.INSTANCE.deInitPool();
    }

    @Test
    public void testCheckEditionPositive() throws ValidatorException, ServiceException {
        Edition edition = getEdition();
        editionValidator.checkEdition(edition);
    }

    @Test(expected = ValidatorException.class)
    public void testCheckEditionInvalidName() throws ValidatorException, ServiceException {
        Edition edition = getEdition();
        edition.setName("");
        editionValidator.checkEdition(edition);
    }

    @Test(expected = ValidatorException.class)
    public void testCheckEditionInvalidType() throws ValidatorException, ServiceException {
        Edition edition = getEdition();
        edition.getEditionType().setType("Invalid type");
        editionValidator.checkEdition(edition);
    }

    @Test(expected = ValidatorException.class)
    public void testCheckEditionInvalidTheme() throws ValidatorException, ServiceException {
        Edition edition = getEdition();
        edition.getEditionTheme().setTitle("Invalid theme");
        editionValidator.checkEdition(edition);
    }

    @Test(expected = ValidatorException.class)
    public void testCheckEditionInvalidPeriodicity() throws ValidatorException, ServiceException {
        Edition edition = getEdition();
        edition.setPeriodicityPerYear(0);
        editionValidator.checkEdition(edition);
    }

    @Test(expected = ValidatorException.class)
    public void testCheckEditionInvalidMinimumSubscriptionPeriod() throws ValidatorException, ServiceException {
        Edition edition = getEdition();
        edition.setMinimumSubscriptionPeriodInMonths(0);
        editionValidator.checkEdition(edition);
    }

    @Test(expected = ValidatorException.class)
    public void testCheckEditionInvalidPrice() throws ValidatorException, ServiceException {
        Edition edition = getEdition();
        edition.setPriceForMinimumSubscriptionPeriod(BigDecimal.valueOf(0));
        editionValidator.checkEdition(edition);
    }

    private Edition getEdition() {
        return new Edition.EditionBuilder()
                .id(1)
                .name("Name")
                .editionType(new EditionType.EditionTypeBuilder()
                    .id(1)
                    .type("Газета")
                    .build())
                .editionTheme(new EditionTheme.EditionThemeBuilder()
                        .id(1)
                        .title("Автомобили. Транспорт")
                        .build())
                .periodicityPerYear(6)
                .minimumSubscriptionPeriodInMonths(3)
                .priceForMinimumSubscriptionPeriod(BigDecimal.valueOf(20))
                .build();
    }
}
