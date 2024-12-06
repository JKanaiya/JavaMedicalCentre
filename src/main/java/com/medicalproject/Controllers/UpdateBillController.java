package com.medicalproject.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import static com.medicalproject.DB.DBCRUD.updateBillPaidState;

public class UpdateBillController {

    @FXML // fx:id="billAmount"
    private Label billAmount; // Value injected by FXMLLoader

    @FXML // fx:id="billDate"
    private Label billDate; // Value injected by FXMLLoader

    @FXML // fx:id="billID"
    private Label billID; // Value injected by FXMLLoader

    @FXML // fx:id="cancelBtn"
    private Button cancelBtn; // Value injected by FXMLLoader

    @FXML // fx:id="confirmBillUpdate"
    private Button confirmBillUpdate; // Value injected by FXMLLoader

    @FXML // fx:id="patientID"
    private Label patientID; // Value injected by FXMLLoader

    @FXML // fx:id="paymentForm"
    private TextField paymentForm; // Value injected by FXMLLoader

    @FXML // fx:id="updateBillPaid"
    private CheckBox updateBillPaid; // Value injected by FXMLLoader

    @FXML // fx:id="errorMessageLabel"
    private Label errorMessageLabel; // Value injected by FXMLLoader



    public void setDetails(int iD, int amount, String date, int patID, String payForm, boolean paidState) {
        billID.setText(String.valueOf(iD));
        billAmount.setText(String.valueOf(amount));
        billDate.setText(date);
        patientID.setText(String.valueOf(patID));
        paymentForm.setText(payForm);
        updateBillPaid.setSelected(paidState);
    }
    @FXML
    private void cancelUpdate(ActionEvent event){
        closeWindow((Node) event.getSource());
    }
    @FXML
    public void confirmUpdate(ActionEvent event){
        if(updateBillPaidState(Integer.parseInt(billID.getText()), updateBillPaid.isSelected(), paymentForm.getText()) == 1){
            closeWindow((Node) event.getSource());
        }
        else {
            errorMessageLabel.setText("Invalid Input, Please try again");
        }
    }
    @FXML
    public void closeWindow(Node node) {
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }
}
