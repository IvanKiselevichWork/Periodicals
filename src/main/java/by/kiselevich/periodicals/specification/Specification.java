package by.kiselevich.periodicals.specification;

import by.kiselevich.periodicals.exception.RepositoryException;
import org.apache.logging.log4j.LogManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface Specification<T> {
    /**
     * Execute {@code Specification} and return result as {@code List}
     * @return {@code List} result
     * @throws RepositoryException if repository error occurs
     */
    List<T> query() throws RepositoryException;

    /**
     * Close {@code ResultSet} if not null and not closed
     * @param resultSet {@code ResultSet} to close
     */
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
