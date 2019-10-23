<template>
  <div class="components-input-demo-presuffix">
    <!---->
    <a-input @click="openModal" placeholder="请点击选择分类" v-model="MaterialCateNames" readOnly :disabled="disabled">
      <a-icon slot="prefix" type="cluster" title="分类选择控件"/>
      <a-icon v-if="MaterialCateIds" slot="suffix" type="close-circle" @click="handleEmpty" title="清空"/>
    </a-input>

    <j-select-MaterialCate-modal
      ref="innerMaterialCateSelectModal"
      :modal-width="modalWidth"
      :multi="multi"
      :rootOpened="rootOpened"
      :MaterialCate-id="value"
      @ok="handleOK"
      @initComp="initComp"/>
  </div>
</template>

<script>
  import JSelectMaterialCateModal from './modal/JSelectMaterialCateModal'
  export default {
    name: 'JSelectMaterialCate',
    components:{
      JSelectMaterialCateModal
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
        MaterialCateNames:"",
        MaterialCateIds:''
      }
    },
    mounted(){
      this.MaterialCateIds = this.value
    },
    watch:{
      value(val){
        this.MaterialCateIds = val
      }
    },
    methods:{
      initComp(MaterialCateNames){
        this.MaterialCateNames = MaterialCateNames
      },
      openModal(){
        this.$refs.innerMaterialCateSelectModal.show()
      },
      handleOK(rows,idstr){
        console.log("当前选中分类",rows)
        console.log("当前选中分类ID",idstr)
        if(!rows){
          this.MaterialCateNames = ''
          this.MaterialCateIds=''
        }else{
          let temp = ''
          for(let item of rows){
            temp+=','+item.cateName
          }
          this.MaterialCateNames = temp.substring(1)
          this.MaterialCateIds=idstr
        }
        this.$emit("change",this.MaterialCateIds)
      },
      getMaterialCateNames(){
        return this.MaterialCateNames
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