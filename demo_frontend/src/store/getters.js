const getters = {
    token: state => state.user.token,   // token
    user: state => state.user.user,     // 用户对象
    role: state => state.user.role,      //用户角色
    userId:state=>state.user.userId
}
  export default getters
