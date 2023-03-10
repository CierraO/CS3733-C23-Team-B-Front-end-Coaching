package edu.wpi.teamb.Controllers.Database;

import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Screen;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class DatabaseHelpController {

  @FXML Button backButton;

  public void initialize() {
    backButton.setOnAction((actionEvent) -> backToScene());
  }

  private void backToScene() {
    Navigation.navigate(Screen.MAP_DATA_EDITOR);
  }
}
