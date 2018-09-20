class RnaTranscription {

    String transcribe(String dnaStrand) {
        char[] dnaSeq = dnaStrand.toCharArray();
        for (int i = 0; i < dnaSeq.length; i++) {
            dnaSeq[i] = transcript(dnaSeq[i]);
        }
        return String.valueOf(dnaSeq);
    }

    char transcript(char c) {
        switch(c) {
            case 'C': return 'G';
            case 'G': return 'C';
            case 'T': return 'A';
            case 'A': return 'U';
        }
        throw new IllegalArgumentException("Invalid input");
    }

}
