// Интерфейс словаря
interface IDictionary {
    void addEntry(String key, String value);
    void removeEntry(String key);
    String findEntry(String key);
    void viewDictionaries();
    void loadDictionary();
    void saveDictionary(boolean append);
}