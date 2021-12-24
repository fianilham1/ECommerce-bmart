package com.tes.buana.common.util;

import org.springframework.data.domain.Sort;

public class SortDirectionUtil {
    public static Sort.Direction getDirection(String requestDirection){
       if(requestDirection.equalsIgnoreCase("desc") ||
               requestDirection.equalsIgnoreCase("descending")) return Sort.Direction.DESC;
       return Sort.Direction.ASC;
    }
}
