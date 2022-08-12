package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.MenuItem;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.nio.file.FileStore;
import java.util.Optional;

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
    private String fileTypeIdentifier="anushka.dep9";
    private byte[] fileTypeInitializer;
    public String content="";

    public void initialize(){

        /* Make File staring 12 bytes to identify .dep9 file type. */
        fileTypeInitializer= fileTypeIdentifier.getBytes();



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


                content = txtHtmlEditor.getHtmlText();
                data= content.getBytes();

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
                    for(byte b:fileTypeInitializer){
                        fos.write(b^ 0xFF); //add 12 bytes front of the file
                    }

                    for (byte b: data){
                        fos.write(b^ 0xFF);   //save inverted byte
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
                    byte[] startingData= new byte[12];

                    for(int j=0;j<12;j++){
                        startingData[j]=(byte)(fis.read()^0xFF);     //take fist 12 bytes to identification
                    }
                    String identifier= new String(startingData);
                    if(identifier.equals(fileTypeIdentifier)) {
                        for (int i = 0; i < fileSize - 13; i++) {
                            dat[i] = (byte) (fis.read() ^ 0xFF);
                        }
                        fis.close();
                        data = dat;
                        String str =new String(data);
                        content=str;
                    }else{
                        fis.close();
                        Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION,"Unsupported File. Do you want to open another file?",ButtonType.YES, ButtonType.NO).showAndWait();
                        if (result.get() == ButtonType.YES) {
                            mnuOpen.fire();
                        }else if(result.get() == ButtonType.NO) {
                            return;
                        }

                    }

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }



                txtHtmlEditor.setHtmlText(content);
            }
        });

        mnuPrint.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ChoiceDialog dialog = new ChoiceDialog(Printer.getDefaultPrinter(), Printer.getAllPrinters());
                dialog.setHeaderText("Choose the printer!");
                dialog.setContentText("Choose a printer from available printers");
                dialog.setTitle("Printer Choice");
                Optional<Printer> opt = dialog.showAndWait();
                if (opt.isPresent()) {
                    Printer printer = opt.get();
                    // start printing ...
                }
                if (Printer.getDefaultPrinter() == null){
                    new Alert(Alert.AlertType.ERROR, "No default printer has been selected").showAndWait();
                    return;
                }
                PrinterJob printerJob = PrinterJob.createPrinterJob();
                if (printerJob != null){
                    printerJob.showPageSetupDialog(txtHtmlEditor.getScene().getWindow());
                    boolean success = printerJob.printPage(txtHtmlEditor);
                    if (success){
                        printerJob.endJob();
                    }else{
                        new Alert(Alert.AlertType.ERROR, "Failed to print, try again").show();
                    }
                }else{
                    new Alert(Alert.AlertType.ERROR, "Failed to initialize a new printer job").show();
                }
            }
        });

        mnuCopy.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

            }
        });
        mnuCut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

            }
        });
        mnuPaste.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

            }
        });
        mnuSelectAll.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

            }
        });

    }
}
