package by.zenonwch.webapp.teststore.repo;

import by.zenonwch.webapp.teststore.model.PartModel;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PagingPartRepo extends PagingAndSortingRepository<PartModel, Integer> {
}
