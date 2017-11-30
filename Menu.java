public class Menu {
    public static final String[] MENU = {
            "0.ЗАвершить работу",
            "1. Вывести таблицу",
            "2. Изменить Ячейку",
            "3. Добавить строку пустую",
            "4. Сохранить таблицу в файл",
    };
    public static void drawMenu(String[] menu) {
        for (String str : menu) {
            System.out.println(str);
        }
    }
}
