<template>
  <div class="components-input-demo-presuffix">
    <!---->
    <a-input @click="openModal" placeholder="请点击选择司机" v-model="departNames" readOnly :disabled="disabled">
      <a-icon slot="prefix" type="cluster" title="部门选择控件"/>
      <a-icon v-if="departIds" slot="suffix" type="close-circle" @click="handleEmpty" title="清空"/>
    </a-input>

    <j-select-driver-modal
      ref="innerDriverSelectModal"
      :modal-width="modalWidth"
      :multi="multi"
      :rootOpened="rootOpened"
      :depart-id="value"
      @ok="handleOK"
      @initComp="initComp"/>
  </div>
</template>

<script>
  import JSelectDriverModal from './modal/JSelectDriverModal'
  export default {
    name: 'JSelectDriver',
    components:{
      JSelectDriverModal
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
        driverNames:"",
        driverIds:''
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
      initComp(driverNames){
        this.driverNames = driverNames
      },
      openModal(){
        this.$refs.innerDriverSelectModal.show()
      },
      handleOK(rows,idstr){
        console.log("当前选中部门",rows)
        console.log("当前选中部门ID",idstr)
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