package com.cds.bigdata.parrot.common.data.io;

import com.cds.bigdata.parrot.common.data.record.Record;
import com.cds.bigdata.parrot.common.data.schema.Schema;
import java.io.IOException;

/**
 * <p>编解码</p>
 * author chendongsheng5 2017/2/9 10:45
 * version V1.0
 * modificationHistory =========================逻辑或功能性重大变更记录
 * modify by user: chendongsheng5 2017/2/9 10:45
 * modify by reason:{方法名}:{原因}
 */
public interface Codec {

  byte[] toBytes(Record record) throws IOException;

  Record fromBytes(byte[] bytes, int offset, int length, Schema schema) throws IOException;
}
