package org.jeecg.modules.tms.materialcate.mapper;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.tms.materialcate.entity.TmsMaterialCate;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 物料分类
 * @Author: jeecg-boot
 * @Date:   2019-09-16
 * @Version: V1.0
 */
public interface TmsMaterialCateMapper extends BaseMapper<TmsMaterialCate> {

	/**
	 * 编辑节点状态
	 * @param id
	 * @param status
	 */
	void updateTreeNodeStatus(@Param("id") String id, @Param("status") String status);

}
