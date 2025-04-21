import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// Мейн класс
public class Main {
    private static IDictionary currentDictionary;
    private static String currentDictionaryName;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Выберите словарь:");
        System.out.println("1. Словарь с 4 латинскими буквами");
        System.out.println("2. Словарь с 5 цифрами");

        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        System.out.println("Введите путь к файлу словаря:");
        String filename = scanner.nextLine();
        File file = new File(filename);

        if (choice == 1) {
            currentDictionary = new LatinDictionary(file);
            currentDictionaryName = "Словарь с 4 латинскими буквами";
        } else if (choice == 2) {
            currentDictionary = new DigitDictionary(file);
            currentDictionaryName = "Словарь с 5 цифрами";
        } else {
            System.out.println("Неверный выбор. По умолчанию выбран словарь с 4 латинскими буквами.");
            currentDictionary = new LatinDictionary(file);
            currentDictionaryName = "Словарь с 4 латинскими буквами";
        }

        while (true) {
            System.out.println("\nТекущий выбранный словарь: " + currentDictionaryName);
            System.out.println("Выберите действие:");
            System.out.println("1. Просмотреть содержимое словаря");
            System.out.println("2. Удалить запись");
            System.out.println("3. Найти запись по ключу");
            System.out.println("4. Добавить запись");
            System.out.println("5. Выбрать другой словарь");
            System.out.println("6. Сохранить значения словаря");
            System.out.println("7. Выйти");

            int action = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (action) {
                case 1:
                    currentDictionary.viewDictionaries();
                    break;
                case 2:
                    System.out.println("Введите ключ для удаления:");
                    String key = scanner.nextLine();
                    currentDictionary.removeEntry(key);
                    break;
                case 3:
                    System.out.println("Введите ключ для поиска:");
                    key = scanner.nextLine();
                    String value = currentDictionary.findEntry(key);
                    if (value != null) {
                        System.out.println("Найденный перевод: " + value);
                    } else {
                        System.out.println("Запись не найдена.");
                    }
                    break;
                case 4:
                    System.out.println("Введите ключ:");
                    key = scanner.nextLine();
                    System.out.println("Введите значение:");
                    value = scanner.nextLine();
                    currentDictionary.addEntry(key, value);
                    break;
                case 5:
                    System.out.println("Выберите словарь:");
                    System.out.println("1. Словарь с 4 латинскими буквами");
                    System.out.println("2. Словарь с 5 цифрами");
                    choice = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    if (choice == 1) {
                        currentDictionary = new LatinDictionary(file);
                        currentDictionaryName = "Словарь с 4 латинскими буквами";
                    } else if (choice == 2) {
                        currentDictionary = new DigitDictionary(file);
                        currentDictionaryName = "Словарь с 5 цифрами";
                    } else {
                        System.out.println("Неверный выбор. По умолчанию выбран словарь с 4 латинскими буквами.");
                        currentDictionary = new LatinDictionary(file);
                        currentDictionaryName = "Словарь с 4 латинскими буквами";
                    }
                    break;
                case 6:
                    IDictionary dopDict;
                    if (currentDictionaryName.equals("Словарь с 4 латинскими буквами")) {
                        dopDict = new DigitDictionary(file);
                    } else {
                        dopDict = new LatinDictionary(file);
                    }
                    currentDictionary.saveDictionary(false);
                    dopDict.saveDictionary(true);
                    break;

                case 7:
                    System.exit(0);
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }
}
