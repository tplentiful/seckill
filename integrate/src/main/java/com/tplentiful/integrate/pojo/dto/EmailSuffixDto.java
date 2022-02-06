package com.tplentiful.integrate.pojo.dto;

import com.tplentiful.integrate.pojo.po.EmailSuffix;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailSuffixDto {
    private List<String> emails;
}
