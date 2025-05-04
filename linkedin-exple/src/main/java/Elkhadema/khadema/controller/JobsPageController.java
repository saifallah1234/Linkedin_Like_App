package Elkhadema.khadema.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;


import Elkhadema.khadema.App;
import Elkhadema.khadema.DAO.DAOImplemantation.JobsDAO;
import Elkhadema.khadema.DAO.DAOImplemantation.PersonDAO;
import Elkhadema.khadema.Service.ServiceImplemantation.JobServiceImp;
import Elkhadema.khadema.domain.Company;
import Elkhadema.khadema.domain.JobOffre;
import Elkhadema.khadema.domain.JobRequest;
import Elkhadema.khadema.domain.Person;
import Elkhadema.khadema.util.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class JobsPageController extends NavbarController {
	JobServiceImp jobServiceImp=new JobServiceImp();
	JobsDAO jobsDAO=new JobsDAO();
	 @FXML
	    private Button applybutton;

	    @FXML
	    private VBox areyouhiring;

	    @FXML
	    private Text companyname;

	    @FXML
	    private Text description;

	    @FXML
	    private Text industry;

	    @FXML
	    private VBox joblist;

	    @FXML
	    private Text numberofapllication;

	    @FXML
	    private VBox postholder;

	    @FXML
	    private VBox postscontainer;

	    @FXML
	    private Text testforapplication;
	    @FXML
	    private Text speciality;
	    @FXML
	    private Button uploadbtn;
	    @FXML
	    private Text username;
	    @FXML
	    private TextArea descriptionjob;
	    @FXML
	    private Text website;
	    @FXML
	    private Text jobtitle;
	 @FXML
	    void postMsg(MouseEvent event) {
//ignore
	    }
	 JobServiceImp js=new JobServiceImp();
	 PersonDAO pd=new PersonDAO();
	 JobsDAO jDao=new JobsDAO();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
		postholder.setVisible(false);
		applybutton.setDisable(true);
		if (pd.get(Session.getUser().getId()).isPresent()) {
			areyouhiring.setVisible(false);
			areyouhiring.setDisable(true);
			areyouhiring.setMaxHeight(0);
			areyouhiring.setPrefHeight(0);
			areyouhiring.setMinHeight(0);
			applybutton.setDisable(false);
			testforapplication.setText("You have "+jDao.getJobRequestsByUser(new Person(Session.getUser().getId(), null, null)).stream().filter(t -> t.getEtat().equals("Accepted")).collect(Collectors.toList()).size()+" Accepted Applications");
		}
		else {
			testforapplication.setText("You have "+jDao.getJobRequestsByCompany(new Company(Session.getUser().getId(), null, null)).stream().filter(t -> t.getEtat().equals("Waiting")).collect(Collectors.toList()).size()+" Waiting Applications");
		}
		joblist.getChildren().clear();
		resetjobs();
	}
	public void resetjobs() {
		js.getAllJobOffres().forEach(t -> showjob(t));;
	}
	 public void showjob(JobOffre jb) {
		 //skolletten
		HBox bigVBox=new HBox();
		bigVBox.getStyleClass().add("posts");
		joblist.getChildren().add(bigVBox);
		HBox titlebox=new HBox();
		ImageView img= new ImageView(jb.getCompany().getPhoto().getImage());
		Text jobtitle=new Text(jb.getPostion());
		titlebox.getChildren().add(img);
		Text company=new Text(jb.getCompany().getUserName());
		Text location=new Text(jb.getLocation());
		Text salary= new Text(""+jb.getPayRange()+"DT");
		VBox textbox=new VBox(jobtitle,company,location,salary);
		bigVBox.getChildren().addAll(titlebox,textbox);
		//Css
		img.setFitWidth(50);
		img.setFitHeight(50);
		img.setPreserveRatio(true);
		bigVBox.setStyle("-fx-font-family: \"SansSerif bold\";"
				+ "-fx-font-size: 1em;");
		jobtitle.setFill(Color.WHITE);
		company.setFill(Color.WHITE);
		location.setFill(Color.WHITE);
		salary.setFill(Color.WHITE);
		titlebox.setAlignment(Pos.TOP_CENTER);
		textbox.setAlignment(Pos.CENTER_LEFT);
		HBox.setHgrow(textbox, Priority.ALWAYS);
		HBox.setMargin(img, new Insets(5, 0, 0, 5));
		HBox.setMargin(textbox, new Insets(0, 0, 0, 8));
		textbox.setSpacing(2);
		bigVBox.setMinHeight(100);
		bigVBox.setOnMouseClicked(event -> showjobinfo(jb));
		//logic

	}
	 byte[] pdf;
	private void showjobinfo(JobOffre jb) {
		pdf =null;
		jobtitle.setText(jb.getPostion());
		username.setText(jb.getCompany().getUserName());
		companyname.setText(jb.getCompany().getCompanyName());
		numberofapllication.setText(""+js.getAllJobRequestByCompany(jb.getCompany()).stream().filter(t -> t.getJobOffre().equals(jb)).count()+" applications");
		industry.setText(jb.getCompany().getIndustry());
		speciality.setText(jb.getCompany().getSpeciality());
		description.setText(jb.getCompany().getDescription());
		descriptionjob.setText(jb.getSummary()+"\n"
				+"HIRING TYPE:"+jb.getEmploymentType()+"\n"
						+ "PAY RANGE:"+jb.getPayRange()+"DT Per Month\n"
								+ "LOCATION:"+jb.getLocation());
		postholder.setVisible(true);
		applybutton.setOnAction(event -> CreateRequest(jb,pdf));
		uploadbtn.setOnMouseClicked(event -> {
			FileChooser fileChooser=new FileChooser();
			   fileChooser.setTitle("Choose Media File");
			   fileChooser.getExtensionFilters().addAll(
			            new FileChooser.ExtensionFilter("Media Files", "*.pdf")
			        );

			   File selectFile =fileChooser.showOpenDialog((Stage) ((Node) event.getSource()).getScene().getWindow());
			   String extension=selectFile.getName().substring(selectFile.getName().lastIndexOf(".")+1);

			   if (extension.equals("pdf")) {
				   try (FileInputStream fis = new FileInputStream(selectFile)) {
					   	pdf= new byte[(int) selectFile.length()];
			            fis.read(pdf);
			            uploadbtn.setText(selectFile.getName());
			        } catch (IOException e) {
			            e.printStackTrace();
			        }
			}
			   else {
				System.out.println("given wrong extension");
			}

		});
		List<JobRequest> r=js.getAllJobRequestByUser(Session.getUser()).stream().filter(t -> t.getJobOffre().getId()==jb.getId()).collect(Collectors.toList());
		if (r.size()!=0) {
			applybutton.setDisable(true);
			applybutton.setText(r.get(0).getEtat());
		}
		else {
			applybutton.setDisable(false);
			applybutton.setText("Apply");
		}
	}
	 @FXML
	    void GoToApplicationView(ActionEvent event) throws IOException {
		 App.setRoot("applicationreview");
	 }
	private void CreateRequest(JobOffre jb,byte[] pdf) {
		if (!checkpdf(pdf)) {
			return;
		}
		JobRequest jr=new JobRequest(Session.getUser(), jb, "Waiting");
		jr.setPdf(pdf);
		jDao.saveJobRequest(jr);
		applybutton.setText("Sent");
		applybutton.setDisable(true);

	}

    private boolean checkpdf(byte[] pdf) {
		if (pdf==null||pdf.length==0) {
			Alert popup=new Alert(AlertType.ERROR);
			popup.setTitle("INPUT ERROR");
			popup.setHeaderText("PLEASE ENTER THE RESUME");
			popup.showAndWait();
			return false;
		}
		return true;
	}
	@FXML
    void Gotojoboffercreation(ActionEvent event) throws IOException {
    	App.setRoot("hiringform");
    }

}
