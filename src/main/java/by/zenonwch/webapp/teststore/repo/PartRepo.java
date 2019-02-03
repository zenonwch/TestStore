package by.zenonwch.webapp.teststore.repo;

import by.zenonwch.webapp.teststore.model.PartModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PartRepo extends JpaRepository<PartModel, Integer>, JpaSpecificationExecutor<PartModel> {
    PartModel findFirstByRequiredTrueOrderByCountAsc();
}