import java.io.File;

public class Test {
    public static void main(String[] args) {
        FileManagerInterface fm = new FileManager();
        File sourcePath = new File("C:\\Users\\1\\Documents\\123");
        File destPath = new File("C:\\Users\\1\\Documents\\Backup95");
        try {
            fm.copyFiles(sourcePath, destPath);
            fm.deleteFiles(sourcePath);
            if (args[2].equals("1")) {
                fm.deleteOldBuckup(new File(args[1]));
            }
        } catch (Exception e) {
            fm.recordLog(e);
        }
    }
}
