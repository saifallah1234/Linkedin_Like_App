package Elkhadema.khadema.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;



import Elkhadema.khadema.domain.Media;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MediaChooser {
	public static Media Choose(ActionEvent event) {
		FileChooser fileChooser=new FileChooser();
		   fileChooser.setTitle("Choose Media File");
		   fileChooser.getExtensionFilters().addAll(
		            new FileChooser.ExtensionFilter("Media Files", "*.png", "*.jpg", "*.jpeg", "*.mp4", "*.avi", "*.mov")
		        );

		   File selectFile =fileChooser.showOpenDialog((Stage) ((Node) event.getSource()).getScene().getWindow());
		   String extension=selectFile.getName().substring(selectFile.getName().lastIndexOf(".")+1);
		   List<String> imgextension= Arrays.asList("png", "jpg", "jpeg");
		   List<String> vidextension= Arrays.asList("mp4", "avi", "mov");

		   if (imgextension.contains(extension)) {
			   try (FileInputStream fis = new FileInputStream(selectFile)) {
		            return new Media(null, fis.readAllBytes(), "img");

		        } catch (IOException e) {
		            e.printStackTrace();
		            return null;
		        }
		}
		   else if (vidextension.contains(extension)) {
			   return new Media(null,selectFile.getPath().getBytes(StandardCharsets.UTF_8),"vid");
		}

		   else {
			System.out.println("given wrong extension");
			return null;
		}
	}
}
