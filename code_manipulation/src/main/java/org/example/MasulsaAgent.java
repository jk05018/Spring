package org.example;

import static net.bytebuddy.matcher.ElementMatchers.*;

import java.lang.instrument.Instrumentation;
import java.util.Comparator;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;

public class MasulsaAgent {

	public static void premain(String agentArgs, Instrumentation inst) {
		new AgentBuilder.Default()
			.type(ElementMatchers.any())
			.transform((builder, typeDescription, classLoader, javaModule) ->
				builder.method(named("pullOut")).intercept(FixedValue.value("Rabbit!"))).installOn(inst);
	}
ㅁ

}
