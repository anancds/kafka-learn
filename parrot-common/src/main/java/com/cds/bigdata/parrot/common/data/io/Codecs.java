package com.cds.bigdata.parrot.common.data.io;

import com.cds.bigdata.parrot.common.data.record.Record;
import com.cds.bigdata.parrot.common.data.schema.Schema;
import com.cds.bigdata.parrot.common.exceptions.DataException;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import java.io.IOException;

/**
 * <p></p>
 * author chendongsheng5 2017/2/9 10:46
 * version V1.0
 * modificationHistory =========================逻辑或功能性重大变更记录
 * modify by user: chendongsheng5 2017/2/9 10:46
 * modify by reason:{方法名}:{原因}
 */
public class Codecs {
private static byte CODEC_MAGIC_JSON = (byte) 0x01;
  private static byte CODEC_VERSION_JSON_00 = (byte) 0x00;

  private static Codec defaultCodec;
  private static byte DEFAULT_CODEC_MAGIC = CODEC_MAGIC_JSON;
  private static byte DEFAULT_CODEC_VERSION = CODEC_VERSION_JSON_00;

  private static Table<Byte, Byte, Codec> CODECS = HashBasedTable.create();

  static {
    defaultCodec = new JsonCodec();
    CODECS.put(CODEC_MAGIC_JSON, CODEC_VERSION_JSON_00, defaultCodec);
  }

  public static byte[] toBytes(Record record) throws IOException {
    byte[] bytes = defaultCodec.toBytes(record);
    byte[] buf = new byte[bytes.length + 2];
    System.arraycopy(bytes, 0, buf, 2, bytes.length);
    buf[0] = DEFAULT_CODEC_MAGIC;
    buf[1] = DEFAULT_CODEC_VERSION;
    return buf;
  }

  public static Record fromBytes(byte[] bytes, int offset, int size, Schema schema) throws IOException {
    if (bytes.length < offset) {
      throw new DataException("bytes is too small to parse.");
    }
    byte magic = bytes[offset + 0];
    byte version = bytes[offset + 1];
    Codec codec = CODECS.get(magic, version);
    if (codec == null) {
      throw new DataException("No codec found for magic '" + magic + "', version '" + version + "'.");
    }
    return codec.fromBytes(bytes, offset + 2, size - 2, schema);
  }
}
