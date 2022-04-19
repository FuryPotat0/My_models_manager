package org.netcracker.labs.My_models_manager;

import org.netcracker.labs.My_models_manager.entities.*;
import org.netcracker.labs.My_models_manager.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

@Service
public class XmlConverter {
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

    public void marshal() throws ParserConfigurationException, TransformerException {
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document newDoc = documentBuilder.newDocument();
        Element rootElement = newDoc.createElement("ModelManagerRootXml");
        newDoc.appendChild(rootElement);

        this.writeManufacturers(newDoc, rootElement);
        this.writeModelStatuses(newDoc, rootElement);
        this.writeRooms(newDoc, rootElement);
        this.writePlaces(newDoc, rootElement);
        this.writeStorages(newDoc, rootElement);
        this.writeModels(newDoc, rootElement);

        this.transformToXml(newDoc);
    }

    private void transformToXml(Document newDoc) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(newDoc);
        StreamResult result = new StreamResult(new File("files/all.xml"));
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
    }

//    public void unmarshal(MultipartFile file) throws ParserConfigurationException, IOException, SAXException {
//        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
//        Document document = documentBuilder.parse((File) file);
//
//        NodeList manufacturers = document.getElementsByTagName("manufacturer");
//        Node manufacturer;
//        NodeList nodeList;
//        for (int i = 0; i < manufacturers.getLength(); i++){
//            manufacturer = manufacturers.item(i);
//            nodeList = manufacturer.getChildNodes();
//            nodeList.
//
//        }
//    }
}

