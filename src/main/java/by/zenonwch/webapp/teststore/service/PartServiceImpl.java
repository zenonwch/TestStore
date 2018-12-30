package by.zenonwch.webapp.teststore.service;

import by.zenonwch.webapp.teststore.exception.PartNotFoundException;
import by.zenonwch.webapp.teststore.model.PartModel;
import by.zenonwch.webapp.teststore.repo.PartRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PartServiceImpl implements PartService {

    private final PartRepo partRepo;

    public PartServiceImpl(final PartRepo partRepo) {
        this.partRepo = partRepo;
    }

    @Override
    @Transactional
    public PartModel createPart(final PartModel newPart) {
        return partRepo.saveAndFlush(newPart);
    }

    @Override
    @Transactional(readOnly = true)
    public PartModel getPart(final int id) {
        final Optional<PartModel> optPart = partRepo.findById(id);

        if (!optPart.isPresent()) {
            throw new PartNotFoundException();
        }

        return optPart.get();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PartModel> getParts() {
        return partRepo.findAll();
    }

    @Override
    @Transactional
    public PartModel updatePart(final PartModel updatedPart) {
        final PartModel existingPart = getPart(updatedPart.getId());

        existingPart.setName(updatedPart.getName());
        existingPart.setRequired(updatedPart.isRequired());
        existingPart.setCount(updatedPart.getCount());

        return partRepo.saveAndFlush(existingPart);
    }

    @Override
    @Transactional
    public void deletePart(final int id) {
        partRepo.deleteById(id);
    }
}