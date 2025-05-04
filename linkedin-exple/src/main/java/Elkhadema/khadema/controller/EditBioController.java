package Elkhadema.khadema.controller;

import Elkhadema.khadema.Service.validateInfo.JobNameValidator;
import Elkhadema.khadema.domain.Person;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditBioController {
    @FXML
    private TextField jobField;
    @FXML
    private TextField ageField;
    @FXML
    private ComboBox<String> sexeComboBox;
    String job;
    int age;
    boolean closedHow;
    public boolean isClosedHow() {
        return closedHow;
    }

    public void setClosedHow(boolean closedHow) {
        this.closedHow = closedHow;
    }

    String sexe;
    private Stage stage;
    private Person person;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize(Person person) {
        this.person=person;
        sexeComboBox.setItems(FXCollections.observableArrayList(
                "male",
                "female"));
        if (person.getSexe() == "male") {
            sexeComboBox.getSelectionModel().select(0);
        } else
            sexeComboBox.getSelectionModel().select(1);
        jobField.setText(person.getJob());
        ageField.setText(String.valueOf(person.getAge()));
    }

    public void submitForm() {
        if (!JobNameValidator.isValidJobName(jobField.getText())) {
            return;
        }
        job = jobField.getText();
        try {
            if (Integer.parseInt(ageField.getText()) < 1) {
                return;
            }

        } catch (Exception e) {
            return;
        }
        age = Integer.parseInt(ageField.getText());
        sexe =sexeComboBox.getSelectionModel().getSelectedItem();
        closedHow=true;
        stage.close();

    }

}
