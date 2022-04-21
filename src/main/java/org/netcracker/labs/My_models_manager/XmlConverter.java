package org.netcracker.labs.My_models_manager;

import org.netcracker.labs.My_models_manager.controllers.MainController;
import org.netcracker.labs.My_models_manager.entities.*;
import org.netcracker.labs.My_models_manager.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Service
public class XmlConverter {
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

    public File marshal() throws ParserConfigurationException, TransformerException {
        LOGGER.info("User want marshal database into xml");
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document newDoc = documentBuilder.newDocument();
        Element rootElement = newDoc.createElement("ModelManagerRootXml");
        newDoc.appendChild(rootElement);

        writeAll(newDoc, rootElement);
        transformToXml(newDoc);
        return new File("files/export.xml");
    }

    private void writeAll(Document newDoc, Element rootElement) {
        writeManufacturers(newDoc, rootElement);
        writeModelStatuses(newDoc, rootElement);
        writeRooms(newDoc, rootElement);
        writePlaces(newDoc, rootElement);
        writeStorages(newDoc, rootElement);
        writeModels(newDoc, rootElement);
    }

    private void transformToXml(Document newDoc) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(newDoc);
        StreamResult result = new StreamResult(new File("files/export.xml"));
        transformer.transform(source, result);
    }

    private void writeManufacturers(Document newDoc, Element rootElement) {
        Element manufacturers = newDoc.createElement("Manufacturers");
        rootElement.appendChild(manufacturers);

        Element manufacturer;
        Element id, name;
        for (Manufacturer manufacturer1 : manufacturerService.getAll()) {
            manufacturer = newDoc.createElement("manufacturer");
            manufacturers.appendChild(manufacturer);

            id = newDoc.createElement("id");
            id.appendChild(newDoc.createTextNode(manufacturer1.getId().toString()));
            manufacturer.appendChild(id);

            name = newDoc.createElement("name");
            name.appendChild(newDoc.createTextNode(manufacturer1.getName()));
            manufacturer.appendChild(name);
        }
        LOGGER.info("Manufacturers marshalling done");
    }

    private void writeModelStatuses(Document newDoc, Element rootElement) {
        Element modelStatuses = newDoc.createElement("ModelStatuses");
        rootElement.appendChild(modelStatuses);

        Element modelStatus;
        Element id, name;
        for (ModelStatus modelStatus1 : modelStatusService.getAll()) {
            modelStatus = newDoc.createElement("modelStatus");
            modelStatuses.appendChild(modelStatus);

            id = newDoc.createElement("id");
            id.appendChild(newDoc.createTextNode(modelStatus1.getId().toString()));
            modelStatus.appendChild(id);

            name = newDoc.createElement("name");
            name.appendChild(newDoc.createTextNode(modelStatus1.getName()));
            modelStatus.appendChild(name);
        }
        LOGGER.info("ModelStatuses marshalling done");
    }

    private void writeRooms(Document newDoc, Element rootElement) {
        Element rooms = newDoc.createElement("Rooms");
        rootElement.appendChild(rooms);

        Element room;
        Element id, name;
        for (Room room1 : roomService.getAll()) {
            room = newDoc.createElement("room");
            rooms.appendChild(room);

            id = newDoc.createElement("id");
            id.appendChild(newDoc.createTextNode(room1.getId().toString()));
            room.appendChild(id);

            name = newDoc.createElement("name");
            name.appendChild(newDoc.createTextNode(room1.getName()));
            room.appendChild(name);
        }
        LOGGER.info("Rooms marshalling done");
    }

    private void writePlaces(Document newDoc, Element rootElement) {
        Element places = newDoc.createElement("Places");
        rootElement.appendChild(places);

        Element place;
        Element id, name, description, roomId;
        for (Place place1 : placeService.getAll()) {
            place = newDoc.createElement("place");
            places.appendChild(place);

            id = newDoc.createElement("id");
            id.appendChild(newDoc.createTextNode(place1.getId().toString()));
            place.appendChild(id);

            name = newDoc.createElement("name");
            name.appendChild(newDoc.createTextNode(place1.getName()));
            place.appendChild(name);

            description = newDoc.createElement("description");
            description.appendChild(newDoc.createTextNode(place1.getDescription()));
            place.appendChild(description);

            roomId = newDoc.createElement("roomId");
            roomId.appendChild(newDoc.createTextNode(place1.getRoom().getId().toString()));
            place.appendChild(roomId);
        }
        LOGGER.info("Places marshalling done");
    }

    private void writeStorages(Document newDoc, Element rootElement) {
        Element storages = newDoc.createElement("Storages");
        rootElement.appendChild(storages);

        Element storage;
        Element id, name, description, placeId;
        for (Storage storage1 : storageService.getAll()) {
            storage = newDoc.createElement("storage");
            storages.appendChild(storage);

            id = newDoc.createElement("id");
            id.appendChild(newDoc.createTextNode(storage1.getId().toString()));
            storage.appendChild(id);

            name = newDoc.createElement("name");
            name.appendChild(newDoc.createTextNode(storage1.getName()));
            storage.appendChild(name);

            description = newDoc.createElement("description");
            description.appendChild(newDoc.createTextNode(storage1.getDescription()));
            storage.appendChild(description);

            placeId = newDoc.createElement("placeId");
            placeId.appendChild(newDoc.createTextNode(storage1.getPlace().getId().toString()));
            storage.appendChild(placeId);
        }
        LOGGER.info("Storages marshalling done");
    }

    private void writeModels(Document newDoc, Element rootElement) {
        Element models = newDoc.createElement("Models");
        rootElement.appendChild(models);

        Element model;
        Element id, name, description, storageId, modelsInSquad, manufacturerId, modelStatusId;
        for (Model model1 : modelService.getAll()) {
            model = newDoc.createElement("model");
            models.appendChild(model);

            id = newDoc.createElement("id");
            id.appendChild(newDoc.createTextNode(model1.getId().toString()));
            model.appendChild(id);

            name = newDoc.createElement("name");
            name.appendChild(newDoc.createTextNode(model1.getName()));
            model.appendChild(name);

            modelsInSquad = newDoc.createElement("modelsInSquad");
            modelsInSquad.appendChild(newDoc.createTextNode(String.valueOf(model1.getModelsInSquad())));
            model.appendChild(modelsInSquad);

            description = newDoc.createElement("description");
            description.appendChild(newDoc.createTextNode(model1.getDescription()));
            model.appendChild(description);

            manufacturerId = newDoc.createElement("manufacturerId");
            manufacturerId.appendChild(newDoc.createTextNode(model1.getManufacturer().getId().toString()));
            model.appendChild(manufacturerId);

            modelStatusId = newDoc.createElement("modelStatusId");
            modelStatusId.appendChild(newDoc.createTextNode(model1.getModelStatus().getId().toString()));
            model.appendChild(modelStatusId);

            storageId = newDoc.createElement("storageId");
            storageId.appendChild(newDoc.createTextNode(model1.getStorage().getId().toString()));
            model.appendChild(storageId);
        }
        LOGGER.info("Models marshalling done");
    }

    public void unmarshal(MultipartFile file) throws ParserConfigurationException, IOException, SAXException {
        LOGGER.info("User want unmarshal file");
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        File tempFile = new File("files/import.xml");
        OutputStream os = new FileOutputStream(tempFile);
        os.write(file.getBytes());
        Document document = documentBuilder.parse(tempFile);

        deleteAll();
        saveAll(document);
    }

    private String getIProperty(NodeList properties, int i) {
        return properties.item(i).getChildNodes().item(0).getTextContent();
    }

    private void deleteAll() {
        modelService.deleteAll();
        storageService.deleteAll();
        placeService.deleteAll();
        roomService.getAll();
        manufacturerService.deleteAll();
        modelStatusService.deleteAll();
        LOGGER.info("All data was deleted");
    }

    private void saveAll(Document document) {
        saveManufacturers(document);
        saveModelStatuses(document);
        saveRooms(document);
        savePlaces(document);
        saveStorages(document);
        saveModels(document);
    }

    private void saveManufacturers(Document document) {
        NodeList manufacturers = document.getElementsByTagName("manufacturer");
        Node manufacturer;
        NodeList manufacturerProperties;
        for (int i = 0; i < manufacturers.getLength(); i++) {
            manufacturer = manufacturers.item(i);
            manufacturerProperties = manufacturer.getChildNodes();

            Manufacturer manufacturer1 = new Manufacturer();
            manufacturer1.setName(getIProperty(manufacturerProperties, 1));
            manufacturerService.save(manufacturer1, Long.parseLong(getIProperty(manufacturerProperties, 0)));
        }
        LOGGER.info("Manufacturers unmarshalling done");
    }

    private void saveModelStatuses(Document document) {
        NodeList modelStatuses = document.getElementsByTagName("modelStatus");
        Node modelStatus;
        NodeList modelStatusProperties;
        for (int i = 0; i < modelStatuses.getLength(); i++) {
            modelStatus = modelStatuses.item(i);
            modelStatusProperties = modelStatus.getChildNodes();

            ModelStatus modelStatus1 = new ModelStatus();
            modelStatus1.setName(getIProperty(modelStatusProperties, 1));
            modelStatusService.save(modelStatus1, Long.parseLong(getIProperty(modelStatusProperties, 0)));
        }
        LOGGER.info("ModelStatuses unmarshalling done");
    }

    private void saveRooms(Document document) {
        NodeList rooms = document.getElementsByTagName("room");
        Node room;
        NodeList roomProperties;
        for (int i = 0; i < rooms.getLength(); i++) {
            room = rooms.item(i);
            roomProperties = room.getChildNodes();

            Room room1 = new Room();
            room1.setName(getIProperty(roomProperties, 1));
            roomService.save(room1, Long.parseLong(getIProperty(roomProperties, 0)));
        }
        LOGGER.info("Rooms unmarshalling done");
    }

    private void savePlaces(Document document) {
        NodeList places = document.getElementsByTagName("place");
        Node place;
        NodeList placeProperties;
        for (int i = 0; i < places.getLength(); i++) {
            place = places.item(i);
            placeProperties = place.getChildNodes();

            Place place1 = new Place();
            place1.setName(getIProperty(placeProperties, 1));
            if (placeProperties.item(2).getChildNodes().item(0) != null)
                place1.setDescription(getIProperty(placeProperties, 2));
            place1.setRoom(roomService.findById(Long.parseLong(getIProperty(placeProperties, 3))).get());
            placeService.save(place1, Long.parseLong(getIProperty(placeProperties, 0)));
        }
        LOGGER.info("Places unmarshalling done");
    }

    private void saveStorages(Document document) {
        NodeList storages = document.getElementsByTagName("storage");
        Node storage;
        NodeList storageProperties;
        for (int i = 0; i < storages.getLength(); i++) {
            storage = storages.item(i);
            storageProperties = storage.getChildNodes();

            Storage storage1 = new Storage();
            storage1.setName(getIProperty(storageProperties, 1));
            if (storageProperties.item(2).getChildNodes().item(0) != null)
                storage1.setDescription(getIProperty(storageProperties, 2));
            storage1.setPlace(placeService.findById(Long.parseLong(getIProperty(storageProperties, 3))).get());
            storageService.save(storage1, Long.parseLong(getIProperty(storageProperties, 0)));
        }
        LOGGER.info("Storages unmarshalling done");
    }

    private void saveModels(Document document) {
        NodeList storages = document.getElementsByTagName("model");
        Node model;
        NodeList modelProperties;
        for (int i = 0; i < storages.getLength(); i++) {
            model = storages.item(i);
            modelProperties = model.getChildNodes();

            Model model1 = new Model();
            model1.setName(getIProperty(modelProperties, 1));
            model1.setModelsInSquad(
                    Integer.parseInt(getIProperty(modelProperties, 2)));
            if (modelProperties.item(3).getChildNodes().item(0) != null)
                model1.setDescription(getIProperty(modelProperties, 3));
            model1.setManufacturer(manufacturerService.findById(Long.parseLong(getIProperty(modelProperties, 4))).get());
            model1.setModelStatus(modelStatusService.findById(Long.parseLong(getIProperty(modelProperties, 5))).get());
            model1.setStorage(storageService.findById(Long.parseLong(getIProperty(modelProperties, 6))).get());

            modelService.save(model1, Long.parseLong(getIProperty(modelProperties, 0)));
        }
        LOGGER.info("Models unmarshalling done");
    }
}

