package org.jeecg.modules.tms.supplier.entity;

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
 * @Description: 客商管理
 * @Author: jeecg-boot
 * @Date:   2019-09-23
 * @Version: V1.0
 */
@Data
@TableName("tms_bd_supplier")
public class TmsBdSupplier implements Serializable {
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
	/**客商编码*/
	@Excel(name = "客商编码", width = 15)
	private String code;
	/**客商名称*/
	@Excel(name = "客商名称", width = 15)
	private String name;
	/**客商简称*/
	@Excel(name = "客商简称", width = 15)
	private String shortname;
	/**客户启用状态*/
	@Excel(name = "客户启用状态", width = 15)
	private String custenablestate;
	/**客商属性*/
	@Excel(name = "客商属性", width = 15)
	private String custsupprop;
	/**客商类型*/
	@Excel(name = "客商类型", width = 15)
	private String custsuptype;
	/**助记码*/
	@Excel(name = "助记码", width = 15)
	private String mnecode;
	/**所属组织*/
	@Excel(name = "所属组织", width = 15)
	private String org;
	/**纳税人登记号*/
	@Excel(name = "纳税人登记号", width = 15)
	private String taxpayerid;
}
