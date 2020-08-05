import java.io.File;
import java.io.IOException;
import java.nio.file.AccessDeniedException;

public interface FileManagerInterface {
    void copyFiles(File pathSource, File pathDest, boolean makeDir) throws IOException;
    void deleteFiles(File pathSource) throws IOException;
    void deleteOldBuckup(File pathSource) throws IOException;
    void recordLog(Exception ex);
}
