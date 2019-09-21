package org.jeecg.modules.tms.material.entity;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;

/**
 * @Description: 商品管理
 * @Author: jeecg-boot
 * @Date:   2019-09-10
 * @Version: V1.0
 */
@Data
@TableName("tms_bd_material")
public class TmsBdMaterial implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
	private String id;
	/**创建人*/
	@Excel(name = "创建人", width = 15)
	private String createBy;
	/**创建日期*/
	@Excel(name = "创建日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	/**更新人*/
	@Excel(name = "更新人", width = 15)
	private String updateBy;
	/**更新日期*/
	@Excel(name = "更新日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date updateTime;
	/**物料编码*/
	@Excel(name = "物料编码", width = 15)
	private String code;
	/**物料名称*/
	@Excel(name = "物料名称", width = 15)
	private String name;
	/**助记码*/
	@Excel(name = "助记码", width = 15)
	private String materialmnecode;
	/**规格*/
	@Excel(name = "规格", width = 15)
	private String materialspec;
	/**型号*/
	@Excel(name = "型号", width = 15)
	private String materialtype;
	/**备注*/
	@Excel(name = "备注", width = 15)
	private String memo;
	/**nc物料id*/
	@Excel(name = "nc物料id", width = 15)
	private String pkMaterial;
}
