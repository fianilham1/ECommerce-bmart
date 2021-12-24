package com.tes.buana.entity.web_query;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class BaseParamsQuery {
    private String id;

    private Long longId;

    private int page;

    private int size = 10;

    private String name = "";

    private String sort_by = "createdBy";

    private String direction = "ascending";
}
