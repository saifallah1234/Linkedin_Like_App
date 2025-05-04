package Elkhadema.khadema.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import Elkhadema.khadema.App;
import Elkhadema.khadema.DAO.DAOImplemantation.ReportDAO;
import Elkhadema.khadema.DAO.DAOImplemantation.UserDAO;
import Elkhadema.khadema.Service.ServiceImplemantation.AdminServiceImp;
import Elkhadema.khadema.Service.ServiceImplemantation.FollowServiceImp;
import Elkhadema.khadema.Service.ServiceImplemantation.PostServiceImp;
import Elkhadema.khadema.Service.ServiceImplemantation.UserServiceImp;
import Elkhadema.khadema.Service.ServiceInterfaces.FollowService;
import Elkhadema.khadema.Service.ServiceInterfaces.UserService;
import Elkhadema.khadema.domain.Media;
import Elkhadema.khadema.domain.Person;
import Elkhadema.khadema.domain.Post;
import Elkhadema.khadema.domain.Reaction;
import Elkhadema.khadema.domain.User;
import Elkhadema.khadema.util.MediaChooser;
import Elkhadema.khadema.util.Session;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CommentsPageController extends NavbarController {
	private Stage stage;
	private Scene scene;
	private Post commentedpost;
	private Parent root;
	FollowService followService = new FollowServiceImp();
	UserService userService = new UserServiceImp();
    AdminServiceImp as=new AdminServiceImp();
	UserDAO userDAO = new UserDAO();
	User session = Session.getUser();
	PostServiceImp ps = new PostServiceImp();
	@FXML
	private ScrollPane CC;
	List<Media> attachedMedias = new ArrayList<Media>();
	@FXML
	private HBox HboxforAttachments;
	@FXML
	private Button buttontoaddattach;
	@FXML
	private VBox commentedPostcontainer;
	@FXML
	private TextArea postcontent;
	@FXML
	private StackPane bigstack;

	@FXML
	private Text replyindexing;
	@FXML
	private HBox vidcontainer;
	@FXML
	private VBox comment_holder;

	boolean loadingMorePosts = false;
	int postindex = 15;
	int maxPosts = 20;
	int sum = 0;
	int loadPosts = 0;


	public void resetComment() {
		comment_holder.getChildren().clear();
		ps.getPostComments(commentedpost).forEach(t -> showpost(t));

	}

	@FXML
	void AddMediabutton(ActionEvent event) {
		Media m = MediaChooser.Choose(event);
		if (m.getMediatype().equals("img")) {
			attachedMedias.add(m);
			ImageView img = new ImageView(m.getImage());
			HboxforAttachments.getChildren().add(img);
			HboxforAttachments.getChildren().forEach(t -> ((ImageView) t)
					.setFitWidth(HboxforAttachments.getWidth() / (double) attachedMedias.size() / 3));
			img.setPreserveRatio(true);
		} else {
			try {
				MediaPlayer mediaPlayer = m.getVideo();
				MediaView mediaView = new MediaView(mediaPlayer);
				attachedMedias.add(m);
				vidcontainer.getChildren().add(mediaView);
				mediaView.setFitWidth(vidcontainer.getWidth() / 2);
				mediaView.setPreserveRatio(true);

			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	public String getlink() {
		String link = commentedpost.getUser().getUserName();
		Post post = commentedpost;
		while (post.getParentPostId() != 0) {
			post = ps.getPostById(new Post(post.getParentPostId()));
			link = post.getUser().getUserName() + "/" + link;
		}
		return link;
	}

	@FXML
	void postCommentButton(ActionEvent event) {
		String content = postcontent.getText();
		if (content.length() > 0) {
			Post post = new Post(session, content, null, commentedpost.getId(), "text", null, 0);
			post.setPostMedias(attachedMedias);
			ps.makePost(post);
			System.out.println("post made");
			attachedMedias = new ArrayList<Media>();
			resetComment();
			HboxforAttachments.getChildren().clear();
			postcontent.setText("");
		}
	}

	public VBox showpost(Post post) {
		Image image = post.getUser().getPhoto().getImage();
		ImageView profileimg;
		if (image == null) {
			profileimg = new ImageView(new Image("file:src//main//resources//images//user.png"));
		} else {
			profileimg = new ImageView(image);
		}
		profileimg.setVisible(false);
		VBox imgholder = makeicon(profileimg);
		Text profilename = new Text(post.getUser().getUserName());
		profilename.setFont(Font.font("SansSerif", 15));
		profilename.setTranslateX(5);
		profilename.setFill(Color.WHITE);
		HBox profilebar = new HBox(imgholder, profilename);
		profilebar.setSpacing(5);
		profilebar.setAlignment(Pos.CENTER_LEFT);
		Text postscontent = new Text(post.getContent());
		postscontent.setDisable(true);
		postscontent.setFill(Color.WHITE);
		postscontent.setOpacity(1);
		postscontent.setFont(Font.font(13));
		postscontent.getStyleClass().add("postTxtField");
		postscontent.setStyle("-fx-border-width: 0;");
		List<HBox> displayedimges = displayimages(post);

		displayedimges.forEach(t -> {
			t.setSpacing(5);
			t.setAlignment(Pos.TOP_CENTER);
			t.setVisible(false);
		});
		VBox iMGHOLDER = new VBox(displayedimges.toArray(new HBox[0]));
		iMGHOLDER.getStyleClass().add("postTxtField");
		iMGHOLDER.setAlignment(Pos.CENTER);
		iMGHOLDER.setSpacing(5);
		Button SettingsButton=new Button();
        HBox Settinghbox=new HBox(SettingsButton);
        HBox.setHgrow(Settinghbox,Priority.ALWAYS);
        Settinghbox.setAlignment(Pos.CENTER_RIGHT);
        SettingsButton.setStyle("-fx-background-color:white;"
        		+ "-fx-background-radius:50;"
        		+ "-fx-font-size:1.4em;"
        		+ "-fx-font-weight:900;");
        profilebar.getChildren().add(Settinghbox);
        HBox.setMargin(SettingsButton, new Insets(0,5,0,0));
       
        if (!as.isAdmin(Session.getUser())) {
        	SettingsButton.setText("REPORT");
        	SettingsButton.setTextFill(Color.valueOf("#0095fe"));
        	 SettingsButton.setOnAction(event -> report(post));
        }
        else {
        	SettingsButton.setText("DELETE");
        	SettingsButton.setTextFill(Color.RED);
        	SettingsButton.setOnAction(event -> {if(delete(post))resetComment();});
		}
        
		Optional<MediaPlayer> mediaPlayer = post.getPostMedias().stream().filter(t -> t.getMediatype().equals("vid"))
				.map(Elkhadema.khadema.domain.Media::getVideo).findFirst();
		MediaPlayer mp[] = { null };
		StackPane videopane = new StackPane();
		if (mediaPlayer.isPresent()) {
			mp[0] = mediaPlayer.get();
			ImageView playbutton = new ImageView(new Image("file:src//main//resources//images//playbutton.png"));
			playbutton.setFitWidth(50);
			playbutton.setPreserveRatio(true);
			playbutton.setOnMouseClicked(event -> {
				playVideo(mp[0]);
			});
			videopane.getChildren().add(playbutton);
		}

		MediaView mediaView = new MediaView(mp[0]);
		mediaView.setVisible(false);
		videopane.getChildren().add(0, mediaView);

		Text likenumber = new Text("" + ps.getPostReactions(post).size());
		likenumber.setFont(Font.font(16));
		likenumber.setFill(Color.WHITE);
		Button likebutton = new Button("like ♥");
		AtomicBoolean isliked = new AtomicBoolean(false);
		likebutton.setOnAction(event -> {
			likeapost(post, isliked, likenumber);
		});
		likebutton.getStyleClass().add("likebutton");
		likebutton.setFont(Font.font(19));
		likebutton.setTextFill(Color.WHITE);
		Text commentnumber = new Text("" + ps.getPostComments(post).size());
		commentnumber.setFont(Font.font(16));
		commentnumber.setFill(Color.WHITE);
		Button commentbutton = new Button("comments ☁");
		commentbutton.getStyleClass().add("likebutton");
		commentbutton.setFont(Font.font(19));
		commentbutton.setTextFill(Color.WHITE);
		commentbutton.setOnAction(event -> {
			try {
				commentToPost(post);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		HBox likeandcommentBox = new HBox(likenumber, likebutton, commentnumber, commentbutton);
		likeandcommentBox.setAlignment(Pos.CENTER_LEFT);
		likeandcommentBox.setStyle("-fx-padding: 0 0 0 10px;");
		HBox.setMargin(likebutton, new Insets(0, 11, 0, 11));
		HBox.setMargin(profileimg, new Insets(0, 5, 0, 8));
		HBox.setMargin(commentnumber, new Insets(0, 5, 0, 5));
		HBox.setMargin(commentbutton, new Insets(0, 5, 0, 5));
		likeandcommentBox.setTranslateX(5);
		VBox posts = new VBox(profilebar, postscontent, iMGHOLDER, videopane, likeandcommentBox);
		VBox lastlayerBox = new VBox(posts);
		lastlayerBox.setFillWidth(true);
		VBox.setMargin(postscontent, new Insets(5, 0, 5, 10));
		VBox.setMargin(posts, new Insets(2.5f, 0, 2.5f, 0));
		posts.getStyleClass().add("posts");

		postscontent.setWrappingWidth(comment_holder.getWidth());
		posts.setMinWidth(CC.getWidth() - 50);
		mediaView.setFitWidth(CC.getWidth() - 52);
		mediaView.setPreserveRatio(true);
		CC.widthProperty().addListener((observable, oldValue, newValue) -> {
			posts.setMinWidth(CC.getWidth() - 50);
			mediaView.setFitWidth(CC.getWidth() - 52);
			postscontent.setWrappingWidth(CC.getWidth());

		});
		Platform.runLater(() -> {
			if (posts.localToScreen(0, 0).getY() < (posts.getScene().getHeight() * 1.2)) {
				profileimg.setVisible(true);
				displayedimges.forEach(t -> {
					t.setVisible(true);
				});
				mediaView.setVisible(true);

			}
		});
		CC.vvalueProperty().addListener((observable, oldValue, newValue) -> {
			if (Math.abs(posts.localToScreen(0, 0).getY()) < (posts.getScene().getHeight() * 1.2)) {
				profileimg.setVisible(true);
				displayedimges.forEach(t -> {
					t.setVisible(true);
				});
				mediaView.setVisible(true);

			} else {
				profileimg.setVisible(false);
				displayedimges.forEach(t -> {
					t.setVisible(false);
				});
				mediaView.setVisible(false);
			}
		});
		comment_holder.getChildren().add(posts);
		return posts;

	}
    private boolean delete(Post post) {
    	Alert popup=new Alert(AlertType.CONFIRMATION);
    	System.out.println("houni");
    	popup.setTitle("ARE YOU SURE");
    	popup.setHeaderText("ARE YOU SURE TO DELETE THIS POST");
    	popup.setContentText("");
    	Optional<ButtonType> r=popup.showAndWait();
    	if (r.isPresent()) {
    		if (r.get()==ButtonType.OK) {
    	    	
				ps.removePost(post);
				return true;
			}
    	}
    	return false;
	}

	private void  report(Post post) {
    	TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Reporting");
        dialog.setHeaderText("Please Tell us what is wrong:");
        dialog.setContentText("Description:");
        Optional<String> result = dialog.showAndWait();
        if(result.isPresent()) {
        	try {
				ReportDAO.save(post, result.get());
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
	}

	private boolean isPlayed = false;

	private void playVideo(MediaPlayer mp) {
		VBox background = new VBox();
		background.setStyle("-fx-background-color: rgba(50, 50, 50, 0.7);");
		MediaView mediaView = new MediaView(mp);
		Button play = new Button("►");
		play.setStyle("-fx-background-color: #0095fe;");
		Label duration = new Label(Duration.ZERO.toString());
		duration.setTextFill(Color.WHITE);
		mediaView.setFitWidth(mp.getMedia().getWidth());
		mediaView.setFitHeight(mp.getMedia().getHeight());

		Slider slider = new Slider();
		Duration totalDuration = mp.getMedia().getDuration();
		slider.setMax(totalDuration.toSeconds());
		duration.setText("Duration: 00:00 / " + (int) mp.getMedia().getDuration().toMinutes() + ":"
				+ (int) mp.getMedia().getDuration().toSeconds());
		mediaView.setPreserveRatio(true);
		slider.setOnMousePressed(
				event -> mp.seek(Duration.seconds(slider.getValue() / 100 * mp.getMedia().getDuration().toSeconds())));
		background.setOnMouseClicked(event -> {
			bigstack.getChildren().remove(background);
			mp.stop();
		});
		mp.currentTimeProperty().addListener(((observableValue, oldValue, newValue) -> {
			slider.setValue(newValue.toSeconds() / mp.getMedia().getDuration().toSeconds() * 100);
			duration.setText("Duration: " + (int) mp.getCurrentTime().toMinutes() + ":"
					+ (int) mp.getCurrentTime().toSeconds() + " / " + (int) mp.getMedia().getDuration().toMinutes()
					+ ":" + (int) mp.getMedia().getDuration().toSeconds());
		}));

		HBox playbuttons = new HBox(play, slider, duration);
		play.setFont(Font.font(19));
		play.setTextFill(Color.WHITE);
		play.setMaxHeight(40);
		playbuttons.setStyle("-fx-background-color: rgba(30, 33, 31,0.5)");
		playbuttons.setMaxWidth(mp.getMedia().getWidth());
		playbuttons.setAlignment(Pos.CENTER_LEFT);
		playbuttons.setMaxHeight(40);
		playbuttons.setMinHeight(40);
		playbuttons.setTranslateY(-40);
		playbuttons.setSpacing(5);
		playbuttons.setPadding(new Insets(0, 5, 0, 1));
		FadeTransition fadeIn = new FadeTransition(Duration.millis(1000), playbuttons);
		fadeIn.setFromValue(0.0);
		fadeIn.setToValue(1.0);

		FadeTransition fadeOut = new FadeTransition(Duration.millis(1000), playbuttons);
		fadeOut.setFromValue(1.0);
		fadeOut.setToValue(0.0);
		mediaView.setOnMouseEntered(event -> {
			fadeIn.play();
		});
		playbuttons.setOnMouseEntered(event -> {
			fadeIn.play();
		});
		playbuttons.setOnMouseExited(event -> fadeOut.play());
		mediaView.setOnMouseExited(event -> fadeOut.play());
		HBox.setHgrow(slider, Priority.ALWAYS);
		play.setOnAction(event -> {
			if (!isPlayed) {
				play.setText("⏸");
				mp.play();
				isPlayed = true;
			} else {
				play.setText("►");
				mp.pause();
				isPlayed = false;
			}
		});
		background.getChildren().addAll(mediaView, playbuttons);

		background.setAlignment(Pos.CENTER);
		bigstack.getChildren().add(background);
		System.out.println(mediaView.getFitWidth());
	}

	public List<HBox> displayimages(Post post) {
		List<Image> imgs = post.getPostMedias().stream().map(Elkhadema.khadema.domain.Media::getImage)
				.filter(t -> t != null).collect(Collectors.toList());
		List<HBox> imgsview = new ArrayList<HBox>();
		List<ImageView> imgViews = new ArrayList<ImageView>();

		ImageView tempimg = new ImageView();
		int displayforthree = imgs.size() / 3;
		for (int i = 0; i < displayforthree; i++) {
			for (int j = i; j < i + 3; j++) {
				tempimg = new ImageView(imgs.get(j));
				tempimg.setFitWidth((CC.getWidth() - 52) / 3);

				tempimg.setPreserveRatio(true);
				imgViews.add(tempimg);
				HBox.setHgrow(tempimg, javafx.scene.layout.Priority.ALWAYS);
			}
			imgsview.add(new HBox(imgViews.toArray(new ImageView[0])));

		}
		imgViews.forEach(t -> {
			CC.widthProperty().addListener((observable, oldValue, newValue) -> {
				System.out.println(t.getFitWidth());
				t.setFitWidth((CC.getWidth() - 52) / 3);
			});
		});
		imgViews = new ArrayList<ImageView>();
		for (int i = displayforthree * 3; i < imgs.size(); i++) {
			tempimg = new ImageView(imgs.get(i));
			tempimg.setFitWidth((CC.getWidth() - 50) / (imgs.size() - displayforthree * 3));

			tempimg.setPreserveRatio(true);
			imgViews.add(tempimg);
		}
		imgViews.stream().skip(displayforthree * 3).forEach(t -> {
			CC.widthProperty().addListener((observable, oldValue, newValue) -> {
				System.out.println(t.getFitWidth());
				t.setFitWidth((CC.getWidth() - 50) / (imgs.size() - displayforthree * 3));
			});
		});

		imgsview.add(new HBox(imgViews.toArray(new ImageView[0])));
		return imgsview;
	}

	public void commentToPost(Post post) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Elkhadema/khadema/comment.fxml"));
		root = loader.load();
		CommentsPageController commentsPageController = loader.getController();
		commentsPageController.init(post);
		stage = App.stage;
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		App.setRoot("comment");
	}

	public void likeapost(Post post, AtomicBoolean isliked, Text likenumber) {
		if (isliked.get()) {
			ps.getPostReactions(post).stream()
					.filter(t -> t.getUser().getUserName().compareTo(session.getUserName()) == 0)
					.forEach(t -> ps.removeReactionFromPost(post, t));
			likenumber.setText("" + ps.getPostReactions(post).size());
			isliked.set(false);
		} else {

			Reaction r = new Reaction(session, post, "like", new Date());
			ps.addReactionPost(post, r);
			likenumber.setText("" + ps.getPostReactions(post).size());
			isliked.set(true);
			;
		}
	}

	private VBox makeicon(ImageView profileimg) {
		profileimg.setFitHeight(200);
		profileimg.setPreserveRatio(true);
		VBox imgholder = new VBox(profileimg);
		Circle clip = new Circle(35);
		clip.setCenterY(100);
		clip.setCenterX((((profileimg.getImage().getWidth() * 200 / profileimg.getImage().getHeight() / 2))));
		profileimg.setTranslateY(-65);
		profileimg.setTranslateX(
				-((profileimg.getImage().getWidth() * 200 / profileimg.getImage().getHeight() / 2) - 35));
		profileimg.setClip(clip);
		imgholder.setMaxHeight(70);
		imgholder.setPrefHeight(70);
		imgholder.setMinHeight(70);
		imgholder.setMaxWidth(70);
		imgholder.setMinWidth(70);
		imgholder.setPrefWidth(70);
		return imgholder;
	}

	public void init(Post post) {
		super.initialize(null, null);
		commentedpost=post;
		setupparentpost();
		initContacts();
		replyindexing.setText("Replying To " + getlink());
		Platform.runLater(() -> {
			resetComment();
		});
	}

	private void setupparentpost() {
		Image image = commentedpost.getUser().getPhoto().getImage();
		ImageView profileimg;
		if (image == null) {
			profileimg = new ImageView(new Image("file:src//main//resources//images//user.png"));
		} else {
			profileimg = new ImageView(image);
		}
		profileimg.setVisible(false);
		VBox imgholder = makeicon(profileimg);
		Text profilename = new Text(commentedpost.getUser().getUserName());
		profilename.setFont(Font.font("SansSerif", 15));
		profilename.setTranslateX(5);
		profilename.setFill(Color.WHITE);
		HBox profilebar = new HBox(imgholder, profilename);
		profilebar.setSpacing(5);
		profilebar.setAlignment(Pos.CENTER_LEFT);
		Text postscontent = new Text(commentedpost.getContent());
		postscontent.setDisable(true);
		postscontent.setFill(Color.WHITE);
		postscontent.setOpacity(1);
		postscontent.setFont(Font.font(13));
		postscontent.getStyleClass().add("postTxtField");
		postscontent.setStyle("-fx-border-width: 0;");
		List<HBox> displayedimges = displayimages(commentedpost);

		displayedimges.forEach(t -> {
			t.setSpacing(5);
			t.setAlignment(Pos.TOP_CENTER);
			t.setVisible(false);
		});
		Button SettingsButton=new Button();
        HBox Settinghbox=new HBox(SettingsButton);
        HBox.setHgrow(Settinghbox,Priority.ALWAYS);
        Settinghbox.setAlignment(Pos.CENTER_RIGHT);
        SettingsButton.setStyle("-fx-background-color:white;"
        		+ "-fx-background-radius:50;"
        		+ "-fx-font-size:1.4em;"
        		+ "-fx-font-weight:900;");
        profilebar.getChildren().add(Settinghbox);
        HBox.setMargin(SettingsButton, new Insets(0,5,0,0));
       
        if (!as.isAdmin(Session.getUser())) {
        	SettingsButton.setText("REPORT");
        	SettingsButton.setTextFill(Color.valueOf("#0095fe"));
        	 SettingsButton.setOnAction(event -> report(commentedpost));
        }
        else {
        	SettingsButton.setText("DELETE");
        	SettingsButton.setTextFill(Color.RED);
        	SettingsButton.setOnAction(event -> {if(delete(commentedpost))
				try {
					goHome();
				} catch (IOException e) {
					e.printStackTrace();
				}});

		}
        
		VBox iMGHOLDER = new VBox(displayedimges.toArray(new HBox[0]));
		iMGHOLDER.getStyleClass().add("postTxtField");
		iMGHOLDER.setAlignment(Pos.CENTER);
		iMGHOLDER.setSpacing(5);

		Optional<MediaPlayer> mediaPlayer = commentedpost.getPostMedias().stream()
				.filter(t -> t.getMediatype().equals("vid"))
				.map(Elkhadema.khadema.domain.Media::getVideo).findFirst();
		MediaPlayer mp[] = { null };
		StackPane videopane = new StackPane();
		if (mediaPlayer.isPresent()) {
			mp[0] = mediaPlayer.get();
			ImageView playbutton = new ImageView(new Image("file:src//main//resources//images//playbutton.png"));
			playbutton.setFitWidth(50);
			playbutton.setPreserveRatio(true);
			playbutton.setOnMouseClicked(event -> {
				playVideo(mp[0]);
			});
			videopane.getChildren().add(playbutton);
		}

		MediaView mediaView = new MediaView(mp[0]);
		mediaView.setVisible(false);
		videopane.getChildren().add(0, mediaView);

		Text likenumber = new Text("" + ps.getPostReactions(commentedpost).size());
		likenumber.setFont(Font.font(16));
		likenumber.setFill(Color.WHITE);
		Button likebutton = new Button("like ♥");
		AtomicBoolean isliked = new AtomicBoolean(false);
		likebutton.setOnAction(event -> {
			likeapost(commentedpost, isliked, likenumber);
		});
		likebutton.getStyleClass().add("likebutton");
		likebutton.setFont(Font.font(19));
		likebutton.setTextFill(Color.WHITE);
		Text commentnumber = new Text("" + ps.getPostComments(commentedpost).size());
		commentnumber.setFont(Font.font(16));
		commentnumber.setFill(Color.WHITE);
		Button commentbutton = new Button("comments ☁");
		commentbutton.getStyleClass().add("likebutton");
		commentbutton.setFont(Font.font(19));
		commentbutton.setTextFill(Color.WHITE);
		commentbutton.setOnAction(event -> {
			try {
				commentToPost(commentedpost);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		HBox likeandcommentBox = new HBox(likenumber, likebutton, commentnumber, commentbutton);
		likeandcommentBox.setAlignment(Pos.CENTER_LEFT);
		likeandcommentBox.setStyle("-fx-padding: 0 0 0 10px;");
		HBox.setMargin(likebutton, new Insets(0, 11, 0, 11));
		HBox.setMargin(profileimg, new Insets(0, 5, 0, 8));
		HBox.setMargin(commentnumber, new Insets(0, 5, 0, 5));
		HBox.setMargin(commentbutton, new Insets(0, 5, 0, 5));
		likeandcommentBox.setTranslateX(5);
		VBox posts = new VBox(profilebar, postscontent, iMGHOLDER, videopane, likeandcommentBox);
		VBox lastlayerBox = new VBox(posts);
		lastlayerBox.setFillWidth(true);
		VBox.setMargin(postscontent, new Insets(5, 0, 5, 10));
		VBox.setMargin(posts, new Insets(2.5f, 0, 2.5f, 0));
		posts.getStyleClass().add("posts");

		postscontent.setWrappingWidth(comment_holder.getWidth());
		posts.setMinWidth(CC.getWidth() - 50);
		mediaView.setFitWidth(CC.getWidth() - 52);
		mediaView.setPreserveRatio(true);
		CC.widthProperty().addListener((observable, oldValue, newValue) -> {
			posts.setMinWidth(CC.getWidth() - 50);
			mediaView.setFitWidth(CC.getWidth() - 52);
			postscontent.setWrappingWidth(CC.getWidth());

		});
		Platform.runLater(() -> {

			if (posts.localToScreen(0, 0).getY() < (posts.getScene().getHeight() * 1.2)) {
				profileimg.setVisible(true);
				displayedimges.forEach(t -> {
					t.setVisible(true);
				});
				mediaView.setVisible(true);

			}
		});
		CC.vvalueProperty().addListener((observable, oldValue, newValue) -> {
			if (Math.abs(posts.localToScreen(0, 0).getY()) < (posts.getScene().getHeight() * 1.2)) {
				profileimg.setVisible(true);
				displayedimges.forEach(t -> {
					t.setVisible(true);
				});
				mediaView.setVisible(true);

			} else {
				profileimg.setVisible(false);
				displayedimges.forEach(t -> {
					t.setVisible(false);
				});
				mediaView.setVisible(false);
			}
		});
		commentedPostcontainer.getChildren().clear();
		commentedPostcontainer.getChildren().add(posts);

	}


	public void openprofile(MouseEvent event, User tmp) throws IOException {
		User user = tmp;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Elkhadema/khadema/mainpage.fxml"));
		ResumeController profileController = loader.getController();
		profileController.init((Person) user);
		root = loader.load();
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	public  Post getCommentedpost() {
		return commentedpost;
	}

	public void setCommentedpost(Post commentedpost) {
		this.commentedpost = commentedpost;
	}

}
