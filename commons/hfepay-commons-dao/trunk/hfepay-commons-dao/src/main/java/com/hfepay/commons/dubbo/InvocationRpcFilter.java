package com.hfepay.commons.dubbo;

import java.util.List;
import java.util.Map;

import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcException;
import com.hfepay.commons.context.InvocationContext;
import com.hfepay.commons.context.InvocationContextHolder;
import com.hfepay.commons.context.SimpleInvocationContext;
import com.hfepay.commons.entity.App;
import com.hfepay.commons.entity.AppName;
import com.hfepay.commons.entity.UserAccount;

/**
 * ClassName:InvocationRpcFilter Date: 2014-2-13 上午10:57:41
 * 
 * @author hfepay
 * @version
 * @since JDK 1.6
 * @see
 */
public class InvocationRpcFilter implements Filter {
	/**
	 * 
	 * ClassName: Context Key <br/>
	 * date: 2014-2-13 下午05:25:14 <br/>
	 * 
	 * @author hfepay
	 * @since JDK 1.6
	 */
	public static class Key {
		public static final String USER_ID = "USER_ID";
		public static final String FULL_NAME = "FULL_NAME";
		
		public static final String APP_CODE = "APP_CODE";
	}

	/**
	 * 
	 * 客户端提供上下文环境，服务端绑定上下文环境到ThreadLocal
	 * 
	 * @see com.alibaba.dubbo.rpc.Filter#invoke(com.alibaba.dubbo.rpc.Invoker,
	 *      com.alibaba.dubbo.rpc.Invocation)
	 */
	
	public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
		RpcContext context = RpcContext.getContext();
		if (context.isProviderSide()) {
			fillInvocationContext(RpcContext.getContext().getAttachments());
		} else {
			InvocationContext invocationContext = null;
			try {
				invocationContext = InvocationContextHolder.getInvocationContext();
			} catch (Exception e) {
			}
			if(invocationContext!=null){
				UserAccount userAccout = invocationContext.getUserAccout();
				if(userAccout!=null){
					context.getAttachments().put(Key.USER_ID, userAccout.getId()+"");
					context.getAttachments().put(Key.FULL_NAME, userAccout.getFullName());
					context.getAttachments().put(Key.APP_CODE,invocationContext.getAppName().getCode());
				}
			}else{
				
			}
		}
        try {
            return invoker.invoke(invocation);
        } finally {
        	InvocationContextHolder.removeContext();
        }
	}

	/**
	 * 
	 * fillInvocationContext:数据填充. <br/>
	 *
	 * @author hfepay
	 * @param invocationContext
	 * @param attachments
	 * @since JDK 1.6
	 */
	private void fillInvocationContext(Map<String, String> attachments) {
		final String userId = attachments.get(Key.USER_ID);
		final String fullName = attachments.get(Key.FULL_NAME);
		final String appCode = attachments.get(Key.APP_CODE);
		if(userId == null){
			return;
		}
		try {
			UserAccount ua = new UserAccount(){
				private static final long serialVersionUID = 1L;

				public void setId(Long id) {
				}
				public Long getId() {
					return Long.parseLong(userId);
				}

				public String getFullName() {
					return fullName;
				}

				public String getAccountName() {
					return null;
				}

				public String getMobile() {
					return null;
				}

				public String getEmail() {
					return null;
				}

				public String getStatus() {
					return null;
				}

				public List<App> getOpenedApps() {
					return null;
				}
				public Map<String, Object> getProperties() {
					return null;
				}
				
			};
			AppName appName = null;
			if(appCode!=null){
				appName = AppName.byCode(appCode);
			}
			SimpleInvocationContext.create(ua, appName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
