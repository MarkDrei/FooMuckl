package de.rkable.foomuckl.core;

import com.google.inject.AbstractModule;

public class FooModule extends AbstractModule  {

	@Override
	protected void configure() {
		bind(Environment.class);
	}

}
