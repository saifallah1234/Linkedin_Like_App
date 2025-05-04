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
import Elkhadema.khadema.Service.ServiceImplemantation.AdminServiceImp;
import Elkhadema.khadema.Service.ServiceImplemantation.CompanyServiceImp;
import Elkhadema.khadema.Service.ServiceImplemantation.FollowServiceImp;
import Elkhadema.khadema.Service.ServiceImplemantation.PostServiceImp;
import Elkhadema.khadema.Service.ServiceImplemantation.SearchServiceImp;
import Elkhadema.khadema.Service.ServiceImplemantation.UserServiceImp;
import Elkhadema.khadema.domain.Company;
import Elkhadema.khadema.domain.Person;
import Elkhadema.khadema.domain.Post;
import Elkhadema.khadema.domain.Reaction;
import Elkhadema.khadema.domain.User;
import Elkhadema.khadema.util.Session;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import javafx.util.Duration;

public class SearchPage extends NavbarController {
	SearchServiceImp ss=new SearchServiceImp();
	CompanyServiceImp cs=new CompanyServiceImp();
	PostServiceImp ps=new PostServiceImp();
	FollowServiceImp fs=new FollowServiceImp();
	UserServiceImp us=new UserServiceImp();
    AdminServiceImp as=new AdminServiceImp();

    @FXML
    private ButtonBar listContact;
    @FXML
    private StackPane bigstack;

    @FXML
    private VBox postholder;
    @FXML
    private ScrollPane CC;

    @FXML
    private VBox postscontainer;

    @FXML
    private VBox postscontainer1;

    @FXML
    private TextField searchbar;


    @FXML
    private ImageView yourpicture;
    @FXML
    private VBox forperson;
    @FXML
    private Text sexe;

    @FXML
    private Text username;

    @FXML
    private Text job;
    @FXML
    private VBox youricon;
    @FXML
    private Text age;

    String searchString;
	public void init(String string) {
        searchString=string;
        super.initialize(null, null);
		if(!cs.isCompany(Session.getUser())) {
        	miniprofilesetup();
        	forperson.setVisible(true);
    	}
    	else {
    		forperson.setVisible(false);
		}
		initContacts();
		ss.searchByUsers(searchString).forEach((t)->{
			showUser(t);
		});;
		ss.searchByPosts(searchString).forEach(t -> showpost(t));

	}
	private void miniprofilesetup() {
    	Person person=((Person)us.getUserById(Session.getUser()));
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

	private void showUser(User user) {
		VBox bigVBox=new VBox();
		HBox headBox=new HBox();
		HBox btnBox=new HBox();
		Image image = user.getPhoto().getImage();
        ImageView profileimg;
        if (image == null) {
            profileimg = new ImageView(new Image("file:src//main//resources//images//user.png"));
        } else {
            profileimg = new ImageView(image);
        }
        profileimg.setVisible(true);

		VBox imgholder = makeicon(profileimg);
		imgholder.setOnMouseClicked( event -> {
	                try {
	                    openprofile(event, user);
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }});
		Text username=new Text();
		Button Followbtn=new Button("+");
		Text abouttext=new Text();
		ScrollPane about=new ScrollPane(abouttext);
        if (!user.equals(Session.getUser())) {
            btnBox.getChildren().add(Followbtn);
        }
		if (fs.isFollowing(user,Session.getUser())) {
			Followbtn.setText("-");
		}
		headBox.getChildren().addAll(imgholder,username,btnBox);
		Followbtn.setOnAction(event -> followuser(user,Followbtn));
		bigVBox.getChildren().addAll(headBox,about);
		postholder.getChildren().add(bigVBox);
		if (!cs.isCompany(user)) {
			Person p= (Person) us.getUserById(user);
			username.setText(p.getUserName());
			abouttext.setText(p.getAbout());
		}
		else {
			Company c=cs.getCompanyInfo(new Company(user.getId(), null, null));
			username.setText(c.getUserName()+"(Company)");
			abouttext.setText(c.getDescription());
		}
		//Css
		Followbtn.getStyleClass().add("postButton");
		btnBox.setAlignment(Pos.CENTER_RIGHT);
		headBox.setAlignment(Pos.CENTER_LEFT);
		VBox.setVgrow(btnBox, Priority.ALWAYS);
		username.setFill(Color.WHITE);
		username.setStyle("-fx-font-family: \"SansSerif bold\";"
				+ "-fx-font-size: 1em;");
		username.setOnMouseClicked( event -> {
	                try {
	                    openprofile(event, user);
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }});

		abouttext.setStyle("-fx-font-family: \"SansSerif bold\";"
				+ "-fx-font-size: 1em;");
		abouttext.setFill(Color.WHITE);
		Followbtn.setTextFill(Color.WHITE);
		Followbtn.setStyle("-fx-font-family: \"SansSerif bold\";"
				+ "-fx-font-size: 1em;"
				+ "-fx-font-weight: 900;"
				+ "-fx-background-radius:50;");
		Followbtn.setPrefWidth(30);
		Followbtn.setMinWidth(30);
		Followbtn.setPrefHeight(30);
		Followbtn.setMinHeight(30);
		Followbtn.setWrapText(true);
		HBox.setMargin(Followbtn, new Insets(5, 10, 5, 5));
		HBox.setMargin(imgholder, new Insets(0, 0, 0, 5));
		VBox.setMargin(about, new Insets(0, 0, 0, 15));
		VBox.setVgrow(about, Priority.ALWAYS);
		about.getStyleClass().add("postTxtField");
		about.setHbarPolicy(ScrollBarPolicy.NEVER);
		about.setPrefViewportHeight(300);
        Platform.runLater(() -> {
        	if(abouttext.getLayoutBounds().getHeight()<70) {
        		about.setPrefHeight(abouttext.getLayoutBounds().getHeight()+5);
            	about.setMinHeight(abouttext.getLayoutBounds().getHeight()+5);
        	}
        	else {
        		about.setPrefHeight(70);
            	about.setMinHeight(70);
			}
            if (bigVBox.localToScreen(0, 0).getY() < (bigVBox.getScene().getHeight() * 1.2)) {
                profileimg.setVisible(true);
            }
        	abouttext.setWrappingWidth(bigVBox.getWidth()-40);
        });
        about.heightProperty().addListener((observable, oldValue, newValue) -> {
        	if(abouttext.getLayoutBounds().getHeight()<70) {
        		about.setPrefHeight(abouttext.getLayoutBounds().getHeight()+5);
            	about.setMinHeight(abouttext.getLayoutBounds().getHeight()+5);
        	}
        	else {
        		about.setPrefHeight(70);
            	about.setMinHeight(70);
			}
        });
        CC.vvalueProperty().addListener((observable, oldValue, newValue) -> {
            if (Math.abs(about.localToScreen(0, 0).getY()) < (about.getScene().getHeight() * 1.2)) {
                profileimg.setVisible(true);

            } else {
                profileimg.setVisible(false);
            }
        });
		about.setContent(abouttext);
		about.setPrefHeight(100);
		about.widthProperty().addListener((observable, oldValue, newValue) -> abouttext.setWrappingWidth(bigVBox.getWidth()-40));
		bigVBox.getStyleClass().add("posts");
		bigVBox.setPadding(new Insets(0, 0, 25, 0));
	}
	private void followuser(User user,Button btn) {
		if (fs.isFollowing(user,Session.getUser())) {
			fs.unFollow(user,Session.getUser());
			btn.setText("+");
		}
		else {
			fs.Follow(user, Session.getUser());
			btn.setText("-");
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

        HBox.setMargin(SettingsButton, new Insets(0,5,0,0));
       
        profilebar.getChildren().add(Settinghbox);
        
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

        postscontent.setWrappingWidth(postholder.getWidth());
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
        if (!as.isAdmin(Session.getUser())) {
        	SettingsButton.setText("REPORT");
        	SettingsButton.setTextFill(Color.valueOf("#0095fe"));
        	 SettingsButton.setOnAction(event -> report(post));
        }
        else {
        	SettingsButton.setText("DELETE");
        	SettingsButton.setTextFill(Color.RED);
        	SettingsButton.setOnAction(event -> delete(post,posts));
		}
        postholder.getChildren().add(posts);
        return posts;
    }
    private void delete(Post post,VBox posts) {
    	Alert popup=new Alert(AlertType.CONFIRMATION);
    	System.out.println("houni");
    	popup.setTitle("ARE YOU SURE");
    	popup.setHeaderText("ARE YOU SURE TO DELETE THIS POST");
    	popup.setContentText("");
    	Optional<ButtonType> r=popup.showAndWait();
    	if (r.isPresent()) {
    		if (r.get()==ButtonType.OK) {
    	    	
				ps.removePost(post);
				posts.setVisible(false);
				posts.setMinHeight(0);
				posts.setMaxHeight(0);
				posts.setPrefHeight(0);
			}
    	}
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
	
	boolean isPlayed;
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
        mediaView.setOnMouseClicked(event -> {
            if (!isPlayed) {
                play.setText("⏸");
                mp.play();
                isPlayed = true;
            } else {
                play.setText("►");
                mp.pause();
                isPlayed = false;
            }
            event.consume();
        });

        playbuttons.setOnMouseEntered(event -> {
            if (isPlayed)
                fadeIn.play();
        });
        playbuttons.setOnMouseExited(event -> {
            if (isPlayed)
                fadeOut.play();
        });
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
	 public void likeapost(Post post, AtomicBoolean isliked, Text likenumber) {
	        if (isliked.get()) {
	            ps.getPostReactions(post).stream()
	                    .filter(t -> t.getUser().getUserName().compareTo(Session.getUser().getUserName()) == 0)
	                    .forEach(t -> ps.removeReactionFromPost(post, t));
	            likenumber.setText("" + ps.getPostReactions(post).size());
	            isliked.set(false);
	        } else {

	            Reaction r = new Reaction(Session.getUser(), post, "like", new Date());
	            ps.addReactionPost(post, r);
	            likenumber.setText("" + ps.getPostReactions(post).size());
	            isliked.set(true);
	            ;
	        }
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
	 private VBox makeicon(ImageView profileimg) {
		 	double width;
		 	System.out.println();
		 	System.out.println(profileimg.getImage().getWidth());
		 	if (profileimg.getImage().getWidth()>300) {
		 		width=200;
		 	}
		 	else {
		 		width=46;
			}
		 	profileimg.setFitHeight(width);
	        profileimg.setPreserveRatio(true);
	        profileimg.setStyle("-fx-border-radius: 20px");
	        VBox imgholder = new VBox(profileimg);
	        Circle clip = new Circle(23);
	        clip.setCenterY(width/2);
	        clip.setCenterX((((profileimg.getImage().getWidth() * width / profileimg.getImage().getHeight() / 2))));
	        profileimg.setTranslateY(-(width/2)+23);
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
