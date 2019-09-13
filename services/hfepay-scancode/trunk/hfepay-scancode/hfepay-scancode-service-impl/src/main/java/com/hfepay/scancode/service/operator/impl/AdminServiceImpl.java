package com.hfepay.scancode.service.operator.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.criteria.Cnd;
import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.criteria.CriteriaBuilder;
import com.hfepay.commons.criteria.Order;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.SysAdminCondition;
import com.hfepay.scancode.commons.dao.AdminDAO;
import com.hfepay.scancode.commons.entity.Admin;
import com.hfepay.scancode.commons.entity.SysRoleUser;
import com.hfepay.scancode.service.commons.ScanCodeConstants;
import com.hfepay.scancode.service.operator.AdminService;
import com.hfepay.scancode.service.operator.SysRoleUserService;
import com.hfepay.scancode.service.utils.PasswordHelper;

/**
 * 账号管理类
* @author ssd
* @date 2015-4-30 下午4:21:17
 */
@Service
public class AdminServiceImpl implements AdminService{
	public static final Logger logger = LoggerFactory.getLogger(MerchantQrcodeServiceImpl.class);

	@Autowired
    private AdminDAO AdminDAO;
	
	@Autowired
	private SysRoleUserService roleUserService;
	
    /**
     * 根据用户名查找用户
     * @param username
     * @return
     * @throws Exception 
     */
    public Admin findByUsername(String username) {
		try {
			CriteriaBuilder cb = Cnd.builder(Admin.class);
			cb.andEQ("userName", username);
			Criteria buildCriteria = cb.buildCriteria();
			Admin info = AdminDAO.findOneByCriteria(buildCriteria);
			//Admin info = adminMapper.findByUsername(username);
			return info;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("findByUsername error :"+e);
		}
		return null;
    }
    
    
    
    /**
	 * 列表(分页)
	 * @param SysAdminCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2016-06-02 15:35:32
	 */
    @Override
	public PagingResult<Admin> findPagingResult(SysAdminCondition SysAdminCondition){
		CriteriaBuilder cb = Cnd.builder(Admin.class);
		if(!Strings.isEmpty(SysAdminCondition.getId())){
			cb.andEQ("id", SysAdminCondition.getId());
		}
		if(!Strings.isEmpty(SysAdminCondition.getUserName())){
			cb.andLike("userName", SysAdminCondition.getUserName());
		}
		if(!Strings.isEmpty(SysAdminCondition.getPassword())){
			cb.andEQ("password", SysAdminCondition.getPassword());
		}
		if(!Strings.isEmpty(SysAdminCondition.getSalt())){
			cb.andEQ("salt", SysAdminCondition.getSalt());
		}
		if(!Strings.isEmpty(SysAdminCondition.getEmail())){
			cb.andEQ("email", SysAdminCondition.getEmail());
		}
		if(!Strings.isEmpty(SysAdminCondition.getPhone())){
			cb.andLike("phone", SysAdminCondition.getPhone());
		}
		if(!Strings.isEmpty(SysAdminCondition.getShortName())){
			cb.andLike("shortName", SysAdminCondition.getShortName());
		}
		if(null != SysAdminCondition.getStatus()){
			cb.andEQ("status", SysAdminCondition.getStatus());
		}
		if(null != SysAdminCondition.getCreateTime()){
			cb.andEQ("createTime", SysAdminCondition.getCreateTime());
		}
		cb.orderBy("createTime", Order.DESC);
		//排序
		if(!Strings.isEmpty(SysAdminCondition.getOrderBy())){
			if(SysAdminCondition.getOrderBy().indexOf(",")>0){
				
				String[] orderBys = SysAdminCondition.getOrderBy().split(",");
				String[] orders = SysAdminCondition.getOrder().split(",");
				for(int i=0; i<orderBys.length && i<orders.length; i++){
					cb.orderBy(orderBys[i], Order.valueOf(orders[i]));
				}
			}else{
				cb.orderBy(SysAdminCondition.getOrderBy(), Order.valueOf(SysAdminCondition.getOrder()));
			}
		}
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(SysAdminCondition.getFirst()), Long.valueOf(SysAdminCondition.getLast()));
		return AdminDAO.findPagingResult(buildCriteria);
	}
	
	/**
	 * 列表
	 * @param Admin 
	 *
	 * @author panq
	 * @date CreateDate : 2016-06-02 15:35:32
	 */
	@Override
	public List<Admin> findAll(SysAdminCondition SysAdminCondition){
		CriteriaBuilder cb = Cnd.builder(Admin.class);
		if(!Strings.isEmpty(SysAdminCondition.getId())){
			cb.andEQ("id", SysAdminCondition.getId());
		}
		if(!Strings.isEmpty(SysAdminCondition.getUserName())){
			cb.andLike("userName", SysAdminCondition.getUserName());
		}
		if(!Strings.isEmpty(SysAdminCondition.getShortName())){
			cb.andLike("shortName", SysAdminCondition.getShortName());
		}
		if(!Strings.isEmpty(SysAdminCondition.getPassword())){
			cb.andEQ("password", SysAdminCondition.getPassword());
		}
		if(!Strings.isEmpty(SysAdminCondition.getSalt())){
			cb.andEQ("salt", SysAdminCondition.getSalt());
		}
		if(null != SysAdminCondition.getStatus()){
			cb.andEQ("status", SysAdminCondition.getStatus());
		}
		if(null != SysAdminCondition.getCreateTime()){
			cb.andEQ("createTime", SysAdminCondition.getCreateTime());
		}
		
		Criteria buildCriteria = cb.buildCriteria();
		return AdminDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * 主键查找
	 *
	 * @author panq
	 * @date CreateDate : 2016-06-02 15:35:32
	 */
	@Override
	public Admin findById(String id){
		return AdminDAO.get(id);
	}
	
	/**
	 * 新增
	 * @param SysAdminCondition
	 *
	 * @author panq
	 * @date CreateDate : 2016-06-02 15:35:32
	 */
	@Override
	public long insert(SysAdminCondition SysAdminCondition){
		Admin Admin = new Admin();
		BeanUtils.copyProperties(SysAdminCondition, Admin);
		return AdminDAO.insert(Admin);
	}

	/**
	 * 删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-06-02 15:35:32
	 */
	@Override
	public long deleteById(String id){
		return AdminDAO.deleteById(id);
	}
	
	/**
	 * 条件删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-06-02 15:35:32
	 */
	@Override
	public long deleteByCriteria(Criteria criteria){
		return AdminDAO.deleteByCriteria(criteria);
	}
	
	/**
	 * 行数汇总
	 *
	 * @author panq
	 * @date CreateDate : 2016-06-02 15:35:32
	 */
	@Override
	public long countByCriteria(Criteria criteria){
		return AdminDAO.countByCriteria(criteria);
	}
	
	/**
	 * 更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-06-02 15:35:32
	 */
	@Override
	public long update(SysAdminCondition SysAdminCondition){
		Admin Admin = new Admin();
		BeanUtils.copyProperties(SysAdminCondition, Admin);
		return AdminDAO.update(Admin);
	}
	
	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-06-02 15:35:32
	 */
	@Override
	public long updateByCriteria(SysAdminCondition SysAdminCondition,Criteria criteria){
		Admin Admin = new Admin();
		BeanUtils.copyProperties(SysAdminCondition, Admin);
		return AdminDAO.updateByCriteria(Admin,criteria);
	}
	
	/**
	 * 状态更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-06-02 15:35:32
	 */
	@Override
	public long updateStatus(String id, Integer status){
		return AdminDAO.updateStatus(id,status);
	}

	@Override
	@Transactional
	public long updateFix(SysAdminCondition SysAdminCondition, String roleId) {
		int nums = 0;
		//判断是否需要更新密码
		if(!Strings.isEmpty(SysAdminCondition.getPassword())){
			PasswordHelper pa = new PasswordHelper();
			Admin ad = new Admin();
			ad.setPassword(SysAdminCondition.getPassword());
			ad.setUserName(SysAdminCondition.getUserName());
			pa.encryptPassword(ad);
			
			SysAdminCondition.setPassword(ad.getPassword());
			SysAdminCondition.setSalt(ad.getSalt());
		}
		//是否新增用户
		if(Strings.isEmpty(SysAdminCondition.getId())){
			//新增
			SysAdminCondition.setCreateTime(new Date());
			SysAdminCondition.setId(Strings.getUUID());
			
			SysAdminCondition.setStatus(Integer.parseInt(ScanCodeConstants.STATUS_ACTIVE));
			this.insert(SysAdminCondition);
		}else{
			this.update(SysAdminCondition);
		}
		if(Strings.isNotEmpty(roleId)){
			//判断是否需要新增用户角色对应关系或者仅更新对应关系
			SysRoleUser admin = roleUserService.selectRoleUserByUserId(SysAdminCondition.getId());
			if(admin == null){
				admin = new SysRoleUser();
				admin.setAccountId(SysAdminCondition.getId());
				admin.setCreateTime(new Date());
				admin.setId(Strings.getUUID());
				admin.setRoleId(roleId);
				roleUserService.insert(admin);
			}else{
				admin.setRoleId(roleId);
				roleUserService.update(admin);
			}
		}
		return nums;
	}
}
