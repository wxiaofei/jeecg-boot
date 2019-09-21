package org.jeecg.modules.tms.materialcate.service;

import org.jeecg.modules.system.model.DepartIdModel;
import org.jeecg.modules.system.model.SysDepartTreeModel;
import org.jeecg.modules.tms.materialcate.entity.MaterialCateIdModel;
import org.jeecg.modules.tms.materialcate.entity.TmsMaterialCate;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.modules.tms.materialcate.entity.TmsMaterialCateTreeModel;

import java.util.List;

/**
 * @Description: 物料分类
 * @Author: jeecg-boot
 * @Date:   2019-09-16
 * @Version: V1.0
 */
public interface ITmsMaterialCateService extends IService<TmsMaterialCate> {

	/**根节点父ID的值*/
	public static final String ROOT_PID_VALUE = "0";
	
	/**树节点有子节点状态值*/
	public static final String HASCHILD = "1";
	
	/**树节点无子节点状态值*/
	public static final String NOCHILD = "0";

	/**新增节点*/
	void addTmsMaterialCate(TmsMaterialCate tmsMaterialCate);
	
	/**修改节点*/
	void updateTmsMaterialCate(TmsMaterialCate tmsMaterialCate) throws JeecgBootException;
	
	/**删除节点*/
	void deleteTmsMaterialCate(String id) throws JeecgBootException;



	/**
	 * 查询所有分类信息,并分节点进行显示
	 * @return
	 */
	List<TmsMaterialCateTreeModel> queryTreeList();

	/**
	 * 查询所有分类CateId信息,并分节点进行显示
	 * @return
	 */
	public List<MaterialCateIdModel> queryCateIdTreeList();

	/**
	 * 根据关键字搜索相关的部门数据
	 * @param keyWord
	 * @return
	 */
	List<TmsMaterialCateTreeModel> searhBy(String keyWord);




}
