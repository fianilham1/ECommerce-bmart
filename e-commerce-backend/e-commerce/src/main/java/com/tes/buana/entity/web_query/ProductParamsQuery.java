package com.tes.buana.entity.web_query;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductParamsQuery extends BaseParamsQuery{
    String category = "";
    String searchKeyword = "";
}
