package edu.wpi.teamb.Controllers.ServiceRequest;

import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Database.PatientTransportationRequest;
import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class FacilitiesMaintenanceController extends BaseRequestController {
    private ObservableList<String> maintanenceOptions =
            FXCollections.observableArrayList("HVAC", "Elevator", "Power");

    @FXML private MFXFilterComboBox<String> maintenanceTypeBox;
    @FXML private MFXFilterComboBox locationBox;

    /** Initialize the page by declaring choice-box options */
    @FXML
    @Override
    public void initialize(){
        Control[] ctrl = {
                urgencyBox,
                assignedStaffBox,
                maintenanceTypeBox,
                locationBox,
                additionalNotesField
        };
        components = new ArrayList<>(Arrays.asList(ctrl));
        textFields = new ArrayList<>();
        choiceBoxes = new ArrayList<>();

        maintenanceTypeBox.setItems(maintanenceOptions);

        for (Control c : components) {
            if (c instanceof MFXTextField) textFields.add((MFXTextField) c);
            if (c instanceof MFXFilterComboBox) choiceBoxes.add((MFXFilterComboBox) c);
        }

        ObservableList<String> locations = getLocations();
        locationBox.setItems(locations);

        helpScreen = Screen.PATIENT_TRANSPORTATION_HELP;
        super.initialize();
    }

    /**
     * Store the data from the form in a csv file and return to home screen
     *
     * @throws IOException
   */
    @FXML
    @Override
    public void submitButtonClicked() throws IOException {
        // handle retrieving values and saving

        PatientTransportationRequest request = new PatientTransportationRequest();
        super.submit(request);

        var maintenance = maintenanceTypeBox.getValue();
        if (maintenance == null) {
            maintenance = "";
        }
        request.setMaintanenceType(maintenance.toString());

        var destination = locationBox.getValue();
        if (destination == null) {
            destination = "";
        }
        request.setlocation(destination.toString());

        // may need to clear fields can be done with functions made for clear
        clearButtonClicked();
        Navigation.navigate((Screen.SUBMISSION_SUCCESS));
    }
}
