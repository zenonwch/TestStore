package by.zenonwch.webapp.teststore.model;

import by.zenonwch.webapp.teststore.dto.PartDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static by.zenonwch.webapp.teststore.model.PartModel.TABLE_NAME;


@Entity
@Table(name = TABLE_NAME)
@Getter
@Setter
public class PartModel {
    static final String TABLE_NAME = "parts";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq")
    private int id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private boolean required;
    @Column(nullable = false)
    private int count;

    public PartDto toDto() {
        final PartDto dto = new PartDto();
        dto.setId(id);
        dto.setName(name);
        dto.setRequired(required);
        dto.setCount(count);
        return dto;
    }
}