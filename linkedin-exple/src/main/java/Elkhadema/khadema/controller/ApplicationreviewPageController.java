package Elkhadema.khadema.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;


import Elkhadema.khadema.DAO.DAOImplemantation.JobsDAO;
import Elkhadema.khadema.DAO.DAOImplemantation.PersonDAO;
import Elkhadema.khadema.Service.ServiceImplemantation.CompanyServiceImp;
import Elkhadema.khadema.Service.ServiceImplemantation.FollowServiceImp;
import Elkhadema.khadema.Service.ServiceImplemantation.JobServiceImp;
import Elkhadema.khadema.Service.ServiceImplemantation.UserServiceImp;
import Elkhadema.khadema.domain.Company;
import Elkhadema.khadema.domain.JobRequest;
import Elkhadema.khadema.domain.Media;
import Elkhadema.khadema.domain.Person;
import Elkhadema.khadema.domain.User;
import Elkhadema.khadema.util.Session;
import Elkhadema.khadema.util.Exception.NotUserException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ApplicationreviewPageController extends NavbarController {
	JobServiceImp js = new JobServiceImp();
	PersonDAO pDao = new PersonDAO();
	JobsDAO jDao = new JobsDAO();
	CompanyServiceImp cs = new CompanyServiceImp();
	PersonDAO pd=new PersonDAO();
	@FXML
	private Text age;

	@FXML
	private VBox forperson;

	@FXML
	private Text job;

	@FXML
	private ButtonBar listContact;

	@FXML
	private VBox postholder;

	@FXML
	private VBox postholder1;

	@FXML
	private ScrollPane CC;
	@FXML
	private VBox postscontainer;

	@FXML
	private VBox postscontainer1;

	@FXML
	private TextField searchbar;

	@FXML
	private Text sexe;

	@FXML
	private Text username;


	@FXML
	private VBox youricon;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if(pd.get(Session.getUser().getId()).isPresent()) {
        	miniprofilesetup();
        	forperson.setVisible(true);
    	}
    	else {
    		forperson.setVisible(false);
		}
		initContacts();
		postholder.setSpacing(15);
		super.initialize(arg0, arg1);
		resetrequests();
	}

	private void resetrequests() {
		postholder.getChildren().clear();
		if (pd.get(Session.getUser().getId()).isPresent()) {
			List<JobRequest> j=js.getAllJobRequestByUser(Session.getUser());
			j.stream().filter(t -> t.getEtat().equals("Accepted")).forEach(t -> showRequestForUser(t));
			j.stream().filter(t -> t.getEtat().equals("Finished")).forEach(t -> showRequestForUser(t));
			j.stream().filter(t -> t.getEtat().equals("Rejected")).forEach(t -> showRequestForUser(t));
			j.stream().filter(t -> t.getEtat().equals("Fire")).forEach(t -> showRequestForUser(t));
			j.stream().filter(t -> t.getEtat().equals("Waiting"))
					.forEach(t -> showRequestForUser(t));

		}
		else {
			List<JobRequest> j=js.getAllJobRequestByCompany(new Company(Session.getUser().getId(), null, null));
			j.stream().filter(t -> t.getEtat().equals("Waiting"))
					.forEach(t -> showRequestForCompany(t));
			j.stream().filter(t -> t.getEtat().equals("Accepted")).forEach(t -> showRequestForCompany(t));
		}

	}
	 private void miniprofilesetup() {
	    	Person person=pd.get(Session.getUser().getId()).get();
	    	username.setText(person.getUserName());
	    	age.setText(""+person.getAge());
	    	sexe.setText(person.getSexe());
	    	job.setText(person.getJob());
	    	ImageView profileimg;
	    	 if (person.getPhoto().getMedia() == null) {
	    		 profileimg = new ImageView(new Image("file:src//main//resources//images//user.png"));
	         } else {
	        	 profileimg = new ImageView(person.getPhoto().getImage());
	         }
	    	 profileimg.setFitHeight(300);
	         profileimg.setPreserveRatio(true);
	         profileimg.setStyle("-fx-border-radius: 20px");
	         VBox imgholder = new VBox(profileimg);
	         Circle clip = new Circle(60);
	         clip.setCenterY(150);
	         clip.setCenterX((((profileimg.getImage().getWidth() * 300 / profileimg.getImage().getHeight() / 2))));
	         profileimg.setTranslateY(-150+60);
	         profileimg.setTranslateX(
	                 -((profileimg.getImage().getWidth() * 300 / profileimg.getImage().getHeight() / 2) - 60));
	         profileimg.setClip(clip);
	         imgholder.setMaxHeight(120);
	         imgholder.setPrefHeight(120);
	         imgholder.setMinHeight(120);
	         imgholder.setMaxWidth(120);
	         imgholder.setMinWidth(120);
	         imgholder.setPrefWidth(120);
	    	 youricon.getChildren().add(imgholder);


		}

	private void showRequestForCompany(JobRequest jr) {
		Person person = pDao.get(jr.getUser().getId()).get();
		jr.setJobOffre(jDao.getJobOfferByid(jr.getJobOffre().getId()).get());
		VBox bigVBox = new VBox();
		HBox headBox = new HBox();
		HBox btnBox = new HBox();
		Image image = person.getPhoto().getImage();
		ImageView profileimg;
		if (image == null) {
			profileimg = new ImageView(new Image("file:src//main//resources//images//user.png"));
		} else {
			profileimg = new ImageView(image);
		}
		profileimg.setVisible(true);

		VBox imgholder = makeicon(profileimg);
		imgholder.setOnMouseClicked(event -> {
			try {
				openprofile(event, person);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		Label username = new Label(person.getFirstName() + " " + person.getLastName() + " AKA " + person.getUserName());
		Button saveButton = new Button("Finish");
		Button fireButton = new Button("Fire");
		Button Acceptbutton = new Button("Accept");
		Button Rejectbutton = new Button("Reject");

		Button downloadpdfButton = new Button("↓ Dowload Resume");
		Text jobappliction = new Text(jr.getJobOffre().getPostion());

		headBox.getChildren().addAll(imgholder, username, btnBox);
		bigVBox.getChildren().addAll(headBox, jobappliction, downloadpdfButton);
		postholder.getChildren().add(bigVBox);
		// Css
		Acceptbutton.getStyleClass().add("aceeptbutton");
		Rejectbutton.getStyleClass().add("rejectButton");
		downloadpdfButton.getStyleClass().add("likebutton");
		saveButton.getStyleClass().add("postButton");
		fireButton.getStyleClass().add("rejectButton");
		Acceptbutton.setOnAction(event -> Accept(jr));
		Rejectbutton.setOnAction(event -> Reject(jr));
		saveButton.setOnAction(event -> Save(jr));
		fireButton.setOnAction(event -> Fire(jr));
		downloadpdfButton.setOnAction(t -> downloadpdf(jDao.getResumeFromRequestById(jr)));
		btnBox.setAlignment(Pos.CENTER_RIGHT);
		headBox.setAlignment(Pos.CENTER_LEFT);
		HBox.setHgrow(btnBox, Priority.ALWAYS);
		username.setTextFill(Color.WHITE);
		username.setStyle("-fx-font-family: \"SansSerif bold\";" + "-fx-font-size: 1em;");
		username.setOnMouseClicked(event -> {
			try {
				openprofile(event, person);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		jobappliction.setStyle("-fx-font-family: \"SansSerif bold\";" + "-fx-font-size: 1em;");
		jobappliction.setFill(Color.WHITE);
		Acceptbutton.setTextFill(Color.WHITE);
		Acceptbutton
				.setStyle("-fx-font-family: \"SansSerif bold\";" + "-fx-font-size: 1em;" + "-fx-font-weight: 900;");
		Rejectbutton.setTextFill(Color.WHITE);
		Rejectbutton
				.setStyle("-fx-font-family: \"SansSerif bold\";" + "-fx-font-size: 1em;" + "-fx-font-weight: 900;");
		downloadpdfButton.setTextFill(Color.WHITE);
		downloadpdfButton
				.setStyle("-fx-font-family: \"SansSerif bold\";" + "-fx-font-size: 1em;" + "-fx-font-weight: 900;");
		saveButton.setTextFill(Color.WHITE);
		saveButton.setStyle("-fx-font-family: \"SansSerif bold\";" + "-fx-font-size: 1em;" + "-fx-font-weight: 900;");
		fireButton.setTextFill(Color.WHITE);
		fireButton.setStyle("-fx-font-family: \"SansSerif bold\";" + "-fx-font-size: 1em;" + "-fx-font-weight: 900;");
		HBox.setMargin(Acceptbutton, new Insets(5, 10, 5, 5));
		HBox.setMargin(Rejectbutton, new Insets(5, 10, 5, 5));
		HBox.setMargin(saveButton, new Insets(5, 10, 5, 5));
		HBox.setMargin(fireButton, new Insets(5, 10, 5, 5));
		HBox.setMargin(username, new Insets(0, 0, 0, 8));
		HBox.setMargin(imgholder, new Insets(0, 0, 0, 5));
		VBox.setMargin(jobappliction, new Insets(10, 0, 10, 15));
		VBox.setVgrow(jobappliction, Priority.ALWAYS);
		Platform.runLater(() -> {
			if (bigVBox.localToScreen(0, 0).getY() < (bigVBox.getScene().getHeight() * 1.2)) {
				profileimg.setVisible(true);
			}
			jobappliction.setWrappingWidth(bigVBox.getWidth() - 40);
		});
		CC.vvalueProperty().addListener((observable, oldValue, newValue) -> {
			if (Math.abs(bigVBox.localToScreen(0, 0).getY()) < (bigVBox.getScene().getHeight() * 1.2)) {
				profileimg.setVisible(true);

			} else {
				profileimg.setVisible(false);
			}
		});
		CC.widthProperty().addListener((observable, oldValue, newValue) -> {
			bigVBox.setPrefWidth(CC.getWidth());
			bigVBox.setMaxWidth(CC.getWidth());
			bigVBox.setMinWidth(CC.getWidth());
		});
		bigVBox.getStyleClass().add("posts");
		bigVBox.setPadding(new Insets(0, 0, 25, 0));
		if (jr.getEtat().equals("Waiting")) {
			btnBox.getChildren().addAll(Acceptbutton, Rejectbutton);
		} else {
			btnBox.getChildren().addAll(saveButton, fireButton);
		}
	}

	private void showRequestForUser(JobRequest jr) {
		jr.setJobOffre(jDao.getJobOfferByid(jr.getJobOffre().getId()).get());
		VBox bigVBox = new VBox();
		HBox headBox = new HBox();
		HBox btnBox = new HBox();
		Media image = jr.getJobOffre().getCompany().getPhoto();
		ImageView profileimg;
		if (image == null) {
			profileimg = new ImageView(new Image("file:src//main//resources//images//user.png"));
		} else {
			profileimg = new ImageView(image.getImage());
		}
		profileimg.setVisible(true);

		VBox imgholder = makeicon(profileimg);
		imgholder.setOnMouseClicked(event -> {
			try {
				openprofile(event, jr.getJobOffre().getCompany());
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		Label username = new Label(cs.getCompanyInfo(jr.getJobOffre().getCompany()).getCompanyName());
		Button cancelButton = new Button("Cancel");


		Button downloadpdfButton = new Button("↓ Dowload Resume");
		Text jobappliction = new Text(jr.getJobOffre().getPostion());

		headBox.getChildren().addAll(imgholder, username, btnBox);
		bigVBox.getChildren().addAll(headBox, jobappliction, downloadpdfButton);
		postholder.getChildren().add(bigVBox);
		// Css

		downloadpdfButton.getStyleClass().add("likebutton");
		cancelButton.getStyleClass().add("postButton");
		cancelButton.setOnAction(event -> Cancel(jr));
		downloadpdfButton.setOnAction(t -> downloadpdf(jDao.getResumeFromRequestById(jr)));
		btnBox.setAlignment(Pos.CENTER_RIGHT);
		headBox.setAlignment(Pos.CENTER_LEFT);
		HBox.setHgrow(btnBox, Priority.ALWAYS);
		username.setTextFill(Color.WHITE);
		username.setStyle("-fx-font-family: \"SansSerif bold\";" + "-fx-font-size: 1em;");
		username.setOnMouseClicked(event -> {
			try {
				openprofile(event, jr.getJobOffre().getCompany());
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		jobappliction.setStyle("-fx-font-family: \"SansSerif bold\";" + "-fx-font-size: 1em;");
		jobappliction.setFill(Color.WHITE);
		cancelButton.setTextFill(Color.WHITE);
		cancelButton
				.setStyle("-fx-font-family: \"SansSerif bold\";" + "-fx-font-size: 1em;" + "-fx-font-weight: 900;");
		downloadpdfButton.setTextFill(Color.WHITE);
		downloadpdfButton
				.setStyle("-fx-font-family: \"SansSerif bold\";" + "-fx-font-size: 1em;" + "-fx-font-weight: 900;");

		HBox.setMargin(cancelButton, new Insets(5, 10, 5, 5));

		HBox.setMargin(username, new Insets(0, 0, 0, 8));
		HBox.setMargin(imgholder, new Insets(0, 0, 0, 5));
		VBox.setMargin(jobappliction, new Insets(10, 0, 10, 15));
		VBox.setVgrow(jobappliction, Priority.ALWAYS);
		Platform.runLater(() -> {
			if (bigVBox.localToScreen(0, 0).getY() < (bigVBox.getScene().getHeight() * 1.2)) {
				profileimg.setVisible(true);
			}
			jobappliction.setWrappingWidth(bigVBox.getWidth() - 40);
		});
		CC.vvalueProperty().addListener((observable, oldValue, newValue) -> {
			if (Math.abs(bigVBox.localToScreen(0, 0).getY()) < (bigVBox.getScene().getHeight() * 1.2)) {
				profileimg.setVisible(true);

			} else {
				profileimg.setVisible(false);
			}
		});
		CC.widthProperty().addListener((observable, oldValue, newValue) -> {
			bigVBox.setPrefWidth(CC.getWidth());
			bigVBox.setMaxWidth(CC.getWidth());
			bigVBox.setMinWidth(CC.getWidth());
		});
		bigVBox.getStyleClass().add("posts");
		bigVBox.setPadding(new Insets(0, 0, 25, 0));
		if (jr.getEtat().equals("Waiting")) {
			btnBox.getChildren().addAll(cancelButton);
		}
		username.setText(username.getText()+"->"+jr.getEtat());

	}
	private void Cancel(JobRequest jr) {
		if(!getpermision("Are you sure you want to Cancel ")) {
			return;
		}
		jDao.deleteJobRequest(jr);
		resetrequests();
	}

	private void Fire(JobRequest jr) {
		if(!getpermision("Are you sure you want to Fire ")) {
			return;
		}
		jDao.updateJobRequest(jr, new JobRequest(jr.getUser(), jr.getJobOffre(), "Fired"));
		resetrequests();
	}

	private void Save(JobRequest jr) {
		if(!getpermision("Are you sure you want to Finish the job for ")) {
			return;
		}
		jDao.updateJobRequest(jr, new JobRequest(jr.getUser(), jr.getJobOffre(), "Finished"));
		resetrequests();

	}

	private void Accept(JobRequest jr) {
		if(!getpermision("Are you sure you want to Accept ")) {
			return;
		}
		jDao.updateJobRequest(jr, new JobRequest(jr.getUser(), jr.getJobOffre(), "Accepted"));
		resetrequests();

	}

	private void Reject(JobRequest jr) {
		if(!getpermision("Are you sure you want to Reject")) {
			return;
		}
		jDao.updateJobRequest(jr, new JobRequest(jr.getUser(), jr.getJobOffre(), "Rejected"));
		resetrequests();
	}

	private boolean getpermision(String text) {
		Alert popup = new Alert(AlertType.CONFIRMATION);
        popup.setTitle("ARE YOU SURE?");
        popup.setHeaderText("");
        popup.setContentText(text);
        popup.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> r=popup.showAndWait();
        if (r.isPresent()) {
			return r.get()==ButtonType.YES;
		}return false;

	}

	private void downloadpdf(byte[] pdf) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save PDF");
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF Files (*.pdf)", "*.pdf");
		fileChooser.getExtensionFilters().add(extFilter);
		File selectedFile = fileChooser.showSaveDialog(new Stage());
		if (selectedFile != null) {
			try (FileOutputStream fos = new FileOutputStream(selectedFile)) {
				fos.write(pdf);
				System.out.println("PDF saved successfully to: " + selectedFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private VBox makeicon(ImageView profileimg) {
		double width;
		System.out.println();
		System.out.println(profileimg.getImage().getWidth());
		if (profileimg.getImage().getWidth() > 300) {
			width = 200;
		} else {
			width = 46;
		}
		profileimg.setFitHeight(width);
		profileimg.setPreserveRatio(true);
		profileimg.setStyle("-fx-border-radius: 20px");
		VBox imgholder = new VBox(profileimg);
		Circle clip = new Circle(23);
		clip.setCenterY(width / 2);
		clip.setCenterX((((profileimg.getImage().getWidth() * width / profileimg.getImage().getHeight() / 2))));
		profileimg.setTranslateY(-(width / 2) + 23);
		profileimg.setTranslateX(
				-((profileimg.getImage().getWidth() * width / profileimg.getImage().getHeight() / 2) - 23));
		profileimg.setClip(clip);
		imgholder.setMaxHeight(46);
		imgholder.setPrefHeight(46);
		imgholder.setMinHeight(46);
		imgholder.setMaxWidth(46);
		imgholder.setMinWidth(46);
		imgholder.setPrefWidth(46);
		return imgholder;
	}


}
