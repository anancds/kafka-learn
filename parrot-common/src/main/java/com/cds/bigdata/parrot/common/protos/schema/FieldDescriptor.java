package com.cds.bigdata.parrot.common.protos.schema;

import com.cds.bigdata.parrot.common.data.schema.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>字段模式描述信息</p>
 * author chendongsheng5 2017/2/8 16:28
 * version V1.0
 * modificationHistory =========================逻辑或功能性重大变更记录
 * modify by user: chendongsheng5 2017/2/8 16:28
 * modify by reason:{方法名}:{原因}
 */
public class FieldDescriptor {

  private String name;
  private String group;
  private int index;
  private Type type;
  private boolean optional;
  private String defaultValue;
  private String itemSchema;
  private Map<String, String> parameters = new HashMap<>();

  public FieldDescriptor() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getGroup() {
    return group;
  }

  public void setGroup(String group) {
    this.group = group;
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  public Type getType() {
    return type;
  }

  public void setType(Type type) {
    this.type = type;
  }

  public boolean isOptional() {
    return optional;
  }

  public void setOptional(boolean optional) {
    this.optional = optional;
  }

  public String getDefaultValue() {
    return defaultValue;
  }

  public void setDefaultValue(String defaultValue) {
    this.defaultValue = defaultValue;
  }

  public String getItemSchema() {
    return itemSchema;
  }

  public void setItemSchema(String itemSchema) {
    this.itemSchema = itemSchema;
  }

  public Map<String, String> getParameters() {
    return parameters;
  }

  public void setParameters(Map<String, String> parameters) {
    this.parameters = parameters;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof FieldDescriptor)) {
      return false;
    }

    FieldDescriptor that = (FieldDescriptor) o;

    if (getIndex() != that.getIndex()) {
      return false;
    }
    if (isOptional() != that.isOptional()) {
      return false;
    }
    if (!getName().equals(that.getName())) {
      return false;
    }
    if (!getGroup().equals(that.getGroup())) {
      return false;
    }
    if (getType() != that.getType()) {
      return false;
    }
    if (!getDefaultValue().equals(that.getDefaultValue())) {
      return false;
    }
    if (!getItemSchema().equals(that.getItemSchema())) {
      return false;
    }
    return getParameters().equals(that.getParameters());
  }

  @Override
  public int hashCode() {
    int result = getName().hashCode();
    result = 31 * result + getGroup().hashCode();
    result = 31 * result + getIndex();
    result = 31 * result + getType().hashCode();
    result = 31 * result + (isOptional() ? 1 : 0);
    result = 31 * result + getDefaultValue().hashCode();
    result = 31 * result + getItemSchema().hashCode();
    result = 31 * result + getParameters().hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "FieldDescriptor{" +
        "name='" + name + '\'' +
        ", group='" + group + '\'' +
        ", index=" + index +
        ", type=" + type +
        ", optional=" + optional +
        ", defaultValue='" + defaultValue + '\'' +
        ", itemSchema='" + itemSchema + '\'' +
        ", parameters=" + parameters +
        '}';
  }
}
