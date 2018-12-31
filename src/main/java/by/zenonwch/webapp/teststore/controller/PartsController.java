package by.zenonwch.webapp.teststore.controller;

import by.zenonwch.webapp.teststore.dto.Paging;
import by.zenonwch.webapp.teststore.dto.PartDto;
import by.zenonwch.webapp.teststore.model.PartModel;
import by.zenonwch.webapp.teststore.service.PartService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PartsController {
    private static final String INITIAL_PAGE = "1";
    private static final int DEFAULT_PAGE_SIZE = 10;

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
    public String getAllParts(
            final Model model,
            @RequestParam(name = "page", defaultValue = INITIAL_PAGE) final int page
    ) {
        final int pageNumber = page < 1 ? 0 : page - 1;
        final Page<PartModel> partList = partService.getParts(pageNumber, DEFAULT_PAGE_SIZE);

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
}