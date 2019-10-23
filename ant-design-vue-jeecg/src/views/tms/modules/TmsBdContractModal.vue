<template>
  <a-modal
    :title="title"
    :width="width"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭">
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">

        <a-form-item label="合同编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'vbillcode', validatorRules.vbillcode]" placeholder="请输入合同编码"></a-input>
        </a-form-item>
          
        <a-form-item label="合同名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'ctname', validatorRules.ctname]" placeholder="请输入合同名称"></a-input>
        </a-form-item>
          
        <a-form-item label="合同签订日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择合同签订日期" v-decorator="[ 'subscribedate', validatorRules.subscribedate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
          
        <a-form-item label="计划生效日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'valdate', validatorRules.valdate]" placeholder="请输入计划生效日期"></a-input>
        </a-form-item>
          
        <a-form-item label="计划终止日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'invallidate', validatorRules.invallidate]" placeholder="请输入计划终止日期"></a-input>
        </a-form-item>
          
        <a-form-item label="总数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'ntotalastnum', validatorRules.ntotalastnum]" placeholder="请输入总数量"></a-input>
        </a-form-item>
          
        <a-form-item label="客户" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'customer', validatorRules.customer]" placeholder="请输入客户"></a-input>
        </a-form-item>
          
        
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import JDate from '@/components/jeecg/JDate'  
  
  export default {
    name: "TmsBdContractModal",
    components: { 
      JDate,
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
        vbillcode:{},
        ctname:{},
        subscribedate:{},
        valdate:{},
        invallidate:{},
        ntotalastnum:{},
        customer:{},
        },
        url: {
          add: "/contract/tmsBdContract/add",
          edit: "/contract/tmsBdContract/edit",
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
          this.form.setFieldsValue(pick(this.model,'vbillcode','ctname','subscribedate','valdate','invallidate','ntotalastnum','customer'))
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
        this.form.setFieldsValue(pick(row,'vbillcode','ctname','subscribedate','valdate','invallidate','ntotalastnum','customer'))
      }
      
    }
  }
</script>