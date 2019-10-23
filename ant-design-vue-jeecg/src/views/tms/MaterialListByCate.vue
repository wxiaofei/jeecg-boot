<template>
  <a-row :gutter="10">
    <a-col :md="8" :sm="24">
      <a-card :bordered="false">
        <div style="background: #fff;padding-left:16px;height: 100%; margin-top: 5px">
          <a-input-search @search="onSearch" style="width:100%;margin-top: 10px" placeholder="请输入分类名称"/>
          <!-- 树-->
          <template>
            <!--组织机构-->
            <a-directory-tree
              selectable
              :selectedKeys="selectedKeys"
              :checkStrictly="true"
              @select="onSelect"
              :dropdownStyle="{maxHeight:'200px',overflow:'auto'}"
              :treeData="cateTree"
            />
          </template>

        </div>
      </a-card>
    </a-col>
    <a-col :md="16" :sm="24">
      <a-card :bordered="false">
        <a-tabs defaultActiveKey="2" @change="callback">
          <a-tab-pane tab="基本信息" key="1" forceRender>
            <Cate-Base-Info ref="CateBaseInfo"></Cate-Base-Info>
          </a-tab-pane>
          <a-tab-pane tab="物料信息" key="2">
            <Cate-Material-Info ref="CateMaterialInfo"></Cate-Material-Info>
          </a-tab-pane>
        </a-tabs>
      </a-card>
    </a-col>
  </a-row>
</template>
<script>
  import CateBaseInfo from './modules/CateBaseInfo'
  import CateMaterialInfo from './modules/CateMaterialInfo'
  import {queryCateTreeList, searchCateByKeywords} from '@/api/api'
  import {JeecgListMixin} from '@/mixins/JeecgListMixin'

  export default {
    name: 'CateMaterialList',
    mixins: [JeecgListMixin],
    components: {
      CateBaseInfo,
      CateMaterialInfo,
    },
    data() {
      return {
        currentCateId: '',
        iExpandedKeys: [],
        loading: false,
        autoExpandParent: true,
        disable: true,
        treeData: [],
        visible: false,
        cateTree: [],
        rightClickSelectedKey: '',
        hiding: true,
        model: {},
        dropTrigger: '',
        disableSubmit: false,
        checkedKeys: [],
        selectedKeys: [],
        autoIncr: 1,
        currSelected: {},
        form: this.$form.createForm(this),
        labelCol: {
          xs: {span: 24},
          sm: {span: 5}
        },
        wrapperCol: {
          xs: {span: 24},
          sm: {span: 16}
        },
        graphDatasource: {
          nodes: [],
          edges: []
        },
      }
    },
    methods: {
      callback(key) {
        console.log(key)
      },
      loadData() {
        this.refresh();
      },
      loadTree() {
        var that = this
        that.treeData = []
        that.cateTree = []
        queryCateTreeList().then((res) => {
          if (res.success) {
            for (let i = 0; i < res.result.length; i++) {
              let temp = res.result[i]
              that.treeData.push(temp)
              that.cateTree.push(temp)
              that.setThisExpandedKeys(temp)
              // console.log(temp.id)
            }
            this.loading = false
          }
        })
      },
      setThisExpandedKeys(node) {
        if (node.children && node.children.length > 0) {
          this.iExpandedKeys.push(node.key)
          for (let a = 0; a < node.children.length; a++) {
            this.setThisExpandedKeys(node.children[a])
          }
        }
      },
      refresh() {
        this.loading = true
        this.loadTree()
      },

      onExpand(expandedKeys) {
        this.iExpandedKeys = expandedKeys
        this.autoExpandParent = false
      },

      onSearch(value) {
        let that = this
        if (value) {
          searchCateByKeywords({keyWord: value}).then((res) => {
            if (res.success) {
              that.cateTree = []
              for (let i = 0; i < res.result.length; i++) {
                let temp = res.result[i]
                that.cateTree.push(temp)
              }
            } else {
              that.$message.warning(res.message)
            }
          })
        } else {
          that.loadTree()
        }

      },
      onCheck(checkedKeys, e) {
        let record = e.node.dataRef;
        this.checkedKeys = [];
        // if (e.checked === true) {
        this.currentCateId = record.id;
        this.checkedKeys.push(record.id);

        this.$refs.CateBaseInfo.open(record);
        this.$refs.CateMaterialInfo.open(record);
        // }
        // else {
        //   this.checkedKeys = [];
        //   this.$refs.DeptBaseInfo.clearForm();
        //   this.$refs.DeptUserInfo.clearList();
        // }

        this.hiding = false;
        // this.checkedKeys = checkedKeys.checked
      },
      onSelect(selectedKeys, e) {
        if (this.selectedKeys[0] !== selectedKeys[0]) {
          this.selectedKeys = [selectedKeys[0]];
        }
        let record = e.node.dataRef;
        this.checkedKeys.push(record.id);
        this.$refs.CateBaseInfo.open(record);
        this.$refs.CateMaterialInfo.onClearSelected();
        this.$refs.CateMaterialInfo.open(record);
      },
    },
    created() {
      // this.loadTree()
    },
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>