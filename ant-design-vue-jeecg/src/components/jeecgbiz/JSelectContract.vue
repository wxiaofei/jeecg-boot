<template>
  <div>
    <a-input-search
      v-model="selectedContract"
      placeholder="请先选择合同"
      disabled
      @search="onSearchContract">
      <a-button slot="enterButton" :disabled="disabled">选择合同</a-button>
    </a-input-search>

    <j-select-contract-modal
      ref="selectModal"
      :modal-width="modalWidth"
      @ok="onSearchContractCallBack" />
  </div>
</template>


<script>
  import JSelectContractModal from './modal/JSelectContractModal'
  export default {
    name: 'JSelectContract',
    components:{
      JSelectContractModal
    },
    props:{
      modalWidth:{
        type:Number,
        default:500,
        required:false
      },
      multi:{
        type:Boolean,
        default:false,
        required:false
      },
      rootOpened:{
        type:Boolean,
        default:true,
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
    data(){
      return {
        visible:false,
        confirmLoading:false,
        selectedContract:"",
      }
    },
    mounted(){
      this.driverIds = this.value
    },
    watch:{
      value(val){
        this.driverIds = val
      }
    },
    methods:{

      onSearchContract() {
        this.$refs.selectModal.showModal()
        this.onSearchContractCallBack('')
      },
      onSearchContractCallBack(selected) {
        this.selectedCateMaterials = selected
        this.$emit("change",selected)
      },
      initComp(driverNames){
        this.driverNames = driverNames
      },
      openModal(){
        this.$refs.selectModal.show()
      },
      handleOK(rows,idstr){
        console.log("当前选中合同",rows)
        console.log("当前选中合同ID",idstr)
        if(!rows){
          this.driverNames = ''
          this.driverIds=''
        }else{
          let temp = ''
          for(let item of rows){
            temp+=','+item.name
          }
          this.driverNames = temp.substring(1)
          this.driverIds=idstr
        }
        this.$emit("change",this.driverIds)
      },
      getDriverNames(){
        return this.driverNames
      },
      handleEmpty(){
        this.handleOK('')
      }
    },
    model: {
      prop: 'value',
      event: 'change'
    }
  }
</script>

<style scoped>
  .components-input-demo-presuffix .anticon-close-circle {
    cursor: pointer;
    color: #ccc;
    transition: color 0.3s;
    font-size: 12px;
  }
  .components-input-demo-presuffix .anticon-close-circle:hover {
    color: #f5222d;
  }
  .components-input-demo-presuffix .anticon-close-circle:active {
    color: #666;
  }
</style>