package com.cds.bigdata.parrot.common.protos.schema;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * <p>schema描述类</p>
 * author chendongsheng5 2017/2/8 16:35
 * version V1.0
 * modificationHistory =========================逻辑或功能性重大变更记录
 * modify by user: chendongsheng5 2017/2/8 16:35
 * modify by reason:{方法名}:{原因}
 */
public class SchemaDescriptor {
  private String name;  // 模式名称
  private Properties params = new Properties(); // Schema参数列表
  private List<FieldDescriptor> fields = new ArrayList<>(); // 字段描述信息列表

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Properties getParams() {
    return params;
  }

  public void setParams(Properties params) {
    this.params = params;
  }

  public List<FieldDescriptor> getFields() {
    return fields;
  }

  public void setFields(List<FieldDescriptor> fields) {
    this.fields = fields;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof SchemaDescriptor)) {
      return false;
    }

    SchemaDescriptor that = (SchemaDescriptor) o;

    if (!name.equals(that.name)) {
      return false;
    }
    if (!params.equals(that.params)) {
      return false;
    }
    return fields.equals(that.fields);
  }

  @Override
  public int hashCode() {
    int result = name.hashCode();
    result = 31 * result + params.hashCode();
    result = 31 * result + fields.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "SchemaDescriptor{" +
        "name='" + name + '\'' +
        ", params=" + params +
        ", fields=" + fields +
        '}';
  }
}
