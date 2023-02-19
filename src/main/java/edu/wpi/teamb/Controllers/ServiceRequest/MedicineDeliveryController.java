package edu.wpi.teamb.Controllers.ServiceRequest;

import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Database.PatientTransportationRequest;
import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class MedicineDeliveryController extends BaseRequestController{
    @FXML private MFXTextField staffidfield;
    @FXML private MFXTextField typeofmedicinefield;
    @FXML private MFXTextField dosagefield;
    @FXML private MFXTextField patientnamefield;

    @FXML
    @Override
    public void initialize(){
        // initialization goes here
        // Create list of components; additionalNotesField MUST be last
        Control[] ctrl = {
                urgencyBox,
                staffidfield,
                typeofmedicinefield,
                dosagefield,
                patientnamefield,
                assignedStaffBox,
                additionalNotesField
        };
        components = new ArrayList<>(Arrays.asList(ctrl));
        textFields = new ArrayList<>();
        choiceBoxes = new ArrayList<>();

        // Create lists of text fields and choice boxes
        for (Control c : components) {
            if (c instanceof MFXTextField) textFields.add((MFXTextField) c);
            if (c instanceof MFXFilterComboBox) choiceBoxes.add((MFXFilterComboBox) c);
        }

        helpScreen = Screen.PATIENT_TRANSPORTATION_HELP;
        super.initialize();
    }

    @FXML
    @Override
    public void submitButtonClicked() throws IOException {
        // handle retrieving values and saving

        MeddicineDeliveryRequest request = new PatientTransportationRequest();
        super.submit(request);

        var equipment = equipmentNeededBox.getValue();
        if (equipment == null) {
            equipment = "";
        }
        request.setEquipmentNeeded(equipment.toString());

        var destination = patientDestinationLocationBox.getValue();
        if (destination == null) {
            destination = "";
        }
        request.setPatientDestinationLocation(destination.toString());

        var curLocation = patientCurrentLocationBox.getValue();
        if (curLocation == null) {
            curLocation = "";
        }
        request.setPatientCurrentLocation(curLocation.toString());

        request.setPatientID(this.patientIDField.getText());
        DBSession.addRequest(request);

        // may need to clear fields can be done with functions made for clear
        clearButtonClicked();
        Navigation.navigate((Screen.SUBMISSION_SUCCESS));
    }
}
