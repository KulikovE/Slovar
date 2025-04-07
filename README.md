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
            System.out.println("Выберите действие:");
            System.out.println("1. Просмотреть содержимое словаря");
            System.out.println("2. Удалить запись");
            System.out.println("3. Найти запись по ключу");
            System.out.println("4. Добавить запись");
            System.out.println("5. Выбрать другой словарь");
            System.out.println("6. Выйти");

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
            currentDictionary = new DictionaryService(true);
            currentDictionaryName = "Язык 1 (4 латинских символа)";
        } else if (dictChoice == 2) {
            currentDictionary = new DictionaryService(false);
            currentDictionaryName = "Язык 2 (5 цифр)";
        } else {
            System.out.println("Неверный выбор. По умолчанию выбран словарь языка 1.");
            currentDictionary = new DictionaryService(true);
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




















import java.io.*;
import java.util.*;

public class DictionaryService {
    private File dictionaryFile;
    private Map<String, String> dictionary;
    public boolean firstDict = true;

    public DictionaryService(boolean frstD) {
        firstDict = frstD;
        setFilepath();
        this.dictionary = new HashMap<>();
        loadDictionary();
    }

    public void setFilepath(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите путь к файлу словаря: ");
        String filePath = scanner.nextLine();
        dictionaryFile = new File(filePath);
    }

    private void loadDictionary() {
        try {
            if(dictionaryFile.createNewFile())
                System.out.println("Файл словаря создан");
            else{
                FileReader fr = new FileReader(dictionaryFile);
                BufferedReader reader = new BufferedReader(fr);
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(" - ");
                    if (parts.length == 2) {
                        if(firstDict) {
                            if(parts[0].matches("[a-zA-Z]{4}")) {
                                dictionary.put(parts[0], parts[1]);
                            }
                        }
                        else{
                            if(parts[0].matches("\\d{5}")) {
                                dictionary.put(parts[0], parts[1]);
                            }
                        }
                    }
                }
                reader.close();
            }
        } catch (IOException e) {
        }
    }

    private void saveDictionary() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dictionaryFile))) {
            for (Map.Entry<String, String> entry : dictionary.entrySet()) {
                writer.write(entry.getKey() + " - " + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
        }
    }

    private void addEntry1(String key, String value) {
        dictionary.put(key, value);
        saveDictionary();
    }

    public void removeEntry1(String key) {
        dictionary.remove(key);
        saveDictionary();
    }

    public String findEntry(String key) {
        return dictionary.get(key);
    }

    public Map<String, String> getDictionary() {
        return new HashMap<>(dictionary);
    }

    public void removeEntry(Scanner scanner) {
        System.out.println("Введите ключ для удаления:");
        String key = scanner.nextLine();

        removeEntry1(key);
        System.out.println("Запись удалена (если существовала).");
    }


    public void viewDictionaries() {
        System.out.println("Словарь языка 1 (4 латинских символа):");
        getDictionary().forEach((k, v) -> System.out.println(k + " - " + v));
    }


    public void addEntry(Scanner scanner) {
        System.out.println("Введите ключ:");
        String key = scanner.nextLine();

        System.out.println("Введите значение:");
        String value = scanner.nextLine();

        if (firstDict) {
            if (key.matches("[a-zA-Z]{4}")) {
                addEntry1(key, value);
                System.out.println("Запись добавлена.");
            } else {
                System.out.println("Ошибка: Ключ должен состоять из 4 латинских букв.");
            }
        } else {
            if (key.matches("\\d{5}")) {
                addEntry1(key, value);
                System.out.println("Запись добавлена.");
            } else {
                System.out.println("Ошибка: Ключ должен состоять из 5 цифр.");
            }
        }
    }
}
