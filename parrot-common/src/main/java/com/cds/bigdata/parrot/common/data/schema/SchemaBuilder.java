package com.cds.bigdata.parrot.common.data.schema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Schema工具类</p>
 * author chendongsheng5 2017/2/9 10:30
 * version V1.0
 * modificationHistory =========================逻辑或功能性重大变更记录
 * modify by user: chendongsheng5 2017/2/9 10:30
 * modify by reason:{方法名}:{原因}
 */
public class SchemaBuilder {

  private int index = 0;
  private List<Field> fields = new ArrayList<>();

  public static SchemaBuilder with() {
    return new SchemaBuilder();
  }

  public Schema build() {
    return new Schema(fields);
  }

  public FieldBuilder int8Field() {
    return new FieldBuilder(index++, Type.INT8);
  }

  public FieldBuilder int16Field() {
    return new FieldBuilder(index++, Type.INT16);
  }

  public FieldBuilder int32Field() {
    return new FieldBuilder(index++, Type.INT32);
  }

  public FieldBuilder int64Field() {
    return new FieldBuilder(index++, Type.INT64);
  }

  public FieldBuilder float32Field() {
    return new FieldBuilder(index++, Type.FLOAT32);
  }

  public FieldBuilder float64Field() {
    return new FieldBuilder(index++, Type.FLOAT64);
  }

  public FieldBuilder booleanField() {
    return new FieldBuilder(index++, Type.BOOLEAN);
  }

  public FieldBuilder stringField() {
    return new FieldBuilder(index++, Type.STRING);
  }

  public FieldBuilder bytesField() {
    return new FieldBuilder(index++, Type.BYTES);
  }

  public ArrayFieldBuilder arrayField() {
    return new ArrayFieldBuilder(index++, Type.ARRAY);
  }

  public class ArrayFieldBuilder {

    private FieldBuilder fieldBuilder;

    public ArrayFieldBuilder(int index, Type type) {
      this.fieldBuilder = new FieldBuilder(index, type);
    }

    public FieldBuilder itemSchema(Schema itemSchema) {
      fieldBuilder.itemSchema = itemSchema;
      return this.fieldBuilder;
    }
  }

  public class FieldBuilder {

    private String name;
    private String group;
    private int index;
    private Type type;
    private boolean optional;
    private Object defaultValue;
    private Schema itemSchema;
    private Map<String, String> parameters = new HashMap<>();

    public FieldBuilder(int index, Type type) {
      this.index = index;
      this.type = type;
    }

    public GroupSetter named(String name) {
      this.name = name;
      return new GroupSetter();
    }

    public class GroupSetter {

      public OptionalSetter inGroup(String groupName) {
        FieldBuilder.this.group = groupName;
        return new OptionalSetter();
      }

      public OptionalSetter inDefaultGroup() {
        FieldBuilder.this.group = Field.DEFAULT_GROUP_NAME;
        return new OptionalSetter();
      }
    }

    public class OptionalSetter {

      public OptionalDefaultValueSetter optional() {
        FieldBuilder.this.optional = true;
        return new OptionalDefaultValueSetter();
      }

      public DefaultValueSetter required() {
        FieldBuilder.this.optional = false;
        return new DefaultValueSetter();
      }
    }

    public class DefaultValueSetter {

      public ParametersSetter defaultValue(Object defaultValue) {
        FieldBuilder.this.defaultValue = defaultValue;
        return new ParametersSetter();
      }
    }

    public class OptionalDefaultValueSetter {

      public ParametersSetter defaultValue(Object defaultValue) {
        FieldBuilder.this.defaultValue = defaultValue;
        return new ParametersSetter();
      }

      public ParametersSetter noDefaultValue() {
        FieldBuilder.this.defaultValue = null;
        return new ParametersSetter();
      }
    }

    public class ParametersSetter {

      public SchemaBuilder withParameters(Map<String, String> parameters) {
        FieldBuilder.this.parameters = parameters;
        addFieldToSchemaBuilder();
        return SchemaBuilder.this;
      }

      public SchemaBuilder withoutParameters() {
        FieldBuilder.this.parameters = new HashMap<>();
        addFieldToSchemaBuilder();
        return SchemaBuilder.this;
      }

      private void addFieldToSchemaBuilder() {
        Field field = new Field(name, group, index, type, optional, defaultValue, parameters);
        if (Type.ARRAY.equals(field.getType())) {
          field.setItemSchema(itemSchema);
        }
        fields.add(field);
      }
    }
  }
}
