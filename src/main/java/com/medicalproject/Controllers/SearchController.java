package com.medicalproject.Controllers;
import java.io.IOException;
import java.math.BigInteger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Map;

import static com.medicalproject.DB.DBCRUD.*;

public class SearchController {

    @FXML
    private TextField searchQuery;

    @FXML
    private Button searchAppointments;

    @FXML
    private Button searchBills;

    @FXML
    private Button searchDoctors;

    @FXML
    private Button searchPatients;

    @FXML
    private ListView<Map<String,String>> listQuery;

    @FXML
    private Button updateDetailsBtn;

    @FXML
    private Button viewDetailsBtn;

    String table = "";

    @FXML
    public void setSearchDoctors(){
        table = "Doctors";
        listQuery.setItems(FXCollections.observableArrayList(searchDoctorDB(searchQuery.getText())));
        // Customize how each item appears in the ListView
        listQuery.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Map<String, String> doctor, boolean empty) {
                super.updateItem(doctor, empty);
                if (empty || doctor == null) {
                    setText(null);
                } else {
                    // Format how each doctor appears in the list
                    setText(String.format("ID: %s    Name:  %s    Email: %s   Phone Number:  %s    Specialization:  %s",
                            doctor.get("id"),
                            doctor.get("name"),
                            doctor.get("email"),
                            doctor.get("phoneNo"),
                            doctor.get("specialization")));
                }
            }
        });
    }
    @FXML
    private void viewDetails() {
        Map<String, String> selectedList = listQuery.getSelectionModel().getSelectedItem();
        if (selectedList != null && table.equals("Doctors")) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DoctorDetails.fxml"));
                Parent root = loader.load();

                String phoneNoStr = selectedList.get("phoneNo");
                BigInteger phoneNo = (phoneNoStr != null) ? new BigInteger(phoneNoStr) : BigInteger.ZERO;

                DoctorDetailsController controller = loader.getController();
                controller.setDoctorDetails(
                        selectedList.get("email"),
                        Integer.parseInt(selectedList.get("id")),
                        selectedList.get("name"),
                        phoneNo,
                        selectedList.get("specialization")
                );

                Stage stage = new Stage();
                stage.setTitle("Doctor Details");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }if (selectedList != null && table.equals("Patients")) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PatientDetails.fxml"));
                Parent root = loader.load();
                PatientDetailsController controller = loader.getController();
                controller.setPatientDetails(
                        selectedList.get("name"),
                        selectedList.get("gender"),
                        Integer.parseInt(selectedList.get("id")),
                        selectedList.get("bloodgroup"),
                        Integer.parseInt(selectedList.get("age")),
                        selectedList.get("address"),
                        Double.parseDouble(selectedList.get("height")),
                        Double.parseDouble(selectedList.get("weight"))
                );

                Stage stage = new Stage();
                stage.setTitle("Patient Details");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }if (selectedList != null && table.equals("Appointments")) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AppointmentDetails.fxml"));
                Parent root = loader.load();
                AppointmentDetailsController controller = loader.getController();
                controller.setAppointmentDetails(
                        selectedList.get("reason"),
                        selectedList.get("specialization"),
                        Integer.parseInt(selectedList.get("doctorID")),
                        Integer.parseInt(selectedList.get("patientID")),
                        selectedList.get("date"),
                        selectedList.get("time")
                );
                Stage stage = new Stage();
                stage.setTitle("Appointment Details");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }if (selectedList != null && table.equals("Bills")) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/BillDetails.fxml"));
                Parent root = loader.load();
                BillDetailsController controller = loader.getController();
                controller.setBillDetails(
                        Integer.parseInt(selectedList.get("amount")),
                        Integer.parseInt(selectedList.get("patientID")),
                        Integer.parseInt(selectedList.get("billID")),
                        Boolean.parseBoolean(selectedList.get("paidState")),
                        selectedList.get("modeOfPayment"),
                        selectedList.get("date")
                );
                Stage stage = new Stage();
                stage.setTitle("Bill Details");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setSearchPatients(){
        table = "Patients";
        listQuery.setItems(FXCollections.observableArrayList(searchPatientDB(searchQuery.getText())));
        // Customize how each item appears in the ListView
        listQuery.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Map<String, String> patient, boolean empty) {
                super.updateItem(patient, empty);
                if (empty || patient == null) {
                    setText(null);
                } else {
                    // Format how each patient appears in the list
                    setText(String.format("%s    %s     %s years old",
                            patient.get("id"),
                            patient.get("name"),
                            patient.get("age")));
                }
            }
        });
    }
    public void setSearchAppointments(){
        table = "Appointments";
        listQuery.setItems(FXCollections.observableArrayList(searchAppointmentDB(Integer.parseInt(searchQuery.getText()))));
        // Customize how each item appears in the ListView
        listQuery.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Map<String, String> appointment, boolean empty) {
                super.updateItem(appointment, empty);
                if (empty || appointment == null) {
                    setText(null);
                } else {
                    // Format how each patient appears in the list
                    setText(String.format("Time: %s        DoctorID: %s         PatientID: %s         Date: %s        Specialization: %s    ",
                            appointment.get("time"),
                            appointment.get("doctorID"),
                            appointment.get("patientID"),
                            appointment.get("date"),
                            appointment.get("specialization")));
                }
            }
        });
    }
    public void setSearchBills(){
        table = "Bills";
        listQuery.setItems(FXCollections.observableArrayList(searchBillDB(Integer.parseInt(searchQuery.getText()))));
        // Customize how each item appears in the ListView
        listQuery.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Map<String, String> bill, boolean empty) {
                super.updateItem(bill, empty);
                if (empty || bill == null) {
                    setText(null);
                } else {
                    // Format how each patient appears in the list
                    setText(String.format("Patient ID: %s        Bill ID: %s         Bill Amount: %s         Bill Date: %s        Mode Of Payment: %s    Paid: %s    ",
                            bill.get("patientID"),
                            bill.get("billID"),
                            bill.get("amount"),
                            bill.get("date"),
                            bill.get("modeOfPayment"),
                            bill.get("paidState")));
                }
            }
        });
    }
}
