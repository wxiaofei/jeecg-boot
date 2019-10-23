<template>
  <a-card :bordered="false">
    <!-- 操作按钮区域 -->
    <!-- table区域-begin -->
    <div>

      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="pkCtSale"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        @change="handleTableChange">

      </a-table>
    </div>
    <!-- table区域-end -->
    <!-- 表单区域 -->
    <TmsContractCustomer-modal ref="modalForm" @ok="modalFormOk"></TmsContractCustomer-modal>
  </a-card>
</template>

<script>
  import TmsContractDetailModal from './modules/TmsContractDetailModal'
  import TmsContractDMainList from './TmsContractDMainList'
  import {JeecgListMixin} from '@/mixins/JeecgListMixin'
  import {getAction} from '@/api/manage'

  export default {
    name: "TmsContractCustomerList",
    mixins: [JeecgListMixin],
    components: {
      TmsContractDMainList,
      TmsContractDetailModal
    },
    data() {
      return {
        description: '合同物料信息',
        // 表头
        columns: [
          {
            title: '物料编码',
            align: "center",
            width: 100,
            dataIndex: 'materialcode',
            key: 'name',
          },


          {
            title: '物料名',
            align: "center",
            width: 100,
            dataIndex: 'materialname',
            key: 'name',
          },

          {
            title: '数量',
            align: "center",
            width: 100,
            dataIndex: 'nastnum',
            key: 'nastnum',
          },

          {
            title: '累计订单数量',
            align: "center",
            width: 100,
            dataIndex: 'nordnum',
            key: 'nordnum',
          }


        ],
        url: {
          list: "/nc/contractMain/queryDetailListByMainId",
        }
      }
    },
    methods: {
      loadData(arg) {
        if (arg === 1) {
          this.ipagination.current = 1;
        }
        var params = this.getQueryParams();
        getAction(this.url.list, {mainId: params.mainId}).then((res) => {
          if (res.success) {
            this.dataSource = res.result;
          } else {
            this.dataSource = null;
          }
        })
      },
      getContractMain(contractId) {
        this.queryParam.mainId = contractId;
        this.loadData(1);
      },

    }

  }
</script>
<style scoped>
  .ant-card {
    margin-left: -30px;
    margin-right: -30px;
  }
</style>