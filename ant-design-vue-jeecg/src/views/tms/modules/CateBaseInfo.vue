<template>
  <a-card :visible="visible">
    <a-form :form="form">
      <a-form-item
        :labelCol="labelCol"
        :wrapperCol="wrapperCol"
        label="分类编码">
        <a-input style="border:0px;" placeholder="" v-decorator="['cateCode', {}]"/>
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="上级分类">
        <a-tree-select
          disabled
          style="width:100%;border: 0px;border: none;outline:none;"
          :dropdownStyle="{maxHeight:'200px',overflow:'auto'}"
          :treeData="treeData"
          v-model="model.pid"
          placeholder="无">
        </a-tree-select>
      </a-form-item>
      <a-form-item
        :labelCol="labelCol"
        :wrapperCol="wrapperCol"
        label="分类名称">
        <a-input style="border:0px;" placeholder="" v-decorator="['cateName', {}]"/>
      </a-form-item>

    </a-form>
  </a-card>
</template>
<script>
  import pick from 'lodash.pick'
  import {queryCateIdTree} from '@/api/api'

  export default {
    name: 'CateBaseInfo',
    components: {},
    data() {
      return {
        cateTree: [],
        id: '',
        model: {},
        visible: false,
        disable: true,
        treeData: [],
        form: this.$form.createForm(this),
        labelCol: {
          xs: {span: 24},
          sm: {span: 3}
        },
        wrapperCol: {
          xs: {span: 24},
          sm: {span: 16}
        },
      }
    },
    created() {
      this.loadTreeData();
    },
    methods: {
      loadTreeData() {
        queryCateIdTree().then((res) => {
          if (res.success) {
            for (let i = 0; i < res.result.length; i++) {
              let temp = res.result[i];
              this.treeData.push(temp);
            }
          }

        })
      },
      open(record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        console.log("record:");
        console.log(record);
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(record, 'cateCode', 'pid', 'orgCode', 'cateName'));
        });
      },
      clearForm() {
        this.form.resetFields();
        this.treeData = [];
      },
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>