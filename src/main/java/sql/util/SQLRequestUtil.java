package sql.util;

import models.DBTableData;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SQLRequestUtil {

    public static String createTable(DBTableData table) {
        return "CREATE TABLE " + table.getTableName() + "(" +
                table.getColumns().stream()
                        .map(c -> c.getColumnName() + " " + c.getColumnType())
                        .collect(Collectors.joining(", ")) + ");\n";
    }

    public static String dropTableIfExist(DBTableData table) {
        return "DROP TABLE IF EXISTS " + table.getTableName() + ";\n";
    }

    public static String insert(DBTableData table) {
        int maxValues = table.getColumns().stream().mapToInt(c -> c.getColumnValues().size()).max()
                .orElseThrow(()-> new RuntimeException("Unable to get rows count in 'insert()' method"));

        String insertInfo = String.format("(%s) VALUES ", table.getColumns().stream()
                .map(DBTableData.Column::getColumnName)
                .filter(n -> !n.equals("id"))
                .collect(Collectors.joining(", ")));

        List<String> insertsList = new ArrayList<>();

        for (int i = 0; i < maxValues; i++) {
            List<String> values = new ArrayList<>();
            for (DBTableData.Column column : table.getColumns()) {
                if (column.getColumnValues().size() - 1 < i)
                    continue;
                values.add(String.format("'%s'", column.getColumnValues().get(i)));
            }
            insertsList.add(String.format(insertInfo + "(%s)", String.join(", ", values)));
        }

        List<String> sql = insertsList.stream().map(i -> String.format("INSERT INTO %s %s;", table.getTableName(), i)).collect(Collectors.toList());
        return String.join("\n", sql);
    }

}