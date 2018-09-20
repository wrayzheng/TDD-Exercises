import java.util.LinkedList;
import java.util.List;

/**
 * @author Wray Zheng
 */
public class JavaGrep {

    public static void main(String[] args) {
        List<String> flagList = new LinkedList<>();
        List<String> fileList = new LinkedList<>();
        String pattern = analyseArgs(args, flagList, fileList);
        if (fileList.isEmpty()) printUsage();
        else {
            GrepTool grepTool = new GrepTool();
            String result = grepTool.grep(pattern, flagList, fileList);
            System.out.println(result);
        }
    }

    public static String analyseArgs(String[] args, List<String> flagList, List<String> fileList) {
        String pattern = "";
        for (String arg : args) {
            if (arg.startsWith("-")) flagList.add(arg);
            else if ("".equals(pattern)) pattern = arg;
            else fileList.add(arg);
        }
        return pattern;
    }

    public static void printUsage() {
        String usage = "Usage:\n" +
                "\tjava JavaGrep [flags] <pattern> <file> ...\n" +
                "Flags:\n" +
                "\t-n Print the line numbers of each matching line.\n" +
                "\t-l Print only the names of files that contain at least one matching line.\n" +
                "\t-i Match line using a case-insensitive comparison.\n" +
                "\t-v Invert the program -- collect all lines that fail to match the pattern.\n" +
                "\t-x Only match entire lines, instead of lines that contain a match.";
        System.out.println(usage);
    }

}
