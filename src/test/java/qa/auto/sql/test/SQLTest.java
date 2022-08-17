package qa.auto.sql.test;

import org.testng.Assert;
import org.testng.annotations.Test;
import qa.auto.sql.base.SQLBaseState;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SQLTest extends SQLBaseState {

    @Test(description = "Countries with euro")
    public void testCountriesWithEuro() throws SQLException {

        // return name of countries where currency is euro
        String sqlQuery = "SELECT countries.name " +
                "FROM countries JOIN economy " +
                "ON countries.id = economy.id " +
                "WHERE economy.currency = 'Euro' " +
                "ORDER BY name DESC";

        ResultSet rs = executeQuery(sqlQuery);
        List<String> results = new ArrayList<>();
        while (rs.next()) {
            results.add(rs.getString("name"));
        }

        Assert.assertEquals(
                results,
                Stream.of("France", "Belgium").collect(Collectors.toList()),
                "Results are wrong");
    }

    @Test(description = "Country with largest population")
    public void testCountryWithLargestPopulation() throws SQLException {

        // return country and its language where largest population
        String sqlQuery = "SELECT countries.name, population.main_language " +
                "FROM countries JOIN population " +
                "ON countries.id = population.id " +
                "WHERE population.population = " +
                "(SELECT MAX(population.population) FROM population)";

        ResultSet rs = executeQuery(sqlQuery);
        rs.next();
        String result = rs.getString("name") + " - " + rs.getString("main_language");

        Assert.assertEquals(
                result,
                "Japan - Japanese",
                "Results are wrong");
    }

    @Test(description = "Capital of countries with gbd in 'billion'")
    public void testCapitalOfCountriesWithGbdInBillion() throws SQLException {

        // return capitals of countries where gbd are billions
        String sqlQuery = "SELECT countries.capital " +
                "FROM countries JOIN economy " +
                "ON countries.id = economy.id " +
                "WHERE economy.gdb LIKE '%billion' " +
                "ORDER BY capital";

        ResultSet rs = executeQuery(sqlQuery);
        List<String> results = new ArrayList<>();
        while (rs.next()) {
            results.add(rs.getString("capital"));
        }

        Assert.assertEquals(
                results,
                Stream.of("Brussels", "Prague").collect(Collectors.toList()),
                "Results are wrong");
    }

    @Test(description = "Add, update, delete record")
    public void testAddUpdateDeleteRecord() throws SQLException {

        // add, update, delete record for every tables

        // adding
        String rowsAddQuery = "INSERT INTO countries (name, capital) VALUES ('Nepal', 'Kathmandu'); " +
                "INSERT INTO population (main_language, population) VALUES ('Nepali', '29640448'); " +
                "INSERT INTO economy (gdb, currency) VALUES ('101,9 billion', 'Nepalese rupee');";
        executeUpdate(rowsAddQuery);
        ResultSet rs = executeQuery("SELECT count(*) FROM countries");
        rs.next();
        int newRecordId = rs.getInt(1);

        String checkQuery = "SELECT * FROM countries " +
                "JOIN population ON countries.id = population.id " +
                "JOIN economy ON countries.id = economy.id " +
                "WHERE countries.name = " +
                "(SELECT countries.name FROM countries WHERE id = '" + newRecordId + "')";
        rs = executeQuery(checkQuery);
        Assert.assertTrue(
                rs.next(),
                "Record isn't added");
        Assert.assertEquals(
                rowToString(rs),
                String.format("%s, Nepal, Kathmandu, %s, Nepali, 29640448, %s, 101,9 billion, Nepalese rupee", newRecordId, newRecordId, newRecordId),
                "Results row doesn't have required data");

        // updating
        String rowsUpdateQuery = "UPDATE countries SET name='China',capital='Beijing' WHERE id = " + newRecordId + ";" +
                "UPDATE population SET main_language='Mandarin',population='1412600000' WHERE id = " + newRecordId + ";" +
                "UPDATE economy SET gdb='30,18 trillion',currency='Renminbi' WHERE id = " + newRecordId + ";";
        executeUpdate(rowsUpdateQuery);

        rs = executeQuery(checkQuery);
        Assert.assertTrue(
                rs.next(),
                "Record isn't exist");
        Assert.assertEquals(
                rowToString(rs),
                String.format("%s, China, Beijing, %s, Mandarin, 1412600000, %s, 30,18 trillion, Renminbi", newRecordId, newRecordId, newRecordId),
                "Results row doesn't have updated data");

        // deleting
        String rowsDeleteQuery = "DELETE FROM population WHERE id = " + newRecordId + ";" +
                "DELETE FROM economy WHERE id = " + newRecordId + ";" +
                "DELETE FROM countries WHERE id = " + newRecordId + ";";
        executeUpdate(rowsDeleteQuery);

        checkQuery = "SELECT NOT EXISTS(SELECT * FROM countries WHERE name = 'China');";
        rs = executeQuery(checkQuery);
        rs.next();
        Assert.assertTrue(
                rs.getBoolean(1),
                "Results row isn't deleted");
    }

}