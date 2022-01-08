package org.example;

import static net.bytebuddy.matcher.ElementMatchers.*;

import java.io.File;
import java.io.IOException;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.pool.TypePool;

public class Masulsa {
	public static void main(String[] args) {
		// ClassLoader classLoader = Masulsa.class.getClassLoader();
		// TypePool typePool = TypePool.Default.of(classLoader);
		// try {
		// 	new ByteBuddy().redefine(typePool.describe("org.example.Moja").resolve(),
		// 		ClassFileLocator.ForClassLoader.of(classLoader))
		// 		.method(named("pullOut")).intercept(FixedValue.value("Rabbit!"))
		// 		.make().saveIn(new File("/Users/hwangseunghan/Dropbox/Mac/Documents/Study_Spring/code_manipulation/target/classes"));
		// } catch (IOException e) {
		// 	e.printStackTrace();
		// }
			System.out.println(new Moja().pullOut());
	}
}
