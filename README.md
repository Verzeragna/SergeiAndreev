# Backup app
This application starts with three parameters: 1) Copy source (path). This is the directory that contains the files to copy; 2) Destination directory; 3) The third parameter can be either 1 or 0. A value of 1 means that the application will automatically delete the oldest directory in the destination directory.
The application creates a new directory in the destination directory with the current date, for example 200611, where 20 is the year, 06 is the month, 11 is the day.

File name                       | File content 
--------------------------------|--------------------------------------------------------------------------------------------------------
DoBackup.java                   | Main class.
FileManager.java                | Class doing all the work.
FileManagerInterface.java       | Interface for working with FileManager class.
Test.java                       | Class for local tests.
