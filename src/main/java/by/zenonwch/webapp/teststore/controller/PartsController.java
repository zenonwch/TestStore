package by.zenonwch.webapp.teststore.controller;

import by.zenonwch.webapp.teststore.dto.PartDto;
import by.zenonwch.webapp.teststore.model.PartModel;
import by.zenonwch.webapp.teststore.service.PartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PartsController {

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
    public String getAllParts(final Model model) {
        final List<PartModel> partList = partService.getParts();

        final List<PartDto> partDtoList = partList.stream()
                .map(PartModel::toDto)
                .collect(Collectors.toList());

        final int canAssemble = partDtoList.stream()
                .filter(PartDto::isRequired)
                .mapToInt(PartDto::getCount)
                .min()
                .orElse(0);

        model.addAttribute("parts", partDtoList);
        model.addAttribute("canAssemble", canAssemble);
        model.addAttribute("mode", "list");
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