package com.cds.bigdata.parrot.common.protos.datasets;

import com.cds.bigdata.parrot.common.protos.schema.SchemaDescriptor;
import java.util.Properties;

/**
 * <p>数据集描述</p>
 * author chendongsheng5 2017/2/8 16:41
 * version V1.0
 * modificationHistory =========================逻辑或功能性重大变更记录
 * modify by user: chendongsheng5 2017/2/8 16:41
 * modify by reason:{方法名}:{原因}
 */


public class DataSetDescriptor {
  private String name = "";         // 数据集名称
  private String type = "";         // 数据集类型
  private Properties pros= new Properties(); // Schema参数列表
  private SchemaDescriptor schemadata = new SchemaDescriptor();

  public Properties getPros() {
    return pros;
  }

  public void setPros(Properties pros) {
    this.pros = pros;
  }

  public DataSetDescriptor() {
  }

  public DataSetDescriptor(String type, String name, String storage) {
    this.type = type;
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public SchemaDescriptor getSchemadata() {
    return schemadata;
  }

  public void setSchemadata(SchemaDescriptor schemadata) {this.schemadata = schemadata;}

}
