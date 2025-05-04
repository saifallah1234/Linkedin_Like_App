package Elkhadema.khadema.domain;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;


import javafx.scene.image.Image;
import javafx.scene.media.MediaPlayer;

public class Media {
	private Post post_id;
	private byte[] media;
	private String mediatype;
	public Post getPost_id() {
		return post_id;
	}
	public void setPost_id(Post post_id) {
		this.post_id = post_id;
	}
	public byte[] getMedia() {
		return media;
	}
	public void setMedia(byte[] media) {
		this.media = media;
	}
	public String getMediatype() {
		return mediatype;
	}
	public void setMediatype(String mediatype) {
		this.mediatype = mediatype;
	}
	public Media(Post post_id, byte[] media, String mediatype) {
		super();
		this.post_id = post_id;
		this.media = media;
		this.mediatype = mediatype;
	}
	public Image getImage() {
		if (mediatype.compareTo("img")==0) {
	        try {
	            return new Image(new ByteArrayInputStream(media));
	        } catch (Exception e) {
	            return new Image(getClass().getResourceAsStream("/Elkhadema/khadema/user.jpg"));
	        }
		}
		else {
			System.err.println("wrong type supposed to be img");
			return null;
		}
	}
	public MediaPlayer getVideo() {
		if (mediatype.compareTo("vid")==0) {
			String path = new String(media, StandardCharsets.UTF_8);
			System.out.println(path);
	        try {
	        	File file = new File(path);
	        	return new MediaPlayer(new javafx.scene.media.Media(file.toURI().toString()));
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
		}
		else {
			System.err.println("wrong type supposed to be vid");
			return null;
		}
	}
	public byte[] ImageCompression() throws IOException {
		 try {
	            // Read the image
	            BufferedImage image = ImageIO.read(new ByteArrayInputStream(media));
	            System.out.println(image);
	            if (image.getColorModel().hasAlpha()) {
	                // Prepare output stream
	                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	                ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(outputStream);

	                // Get available ImageWriters
	                Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("png");
	                ImageWriter writer = writers.next();
	                writer.setOutput(imageOutputStream);

	                // Set compression parameters for PNG
	                ImageWriteParam params = writer.getDefaultWriteParam();
	                params.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
	                params.setCompressionQuality(1.0f);

	                // Write the image with compression
	                writer.write(null, new javax.imageio.IIOImage(image, null, null), params);

	                // Cleanup
	                imageOutputStream.close();
	                writer.dispose();

	                System.out.println("Image compression successful.");

	                return outputStream.toByteArray();
	            } else {
	            	 // Prepare output stream
		            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		            ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(outputStream);

		            // Get available ImageWriters
		            Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
		            ImageWriter writer = writers.next();
		            writer.setOutput(imageOutputStream);

		            // Set compression parameters
		            ImageWriteParam params = writer.getDefaultWriteParam();
		            params.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		            params.setCompressionQuality(0.5f); // Set the compression quality (0.0 - 1.0)

		            // Write the image with compression
		            writer.write(null, new javax.imageio.IIOImage(image, null, null), params);

		            // Cleanup
		            imageOutputStream.close();
		            writer.dispose();

		            System.out.println("Image compression successful.");

		            return outputStream.toByteArray();
	            }

	        } catch (IOException e) {
	            System.out.println("Error: " + e);
	            return null;
	        }
	}
	public static byte[] ImageDecompress(byte[] compressedImageData) {
		if (compressedImageData!=null) {
		 try {
	            // Prepare input stream
	            ByteArrayInputStream inputStream = new ByteArrayInputStream(compressedImageData);
	            ImageInputStream imageInputStream = ImageIO.createImageInputStream(inputStream);

	            // Get available ImageReaders
	            Iterator<ImageReader> readers = ImageIO.getImageReaders(imageInputStream);
	            ImageReader reader = readers.next();
	            reader.setInput(imageInputStream);

	            // Read the image
	            BufferedImage image = reader.read(0);

	            // Convert BufferedImage to byte array
	            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	            ImageIO.write(image, "png", outputStream);

	            if (outputStream.size()==0) {
	            	ImageIO.write(image, "jpg", outputStream);

				}
	            // Cleanup
	            imageInputStream.close();
	            reader.dispose();

	            System.out.println("Image decompression successful.");

	            return outputStream.toByteArray();
	        } catch (IOException e) {
	            System.out.println("Error: " + e);
	            return null;
	        }
		}
		 return null;
		}
		public  byte[] compressVideo() {
			try {
	            ProcessBuilder processBuilder = new ProcessBuilder("ffmpeg", "-i", "pipe:0", "-c:v", "libx264", "-preset", "ultrafast", "-f", "mp4", "pipe:1");
	            processBuilder.redirectErrorStream(true);
	            Process process = processBuilder.start();

	            try (InputStream inputStream = new ByteArrayInputStream(media);
	                 ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

	                // Feed input video to FFmpeg process
	                byte[] buffer = new byte[1024];
	                int length;
	                while ((length = inputStream.read(buffer)) != -1) {
	                    process.getOutputStream().write(buffer, 0, length);
	                }
	                process.getOutputStream().close();

	                // Read compressed video from FFmpeg process
	                while ((length = process.getInputStream().read(buffer)) != -1) {
	                    outputStream.write(buffer, 0, length);
	                }

	                process.waitFor(); // Wait for FFmpeg process to finish
	                return outputStream.toByteArray();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
    }

    public static byte[] decompressVideo(byte[] compressedVideoData) {
    	 try {
             ProcessBuilder processBuilder = new ProcessBuilder("ffmpeg", "-i", "pipe:0", "-c:v", "copy", "-f", "rawvideo", "pipe:1");
             processBuilder.redirectErrorStream(true);
             Process process = processBuilder.start();

             try (InputStream inputStream = new ByteArrayInputStream(compressedVideoData);
                  ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

                 // Feed input video to FFmpeg process
                 byte[] buffer = new byte[1024];
                 int length;
                 while ((length = inputStream.read(buffer)) != -1) {
                     process.getOutputStream().write(buffer, 0, length);
                 }
                 process.getOutputStream().close();

                 // Read decompressed video from FFmpeg process
                 while ((length = process.getInputStream().read(buffer)) != -1) {
                     outputStream.write(buffer, 0, length);
                 }

                 process.waitFor(); // Wait for FFmpeg process to finish
                 return outputStream.toByteArray();
             }
         } catch (Exception e) {
             e.printStackTrace();
             return null;
         }
    	 }
}
