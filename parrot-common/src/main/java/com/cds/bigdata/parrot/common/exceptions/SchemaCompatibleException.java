package com.cds.bigdata.parrot.common.exceptions;


import com.cds.bigdata.parrot.common.data.schema.Type;

/**
 * 输入的模式不匹配
 */
public class SchemaCompatibleException extends DataException {

  public SchemaCompatibleException(String fieldName, Type actual, Type required) {
    super("Value input for '" + fieldName + "' is NOT compatible, required '" + required.name()
        + "', actual '" + actual.name() + "'.");
  }

  public SchemaCompatibleException(String fieldName, Type actual, Type required,
      Throwable throwable) {
    super("Value input for '" + fieldName + "' is NOT compatible, required '" + required.name()
        + "', actual '" + actual.name() + "'.", throwable);
  }

  public SchemaCompatibleException(Throwable throwable) {
    super(throwable);
  }
}
