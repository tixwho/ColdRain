package com.coldrain.toolkit;

import java.io.File;

public class DirMaker {

    // Todo: remove filename to get its directory, and make a new "PlaylistOutput" directory in this
    // directory if no previous one exists.
    // Return is NOT a dir, but the actual filename// Or a dir if input is single
    
    //Overloading
    //Input1: Input Playlist Address
    //Input2: Output Playlist suffix
    //Return: Output Playlist full address
    
    //TODO make mkDirIfNotExist a void return. Integreate getParent() into main program to avoid conusing.
    public static String mkDirIfNotExist(String filePath, String newSuffix) {
        String newDirName = "outputList";
        File file = new File(filePath);
        String parentPath = file.getParent();
        File directory = new File(String.valueOf(parentPath),newDirName);
        if(!directory.exists()){
            directory.mkdir();
        }
        String nameWithNewSuffix = file.getName().replaceFirst("[.][^.]+$", "").concat(newSuffix);
        File returnFile = new File(directory, nameWithNewSuffix);
        return String.valueOf(returnFile);

    }
    
    //Overloading
    //Input1: Input Playlist Address
    //Return: Output Directory Full Address
    public static String mkDirIfNotExist(String filePath) {
        String newDirName = "outputList";
        File file = new File(filePath);
        String parentPath = file.getParent();
        File directory = new File(String.valueOf(parentPath),newDirName);
        if(!directory.exists()){
            directory.mkdir();
        }
        return String.valueOf(directory);
    }
    
    public static boolean mkRawDirIfNotExist(String filePath) {
        File directory = new File(filePath);
        if(!directory.exists()){
            directory.mkdir();
            return true;
        }
        return false;
    }
    
    
    

}
