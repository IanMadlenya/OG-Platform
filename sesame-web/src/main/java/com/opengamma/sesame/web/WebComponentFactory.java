/**
 * Copyright (C) 2015 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.sesame.web;

import com.opengamma.component.ComponentRepository;
import com.opengamma.component.factory.AbstractComponentFactory;
import com.opengamma.sesame.component.ViewRunnerComponentFactory;
import com.opengamma.sesame.marketdata.builders.MarketDataEnvironmentFactory;
import com.opengamma.sesame.web.curves.CurveBundleResource;
import com.opengamma.sesame.web.curves.SwapPricingResource;
import org.joda.beans.Bean;
import org.joda.beans.BeanBuilder;
import org.joda.beans.BeanDefinition;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Web component to register resources to expose REST endpoints
 */
@BeanDefinition
public class WebComponentFactory extends AbstractComponentFactory {

  /** Builds market data that's required by the calculations but not supplied by the user. */
  @PropertyDefinition(validate = "notNull")
  private MarketDataEnvironmentFactory _marketDataEnvironmentFactory;

  @PropertyDefinition(validate = "notNull")
  private ViewRunnerComponentFactory _viewRunnerComponentFactory;

  @Override
  public void init(ComponentRepository repo, LinkedHashMap<String, String> configuration) throws Exception {
    repo.getRestComponents().publishResource(new SwapPricingResource(_marketDataEnvironmentFactory, _viewRunnerComponentFactory));
    repo.getRestComponents().publishResource(new CurveBundleResource(_marketDataEnvironmentFactory));
  }
  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code WebComponentFactory}.
   * @return the meta-bean, not null
   */
  public static WebComponentFactory.Meta meta() {
    return WebComponentFactory.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(WebComponentFactory.Meta.INSTANCE);
  }

  @Override
  public WebComponentFactory.Meta metaBean() {
    return WebComponentFactory.Meta.INSTANCE;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets builds market data that's required by the calculations but not supplied by the user.
   * @return the value of the property, not null
   */
  public MarketDataEnvironmentFactory getMarketDataEnvironmentFactory() {
    return _marketDataEnvironmentFactory;
  }

  /**
   * Sets builds market data that's required by the calculations but not supplied by the user.
   * @param marketDataEnvironmentFactory  the new value of the property, not null
   */
  public void setMarketDataEnvironmentFactory(MarketDataEnvironmentFactory marketDataEnvironmentFactory) {
    JodaBeanUtils.notNull(marketDataEnvironmentFactory, "marketDataEnvironmentFactory");
    this._marketDataEnvironmentFactory = marketDataEnvironmentFactory;
  }

  /**
   * Gets the the {@code marketDataEnvironmentFactory} property.
   * @return the property, not null
   */
  public final Property<MarketDataEnvironmentFactory> marketDataEnvironmentFactory() {
    return metaBean().marketDataEnvironmentFactory().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the viewRunnerComponentFactory.
   * @return the value of the property, not null
   */
  public ViewRunnerComponentFactory getViewRunnerComponentFactory() {
    return _viewRunnerComponentFactory;
  }

  /**
   * Sets the viewRunnerComponentFactory.
   * @param viewRunnerComponentFactory  the new value of the property, not null
   */
  public void setViewRunnerComponentFactory(ViewRunnerComponentFactory viewRunnerComponentFactory) {
    JodaBeanUtils.notNull(viewRunnerComponentFactory, "viewRunnerComponentFactory");
    this._viewRunnerComponentFactory = viewRunnerComponentFactory;
  }

  /**
   * Gets the the {@code viewRunnerComponentFactory} property.
   * @return the property, not null
   */
  public final Property<ViewRunnerComponentFactory> viewRunnerComponentFactory() {
    return metaBean().viewRunnerComponentFactory().createProperty(this);
  }

  //-----------------------------------------------------------------------
  @Override
  public WebComponentFactory clone() {
    return JodaBeanUtils.cloneAlways(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      WebComponentFactory other = (WebComponentFactory) obj;
      return JodaBeanUtils.equal(getMarketDataEnvironmentFactory(), other.getMarketDataEnvironmentFactory()) &&
          JodaBeanUtils.equal(getViewRunnerComponentFactory(), other.getViewRunnerComponentFactory()) &&
          super.equals(obj);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = hash * 31 + JodaBeanUtils.hashCode(getMarketDataEnvironmentFactory());
    hash = hash * 31 + JodaBeanUtils.hashCode(getViewRunnerComponentFactory());
    return hash ^ super.hashCode();
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(96);
    buf.append("WebComponentFactory{");
    int len = buf.length();
    toString(buf);
    if (buf.length() > len) {
      buf.setLength(buf.length() - 2);
    }
    buf.append('}');
    return buf.toString();
  }

  @Override
  protected void toString(StringBuilder buf) {
    super.toString(buf);
    buf.append("marketDataEnvironmentFactory").append('=').append(JodaBeanUtils.toString(getMarketDataEnvironmentFactory())).append(',').append(' ');
    buf.append("viewRunnerComponentFactory").append('=').append(JodaBeanUtils.toString(getViewRunnerComponentFactory())).append(',').append(' ');
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code WebComponentFactory}.
   */
  public static class Meta extends AbstractComponentFactory.Meta {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code marketDataEnvironmentFactory} property.
     */
    private final MetaProperty<MarketDataEnvironmentFactory> _marketDataEnvironmentFactory = DirectMetaProperty.ofReadWrite(
        this, "marketDataEnvironmentFactory", WebComponentFactory.class, MarketDataEnvironmentFactory.class);
    /**
     * The meta-property for the {@code viewRunnerComponentFactory} property.
     */
    private final MetaProperty<ViewRunnerComponentFactory> _viewRunnerComponentFactory = DirectMetaProperty.ofReadWrite(
        this, "viewRunnerComponentFactory", WebComponentFactory.class, ViewRunnerComponentFactory.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, (DirectMetaPropertyMap) super.metaPropertyMap(),
        "marketDataEnvironmentFactory",
        "viewRunnerComponentFactory");

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 964996125:  // marketDataEnvironmentFactory
          return _marketDataEnvironmentFactory;
        case 1396929954:  // viewRunnerComponentFactory
          return _viewRunnerComponentFactory;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends WebComponentFactory> builder() {
      return new DirectBeanBuilder<WebComponentFactory>(new WebComponentFactory());
    }

    @Override
    public Class<? extends WebComponentFactory> beanType() {
      return WebComponentFactory.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code marketDataEnvironmentFactory} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<MarketDataEnvironmentFactory> marketDataEnvironmentFactory() {
      return _marketDataEnvironmentFactory;
    }

    /**
     * The meta-property for the {@code viewRunnerComponentFactory} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<ViewRunnerComponentFactory> viewRunnerComponentFactory() {
      return _viewRunnerComponentFactory;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 964996125:  // marketDataEnvironmentFactory
          return ((WebComponentFactory) bean).getMarketDataEnvironmentFactory();
        case 1396929954:  // viewRunnerComponentFactory
          return ((WebComponentFactory) bean).getViewRunnerComponentFactory();
      }
      return super.propertyGet(bean, propertyName, quiet);
    }

    @Override
    protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 964996125:  // marketDataEnvironmentFactory
          ((WebComponentFactory) bean).setMarketDataEnvironmentFactory((MarketDataEnvironmentFactory) newValue);
          return;
        case 1396929954:  // viewRunnerComponentFactory
          ((WebComponentFactory) bean).setViewRunnerComponentFactory((ViewRunnerComponentFactory) newValue);
          return;
      }
      super.propertySet(bean, propertyName, newValue, quiet);
    }

    @Override
    protected void validate(Bean bean) {
      JodaBeanUtils.notNull(((WebComponentFactory) bean)._marketDataEnvironmentFactory, "marketDataEnvironmentFactory");
      JodaBeanUtils.notNull(((WebComponentFactory) bean)._viewRunnerComponentFactory, "viewRunnerComponentFactory");
      super.validate(bean);
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
