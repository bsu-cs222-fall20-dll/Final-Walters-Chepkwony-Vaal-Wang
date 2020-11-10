package edu.bsu.cs222.pos;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;


public class ReceiptUI extends Application{
    private final Label titleLabel = new Label("Company Name");
    private final Label taxLabel = new Label("Tax:");
    private final Label balanceLabel = new Label("Balance:");
    private final Label dateAndTimeLabel = new Label("Date and Time:");
    private final HBox receiptItems = new HBox();
    private final HBox receiptTotal = new HBox();
    private static final TableView<Item> receiptItemList = new TableView<>();
    private final TableColumn<Item, Item> receiptNameColumn = new TableColumn<>("Item Name");
    private final TableColumn<Item, Item> receiptPriceColumn = new TableColumn<>("Price");
    private final ScrollPane receiptItemListScrollPane = new ScrollPane(receiptItemList);

    @Override
    public void start (Stage primaryStage) {
        primaryStage.setTitle("Receipt");
        primaryStage.setWidth(400);
        primaryStage.setHeight(600);
        formatDisplay();
        primaryStage.setScene(new Scene(createRoot()));
        primaryStage.show();
    }

    private Pane createRoot () {
        VBox root = new VBox();
        root.getChildren().addAll(
                titleLabel,
                receiptItems,
                receiptItemListScrollPane,
                taxLabel,
                balanceLabel,
                dateAndTimeLabel
        );
        return root;
    }

    private void formatDisplay(){
        titleLabel.setMinWidth(400);
        titleLabel.setMinHeight(50);
        titleLabel.setFont(Font.font("Arial", 25));
        titleLabel.setTranslateY(5);
        titleLabel.setAlignment(Pos.CENTER);
        receiptItems.setMinWidth(400);
        receiptItems.setMinHeight(400);
        receiptItems.setMinWidth(400);
        receiptItems.setMinHeight(150);
        receiptNameColumn.setMinWidth(180);
        receiptItemList.setMinWidth(350);
        receiptItemList.setTranslateX(50);
        receiptItemList.getColumns().addAll(receiptNameColumn, receiptPriceColumn);
        taxLabel.setFont(Font.font("Arial", 20));
        HBox.setMargin(taxLabel, new Insets(10, 5, 10, 175));
        balanceLabel.setFont(Font.font("Arial", 20));
        HBox.setMargin(balanceLabel, new Insets(10, 5, 10, 175));
        dateAndTimeLabel.setFont(Font.font("Arial", 20));
        HBox.setMargin(dateAndTimeLabel, new Insets(10, 5, 10, 175));
        receiptItemListScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        receiptItemListScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        receiptItemListScrollPane.setVvalue(.5);
        receiptItemListScrollPane.setHvalue(.5);
        receiptItemListScrollPane.setDisable(false);
    }

    public static void displayItem(ArrayList<Item> data){
        ObservableList<Item> observableData = FXCollections.observableList(data);
        receiptItemList.setItems(observableData);
        receiptItemList.refresh();
    }

}