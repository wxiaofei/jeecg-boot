package org.jeecg.modules.tms.materialcate.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.system.entity.SysDepart;
import org.jeecg.modules.system.model.DepartIdModel;
import org.jeecg.modules.system.model.SysDepartTreeModel;
import org.jeecg.modules.system.util.FindsDepartsChildrenUtil;
import org.jeecg.modules.system.util.FindsTreeChildrenUtil;
import org.jeecg.modules.tms.materialcate.entity.MaterialCateIdModel;
import org.jeecg.modules.tms.materialcate.entity.TmsMaterialCate;
import org.jeecg.modules.tms.materialcate.entity.TmsMaterialCateTreeModel;
import org.jeecg.modules.tms.materialcate.mapper.TmsMaterialCateMapper;
import org.jeecg.modules.tms.materialcate.service.ITmsMaterialCateService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 物料分类
 * @Author: jeecg-boot
 * @Date:   2019-09-16
 * @Version: V1.0
 */
@Service
public class TmsMaterialCateServiceImpl extends ServiceImpl<TmsMaterialCateMapper, TmsMaterialCate> implements ITmsMaterialCateService {

	@Override
	public void addTmsMaterialCate(TmsMaterialCate tmsMaterialCate) {
		if(oConvertUtils.isEmpty(tmsMaterialCate.getPid())){
			tmsMaterialCate.setPid(ITmsMaterialCateService.ROOT_PID_VALUE);
		}else{
			//如果当前节点父ID不为空 则设置父节点的hasChildren 为1
			TmsMaterialCate parent = baseMapper.selectById(tmsMaterialCate.getPid());
			if(parent!=null && !"1".equals(parent.getHasChild())){
				parent.setHasChild("1");
				baseMapper.updateById(parent);
			}
		}
		baseMapper.insert(tmsMaterialCate);
	}
	
	@Override
	public void updateTmsMaterialCate(TmsMaterialCate tmsMaterialCate) {
		TmsMaterialCate entity = this.getById(tmsMaterialCate.getId());
		if(entity==null) {
			throw new JeecgBootException("未找到对应实体");
		}
		String old_pid = entity.getPid();
		String new_pid = tmsMaterialCate.getPid();
		if(!old_pid.equals(new_pid)) {
			updateOldParentNode(old_pid);
			if(oConvertUtils.isEmpty(new_pid)){
				tmsMaterialCate.setPid(ITmsMaterialCateService.ROOT_PID_VALUE);
			}
			if(!ITmsMaterialCateService.ROOT_PID_VALUE.equals(tmsMaterialCate.getPid())) {
				baseMapper.updateTreeNodeStatus(tmsMaterialCate.getPid(), ITmsMaterialCateService.HASCHILD);
			}
		}
		baseMapper.updateById(tmsMaterialCate);
	}
	
	@Override
	public void deleteTmsMaterialCate(String id) throws JeecgBootException {
		TmsMaterialCate tmsMaterialCate = this.getById(id);
		if(tmsMaterialCate==null) {
			throw new JeecgBootException("未找到对应实体");
		}
		updateOldParentNode(tmsMaterialCate.getPid());
		baseMapper.deleteById(id);
	}

	@Override
	public List<TmsMaterialCateTreeModel> queryTreeList() {
		LambdaQueryWrapper<TmsMaterialCate> query = new LambdaQueryWrapper<TmsMaterialCate>();

		List<TmsMaterialCate> list = this.list(query);
		// 调用wrapTreeDataToTreeList方法生成树状数据
		List<TmsMaterialCateTreeModel> listResult = FindsTreeChildrenUtil.wrapTreeDataToTreeList(list);
		return listResult;

	}

	@Override
	public List<MaterialCateIdModel> queryCateIdTreeList() {
		LambdaQueryWrapper<TmsMaterialCate> query = new LambdaQueryWrapper<TmsMaterialCate>();

		List<TmsMaterialCate> list = this.list(query);
		// 调用wrapTreeDataToTreeList方法生成树状数据
		List<MaterialCateIdModel> listResult = FindsTreeChildrenUtil.wrapTreeDataToDepartIdTreeList(list);
		return listResult;
	}

	@Override
	public List<TmsMaterialCateTreeModel> searhBy(String keyWord) {
		LambdaQueryWrapper<TmsMaterialCate> query = new LambdaQueryWrapper<TmsMaterialCate>();
		query.like(TmsMaterialCate::getCateName, keyWord);
		//update-begin--Author:huangzhilin  Date:20140417 for：[bugfree号]组织机构搜索回显优化--------------------
		TmsMaterialCateTreeModel model = new TmsMaterialCateTreeModel();
		List<TmsMaterialCate> cateList = this.list(query);
		List<TmsMaterialCateTreeModel> newList = new ArrayList<>();
		if(cateList.size() > 0) {
			for(TmsMaterialCate cate : cateList) {
				model = new TmsMaterialCateTreeModel(cate);
				model.setChildren(null);
				//update-end--Author:huangzhilin  Date:20140417 for：[bugfree号]组织机构搜索功回显优化----------------------
				newList.add(model);
			}
			return newList;
		}
		return null;
	}


	/**
	 * 根据所传pid查询旧的父级节点的子节点并修改相应状态值
	 * @param pid
	 */
	private void updateOldParentNode(String pid) {
		if(!ITmsMaterialCateService.ROOT_PID_VALUE.equals(pid)) {
			Integer count = baseMapper.selectCount(new QueryWrapper<TmsMaterialCate>().eq("pid", pid));
			if(count==null || count<=1) {
				baseMapper.updateTreeNodeStatus(pid, ITmsMaterialCateService.NOCHILD);
			}
		}
	}

}
