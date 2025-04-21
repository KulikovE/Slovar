import java.io.*;
import java.util.*;

public class DictionaryService {
    public File dictionaryFile;
    private Map<String, String> dictionary;
    public boolean firstDict = true;

    public DictionaryService(boolean frstD, File file) {
        firstDict = frstD;
        if(file == null) {
            setFilepath();
        }else{
            dictionaryFile = file;
        }
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

    public void saveDictionary(boolean f) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dictionaryFile, f))) {
            for (Map.Entry<String, String> entry : dictionary.entrySet()) {
                writer.write(entry.getKey() + " - " + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
        }
    }

    private void addEntry1(String key, String value) {
        dictionary.put(key, value);
    }

    public void removeEntry1(String key) {
        dictionary.remove(key);
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
