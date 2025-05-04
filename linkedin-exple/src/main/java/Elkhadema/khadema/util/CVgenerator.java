package Elkhadema.khadema.util;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.color.DeviceRgb;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.ListItem;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import Elkhadema.khadema.domain.Competance;
import Elkhadema.khadema.domain.Experience;
import Elkhadema.khadema.domain.Media;
import Elkhadema.khadema.domain.Person;

public class CVgenerator {
    public static void Generate(Person person, String path, List<Competance> comp, List<Experience> experience)
            throws IOException {
        PdfWriter pdfWriter = new PdfWriter(path);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        pdfDocument.setDefaultPageSize(PageSize.A4);
        Document document = new Document(pdfDocument);
        Color color1 = new DeviceRgb(0, 149, 254); // Light blue
        Color color2 = new DeviceRgb(0, 24, 212); // Dark purple
        int steps = 50;
        float stepSize = PageSize.A4.getWidth() / steps;
        PdfCanvas canvas = new PdfCanvas(pdfDocument.addNewPage());
        for (int i = 0; i < steps; i++) {
            float x = i * stepSize;
            Color color = interpolateColor(color1, color2, (float) i / steps);
            canvas.setFillColor(color)
                    .rectangle(x, PageSize.A4.getHeight() * 0.82, stepSize, PageSize.A4.getHeight() * 0.18)
                    .fill();
        }

        Paragraph header = new Paragraph(person.getFirstName() + " " + person.getLastName())
                .setFontColor(Color.WHITE)
                .setFontSize(28)
                .setTextAlignment(TextAlignment.CENTER)
                .setBold()
                .setFont(PdfFontFactory.createFont(FontConstants.COURIER_BOLD));
        ImageData imageData;
        if (person.getPhoto() == null) {
            imageData=ImageDataFactory.create("src/main/resources/Elkhadema/khadema/user.jpg");
        } else
            imageData = ImageDataFactory.create(person.getPhoto().getMedia());
        Image image = new Image(imageData);
        image.setWidth(100);
        image.setHeight(100);
        image.setBorder(new SolidBorder(1));
        canvas.roundRectangle(100, PageSize.A4.getHeight() - 110, 100, 100, 50);
        canvas.clip();
        canvas.addImage(imageData, 100, (float) (PageSize.A4.getHeight() * 0.85), 100, false);
        canvas.release();
        document.add(header);

        float fullwidth[] = { 600, 250 };
        Table tb = new Table(fullwidth).setMarginTop(100).setFontColor(Color.BLACK).setHeight(800)
                .setFont(PdfFontFactory.createFont(FontConstants.HELVETICA));
        Cell c1 = new Cell();
        Cell c2 = new Cell();
        // c1.add(new Paragraph("RESUME OBJECTIVE").setBold().setFontSize(15));
        // c1.add(new Paragraph("Redford & Sons, Boston, MA / September 2018 -
        // Present").setFontSize(11));
        c1.add(new Paragraph("About").setBold().setFontSize(15));
        c1.add(new Paragraph(person.getAbout()).setFontSize(11).setPaddingLeft(15));
        c1.add(new Paragraph("Experiences").setBold().setFontSize(15));
        experience.forEach(t -> {
            c1.add(new Paragraph(t.getTechnologie()).setFontSize(14));
            c1.add(new Paragraph(t.getMission()).setFontSize(11).setPaddingLeft(15));
            c1.add(new Paragraph(t.getType()).setFontSize(11).setPaddingLeft(15));
            c1.add(new Paragraph(t.getDateExperience()).setFontSize(11).setPaddingLeft(15));
            c1.add(new Paragraph(t.getDescription()).setFontSize(11).setPaddingLeft(15));
        });

        c2.add(new Paragraph("Contact info").setBold().setFontSize(15));
        c2.add(new Paragraph(person.getContactInfo().getEmail()).setFontSize(11));
        c2.add(new Paragraph("" + person.getContactInfo().getPhoneNumber()).setFontSize(11));
        c2.add(new Paragraph(person.getContactInfo().getAddress()).setFontSize(11));
        c2.add(new Paragraph("Competances").setBold().setFontSize(15));
        comp.forEach(t -> {
            c2.add(new ListItem(t.getTitre())).setFontSize(11);
        });
        c1.setBorder(Border.NO_BORDER);
        c2.setBorder(Border.NO_BORDER);
        c1.setBorderRight(new SolidBorder(Color.GRAY, 0.1f, 0.5f));
        c2.setPaddingLeft(15);
        tb.addCell(c1);
        tb.addCell(c2);
        document.add(tb);
        // Close the document
        document.close();
    }

    private static Color interpolateColor(Color color1, Color color2, float ratio) {
        float r = (float) (color1.getColorValue()[0] * (1 - ratio) + color2.getColorValue()[0] * ratio);
        float g = (float) (color1.getColorValue()[1] * (1 - ratio) + color2.getColorValue()[1] * ratio);
        float b = (float) (color1.getColorValue()[2] * (1 - ratio) + color2.getColorValue()[2] * ratio);
        return new DeviceRgb(r, g, b);
    }

    public static void main(String[] args) throws IOException {
        List<Competance> comp = Arrays.asList(new Competance(0, "AI", null, null, 0),
                new Competance(0, "Busnesss", null, null, 0), new Competance(0, "Software engeneer", null, null, 0));
        List<Experience> expe = Arrays.asList(new Experience(0, "descriptions1", "experience1", null, null),
                new Experience(0, "descriptions2", "experience2", null, null),
                new Experience(0, "descriptions3", "experience3", null, null));
        Person person = new Person(0, "wassim", "nefzi");
        CVgenerator.Generate(person, "CV.pdf", comp, expe);
    }
}
