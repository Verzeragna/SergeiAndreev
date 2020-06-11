import java.io.File;
import java.io.IOException;
import java.nio.file.AccessDeniedException;

public interface FileManagerInterface {
    void copyFiles(File pathSource, File pathDest) throws IOException;
    void deleteFiles(File pathSource) throws IOException;
    void deleteOldBuckup(File pathSource) throws AccessDeniedException;
    void recordLog(Exception ex);
}
