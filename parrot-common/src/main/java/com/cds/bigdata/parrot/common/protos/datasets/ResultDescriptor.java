package com.cds.bigdata.parrot.common.protos.datasets;

import com.cds.bigdata.parrot.common.protos.schema.SchemaDescriptor;
import java.util.ArrayList;
import java.util.List;

/**
 * <p></p>
 * author chendongsheng5 2017/2/8 16:45
 * version V1.0
 * modificationHistory =========================逻辑或功能性重大变更记录
 * modify by user: chendongsheng5 2017/2/8 16:45
 * modify by reason:{方法名}:{原因}
 */
public class ResultDescriptor {

  private List<SchemaDescriptor> schemaData = new ArrayList<>();
  private List<DataSetDescriptor> datasetData = new ArrayList<>();

  public List<SchemaDescriptor> getSchemaData() {
    return schemaData;
  }

  public void setSchemaData(ArrayList<SchemaDescriptor> schemaData) {
    this.schemaData = schemaData;
  }

  public List<DataSetDescriptor> getDatasetData() {
    return datasetData;
  }

  public void setDatasetData(List<DataSetDescriptor> datasetData) {
    this.datasetData = datasetData;
  }
}
