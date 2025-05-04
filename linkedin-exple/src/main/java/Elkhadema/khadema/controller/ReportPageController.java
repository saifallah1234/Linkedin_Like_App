package Elkhadema.khadema.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;

import Elkhadema.khadema.App;
import Elkhadema.khadema.DAO.DAOImplemantation.ReportDAO;
import Elkhadema.khadema.Service.ServiceImplemantation.PostServiceImp;
import Elkhadema.khadema.domain.Post;
import Elkhadema.khadema.util.Session;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class ReportPageController extends NavbarController implements Initializable{
	PostServiceImp ps=new PostServiceImp();
	@FXML
    private ScrollPane CC;

    @FXML
    private HBox HboxforAttachments;

    @FXML
    private ScrollPane Scrollpann;

    @FXML
    private VBox Vboxx1;

    @FXML
    private VBox Vboxx2;

    @FXML
    private Text age;

    @FXML
    private StackPane bigstack;

    @FXML
    private Text description;

    @FXML
    private VBox exemple1;

    @FXML
    private Button foradmin;

    @FXML
    private VBox forperson;

    @FXML
    private Text job;

    @FXML
    private VBox mothersofmother;

    @FXML
    private VBox notifBox;

    @FXML
    private VBox notifList;

    @FXML
    private VBox postholder;

    @FXML
    private VBox postscontainer;

    @FXML
    private TextField searchbar;

    @FXML
    private Text sexe;

    @FXML
    private Text textReport;

    @FXML
    private HBox titlebox;

    @FXML
    private Text username;

    @FXML
    private Text username1;

    @FXML
    private VBox vContacts;

    @FXML
    private HBox vidcontainer;

    @FXML
    private VBox youricon;
    
    @Override 
    public void initialize(URL arg0, ResourceBundle arg1) {
        super.initialize(arg0, arg1);
        ReportDAO.getAllReportedPost().forEach(t->{
        	showReport(t);
        });;
    }

	private void showReport(Post post) {
		 //skolletten
		Button viewbtn=new Button("VIEW");
		HBox btnBox=new HBox(viewbtn);
		Label username=new Label(post.getUser().getUserName()+" POSTED AT "+post.getCreationDate().toString());
		HBox titleBox=new HBox(username,btnBox);
		Text description=new Text(ReportDAO.getdescriptionReportfromPost(post).stream().reduce("", (t, u) -> t+"\n -"+u));
		VBox in=new VBox(description);
		ScrollPane sp=new ScrollPane(in);
		Label report=new Label("REPORTS:");
		VBox descriptionbox=new VBox(report,sp);
		VBox bigbox=new VBox(titleBox,descriptionbox);
		postholder.getChildren().add(bigbox);
		//Css
		description.setFont(Font.font("SansSerif", FontWeight.SEMI_BOLD, 15));
		description.setFill(Color.WHITE);
		report.setFont(Font.font("SansSerif", FontWeight.EXTRA_BOLD, 17));
		report.setTextFill(Color.WHITE);
		sp.setHbarPolicy(ScrollBarPolicy.NEVER);
		sp.getStyleClass().add("notificationPannel");
		VBox.setMargin(sp, new Insets(0, 15, 0, 15));
		username.setFont(Font.font("SansSerif", FontWeight.BOLD, 21));
		username.setTextFill(Color.WHITE);
		HBox.setMargin(report, new Insets(0, 0, 0, 5));
		titleBox.setAlignment(Pos.CENTER_LEFT);
		HBox.setHgrow(btnBox, Priority.ALWAYS);
		HBox.setHgrow(viewbtn, Priority.ALWAYS);
		btnBox.setAlignment(Pos.CENTER_RIGHT);
		viewbtn.setMinWidth(100);
		viewbtn.setFont(Font.font("SansSerif", FontWeight.EXTRA_BOLD, 20));
		viewbtn.setTextFill(Color.valueOf("#0095fe"));
		viewbtn.setStyle("-fx-background-color:white;"
        		+ "-fx-background-radius:50;"
        		+ "-fx-font-size:1.4em;"
        		+ "-fx-font-weight:900;");
		viewbtn.setOnAction(event -> view(post));
		bigbox.getStyleClass().add("posts");
		bigbox.setPadding(new Insets(0, 0, 15, 0));
		sp.setMinHeight(241);
		sp.setPrefHeight(241);
		VBox.setMargin(bigbox, new Insets(15, 0, 0, 0));
        Platform.runLater(() -> {
        	description.setWrappingWidth(bigbox.getWidth()-40);
        });
        bigbox.widthProperty().addListener((observable, oldValue, newValue) -> {
        	description.setWrappingWidth(bigbox.getWidth()-40);
        });
		
	}

	private void view(Post post) {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Elkhadema/khadema/comment.fxml"));
			try {
				root = loader.load();
			} catch (IOException e) {
				e.printStackTrace();
			}
			CommentsPageController commentsPageController = loader.getController();
			ReportDAO.delete(post);
	        commentsPageController.init(ps.getPostById(post));
			stage = App.stage;
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
	        try {
				App.setRoot("comment");
			} catch (IOException e) {
				e.printStackTrace();
			}

	}
	
   
}
