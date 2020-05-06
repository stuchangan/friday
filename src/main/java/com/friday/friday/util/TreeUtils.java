package com.friday.friday.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.friday.friday.model.SysPermission;

import java.util.List;

public class TreeUtils{

    /**
     * 菜单树
     * @param parentId 父id
     * @param permissionsAll 所有操作
     * @param jsonArray 树数组
     */
    public static void setPermissionsTree(Integer parentId, List<SysPermission> permissionsAll, JSONArray jsonArray){
        for (SysPermission permission : permissionsAll){
            if(permission.getParentId().equals(parentId)){
                String string = JSONObject.toJSONString(permission);
                JSONObject parent = (JSONObject) JSONObject.parse(string);
                jsonArray.add(parent);
                if(permissionsAll.stream().filter(p -> p.getParentId().equals(permission.getId())).findAny() != null){
                    JSONArray child = new JSONArray();
                    parent.put("child",child);
                    setPermissionsTree(permission.getId(),permissionsAll,child);
                }
            }
        }

    }
}
