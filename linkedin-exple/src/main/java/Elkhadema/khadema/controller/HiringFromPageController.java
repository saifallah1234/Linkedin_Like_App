package Elkhadema.khadema.controller;

import java.io.IOException;
import Elkhadema.khadema.App;
import Elkhadema.khadema.Service.ServiceImplemantation.CompanyServiceImp;
import Elkhadema.khadema.Service.ServiceImplemantation.JobServiceImp;
import Elkhadema.khadema.domain.Company;
import Elkhadema.khadema.domain.JobOffre;
import Elkhadema.khadema.util.Session;
import Elkhadema.khadema.util.Exception.NotCompanyException;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class HiringFromPageController extends NavbarController{
	@FXML
    private TextField employementtype;

    @FXML
    private Text errormsg;

    @FXML
    private TextField pay_range;
    @FXML
    private TextField locationm;
    @FXML
    private TextArea summ;
    @FXML
    private Text errormsg1;
    @FXML
    private TextField title;
    JobServiceImp js=new JobServiceImp();
    CompanyServiceImp	cs=new CompanyServiceImp();
    @FXML
    void postMsg(MouseEvent event) {}
    @FXML
    void post() throws NumberFormatException, NotCompanyException, IOException {
    	errormsg.setVisible(false);
    	errormsg1.setVisible(false);
    	if ((title.getText().length()>0)&&(pay_range.getText().length()>0)&&(summ.getText().length()>200)&&(locationm.getText().length()>3)) {

			if(pay_range.getText().matches("\\d*\\.?\\d+")) {
				js.addJob(new JobOffre(0, cs.getCompanyInfo(new Company(Session.getUser().getId(), null, null)), summ.getText(), title.getText(),Double.parseDouble(pay_range.getText()), employementtype.getText(), locationm.getText()));
				App.setRoot("jobs");
			}
			else {
				errormsg1.setVisible(true);
			}
		}
    	else {
			errormsg.setVisible(true);
		}
    }


}
