package org.jeecg.modules.tms.materialcate.entity;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 部门表 存储树结构数据的实体类
 * <p>
 * 
 * @Author Steve
 * @Since 2019-01-22 
 */
public class TmsMaterialCateTreeModel implements Serializable{

    private static final long serialVersionUID = 1L;

    /** 对应SysDepart中的id字段,前端数据树中的key*/
    private String key;

    /** 对应SysDepart中的id字段,前端数据树中的value*/
    private String value;

    /** 对应depart_name字段,前端数据树中的title*/
    private String title;


    private boolean isLeaf;
    // 以下所有字段均与SysDepart相同

    private String id;

    private String createBy;

    private Date createTime;

    private String updateBy;
    /**更新日期*/

    private Date updateTime;
    /**所属部门*/

    private String sysOrgCode;
    /**父级节点*/
    private String pid;
    /**是否有子节点*/
    private String hasChild;
    /**分类编码*/
    private String cateCode;
    /**分类名称*/
    private String cateName;

    public TmsMaterialCateTreeModel() { }

    private List<TmsMaterialCateTreeModel> children = new ArrayList<>();

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



    public boolean getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(boolean isleaf) {
        this.isLeaf = isleaf;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getSysOrgCode() {
        return sysOrgCode;
    }

    public void setSysOrgCode(String sysOrgCode) {
        this.sysOrgCode = sysOrgCode;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getHasChild() {
        return hasChild;
    }

    public void setHasChild(String hasChild) {
        this.hasChild = hasChild;
    }

    public String getCateCode() {
        return cateCode;
    }

    public void setCateCode(String cateCode) {
        this.cateCode = cateCode;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public List<TmsMaterialCateTreeModel> getChildren() {
        return children;
    }

    public void setChildren(List<TmsMaterialCateTreeModel> children) {
        if (children==null){
            this.isLeaf=true;
        }
        this.children = children;
    }




    /**
     * 将SysDepart对象转换成SysDepartTreeModel对象
     * @param sysDepart
     */
    public TmsMaterialCateTreeModel(TmsMaterialCate tmsMaterialCate) {
        this.key = tmsMaterialCate.getId();
        this.value = tmsMaterialCate.getId();
        this.title = tmsMaterialCate.getCateName();
        this.id = tmsMaterialCate.getId();

        this.pid = tmsMaterialCate.getPid();
        this.cateCode = tmsMaterialCate.getCateCode();
        this.cateName = tmsMaterialCate.getCateName();
        this.createBy = tmsMaterialCate.getCreateBy();
        this.createTime = tmsMaterialCate.getCreateTime();
        this.updateBy = tmsMaterialCate.getUpdateBy();
        this.updateTime = tmsMaterialCate.getUpdateTime();
        this.sysOrgCode = tmsMaterialCate.getSysOrgCode();
        this.hasChild=tmsMaterialCate.getHasChild();

    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
			return true;
		}
        if (o == null || getClass() != o.getClass()) {
			return false;
		}
        TmsMaterialCateTreeModel that = (TmsMaterialCateTreeModel) o;
        return isLeaf == that.isLeaf &&
                Objects.equals(getKey(), that.getKey()) &&
                Objects.equals(getValue(), that.getValue()) &&
                Objects.equals(getTitle(), that.getTitle()) &&
                Objects.equals(getId(), that.getId()) &&
                Objects.equals(createBy, that.createBy) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(updateBy, that.updateBy) &&
                Objects.equals(updateTime, that.updateTime) &&
                Objects.equals(sysOrgCode, that.sysOrgCode) &&
                Objects.equals(pid, that.pid) &&
                Objects.equals(hasChild, that.hasChild) &&
                Objects.equals(cateCode, that.cateCode) &&
                Objects.equals(cateName, that.cateName) &&
                Objects.equals(getChildren(), that.getChildren());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKey(), getValue(), getTitle(), isLeaf, getId(), createBy, createTime, updateBy, updateTime, sysOrgCode, pid, hasChild, cateCode, cateName, getChildren());
    }







}
