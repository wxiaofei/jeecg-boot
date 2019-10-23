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
          <a-input v-decorator="[ 'license', validatorRules.license]" placeholder="请输入车牌号"></a-input>
        </a-form-item>
          
        <a-form-item label="状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['relation']" :trigger-change="true" dictCode="relation" placeholder="请选择状态"/>
        </a-form-item>
          
        <a-form-item label="品牌" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['brand']" :trigger-change="true" dictCode="brand" placeholder="请选择品牌"/>
        </a-form-item>
          
        <a-form-item label="型号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'type', validatorRules.type]" placeholder="请输入型号"></a-input>
        </a-form-item>
          
        <a-form-item label="车长(米)" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'length', validatorRules.length]" placeholder="请输入车长(米)" style="width: 100%"/>
        </a-form-item>
          
        <a-form-item label="车高(米)" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'height', validatorRules.height]" placeholder="请输入车高(米)" style="width: 100%"/>
        </a-form-item>
          
        <a-form-item label="车宽(米)" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'width', validatorRules.width]" placeholder="请输入车宽(米)" style="width: 100%"/>
        </a-form-item>
          
        <a-form-item label="核定载重(吨)" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'capacity', validatorRules.capacity]" placeholder="请输入核定载重(吨)" style="width: 100%"/>
        </a-form-item>
          
        <a-form-item label="车辆注册年份" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'registration', validatorRules.registration]" placeholder="请输入车辆注册年份"></a-input>
        </a-form-item>
          
        <a-form-item label="行驶证有效期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择行驶证有效期" v-decorator="[ 'validity', validatorRules.validity]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
          
        <a-form-item label="创建人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'createBy', validatorRules.createBy]" placeholder="请输入创建人"></a-input>
        </a-form-item>
          
        <a-form-item label="创建时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择创建时间" v-decorator="[ 'createTime', validatorRules.createTime]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
          
        <a-form-item label="修改人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'updateBy', validatorRules.updateBy]" placeholder="请输入修改人"></a-input>
        </a-form-item>
          
        <a-form-item label="修改时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择修改时间" v-decorator="[ 'updateTime', validatorRules.updateTime]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
          
        
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import JDate from '@/components/jeecg/JDate'  
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  
  export default {
    name: "TmsBdVehicleModal",
    components: { 
      JDate,
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
        license:{rules: [{ required: true, message: '请输入车牌号!' }]},
        relation:{},
        brand:{},
        type:{},
        length:{},
        height:{},
        width:{},
        capacity:{},
        registration:{},
        validity:{rules: [{ required: true, message: '请输入行驶证有效期!' }]},
        createBy:{},
        createTime:{},
        updateBy:{},
        updateTime:{},
        },
        url: {
          add: "/vehicle/tmsBdVehicle/add",
          edit: "/vehicle/tmsBdVehicle/edit",
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
          this.form.setFieldsValue(pick(this.model,'license','relation','brand','type','length','height','width','capacity','registration','validity','createBy','createTime','updateBy','updateTime'))
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
        this.form.setFieldsValue(pick(row,'license','relation','brand','type','length','height','width','capacity','registration','validity','createBy','createTime','updateBy','updateTime'))
      }
      
    }
  }
</script>