/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package velasco.karen.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author Karen Velasco
 */
public class FileChooser {

    /**
     * @param args the command line arguments
     */
    public static String getFilePath(){
        // TODO code application logic here
        String path = null;
        try{
            JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

            int returnValue = jfc.showOpenDialog(null);
		// int returnValue = jfc.showSaveDialog(null);

	    if (returnValue == JFileChooser.APPROVE_OPTION) {
		File selectedFile = jfc.getSelectedFile();
                path = selectedFile.getAbsolutePath();
	    }
        
            System.out.println(path);
            return path;
        }catch(Exception e){
            System.out.println("Hubo un error en la lectura del archivo.");
        }
        return null;
    }
}
