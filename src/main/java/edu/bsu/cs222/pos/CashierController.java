package edu.bsu.cs222.pos;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.util.ArrayList;
import java.util.HashMap;

public class CashierController {
    private static final Company company = AdminController.getCompany();
    private static Item selectedItem;
    //will make a button to initialize a new order; won't need to be final
    private final static Order itemsInCart = new Order();

    public static void addSellableItemsToDisplay()  {
        HashMap<String, Item> inventoryList = company.getAvailableInventoryList();
        ArrayList<Item> inventoryArrayList = new ArrayList<>(inventoryList.values());
        CashierUI.displaySellableItems(inventoryArrayList);
    }

    public static void itemSearchByBarcode(TextField barcodeSearch, Button searchButton, TextField itemInput, TextField priceInput){
        searchButton.setOnMouseClicked(event -> {
            String barcode = barcodeSearch.getText();
            selectedItem = company.getItemByID(barcode);
            itemInput.setText(selectedItem.name);
            priceInput.setText(selectedItem.price.toString());
        });
    }
    public static void itemSearchByName(TextField itemNameSearchField, Button itemSearchByNameButton,TextField itemInput, TextField priceInput ){
        itemSearchByNameButton.setOnMouseClicked(event -> {
            String itemName = itemNameSearchField.getText();
            selectedItem = company.getItemByName(itemName);
            itemInput.setText(selectedItem.name);
            priceInput.setText(selectedItem.price.toString());
        });

    }

    public static void addItemToCart(Button addItem, TextField subtotal, TextField tax, TextField total,TextField dateAndTime){
        addItem.setOnMouseClicked(event -> {
            if(!(selectedItem == null)) {
                itemsInCart.addItem(selectedItem);
                subtotal.setText(itemsInCart.getSubtotal().toString());
                tax.setText(itemsInCart.getTax().toString());
                total.setText(itemsInCart.getTotalWithTax().toString());
                dateAndTime.setText(itemsInCart.getDateAndTime().toString());
                CashierUI.displaySelectedItems(itemsInCart.getItemList());
            }
        });
    }

    public static void reset(Button anotherOrderButton,
                                        javafx.scene.control.TableView<Item> receiptItemList,
                                        TextField barcodeSearch,TextField itemInput, TextField priceInput,
                                        TextField subtotal, TextField tax, TextField total,TextField dateAndTime) {
        anotherOrderButton.setOnMouseClicked(event -> {
            for ( int i = 0; i < receiptItemList.getItems().size(); i++) {
                receiptItemList.getItems().clear();
            }
            barcodeSearch.clear();
            itemInput.clear();
            priceInput.clear();
            subtotal.clear();
            tax.clear();
            total.clear();
            dateAndTime.clear();
        });
    }
}
