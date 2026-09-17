import java.io.IOException;
import java.lang.Thread;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Main {

    private static final String PATH_TO_SCANS_DIRECTORY = "C:\\Users\\cmouy\\Pictures\\Scans";
    private static final String DIRECTORY_TO_MOVE_NAME = "dir";

    public static void main(String[] args) throws InterruptedException, IOException {
        System.out.println("Starte scans-organizer\n");

        boolean running = false;

        while(running) {
            List<String> files = listFilePaths(PATH_TO_SCANS_DIRECTORY);
            //so polling, auch interessant: java.nio.file.WatchService
            Thread.sleep(5000);
        }
        List<String> files = listFilePaths(PATH_TO_SCANS_DIRECTORY);
        Llm llm = new Llm(files);
    }

    public static String[] listFileNames(String dir) throws IOException {
        try (Stream<Path> stream = Files.list(Paths.get(dir))) {
            return stream
                    .filter(file -> !Files.isDirectory(file))
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .toArray(String[]::new);
        }
    }

    public static List<String> listFilePaths(String dir) throws IOException {
        try (Stream<Path> stream = Files.list(Paths.get(dir))) {
            return stream
                    .filter(file -> !Files.isDirectory(file))
                    .map(Path::toString)
                    .toList();
        }
    }

    public static void moveFile(String fileName, String directoryToMoveTo) throws IOException {
        Path sourcePath = Paths.get(PATH_TO_SCANS_DIRECTORY, fileName);
        Path targetPath = Paths.get(PATH_TO_SCANS_DIRECTORY, "\\" + directoryToMoveTo + "\\" + fileName);
        try {
            Files.move(sourcePath, targetPath);
        } catch (IOException e) {
            System.err.println("Die Datei konnte nicht verschoben werden, da der Name \"" + fileName +"\" im Zielordner \"" + directoryToMoveTo + "\" bereits existiert!");
            System.exit(1);
        }
    }
}
