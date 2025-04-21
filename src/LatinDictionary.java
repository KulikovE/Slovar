import java.io.File;

class LatinDictionary extends DictionaryCore {
    public LatinDictionary(File file) {
        super("[a-zA-Z]{4}", file);
    }
}