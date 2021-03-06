package edu.bsu.cs222.pos;

import javafx.scene.control.*;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.HashMap;

public class CashierController {
    private static final Company company = AdminController.getCompany();
    private static Item selectedItem;
    private final static Order itemsInCart = new Order();

    public static void addSellableItemsToDisplay()  {
        HashMap<String, Item> inventoryList = company.getAvailableInventoryList();
        ArrayList<Item> inventoryArrayList = new ArrayList<>(inventoryList.values());
        CashierUI.displaySellableItems(inventoryArrayList);
    }

    public static void itemSearchByBarcodeOrName(ComboBox<String> searchSelection, TextField searchField,Button searchButton,
                                                 TextField selectedItemInput, TextField priceInput) {
        searchButton.setOnMouseClicked(event -> {
            if(searchSelection.getValue().equals("Barcode search")){
                String barcode = searchField.getText();
                selectedItem = company.getItemByID(barcode);
                if (selectedItem != null) {
                    selectedItemInput.setText(selectedItem.name);
                    priceInput.setText(selectedItem.price.toString());
                }
            }else if (searchSelection.getValue().equals("Name search")){
                String itemName = searchField.getText();
                selectedItem = company.getItemByName(itemName);
                if (selectedItem != null) {
                    selectedItemInput.setText(selectedItem.name);
                    priceInput.setText(selectedItem.price.toString());
                }
            }
        });
    }

    public static void addItemToByAddButton(Button addItem, TextField subtotal, TextField tax, TextField total){
        addItem.setOnMouseClicked(event -> {
            if(!(selectedItem == null)) {
                addItemToCart(selectedItem,subtotal,tax,total);
            }
        });



    }

    public static void deleteSelectedItem(TableView<Item> itemList, TextField subtotal, TextField tax, TextField total){
        itemList.setRowFactory( tv -> {
            TableRow<Item> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Item rowData = row.getItem();
                    itemsInCart.deleteItem(rowData);
                    subtotal.setText(itemsInCart.getSubtotal().toString());
                    tax.setText(itemsInCart.getTax().toString());
                    total.setText(itemsInCart.getTotalWithTax().toString());
                    CashierUI.displaySelectedItems(itemsInCart.getItemList());
                }
            });
            return row ;
        });
    }

    public static void addItemByDoubleClick(TableView<Item> itemRow,TextField subtotal,TextField tax, TextField total) {
        itemRow.setRowFactory(tv -> {
            TableRow<Item> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Item selectedItem = row.getItem();
                    if (!(selectedItem == null)) {
                        addItemToCart(selectedItem,subtotal,tax,total);

                    }
                }
            });
            return row;
        });
    }

    private static void addItemToCart(Item selectedItem, TextField subtotal, TextField tax, TextField total) {
        itemsInCart.addItem(selectedItem);
        subtotal.setText(itemsInCart.getSubtotal().toString());
        tax.setText(itemsInCart.getTax().toString());
        total.setText(itemsInCart.getTotalWithTax().toString());
        CashierUI.displaySelectedItems(itemsInCart.getItemList());
    }


    public static void reset(Button anotherOrderButton,
                             javafx.scene.control.TableView<Item> receiptItemList,TextField searchField,
                             TextField itemInput, TextField priceInput,
                             TextField subtotal, TextField tax, TextField total) {
        anotherOrderButton.setOnMouseClicked(event -> {
            for ( int i = 0; i < receiptItemList.getItems().size(); i++) {
                receiptItemList.getItems().clear();
            }
            searchField.clear();
            itemInput.clear();
            priceInput.clear();
            subtotal.clear();
            tax.clear();
            total.clear();
        });
    }

    public static void checkout(Button checkoutButton) {
        checkoutButton.setOnMouseClicked(event -> {
            checkoutButton.setDisable(true);
                Stage receipt = ReceiptUI.popUp();
                receipt.getScene().getWindow().setOnCloseRequest(closedEvent -> checkoutButton.setDisable(false));
        });
    }

    public static void addItemsInCartToDisplay(TextField subtotal,TextField tax,TextField total,TextField dateAndTime) {
            subtotal.setText(itemsInCart.getSubtotal().toString());
            tax.setText(itemsInCart.getTax().toString());
            total.setText(itemsInCart.getTotalWithTax().toString());
            dateAndTime.setText(itemsInCart.getDateAndTime());
            ArrayList<Item> uniqueItemList = new ArrayList<>();
            itemsInCart.getItemList().stream().distinct().forEach(
                    uniqueItemList::add
            );
            ReceiptUI.displayItemsInCart(uniqueItemList);
    }

}
