package com.hfepay.scancode.service.utils;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import com.hfepay.scancode.commons.entity.Admin;
import com.hfepay.scancode.commons.entity.ChannelAdmin;

/**
 * 密碼管理類
* @author ssd
* @date 2015-4-30 下午5:02:52
 */
public class PasswordHelper {

    private static RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    private static String algorithmName="md5";
    private static String hashIterations="2";

    
    /**
     * 获取一串加密盐值字符串
     * @return
     */
    public static String getSalt(){
    	return randomNumberGenerator.nextBytes().toHex();
    }
    
    /**
     * 
    *
    * @Description: 加密密碼,我们使用MD5算法，“密码+盐（用户名+随机数）”的方式生成散列值，加密方法是：md5(md5(密码+username+salt2))，其中salt2是一串随机的散列值
    * @param @param user    设定文件 
    * @author ssd
    * @date 2015-4-30 下午5:03:15 
    * @return void    返回类型 
    * @throws
     */
    public void encryptPassword(ChannelAdmin user) {

        user.setSalt(randomNumberGenerator.nextBytes().toHex());

        String newPassword = new SimpleHash(
                algorithmName,
                user.getPassword(),
                ByteSource.Util.bytes(user.getCredentialsSalt()),
                Integer.parseInt(hashIterations)).toHex();

        user.setPassword(newPassword);
    }
    
    /**
     * 
    *
    * @Description: 加密密碼,我们使用MD5算法，“密码+盐（用户名+随机数）”的方式生成散列值，加密方法是：md5(md5(密码+username+salt2))，其中salt2是一串随机的散列值
    * @param @param user    设定文件 
    * @author ssd
    * @date 2015-4-30 下午5:03:15 
    * @return void    返回类型 
    * @throws
     */
    public void encryptPassword(Admin user) {

        user.setSalt(randomNumberGenerator.nextBytes().toHex());

        String newPassword = new SimpleHash(
                algorithmName,
                user.getPassword(),
                ByteSource.Util.bytes(user.getCredentialsSalt()),
                Integer.parseInt(hashIterations)).toHex();

        user.setPassword(newPassword);
    }
    
    /**
     * 给文明密码加密，获取加密后的密码
     * @param userName
     * @param password
     * @param salt
     * @return
     */
    public static String getEncryptPassword(String userName,String password,String salt){
    	String newPassword = new SimpleHash(
                algorithmName,
                password,
                ByteSource.Util.bytes(userName+salt),
                Integer.parseInt(hashIterations)).toHex();
    	return newPassword;
    }
    
    
    
    public static void main(String[] args) {
    	String salt = PasswordHelper.getSalt();
    	
    	String pwd = PasswordHelper.getEncryptPassword("18725984020", "tanbiao123456", salt);
    	
    	System.out.println("pw:"+pwd+",salt:"+salt);
    }
}
