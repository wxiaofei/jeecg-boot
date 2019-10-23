<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">

          <a-col :md="6" :sm="24">
            <a-form-item label="合同号">
              <a-input placeholder="请输入合同号" v-model="queryParam.vbillcode"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="24">
            <a-form-item label="合同名称">
              <a-select placeholder="请输入订单类型" v-model="queryParam.ctname">
                <a-select-option value="1">点价合同</a-select-option>
                <a-select-option value="2">现货销售合同</a-select-option>
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
        rowKey="pkCtSale"
        filterMultiple="filterMultiple"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange,type:type}"
        @change="handleTableChange"
        :customRow="clickThenCheck"
      >



      </a-table>
    </div>
    <!-- table区域-end -->

    <a-tabs defaultActiveKey="1">
      <a-tab-pane tab="物料信息" key="1">
        <Tms-Contract-Detail-List ref="TmsContractDetailList"></Tms-Contract-Detail-List>
      </a-tab-pane>

    </a-tabs>

    <!-- 表单区域 -->
    <TmsContractDMain-modal ref="modalForm" @ok="modalFormOk"></TmsContractDMain-modal>

  </a-card>
</template>

<script>
  import TmsContractDMainModal from './modules/TmsContractDMainModal'
  import TmsContractDetailModal from './modules/TmsContractDetailModal'
  import TmsContractDetailList from './TmsContractDetailList'
  import {deleteAction} from '@/api/manage'
  import {JeecgListMixin} from '@/mixins/JeecgListMixin'

  export default {
    name: "TmsContractDMainList",
    mixins: [JeecgListMixin],
    components: {
      TmsContractDMainModal,
      TmsContractDetailModal,
      TmsContractDetailList,
    },
    data() {
      return {
        description: '合同管理页面',
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
            title: '合同号',
            align: "center",
            dataIndex: 'vbillcode'
          },

          {
            title: '合同名称',
            align: "center",
            dataIndex: 'ctname'
          },

          {
            title: '合同状态',
            align: "center",
            dataIndex: 'fstatusflag',
            customRender: (text) => {
              let re = "";
              if (text === '1') {
                re = "生效";
              } else if (text === '2') {
                re = "未生效";
              }
              return re;
            }
          },

          {
            title: '合同日期',
            align: "center",
            dataIndex: 'dbilldate'
          }
          ],
        // 分页参数
        type: "radio",
        url: {
          list: "/nc/contractMain/list",
        },
      }
    },
    methods: {
      clickThenCheck(record) {
        return {
          on: {
            click: () => {
              this.onSelectChange(record.pkCtSale.split(","), [record]);
            }
          }
        };
      },
      onSelectChange(selectedRowKeys, selectionRows) {
        this.selectedRowKeys = selectedRowKeys;
        this.selectionRows = selectionRows;
        this.$refs.TmsContractDetailList.getContractMain(this.selectedRowKeys[0]);
      },
      onClearSelected() {
        this.selectedRowKeys = [];
        this.selectionRows = [];
        this.$refs.TmsContractDetailList.queryParam.mainId = null;
        this.$refs.TmsContractDetailList.loadData();
        this.$refs.TmsContractDetailList.selectedRowKeys = [];
        this.$refs.TmsContractDetailList.selectionRows = [];

      },

      searchQuery:function(){
        this.selectedRowKeys = [];
        this.selectionRows = [];
        this.$refs.TmsContractDetailList.queryParam.mainId = null;
        this.$refs.TmsContractDetailList.loadData();
        this.$refs.TmsContractDetailList.selectedRowKeys = [];
        this.$refs.TmsContractDetailList.selectionRows = [];
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