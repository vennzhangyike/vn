package com.hfepay.scancode.channel.commons;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hfepay.scancode.commons.entity.Admin;

/**
 * 密碼管理類
* @author ssd
* @date 2015-4-30 下午5:02:52
 */
@Service("passwordHelper")
public class PasswordHelper {

    private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    @Value("${password.algorithmName}")
    private String algorithmName="md5";
    @Value("${password.hashIterations}")
    private String hashIterations="2";

    public void setRandomNumberGenerator(RandomNumberGenerator randomNumberGenerator) {
        this.randomNumberGenerator = randomNumberGenerator;
    }

    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    public void setHashIterations(String hashIterations) {
        this.hashIterations = hashIterations;
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
     * 
    *
    * @Description: 字符串加密为密码
    * @author yuping
    * @date 2015-4-30 下午5:03:15 
    * @return String    返回类型 
     */
    public String getEncryptPassword(Admin user) {
        String newPassword = new SimpleHash(
                algorithmName,
                user.getPassword(),
                ByteSource.Util.bytes(user.getCredentialsSalt()),
                Integer.parseInt(hashIterations)).toHex();
        return newPassword;
    }    
    
    
    public static void main(String[] args) {
    	Admin pa = new Admin();
    	pa.setUserName("18725984020");
    	pa.setPassword("tanbiao123456");
    	PasswordHelper ph = new PasswordHelper();
    	ph.encryptPassword(pa);
    	
    	System.out.println("pw:"+pa.getPassword()+",salt:"+pa.getSalt());
    }
}
