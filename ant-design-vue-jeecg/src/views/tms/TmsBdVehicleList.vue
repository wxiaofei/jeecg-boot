<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">

        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->
    
    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('车辆信息管理')">导出</a-button>
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

    <tmsBdVehicle-modal ref="modalForm" @ok="modalFormOk"></tmsBdVehicle-modal>
  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import TmsBdVehicleModal from './modules/TmsBdVehicleModal'
  import {initDictOptions, filterMultiDictText} from '@/components/dict/JDictSelectUtil'
  export default {
    name: "TmsBdVehicleList",
    mixins:[JeecgListMixin],
    components: {
      TmsBdVehicleModal
    },
    data () {
      return {
        description: '车辆信息管理管理页面',
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
            title:'车牌号',
            align:"center",
            dataIndex: 'license'
          },
          {
            title:'状态',
            align:"center",
            dataIndex: 'relation',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['relation'], text+"")
              }
            }
          },
          {
            title:'品牌',
            align:"center",
            dataIndex: 'brand',
            customRender:(text)=>{
              if(!text){
                return ''
              }else{
                return filterMultiDictText(this.dictOptions['brand'], text+"")
              }
            }
          },
          {
            title:'型号',
            align:"center",
            dataIndex: 'type'
          },
          {
            title:'车长(米)',
            align:"center",
            dataIndex: 'length'
          },
          {
            title:'车高(米)',
            align:"center",
            dataIndex: 'height'
          },
          {
            title:'车宽(米)',
            align:"center",
            dataIndex: 'width'
          },
          {
            title:'核定载重(吨)',
            align:"center",
            dataIndex: 'capacity'
          },
          {
            title:'车辆注册年份',
            align:"center",
            dataIndex: 'registration'
          },
          {
            title:'行驶证有效期',
            align:"center",
            dataIndex: 'validity',
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
          list: "/vehicle/tmsBdVehicle/list",
          delete: "/vehicle/tmsBdVehicle/delete",
          deleteBatch: "/vehicle/tmsBdVehicle/deleteBatch",
          exportXlsUrl: "/vehicle/tmsBdVehicle/exportXls",
          importExcelUrl: "vehicle/tmsBdVehicle/importExcel",
        },
        dictOptions:{
         relation:[],
         brand:[],
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
        initDictOptions('relation').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'relation', res.result)
          }
        })
        initDictOptions('brand').then((res) => {
          if (res.success) {
            this.$set(this.dictOptions, 'brand', res.result)
          }
        })
      }
       
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>