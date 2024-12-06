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
    /**
     * Method to set the details of the bill into the corresponding labels.
     * This method is called to populate the UI with the relevant bill information.
     *
     * @param amount The amount of the bill
     * @param patID The patient ID related to the bill
     * @param bID The bill ID
     * @param bPaid A boolean value indicating whether the bill is paid or not
     * @param payForm The form of payment (e.g., cash, card, etc.)
     * @param date The date the bill was generated
     */
    public void setBillDetails(int amount, int patID, int bID, boolean bPaid, String payForm, String date){
        billAmount.setText(String.valueOf(amount));
        billDate.setText(date);
        billID.setText(String.valueOf(bID));
        billPaid.setText(String.valueOf(bPaid));
        patientID.setText(String.valueOf(patID));
        paymentForm.setText(payForm);
    }
    /**
     * Method to delete a bill based on the given bill ID.
     * This method removes the bill data from the database.
     *
     * @param billID The ID of the bill to be deleted
     */
    public void deleteBill(int billID){
        removeData(billID, "Bills", "BillID");
    }
}
