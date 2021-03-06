package org.netcracker.labs.My_models_manager.controllers;

import org.netcracker.labs.My_models_manager.XmlConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

@Controller
public class MainController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private XmlConverter xmlConverter;

    @GetMapping("/")
    public String main(Model model) {
        LOGGER.info("user go on {} page", MainController.class);
        return "index";
    }

    @GetMapping("/preview")
    public String preview(Model model){
        return "preview";
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> download(Model model) {
        try {
            File file = xmlConverter.marshal();
            HttpHeaders header = new HttpHeaders();
            header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .headers(header)
                    .contentLength(file.length())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } catch (Exception e) {
            LOGGER.error("error while marshalling");
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    @PostMapping("/upload")
    public String upload(Model model, @RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                model.addAttribute("errorText", "Ok");
                xmlConverter.unmarshal(file);
                xsltConverter();
                return "redirect:/preview";
            } catch (Exception e) {
                xmlConverter.deleteAll();
                model.addAttribute("errorText", "can't convert this file");
                LOGGER.error("error while unmarshalling");
                LOGGER.error(e.getMessage(), e);
            }
        } else model.addAttribute("errorText", "empty file");
        return "index";
    }

    private void xsltConverter(){
        try {
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Source xslDoc = new StreamSource("src/main/resources/static/xsltConverter.xsl");
            Source xmlDoc = new StreamSource("files/import.xml");
            String outputFileName = "src/main/resources/templates/preview.html";
            OutputStream htmlFile = new FileOutputStream(outputFileName);
            Transformer transformer = tFactory.newTransformer(xslDoc);
            transformer.transform(xmlDoc, new StreamResult(htmlFile));
        } catch (Exception e) {
            LOGGER.error("error while XSLT convert");
            LOGGER.error(e.getMessage(), e);
        }
    }
}

