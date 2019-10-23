<template>
  <a-drawer
    :title="title"
    :maskClosable="true"
    :width="drawerWidth"
    placement="right"
    :closable="true"
    @close="handleCancel"
    :visible="visible"
    style="height: calc(100% - 55px);overflow: auto;padding-bottom: 53px;">

    <template slot="title">
      <div style="width: 100%;">
        <span>{{ title }}</span>
        <span style="display:inline-block;width:calc(100% - 51px);padding-right:10px;text-align: right">
          <a-button @click="toggleScreen" icon="appstore" style="height:20px;width:20px;border:0px"></a-button>
        </span>
      </div>

    </template>

    <a-spin :spinning="confirmLoading">
      <a-form :form="form">

        <a-form-item label="物料编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input placeholder="请输入用户账号" v-decorator="[ 'code', validatorRules.code]" :readOnly="!!model.id"/>
        </a-form-item>

        <template v-if="!model.id">
          <a-form-item label="物料名称" :labelCol="labelCol" :wrapperCol="wrapperCol" >
            <a-input  placeholder="请输入物料名称" v-decorator="[ 'name', validatorRules.name]" />
          </a-form-item>

          <a-form-item label="助记码" :labelCol="labelCol" :wrapperCol="wrapperCol" >
            <a-input  placeholder="请输入助记码" v-decorator="[ 'materialmnecode']"/>
          </a-form-item>
        </template>

        <a-form-item label="规格" :labelCol="labelCol" :wrapperCol="wrapperCol" >
          <a-input placeholder="请输入规格" v-decorator="[ 'materialspec']" />
        </a-form-item>

        <a-form-item label="型号" :labelCol="labelCol" :wrapperCol="wrapperCol" >
          <a-input placeholder="请输入型号" v-decorator="[ 'materialtype']" />
        </a-form-item>

        <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol" >
          <a-input placeholder="请输入备注" v-decorator="[ 'memo']" />
        </a-form-item>


      </a-form>
    </a-spin>


    <div class="drawer-bootom-button" v-show="!disableSubmit">
      <a-popconfirm title="确定放弃编辑？" @confirm="handleCancel" okText="确定" cancelText="取消">
        <a-button style="margin-right: .8rem">取消</a-button>
      </a-popconfirm>
      <a-button @click="handleSubmit" type="primary" :loading="confirmLoading">提交</a-button>
    </div>
  </a-drawer>
</template>

<script>
  import pick from 'lodash.pick'
  import moment from 'moment'
  import Vue from 'vue'

  import { ACCESS_TOKEN } from "@/store/mutation-types"
  import { getAction } from '@/api/manage'
  import {addMaterial,editMaterial} from '@/api/api'
  import {duplicateCheck } from '@/api/api'

  export default {
    name: "MaterialModal",
    components: {
    },
    data () {
      return {
        modalWidth:800,
        drawerWidth:700,
        modaltoggleFlag:true,
        confirmDirty: false,
        cateId:"", //保存分类id
        disableSubmit:false,
        dateFormat:"YYYY-MM-DD",
        validatorRules:{
        },
        title:"操作",
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
        uploadLoading:false,
        confirmLoading: false,
        headers:{},
        form:this.$form.createForm(this),
        url: {
          userId:"/sys/user/generateUserId", // 引入生成添加用户情况下的url
        },
      }
    },
    created () {
      const token = Vue.ls.get(ACCESS_TOKEN);
      this.headers = {"X-Access-Token":token}

    },
    computed:{

    },
    methods: {

      //窗口最大化切换
      toggleScreen(){
        if(this.modaltoggleFlag){
          this.modalWidth = window.innerWidth;
        }else{
          this.modalWidth = 800;
        }
        this.modaltoggleFlag = !this.modaltoggleFlag;
      },

      refresh () {
          this.cateId=""
      },
      add (value) {
        this.refresh();
        this.cateId=value;
        this.edit({cateId:value});
      },
      edit (record) {
        this.resetScreenSize(); // 调用此方法,根据屏幕宽度自适应调整抽屉的宽度
        let that = this;
        that.form.resetFields();
        that.visible = true;
        that.model = Object.assign({}, record);
        that.$nextTick(() => {
          that.form.setFieldsValue(pick(this.model,'code','name'))
        });

      },

      close () {
        this.$emit('close');
        this.visible = false;
        this.disableSubmit = false;

      },
      moment,
      handleSubmit () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let formData = Object.assign(this.model, values);
            let obj;
            if(!this.model.id){
              formData.cateId = that.cateId;
              obj=addMaterial(formData);
            }else{
              obj=editMaterial(formData);
            }
            obj.then((res)=>{
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


      validateMaterialcode(rule, value, callback){
        var params = {
          tableName: 'tms_bd_material',
          fieldName: 'name',
          fieldVal: value,
          dataId: this.id
        };
        duplicateCheck(params).then((res) => {
          if (res.success) {
          callback()
        } else {
          callback("物料名已存在!")
        }
      })
      },
      handleConfirmBlur  (e) {
        const value = e.target.value;
        this.confirmDirty = this.confirmDirty || !!value
      },



      // 根据屏幕变化,设置抽屉尺寸
      resetScreenSize(){
        let screenWidth = document.body.clientWidth;
        if(screenWidth < 500){
          this.drawerWidth = screenWidth;
        }else{
          this.drawerWidth = 700;
        }
      },
    }
  }
</script>

<style scoped>
  .avatar-uploader > .ant-upload {
    width:104px;
    height:104px;
  }
  .ant-upload-select-picture-card i {
    font-size: 49px;
    color: #999;
  }

  .ant-upload-select-picture-card .ant-upload-text {
    margin-top: 8px;
    color: #666;
  }

  .ant-table-tbody .ant-table-row td{
    padding-top:10px;
    padding-bottom:10px;
  }

  .drawer-bootom-button {
    position: absolute;
    bottom: -8px;
    width: 100%;
    border-top: 1px solid #e8e8e8;
    padding: 10px 16px;
    text-align: right;
    left: 0;
    background: #fff;
    border-radius: 0 0 2px 2px;
  }
</style>