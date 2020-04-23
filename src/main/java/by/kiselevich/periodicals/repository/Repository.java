package by.kiselevich.periodicals.repository;

import by.kiselevich.periodicals.exception.RepositoryException;
import by.kiselevich.periodicals.specification.Specification;
import org.apache.logging.log4j.LogManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface Repository<T> {

    List<T> query(Specification<T> specification) throws RepositoryException;

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
