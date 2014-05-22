/**
 * Copyright (C) 2014 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.sesame.component;

import java.util.LinkedHashMap;
import java.util.Map;

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

import com.opengamma.component.ComponentRepository;
import com.opengamma.component.factory.AbstractComponentFactory;
import com.opengamma.core.marketdatasnapshot.MarketDataSnapshotSource;
import com.opengamma.sesame.marketdata.SnapshotMarketDataFactory;

/**
 * Creates a snapshot market data factory.
 */
@BeanDefinition
public class SnapshotMarketDataFactoryComponentFactory extends AbstractComponentFactory {

  /**
   * The classifier that the factory should publish under.
   */
  @PropertyDefinition(validate = "notNull")
  private String _classifier;
  /**
   * The snapshot source.
   */
  @PropertyDefinition(validate = "notNull")
  private MarketDataSnapshotSource _snapshotSource;


  //-------------------------------------------------------------------------
  @Override
  public void init(ComponentRepository repo, LinkedHashMap<String, String> configuration) throws Exception {
    SnapshotMarketDataFactory factory = createMarketDataFactory(repo);
    repo.registerComponent(SnapshotMarketDataFactory.class, getClassifier(), factory);
  }

  /**
   * Create the factory.
   *
   * @param repo  the component repository, typically not used, not null
   * @return the factory, not null
   */
  protected SnapshotMarketDataFactory createMarketDataFactory(ComponentRepository repo) {
    return new SnapshotMarketDataFactory(getSnapshotSource());
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code SnapshotMarketDataFactoryComponentFactory}.
   * @return the meta-bean, not null
   */
  public static SnapshotMarketDataFactoryComponentFactory.Meta meta() {
    return SnapshotMarketDataFactoryComponentFactory.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(SnapshotMarketDataFactoryComponentFactory.Meta.INSTANCE);
  }

  @Override
  public SnapshotMarketDataFactoryComponentFactory.Meta metaBean() {
    return SnapshotMarketDataFactoryComponentFactory.Meta.INSTANCE;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the classifier that the factory should publish under.
   * @return the value of the property, not null
   */
  public String getClassifier() {
    return _classifier;
  }

  /**
   * Sets the classifier that the factory should publish under.
   * @param classifier  the new value of the property, not null
   */
  public void setClassifier(String classifier) {
    JodaBeanUtils.notNull(classifier, "classifier");
    this._classifier = classifier;
  }

  /**
   * Gets the the {@code classifier} property.
   * @return the property, not null
   */
  public final Property<String> classifier() {
    return metaBean().classifier().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the snapshot source.
   * @return the value of the property, not null
   */
  public MarketDataSnapshotSource getSnapshotSource() {
    return _snapshotSource;
  }

  /**
   * Sets the snapshot source.
   * @param snapshotSource  the new value of the property, not null
   */
  public void setSnapshotSource(MarketDataSnapshotSource snapshotSource) {
    JodaBeanUtils.notNull(snapshotSource, "snapshotSource");
    this._snapshotSource = snapshotSource;
  }

  /**
   * Gets the the {@code snapshotSource} property.
   * @return the property, not null
   */
  public final Property<MarketDataSnapshotSource> snapshotSource() {
    return metaBean().snapshotSource().createProperty(this);
  }

  //-----------------------------------------------------------------------
  @Override
  public SnapshotMarketDataFactoryComponentFactory clone() {
    return JodaBeanUtils.cloneAlways(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      SnapshotMarketDataFactoryComponentFactory other = (SnapshotMarketDataFactoryComponentFactory) obj;
      return JodaBeanUtils.equal(getClassifier(), other.getClassifier()) &&
          JodaBeanUtils.equal(getSnapshotSource(), other.getSnapshotSource()) &&
          super.equals(obj);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash += hash * 31 + JodaBeanUtils.hashCode(getClassifier());
    hash += hash * 31 + JodaBeanUtils.hashCode(getSnapshotSource());
    return hash ^ super.hashCode();
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(96);
    buf.append("SnapshotMarketDataFactoryComponentFactory{");
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
    buf.append("classifier").append('=').append(JodaBeanUtils.toString(getClassifier())).append(',').append(' ');
    buf.append("snapshotSource").append('=').append(JodaBeanUtils.toString(getSnapshotSource())).append(',').append(' ');
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code SnapshotMarketDataFactoryComponentFactory}.
   */
  public static class Meta extends AbstractComponentFactory.Meta {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code classifier} property.
     */
    private final MetaProperty<String> _classifier = DirectMetaProperty.ofReadWrite(
        this, "classifier", SnapshotMarketDataFactoryComponentFactory.class, String.class);
    /**
     * The meta-property for the {@code snapshotSource} property.
     */
    private final MetaProperty<MarketDataSnapshotSource> _snapshotSource = DirectMetaProperty.ofReadWrite(
        this, "snapshotSource", SnapshotMarketDataFactoryComponentFactory.class, MarketDataSnapshotSource.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, (DirectMetaPropertyMap) super.metaPropertyMap(),
        "classifier",
        "snapshotSource");

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case -281470431:  // classifier
          return _classifier;
        case -1862154497:  // snapshotSource
          return _snapshotSource;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends SnapshotMarketDataFactoryComponentFactory> builder() {
      return new DirectBeanBuilder<SnapshotMarketDataFactoryComponentFactory>(new SnapshotMarketDataFactoryComponentFactory());
    }

    @Override
    public Class<? extends SnapshotMarketDataFactoryComponentFactory> beanType() {
      return SnapshotMarketDataFactoryComponentFactory.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code classifier} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<String> classifier() {
      return _classifier;
    }

    /**
     * The meta-property for the {@code snapshotSource} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<MarketDataSnapshotSource> snapshotSource() {
      return _snapshotSource;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case -281470431:  // classifier
          return ((SnapshotMarketDataFactoryComponentFactory) bean).getClassifier();
        case -1862154497:  // snapshotSource
          return ((SnapshotMarketDataFactoryComponentFactory) bean).getSnapshotSource();
      }
      return super.propertyGet(bean, propertyName, quiet);
    }

    @Override
    protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
      switch (propertyName.hashCode()) {
        case -281470431:  // classifier
          ((SnapshotMarketDataFactoryComponentFactory) bean).setClassifier((String) newValue);
          return;
        case -1862154497:  // snapshotSource
          ((SnapshotMarketDataFactoryComponentFactory) bean).setSnapshotSource((MarketDataSnapshotSource) newValue);
          return;
      }
      super.propertySet(bean, propertyName, newValue, quiet);
    }

    @Override
    protected void validate(Bean bean) {
      JodaBeanUtils.notNull(((SnapshotMarketDataFactoryComponentFactory) bean)._classifier, "classifier");
      JodaBeanUtils.notNull(((SnapshotMarketDataFactoryComponentFactory) bean)._snapshotSource, "snapshotSource");
      super.validate(bean);
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
