package by.zenonwch.webapp.teststore.service;

import by.zenonwch.webapp.teststore.model.PartModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface PartService {
    PartModel createPart(PartModel newPart);

    PartModel getPart(int id);

    List<PartModel> getParts();

    Page<PartModel> getParts(Specification<PartModel> specification, Pageable pageable);

    int getPossibleComputersCount();

    PartModel updatePart(PartModel updatedPart);

    void deletePart(int id);
}