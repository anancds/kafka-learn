package com.cds.bigdata.parrot.common.data.schema;

/**
 * <p>数据类型</p>
 * author chendongsheng5 2017/2/8 16:32
 * version V1.0
 * modificationHistory =========================逻辑或功能性重大变更记录
 * modify by user: chendongsheng5 2017/2/8 16:32
 * modify by reason:{方法名}:{原因}
 */
public enum Type {
  INT8, INT16, INT32, INT64, FLOAT32, FLOAT64, BOOLEAN, STRING, BYTES, ARRAY;

  private String name;

  Type() {
    this.name = this.name().toLowerCase();
  }

  public String getName() {
    return name;
  }

  public boolean isPrimitive() {
    switch (this) {
      case INT8:
      case INT16:
      case INT32:
      case INT64:
      case FLOAT32:
      case FLOAT64:
      case BOOLEAN:
      case STRING:
      case BYTES:
        return true;
    }
    return false;
  }
}
