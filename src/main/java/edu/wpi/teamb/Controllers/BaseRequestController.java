package edu.wpi.teamb.Controllers;

import edu.wpi.teamb.Entities.RequestStatus;
import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Screen;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;

public class BaseRequestController {
  // JavaFX components
  @FXML protected TextField firstNameField;
  @FXML protected TextField lastNameField;
  @FXML protected TextField employeeIDField;
  @FXML protected TextField emailField;
  @FXML protected ChoiceBox urgencyBox;
  @FXML protected TextField assignedEmployeeField;
  @FXML protected TextField additionalNotesField;
  private RequestStatus request;
  @FXML protected Button cancelButton;
  @FXML protected Button helpButton;
  @FXML protected Button clearButton;
  @FXML protected Button submitButton;

  // Choice-box options
  protected ObservableList<String> urgencyOptions =
      FXCollections.observableArrayList("Low", "Moderate", "High", "Requires Immediate Attention");

  // List of all text fields and choice boxes for flexibility; when adding new input components to
  // form, add to this list
  protected ArrayList<Control> components;
  protected ArrayList<TextField> textFields;
  protected ArrayList<ChoiceBox> choiceBoxes;

  protected Screen helpScreen;

  /**
   * Initialize the page by instantiating 3 arrays of all components, text field components, and
   * choice-box components, as well as declaring choice-box options
   */
  @FXML
  public void initialize() {
    submitButton.setDisable(true);
    urgencyBox.setItems(urgencyOptions);
  }

  /**
   * Return to the home screen without saving form data
   *
   * @throws IOException
   */
  public void cancelButtonClicked() throws IOException {
    Navigation.navigate(Screen.HOME);
  }

  /**
   * Display the help page
   *
   * @throws IOException
   */
  public void helpButtonClicked() throws IOException {
    Navigation.navigate(helpScreen);
  }

  /**
   * Remove all inputted data from the form
   *
   * @throws IOException
   */
  public void clearButtonClicked() throws IOException {
    for (TextField t : textFields) t.clear();

    for (ChoiceBox c : choiceBoxes) c.getSelectionModel().clearSelection();
  }

  /**
   * Store the data from the form in a Java object and show confirmation screen
   *
   * @throws IOException
   */
  public void submitButtonClicked() throws IOException, SQLException {
    // stub
  }

  /**
   * Returns the text in the given component, whether it's a TextField or ChoiceBox
   *
   * @param component the TextField or ChoiceBox
   * @return a String containing the inputted text
   */
  protected String getText(Control component) {
    if (component instanceof TextField) return ((TextField) component).getText();
    else if (component instanceof ChoiceBox) {
      String s = (String) ((ChoiceBox) component).getValue();
      if (s == null) s = "";
      return s;
    } else {
      return "";
    }
  }

  /**
   * Whenever a key is released, updates disable status of clearButton and submitButton
   *
   * @throws IOException
   */
  public void buttonControl() throws IOException {
    boolean submitEnable = isFormFull();
    submitButton.setDisable(!submitEnable);
  }

  /**
   * Determine whether every field (except notes) in the request form is full
   *
   * @return true if form is full, false otherwise
   */
  private boolean isFormFull() {
    return isFormFull(0);
  }

  /**
   * Determine whether every field (except notes) in the request form is full
   *
   * @param i iterator i should be initialized as 0
   * @return true if form is full, false otherwise
   */
  private boolean isFormFull(int i) {
    // Skip final item, which should be the notes field
    if (i == components.size() - 2) return !getText(components.get(i)).equals("");
    return !getText(components.get(i)).equals("") && isFormFull(i + 1);
  }
}