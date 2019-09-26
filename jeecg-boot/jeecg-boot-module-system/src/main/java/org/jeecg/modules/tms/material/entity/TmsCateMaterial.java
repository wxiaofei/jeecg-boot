package org.jeecg.modules.tms.material.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("tms_cate_material")
public class TmsCateMaterial implements Serializable {
	private static final long serialVersionUID = 1L;

	/**主键id*/
    @TableId(type = IdType.UUID)
	private String id;
	/**用户id*/
	private String cateId;
	/**部门id*/
	private String materialId;
	public TmsCateMaterial(String id, String cateId, String materialId) {
		super();
		this.id = id;
		this.cateId = cateId;
		this.materialId = materialId;
	}

	public TmsCateMaterial(String id, String cateId) {
		this.materialId = id;
		this.cateId = cateId;
	}
}
