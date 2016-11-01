package experiments.streams;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Created by dpivovar on 13.10.2016.
 */
public class FileStreams {
    public static void main(String[] args) {
        long uniqueWords = 0;

        try (Stream<String> lines = Files.lines(Paths.get("F:\\Temp\\log.txt"), Charset.defaultCharset())) {
            uniqueWords = lines.flatMap(line -> Arrays.stream(line.split(" "))).distinct().count();
            System.out.println("unique words: " + uniqueWords);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
