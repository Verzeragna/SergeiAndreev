import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.file.AccessDeniedException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class FileManager implements FileManagerInterface {

    @Override
    public void copyFiles(File pathSource, File pathDest) throws IOException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMdd");
        StringBuilder newPath = new StringBuilder();
        newPath.append(pathDest).append("\\");
        String fileName = simpleDateFormat.format(new Date());
        newPath.append(fileName);
        File newDir = new File(newPath.toString());
        newDir.mkdir();
        FileUtils.copyDirectory(pathSource, newDir);
    }

    @Override
    public void deleteFiles(File pathSource) throws IOException {
        for (File myFile : pathSource.listFiles()) {
            if (myFile.isDirectory()) {
                FileUtils.deleteDirectory(myFile);
            } else {
                myFile.delete();
            }
        }
    }

    @Override
    public void deleteOldBuckup(File pathSource) throws AccessDeniedException {
        List<File> dirList = new ArrayList<>();
        if(pathSource.listFiles().length>2) {
            for (File myFile : pathSource.listFiles()) {
                if (myFile.isDirectory()) {
                    dirList.add(myFile);
                }
            }
            Collections.sort(dirList);
            boolean status = dirList.get(0).delete();
            if (dirList.size() == 0 || !status) {
                throw new AccessDeniedException("Unable to access directory");
            }
        }
    }

    @Override
    public void recordLog(Exception ex) {
        String logPath = new File("").getAbsolutePath() + "\\log.txt";
        File log = new File(logPath);
        try {
            if (!log.exists()) {

                log.createNewFile();
                FileWriter nWriter = new FileWriter(log, true);
                String date = getcurrentDate();
                nWriter.write(date + " : " + ex.getMessage());
                nWriter.append("\r\n");
                nWriter.flush();

            } else {
                FileWriter nWriter = new FileWriter(log, true);
                String date = getcurrentDate();
                nWriter.write(date + " : " + ex.getMessage());
                nWriter.append("\r\n");
                nWriter.flush();
            }
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    private String getcurrentDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yy hh:mm");
        return simpleDateFormat.format(new Date());
    }
}
