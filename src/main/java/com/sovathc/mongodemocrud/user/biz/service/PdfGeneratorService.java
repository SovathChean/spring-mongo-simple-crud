package com.sovathc.mongodemocrud.user.biz.service;

import com.sovathc.mongodemocrud.common.exception.BusinessException;
import com.sovathc.mongodemocrud.user.biz.entity.UserEntity;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public interface PdfGeneratorService {
    void generatePdfFile(String templateName, Map<String, Object> data, String pdfFileName) throws BusinessException;
    void downloadPDF(Map<String, Object> data, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException;
}
