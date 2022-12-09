package controller;

import helper.UserLogin;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public TextField usernameInput;
    public TextField passwordInput;
    public Button loginButton;
    public Button cancelButton;
    public Label zoneIDLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ZoneId localZID = ZoneId.systemDefault();

        zoneIDLabel.setText(String.valueOf(localZID));
    }


    public void loginClicked(ActionEvent actionEvent) throws IOException, SQLException {

        String username = usernameInput.getText();
        String password = passwordInput.getText();

        boolean checkUser = UserLogin.checkCredentials(username, password);

        if (checkUser == true) {
            Parent appointmentsView = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
            //set new scene with add part modal
            Scene scene = new Scene(appointmentsView);
            //set stage of the modal
            Stage modal = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            //put add part modal inside
            modal.setScene(scene);
            //show the modal
            modal.show();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("A user with this information does not exist");
            alert.showAndWait();
        }
    }
    public void cancelClicked(ActionEvent actionEvent) {
        System.exit(0);
    }
}
