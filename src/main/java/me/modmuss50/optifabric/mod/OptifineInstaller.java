package me.modmuss50.optifabric.mod;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

//A class used to extract the optifine jar from the installer
public class OptifineInstaller {

	public static void extract(File installer, File output, File minecraftJar) throws IOException {
		System.out.println("Running optifine patcher");

		try (URLClassLoader classLoader = new URLClassLoader(new URL[]{installer.toURI().toURL()}, OptifineInstaller.class.getClassLoader())) {
			Class<?> clazz = classLoader.loadClass("optifine.Patcher");
			Method method = clazz.getDeclaredMethod("process", File.class, File.class, File.class);
			method.invoke(null, minecraftJar, installer, output);
		} catch (ReflectiveOperationException e) {
			throw new RuntimeException("Error running OptiFine patcher at " + installer + " on " + minecraftJar, e);
		}
	}

}
