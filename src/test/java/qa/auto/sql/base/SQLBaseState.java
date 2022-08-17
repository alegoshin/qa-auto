package qa.auto.sql.base;

import io.qameta.allure.Step;
import models.DBTableData;
import org.testng.annotations.*;
import sql.util.DBConnectionUtil;
import sql.util.DBStatementUtil;
import sql.util.SQLRequestUtil;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

public class SQLBaseState {

    private static volatile Statement statement;
    public DBTableData countries;
    public DBTableData population;
    public DBTableData economy;

    {
        // countries table
        countries = new DBTableData("countries");
        countries.addColumn("id", "SERIAL PRIMARY KEY");
        countries.addColumn("name", "text NOT NULL", "France", "Belgium", "Czech", "Japan");
        countries.addColumn("capital", "text NOT NULL", "Paris", "Brussels", "Prague", "Tokyo");

        // population table
        population = new DBTableData("population");
        population.addColumn("id", "SERIAL REFERENCES countries");
        population.addColumn("main_language", "text NOT NULL", "French", "Dutch", "Czech", "Japanese");
        population.addColumn("population", "integer NOT NULL", "68084217", "11521238", "10701777", "125309000");

        // economy table
        economy = new DBTableData("economy");
        economy.addColumn("id", "SERIAL REFERENCES countries");
        economy.addColumn("gdb", "varchar(20) NOT NULL", "2,551 trillion", "529,7 billion", "282,34 billion", "5,08 trillion");
        economy.addColumn("currency", "text NOT NULL", "Euro", "Euro", "Czech crown", "Yen");
    }

    @BeforeMethod
    @Step("Start precondition method")
    public void setup(Method method) {
        try {
            statement = DBStatementUtil.getStatement();
            createTables();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @AfterMethod
    @Step("Close database connection")
    public void teardown() {
        try {
            deleteTables();
        } catch (SQLException ex) {
            System.out.println("Error: Tables wasn't removed");
        } finally {
            DBStatementUtil.close();
            DBConnectionUtil.close();
        }
    }

    @Step("Create tables")
    synchronized private void createTables() throws SQLException {
        List<DBTableData> tables = Arrays.asList(countries, economy, population);
        deleteTables();
        for (DBTableData table : tables) {
            String sqlQuery = SQLRequestUtil.createTable(table) + SQLRequestUtil.insert(table);
            statement.executeUpdate(sqlQuery);
        }
    }

    @Step("Delete tables")
    synchronized private void deleteTables() throws SQLException {
        List<DBTableData> tables = Arrays.asList(population, economy, countries);

        for (DBTableData table : tables) {
            statement.executeUpdate(SQLRequestUtil.dropTableIfExist(table));
        }
    }

    @Step("Execute: {sqlQuery}")
    public void execute(String sqlQuery) throws SQLException {
        statement.execute(sqlQuery);
    }

    @Step("Execute update: {sqlQuery}")
    public void executeUpdate(String sqlQuery) throws SQLException {
        statement.executeUpdate(sqlQuery);
    }

    @Step("Execute query: {sqlQuery}")
    public ResultSet executeQuery(String sqlQuery) throws SQLException {
        return statement.executeQuery(sqlQuery);
    }

    /**
     * Compile columns values to string
     *
     * @param rs ResultSet with already selected row by method 'next()'
     * @return Compiled columns values
     */
    public String rowToString(ResultSet rs) throws SQLException {
        StringBuilder row = new StringBuilder();
        int columnsCount = rs.getMetaData().getColumnCount();
        for (int i = 1; i <= columnsCount; i++) {
            row.append(rs.getString(i));
            if (i != columnsCount) {
                row.append(", ");
            }
        }
        return row.toString();
    }

}