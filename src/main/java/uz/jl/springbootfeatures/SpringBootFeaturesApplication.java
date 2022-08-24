package uz.jl.springbootfeatures;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Base64;

@SpringBootApplication
@Controller
public class SpringBootFeaturesApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootFeaturesApplication.class, args);
    }

    private static final String QR_CODE_IMAGE_PATH = "./src/main/resources/static/img/QRCode.png";

    @GetMapping("/")
    public String getQRCode(Model model) {
        String pdp = "https://pdp.uz";
        String github = "https://github.com/jlkesh";
        final String QR_CODE_IMAGE_PATH = "./src/main/resources/static/img/QRCode.png";


        byte[] image = new byte[0];
        try {
            image = QRCodeGenerator.getQRCodeImage(pdp, 250, 250);

            // Generate and Save Qr Code Image in static/image folder
            QRCodeGenerator.generateQRCodeImage(github, 250, 250, QR_CODE_IMAGE_PATH);

        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
        // Convert Byte Array into Base64 Encode String
        String qrcode = Base64.getEncoder().encodeToString(image);

        model.addAttribute("pdp", pdp);
        model.addAttribute("github", github);
        model.addAttribute("qrcode", qrcode);

        return "qrcode";
    }

}

class QRCodeGenerator {

    public static void generateQRCodeImage(String text, int width, int height, String filePath)
            throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

    }


    public static byte[] getQRCodeImage(String text, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            MatrixToImageConfig con = new MatrixToImageConfig(0xD2191902, 0xFFFFC041);

        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream, con);
        return pngOutputStream.toByteArray();
    }

}