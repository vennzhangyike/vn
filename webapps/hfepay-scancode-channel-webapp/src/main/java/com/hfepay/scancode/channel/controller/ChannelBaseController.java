/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */
package com.hfepay.scancode.channel.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.hfepay.common.web.controller.BaseController;
import com.hfepay.commons.base.collection.Maps;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.utils.Pager;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.channel.commons.Constants;
import com.hfepay.scancode.commons.condition.ChannelAdminCondition;
import com.hfepay.scancode.commons.condition.ChannelBaseCondition;
import com.hfepay.scancode.commons.condition.ChannelRoleCondition;
import com.hfepay.scancode.commons.condition.NodeRelationCondition;
import com.hfepay.scancode.commons.condition.PlatformQrcodeCondition;
import com.hfepay.scancode.commons.condition.QrcodeAssignedLogCondition;
import com.hfepay.scancode.commons.entity.Admin;
import com.hfepay.scancode.commons.entity.ChannelAdmin;
import com.hfepay.scancode.commons.entity.ChannelBase;
import com.hfepay.scancode.commons.entity.ChannelExpand;
import com.hfepay.scancode.commons.entity.ChannelRole;
import com.hfepay.scancode.service.channel.ChannelAdminService;
import com.hfepay.scancode.service.channel.ChannelRoleService;
import com.hfepay.scancode.service.operator.ChannelBaseService;
import com.hfepay.scancode.service.operator.ChannelExpandService;
import com.hfepay.scancode.service.operator.NodeRelationService;
import com.hfepay.scancode.service.operator.ParamsInfoService;
import com.hfepay.scancode.service.operator.PlatformQrcodeService;
import com.hfepay.scancode.service.operator.QrcodeAssignedLogService;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

@Controller
@RequestMapping("/adminManage/channelbase")
public class ChannelBaseController extends BaseController{
	
	@Autowired
	private ChannelBaseService channelBaseService;
	@Autowired
	private ChannelExpandService channelExpandService;
	@Autowired
	private PlatformQrcodeService platformQrcodeService;
	@Autowired
	private QrcodeAssignedLogService qrcodeAssignedLogService;
	@Autowired
	private ChannelAdminService channelAdminService;
	@Autowired
	private ChannelRoleService channelRoleService;
	@Autowired
	private NodeRelationService nodeRelationService;
	@Autowired
	private ParamsInfoService paramsInfoService;
	
	private static String qrFileType = ".png";//二维码文件类型
	
	/**
	 * @Title: saveOrUpdateById
	 * @Description: 查询列表
	 * @author: Ricky
	 * @param 
	 * @return: String
	 * @date CreateDate : 2016-10-13 09:50:44
	 */
	@RequestMapping(method= {RequestMethod.GET,RequestMethod.POST})
	public String list() {
		return "admin/channel/channelbase/list";
	}
	
	/**
	 * @Title: listContent
	 * @Description: 列表查询
	 * @author: Ricky
	 * @param ChannelBaseCondition
	 * @return: String
	 * @date CreateDate : 2016-10-13 09:50:44
	 */
	@RequestMapping(value = "/content", method= {RequestMethod.POST})
	public String listContent(HttpServletRequest request,ModelMap model,ChannelBaseCondition channelBaseCondition){
		ChannelAdmin user = (ChannelAdmin) request.getSession().getAttribute("currentUser");
		channelBaseCondition.setChannelNo(user.getChannelCode());
		PagingResult<ChannelBase> page = channelBaseService.findPagingResult(channelBaseCondition);
		Pager<ChannelBase> pager = new Pager<ChannelBase>();
		pager.setPageNo(channelBaseCondition.getPageNo());
		pager.setPageSize(channelBaseCondition.getPageSize());
		pager.setResult(page.getResult());
		pager.setTotalCount(page.getTotalCount());
 		if(pager.getOrder() == null){
			pager.setOrder("");
		}
		model.addAttribute("pager",pager);
		return "admin/channel/channelbase/listContent";
	}
	

	/**
	 * @Title: list
	 * @Description: 列表查询，无分页
	 * @author: Ricky
	 * @param ChannelBaseCondition
	 * @return: JSONObject
	 * @date CreateDate : 2016-10-13 09:50:44
	 */
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject list(HttpServletRequest request,ChannelBaseCondition channelBaseCondition){	
		ChannelAdmin user = (ChannelAdmin) request.getSession().getAttribute("currentUser");
		channelBaseCondition.setChannelNo(user.getChannelCode());
        List<ChannelBase> list = channelBaseService.findAll(channelBaseCondition);
        JSONObject json = new JSONObject();
        json.put("objList", list);
		return json;
	}
	
	/**
	 * @Title: saveOrUpdateById
	 * @Description: 删除
	 * @author: Ricky
	 * @param ChannelBaseCondition
	 * @return: JSONObject
	 * @date CreateDate : 2016-10-13 09:50:44
	 */
	@RequestMapping(value="/del",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject del(HttpServletRequest request,ChannelBaseCondition channelBaseCondition){	
        long flag = channelBaseService.deleteById(channelBaseCondition.getId());
        JSONObject json = new JSONObject();
        if(flag == 0){
        	json.put("message", Constants.FAIL_MSG);
        	json.put("status", Constants.FAIL_STATE);
        }else{
        	json.put("message", Constants.SUCCESS_MSG);
        	json.put("status", Constants.SUCCESS_STATE);
        }
		return json;
	}
	


	/**
	 * @Title: saveOrUpdateById
	 * @Description: 状态变更
	 * @author: wh
	 * @param ChannelBaseCondition
	 * @return: JSONObject
	 * @date CreateDate : 2016-10-13 09:50:44
	 */
	@RequestMapping(value = "/updateStatus", method= {RequestMethod.POST})
	@ResponseBody
	public JSONObject updateStatus(HttpServletRequest request,ChannelBaseCondition channelBaseCondition){		
        long flag = channelBaseService.updateStatus(channelBaseCondition.getId(),channelBaseCondition.getStatus());
        ChannelBase entity = channelBaseService.findById(channelBaseCondition.getId());
        if(entity != null){
        	ChannelExpand channelExpand = channelExpandService.findByChannelNo(entity.getChannelNo());
        	if(channelExpand != null){
        		flag = channelExpandService.updateStatus(channelExpand.getId(),channelBaseCondition.getStatus());
        	}
        	
        }
        JSONObject json = new JSONObject();
        if(flag == 0){
        	json.put("message", Constants.FAIL_MSG);
        	json.put("status", Constants.FAIL_STATE);
        }else{
        	json.put("message", Constants.SUCCESS_MSG);
        	json.put("status", Constants.SUCCESS_STATE);
        }
		return json;
	}	
	
	/**
	 * @Title: saveOrUpdateById
	 * @Description: addQrcode
	 * @author: wh
	 * @param ChannelBaseCondition
	 * @return: JSONObject
	 * @date CreateDate : 2016-10-13 09:50:44
	 */
	@RequestMapping(value = "/addQrcode", method= {RequestMethod.POST})
	@ResponseBody
	public JSONObject addQrcode(HttpServletRequest request,String channelNo ,String qrcodeNum){	
		Admin user = (Admin) request.getSession().getAttribute("currentScanAdminUser");
		JSONObject json = new JSONObject();
        try {
			ChannelExpand channelExpand = channelExpandService.findByChannelNo(channelNo);
//			String imgUrl = fileUploadConfig.getFilePath() + Constants.SPT + fileUploadConfig.getViralPath();//二维码访问路径
//			String path = fileUploadConfig.getPath() + Constants.SPT + fileUploadConfig.getQrPath();//二维码存放路径
			int qrNumber = Integer.parseInt(qrcodeNum);
			if(channelExpand != null){
				for(int i=0;i<qrNumber;i++ ){
					String qrCode = platformQrcodeService.getQrcodeCode();
					String qrName = qrCode + qrFileType ;//二维码名称
					StringBuffer content = new StringBuffer();//二维码扫码地址+参数(渠道编码+二维码编码)
					
					net.sf.json.JSONObject paramsInfoJson = paramsInfoService.getParamsInfoByDomain(channelNo);
					content.append(paramsInfoJson.getString("qrcodePath"));
//					content.append(fileUploadConfig.getQrcodePath());
					content.append("?channelNo=");
					content.append(channelExpand.getChannelNo());
					content.append("&qrCode=");
					content.append(qrCode);
//					String icon = channelExpand.getIcon();
//					if (Strings.isEmpty(icon)) {
//						icon = "";
//					}else {
//						icon = icon.substring(icon.indexOf(fileUploadConfig.getPath()));
//					}
//					channelBaseService.encode(content.toString(),icon , path, true,qrName);//生成二维码
					
					PlatformQrcodeCondition platformQrcodeCondition = new PlatformQrcodeCondition();
					platformQrcodeCondition.setId(Strings.getUUID());
					platformQrcodeCondition.setChannelNo(channelExpand.getChannelNo());
					platformQrcodeCondition.setQrName(qrName);
					platformQrcodeCondition.setQrCode(qrCode);
					platformQrcodeCondition.setImage(content.toString());
					platformQrcodeCondition.setQrDesc("");
					platformQrcodeCondition.setUseStatus(Constants.STATUS_NOT_USE);
					platformQrcodeCondition.setQrStatus(Constants.STATUS_ACTIVE);
					platformQrcodeCondition.setRecordStatus(Constants.Y);
					platformQrcodeCondition.setCreateTime(new Date());
					platformQrcodeCondition.setOperator(user.getId());
					platformQrcodeService.insert(platformQrcodeCondition);//保存二维码
				}
				ChannelBaseCondition channelBaseCondition = new ChannelBaseCondition();
				ChannelBase channelBase = channelBaseService.findByChannelNo(channelNo);
				BeanUtils.copyProperties(channelBase, channelBaseCondition);
				int qrTotal = qrNumber + channelBase.getQrTotal();
				int lessTotal = qrNumber + channelBase.getLessTotal();
				channelBaseCondition.setQrTotal(qrTotal);
				channelBaseCondition.setLessTotal(lessTotal);
				channelBaseService.update(channelBaseCondition);//更新二维码数量
				QrcodeAssignedLogCondition qrcodeAssignedLogCondition = new QrcodeAssignedLogCondition();
				qrcodeAssignedLogCondition.setId(Strings.getUUID());
				qrcodeAssignedLogCondition.setAssignment(Constants.OPERATOR_SYSTEM);
				qrcodeAssignedLogCondition.setRecipients(channelExpand.getChannelNo());				
				qrcodeAssignedLogCondition.setQrNumber(qrNumber);
				qrcodeAssignedLogCondition.setAssignedType(Constants.ASSIGNEDTYPE_ACTIVE);
				qrcodeAssignedLogCondition.setCreateTime(new Date());
				qrcodeAssignedLogCondition.setOperator(user.getId());
				qrcodeAssignedLogService.insert(qrcodeAssignedLogCondition);//二维码流水日志
			}
			
		} catch (Exception e) {
			json.put("message", Constants.FAIL_MSG);
        	json.put("status", Constants.FAIL_STATE);
		}
        json.put("message", Constants.SUCCESS_MSG);
    	json.put("status", Constants.SUCCESS_STATE);
		return json;
	}
	
	/**
	 * @Title: saveChannelAdmin
	 * @Description: 新增/更新渠道账户
	 * @param ChannelExpandCondition
	 * @return: JSON
	 * @date CreateDate : 2016-10-13 13:44:26
	 */
	@RequestMapping(value="/savechanneladmin", method= {RequestMethod.POST})
	@ResponseBody
	public JSON saveChannelAdmin(HttpServletRequest request,ModelMap model,ChannelAdminCondition channelAdminCondition,String roleId,String channelCode) throws Exception {
		Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			channelAdminCondition.setIdentityFlag(Constants.IDENTITYFLAG_CHANNEL);
			channelAdminCondition.setIdentityNo(channelAdminCondition.getChannelCode());
			channelAdminService.updateFix(channelAdminCondition, roleId, channelAdminCondition.getChannelCode());
			if(Strings.isEmpty(channelAdminCondition.getId())){
				//保存渠道，存放一条关联信息到节点表。只有一条
				NodeRelationCondition dCondition = new NodeRelationCondition();
				dCondition.setIdentityFlag(Constants.IDENTITYFLAG_CHANNEL);//1渠道2代理商3商户：必需参数
				dCondition.setId(Strings.getUUID());
				dCondition.setParentNode("1");
				dCondition.setCurrentNode(channelCode);//当前节点id//必须参数
				dCondition.setCurrentNodeLevel("0");//当前节点级别：渠道是0级别
				dCondition.setOperator(Constants.OPERATOR_SYSTEM);//操作人
				
				nodeRelationService.doSaveNodeRelations(dCondition);
			}
			
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG,"url","/adminManage/channelbase");
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
		}
		return JSONSerializer.toJSON(map);
	}
	
	/**
	 * @Title: channeladmin
	 * @Description: 查询单个渠道用户账户
	 * @param id
	 * @return: String
	 * @date CreateDate : 2016-10-13 13:44:26
	 */
	@RequestMapping(value="/channeladmin/{channelNo}", method= {RequestMethod.GET})
	public String channeladmin(ModelMap model,@PathVariable String channelNo) throws Exception {
		ChannelAdmin entity = channelAdminService.findByChannelNo(channelNo);
		model.addAttribute("Obj", entity);
		ChannelRoleCondition channelRoleCondition = new ChannelRoleCondition();
		channelRoleCondition.setChannelCode(channelNo);
		List<ChannelRole> channelRoles = channelRoleService.findAllNoPage(channelRoleCondition);
		model.addAttribute("roles", channelRoles);
		model.addAttribute("channelCode", channelNo);
		return "admin/channel/channeladmin/edit";
	}
}

