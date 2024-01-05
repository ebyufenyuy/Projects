//Ellison Yufenyuy
//Implementing a model of a computer's file system

import java.util.ArrayList; //Imports 
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

class File { //Class name 
    private String name;
    private StringBuilder content; //Mutuable/changeable class to change content in the file

    public File(String name) { //Constructor for creating/making a file with a given name
        this.name = name;
        this.content = new StringBuilder(); //Initialize content as an empty StringBuilder

    }

    public String getName() { //Method to retrieve name of the file
        return name;
    }

    public String getContent() { //Getter Method to get/retrieve the content of the file
        return content.toString();
    }

    public void appendContent(String data) {
        content.append(data);
    }
}

class Directory { //Class name 
    private String name;
    private List<File> files;
    private List<Directory> subdirectories;
   
    public Directory(String name) {
        this.name = name;
        this.files = new ArrayList<>(); //Initialize the list of files and subdirectories (via ArrayList)
        this.subdirectories = new ArrayList<>();
    }

    public String getName() {
        return name; //Return name of file 
    }

    public List<File> getFiles() {
        return Collections.unmodifiableList(files);
    }

    public List<Directory> getCurrSubdirectories() {
        return Collections.unmodifiableList(subdirectories);


    }

    public void addFile(File file) {
        files.add(file); //Add the file to the list of files in the directory.
    }

    public void addSubdirectory(Directory directory) {
        subdirectories.add(directory);
    }
}

public class FileSys {
    private Directory rootDirectorySys;
    private Directory currentUseDirectory;
    
    public FileSys() {
        this.rootDirectorySys = new Directory("/");
        this.currentUseDirectory = rootDirectorySys;
    }

    public void create(String fileName) {
        if (fileOrDirectoryExists(fileName)) {
            System.out.println("ERROR: File or directory is already existing"); //Error Message 


        } else {

            File file = new File(fileName);

            System.out.println("Enter content for file (end with ~):"); //Call for user to enter content 

            String content = readContent();

            file.appendContent(content);

            currentUseDirectory.addFile(file);
            
        }
    }

    public void cat(String fileName) {
        File file = findFile(fileName);
        if (file != null) {
            System.out.println(file.getContent());
        } else {
            System.out.println("ERROR: File is not found"); //Error message
        }
    }

    public void rm(String fileName) {
        File file = findFile(fileName);
        if (file != null) {
            currentUseDirectory.getFiles().remove(file); //Remove file from the list of files in the directory
    } else {
        
            System.out.println("ERROR: File is not found"); //Error message 
        }
    }

    public void mkdir(String dirName) {
        if (fileOrDirectoryExists(dirName)) {
            System.out.println("ERROR: File or directory is already existing");
        } else {
            Directory directory = new Directory(dirName); //Create new directory with name
            currentUseDirectory.addSubdirectory(directory);
        }
    }

    public void rmdir(String dirName) {

        Directory directory = findDirectory(dirName);

        if (directory != null) {
            currentUseDirectory.getCurrSubdirectories().remove(directory);
        } else {
            System.out.println("ERROR: Directory is not found"); //Error message
        }
    }

    
    public void cd(String dirName) {
        if (dirName.equals("/")) {
            currentUseDirectory = rootDirectorySys;
        } else if (dirName.equals("..")) {
            if (currentUseDirectory != rootDirectorySys) {
                currentUseDirectory = getParentDirectory(currentUseDirectory);


            }
        } else {


            Directory newDir = findDirectory(dirName);
            if (newDir != null) {
                currentUseDirectory = newDir;
            } else {
                System.out.println("ERROR: Directory is not found"); //Error message 
            }
        }
    }
    
    public void ls() {
        List<String> items = new ArrayList<>(); //Create a list to store the names of files and subdirectories
        for (Directory dir : currentUseDirectory.getCurrSubdirectories()) {
            items.add(dir.getName() + "(Directory)");
        }
        for (File file : currentUseDirectory.getFiles()) {
            items.add(file.getName());
        }
        Collections.sort(items); //Put the list in alphabeticall order (Like how other Computer systems do/order)
        for (String item : items) {
            System.out.println(item); //Print the sorted list of items
        }
    }
    
    public void du() {

        long totalSize = calculateTotalSize(currentUseDirectory);


        System.out.println(totalSize + " bytes"); //Print total size within
    }
    
    public void pwd() {
        System.out.println(getFullPath(currentUseDirectory)); //Print the entire path of the current working directory
    }

    public void find(String name) {

        findAndPrintPaths(currentUseDirectory, name);
    }

    public void exit() {


        System.out.println("Exiting Program"); //Print message showing that the program is exiting
        System.exit(0);
    }

    
    private String readContent() { //Method to read content until ~ 
        StringBuilder content = new StringBuilder();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter a line (end with ~): ");
            String line = scanner.nextLine();
            if (line.contains("~")) {
                content.append(line, 0, line.indexOf("~"));
                break;
            } else {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }
    
    

    
    private boolean fileOrDirectoryExists(String name) { //Helper method to check if a file or directory exists or is in the current directory
        return findFile(name) != null || findDirectory(name) != null;
    }

    
    private File findFile(String fileName) { //Helper method to find a file by name in the current directory
        for (File file : currentUseDirectory.getFiles()) {
            if (file.getName().equals(fileName)) {
                return file;
            }
        }
        return null;
    }

    
    private Directory findDirectory(String dirName) { //Helper method to find a directory by name in the current directory
        for (Directory directory : currentUseDirectory.getCurrSubdirectories()) {
            if (directory.getName().equals(dirName)) {
                return directory;
            }
        }
        return null;
    }

    // ... (implement other helper methods and commands)
    private Directory getParentDirectory(Directory directory) {
        for (Directory dir : currentUseDirectory.getCurrSubdirectories()) {
            if (dir.getCurrSubdirectories().contains(directory)) {
                return dir;
            }
        }
        return null;  //Return null if the parent directory is not found 
    }

    
 

    private long calculateTotalSize(Directory directory) {
        long totalSize = 0;  //Initialize the total size
        
        for (File file : directory.getFiles()) {


            totalSize += file.getContent().getBytes().length;
        }

        for (Directory subdirectory : directory.getCurrSubdirectories()) { //Using recursion to calculate the total size of files in subdirectories
            totalSize += calculateTotalSize(subdirectory);
        }

        return totalSize; //Return the total size 
    }

 

 
 private void findAndPrintPaths(Directory directory, String name) { //Helper method to find and print paths of files or directories with the given name
     findAndPrintPathsRecursive(directory, name, new StringBuilder());
 }

 private void findAndPrintPathsRecursive(Directory directory, String name, StringBuilder currentPath) {

     for (File file : directory.getFiles()) {
         if (file.getName().equals(name)) {

             System.out.println(currentPath.toString() + file.getName());
         }
     }

     for (Directory subdirectory : directory.getCurrSubdirectories()) {

         StringBuilder newPath = new StringBuilder(currentPath);

         newPath.append(directory.getName()).append("/");
         findAndPrintPathsRecursive(subdirectory, name, newPath);
     }
 }

 
 private String getFullPath(Directory directory) { //Method to get the full path of the directory
     StringBuilder fullPath = new StringBuilder("/");
     getFullPathRecursive(rootDirectorySys, directory, fullPath);
     return fullPath.toString();
 }

 private boolean getFullPathRecursive(Directory current, Directory target, StringBuilder path) {
     if (current == target) {
         return true;
     }

     for (Directory subdirectory : current.getCurrSubdirectories()) {
         path.append(subdirectory.getName()).append("/");
         if (getFullPathRecursive(subdirectory, target, path)) {
             return true;
         }
         path.delete(path.length() - subdirectory.getName().length() - 1, path.length());
     }

     return false;
 }

    public static void main(String[] args) {
        FileSys fileSystem = new FileSys();
        fileSystem.run();
    }

    public void run() {
    	Scanner scanner = new Scanner(System.in);
        String userInput;

        while (true) {
            System.out.print("Enter a command: ");
            userInput = scanner.nextLine();

            
            String[] commandParts = userInput.split(" "); //Parse and execute the command
            String command = commandParts[0].toLowerCase(); //Convert command to lowercase for cases

            if ("create".equals(command)) {
                if (commandParts.length > 1) {
                    create(commandParts[1]); //Execute "create"command with user input 
                } else {
                    System.out.println("ERROR: Need file or directory name for 'create' command");
                }
            } else if ("cat".equals(command)) {
                if (commandParts.length > 1) {
                    cat(commandParts[1]); //Execute "cat"command with user input
                } else {
                    System.out.println("ERROR: Need the file name for 'cat' command");
                }
            } else if ("rm".equals(command)) {
                if (commandParts.length > 1) {
                    rm(commandParts[1]); //Execute "rm"command with user input
                } else {
                    System.out.println("ERROR: Missing file or directory name for 'rm' command");
                }
            } else if ("mkdir".equals(command)) {
                if (commandParts.length > 1) {
                    mkdir(commandParts[1]); 
                } else {
                    System.out.println("ERROR: Missing the directory name for 'mkdir' command");
                }
            } else if ("rmdir".equals(command)) {
                if (commandParts.length > 1) {
                    rmdir(commandParts[1]);
                } else {
                    System.out.println("ERROR: Missing the directory name for 'rmdir' command");
                }
            } else if ("cd".equals(command)) {
                if (commandParts.length > 1) {
                    cd(commandParts[1]); //Execute "cd"command with user input
                } else {2
                    System.out.println("ERROR: Missing the directory name for 'cd' command");
                }
            } else if ("ls".equals(command)) {
                ls();
            } else if ("du".equals(command)) {
                du();
            } else if ("pwd".equals(command)) {
                pwd();
            } else if ("find".equals(command)) {
                if (commandParts.length > 1) {
                    
                    find(commandParts[1]);
                } else {
                    System.out.println("ERROR: Missing the directory name for 'find' command");
                }
            } else if ("exit".equals(command)) {
            	 
                exit(); //Exit program
                
            } else {
                System.out.println("ERROR: Command not known/Unknown"); //Print error 
            }
        }
    }
    
    
}