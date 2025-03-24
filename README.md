import java.io.*;
import java.util.*;

public class DictionaryService {
    private File dictionaryFile;
    private Map<String, String> dictionary;

    public DictionaryService(String filePath) {
        this.dictionaryFile = new File(filePath);
        this.dictionary = new HashMap<>();
        loadDictionary();
    }

    private void loadDictionary() {
        try (BufferedReader reader = new BufferedReader(new FileReader(dictionaryFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" - ");
                if (parts.length == 2) {
                    dictionary.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveDictionary() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dictionaryFile))) {
            for (Map.Entry<String, String> entry : dictionary.entrySet()) {
                writer.write(entry.getKey() + " - " + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addEntry(String key, String value) {
        dictionary.put(key, value);
        saveDictionary();
    }

    public void removeEntry(String key) {
        dictionary.remove(key);
        saveDictionary();
    }

    public String findEntry(String key) {
        return dictionary.get(key);
    }

    public Map<String, String> getDictionary() {
        return new HashMap<>(dictionary);
    }
}













import java.util.Scanner;

public class DictionaryApp {
    private static DictionaryService language1Dictionary;
    private static DictionaryService language2Dictionary;
    private static DictionaryService currentDictionary; // Текущий выбранный словарь
    private static String currentDictionaryName; // Название текущего словаря

    public static void main(String[] args) {
        language1Dictionary = new DictionaryService("language1.txt");
        language2Dictionary = new DictionaryService("language2.txt");

        Scanner scanner = new Scanner(System.in);

        // Выбор словаря в начале работы программы
        selectDictionary(scanner);

        while (true) {
            System.out.println("\nТекущий выбранный словарь: " + currentDictionaryName);
            System.out.println("Выберите действие:");
            System.out.println("1. Просмотреть содержимое словарей");
            System.out.println("2. Удалить запись");
            System.out.println("3. Найти запись по ключу");
            System.out.println("4. Добавить запись");
            System.out.println("5. Выбрать другой словарь");
            System.out.println("6. Выйти");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    viewDictionaries();
                    break;
                case 2:
                    removeEntry(scanner);
                    break;
                case 3:
                    findEntry(scanner);
                    break;
                case 4:
                    addEntry(scanner);
                    break;
                case 5:
                    selectDictionary(scanner); // Выбор другого словаря
                    break;
                case 6:
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
            currentDictionary = language1Dictionary;
            currentDictionaryName = "Язык 1 (4 латинских символа)";
        } else if (dictChoice == 2) {
            currentDictionary = language2Dictionary;
            currentDictionaryName = "Язык 2 (5 цифр)";
        } else {
            System.out.println("Неверный выбор. По умолчанию выбран словарь языка 1.");
            currentDictionary = language1Dictionary;
            currentDictionaryName = "Язык 1 (4 латинских символа)";
        }
    }

    private static void viewDictionaries() {
        System.out.println("Словарь языка 1 (4 латинских символа):");
        language1Dictionary.getDictionary().forEach((k, v) -> System.out.println(k + " - " + v));

        System.out.println("\nСловарь языка 2 (5 цифр):");
        language2Dictionary.getDictionary().forEach((k, v) -> System.out.println(k + " - " + v));
    }

    private static void removeEntry(Scanner scanner) {
        System.out.println("Введите ключ для удаления:");
        String key = scanner.nextLine();

        currentDictionary.removeEntry(key);
        System.out.println("Запись удалена (если существовала).");
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

    private static void addEntry(Scanner scanner) {
        System.out.println("Введите ключ:");
        String key = scanner.nextLine();

        System.out.println("Введите значение:");
        String value = scanner.nextLine();

        if (currentDictionary == language1Dictionary) {
            if (key.matches("[a-zA-Z]{4}")) {
                currentDictionary.addEntry(key, value);
                System.out.println("Запись добавлена.");
            } else {
                System.out.println("Ошибка: Ключ должен состоять из 4 латинских букв.");
            }
        } else if (currentDictionary == language2Dictionary) {
            if (key.matches("\\d{5}")) {
                currentDictionary.addEntry(key, value);
                System.out.println("Запись добавлена.");
            } else {
                System.out.println("Ошибка: Ключ должен состоять из 5 цифр.");
            }
        }
    }
}
