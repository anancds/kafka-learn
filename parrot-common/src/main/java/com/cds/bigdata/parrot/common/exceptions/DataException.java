package com.cds.bigdata.parrot.common.exceptions;

public class DataException extends RuntimeException {

  public DataException(String s) {
    super(s);
  }

  public DataException(String s, Throwable throwable) {
    super(s, throwable);
  }

  public DataException(Throwable throwable) {
    super(throwable);
  }
}
