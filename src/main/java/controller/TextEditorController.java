package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.nio.file.FileStore;

public class TextEditorController {
    public MenuItem mnuNew;
    public MenuItem mnuOpen;
    public MenuItem mnuSave;
    public MenuItem mnuPrint;
    public MenuItem mnuClose;

    public HTMLEditor txtHtmlEditor;
    public MenuItem mnuCut;
    public MenuItem mnuCopy;
    public MenuItem mnuPaste;
    public MenuItem mnuSelectAll;
    public File savePath;
    public File openedFile;
    public byte[] data;

    public void initialize(){

        mnuNew.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                txtHtmlEditor.setHtmlText("");

            }
        });
        mnuClose.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Platform.exit();
            }
        });
        mnuSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {


                String str = txtHtmlEditor.getHtmlText();
                data= str.getBytes();

                FileChooser saveFileChooser= new FileChooser();
                saveFileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
                saveFileChooser.setTitle("Save location");
                saveFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("dep9 (*.dep9*)", "*.dep9*"));
                savePath=saveFileChooser.showSaveDialog(txtHtmlEditor.getScene().getWindow());
                System.out.println(savePath.getName());
                String fileName= savePath.getName();

                if(!fileName.endsWith(".dep9")){
                    fileName=fileName+".dep9";
                }
                File file = new File(savePath.getParentFile(), fileName);
                if (!file.exists()){
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                try {
                    FileOutputStream fos =new FileOutputStream(file);
                    for (byte b: data){
                        fos.write(b);
                    }
                    fos.close();
                    txtHtmlEditor.setHtmlText("");

                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        });
        mnuOpen.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FileChooser openFileChooser = new FileChooser();
                openFileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
                openFileChooser.setTitle("Select File");
                openFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("dep9 (*.dep9*)", "*.dep9*"));
                openedFile = openFileChooser.showOpenDialog(txtHtmlEditor.getScene().getWindow());
                System.out.println(openedFile.getAbsolutePath());

                try {
                    FileInputStream fis = new FileInputStream(openedFile);
                    int fileSize= (int) openedFile.length();
                    byte[] dat = new byte[fileSize];

                    for(int i=0; i<fileSize;i++){
                        dat[i]= (byte) fis.read();
                    }
                    fis.close();
                    data=dat;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }


                String strr =new String(data);
                txtHtmlEditor.setHtmlText(strr);
            }
        });


    }
}
