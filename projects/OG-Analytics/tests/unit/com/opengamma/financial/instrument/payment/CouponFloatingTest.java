/**
 * Copyright (C) 2009 - 2011 by OpenGamma Inc.
 *
 * Please see distribution for license.
 */
package com.opengamma.financial.instrument.payment;

import static org.junit.Assert.assertEquals;

import javax.time.calendar.ZonedDateTime;

import org.junit.Test;

import com.opengamma.financial.convention.daycount.DayCount;
import com.opengamma.financial.convention.daycount.DayCountFactory;
import com.opengamma.util.time.DateUtil;

/**
 * 
 */
public class CouponFloatingTest {

  private static final ZonedDateTime PAYMENT_DATE = DateUtil.getUTCDate(2011, 4, 6);
  private static final ZonedDateTime FIXING_DATE = DateUtil.getUTCDate(2011, 1, 3);
  private static final ZonedDateTime ACCRUAL_START_DATE = DateUtil.getUTCDate(2011, 1, 5);
  private static final ZonedDateTime ACCRUAL_END_DATE = DateUtil.getUTCDate(2011, 4, 5);
  private static final DayCount DAY_COUNT = DayCountFactory.INSTANCE.getDayCount("Actual/360");
  private static final double ACCRUAL_FACTOR = DAY_COUNT.getDayCountFraction(ACCRUAL_START_DATE, ACCRUAL_END_DATE);
  private static final double NOTIONAL = 1000000; //1m

  private static final CouponDefinition COUPON = new CouponDefinition(PAYMENT_DATE, ACCRUAL_START_DATE, ACCRUAL_END_DATE, ACCRUAL_FACTOR, NOTIONAL);
  private static final CouponFloatingDefinition FLOAT_COUPON = new CouponFloatingDefinition(PAYMENT_DATE, ACCRUAL_START_DATE, ACCRUAL_END_DATE, ACCRUAL_FACTOR, NOTIONAL, FIXING_DATE);
  private static final CouponFloatingDefinition FLOAT_COUPON_2 = CouponFloatingDefinition.from(COUPON, FIXING_DATE);

  @Test(expected = IllegalArgumentException.class)
  public void testNullPaymentDate() {
    new CouponFloatingDefinition(null, ACCRUAL_START_DATE, ACCRUAL_END_DATE, ACCRUAL_FACTOR, NOTIONAL, FIXING_DATE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullFixingDate() {
    new CouponFloatingDefinition(PAYMENT_DATE, ACCRUAL_START_DATE, ACCRUAL_END_DATE, ACCRUAL_FACTOR, NOTIONAL, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullCoupon() {
    CouponFloatingDefinition.from(null, FIXING_DATE);
  }

  @Test
  public void test() {
    assertEquals(FLOAT_COUPON.getPaymentDate(), COUPON.getPaymentDate());
    assertEquals(FLOAT_COUPON.getAccrualStartDate(), COUPON.getAccrualStartDate());
    assertEquals(FLOAT_COUPON.getAccrualEndDate(), COUPON.getAccrualEndDate());
    assertEquals(FLOAT_COUPON.getAccrualFactor(), COUPON.getAccrualFactor(), 1E-10);
    assertEquals(FLOAT_COUPON.getNotional(), COUPON.getNotional(), 1E-2);
    assertEquals(FLOAT_COUPON.getFixingDate(), FIXING_DATE);
    assertEquals(FLOAT_COUPON.isFixed(), false);
    assertEquals(FLOAT_COUPON_2.getPaymentDate(), COUPON.getPaymentDate());
    assertEquals(FLOAT_COUPON_2.getAccrualStartDate(), COUPON.getAccrualStartDate());
    assertEquals(FLOAT_COUPON_2.getAccrualEndDate(), COUPON.getAccrualEndDate());
    assertEquals(FLOAT_COUPON_2.getAccrualFactor(), COUPON.getAccrualFactor(), 1E-10);
    assertEquals(FLOAT_COUPON_2.getNotional(), COUPON.getNotional(), 1E-2);
    assertEquals(FLOAT_COUPON_2.getFixingDate(), FIXING_DATE);
    assertEquals(FLOAT_COUPON_2.isFixed(), false);
  }

  @Test
  public void testFixingProcess() {
    CouponFloatingDefinition CouponWithReset = CouponFloatingDefinition.from(COUPON, FIXING_DATE);
    double RESET_RATE = 0.04;
    assertEquals(CouponWithReset.isFixed(), false);
    CouponWithReset.fixingProcess(RESET_RATE);
    assertEquals(CouponWithReset.isFixed(), true);
    assertEquals(CouponWithReset.getFixedRate(), RESET_RATE, 1E-10);

  }

}
