package org.jeecg.modules.tms.driver.entity;

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
 * @Description: 司机信息管理
 * @Author: jeecg-boot
 * @Date:   2019-09-09
 * @Version: V1.0
 */
@Data
@TableName("tms_bd_driver")
public class TmsBdDriver implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
	private java.lang.String id;
	/**司机名*/
	@Excel(name = "司机名", width = 15)
	private java.lang.String name;
	/**性别*/
	@Excel(name = "性别", width = 15)
	private java.lang.String sex;
	/**身份证号码*/
	@Excel(name = "身份证号码", width = 15)
	private java.lang.String idcard;
	/**驾驶证号码*/
	@Excel(name = "驾驶证号码", width = 15)
	private java.lang.String drivercard;
	/**准驾类型*/
	@Excel(name = "准驾类型", width = 15)
	private java.lang.String driverType;
	/**状态*/
	@Excel(name = "状态", width = 15)
	private java.lang.String relation;
	/**电话*/
	@Excel(name = "电话", width = 15)
	private java.lang.String telphone;
	/**家庭地址*/
	@Excel(name = "家庭地址", width = 15)
	private java.lang.String address;
	/**创建人*/
	@Excel(name = "创建人", width = 15)
	private java.lang.String createBy;
	/**创建时间*/
	@Excel(name = "创建时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	private java.util.Date createTime;
	/**修改人*/
	@Excel(name = "修改人", width = 15)
	private java.lang.String updateBy;
	/**修改时间*/
	@Excel(name = "修改时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	private java.util.Date updateTime;
}
