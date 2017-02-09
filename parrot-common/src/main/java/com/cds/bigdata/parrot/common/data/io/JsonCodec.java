package com.cds.bigdata.parrot.common.data.io;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cds.bigdata.parrot.common.data.record.Record;
import com.cds.bigdata.parrot.common.data.schema.Field;
import com.cds.bigdata.parrot.common.data.schema.Schema;
import com.cds.bigdata.parrot.common.util.ThreadLocalCache;
import java.io.IOException;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Json编解码</p>
 * author chendongsheng5 2017/2/9 10:47
 * version V1.0
 * modificationHistory =========================逻辑或功能性重大变更记录
 * modify by user: chendongsheng5 2017/2/9 10:47
 * modify by reason:{方法名}:{原因}
 */
public class JsonCodec implements Codec {

  /**
   * 序列化
   */
  @Override
  public byte[] toBytes(Record record) throws IOException {
    return JSON.toJSONBytes(record);
  }

  /**
   * 反序列化
   */
  @Override
  public Record fromBytes(byte[] bytes, int offset, int length, Schema schema) throws IOException {
    return toRecord(
        JSON.parseObject(bytes, offset, length, ThreadLocalCache.getUTF8Decoder(),
            JSONObject.class),
        schema
    );
  }

  private Record toRecord(JSONObject object, Schema schema) {
    if (object != null) {
      byte[] key = object.getBytes("key");
      Record record = new Record(key, schema);
      long ts = object.getLong("ts");
      record.setTs(ts);
      JSONArray tags = object.getJSONArray("tags");
      if (tags != null) {
        for (int i = 0; i < tags.size(); i++) {
          record.addTag(tags.getString(i));
        }
      }
      JSONArray values = object.getJSONArray("values");
      for (int i = 0; i < values.size(); i++) {
        Field field = schema.field(i);
        // FIXME 是否应该给出更好的处理方式？
        if (field == null) {
          continue;
        }
        switch (field.getType()) {
          case INT8: {
            Byte value = values.getByte(i);
            if (value != null) {
              record.field(field.getName(), value);
            }
            break;
          }
          case INT16: {
            Short value = values.getShort(i);
            if (value != null) {
              record.field(field.getName(), value);
            }
            break;
          }
          case INT32: {
            Integer value = values.getInteger(i);
            if (value != null) {
              record.field(field.getName(), value);
            }
            break;
          }
          case INT64: {
            Long value = values.getLong(i);
            if (value != null) {
              record.field(field.getName(), value);
            }
            break;
          }
          case FLOAT32: {
            Float value = values.getFloat(i);
            if (value != null) {
              record.field(field.getName(), value);
            }
            break;
          }
          case FLOAT64: {
            Double value = values.getDouble(i);
            if (value != null) {
              record.field(field.getName(), value);
            }
            break;
          }
          case BOOLEAN: {
            Boolean value = values.getBoolean(i);
            if (value != null) {
              record.field(field.getName(), value);
            }
            break;
          }
          case STRING: {
            String value = values.getString(i);
            if (value != null) {
              record.field(field.getName(), value);
            }
            break;
          }
          case BYTES: {
            byte[] value = values.getObject(i, byte[].class);
            if (value != null) {
              record.field(field.getName(), value);
            }
            break;
          }
          case ARRAY: {
            JSONArray array = values.getJSONArray(i);
            if (array != null) {
              List<Record> records = new ArrayList<>(array.size());
              for (Object obj : array) {
                records.add(toRecord((JSONObject) obj, field.getItemSchema()));
              }
              record.field(field.getName(), records);
            }
            break;
          }
        }
      }
      return record;
    }
    return null;
  }
}
