<template>
  <div>
    <div style="margin-bottom: 5px">
      <el-input v-model="name" placeholder="请输入物品名" suffix-icon="el-icon-search" style="width: 120px"
                @keyup.enter.native="loadPost"></el-input>
      <el-select v-model="storage" placeholder="请选择仓库" style="margin-left: 5px;width: 120px">
        <el-option
          v-for="item in storageData"
          :key="item.id"
          :label="item.name"
          :value="item.id">
        </el-option>
      </el-select>
      <el-select v-model="goodstype" placeholder="请选择类型" style="margin-left: 5px; width: 120px"  >
        <el-option
          v-for="item in goodstypeData"
          :key="item.id"
          :label="item.name"
          :value="item.id">
        </el-option>
      </el-select>

      <el-button type="primary" style="margin-left: 5px" @click="loadPost">查询</el-button>
      <el-button type="success" style="margin-left: 5px" @click="resetParam" >重置</el-button>
      <el-button type="primary" style="margin-left: 5px" @click="add">新增</el-button>
      <el-button type="primary" style="margin-left: 5px" @click="inGoods">入库</el-button>
      <el-button type="primary" style="margin-left: 5px" @click="outGoods">出库</el-button>
    </div>
    <el-table :data="tableData"
              :header-cell-style="{background:'#f3f3fd',color:'#555'}"
              border
              highlight-current-row
              @current-change="selectCurrentChange"
    >
      <el-table-column prop="id" label="ID" width="80px">
      </el-table-column>
      <el-table-column prop="name" label="物品名" width="100px">
      </el-table-column>
      <el-table-column prop="storage" label="仓库" width="100px" :formatter="formatStorage">
      </el-table-column>
      <el-table-column prop="goodstype" label="分类" width="100px" :formatter="formatGoodsType">
      </el-table-column>
      <el-table-column prop="count" label="数量" width="100px">
      </el-table-column>
      <el-table-column prop="remark" label="备注">
      </el-table-column>
      <el-table-column prop="operate" label="操作" width="150px">
        <template slot-scope="scope">
          <el-button size="small" type="success" @click="mod(scope.row)">编辑</el-button>
          <el-popconfirm
            title="确定删除吗？"
            @confirm="del(scope.row.id)"
            style="margin-left: 5px;"
          >
            <el-button slot="reference" size="small" type="danger">删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="pageNum"
      :page-sizes="[2, 5, 7]"
      :page-size="pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total">
    </el-pagination>
    <el-dialog
      title="提示"
      :visible.sync="centerDialogVisible"
      width="30%"
      center>
      <el-form ref="form" :rules="rules" :model="form" label-width="70px">
        <el-form-item label="物品名" prop="name">
          <el-col :span="20">
            <el-input v-model="form.name"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="仓库" prop="storage" label-width="70px">
          <el-col :span="20">
            <el-select v-model="form.storage" placeholder="请选择仓库" style="margin-left: 5px">
              <el-option
                v-for="item in storageData"
                :key="item.id"
                :label="item.name"
                :value="item.id">
              </el-option>
            </el-select>
          </el-col>
        </el-form-item>
        <el-form-item label="分类" prop="goodstype" label-width="70px">
          <el-col :span="20">
            <el-select v-model="form.goodstype" placeholder="请选择类型" style="margin-left: 5px">
              <el-option
                v-for="item in goodstypeData"
                :key="item.id"
                :label="item.name"
                :value="item.id">
              </el-option>
            </el-select>
          </el-col>
        </el-form-item>
        <el-form-item label="数量" prop="count">
          <el-col :span="20">
            <el-input v-model="form.count"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-col :span="20">
            <el-input type="textarea" v-model="form.remark"></el-input>
          </el-col>
        </el-form-item>

      </el-form>
      <span slot="footer" class="dialog-footer">
    <el-button @click="centerDialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="save">确 定</el-button>
  </span>
    </el-dialog>
    <el-dialog
      title="提示"
      :visible.sync="inDialogVisible"
      width="30%"
      center>
      <el-dialog
        width="75%"
        title="用户选择"
        :visible.sync="innerVisible"
        append-to-body>
        <SelectUser @doSelectUser="doSelectUser"/>
        <span slot="footer" class="dialog-footer">
        <el-button @click="innerVisible = false">取 消</el-button>
        <el-button type="primary" @click="confirmUser">确 定</el-button>
  </span>
      </el-dialog>
      <el-form ref="form1" :rules="relus1" :model="form1" label-width="70px">
        <el-form-item label="物品名">
          <el-col :span="20">
            <el-input v-model="form1.goodsname" readonly></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="申请人">
          <el-col :span="20">
            <el-input v-model="form1.username" readonly @click.native="selectUser"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="数量" prop="count">
          <el-col :span="20">
            <el-input v-model="form1.count"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-col :span="20">
            <el-input type="textarea" v-model="form1.remark"></el-input>
          </el-col>
        </el-form-item>

      </el-form>
      <span slot="footer" class="dialog-footer">
    <el-button @click="inDialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="Goods">确 定</el-button>
  </span>
    </el-dialog>
  </div>

</template>

<script>
import SelectUser from "../user/SelectUser";
export default {

  name: "StorageManage",
  components:{SelectUser},
  data() {

    let checkCount = (rule, value, callback) => {
      if(value>9999){
        callback(new Error('数量过大'));
      }else{
        callback();
      }
    };
    let checkCount1 = (rule, value, callback) => {
      if(this.form1.action==1&&value>9999){
        callback(new Error('数量过大'));
      }else {
        this.$axios.get(this.$httpUrl + "/goods/findByNo?no=" + this.currentRow.id).then(res => res.data).then(res => {
          if (res.code != 200) {
            callback()
          } else {
            if (this.form1.action==1&&value >res.data.count) {
              callback(new Error('出库数量过量'))
            }
          }
        })
      }
    };
    return {
      user:JSON.parse(sessionStorage.getItem('CurUser')),
      storageData:[],
      goodstypeData:[],
      tableData:[],
      tableData1:[],
      pageSize:5,
      pageNum:1,
      total:0,
      name:'',
      storage: '',
      goodstype: '',
      centerDialogVisible:false,
      inDialogVisible:false,
      innerVisible:false,
      currentRow:{},
      tempUser:{},
      form:{
        id:'',
        name:'',
        storage:'',
        goodstype:'',
        count:0,
        remark:''
      },
      form1:{
        goods:'',
        goodsname:'',
        count:'',
        userid:'',
        adminId:'',
        remark:'',
        action:'1'

      },
      relus1:{
        userid: [
          { required: true, message: '请输入申请人名', trigger: 'blur' },
        ],
        count: [
          {required: true, message: '请输入数量', trigger: 'blur'},
          {pattern: /^([1-9][0-9]*){1,4}$/,message: '数量必须为正整数字',trigger: "blur"},
          {validator:checkCount1,trigger: 'blur'}
        ]
      },
      rules: {
        name: [
          { required: true, message: '请输入物品名', trigger: 'blur' },
        ],
        storage:[
          {required: true, message: '请选择仓库', trigger: 'blur'}
        ],
        goodstype:[
          {required: true, message: '请选择分类', trigger: 'blur'}
        ],
        count: [
          {required: true, message: '请输入数量', trigger: 'blur'},
          {pattern: /^([1-9][0-9]*){1,4}$/,message: '数量必须为正整数字',trigger: "blur"},
          {validator:checkCount,trigger: 'blur'}
        ],

      }
    }
  },
  methods:{
    doSelectUser(val){
      this.tempUser=val
    },
    confirmUser() {
      this.form1.username=this.tempUser.name
      this.form1.userid=this.tempUser.id
      this.innerVisible = false
    },
    selectCurrentChange(val) {
      this.currentRow = val;
    },
    formatStorage(row){
      let temp=this.storageData.find(item=>{
        return item.id==row.storage
      })

      return temp && temp.name
    },
    formatGoodsType(row){
      let temp=this.goodstypeData.find(item=>{
        return item.id==row.goodstype
      })

      return temp && temp.name
    },
    resetForm() {
      this.$refs.form.resetFields();
    },
    add(){
      this.centerDialogVisible=true
      this.$nextTick(()=>{
        this.resetForm()
      })
    },
    inGoods(){

      if(!this.currentRow.id){
        alert("请选择记录")
        return;
      }

      this.inDialogVisible=true
      this.$nextTick(()=>{
        this.resetInForm()
      })

      this.form1.goodsname=this.currentRow.name
      this.form1.goods=this.currentRow.id
      this.form1.adminId=this.user.id
      this.form1.action=2
    },
    selectUser(){
      this.innerVisible=true
    },
    resetInForm() {
      this.$refs.form1.resetFields();
    },
    outGoods(){
      if(!this.currentRow.id){
        alert("请选择记录")
        return;
      }

      this.inDialogVisible=true
      this.$nextTick(()=>{
        this.resetInForm()
      })

      this.form1.goodsname=this.currentRow.name
      this.form1.goods=this.currentRow.id
      this.form1.adminId=this.user.id
      this.form1.action=2
    },
    mod(row){
      this.centerDialogVisible = true
      this.$nextTick(()=>{
        //赋值到表单
        this.form.id = row.id
        this.form.name = row.name
        this.form.storage=row.storage
        this.form.goodstype=row.goodstype
        this.form.count=row.count
        this.form.remark = row.remark
      })
    },
    del(id){
      console.log(id)

      this.$axios.get(this.$httpUrl+'/goods/delete?id='+id).then(res=>res.data).then(res=>{
        console.log(res)
        if(res.code==200){

          this.$message({
            message: '操作成功！',
            type: 'success'
          });
          this.loadPost()
        }else{
          this.$message({
            message: '操作失败！',
            type: 'error'
          });
        }

      })

    },
    doSave(){
      this.$axios.post(this.$httpUrl+'/goods/save',this.form).then(res=>res.data).then(res=>{
        console.log(res)
        if(res.code==200){

          this.$message({
            message: '操作成功！',
            type: 'success'
          });
          this.centerDialogVisible = false
          this.loadPost()
          this. resetForm()
        }else{
          this.$message({
            message: '操作失败！',
            type: 'error'
          });
        }

      })
    },
    doMod(){
      this.$axios.post(this.$httpUrl+'/goods/update',this.form).then(res=>res.data).then(res=>{
        console.log(res)
        if(res.code==200){
          this.$message({
            message: '操作成功！',
            type: 'success'
          });
          this.centerDialogVisible = false
          this.loadPost()
          this. resetForm()
        }else{
          this.$message({
            message: '操作失败！',
            type: 'error'
          });
        }

      })
    },
    save(){
      this.$refs.form.validate((valid) => {
        if (valid) {
          if(this.form.id){
            this.doMod();
          }else{
            this.doSave();
          }
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    doInGoods(){
      this.$axios.post(this.$httpUrl+'/record/save',this.form1).then(res=>res.data).then(res=>{
        console.log(res)
        if(res.code==200){

          this.$message({
            message: '操作成功！',
            type: 'success'
          });
          this.inDialogVisible= false
          this.loadPost()
          this. resetForm()
        }else{
          this.$message({
            message: '操作失败！',
            type: 'error'
          });
        }

      })
    },
    goods(){
      this.$refs.form.validate((valid) => {
        if (valid) {
          if(this.form1.count){
            this.doInGoods()
          }
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    loadPost(){
      this.$axios.post(this.$httpUrl+'/goods/listPage',{
        pageSize:this.pageSize,
        pageNum:this.pageNum,
        param:{
          name:this.name,
          goodstype: this.goodstype+'',
          storage: this.storage+''
        }
      }).then(res=>res.data).then(res=>{
        console.log(res)
        if(res.code==200){
          this.tableData=res.data;
          this.total=res.total;
        }else {
          alert('获取数据失败')
        }

      })
    },
    loadStorage(){
      this.$axios.get(this.$httpUrl+'/storage/List').then(res=>res.data).then(res=>{
        console.log(res)
        if(res.code==200){
          this.storageData=res.data;
        }else {
          alert('获取数据失败')
        }

      })
    },
    loadGoodstype(){
      this.$axios.get(this.$httpUrl+'/goodstype/List').then(res=>res.data).then(res=>{
        console.log(res)
        if(res.code==200){
          this.goodstypeData=res.data;
        }else {
          alert('获取数据失败')
        }

      })
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.pageNum=1
      this.pageSize=val
      this.loadPost()
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.pageNum=val
      this.loadPost()
    },
    resetParam(){
      this.name=''
      this.storage=''
      this.goodstype=''
    }
  },
  beforeMount() {
    this.loadStorage()
    this.loadGoodstype()
    this.loadPost()
  }


}
</script>

<style scoped>

</style>
