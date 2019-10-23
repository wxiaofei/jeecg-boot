<template>
  <a-modal
    :width="modalWidth"
    :visible="visible"
    :title="title"
    @ok="handleSubmit"
    @cancel="close"
    cancelText="关闭"
    style="margin-top: -70px"
    wrapClassName="ant-modal-cust-warp"
  >
    <a-row :gutter="10" style="background-color: #ececec; padding: 10px; margin: -10px">
      <a-col :md="6" :sm="24">
        <a-card :bordered="false">
          <!--组织分类-->
          <a-directory-tree
            selectable
            :selectedKeys="selectedKeys"
            :checkStrictly="true"
            @select="this.onSelect"
            :dropdownStyle="{maxHeight:'200px',overflow:'auto'}"
            :treeData="cateTree"
          />
        </a-card>
      </a-col>
      <a-col :md="18" :sm="24">
        <a-card :bordered="false">
          物料名称:
          <a-input-search
            :style="{width:'150px',marginBottom:'15px'}"
            placeholder="请输入物料名称"
            v-model="queryParam.name"
            @search="onSearch"
          ></a-input-search>
          <a-button @click="searchReset(1)" style="margin-left: 20px" icon="redo">重置</a-button>
          <!--物料列表-->
          <a-table
            ref="table"
            :scroll="scrollTrigger"
            size="middle"
            rowKey="id"
            :columns="columns"
            :dataSource="dataSource"
            :pagination="ipagination"
            :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
            @change="handleTableChange">
          </a-table>
        </a-card>
      </a-col>
    </a-row>
  </a-modal>
</template>

<script>
  import { filterObj } from '@/utils/util'
  import { queryCateTreeList, searchMaterial} from '@/api/api'
  export default {
    name: 'JSelectMaterialByCateModal',
    components: {},
    props:['modalWidth'],
    data() {
      return {
        queryParam: {
          name:"",
        },
        columns: [
          {
            title: '物料编码',
            align: 'center',
            dataIndex: 'code'
          },
          {
            title: '物料名称',
            align: 'center',
            dataIndex: 'name'
          },
          {
            title: '规格',
            align: 'center',
            dataIndex: 'materialspec'
          }
        ],
        scrollTrigger: {},
        dataSource: [],
        selectedKeys: [],
        userNameArr: [],
        cateName: '',
        userRolesMap: {},
        title: '选择物料',
        ipagination: {
          current: 1,
          pageSize: 10,
          pageSizeOptions: ['10', '20', '30'],
          showTotal: (total, range) => {
            return range[0] + '-' + range[1] + ' 共' + total + '条'
          },
          showQuickJumper: true,
          showSizeChanger: true,
          total: 0
        },
        isorter: {
          column: 'createTime',
          order: 'desc'
        },
        selectedRowKeys: [],
        selectedRows: [],
        cateTree: [],
        visible: false,
        form: this.$form.createForm(this)
      }
    },
    created() {
      // 该方法触发屏幕自适应
      this.resetScreenSize();
      this.loadData(1);
    },
    methods: {
      loadData(arg) {
        if (arg === 1) {
          this.ipagination.current = 1;
        }
        let params = this.getQueryParams();//查询条件
        searchMaterial(params).then((res) => {
          if (res.success) {
            this.dataSource = res.result.records;
            this.ipagination.total = res.result.total;
          }
        })
      },

      // 触发屏幕自适应
      resetScreenSize() {
        let screenWidth = document.body.clientWidth;
        if (screenWidth < 500) {
          this.scrollTrigger = { x: 800 };
        } else {
          this.scrollTrigger = {};
        }
      },
      showModal() {
        this.visible = true;
        this.queryCateTree();
        this.form.resetFields();
      },
      getQueryParams() {
        let param = Object.assign({}, this.queryParam, this.isorter);
        param.field = this.getQueryField();
        param.pageNo = this.ipagination.current;
        param.pageSize = this.ipagination.pageSize;
        return filterObj(param);
      },
      getQueryField() {
        let str = 'id,';
        for (let a = 0; a < this.columns.length; a++) {
          str += ',' + this.columns[a].dataIndex;
        }
        return str;
      },
      searchReset(num) {
        let that = this;
        if(num !== 0){
          that.queryParam = {};
          that.loadData(1);
        }
        that.selectedRowKeys = [];
        that.userNameArr = [];
        that.selectedKeys = [];
      },
      close() {
        this.searchReset(0);
        this.visible = false;
      },
      handleTableChange(pagination, filters, sorter) {
        //TODO 筛选
        if (Object.keys(sorter).length > 0) {
          this.isorter.column = sorter.field;
          this.isorter.order = 'ascend' === sorter.order ? 'asc' : 'desc';
        }
        this.ipagination = pagination;
        this.loadData();
      },
      handleSubmit() {
        let that = this;
        for (let i = 0, len = this.selectedRowKeys.length; i < len; i++) {
          this.getUserNames(this.selectedRowKeys[i]);
        }
        that.$emit('ok', that.userNameArr.join(','));
        that.close();
      },
      // 遍历匹配,获取用户真实姓名
      getUserNames(rowId) {
        let dataSource = this.dataSource;
        for (let i = 0, len = dataSource.length; i < len; i++) {
          if (rowId === dataSource[i].id) {
            this.userNameArr.push(dataSource[i].name);
          }
        }
      },
      // 点击树节点,筛选出对应的用户
      onSelect(selectedKeys) {
        if (selectedKeys[0] != null) {
          this.queryMaterialByCateId(selectedKeys); // 调用方法根据选选择的id查询用户信息
          if (this.selectedKeys[0] !== selectedKeys[0]) {
            this.selectedKeys = [selectedKeys[0]];
          }
        }
      },
      onSelectChange(selectedRowKeys, selectionRows) {
        this.selectedRowKeys = selectedRowKeys;
        this.selectionRows = selectionRows;
      },
      onSearch() {
        this.loadData(1);
      },
      // 根据选择的id来查询用户信息
      queryMaterialByCateId(selectedKeys) {
        searchMaterial({ cateId: selectedKeys.toString() }).then((res) => {
          if (res.success) {
            this.dataSource = res.result.records;
            this.ipagination.total = res.result.total;
          }
        })
      },
      // 传入用户id,找到匹配的角色名称
      queryUserRole(userId) {
        let map = this.userRolesMap;
        let roleName = [];
        for (var key in map) {
          if (userId === key) {
            roleName.push(map[key]);
          }
        }
        return roleName.join(',');
      },
      queryCateTree() {
        queryCateTreeList().then((res) => {
          if (res.success) {
            this.cateTree = res.result;
          }
        })
      },

      modalFormOk() {
        this.loadData();
      }
    }
  }
</script>

<style scoped>
  .ant-table-tbody .ant-table-row td {
    padding-top: 10px;
    padding-bottom: 10px;
  }

  #components-layout-demo-custom-trigger .trigger {
    font-size: 18px;
    line-height: 64px;
    padding: 0 24px;
    cursor: pointer;
    transition: color .3s;
  }
</style>