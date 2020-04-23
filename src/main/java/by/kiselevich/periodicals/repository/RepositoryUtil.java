package by.kiselevich.periodicals.repository;

import by.kiselevich.periodicals.exception.RepositoryException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RepositoryUtil {

    private static final Logger LOG = LogManager.getLogger(RepositoryUtil.class);

    private static final int ONE_ROW_COUNT = 1;
    private static final int GENERATED_ID_ROW_NUMBER = 1;

    protected int executeUpdateOneRowAndGetGeneratedId(PreparedStatement statement, String exceptionMessage) throws RepositoryException {
        ResultSet generatedId = null;
        try {
            int updatedRowCount = statement.executeUpdate();
            if (updatedRowCount == ONE_ROW_COUNT) {
                generatedId = statement.getGeneratedKeys();
                if (generatedId.next()) {
                    return generatedId.getInt(GENERATED_ID_ROW_NUMBER);
                }
            }
            throw new RepositoryException(exceptionMessage);
        } catch (SQLException e) {
            throw new RepositoryException(e);
        } finally {
            closeResultSet(generatedId);
        }
    }

    protected void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                if (!resultSet.isClosed()) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                LOG.warn(e);
            }
        }
    }
}
