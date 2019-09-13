 
package com.hfepay.commons.base.lang;


/**
 * 提供函数式范式编程的回调接口
 * @author Sam
 *
 * @param <F>
 * @param <T>
 */
public interface Function<F, T> {
 
  T apply( F input);

 
  @Override
  boolean equals( Object object);
}
