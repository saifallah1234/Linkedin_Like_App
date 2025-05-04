package Elkhadema.khadema.controller;

import java.time.LocalDate;
import Elkhadema.khadema.Service.validateInfo.DateValidator;
import Elkhadema.khadema.Service.validateInfo.JobNameValidator;
import Elkhadema.khadema.domain.Experience;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditExperienceController {
    Experience experience;
    @FXML
    private TextArea descriptionfField;

    @FXML
    private ComboBox<String> typeField;

    @FXML
    private TextField missionField;
    @FXML
    private TextField technologieField;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    private Stage stage;
    public boolean choix;

    public Experience getExperience() {
        return experience;
    }
    public void setExperience(Experience experience) {
        this.experience = experience;
    }

    public boolean isChoix() {
        return choix;
    }

    public void setChoix(boolean choix) {
        this.choix = choix;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize(Experience experience) {
        this.experience = experience;
        if (experience == null) {
            this.experience = new Experience(0, "", "", "full-time", null, null, "");
        }
        descriptionfField.setText(this.experience.getDescription());
        typeField.setItems(FXCollections.observableArrayList("full-time", "part-time"));
        if (this.experience.getType().equals("full-time"))
            typeField.getSelectionModel().select(0);
        else
            typeField.getSelectionModel().select(1);
        missionField.setText(this.experience.getMission());
        technologieField.setText(this.experience.getTechnologie());
        startDatePicker.setValue(this.experience.getStartDate());
        if (this.experience.getStartDate() == null) {
            endDatePicker.setValue(null);
        } else
            endDatePicker.setValue(LocalDate.now());

    }
    @FXML
    public void submitForm() {
        if (descriptionfField.getText().strip().isEmpty()) {
            experience.setDescription("");
        }
        else
        experience.setDescription(descriptionfField.getText());

        if (!JobNameValidator.isValidJobName(technologieField.getText())) {
            return;
        }
        experience.setTechnologie(technologieField.getText());
        if (!DateValidator.isValidDate(startDatePicker.getValue())) {
            return;
        }
        experience.setStartDate(startDatePicker.getValue());
        if (endDatePicker.getValue().isBefore(experience.getStartDate())) {
            return;
        }
        experience.setEndDate(endDatePicker.getValue());
        if (missionField.getText().strip().isEmpty()) {
            return;
        }
        experience.setMission(missionField.getText());
        choix = true;
        stage.close();

    }

}
