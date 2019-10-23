package org.jeecg.modules.tms.queue.entity;

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
 * @Description: 车辆排队
 * @Author: jeecg-boot
 * @Date:   2019-10-13
 * @Version: V1.0
 */
@Data
@TableName("tms_vehicle_queue")
public class TmsVehicleQueue implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
	private java.lang.String id;
	/**创建人*/
	@Excel(name = "创建人", width = 15)
	private java.lang.String createBy;
	/**创建日期*/
	@Excel(name = "创建日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date createTime;
	/**更新人*/
	@Excel(name = "更新人", width = 15)
	private java.lang.String updateBy;
	/**更新日期*/
	@Excel(name = "更新日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date updateTime;
	/**所属部门*/
	@Excel(name = "所属部门", width = 15)
	private java.lang.String sysOrgCode;
	/**车牌号*/
	@Excel(name = "车牌号", width = 15)
	private java.lang.String vehicleCode;
	/**订单号*/
	@Excel(name = "订单号", width = 15)
	private java.lang.String orderCode;
	/**申报时间*/
	@Excel(name = "申报时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	private java.util.Date applyDate;
	/**入厂时间*/
	@Excel(name = "入厂时间", width = 15)
	private java.lang.String inDate;
	/**出厂时间*/
	@Excel(name = "出厂时间", width = 15)
	private java.lang.String outDate;
	/**司机姓名*/
	@Excel(name = "司机姓名", width = 15)
	private java.lang.String driverName;
	/**司机电话*/
	@Excel(name = "司机电话", width = 15)
	private java.lang.String driverPhone;
	/**车辆状态*/
	@Excel(name = "车辆状态", width = 15)
	private java.lang.String vehicleStatus;
}
