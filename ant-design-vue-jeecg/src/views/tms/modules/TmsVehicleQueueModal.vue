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

        <a-form-item label="车牌号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'vehicleCode', validatorRules.vehicleCode]" placeholder="请输入车牌号"></a-input>
        </a-form-item>
          
        <a-form-item label="订单号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'orderCode', validatorRules.orderCode]" placeholder="请输入订单号"></a-input>
        </a-form-item>
          
        <a-form-item label="申报时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择申报时间" v-decorator="[ 'applyDate', validatorRules.applyDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
          
        <a-form-item label="入厂时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'inDate', validatorRules.inDate]" placeholder="请输入入厂时间"></a-input>
        </a-form-item>
          
        <a-form-item label="出厂时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'outDate', validatorRules.outDate]" placeholder="请输入出厂时间"></a-input>
        </a-form-item>
          
        <a-form-item label="司机姓名" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'driverName', validatorRules.driverName]" placeholder="请输入司机姓名"></a-input>
        </a-form-item>
          
        <a-form-item label="司机电话" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'driverPhone', validatorRules.driverPhone]" placeholder="请输入司机电话"></a-input>
        </a-form-item>
          
        <a-form-item label="车辆状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'vehicleStatus', validatorRules.vehicleStatus]" placeholder="请输入车辆状态"></a-input>
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
    name: "TmsVehicleQueueModal",
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
        vehicleCode:{},
        orderCode:{},
        applyDate:{},
        inDate:{},
        outDate:{},
        driverName:{},
        driverPhone:{},
        vehicleStatus:{},
        },
        url: {
          add: "/queue/tmsVehicleQueue/add",
          edit: "/queue/tmsVehicleQueue/edit",
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
          this.form.setFieldsValue(pick(this.model,'vehicleCode','orderCode','applyDate','inDate','outDate','driverName','driverPhone','vehicleStatus'))
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
        this.form.setFieldsValue(pick(row,'vehicleCode','orderCode','applyDate','inDate','outDate','driverName','driverPhone','vehicleStatus'))
      }
      
    }
  }
</script>