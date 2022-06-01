package com.atguigu.helper;

import com.atguigu.entity.Permission;

import java.util.ArrayList;
import java.util.List;

public  class MyHelper {

    public static List<Permission> build(List<Permission> permissionList)
    {
        List<Permission> treeNodes = new ArrayList<>();

        for (Permission treeNode : permissionList) {
            if(treeNode.getParentId() == 0)
            {
                treeNode.setLevel(1);
                treeNode.setChildren(getChildren(treeNode,permissionList));
                treeNodes.add(treeNode);
            }
        }
        return treeNodes;
    }

    private static List<Permission> getChildren(Permission parent,List<Permission> originalPermissionList)
    {
        ArrayList<Permission> childrens = new ArrayList<>();
        for (Permission children : originalPermissionList) {
            if(children.getParentId() == parent.getId())
            {
                children.setLevel(parent.getLevel()+1);
                children.setParentName(parent.getName());
                children.setChildren(getChildren(children,originalPermissionList));
                childrens.add(children);
            }
        }
        return childrens;
    }
}
