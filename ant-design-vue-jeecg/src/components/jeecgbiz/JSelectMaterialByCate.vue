<template>
  <div>
    <a-input-search
      v-model="selectedCateMaterials"
      placeholder="请先选择物料"
      disabled
      @search="onSearchMaterial">
      <a-button slot="enterButton" :disabled="disabled">选择物料</a-button>
    </a-input-search>
    <j-select-material-by-cate-modal
      ref="selectModal"
      :modal-width="modalWidth"
      @ok="onSearchCateMaterialCallBack" />
  </div>
</template>

<script>
  import JSelectMaterialByCateModal from './modal/JSelectMaterialByCateModal'
  export default {
    name: 'JSelectMaterialByCate',
    components: { JSelectMaterialByCateModal },
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
        selectedCateMaterials:"",
      }
    },
    mounted(){
      this.selectedCateMaterials = this.value
    },
    watch:{
      value(val){
        this.selectedCateMaterials = val
      }
    },
    model: {
      prop: 'value',
      event: 'change'
    },
    methods: {
      //通过组织机构筛选选择用户
      onSearchMaterial() {
        this.$refs.selectModal.showModal()
        this.onSearchCateMaterialCallBack('')
      },
      onSearchCateMaterialCallBack(selected) {
        this.selectedCateMaterials = selected
        this.$emit("change",selected)
      }
    }
  }
</script>

<style scoped>

</style>