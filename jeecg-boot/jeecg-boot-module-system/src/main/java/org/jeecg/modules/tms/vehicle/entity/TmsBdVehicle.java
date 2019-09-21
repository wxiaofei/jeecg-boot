package org.jeecg.modules.tms.vehicle.entity;

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
 * @Description: 车辆信息管理
 * @Author: jeecg-boot
 * @Date:   2019-09-10
 * @Version: V1.0
 */
@Data
@TableName("tms_bd_vehicle")
public class TmsBdVehicle implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
	private String id;
	/**车牌号*/
	@Excel(name = "车牌号", width = 15)
	private String license;
	/**状态*/
	@Excel(name = "状态", width = 15)
	private String relation;
	/**品牌*/
	@Excel(name = "品牌", width = 15)
	private String brand;
	/**型号*/
	@Excel(name = "型号", width = 15)
	private String type;
	/**车长(米)*/
	@Excel(name = "车长(米)", width = 15)
	private BigDecimal length;
	/**车高(米)*/
	@Excel(name = "车高(米)", width = 15)
	private BigDecimal height;
	/**车宽(米)*/
	@Excel(name = "车宽(米)", width = 15)
	private BigDecimal width;
	/**核定载重(吨)*/
	@Excel(name = "核定载重(吨)", width = 15)
	private BigDecimal capacity;
	/**车辆注册年份*/
	@Excel(name = "车辆注册年份", width = 15)
	private String registration;
	/**行驶证有效期*/
	@Excel(name = "行驶证有效期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	private Date validity;
	/**创建人*/
	@Excel(name = "创建人", width = 15)
	private String createBy;
	/**创建时间*/
	@Excel(name = "创建时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	private Date createTime;
	/**修改人*/
	@Excel(name = "修改人", width = 15)
	private String updateBy;
	/**修改时间*/
	@Excel(name = "修改时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	private Date updateTime;
}
