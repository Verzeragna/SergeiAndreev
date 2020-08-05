import java.io.File;

public class CopyMethods implements CopyMethodsInterface {
    private FileManagerInterface fm = new FileManager();

    public void firstMethod(String[] args){
        File sourcePath = new File(args[1]);
        File destPath = new File(args[2]);
        try {
            if (args[3].equals("1")) {
                fm.deleteOldBuckup(destPath);
            }
            fm.copyFiles(sourcePath, destPath, true);
            fm.deleteFiles(sourcePath);
        } catch (Exception e) {
            fm.recordLog(e);
        }
    }

    public void secondMethod(String[] args){
        File sourcePath = new File(args[1]);
        File destPath = new File(args[2]);
        try{
            fm.copyFiles(sourcePath, destPath, false);
            fm.deleteFiles(sourcePath);
        }catch (Exception e) {
            fm.recordLog(e);
        }
    }
}
