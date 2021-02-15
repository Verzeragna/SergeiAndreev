import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;

import java.io.File;
import java.io.IOException;
import java.nio.file.AccessDeniedException;

public interface FileManagerInterface {
    void copyFiles(File pathSource, File pathDest, boolean makeDir) throws IOException, MessagingException, MessagingException;
    void recordCopyLog();
    void deleteFiles(File pathSource) throws IOException;
    void deleteOldBuckup(File pathSource, int countDays) throws IOException;
    void recordLog(Exception ex);
    void copyLog(String message);
}
