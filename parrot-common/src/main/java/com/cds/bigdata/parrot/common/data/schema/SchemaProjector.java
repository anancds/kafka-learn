package com.cds.bigdata.parrot.common.data.schema;

import com.cds.bigdata.parrot.common.data.record.Record;
import com.cds.bigdata.parrot.common.exceptions.DataException;
import com.cds.bigdata.parrot.common.util.Bytes;
import com.google.common.collect.Lists;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.List;

/**
 * <p>字段映射工具类，将字段映射成合格的类型</p>
 * author chendongsheng5 2017/2/9 10:29
 * version V1.0
 * modificationHistory =========================逻辑或功能性重大变更记录
 * modify by user: chendongsheng5 2017/2/9 10:29
 * modify by reason:{方法名}:{原因}
 */
public class SchemaProjector {

  /**
   * 尽可能的将数据投影成合规的数据，如果无法投影，返回null
   */
  public static Object project(Type type, Object value) {
    if (value == null) {
      return null;
    }
    List<Class> javaClasses = Schema.map(type);
    if (javaClasses != null && javaClasses.contains(value.getClass())) {
      return value;
    } else {
      try {
        switch (type) {
          case INT8:
            if (value instanceof byte[]) {
              return ((byte[]) value).length > 0 ? ((byte[]) value)[0] : null;
            } else if (value instanceof Number) {
              return ((Number) value).byteValue();
            } else if (value instanceof Boolean) {
              return ((Boolean) value).booleanValue() ? (byte) 1 : (byte) 0;
            } else if (value instanceof String) {
              return Byte.parseByte((String) value);
            } else {
              throw new DataException(
                  "Cannot project to right type, except type=" + type + ", value type=" + value
                      .getClass());
            }
          case INT16:
            if (value instanceof byte[]) {
              return Bytes.toShort((byte[]) value);
            } else if (value instanceof Number) {
              return ((Number) value).shortValue();
            } else if (value instanceof Boolean) {
              return ((Boolean) value).booleanValue() ? 1 : 0;
            } else if (value instanceof String) {
              return Short.parseShort((String) value);
            } else {
              throw new DataException(
                  "Cannot project to right type, except type=" + type + ", value type=" + value
                      .getClass());
            }
          case INT32:
            if (value instanceof byte[]) {
              return Bytes.toInt((byte[]) value);
            } else if (value instanceof Number) {
              return ((Number) value).intValue();
            } else if (value instanceof Boolean) {
              return ((Boolean) value).booleanValue() ? 1 : 0;
            } else if (value instanceof String) {
              return Integer.parseInt((String) value);
            } else {
              throw new DataException(
                  "Cannot project to right type, except type=" + type + ", value type=" + value
                      .getClass());
            }
          case INT64:
            if (value instanceof byte[]) {
              return Bytes.toLong((byte[]) value);
            } else if (value instanceof Number) {
              return ((Number) value).longValue();
            } else if (value instanceof Boolean) {
              return ((Boolean) value).booleanValue() ? 1 : 0;
            } else if (value instanceof String) {
              return Long.parseLong((String) value);
            } else {
              throw new DataException(
                  "Cannot project to right type, except type=" + type + ", value type=" + value
                      .getClass());
            }
          case FLOAT32:
            if (value instanceof byte[]) {
              return Bytes.toFloat((byte[]) value);
            } else if (value instanceof Number) {
              return ((Number) value).floatValue();
            } else if (value instanceof Boolean) {
              return ((Boolean) value).booleanValue() ? 1 : 0;
            } else if (value instanceof String) {
              return Float.parseFloat((String) value);
            } else {
              throw new DataException(
                  "Cannot project to right type, except type=" + type + ", value type=" + value
                      .getClass());
            }
          case FLOAT64:
            if (value instanceof byte[]) {
              return Bytes.toDouble((byte[]) value);
            } else if (value instanceof Number) {
              return ((Number) value).doubleValue();
            } else if (value instanceof Boolean) {
              return ((Boolean) value).booleanValue() ? 1 : 0;
            } else if (value instanceof String) {
              return Double.parseDouble((String) value);
            } else {
              throw new DataException(
                  "Cannot project to right type, except type=" + type + ", value type=" + value
                      .getClass());
            }
          case BOOLEAN:
            if (value instanceof byte[]) {
              return Bytes.toBoolean((byte[]) value);
            } else if (value instanceof Number) {
              return ((Number) value).doubleValue() > 0;
            } else if (value instanceof String) {
              try {
                return Double.parseDouble((String) value) > 0;
              } catch (Throwable t) {
                // No-ops
              }
              return Boolean.parseBoolean((String) value);
            } else {
              throw new DataException(
                  "Cannot project to right type, except type=" + type + ", value type=" + value
                      .getClass());
            }
          case STRING:
            if (value instanceof byte[]) {
              return Bytes.toString((byte[]) value);
            } else {
              return value.toString();
            }
          case BYTES:
            if (value instanceof byte[]) {
              return ByteBuffer.wrap((byte[]) value);
            } else if (value instanceof Byte) {
              return ByteBuffer.wrap(new byte[]{((Byte) value).byteValue()});
            } else if (value instanceof Short) {
              return ByteBuffer.wrap(Bytes.toBytes((Short) value));
            } else if (value instanceof Integer) {
              return ByteBuffer.wrap(Bytes.toBytes((Integer) value));
            } else if (value instanceof Long) {
              return ByteBuffer.wrap(Bytes.toBytes((Long) value));
            } else if (value instanceof Float) {
              return ByteBuffer.wrap(Bytes.toBytes((Float) value));
            } else if (value instanceof Double) {
              return ByteBuffer.wrap(Bytes.toBytes((Double) value));
            } else if (value instanceof Boolean) {
              return ByteBuffer.wrap(Bytes.toBytes((Boolean) value));
            } else if (value instanceof Boolean) {
              return ByteBuffer.wrap(Bytes.toBytes((Boolean) value));
            } else if (value instanceof String) {
              return ByteBuffer.wrap(Bytes.toBytes((String) value));
            } else {
              throw new DataException(
                  "Cannot project to right type, except type=" + type + ", value type=" + value
                      .getClass());
            }
          case ARRAY:
            if (value instanceof List) {
              return value;
            } else if (value instanceof Array) {
              return Lists.newArrayList((Record[]) value);
            } else if (value instanceof Collection) {
              return Lists.newArrayList((Collection) value);
            } else if (value instanceof Iterable) {
              return Lists.newArrayList((Iterable) value);
            } else {
              throw new DataException(
                  "Cannot project to right type, except type=" + type + ", value type=" + value
                      .getClass());
            }
        }
      } catch (Throwable t) {
        // No-ops
        t.printStackTrace();
      }
    }
    return null;
  }
}
