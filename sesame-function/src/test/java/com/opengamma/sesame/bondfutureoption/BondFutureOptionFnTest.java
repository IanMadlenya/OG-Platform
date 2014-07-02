/**
 * Copyright (C) 2014 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.sesame.bondfutureoption;

import static com.opengamma.sesame.config.ConfigBuilder.argument;
import static com.opengamma.sesame.config.ConfigBuilder.arguments;
import static com.opengamma.sesame.config.ConfigBuilder.config;
import static com.opengamma.sesame.config.ConfigBuilder.function;
import static com.opengamma.sesame.config.ConfigBuilder.implementations;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.testng.AssertJUnit.fail;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.threeten.bp.Instant;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;
import org.threeten.bp.OffsetTime;
import org.threeten.bp.Period;
import org.threeten.bp.ZoneOffset;
import org.threeten.bp.ZonedDateTime;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.opengamma.core.historicaltimeseries.HistoricalTimeSeries;
import com.opengamma.core.historicaltimeseries.HistoricalTimeSeriesSource;
import com.opengamma.core.historicaltimeseries.impl.SimpleHistoricalTimeSeries;
import com.opengamma.core.id.ExternalSchemes;
import com.opengamma.core.link.ConfigLink;
import com.opengamma.core.position.Counterparty;
import com.opengamma.core.position.impl.SimpleCounterparty;
import com.opengamma.core.position.impl.SimpleTrade;
import com.opengamma.core.security.SecuritySource;
import com.opengamma.core.value.MarketDataRequirementNames;
import com.opengamma.financial.analytics.curve.ConfigDBCurveConstructionConfigurationSource;
import com.opengamma.financial.analytics.curve.CurveConstructionConfigurationSource;
import com.opengamma.financial.analytics.curve.exposure.ExposureFunctions;
import com.opengamma.financial.convention.daycount.DayCount;
import com.opengamma.financial.convention.daycount.DayCounts;
import com.opengamma.financial.convention.frequency.Frequency;
import com.opengamma.financial.convention.frequency.PeriodFrequency;
import com.opengamma.financial.convention.yield.SimpleYieldConvention;
import com.opengamma.financial.convention.yield.YieldConvention;
import com.opengamma.financial.security.bond.BondSecurity;
import com.opengamma.financial.security.bond.GovernmentBondSecurity;
import com.opengamma.financial.security.future.BondFutureDeliverable;
import com.opengamma.financial.security.future.BondFutureSecurity;
import com.opengamma.financial.security.option.BondFutureOptionSecurity;
import com.opengamma.financial.security.option.EuropeanExerciseType;
import com.opengamma.financial.security.option.ExerciseType;
import com.opengamma.financial.security.option.OptionType;
import com.opengamma.id.ExternalId;
import com.opengamma.id.ExternalIdBundle;
import com.opengamma.id.UniqueId;
import com.opengamma.service.ServiceContext;
import com.opengamma.service.ThreadLocalServiceContext;
import com.opengamma.service.VersionCorrectionProvider;
import com.opengamma.sesame.ConfigDbMarketExposureSelectorFn;
import com.opengamma.sesame.CurveDefinitionFn;
import com.opengamma.sesame.CurveSpecificationFn;
import com.opengamma.sesame.CurveSpecificationMarketDataFn;
import com.opengamma.sesame.DefaultCurveDefinitionFn;
import com.opengamma.sesame.DefaultCurveSpecificationFn;
import com.opengamma.sesame.DefaultCurveSpecificationMarketDataFn;
import com.opengamma.sesame.DefaultDiscountingMulticurveBundleFn;
import com.opengamma.sesame.DefaultFXMatrixFn;
import com.opengamma.sesame.DefaultHistoricalTimeSeriesFn;
import com.opengamma.sesame.DiscountingMulticurveBundleFn;
import com.opengamma.sesame.DiscountingMulticurveCombinerFn;
import com.opengamma.sesame.Environment;
import com.opengamma.sesame.ExposureFunctionsDiscountingMulticurveCombinerFn;
import com.opengamma.sesame.ExposureFunctionsIssuerProviderFn;
import com.opengamma.sesame.FXMatrixFn;
import com.opengamma.sesame.HistoricalTimeSeriesFn;
import com.opengamma.sesame.InterpolatedIssuerBundleFn;
import com.opengamma.sesame.IssuerProviderBundleFn;
import com.opengamma.sesame.IssuerProviderFn;
import com.opengamma.sesame.MarketExposureSelectorFn;
import com.opengamma.sesame.RootFinderConfiguration;
import com.opengamma.sesame.SimpleEnvironment;
import com.opengamma.sesame.bond.BondMockSources;
import com.opengamma.sesame.component.RetrievalPeriod;
import com.opengamma.sesame.component.StringSet;
import com.opengamma.sesame.config.FunctionModelConfig;
import com.opengamma.sesame.engine.ComponentMap;
import com.opengamma.sesame.engine.FixedInstantVersionCorrectionProvider;
import com.opengamma.sesame.graph.FunctionModel;
import com.opengamma.sesame.marketdata.DefaultMarketDataFn;
import com.opengamma.sesame.marketdata.MarketDataFn;
import com.opengamma.sesame.trade.BondFutureOptionTrade;
import com.opengamma.timeseries.date.localdate.ImmutableLocalDateDoubleTimeSeries;
import com.opengamma.util.money.Currency;
import com.opengamma.util.money.MultipleCurrencyAmount;
import com.opengamma.util.result.Result;
import com.opengamma.util.test.TestGroup;
import com.opengamma.util.time.DateUtils;
import com.opengamma.util.time.Expiry;

/**
 * Test for bond future options using the black calculator.
 */
@Test(groups = TestGroup.UNIT)
public class BondFutureOptionFnTest {
  
  private static final BondMockSources _bondMockSources = new BondMockSources();
  
  private static final ZonedDateTime VALUATION_TIME = DateUtils.getUTCDate(2014, 1, 22);
  
  private static final Environment ENV =
      new SimpleEnvironment(VALUATION_TIME,
                            _bondMockSources.createMarketDataSource());
  
  private BondFutureOptionFn _bondFutureOptionFn;
  
  private BondSecurity _bond = createBondSecurity();
  
  private BondFutureSecurity _bondFuture = createBondFuture();
  
  private BondFutureOptionTrade _bondFutureOptionTrade = createBondFutureOptionTrade();

  @BeforeClass
  public void setUp() {
    FunctionModelConfig config = config(
                                        arguments(
                                                  function(ConfigDbMarketExposureSelectorFn.class,
                                                           argument("exposureConfig", ConfigLink.resolvable(BondMockSources.BOND_EXPOSURE_FUNCTIONS, ExposureFunctions.class))),
                                                  function(RootFinderConfiguration.class,
                                                           argument("rootFinderAbsoluteTolerance", 1e-9),
                                                           argument("rootFinderRelativeTolerance", 1e-9),
                                                           argument("rootFinderMaxIterations", 1000)),
                                                  function(DefaultDiscountingMulticurveBundleFn.class,
                                                           argument("impliedCurveNames", StringSet.of())),
                                                  function(DefaultHistoricalTimeSeriesFn.class,
                                                           argument("resolutionKey", "DEFAULT_TSS"),
                                                           argument("htsRetrievalPeriod", RetrievalPeriod.of(Period.ofYears(1)))
                                                  )
                                        ),
                                        implementations(BondFutureOptionFn.class, DefaultBondFutureOptionFn.class,
                                                        BondFutureOptionCalculatorFactory.class, BondFutureOptionBlackCalculatorFactory.class,
                                                        CurveSpecificationMarketDataFn.class, DefaultCurveSpecificationMarketDataFn.class,
                                                        FXMatrixFn.class, DefaultFXMatrixFn.class,
                                                        BlackBondFuturesProviderFn.class, TestBlackBondFuturesProviderFn.class,
                                                        DiscountingMulticurveCombinerFn.class, ExposureFunctionsDiscountingMulticurveCombinerFn.class,
                                                        IssuerProviderFn.class, ExposureFunctionsIssuerProviderFn.class,
                                                        IssuerProviderBundleFn.class, InterpolatedIssuerBundleFn.class,
                                                        CurveDefinitionFn.class, DefaultCurveDefinitionFn.class,
                                                        DiscountingMulticurveBundleFn.class, DefaultDiscountingMulticurveBundleFn.class,
                                                        CurveSpecificationFn.class, DefaultCurveSpecificationFn.class,
                                                        CurveConstructionConfigurationSource.class, ConfigDBCurveConstructionConfigurationSource.class,
                                                        HistoricalTimeSeriesFn.class, DefaultHistoricalTimeSeriesFn.class,
                                                        MarketExposureSelectorFn.class, ConfigDbMarketExposureSelectorFn.class,
                                                        MarketDataFn.class, DefaultMarketDataFn.class)
    );

    ImmutableMap<Class<?>, Object> components = generateComponents();
    VersionCorrectionProvider vcProvider = new FixedInstantVersionCorrectionProvider(Instant.now());
    ServiceContext serviceContext = ServiceContext.of(components).with(VersionCorrectionProvider.class, vcProvider);
    ThreadLocalServiceContext.init(serviceContext);
    
    _bondFutureOptionFn = FunctionModel.build(BondFutureOptionFn.class, config, ComponentMap.of(components));
  }
  
  private ImmutableMap<Class<?>, Object> generateComponents() {
    ImmutableMap.Builder<Class<?>, Object> builder = ImmutableMap.builder();
    for (Map.Entry<Class<?>, Object> keys: _bondMockSources.generateBaseComponents().entrySet()) {
      if (keys.getKey().equals(HistoricalTimeSeriesSource.class)) {
        appendHistoricalTimeSeriesSourceMock((HistoricalTimeSeriesSource) keys.getValue());
      }
      if (keys.getKey().equals(SecuritySource.class)) {
        appendSecuritySourceMock((SecuritySource) keys.getValue());
      }
      builder.put(keys.getKey(), keys.getValue());
    }
    ImmutableMap<Class<?>, Object> components = builder.build();
    return components;
  }
  
  private void appendHistoricalTimeSeriesSourceMock(HistoricalTimeSeriesSource mock) {
    HistoricalTimeSeries irFuturePrices = new SimpleHistoricalTimeSeries(UniqueId.of("Blah", "1"), ImmutableLocalDateDoubleTimeSeries.of(VALUATION_TIME.toLocalDate(), 0.975));
    when(mock.getHistoricalTimeSeries(eq(MarketDataRequirementNames.MARKET_VALUE),
                                      any(ExternalIdBundle.class),
                                      eq("DEFAULT_TSS"),
                                      any(LocalDate.class),
                                      eq(true),
                                      any(LocalDate.class),
                                      eq(true))).thenReturn(irFuturePrices);
  }
  
  private void appendSecuritySourceMock(SecuritySource mock) {
    when(mock.getSingle(eq(_bond.getExternalIdBundle()))).thenReturn(_bond);
    when(mock.getSingle(eq(_bondFuture.getExternalIdBundle()))).thenReturn(_bondFuture);
  }
  
  private BondSecurity createBondSecurity() {
    
    String issuerName = BondMockSources.BOND_ISSUER_KEY;
    String issuerDomicile = "US";
    String issuerType = "Sovereign";
    ZonedDateTime effectiveDate = DateUtils.getUTCDate(2014, 6, 18);
    ZonedDateTime maturityDate = DateUtils.getUTCDate(2015, 6, 18);
    Currency currency = Currency.USD;
    YieldConvention yieldConvention = SimpleYieldConvention.US_TREASURY_EQUIVALANT;
    Expiry lastTradeDate = new Expiry(maturityDate);
    String couponType = "Fixed";
    double couponRate = 0.02;
    Period couponPeriod = Period.parse("P6M");
    Frequency couponFrequency = PeriodFrequency.of(couponPeriod);
    DayCount dayCountConvention = DayCounts.ACT_ACT_ICMA;
    ZonedDateTime firstCouponDate = effectiveDate;
    ZonedDateTime interestAccrualDate = effectiveDate.minus(couponPeriod);
    ZonedDateTime settlementDate = maturityDate; // assume 0 day settlement lag
    Double issuancePrice = null;
    double totalAmountIssued = 100_000_000;
    double minimumAmount = 1;
    double minimumIncrement = 1;
    double parAmount = 100;
    double redemptionValue = 100;
    
    GovernmentBondSecurity bond = new GovernmentBondSecurity(issuerName,
                                                             issuerType,
                                                             issuerDomicile, 
                                                             issuerType, // market
                                                             currency,
                                                             yieldConvention,
                                                             lastTradeDate,
                                                             couponType,
                                                             couponRate,
                                                             couponFrequency,
                                                             dayCountConvention,
                                                             interestAccrualDate, 
                                                             settlementDate, 
                                                             firstCouponDate,
                                                             issuancePrice,
                                                             totalAmountIssued,
                                                             minimumAmount,
                                                             minimumIncrement,
                                                             parAmount,
                                                             redemptionValue);

    // Need this for time series lookup
    ExternalId bondId = ExternalSchemes.syntheticSecurityId("Test bond");
    bond.setExternalIdBundle(bondId.toBundle());
    return bond;
  }
  
  private BondFutureSecurity createBondFuture() {
    
    Currency currency = Currency.USD;

    ZonedDateTime deliveryDate = DateUtils.getUTCDate(2014, 6, 18);
    Expiry expiry = new Expiry(deliveryDate);
    String tradingExchange = "";
    String settlementExchange = "";
    double unitAmount = 1;
    Collection<BondFutureDeliverable> basket = new ArrayList<>();
    BondFutureDeliverable bondFutureDeliverable = new BondFutureDeliverable(_bond.getExternalIdBundle(), 0.9);
    basket.add(bondFutureDeliverable);
    
    ZonedDateTime firstDeliveryDate = deliveryDate;
    ZonedDateTime lastDeliveryDate = deliveryDate;
    String category = "test";
    
    BondFutureSecurity bondFuture = new BondFutureSecurity(expiry, tradingExchange, settlementExchange, currency, unitAmount, basket, firstDeliveryDate, lastDeliveryDate, category);
    bondFuture.setExternalIdBundle(ExternalSchemes.syntheticSecurityId("Test bond future").toBundle());
    return bondFuture;
  }
  
  private BondFutureOptionTrade createBondFutureOptionTrade() {
    String tradingExchange = "";
    String settlementExchange = "";
    Expiry expiry = _bondFuture.getExpiry();
    ExerciseType exerciseType = new EuropeanExerciseType();
    ExternalId underlyingId = Iterables.getOnlyElement(_bondFuture.getExternalIdBundle());
    double pointValue = Double.NaN;
    Currency currency = _bondFuture.getCurrency();
    double strike = 0.2;
    OptionType optionType = OptionType.PUT;
    boolean margined = true;
    BondFutureOptionSecurity option = new BondFutureOptionSecurity(tradingExchange, settlementExchange, expiry, exerciseType, underlyingId, pointValue, margined, currency, strike, optionType);
    option.setExternalIdBundle(ExternalSchemes.syntheticSecurityId("Test bond future option").toBundle());
    
    Counterparty counterparty = new SimpleCounterparty(ExternalId.of(Counterparty.DEFAULT_SCHEME, "COUNTERPARTY"));
    BigDecimal tradeQuantity = BigDecimal.valueOf(10);
    LocalDate tradeDate = LocalDate.of(2000, 1, 1);
    OffsetTime tradeTime = OffsetTime.of(LocalTime.of(0, 0), ZoneOffset.UTC);
    SimpleTrade trade = new SimpleTrade(option, tradeQuantity, counterparty, tradeDate, tradeTime);
    trade.setPremium(10.0);
    trade.setPremiumCurrency(Currency.USD);
    return new BondFutureOptionTrade(trade);
  }
  
  @Test
  public void testPresentValue() {
    Result<MultipleCurrencyAmount> pvComputed = _bondFutureOptionFn.calculatePV(ENV, _bondFutureOptionTrade);
    if (!pvComputed.isSuccess()) {
      fail(pvComputed.getFailureMessage());
    }
  }
}
