import java.io.File;

class DigitDictionary extends DictionaryCore {
    public DigitDictionary(File file) {
        super("\\d{5}", file);
    }
}