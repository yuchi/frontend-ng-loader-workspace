package com.liferay.frontend.packages.definitions;

import java.net.URL;
import java.util.Optional;

import javax.servlet.ServletContext;

public interface PackageInterpreter {

	public String getType();

	public Optional<PackagesBundleConfig> interpret(
		ServletContext servletContext, String location);

}
