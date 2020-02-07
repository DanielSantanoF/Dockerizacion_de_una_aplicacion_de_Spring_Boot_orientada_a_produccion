package com.salesianostriana.dam.proyectorepaso.configuracion;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

/**
 * @author Daniel Santano Fernández
 * Clase que usamos para configurar el MVC
 */
@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer{

	/**
	 * Metodo para decir el getmaping de login y acceso denegado
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login");
		registry.addViewController("/AccesoDenegado");
	}
	
	/**
	 * Metodo para indicar donde estan nuestras rutas estaticas necesario al usar la anotación @EnableWebMvc
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/images/**").addResourceLocations("file:files/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
		registry.addResourceHandler("/img/**").addResourceLocations("classpath:/static/img/");
		registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
		registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
		registry.addResourceHandler("/admin/img/**").addResourceLocations("classpath:/static/img/");
		registry.addResourceHandler("/admin/css/**").addResourceLocations("classpath:/static/css/");
			
	}
	
	/**
	 * Determinamos cual es el locale del nuestra app
	 * @return slr el locale indicado
	 */
	@Bean
	public LocaleResolver localeResolver() {
	    SessionLocaleResolver slr = new SessionLocaleResolver();
	    slr.setDefaultLocale(Locale.getDefault());
	    return slr;
	}
	
	/**
	 * Metodo para cambiar el locale segun el valor que le demos al lang
	 * @return lci el locale nuevo
	 */
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
	    LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
	    lci.setParamName("lang");
	    return lci;
	}
	
	/**
	 * Metodo necesario para que funciona el LocaleChangeInterceptor
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
	    registry.addInterceptor(localeChangeInterceptor());
	}
}
