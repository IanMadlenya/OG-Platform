// Automatically created - do not modify
///CLOVER:OFF
// CSOFF: Generated File
package com.opengamma.engine.view.calcnode.msg;
public class Result extends com.opengamma.engine.view.calcnode.msg.RemoteCalcNodeMessage implements java.io.Serializable {
  public void accept (RemoteCalcNodeMessageVisitor visitor) { visitor.visitResultMessage (this); }
  private static final long serialVersionUID = -27839947071064l;
  private com.opengamma.engine.view.calcnode.CalculationJobResult _result;
  public static final String RESULT_KEY = "result";
  private com.opengamma.engine.view.calcnode.msg.Ready _ready;
  public static final String READY_KEY = "ready";
  public Result (com.opengamma.engine.view.calcnode.CalculationJobResult result) {
    if (result == null) throw new NullPointerException ("'result' cannot be null");
    else {
      _result = result;
    }
  }
  protected Result (final org.fudgemsg.mapping.FudgeDeserializationContext fudgeContext, final org.fudgemsg.FudgeFieldContainer fudgeMsg) {
    super (fudgeMsg);
    org.fudgemsg.FudgeField fudgeField;
    fudgeField = fudgeMsg.getByName (RESULT_KEY);
    if (fudgeField == null) throw new IllegalArgumentException ("Fudge message is not a Result - field 'result' is not present");
    try {
      _result = fudgeContext.fieldValueToObject (com.opengamma.engine.view.calcnode.CalculationJobResult.class, fudgeField);
    }
    catch (IllegalArgumentException e) {
      throw new IllegalArgumentException ("Fudge message is not a Result - field 'result' is not CalculationJobResult message", e);
    }
    fudgeField = fudgeMsg.getByName (READY_KEY);
    if (fudgeField != null)  {
      try {
        final com.opengamma.engine.view.calcnode.msg.Ready fudge1;
        fudge1 = com.opengamma.engine.view.calcnode.msg.Ready.fromFudgeMsg (fudgeMsg.getFieldValue (org.fudgemsg.FudgeFieldContainer.class, fudgeField));
        setReady (fudge1);
      }
      catch (IllegalArgumentException e) {
        throw new IllegalArgumentException ("Fudge message is not a Result - field 'ready' is not Ready message", e);
      }
    }
  }
  public Result (com.opengamma.engine.view.calcnode.CalculationJobResult result, com.opengamma.engine.view.calcnode.msg.Ready ready) {
    if (result == null) throw new NullPointerException ("'result' cannot be null");
    else {
      _result = result;
    }
    if (ready == null) _ready = null;
    else {
      _ready = (com.opengamma.engine.view.calcnode.msg.Ready)ready.clone ();
    }
  }
  protected Result (final Result source) {
    super (source);
    if (source == null) throw new NullPointerException ("'source' must not be null");
    if (source._result == null) _result = null;
    else {
      _result = source._result;
    }
    if (source._ready == null) _ready = null;
    else {
      _ready = (com.opengamma.engine.view.calcnode.msg.Ready)source._ready.clone ();
    }
  }
  public Result clone () {
    return new Result (this);
  }
  public org.fudgemsg.FudgeFieldContainer toFudgeMsg (final org.fudgemsg.mapping.FudgeSerializationContext fudgeContext) {
    if (fudgeContext == null) throw new NullPointerException ("fudgeContext must not be null");
    final org.fudgemsg.MutableFudgeFieldContainer msg = fudgeContext.newMessage ();
    toFudgeMsg (fudgeContext, msg);
    return msg;
  }
  public void toFudgeMsg (final org.fudgemsg.mapping.FudgeSerializationContext fudgeContext, final org.fudgemsg.MutableFudgeFieldContainer msg) {
    super.toFudgeMsg (fudgeContext, msg);
    if (_result != null)  {
      fudgeContext.objectToFudgeMsgWithClassHeaders (msg, RESULT_KEY, null, _result, com.opengamma.engine.view.calcnode.CalculationJobResult.class);
    }
    if (_ready != null)  {
      final org.fudgemsg.MutableFudgeFieldContainer fudge1 = org.fudgemsg.mapping.FudgeSerializationContext.addClassHeader (fudgeContext.newMessage (), _ready.getClass (), com.opengamma.engine.view.calcnode.msg.Ready.class);
      _ready.toFudgeMsg (fudgeContext, fudge1);
      msg.add (READY_KEY, null, fudge1);
    }
  }
  public static Result fromFudgeMsg (final org.fudgemsg.mapping.FudgeDeserializationContext fudgeContext, final org.fudgemsg.FudgeFieldContainer fudgeMsg) {
    final java.util.List<org.fudgemsg.FudgeField> types = fudgeMsg.getAllByOrdinal (0);
    for (org.fudgemsg.FudgeField field : types) {
      final String className = (String)field.getValue ();
      if ("com.opengamma.engine.view.calcnode.msg.Result".equals (className)) break;
      try {
        return (com.opengamma.engine.view.calcnode.msg.Result)Class.forName (className).getDeclaredMethod ("fromFudgeMsg", org.fudgemsg.mapping.FudgeDeserializationContext.class, org.fudgemsg.FudgeFieldContainer.class).invoke (null, fudgeContext, fudgeMsg);
      }
      catch (Throwable t) {
        // no-action
      }
    }
    return new Result (fudgeContext, fudgeMsg);
  }
  public com.opengamma.engine.view.calcnode.CalculationJobResult getResult () {
    return _result;
  }
  public void setResult (com.opengamma.engine.view.calcnode.CalculationJobResult result) {
    if (result == null) throw new NullPointerException ("'result' cannot be null");
    else {
      _result = result;
    }
  }
  public com.opengamma.engine.view.calcnode.msg.Ready getReady () {
    return _ready;
  }
  public void setReady (com.opengamma.engine.view.calcnode.msg.Ready ready) {
    if (ready == null) _ready = null;
    else {
      _ready = (com.opengamma.engine.view.calcnode.msg.Ready)ready.clone ();
    }
  }
  public String toString () {
    return org.apache.commons.lang.builder.ToStringBuilder.reflectionToString(this, org.apache.commons.lang.builder.ToStringStyle.SHORT_PREFIX_STYLE);
  }
}
///CLOVER:ON
// CSON: Generated File
