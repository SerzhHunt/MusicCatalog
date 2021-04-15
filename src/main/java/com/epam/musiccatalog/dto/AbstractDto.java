package com.epam.musiccatalog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AbstractDto implements Serializable {

    private Long id;
}
