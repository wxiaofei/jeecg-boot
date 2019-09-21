package org.jeecg.modules.demo.hopefull.entity;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Description: 司机信息
 * @Author: jeecg-boot
 * @Date:   2019-08-15
 * @Version: V1.0
 */
@Data
@TableName("tms_bd_driver")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="tms_bd_driver对象", description="司机信息")
public class Driver {
    
	/**主键*/
	@TableId(type = IdType.UUID)
    @ApiModelProperty(value = "主键")
	private String id;
	/**司机名*/
	@Excel(name = "司机名", width = 15)
    @ApiModelProperty(value = "司机名")
	private String name;
	/**性别*/
	@Excel(name = "性别", width = 15)
    @ApiModelProperty(value = "性别")
	private String sex;
	/**身份证号码*/
	@Excel(name = "身份证号码", width = 15)
    @ApiModelProperty(value = "身份证号码")
	private String idcard;
	/**身份证扫描件*/
	@Excel(name = "身份证扫描件", width = 15)
    @ApiModelProperty(value = "身份证扫描件")
	private String idcardPic;
	/**驾驶证号码*/
	@Excel(name = "驾驶证号码", width = 15)
    @ApiModelProperty(value = "驾驶证号码")
	private String drivercard;
	/**驾驶证扫描件*/
	@Excel(name = "驾驶证扫描件", width = 15)
    @ApiModelProperty(value = "驾驶证扫描件")
	private String drivercardPic;
	/**准驾类型*/
	@Excel(name = "准驾类型", width = 15)
    @ApiModelProperty(value = "准驾类型")
	private String driverType;
	/**状态(1：内部  2：外部)*/
	@Excel(name = "状态(1：内部  2：外部)", width = 15)
    @ApiModelProperty(value = "状态(1：内部  2：外部)")
	private Integer relation;
	/**电话*/
	@Excel(name = "电话", width = 15)
    @ApiModelProperty(value = "电话")
	private String telphone;
	/**家庭地址*/
	@Excel(name = "家庭地址", width = 15)
    @ApiModelProperty(value = "家庭地址")
	private String address;
	/**创建人*/
	@Excel(name = "创建人", width = 15)
    @ApiModelProperty(value = "创建人")
	private String createBy;
	/**创建时间*/
	@Excel(name = "创建时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
	private Date createTime;
	/**修改人*/
	@Excel(name = "修改人", width = 15)
    @ApiModelProperty(value = "修改人")
	private String updateBy;
	/**修改时间*/
	@Excel(name = "修改时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "修改时间")
	private Date updateTime;
}
