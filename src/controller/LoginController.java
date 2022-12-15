package controller;

import helper.UserQuery;
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

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public TextField usernameInput;
    public TextField passwordInput;
    public Button loginButton;
    public Button cancelButton;
    public Label zoneIDLabel;
    public Label usernameLabel;
    public Label passwordLabel;
    private ResourceBundle rb = ResourceBundle.getBundle("main/language_" + Locale.getDefault().getLanguage());

    /**
     * Sets the text areas on the page with the system's default language*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ZoneId localZID = ZoneId.systemDefault();

            loginButton.setText(rb.getString("Login"));
        passwordInput.setPromptText(rb.getString("Password"));
        usernameInput.setPromptText(rb.getString("Username"));
        cancelButton.setText(rb.getString("close"));
        usernameLabel.setText(rb.getString("Username"));
        passwordLabel.setText(rb.getString("Password"));
        zoneIDLabel.setText(String.valueOf(localZID));
    }


    /**
     * When the user clicks the login button, this function will check that the user exists using the checkLogin method in UserQuery
     * will also document the login and whether it was successful to the login_activity.txt file
     * and will set the default Locale to English*/
    public void loginClicked(ActionEvent actionEvent) throws IOException, SQLException {

        System.out.println(rb);
        System.out.println(Locale.getDefault());
        String username = usernameInput.getText();
        String password = passwordInput.getText();

        boolean checkUser = UserQuery.checkLogin(username, password);

        String filename = "login_activity.txt", result;
        FileWriter fw = new FileWriter(filename, true);
//        PrintWriter pw = new PrintWriter(fw);


        if (checkUser == true) {
            Locale.setDefault(new Locale("en", "US"));

            result = username + " logged in at " + LocalDateTime.now() + "\n";
            fw.write(result);
            fw.close();
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
            result = username + " attempted to login at " + LocalDateTime.now() + " but failed. \n";
            fw.write(result);
            fw.close();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(rb.getString("doesNotExist"));
            alert.showAndWait();
        }

    }
    public void cancelClicked(ActionEvent actionEvent) {
        System.exit(0);
    }


}
