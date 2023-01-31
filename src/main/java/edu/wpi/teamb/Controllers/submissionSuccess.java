package edu.wpi.teamb.Controllers;

import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Screen;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class submissionSuccess {

  @FXML private Button returnButton;

  @FXML private Button requestsButton;

  public void returnButtonClicked() throws IOException {
    Navigation.navigate(Screen.HOME);
  }

  public void requestsButtonClicked() throws IOException {
    Navigation.navigate(Screen.REQUESTS);
  }
}
