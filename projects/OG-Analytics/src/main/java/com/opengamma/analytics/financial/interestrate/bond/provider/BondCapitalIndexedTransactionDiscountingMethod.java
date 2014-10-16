/**
 * Copyright (C) 2011 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.analytics.financial.interestrate.bond.provider;

import org.apache.commons.lang.Validate;

import com.opengamma.analytics.financial.interestrate.bond.definition.BondCapitalIndexedTransaction;
import com.opengamma.analytics.financial.interestrate.payments.derivative.Coupon;
import com.opengamma.analytics.financial.provider.calculator.inflation.PresentValueCurveSensitivityDiscountingInflationCalculator;
import com.opengamma.analytics.financial.provider.calculator.inflation.PresentValueDiscountingInflationCalculator;
import com.opengamma.analytics.financial.provider.description.inflation.InflationIssuerProviderInterface;
import com.opengamma.analytics.financial.provider.description.inflation.ParameterInflationIssuerProviderInterface;
import com.opengamma.analytics.financial.provider.sensitivity.inflation.MultipleCurrencyInflationSensitivity;
import com.opengamma.util.money.MultipleCurrencyAmount;

/**
 * Pricing method for inflation bond transaction. The price is computed by index estimation and discounting.
 */
public final class BondCapitalIndexedTransactionDiscountingMethod {

  /**
   * The unique instance of the class.
   */
  private static final BondCapitalIndexedTransactionDiscountingMethod INSTANCE = new BondCapitalIndexedTransactionDiscountingMethod();

  /**
   * Return the class instance.
   * @return The instance.
   */
  public static BondCapitalIndexedTransactionDiscountingMethod getInstance() {
    return INSTANCE;
  }

  /**
   * The present value inflation calculator (for the different parts of the bond transaction).
   */
  private static final PresentValueDiscountingInflationCalculator PVIC = PresentValueDiscountingInflationCalculator.getInstance();
  private static final PresentValueCurveSensitivityDiscountingInflationCalculator PVCSIC = PresentValueCurveSensitivityDiscountingInflationCalculator.getInstance();
  /**
   * The method used for security computation.
   */
  private static final BondCapitalIndexedSecurityDiscountingMethod METHOD_SECURITY = 
      new BondCapitalIndexedSecurityDiscountingMethod();

  /**
   * Computes the present value of a capital indexed bond transaction by index estimation and discounting.
   * @param bond The bond transaction.
   * @param provider The provider.
   * @return The present value.
   */
  public MultipleCurrencyAmount presentValue(final BondCapitalIndexedTransaction<?> bond, final InflationIssuerProviderInterface provider) {
    final MultipleCurrencyAmount pvBond = METHOD_SECURITY.presentValue(bond.getBondTransaction(), provider);
    double notional = bond.getNotionalStandard();
    final MultipleCurrencyAmount pvSettlement = bond.getBondTransaction().getSettlement().
        accept(PVIC, provider.getInflationProvider()).multipliedBy(-bond.getQuantity() * (bond.getTransactionPrice() 
            + bond.getBondTransaction().getAccruedInterest() / notional));
    return pvBond.multipliedBy(bond.getQuantity()).plus(pvSettlement);
  }

  /**
   * Computes the security present value from a quoted clean real price.
   * @param bond The bond transaction.
   * @param provider The provider.
   * @param cleanPriceReal The clean price.
   * @return The present value.
   */
  public MultipleCurrencyAmount presentValueFromCleanPriceReal(final BondCapitalIndexedTransaction<Coupon> bond, final InflationIssuerProviderInterface provider, final double cleanPriceReal) {
    Validate.notNull(bond, "Coupon");
    Validate.notNull(provider, "Provider");
    final MultipleCurrencyAmount pvBond = METHOD_SECURITY.presentValueFromCleanRealPrice(bond.getBondTransaction(), provider, cleanPriceReal);
    final MultipleCurrencyAmount pvSettlement = bond.getBondTransaction().getSettlement().accept(PVIC, provider.getInflationProvider()).multipliedBy(
        bond.getQuantity() * bond.getBondTransaction().getCoupon().getNthPayment(0).getNotional());
    return pvBond.plus(pvSettlement);
  }
  
  /**
   * Computes the present value of a capital indexed bond transaction from the real yield (and the curves for the
   * discounting from spot).
   * @param bond The bond transaction.
   * @param provider The provider.
   * @param realYield The real yield.
   * @return The present value.
   */
  public MultipleCurrencyAmount presentValueFromRealYield(final BondCapitalIndexedTransaction<Coupon> bond, 
      final InflationIssuerProviderInterface provider, final double realYield) {
    Validate.notNull(bond, "Coupon");
    Validate.notNull(provider, "Provider");
    // TODO: Write relevant code. Only for API
    return null;
  }

  
  /**
   * Computes the present value of a capital indexed bond transaction from the real yield and the inflation
   * assumption (and the curves for the discounting from spot). In particular used for Brazilian inflation bonds
   * where a inflation assumption is used between the last published price index and the settlement date.
   * @param bond The bond transaction.
   * @param provider The provider.
   * @param realYield The real yield.
   * @param inflationAssumption The inflation assumption.
   * @return The present value.
   */
  public MultipleCurrencyAmount presentValueFromRealYield(final BondCapitalIndexedTransaction<Coupon> bond, 
      final InflationIssuerProviderInterface provider, final double realYield, double inflationAssumption) {
    Validate.notNull(bond, "Coupon");
    Validate.notNull(provider, "Provider");
    // TODO: Write relevant code. Only for API
    return null;
  }
  
  /**
   * Computes the present value of a capital indexed bond transaction from the dirty nominal note price (and the 
   * curves for the discounting from spot). In particular used for Brazilian inflation bonds for which the standard 
   * quoting mechanism is using dirty nominal note price.
   * @param bond The bond transaction.
   * @param provider The provider.
   * @param dirtyNotePriceNominal The dirty notional note price.
   * @return The present value.
   */
  public MultipleCurrencyAmount presentValueFromDirtyNominalNotePrice(final BondCapitalIndexedTransaction<Coupon> bond, 
      final InflationIssuerProviderInterface provider, final double dirtyNotePriceNominal) {
    Validate.notNull(bond, "Coupon");
    Validate.notNull(provider, "Provider");
    // TODO: Write relevant code. Only for API
    return null;
  }

  /**
   * Computes the present value of a capital indexed bond transaction by index estimation and discounting.
   * @param bond The bond transaction.
   * @param provider The provider.
   * @return The present value.
   */
  public MultipleCurrencyInflationSensitivity presentValueCurveSensitivity(final BondCapitalIndexedTransaction<?> bond, 
      final ParameterInflationIssuerProviderInterface provider) {
    final MultipleCurrencyInflationSensitivity sensitivityBond = METHOD_SECURITY.presentValueCurveSensitivity(bond.getBondTransaction(), provider);
    final MultipleCurrencyInflationSensitivity sensitivitySettlement = bond.getBondTransaction().getSettlement().
        accept(PVCSIC, provider.getInflationProvider()).multipliedBy(-bond.getQuantity() * bond.getTransactionPrice());
    return sensitivityBond.multipliedBy(bond.getQuantity()).plus(sensitivitySettlement);
  }

}
