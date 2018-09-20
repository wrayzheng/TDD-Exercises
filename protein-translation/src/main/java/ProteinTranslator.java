import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

class ProteinTranslator {

    private static final String STOP = "STOP";
    private Map<String, String> dict = new HashMap<>();
    private String rnaSequence;
    private int curPos;

    {
        addEntry("AUG", "Methionine");
        addEntry("UUU, UUC", "Phenylalanine");
        addEntry("UUA, UUG", "Leucine");
        addEntry("UCU, UCC, UCA, UCG", "Serine");
        addEntry("UAU, UAC", "Tyrosine");
        addEntry("UGU, UGC", "Cysteine");
        addEntry("UGG", "Tryptophan");
        addEntry("UAA, UAG, UGA", STOP);
    }

    void addEntry(String keys, String value) {
        String[] ks = keys.split(",\\s*");
        for (String key : ks)
            dict.put(key, value);
    }

    List<String> translate(String rnaSequence) {
        this.rnaSequence = rnaSequence;
        this.curPos = 0;
        boolean stop = false;
        List<String> ret = new LinkedList<>();

        while (hasNextProtein() && !stop) {
            String protein = nextProtein();
            if (STOP.equals(protein)) stop = true;
            else ret.add(protein);
        }

        return ret;
    }

    boolean hasNextProtein() {
        return curPos <= rnaSequence.length() - 3;
    }

    String nextProtein() {
        String codon = nextCodon();
        return codonToProtein(codon);
    }

    String nextCodon() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++)
            sb.append(rnaSequence.charAt(curPos++));
        return sb.toString();
    }

    String codonToProtein(String codon) {
        String protein = dict.get(codon);
        if (protein == null)
            throw new IllegalArgumentException("Illegal codon");
        return protein;
    }
}