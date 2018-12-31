package by.zenonwch.webapp.teststore.service;

import by.zenonwch.webapp.teststore.exception.PartNotFoundException;
import by.zenonwch.webapp.teststore.model.PartModel;
import by.zenonwch.webapp.teststore.repo.PagingPartRepo;
import by.zenonwch.webapp.teststore.repo.PartRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PartServiceImpl implements PartService {

    private final PartRepo partRepo;
    private final PagingPartRepo pagingPartRepo;

    public PartServiceImpl(final PartRepo partRepo, final PagingPartRepo pagingPartRepo) {
        this.partRepo = partRepo;
        this.pagingPartRepo = pagingPartRepo;
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
    @Transactional(readOnly = true)
    public Page<PartModel> getParts(final int page, final int size) {
        return pagingPartRepo.findAll(PageRequest.of(page, size));
    }

    @Override
    @Transactional(readOnly = true)
    public int getPossibleComputersCount() {
        return partRepo.findFirstByRequiredTrueOrderByCountAsc().getCount();
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