package com.cds.bigdata.parrot.common.exceptions;

/**
 * 找不到指定的字段
 */
public class FieldNotFoundException extends DataException {

  public FieldNotFoundException(String fieldName) {
    super("Field name '" + fieldName + "' is NOT found.");
  }

  public FieldNotFoundException(String fieldName, Throwable throwable) {
    super("Field name '" + fieldName + "' is NOT found.", throwable);
  }

  public FieldNotFoundException(Throwable throwable) {
    super(throwable);
  }
}
