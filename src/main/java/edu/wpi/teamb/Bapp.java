package edu.wpi.teamb;

import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Screen;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Bapp extends Application {
  @Setter @Getter private static Stage primaryStage;
  @Setter @Getter private static BorderPane rootPane;

  @Override
  public void init() {
    log.info("Starting Up");
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("views/Navigation/Root.fxml"));
    root.setId("home");
    Scene scene = new Scene(root, 1200, 650);
    scene.getStylesheets().addAll(this.getClass().getResource("/css/style.css").toExternalForm());

    Bapp.primaryStage = primaryStage;
    Bapp.rootPane = (BorderPane) root;

    Image icon = new Image(this.getClass().getResource("/media/icon.png").toString());
    primaryStage.getIcons().add(icon);

    primaryStage.setScene(scene);
    primaryStage.setTitle("Brigham and Women's Hospital");
    primaryStage.show();
    Navigation.navigate(Screen.SIGN_IN);
    DBSession.refreshAll();
  }

  @Override
  public void stop() {
    log.info("Shutting Down");
  }
}
