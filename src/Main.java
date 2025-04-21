import java.util.Objects;
import java.util.Scanner;

public class Main {
    private static DictionaryService currentDictionary; // Текущий выбранный словарь
    private static String currentDictionaryName; // Название текущего словаря

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Выбор словаря в начале работы программы
        selectDictionary(scanner);

        while (true) {
            System.out.println("\nТекущий выбранный словарь: " + currentDictionaryName);
            System.out.println("Выберите действие: ");
            System.out.println("1. Просмотреть содержимое словаря");
            System.out.println("2. Удалить запись");
            System.out.println("3. Найти запись по ключу");
            System.out.println("4. Добавить запись");
            System.out.println("5. Выбрать другой словарь");
            System.out.println("6. Сохранить значения словаря");
            System.out.println("7. Выйти");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    currentDictionary.viewDictionaries();
                    break;
                case 2:
                    currentDictionary.removeEntry(scanner);
                    break;
                case 3:
                    findEntry(scanner);
                    break;
                case 4:
                    currentDictionary.addEntry(scanner);
                    break;
                case 5:
                    selectDictionary(scanner); // Выбор другого словаря
                    break;
                case 6:
                    DictionaryService dopDict;
                    if (Objects.equals(currentDictionaryName, "Язык 1 (4 латинских символа)"))
                        dopDict = new DictionaryService(false, currentDictionary.dictionaryFile);
                    else
                        dopDict = new DictionaryService(true, currentDictionary.dictionaryFile);
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

    // Метод для выбора словаря
    private static void selectDictionary(Scanner scanner) {
        System.out.println("Выберите словарь:");
        System.out.println("1. Язык 1 (4 латинских символа)");
        System.out.println("2. Язык 2 (5 цифр)");

        int dictChoice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        if (dictChoice == 1) {
            currentDictionary = new DictionaryService(true, null);
            currentDictionaryName = "Язык 1 (4 латинских символа)";
        } else if (dictChoice == 2) {
            currentDictionary = new DictionaryService(false, null);
            currentDictionaryName = "Язык 2 (5 цифр)";
        } else {
            System.out.println("Неверный выбор. По умолчанию выбран словарь языка 1.");
            currentDictionary = new DictionaryService(true, null);
            currentDictionaryName = "Язык 1 (4 латинских символа)";
        }
    }



    private static void findEntry(Scanner scanner) {
        System.out.println("Введите ключ для поиска:");
        String key = scanner.nextLine();

        String value = currentDictionary.findEntry(key);

        if (value != null) {
            System.out.println("Найденный перевод: " + value);
        } else {
            System.out.println("Запись не найдена.");
        }
    }
}
