package com.cowbeer.offerpost.data.command;

import lombok.Data;

import java.util.List;

/**
 * @author: Diors.Po
 * @Email: 171256175@qq.com
 * @date 2019-12-21 21:42
 */
@Data
public class SearchPostCommand {
    List<String> salaryScope;
    List<String> workLocations;
}
