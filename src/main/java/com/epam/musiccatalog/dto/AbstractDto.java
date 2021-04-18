package com.epam.musiccatalog.dto;

import lombok.Data;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
@Data
public abstract class AbstractDto implements Serializable {

    private Long id;
}
