const app = new Vue({
    el:"#top",
    data:{
        value:"",
        chooseDepart:"",
        DepartList:[],
        RoleList:[],
        userInfo:[],
    },
    mounted:function () {
        axios.get('/getRolesAndDepart')
            .then(function (response) {
                app.$data.DepartList=response.data.data.departNameList;
                app.$data.RoleList = response.data.data.roleNameZhList;
                app.$data.userInfo = parent.userInfo
            })
    },
    methods:{
        handleChange() {
            if (this.value.length != 0) {
                let arr = this.$refs['departName'].getCheckedNodes()[0].pathLabels
                this.chooseDepart = arr[arr.length-1]
                console.log(this.chooseDepart)
            }
        }
    }
});

