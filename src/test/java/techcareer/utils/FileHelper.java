package techcareer.utils;

public class FileHelper {

    public static String getPathFromResources(String fileName) {
        String path = "";
        try {
            path = Thread.currentThread().getContextClassLoader().getResource(fileName).toURI().getPath();
        } catch (Exception e) {

        }
        return path;
    }
}
