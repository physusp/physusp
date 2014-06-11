package com.gedaeusp.domain;

import javax.servlet.http.HttpServletResponse;

import com.thoughtworks.xstream.XStream;

import br.com.caelum.vraptor.interceptor.TypeNameExtractor;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.serialization.ProxyInitializer;
import br.com.caelum.vraptor.serialization.xstream.XStreamBuilder;
import br.com.caelum.vraptor.serialization.xstream.XStreamJSONSerialization;

@Component
public class CustomJSONSerialization extends XStreamJSONSerialization {

	public CustomJSONSerialization(HttpServletResponse response,
			TypeNameExtractor extractor, ProxyInitializer initializer,
			XStreamBuilder builder) {
		super(response, extractor, initializer, builder);
	}

	@Override  
    protected XStream getXStream() {  
         XStream stream = super.getXStream();  
         stream.registerConverter(new UnitValueConverter());  
         return stream;  
  
    } 

}
