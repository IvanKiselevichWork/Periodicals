package by.kiselevich.periodicals.specification;

import by.kiselevich.periodicals.exception.RepositoryException;
import org.apache.logging.log4j.LogManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface Specification<T> {
    List<T> query() throws RepositoryException;

    default void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                if (!resultSet.isClosed()) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                 LogManager.getLogger().warn(e);
            }
        }
    }
}
