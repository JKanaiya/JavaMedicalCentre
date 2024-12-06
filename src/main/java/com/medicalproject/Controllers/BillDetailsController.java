package com.medicalproject.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import static com.medicalproject.DB.DBCRUD.removeData;

public class BillDetailsController {

    @FXML
    private Label billAmount;

    @FXML
    private Label billDate;

    @FXML
    private Label billID;

    @FXML
    private Label billPaid;

    @FXML
    private Label patientID;

    @FXML
    private Label paymentForm;



    public void setBillDetails(int amount, int patID, int bID, boolean bPaid, String payForm, String date){
        billAmount.setText(String.valueOf(amount));
        billDate.setText(date);
        billID.setText(String.valueOf(bID));
        billPaid.setText(String.valueOf(bPaid));
        patientID.setText(String.valueOf(patID));
        paymentForm.setText(payForm);
    }
    public void deleteBill(int billID){
        removeData(billID, "Bills", "BillID");
    }
}
