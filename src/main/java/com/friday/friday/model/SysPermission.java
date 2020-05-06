package com.friday.friday.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class SysPermission extends BaseEntity<Integer> {
    private static final long serialVersionUID = -6525908145032868837L;
    private Integer parentId;
    private String name;
    private String css;
    private String href;
    private Integer type;
    private String permission;
    private Integer sort;

    private List<SysPermission> child;



}
