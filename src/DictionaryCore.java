import java.io.*;
import java.util.HashMap;
import java.util.Map;

// Ядро словаря

class DictionaryCore implements IDictionary {
    protected Map<String, String> dictionary;
    protected String regex;
    protected File file;

    public DictionaryCore(String regex, File file) {
        this.dictionary = new HashMap<>();
        this.regex = regex;
        this.file = file;
        loadDictionary();
    }

    @Override
    public void addEntry(String key, String value) {
        if (key.matches(regex)) {
            dictionary.put(key, value);
            System.out.println("Запись добавлена.");
        } else {
            System.out.println("Ошибка: Ключ не соответствует формату.");
        }
    }

    @Override
    public void removeEntry(String key) {
        dictionary.remove(key);
        System.out.println("Запись удалена (если существовала).");
    }

    @Override
    public String findEntry(String key) {
        return dictionary.get(key);
    }

    @Override
    public void viewDictionaries() {
        dictionary.forEach((k, v) -> System.out.println(k + " - " + v));
    }

    @Override
    public void loadDictionary() {
        try {
            if (file.createNewFile()) {
                System.out.println("Файл словаря создан");
            } else {
                FileReader fr = new FileReader(file);
                BufferedReader reader = new BufferedReader(fr);
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(" - ");
                    if (parts.length == 2 && parts[0].matches(regex)) {
                        dictionary.put(parts[0], parts[1]);
                    }
                }
                reader.close();
            }
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла.");
        }
    }

    @Override
    public void saveDictionary(boolean append) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, append))) {
            for (Map.Entry<String, String> entry : dictionary.entrySet()) {
                writer.write(entry.getKey() + " - " + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Ошибка сохранения файла.");
        }
    }
}