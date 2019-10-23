<template>
  <div>
    <a-input-search
      v-model="selectedDepDrivers"
      placeholder="请先选择司机"
      disabled
      @search="onSearchDepDriver">
      <a-button slot="enterButton" :disabled="disabled">选择司机</a-button>
    </a-input-search>

    <j-select-Driver-by-dep-modal
      ref="selectModal"
      :modal-width="modalWidth"
      @ok="onSearchDepDriverCallBack" />
  </div>
</template>

<script>
  import JSelectDriverByDepModal from './modal/JSelectDriverByDepModal'
  export default {
    name: 'JSelectDriverByDep',
    components: { JSelectDriverByDepModal },
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
        selectedDepDrivers:"",
      }
    },
    mounted(){
      this.selectedDepDrivers = this.value
    },
    watch:{
      value(val){
        this.selectedDepDrivers = val
      }
    },
    model: {
      prop: 'value',
      event: 'change'
    },
    methods: {
      //通过组织机构筛选选择用户
      onSearchDepDriver() {
        this.$refs.selectModal.showModal()
        this.onSearchDepDriverCallBack('')
      },
      onSearchDepDriverCallBack(selectedDepDrivers) {
        this.selectedDepDrivers = selectedDepDrivers
        this.$emit("change",selectedDepDrivers)
      }
    }
  }
</script>

<style scoped>

</style>