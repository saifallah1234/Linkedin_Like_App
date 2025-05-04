package Elkhadema.khadema.controller;

import java.io.IOException;
import java.util.List;

import Elkhadema.khadema.DAO.DAOImplemantation.CompanyDAO;
import Elkhadema.khadema.DAO.DAOImplemantation.CompetanceDAO;
import Elkhadema.khadema.DAO.DAOImplemantation.ExperienceDAO;
import Elkhadema.khadema.DAO.DAOImplemantation.PersonDAO;
import Elkhadema.khadema.Service.ServiceImplemantation.AdminServiceImp;
import Elkhadema.khadema.Service.ServiceImplemantation.FollowServiceImp;
import Elkhadema.khadema.Service.ServiceImplemantation.GenerateCVServiceImp;
import Elkhadema.khadema.Service.ServiceImplemantation.JobServiceImp;
import Elkhadema.khadema.Service.ServiceImplemantation.UserServiceImp;
import Elkhadema.khadema.Service.ServiceInterfaces.AdminService;
import Elkhadema.khadema.Service.ServiceInterfaces.FollowService;
import Elkhadema.khadema.Service.ServiceInterfaces.GenerateCVService;
import Elkhadema.khadema.Service.ServiceInterfaces.JobService;
import Elkhadema.khadema.Service.ServiceInterfaces.UserService;
import Elkhadema.khadema.Service.validateInfo.JobNameValidator;
import Elkhadema.khadema.Service.validateInfo.Specialityalidator;
import Elkhadema.khadema.Service.validateInfo.UrlValidator;
import Elkhadema.khadema.domain.JobOffre;
import Elkhadema.khadema.domain.Media;
import Elkhadema.khadema.domain.Company;
import Elkhadema.khadema.domain.User;
import Elkhadema.khadema.util.MediaChooser;
import Elkhadema.khadema.util.Session;
import Elkhadema.khadema.util.Exception.UserNotFoundException;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class CompanyController extends NavbarController {
    User session = Session.getUser();
    Company company;

    PersonDAO personDAO = new PersonDAO();
    CompanyDAO companyDAO = new CompanyDAO();
    JobService jobService = new JobServiceImp();
    FollowService followService = new FollowServiceImp();
    UserService userService = new UserServiceImp();
    ExperienceDAO experienceDAO = new ExperienceDAO();
    CompetanceDAO competanceDAO = new CompetanceDAO();
    GenerateCVService cvService = new GenerateCVServiceImp();
    AdminService adminService=new AdminServiceImp();

    @FXML
    ImageView profileImg;
    @FXML
    Text nameText;

    @FXML
    HBox btnVbox;
    @FXML
    VBox experienceVBox;

    @FXML
    Button changeImgbtn;
    @FXML
    Button editBioBtn;
    @FXML
    Button editAboutBtn;

    @FXML
    private TextArea aboutField;
    @FXML
    private TextField websiteField;
    @FXML
    private TextField industryField;
    @FXML
    private TextField locationField;
    @FXML
    private TextArea specialityField;
    @FXML
    private TextField smallDesText;
    @FXML
    private TextField motoField;
    @FXML
    private Button cancelOverviewEdit;
    @FXML
    private Button confirmOverviewEdit;
    @FXML
    private Text showText;
    @FXML
    private Button banButton;
    @FXML
    public void ban(){
        //TODO later
    }
    @FXML
    public void init(User user) {
        super.initialize(null, null);
        company = companyDAO.get(user.getId()).get();
        if (!company.equals(session)) {
            Button followbutton = getFollowbutton();
            Button chatButton = getChatButton();
            btnVbox.getChildren().addAll(followbutton, chatButton);
            initButttons();
            if (adminService.isAdmin(session)) {
                banButton.setVisible(true);
                banButton.setDisable(false);
            }
        }
        cancelOverviewEdit.setDisable(true);
        cancelOverviewEdit.setVisible(false);
        confirmOverviewEdit.setDisable(true);
        confirmOverviewEdit.setVisible(false);
        nameText.setText(company.getUserName());
        profileImg.setImage(company.getPhoto().getImage());
        profileImg.getStyleClass().add("round-image");
        initContacts();
        afficheBio(company);
        initOverview();
        initMoto();

        List<JobOffre> joboffres = jobService.getAllJobOffresByCompany(company);
        joboffres.stream().limit(2).forEach(joboffre -> {
            VBox jobOffreBox = new VBox();
            afficheJobOffre(joboffre, jobOffreBox);
            experienceVBox.getChildren().add(jobOffreBox);
        });
        if (joboffres.size() < 2) {
            showText.setDisable(true);
            showText.setVisible(false);
        }
    }

    private void initButttons() {
        changeImgbtn.setDisable(true);
        changeImgbtn.setVisible(false);
        editBioBtn.setDisable(true);
        editBioBtn.setVisible(false);
        editBioBtn.setDisable(true);
        editBioBtn.setVisible(false);
        editAboutBtn.setDisable(true);
        editAboutBtn.setVisible(false);
    }

    @FXML
    public void cancelEdit() {
        initOverview();
        editAboutBtn.setDisable(false);
        editAboutBtn.setVisible(true);
        cancelOverviewEdit.setDisable(true);
        cancelOverviewEdit.setVisible(false);
        confirmOverviewEdit.setDisable(true);
        confirmOverviewEdit.setVisible(false);
    }

    @FXML
    public void confirmEdit() throws UserNotFoundException {
        Company newCompany = new Company(company.getId(), company.getPassword(), company.getUserName());
        if (aboutField.getText().strip().isEmpty()) {
            invalidText.setText("industry invalid");
            return;
        }
        newCompany.setDescription(aboutField.getText());
        if (!UrlValidator.validateURL(websiteField.getText())) {
            invalidText.setText("website url invalid");
            return;
        }
        newCompany.setWebsite(websiteField.getText());
        if (!JobNameValidator.isValidJobName(industryField.getText())) {
            invalidText.setText("invalid industry name");
            return;
        }
        newCompany.setIndustry(industryField.getText());
        if (!Specialityalidator.validateText(specialityField.getText())) {
            invalidText.setText("invalid specialities");
            return;
        }
        newCompany.setSpeciality(specialityField.getText());
        userService.EditUser(company, newCompany);
        initOverview();
        cancelOverviewEdit.setDisable(true);
        cancelOverviewEdit.setVisible(false);
        confirmOverviewEdit.setDisable(true);
        confirmOverviewEdit.setVisible(false);
        editAboutBtn.setDisable(false);
        editAboutBtn.setVisible(true);

    }

    private Button getChatButton() {
        Button chatButton = new Button("chat");
        chatButton.getStyleClass().add("postButton");
        chatButton.setOnAction(event -> {
            try {
                goChat(event, company);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return chatButton;
    }

    private Button getFollowbutton() {
        Button followbutton = new Button("follow");
        if (followService.isFollowing(session, company)) {
            followbutton.setText("unfollow");
        }
        followbutton.getStyleClass().add("postButton");
        followbutton.setOnAction(event -> {
            if (!followService.isFollowing(session, company)) {
                followService.Follow(session, company);
                followbutton.setText("unfollow");
            } else {
                followService.unFollow(session, company);
                followbutton.setText("follow");
            }
        });
        return followbutton;
    }

    private void initOverview() {
        aboutField.setText(company.getDescription());
        websiteField.setText(company.getWebsite());
        industryField.setText(company.getIndustry());
        locationField.setText(company.getContactInfo().getAddress());
        specialityField.setText(company.getSpeciality());
    }

    private void initMoto() {
        motoField.setOnKeyPressed(event -> {
            if (event.getCode().isWhitespaceKey() && !event.isShiftDown()) {
                company.setMoto(motoField.getText());
                try {
                    userService.EditUser(company, company);
                } catch (UserNotFoundException e) {
                    e.printStackTrace();
                }
                motoField.setDisable(true);
            }
        });
        motoField.focusedProperty().addListener((observale, oldValue, newValue) -> {
            if (!newValue) {
                company.setMoto(motoField.getText());
                try {
                    userService.EditUser(company, company);
                } catch (UserNotFoundException e) {
                    e.printStackTrace();
                }
                motoField.setDisable(true);
            }
        });
    }

    private void afficheJobOffre(JobOffre jobOffre, VBox vBox) {
        Text jobOffreText = new Text(jobOffre.getPostion());
        jobOffreText.setFont(new Font("SansSerif Bold", 15));
        Text jobPayText = new Text(String.valueOf(jobOffre.getPayRange()));
        jobPayText.setFont(new Font("SansSerif Bold", 12));
        Text jobLocationText = new Text(jobOffre.getLocation());
        jobLocationText.setFont(new Font("SansSerif Bold", 12));
        vBox.getChildren().addAll(jobOffreText, jobPayText, jobLocationText);
    }

    private void afficheBio(Company company) {
        if (company.getMoto() == null || company.getMoto().strip().isEmpty()) {
            motoField.setText("");
        } else
            motoField.setText(company.getMoto());
        String description = company.getIndustry().concat(" · ");
        description = description.concat(company.getAddress()).concat(" · ");
        description = description.concat(String.valueOf(followService.getFollowers(company).size())).concat(" · ");
        description = description.concat(String.valueOf(company.getComapnySize()));
        smallDesText.setText(description);
        motoField.setDisable(true);
        smallDesText.setDisable(true);
        smallDesText.getStyleClass().add("disabled-text");
        motoField.getStyleClass().add("disabled-text");
    }

    @FXML
    void addImage(ActionEvent event) throws UserNotFoundException {
        Media m = MediaChooser.Choose(event);
        if (m.getImage() == null) {
            profileImg.setImage(new Image("user.jpg.png"));
            return;
        }
        if (!m.getMediatype().equals("img")) {
            return;
        }
        profileImg.setImage(m.getImage());
        company.setPhoto(m);
        userService.EditUser(company, company);
    }

    @FXML
    private void editBio() throws IOException, UserNotFoundException {
        motoField.setDisable(false);
        motoField.setVisible(true);
        motoField.requestFocus();
    }
    @FXML
    Text invalidText;
    @FXML
    private void editAbout() {
        editAboutBtn.setDisable(true);
        editAboutBtn.setVisible(false);
        confirmOverviewEdit.setDisable(false);
        confirmOverviewEdit.setVisible(true);
        cancelOverviewEdit.setDisable(false);
        cancelOverviewEdit.setVisible(true);
        aboutField.setDisable(false);
        websiteField.setDisable(false);
        industryField.setDisable(false);
        locationField.setDisable(false);
        specialityField.setDisable(false);
        aboutField.requestFocus();
    }

}
