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

        <a-form-item label="流程状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'bpmStatus', validatorRules.bpmStatus]" placeholder="请输入流程状态"></a-input>
        </a-form-item>
          
        <a-form-item label="创建时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'createTime', validatorRules.createTime]" placeholder="请输入创建时间"></a-input>
        </a-form-item>
          
        <a-form-item label="创建人登录名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'createBy', validatorRules.createBy]" placeholder="请输入创建人登录名称"></a-input>
        </a-form-item>
          
        <a-form-item label="更新人名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'updateBy', validatorRules.updateBy]" placeholder="请输入更新人名称"></a-input>
        </a-form-item>
          
        <a-form-item label="修改时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'updateTime', validatorRules.updateTime]" placeholder="请输入修改时间"></a-input>
        </a-form-item>
          
        <a-form-item label="所属公司" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'org', validatorRules.org]" placeholder="请输入所属公司"></a-input>
        </a-form-item>
          
        <a-form-item label="委托书编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'vbillcode', validatorRules.vbillcode]" placeholder="请输入委托书编码"></a-input>
        </a-form-item>
          
        <a-form-item label="供应方" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'business', validatorRules.business]" placeholder="请输入供应方"></a-input>
        </a-form-item>
          
        <a-form-item label="采购方" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'purchase', validatorRules.purchase]" placeholder="请输入采购方"></a-input>
        </a-form-item>
          
        <a-form-item label="发运方式" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'delivery', validatorRules.delivery]" placeholder="请输入发运方式"></a-input>
        </a-form-item>
          
        <a-form-item label="运输方式" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'transport', validatorRules.transport]" placeholder="请输入运输方式"></a-input>
        </a-form-item>
          
        <a-form-item label="司机" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-select-driver-by-dep v-decorator="['driverid']" :trigger-change="true"/>
        </a-form-item>
          
        <a-form-item label="车辆" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'vehicleid', validatorRules.vehicleid]" placeholder="请输入车辆"></a-input>
        </a-form-item>
          
        <a-form-item label="物料号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-select-material-by-cate v-decorator="[ 'materialno', validatorRules.materialno]" placeholder="请输入物料号"></j-select-material-by-cate>
        </a-form-item>
          
        <a-form-item label="委托数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'proxyNum', validatorRules.proxyNum]" placeholder="请输入委托数量" style="width: 100%"/>
        </a-form-item>
          
        <a-form-item label="订单号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'orderno', validatorRules.orderno]" placeholder="请输入订单号"></a-input>
        </a-form-item>
          
        <a-form-item label="复核人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'auditBy', validatorRules.auditBy]" placeholder="请输入复核人"></a-input>
        </a-form-item>
          
        <a-form-item label="复核时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择复核时间" v-decorator="[ 'auditDate', validatorRules.auditDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
          
        <a-form-item label="确认人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'confirmBy', validatorRules.confirmBy]" placeholder="请输入确认人"></a-input>
        </a-form-item>
          
        <a-form-item label="确认时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择确认时间" v-decorator="[ 'confirmDate', validatorRules.confirmDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
          
        <a-form-item label="关闭人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'closeBy', validatorRules.closeBy]" placeholder="请输入关闭人"></a-input>
        </a-form-item>
          
        <a-form-item label="关闭时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择关闭时间" v-decorator="[ 'closehDate', validatorRules.closehDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
          
        <a-form-item label="作废人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'abolishBy', validatorRules.abolishBy]" placeholder="请输入作废人"></a-input>
        </a-form-item>
          
        <a-form-item label="作废时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择作废时间" v-decorator="[ 'abolishDate', validatorRules.abolishDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
          
        <a-form-item label="退回人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'untreadBy', validatorRules.untreadBy]" placeholder="请输入退回人"></a-input>
        </a-form-item>
          
        <a-form-item label="退回时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择退回时间" v-decorator="[ 'untreadDate', validatorRules.untreadDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
          
        <a-form-item label="退回原因" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'untreadMsg', validatorRules.untreadMsg]" placeholder="请输入退回原因"></a-input>
        </a-form-item>
          
        <a-form-item label="关闭原因" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'closeMsg', validatorRules.closeMsg]" placeholder="请输入关闭原因"></a-input>
        </a-form-item>
          
        
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import JDate from '@/components/jeecg/JDate'  
  import JSelectUserByDep from '@/components/jeecgbiz/JSelectUserByDep'
  import JSelectDriverByDep from '@/components/jeecgbiz/JSelectDriverByDep'
  import JSelectMaterialByCate from '@/components/jeecgbiz/JSelectMaterialByCate'
  
  export default {
    name: "TmsProxyMainModal",
    components: { 
      JDate,
      JSelectUserByDep,
      JSelectMaterialByCate,
      JSelectDriverByDep,
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
        bpmStatus:{},
        createTime:{},
        createBy:{},
        updateBy:{},
        updateTime:{},
        org:{},
        vbillcode:{},
        business:{},
        purchase:{},
        delivery:{},
        transport:{},
        driverid:{},
        vehicleid:{},
        materialno:{},
        proxyNum:{},
        orderno:{},
        auditBy:{},
        auditDate:{},
        confirmBy:{},
        confirmDate:{},
        closeBy:{},
        closehDate:{},
        abolishBy:{},
        abolishDate:{},
        untreadBy:{},
        untreadDate:{},
        untreadMsg:{},
        closeMsg:{},
        },
        url: {
          add: "/proxymain/tmsProxyMain/add",
          edit: "/proxymain/tmsProxyMain/edit",
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
          this.form.setFieldsValue(pick(this.model,'bpmStatus','createTime','createBy','updateBy','updateTime','org','vbillcode','business','purchase','delivery','transport','driverid','vehicleid','materialno','proxyNum','orderno','auditBy','auditDate','confirmBy','confirmDate','closeBy','closehDate','abolishBy','abolishDate','untreadBy','untreadDate','untreadMsg','closeMsg'))
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
        this.form.setFieldsValue(pick(row,'bpmStatus','createTime','createBy','updateBy','updateTime','org','vbillcode','business','purchase','delivery','transport','driverid','vehicleid','materialno','proxyNum','orderno','auditBy','auditDate','confirmBy','confirmDate','closeBy','closehDate','abolishBy','abolishDate','untreadBy','untreadDate','untreadMsg','closeMsg'))
      }
      
    }
  }
</script>