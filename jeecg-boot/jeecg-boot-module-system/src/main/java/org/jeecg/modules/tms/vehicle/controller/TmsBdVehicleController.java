package org.jeecg.modules.tms.vehicle.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.tms.vehicle.entity.TmsBdVehicle;
import org.jeecg.modules.tms.vehicle.service.ITmsBdVehicleService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;

 /**
 * @Description: 车辆信息管理
 * @Author: jeecg-boot
 * @Date:   2019-09-10
 * @Version: V1.0
 */
@RestController
@RequestMapping("/vehicle/tmsBdVehicle")
@Slf4j
public class TmsBdVehicleController {
	@Autowired
	private ITmsBdVehicleService tmsBdVehicleService;
	
	/**
	  * 分页列表查询
	 * @param tmsBdVehicle
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<IPage<TmsBdVehicle>> queryPageList(TmsBdVehicle tmsBdVehicle,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
		Result<IPage<TmsBdVehicle>> result = new Result<IPage<TmsBdVehicle>>();
		QueryWrapper<TmsBdVehicle> queryWrapper = QueryGenerator.initQueryWrapper(tmsBdVehicle, req.getParameterMap());
		Page<TmsBdVehicle> page = new Page<TmsBdVehicle>(pageNo, pageSize);
		IPage<TmsBdVehicle> pageList = tmsBdVehicleService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	
	/**
	  *   添加
	 * @param tmsBdVehicle
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<TmsBdVehicle> add(@RequestBody TmsBdVehicle tmsBdVehicle) {
		Result<TmsBdVehicle> result = new Result<TmsBdVehicle>();
		try {
			tmsBdVehicleService.save(tmsBdVehicle);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}
	
	/**
	  *  编辑
	 * @param tmsBdVehicle
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<TmsBdVehicle> edit(@RequestBody TmsBdVehicle tmsBdVehicle) {
		Result<TmsBdVehicle> result = new Result<TmsBdVehicle>();
		TmsBdVehicle tmsBdVehicleEntity = tmsBdVehicleService.getById(tmsBdVehicle.getId());
		if(tmsBdVehicleEntity==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = tmsBdVehicleService.updateById(tmsBdVehicle);
			//TODO 返回false说明什么？
			if(ok) {
				result.success("修改成功!");
			}
		}
		
		return result;
	}
	
	/**
	  *   通过id删除
	 * @param id
	 * @return
	 */
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		try {
			tmsBdVehicleService.removeById(id);
		} catch (Exception e) {
			log.error("删除失败",e.getMessage());
			return Result.error("删除失败!");
		}
		return Result.ok("删除成功!");
	}
	
	/**
	  *  批量删除
	 * @param ids
	 * @return
	 */
	@DeleteMapping(value = "/deleteBatch")
	public Result<TmsBdVehicle> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<TmsBdVehicle> result = new Result<TmsBdVehicle>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this.tmsBdVehicleService.removeByIds(Arrays.asList(ids.split(",")));
			result.success("删除成功!");
		}
		return result;
	}
	
	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/queryById")
	public Result<TmsBdVehicle> queryById(@RequestParam(name="id",required=true) String id) {
		Result<TmsBdVehicle> result = new Result<TmsBdVehicle>();
		TmsBdVehicle tmsBdVehicle = tmsBdVehicleService.getById(id);
		if(tmsBdVehicle==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(tmsBdVehicle);
			result.setSuccess(true);
		}
		return result;
	}

  /**
      * 导出excel
   *
   * @param request
   * @param response
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, TmsBdVehicle tmsBdVehicle) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<TmsBdVehicle> queryWrapper = QueryGenerator.initQueryWrapper(tmsBdVehicle, request.getParameterMap());
      List<TmsBdVehicle> pageList = tmsBdVehicleService.list(queryWrapper);
      // Step.2 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      // 过滤选中数据
      String selections = request.getParameter("selections");
      if(oConvertUtils.isEmpty(selections)) {
    	  mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
      }else {
    	  List<String> selectionList = Arrays.asList(selections.split(","));
    	  List<TmsBdVehicle> exportList = pageList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
    	  mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
      }
      //导出文件名称
      mv.addObject(NormalExcelConstants.FILE_NAME, "车辆信息管理列表");
      mv.addObject(NormalExcelConstants.CLASS, TmsBdVehicle.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("车辆信息管理列表数据", "导出人:Jeecg", "导出信息"));
      return mv;
  }

  /**
      * 通过excel导入数据
   *
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
  public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
      Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
      for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
          MultipartFile file = entity.getValue();// 获取上传文件对象
          ImportParams params = new ImportParams();
          params.setTitleRows(2);
          params.setHeadRows(1);
          params.setNeedSave(true);
          try {
              List<TmsBdVehicle> listTmsBdVehicles = ExcelImportUtil.importExcel(file.getInputStream(), TmsBdVehicle.class, params);
              tmsBdVehicleService.saveBatch(listTmsBdVehicles);
              return Result.ok("文件导入成功！数据行数:" + listTmsBdVehicles.size());
          } catch (Exception e) {
              log.error(e.getMessage(),e);
              return Result.error("文件导入失败:"+e.getMessage());
          } finally {
              try {
                  file.getInputStream().close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }
      return Result.ok("文件导入失败！");
  }

}
