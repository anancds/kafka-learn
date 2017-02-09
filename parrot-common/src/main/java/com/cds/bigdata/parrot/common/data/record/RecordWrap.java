package com.cds.bigdata.parrot.common.data.record;

/**
 * <p></p>
 * author chendongsheng5 2017/2/9 10:41
 * version V1.0
 * modificationHistory =========================逻辑或功能性重大变更记录
 * modify by user: chendongsheng5 2017/2/9 10:41
 * modify by reason:{方法名}:{原因}
 */
public class RecordWrap {

  public int fails = 0; //失败次数计数
  private String id = ""; //未用，暂留
  private Record record;
  private Object sendRequest; //将record转化成的对象(单一)

  public RecordWrap(String id, Record record, Object sendRequest) {
    this.id = id;
    this.record = record;
    this.sendRequest = sendRequest;
  }

  public RecordWrap(Record record, Object sendRequest) {
    this.record = record;
    this.sendRequest = sendRequest;
  }

  public Object getSendRequest() {
    return this.sendRequest;
  }

  public Record getRecord() {
    return this.record;
  }

  public String getId() {
    return this.id;
  }
}
