import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Wray Zheng
 */
public class GrepTool {

    private boolean showLineNum;
    private boolean caseInsensitive;
    private boolean printFileNameOnly;
    private boolean matchEntireLine;
    private boolean inverted;
    private boolean multipleFilesMode;

    /**
     * A simple grep utility
     * @param pattern pattern to search for
     * @param flagList <li>-n Print the line numbers of each matching line.</li>
     *                  <li>-l Print only the names of files that contain at least one matching line.</li>
     *                  <li>-i Match line using a case-insensitive comparison.</li>
     *                  <li>-v Invert the program -- collect all lines that fail to match the pattern.</li>
     *                  <li>-x Only match entire lines, instead of lines that contain a match.</li>
     * @param fileList files to search through
     * @return
     */
    public String grep(String pattern, List<String> flagList, List<String> fileList) {
        processFlags(flagList);
        return grepOnMultipleFiles(pattern, fileList);
    }

    public void processFlags(List<String> flagList) {
        showLineNum = flagList.contains("-n");
        caseInsensitive = flagList.contains("-i");
        printFileNameOnly = flagList.contains("-l");
        matchEntireLine = flagList.contains("-x");
        inverted = flagList.contains("-v");
    }

    public String grepOnMultipleFiles(String pattern, List<String> fileList) {
        if (fileList.size() > 1) multipleFilesMode = true;
        StringBuilder sb = new StringBuilder();
        for (String file : fileList) {
            String result = grepOnSingleFile(pattern, file);
            sb.append(result);
        }
        if (sb.length() != 0) trimLastCarriageReturn(sb);
        return sb.toString();
    }

    public String grepOnSingleFile(String pattern, String file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            return grepOnSingleFile(pattern, file, reader);
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
        return "";
    }

    public void trimLastCarriageReturn(StringBuilder sb) {
        sb.setLength(sb.length() - 1);
    }

    public String grepOnSingleFile(String pattern, String file, BufferedReader reader) throws IOException {
        Pattern ptn = compilePattern(pattern);
        StringBuilder sb = new StringBuilder();
        String line = reader.readLine();
        for (int lineNum = 1; line != null; lineNum++) {
            if (match(ptn, line)) {
                if (printFileNameOnly) return appendCarriageReturn(file);
                String formatted = formattedLine(file, lineNum, line);
                sb.append(appendCarriageReturn(formatted));
            }
            line = reader.readLine();
        }
        return sb.toString();
    }

    public Pattern compilePattern(String pattern) {
        int flag = Pattern.LITERAL | (caseInsensitive ? Pattern.CASE_INSENSITIVE : 0);
        return Pattern.compile(pattern, flag);
    }

    public boolean match(Pattern pattern, String toMatch) {
        Matcher matcher = pattern.matcher(toMatch);
        boolean ret = matchEntireLine ? matcher.matches() : matcher.find();
        return inverted ? !ret : ret;
    }

    public String formattedLine(String file, int lineNum, String line) {
        StringBuilder sb = new StringBuilder();
        if (multipleFilesMode) {
            sb.append(file);
            sb.append(":");
        }
        if (showLineNum) {
            sb.append(lineNum);
            sb.append(":");
        }
        sb.append(line);
        return sb.toString();
    }

    public String appendCarriageReturn(String original) {
        return original + "\n";
    }

}
