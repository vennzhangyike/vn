package com.hfepay.scancode.commons.dao.channel;

import java.util.List;

import com.hfepay.commons.dao.EntityDAO;
import com.hfepay.scancode.commons.entity.ChannelRoleResource;


    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:43")
public interface ChannelRoleResourceDAO extends EntityDAO<ChannelRoleResource, String> {

		long deleteChannelRoleResourceByRoleId(String roleId);

		void insertBatch(List<ChannelRoleResource> list);

}