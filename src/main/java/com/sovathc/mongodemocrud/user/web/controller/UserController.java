package com.sovathc.mongodemocrud.user.web.controller;

import com.sovathc.mongodemocrud.common.controller.AbstractController;
import com.sovathc.mongodemocrud.common.controller.ResponseBuilderMessage;
import com.sovathc.mongodemocrud.common.controller.ResponseMessage;
import com.sovathc.mongodemocrud.common.response.PageableResponse;
import com.sovathc.mongodemocrud.common.utils.PdfAddWatermarkUtils;
import com.sovathc.mongodemocrud.common.utils.PdfGeneratorUtils;
import com.sovathc.mongodemocrud.common.utils.PdfTextGenerateUtils;
import com.sovathc.mongodemocrud.common.utils.PdfWaterMarkUtils;
import com.sovathc.mongodemocrud.user.biz.dto.UserDTO;
import com.sovathc.mongodemocrud.user.biz.mapper.UserMapper;
import com.sovathc.mongodemocrud.user.biz.service.UserService;
import com.sovathc.mongodemocrud.user.web.vo.request.UserCreatedRequest;
import com.sovathc.mongodemocrud.user.web.vo.request.UserPageableRequest;
import com.sovathc.mongodemocrud.user.web.vo.request.UserUpdatedRequest;
import com.sovathc.mongodemocrud.user.web.vo.response.UserItemResponse;
import com.sovathc.mongodemocrud.user.web.vo.response.UserResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

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
}
