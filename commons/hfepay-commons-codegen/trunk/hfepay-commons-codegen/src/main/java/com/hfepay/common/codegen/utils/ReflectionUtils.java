package com.hfepay.common.codegen.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 反射工具类.
 * 
 * 提供访问私有变量,获取泛型类型Class, 提取集合中元素的属性, 转换字符串到对象等Util函数.
 * 
 * @author calvin
 */
public class ReflectionUtils {

  private static Logger logger = LoggerFactory.getLogger(ReflectionUtils.class);

  static {
    DateConverter dc = new DateConverter();
    dc.setUseLocaleFormat(true);
    dc.setPatterns(new String[] { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss" });
    ConvertUtils.register(dc, Date.class);
  }

  /**
   * 调用Getter方法.
   */
  public static Object invokeGetterMethod(Object obj, String propertyName) {
    String getterMethodName = "get" + StringUtils.capitalize(propertyName);
    return invokeMethod(obj, getterMethodName, new Class[] {}, new Object[] {});
  }

  /**
   * 调用Setter方法.使用value的Class来查找Setter方法.
   */
  public static void invokeSetterMethod(Object obj, String propertyName, Object value) {
    invokeSetterMethod(obj, propertyName, value, null);
  }

  /**
   * 调用Setter方法.
   * 
   * @param propertyType
   *          用于查找Setter方法,为空时使用value的Class替代.
   */
  public static void invokeSetterMethod(Object obj, String propertyName, Object value, Class<?> propertyType) {
    Class<?> type = propertyType != null ? propertyType : value.getClass();
    String setterMethodName = "set" + StringUtils.capitalize(propertyName);
    invokeMethod(obj, setterMethodName, new Class[] { type }, new Object[] { value });
  }

  /**
   * 直接读取对象属性值, 无视private/protected修饰符, 不经过getter函数.
   */
  public static Object getFieldValue(final Object obj, final String fieldName) {
    Field field = getAccessibleField(obj, fieldName);

    if (field == null) {
      throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + obj + "]");
    }

    Object result = null;
    try {
      result = field.get(obj);
    } catch (IllegalAccessException e) {
      logger.error("不可能抛出的异常{}", e.getMessage());
    }
    return result;
  }

  /**
   * 直接设置对象属性值, 无视private/protected修饰符, 不经过setter函数.
   */
  public static void setFieldValue(final Object obj, final String fieldName, final Object value) {
    Field field = getAccessibleField(obj, fieldName);

    if (field == null) {
      throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + obj + "]");
    }

    try {
      field.set(obj, value);
    } catch (IllegalAccessException e) {
      logger.error("不可能抛出的异常:{}", e.getMessage());
    }
  }

  /**
   * 循环向上转型, 获取对象的DeclaredField, 并强制设置为可访问.
   * 
   * 如向上转型到Object仍无法找到, 返回null.
   */
  public static Field getAccessibleField(final Object obj, final String fieldName) {
    //Assert.notNull(obj, "object不能为空");
    //Assert.hasText(fieldName, "fieldName");
    for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
      try {
        Field field = superClass.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field;
      } catch (NoSuchFieldException e) {// NOSONAR
        // Field不在当前类定义,继续向上转型
      }
    }
    return null;
  }

  /**
   * 直接调用对象方法, 无视private/protected修饰符. 用于一次性调用的情况.
   */
  public static Object invokeMethod(final Object obj, final String methodName, final Class<?>[] parameterTypes, final Object[] args) {
    Method method = getAccessibleMethod(obj, methodName, parameterTypes);
    if (method == null) {
      logger.debug("###########################################" + obj.toString() +  " found follow methods:");
      debugAccessibleMethods(obj);
      throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + obj + "]");
    }

    try {
      return method.invoke(obj, args);
    } catch (Exception e) {
      throw convertReflectionExceptionToUnchecked(e);
    }
  }

  /**
   * 循环向上转型, 获取对象的DeclaredMethod,并强制设置为可访问. 如向上转型到Object仍无法找到, 返回null.
   * 
   * 用于方法需要被多次调用的情况. 先使用本函数先取得Method,然后调用Method.invoke(Object obj, Object...
   * args)
   */
  public static Method getAccessibleMethod(final Object obj, final String methodName, final Class<?>... parameterTypes) {
    //Assert.notNull(obj, "object不能为空");

    for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
      try {
        Method method = superClass.getDeclaredMethod(methodName, parameterTypes);

        method.setAccessible(true);

        return method;

      } catch (NoSuchMethodException e) {// NOSONAR
        // Method不在当前类定义,继续向上转型
      }
    }
    return null;
  }
  
  public static void debugAccessibleMethods(final Object obj) {
    //Assert.notNull(obj, "object不能为空");
    for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
        Method[] methods = superClass.getDeclaredMethods();
        for(Method method:methods){
          logger.debug(method.getName() + ": " + method.toString());
        }
    }
  }

  /**
   * 通过反射, 获得Class定义中声明的父类的泛型参数的类型. 如无法找到, 返回Object.class. eg. public UserDao
   * extends HibernateDao<User>
   * 
   * @param clazz
   *          The class to introspect
   * @return the first generic declaration, or Object.class if cannot be
   *         determined
   */
  @SuppressWarnings("unchecked")
  public static <T> Class<T> getSuperClassGenricType(@SuppressWarnings("rawtypes") final Class clazz) {
    return getSuperClassGenricType(clazz, 0);
  }

  /**
   * 通过反射, 获得Class定义中声明的父类的泛型参数的类型. 如无法找到, 返回Object.class.
   * 
   * 如public UserDao extends HibernateDao<User,Long>
   * 
   * @param clazz
   *          clazz The class to introspect
   * @param index
   *          the Index of the generic ddeclaration,start from 0.
   * @return the index generic declaration, or Object.class if cannot be
   *         determined
   */
  @SuppressWarnings("unchecked")
  public static Class getSuperClassGenricType(final Class clazz, final int index) {

    Type genType = clazz.getGenericSuperclass();

    if (!(genType instanceof ParameterizedType)) {
      logger.warn(clazz.getSimpleName() + "'s superclass not ParameterizedType");
      return Object.class;
    }

    Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

    if (index >= params.length || index < 0) {
      logger.warn("Index: " + index + ", Size of " + clazz.getSimpleName() + "'s Parameterized Type: " + params.length);
      return Object.class;
    }
    if (!(params[index] instanceof Class)) {
      logger.warn(clazz.getSimpleName() + " not set the actual class on superclass generic parameter");
      return Object.class;
    }

    return (Class) params[index];
  }

  /**
   * 提取集合中的对象的属性(通过getter函数), 组合成List.
   * 
   * @param collection
   *          来源集合.
   * @param propertyName
   *          要提取的属性名.
   */
  @SuppressWarnings("unchecked")
  public static List convertElementPropertyToList(final Collection collection, final String propertyName) {
    List list = new ArrayList();

    try {
      for (Object obj : collection) {
        list.add(PropertyUtils.getProperty(obj, propertyName));
      }
    } catch (Exception e) {
      throw convertReflectionExceptionToUnchecked(e);
    }

    return list;
  }

  /**
   * 提取集合中的对象的属性(通过getter函数), 组合成由分割符分隔的字符串.
   * 
   * @param collection
   *          来源集合.
   * @param propertyName
   *          要提取的属性名.
   * @param separator
   *          分隔符.
   */
  @SuppressWarnings("unchecked")
  public static String convertElementPropertyToString(final Collection collection, final String propertyName, final String separator) {
    List list = convertElementPropertyToList(collection, propertyName);
    return StringUtils.join(list, separator);
  }

  /**
   * 转换字符串到相应类型.
   * 
   * @param value
   *          待转换的字符串
   * @param toType
   *          转换目标类型
   */
  public static Object convertStringToObject(String value, Class<?> toType) {
    try {
      return ConvertUtils.convert(value, toType);
    } catch (Exception e) {
      throw convertReflectionExceptionToUnchecked(e);
    }
  }

  /**
   * 将反射时的checked exception转换为unchecked exception.
   */
  public static RuntimeException convertReflectionExceptionToUnchecked(Exception e) {
    if (e instanceof IllegalAccessException || e instanceof IllegalArgumentException || e instanceof NoSuchMethodException) {
      return new IllegalArgumentException("Reflection Exception.", e);
    } else if (e instanceof InvocationTargetException) {
      return new RuntimeException("Reflection Exception.", ((InvocationTargetException) e).getTargetException());
    } else if (e instanceof RuntimeException) {
      return (RuntimeException) e;
    }
    return new RuntimeException("Unexpected Checked Exception.", e);
  }

  static Object operate(Object obj, String fieldName, Object fieldVal, String type) {
    Object ret = null;
    try {
      // 获得对象类型
      Class<? extends Object> classType = obj.getClass();
      // 获得对象的所有属性
      Field fields[] = classType.getDeclaredFields();
      for (int i = 0; i < fields.length; i++) {
        Field field = fields[i];
        if (field.getName().equals(fieldName)) {

          String firstLetter = fieldName.substring(0, 1).toUpperCase(); // 获得和属性对应的getXXX()方法的名字
          if ("set".equals(type)) {
            String setMethodName = "set" + firstLetter + fieldName.substring(1); // 获得和属性对应的getXXX()方法
            Method setMethod = classType.getMethod(setMethodName, new Class[] { field.getType() }); // 调用原对象的getXXX()方法
            ret = setMethod.invoke(obj, new Object[] { fieldVal });
          }
          if ("get".equals(type)) {
            String getMethodName = "get" + firstLetter + fieldName.substring(1); // 获得和属性对应的setXXX()方法的名字
            Method getMethod = classType.getMethod(getMethodName, new Class[] {});
            ret = getMethod.invoke(obj, new Object[] {});
          }
          return ret;
        }
      }
    } catch (Exception e) {
      logger.warn("reflect error:" + fieldName, e);
    }
    return ret;
  }
}
