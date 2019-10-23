<template>
  <a-modal
    :title="title"
    :width="800"
    :visible="visible"
    :confirmLoading="confirmLoading"
    :okButtonProps="{ props: {disabled: disableSubmit} }"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭">

    <!-- 编辑 -->

  </a-modal>
</template>

<script>
  import {httpAction} from '@/api/manage'
  import pick from 'lodash.pick'
  import Vue from 'vue'
  import {ACCESS_TOKEN} from "@/store/mutation-types"

  import { getUploadFileList,getFilePaths } from '@/utils/commonUploadFile.js'

  export default {
    name: "TmsContractDetailModal",
    data() {
      return {
        title: "操作",
        visible: false,
        model: {},
        labelCol: {
          xs: {span: 24},
          sm: {span: 5},
        },
        wrapperCol: {
          xs: {span: 24},
          sm: {span: 16},
        },
        // 表头
        columns: [
          {
            title: '客户名',
            align: "center",
            dataIndex: 'name',
          },
          {
            title: '性别',
            align: "center",
            dataIndex: 'sex',
          },
          {
            title: '身份证号码',
            align: "center",
            dataIndex: 'idcard',
          },
          {
            title: '身份证扫描件',
            align: "center",
            dataIndex: 'idcardPic',
          },
          {
            title: '电话',
            dataIndex: 'telphone',
            align: "center",
          },
          {
            title: '订单号码',
            dataIndex: 'orderId',
            align: "center",
          },
          {
            title: '创建人',
            dataIndex: 'createBy',
            align: "center",
          },
          {
            title: '创建时间',
            dataIndex: 'createTime',
            align: "center",
          },
          {
            title: '更新时间',
            dataIndex: 'updateBy',
            align: "center",
          },
          {
            title: '更新人',
            dataIndex: 'updateTime',
            align: "center",
          },
        ],
        fileList: [],
        disableSubmit: false,
        selectedRowKeys: [],
        orderId: "",
        hiding: false,
        headers: {},
        picUrl: "",
        picArray:[],
        previewVisible: false,
        previewImage: '',
        addStatus: false,
        editStatus: false,
        confirmLoading: false,
        form: this.$form.createForm(this),
        url: {
          add: "/test/order/addCustomer",
          edit: "/test/order/editCustomer",
          fileUpload: window._CONFIG['domianURL'] + "/sys/common/upload",
          imgerver: window._CONFIG['domianURL'] + "/sys/common/view",
          getOrderCustomerList: "/test/order/listOrderCustomerByMainId",
        },
        validatorRules: {
          telphone: {rules: [{validator: this.validateMobile}]},
          idcard: {rules: [{validator: this.validateIdCard}]}
        },
      }
    },
    computed: {
      uploadAction: function () {
        return this.url.fileUpload;
      }
    },
    created() {
      const token = Vue.ls.get(ACCESS_TOKEN);
      this.headers = {"X-Access-Token": token}
    },
    methods: {
      add(orderId) {
        this.hiding = true;
        if (orderId) {
          this.orderId = orderId;
          this.edit({orderId}, '');
        } else {
          this.$message.warning("请选择一个客户信息");
        }
      },
      detail(record) {
        this.edit(record, 'd');
      },


      edit(record, v) {
        if (v == 'e') {
          this.hiding = false;
          this.disableSubmit = false;
        } else if (v == 'd') {
          this.hiding = false;
          this.disableSubmit = true;
        } else {
          this.hiding = true;
          this.disableSubmit = false;
        }

        this.form.resetFields();
        this.orderId = record.orderId;
        let currFileList = getUploadFileList(record.idcardPic)
        this.fileList = [...currFileList]
        this.model = Object.assign({}, record);
        if (record.id) {
          this.hiding = false;
          this.addStatus = false;
          this.editStatus = true;
          this.$nextTick(() => {
            this.form.setFieldsValue(pick(this.model, 'id', 'name', 'sex', 'idcard','telphone', 'orderId', 'createBy', 'createTime', 'updateBy', 'updateTime'))
          });
        } else {
          this.addStatus = false;
          this.editStatus = true;
        }
        this.visible = true;
      },
      close() {
        this.$emit('close');
        this.visible = false;
        this.picUrl = "";
        this.fileList=[];
      },
      handleOk() {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if (!this.model.id) {
              httpurl += this.url.add;
              method = 'post';
            } else {
              httpurl += this.url.edit;
              method = 'put';
            }
            let formData = Object.assign(this.model, values);
            console.log(formData);
            formData.orderId = this.orderId;
            formData.idcardPic = getFilePaths(this.fileList)
            httpAction(httpurl, formData, method).then((res) => {
              if (res.success) {
                that.$message.success(res.message);
                that.$emit('ok');
              } else {
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
              that.close();
            })
          }
        })
      },
      handleCancel() {
        this.close();
      },
      validateMobile(rule, value, callback) {
        if (!value || new RegExp(/^1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\d{8}$/).test(value)) {
          callback();
        } else {
          callback("您的手机号码格式不正确!");
        }
      },
      validateIdCard(rule, value, callback) {
        if (!value || new RegExp(/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/).test(value)) {
          callback();
        } else {
          callback("您的身份证号码格式不正确!");
        }
      },
      handleChange(info) {
        this.fileList = info.fileList;
        if (info.file.status === 'uploading') {
          return
        }
        if (info.file.status === 'done') {
          var response = info.file.response;
          if (!response.success) {
            this.$message.warning(response.message);
          }
        }
      },
      handlePicCancel() {
        this.previewVisible = false
        this.previewImage=''
      },
      handlePicView(url){
        this.previewImage = this.url.imgerver + "/" + url
        this.previewVisible = true
      },
      handlePreview(file) {
        this.previewImage = file.url || file.thumbUrl
        this.previewVisible = true
      },
      getIdCardView(url) {
       // let pics = this.model.idcardPic.split(",");
        //let pics_len = pics.length;
        // 显示上传的最后一个图片
        return this.url.imgerver + "/" + url
      }
    }
  }
</script>

<style scoped>
  /* tile uploaded pictures */
  .upload-list-inline > > > .ant-upload-list-item {
    float: left;
    width: 200px;
    margin-right: 8px;
  }

  .upload-list-inline > > > .ant-upload-animate-enter {
    animation-name: uploadAnimateInlineIn;
  }

  .upload-list-inline > > > .ant-upload-animate-leave {
    animation-name: uploadAnimateInlineOut;
  }
</style>