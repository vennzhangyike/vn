package com.hfepay.pay.utils;

/**
 * @ClassName: NodeRelationSeqUtils
 * @Description: NodeRelation的seq取值方法
 * @author: husain
 * @date: 2017年1月22日 下午3:46:53
 */
public final class NodeRelationSeqUtils {
	public final static String SEQ_START_STR = "0000";
	private final static String first="0";//第一位字符
	private final static String last="Z";//最后一位是Z，当到了最后一位的时候，倒数第二位变化，依次类推
	private final static String seq = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";//构造seq的序列
	
	//下一层级变化的之后末尾四位，
	//当前节点的下级节点是在当前节点的基础上添加末尾0000
	//父级节点的下级节点需要在当前下级节点的基础上变换末尾四位
	public static String getNext(String current){
		int index = current.length()-4;//末尾四位变化
		String parentStr=current.substring(0, index);//倒数第五位开始是父节点，不变化
		String currentChangeStr = current.substring(index);//变换的依据，末尾四位
		if(currentChangeStr.equals("ZZZZ")){
			throw new RuntimeException("当前序列已满....");
		}
		StringBuffer buffer = new StringBuffer(parentStr);
		StringBuffer tail = new StringBuffer();
		StringBuffer tailChangedStr = getStr(tail, currentChangeStr);
		buffer.append(tailChangedStr);
		return buffer.toString();
	}
	
	//获取当前节点的下一个子节点
	private static StringBuffer getStr(StringBuffer buffer,String currentStr){
		String lastStr = currentStr.substring(currentStr.length()-1);
		if(lastStr.equals(last)){//是Z则需要进位
			String headStr = currentStr.substring(0, currentStr.length()-1);
			buffer.append(first);
			buffer = buffer.reverse();
			return getStr(buffer,headStr);
		}else{
			String headStr = currentStr.substring(0,currentStr.length()-1);//头
			String nextStr = getNextChar(lastStr);
			buffer = new StringBuffer(headStr).append(nextStr).append(buffer);
		}
		return buffer;
	}
	
	//获取序列的下一个字符
	private static String getNextChar(String changeStr){
		int next = seq.indexOf(changeStr)+1;
		return seq.substring(next, next+1);
	}
	
}
