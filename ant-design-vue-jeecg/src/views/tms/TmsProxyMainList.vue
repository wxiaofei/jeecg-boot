<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">

          <a-col :md="6" :sm="8">
            <a-form-item label="委托书编码">
              <a-input placeholder="请输入委托书编码" v-model="queryParam.vbillcode"></a-input>
            </a-form-item>
          </a-col>

          <a-col :md="6" :sm="8" >
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>

            </span>
          </a-col>
          <a-col :md="6" :sm="8">

            <template v-if="superQueryFlag">
              <a-tooltip title="已有高级查询条件生效!">
                <button :disabled="false" class="ant-btn ant-btn-primary" @click="superQuery">
                  <a-icon type="appstore" theme="twoTone" spin="true"></a-icon>
                  <span>高级查询</span>
                </button>
              </a-tooltip>
            </template>
            <a-button v-else type="primary" @click="superQuery" icon="filter">高级查询</a-button>

            <a @click="handleToggleSearch" style="margin-left: 8px">
              {{ toggleSearchStatus ? '收起' : '展开' }}
              <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
            </a>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->
    
    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('委托书管理')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
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
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange">
        
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无此图片</span>
          <img v-else :src="getImgView(text)" height="25px" alt="图片不存在" style="max-width:80px;font-size: 12px;font-style: italic;"/>
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无此文件</span>
          <a-button
            v-else
            :ghost="true"
            type="primary"
            icon="download"
            size="small"
            @click="uploadFile(text)">
            下载
          </a-button>
        </template>

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>

          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>

    <tmsProxyMain-modal ref="modalForm" @ok="modalFormOk"></tmsProxyMain-modal>

    <!-- 高级查询区域 -->
    <j-super-query :fieldList="fieldList" ref="superQueryModal" @handleSuperQuery="handleSuperQuery"></j-super-query>

  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import TmsProxyMainModal from './modules/TmsProxyMainModal'
  import JSuperQuery from '@/components/jeecg/JSuperQuery.vue';

  //高级查询modal需要参数
  const superQueryFieldList=[{
    type:"date",
    value:"birthday",
    text:"生日"
  },{
    type:"string",
    value:"name",
    text:"用户名"
  },{
    type:"int",
    value:"age",
    text:"年龄"
  }]
  export default {
    name: "TmsProxyMainList",
    mixins:[JeecgListMixin],
    components: {
      TmsProxyMainModal,
      JSuperQuery
    },


    data () {
      return {
        description: '委托书管理管理页面',
        // 表头
        columns: [
          {
            title: '#',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
          },

          {
            title:'所属公司',
            align:"center",
            dataIndex: 'org'
          },
          {
            title:'委托书编码',
            align:"center",
            dataIndex: 'vbillcode'
          },
          {
            title:'供应方',
            align:"center",
            dataIndex: 'business'
          },
          {
            title:'采购方',
            align:"center",
            dataIndex: 'purchase'
          },
          {
            title:'发运方式',
            align:"center",
            dataIndex: 'delivery'
          },
          {
            title:'运输方式',
            align:"center",
            dataIndex: 'transport'
          },
          {
            title:'司机',
            align:"center",
            dataIndex: 'driverid'
          },
          {
            title:'车辆',
            align:"center",
            dataIndex: 'vehicleid'
          },
          {
            title:'物料号',
            align:"center",
            dataIndex: 'materialno'
          },
          {
            title:'委托数量',
            align:"center",
            dataIndex: 'proxyNum'
          },
          {
            title:'订单号',
            align:"center",
            dataIndex: 'orderno'
          },
          {
            title:'复核人',
            align:"center",
            dataIndex: 'auditBy'
          },
          {
            title:'复核时间',
            align:"center",
            dataIndex: 'auditDate',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'确认人',
            align:"center",
            dataIndex: 'confirmBy'
          },
          {
            title:'确认时间',
            align:"center",
            dataIndex: 'confirmDate',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },

          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
        url: {
          list: "/proxymain/tmsProxyMain/list",
          delete: "/proxymain/tmsProxyMain/delete",
          deleteBatch: "/proxymain/tmsProxyMain/deleteBatch",
          exportXlsUrl: "/proxymain/tmsProxyMain/exportXls",
          importExcelUrl: "proxymain/tmsProxyMain/importExcel",
        },
        fieldList:superQueryFieldList,
        dictOptions:{
        } 
      }
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    methods: {
      initDictConfig(){
      }
       
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>