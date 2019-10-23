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

        <a-form-item label="物料编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'code', validatorRules.code]" placeholder="请输入物料编码"></a-input>
        </a-form-item>
          
        <a-form-item label="物料名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'name', validatorRules.name]" placeholder="请输入物料名称"></a-input>
        </a-form-item>

        <a-form-item label="选择分类" :labelCol="labelCol" :wrapperCol="wrapperCol">
    <!--      <j-select-material-cate v-decorator="['cateId']" :trigger-change="true"></j-select-material-cate>-->
          <j-tree-select
            style="width: 300px"
            v-model="treeValue"
            dict="sys_depart,depart_name,id"
            pid-field="parent_id">
          </j-tree-select>
          {{ treeValue }}
        </a-form-item>

        <a-form-item label="助记码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'materialmnecode', validatorRules.materialmnecode]" placeholder="请输入助记码"></a-input>
        </a-form-item>
          
        <a-form-item label="规格" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'materialspec', validatorRules.materialspec]" placeholder="请输入规格"></a-input>
        </a-form-item>
          
        <a-form-item label="型号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'materialtype', validatorRules.materialtype]" placeholder="请输入型号"></a-input>
        </a-form-item>
          
        <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'memo', validatorRules.memo]" placeholder="请输入备注"></a-input>
        </a-form-item>

          
        <a-form-item label="nc物料id" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-select-user-by-dep v-decorator="['pkMaterial']" :trigger-change="true"/>
        </a-form-item>
          
        
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import JSelectUserByDep from '@/components/jeecgbiz/JSelectUserByDep'
  import JSelectMaterialCate from '@/components/jeecgbiz/JSelectMaterialCate'
  import JTreeSelect from '@/components/jeecg/JTreeSelect'

  export default {
    name: "TmsBdMaterialModal",
    components: { 
      JSelectUserByDep,
      JSelectMaterialCate,
      JTreeSelect,
    },
    data () {
      return {
        form: this.$form.createForm(this),
        treeValue:'',
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
        code:{rules: [{ required: true, message: '请输入物料编码!' }]},
        name:{},
        cateId:{},
        materialmnecode:{},
        materialspec:{},
        materialtype:{},
        memo:{},
        pkMaterial:{},
        },
        url: {
          add: "/material/tmsBdMaterial/add",
          edit: "/material/tmsBdMaterial/edit",
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
          this.form.setFieldsValue(pick(this.model,'code','name','cateId','materialmnecode','materialspec','materialtype','memo','pkMaterial'))
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
        this.form.setFieldsValue(pick(row,'code','name','cateId','materialmnecode','materialspec','materialtype','memo','pkMaterial'))
      },
      getTreeFieldValue(){
        return this.form.getFieldValue("demoTree")
      }
      
    }
  }
</script>