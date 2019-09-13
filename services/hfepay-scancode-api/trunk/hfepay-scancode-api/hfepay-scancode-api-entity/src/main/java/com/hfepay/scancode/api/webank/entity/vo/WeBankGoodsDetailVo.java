package com.hfepay.scancode.api.webank.entity.vo;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 微众 商户账户信息VO(API)
 * @author lemon
 *
 */
@SuppressWarnings("serial")
public class WeBankGoodsDetailVo implements Serializable{
	private String goodsId;//必填 32 商品的编号 apple-01 
	private String alipayGoodsId;//可选 32 支付宝定义的统一商品编号 20010001 
	private String goodsName;//必填 256 商品名称 ipad 
	private String quantity;// 必填 10 商品数量 1 
	private String price;//必填 9 商品单价，单位为元 2000 
	private String goodsCategory;//可选 24 商品类目 34543238 
	private String body;//商品描述信息

	public String getGoodsId() {
		return goodsId;
	}



	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}



	public String getAlipayGoodsId() {
		return alipayGoodsId;
	}



	public void setAlipayGoodsId(String alipayGoodsId) {
		this.alipayGoodsId = alipayGoodsId;
	}



	public String getGoodsName() {
		return goodsName;
	}



	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}



	public String getQuantity() {
		return quantity;
	}



	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}



	public String getPrice() {
		return price;
	}



	public void setPrice(String price) {
		this.price = price;
	}



	public String getGoodsCategory() {
		return goodsCategory;
	}



	public void setGoodsCategory(String goodsCategory) {
		this.goodsCategory = goodsCategory;
	}



	public String getBody() {
		return body;
	}



	public void setBody(String body) {
		this.body = body;
	}



	public Map<String,String> toMap(){
        Map<String,String> map = new HashMap<String, String>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            Object obj;
            try {
                obj = field.get(this);
                if(obj!=null && !"".equals(obj)){
                    map.put(field.getName(), String.valueOf(obj));
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }
	
	
}
