import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Excel excel = new Excel();
        excel.makeTableFromFile("src/input.csv");
        while (true) {
            Menu.drawMenu(Menu.MENU);
            switch (input.nextInt()) {
                case 0:
                    return;
                case 1:
                    excel.drawTable();
                    break;
                case 2:
                    System.out.println("Введите номер строки (Нулевая строка, это заголовок):");
                    int rowNum = input.nextInt();
                    System.out.println("Введите номер столбца (нумерация с нуля):");
                    int columnNum = input.nextInt();
                    System.out.println("Введите новое значение ячейки:");
                    String text = input.next();
                    input.nextLine();
                    excel.setCell(rowNum, columnNum, text);
                    break;
                case 3:
                    excel.addEmptyRow();
                    break;
                case 4:
                    excel.saveToFile("src/output.csv");
            }
        }
    }
}