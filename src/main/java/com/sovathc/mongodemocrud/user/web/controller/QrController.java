package com.sovathc.mongodemocrud.user.web.controller;

import com.google.zxing.WriterException;
import com.sovathc.mongodemocrud.common.client.AmazonClient;
import com.sovathc.mongodemocrud.common.client.AmazonS3Client;
import com.sovathc.mongodemocrud.common.client.S3Response;
import com.sovathc.mongodemocrud.common.converter.Base64ToImage;
import com.sovathc.mongodemocrud.common.utils.QrCodeGenerator;
import com.sovathc.mongodemocrud.common.utils.QrCodeLogo;
import com.sovathc.mongodemocrud.user.biz.dto.UploadKhQrDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.util.Base64;

@RestController
@RequestMapping("api/qrcode")
public class QrController {
    private static final String QR_CODE_IMAGE_PATH = "./src/main/resources/static/img/QRCode.png";
    @Autowired
    private AmazonClient client;
    @Autowired
    private AmazonS3Client s3Client;


    @GetMapping("/test")
    public String getQRCode(Model model){
        String medium="00020101021230490008aka@wing0108007503150209BILLPAYON0308MERCHANT520452625303116540441005802KH5916My Supplier(BTC)6008MERCHANT61051200062270116WMK-1232311111290303YES6304B657";
        String github="00020101021230490008aka@wing0108007503150209BILLPAYON0308MERCHANT520452625303116540441005802KH5916My Supplier(BTC)6008MERCHANT61051200062270116WMK-1232311111290303YES6304B657";

        byte[] image = new byte[0];
        try {

            // Generate and Return Qr Code in Byte Array
            image = QrCodeGenerator.getQRCodeImage(medium,250,250);

            // Generate and Save Qr Code Image in static/image folder
            QrCodeGenerator.generateQRCodeImage(github,250,250, QR_CODE_IMAGE_PATH);

        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
        // Convert Byte Array into Base64 Encode String
        String qrcode = Base64.getEncoder().encodeToString(image);

        model.addAttribute("medium",medium);
        model.addAttribute("github",github);
        model.addAttribute("qrcode",qrcode);

        return "qrcode";
    }

    @GetMapping("/logo")
    public String generate(){
        QrCodeLogo qrCodeLogo = new QrCodeLogo();
        qrCodeLogo.generate();
        return "qrcode";
    }
    @PostMapping("/base64")
    public void uploadS3(@RequestBody UploadKhQrDTO dto)
    {
        client.store(dto);
    }
    @PostMapping("/base64/test")
    public String uploadBase64Test(@RequestBody UploadKhQrDTO dto) throws IOException {

       S3Response response = s3Client.uploadFileToS3Bucket(DatatypeConverter.parseBase64Binary(dto.getImage()),
                ".png",
                dto.getName(),
                ".png");
       return response.getPath();
    }
}
