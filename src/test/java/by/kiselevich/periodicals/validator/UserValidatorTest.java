package by.kiselevich.periodicals.validator;

import by.kiselevich.periodicals.entity.User;
import by.kiselevich.periodicals.exception.NoJDBCDriverException;
import by.kiselevich.periodicals.exception.NoJDBCPropertiesException;
import by.kiselevich.periodicals.exception.ServiceException;
import by.kiselevich.periodicals.exception.ValidatorException;
import by.kiselevich.periodicals.pool.ConnectionPoolImpl;
import org.junit.*;

public class UserValidatorTest extends Assert {

    private static final UserValidator userValidator = UserValidator.getInstance();

    @BeforeClass
    public static void init() throws NoJDBCDriverException, NoJDBCPropertiesException {
        ConnectionPoolImpl.INSTANCE.initPool();
    }

    @AfterClass
    public static void deInit() {
        ConnectionPoolImpl.INSTANCE.deInitPool();
    }

    @Test
    public void testCheckUserCredentialsOnSignUpPositive() throws ValidatorException, ServiceException {
        userValidator.checkUserCredentialsOnSignUp(getUser());
    }

    @Test(expected = ValidatorException.class)
    public void testCheckUserCredentialsOnSignUpInvalidLogin() throws ValidatorException, ServiceException {
        User user = getUser();
        user.setLogin("");
        userValidator.checkUserCredentialsOnSignUp(user);
    }

    @Test(expected = ValidatorException.class)
    public void testCheckUserCredentialsOnSignUpInvalidPassword() throws ValidatorException, ServiceException {
        User user = getUser();
        user.setPassword("");
        userValidator.checkUserCredentialsOnSignUp(user);
    }

    @Test(expected = ValidatorException.class)
    public void testCheckUserCredentialsOnSignUpInvalidFullName() throws ValidatorException, ServiceException {
        User user = getUser();
        user.setFullName("");
        userValidator.checkUserCredentialsOnSignUp(user);
    }

    @Test(expected = ValidatorException.class)
    public void testCheckUserCredentialsOnSignUpInvalidEmail() throws ValidatorException, ServiceException {
        User user = getUser();
        user.setEmail("");
        userValidator.checkUserCredentialsOnSignUp(user);
    }

    @Test
    public void testCheckUserCredentialsOnSignInPositive() throws ValidatorException {
        userValidator.checkUserCredentialsOnSignIn(getUser());
    }

    @Test(expected = ValidatorException.class)
    public void testCheckUserCredentialsOnSignInInvalidLogin() throws ValidatorException {
        User user = getUser();
        user.setLogin("");
        userValidator.checkUserCredentialsOnSignIn(user);
    }

    @Test(expected = ValidatorException.class)
    public void testCheckUserCredentialsOnSignInInvalidPassword() throws ValidatorException {
        User user = getUser();
        user.setPassword("");
        userValidator.checkUserCredentialsOnSignIn(user);
    }

    private User getUser() {
        return new User.UserBuilder()
                .id(0)
                .userRole(null)
                .login("login")
                .password("password")
                .fullName("full name")
                .email("email@email.com")
                .build();
    }
}
