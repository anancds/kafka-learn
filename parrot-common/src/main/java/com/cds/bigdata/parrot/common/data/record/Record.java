package com.cds.bigdata.parrot.common.data.record;

import com.cds.bigdata.parrot.common.data.schema.Field;
import com.cds.bigdata.parrot.common.data.schema.Schema;
import com.cds.bigdata.parrot.common.data.schema.SchemaProjector;
import com.cds.bigdata.parrot.common.data.schema.Type;
import com.cds.bigdata.parrot.common.exceptions.DataException;
import com.cds.bigdata.parrot.common.exceptions.FieldNotFoundException;
import com.cds.bigdata.parrot.common.exceptions.SchemaCompatibleException;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p></p>
 * author chendongsheng5 2017/2/9 10:39
 * version V1.0
 * modificationHistory =========================逻辑或功能性重大变更记录
 * modify by user: chendongsheng5 2017/2/9 10:39
 * modify by reason:{方法名}:{原因}
 */
public class Record implements Serializable {

  private transient Schema schema;
  private byte[] key;
  private Object[] values;
  private Set<String> tags = new HashSet<>();
  private long ts = System.currentTimeMillis();

  /**
   * 仅在序列化过程使用
   */
  public Record() {
  }

  /**
   * 构造函数
   */
  public Record(byte[] key, Schema schema) {
    this.schema = schema;
    this.key = key;
    this.values = new Object[schema.fields().size()];
  }

  public long getTs() {
    return ts;
  }

  public void setTs(long ts) {
    this.ts = ts;
  }

  /**
   * 获取Tag清单
   */
  public Set<String> getTags() {
    return tags;
  }

  /**
   * 仅在序列化过程使用
   */
  public void setTags(Set<String> tags) {
    this.tags = tags;
  }

  /**
   * 添加Tag
   */
  public void addTag(String tag) {
    this.tags.add(tag);
  }

  /**
   * 删除Tag
   */
  public void removTag(String tag) {
    this.tags.remove(tag);
  }

  /**
   * 判断是否存在给定Tag
   */
  public boolean hasTag(String tag) {
    return this.tags.contains(tag);
  }

  /**
   * 获取schema
   */
  public Schema getSchema() {
    return schema;
  }

  /**
   * 仅在序列化过程使用
   */
  public void setSchema(Schema schema) {
    this.schema = schema;
    for (Field field : schema.fields()) {
      if (Type.ARRAY.equals(field.getType())) {
        Object subRecords = values[field.getIndex()];
        if (subRecords != null) {
          for (Record record : (List<Record>) subRecords) {
            record.setSchema(field.getItemSchema());
          }
        }
      }
    }
  }

  /**
   * 获取键值
   */
  public byte[] getKey() {
    return key;
  }

  /**
   * 仅在序列化过程使用
   */
  public void setKey(byte[] key) {
    this.key = key;
  }

  /**
   * 获取原始数据
   */
  public Object[] getValues() {
    return values;
  }

  /**
   * 仅在序列化过程使用
   */
  public void setValues(Object[] values) {
    this.values = values;
  }

  /**
   * 获取字段数
   */
  public int fields() {
    return values == null ? 0 : values.length;
  }

  public Object fieldValue(int index) {
    Field field = getField(index);
    return fieldValue(field, field.getType());
  }

  public Object fieldValue(String fieldName) {
    return fieldValue(schema.fieldIndex(fieldName));
  }

  private Field getField(String fieldName) {
    Field field = this.schema.field(fieldName);
    if (field == null) {
      throw new DataException("Field " + fieldName + " is NOT existed.");
    }
    return field;
  }

  private Field getField(int index) throws DataException {
    Field field = this.schema.field(index);
    if (field == null) {
      throw new DataException("Field " + index + " is NOT existed.");
    }
    return field;
  }

  private Object fieldValue(Field field, Type type) {
    int index = field.getIndex();
    Object value = (index < 0 || index >= values.length) ? null : values[index];
    if (value == null) {
      Object defaultValue = field.getDefaultValue();
      if (defaultValue == null) {
        if (field.isOptional()) {
          return null;
        } else {
          throw new DataException("Required Field '" + field.getName() + "' is NULL.");
        }
      } else {
        return defaultValue;
      }
    } else {
      return SchemaProjector.project(type, value);
    }
  }

  public Record setFieldValue(String fieldName, Object value) {
    Field field = getField(fieldName);
    return setFieldValue(field.getIndex(), value);
  }

  public Record setFieldValue(int index, Object value) {
    Field field = getField(index);
    Object regValue = SchemaProjector.project(field.getType(), value);
    if (regValue instanceof ByteBuffer) {
      values[index] = ((ByteBuffer) regValue).array();
    } else {
      values[index] = regValue;
    }
    return this;
  }

  public Record field(String fieldName, byte value) {
    setFieldValue(fieldName, value);
    return this;
  }

  public Record field(String fieldName, short value) {
    setFieldValue(fieldName, value);
    return this;
  }

  public Record field(String fieldName, int value) {
    setFieldValue(fieldName, value);
    return this;
  }

  public Record field(String fieldName, long value) {
    setFieldValue(fieldName, value);
    return this;
  }

  public Record field(String fieldName, float value) {
    setFieldValue(fieldName, value);
    return this;
  }

  public Record field(String fieldName, double value) {
    setFieldValue(fieldName, value);
    return this;
  }

  public Record field(String fieldName, boolean value) {
    setFieldValue(fieldName, value);
    return this;
  }

  public Record field(String fieldName, byte[] value) {
    setFieldValue(fieldName, value);
    return this;
  }

  public Record field(String fieldName, String value) {
    setFieldValue(fieldName, value);
    return this;
  }

  public Record field(String fieldName, List<Record> records) {
    setFieldValue(fieldName, records);
    return this;
  }

  public Byte byteValue(int index) {
    return (Byte) fieldValue(getField(index), Type.INT8);
  }

  public Byte byteValue(String fieldName) {
    int index = schema.fieldIndex(fieldName);
    return byteValue(index);
  }

  public Short shortValue(int index) {
    return (Short) fieldValue(getField(index), Type.INT16);
  }

  public Short shortValue(String fieldName) {
    int index = schema.fieldIndex(fieldName);
    return shortValue(index);
  }

  public Integer intValue(int index) {
    return (Integer) fieldValue(getField(index), Type.INT32);
  }

  public Integer intValue(String fieldName) {
    int index = schema.fieldIndex(fieldName);
    return intValue(index);
  }

  public Long longValue(int index) {
    return (Long) fieldValue(getField(index), Type.INT64);
  }

  public Long longValue(String fieldName) {
    int index = schema.fieldIndex(fieldName);
    return longValue(index);
  }

  public Float floatValue(int index) {
    return (Float) fieldValue(getField(index), Type.FLOAT32);
  }

  public Float floatValue(String fieldName) {
    int index = schema.fieldIndex(fieldName);
    return floatValue(index);
  }

  public Double doubleValue(int index) {
    return (Double) fieldValue(getField(index), Type.FLOAT64);
  }

  public Double doubleValue(String fieldName) {
    int index = schema.fieldIndex(fieldName);
    return doubleValue(index);
  }

  public Boolean booleanValue(int index) {
    return (Boolean) fieldValue(getField(index), Type.BOOLEAN);
  }

  public Boolean booleanValue(String fieldName) {
    int index = schema.fieldIndex(fieldName);
    return booleanValue(index);
  }

  public String stringValue(int index) {
    return (String) fieldValue(getField(index), Type.STRING);
  }

  public String stringValue(String fieldName) {
    int index = schema.fieldIndex(fieldName);
    return stringValue(index);
  }

  public ByteBuffer bytesValue(int index) {
    return (ByteBuffer) fieldValue(getField(index), Type.BYTES);
  }

  public ByteBuffer bytesValue(String fieldName) {
    int index = schema.fieldIndex(fieldName);
    return bytesValue(index);
  }

  public List<Record> arrayValue(int index) {
    return (List<Record>) fieldValue(getField(index), Type.ARRAY);
  }

  public List<Record> arrayValue(String fieldName) {
    int index = schema.fieldIndex(fieldName);
    return arrayValue(index);
  }

  /**
   * 是否有指定的字段
   */
  public boolean hasField(String fieldName) {
    try {
      return fieldValue(fieldName) != null;
    } catch (Throwable t) {
      // No-ops
    }
    return false;
  }


  /**
   * 检查类型是否兼容
   */
  private Field checkSchema(String fieldName, Type actual) {
    Field field = schema.field(fieldName);
    if (field == null) {
      throw new FieldNotFoundException(fieldName);
    }
    Type type = field.getType();
    if (!type.equals(actual)) {
      throw new SchemaCompatibleException(fieldName, actual, type);
    }
    return field;
  }

  private Field checkSchema(int fieldIndex, Type actual) {
    Field field = schema.field(fieldIndex);
    if (field == null) {
      throw new FieldNotFoundException("idx=" + fieldIndex);
    }
    Type type = field.getType();
    if (!type.equals(actual)) {
      throw new SchemaCompatibleException("idx=" + fieldIndex, actual, type);
    }
    return field;
  }
}
