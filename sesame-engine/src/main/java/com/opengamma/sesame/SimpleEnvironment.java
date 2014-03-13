/**
 * Copyright (C) 2014 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.sesame;

import java.util.Objects;

import org.joda.beans.PropertyDefinition;
import org.threeten.bp.LocalDate;
import org.threeten.bp.ZonedDateTime;

import com.opengamma.sesame.marketdata.MarketDataSource;
import com.opengamma.util.ArgumentChecker;

/**
 * <p>Simple immutable {@link Environment} implementation.
 * Functions should not create instances of this directly. If a function needs to modify the environment
 * before calling another function it should use the helper methods on the {@link Environment} interface, e.g.
 * {@link Environment#withValuationTime(ZonedDateTime)} etc.</p>
 *
 * <p>Instances should only be created in test cases or for passing to functions executing outside the engine.
 * If a function directly creates its own environments in a running engine it could dramatically affect performance
 * by preventing caching of shared values and forcing them to be recalculated every time they are used.</p>
 */
public final class SimpleEnvironment implements Environment {

  /** The valuation time. */
  @PropertyDefinition(validate = "notNull")
  private final ZonedDateTime _valuationTime;

  /** The function that provides market data. */
  @PropertyDefinition(validate = "notNull")
  private final MarketDataSource _marketDataSource;

  @Override
  public LocalDate getValuationDate() {
    return _valuationTime.toLocalDate();
  }

  @Override
  public ZonedDateTime getValuationTime() {
    return _valuationTime;
  }

  @Override
  public MarketDataSource getMarketDataSource() {
    return _marketDataSource;
  }

  @Override
  public Environment withValuationTime(ZonedDateTime valuationTime) {
    return new SimpleEnvironment(ArgumentChecker.notNull(valuationTime, "valuationTime"), _marketDataSource);
  }

  @Override
  public Environment withMarketData(MarketDataSource marketData) {
    return new SimpleEnvironment(_valuationTime, ArgumentChecker.notNull(marketData, "marketData"));
  }

  @Override
  public Environment with(ZonedDateTime valuationTime, MarketDataSource marketData) {
    return new SimpleEnvironment(valuationTime, marketData);
  }

  public SimpleEnvironment(ZonedDateTime valuationTime, MarketDataSource marketDataSource) {
    _valuationTime = ArgumentChecker.notNull(valuationTime, "valuationTime");
    _marketDataSource = ArgumentChecker.notNull(marketDataSource, "marketDataSource");
  }

  @Override
  public int hashCode() {
    return Objects.hash(_valuationTime, _marketDataSource);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    SimpleEnvironment other = (SimpleEnvironment) obj;
    return
        Objects.equals(this._valuationTime, other._valuationTime) &&
        Objects.equals(this._marketDataSource, other._marketDataSource);
  }
}
