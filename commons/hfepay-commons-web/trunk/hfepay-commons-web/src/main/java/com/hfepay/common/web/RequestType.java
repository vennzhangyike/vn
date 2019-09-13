/**
 * 修改历史：<br/>
 * =================================================================<br/>
 * 修改人 修改时间 修改原因/内容<br/>
 * =================================================================<br/>
 * sam 20100111 增加修改历史注释<br/>
 */

package com.hfepay.common.web;

/**
 * 请求类型，根据请求头部信息识别为JSON或者是form等请求
 * 
 * @author Sam
 * 
 */
public enum RequestType {
    JSON, FORM_SIMPLE, FORM_UPLOAD, GENERAL
    
}
