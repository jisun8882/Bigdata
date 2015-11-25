package common;
 
import org.apache.hadoop.util.ProgramDriver;
 
public class Driver {
 
    /**
     * @param args
     */
    public static void main(String[] args) {
        int exitCode= -1;
        ProgramDriver pgd = new ProgramDriver();
        try {
            pgd.addClass("wordcount", lab01.WordCount.class," A map/recude program that counts the work");
            pgd.addClass("BigramCount", lab01.BigramCount.class, " A map/reduce program with bigram that counts the work");
            pgd.driver(args);
            exitCode=0;
        }
        catch(Throwable e) {
            e.printStackTrace();
        }
        System.exit(exitCode);
    }
}