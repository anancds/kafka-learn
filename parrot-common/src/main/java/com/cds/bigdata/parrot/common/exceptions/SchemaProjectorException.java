package com.cds.bigdata.parrot.common.exceptions;

public class SchemaProjectorException extends DataException {

  public SchemaProjectorException(String s) {
    super(s);
  }

  public SchemaProjectorException(String s, Throwable throwable) {
    super(s, throwable);
  }

  public SchemaProjectorException(Throwable throwable) {
    super(throwable);
  }
}
