package by.zenonwch.webapp.teststore.service;

import by.zenonwch.webapp.teststore.model.PartModel;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PartService {
    PartModel createPart(PartModel newPart);

    PartModel getPart(int id);

    List<PartModel> getParts();

    Page<PartModel> getParts(int page, int size);

    int getPossibleComputersCount();

    PartModel updatePart(PartModel updatedPart);

    void deletePart(int id);
}
