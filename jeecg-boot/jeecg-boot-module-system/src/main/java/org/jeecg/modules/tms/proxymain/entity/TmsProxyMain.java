package org.jeecg.modules.tms.proxymain.entity;

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
 * @Description: 委托书管理
 * @Author: jeecg-boot
 * @Date:   2019-09-23
 * @Version: V1.0
 */
@Data
@TableName("tms_proxy_main")
public class TmsProxyMain implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
	private String id;
	/**流程状态*/
	@Excel(name = "流程状态", width = 15)
	private String bpmStatus;
	/**创建时间*/
	@Excel(name = "创建时间", width = 15)
	private String createTime;
	/**创建人登录名称*/
	@Excel(name = "创建人登录名称", width = 15)
	private String createBy;
	/**更新人名称*/
	@Excel(name = "更新人名称", width = 15)
	private String updateBy;
	/**修改时间*/
	@Excel(name = "修改时间", width = 15)
	private String updateTime;
	/**所属公司*/
	@Excel(name = "所属公司", width = 15)
	private String org;
	/**委托书编码*/
	@Excel(name = "委托书编码", width = 15)
	private String vbillcode;
	/**供应方*/
	@Excel(name = "供应方", width = 15)
	private String business;
	/**采购方*/
	@Excel(name = "采购方", width = 15)
	private String purchase;
	/**发运方式*/
	@Excel(name = "发运方式", width = 15)
	private String delivery;
	/**运输方式*/
	@Excel(name = "运输方式", width = 15)
	private String transport;
	/**司机*/
	@Excel(name = "司机", width = 15)
	private String driverid;
	/**车辆*/
	@Excel(name = "车辆", width = 15)
	private String vehicleid;
	/**物料号*/
	@Excel(name = "物料号", width = 15)
	private String materialno;
	/**委托数量*/
	@Excel(name = "委托数量", width = 15)
	private BigDecimal proxyNum;
	/**订单号*/
	@Excel(name = "订单号", width = 15)
	private String orderno;
	/**复核人*/
	@Excel(name = "复核人", width = 15)
	private String auditBy;
	/**复核时间*/
	@Excel(name = "复核时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	private Date auditDate;
	/**确认人*/
	@Excel(name = "确认人", width = 15)
	private String confirmBy;
	/**确认时间*/
	@Excel(name = "确认时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	private Date confirmDate;
	/**关闭人*/
	@Excel(name = "关闭人", width = 15)
	private String closeBy;
	/**关闭时间*/
	@Excel(name = "关闭时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	private Date closehDate;
	/**作废人*/
	@Excel(name = "作废人", width = 15)
	private String abolishBy;
	/**作废时间*/
	@Excel(name = "作废时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	private Date abolishDate;
	/**退回人*/
	@Excel(name = "退回人", width = 15)
	private String untreadBy;
	/**退回时间*/
	@Excel(name = "退回时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	private Date untreadDate;
	/**退回原因*/
	@Excel(name = "退回原因", width = 15)
	private String untreadMsg;
	/**关闭原因*/
	@Excel(name = "关闭原因", width = 15)
	private String closeMsg;
}
