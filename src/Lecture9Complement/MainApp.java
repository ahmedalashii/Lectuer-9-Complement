package Lecture9Complement;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
    
public class MainApp extends Application {

    private ListView<String> listViewSource, listViewDest;
    private TextField textFieldName;
    private CheckBox checkBoxAll;
    private RadioButton radioButtonGold, radioButtonCyan;
    private Button buttonAdd, buttonDel, buttonUpdate, buttonCopy, buttonClear;

    @Override
    public void start(Stage primaryStage) throws Exception {
        String names[] = {"Ahmed", "Huda"};
        listViewSource = new ListView(
                FXCollections.observableArrayList(names)
        );
        listViewSource.getSelectionModel().selectedItemProperty().addListener(e
                -> textFieldName.setText(listViewSource.getSelectionModel().getSelectedItem())
        );
        listViewSource.setPrefSize(150, 250);
        listViewDest = new ListView<>();
        listViewDest.setPrefSize(150, 250);
        HBox hBox1 = new HBox(10, listViewSource, listViewDest);
        hBox1.setAlignment(Pos.CENTER);

        textFieldName = new TextField();

        checkBoxAll = new CheckBox("Select All");

        radioButtonGold = new RadioButton("Gold");
        radioButtonCyan = new RadioButton("Cyan");

        ToggleGroup toggleGroup = new ToggleGroup();
        radioButtonGold.setToggleGroup(toggleGroup);
        radioButtonCyan.setToggleGroup(toggleGroup);
        HBox hBox2 = new HBox(20, radioButtonGold, radioButtonCyan);
        hBox2.setAlignment(Pos.CENTER);

        buttonAdd = new Button("Add");
        buttonDel = new Button("Delete");
        buttonUpdate = new Button("Update");
        buttonCopy = new Button("Copy");
        buttonClear = new Button("Clear");
        MyEventHandler myEventHandler = new MyEventHandler();
        buttonAdd.setOnAction(myEventHandler);
        buttonDel.setOnAction(myEventHandler);
        buttonUpdate.setOnAction(myEventHandler);
        buttonCopy.setOnAction(myEventHandler);
        buttonClear.setOnAction(myEventHandler);
        checkBoxAll.setOnAction(myEventHandler);

        HBox hBox3 = new HBox(10, buttonAdd, buttonDel, buttonUpdate, buttonCopy, buttonClear);
        hBox3.setAlignment(Pos.CENTER);

        VBox vBox1 = new VBox(10, hBox1, textFieldName, checkBoxAll, hBox2, hBox3);
        vBox1.setPadding(new Insets(20));
        vBox1.setAlignment(Pos.CENTER);
        radioButtonGold.setOnAction(e
                -> vBox1.setStyle("-fx-background-color:gold")
        );
        radioButtonCyan.setOnAction(e // inline css styling  
                -> vBox1.setStyle("-fx-background-color:cyan")
        );
        FlowPane flowPane = new FlowPane(vBox1);
        flowPane.setAlignment(Pos.CENTER);

        Scene scene = new Scene(flowPane);
        scene.getStylesheets().add("file:src/Ch2Part2App/style.css");
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Main App");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private class MyEventHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            if (event.getSource() == checkBoxAll) {
                if (checkBoxAll.isSelected()) {
                    listViewSource.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                    listViewSource.getSelectionModel().selectAll();
                } else { // single
                    listViewSource.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
                }
            } else {
                String buttonName = ((Button) event.getSource()).getText();
                switch (buttonName) {
                    case "Add":
                        if (!textFieldName.getText().isEmpty()) { // just add if The user input letters or words (not empty) >> don't add null objects
                            listViewSource.getItems().add(textFieldName.getText());
                        }
                        break;
                    case "Delete":
                        if (listViewSource.getSelectionModel().getSelectionMode() == SelectionMode.SINGLE) {
                            listViewSource.getItems().remove(listViewSource.getSelectionModel().getSelectedIndex()); // remove the selected item by index
                        } else { // remove all (if Selection Model is multiple)
                              listViewSource.getItems().clear();
                              // or :
//                               listViewSource.getItems().removeAll(listViewSource.getItems());
                        }
                        break;
                    case "Update":
                        listViewSource.getItems().set(listViewSource.getSelectionModel().getSelectedIndex(), textFieldName.getText());
                        break;
                    case "Copy":
                        if (listViewSource.getSelectionModel().getSelectionMode() == SelectionMode.SINGLE) {
                            if (listViewSource.getSelectionModel().getSelectedItem() != null) {
                                listViewDest.getItems().add(listViewSource.getSelectionModel().getSelectedItem());
                            }
                        } else { // multiple
                            listViewDest.getItems().addAll(listViewSource.getItems());
                        }
                        break;
                    case "Clear":
                        listViewSource.getItems().clear();
                        listViewDest.getItems().clear();
                        break;
                }
            }
        }
    }
}
