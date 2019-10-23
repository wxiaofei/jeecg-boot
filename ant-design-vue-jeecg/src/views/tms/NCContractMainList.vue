<template>
  <a-card :bordered="false">
      <!--查询区域-->

    <div class="table-page-search-wrapper">
      <a-form layout="inline">

        <a-row>
          <a-col :md="6" :sm="24">
            <a-form-item label="合同号">
              <a-input placeholoder ="请输入合同号" v-model="queryParam.vbillcode"></a-input>
            </a-form-item>
          </a-col>

          <a-col :md="6" :sm="24">
            <a-form-item label="合同名称">
              <a-select placeholder="请输入合同名称" v-model="queryParam.ctname">
                   <a-select-option value="1">现价合同</a-select-option>
                    <a-select-option value="2">现货销售合同</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>

          <a-col :md="6" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type = "primaer"
            </span>
          </a-col>
        </a-row>
      </a-form>

    </div>
  </a-card>



</template>
<script>
  import ARow from 'ant-design-vue/es/grid/Row'
  import ACol from 'ant-design-vue/es/grid/Col'
  import AFormItem from 'ant-design-vue/es/form/FormItem'
  export default {
    components: { AFormItem, ACol, ARow }
  }
</script>