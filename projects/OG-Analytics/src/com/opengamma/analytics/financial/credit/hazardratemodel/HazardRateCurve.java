/**
 * Copyright (C) 2012 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.analytics.financial.credit.hazardratemodel;

import com.opengamma.OpenGammaRuntimeException;
import com.opengamma.analytics.financial.credit.cds.ISDAExtrapolator1D;
import com.opengamma.analytics.financial.credit.cds.ISDAInterpolator1D;
import com.opengamma.analytics.math.curve.ConstantDoublesCurve;
import com.opengamma.analytics.math.curve.DoublesCurve;
import com.opengamma.analytics.math.curve.InterpolatedDoublesCurve;
import com.opengamma.analytics.math.interpolation.CombinedInterpolatorExtrapolator;
import com.opengamma.analytics.math.interpolation.FlatExtrapolator1D;
import com.opengamma.util.ArgumentChecker;

/**
 * Class for constructing and querying a term structure of (calibrated) hazard rates
 * Partially adopted from the RiskCare implementation of the ISDA model
 */
public class HazardRateCurve {

  // ----------------------------------------------------------------------------------------------------------------------------------------

  //private final String _name;

  private final double _offset;

  private final DoublesCurve _curve;

  private final double[] _shiftedTimePoints;

  private final double _zeroDiscountFactor;

  // ----------------------------------------------------------------------------------------------------------------------------------------

  public HazardRateCurve(final double[] xData, final double[] yData, final double offset) {

    _offset = offset;

    // Choose interpolation/extrapolation to match the behaviour of curves in the ISDA CDS reference code
    if (xData.length > 1) {
      _curve = InterpolatedDoublesCurve.fromSorted(xData, yData,
          new CombinedInterpolatorExtrapolator(
              new ISDAInterpolator1D(),    // ISDA style interpolation
              new FlatExtrapolator1D(),    // Flat rate extrapolated to the left
              new ISDAExtrapolator1D()));  // ISDA style extrapolation to the right
    } else if (xData.length == 1) {
      _curve = ConstantDoublesCurve.from(yData[0]);  // Unless the curve is flat, in which case use a constant curve
    } else {
      throw new OpenGammaRuntimeException("Cannot construct a curve with no points");
    }

    _shiftedTimePoints = new double[xData.length];

    for (int i = 0; i < xData.length; ++i) {
      _shiftedTimePoints[i] = xData[i] + _offset;
    }

    _zeroDiscountFactor = Math.exp(_offset * getHazardRate(0.0));
  }

  // ----------------------------------------------------------------------------------------------------------------------------------------

  // Builder method to build a new SurvivalCurve object given the tenor and hazard rate inputs

  public HazardRateCurve bootstrapHelperHazardRateCurve(double[] tenorsAsDoubles, double[] hazardRates) {

    ArgumentChecker.notNull(tenorsAsDoubles, "Tenors as doubles field");
    ArgumentChecker.notNull(hazardRates, "Hazard rates field");

    HazardRateCurve modifiedHazardRateCurve = new HazardRateCurve(tenorsAsDoubles, hazardRates, 0.0);

    return modifiedHazardRateCurve;
  }

  // ----------------------------------------------------------------------------------------------------------------------------------------

  public double getOffset() {
    return _offset;
  }

  public DoublesCurve getCurve() {
    return _curve;
  }

  public double[] getShiftedTimePoints() {
    return _shiftedTimePoints;
  }

  public double getZeroDiscountFactor() {
    return _zeroDiscountFactor;
  }

  public double getHazardRate(final Double t) {
    return _curve.getYValue(t - _offset);
  }

  public double getSurvivalProbability(final double t) {
    return Math.exp((_offset - t) * getHazardRate(t)) / _zeroDiscountFactor;
  }

  // ----------------------------------------------------------------------------------------------------------------------------------------
}
