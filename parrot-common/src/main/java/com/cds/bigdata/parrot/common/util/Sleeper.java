package com.cds.bigdata.parrot.common.util;

import java.util.concurrent.TimeoutException;

/**
 * Sleep工具类
 * Created by chendongsheng5 on 2017/2/8.
 */
public class Sleeper {

  private long delayMs;   // 延迟启动sleep的毫秒数
  private long duration;  // 一次Sleep的时长 ms
  private long interval;  // 两次Sleep之间的时间间隔 ms
  private Waker waker;    // 退出sleep的唤醒类

  private Sleeper(long duration, long interval, long delayMs, Waker waker) {
    this.duration = duration > 0 ? duration : Long.MAX_VALUE;
    this.interval = interval > 0 ? interval : 100;
    this.delayMs = delayMs > 0 ? delayMs : 0;
    this.waker = waker;
  }

  public static Sleeper create(long ms) {
    return create(ms, null);
  }

  public static Sleeper create(long ms, Waker waker) {
    return create(ms, 500, waker);
  }

  public static Sleeper create(long ms, long interval, Waker waker) {
    return new Sleeper(ms, interval, -1, waker);
  }

  public static Sleeper create(long ms, long interval, long delayMs, Waker waker) {
    return new Sleeper(ms, interval, delayMs, waker);
  }

  public void sleep() throws TimeoutException {
    if (delayMs > 0) {
      try {
        Thread.sleep(delayMs);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    long cost;
    long s = System.currentTimeMillis();
    do {
      try {
        Thread.sleep(interval);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      cost = System.currentTimeMillis() - s;
      if (duration > 0 && cost > duration) {
        throw new TimeoutException(
            "Task Not terminated after " + cost + " ms (timeout=" + duration + ").");
      }
    } while (waker == null || !waker.shouldWake());
  }

  public void sleepQuietly() {
    try {
      sleep();
    } catch (TimeoutException e) {
      // No-ops
    }
  }

  /**
   * 唤醒类接口
   */
  public interface Waker {

    /**
     * 返回true， Sleeper退出，返回false， Sleeper继续
     */
    boolean shouldWake();
  }
}
