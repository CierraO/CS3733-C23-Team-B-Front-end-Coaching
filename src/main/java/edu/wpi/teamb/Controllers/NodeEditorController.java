package edu.wpi.teamb.Controllers;

import edu.wpi.teamb.Database.NodeInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class NodeEditorController {
    @FXML TextField xField;
    @FXML TextField yField;
    @FXML ChoiceBox<String> floorBox;
    NodeInfo node = MapEditorController.getCurrentNode();
    String origFloor;

    public void initialize() {
        ObservableList<String> floors = FXCollections.observableArrayList();
        for (String s : new String[] {"L1, L2"})
            floors.add(s);
        floorBox.setItems(floors);
        xField.setPromptText("" + node.getXCoord());
        yField.setPromptText("" + node.getYCoord());
        origFloor = node.getFloor();
    }

    public void submitClicked() {
        boolean changed = false;

        String newX = xField.getText();
        String newY = yField.getText();
        String newFloor = floorBox.getValue();

        if (!newX.isEmpty()) {
            node.setxCoord(Integer.parseInt(newX));
            changed = true;
        }
        if (!newY.isEmpty()) {
            node.setyCoord(Integer.parseInt(newY));
            changed = true;
        }
        if (!newFloor.equals(origFloor)) {
            node.setFloor(newFloor);
            changed = true;
        }

        if (changed) node.update();
    }

    public void cancelClicked() {
        return;
    }
}
