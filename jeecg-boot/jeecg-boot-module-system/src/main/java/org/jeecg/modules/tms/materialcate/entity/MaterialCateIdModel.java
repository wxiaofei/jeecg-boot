package org.jeecg.modules.tms.materialcate.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 部门表 封装树结构的部门的名称的实体类
 * <p>
 * 
 * @Author Steve
 * @Since 2019-01-22 
 *
 */
public class MaterialCateIdModel implements Serializable {


    // 主键ID
    private String key;

    // 主键ID
    private String value;

    // 部门名称
    private String title;
    
    List<MaterialCateIdModel> children = new ArrayList<>();
    
    /**
     * 将SysDepartTreeModel的部分数据放在该对象当中
     * @param treeModel
     * @return
     */
    public MaterialCateIdModel convert(TmsMaterialCateTreeModel treeModel) {
        this.key = treeModel.getId();
        this.value = treeModel.getId();
        this.title = treeModel.getCateName();
        return this;
    }
    
    /**
     * 该方法为用户部门的实现类所使用
     * @param materialCate
     * @return
     */
    public MaterialCateIdModel convertByUserDepart(TmsMaterialCate materialCate) {
        this.key = materialCate.getId();
        this.value = materialCate.getId();
        this.title = materialCate.getCateName();
        return this;
    } 

    public List<MaterialCateIdModel> getChildren() {
        return children;
    }

    public void setChildren(List<MaterialCateIdModel> children) {
        this.children = children;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
