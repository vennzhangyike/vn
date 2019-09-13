package com.hfepay.common.web.security.xss;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolverComposite;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.ServletRequestDataBinderFactory;
import org.springframework.web.util.WebUtils;

import com.hfepay.common.web.security.xss.annotation.XSSAnnotation;
import com.hfepay.common.web.security.xss.annotation.XSSParameterAnnotation;
import com.hfepay.common.web.security.xss.util.XSSUtils;

public class XSSHandlerMethodArgumentResolver extends
		HandlerMethodArgumentResolverComposite {
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterAnnotation(XSSParameterAnnotation.class) != null
				|| parameter.getParameterAnnotation(XSSAnnotation.class) != null;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter,
			ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) throws Exception {
		XSSParameterAnnotation xssParameterAnnotation = parameter
				.getParameterAnnotation(XSSParameterAnnotation.class);
		if (xssParameterAnnotation != null) {
			HttpServletRequest request = webRequest
					.getNativeRequest(HttpServletRequest.class);
			String value = xssParameterAnnotation.value();
			String parameterValue = request.getParameter(value);
			if (parameterValue != null) {
				String[] types = xssParameterAnnotation.types();
				return XSSUtils.filterXSS(parameterValue, Arrays.asList(types));
			}
		} else {
			XSSAnnotation xssAnnotation = parameter
					.getParameterAnnotation(XSSAnnotation.class);
			if (xssAnnotation != null) {
				String name = parameter.getParameterName();
				Object target = (mavContainer.containsAttribute(name)) ? mavContainer
						.getModel().get(name) : createAttribute(name,
						parameter, binderFactory, webRequest);

				WebDataBinder binder = binderFactory.createBinder(webRequest,
						target, name);
				target = binder.getTarget();
				if (target != null) {
					bindRequestParameters(mavContainer, binderFactory, binder,
							webRequest, parameter);

					validateIfApplicable(binder, parameter);
					if (binder.getBindingResult().hasErrors()) {
						if (isBindExceptionRequired(binder, parameter)) {
							throw new BindException(binder.getBindingResult());
						}
					}
				}

				target = binder.convertIfNecessary(binder.getTarget(),
						parameter.getParameterType());
				mavContainer.addAttribute(name, target);
				
				return target;
			}
		}

		return super.resolveArgument(parameter, mavContainer, webRequest,
				binderFactory);
	}

	/**
	 * Extension point to create the model attribute if not found in the model.
	 * The default implementation uses the default constructor.
	 * 
	 * @param attributeName
	 *            the name of the attribute, never {@code null}
	 * @param parameter
	 *            the method parameter
	 * @param binderFactory
	 *            for creating WebDataBinder instance
	 * @param request
	 *            the current request
	 * @return the created model attribute, never {@code null}
	 */
	protected Object createAttribute(String attributeName,
			MethodParameter parameter, WebDataBinderFactory binderFactory,
			NativeWebRequest request) throws Exception {

		String value = getRequestValueForAttribute(attributeName, request);

		if (value != null) {
			Object attribute = createAttributeFromRequestValue(value,
					attributeName, parameter, binderFactory, request);
			if (attribute != null) {
				return attribute;
			}
		}

		return BeanUtils.instantiateClass(parameter.getParameterType());
	}

	/**
	 * Obtain a value from the request that may be used to instantiate the model
	 * attribute through type conversion from String to the target type.
	 * <p>
	 * The default implementation looks for the attribute name to match a URI
	 * variable first and then a request parameter.
	 * 
	 * @param attributeName
	 *            the model attribute name
	 * @param request
	 *            the current request
	 * @return the request value to try to convert or {@code null}
	 */
	protected String getRequestValueForAttribute(String attributeName,
			NativeWebRequest request) {
		Map<String, String> variables = getUriTemplateVariables(request);
		if (StringUtils.hasText(variables.get(attributeName))) {
			return variables.get(attributeName);
		} else if (StringUtils.hasText(request.getParameter(attributeName))) {
			return request.getParameter(attributeName);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	protected final Map<String, String> getUriTemplateVariables(
			NativeWebRequest request) {
		Map<String, String> variables = (Map<String, String>) request
				.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE,
						RequestAttributes.SCOPE_REQUEST);
		return (variables != null) ? variables : Collections
				.<String, String> emptyMap();
	}

	/**
	 * Create a model attribute from a String request value (e.g. URI template
	 * variable, request parameter) using type conversion.
	 * <p>
	 * The default implementation converts only if there a registered
	 * {@link Converter} that can perform the conversion.
	 * 
	 * @param sourceValue
	 *            the source value to create the model attribute from
	 * @param attributeName
	 *            the name of the attribute, never {@code null}
	 * @param parameter
	 *            the method parameter
	 * @param binderFactory
	 *            for creating WebDataBinder instance
	 * @param request
	 *            the current request
	 * @return the created model attribute, or {@code null}
	 * @throws Exception
	 */
	protected Object createAttributeFromRequestValue(String sourceValue,
			String attributeName, MethodParameter parameter,
			WebDataBinderFactory binderFactory, NativeWebRequest request)
			throws Exception {
		DataBinder binder = binderFactory.createBinder(request, null,
				attributeName);
		ConversionService conversionService = binder.getConversionService();
		if (conversionService != null) {
			TypeDescriptor source = TypeDescriptor.valueOf(String.class);
			TypeDescriptor target = new TypeDescriptor(parameter);
			if (conversionService.canConvert(source, target)) {
				return binder.convertIfNecessary(sourceValue,
						parameter.getParameterType(), parameter);
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Downcast {@link WebDataBinder} to {@link ServletRequestDataBinder} before
	 * binding.
	 * 
	 * @throws Exception
	 * @see ServletRequestDataBinderFactory
	 */
	protected void bindRequestParameters(ModelAndViewContainer mavContainer,
			WebDataBinderFactory binderFactory, WebDataBinder binder,
			NativeWebRequest request, MethodParameter parameter)
			throws Exception {
		ServletRequest servletRequest = prepareServletRequest(
				binder.getTarget(), request, parameter);
		// bind model
		ServletRequestDataBinder servletBinder = (ServletRequestDataBinder) binder;
		servletBinder.bind(servletRequest);

	}

	private ServletRequest prepareServletRequest(Object target,
			NativeWebRequest request, MethodParameter parameter) {
		HttpServletRequest nativeRequest = (HttpServletRequest) request
				.getNativeRequest();
		MultipartRequest multipartRequest = WebUtils.getNativeRequest(
				nativeRequest, MultipartRequest.class);
		MockHttpServletRequest mockRequest = null;
		if (multipartRequest != null) {
			MockMultipartHttpServletRequest mockMultipartRequest = new MockMultipartHttpServletRequest();
			mockMultipartRequest.getMultiFileMap().putAll(
					multipartRequest.getMultiFileMap());
		} else {
			mockRequest = new MockHttpServletRequest();
		}

		XSSAnnotation xssAnnotation = parameter
				.getParameterAnnotation(XSSAnnotation.class);
		XSSParameterAnnotation[] xssParameterAnnotations = xssAnnotation
				.value();

		for (Entry<String, String> entry : getUriTemplateVariables(request)
				.entrySet()) {
			String parameterName = entry.getKey();
			String value = entry.getValue();
			XSSParameterAnnotation xssParameterAnnotation = getXSSParameterAnnotation(
					parameterName, xssParameterAnnotations);
			if (xssParameterAnnotation != null) {
				String[] types = xssParameterAnnotation.types();
				mockRequest.setParameter(parameterName,
						XSSUtils.filterXSS(value, Arrays.asList(types)));
			}
		}

		for (Object parameterEntry : nativeRequest.getParameterMap().entrySet()) {
			Entry<String, String[]> entry = (Entry<String, String[]>) parameterEntry;
			String parameterName = entry.getKey();
			String[] value = entry.getValue();
			XSSParameterAnnotation xssParameterAnnotation = getXSSParameterAnnotation(
					parameterName, xssParameterAnnotations);
			if (xssParameterAnnotation != null) {
				String[] types = xssParameterAnnotation.types();
				for (int i = 0; i < value.length; i++) {
					value[i] = XSSUtils.filterXSS(value[i],
							Arrays.asList(types));
				}
			}
			mockRequest.setParameter(parameterName, value);
		}

		return mockRequest;
	}

	private XSSParameterAnnotation getXSSParameterAnnotation(
			String parameterName,
			XSSParameterAnnotation[] xssParameterAnnotations) {
		if (xssParameterAnnotations != null) {
			for (XSSParameterAnnotation ann : xssParameterAnnotations) {
				if (ann.value().equals(parameterName)) {
					return ann;
				}
			}
		}
		return null;
	}

	 /**
     * Validate the model attribute if applicable.
     * <p>The default implementation checks for {@code @javax.validation.Valid}.
     * @param binder the DataBinder to be used
     * @param parameter the method parameter
     */
    protected void validateIfApplicable(WebDataBinder binder, MethodParameter parameter) {
        Annotation[] annotations = parameter.getParameterAnnotations();
        for (Annotation annot : annotations) {
            if (annot.annotationType().getSimpleName().startsWith("Valid")) {
                Object hints = AnnotationUtils.getValue(annot);
                binder.validate(hints instanceof Object[] ? (Object[]) hints : new Object[] {hints});
            }
        }
    }
    
    /**
     * Whether to raise a {@link BindException} on bind or validation errors.
     * The default implementation returns {@code true} if the next method 
     * argument is not of type {@link Errors}.
     * @param binder the data binder used to perform data binding
     * @param parameter the method argument
     */
    protected boolean isBindExceptionRequired(WebDataBinder binder, MethodParameter parameter) {
        int i = parameter.getParameterIndex();
        Class<?>[] paramTypes = parameter.getMethod().getParameterTypes();
        boolean hasBindingResult = (paramTypes.length > (i + 1) && Errors.class.isAssignableFrom(paramTypes[i + 1]));
        
        return !hasBindingResult;
    }
    
}
