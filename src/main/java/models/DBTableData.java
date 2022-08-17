package models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DBTableData {

    private String tableName;
    private List<Column> columns = new ArrayList<>();

    public DBTableData(String tableName) {
        this.tableName = tableName;
    }

    public static class Column {
        private final String columnName;
        private final String columnType;
        private final List<String> columnValues;

        public Column(String columnName, String columnType, String... columnValues) {
            this.columnName = columnName;
            this.columnType = columnType;
            this.columnValues = Arrays.asList(columnValues);
        }

        public String getColumnName() {
            return columnName;
        }

        public String getColumnType() {
            return columnType;
        }

        public List<String> getColumnValues() {
            return columnValues;
        }
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void addColumn(String columnName, String columnType, String... columnValues) {
        columns.add(new Column(columnName, columnType, columnValues));
    }

    public List<Column> getColumns() {
        return columns;
    }

}
