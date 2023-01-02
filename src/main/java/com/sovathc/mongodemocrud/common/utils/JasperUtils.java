//package com.sovathc.mongodemocrud.common.utils;
//
//import lombok.extern.slf4j.Slf4j;
//
//import java.io.*;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.Locale;
//import java.util.Map;
//
//
//import org.springframework.stereotype.Component;
//import org.springframework.util.ResourceUtils;
//
//@Slf4j
//@Component
//public class JasperUtils {
//    public void generateInvoiceFor() throws IOException {
//
//        File pdfFile = File.createTempFile("/Users/9neal/Documents/user", ".pdf");
//
//        log.info(String.format("Invoice pdf path : %s", pdfFile.getAbsolutePath()));
//
//        try(FileOutputStream pos = new FileOutputStream(pdfFile))
//        {
//            // Load invoice JRXML template.
//            final JasperReport report = loadTemplate();
//
//            // Fill parameters map.
//            Map<String, Object> parameters = new HashMap<>();
//            parameters.put("data", "user");
//            // Create an empty dataouvrce.
//            final JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(Collections.singletonList("Invoice"));
//
//            // Render the invoice as a PDF file.
//            JasperPrint print = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
//
//            JasperExportManager.exportReportToPdfStream(print, pos);
//        }
//        catch (final Exception e)
//        {
//            log.error(String.format("An error occured during PDF creation: %s", e));
//            throw new RuntimeException(e);
//        }
//    }
//
//
//    // Load invoice JRXML template
//    private JasperReport loadTemplate() throws JRException, FileNotFoundException {
//
//        File reportInputStream = ResourceUtils.getFile("classpath:reports/report.jrxml");
//        final JasperDesign jasperDesign = JRXmlLoader.load(reportInputStream);
//
//        return JasperCompileManager.compileReport(jasperDesign);
//    }
//
//}
