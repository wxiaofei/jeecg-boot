<template>
  <a-drawer
    :title="title"
    :width="width"
    placement="right"
    :closable="false"
    @close="close"
    :visible="visible">
  
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">

        <a-form-item label="客商编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'code', validatorRules.code]" placeholder="请输入客商编码"></a-input>
        </a-form-item>
        <a-form-item label="客商名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'name', validatorRules.name]" placeholder="请输入客商名称"></a-input>
        </a-form-item>
        <a-form-item label="客商简称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'shortname', validatorRules.shortname]" placeholder="请输入客商简称"></a-input>
        </a-form-item>
        <a-form-item label="客户启用状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['custenablestate']" :trigger-change="true" dictCode="custenablestate" placeholder="请选择客户启用状态"/>
        </a-form-item>
        <a-form-item label="客商属性" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['custsupprop']" :trigger-change="true" dictCode="custsupprop" placeholder="请选择客商属性"/>
        </a-form-item>
        <a-form-item label="客商类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['custsuptype']" :trigger-change="true" dictCode="custsuptype" placeholder="请选择客商类型"/>
        </a-form-item>
        <a-form-item label="助记码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'mnecode', validatorRules.mnecode]" placeholder="请输入助记码"></a-input>
        </a-form-item>
        <a-form-item label="所属组织" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'org', validatorRules.org]" placeholder="请输入所属组织"></a-input>
        </a-form-item>
        <a-form-item label="纳税人登记号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'taxpayerid', validatorRules.taxpayerid]" placeholder="请输入纳税人登记号"></a-input>
        </a-form-item>
        
      </a-form>
    </a-spin>
    <a-button type="primary" @click="handleOk">确定</a-button>
    <a-button type="primary" @click="handleCancel">取消</a-button>
  </a-drawer>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  
  export default {
    name: "TmsBdSupplierModal",
    components: { 
      JDictSelectTag,
    },
    data () {
      return {
        form: this.$form.createForm(this),
        title:"操作",
        width:800,
        visible: false,
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },

        confirmLoading: false,
        validatorRules:{
        code:{rules: [{ required: true, message: '请输入客商编码!' }]},
        name:{},
        shortname:{},
        custenablestate:{},
        custsupprop:{},
        custsuptype:{},
        mnecode:{},
        org:{},
        taxpayerid:{},
        },
        url: {
          add: "/supplier/tmsBdSupplier/add",
          edit: "/supplier/tmsBdSupplier/edit",
        }
     
      }
    },
    created () {
    },
    methods: {
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'code','name','shortname','custenablestate','custsupprop','custsuptype','mnecode','org','taxpayerid'))
        })
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleOk () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            let formData = Object.assign(this.model, values);
            console.log("表单提交数据",formData)
            httpAction(httpurl,formData,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
              that.close();
            })
          }
         
        })
      },
      handleCancel () {
        this.close()
      },
      popupCallback(row){
        this.form.setFieldsValue(pick(row,'code','name','shortname','custenablestate','custsupprop','custsuptype','mnecode','org','taxpayerid'))
      }
      
    }
  }
</script>

<style lang="less" scoped>
/** Button按钮间距 */
  .ant-btn {
    margin-left: 30px;
    margin-bottom: 30px;
    float: right;
  }
</style>