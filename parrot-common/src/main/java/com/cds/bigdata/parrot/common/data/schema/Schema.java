package com.cds.bigdata.parrot.common.data.schema;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * <p></p>
 * author chendongsheng5 2017/2/9 10:27
 * version V1.0
 * modificationHistory =========================逻辑或功能性重大变更记录
 * modify by user: chendongsheng5 2017/2/9 10:27
 * modify by reason:{方法名}:{原因}
 */
public class Schema implements Serializable{
  private static final Map<Type, List<Class>> SCHEMA_TYPE_CLASSES = new HashMap<>();

  static {
    SCHEMA_TYPE_CLASSES.put(Type.INT8, Arrays.asList((Class) Byte.class));
    SCHEMA_TYPE_CLASSES.put(Type.INT16, Arrays.asList((Class) Short.class));
    SCHEMA_TYPE_CLASSES.put(Type.INT32, Arrays.asList((Class) Integer.class));
    SCHEMA_TYPE_CLASSES.put(Type.INT64, Arrays.asList((Class) Long.class));
    SCHEMA_TYPE_CLASSES.put(Type.FLOAT32, Arrays.asList((Class) Float.class));
    SCHEMA_TYPE_CLASSES.put(Type.FLOAT64, Arrays.asList((Class) Double.class));
    SCHEMA_TYPE_CLASSES.put(Type.BOOLEAN, Arrays.asList((Class) Boolean.class));
    SCHEMA_TYPE_CLASSES.put(Type.STRING, Arrays.asList((Class) String.class));
    SCHEMA_TYPE_CLASSES.put(Type.BYTES, Arrays.asList((Class) ByteBuffer.class));
  }

  private List<Field> fields;
  private Map<String, Field> fieldsByName;
  private Properties params = new Properties();

  /**
   * 无参构造函数，用于序列化操作
   */
  public Schema() {
  }

  public Schema(List<Field> fields) {
    this.fieldsByName = new HashMap<>();
    Field[] fieldsInOrder = new Field[fields.size()];
    for (Field field : fields) {
      fieldsInOrder[field.getIndex()] = field;
      this.fieldsByName.put(field.getName(), field);
    }
    this.fields = new ArrayList<>(fields.size());
    for (Field field : fieldsInOrder) {
      this.fields.add(field);
    }
  }

  public static List<Class> map(Type type) {
    return SCHEMA_TYPE_CLASSES.get(type);
  }

  public List<Field> fields() {
    return fields;
  }

  public Field field(String fieldName) {
    return fieldsByName.get(fieldName);
  }

  public Field field(int fieldIndex) {
    if (fieldIndex < 0 || fieldIndex >= fields.size()) {
      return null;
    }
    return fields.get(fieldIndex);
  }

  public int fieldIndex(String fieldName) {
    Field field = field(fieldName);
    if (field == null) {
      return -1;
    }
    return field.getIndex();
  }

  public Schema schema() {
    return this;
  }

  public Properties getParams() {
    return params;
  }

  public void setParams(Properties params) {
    this.params.putAll(params);
  }

  public Object paramsValue(String paramsKey) {
    return this.params.get(paramsKey);
  }

  public String paramsStringValue(String paramsKey) {
    return (String) this.params.get(paramsKey);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Schema)) return false;

    Schema that = (Schema) o;

    if (fields != null ? !fields.equals(that.fields) : that.fields != null) return false;
    return fieldsByName != null ? fieldsByName.equals(that.fieldsByName) : that.fieldsByName == null;

  }

  @Override
  public int hashCode() {
    int result = fields != null ? fields.hashCode() : 0;
    result = 31 * result + (fieldsByName != null ? fieldsByName.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Schema{" +
        "fields=" + fields +
        ", fieldsByName=" + fieldsByName +
        '}';
  }
}
