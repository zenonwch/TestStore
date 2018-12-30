package by.zenonwch.webapp.teststore.service;

import by.zenonwch.webapp.teststore.model.PartModel;

import java.util.List;

public interface PartService {
    PartModel createPart(PartModel newPart);

    PartModel getPart(int id);

    List<PartModel> getParts();

    PartModel updatePart(PartModel updatedPart);

    void deletePart(int id);
}
