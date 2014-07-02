/**
 * Copyright (C) 2014 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.sesame.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.joda.beans.Bean;
import org.joda.beans.BeanDefinition;
import org.joda.beans.ImmutableBean;
import org.joda.beans.ImmutableConstructor;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectFieldsBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;
import org.threeten.bp.ZonedDateTime;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.opengamma.id.ExternalIdBundle;
import com.opengamma.id.UniqueIdentifiable;
import com.opengamma.sesame.config.FunctionArguments;
import com.opengamma.sesame.config.ViewConfig;
import com.opengamma.sesame.marketdata.FieldName;
import com.opengamma.util.ArgumentChecker;
import com.opengamma.util.result.Result;
import com.opengamma.util.tuple.Pair;

/**
 * Holds all the inputs that were provided when a view
 * cycle was run.
 */
@BeanDefinition
public class ViewInputs implements ImmutableBean {

  @PropertyDefinition
  private final List<Object> _tradeInputs;
  @PropertyDefinition(validate = "notNull")
  private final ViewConfig _viewConfig;
  @PropertyDefinition(validate = "notNull")
  private final FunctionArguments _functionArguments;
  @PropertyDefinition(validate = "notNull")
  private final ZonedDateTime _valuationTime;
  @PropertyDefinition(validate = "notNull")
  private final Map<Class<?>, Object> _scenarioArguments;
  @PropertyDefinition(validate = "notNull")
  private final Map<ZonedDateTime, Map<Pair<ExternalIdBundle, FieldName>, Result<?>>> _marketData;
  @PropertyDefinition(validate = "notNull")
  private final Multimap<Class<?>, UniqueIdentifiable> _configData;

  /**
   * Create the view inputs associated with a cycle.
   *
   * @param tradeInputs the trades/securities used for the cycle
   * @param viewConfig the view config used for the cycle
   * @param functionArguments the function arguments use for the cycle
   * @param valuationTime the valuation time for the cycle
   * @param scenarioArguments the scenario arguments used for the cycle
   * @param marketData all the market data used for the cycle
   * @param configData all the other data (config, conventions etc)
   * used for the cycle
   */
  @ImmutableConstructor
  public ViewInputs(List<?> tradeInputs,
                    ViewConfig viewConfig, FunctionArguments functionArguments,
                    ZonedDateTime valuationTime,
                    Map<Class<?>, Object> scenarioArguments,
                    Map<ZonedDateTime, Map<Pair<ExternalIdBundle, FieldName>, Result<?>>> marketData,
                    Multimap<Class<?>, UniqueIdentifiable> configData) {

    // We have to copy to keep the type checking happy - if the input
    // was already an immutable list then this will be a no op
    _tradeInputs = ImmutableList.copyOf(ArgumentChecker.notNull(tradeInputs, "tradeInputs"));
    _viewConfig = ArgumentChecker.notNull(viewConfig, "viewConfig");
    _functionArguments = ArgumentChecker.notNull(functionArguments, "functionArguments");
    _valuationTime = ArgumentChecker.notNull(valuationTime, "valuationTime");
    _scenarioArguments = ImmutableMap.copyOf(ArgumentChecker.notNull(scenarioArguments, "scenarioArguments"));
    _marketData = ImmutableMap.copyOf(ArgumentChecker.notNull(marketData, "marketData"));
    _configData = ImmutableMultimap.copyOf(ArgumentChecker.notNull(configData, "configData"));
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code ViewInputs}.
   * @return the meta-bean, not null
   */
  public static ViewInputs.Meta meta() {
    return ViewInputs.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(ViewInputs.Meta.INSTANCE);
  }

  /**
   * Returns a builder used to create an instance of the bean.
   * @return the builder, not null
   */
  public static ViewInputs.Builder builder() {
    return new ViewInputs.Builder();
  }

  @Override
  public ViewInputs.Meta metaBean() {
    return ViewInputs.Meta.INSTANCE;
  }

  @Override
  public <R> Property<R> property(String propertyName) {
    return metaBean().<R>metaProperty(propertyName).createProperty(this);
  }

  @Override
  public Set<String> propertyNames() {
    return metaBean().metaPropertyMap().keySet();
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the tradeInputs.
   * @return the value of the property
   */
  public List<Object> getTradeInputs() {
    return _tradeInputs;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the viewConfig.
   * @return the value of the property, not null
   */
  public ViewConfig getViewConfig() {
    return _viewConfig;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the functionArguments.
   * @return the value of the property, not null
   */
  public FunctionArguments getFunctionArguments() {
    return _functionArguments;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the valuationTime.
   * @return the value of the property, not null
   */
  public ZonedDateTime getValuationTime() {
    return _valuationTime;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the scenarioArguments.
   * @return the value of the property, not null
   */
  public Map<Class<?>, Object> getScenarioArguments() {
    return _scenarioArguments;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the marketData.
   * @return the value of the property, not null
   */
  public Map<ZonedDateTime, Map<Pair<ExternalIdBundle, FieldName>, Result<?>>> getMarketData() {
    return _marketData;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the configData.
   * @return the value of the property, not null
   */
  public Multimap<Class<?>, UniqueIdentifiable> getConfigData() {
    return _configData;
  }

  //-----------------------------------------------------------------------
  /**
   * Returns a builder that allows this bean to be mutated.
   * @return the mutable builder, not null
   */
  public Builder toBuilder() {
    return new Builder(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      ViewInputs other = (ViewInputs) obj;
      return JodaBeanUtils.equal(getTradeInputs(), other.getTradeInputs()) &&
          JodaBeanUtils.equal(getViewConfig(), other.getViewConfig()) &&
          JodaBeanUtils.equal(getFunctionArguments(), other.getFunctionArguments()) &&
          JodaBeanUtils.equal(getValuationTime(), other.getValuationTime()) &&
          JodaBeanUtils.equal(getScenarioArguments(), other.getScenarioArguments()) &&
          JodaBeanUtils.equal(getMarketData(), other.getMarketData()) &&
          JodaBeanUtils.equal(getConfigData(), other.getConfigData());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash += hash * 31 + JodaBeanUtils.hashCode(getTradeInputs());
    hash += hash * 31 + JodaBeanUtils.hashCode(getViewConfig());
    hash += hash * 31 + JodaBeanUtils.hashCode(getFunctionArguments());
    hash += hash * 31 + JodaBeanUtils.hashCode(getValuationTime());
    hash += hash * 31 + JodaBeanUtils.hashCode(getScenarioArguments());
    hash += hash * 31 + JodaBeanUtils.hashCode(getMarketData());
    hash += hash * 31 + JodaBeanUtils.hashCode(getConfigData());
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(256);
    buf.append("ViewInputs{");
    int len = buf.length();
    toString(buf);
    if (buf.length() > len) {
      buf.setLength(buf.length() - 2);
    }
    buf.append('}');
    return buf.toString();
  }

  protected void toString(StringBuilder buf) {
    buf.append("tradeInputs").append('=').append(JodaBeanUtils.toString(getTradeInputs())).append(',').append(' ');
    buf.append("viewConfig").append('=').append(JodaBeanUtils.toString(getViewConfig())).append(',').append(' ');
    buf.append("functionArguments").append('=').append(JodaBeanUtils.toString(getFunctionArguments())).append(',').append(' ');
    buf.append("valuationTime").append('=').append(JodaBeanUtils.toString(getValuationTime())).append(',').append(' ');
    buf.append("scenarioArguments").append('=').append(JodaBeanUtils.toString(getScenarioArguments())).append(',').append(' ');
    buf.append("marketData").append('=').append(JodaBeanUtils.toString(getMarketData())).append(',').append(' ');
    buf.append("configData").append('=').append(JodaBeanUtils.toString(getConfigData())).append(',').append(' ');
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code ViewInputs}.
   */
  public static class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code tradeInputs} property.
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    private final MetaProperty<List<Object>> _tradeInputs = DirectMetaProperty.ofImmutable(
        this, "tradeInputs", ViewInputs.class, (Class) List.class);
    /**
     * The meta-property for the {@code viewConfig} property.
     */
    private final MetaProperty<ViewConfig> _viewConfig = DirectMetaProperty.ofImmutable(
        this, "viewConfig", ViewInputs.class, ViewConfig.class);
    /**
     * The meta-property for the {@code functionArguments} property.
     */
    private final MetaProperty<FunctionArguments> _functionArguments = DirectMetaProperty.ofImmutable(
        this, "functionArguments", ViewInputs.class, FunctionArguments.class);
    /**
     * The meta-property for the {@code valuationTime} property.
     */
    private final MetaProperty<ZonedDateTime> _valuationTime = DirectMetaProperty.ofImmutable(
        this, "valuationTime", ViewInputs.class, ZonedDateTime.class);
    /**
     * The meta-property for the {@code scenarioArguments} property.
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    private final MetaProperty<Map<Class<?>, Object>> _scenarioArguments = DirectMetaProperty.ofImmutable(
        this, "scenarioArguments", ViewInputs.class, (Class) Map.class);
    /**
     * The meta-property for the {@code marketData} property.
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    private final MetaProperty<Map<ZonedDateTime, Map<Pair<ExternalIdBundle, FieldName>, Result<?>>>> _marketData = DirectMetaProperty.ofImmutable(
        this, "marketData", ViewInputs.class, (Class) Map.class);
    /**
     * The meta-property for the {@code configData} property.
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    private final MetaProperty<Multimap<Class<?>, UniqueIdentifiable>> _configData = DirectMetaProperty.ofImmutable(
        this, "configData", ViewInputs.class, (Class) Multimap.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "tradeInputs",
        "viewConfig",
        "functionArguments",
        "valuationTime",
        "scenarioArguments",
        "marketData",
        "configData");

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 1675813997:  // tradeInputs
          return _tradeInputs;
        case 1970035271:  // viewConfig
          return _viewConfig;
        case -260573090:  // functionArguments
          return _functionArguments;
        case 113591406:  // valuationTime
          return _valuationTime;
        case -1055250522:  // scenarioArguments
          return _scenarioArguments;
        case 1116764678:  // marketData
          return _marketData;
        case 831026700:  // configData
          return _configData;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public ViewInputs.Builder builder() {
      return new ViewInputs.Builder();
    }

    @Override
    public Class<? extends ViewInputs> beanType() {
      return ViewInputs.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code tradeInputs} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<List<Object>> tradeInputs() {
      return _tradeInputs;
    }

    /**
     * The meta-property for the {@code viewConfig} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<ViewConfig> viewConfig() {
      return _viewConfig;
    }

    /**
     * The meta-property for the {@code functionArguments} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<FunctionArguments> functionArguments() {
      return _functionArguments;
    }

    /**
     * The meta-property for the {@code valuationTime} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<ZonedDateTime> valuationTime() {
      return _valuationTime;
    }

    /**
     * The meta-property for the {@code scenarioArguments} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Map<Class<?>, Object>> scenarioArguments() {
      return _scenarioArguments;
    }

    /**
     * The meta-property for the {@code marketData} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Map<ZonedDateTime, Map<Pair<ExternalIdBundle, FieldName>, Result<?>>>> marketData() {
      return _marketData;
    }

    /**
     * The meta-property for the {@code configData} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Multimap<Class<?>, UniqueIdentifiable>> configData() {
      return _configData;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 1675813997:  // tradeInputs
          return ((ViewInputs) bean).getTradeInputs();
        case 1970035271:  // viewConfig
          return ((ViewInputs) bean).getViewConfig();
        case -260573090:  // functionArguments
          return ((ViewInputs) bean).getFunctionArguments();
        case 113591406:  // valuationTime
          return ((ViewInputs) bean).getValuationTime();
        case -1055250522:  // scenarioArguments
          return ((ViewInputs) bean).getScenarioArguments();
        case 1116764678:  // marketData
          return ((ViewInputs) bean).getMarketData();
        case 831026700:  // configData
          return ((ViewInputs) bean).getConfigData();
      }
      return super.propertyGet(bean, propertyName, quiet);
    }

    @Override
    protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
      metaProperty(propertyName);
      if (quiet) {
        return;
      }
      throw new UnsupportedOperationException("Property cannot be written: " + propertyName);
    }

  }

  //-----------------------------------------------------------------------
  /**
   * The bean-builder for {@code ViewInputs}.
   */
  public static class Builder extends DirectFieldsBeanBuilder<ViewInputs> {

    private List<Object> _tradeInputs;
    private ViewConfig _viewConfig;
    private FunctionArguments _functionArguments;
    private ZonedDateTime _valuationTime;
    private Map<Class<?>, Object> _scenarioArguments = new HashMap<Class<?>, Object>();
    private Map<ZonedDateTime, Map<Pair<ExternalIdBundle, FieldName>, Result<?>>> _marketData = new HashMap<ZonedDateTime, Map<Pair<ExternalIdBundle, FieldName>, Result<?>>>();
    private Multimap<Class<?>, UniqueIdentifiable> _configData = ArrayListMultimap.create();

    /**
     * Restricted constructor.
     */
    protected Builder() {
    }

    /**
     * Restricted copy constructor.
     * @param beanToCopy  the bean to copy from, not null
     */
    protected Builder(ViewInputs beanToCopy) {
      this._tradeInputs = (beanToCopy.getTradeInputs() != null ? new ArrayList<Object>(beanToCopy.getTradeInputs()) : null);
      this._viewConfig = beanToCopy.getViewConfig();
      this._functionArguments = beanToCopy.getFunctionArguments();
      this._valuationTime = beanToCopy.getValuationTime();
      this._scenarioArguments = new HashMap<Class<?>, Object>(beanToCopy.getScenarioArguments());
      this._marketData = new HashMap<ZonedDateTime, Map<Pair<ExternalIdBundle, FieldName>, Result<?>>>(beanToCopy.getMarketData());
      this._configData = ArrayListMultimap.create(beanToCopy.getConfigData());
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
      switch (propertyName.hashCode()) {
        case 1675813997:  // tradeInputs
          return _tradeInputs;
        case 1970035271:  // viewConfig
          return _viewConfig;
        case -260573090:  // functionArguments
          return _functionArguments;
        case 113591406:  // valuationTime
          return _valuationTime;
        case -1055250522:  // scenarioArguments
          return _scenarioArguments;
        case 1116764678:  // marketData
          return _marketData;
        case 831026700:  // configData
          return _configData;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Builder set(String propertyName, Object newValue) {
      switch (propertyName.hashCode()) {
        case 1675813997:  // tradeInputs
          this._tradeInputs = (List<Object>) newValue;
          break;
        case 1970035271:  // viewConfig
          this._viewConfig = (ViewConfig) newValue;
          break;
        case -260573090:  // functionArguments
          this._functionArguments = (FunctionArguments) newValue;
          break;
        case 113591406:  // valuationTime
          this._valuationTime = (ZonedDateTime) newValue;
          break;
        case -1055250522:  // scenarioArguments
          this._scenarioArguments = (Map<Class<?>, Object>) newValue;
          break;
        case 1116764678:  // marketData
          this._marketData = (Map<ZonedDateTime, Map<Pair<ExternalIdBundle, FieldName>, Result<?>>>) newValue;
          break;
        case 831026700:  // configData
          this._configData = (Multimap<Class<?>, UniqueIdentifiable>) newValue;
          break;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
      return this;
    }

    @Override
    public Builder set(MetaProperty<?> property, Object value) {
      super.set(property, value);
      return this;
    }

    @Override
    public Builder setString(String propertyName, String value) {
      setString(meta().metaProperty(propertyName), value);
      return this;
    }

    @Override
    public Builder setString(MetaProperty<?> property, String value) {
      super.set(property, value);
      return this;
    }

    @Override
    public Builder setAll(Map<String, ? extends Object> propertyValueMap) {
      super.setAll(propertyValueMap);
      return this;
    }

    @Override
    public ViewInputs build() {
      return new ViewInputs(
          _tradeInputs,
          _viewConfig,
          _functionArguments,
          _valuationTime,
          _scenarioArguments,
          _marketData,
          _configData);
    }

    //-----------------------------------------------------------------------
    /**
     * Sets the {@code tradeInputs} property in the builder.
     * @param tradeInputs  the new value
     * @return this, for chaining, not null
     */
    public Builder tradeInputs(List<Object> tradeInputs) {
      this._tradeInputs = tradeInputs;
      return this;
    }

    /**
     * Sets the {@code viewConfig} property in the builder.
     * @param viewConfig  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder viewConfig(ViewConfig viewConfig) {
      JodaBeanUtils.notNull(viewConfig, "viewConfig");
      this._viewConfig = viewConfig;
      return this;
    }

    /**
     * Sets the {@code functionArguments} property in the builder.
     * @param functionArguments  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder functionArguments(FunctionArguments functionArguments) {
      JodaBeanUtils.notNull(functionArguments, "functionArguments");
      this._functionArguments = functionArguments;
      return this;
    }

    /**
     * Sets the {@code valuationTime} property in the builder.
     * @param valuationTime  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder valuationTime(ZonedDateTime valuationTime) {
      JodaBeanUtils.notNull(valuationTime, "valuationTime");
      this._valuationTime = valuationTime;
      return this;
    }

    /**
     * Sets the {@code scenarioArguments} property in the builder.
     * @param scenarioArguments  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder scenarioArguments(Map<Class<?>, Object> scenarioArguments) {
      JodaBeanUtils.notNull(scenarioArguments, "scenarioArguments");
      this._scenarioArguments = scenarioArguments;
      return this;
    }

    /**
     * Sets the {@code marketData} property in the builder.
     * @param marketData  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder marketData(Map<ZonedDateTime, Map<Pair<ExternalIdBundle, FieldName>, Result<?>>> marketData) {
      JodaBeanUtils.notNull(marketData, "marketData");
      this._marketData = marketData;
      return this;
    }

    /**
     * Sets the {@code configData} property in the builder.
     * @param configData  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder configData(Multimap<Class<?>, UniqueIdentifiable> configData) {
      JodaBeanUtils.notNull(configData, "configData");
      this._configData = configData;
      return this;
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder(256);
      buf.append("ViewInputs.Builder{");
      int len = buf.length();
      toString(buf);
      if (buf.length() > len) {
        buf.setLength(buf.length() - 2);
      }
      buf.append('}');
      return buf.toString();
    }

    protected void toString(StringBuilder buf) {
      buf.append("tradeInputs").append('=').append(JodaBeanUtils.toString(_tradeInputs)).append(',').append(' ');
      buf.append("viewConfig").append('=').append(JodaBeanUtils.toString(_viewConfig)).append(',').append(' ');
      buf.append("functionArguments").append('=').append(JodaBeanUtils.toString(_functionArguments)).append(',').append(' ');
      buf.append("valuationTime").append('=').append(JodaBeanUtils.toString(_valuationTime)).append(',').append(' ');
      buf.append("scenarioArguments").append('=').append(JodaBeanUtils.toString(_scenarioArguments)).append(',').append(' ');
      buf.append("marketData").append('=').append(JodaBeanUtils.toString(_marketData)).append(',').append(' ');
      buf.append("configData").append('=').append(JodaBeanUtils.toString(_configData)).append(',').append(' ');
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
