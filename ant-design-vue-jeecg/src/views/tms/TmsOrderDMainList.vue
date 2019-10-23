<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">

          <a-col :md="6" :sm="24">
            <a-form-item label="委托书编码">
              <a-input placeholder="请输入委托书编码" v-model="queryParam.orderCode"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="24">
            <a-form-item label="订单类型">
              <a-select placeholder="请输入订单类型" v-model="queryParam.ctype">
                <a-select-option value="1">国内订单</a-select-option>
                <a-select-option value="2">国际订单</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>

          <a-col :md="6" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
            </span>
          </a-col>

        </a-row>
      </a-form>
    </div>


    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>

      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel">
            <a-icon type="delete"/>
            删除
          </a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作
          <a-icon type="down"/>
        </a-button>
      </a-dropdown>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        filterMultiple="filterMultiple"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange,type:type}"
        @change="handleTableChange"
        :customRow="clickThenCheck"
      >

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>
          <a-divider type="vertical"/>
          <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
            <a>删除</a>
          </a-popconfirm>
        </span>

      </a-table>
    </div>
    <!-- table区域-end -->

    <a-tabs defaultActiveKey="1">
      <a-tab-pane tab="委托信息" key="1">
        <Tms-Order-Detail-List ref="TmsOrderDetailList"></Tms-Order-Detail-List>
      </a-tab-pane>
    </a-tabs>

    <!-- 表单区域 -->
    <tmsOrderDMain-modal ref="modalForm" @ok="modalFormOk"></tmsOrderDMain-modal>

  </a-card>
</template>

<script>
  import TmsOrderDMainModal from './modules/TmsOrderDMainModal'
  import TmsOrderDetailList from './TmsOrderDetailList'
  import {deleteAction} from '@/api/manage'
  import TmsOrderDetailModal from './modules/TmsOrderDetailModal'
  import {JeecgListMixin} from '@/mixins/JeecgListMixin'

  export default {
    name: "TmsOrderDMainList",
    mixins: [JeecgListMixin],
    components: {
      TmsOrderDetailModal,
      TmsOrderDMainModal,
      TmsOrderDetailList,
    },
    data() {
      return {
        description: '订单管理页面',
        /* 分页参数 */
        ipagination:{
          current: 1,
          pageSize: 5,
          pageSizeOptions: ['5', '10', '20'],
          showTotal: (total, range) => {
            return range[0] + "-" + range[1] + " 共" + total + "条"
          },
          showQuickJumper: true,
          showSizeChanger: true,
          total: 0
        },
        // 表头
        columns: [{
          title: '#',
          dataIndex: '',
          key: 'rowIndex',
          width: 60,
          align: "center",
          customRender: function (t, r, index) {
            return parseInt(index) + 1;
          }
        },
          {
            title: '委托书编码',
            align: "center",
            dataIndex: 'orderCode'
          },
          {
            title: '订单日期',
            align: "center",
            dataIndex: 'orderDate'
          },
          {
            title: '订单金额',
            align: "center",
            dataIndex: 'orderMoney'
          },
          {
            title: '供应方',
            align: "center",
            dataIndex: 'business'
          },
          {
            title: '采购方',
            align: "center",
            dataIndex: 'purchase'
          },

          {
            title: '司机',
            align: "center",
            dataIndex: 'driverid'
          },

          {
            title: '车辆',
            align: "center",
            dataIndex: 'vehicleid'
          },

          {
            title: '发运方式',
            align: "center",
            dataIndex: 'delivery'
          },

          {
            title: '运输方式',
            align: "center",
            dataIndex: 'transport'
          },
          {
            title: '备注',
            align: "center",
            dataIndex: 'content'
          },
          {
            title: '操作',
            dataIndex: 'action',
            align: "center",
            scopedSlots: {customRender: 'action'},
          }],
        // 分页参数
        type: "radio",
        url: {
          list: "/tms/order/orderList",
          delete: "/tms/order/delete",
          deleteBatch: "/tms/order/deleteBatch",
        },
      }
    },
    methods: {
      clickThenCheck(record) {
        return {
          on: {
            click: () => {
              this.onSelectChange(record.id.split(","), [record]);
            }
          }
        };
      },
      onSelectChange(selectedRowKeys, selectionRows) {
        this.selectedRowKeys = selectedRowKeys;
        this.selectionRows = selectionRows;
        this.$refs.TmsOrderDetailList.getOrderMain(this.selectedRowKeys[0]);
      },
      onClearSelected() {
        this.selectedRowKeys = [];
        this.selectionRows = [];
        this.$refs.TmsOrderDetailList.queryParam.mainId = null;
        this.$refs.TmsOrderDetailList.loadData();
        this.$refs.TmsOrderDetailList.selectedRowKeys = [];
        this.$refs.TmsOrderDetailList.selectionRows = [];

      },

      handleDelete: function (id) {
        var that = this;
        deleteAction(that.url.delete, {id: id}).then((res) => {
          if (res.success) {
            that.$message.success(res.message);
            that.loadData();
            this.$refs.TmsOrderDetailList.loadData();
          } else {
            that.$message.warning(res.message);
          }
        });
      },
      searchQuery:function(){
        this.selectedRowKeys = [];
        this.selectionRows = [];
        this.$refs.TmsOrderDetailList.queryParam.mainId = null;
        this.$refs.TmsOrderDetailList.loadData();
        this.$refs.TmsOrderDetailList.selectedRowKeys = [];
        this.$refs.TmsOrderDetailList.selectionRows = [];

        this.loadData();
      }
    }
  }
</script>
<style scoped>
  .ant-card-body .table-operator {
    margin-bottom: 18px;
  }

  .ant-table-tbody .ant-table-row td {
    padding-top: 15px;
    padding-bottom: 15px;
  }

  .anty-row-operator button {
    margin: 0 5px
  }

  .ant-btn-danger {
    background-color: #ffffff
  }

  .ant-modal-cust-warp {
    height: 100%
  }

  .ant-modal-cust-warp .ant-modal-body {
    height: calc(100% - 110px) !important;
    overflow-y: auto
  }

  .ant-modal-cust-warp .ant-modal-content {
    height: 90% !important;
    overflow-y: hidden
  }
</style>