package Elkhadema.khadema.controller;

import Elkhadema.khadema.Service.validateInfo.JobNameValidator;
import Elkhadema.khadema.domain.Competance;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditCompetanceController {
    private Competance competance;
    @FXML
    private TextArea descriptionfField;
    @FXML
    private TextField titreField;
    @FXML
    private TextField niveauField;

    @FXML
    private TextField technologieField;

    private Stage stage;
    public boolean choix;
    public Competance getCompetance() {
        return competance;
    }
    public void setCompetance(Competance competance) {
        this.competance = competance;
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
    public void initialize(Competance competance) {
        this.competance = competance;
        if (competance == null) {
            this.competance = new Competance(0, "", "", "", 0);
        }
        descriptionfField.setText(this.competance.getDescription());
        titreField.setText(this.competance.getTitre());

        niveauField.setText(String.valueOf(this.competance.getNiveau()) );
        technologieField.setText(this.competance.getTechnologie());

    }

    @FXML
    public void submitForm() {
        if (descriptionfField.getText().strip().isEmpty()) {
            competance.setDescription("");
        } else
            competance.setDescription(descriptionfField.getText());

        if (!JobNameValidator.isValidJobName(technologieField.getText())) {
            return;
        }
        competance.setTechnologie(technologieField.getText());
        try {
            competance.setNiveau(Integer.parseInt(niveauField.getText()));
            if (competance.getNiveau()<1) {
                return;
            }
            } catch (NumberFormatException e) {
                return;
            }
        choix = true;
        stage.close();

    }

}
