package by.zenonwch.webapp.teststore.controller;

import by.zenonwch.webapp.teststore.dto.Paging;
import by.zenonwch.webapp.teststore.dto.PartDto;
import by.zenonwch.webapp.teststore.model.PartModel;
import by.zenonwch.webapp.teststore.service.PartService;
import by.zenonwch.webapp.teststore.util.JsonMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class PartsController {
    private static final char PERCENT_SIGN = '%';
    private static final String DEFAULT_PAGE = "1";
    private static final String DEFAULT_PAGE_SIZE = "10";
    private static final String DEFAULT_SORT_FIELD = "name";
    private static final String DEFAULT_SORT_DIR = "ASC";
    private static final String DEFAULT_FILTERS = "";

    private final PartService partService;

    public PartsController(final PartService partService) {
        this.partService = partService;
    }

    @PostMapping("/parts")
    public String createPart(@Valid @ModelAttribute final PartDto part, final Model model) {
        final PartModel newPart = part.toModel();
        final PartModel savedPart = partService.createPart(newPart);
        final PartDto savedDto = savedPart.toDto();

        model.addAttribute("part", savedDto);
        return "redirect:/parts";
    }

    @GetMapping("/parts")
    public String getAllParts(final Model model,
                              @RequestParam(name = "page", defaultValue = DEFAULT_PAGE) final int page,
                              @RequestParam(name = "size", defaultValue = DEFAULT_PAGE_SIZE) final int size,
                              @RequestParam(name = "sortField", defaultValue = DEFAULT_SORT_FIELD) final String sortBy,
                              @RequestParam(name = "sortDir", defaultValue = DEFAULT_SORT_DIR) final String sortDir,
                              @RequestParam(name = "filters", defaultValue = DEFAULT_FILTERS) final String filters) {

        final int pageNumber = page < 1 ? 0 : page - 1;
        final Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(Sort.Direction.fromString(sortDir), sortBy));

        final Specification<PartModel> specification = createPartSpecification(filters);

        final Page<PartModel> partList = partService.getParts(specification, pageable);

        final List<PartDto> partDtoList = partList.stream()
                .map(PartModel::toDto)
                .collect(Collectors.toList());

        final int totalPages = partList.getTotalPages();
        final int currentPage = partList.getNumber();
        final Paging paging = new Paging(totalPages, currentPage);

        final int canAssemble = partService.getPossibleComputersCount();

        model.addAttribute("mode", "list");
        model.addAttribute("parts", partDtoList);
        model.addAttribute("canAssemble", canAssemble);
        model.addAttribute("paging", paging);
        return "parts";
    }

    @GetMapping("/newPart")
    public String getCreationPage(final Model model) {
        model.addAttribute("part", new PartDto());
        return "parts";
    }

    @GetMapping("/parts/{id}")
    public String getPart(@PathVariable("id") final int id, final Model model) {
        final PartModel part = partService.getPart(id);
        final PartDto partDto = part.toDto();

        model.addAttribute("part", partDto);
        return "parts";
    }

    @PutMapping("/parts/{id}")
    @SuppressWarnings("MVCPathVariableInspection")
    public String updatePart(@Valid @ModelAttribute final PartDto part, final Model model) {
        final PartModel updatedPart = part.toModel();
        final PartModel savedPart = partService.updatePart(updatedPart);
        final PartDto savedDto = savedPart.toDto();

        model.addAttribute("part", savedDto);
        return "redirect:/parts";
    }

    @DeleteMapping("/parts/{id}")
    public String deletePart(@PathVariable("id") final int id, final Model model) {
        partService.deletePart(id);

        model.addAttribute("mode", "list");
        return "redirect:/parts";
    }

    private Specification<PartModel> createPartSpecification(final String filters) {
        final Map<String, String> filterMap = filters == null || filters.isEmpty()
                ? Collections.emptyMap()
                : JsonMapper.fromJsonToMap(filters);

        return (Specification<PartModel>) (root, query, cb) -> {

            final Function<String, Predicate> buildPredicate = key -> {
                final Path<String> value = root.get(key);
                final Expression<String> expression = cb.lower(value);
                final String pattern = PERCENT_SIGN + filterMap.get(key).toLowerCase() + PERCENT_SIGN;

                return cb.like(expression, pattern);
            };

            final Predicate[] predicates = filterMap.keySet().stream()
                    .map(buildPredicate)
                    .toArray(Predicate[]::new);

            return cb.and(predicates);
        };
    }
}