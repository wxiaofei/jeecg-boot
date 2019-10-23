<template>
    <div>
      <a-input-search
        v-model="selectedSupplierUser"
        placeholder="请先选择客户"
        disabled
        @search="onSearchDepUser">
        <a-button slot="enterButton" :disabled="disabled">选择客户</a-button>
      </a-input-search>
      <j-select-supplier-by-dep-modal
        ref="selectModal"
        :modal-width="modalWidth"
        @ok="onSearchDepUserCallBack" />
    </div>
</template>

<script type="text/ecmascript-6">
  import JSelectSupplierModal from './modal/JSelectSupplierModal'
  export default {
    name: 'JSelectSupplier',
    components: { JSelectSupplierModal },
    props:{
      modalWidth:{
        type:Number,
        default:1250,
        required:false
      },
      value:{
        type:String,
        required:false
      },
      disabled:{
        type: Boolean,
        required: false,
        default: false
      }
    },
    data() {
      return {
        selectedSupplierUser:"",
      }
    },
    mounted(){
      this.selectedSupplierUser = this.value
    },
    watch:{
      value(val){
        this.selectedSupplierUser = val
      }
    },
    model: {
      prop: 'value',
      event: 'change'
    },
    methods: {
      //通过组织机构筛选选择用户
      onSearchDepUser() {
        this.$refs.selectModal.showModal()
        this.onSearchDepUserCallBack('')
      },
      onSearchDepUserCallBack(selectedSupplierUser) {
        this.selectedDepUsers = selectedSupplierUser
        this.$emit("change",selectedSupplierUser)
      }
    }
  }
</script>

<style scoped>

</style>