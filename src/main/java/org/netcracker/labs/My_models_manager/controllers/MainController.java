package org.netcracker.labs.My_models_manager.controllers;

import org.netcracker.labs.My_models_manager.XmlConverter;
import org.netcracker.labs.My_models_manager.entities.*;
import org.netcracker.labs.My_models_manager.services.*;
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

import javax.xml.bind.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

@Controller
public class MainController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private ManufacturerService manufacturerService;

    @Autowired
    private ModelStatusService modelStatusService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private PlaceService placeService;

    @Autowired
    private StorageService storageService;

    @Autowired
    private ModelService modelService;

    @Autowired
    private XmlConverter xmlConverter;

    @GetMapping("/")
    public String main(Model model) throws JAXBException, IOException, ParserConfigurationException, TransformerException {
        LOGGER.info("user go on {} page", MainController.class);
        //createManufacturerXml();
        //XmlConverter xmlConverter = new XmlConverter();
        xmlConverter.marshal();
        return "index";
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> download(Model model) throws FileNotFoundException {
        File file = new File("files/all.xml");
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                .headers(header)
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @PostMapping("/upload")
    public String upload(Model model, @RequestParam("file") MultipartFile file){
        if (!file.isEmpty()){
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream("files/import.xml"));
                stream.write(bytes);
                stream.close();
                model.addAttribute("errorText", "Ok");


//                SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
//                Schema schema = schemaFactory.newSchema(new File("modelManager.xsd"));
//                Validator validator = schema.newValidator();
//                validator.validate(new StreamSource(new ByteArrayInputStream(bytes)));

                JAXBContext context = JAXBContext.newInstance(ModelManagerRootXml.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();

                ModelManagerRootXml rootXml = (ModelManagerRootXml) unmarshaller.unmarshal(new File("files/import.xml"));
                for (Manufacturer manufacturer: rootXml.getManufacturers()){
                    manufacturerService.save(manufacturer);
                }

                for (ModelStatus modelStatus: rootXml.getModelStatuses()){
                    modelStatusService.save(modelStatus);
                }

                for (Room room: rootXml.getRooms()){
                    roomService.save(room);
                }

                for (Place place: rootXml.getPlaces()){
                    placeService.save(place);
                }

                for (Storage storage: rootXml.getStorages()){
                    storageService.save(storage);
                }

                for (org.netcracker.labs.My_models_manager.entities.Model model1: rootXml.getModels()){
                    modelService.save(model1);
                }
            } catch (Exception e){
                model.addAttribute("errorText", "bad file");
            }
        }
        else model.addAttribute("errorText", "empty file");
        return "index";
    }

    private void createXsdManufacturer() throws JAXBException, IOException {
        File file = new File("files/manufacturer.xsd");
        JAXBContext context = JAXBContext.newInstance(Manufacturer.class);
        context.generateSchema(new SchemaOutputResolver() {
            @Override
            public Result createOutput(String s, String s1) throws IOException {
                StreamResult result = new StreamResult(new FileOutputStream(file));
                result.setSystemId(file.getName());
                return result;
            }
        });
    }

    private void createManufacturerXml() throws JAXBException, IOException {
        File file = new File("files/all.xml");
        JAXBContext context = JAXBContext.newInstance(ModelManagerRootXml.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        StringWriter writer = new StringWriter();
        ModelManagerRootXml rootXml = new ModelManagerRootXml(manufacturerService.getAll(), modelStatusService.getAll(),
                roomService.getAll(), placeService.getAll(), storageService.getAll(), modelService.getAll());

        marshaller.marshal(rootXml, writer);
        FileWriter fileOutputStream = new FileWriter(file);
        fileOutputStream.write(writer.toString());
        fileOutputStream.close();
    }
}

