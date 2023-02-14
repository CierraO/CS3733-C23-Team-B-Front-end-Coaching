package edu.wpi.teamb.Navigation;

public enum Screen {
  HOME("views/Navigation/HomeScreen1.fxml"),
  DATABASE_HELP("views/Database/DatabaseHelp.fxml"),
  MAP_DATA_EDITOR("views/Database/MapDataEditor.fxml"),
  MAP_EDITOR("views/Database/MapEditor.fxml"),
  NODE_EDITOR("views/Database/NodeEditor.fxml"),
  SIDE_NODE_EDITOR("views/Database/SideNodeEditor.fxml"),
  NODE_CREATOR("views/Database/NodeCreator.fxml"),
  LOCATION_EDITOR("views/Database/LocationEditor.fxml"),
  LOCATION_CREATOR("views/Database/LocationCreator.fxml"),
  MOVE_CREATOR("views/Database/MoveCreator.fxml"),
  EDGE_EDITOR("views/Database/EdgeEditor.fxml"),
  EDGE_CREATOR("views/Database/EdgeCreator.fxml"),
  NAVIGATION("views/Navigation/Navigation.fxml"),
  PATHFINDING("views/Database/Pathfinding.fxml"),
  PATIENT_TRANSPORTATION("views/ServiceRequest/PatientTransportation.fxml"),
  PATIENT_TRANSPORTATION_HELP("views/ServiceRequest/PatientTransportationHelpPage.fxml"),
  SANITATION("views/ServiceRequest/NewSanitationService.fxml"),
  SANITATION_HELP("views/ServiceRequest/SanitationHelpPage.fxml"),
  COMPUTER_SERVICES("views/ServiceRequest/ComputerService.fxml"),
  COMPUTER_SERVICES_HELP("views/ServiceRequest/ComputerServiceHelpPage.fxml"),
  SIGN_IN("views/Profile/SignIn.fxml"),
  REQUESTS("views/ServiceRequest/Requests.fxml"),
  PROFILE("views/Profile/Profile.fxml"),
  MAINHELP("views/Navigation/MainHelpPage.fxml"),
  FOOTER("views/Navigation/Footer.fxml"),
  SUBMISSION_SUCCESS("views/ServiceRequest/SubmissionSuccess.fxml"),
  ABOUT("views/Navigation/About.fxml"),
  LANDING_PAGE("views/ServiceRequest/Forms.fxml"),
  CREATE_ACCOUNT("views/Profile/CreateAccount.fxml"),
  ALL_USERS("views/Profile/AllUsers.fxml"),
  LANDING_PAGE_CREDITS("views/Navigation/LandingPageCredits.fxml"),
  TEMPLATE("views/ServiceRequest/Template.fxml");

  private final String filename;

  Screen(String filename) {
    this.filename = filename;
  }

  public String getFilename() {
    return filename;
  }
}
