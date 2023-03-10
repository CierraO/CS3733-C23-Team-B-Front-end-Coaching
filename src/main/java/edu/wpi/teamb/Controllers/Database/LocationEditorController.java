package edu.wpi.teamb.Controllers.Database;

import edu.wpi.teamb.Algorithms.Sorting;
import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Database.LocationName;
import edu.wpi.teamb.Database.Move;
import edu.wpi.teamb.Database.Node;
import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Screen;
import edu.wpi.teamb.Pathfinding.Pathfinding;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class LocationEditorController {
  @FXML MFXFilterComboBox<String> locationBox;
  @FXML Button submitButton;
  @FXML TextField longNameField;
  @FXML TextField shortNameField;
  @FXML MFXFilterComboBox<String> locationTypeBox;
  @FXML MFXFilterComboBox<String> nodeBox;
  @FXML Text bigText;
  Map<String, LocationName> locations = new HashMap<>();
  private String origType;
  private String origNode;
  private LocationName location;
  ObservableList<String> types = FXCollections.observableArrayList();
  ObservableList<String> nodes = FXCollections.observableArrayList();

  /** Method run when controller is initialized */
  public void initialize() {
    locationBox.setItems(getLocations());
    Collections.addAll(
        types, "CONF", "DEPT", "HALL", "LABS", "REST", "SERV", "EXIT", "STAI", "ELEV");
    nodes = getNodes();
    locations = DBSession.getAllLocationNames();
  }

  public void setFields() {
    if (!locationBox.getText().isEmpty()) {
      location = locations.get(locationBox.getValue());
      origNode = DBSession.getMostRecentNodeID(location.getLongName());
      if (origNode == null) origNode = "";

      String longName = location.getLongName();
      String shortName = location.getShortName();
      String locationType = location.getLocationType();

      locationTypeBox.setItems(types);
      nodeBox.setItems(nodes);
      longNameField.setPromptText(longName);
      shortNameField.setPromptText(shortName);
      locationTypeBox.setValue(locationType);
      nodeBox.setValue(origNode);

      origType = locationType;
    }
  }

  public void resetFields() {
    locationBox.clear();
    longNameField.clear();
    longNameField.setPromptText("");
    shortNameField.clear();
    shortNameField.setPromptText("");
    locationTypeBox.clear();
    locationBox.setItems(getLocations());
    nodeBox.clear();
    location = null;
  }

  private ObservableList<String> getLocations() {
    ObservableList<String> list = FXCollections.observableArrayList();
    Map<String, LocationName> locationsDBList = DBSession.getAllLocationNames();

    locationsDBList.forEach((key, value) -> list.add(value.getLongName()));

    Sorting.quickSort(list);
    return list;
  }

  private ObservableList<String> getNodes() {
    ObservableList<String> list = FXCollections.observableArrayList();
    Map<String, Node> nodeDBMap = DBSession.getAllNodes();

    nodeDBMap.forEach((key, value) -> list.add(value.getNodeID()));

    Sorting.quickSort(list);
    return list;
  }

  public void submitClicked() {
    boolean changed = false;

    String newLongName = location.getLongName();
    String newShortName = location.getShortName();
    String newLocationType = location.getLocationType();

    if (!longNameField.getText().isEmpty()) {
      changed = true;
      newLongName = longNameField.getText();
    }
    if (!shortNameField.getText().isEmpty()) {
      changed = true;
      newShortName = shortNameField.getText();
    }
    if (!newLocationType.equals(origType)) {
      changed = true;
      newLocationType = locationTypeBox.getValue();
    }

    if (changed) {
      LocationName newLN = new LocationName();
      newLN.setLocationType(newLocationType);
      newLN.setLongName(newLongName);
      newLN.setShortName(newShortName);

      if (nodeBox.getText().equals(origNode)) DBSession.updateLocationName(newLN, location);
      else {
        DBSession.updateLocationName(newLN, location);

        Move oldMove =
            DBSession.getLNMoves(new Date(System.currentTimeMillis())).get(location.getLongName());
        Move newMove = new Move();
        Node newNode = DBSession.getAllNodes().get(nodeBox.getValue());
        if (oldMove != null) newMove.setMoveDate(oldMove.getMoveDate());
        else {
          java.util.Date d;
          try {
            d = new SimpleDateFormat("yyyy-mm-dd").parse("2023-01-01");
          } catch (ParseException e) {
            throw new RuntimeException(e);
          }
          newMove.setMoveDate(d);
        }
        newMove.setNode(newNode);
        newMove.setLocationName(location);

        if (oldMove != null) DBSession.updateMove(oldMove, newMove);
        else DBSession.addMove(newMove);
      }

      Pathfinding.refreshData();
      location = newLN;
    } else if (!nodeBox.getValue().equals(origNode)) {
      Move oldMove =
          DBSession.getLNMoves(new Date(System.currentTimeMillis())).get(location.getLongName());
      Move newMove = new Move();
      Node newNode = DBSession.getAllNodes().get(nodeBox.getValue());
      if (oldMove != null) newMove.setMoveDate(oldMove.getMoveDate());
      else {
        java.util.Date d;
        try {
          d = new SimpleDateFormat("yyyy-mm-dd").parse("2023-01-01");
        } catch (ParseException e) {
          throw new RuntimeException(e);
        }
        newMove.setMoveDate(d);
      }
      newMove.setNode(newNode);
      newMove.setLocationName(location);

      if (oldMove != null) DBSession.updateMove(oldMove, newMove);
      else DBSession.addMove(newMove);

    } else {
      bigText.setText("No Change");
      bigText.setFill(Color.RED);
      return;
    }
    cancelClicked();
  }

  public void cancelClicked() {
    Navigation.navigate(Screen.MAP_EDITOR);
  }

  public void newLocationClicked() {
    Navigation.navigate(Screen.LOCATION_CREATOR);
  }

  public void deleteClicked() {
    DBSession.deleteLocationName(location);
    locations.remove(location.getLongName());
    resetFields();
  }
}
