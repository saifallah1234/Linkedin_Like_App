package Elkhadema.khadema.controller;

import java.io.IOException;
import java.util.List;
import Elkhadema.khadema.Service.ServiceImplemantation.FollowServiceImp;
import Elkhadema.khadema.Service.ServiceImplemantation.MessageServiceIMP;
import Elkhadema.khadema.Service.ServiceInterfaces.FollowService;
import Elkhadema.khadema.Service.ServiceInterfaces.MessageService;
import Elkhadema.khadema.domain.Media;
import Elkhadema.khadema.domain.Message;
import Elkhadema.khadema.domain.User;
import Elkhadema.khadema.util.MediaChooser;
import Elkhadema.khadema.util.Session;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * ChatRoomController
 */
public class ChatRoomController extends NavbarController  {
    private User currentMessageReciver;
    private long lastMessageId = 0;
    private MessageService messageService = new MessageServiceIMP();
    private FollowService followService = new FollowServiceImp();
    private List<User> contacts = followService.getfollowing(Session.getUser());
    private int parentMessageId;

    @FXML
    TextArea messageText;
    @FXML
    Button sendBtn;
    @FXML
    VBox messageVBox;
    @FXML
    private HBox HboxforAttachments;

    @FXML
    private Button buttontoaddattach;

    @FXML
    ScrollPane messagePane;

    public void init(User user) {
        super.initialize(null, null);
        initContacts();
        try {
            if (user !=null)
                currentMessageReciver=user;
            else
                currentMessageReciver = contacts.get(0);
            messageVBox.getChildren().clear();
            loadMessages(currentMessageReciver);
        } catch (Exception e) {
            currentMessageReciver = null;
        }
        addNewMessages();
        messageText.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.trim().isEmpty()) {
                sendBtn.setDisable(false);
            } else {
                sendBtn.setDisable(true);
            }
        });
        sendBtn.setDisable(true);
    }

    private void addNewMessages() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if (currentMessageReciver == null) {
                return;
            }
            List<Message> messages = messageService.chat(Session.getUser(), currentMessageReciver);
            messages.stream().dropWhile(message -> message.getId() != lastMessageId).filter(message -> message.getSender()!=Session.getUser())
                    .skip(1)
                    .forEach(message -> afficheMessage(message));
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void loadMessages(User user) {
        messageVBox.getChildren().clear();
        List<Message> messages = messageService.chat(Session.getUser(), user);
        if (messages.isEmpty()) {
            return;
        }
        for (Message message : messages) {
            afficheMessage(message);
        }
        Platform.runLater(() -> messagePane.setVvalue(1.0));
    }

    Media attachMedia;

    @FXML
    void AddMediabutton(ActionEvent event) {
        Media m = MediaChooser.Choose(event);
        if (m.getMediatype().equals("img")) {
            attachMedia = m;
            ImageView img = new ImageView(m.getImage());
            HboxforAttachments.getChildren().add(img);
            img.setFitWidth(100);
            img.setPreserveRatio(true);
        } else {
            System.out.println("can't import a video here");
        }
    }

    private void afficheMessage(Message message) {
        boolean tmp = messagePane.getVvalue() == 1.0;
        ImageView imageView = new ImageView(new Image("file:src//main//resources//images//user.png"));
        imageView.setFitHeight(46);
        imageView.setFitWidth(46);
        HBox hBox;
        if (message.getSender().equals(Session.getUser())) {
            Text text = new Text(Session.getUser().getUserName());
            text.setFill(Color.WHITE);
            text.setFont(new Font("SansSerif", 15));
            text.setTranslateX(-Session.getUser().getUserName().length() + 3);
            hBox = new HBox(text, imageView);
            hBox.setAlignment(Pos.CENTER_RIGHT);
        } else {
            Text text = new Text(currentMessageReciver.getUserName());
            text.setFill(Color.WHITE);
            text.setTranslateX(currentMessageReciver.getUserName().length() - 3);
            text.setFont(new Font("SansSerif", 15));
            hBox = new HBox(imageView, text);
            hBox.setAlignment(Pos.CENTER_LEFT);
        }
        TextArea contentText = new TextArea(message.getContent());
        contentText.setDisable(true);
        contentText.setWrapText(true);
        contentText.setOpacity(1);
        contentText.setStyle(
                "-fx-font-family: 'Helvetica';" +
                        "-fx-font-size: 0.875 em" +
                        "-fx-padding: 10px;" +
                        "-fx-background-color: #f5f6f7;" +
                        "-fx-border-width: 1px;" +
                        "-fx-text-fill: black;" +
                        "-fx-background : transparent;" +
                        "-fx-background-radius: 10px;");
        // ahawa kifech t addi image
        VBox vBox = new VBox(hBox, contentText);
        if (message.getImage()!=null) {
            ImageView iv = new ImageView(message.getImage().getImage());
            VBox vboxforimage = new VBox(iv);
            iv.setFitWidth(500);
            iv.setPreserveRatio(true);
            vboxforimage.setAlignment(Pos.TOP_RIGHT);
            vBox.getChildren().add(vboxforimage);// houni image right 3ala 5ater titb3ath twali left ki recieved
        }
        messageVBox.getChildren().add(vBox);
        // end houni
        if (message.getSender() != Session.getUser()) {
            messageService.MessageRead(message, Session.getUser());
        }

        lastMessageId = message.getId();
        Platform.runLater(() -> {
            if (tmp)
                messagePane.setVvalue(1.0);
        });
    }

    @FXML
    public void postMsg(ActionEvent event) {
        Message message = new Message(0, Session.getUser(), messageText.getText(), null, parentMessageId);
        message.setImage(attachMedia);
        messageService.sendMessage(currentMessageReciver, message);
        afficheMessage(message);
        messageText.setText("");
        attachMedia = null;
        HboxforAttachments.getChildren().clear();
        messageText.requestFocus();

    }

    public void returnHome(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Elkhadema/khadema/mainpage.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}