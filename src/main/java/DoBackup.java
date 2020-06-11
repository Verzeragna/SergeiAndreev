import java.io.File;

public class DoBackup {
    public static void main(String[] args) {
        FileManagerInterface fm = new FileManager();
        File sourcePath = new File(args[0]);
        File destPath = new File(args[1]);
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
