package com.cds.bigdata.parrot.common.data.schema;

import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * <p></p>
 * author chendongsheng5 2017/2/9 10:28
 * version V1.0
 * modificationHistory =========================逻辑或功能性重大变更记录
 * modify by user: chendongsheng5 2017/2/9 10:28
 * modify by reason:{方法名}:{原因}
 */
public class Field implements Serializable {

  public static String DEFAULT_GROUP_NAME = "default";

  private final String name;
  private final String group;
  private final int index;
  private final Type type;
  private final boolean optional;
  private final Object defaultValue;
  private final Map<String, String> parameters = new HashMap<>();
  private Schema itemSchema;

  public Field(String name,
      String group,
      int index,
      Type type,
      boolean optional,
      Object defaultValue,
      Map<String, String> parameters) {
    this.name = name;
    this.group = group;
    this.index = index;
    this.type = type;
    this.optional = optional;
    this.defaultValue = defaultValue;
    if (parameters != null) {
      this.parameters.putAll(parameters);
    }
  }

  public Field(String name,
      int index,
      Type type,
      boolean optional,
      Object defaultValue,
      Map<String, String> parameters) {
    this(name, DEFAULT_GROUP_NAME, index, type, optional, defaultValue, parameters);
  }

  public String getName() {
    return name;
  }

  public int getIndex() {
    return index;
  }

  public Type getType() {
    return type;
  }

  public boolean isOptional() {
    return optional;
  }

  public Object getDefaultValue() {
    return SchemaProjector.project(this.type, defaultValue);
  }

  public Map<String, String> getParameters() {
    return parameters;
  }

  public Schema getItemSchema() {
    return itemSchema;
  }

  public void setItemSchema(Schema itemSchema) {
    // 只有ARRAY才能设置元素Schema
    Preconditions.checkArgument(Type.ARRAY.equals(this.type));
    this.itemSchema = itemSchema;
  }

  public String getGroup() {
    return group;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Field)) {
      return false;
    }

    Field field = (Field) o;

    if (index != field.index) {
      return false;
    }
    if (optional != field.optional) {
      return false;
    }
    if (name != null ? !name.equals(field.name) : field.name != null) {
      return false;
    }
    if (type != field.type) {
      return false;
    }
    if (defaultValue != null ? !defaultValue.equals(field.defaultValue)
        : field.defaultValue != null) {
      return false;
    }
    return parameters != null ? parameters.equals(field.parameters) : field.parameters == null;

  }

  @Override
  public int hashCode() {
    int result = name != null ? name.hashCode() : 0;
    result = 31 * result + index;
    result = 31 * result + (type != null ? type.hashCode() : 0);
    result = 31 * result + (optional ? 1 : 0);
    result = 31 * result + (defaultValue != null ? defaultValue.hashCode() : 0);
    result = 31 * result + (parameters != null ? parameters.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Field{" +
        "name='" + name + '\'' +
        ", index=" + index +
        ", type=" + type +
        ", optional=" + optional +
        ", defaultValue=" + defaultValue +
        ", parameters=" + parameters +
        '}';
  }
}
