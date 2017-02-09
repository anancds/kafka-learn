package com.cds.bigdata.parrot.common.api;

import java.util.concurrent.TimeoutException;

/**
 * <p></p>
 * author chendongsheng5 2017/2/8 15:45
 * version V1.0
 * modificationHistory =========================逻辑或功能性重大变更记录
 * modify by user: chendongsheng5 2017/2/8 15:45
 * modify by reason:{方法名}:{原因}
 */
public interface ParrotClient {

  void connect() throws TimeoutException;

  void disconnect();

}
