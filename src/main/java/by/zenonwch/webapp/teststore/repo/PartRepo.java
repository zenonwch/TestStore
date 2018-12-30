package by.zenonwch.webapp.teststore.repo;

import by.zenonwch.webapp.teststore.model.PartModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PartRepo extends JpaRepository<PartModel, Long> {
    Optional<PartModel> findById(int id);

    void deleteById(int id);
}