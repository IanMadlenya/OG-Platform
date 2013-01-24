/**
 * Copyright (C) 2013 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.engine.fudgemsg;

import static org.testng.Assert.assertEquals;

import java.util.Collections;
import java.util.Set;

import org.testng.annotations.Test;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.opengamma.engine.ComputationTargetSpecification;
import com.opengamma.engine.target.ComputationTargetRequirement;
import com.opengamma.engine.value.ValueProperties;
import com.opengamma.engine.value.ValuePropertyNames;
import com.opengamma.engine.value.ValueRequirement;
import com.opengamma.engine.value.ValueSpecification;
import com.opengamma.engine.view.compilation.CompiledViewCalculationConfiguration;
import com.opengamma.engine.view.compilation.CompiledViewCalculationConfigurationImpl;
import com.opengamma.id.ExternalId;
import com.opengamma.id.UniqueId;
import com.opengamma.util.test.AbstractFudgeBuilderTestCase;

/**
 * Tests the {@link CompiledViewCalculationConfigurationFudgeBuilder} class.
 */
@Test
public class CompiledViewCalculationConfigurationFudgeBuilderTest extends AbstractFudgeBuilderTestCase {

  public void testEmpty() {
    final CompiledViewCalculationConfiguration in = new CompiledViewCalculationConfigurationImpl("1", Collections.<ComputationTargetSpecification>emptySet(),
        Collections.<ValueSpecification, Set<ValueRequirement>>emptyMap(), Collections.<ValueRequirement, ValueSpecification>emptyMap());
    final CompiledViewCalculationConfiguration out = cycleObject(CompiledViewCalculationConfiguration.class, in);
    assertEquals(out.getName(), in.getName());
    assertEquals(out.getComputationTargets(), in.getComputationTargets());
    assertEquals(out.getMarketDataRequirements(), in.getMarketDataRequirements());
    assertEquals(out.getTerminalOutputSpecifications(), in.getTerminalOutputSpecifications());
  }

  public void testBasic() {
    final ComputationTargetRequirement targetReq = ComputationTargetRequirement.of(ExternalId.of("Foo", "Bar"));
    final ComputationTargetSpecification targetSpec = ComputationTargetSpecification.of(UniqueId.of("Sec", "123"));
    final CompiledViewCalculationConfiguration in = new CompiledViewCalculationConfigurationImpl("2", ImmutableSet.of(ComputationTargetSpecification.NULL, targetSpec),
        ImmutableMap.<ValueSpecification, Set<ValueRequirement>>of(new ValueSpecification("Value", targetSpec, ValueProperties.with(ValuePropertyNames.FUNCTION, "Foo").get()),
            ImmutableSet.of(new ValueRequirement("Value", targetReq))), ImmutableMap.of(new ValueRequirement("Data", targetReq),
                new ValueSpecification("Data", targetSpec, ValueProperties.with(ValuePropertyNames.FUNCTION, "Bar").get())));
    final CompiledViewCalculationConfiguration out = cycleObject(CompiledViewCalculationConfiguration.class, in);
    assertEquals(out.getName(), in.getName());
    assertEquals(out.getComputationTargets(), in.getComputationTargets());
    assertEquals(out.getTerminalOutputSpecifications(), in.getTerminalOutputSpecifications());
    assertEquals(out.getMarketDataRequirements(), in.getMarketDataRequirements());
  }

}
