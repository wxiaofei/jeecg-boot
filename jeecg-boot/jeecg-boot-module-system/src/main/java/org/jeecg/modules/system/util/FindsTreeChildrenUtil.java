package org.jeecg.modules.system.util;

import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.tms.materialcate.entity.MaterialCateIdModel;
import org.jeecg.modules.tms.materialcate.entity.TmsMaterialCate;
import org.jeecg.modules.tms.materialcate.entity.TmsMaterialCateTreeModel;

import java.util.ArrayList;
import java.util.List;

/**
 * <P>
 * 对应部门的表,处理并查找树级数据
 * <P>
 * 
 * @Author: Steve
 * @Date: 2019-01-22
 */
public class FindsTreeChildrenUtil {

	//部门树信息-树结构
	//private static List<SysDepartTreeModel> sysDepartTreeList = new ArrayList<SysDepartTreeModel>();

	//部门树id-树结构
    //private static List<DepartIdModel> idList = new ArrayList<>();


    /**
     * queryTreeList的子方法 ====1=====
     * 该方法是s将SysDepart类型的list集合转换成SysDepartTreeModel类型的集合
     */
    public static List<TmsMaterialCateTreeModel> wrapTreeDataToTreeList(List<TmsMaterialCate> recordList) {
        // 在该方法每请求一次,都要对全局list集合进行一次清理
        //idList.clear();
    	List<MaterialCateIdModel> idList = new ArrayList<MaterialCateIdModel>();
        List<TmsMaterialCateTreeModel> records = new ArrayList<>();
        for (int i = 0; i < recordList.size(); i++) {
            TmsMaterialCate cate = recordList.get(i);
            records.add(new TmsMaterialCateTreeModel(cate));
        }
        List<TmsMaterialCateTreeModel> tree = findChildren(records, idList);
        setEmptyChildrenAsNull(tree);
        return tree;


    }

    /**
     * 获取 DepartIdModel
     * @param recordList
     * @return
     */
    public static List<MaterialCateIdModel> wrapTreeDataToDepartIdTreeList(List<TmsMaterialCate> recordList) {
        // 在该方法每请求一次,都要对全局list集合进行一次清理
        //idList.clear();
        List<MaterialCateIdModel> idList = new ArrayList<MaterialCateIdModel>();
        List<TmsMaterialCateTreeModel> records = new ArrayList<>();
        for (int i = 0; i < recordList.size(); i++) {
            TmsMaterialCate depart = recordList.get(i);
            records.add(new TmsMaterialCateTreeModel(depart));
        }
        findChildren(records, idList);
        return idList;
    }



    /**
     * queryTreeList的子方法 ====2=====
     * 该方法是找到并封装顶级父类的节点到TreeList集合
     */
    private static List<TmsMaterialCateTreeModel> findChildren(List<TmsMaterialCateTreeModel> recordList,
                                                         List<MaterialCateIdModel> departIdList) {

        List<TmsMaterialCateTreeModel> treeList = new ArrayList<>();
        for (int i = 0; i < recordList.size(); i++) {
            TmsMaterialCateTreeModel branch = recordList.get(i);
            if (oConvertUtils.isEmpty(branch.getPid())||branch.getPid().equals("0")) {
                treeList.add(branch);
                MaterialCateIdModel departIdModel = new MaterialCateIdModel().convert(branch);
                departIdList.add(departIdModel);
            }
        }
        getGrandChildren(treeList,recordList,departIdList);

        //idList = departIdList;
        return treeList;
    }

    /**
     * queryTreeList的子方法====3====
     *该方法是找到顶级父类下的所有子节点集合并封装到TreeList集合
     */
    private static void getGrandChildren(List<TmsMaterialCateTreeModel> treeList,List<TmsMaterialCateTreeModel> recordList,List<MaterialCateIdModel> idList) {

        for (int i = 0; i < treeList.size(); i++) {
            TmsMaterialCateTreeModel model = treeList.get(i);
            MaterialCateIdModel idModel = idList.get(i);
            for (int i1 = 0; i1 < recordList.size(); i1++) {
                TmsMaterialCateTreeModel m = recordList.get(i1);
                if (m.getPid()!=null && m.getPid().equals(model.getId())) {
                    model.getChildren().add(m);
                    MaterialCateIdModel dim = new MaterialCateIdModel().convert(m);
                    idModel.getChildren().add(dim);
                }
            }
            getGrandChildren(treeList.get(i).getChildren(), recordList, idList.get(i).getChildren());
        }

    }


    /**
     * queryTreeList的子方法 ====4====
     * 该方法是将子节点为空的List集合设置为Null值
     */
    private static void setEmptyChildrenAsNull(List<TmsMaterialCateTreeModel> treeList) {

        for (int i = 0; i < treeList.size(); i++) {
            TmsMaterialCateTreeModel model = treeList.get(i);
            if (model.getChildren().size() == 0) {
                model.setChildren(null);
                model.setIsLeaf(true);
            }else{
                setEmptyChildrenAsNull(model.getChildren());
                model.setIsLeaf(false);
            }
        }

    }
}
