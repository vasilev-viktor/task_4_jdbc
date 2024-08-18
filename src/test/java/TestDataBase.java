import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.*;

public class TestDataBase {

    private static final String URL = "jdbc:h2:tcp://localhost:9092/mem:testdb";
    private static final String USERNAME = "user";
    private static final String PASSWORD = "pass";

    @Test
    @DisplayName("driver")
    public void driver() throws ClassNotFoundException {
        Class.forName("org.h2.Driver");
        DriverManager.drivers().map(m -> m.getClass().getName()).forEach(System.out::println);

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {

            String task1 = "Кейс 1: Добавление нового товара. Фрукт - экзотический.";
            String task2 = "Кейс 2: Изменение существующего товара.";
            String task3 = "Кейс 3: Удаление товара.";
            String task4 = "Кейс 4: Добавление нового товара без указания FOOD_TYPE.";
            String task5 = "Кейс 5: Добавление нового товара с указанием в FOOD_EXOTIC знаначение типа данных VARCHAR.";
            String task6 = "Кейс 6: Добавление нового товара с указанием в FOOD_NAME смешанного значения (Буквы (Кирилица) и цифры).";
            String task7 = "Кейс 7: Добавление нового товара с длинным FOOD_NAME.";
            String task8 = "Кейс 8: Добавление нового товара с FOOD_TYPE 'NULL'.";


            try {
                statement.execute("INSERT INTO FOOD VALUES (5, 'Дуриан', 'FRUIT', 1)");
                System.out.println(task1 + "Создана новая строка  (5, 'Дуриан', 'FRUIT', 1)");
            } catch (SQLException e) {
                System.out.println(task1 + " Не создан, создан ранее");
            }

            try {
                statement.executeUpdate("UPDATE FOOD SET FOOD_EXOTIC = 0 WHERE FOOD_NAME = 'Апельсин'");
                System.out.println(task2 + "Изменение FOOD_EXOTIC с 1 на 0");
            } catch (SQLException e) {
                System.out.println(task2 + " Изменение не произошло");
            }


            try {
                statement.executeUpdate("DELETE FROM FOOD WHERE FOOD_ID = 5");
                System.out.println(task3 + "FOOD_ID = 5 - Удалено");
            } catch (SQLException e) {
                System.out.println(task3 + " Изменение не произошло");
            }


            try {
                statement.execute("INSERT INTO FOOD VALUES (5, 'Дуриан', 1)");
                System.out.println(task4 + "Создана новая строка (5, 'Дуриан', 1)");
            } catch (SQLException e) {
                System.out.println(task4 + " Строка не создана, нехватает значения");
            }


            try {
                statement.execute("INSERT INTO FOOD VALUES (5, 'Дуриан', 'FRUIT', 'ЭКЗОТИЧЕСКИЙ')");
                System.out.println(task5 + "Создана новая строка  (5, 'Дуриан', 'FRUIT', 'ЭКЗОТИЧЕСКИЙ')");
            } catch (SQLException e) {
                System.out.println(task5 + " Не создан, ОШИБКА: неверно указан тип данных");
            }


            try {
                statement.execute("INSERT INTO FOOD VALUES (5, 'арт 547890', 'FRUIT', '0')");
                statement.executeUpdate("DELETE FROM FOOD WHERE FOOD_ID = 5");
                System.out.println(task6 + "Создана новая строка  (5, 'арт 547890', 'FRUIT', '0'), удаление строки");
            } catch (SQLException e) {
                System.out.println(task6 + " Не создан, создан ранее");
            }


            try {
                statement.execute("INSERT INTO FOOD VALUES (5, 'Наивкуснейший, ароматнейший розовый пЭрсик, вахххххх," +
                        " сочный пЭрсик, мамой клянусь. Добро пожаловать в магазин У Ашота', 'VEGETABLE', '0')");
                System.out.println(task7 + "Создана новая строка  (5, 'Наивкуснейший, ароматнейший розовый пЭрсик, вахххххх," +
                        " сочный пЭрсик, мамой клянусь. Добро пожаловать в магазин У Ашота', 'VEGETABLE', '0')");
            } catch (SQLException e) {
                System.out.println(task7 + " Не создан, слишком длинное FOOD_NAME");
            }


            try {
                statement.execute("INSERT INTO FOOD VALUES (5, 'Малина', 'NULL', '0')");
                statement.executeUpdate("DELETE FROM FOOD WHERE FOOD_ID = 5");
                System.out.println(task8 + " Создана новая строка (5, 'Малина', 'NULL', '0'), удаление строки");
            } catch (SQLException e) {
                System.out.println(task8 + " Не создан, значение 'NULL' не допустим");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


}


