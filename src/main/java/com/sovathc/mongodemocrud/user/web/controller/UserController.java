package com.sovathc.mongodemocrud.user.web.controller;

import com.sovathc.mongodemocrud.common.controller.AbstractController;
import com.sovathc.mongodemocrud.common.controller.ResponseBuilderMessage;
import com.sovathc.mongodemocrud.common.controller.ResponseMessage;
import com.sovathc.mongodemocrud.common.exception.BusinessException;
import com.sovathc.mongodemocrud.common.response.PageableResponse;
import com.sovathc.mongodemocrud.common.utils.*;
import com.sovathc.mongodemocrud.user.biz.dto.UserDTO;
import com.sovathc.mongodemocrud.user.biz.mapper.UserMapper;
import com.sovathc.mongodemocrud.user.biz.service.UserService;
import com.sovathc.mongodemocrud.user.web.vo.request.*;
import com.sovathc.mongodemocrud.user.web.vo.response.UserItemResponse;
import com.sovathc.mongodemocrud.user.web.vo.response.UserResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Tag(name = "User")
@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController implements AbstractController<UserItemResponse, UserResponse, UserCreatedRequest, UserUpdatedRequest, UserPageableRequest> {
    private final UserService service;
    private final PdfGeneratorUtils pdfGeneratorUtils;
    private final PdfTextGenerateUtils pdfTextGenerateUtils;
    private final PdfWaterMarkUtils waterMarkUtils;
    private final PdfAddWatermarkUtils pdfAddWatermarkUtils;
    private final TelegramUtils telegramUtils;
    @SneakyThrows
    @Override
    public ResponseMessage<UserResponse> findOne(String id)
    {
        UserDTO userResponseDTO = this.service.findOne(id);
        UserResponse response = new UserResponse();
        UserMapper.INSTANCE.dtoToResponse(userResponseDTO, response);

        return new ResponseBuilderMessage<UserResponse>()
                .success().addData(response).build();
    }
    @SneakyThrows
    @Override
    public ResponseMessage<PageableResponse<UserItemResponse>> findWithPage(UserPageableRequest request)
    {
        UserDTO userDTO = new UserDTO();
        UserMapper.INSTANCE.pagableToDto(request, userDTO);
        Page<UserDTO> page = this.service.findAll(userDTO, request);
        List<UserItemResponse> responseList = new ArrayList<>();
        UserMapper.INSTANCE.listDtoToListItemResponse(page.getContent(), responseList);
        PageableResponse<UserItemResponse> response = new PageableResponse<>(page.getTotalElements(), responseList, request);

        return new ResponseBuilderMessage<PageableResponse<UserItemResponse>>()
                .success().addData(response).build();
    }
    @SneakyThrows
    @Override
    public ResponseMessage<UserResponse> create(UserCreatedRequest request)
    {
        UserDTO userDTO = new UserDTO();
        UserMapper.INSTANCE.createdToDto(request, userDTO);
        UserDTO userResponseDTO = this.service.create(userDTO);
        UserResponse response = new UserResponse();
        UserMapper.INSTANCE.dtoToResponse(userResponseDTO, response);

        return new ResponseBuilderMessage<UserResponse>()
                .success().addData(response).build();
    }
    @SneakyThrows
    @Override
    public ResponseMessage<UserResponse> update(String id, UserUpdatedRequest request)
    {
        UserDTO userDTO = new UserDTO();
        UserMapper.INSTANCE.updatedToDto(request, userDTO);
        UserDTO userResponseDTO = this.service.update(id, userDTO);
        UserResponse response = new UserResponse();
        UserMapper.INSTANCE.dtoToResponse(userResponseDTO, response);
        return new ResponseBuilderMessage<UserResponse>()
                .success().addData(response).build();
    }

    @SneakyThrows
    @Override
    public ResponseMessage<Void> delete(String id)
    {
        this.service.delete(id);
        return new ResponseBuilderMessage<Void>()
                .success().build();
    }
    @SneakyThrows
    @GetMapping("/pdf/{id}")
    public ResponseMessage<Void> generatePdf(@PathVariable String id)
    {
        this.service.generateUserPdf(id);

        return new ResponseBuilderMessage<Void>()
                .success().build();
    }
    @SneakyThrows
    @GetMapping(value = "/download/{id}",produces = MediaType.APPLICATION_PDF_VALUE)
    public HttpEntity<byte[]> downloadPDF(@PathVariable String id, UserPageableRequest request)
    {
        return this.service.downloadPDF(request.getTemplateName(), id);
    }
    @SneakyThrows
    @GetMapping(value = "/download-japer/{id}",produces = MediaType.APPLICATION_PDF_VALUE)
    public HttpEntity<byte[]>  downloadJapserPDF(@PathVariable String id, UserPageableRequest request)
    {
        return this.service.downloadJapserPDF();
    }
    @SneakyThrows
    @GetMapping(value = "/generate-pdf/{id}",produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseMessage<Void> generatePDF(@PathVariable String id, UserPageableRequest request)
    {
        this.service.generatePDF(request.getTemplateName(), id);
        return new ResponseBuilderMessage<Void>()
                .success().build();
    }
    @SneakyThrows
    @GetMapping(value = "/download-license/{id}",produces = MediaType.APPLICATION_PDF_VALUE)
    public void downloadLicense(@PathVariable String id, UserPageableRequest request)
    {
        this.pdfGeneratorUtils.html2Pdf();
    }
    @SneakyThrows
    @GetMapping(value = "/download-license-output/{id}",produces = MediaType.APPLICATION_PDF_VALUE)
    public HttpEntity<byte[]> downloadLicenseOutput(@PathVariable String id, UserPageableRequest request)
    {
        byte[] bytes = this.pdfGeneratorUtils.html2PdfByte();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.builder("inline").filename("user.pdf").build());
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentLength(bytes.length);

        return new HttpEntity<>(bytes, headers);
    }
    @SneakyThrows
    @GetMapping(value = "/download-image/{id}",produces = MediaType.APPLICATION_PDF_VALUE)
    public void downloadIronText(@PathVariable String id, UserPageableRequest request)
    {
        this.pdfGeneratorUtils.generateInvoiceFor();
    }
    @SneakyThrows
    @GetMapping(value = "/download-watermark/{id}",produces = MediaType.APPLICATION_PDF_VALUE)
    public HttpEntity<byte[]> downloadWatermark(@PathVariable String id, UserPageableRequest request)
    {
        byte[] bytes = this.waterMarkUtils.generatePDFFromHTML(request.getTemplateName());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.builder("inline").filename("user.pdf").build());
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentLength(bytes.length);

        return new HttpEntity<>(bytes, headers);
    }
    @SneakyThrows
    @GetMapping(value = "/download-generate-watermark/{id}",produces = MediaType.APPLICATION_PDF_VALUE)
    public HttpEntity<byte[]> downloadGenerateWatermark(@PathVariable String id, UserPageableRequest request)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        this.waterMarkUtils.generateWatermark(byteArrayOutputStream);
        byte[] bytes = this.waterMarkUtils.watermarkImage(byteArrayOutputStream);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.builder("inline").filename("user.pdf").build());
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentLength(bytes.length);

        return new HttpEntity<>(bytes, headers);
    }
    @SneakyThrows
    @GetMapping(value = "/download-add-watermark/{id}",produces = MediaType.APPLICATION_PDF_VALUE)
    public void downloadAddWatermark(@PathVariable String id, UserPageableRequest request)
    {
        this.pdfAddWatermarkUtils.addWaterMark();
    }
    @SneakyThrows
    @PostMapping(value="/valid")
    public String validateUrl(@RequestBody @Valid UserValidateUrl requestUrl)
    {
        boolean isMatch = Pattern.compile("\"^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]\"")
                .matcher(requestUrl.getCallbackUrl())
                .find();

        if(Boolean.FALSE.equals(isMatch))
            throw new BusinessException("S001", "Error https.");
        return requestUrl.getCallbackUrl();
    }
    @SneakyThrows
    @PostMapping(value="/test/get/crc")
    public String getCrc(@RequestBody @Valid CrcRequest request)
    {
        String crc = String.format("%s|%s|%s", request.getOrderReferenceNo(), request.getCurrency(), request.getTotal());

        return CrcUtils.encode(crc, request.getSalt());
    }
    @SneakyThrows
    @PostMapping(value="/test/get/crc/callback")
    public String getCallbackCrc(@RequestBody @Valid CrcRequest request)
    {
        String crc = String.format("%s|%s|%s|%s|%s", request.getOrderReferenceNo(), request.getTransactionId(), request.getCurrency(), request.getTotal(), request.getFee());

        return CrcUtils.encode(crc, request.getSalt());
    }
    @SneakyThrows
    @PostMapping(value="/test/compare/crc")
    public Boolean compareCrc(@RequestBody @Valid CrcRequest request)
    {
        String getCrc = request.getCrc();
        String crc = String.format("%s|%s|%s", request.getOrderReferenceNo(), request.getCurrency(), request.getTotal());
        String realString = String.format("%s|%s", request.getOrderReferenceNo(), request.getCurrency(), request.getTotal());

        return CrcUtils.encode(crc, request.getSalt()).equalsIgnoreCase(getCrc);
    }
    @SneakyThrows
    @GetMapping(value="/test/date-converter")
    public String dateConverter()
    {
        return DateFormatConverterUtils.simpleConvert(LocalDateTime.now(), "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    }

    @SneakyThrows
    @GetMapping(value="/test/aggregation")
    public AggregationResults<Map> aggregationUser()
    {
        return service.testAggregationMongo();
    }
    @SneakyThrows
    @PostMapping(value="/send-telegram")
    public void sendTelegram(@RequestBody KhQrCallbackRequestVO requestVO)
    {
        telegramUtils.sendMessage(requestVO);
    }
}
