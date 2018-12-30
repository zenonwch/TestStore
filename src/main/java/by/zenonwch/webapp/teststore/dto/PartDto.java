package by.zenonwch.webapp.teststore.dto;

import by.zenonwch.webapp.teststore.model.PartModel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class PartDto {
    private int id;
    @NonNull
    private String name;
    private boolean required;
    private int count;

    public PartModel toModel() {
        final PartModel model = new PartModel();
        model.setId(id);
        model.setName(name);
        model.setRequired(required);
        model.setCount(count);
        return model;
    }
}