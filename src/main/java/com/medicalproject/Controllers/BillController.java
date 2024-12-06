package com.medicalproject.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import static com.medicalproject.Bills.generateBill;
import static com.medicalproject.Bills.procedures;
import static com.medicalproject.DB.DBCRUD.addBill;
import static com.medicalproject.DB.DBCRUD.getNewID;

public class BillController implements Initializable {

    @FXML
    private TextField billPatientID;

    @FXML
    private ListView<Map.Entry<String, Integer>> availableListView;

    @FXML
    private ListView<Map.Entry<String, Integer>> selectedListView;

   @FXML
   private Label billDate;

    @FXML
    private Label billID;

    @FXML
    private Button CancelBillGen;

    @FXML
    private Button billComplete;

    @FXML
    private Button addButton;

    @FXML
    private Button removeButton;
    // Observable lists to manage procedures in the available and selected lists
    private ObservableList<Map.Entry<String, Integer>> availableItems;
    private ObservableList<Map.Entry<String, Integer>> selectedItems;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize observable lists
        availableItems = FXCollections.observableArrayList();
        selectedItems = FXCollections.observableArrayList();

        // Add items to the available list
        availableItems.addAll(procedures.entrySet());

        // Connect lists to their data
        availableListView.setItems(availableItems);
        selectedListView.setItems(selectedItems);

        // Set up cell factories for both lists
        setCellFactory(availableListView);
        setCellFactory(selectedListView);

        billDate.setText(LocalDate.now().toString());
        billID.setText(getNewID("Bills", "BillID"));
    }
    // Method to add a procedure from the available list to the selected list
    public void addButton(ActionEvent e){
        moveItem(availableListView, availableItems, selectedItems);
    }
    // Method to remove a procedure from the selected list back to the available list
    public void removeButton(ActionEvent e){
        moveItem(selectedListView, selectedItems, availableItems);
    }
    // Method to cancel the bill generation and close the window
    public void cancelBillGeneration (ActionEvent ae){
        Stage stage = (Stage) CancelBillGen.getScene().getWindow();
        stage.close();
    }
    // Method to calculate the total cost of selected procedures
    private int billTotal(){
        int totalCost = 0;
        if (selectedItems != null && !selectedItems.isEmpty()){
            for (Map.Entry<String, Integer> entry : selectedItems) {
                totalCost += entry.getValue();
            }
        }
        return totalCost;
    }
    // Method to complete the bill generation
    public void completeBill() throws IOException {
        Map<String, Integer> selectedProcedures  = new HashMap<>();
        for (Map.Entry<String, Integer> entry : selectedItems) {
            selectedProcedures.put(entry.getKey(),entry.getValue() );
        }
        addBill(Integer.parseInt(billID.getText()), Integer.parseInt(billPatientID.getText()), BigDecimal.valueOf(billTotal()) , LocalDateTime.now(), false);

        generateBill(Integer.parseInt(billID.getText()), Integer.parseInt(billPatientID.getText()), selectedProcedures , LocalDate.now(), billTotal());
        Stage stage = (Stage) billComplete.getScene().getWindow();
        stage.close();
    }
    // Method to move a selected item between two ListViews (source and target)
    private void moveItem(
            ListView<Map.Entry<String, Integer>> sourceList,
            ObservableList<Map.Entry<String, Integer>> sourceItems,
            ObservableList<Map.Entry<String, Integer>> targetItems) {
        Map.Entry<String, Integer> selectedItem = sourceList.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            targetItems.add(selectedItem);
            sourceItems.remove(selectedItem);
        }
    }
    // Method to set up the cell factory for formatting the ListView entries
    private void setCellFactory(ListView<Map.Entry<String, Integer>> listView) {
        listView.setCellFactory(param -> new ListCell<Map.Entry<String, Integer>>() {
            @Override
            protected void updateItem(Map.Entry<String, Integer> item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                }
                else {
                    setText(String.format("%-20s $%8d", item.getKey(), item.getValue()));
                }
            }
        });
    }
}

