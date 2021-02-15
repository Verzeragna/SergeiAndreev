import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

public class FileManager implements FileManagerInterface {

    File newDir;
    File[] sourceFiles, destFiles;
    String jarPath;

    public FileManager(){
        jarPath = FileManager.class.getProtectionDomain().getCodeSource().getLocation().getPath();
    }

    @Override
    public void copyFiles(File pathSource, File pathDest, boolean makeDir) throws IOException, MessagingException {
        sourceFiles = pathSource.listFiles();
        if (makeDir) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMdd");
            StringBuilder newPath = new StringBuilder();
            newPath.append(pathDest).append("\\");
            String fileName = simpleDateFormat.format(new Date());
            newPath.append(fileName);
            newDir = new File(newPath.toString());
            boolean isCreated = newDir.mkdir();
            if (!isCreated) {
                throw new IOException("Can not create directory: " + newDir.getName());
            }
            copyFiles(pathSource, newDir);
            destFiles = newDir.listFiles();
        } else {
            FileUtils.copyDirectory(pathSource, pathDest);
        }
    }

    void copyFiles(File pathSource, File pathDest) throws IOException, MessagingException {
        int i;
        File[] arrayFiles = pathSource.listFiles();
        if (arrayFiles != null) {
            for (i = 0; i < arrayFiles.length; i++) {
                File fileDest = new File(pathDest.getAbsolutePath() + "\\" + arrayFiles[i].getName());
                FileUtils.copyFile(arrayFiles[i], fileDest);
                /*boolean isDeleted = arrayFiles[i].delete();
                if (!isDeleted) {
                    throw new IOException("File can not be deleted: " + arrayFiles[i].getName());
                }*/
            }
        } else {
            throw new NullPointerException("No files found!!!");
        }
    }

    public void recordCopyLog() {
        StringBuilder textMessage = new StringBuilder();
        if (destFiles.length == 0) {
            textMessage.append("Ошибка копирования (см. логи на сервере)!");
        } else {
            textMessage.append("Скопировано ").append(destFiles.length).append(" файлов из ").append(sourceFiles.length).append(".");
        }
            copyLog(textMessage.toString());
    }

    @Override
    public void deleteFiles(File pathSource) throws IOException {
        File[] arrayFiles = pathSource.listFiles();
        if (arrayFiles != null) {
            for (File myFile : arrayFiles) {
                if (myFile.isDirectory()) {
                    FileUtils.deleteDirectory(myFile);
                } else {
                    boolean isDeleted = myFile.delete();
                    if (!isDeleted) {
                        throw new IOException("File can not be deleted: " + myFile.getName());
                    }
                }
            }
        } else {
            throw new NullPointerException("No files found!!!");
        }
    }

    @Override
    public void deleteOldBuckup(File pathSource, int countDays) throws IOException {
        List<File> dirList = new ArrayList<>();
        File[] arrayFiles = pathSource.listFiles();
        if (arrayFiles != null) {
            if (arrayFiles.length > countDays) {
                for (File myFile : arrayFiles) {
                    if (myFile.isDirectory()) {
                        dirList.add(myFile);
                    }
                }
                Collections.sort(dirList);
                FileUtils.deleteDirectory(dirList.get(0));
                if (dirList.size() == 0) {
                    throw new AccessDeniedException("Unable to access directory: " + "size=" + dirList.size());
                }
            }
        } else {
            throw new NullPointerException("No files found!!!");
        }
    }

    @Override
    public void recordLog(Exception ex) {
        String logPath = new File(jarPath).getParent() + "\\log.txt";
        File log = new File(logPath);
        try {
            if (!log.exists()) {

                boolean isCreated = log.createNewFile();
                if (!isCreated) {
                    System.out.println("Can not create log file!!!");
                } else {
                    FileWriter nWriter = new FileWriter(log, true);
                    String date = getcurrentDate();
                    nWriter.write(date + " : " + ex.getMessage());
                    nWriter.append("\r\n");
                    nWriter.flush();
                }
            } else {
                FileWriter nWriter = new FileWriter(log, true);
                String date = getcurrentDate();
                nWriter.write(date + " : " + ex.getMessage());
                nWriter.append("\r\n");
                nWriter.flush();
            }
        } catch (IOException io) {
            io.printStackTrace();
            System.out.println("Can not write to log file!!!");
        }
    }

    @Override
    public void copyLog(String message) {
        String logPath = new File(jarPath).getParent() + "\\copyLog.txt";
        File log = new File(logPath);
        try{
            if (!log.exists()) {

                boolean isCreated = log.createNewFile();
                if (!isCreated) {
                    System.out.println("Can not create copyLog file!!!");
                } else {
                    FileWriter nWriter = new FileWriter(log, true);
                    String date = getcurrentDate();
                    nWriter.write(date + " : " + message);
                    nWriter.append("\r\n");
                    nWriter.flush();
                    nWriter.close();
                }
            } else {
                FileWriter nWriter = new FileWriter(log, true);
                String date = getcurrentDate();
                nWriter.write(date + " : " + message);
                nWriter.append("\r\n");
                nWriter.flush();
                nWriter.close();
            }
        }catch (IOException io) {
            io.printStackTrace();
            System.out.println("Can not write to copyLog file!!!");
        }

    }

    private String getcurrentDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yy hh:mm");
        return simpleDateFormat.format(new Date());
    }
}
